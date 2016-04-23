package com.scrumiverse.web;

import java.util.Date;
import java.util.Set;

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

import com.scrumiverse.binder.DateBinder;
import com.scrumiverse.exception.AccessViolationException;
import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.SprintPersistenceException;
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.exception.UserStoryPersistenceException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.PlanState;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.model.scrumFeatures.ChangeEvent;
import com.scrumiverse.model.scrumFeatures.HistoryEntry;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.SprintDAO;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

/**
 * Controller for Sprint interactions
 * @author Toni Serfling, Kevin Jolitz
 * @version 23.04.2016
 */

@Controller
public class SprintController extends MetaController {
	
	@Autowired
	private SprintDAO sprintDAO;

	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private UserStoryDAO userDAO;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateBinder());
	}
	
	/**
	 * Adds a new Sprint 
	 * @param HttpSession
	 * @return ModelAndView
	 * @throws ProjectPersistenceException 
	 */
	@RequestMapping("/addSprint.htm") 
	public ModelAndView addSprint(HttpSession session) throws ProjectPersistenceException {
		try {
			this.checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			Project project = this.loadCurrentProject(session);
			Sprint sprint = new Sprint();
			sprintDAO.saveSprint(sprint);
			sprint.addHistoryEntry(new HistoryEntry(user, ChangeEvent.SPRINT_CREATED));
			sprintDAO.updateSprint(sprint);
			project.addSprint(sprint);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:sprintOverview.htm");	
		}catch (UserPersistenceException | InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	/**
	 * Updates given Sprint
	 * @param HttpSession
	 * @param Sprint
	 * @return ModelAndView
	 */
	@RequestMapping("/updateSprint.htm")
	public ModelAndView updateSprint(HttpSession session, Sprint sprint) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Sprint);
			User user = this.loadActiveUser(session);
			Sprint oldSprint = sprintDAO.getSprint(sprint.getId());
			sprint.setHistory(oldSprint.getHistory());
			sprint.setUserStories(oldSprint.getUserStories());
			sprint.addHistoryEntry(new HistoryEntry(user, ChangeEvent.SPRINT_UPDATED));
			sprintDAO.updateSprint(sprint);
			return new ModelAndView("redirect:sprintOverview.htm");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (SprintPersistenceException | UserPersistenceException | ProjectPersistenceException | InsufficientRightsException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:sprintOverview.htm");
		} 
	}
	/**
	 * Deletes given sprint by id
	 * @param HttpSession
	 * @param int
	 * @return ModelAndView
	 */
	@RequestMapping("deleteSprint.htm")
	public ModelAndView deleteSprint(HttpSession session, @RequestParam int id) {
		try {
			checkInvalidSession(session);
			Sprint sprint = sprintDAO.getSprint(id);
			testIsPartOfCurrentProject(session, sprint);
			testRight(session, Right.Delete_Sprint);
			sprintDAO.deleteSprint(sprint);
			return new ModelAndView("redirect:sprintOverview.htm");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (SprintPersistenceException | ProjectPersistenceException |
				 UserPersistenceException | InsufficientRightsException e) {
			return new ModelAndView("redirect:sprintOverview.htm");
		} catch (AccessViolationException  e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}

	/**
	 * Shows all sprints
	 * @param session
	 * @return ModelAndView
	 * @throws ProjectPersistenceException
	 */
	@RequestMapping("/sprintOverview.htm")
	public ModelAndView sprintOverview(HttpSession session) throws ProjectPersistenceException {
		ModelMap map = new ModelMap();
		try {
			checkInvalidSession(session);
			map = this.prepareModelMap(session);
			int projectId = (int) session.getAttribute("currentProjectId");
			testRight(session, Right.Read_Sprint);
			Set<Sprint> sprints = sprintDAO.getSprintsFromProject(projectId);
			map.addAttribute("sprints", sprints);
			map.addAttribute("planstates", PlanState.values());
			map.addAttribute("action", Action.sprintOverview);
			map.addAttribute("project", this.loadCurrentProject(session));
			map.addAttribute("canCreateSprint", this.loadCurrentProject(session).getProjectUserFromUser(this.loadActiveUser(session)).getRole().hasRight(Right.Create_Sprint));
			map.addAttribute("canDeleteSprint", this.loadCurrentProject(session).getProjectUserFromUser(this.loadActiveUser(session)).getRole().hasRight(Right.Delete_Sprint));
			map.addAttribute("canUpdateSprint", this.loadCurrentProject(session).getProjectUserFromUser(this.loadActiveUser(session)).getRole().hasRight(Right.Update_Sprint));
			return new ModelAndView("index", map);
		} catch(InsufficientRightsException e) {
			map.addAttribute("action", Action.sprintOverview);
			map.addAttribute("project", this.loadCurrentProject(session));
			return new ModelAndView("index", map);
		} catch(InvalidSessionException | UserPersistenceException | SprintPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	/**
	 * Handles the addition and removal of multiple UserStories to given sprint by id
	 * @param HttpSession
	 * @param int
	 * @param String (addedStories)
	 * @param String (removedStories)
	 * @return ModelAndView
	 */
	@RequestMapping("/syncBacklogAndSprint.htm")
	public ModelAndView synchronizeSprintAndBacklog(HttpSession session, @RequestParam int sprintid, @RequestParam String addedStories, @RequestParam String removedStories) {
		try {
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			Sprint choosenSprint = sprintDAO.getSprint(sprintid);
			testIsPartOfCurrentProject(session, choosenSprint);
			testRight(session, Right.Update_Sprint);
			String[] addUserStoryIds = addedStories.split(",");
			String[] removeUserStoryIds = removedStories.split(",");
			//load all related user stories, add them to the sprint and remove them from the backlog
			if(!addedStories.equals("")) {
				for(String id : addUserStoryIds) {
					int usid = Integer.parseInt(id);
					UserStory userStory = userDAO.getUserStory(usid);
					choosenSprint.addUserStory(userStory);
					choosenSprint.addHistoryEntry(new HistoryEntry(user, ChangeEvent.USER_STORY_ASSIGNED));
					userStory.addHistoryEntry(new HistoryEntry(user, ChangeEvent.SPRINT_ASSIGNED));
					userDAO.updateUserStory(userStory);
					sprintDAO.updateSprint(choosenSprint);
				}
			}
			//load all related user stories, remove them from the sprint and add them to the backlog
			if(!removedStories.equals("")) {
				for(String id : removeUserStoryIds) {
					int usid = Integer.parseInt(id);
					UserStory uStory = userDAO.getUserStory(usid);
					removeUserStoryFromSprint(uStory, choosenSprint, user);
				}
			}
			return new ModelAndView("redirect:sprintOverview.htm");		
		} catch (UserStoryPersistenceException | SprintPersistenceException | ProjectPersistenceException | InsufficientRightsException e) {
			return new ModelAndView("redirect:sprintOverview.htm");
		} catch (AccessViolationException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (UserPersistenceException | InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	/**
	 * Removes given UserStory from given Sprint
	 * @param UserStory
	 * @param Sprint
	 * @param User
	 * @throws UserStoryPersistenceException
	 */
	private void removeUserStoryFromSprint(UserStory userStory, Sprint sprint, User user) throws UserStoryPersistenceException {
		sprint.removeUserStory(userStory);
		sprint.addHistoryEntry(new HistoryEntry(user, ChangeEvent.USER_STORY_REMOVED));
		userStory.addHistoryEntry(new HistoryEntry(user, ChangeEvent.SPRINT_REMOVED));
		userDAO.updateUserStory(userStory);
		sprintDAO.updateSprint(sprint);
	}
}