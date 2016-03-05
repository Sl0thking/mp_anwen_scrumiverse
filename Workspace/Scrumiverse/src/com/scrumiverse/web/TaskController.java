package com.scrumiverse.web;

import java.util.ArrayList;
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

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.NoUserStoryFoundException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.TaskDAO;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

/**
 * Controller for operations with task data modell
 * 
 * @author Kevin Jolitz
 * @version 27.02.2016
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
		try {
			checkInvalidSession(session);
			Project project = loadCurrentProject(session);
			Set<UserStory> userStories = project.getUserstories();
			Map<UserStory, List<Task>> tasksOfUserStoryMap = new HashMap<UserStory, List<Task>>();
			for(UserStory userStory : userStories) {
				List<Task> taskList = new ArrayList<Task>();
				taskList.addAll(userStory.getTasks());
				tasksOfUserStoryMap.put(userStory, taskList);
			}
			ModelMap map = this.prepareModelMap(session);
			map.addAttribute("action", Action.taskboard);
			map.addAttribute("userStories", userStories);
			map.addAttribute("tasksOfUserStories", tasksOfUserStoryMap);
			return new ModelAndView("index", map);
		} catch(NoProjectFoundException | InvalidSessionException | NoSuchUserException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}

	/**
	 * Create new empty task in database
	 * @return ModelAndView
	 */
	@RequestMapping("/addTask.htm")
	public ModelAndView createNewTask(@RequestParam int id) {
		Task task = new Task();
		taskDAO.saveTask(task);
		try{
			UserStory userStory = userStoryDAO.getUserStory(id);
			userStory.addTask(task);
			userStoryDAO.updateUserStory(userStory);
		} catch (NoUserStoryFoundException e){
			e.printStackTrace();
		}
		return new ModelAndView("redirect:showTasks.htm");
	}
	
	/**
	 * Show details of a specific task
	 * @param taskID id of specific task
	 * @param session 
	 * @return ModelAndView
	 */
	@RequestMapping("/showTaskDetails.htm")
	public ModelAndView showTaskDetails(@RequestParam int taskID, HttpSession session) {
		try {
			ModelMap map = prepareModelMap(session);
			Task loadedTask = taskDAO.getTask(taskID);
			map.addAttribute("detailTask", loadedTask);
			System.out.println(loadedTask.getDescription());
			return new ModelAndView("redirect:backlog.htm");
		}catch (Exception e) {
			return new ModelAndView("redirect:backlog.htm");
		}
	}
}