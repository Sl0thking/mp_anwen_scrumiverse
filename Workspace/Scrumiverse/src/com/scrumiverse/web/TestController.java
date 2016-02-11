package com.scrumiverse.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.User;

@Controller
public class TestController {
	
	@RequestMapping("/login.htm")
	public ModelAndView test(){
		ModelMap map=new ModelMap();
		map.addAttribute("dummy", new User());
		return new ModelAndView("test", map);
	}
}