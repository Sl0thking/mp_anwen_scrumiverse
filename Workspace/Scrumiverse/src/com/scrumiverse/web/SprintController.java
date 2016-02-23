package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.persistence.DAO.SprintDAO;

/**
 * Controller for Sprint interactions
 * @author DoctorWhose
 * @version 23.02.2016
 */

public class SprintController {
	
	@Autowired
	private SprintDAO sprintDAO;

	/**
	 * Adds a new Sprint 
	 * @return
	 */
	
	@RequestMapping("/addSprint.htm") 
	public ModelAndView addSprint() {		
		Sprint sprint = new Sprint();
		sprintDAO.saveSprint(sprint);		
		return new ModelAndView("redirect:backlog.htm");
		
	}
}
