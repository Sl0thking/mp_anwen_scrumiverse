package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.RoleDAO;

@Controller
public class RoleController extends MetaController {
	
	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@RequestMapping("/updateRole.htm")
	public ModelAndView updateRole(Role role) {
		roleDAO.updateRole(role);
		return new ModelAndView("redirect:projectOverview.htm");
	}
	
	@RequestMapping("/addRole.htm")
	public ModelAndView addRole(HttpSession session) {
		try {
			Project currentProject = this.loadCurrentProject(session);
			Role role = new Role();
			roleDAO.saveRole(role);
			currentProject.addRole(role);
			projectDAO.updateProject(currentProject);
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (NoProjectFoundException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
}