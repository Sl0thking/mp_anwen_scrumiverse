package com.scrumiverse.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.Role;

@Controller
public class RoleController {
	
	@RequestMapping("/updateRole.htm")
	public ModelAndView updateRole(Role role) {
		System.out.println(role.getRights());
		return new ModelAndView("redirect:projectOverview.htm");
	}

}
