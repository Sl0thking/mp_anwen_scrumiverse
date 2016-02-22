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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.binder.ProjectBinder;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.RoleDAO;
import com.scrumiverse.persistence.DAO.UserDAO;
import com.scrumiverse.utility.Utility;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private UserDAO userDAO;
	
//	@InitBinder
//	protected void initBinder(ServletRequestDataBinder binder) {
//			binder.registerCustomEditor(Project.class, new ProjectBinder(projectDAO));
//		}

	
	@RequestMapping("/projectOverview.htm")
	public ModelAndView project_overview(HttpSession session) {
		ModelMap map = Utility.generateModelMap(session);
		User user = ((User) session.getAttribute("loggedUser"));
		map.addAttribute("projectList", projectDAO.getProjectsFromUser(user.getUserID()));
		map.addAttribute("action", Action.projectOverview);
		return new ModelAndView("index", map);
	}
	
	@RequestMapping("/addProject.htm")
	public ModelAndView addProject(HttpSession session) {		
		Project project = new Project();
		projectDAO.addProject(project);
		Role role = new Role();
		User user = (User) session.getAttribute("loggedUser");
		
		//awesome function for creating admin/product owner role
		role.setName("ProductOwner");
		role.addRight(Right.Invite_To_Project);
		roleDAO.addRole(role);
		project.addUser(user, role);
		user.addProject(project);
		userDAO.addUser(user);
		projectDAO.addProject(project);
		projectDAO.removeProject(project);
		return new ModelAndView("redirect:projectOverview.htm");		 		 
	 }
	
	@RequestMapping("/selectProject.htm")
	public ModelAndView selectProject(@RequestParam int id, HttpSession session) {		
		session.setAttribute("currentProject", projectDAO.getProject(id));		
		return new ModelAndView("redirect:/backlog.htm");
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
	public ModelAndView removeProject(Project project) {
		projectDAO.removeProject(project);
		
		return new ModelAndView("redirect/projectSettings.htm");
	}
	
	@RequestMapping("/renameProject.htm")
	public ModelAndView renameProject(Project project, String name) {
		projectDAO.renameProject(project, name);
		
		return new ModelAndView("redirect/projectSettings.htm");
	}
	
}