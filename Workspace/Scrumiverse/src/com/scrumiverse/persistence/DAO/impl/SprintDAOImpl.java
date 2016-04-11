package com.scrumiverse.persistence.DAO.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.SprintPersistenceException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.persistence.DAO.SprintDAO;

/**
 * DAO Implementation of Sprints
 * @author Toni Serfling, Lasse Jacobs, Kevin Jolitz
 * @version 11.04.2016
 */

public class SprintDAOImpl implements SprintDAO {
	
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactorySprint) {
		this.hibernateTemplate = 
               new HibernateTemplate(sessionFactorySprint); }

	@Override
	public void saveSprint(Sprint s) {
		hibernateTemplate.save(s);	
	}
	
	@Override
	public void updateSprint(Sprint sprint){
		hibernateTemplate.update(sprint);
	}
	
	@Override
	public void deleteSprint(Sprint sprint){
		hibernateTemplate.delete(sprint);
	}
	/**
	 * Returns a single sprint by id
	 * @param int
	 * @return Sprint
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Sprint getSprint(int sprintID) throws SprintPersistenceException {
		List<Sprint> sprints = hibernateTemplate.find("from Sprint where id='" + sprintID + "'");	
		if(sprints.size() == 1){
			return sprints.get(0);
		} else {
			throw new SprintPersistenceException();
		}
	}
	
	/**
	 * returns a set of sprints from specific project
	 * @param int
	 * @return Set<Sprint>
	 * @throws SprintPersistenceException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Sprint> getSprintsFromProject(int projectID) throws SprintPersistenceException {
		List<Project> possibleProjects = hibernateTemplate.find("from Project where ProjectID='"+ projectID + "'");
		if(possibleProjects.size() == 1) {
			return possibleProjects.get(0).getSprints();
		} else {
			throw new SprintPersistenceException();
		}
	}
}