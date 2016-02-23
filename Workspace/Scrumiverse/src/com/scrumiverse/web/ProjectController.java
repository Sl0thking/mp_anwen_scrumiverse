package com.scrumiverse.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.ProjectUser;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.UserDAO;
import com.scrumiverse.utility.Utility;

/**
 * Controller for project interactions
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 23.02.2016
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
	public ModelAndView projectOverview(HttpSession session) {
		if(Utility.isSessionValid(session)) {
			ModelMap map = Utility.generateModelMap(session);
			User user = ((User) session.getAttribute("loggedUser"));
			Set<Project> projectList = projectDAO.getProjectsFromUser(user.getUserID());
			Map<Project, Boolean> manageRights = new HashMap<Project, Boolean>();
			for(Project p : projectList) {
				manageRights.put(p, p.hasUserRight(Right.Manage_Project, user));
			}
			map.addAttribute("projectList", projectDAO.getProjectsFromUser(user.getUserID()));
			map.addAttribute("manageRight", manageRights);
			map.addAttribute("action", Action.projectOverview);
			return new ModelAndView("index", map);
		} else {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Add a project and redirect to Overview
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/addProject.htm")
	public ModelAndView addProject(HttpSession session) {
		try {
			if(!Utility.isSessionValid(session)) {
				throw new InvalidSessionException();
			} 
			User user = (User) session.getAttribute("loggedUser");
			Project project = new Project();
			projectDAO.saveProject(project);
			project.addProjectUser(user,(Role) project.getRoles().toArray()[0]);
			user.addProject(project);
			userDAO.saveUser(user);
			projectDAO.saveProject(project);
			return new ModelAndView("redirect:projectOverview.htm");
		} catch(InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
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
			if(!Utility.isSessionValid(session)) {
				throw new InvalidSessionException();
			} 
			Project project = projectDAO.getProject(id);
			session.setAttribute("currentProject", project);
			return new ModelAndView("redirect:backlog.htm");
		} catch(NoProjectFoundException e) {
			//zukünftig vll mit Fehler über param
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Opens settings of selected project
	 * @param project
	 * @param session
	 * @return ModelAndView
	 */
	
	@RequestMapping("/projectSettings.htm")
	public ModelAndView projectSettings(@RequestParam int id, HttpSession session)  {
		try {
			if(Utility.isSessionValid(session)) {
				throw new InvalidSessionException();
			}
			Project requestedProject = projectDAO.getProject(id);
			User user = (User) session.getAttribute("loggedUser");
			ProjectUser pUser= requestedProject.getProjectUserFromUser(user);
			if(!pUser.getRole().hasRights(Right.Manage_Project)) {
				throw new InsufficientRightsException();
			}
			ModelMap map = Utility.generateModelMap(session);
			map.addAttribute("project", requestedProject);
			map.addAttribute("action", Action.projectSettings);
			return new ModelAndView("index", map);
		} catch(InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (Exception e1) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	/**
	 * Add a user to a project and redirect to settings
	 * @param project
	 * @param user
	 * @return ModelAndView
	 */
	
	@RequestMapping("/addUserToProject.htm")
	public ModelAndView addUser(Project project, User user) {
		project.addProjectUser(user, new Role());
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
		p.removeProjectUser(user);
		System.out.println("Is Empty " + p.getProjectUsers().isEmpty());
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