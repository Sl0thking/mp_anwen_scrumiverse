package com.scrumiverse.persistence.DAO.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.CategoryPersistenceException;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.persistence.DAO.CategoryDAO;

/**
 * Implementation of category persistence methods
 * 
 * @author Kevin Jolitz
 * @version 25.03.2016
 */
public class CategoryDAOImpl implements CategoryDAO {

	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactorySprint) {
		this.hibernateTemplate = 
               new HibernateTemplate(sessionFactorySprint); }

	@Override
	public void saveCategory(Category category) {
		hibernateTemplate.save(category);
	}

	@Override
	public Category getCategoryById(int id) throws CategoryPersistenceException {
		List<Category> categories = hibernateTemplate.find("from Category where Id='" + id + "'");
		if(categories.size() > 1 || categories.size() <= 0) {
			throw new CategoryPersistenceException();
		}
		return categories.get(0);
	}

	@Override
	public void updateCategory(Category category) {
		hibernateTemplate.update(category);
	}

	@Override
	public void deleteCategory(Category category) {
		hibernateTemplate.delete(category);
	}
}