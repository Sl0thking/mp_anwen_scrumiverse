package com.scrumiverse.persistence.DAO.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

/**
 * Implementation of Interface UserStoryDAO for handling 
 * the database for User Story interactions
 * 
 * @author Lasse Jacobs
 * @version 18.02.16
 *
 */

public class UserStoryDAOImpl implements UserStoryDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory); 
	}

	@Override
	public void addUserStory(UserStory userStory) {
		hibernateTemplate.saveOrUpdate(userStory);
	}

	@Override
	public List<UserStory> getAllUserstories() {
		List<UserStory> userstories = hibernateTemplate.find("from UserStory");
		return userstories;
	}

}
