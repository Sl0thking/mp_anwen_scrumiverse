package com.scrumiverse.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.UserStoryDAO;
import com.scrumiverse.persistence.DAO.impl.NoUserStoriesException;
import com.scrumiverse.utility.Utility;

@Controller
public class UserStoryController {
	
	@Autowired
	UserStoryDAO userStoryDAO;
	
	@RequestMapping("/backlog.htm")
	public ModelAndView backlog(HttpSession session){
		ModelMap map = Utility.generateModelMap(session);
		List<UserStory> userStories = userStoryDAO.getAllUserstories();
		map.addAttribute("userStories", userStories);
		map.addAttribute("action", Action.backlog);
		return new ModelAndView("index", map);
	}
	
	@RequestMapping("/add_userstory.htm")
	public ModelAndView addUserStory(UserStory userstory, HttpSession session){
		ModelMap map = Utility.generateModelMap(session);
		userStoryDAO.addUserStory(userstory);
		map.addAttribute("action", Action.backlog);
		// ??
		map.addAttribute("userstory", new UserStory());
		return new ModelAndView("index", map);
	}
	

}
