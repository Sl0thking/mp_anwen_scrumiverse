package com.scrumiverse.persistence.DAO;

import java.util.List;
import java.util.SortedSet;

import com.scrumiverse.exception.TaskPersistenceException;
import com.scrumiverse.model.scrumCore.Task;

/**
 * Simple Interface of task persistence functions
 * 
 * @author Kevin Jolitz
 * @version 11.04.2016
 *
 */
public interface TaskDAO {
	public void saveTask(Task task);
	public void updateTask(Task task);
	public List<Task> getAllTasks();
	public Task getTask(int taskID) throws TaskPersistenceException;
	public void deleteTask(Task task);
	SortedSet<Task> getTasksOfUserStory(int userStoryID) throws TaskPersistenceException;
}