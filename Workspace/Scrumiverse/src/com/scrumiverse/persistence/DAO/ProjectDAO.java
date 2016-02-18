package com.scrumiverse.persistence.DAO;

import java.util.List;
import java.util.Map;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.scrumCore.Project;

public interface ProjectDAO {
	
	public void addProject(Project p);
	public List<Project> getAllProjects();
	public Project getProject(int projectID);
	public void addUser(Project p, User u);
	public Map<User, Role> getAllUsers(int projectID);

}
