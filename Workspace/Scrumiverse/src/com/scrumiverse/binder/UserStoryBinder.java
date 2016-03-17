package com.scrumiverse.binder;

import java.beans.PropertyEditorSupport;

import com.scrumiverse.exception.NoUserStoryFoundException;
import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.UserStoryDAO;

public class UserStoryBinder extends PropertyEditorSupport {
	
	UserStoryDAO userstoryDAO;
	
	public UserStoryBinder(UserStoryDAO userstoryDAO){
		this.userstoryDAO = userstoryDAO;
	}
	
	@Override
	public String getAsText(){
		Object o = getValue();
		if(o == null){
			return null;
		} else {
			return Integer.toString(((UserStory) o).getId());
		}
	}
	
	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
		try{
			UserStory userstory = userstoryDAO.getUserStory(Integer.parseInt(arg0));
			setValue(userstory);
		} catch (NoUserStoryFoundException e) {
			e.printStackTrace();
		}
	}
}
