package com.scrumiverse.web;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.SessionIsNotClearedException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumFeatures.Message;
import com.scrumiverse.persistence.DAO.MessageDAO;
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
	
	@Autowired
	private MessageDAO messageDAO;
	
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
		// Preparation of Message System
		Message message = new Message();
		message.setSender(currentUser);
		Set<Project> projects = currentUser.getProjects();
		Set<User> recievers = new HashSet<User>();
		for(Project p: projects) {
			recievers.addAll((p.getAllUsers()));
		}
		int uID = currentUser.getUserID();
		Set<Message> userMessages = messageDAO.getMessagesFromUser(uID);
		
		map.addAttribute("message", message);
		map.addAttribute("potentialRecievers", recievers);
		map.addAttribute("messageList", userMessages);
		map.addAttribute("currentUser", currentUser);
		map.addAttribute("isLogged", isLogged);
		map.addAttribute("currentProject", currentProject);
		map.addAttribute("action", Action.login);
		return map;
	}
}