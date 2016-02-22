package com.scrumiverse.persistence.DAO;

import java.util.List;
import java.util.Map;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.impl.ProjectDAOImpl;

/**
 * 
 * Interface of DAO of project objects
 * 
 * @author Toni Serfling
 * @version 22.02.2016
 */

public interface ProjectDAO {
	
	public void addProject(Project p);
	public List<Project> getProjectsFromUser(int userID);
	public Project getProject(int projectID);
	public void addUser(Project p, User u);
	public Map<User, Role> getAllUsers(int projectID);
	public void removeUser(Project p, int userID);
	public void removeProject(Project p);
	public void renameProject(Project p, String name);
	public void updateProject(Project p);

}
