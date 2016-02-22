package com.scrumiverse.persistence.DAO;

import java.util.List;

import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.impl.NoSuchUserException;

public interface UserDAO {
	public void saveUser(User user); 
	public User getUserByEmail(String email) throws NoSuchUserException;
	public List<User> getAllUsers();
}
