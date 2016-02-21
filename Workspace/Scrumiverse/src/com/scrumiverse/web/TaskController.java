package com.scrumiverse.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scrumiverse.model.account.Right;
import com.scrumiverse.model.account.Role;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumCore.Task;
import com.scrumiverse.persistence.DAO.ProjectDAO;
import com.scrumiverse.persistence.DAO.RoleDAO;
import com.scrumiverse.persistence.DAO.TaskDAO;
import com.scrumiverse.persistence.DAO.UserDAO;
import com.scrumiverse.persistence.DAO.impl.UserDAOImpl;

@Controller
public class TaskController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	TaskDAO taskDAO;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	@RequestMapping("/taskTest.htm")
	public void taskTest() {
		Task task = new Task();
		Task task2 = new Task();
		task.addTag("USER");
		task.addTag("USER2");
		
		User user = new User();
		userDAO.addUser(user);
		Project p = new Project();
		Role r = new Role();
		r.addRight(Right.Create_Sprint);
		roleDAO.addRole(r);
		//p.addUser(user, r);
		user.addProject(p);
		projectDAO.addProject(p);
		userDAO.addUser(user);
		taskDAO.updateTask(task);
		taskDAO.updateTask(task2);
		task.setEstimatedWorkTimeOfUser(user, 60);
		task2.setEstimatedWorkTimeOfUser(user, 22);
		taskDAO.updateTask(task);
		taskDAO.updateTask(task2);
	}
	
	@RequestMapping("/taskTest2.htm")
	public void taskTest2() {
		List<User> getUsers = userDAO.getAllUsers();
		for(User user : getUsers) {
			System.out.println(user.getEmail() + " " + user.getProjects().get(0).getName());
		}
		List<Task> tasks = taskDAO.getAllTasks();
		for(Task t : tasks) {
			System.out.println(t);
		}
	}
}
