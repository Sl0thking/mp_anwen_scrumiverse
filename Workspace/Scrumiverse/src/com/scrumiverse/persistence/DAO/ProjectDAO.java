package com.scrumiverse.persistence.DAO;

import java.util.List;
import java.util.Map;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.impl.NoProjectFoundException;

/**
 * 
 * Interface of DAO of project objects
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 22.02.2016
 */
public interface ProjectDAO {
	
	public void saveProject(Project p);
	public void updateProject(Project p);
	public void deleteProject(Project p) throws Exception;
	public List<Project> getProjectsFromUser(int userID);
	public Project getProject(int projectID) throws NoProjectFoundException;
	public Map<User, Role> getAllUsers(int projectID);



}
