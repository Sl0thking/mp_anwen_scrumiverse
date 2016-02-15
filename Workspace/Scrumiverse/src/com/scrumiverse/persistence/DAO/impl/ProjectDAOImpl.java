package com.scrumiverse.persistence.DAO.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;

public class ProjectDAOImpl implements ProjectDAO {
	
//	private HibernateTemplate hibernateTemplate;
//	
//	public void setSessionFactory(SessionFactory sessionFactoryProject) {
//		this.hibernateTemplate = 
//               new HibernateTemplate(sessionFactoryProject); }
//
//	@Override
//	public void addProject(Project project) {
//		hibernateTemplate.saveOrUpdate(project);	
//	}
//
//	@Override
//	public List<Project> getAllProjects() throws NoProjectsFoundException{
//		
//		List<Project> projects = hibernateTemplate.find("from Project");
//		
//		if(projects.size() == 0) {
//			throw new NoProjectsFoundException();
//		}
//		return projects;
//			
//	}
//	
	

}
