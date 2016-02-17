package com.scrumiverse.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.impl.NoProjectsFoundException;
import com.scrumiverse.utility.Utility;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@RequestMapping("/project_overview.htm")
	public ModelAndView project_overview(HttpSession session) throws NoProjectsFoundException {
		ModelMap map = Utility.generateModelMap(session);
		
		map.addAttribute("projectList", projectDAO.getAllProjects());
		map.addAttribute("action", Action.project_overview);
		return new ModelAndView("project_overview", map);
	}
	
	@RequestMapping("/add_project.htm")
	public ModelAndView addProject(Project project) {
		
		projectDAO.addProject(project);
		
		return new ModelAndView("redirect:/project_overview.htm");
		 		 
	 }
	
		

}
