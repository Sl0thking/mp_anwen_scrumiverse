package com.scrumiverse.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.TaskPersistenceException;
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.exception.UserStoryPersistenceException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.PlanState;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.ProjectUser;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.model.scrumFeatures.ChangeEvent;
import com.scrumiverse.model.scrumFeatures.Notification;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.TaskDAO;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

/**
 * Controller for operations with task data modell
 * 
 * @author Kevin Jolitz
 * @version 08.04.2016
 */
@Controller
public class TaskController extends MetaController {
	
	@Autowired
	TaskDAO taskDAO;
	
	@Autowired
	UserStoryDAO userStoryDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@RequestMapping("/showTasks.htm")
	public ModelAndView showTasks(HttpSession session) {
		ModelMap map = new ModelMap();
		User user = null;
		Project project = null;
		ProjectUser pUser = null;
		try {
			checkInvalidSession(session);
			user = this.loadActiveUser(session);
			project = loadCurrentProject(session);
			pUser = project.getProjectUserFromUser(user);
			map = this.prepareModelMap(session);
			testRight(session, Right.Read_Task);			
			Set<UserStory> userStories = project.getUserstories();
			Map<UserStory, List<Task>> tasksOfUserStoryMap = new HashMap<UserStory, List<Task>>();
			for(UserStory userStory : userStories) {
				List<Task> taskList = new ArrayList<Task>();
				taskList.addAll(userStory.getTasks());
				tasksOfUserStoryMap.put(userStory, taskList);
			}
			// creates map of each task and their users + time the users worked on the task
			Map<Task, HashMap<User, Integer>> userWorkedTimesOfTaskMap = new HashMap<Task, HashMap<User, Integer>>();
			for (UserStory userStory : userStories) {
				for (Task task : userStory.getTasks()) {
					HashMap<User, Integer> userWorkedTimes = new HashMap<User, Integer>();
					for (User taskUser : task.getResponsibleUsers()) {
						userWorkedTimes.put(taskUser, task.getWorkTimeOfUser(taskUser));
					}
					userWorkedTimesOfTaskMap.put(task, userWorkedTimes);
				}
			}
			map.addAttribute("action", Action.taskboard);
			map.addAttribute("userStories", userStories);
			map.addAttribute("tasksOfUserStories", tasksOfUserStoryMap);
			map.addAttribute("planStates", PlanState.values());
			map.addAttribute("userWorkedTimeOfTask", userWorkedTimesOfTaskMap);
			map.addAttribute("canCreateTask", pUser.getRole().hasRight(Right.Create_Task));
			map.addAttribute("canDeleteTask", pUser.getRole().hasRight(Right.Delete_Task));
			return new ModelAndView("index", map);
		} catch(ProjectPersistenceException | UserPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch(InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (InsufficientRightsException e) {
			if(user != null && project != null && pUser != null) {
				map.addAttribute("action", Action.taskboard);
				map.addAttribute("canCreateTask", pUser.getRole().hasRight(Right.Create_Task));
				map.addAttribute("canDeleteTask", pUser.getRole().hasRight(Right.Delete_Task));
				return new ModelAndView("index", map);
			} else {
				return new ModelAndView("redirect:projectOverview.htm");
			}
		}
	}

	/**
	 * Create new empty task in database
	 * @return ModelAndView
	 */
	@RequestMapping("/addTask.htm")
	public ModelAndView createNewTask(@RequestParam int id, HttpSession session) {
		try{
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			Task task = new Task();
			task.addHistoryEntry(ChangeEvent.TASK_CREATED, user);
			taskDAO.saveTask(task);		
			UserStory userStory = userStoryDAO.getUserStory(id);
			userStory.addTask(task);
			userStoryDAO.updateUserStory(userStory);
			return new ModelAndView("redirect:showTasks.htm");
		} catch (UserStoryPersistenceException | UserPersistenceException e){
			return new ModelAndView("redirect:showTasks.htm");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Handles deletion of specific task
	 * @param taskID id of specific task
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/deleteTask.htm")
	public ModelAndView deleteTask(@RequestParam int taskID, HttpSession session) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Delete_Task);
			Task task = taskDAO.getTask(taskID);
			taskDAO.deleteTask(task);
			return new ModelAndView("redirect:showTasks.htm");
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (ProjectPersistenceException | TaskPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InsufficientRightsException e) {
			return new ModelAndView("redirect:showTasks.htm");
		}
	}
	
	@RequestMapping("/updateTask.htm")
	public ModelAndView updateTask(Task task, HttpSession session) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Task);
			User user = this.loadActiveUser(session);
			Task oldTask = taskDAO.getTask(task.getId());
			task.setHistory(oldTask.getHistory());
			task.setTags(oldTask.getTags());
			task.setPlannedMinOfUsers(oldTask.getPlannedMinOfUsers());
			task.addHistoryEntry(ChangeEvent.TASK_UPDATED, user);
			generateNotification(session, ChangeEvent.TASK_UPDATED, task);
			taskDAO.updateTask(task);
			return new ModelAndView("redirect:showTasks.htm#" + task.getId());
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (ProjectPersistenceException | TaskPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InsufficientRightsException e) {
			return new ModelAndView("redirect:showTasks.htm#" + task.getId());
		}	
	}
	
