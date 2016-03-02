package com.scrumiverse.web;

import java.util.HashMap;
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

/**
 * Controller for project interactions
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 27.02.2016
 *
 */

@Controller
public class ProjectController extends MetaController {
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private UserDAO userDAO;

	/**
	 * Overview over all projects of current user
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/projectOverview.htm")
	public ModelAndView projectOverview(HttpSession session) {
		try {
			checkInvalidSession(session);
			session.removeAttribute("currentProjectId");
			ModelMap map = this.prepareModelMap(session);
			User user = loadActiveUser(session);
			Set<Project> projectList = user.getProjects();
			Map<Project, Boolean> manageRights = new HashMap<Project, Boolean>();
			for(Project p : projectList) {
				manageRights.put(p, p.hasUserRight(Right.Manage_Project, user));
			}
			map.addAttribute("projectList", projectList);
			map.addAttribute("manageRight", manageRights);
			map.addAttribute("action", Action.projectOverview);
			return new ModelAndView("index", map);
		} catch(InvalidSessionException | NoSuchUserException | NoProjectFoundException e) {
			e.printStackTrace();
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
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			Project project = new Project();
			projectDAO.saveProject(project);
			project.addProjectUser(user,(Role) project.getRoles().toArray()[0]);
			user.addProject(project);
			userDAO.updateUser(user);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:projectOverview.htm");
		} catch(InvalidSessionException | NoSuchUserException e) {
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
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			Project project = projectDAO.getProject(id);
			//check if user is member of target project to prevent
			//unauthorized access 
			if(!project.isUserMember(user)) {
				throw new InsufficientRightsException();
			}
			session.setAttribute("currentProjectId", project.getProjectID());
			return new ModelAndView("redirect:backlog.htm");
		} catch(NoProjectFoundException | InsufficientRightsException | NoSuchUserException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch(InvalidSessionException e) {
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
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);	
			Project requestedProject = projectDAO.getProject(id);
			ModelMap map = this.prepareModelMap(session);
			
			if(!requestedProject.isUserMember(user)) {
				throw new InsufficientRightsException();
			}
			ProjectUser pUser= requestedProject.getProjectUserFromUser(user);
			if(!pUser.getRole().hasRights(Right.Manage_Project)) {
				throw new InsufficientRightsException();
			}
			map.addAttribute("currentProjectId", requestedProject.getProjectID());
			map.addAttribute("action", Action.projectSettings);
			return new ModelAndView("index", map);
		} catch (InvalidSessionException e) {
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
	
	@RequestMapping("/removeProjectUser.htm")
	public ModelAndView removeProjectUser(HttpSession session, @RequestParam int id) {
		try {
			Project project = this.loadCurrentProject(session);
			User user = this.loadActiveUser(session);
			if(user.getUserID() != id) {
				user = userDAO.getUser(id);
			}
			removeUserFromProject(user, project);
		} catch(NoSuchUserException e) {
			return new ModelAndView("redirect:projectSettings.htm");
		} catch (NoProjectFoundException e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:projectSettings.htm");
	}
	
	/**
	 * Removes a project and return to Overview
	 * @param project
	 * @return ModelAndView
	 * @throws  
	 * @throws Exception 
	 */
	@RequestMapping("/removeProject.htm")
	public ModelAndView removeProject(HttpSession session) {
		try {
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			Project p = this.loadCurrentProject(session);
			if(!p.isUserMember(user) || !p.hasUserRight(Right.Manage_Project, user)) {
				throw new InsufficientRightsException();
			}
			//Remove project from User in session
			removeUserFromProject(user, p);
			//Remove remaining users
			for(User projectUser : p.getAllUsers()) {
				removeUserFromProject(projectUser, p);
			}
			projectDAO.deleteProject(p);
			session.removeAttribute("currentProjectId");
			return new ModelAndView("redirect:projectOverview.htm");
		} catch(InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		} catch(Exception e) {
			//return error code
			e.printStackTrace();
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	/**
	 * Remove user from project and project from internal list in user
	 * @param user
	 * @param project
	 * @throws NoSuchUserException
	 */
	private void removeUserFromProject(User user, Project project) throws NoSuchUserException {
		user.leaveProject(project);
		project.removeProjectUser(user);
		projectDAO.updateProject(project);
		userDAO.updateUser(user);
	}
	
	/**
	 * Rename a project and return to Settings
	 * @param project
	 * @param name
	 * @return ModelAndView
	 */
	@RequestMapping("/saveProject.htm")
	public ModelAndView renameProject(Project project) {
		projectDAO.updateProject(project);
		return new ModelAndView("redirect:projectSettings.htm");
	}
	
}