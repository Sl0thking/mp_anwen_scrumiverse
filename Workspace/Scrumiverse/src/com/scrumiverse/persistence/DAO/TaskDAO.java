package com.scrumiverse.persistence.DAO;

import java.util.List;

import com.scrumiverse.model.scrumCore.Task;

/**
 * Simple Interface of task persistence functions
 * 
 * @author Kevin Jolitz
 * @version 21.02.2016
 *
 */
public interface TaskDAO {
	public void saveTask(Task task);
	public void updateTask(Task task);
	public List<Task> getAllTasks();
}