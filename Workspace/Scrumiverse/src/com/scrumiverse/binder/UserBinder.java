package com.scrumiverse.binder;

import java.beans.PropertyEditorSupport;

import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Binder class for Users
 * @author Kevin Jolitz
 * @version 08.04.2016
 */
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
			setValue(user);
		} catch (UserPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
