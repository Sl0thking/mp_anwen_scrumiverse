package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.NotChangeableRoleException;
import com.scrumiverse.exception.RoleNotInProjectException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.RoleDAO;

/**
 * Controller for role manipulation
 * 
 * @author Kevin Jolitz
 * @version 29.03.2016
 */
@Controller
public class RoleController extends MetaController {
	
	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@RequestMapping("/addRole.htm")
	public ModelAndView addRole(HttpSession session) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Project);
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			Role role = new Role();
			roleDAO.saveRole(role);
			activeProject.addRole(role);
			projectDAO.updateProject(activeProject);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "#role-tab");
		} catch (NoProjectFoundException | InvalidSessionException | InsufficientRightsException | NoSuchUserException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	@RequestMapping("/updateRole.htm")
	public ModelAndView updateRole(HttpSession session, Role role) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Project);
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			roleDAO.updateRole(role);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "#role-tab");
		} catch (NoProjectFoundException | InvalidSessionException | InsufficientRightsException | NoSuchUserException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} 
	}
	
	@RequestMapping("/removeRole.htm")
	public ModelAndView removeRole(HttpSession session, @RequestParam int id) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Project);
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			Role role = roleDAO.getRole(id);
			int count = activeProject.getUsersWithRoleCount(role);
			if(count == 0) {
				activeProject.deleteRole(role);
				projectDAO.updateProject(activeProject);
				return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "#role-tab");
			} else {
				//show with error
				return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "#role-tab");
			}
		} catch (NoProjectFoundException | InvalidSessionException | InsufficientRightsException | NoSuchUserException | RoleNotInProjectException | NotChangeableRoleException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} 
	}
}