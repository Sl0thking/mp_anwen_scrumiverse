package com.scrumiverse.persistence.DAO;

import java.util.List;

import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.impl.NoProjectsFoundException;

public interface ProjectDAO {
	
	public void addProject(Project p);
	public List<Project> getAllProjects() throws NoProjectsFoundException;

}
