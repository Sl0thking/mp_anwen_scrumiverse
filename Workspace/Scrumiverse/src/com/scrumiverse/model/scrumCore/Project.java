package com.scrumiverse.model.scrumCore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.hibernate.annotations.MapKeyManyToMany;

import com.scrumiverse.model.account.*;
import com.scrumiverse.model.scrumCore.*;
import com.scrumiverse.model.scrumFeatures.*;
import javax.persistence.JoinColumn;

/**
 * Datamodel for a scrumiverse project
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 22.02.2016
 */

@Entity
public class Project {
	
	private int projectID;
	private String name;
	private String description;
	//private List<Role> roles;
	private Map<User, Role> users;
	//private List<Sprint> sprints;
	//private List<UserStory> userstories;
	//private List<Category> categories;
	
	public Project() {
		projectID = 0;
		name = "New Project";
		description = "Project Description";
		users = new HashMap<User, Role>();
	}
	
	@Id
	@GeneratedValue
	@Column(name="ProjectID")
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public List<Role> getRoles() {
//		return roles;
//	}
//	public void setRoles(List<Role> roles) {
//		this.roles = roles;
//	}
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinTable(name="Project_User", 
			joinColumns={@JoinColumn(name="ProjectID")}, 
			inverseJoinColumns={@JoinColumn(name="RoleID")})
	@MapKeyManyToMany(joinColumns={@JoinColumn(name="UserID")})
	public Map<User, Role> getUsers() {
		return users;
	}
	
	public void setUsers(Map<User, Role> users) {
		this.users = users;
	}
	
//	public List<Sprint> getSprints() {
//		return sprints;
//	}
//	public void setSprints(List<Sprint> sprints) {
//		this.sprints = sprints;
//	}
//	public List<UserStory> getUserstories() {
//		return userstories;
//	}
//	public void setUserstories(List<UserStory> userstories) {
//		this.userstories = userstories;
//	}
//	public List<Category> getCategories() {
//		return categories;
//	}
//	public void setCategories(List<Category> categories) {
//		this.categories = categories;
//	}
	
//	public void addCategory(Category c) {		
//		this.categories.add(c);				
//	}
//	
//	public void deleteCategory(int CategoryID) {
//		this.categories.remove(CategoryID);		
//	}
	
	public void addUser(User u) {		
		this.users.put(u, null);		
	}
	
	public void removeUser(int userID) {
		this.users.remove(userID);		
	}
//	
//	public void setRole(int userID, Role r) {
//		this.roles.set(userID, r);
//	}
//	
//	public void deleteRole(int RoleID) {
//		this.roles.remove(RoleID);
//	}
//	
//	public void addRole(Role r) {
//		this.roles.add(r);
//	}
//	
//	public void addSprint(Sprint s) {
//		this.sprints.add(s);
//	}
//	
//	public void removeSprint(int sprintID) {
//		this.sprints.remove(sprintID);
//	}
//	
//	public void addUserStory(UserStory u) {
//		this.userstories.add(u);
//	}
//	
//	public void removeUserStory(int UserStoryID) {
//		this.userstories.remove(UserStoryID);
//	}
//	
//	@Override
//	public String toString() {
//		return "Project [projectID=" + projectID + ", name=" + name + ", description=" + description + ", roles="
//				+ roles + ", users=" + users + ", sprints=" + sprints + ", userstories=" + userstories + ", categories="
//				+ categories + "]";
//	}
}
