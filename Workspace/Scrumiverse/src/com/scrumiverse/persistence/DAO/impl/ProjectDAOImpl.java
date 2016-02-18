package com.scrumiverse.persistence.DAO.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.persistence.DAO.ProjectDAO;

public class ProjectDAOImpl implements ProjectDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sessionFactoryProject) {
		this.hibernateTemplate = 
					new HibernateTemplate(sessionFactoryProject); }

	@Override
	public void addProject(Project p) {
		hibernateTemplate.saveOrUpdate(p);		
	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> projects = hibernateTemplate.find("from Project");
			
		return projects;	
	}
	
	@Override
	public Project getProject(int projectID){
		Project project = (Project) hibernateTemplate.find("select", projectID, "from Project");
		
		return project;
	}
	
	@Override
	public void addUser(Project p, User u){
		p.addUser(u);
		hibernateTemplate.update(p);
	}
	
	@Override
	public Map<User, Role> getAllUsers(int projectID) {
		Map<User, Role> users = (Map<User, Role>) hibernateTemplate.find("select User from", projectID);
		
		return users;
		
	}
	
	

}
