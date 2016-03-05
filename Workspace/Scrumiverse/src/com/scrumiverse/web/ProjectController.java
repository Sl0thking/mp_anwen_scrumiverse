package com.scrumiverse.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.RoleNotInProjectException;
import com.scrumiverse.exception.TriedToRemoveAdminException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.ProjectUser;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.RoleDAO;
import com.scrumiverse.persistence.DAO.SprintDAO;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Controller for project interactions
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 05.03.2016
 *
 */

@Controller
public class ProjectController extends MetaController {
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SprintDAO sprintDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Role.class, new RoleBinder(roleDAO));
		binder.registerCustomEditor(User.class, new UserBinder(userDAO));
	}

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
			project.addProjectUser(user,(Role) project.getRole(StdRoleNames.ProductOwner.name()));
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
			session.setAttribute("currentProjectId", requestedProject.getProjectID());
			map.addAttribute("project", requestedProject);
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
	public ModelAndView addUser(HttpSession session, @RequestParam String email) {
		int projectId = 0;
		try {
			checkInvalidSession(session);
			Project currentProject = this.loadCurrentProject(session);
			projectId = currentProject.getProjectID();
			User user = userDAO.getUserByEmail(email);
			currentProject.addProjectUser(user);
			projectDAO.updateProject(currentProject);
			user.addProject(currentProject);
			userDAO.updateUser(user);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch (NoSuchUserException e) {
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "&error=1");
		} catch (NoProjectFoundException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Remove a user from a project and redirect to settings
	 * @param project
	 * @param user
	 * @return ModelAndView
	 */
	
	@RequestMapping("/removeProjectUser.htm")
	public ModelAndView removeProjectUser(HttpSession session, @RequestParam int id) {
		int projectId = 0;
		try {
			checkInvalidSession(session);
			Project project = this.loadCurrentProject(session);
			projectId = project.getProjectID();
			User user = userDAO.getUser(id);
			removeUserFromProject(user, project, false);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch(NoSuchUserException | TriedToRemoveAdminException e) {
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch (NoProjectFoundException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
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
			removeUserFromProject(user, p, true);
			//Remove remaining users
			for(User projectUser : p.getAllUsers()) {
				removeUserFromProject(projectUser, p, true);
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
	 * @throws TriedToRemoveAdminException 
	 */
	private void removeUserFromProject(User user, Project project, boolean forced) throws NoSuchUserException, TriedToRemoveAdminException {
		user.leaveProject(project);
		project.removeProjectUser(user, forced);
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
	public ModelAndView renameProject(Project project, HttpSession session) {
		try {
			checkInvalidSession(session);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:projectSettings.htm?id=" + project.getProjectID());
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	/**
	 * Opens the reporting page
	 * @param session
	 * @return ModelAndView
	 */	
	@RequestMapping("/reporting.htm")
	public ModelAndView reporting(HttpSession session) {
		try {
			checkInvalidSession(session);
			ModelMap map = this.prepareModelMap(session);
			int projectId = (int) session.getAttribute("currentProjectId");
			Set<Sprint> sprints = sprintDAO.getSprintsFromProject(projectId);
			// adding jsonobjects to map
			map.addAttribute("jsonobject", dummyJSON());
			map.addAttribute("sprints", sprints);
			map.addAttribute("action", Action.reporting);
			return new ModelAndView("index", map);		
		} catch(NoSuchUserException | InvalidSessionException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:login.htm");
		} catch(NoProjectFoundException | JSONException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	/**
	 * Changes the user of a Project
	 * @param session
	 * @param pUser
	 * @return ModelAndView
	 */
	@RequestMapping("/changeProjectUser.htm")
	public ModelAndView updateProjectUser(HttpSession session, ProjectUser pUser) {
		int projectId = 0;
		try {
			checkInvalidSession(session);
			projectId = this.loadCurrentProject(session).getProjectID();
			User user = pUser.getUser();
			Role role = pUser.getRole();
			Project currentProject = this.loadCurrentProject(session);
			currentProject.setProjectUserRole(user, role);
			projectDAO.updateProject(currentProject);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch (NoProjectFoundException | RoleNotInProjectException | TriedToRemoveAdminException | NoSuchUserException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/*
	 * JSON Dummy, going to be removed once backend data is available
	 */
	public JSONObject dummyJSON() throws JSONException {
		//JSON Object mit allen 4 Serien
		JSONObject dummyObject = new JSONObject();
		//Benötigt 1d-Array, welches die Anzahl aller Userstories pro Tag enthält
		JSONArray backlogScope = new JSONArray();
		//Benötigt 1d-Array, welches die Ideale Anzahl von fertigen UserStories pro tag zurückgibt 
		//(Anzahl Tage im Sprint/max. Anzahl Userstories)
		JSONArray idealRemaining = new JSONArray();
		//Benötigt 1d-Array, welches die unfertigen UserStories pro tag zurückgibt
		JSONArray remainingItems = new JSONArray();
		//Benötigt 1d-Array, welches die fertigen Userstories pro tag zurückgibt
		JSONArray doneItems = new JSONArray();
				
		/*
		 * for(int i=0; i<=Sprint.getBenötigtesArray.length;i++) {
		 * 	somejsonarray.put(benötigtesArray[i]);
		 * };
		 */
		backlogScope.put(30);
		backlogScope.put(30);
		backlogScope.put(31);
		backlogScope.put(36);
		backlogScope.put(36);
		backlogScope.put(36);
		backlogScope.put(30);
		idealRemaining.put(36);
		idealRemaining.put(30);
		idealRemaining.put(24);
		idealRemaining.put(18);
		idealRemaining.put(12);
		idealRemaining.put(6);
		idealRemaining.put(0);
		remainingItems.put(30);
		remainingItems.put(30);
		remainingItems.put(31);
		remainingItems.put(34);
		remainingItems.put(25);
		remainingItems.put(20);
		remainingItems.put(5);
		doneItems.put(0);
		doneItems.put(0);
		doneItems.put(0);
		doneItems.put(2);
		doneItems.put(11);
		doneItems.put(16);
		doneItems.put(25);
		dummyObject.put("backlogScope", backlogScope);
		dummyObject.put("idealRemaining", idealRemaining);
		dummyObject.put("remainingItems", remainingItems);
		dummyObject.put("doneItems", doneItems);
		return dummyObject;
	}
}