package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * Controller for the Index-Page
 * 
 * @author Kevin Wesseler
 * @version 20.04.2016
 */
@Controller
public class IndexController {
	
	/**
	 * RequestMapping for the index page
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/index.htm")
	public ModelAndView test(HttpSession session) {
		return new ModelAndView("redirect:login.htm");
	}
}