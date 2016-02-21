package com.scrumiverse.persistence.DAO;

import java.util.List;

import com.scrumiverse.model.scrumCore.Task;

public interface TaskDAO {
	
	public void addTask(Task task);
	public void updateTask(Task task);
	public List<Task> getAllTasks();

}
