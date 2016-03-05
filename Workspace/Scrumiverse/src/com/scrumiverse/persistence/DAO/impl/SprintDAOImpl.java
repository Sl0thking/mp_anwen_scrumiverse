package com.scrumiverse.persistence.DAO.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.SprintDAO;

/**
 * DAO Implementation of Sprints
 * @author DoctorWhose, Lasse Jacobs, Kevin Jolitz
 * @version 04.03.2016
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
	
	@Override
	public Sprint getSprint(int sprintID){
		Sprint sprint = new Sprint();
		try{
			sprint = (Sprint) (hibernateTemplate.find("from Sprint where id='" + sprintID + "'").get(0));
		}catch(NullPointerException e){
			e.printStackTrace();
			System.out.println("Sprint ist nicht in der Datenbank zu finden mit id" + sprintID);
		}
		return sprint;
	}

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