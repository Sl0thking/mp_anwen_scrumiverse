package com.scrumiverse.model.scrumCore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.model.scrumFeatures.MoscowState;
import com.scrumiverse.model.scrumFeatures.WorkLog;

/**
 * User Story Model for Scrum Projects.
 * 
 * @author Lasse Jacobs
 * @version 18.02.16
 *
 */

@Entity
public class UserStory extends PlanElement {
	private int businessValue;
	private int effortValue;
	private MoscowState moscow;
	private Category category;
	private List<Task> tasks;
	private Date dueDate;
//	private Sprint relatedSprint;
	
	public UserStory(){
		businessValue = 0;
		effortValue = 0;
		moscow = MoscowState.Wont;
		tasks = new ArrayList<Task>();
		//Standartwert?!
		category = null;
		dueDate = null;
//		relatedSprint = null;
	}

	public int getBusinessValue() {
		return businessValue;
	}

	public void setBusinessValue(int businessValue) {
		this.businessValue = businessValue;
	}

	public int getEffortValue() {
		return effortValue;
	}

	public void setEffortValue(int effortValue) {
		this.effortValue = effortValue;
	}

	public MoscowState getMoscow() {
		return moscow;
	}

	public void setMoscow(MoscowState moscow) {
		this.moscow = moscow;
	}
	@OneToOne
	@JoinColumn(name="category_ID")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@OneToMany
	@JoinColumn(name="UserStoryID")
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
//sprint not implemented
//	@OneToOne
//	@JoinColumn(name="Sprint_ID")
//	public Sprint getRelatedSprint() {
//		return relatedSprint;
//	}
//
//	public void setRelatedSprint(Sprint relatedSprint) {
//		this.relatedSprint = relatedSprint;
//	}
	public List<User> getResponsibleUsers(){
		List userlist = new ArrayList<User>();
		for(Task task: tasks){
			for(User user: task.getResponsibleUsers()){
				if(!userlist.contains(user)){
					userlist.add(user);
				}
			}
		}
		return userlist;
	}
	public int getPlannedMinutes(){
		int result = 0;
		for(Task task: tasks){
			for(User user:task.getResponsibleUsers()){
				result += task.getEstimatedWorkTimeOfUser(user);
			}
//			result += task.getAllPlannedTime();
		}
		return result;
	}
	public int getRemainingMinutes(){
		int result = 0;
		for(Task task: tasks){
			for(User user: task.getResponsibleUsers()){
				result += task.getRemainingMinOfUser(user);
			}
//			result += task.getAllRemainingMin();
		}
		return result;
	}
	public int getWorkedMinutes(){
		int result = 0;
		for(Task task: tasks){
			for(User user: task.getResponsibleUsers()){
				result += task.getLoggedTimeOfUser(user);
			}
//			result += task.getAllWorkedMin();
		}
		return result;
	}
//needs sorting
	public List<WorkLog> getWorkLogs(){
		List result = new ArrayList();
		for(Task task: tasks){
//			for(WorkLog work: task.getAllWorkLogs()){
			for(WorkLog work: task.getWorkLogs()){

				result.add(work);
			}
		}
		return (List<WorkLog>) result;
	}
	

}
