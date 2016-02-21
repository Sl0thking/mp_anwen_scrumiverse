package com.scrumiverse.persistence.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.account.Role;
import com.scrumiverse.persistence.DAO.RoleDAO;

public class RoleDAOImpl implements RoleDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionFactory(SessionFactory sessionFactoryProject) {
		this.hibernateTemplate = 
					new HibernateTemplate(sessionFactoryProject); }
	
	@Override
	public void addRole(Role r) {
		hibernateTemplate.saveOrUpdate(r);
		
	}

}
