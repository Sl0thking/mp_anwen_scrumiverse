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
	
	public static ProjectDAOImpl instance;
	
	public static ProjectDAOImpl getInstance() {
		if (instance == null) {
			instance=new ProjectDAOImpl();
		}
		return instance;
		
	}

	@Override
	public void addProject(Project p) {
		hibernateTemplate.saveOrUpdate(p);		
	}

	@Override
	public List<Project> getProjectsFromUser(int userID) {
		List<Project> projects = hibernateTemplate.find("from Project where id='"+ userID +"'");
		return projects;	
	}
	
	@Override
	public Project getProject(int projectID){
		Project project = (Project) hibernateTemplate.find("from Project where id='" + projectID +"'");
		
		return project;
	}
	
	@Override
	public void addUser(Project p, User u){
		p.addUser(u);
		hibernateTemplate.update(p);
	}
	
	@Override
	public Map<User, Role> getAllUsers(int projectID) {
		Map<User, Role> users = (Map<User, Role>) hibernateTemplate.find("from User where id='"+ projectID + "'");
		
		return users;
		
	}
	
	@Override
	public void removeUser(Project p, int userID) {
		p.removeUser(userID);
		hibernateTemplate.update(p);
	}
	
	@Override
	public void removeProject(Project p) {
		hibernateTemplate.delete(p);
	}
	
	@Override
	public void renameProject(Project p, String name) {
		p.setName(name);
		hibernateTemplate.update(p);
	}
}
