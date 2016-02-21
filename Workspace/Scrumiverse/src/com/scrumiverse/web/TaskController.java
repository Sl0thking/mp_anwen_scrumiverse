package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.scrumCore.Task;
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