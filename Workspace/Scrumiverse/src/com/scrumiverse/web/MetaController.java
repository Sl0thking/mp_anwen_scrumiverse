package com.scrumiverse.web;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.InvalidContentTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvaldFileSizeException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.exception.SessionIsNotClearedException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.ProjectUser;
import com.scrumiverse.model.scrumFeatures.Message;
import com.scrumiverse.model.scrumFeatures.Notification;
import com.scrumiverse.persistence.DAO.NotificationDAO;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Master Controller for management and shared operations 
 * on session data
 * 
 * @author Kevin Jolitz, Toni Serfling
 * @version 17.04.2016
 *
 */
public abstract class MetaController {
	
	@Autowired
	protected UserDAO userDAO;
	
	@Autowired
	protected ProjectDAO projectDAO;
	
	@Autowired
	protected NotificationDAO notificationDAO;
	
	private static final long MAX_SIZE_MB = 4;
	
	/**
	 * Returns the currently logged in user in this session
	 * @param HttpSession
	 * @return User
	 * @throws UserPersistenceException
	 */
	protected User loadActiveUser(HttpSession session) throws UserPersistenceException {
		int userId = (int) session.getAttribute("userId");
		return userDAO.getUser(userId);
	}
	
	/**
	 * Returns the currently loaded project in this session, if it exists
	 * @param HttpSession
	 * @return Project
	 * @throws ProjectPersistenceException
	 */
	protected Project loadCurrentProject(HttpSession session) throws ProjectPersistenceException {
		if(session.getAttribute("currentProjectId") != null) {
			int projectId = (int) session.getAttribute("currentProjectId");
			return projectDAO.getProject(projectId);
		} else {
			return null;
		}
	}
	/**
	 * Checks if the current Session is still valid
	 * @param HttpSession
	 * @throws InvalidSessionException
	 */
	protected void checkInvalidSession(HttpSession session) throws InvalidSessionException {
		if(!isSessionValid(session)) {
			throw new InvalidSessionException();
		}
	}
	/**
	 * Checks if the current Session is valid
	 * @param HttpSession
	 * @throws SessionIsNotClearedException
	 */
	protected void checkValidSession(HttpSession session) throws SessionIsNotClearedException {
		if(isSessionValid(session)) {
			throw new SessionIsNotClearedException();
		}
	}
	/**
	 * Returns whether the current session is still valid
	 * @param session
	 * @return boolean
	 */
	protected boolean isSessionValid(HttpSession session) {
		return session.getAttribute("userId") != null 
			&& session.getAttribute("isLogged") != null;
	}
	
	/**
	 * Checks whether the current user has the given fitting right for the current project
	 * @param HttpSession
	 * @param Right
	 * @return boolean
	 * @throws InsufficientRightsException
	 * @throws ProjectPersistenceException
	 * @throws UserPersistenceException
	 */
	protected boolean testRight(HttpSession session, Right right) throws InsufficientRightsException, ProjectPersistenceException, UserPersistenceException {
		Project requestedProject = this.loadCurrentProject(session);
		User currentUser = this.loadActiveUser(session);
		if(!requestedProject.isUserMember(currentUser)) {
			throw new InsufficientRightsException();
		}
		ProjectUser pUser = requestedProject.getProjectUserFromUser(currentUser);
		if(!pUser.getRole().hasRight(right)) {
			throw new InsufficientRightsException();
		}
		return true;
	}
	
	/**
	 * Prepares a standard Model Map from the session, containing the current user, his projects and his messages
	 * @param HttpSession
	 * @return ModelMap
	 * @throws UserPersistenceException
	 * @throws ProjectPersistenceException
	 */
	protected ModelMap prepareModelMap(HttpSession session) throws UserPersistenceException, ProjectPersistenceException {
		// preparation of standard attributes
		ModelMap map = new ModelMap();
		User currentUser = loadActiveUser(session);
		Project currentProject = loadCurrentProject(session);
		boolean isLogged = currentUser != null;
		
		// preparation of message system attributes
		Message message = new Message();
		// add all potential message receivers
		Set<Project> projects = currentUser.getProjects();
		Set<User> recievers = new HashSet<User>();
		for (Project p: projects) {
			for (User u: p.getAllUsers()) {
				if (!u.equals(currentUser))
					recievers.add(u);
			}
		}
		// count unread messages and notifications
		int unreadMessages = 0, unreadNotifications = 0;
		for (Message m: currentUser.getMessages()) {
			if (!m.isSeen())
				unreadMessages++;
		}
		for (Notification n: currentUser.getNotifications()) {
			if (!n.isSeen())
				unreadNotifications++;
		}
		
		map.addAttribute("currentUser", currentUser);
		map.addAttribute("isLogged", isLogged);
		map.addAttribute("currentProject", currentProject);
		map.addAttribute("message", message);
		map.addAttribute("potentialRecievers", recievers);
		map.addAttribute("unreadMessages", unreadMessages);
		map.addAttribute("unreadNotifications", unreadNotifications);
		map.addAttribute("action", Action.login);
		return map;
	}
	/**
	 * Uploads a picture to the server
	 * @param HttpServletRequest
	 * @param MultipartFile (the picture)
	 * @param String (serverPath)
	 * @param int (elementID)
	 * @throws InvalidContentTypeException
	 * @throws IOException
	 * @throws InvaldFileSizeException
	 */
	protected void uploadPicture(HttpServletRequest request, MultipartFile file, String serverPath, int elementId) throws InvalidContentTypeException, IOException, InvaldFileSizeException {
			checkContentType(file.getContentType());
			checkFileSize(file.getSize());
			// Assemble imagefile from several elements
			String ending = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
			File contextPath = new File(request.getServletContext().getRealPath(""));
			File image = new File(contextPath + File.separator + serverPath + elementId + ending);
			System.out.println(image.getAbsolutePath());
			image.createNewFile();
			file.transferTo(image);
	}
	/**
	 * Checks if upload file size is greater than 4 Megabytes
	 * @param long
	 * @throws InvaldFileSizeException
	 */
	private void checkFileSize(long size) throws InvaldFileSizeException {
		long sizeInMb = size / 1000000L;
		System.out.println("SIZE IN MB: " + size);
		if(sizeInMb > MAX_SIZE_MB) {
			throw new InvaldFileSizeException();
		}
	}
	/**
	 * Checks if given content is a .png or .jpeg file
	 * @param String (contentType)
	 * @throws InvalidContentTypeException
	 */
	private void checkContentType(String contentType) throws InvalidContentTypeException {
		if(!(contentType.equals("image/png") || contentType.equals("image/jpeg"))) {
			throw new InvalidContentTypeException();
		}
	}
}