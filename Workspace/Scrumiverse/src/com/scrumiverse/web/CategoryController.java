package com.scrumiverse.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.persistence.DAO.CategoryDAO;
import com.scrumiverse.persistence.DAO.ProjectDAO;

@Controller
public class CategoryController extends MetaController {
	
	@Autowired
	CategoryDAO categoryDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@RequestMapping("/updateCategory.htm")
	public ModelAndView updateCategory(HttpSession session, Category category) {
		try {
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			categoryDAO.updateCategory(category);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch (NoProjectFoundException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
		
	}
	
	@RequestMapping("/addCategory.htm")
	public ModelAndView addCategory(HttpSession session) {
		try {
			Project activeProject = this.loadCurrentProject(session);
			int projectId = activeProject.getProjectID();
			Category category = new Category();
			categoryDAO.saveCategory(category);
			activeProject.addCategory(category);
			projectDAO.updateProject(activeProject);
			return new ModelAndView("redirect:projectSettings.htm?id=" + projectId);
		} catch (NoProjectFoundException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
}