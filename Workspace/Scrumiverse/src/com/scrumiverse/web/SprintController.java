package com.scrumiverse.web;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.persistence.DAO.SprintDAO;
import com.scrumiverse.utility.Utility;

/**
 * Controller for Sprint interactions
 * @author Toni Serfling
 * @version 23.02.2016
 */

@Controller
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
		return new ModelAndView("redirect:sprintOverview.htm");	
	}
	
	@RequestMapping("/sprintOverview.htm")
	public ModelAndView sprintOverview(HttpSession session) {
		ModelMap map = Utility.generateModelMap(session);
		Project project = (Project) session.getAttribute("currentProject");
		Set<Sprint> sprints = sprintDAO.getSprintsFromProject(project.getProjectID());
		map.addAttribute("sprints", sprints);
		map.addAttribute("action", Action.sprintOverview);
		return new ModelAndView("index", map);
	}
}
