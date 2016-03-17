package com.scrumiverse.web;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSprintFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.NoUserStoryFoundException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.SprintDAO;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

/**
 * Controller for Sprint interactions
 * @author Toni Serfling, Kevin Jolitz
 * @version 04.03.2016
 */

@Controller
public class SprintController extends MetaController {
	
	@Autowired
	private SprintDAO sprintDAO;

	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private UserStoryDAO userDAO;
	/**
	 * Adds a new Sprint 
	 * @return
	 * @throws NoProjectFoundException 
	 */
	
	@RequestMapping("/addSprint.htm") 
	public ModelAndView addSprint(HttpSession session) throws NoProjectFoundException {
		try {
			this.checkInvalidSession(session);
			Project project = this.loadCurrentProject(session);
			Sprint sprint = new Sprint();
			sprintDAO.saveSprint(sprint);
			project.addSprint(sprint);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:sprintOverview.htm");	
		}catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	/**
	 * Shows all sprints
	 * @param session
	 * @return ModelAndView
	 * @throws NoProjectFoundException
	 */
	@RequestMapping("/sprintOverview.htm")
	public ModelAndView sprintOverview(HttpSession session) throws NoProjectFoundException {
		try {
			this.checkInvalidSession(session);
			ModelMap map = this.prepareModelMap(session);
			int projectId = (int) session.getAttribute("currentProjectId");
			Set<Sprint> sprints = sprintDAO.getSprintsFromProject(projectId);
			map.addAttribute("sprints", sprints);
			map.addAttribute("action", Action.sprintOverview);
			map.addAttribute("project", this.loadCurrentProject(session));
			return new ModelAndView("index", map);
		} catch(InvalidSessionException | NoSuchUserException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	@RequestMapping("/syncBacklogAndSprint.htm")
	public ModelAndView synchronizeSprintAndBacklog(HttpSession session, @RequestParam int sprintid, @RequestParam String addedStories, @RequestParam String removedStories) {
		try {
			checkInvalidSession(session);
			Project activeProject = this.loadCurrentProject(session);
			Sprint choosenSprint = sprintDAO.getSprint(sprintid);
			String[] addUserStoryIds = addedStories.split(",");
			String[] removeUserStoryIds = removedStories.split(",");
			//load all related user stories, add them to the sprint and remove them from the backlog
			if(!addedStories.equals("")) {
				for(String id : addUserStoryIds) {
					int usid = Integer.parseInt(id);
					UserStory userStory = userDAO.getUserStory(usid);
					choosenSprint.addUserStory(userStory);
					sprintDAO.updateSprint(choosenSprint);
				}
			}
			//load all related user stories, remove them from the sprint and add them to the backlog
			if(!removedStories.equals("")) {
				for(String id : removeUserStoryIds) {
					int usid = Integer.parseInt(id);
					UserStory userStory = userDAO.getUserStory(usid);
					choosenSprint.removeUserStory(userStory);
					sprintDAO.updateSprint(choosenSprint);
				}
			}
			return new ModelAndView("redirect:sprintOverview.htm");		
		} catch (NoProjectFoundException | NoUserStoryFoundException | NoSprintFoundException e) {
			return new ModelAndView("redirect:sprintOverview.htm");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
}