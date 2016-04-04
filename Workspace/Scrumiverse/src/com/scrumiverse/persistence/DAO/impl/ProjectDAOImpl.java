package com.scrumiverse.persistence.DAO.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;

/**
 * Implementation of the dao for project objects.
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 04.04.2016
 *
 */

public class ProjectDAOImpl implements ProjectDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sessionFactoryProject) {
		this.hibernateTemplate = 
					new HibernateTemplate(sessionFactoryProject); }
	

	@Override
	public void saveProject(Project p) {
		hibernateTemplate.save(p);		
	}
	/**
	 * returns all projects by userID
	 * @param int
	 * @return Set<Project>
	 */
	@Override
	public Set<Project> getProjectsFromUser(int userID) {
		User relatedUser = (User) hibernateTemplate.find("from User where userID='"+ userID +"'").get(0);
		return relatedUser.getProjects();
	}
	/**
	 * returns a specific project by projectID
	 * @param int
	 * @return Project
	 */
	@Override
	public Project getProject(int projectID) throws NoProjectFoundException{
		List<Project> projects = hibernateTemplate.find("from Project where id='" + projectID +"'");
		if(projects.size() != 0) {
			return projects.get(0);
		}
		throw new NoProjectFoundException();
	}
	
	@Override
	public void updateProject(Project p){
		hibernateTemplate.update(p);
	}

	@Override
	public void deleteProject(Project p) throws Exception {
		if(p.getProjectUsers().size() == 0) {
			hibernateTemplate.delete(p);
		} else {
			throw new Exception();
		}
	}
}