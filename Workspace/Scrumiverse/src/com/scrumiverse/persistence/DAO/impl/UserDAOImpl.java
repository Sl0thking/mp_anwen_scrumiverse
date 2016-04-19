package com.scrumiverse.persistence.DAO.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Implementation of the dao for user objects.
 * 
 * @author Kevin Jolitz
 * @version 12.04.2016
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
	/**
	 * Returns user by Email
	 * @param String
	 * @return User
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User getUserByEmail(String email) throws UserPersistenceException {
		List<User> users = hibernateTemplate.find("from User where email='" + email + "'");
		if(users.size() == 1) {
			return users.get(0);
		}
		throw new UserPersistenceException();
	}
	/**
	 * Returns all users
	 * @return List<User>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		return hibernateTemplate.find("from User");
	}
	/**
	 * Returns user by id
	 * @param int
	 * @return User
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User getUser(int id) throws UserPersistenceException {
		List<User> users = hibernateTemplate.find("from User where userID='" + id + "'"); 
		if(users.size() == 1) {
			return users.get(0);
		}
		throw new UserPersistenceException();
	}
}