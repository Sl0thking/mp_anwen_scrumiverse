package com.scrumiverse.model.account;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

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
	private Set<Right> rights;

	public Role() {
		name = "Unknown Role";
		rights = new HashSet<Right>();
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
	
	@CollectionOfElements
	@JoinTable(joinColumns={@JoinColumn(name = "RoleID")})
	@Column(name = "right_name", nullable = false)
	@Enumerated(EnumType.STRING)
	public Set<Right> getRights() {
		return rights;
	}

	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}
}