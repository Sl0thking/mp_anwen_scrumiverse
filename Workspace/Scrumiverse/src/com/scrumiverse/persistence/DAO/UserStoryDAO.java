package com.scrumiverse.persistence.DAO;

import java.util.List;

import com.scrumiverse.binder.ProjectBinder;
import com.scrumiverse.exception.NoUserStoriesException;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.UserStory;

public interface UserStoryDAO {
	public List<UserStory> getAllUserstories();
	public List<UserStory> getUserStoriesOfProject(int projectID);
	public void saveUserStory(UserStory userStory);
	public void updateUserStory(UserStory userStory);
	public void deleteUserStory(UserStory userStory);
	public UserStory getUserStory(int userStoryID);

}
