package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.CategoryPersistenceException;
import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.persistence.DAO.CategoryDAO;
import com.scrumiverse.persistence.DAO.ProjectDAO;

/**
 * Controller for category manipulation
 * 
 * @author Kevin Jolitz
 * @version 29.03.2016
 */
@Controller
public class CategoryController extends MetaController {
	
	@Autowired
	CategoryDAO categoryDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@RequestMapping("/addCategory.htm")
	public ModelAndView addCategory(HttpSession session) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Project);
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			Category category = new Category();
			categoryDAO.saveCategory(category);
			activeProject.addCategory(category);
			projectDAO.updateProject(activeProject);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "#category-tab");
		} catch (NoProjectFoundException | InvalidSessionException | InsufficientRightsException | NoSuchUserException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	@RequestMapping("/updateCategory.htm")
	public ModelAndView updateCategory(HttpSession session, Category category) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Project);
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			categoryDAO.updateCategory(category);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "#category-tab");
		} catch (NoProjectFoundException | InvalidSessionException | InsufficientRightsException | NoSuchUserException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	@RequestMapping("/removeCategory.htm")
	public ModelAndView removeCategory(HttpSession session, @RequestParam int id) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Project);
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			Category category = categoryDAO.getCategoryById(id);
			activeProject.removeCategory(category);
			projectDAO.updateProject(activeProject);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "#category-tab");
		} catch (NoProjectFoundException | InvalidSessionException | InsufficientRightsException | NoSuchUserException | CategoryPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
}