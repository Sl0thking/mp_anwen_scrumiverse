package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {		
	/**
	 * RequestMapping for the index page
	 * @return The index page
	 */
	@RequestMapping("/index.htm")
	public ModelAndView test(HttpSession session) 
	{
		return new ModelAndView("redirect:login.htm");
	}
}