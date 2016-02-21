package com.scrumiverse.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.binder.ProjectBinder;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.utility.Utility;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectDAO projectDAO;
	
//	@InitBinder
//	protected void initBinder(ServletRequestDataBinder binder) {
//			binder.registerCustomEditor(Project.class, new ProjectBinder(projectDAO));
//		}

	
	@RequestMapping("/projectOverview.htm")
	public ModelAndView project_overview(HttpSession session) {
		ModelMap map = Utility.generateModelMap(session);
		User user = ((User) session.getAttribute("loggedUser"));
		map.addAttribute("projectList", projectDAO.getAllProjects(user.getUserID()));
		map.addAttribute("action", Action.projectOverview);
		return new ModelAndView("index", map);
	}
	
	@RequestMapping("/addProject.htm")
	public ModelAndView addProject(HttpSession session) {		
		Project project = new Project();
		project.addUser((User) session.getAttribute("loggedUser"));
		projectDAO.addProject(project);

		return new ModelAndView("redirect:/projectOverview.htm");		 		 
	 }
	
	@RequestMapping("/projectSettings.htm")
	public ModelAndView projectSettings(Project project, HttpSession session)  {
		
		ModelMap map = Utility.generateModelMap(session);
		int projectID = project.getProjectID();
		Project selectedProject = projectDAO.getProject(projectID);
		map.addAttribute("project", selectedProject);
		map.addAttribute("action", Action.projectSettings);
		return new ModelAndView("index", map);
		
	}
	
	@RequestMapping("/addUserToProject.htm")
	public ModelAndView addUser(Project project, User user) {
		projectDAO.addUser(project, user);
		
		return new ModelAndView("redirect/projectSettings.htm");
		
	}
	
	@RequestMapping("/removeUserFromProject.htm")
	public ModelAndView removeUser(Project project, User user) {
		projectDAO.removeUser(project, user.getUserID());
		
		return new ModelAndView("redirect/projectSettings.htm");
	}
	
	@RequestMapping("/removeProject.htm")
	public ModelAndView removeProject(Project project, HttpSession session) {
		projectDAO.removeProject(project);
		
		return new ModelAndView("redirect/projectSettings.htm");
	}
	
	
}