package com.scrumiverse.binder;

import java.beans.PropertyEditorSupport;

import com.scrumiverse.exception.RolePersistenceException;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.persistence.DAO.RoleDAO;

/**
 * Binder class for Roles
 * 
 * @author Kevin Jolitz
 * @version 08.04.2016
 */
public class RoleBinder extends PropertyEditorSupport {

	RoleDAO roleDAO;
	
	public RoleBinder(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	@Override
	public String getAsText() {
		Object o = getValue(); 
		if(o == null) {
			return null;
		} else {
			return Integer.toString( ((Role) o).getRoleID() );
		}
	}

	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		Role role;
		try {
			role = roleDAO.getRole(Integer.parseInt(id));
			setValue(role);
		} catch (RolePersistenceException e) {
			e.printStackTrace();
		}
	}
}