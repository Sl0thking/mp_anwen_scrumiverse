package com.scrumiverse.binder;

import java.beans.PropertyEditorSupport;

import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.impl.ProjectDAOImpl;

public class ProjectBinder extends PropertyEditorSupport{
	
	public void setAsText(String id) {
		Project p = ProjectDAOImpl.getInstance().getProject(Integer.parseInt(id));
		setValue(p);
	}
	
	public String getAsText() {
		Object o = getValue();
		if(o == null) {
			return null;
		}
		else
			return Integer.toString(((Project)o).getProjectID());
		
	}
}
