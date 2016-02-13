package com.scrumiverse.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.User;

@Controller
public class IndexController {		
	/**
	 * RequestMapping for the index page
	 * @return The index page
	 */
	@RequestMapping("/index.htm")
	public ModelAndView test() 
	{
		ModelMap map = new ModelMap();
		map.addAttribute("user", new User());
		map.addAttribute("action", Action.login);
		return new ModelAndView("index", map);
	}
}