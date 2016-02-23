package com.scrumiverse.persistence.DAO.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.persistence.DAO.SprintDAO;

/**
 * DAO Implementation of Sprints
 * @author DoctorWhose
 * @version 23.02.2016
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
	public List<Sprint> getSprintsFromProject(int projectID) {
		Project p = (Project) hibernateTemplate.find("from Project where id='"+ projectID + "'").get(0);
//		List<Sprint> sprints = p.getSprints();
		// waiting for implementation in Project
		return null;
	}

}
