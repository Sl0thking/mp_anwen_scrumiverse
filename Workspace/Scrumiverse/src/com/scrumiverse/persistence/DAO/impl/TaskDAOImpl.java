package com.scrumiverse.persistence.DAO.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.persistence.DAO.TaskDAO;

/**
 * Hibernate based implementation of task persistence functions 
 * 
 * @author Kevin Jolitz
 * @version 21.02.2016
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
		hibernateTemplate.saveOrUpdate(task);
	}

	@Override
	public List<Task> getAllTasks() {
		return hibernateTemplate.find("from Task");
	}
}