package com.scrumiverse.persistence.DAO.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.persistence.DAO.ProjectDAO;

/**
 * Implementation of the dao for project objects.
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 22.02.2016
 *
 */

public class ProjectDAOImpl implements ProjectDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sessionFactoryProject) {
		this.hibernateTemplate = 
					new HibernateTemplate(sessionFactoryProject); }
	
//	public static ProjectDAOImpl instance;
//	
//	public static ProjectDAOImpl getInstance() {
//		if (instance == null) {
//			instance=new ProjectDAOImpl();
//		}
//		return instance;
//		
//	}

	@Override
	public void saveProject(Project p) {
		hibernateTemplate.saveOrUpdate(p);		
	}

	@Override
	public List<Project> getProjectsFromUser(int userID) {
		List<Project> projects = hibernateTemplate.find("from Project where id='"+ userID +"'");
		return projects;	
	}
	
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
	public Map<User, Role> getAllUsers(int projectID) {
		Map<User, Role> users = (Map<User, Role>) hibernateTemplate.find("from User where id='"+ projectID + "'");
		return users;
		
	}

	@Override
	public void deleteProject(Project p) throws Exception {
		if(p.getUsers().keySet().size() == 0) {
			hibernateTemplate.delete(p);
		} else {
			throw new Exception();
		}
	}


}
