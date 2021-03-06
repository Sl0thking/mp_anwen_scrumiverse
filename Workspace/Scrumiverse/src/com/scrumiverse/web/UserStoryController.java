package com.scrumiverse.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.binder.CategoryBinder;
import com.scrumiverse.binder.DateBinder;
import com.scrumiverse.binder.SprintBinder;
import com.scrumiverse.binder.UserStoryBinder;
import com.scrumiverse.enums.Action;
import com.scrumiverse.enums.ChangeEvent;
import com.scrumiverse.enums.MoscowState;
import com.scrumiverse.enums.PlanState;
import com.scrumiverse.enums.Right;
import com.scrumiverse.exception.AccessViolationException;
import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.exception.UserStoryPersistenceException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.model.scrumFeatures.HistoryEntry;
import com.scrumiverse.model.scrumFeatures.Notification;
import com.scrumiverse.persistence.DAO.CategoryDAO;
import com.scrumiverse.persistence.DAO.HistoryDAO;
import com.scrumiverse.persistence.DAO.NotificationDAO;
import com.scrumiverse.persistence.DAO.SprintDAO;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

/**
 * Controller for User Story interactions.
 * 
 * @author Lasse Jacobs, Kevin Jolitz
 * @version 24.04.16
 *
 */
@Controller
public class UserStoryController extends MetaController {
	
	@Autowired
	private UserStoryDAO userStoryDAO;
	
	@Autowired
	private SprintDAO sprintDAO;
	
