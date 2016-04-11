package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.exception.NotChangeableRoleException;
import com.scrumiverse.exception.RoleNotInProjectException;
import com.scrumiverse.exception.ShutOutException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.RoleDAO;

/**
 * Controller for role manipulation
 * 
 * @author Kevin Jolitz
 * @version 04.04.2016
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
		} catch (ProjectPersistenceException | InvalidSessionException | InsufficientRightsException | UserPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	@RequestMapping("/updateRole.htm")
	public ModelAndView updateRole(HttpSession session, Role role) {
		int projectId = 0;
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Project);
			User user = this.loadActiveUser(session);
			Project activeProject = this.loadCurrentProject(session);
			projectId = activeProject.getProjectID();
			Role oldRole = activeProject.getProjectUserFromUser(user).getRole();
			if(oldRole.equals(role) && !role.hasRight(Right.Update_Project)) {
				throw new ShutOutException();
			}
			roleDAO.updateRole(role);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "#role-tab");
		} catch (ShutOutException e) {
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "&error=3" + "#role-tab");
		} catch (ProjectPersistenceException | InvalidSessionException | InsufficientRightsException | UserPersistenceException e) {
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
		} catch (ProjectPersistenceException | InvalidSessionException | InsufficientRightsException | UserPersistenceException | RoleNotInProjectException | NotChangeableRoleException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} 
	}
}