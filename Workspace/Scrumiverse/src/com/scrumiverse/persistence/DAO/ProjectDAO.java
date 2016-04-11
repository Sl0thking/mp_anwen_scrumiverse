package com.scrumiverse.persistence.DAO;

import java.util.Set;

import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.model.scrumCore.Project;

/**
 * 
 * Interface of DAO of project objects
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 23.02.2016
 */
public interface ProjectDAO {
	public void saveProject(Project p);
	public void updateProject(Project p);
	public void deleteProject(Project p) throws Exception;
	public Set<Project> getProjectsFromUser(int userID) throws ProjectPersistenceException;
	public Project getProject(int projectID) throws ProjectPersistenceException;
}