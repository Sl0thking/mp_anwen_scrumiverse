package com.scrumiverse.model.scrumCore;

import java.util.List;
import java.util.Map;

import com.scrumiverse.model.account.*;
import com.scrumiverse.model.scrumCore.*;
import com.scrumiverse.model.scrumFeatures.*;

public class Project {
	
	private int projectID;
	private String name;
	private String description;
	private List<Role> roles;
	private Map<Role, User> users;
	private List<Sprint> sprints;
	private List<UserStory> userstories;
	private List<Category> categories;
	
	public Project(int projectID, String name, String description, List<Role> roles, Map<Role, User> users,
			List<Sprint> sprints, List<UserStory> userstories, List<Category> categories) {
		super();
		this.projectID = projectID;
		this.name = name;
		this.description = description;
		this.roles = roles;
		this.users = users;
		this.sprints = sprints;
		this.userstories = userstories;
		this.categories = categories;
	}
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
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Map<Role, User> getUsers() {
		return users;
	}
	public void setUsers(Map<Role, User> users) {
		this.users = users;
	}
	public List<Sprint> getSprints() {
		return sprints;
	}
	public void setSprints(List<Sprint> sprints) {
		this.sprints = sprints;
	}
	public List<UserStory> getUserstories() {
		return userstories;
	}
	public void setUserstories(List<UserStory> userstories) {
		this.userstories = userstories;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public void addCategory(Category c) {		
		categories.add(c);				
	}
	
	public void deleteCategory(int CategoryID) {		
		categories.remove(CategoryID);		
	}
	
	public void addUser(User u) {		
		users.put(null, u);		
	}
	
	public void removeUser(int userID) {
		users.remove(userID);		
	}
	
	public void setRole(int userID, Role r) {
		roles.set(userID, r);
	}
	
	public void deleteRole(int RoleID) {
		roles.remove(RoleID);
	}
	
	public void addRole(Role r) {
		roles.add(r);
	}
	
	public void addSprint(Sprint s) {
		sprints.add(s);
	}
	
	public void removeSprint(int sprintID) {
		sprints.remove(sprintID);
	}
	
	public void addUserStory(UserStory u) {
		userstories.add(u);
	}
	
	public void removeUserStory(int UserStoryID) {
		userstories.remove(UserStoryID);
	}
}
