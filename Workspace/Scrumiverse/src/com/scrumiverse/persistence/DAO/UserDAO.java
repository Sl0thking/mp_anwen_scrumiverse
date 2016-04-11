package com.scrumiverse.persistence.DAO;

import java.util.List;

import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.model.account.User;

public interface UserDAO {
	public void saveUser(User user); 
	public User getUserByEmail(String email) throws UserPersistenceException;
	public List<User> getAllUsers();
	void updateUser(User user);
	public User getUser(int id) throws UserPersistenceException;
}
