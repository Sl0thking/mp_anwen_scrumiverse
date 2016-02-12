package com.scrumiverse.persistence.DAO;

import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.impl.NoSuchUserException;

public interface UserDAO {
	public void addUser(User user); 
	public User getUserByEmail(String email) throws NoSuchUserException;
}
