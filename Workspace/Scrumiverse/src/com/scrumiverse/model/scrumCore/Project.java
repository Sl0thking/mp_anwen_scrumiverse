package com.scrumiverse.model.scrumCore;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.scrumiverse.model.account.*;
import com.scrumiverse.model.scrumCore.*;
import com.scrumiverse.model.scrumFeatures.*;

@Entity
public class Project {
	
	private int projectID;
	private String projectName;
	private String projectDescription;
	//private List<Role> roles;
	//private Map<Role, User> users;
	//private List<Sprint> sprints;
	//private List<UserStory> userstories;
	//private List<Category> categories;
	
		
	@Id
	@GeneratedValue
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getName() {
		return projectName;
	}
	public void setName(String name) {
		this.projectName = name;
	}
	public String getDescription() {
		return projectDescription;
	}
	public void setDescription(String description) {
		this.projectDescription = description;
	}
//	public List<Role> getRoles() {
//		return roles;
//	}
//	public void setRoles(List<Role> roles) {
//		this.roles = roles;
//	}
//	public Map<Role, User> getUsers() {
//		return users;
//	}
//	public void setUsers(Map<Role, User> users) {
//		this.users = users;
//	}
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
//	
//	public void addUser(User u) {		
//		this.users.put(null, u);		
//	}
//	
//	public void removeUser(int userID) {
//		this.users.remove(userID);		
//	}
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
	@Override
	public String toString() {
		return "Project [projectID=" + projectID + ", name=" + projectName + ", description=" + projectDescription +  "]";
	}
}
