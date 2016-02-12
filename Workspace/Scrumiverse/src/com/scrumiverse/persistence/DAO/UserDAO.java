package com.scrumiverse.persistence.DAO;

import com.scrumiverse.model.account.User;

public interface UserDAO {
	public void addUser(User user); 
	public boolean checkLogin(User user, String hashedPW);
}
