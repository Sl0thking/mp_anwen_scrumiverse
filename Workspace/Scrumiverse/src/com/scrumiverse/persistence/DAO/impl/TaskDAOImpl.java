package com.scrumiverse.persistence.DAO.impl;

import java.util.List;
import java.util.SortedSet;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.TaskPersistenceException;
import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.TaskDAO;

/**
 * Hibernate based implementation of task persistence functions 
 * 
 * @author Kevin Jolitz
 * @version 11.04.2016
 */
public class TaskDAOImpl implements TaskDAO{
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sessionFactoryProject) {
		this.hibernateTemplate = 
					new HibernateTemplate(sessionFactoryProject); }
	
	@Override
	public void saveTask(Task task) {
		hibernateTemplate.save(task);
	}

	@Override
	public void updateTask(Task task) {
		hibernateTemplate.update(task);
	}
	
	@Override
	public void deleteTask(Task task) {
		hibernateTemplate.delete(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getAllTasks() {
		return hibernateTemplate.find("from Task");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Task getTask(int taskID) throws TaskPersistenceException {
		List<Task> possibleTasks = hibernateTemplate.find("from Task where id='" + taskID + "'");
		if(possibleTasks.size() == 1) {
			return possibleTasks.get(0);
		} else {
			throw new TaskPersistenceException();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SortedSet<Task> getTasksOfUserStory(int userStoryID) throws TaskPersistenceException {
		List<UserStory> possibleUserStories = hibernateTemplate.find("from UserStory where id='" + userStoryID + "'");
		if(possibleUserStories.size() == 1) {
			return possibleUserStories.get(0).getTasks();
		} else {
			throw new TaskPersistenceException();
		}
	}
}