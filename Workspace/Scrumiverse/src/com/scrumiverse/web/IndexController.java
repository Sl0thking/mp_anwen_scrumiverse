package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.User;
import com.scrumiverse.utility.Utility;

@Controller
public class IndexController {		
	/**
	 * RequestMapping for the index page
	 * @return The index page
	 */
	@RequestMapping("/index.htm")
	public ModelAndView test(HttpSession session) 
	{
		ModelMap map = Utility.generateModelMap(session);
		map.addAttribute("user", new User());
		return new ModelAndView("index", map);
	}
}