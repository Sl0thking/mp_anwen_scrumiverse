package com.scrumiverse.persistence.DAO.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.NoSprintFoundException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.SprintDAO;

/**
 * DAO Implementation of Sprints
 * @author Toni Serfling, Lasse Jacobs, Kevin Jolitz
 * @version 04.04.2016
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
	@Override
	public Sprint getSprint(int sprintID) throws NoSprintFoundException {
		List<Sprint> sprints = hibernateTemplate.find("from Sprint where id='" + sprintID + "'");	
		if(sprints.size() != 0){
			return sprints.get(0);
		}
		throw new NoSprintFoundException();
	}
	/**
	 * returns a set of sprints from specific project
	 * @param int
	 * @return Set<Sprint>
	 */
	@Override
	public Set<Sprint> getSprintsFromProject(int projectID) {
		Project p = new Project();
		try {
			p = (Project) hibernateTemplate.find("from Project where ProjectID='"+ projectID + "'").get(0);
		} catch(NullPointerException n) {
			n.printStackTrace();	
			return new HashSet<Sprint>();
		}
		return (Set<Sprint>) p.getSprints();
	}
}