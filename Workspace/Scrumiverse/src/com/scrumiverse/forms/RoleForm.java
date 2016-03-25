package com.scrumiverse.forms;

import com.scrumiverse.model.account.Role;

/**
 * Simple class for easy sending of a selected 
 * Role in projectSettings.
 * Holds a selected role for sending in a form.
 * 
 * @author Kevin Jolitz
 * @version 17.03.2016
 *
 */
public class RoleForm {
	private Role role;

//	public RoleForm() {
//		role = new Role();
//	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
