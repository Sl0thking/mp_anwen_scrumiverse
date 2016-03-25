package com.scrumiverse.binder;

import java.beans.PropertyEditorSupport;

import com.scrumiverse.exception.CategoryPersistenceException;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.persistence.DAO.CategoryDAO;

public class CategoryBinder extends PropertyEditorSupport {

	CategoryDAO categoryDAO;
	
	public CategoryBinder(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	
	
	@Override
	public String getAsText() {
		Object o = getValue(); 
		if(o == null) {
			return null;
		} else {
			return Integer.toString( ((Category) o).getId() );
		}
	}

	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		try {
			Category category = categoryDAO.getCategoryById(Integer.parseInt(id));
			setValue(category);
		} catch (CategoryPersistenceException e) {}
	}
}