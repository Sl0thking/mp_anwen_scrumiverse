package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.AccessViolationException;
import com.scrumiverse.exception.CategoryPersistenceException;
import com.scrumiverse.exception.InsufficientRightsException;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.persistence.DAO.CategoryDAO;
import com.scrumiverse.persistence.DAO.ProjectDAO;

/**
 * Controller for category manipulation
 * 
 * @author Kevin Jolitz
 * @version 23.04.2016
 */
@Controller
public class CategoryController extends MetaController {
	
	@Autowired
	CategoryDAO categoryDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	/**
	 * Adds a category to the current project
	 * @param HttpSession
	 * @return ModelAndView
	 */
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
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "&tab=category-tab");
		} catch (ProjectPersistenceException | InvalidSessionException | InsufficientRightsException | UserPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	/**
	 * Updates given category in current project
	 * @param HttpSession
	 * @param Category
	 * @return ModelAndView
	 */
	@RequestMapping("/updateCategory.htm")
	public ModelAndView updateCategory(HttpSession session, Category category) {
		try {
			checkInvalidSession(session);
			testRight(session, Right.Update_Project);
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			categoryDAO.updateCategory(category);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "&tab=category-tab");
		} catch (ProjectPersistenceException | InvalidSessionException | InsufficientRightsException | UserPersistenceException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	/**
	 * Removes category from project by given category id
	 * @param HttpSession
	 * @param int
	 * @return ModelAndView
	 */
	@RequestMapping("/removeCategory.htm")
	public ModelAndView removeCategory(HttpSession session, @RequestParam int id) {
		try {
			checkInvalidSession(session);
			Category category = categoryDAO.getCategoryById(id);
			testIsPartOfCurrentProject(session, category);
			testRight(session, Right.Update_Project);
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			activeProject.removeCategory(category);
			projectDAO.updateProject(activeProject);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId + "&tab=category-tab");
		} catch (ProjectPersistenceException | InvalidSessionException | InsufficientRightsException | 
				 UserPersistenceException | CategoryPersistenceException | AccessViolationException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
}