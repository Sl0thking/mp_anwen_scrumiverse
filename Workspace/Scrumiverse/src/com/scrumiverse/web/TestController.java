package com.scrumiverse.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.User;

@Controller
public class TestController {

	@RequestMapping("/backlog.htm")
	public ModelAndView backlog(){
		ModelMap map=new ModelMap();
		map.addAttribute("user", new User());
		return new ModelAndView("backlog", map);
	}
}
