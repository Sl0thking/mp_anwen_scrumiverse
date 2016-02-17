package com.scrumiverse.persistence.DAO;

import java.util.List;

import com.scrumiverse.model.scrumCore.UserStory;
import com.scrumiverse.persistence.DAO.impl.NoUserStoriesException;

public interface UserStoryDAO {
	
	public void addUserStory(UserStory userstory);
	public List<UserStory> getAllUserstories();

}
