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

import com.scrumiverse.enums.Action;
import com.scrumiverse.enums.Right;
import com.scrumiverse.exception.AccessViolationException;
import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvaldFileSizeException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.exception.SessionIsNotClearedException;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.PlanElement;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.ProjectUser;
import com.scrumiverse.model.scrumFeatures.Category;
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
 * @version 23.04.2016
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
	 * 
	 * @param HttpSession Current HTTPSession
	 * @return current active user of this session
	 * @throws UserPersistenceException
	 */
	protected User loadActiveUser(HttpSession session) throws UserPersistenceException {
		int userId = (int) session.getAttribute("userId");
		return userDAO.getUser(userId);
	}
	
	/**
	 * Returns the currently loaded project in this session, if it exists
	 * 
	 * @param HttpSession current HTTPSession
	 * @return current active project of this session
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
	 * 
	 * @param HttpSession Current HTTPSession
	 * @throws InvalidSessionException
	 */
	protected void checkInvalidSession(HttpSession session) throws InvalidSessionException {
		if(!isSessionValid(session)) {
			throw new InvalidSessionException();
		}
	}
	/**
	 * Checks if the current Session is valid
	 * 
	 * @param HttpSession Current HTTPSession
	 * @throws SessionIsNotClearedException
	 */
	protected void checkValidSession(HttpSession session) throws SessionIsNotClearedException {
		if(isSessionValid(session)) {
			throw new SessionIsNotClearedException();
		}
	}
	/**
	 * Returns whether the current session is still valid
	 * 
	 * @param session Current HTTPSession
	 * @return is session valid
	 */
	protected boolean isSessionValid(HttpSession session) {
		return session.getAttribute("userId") != null 
			&& session.getAttribute("isLogged") != null;
	}
	
	/**
	 * Tests whether the current user has the given fitting right for the current project
	 * 
	 * @param HttpSession Current HTTP
	 * @param Right Right to check for current user
	 * @return if user has right
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
	 * Checks if a project is not null
	 * 
	 * @param project project to check
	 * @return project is not null
	 * @throws AccessViolationException
	 */
	private boolean testIfProjectNotNull(Project project) throws AccessViolationException {
		if(project == null) {
			throw new AccessViolationException();
		}
		return true;
	}
	
	/**
	 * Tests if scrum element is a part of current project. 
	 * Prevents manipulating foreign elements of another project.
	 * 
	 * @param session Current HTTPSession
	 * @param element element to check
	 * @return is element in current project
	 * @throws InsufficientRightsException
	 * @throws ProjectPersistenceException
	 * @throws UserPersistenceException
	 * @throws AccessViolationException 
	 */
	protected boolean testIsPartOfCurrentProject(HttpSession session, PlanElement element) throws ProjectPersistenceException, UserPersistenceException, AccessViolationException {
		Project project = this.loadCurrentProject(session);
		testIfProjectNotNull(project);
		try {
			boolean isPart = project.isPartOfProject(element);
			if(!isPart) {
				throw new AccessViolationException();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Tests if category is a part of current project. 
	 * Prevents manipulating foreign elements of another project.
	 * 
	 * @param session Current HTTPSession
	 * @param category category to check
	 * @return is element in current project
	 * @throws InsufficientRightsException
	 * @throws ProjectPersistenceException
	 * @throws UserPersistenceException
	 * @throws AccessViolationException 
	 */
	protected boolean testIsPartOfCurrentProject(HttpSession session, Category category) throws ProjectPersistenceException, UserPersistenceException, AccessViolationException {
		Project project = this.loadCurrentProject(session);
		testIfProjectNotNull(project);
		boolean isPart = project.isPartOfProject(category);
		if(!isPart) {
			throw new AccessViolationException();
		}
		return true;
	}
	
	/**
	 * Tests if role is a part of current project. 
	 * Prevents manipulating foreign elements of another project.
	 * 
	 * @param session Current HTTPSession
	 * @param role role to check
	 * @return is element in current project
	 * @throws InsufficientRightsException
	 * @throws ProjectPersistenceException
	 * @throws UserPersistenceException
	 * @throws AccessViolationException 
	 */
	protected boolean testIsPartOfCurrentProject(HttpSession session, Role role) throws ProjectPersistenceException, UserPersistenceException, AccessViolationException {
		Project project = this.loadCurrentProject(session);
		testIfProjectNotNull(project);
		boolean isPart = project.isPartOfProject(role);
		if(!isPart) {
			throw new AccessViolationException();
		}
		return true;
	}
	
	/**
	 * Tests if user is a part of current project. 
	 * Prevents manipulating foreign elements of another project.
	 * 
	 * @param session Current HTTPSession
	 * @param user user to check
	 * @return is element in current project
	 * @throws InsufficientRightsException
	 * @throws ProjectPersistenceException
	 * @throws UserPersistenceException
	 * @throws AccessViolationException 
	 */
	protected boolean testIsPartOfCurrentProject(HttpSession session, User user) throws ProjectPersistenceException, UserPersistenceException, AccessViolationException {
		Project project = this.loadCurrentProject(session);
		testIfProjectNotNull(project);
		try {	
			project.getProjectUserFromUser(user);
			return true;
		//user not a project member
		} catch (UserPersistenceException e) {
			throw new AccessViolationException();
		}
	}
	
	/**
	 * Prepares a standard Model Map from the session, containing the current user, his projects and his messages
	 * 
	 * @param HttpSession Current HTTPSession
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
	 * 
	 * @param HttpServletRequest Current HTTPRequest
	 * @param MultipartFile the picture to upload
	 * @param String the path to saved pictures
	 * @param int id of user or project
	 * @throws InvalidContentTypeException
	 * @throws IOException
	 * @throws InvaldFileSizeException
	 */
	protected void uploadPicture(HttpServletRequest request, MultipartFile file, String serverPath, int elementId) throws InvalidContentTypeException, IOException, InvaldFileSizeException {
			if(file != null) {
				checkContentType(file.getContentType());
				checkFileSize(file.getSize());
				// Assemble imagefile from several elements
				String ending = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
				File contextPath = new File(request.getServletContext().getRealPath(""));
				File image = new File(contextPath + File.separator + serverPath + elementId + ending);
				System.out.println(image.getAbsolutePath());
				image.createNewFile();
				file.transferTo(image);
			} else {
				throw new InvalidContentTypeException();
			}
	}
	/**
	 * Checks if upload file size is greater than 4 Megabytes
	 * 
	 * @param long current size in bytes
	 * @throws InvaldFileSizeException
	 */
	private void checkFileSize(long size) throws InvaldFileSizeException {
		long sizeInMb = size / 1000000L;
		System.out.println("SIZE IN MB: " + sizeInMb);
		if(sizeInMb > MAX_SIZE_MB) {
			throw new InvaldFileSizeException();
		}
	}
	/**
	 * Checks if given content is a .png or .jpeg file
	 * 
	 * @param String name of content type
	 * @throws InvalidContentTypeException
	 */
	private void checkContentType(String contentType) throws InvalidContentTypeException {
		if(!(contentType.equals("image/png") || contentType.equals("image/jpeg"))) {
			throw new InvalidContentTypeException();
		}
	}
}