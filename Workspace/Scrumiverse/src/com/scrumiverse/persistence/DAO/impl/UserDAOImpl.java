package com.scrumiverse.persistence.DAO.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Implementation of the dao for user objects.
 * 
 * @author Kevin Jolitz
 * @version 16.02.2016
 *
 */
public class UserDAOImpl implements UserDAO{

	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = 
               new HibernateTemplate(sessionFactory); }
	
	@Override
	public void saveUser(User user) {
		hibernateTemplate.save(user);
	}
	
	@Override
	public void updateUser(User user){
		hibernateTemplate.update(user);
	}

	@Override
	public User getUserByEmail(String email) throws NoSuchUserException {
		List<User> users = hibernateTemplate.find("from User where email='" + email + "'");
		if(users.size() == 0) {
			throw new NoSuchUserException();
		}
		return users.get(0);
	}

	@Override
	public List<User> getAllUsers() {
		return hibernateTemplate.find("from User");
	}
}