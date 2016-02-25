package com.scrumiverse.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.NoUserStoriesException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.UserStoryDAO;
import com.scrumiverse.utility.Utility;

/**
 * Controller for User Story interactions.
 * 
 * @author Lasse Jacobs
 * @version 24.02.16
 *
 */
@Controller
public class UserStoryController {
	
	@Autowired
	UserStoryDAO userStoryDAO;
	
	
	
	/**
	 * Create new empty UserStory in database
	 * @return ModelAndView
	 */
	@RequestMapping("/newUserStory.htm")
	public ModelAndView createNewUserStory(HttpSession session){
		Project project = (Project) session.getAttribute("currentProject");
		UserStory userStory = new UserStory();
		project.addUserStory(userStory);
		userStoryDAO.saveUserStory(userStory);
		return new ModelAndView("redirect:backlog.htm");
	}
	
	/**
	 * Shows backlog site with all UserStories
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/backlog.htm")
	public ModelAndView backlog(HttpSession session){
		ModelMap map = Utility.generateModelMap(session);
		List<UserStory> userStories = userStoryDAO.getAllUserstories();
		map.addAttribute("userstories", userStories);
		for(UserStory us: userStories){
			System.out.println(us.getDescription());
		}
		map.addAttribute("action", Action.backlog);
		return new ModelAndView("index", map);
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
		ModelMap map = Utility.generateModelMap(session);
		UserStory loadedUserStory = userStoryDAO.getUserStory(userStoryID);
		map.addAttribute("detailUserStory", loadedUserStory);
		return new ModelAndView("redirect:backlog.htm");
	}
}
