package com.scrumiverse.web;

import java.beans.PropertyEditorSupport;

import com.scrumiverse.model.account.Role;
import com.scrumiverse.persistence.DAO.RoleDAO;

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
			System.err.println("ROLE-GIVEN: " + ((Role) o).getName());
			return Integer.toString( ((Role) o).getRoleID() );
		}
	}

	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		Role role = roleDAO.getRole(Integer.parseInt(id));
		System.out.println("ROLE: " + role.getName());
		setValue(role);
	}
	
}
