package com.scrumiverse.persistence.DAO;

import java.util.List;
import java.util.Set;

import com.scrumiverse.model.scrumCore.UserStory;

/**
 * Interface for user story persistence classes
 * 
 * @author Kevin Jolitz
 * @version 27.02.2016
 *
 */
public interface UserStoryDAO {
	public List<UserStory> getAllUserstories();
	public Set<UserStory> getUserStoriesOfProject(int projectID);
	public void saveUserStory(UserStory userStory);
	public void updateUserStory(UserStory userStory);
	public void deleteUserStory(UserStory userStory);
	public UserStory getUserStory(int userStoryID);

}
