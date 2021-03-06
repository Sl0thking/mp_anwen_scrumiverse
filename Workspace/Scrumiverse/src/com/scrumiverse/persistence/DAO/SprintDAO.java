package com.scrumiverse.persistence.DAO;

import java.util.Set;

import com.scrumiverse.exception.SprintPersistenceException;
import com.scrumiverse.model.scrumCore.Sprint;

/** 
 * DAO Interface for Sprints
 * @author DoctorWhose
 * @version 23.02.2016
 */
public interface SprintDAO {
	public void saveSprint(Sprint s);
	public Set<Sprint> getSprintsFromProject(int projectID) throws SprintPersistenceException;
	void updateSprint(Sprint sprint);
	void deleteSprint(Sprint sprint);
	Sprint getSprint(int sprintID) throws SprintPersistenceException;
}
