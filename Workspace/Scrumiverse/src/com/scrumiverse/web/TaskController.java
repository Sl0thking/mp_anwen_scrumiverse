package com.scrumiverse.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.persistence.DAO.TaskDAO;

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
	
//	@RequestMapping()
//	public ModelAndView showTaskDetails(Task task) {
//		taskDAO.getTask(task);
//	}
}
