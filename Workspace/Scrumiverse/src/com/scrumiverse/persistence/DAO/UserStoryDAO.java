package com.scrumiverse.persistence.DAO;

import java.util.List;
import java.util.SortedSet;

import com.scrumiverse.exception.ProjectPersistenceException;
import com.scrumiverse.exception.UserStoryPersistenceException;
import com.scrumiverse.model.scrumCore.UserStory;

/**
 * Interface for user story persistence classes
 * 
 * @author Kevin Jolitz
 * @version 12.04.2016
 *
 */
public interface UserStoryDAO {
	public List<UserStory> getAllUserstories();
	public SortedSet<UserStory> getUserStoriesOfProject(int projectID) throws ProjectPersistenceException;
	public void saveUserStory(UserStory userStory);
	public void updateUserStory(UserStory userStory);
	public void deleteUserStory(UserStory userStory);
	public UserStory getUserStory(int userStoryID) throws UserStoryPersistenceException;

}
