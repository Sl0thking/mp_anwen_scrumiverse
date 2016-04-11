package com.scrumiverse.persistence.DAO.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.CannotDeleteProjectWithUsersException;
import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;

/**
 * Implementation of the dao for project objects.
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 11.04.2016
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
	 * @throws ProjectPersistenceException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Project> getProjectsFromUser(int userID) throws ProjectPersistenceException {
		List<User> possibleUsers = hibernateTemplate.find("from User where userID='"+ userID +"'");
		if(possibleUsers.size() == 1) {
			User relatedUser = possibleUsers.get(0);
			return relatedUser.getProjects();
		//when more or less than one project is found, an exception is thrown
		} else {
			throw new ProjectPersistenceException();
		}
	}
	
	/**
	 * returns a specific project by projectID
	 * @param int
	 * @return Project
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Project getProject(int projectID) throws ProjectPersistenceException{
		List<Project> projects = hibernateTemplate.find("from Project where id='" + projectID +"'");
		if(projects.size() == 1) {
			return projects.get(0);
		} else {
			throw new ProjectPersistenceException();
		}
	}
	
	@Override
	public void updateProject(Project p){
		hibernateTemplate.update(p);
	}

	@Override
	public void deleteProject(Project p) throws CannotDeleteProjectWithUsersException  {
		if(p.getProjectUsers().size() == 0) {
			hibernateTemplate.delete(p);
		} else {
			throw new CannotDeleteProjectWithUsersException();
		}
	}
}