package com.scrumiverse.web;

import java.beans.PropertyEditorSupport;

import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.UserDAO;

public class UserBinder extends PropertyEditorSupport {

	UserDAO userDAO;
	
	public UserBinder(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Override
	public String getAsText() {
		Object o = getValue();
		if(o == null) {
			return null;
		} else {
			return Integer.toString(((User) o).getUserID());
		}
	}

	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		try {
			User user = userDAO.getUserByEmail(arg0);
			System.out.println(user.getEmail());
			setValue(user);
		} catch (NoSuchUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
