package com.scrumiverse.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.utility.Utility;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@RequestMapping("/projectOverview.htm")
	public ModelAndView project_overview(HttpSession session) {
		ModelMap map = Utility.generateModelMap(session);
		
		map.addAttribute("projectList", projectDAO.getAllProjects());
		map.addAttribute("action", Action.projectOverview);
		return new ModelAndView("index", map);
	}
	
	@RequestMapping("/add_project.htm")
	public ModelAndView addProject(Project project, HttpSession session) {		
		project.addUser((User) session.getAttribute("loggedUser"));
		projectDAO.addProject(project);

		ModelMap map = Utility.generateModelMap(session);
		map.addAttribute("projectList", projectDAO.getAllProjects());
		map.addAttribute("action", Action.projectOverview);
		return new ModelAndView("index", map);		 		 
	 }
	
}