package com.scrumiverse.model.account;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.CollectionOfElements;

import com.scrumiverse.enums.Right;

/**
 * Role of a scrumiverse user
 * 
 * @author Kevin Jolitz
 * @version 17.03.2016
 */
@Entity
public class Role implements Comparable<Role> {
	
	private int roleID;
	private String name;
	private boolean changeable;
	private Set<Right> rights;

	public Role() {
		name = "Unknown Role";
		rights = new HashSet<Right>();
		changeable = true;
	}
	
	public Role(String name) {
		this.name = name;
		this.rights = new HashSet<Right>();
		this.changeable = true;
	}

	@Id
	@GeneratedValue
	@Column(name = "RoleID")
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

	public boolean hasRight(Right right) {
		return rights.contains(right);
	}
	
	/**
	 * Add a new right to this role
	 * 
	 * @param right right to execute different actions on a project 
	 */
	public void addRight(Right right) {
		rights.add(right);
	}
	
	/**
	 * Delete a right from this role
	 * 
	 * @param right right to execute different actions on a project 
	 */
	public void deleteRight(Right right) {
		rights.remove(right);
	}
	
	/**
	 * Create a deep copy of this role
	 * 
	 * @return Copy of this role
	 */
	public Role copy() {
		Role copyRole = new Role();
		copyRole.setName(this.name);
		copyRole.setRights(this.getRights());
		return copyRole;
	}
	
	@CollectionOfElements(fetch=FetchType.EAGER)
	@JoinTable(joinColumns={@JoinColumn(name = "RoleID")})
	@Column(name = "right_name", nullable = false)
	@Enumerated(EnumType.STRING)
	public Set<Right> getRights() {
		return rights;
	}

	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}

	/**
	 * Returns if this role is changeable or static
	 * 
	 * @return if this role is changeable
	 */
	public boolean isChangeable() {
		return changeable;
	}

	/**
	 * Set if this role is changeable
	 * 
	 * @param changeable set this role to changeable
	 */
	public void setChangeable(boolean changeable) {
		this.changeable = changeable;
	}

	@Override
	public int compareTo(Role o) {
		if(this.getName().compareTo(o.getName()) == 0) {
			return new Integer(this.getRoleID()).compareTo(new Integer(o.getRoleID()));
		} else {
			return this.getName().compareTo(o.getName());
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + roleID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (roleID != other.roleID)
			return false;
		return true;
	}

	@Override
 	public String toString() {
		return String.valueOf(roleID);
	}	
}