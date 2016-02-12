package com.scrumiverse.persistence.DAO.impl;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.UserDAO;

public class UserDAOImpl implements UserDAO{

	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = 
               new HibernateTemplate(sessionFactory); }

	
	@Override
	public void addUser(User user) {
		System.out.println(user.getName());
		hibernateTemplate.saveOrUpdate(user);
		
	}

	@Override
	public boolean checkLogin(User user, String hashedPW) {
		return false;	
	}
}
