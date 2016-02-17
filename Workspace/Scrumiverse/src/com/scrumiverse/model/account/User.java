package com.scrumiverse.model.account;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;



import com.scrumiverse.model.scrumCore.Project;

@Entity
public class User {
	private int userID;
	private String email;
	private String name;
	private boolean emailNotification;
	private List<Project> projects;
	private String password;
	
	public User() {
		email = "";
		name = "";
		emailNotification = false;
		password = "";
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
	
	
	
	//Needs a check for already existing projects
//	public void addProject(Project p) {
//		this.projects.add(p);
//	}
	
//	public List<Project> getAllProjects() {
//		return this.projects;
//	}
	
//	public void deleteProject(int projectID) {
//		Project targetProject = null;
//		for(Project p : this.projects) {
//			if(p.getProjectID() == projectID) {
//				targetProject = p;
//			}
//		}
//		if(targetProject != null) {
//			this.projects.remove(targetProject);
//		}
//	}

	@ManyToMany(mappedBy="users")
	public List<Project> getProjects() {
		return projects;
	}


	public void setProjects(List<Project> projects) {
		this.projects = projects;
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
	
	
}
