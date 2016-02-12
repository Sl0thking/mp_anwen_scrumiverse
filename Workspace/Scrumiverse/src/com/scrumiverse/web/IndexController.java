package com.scrumiverse.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		map.addAttribute("action", Action.login);
		return new ModelAndView("index", map);
	}
}