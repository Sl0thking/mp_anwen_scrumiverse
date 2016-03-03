package com.scrumiverse.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.model.scrumFeatures.WorkLog;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.TaskDAO;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

/**
 * Controller for User Story interactions.
 * 
 * @author Lasse Jacobs, Kevin Jolitz
 * @version 27.02.16
 *
 */
@Controller
public class UserStoryController extends MetaController {
	
	@Autowired
	UserStoryDAO userStoryDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	TaskDAO taskDAO;
	
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
			//Dummy Tasks
			Random rand = new Random();
			Task t = new Task();
			taskDAO.saveTask(t);
			t.setPlannedTimeOfUser( activeUser, rand.nextInt(((100 - 0) + 1) + 0));
			// Worklog wird nicht in Hibernate gespeichert
			WorkLog wl = new WorkLog();
			wl.setUser( activeUser );
			wl.setLoggedMinutes(rand.nextInt(((100 - 0) + 1) + 0));
			t.logWork(wl);
			taskDAO.updateTask(t);
			userStory.addTask(t);
			userStoryDAO.updateUserStory(userStory);
			System.out.println("------------------------"+t.getWorkMin());
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
		try {
			checkInvalidSession(session);
			ModelMap map = this.prepareModelMap(session);
			int projectId = (int) session.getAttribute("currentProjectId");
			Set<UserStory> userStories = userStoryDAO.getUserStoriesOfProject(projectId);
			map.addAttribute("userstories", userStories);
			map.addAttribute("action", Action.backlog);
			return new ModelAndView("index", map);
		} catch (InvalidSessionException | NoSuchUserException e) {
			return new ModelAndView("redirect:login.html");
		}
	}
	
	/**
	 * Updates a UserStory in database
	 * @param userStory
	 * @return ModelAndView
	 */
	@RequestMapping("/saveUserStory.htm")
	public ModelAndView updateUserStory(UserStory userStory){
		if(userStory.getId() == 0){
			System.out.println("Userstory not found");
		}else{
			userStoryDAO.updateUserStory(userStory);
		}
		return new ModelAndView("redirect:backlog.htm");
		
	}
	
	/**
	 * Show details for a UserStory
	 * @param userStoryID id of the UserStory
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/showUserStoryDetails.htm")
	public ModelAndView showUserStoryDetails(@RequestParam int userStoryID, HttpSession session){
		ModelMap map;
		try {
			map = this.prepareModelMap(session);
			UserStory loadedUserStory = userStoryDAO.getUserStory(userStoryID);
			map.addAttribute("detailUserStory", loadedUserStory);
		} catch (NoSuchUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoProjectFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:backlog.htm");
	}
}
