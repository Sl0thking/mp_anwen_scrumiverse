package com.scrumiverse.model.scrumCore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Category;
import com.scrumiverse.model.scrumFeatures.MoscowState;
import com.scrumiverse.model.scrumFeatures.WorkLog;

@Entity
public class UserStory extends PlanElement {
	private int businessValue;
	private int effortValue;
	//private MoscowState moscow;
	//private Category category;
	//private List<Task> tasks;
	private Date dueDate;
	//private Sprint relatedSprint;
	
	public UserStory(){
		businessValue = 0;
		effortValue = 0;
//		moscow = MoscowState.Wont;
//		tasks = new ArrayList<Task>();
		//Standartwert?!
//		category = null;
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

//	public MoscowState getMoscow() {
//		return moscow;
//	}
//
//	public void setMoscow(MoscowState moscow) {
//		this.moscow = moscow;
//	}
//
//	public Category getCategory() {
//		return category;
//	}
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}
//
//	public List<Task> getTasks() {
//		return tasks;
//	}
//
//	public void setTasks(List<Task> tasks) {
//		this.tasks = tasks;
//	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

//	public Sprint getRelatedSprint() {
//		return relatedSprint;
//	}
//
//	public void setRelatedSprint(Sprint relatedSprint) {
//		this.relatedSprint = relatedSprint;
//	}
	
//	public List<User> getResponsibleUsers(){
//		List userlist = new ArrayList<User>();
//		
//		//
//		Map m = new HashMap<String, Integer>();
//		m.put("blub", 4);
//		m.put("toni", 3);
//		Object[] s = m.keySet().toArray();
//		for(int i=0;i<s.length;i++){
//			System.out.println(s[i]);
//		}
//		System.out.println(m.keySet());
//		
//		//
////		for(Task task: tasks){
////		}
//		
//		return userlist;
//	}
//	public int getPlannedMinutes(){
//		return 0;
//	}
//	public int getRemainingMinutes(){
//		return 0;
//	}
//	public int getWorkedMinutes(){
//		return 0;
//	}
//	public List<WorkLog> getWorkLogs(){
//		return new ArrayList<WorkLog>();
//	}
	

}
