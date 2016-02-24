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
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.UserStoryDAO;
import com.scrumiverse.utility.Utility;

/**
 * Controller for User Story interactions.
 * 
 * @author Lasse Jacobs
 * @version 22.02.16
 *
 */
@Controller
public class UserStoryController {
	
	@Autowired
	UserStoryDAO userStoryDAO;
	
	
	@RequestMapping("/newUserStory.htm")
	public ModelAndView createNewUserStory(){
		UserStory userStory = new UserStory();
		userStoryDAO.saveUserStory(userStory);
		return new ModelAndView("redirect:backlog.htm");
	}
	
	@RequestMapping("/backlog.htm")
	public ModelAndView backlog(HttpSession session){
		ModelMap map = Utility.generateModelMap(session);
		List<UserStory> userStories = userStoryDAO.getAllUserstories();
		map.addAttribute("userStories", userStories);
		map.addAttribute("action", Action.backlog);
		return new ModelAndView("index", map);
	}
	
	@RequestMapping("/saveUserStory.htm")
	public ModelAndView updateUserStory(UserStory userStory){
		userStoryDAO.updateUserStory(userStory);
		return new ModelAndView("redirect:backlog.htm");
		
	}
	
	@RequestMapping("/showUserStoryDetails.htm")
	public ModelAndView showUserStoryDetails(@RequestParam int userStoryID, HttpSession session){
		ModelMap map = Utility.generateModelMap(session);
		UserStory loadedUserStory = userStoryDAO.getUserStory(userStoryID);
		map.addAttribute("detailUserStory", loadedUserStory);
		return new ModelAndView("redirect:backlog.htm");
	}
	

}
