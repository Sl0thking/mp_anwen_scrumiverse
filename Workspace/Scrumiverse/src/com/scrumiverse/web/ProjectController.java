package com.scrumiverse.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.Role;
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
	
	@RequestMapping("/addProject.htm")
	public ModelAndView addProject(Project project, HttpSession session) {		
		project.addUser((User) session.getAttribute("loggedUser"));
		projectDAO.addProject(project);

		return new ModelAndView("redirect:/projectOverview.htm");		 		 
	 }
	
	@RequestMapping("/projectSettings.htm")
	public ModelAndView projectSettings(Project project, HttpSession session)  {
		
		ModelMap map = Utility.generateModelMap(session);
		int projectID = project.getProjectID();
		Project selectedProject = projectDAO.getProject(projectID);
		Map<User, Role> userMap = projectDAO.getAllUsers(projectID);
		map.addAttribute("project", selectedProject);
		map.addAttribute("userList", userMap);
		map.addAttribute("action", Action.projectSettings);
		return new ModelAndView("index", map);
		
	}
	
	@RequestMapping("/addUserToProject.htm")
	public ModelAndView addUser(Project project, User user, HttpSession session) {
		projectDAO.addUser(project, user);
		
		return new ModelAndView("redirect/projectSettings.htm");
		
	}
	
	
}