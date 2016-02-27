package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.SessionIsNotClearedException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Master Controller for management and shared operations 
 * on session data
 * 
 * @author Kevin Jolitz
 * @version 27.02.2016
 *
 */
public abstract class MetaController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ProjectDAO projectDAO;
	
	protected User loadActiveUser(HttpSession session) throws NoSuchUserException {
		int userId = (int) session.getAttribute("userId");
		return userDAO.getUser(userId);
	}
	
	protected Project loadCurrentProject(HttpSession session) throws NoProjectFoundException {
		if(session.getAttribute("currentProjectId") != null) {
			int projectId = (int) session.getAttribute("currentProjectId");
			return projectDAO.getProject(projectId);
		} else {
			return null;
		}
	}
	
	protected void checkInvalidSession(HttpSession session) throws InvalidSessionException {
		if(!isSessionValid(session)) {
			throw new InvalidSessionException();
		}
	}
	
	protected void checkValidSession(HttpSession session) throws SessionIsNotClearedException {
		if(isSessionValid(session)) {
			throw new SessionIsNotClearedException();
		}
	}
	
	protected boolean isSessionValid(HttpSession session) {
		return session.getAttribute("userId") != null 
			&& session.getAttribute("isLogged") != null;
	}
	
	protected ModelMap prepareModelMap(HttpSession session) throws NoSuchUserException, NoProjectFoundException {
		ModelMap map = new ModelMap();
		User currentUser = loadActiveUser(session);
		Project currentProject = loadCurrentProject(session);
		boolean isLogged = currentUser != null;
		map.addAttribute("currentUser", currentUser);
		map.addAttribute("isLogged", isLogged);
		map.addAttribute("currentProject", currentProject);
		//std action
		map.addAttribute("action", Action.login);
		return map;
	}
}