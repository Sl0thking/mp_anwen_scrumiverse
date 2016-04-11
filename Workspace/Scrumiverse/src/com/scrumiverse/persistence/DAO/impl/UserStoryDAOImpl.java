package com.scrumiverse.persistence.DAO.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.UserStoryPersistenceException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

/**
 * Implementation of Interface UserStoryDAO for handling 
 * the database for User Story interactions
 * 
 * @author Lasse Jacobs
 * @version 22.02.16
 *
 */

public class UserStoryDAOImpl implements UserStoryDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory); 
	}

	@Override
	public void saveUserStory(UserStory userStory) {
		hibernateTemplate.save(userStory);
	}
	
	@Override
	public void updateUserStory(UserStory userStory){
		hibernateTemplate.update(userStory);
	}
	
	@Override
	public void deleteUserStory(UserStory userStory){
		hibernateTemplate.delete(userStory);
	}

	@Override
	public List<UserStory> getAllUserstories() {
		return hibernateTemplate.find("from UserStory");
	}
	
	@Override
	public UserStory getUserStory(int userStoryID) throws UserStoryPersistenceException{
		List<UserStory> userstories = hibernateTemplate.find("from UserStory where id='" + userStoryID + "'");
		if(userstories.size() != 0){
			return userstories.get(0);
		}
		throw new UserStoryPersistenceException();
	}
	
	@Override
	public SortedSet<UserStory> getUserStoriesOfProject(int projectID) throws ProjectPersistenceException{
		List<Project> projects = hibernateTemplate.find("from Project where id='" + projectID +"'");
		if(projects.size() != 0) {
			Project project = projects.get(0);
			return project.getUserstories();
		}
		throw new ProjectPersistenceException();
	}

}
