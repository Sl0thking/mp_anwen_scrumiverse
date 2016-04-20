package com.scrumiverse.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.InvalidContentTypeException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.binder.CategoryBinder;
import com.scrumiverse.binder.DateBinder;
import com.scrumiverse.binder.RoleBinder;
import com.scrumiverse.binder.UserBinder;
import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvaldFileSizeException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.exception.RoleNotInProjectException;
import com.scrumiverse.exception.SprintPersistenceException;
import com.scrumiverse.exception.TriedToRemoveAdminException;
import com.scrumiverse.forms.CategoryForm;
import com.scrumiverse.forms.RoleForm;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.PlanState;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.ProjectUser;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.persistence.DAO.CategoryDAO;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.RoleDAO;
import com.scrumiverse.persistence.DAO.SprintDAO;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Controller for project interactions
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 17.04.2016
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
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Role.class, new RoleBinder(roleDAO));
		binder.registerCustomEditor(User.class, new UserBinder(userDAO));
		binder.registerCustomEditor(Date.class, new DateBinder());
		binder.registerCustomEditor(Category.class, new CategoryBinder(categoryDAO));
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
			//create a map, to associate every project of user with the right to manage
			//view can check this map to decide, if link to projectSettings is shown
			for(Project p : projectList) {
				manageRights.put(p, p.hasUserRight(Right.Update_Project, user));
			}
			map.addAttribute("projectList", projectList);
			map.addAttribute("manageRight", manageRights);
			map.addAttribute("action", Action.projectOverview);
			return new ModelAndView("index", map);
		} catch(InvalidSessionException | UserPersistenceException | ProjectPersistenceException e) {
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
			prepareStdRoles(project);
			project.addProjectUser(user,(Role) project.getRole(StdRoleNames.ProductOwner.name()));
			user.addProject(project);
			userDAO.updateUser(user);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:projectOverview.htm");
		} catch(InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}
	 }
	
	private void prepareStdRoles(Project project) {
		Role productOwner = new Role(StdRoleNames.ProductOwner.name());
		productOwner.setChangeable(false);
		productOwner.addRight(Right.Invite_To_Project);
		productOwner.addRight(Right.Update_Project);
		productOwner.addRight(Right.Remove_From_Project);
		productOwner.addRight(Right.Create_Sprint);
		productOwner.addRight(Right.Create_UserStory);
		productOwner.addRight(Right.Delete_Project);
		productOwner.addRight(Right.Delete_Sprint);
		productOwner.addRight(Right.Delete_UserStory);
		productOwner.addRight(Right.Update_Sprint);
		productOwner.addRight(Right.Update_UserStory);
		productOwner.addRight(Right.Read_Sprint);
		productOwner.addRight(Right.Read_Task);
		productOwner.addRight(Right.Read_UserStory);
		productOwner.addRight(Right.View_Review);
		
		Role member = new Role(StdRoleNames.Member.name());
		member.setChangeable(false);
		member.addRight(Right.Read_Sprint);
		member.addRight(Right.Read_Task);
		member.addRight(Right.Read_UserStory);
		member.addRight(Right.View_Review);
		member.addRight(Right.Create_Task);
		member.addRight(Right.Update_Task);
		member.addRight(Right.Delete_Task);
		member.addRight(Right.Notify_Your_UserStory_Task);
		
		Role scrumMaster = new Role(StdRoleNames.ScrumMaster.name());
		scrumMaster.setChangeable(false);
		scrumMaster.addRight(Right.Read_Sprint);
		scrumMaster.addRight(Right.Read_Task);
		scrumMaster.addRight(Right.Read_UserStory);
		scrumMaster.addRight(Right.View_Review);
		scrumMaster.addRight(Right.Notify_UserStory_Task_for_Current_Sprint);
		scrumMaster.addRight(Right.Notify_PlannedMin_for_Current_Sprint);
		
		project.addRole(productOwner);
		project.addRole(member);
		project.addRole(scrumMaster);
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
			return new ModelAndView("redirect:dashboard.htm");
		} catch(ProjectPersistenceException | InsufficientRightsException | UserPersistenceException e) {
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
	public ModelAndView projectSettings(@RequestParam int id, RoleForm receivedRoleForm, CategoryForm receivedCategoryForm, HttpSession session)  {
		try {
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);	
			Project requestedProject = projectDAO.getProject(id);
			session.setAttribute("currentProjectId", requestedProject.getProjectID());
			ModelMap map = this.prepareModelMap(session);
			testRight(session, Right.Update_Project);
			if(receivedRoleForm.getRole() == null) {
				receivedRoleForm.setRole(requestedProject.getRoles().first());
			}
			if(requestedProject.getCategories().size() == 0) {
				receivedCategoryForm.setCategory(new Category());
			} else if(receivedCategoryForm.getCategory() == null){
				receivedCategoryForm.setCategory(requestedProject.getCategories().first());
			}
			map.addAttribute("project", requestedProject);
			map.addAttribute("selectedRole", receivedRoleForm.getRole());
			map.addAttribute("roleForm", receivedRoleForm);
			map.addAttribute("selectedCategory", receivedCategoryForm.getCategory());
			map.addAttribute("categoryForm", receivedCategoryForm);
			map.addAttribute("removeFromProject", requestedProject.getProjectUserFromUser(user).getRole().hasRight(Right.Remove_From_Project));
			map.addAttribute("inviteToProject", requestedProject.getProjectUserFromUser(user).getRole().hasRight(Right.Invite_To_Project));
			map.addAttribute("deleteProject", requestedProject.getProjectUserFromUser(user).getRole().hasRight(Right.Delete_Project));
			map.addAttribute("action", Action.projectSettings);
			return new ModelAndView("index", map);
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (Exception e1) {
			session.removeAttribute("currentProjectId");
			e1.printStackTrace();
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
		boolean hasUpdateRight = false;
		try {
			checkInvalidSession(session);
			Project currentProject = this.loadCurrentProject(session);
			hasUpdateRight = testRight(session, Right.Update_Project);
			projectId = currentProject.getProjectID();
			testRight(session, Right.Invite_To_Project);
			User user = userDAO.getUserByEmail(email);
			currentProject.addProjectUser(user);
			projectDAO.updateProject(currentProject);
			user.addProject(currentProject);
			userDAO.updateUser(user);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch (UserPersistenceException e) {
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "&error=4");
		} catch (ProjectPersistenceException | InsufficientRightsException e) {
			if(hasUpdateRight) {
				return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
			}
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
			testRight(session, Right.Remove_From_Project);
			User user = userDAO.getUser(id);
			removeUserFromProject(user, project, false);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch(TriedToRemoveAdminException e) {
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "&error=2");
		} catch(UserPersistenceException | ProjectPersistenceException e) {
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch(InsufficientRightsException e) {
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
		int projectId = 0;
		boolean hasUpdateRight = false;
		try {
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			Project p = this.loadCurrentProject(session);
			projectId = p.getProjectID();
			
			hasUpdateRight = this.testRight(session, Right.Update_Project);
			this.testRight(session, Right.Delete_Project);
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
			if(hasUpdateRight) {
				return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
			}
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	/**
	 * Remove user from project and project from internal list in user
	 * @param user
	 * @param project
	 * @throws UserPersistenceException
	 * @throws TriedToRemoveAdminException 
	 */
	private void removeUserFromProject(User user, Project project, boolean forced) throws UserPersistenceException, TriedToRemoveAdminException {
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
			Project currentProject = projectDAO.getProject(project.getProjectID());
			project.setRoles(currentProject.getRoles());
			project.setSprints(currentProject.getSprints());
			project.setUserstories(currentProject.getUserstories());
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:projectSettings.htm?id=" + project.getProjectID());
		} catch (InvalidSessionException | ProjectPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}

	/**
	 * Opens the dashboard
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/dashboard.htm")
	public ModelAndView viewDashboard(HttpSession session) {
		try {
			checkInvalidSession(session);
			Project project = loadCurrentProject(session);
			User loggedUser = loadActiveUser(session);
			Sprint currentSprint = project.getCurrentSprint();
			
			// get relevant userstories and tasks for the current user
			Set<UserStory> relevantUserStories = new HashSet<UserStory>();
			Set<Task> relevantTasks = new HashSet<Task>();
			// get planned time and worked time of user per task
			HashMap<Integer, Integer> plannedTimeOnTask = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> workedTimeOnTask = new HashMap<Integer, Integer>();
			for(UserStory us : project.getUserstories()) {
				// check if the user is assigned to the userstory and the userstory is not done
				if (us.getResponsibleUsers().contains(loggedUser) && !us.getPlanState().equals(PlanState.Done)) {
					relevantUserStories.add(us);
					for (Task t : us.getTasks()) {
						// checks if the user is assigned to the task and the task is not done
						if (t.getResponsibleUsers().contains(loggedUser) && !t.getPlanState().equals(PlanState.Done)) {
							relevantTasks.add(t);
							// adds planned and worked time of user for task
							plannedTimeOnTask.put(t.getId(), t.getPlannedMinOfUser(loggedUser));
							workedTimeOnTask.put(t.getId(), t.getWorkTimeOfUser(loggedUser));
						}
					}
				}
			}	
			// prepare data for burndown chart
			Set<Sprint> sprints = sprintDAO.getSprintsFromProject(project.getProjectID());
			Map<Sprint, JSONObject> chartData = new HashMap<Sprint, JSONObject>();
			for (Sprint s : sprints) {
				chartData.put(s, createChartData(s));
			}
			
			ModelMap map = this.prepareModelMap(session);
			map.addAttribute("currentSprint", currentSprint);
			map.addAttribute("relevantUserStories", relevantUserStories);
			map.addAttribute("relevantTasks", relevantTasks);
			map.addAttribute("plannedTimeOnTask", plannedTimeOnTask);
			map.addAttribute("workedTimeOnTask", workedTimeOnTask);
			map.addAttribute("chartData", chartData);
			map.addAttribute("action", Action.dashboard);
			return new ModelAndView("index", map);		
		} catch(UserPersistenceException | InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		} catch(ProjectPersistenceException | SprintPersistenceException e) {
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
		} catch (ProjectPersistenceException | RoleNotInProjectException | TriedToRemoveAdminException | UserPersistenceException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "&error=2");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/changeProjectPic")
	public ModelAndView changeUserPic(HttpServletRequest request, HttpSession session, @RequestParam("image") MultipartFile file) {
		int projectId = 0;
		try {
			checkInvalidSession(session);
			Project project = this.loadCurrentProject(session);
			projectId = project.getProjectID();
			String ending = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
			String serverPath = "resources" 
					  		  + File.separator + "projectPictures" 
					  		  + File.separator + "projectPic_";
			String fullPath = serverPath + project.getProjectID() + ending;
			this.uploadPicture(request, file, serverPath, project.getProjectID());
			project.setPicPath(fullPath);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch(InvalidSessionException | IllegalStateException | IOException |  ProjectPersistenceException e) {
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch (InvalidContentTypeException | InvaldFileSizeException e) {
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "&error=4");
		}
	}
	
	/**
	 * Generates the chart data of a sprint for the burndown chart
	 * @param s
	 * @return JSONObject
	 */
	private JSONObject createChartData(Sprint s) {
		// JSONObject containing all chart data
		JSONObject jObject = new JSONObject();
		try {
			jObject.put("startDate", s.getStartDate());
			jObject.put("idealRemaining", s.getIdealRemainingUS());
			jObject.put("backlogScope", s.getBacklogScope());
			jObject.put("doneItems", s.getDoneItems());
			jObject.put("remainingItems",s.getRemainingItems());		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jObject;
	}
}