	@Autowired
	private HistoryDAO historyDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(UserStory.class, new UserStoryBinder(userStoryDAO));
		binder.registerCustomEditor(Sprint.class, new SprintBinder(sprintDAO));
		binder.registerCustomEditor(Date.class, new DateBinder());
		binder.registerCustomEditor(Category.class, new CategoryBinder(categoryDAO));
	}
	
	/**
	 * Creates a new userstory in database.
	 * @param session The current session
	 * @return ModelAndView
	 * @throws ProjectPersistenceException
	 */
	@RequestMapping("/newUserStory.htm")
	public ModelAndView createNewUserStory(HttpSession session) throws ProjectPersistenceException{
		try {
			checkInvalidSession(session);
			User activeUser = this.loadActiveUser(session);
			Project project = this.loadCurrentProject(session);
			UserStory userStory = new UserStory();
			userStoryDAO.saveUserStory(userStory);
			HistoryEntry entry = new HistoryEntry(activeUser, ChangeEvent.USER_STORY_CREATED);
			historyDAO.saveHistoryEntry(entry);
			userStory.addHistoryEntry(entry);
			userStoryDAO.updateUserStory(userStory);
			project.addUserStory(userStory);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:backlog.htm");
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * 
	 * @param session The current session
	 * @return ModelAndView
	 * @throws ProjectPersistenceException
	 */
	@RequestMapping("/backlog.htm")
	public ModelAndView backlog(HttpSession session) throws ProjectPersistenceException{
		ModelMap map = new ModelMap();
		try {
			checkInvalidSession(session);
			map = this.prepareModelMap(session);
			int projectId = (int) session.getAttribute("currentProjectId");
			Project project = this.loadCurrentProject(session);
			User user = this.loadActiveUser(session);
			//Check if user has right to read user stories
			//when not, exception is thrown and backend is not loading user stories
			testRight(session, Right.Read_UserStory);
			SortedSet<UserStory> userStories = userStoryDAO.getUserStoriesOfProject(projectId);
			map.addAttribute("userstories", userStories.toArray(new UserStory[userStories.size()]));
			map.addAttribute("action", Action.backlog);
			map.addAttribute("planstates", PlanState.values());
			map.addAttribute("moscows", MoscowState.values());
			map.addAttribute("sdf", new SimpleDateFormat("yyyy-MM-dd"));
			map.addAttribute("canUpdateUserstory", project.getProjectUserFromUser(user).getRole().hasRight(Right.Update_UserStory));
			map.addAttribute("canCreateUserStory", project.getProjectUserFromUser(user).getRole().hasRight(Right.Create_UserStory));
			map.addAttribute("canDeleteUserStory", project.getProjectUserFromUser(user).getRole().hasRight(Right.Delete_UserStory));
			map.addAttribute("canDeleteTask", project.getProjectUserFromUser(user).getRole().hasRight(Right.Delete_Task));
			map.addAttribute("canUpdateSprint", project.getProjectUserFromUser(user).getRole().hasRight(Right.Update_Sprint));
			return new ModelAndView("index", map);
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (InsufficientRightsException e) {
			map.addAttribute("action", Action.backlog);
			return new ModelAndView("index", map);
		}
	}
	
	/**
	 * Updates a given userstory in the database.
	 * @param session The current session
	 * @param userStory The updated userstory
	 * @return ModelAndView
	 * @throws UserStoryPersistenceException
	 */
	@RequestMapping("changeUserStory.htm")
	public ModelAndView updateUserStory(HttpSession session, UserStory userStory) throws UserStoryPersistenceException{
		try {
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			UserStory oldUStory = userStoryDAO.getUserStory(userStory.getId());
			userStory.setTasks(oldUStory.getTasks());
			userStory.setHistory(oldUStory.getHistory());
			checkSprintChangeEvents(oldUStory, userStory, user);
			userStory.addHistoryEntry(ChangeEvent.USER_STORY_UPDATED, user);
			userStoryDAO.updateUserStory(userStory);
			generateNotification(session, ChangeEvent.USER_STORY_UPDATED, userStory);
			return new ModelAndView("redirect:backlog.htm");
		}catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Deletes a userstory with given id in database.
	 * @param session The current session
	 * @param id The id from the userstory that needs to be deleted
	 * @return ModelAndView
	 */
	@RequestMapping("/removeUserStory.htm")
	public ModelAndView removeUserStory(HttpSession session, @RequestParam int id) {
		try{
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			UserStory userStory = userStoryDAO.getUserStory(id);
			testIsPartOfCurrentProject(session, userStory);
			testRight(session, Right.Delete_UserStory);
			Sprint sprint = userStory.getRelatedSprint();
			if(sprint != null) {
				sprint.addHistoryEntry(ChangeEvent.USER_STORY_REMOVED, user);
				sprintDAO.updateSprint(sprint);
			}
			userStoryDAO.deleteUserStory(userStoryDAO.getUserStory(id));
			return new ModelAndView("redirect:backlog.htm");
		}catch(UserStoryPersistenceException | UserPersistenceException |ProjectPersistenceException e){
			return new ModelAndView("redirect:backlog.htm");
		} catch(InsufficientRightsException | AccessViolationException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InvalidSessionException e){
			return new ModelAndView("redirect:login.htm");
		} 
	}
	
	/**
	 * Checks if a history entry has to be generated for the given sprint and userstory
	 * @param oldUStory The old userstory
	 * @param userStory The updated userstory
	 * @param user The current logged in user
	 */
	private void checkSprintChangeEvents(UserStory oldUStory, UserStory userStory, User user) {
		//Change from Backlog to Sprint (adding or removing)
		if((oldUStory.getRelatedSprint() == null && userStory.getRelatedSprint() != null) ||
		   (oldUStory.getRelatedSprint() != null && userStory.getRelatedSprint() == null)) {
			//Expect adding a userStory to sprint
			ChangeEvent userStoryEvent;
			//But when sprint is removed, change events
			if(userStory.getRelatedSprint() == null) {
				userStoryEvent = ChangeEvent.SPRINT_REMOVED;
				oldUStory.getRelatedSprint().addHistoryEntry(ChangeEvent.USER_STORY_REMOVED, user);
				sprintDAO.updateSprint(oldUStory.getRelatedSprint());
			} else {
				userStoryEvent = ChangeEvent.SPRINT_ASSIGNED;
				userStory.getRelatedSprint().addHistoryEntry(ChangeEvent.USER_STORY_ASSIGNED, user);
				sprintDAO.updateSprint(userStory.getRelatedSprint());
			}
			userStory.addHistoryEntry(userStoryEvent, user);
			userStoryDAO.updateUserStory(userStory);
		//Change betweens Sprints
		} else if(oldUStory.getRelatedSprint() != null && userStory.getRelatedSprint() != null && !oldUStory.getRelatedSprint().equals(userStory.getRelatedSprint())) {
			//save in old sprint event
			oldUStory.getRelatedSprint().addHistoryEntry(ChangeEvent.USER_STORY_REMOVED, user);
			sprintDAO.updateSprint(oldUStory.getRelatedSprint());
			//save in new sprint assignment
			userStory.getRelatedSprint().addHistoryEntry(ChangeEvent.USER_STORY_ASSIGNED, user);
			sprintDAO.updateSprint(userStory.getRelatedSprint());
			//add Sprint Events to User Story
			userStory.addHistoryEntry(ChangeEvent.SPRINT_REMOVED, user);
			userStory.addHistoryEntry(ChangeEvent.SPRINT_ASSIGNED, user);
			userStoryDAO.updateUserStory(userStory);
		}
	}
	
	/**
	 * Generates a notification with given userstory and changeevent.
	 * @param session The current session
	 * @param event The event that specified the change
	 * @param userstory The userstory that has been changed
	 */
	private void generateNotification(HttpSession session, ChangeEvent event, UserStory userstory) {
		try {
			User user = this.loadActiveUser(session);
			Project project = this.loadCurrentProject(session);
			// Loops through all users in project
			for(User u: project.getAllUsers()){
				// Checks if the user of the changeEvent isnt the current user u
				// Checks if there is a relatedSprint in unserstory and whether this is the current one or not.
				if(!u.equals(user) && project.getCurrentSprint() != null && userstory.getRelatedSprint() != null && project.getCurrentSprint().equals(userstory.getRelatedSprint())){
					// Checks if the current user u has rights to be notified
					if(		(project.hasUserRight(Right.Notify_Your_UserStory_Task, u) && userstory.getResponsibleUsers().contains(u))
						||	(project.hasUserRight(Right.Notify_UserStory_Task_for_Current_Sprint, u))
					){
						Notification notify = new Notification(user, u, event, userstory);
						notificationDAO.saveNotification(notify);
						u.addNotification(notify);
					}
				}
			}
		} catch (UserPersistenceException | ProjectPersistenceException e) {
			e.printStackTrace();
		}
	}
	
}