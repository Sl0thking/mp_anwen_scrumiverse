package com.scrumiverse.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.binder.DateBinder;
import com.scrumiverse.binder.SprintBinder;
import com.scrumiverse.binder.UserStoryBinder;
import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.NoUserStoryFoundException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.PlanState;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.model.scrumFeatures.ChangeEvent;
import com.scrumiverse.model.scrumFeatures.HistoryEntry;
import com.scrumiverse.model.scrumFeatures.MoscowState;
import com.scrumiverse.model.scrumFeatures.WorkLog;
import com.scrumiverse.persistence.DAO.HistoryDAO;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.SprintDAO;
import com.scrumiverse.persistence.DAO.TaskDAO;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

/**
 * Controller for User Story interactions.
 * 
 * @author Lasse Jacobs, Kevin Jolitz
 * @version 17.03.16
 *
 */
@Controller
public class UserStoryController extends MetaController {
	
	@Autowired
	private UserStoryDAO userStoryDAO;
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private SprintDAO sprintDAO;
	
	@Autowired
	private HistoryDAO historyDAO;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(UserStory.class, new UserStoryBinder(userStoryDAO));
		binder.registerCustomEditor(Sprint.class, new SprintBinder(sprintDAO));
		binder.registerCustomEditor(Date.class, new DateBinder());
	}
	
	/**
	 * Create new empty UserStory in database
	 * @return ModelAndView
	 * @throws NoProjectFoundException 
	 */
	@RequestMapping("/newUserStory.htm")
	public ModelAndView createNewUserStory(HttpSession session) throws NoProjectFoundException{
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
			
			//Dummy Tasks
//			Random rand = new Random();
//			Task t = new Task();
//			taskDAO.saveTask(t);
//			t.setPlannedTimeOfUser( activeUser, rand.nextInt(((100 - 0) + 1) + 0));
//			// Worklog wird nicht in Hibernate gespeichert
//			WorkLog wl = new WorkLog();
//			wl.setUser( activeUser );
//			wl.setLoggedMinutes(rand.nextInt(((100 - 0) + 1) + 0));
//			t.logWork(wl);
//			taskDAO.updateTask(t);
//			userStory.addTask(t);
//			userStoryDAO.updateUserStory(userStory);
//			System.out.println("------------------------"+t.getWorkMin());
			//Dummy Tasks
			project.addUserStory(userStory);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:backlog.htm");
		} catch (InvalidSessionException | NoSuchUserException e) {
			return new ModelAndView("redirect:login.htm");
		}
		
	}
	
	/**
	 * Shows backlog site with all UserStories
	 * @param session
	 * @return ModelAndView
	 * @throws NoProjectFoundException 
	 */
	@RequestMapping("/backlog.htm")
	public ModelAndView backlog(HttpSession session) throws NoProjectFoundException{
		ModelMap map = new ModelMap();
		try {
			checkInvalidSession(session);
			map = this.prepareModelMap(session);
			int projectId = (int) session.getAttribute("currentProjectId");
			Project project = this.loadCurrentProject(session);
			User user = this.loadActiveUser(session);
			testRight(session, Right.Read_UserStory);
			SortedSet<UserStory> userStories = userStoryDAO.getUserStoriesOfProject(projectId);
			map.addAttribute("userstories", userStories.toArray(new UserStory[userStories.size()]));
			map.addAttribute("action", Action.backlog);
			map.addAttribute("planstates", PlanState.values());
			map.addAttribute("moscows", MoscowState.values());
			map.addAttribute("sdf", new SimpleDateFormat("yyyy-MM-dd"));
			map.addAttribute("createUserStory", project.getProjectUserFromUser(user).getRole().hasRight(Right.Create_UserStory));
			return new ModelAndView("index", map);
		} catch (InvalidSessionException | NoSuchUserException e) {
			return new ModelAndView("redirect:login.html");
		} catch (InsufficientRightsException e) {
			map.addAttribute("action", Action.backlog);
			return new ModelAndView("index", map);
		}
	}
	
	/**
	 * Updates a UserStory in database
	 * @param userStory
	 * @return ModelAndView
	 */
	@RequestMapping("changeUserStory.htm")
	public ModelAndView updateUserStory(HttpSession session, UserStory userStory, BindingResult result){
		try {
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			
			if(userStory.getId() == 0){
				System.out.println("Userstory not found");
			}else{
				userStory.addHistoryEntry(ChangeEvent.USER_STORY_UPDATED, user);
				userStoryDAO.updateUserStory(userStory);
			}
			return new ModelAndView("redirect:backlog.htm");
		}catch (InvalidSessionException | NoSuchUserException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	@RequestMapping("/removeUserStory.htm")
	public ModelAndView removeUserStory(HttpSession session, @RequestParam int id) {
		try{
			checkInvalidSession(session);
			userStoryDAO.deleteUserStory(userStoryDAO.getUserStory(id));
			return new ModelAndView("redirect:backlog.htm");
		}catch(NoUserStoryFoundException e){
			return new ModelAndView("redirect:backlog.htm");
		}catch (InvalidSessionException e){
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Show details for a UserStory
	 * @param userStoryID id of the UserStory
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/showUserStoryDetails.htm")
	public ModelAndView showUserStoryDetails(@RequestParam int userStoryID, HttpSession session){
		try {
			checkInvalidSession(session);
			ModelMap map = this.prepareModelMap(session);
			UserStory loadedUserStory = userStoryDAO.getUserStory(userStoryID);
			map.addAttribute("detailUserStory", loadedUserStory);
		} catch (NoUserStoryFoundException e) {
			e.printStackTrace();
		} catch (InvalidSessionException  | NoSuchUserException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (NoProjectFoundException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
		return new ModelAndView("redirect:backlog.htm");
	}
}