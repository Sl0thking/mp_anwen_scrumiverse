package com.scrumiverse.model.account;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CollectionOfElements;

/**
 * Role of a scrumiverse user
 * 
 * @author Kevin Jolitz
 * @version 18.02.2016
 */
@Entity
public class Role {
	
	private int roleID;
	private String name;
	private List<Right> rights;

	@Id
	@GeneratedValue
	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasRights(Right right) {
		return rights.contains(right);
	}
	
	public void addRight(Right right) {
		rights.add(right);
	}
	
	public void deleteRight(Right right) {
		rights.remove(right);
	}
	
	/**
	 * Create a deep copy of this role
	 * @return Copy of this role
	 */
	public Role copy() {
		Role copyRole = new Role();
		copyRole.setName(this.name);
		copyRole.setRights(this.getRights());
		return copyRole;
	}
	
	@CollectionOfElements(targetElement = Right.class)
	@Enumerated(EnumType.STRING)
	public List<Right> getRights() {
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}
}