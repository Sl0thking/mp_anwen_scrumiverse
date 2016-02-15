package com.scrumiverse.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.impl.NoProjectsFoundException;

@Controller
public class ProjectController {
	
//	@Autowired
//	private ProjectDAO projectDAO;
//	
//	@RequestMapping("/project_overview.htm")
//	public ModelAndView project_overview() throws NoProjectsFoundException {
//		ModelMap map = new ModelMap();
//		projectDAO.getAllProjects();
//		map.addAttribute("project", new Project());
//		return new ModelAndView("project_overview", map);
//	}
//	
//	@RequestMapping("/add_project.htm")
//	public ModelAndView addProject(Project project) {
//		ModelMap map = new ModelMap();
//		
//		String projectName = project.getName();
//		project.setName(projectName);
//		String projectDescription = project.getDescription();
//		project.setDescription(projectDescription);
//		
//		projectDAO.addProject(project);
//		
//		map.addAttribute("project", new Project());
//		
//		return new ModelAndView("project_overview", map);
//		 
//		 
//		 
//	 }
//	
//		

}
