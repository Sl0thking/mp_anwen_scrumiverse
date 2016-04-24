package com.scrumiverse.binder;

import java.beans.PropertyEditorSupport;

import com.scrumiverse.exception.SprintPersistenceException;
import com.scrumiverse.model.scrumCore.Sprint;
import com.scrumiverse.persistence.DAO.SprintDAO;

/**
 * Binder class for Sprints
 * 
 * @author Lasse Jacobs
 * @version 08.04.2016
 */
public class SprintBinder extends PropertyEditorSupport {
	
	SprintDAO sprintDAO;
	
	public SprintBinder(SprintDAO sprintDAO){
		this.sprintDAO = sprintDAO;
	}
	
	@Override
	public String getAsText(){
		Object o = getValue();
		if(o == null){
			return null;
		} else {
			return Integer.toString(((Sprint) o).getId());
		}
	}
	
	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		int sprintID = Integer.parseInt(arg0);
		try {
			if (sprintID == 0){
				setValue(null);
			}else {
				setValue(sprintDAO.getSprint(Integer.parseInt(arg0)));
			}
		} catch (SprintPersistenceException e) {
			e.printStackTrace();
		}
	}
}