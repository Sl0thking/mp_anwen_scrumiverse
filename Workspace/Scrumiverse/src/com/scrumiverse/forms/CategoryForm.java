package com.scrumiverse.forms;

import com.scrumiverse.model.scrumFeatures.Category;

/**
 * Simple class for easy sending of a selected 
 * Category in projectSettings.
 * Holds a selected role for sending in a form.
 * 
 * @author Kevin Jolitz
 * @version 24.04.2016
 */
public class CategoryForm {
	
	private Category category;
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}