	/**
	 * Handles removing of tag from task
	 * @param taskID id of the task
	 * @param tag tag that shall be removed
	 * @param session
	 * @return
	 */
	@RequestMapping("/removeTagFromTask.htm")
	public ModelAndView removeTagFromTask(@RequestParam int taskID, @RequestParam String tag, HttpSession session) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Task);
			Task task = taskDAO.getTask(taskID);
			task.removeTag(tag);
			taskDAO.updateTask(task);
			return new ModelAndView("redirect:showTasks.htm#" + taskID);
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (ProjectPersistenceException | TaskPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InsufficientRightsException e) {
			return new ModelAndView("redirect:showTasks.htm#" + taskID);
		}	
	}
	
	/**
	 * Handles adding of tags to task
	 * @param taskID id of the task
	 * @param tags tags that shall be added
	 * @param session
	 * @return
	 */
	@RequestMapping("/addTagsToTask.htm")
	public ModelAndView addTagsToTask(@RequestParam int taskID, @RequestParam String tags, HttpSession session) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Task);
			Task task = taskDAO.getTask(taskID);
			// split comma seperated list of tags
			List<String> tagList = Arrays.asList(tags.split(","));
			for (String tag : tagList) {
				task.addTag(tag.trim());
			}
			taskDAO.updateTask(task);
			return new ModelAndView("redirect:showTasks.htm#" + taskID);
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (ProjectPersistenceException | TaskPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InsufficientRightsException e) {
			return new ModelAndView("redirect:showTasks.htm#" + taskID);
		}	
	}
	
	/**
	 * Handles adding of user to task
	 * @param taskID id of the task
	 * @param userID id of the user that shall be added
	 * @param session
	 * @return
	 */
	@RequestMapping("/addUserToTask")
	public ModelAndView addUserToTask(@RequestParam int taskID, @RequestParam int userID, HttpSession session) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Task);
			Task task = taskDAO.getTask(taskID);
			User user = userDAO.getUser(userID);
			task.addUser(user);
			taskDAO.updateTask(task);
			return new ModelAndView("redirect:showTasks.htm#" + taskID);
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (ProjectPersistenceException | TaskPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InsufficientRightsException e) {
			return new ModelAndView("redirect:showTasks.htm#" + taskID);
		}	
	}
	
	/**
	 * Handles removal of user from task
	 * @param taskID id of the task
	 * @param userID id of the user that shall be removed
	 * @param session
	 * @return
	 */
	@RequestMapping("/removeUserFromTask")
	public ModelAndView removeUserFromTask(@RequestParam int taskID, @RequestParam int userID, HttpSession session) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Task);
			Task task = taskDAO.getTask(taskID);
			User user = userDAO.getUser(userID);
			task.removeUser(user);
			taskDAO.updateTask(task);
			return new ModelAndView("redirect:showTasks.htm#" + taskID);
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (ProjectPersistenceException | TaskPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		} catch (InsufficientRightsException e) {
			return new ModelAndView("redirect:showTasks.htm#" + taskID);
		}	
	}
	
	private void generateNotification(HttpSession session, ChangeEvent event, Task task) {
		try {
			User user = this.loadActiveUser(session);
			Project project = this.loadCurrentProject(session);
			Task oldTask = taskDAO.getTask(task.getId());
			for(User u: project.getAllUsers()){
				if(!u.equals(user) && isTaskInSprint(task, project.getCurrentSprint())){
					if(		(project.hasUserRight(Right.Notify_Your_UserStory_Task, u) && task.getResponsibleUsers().contains(u))
						||	(project.hasUserRight(Right.Notify_UserStory_Task_for_Current_Sprint, u))
						|| 	(project.hasUserRight(Right.Notify_PlannedMin_for_Current_Sprint, u) && task.getPlannedMin() != oldTask.getPlannedMin())
					){
						Notification notify = new Notification(user, u, event, task);
						messageDAO.saveNotification(notify);
						u.addNotification(notify);
					}
				}
			}
		} catch (UserPersistenceException | ProjectPersistenceException | TaskPersistenceException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isTaskInSprint(Task task, Sprint sprint){
		boolean result = false;
		for(UserStory forUserstory: sprint.getUserStories()){
			for(Task forTask: forUserstory.getTasks()){
				if(forTask.equals(task)){
					result = true;
				}
			}
		}
		return result;
	}
}