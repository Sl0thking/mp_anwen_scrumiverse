package com.scrumiverse.model.scrumCore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.model.scrumFeatures.MoscowState;
import com.scrumiverse.model.scrumFeatures.WorkLog;

/**
 * User Story Model for Scrum Projects.
 * 
 * @author Lasse Jacobs
 * @version 24.02.16
 *
 */
@Entity
public class UserStory extends PlanElement {
	private int businessValue;
	private int effortValue;
	private MoscowState moscow;
//	private Category category;
	private List<Task> tasks;
	private Date dueDate;
	private Sprint relatedSprint;
	
//	public UserStory(){
//		businessValue = 0;
//		effortValue = 0;
//		moscow = MoscowState.Wont;
//		tasks = new ArrayList<Task>();
//		//Standartwert?!
////		category = null;
//		dueDate = new Date();
//		relatedSprint = null;
//		
//	}
	
	public UserStory(){
		Random rand = new Random();
		//((max - min) + 1) + min
		businessValue = rand.nextInt((100 - 0) + 1) + 0;
		effortValue = rand.nextInt((100 - 0) + 1) + 0;
		switch(rand.nextInt((3 - 0) + 1) + 0){
		case 0:
			moscow = MoscowState.Could;
			break;
		case 1:
			moscow = MoscowState.Should;
			break;
		case 2:
			moscow = MoscowState.Must;
			break;
			
		default:
			moscow = MoscowState.Wont;
		}
		tasks = new ArrayList<Task>();
//		category = new Category();
//		category.setName("Tonis Feeding Category");
		dueDate = new Date();
		dueDate.setTime(dueDate.getTime() + (rand.nextInt((20 - 0) + 1)*(1000*60*60*24)));
		relatedSprint = null;
		//planelement
		setDescription("[US"+rand.nextInt((100 - 0) + 1)+"] Killing Humanity");
		switch(rand.nextInt((2 - 0) + 1) + 0){
		case 0:
			setPlanState(PlanState.Planning);
			break;
		case 1:
			setPlanState(PlanState.Done);
			break;
		default:
			setPlanState(PlanState.InProgress);
		}
		setAcceptanceCriteria("Everyone dead");
		//history
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
//	@OneToOne
//	@JoinColumn(name="category_ID")
//	public Category getCategory() {
//		return category;
//	}
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
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

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "SprintID")
	public Sprint getRelatedSprint() {
		return relatedSprint;
	}

	public void setRelatedSprint(Sprint relatedSprint) {
		this.relatedSprint = relatedSprint;
	}
	/**
	 * Returns Users working on Tasks in this UserStory
	 * @return List Users from this UserStory
	 */
	@Transient
	public List<User> getResponsibleUsers(){
		List<User> userlist = new ArrayList<User>();
		for(Task task: getTasks()){
			for(User user: task.getResponsibleUsers()){
				if(!userlist.contains(user)){
					userlist.add(user);
				}
			}
		}
		return userlist;
	}
	/**
	 * Returns all planned minutes from tasks in this UseStory
	 * @return Number of planned minutes
	 */
	@Transient
	public int getPlannedMinutes(){
		int result = 0;
		for(Task task: tasks){
			for(User user:task.getResponsibleUsers()){
				result += task.getPlannedMinOfUser(user);
			}
		}
		return result;
	}
	/**
	 * Returns all remaining minutes from tasks in this UseStory
	 * @return Number of remaining minutes
	 */
	@Transient
	public int getRemainingMinutes(){
		int result = 0;
		for(Task task: tasks){
			for(User user: task.getResponsibleUsers()){
				result += task.getRemainingMinOfUser(user);
			}
		}
		return result;
	}
	/**
	 * Returns all logged minutes of work from tasks in this UseStory
	 * @return Number of worked minutes
	 */
	@Transient
	public int getWorkedMinutes(){
		int result = 0;
		for(Task task: tasks){
			for(User user: task.getResponsibleUsers()){
				result += task.getWorkTimeOfUser(user);
			}
		}
		return result;
	}
	/**
	 * Adds a Task to this UserStory
	 * @param task the task to add
	 */
	public void addTask(Task task){
		tasks.add(task);
	}
	
	/**
	 * Returns a list of work logs from all tasks in this UserStory
	 * @return List of work logs sorted by time
	 */
	// needs sorting
	@Transient
	public List<WorkLog> getWorkLogs(){
		List<WorkLog> result = new ArrayList<WorkLog>();
		for(Task task: tasks){
			for(WorkLog work: task.getWorkLogs()){

				result.add(work);
			}
		}
		return (List<WorkLog>) result;
	}
	/**
	 * 
	 * @return
	 */
	@Transient
	public String getRemainingDays(){
		Date today = new Date();
		int result = 0;
		//Nullpinter abfangen
		try{
			result = (int) ((dueDate.getTime() - today.getTime())/(1000*60*60*24));
		}catch (NullPointerException e){
		}
		return result+"";
	}
	
	@Override
	public String toString(){
		return "UserStory "+getId()+" - Description: "+getDescription();
	}

}
