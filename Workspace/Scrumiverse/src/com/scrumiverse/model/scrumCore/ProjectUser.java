package com.scrumiverse.model.scrumCore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;

/**
 * Association between a user and role in a project context.
 * Prevents a trenary association.
 * 
 * @author Kevin Jolitz
 * @version 23.02.2016
 *
 */
@Entity
public class ProjectUser {
	private int id;
	private User user;
	private Role role;

	public ProjectUser() {
		super();
	}
	
	public ProjectUser(User user, Role role) {
		super();
		this.user = user;
		this.role = role;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		ProjectUser other = (ProjectUser) obj;
		if (id != other.id)
			return false;
		return true;
	}
}