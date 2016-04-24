package com.scrumiverse.binder;

import java.beans.PropertyEditorSupport;

import com.scrumiverse.exception.CategoryPersistenceException;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.persistence.DAO.CategoryDAO;

/**
 * Binder class for Categories
 * 
 * @author Lasse Jacobs
 * @version 08.04.2016
 *
 */
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
		int categoryID = Integer.parseInt(id);
		try {
			if (categoryID == 0){
				setValue(null);
			}else {
				setValue(categoryDAO.getCategoryById(Integer.parseInt(id)));
			}
		} catch (CategoryPersistenceException e) {
			e.printStackTrace();
		}
	}
}