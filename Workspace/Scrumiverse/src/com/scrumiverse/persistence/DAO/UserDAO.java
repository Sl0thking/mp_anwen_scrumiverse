package com.scrumiverse.persistence.DAO;

import java.util.List;

import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.model.account.User;

public interface UserDAO {
	public void saveUser(User user); 
	public User getUserByEmail(String email) throws NoSuchUserException;
	public List<User> getAllUsers();
}
