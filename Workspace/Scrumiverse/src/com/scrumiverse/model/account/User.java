package com.scrumiverse.model.account;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

import com.scrumiverse.model.scrumCore.Project;

/**
 * Datamodell for a scrumiverse user account
 * 
 * @author Kevin Jolitz
 * @version 23.02.2016
 */
@Entity
public class User {
	private int userID;
	
	@NotBlank(message="please enter a valid email adress.")
	@RegExp(value="^[A-Za-z0-9.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
			message="not a valid email adress.")
	private String email;
	
	@NotBlank(message="please enter a surname and name.")
	@RegExp(value="^[\\w]{3,30}[ ]{1,}[\\w]{3,30}$",
			message="split name and surname with whitespace.")
	private String name;
	
	private boolean emailNotification;
	private Set<Project> projects;
	
	@NotBlank(message="must enter a valid password.")
	@RegExp(value="^(?=.*[\\d])(?=.*[A-Z])(?=.*[a-z])[\\w\\d!@#$%_]{6,40}$",
			message="must have no spaces, at least 1 digit, at least 1 uppercase and lowercase letter and at least one lowercase letter.")
	private String password;
	
	public User() {
		email = "";
		name = "";
		emailNotification = false;
		password = "";
		projects = new HashSet<Project>();
	}
	
	@Id
	@GeneratedValue
	@Column(name="UserID")
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isEmailNotification() {
		return emailNotification;
	}
	
	public void setEmailNotification(boolean emailNotification) {
		this.emailNotification = emailNotification;
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	public Set<Project> getProjects() {
		return projects;	
}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	public void addProject(Project project) {
		this.projects.add(project);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", email=" + email + ", name=" + name + ", emailNotification="
				+ emailNotification + ", password=" + password + "]";
	}

	public void leaveProject(Project p) {
		this.projects.remove(p);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userID;
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
		User other = (User) obj;
		if (userID != other.userID)
			return false;
		return true;
	}	
}