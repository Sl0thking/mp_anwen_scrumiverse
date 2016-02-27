package com.scrumiverse.persistence.DAO.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

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
	public UserStory getUserStory(int userStoryID){
		UserStory userStory = new UserStory();
		try{
			userStory = (UserStory) (hibernateTemplate.find("from UserStory where id='" + userStoryID + "'").get(0));
		}catch(NullPointerException e){
			e.printStackTrace();
			System.out.println("User Story ist nicht in Datenbank zu finden mit id: " + userStoryID);
		}
		return userStory;
	}
	
	@Override
	public Set<UserStory> getUserStoriesOfProject(int projectID){
		Project proj = new Project();
		try{
			proj = (Project) hibernateTemplate.find("from Project where id='" + projectID + "'").get(0);
		}catch(NullPointerException e){
			e.printStackTrace();
			return new HashSet<UserStory>();
		}
		return (Set<UserStory>) proj.getUserstories();
	}

}
