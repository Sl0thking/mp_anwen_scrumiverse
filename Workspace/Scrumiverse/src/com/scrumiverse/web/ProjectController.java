package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.UserDAO;
import com.scrumiverse.persistence.DAO.impl.NoProjectFoundException;
import com.scrumiverse.utility.Utility;

/**
 * Controller for project interactions
 * 
 * @author Toni Serfling
 * @version 22.02.2016
 *
 */

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private UserDAO userDAO;
	
//	@InitBinder
//	protected void initBinder(ServletRequestDataBinder binder) {
//			binder.registerCustomEditor(Project.class, new ProjectBinder(projectDAO));
//		}

	/**
	 * Overview over all projects of current user
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/projectOverview.htm")
	public ModelAndView project_overview(HttpSession session) {
		ModelMap map = Utility.generateModelMap(session);
		User user = ((User) session.getAttribute("loggedUser"));
		map.addAttribute("projectList", projectDAO.getProjectsFromUser(user.getUserID()));
		map.addAttribute("action", Action.projectOverview);
		return new ModelAndView("index", map);
	}
	
	/**
	 * Add a project and redirect to Overview
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/addProject.htm")
	public ModelAndView addProject(HttpSession session) {		
		Project project = new Project();
		User user = (User) session.getAttribute("loggedUser");
		projectDAO.saveProject(project);
		//awesome function for creating admin/product owner role
		Role role = new Role();
		role.setName("ProductOwner");
		role.addRight(Right.Invite_To_Project);
		//-----------------------------------------------------
		project.addUser(user, role);
		user.addProject(project);
		userDAO.saveUser(user);
		projectDAO.saveProject(project);
		return new ModelAndView("redirect:projectOverview.htm");		 		 
	 }
	
	/**
	 * select specific project and redirect to backlog
	 * @param id
	 * @param session
	 * @return ModelAndView
	 */
	
	@RequestMapping("/selectProject.htm")
	public ModelAndView selectProject(@RequestParam int id, HttpSession session) {	
		try {
			Project project = projectDAO.getProject(id);
			session.setAttribute("currentProject", project);	
		} catch(NoProjectFoundException e) {
			//zukünftig vll mit Fehler über param
			return new ModelAndView("redirect:projectOverview.htm");
		}
		return new ModelAndView("redirect:backlog.htm");
	}
	
	/**
	 * Opens settings of selected project
	 * @param project
	 * @param session
	 * @return ModelAndView
	 */
	
	@RequestMapping("/projectSettings.htm")
	public ModelAndView projectSettings(Project project, HttpSession session)  {
		ModelMap map = Utility.generateModelMap(session);
		int projectID = project.getProjectID();
		Project selectedProject;
		try {
			selectedProject = projectDAO.getProject(projectID);
			map.addAttribute("project", selectedProject);
			map.addAttribute("action", Action.projectSettings);
		} catch (NoProjectFoundException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
		return new ModelAndView("index", map);
	}
	
	/**
	 * Add a user to a project and redirect to settings
	 * @param project
	 * @param user
	 * @return ModelAndView
	 */
	
	@RequestMapping("/addUserToProject.htm")
	public ModelAndView addUser(Project project, User user) {
		project.addUser(user, new Role());
		user.addProject(project);
		projectDAO.updateProject(project);
		userDAO.saveUser(user);
		
		return new ModelAndView("redirect:projectSettings.htm");
		
	}
	
	/**
	 * Remove a user from a project and redirect to settings
	 * @param project
	 * @param user
	 * @return ModelAndView
	 */
	
	@RequestMapping("/removeUserFromProject.htm")
	public ModelAndView removeUser(Project project, User user) {
		//todo
		return new ModelAndView("redirect:projectSettings.htm");
	}
	
	/**
	 * Removes a project and return to Overview
	 * @param project
	 * @return ModelAndView
	 * @throws Exception 
	 */
	
	@RequestMapping("/removeProject.htm")
	public ModelAndView removeProject(HttpSession session, @RequestParam int id) throws Exception {
		User user =(User) session.getAttribute("loggedUser");
		Project p = projectDAO.getProject(id);
		user.leaveProject(p);
		p.removeUser(user);
		System.out.println("Is Empty " + p.getUsers().isEmpty());
		projectDAO.updateProject(p);
		userDAO.saveUser(user);
		projectDAO.deleteProject(p);
		return new ModelAndView("redirect:projectOverview.htm");
	}
	
	/**
	 * Rename a project and return to Settings
	 * @param project
	 * @param name
	 * @return ModelAndView
	 */
	@RequestMapping("/renameProject.htm")
	public ModelAndView renameProject(Project project, String name) {
		//projectDAO.renameProject(project, name);
		
		return new ModelAndView("redirect:projectSettings.htm");
	}
	
}