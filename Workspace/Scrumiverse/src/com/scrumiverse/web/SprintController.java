package com.scrumiverse.web;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.SprintDAO;

/**
 * Controller for Sprint interactions
 * @author Toni Serfling, Kevin Jolitz
 * @version 27.02.2016
 */

@Controller
public class SprintController extends MetaController {
	
	@Autowired
	private SprintDAO sprintDAO;

	@Autowired
	private ProjectDAO projectDAO;
	/**
	 * Adds a new Sprint 
	 * @return
	 * @throws NoProjectFoundException 
	 */
	
	@RequestMapping("/addSprint.htm") 
	public ModelAndView addSprint(HttpSession session) throws NoProjectFoundException {
		try {
			this.checkInvalidSession(session);
			Project project = this.loadCurrentProject(session);
			Sprint sprint = new Sprint();
			sprintDAO.saveSprint(sprint);
			project.addSprint(sprint);
			projectDAO.updateProject(project);
			return new ModelAndView("redirect:sprintOverview.htm");	
		}catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	@RequestMapping("/sprintOverview.htm")
	public ModelAndView sprintOverview(HttpSession session) throws NoProjectFoundException {
		try {
			this.checkInvalidSession(session);
			ModelMap map = this.prepareModelMap(session);
			int projectId = (int) session.getAttribute("currentProjectId");
			Set<Sprint> sprints = sprintDAO.getSprintsFromProject(projectId);
			map.addAttribute("sprints", sprints);
			map.addAttribute("action", Action.sprintOverview);
			map.addAttribute("project", this.loadCurrentProject(session));
			return new ModelAndView("index", map);
		} catch(InvalidSessionException | NoSuchUserException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
}