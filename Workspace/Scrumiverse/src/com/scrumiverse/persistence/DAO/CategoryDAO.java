package com.scrumiverse.persistence.DAO;

import com.scrumiverse.exception.CategoryPersistenceException;
import com.scrumiverse.model.scrumFeatures.Category;

/**
 * Interface for persistence methods of categories
 * 
 * @author Kevin Jolitz
 * @version 25.03.2016
 *
 */
public interface CategoryDAO {
	public void saveCategory(Category category);
	public Category getCategoryById(int id) throws CategoryPersistenceException;
	public void updateCategory(Category category);
	public void deleteCategory(Category category);
}
