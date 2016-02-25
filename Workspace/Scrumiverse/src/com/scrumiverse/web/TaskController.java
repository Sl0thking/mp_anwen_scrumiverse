package com.scrumiverse.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.dom4j.datatype.InvalidSchemaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.TaskDAO;
import com.scrumiverse.utility.Utility;

/**
 * Controller for operations with task data modell
 * 
 * @author Kevin Jolitz
 * @version 21.02.2016
 */
@Controller
public class TaskController {
	
	@Autowired
	TaskDAO taskDAO;
	
	@RequestMapping("/showTasks.htm")
	public ModelAndView showTasks(HttpSession session) {
		try {
			if(!Utility.isSessionValid(session)) {
				throw new InvalidSessionException();
			}
			Project project = (Project) session.getAttribute("currentProject");
			Set<UserStory> userStories = project.getUserstories();
			Map<UserStory, List<Task>> tasksOfUserStoryMap = new HashMap<UserStory, List<Task>>();
			for(UserStory userStory : userStories) {
				tasksOfUserStoryMap.put(userStory, userStory.getTasks());
			}
			ModelMap map = Utility.generateModelMap(session);
			map.addAttribute("action", Action.taskboard);
			map.addAttribute("userStories", userStories);
			map.addAttribute("tasksOfUserStories", tasksOfUserStoryMap);
			return new ModelAndView("index", map);
		} catch(InvalidSessionException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}

	/**
	 * Create new empty task in database
	 * @return ModelAndView
	 */
	@RequestMapping("/newTask.htm")
	public ModelAndView createNewTask() {
		Task task = new Task();
		taskDAO.saveTask(task);
		return new ModelAndView("redirect:backlog.htm");
	}
	
	/**
	 * Show details of a specific task
	 * @param taskID id of specific task
	 * @param session 
	 * @return ModelAndView
	 */
	@RequestMapping("/showTaskDetails.htm")
	public ModelAndView showTaskDetails(@RequestParam int taskID, HttpSession session) {
		ModelMap map = Utility.generateModelMap(session);
		Task loadedTask = taskDAO.getTask(taskID);
		map.addAttribute("detailTask", loadedTask);
		System.out.println(loadedTask.getDescription());
		return new ModelAndView("redirect:backlog.htm");
	}
}