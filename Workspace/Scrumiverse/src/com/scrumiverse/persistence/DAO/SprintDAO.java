package com.scrumiverse.persistence.DAO;

import java.util.List;

import com.scrumiverse.model.scrumCore.Sprint;

/** 
 * DAO Interface for Sprints
 * @author DoctorWhose
 * @version 23.02.2016
 */

public interface SprintDAO {
	public void saveSprint(Sprint s);
	public List<Sprint> getSprintsFromProject(int projectID);
}
