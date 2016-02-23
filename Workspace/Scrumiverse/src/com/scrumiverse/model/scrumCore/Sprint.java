package com.scrumiverse.model.scrumCore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


/**
 * Datamodel of Sprints
 * @author DoctorWhose
 * @version 23.02.2016
 */
@Entity
public class Sprint extends PlanElement {

	private List<UserStory> userStories;
	private Date startDate;
	private Date endDate;
	
	public Sprint(){
		userStories = new ArrayList<UserStory>();
		startDate = null;
		endDate = null;
	}
	
	@OneToMany
	@JoinColumn(name="UserStoryID")
	public List<UserStory> getUserStories() {
		return userStories;
	}
	public void setUserStories(List<UserStory> userStories) {
		this.userStories = userStories;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void addUserStory(UserStory userStory) {
		this.userStories.add(userStory);
	}
	
	public void removeUserStory(UserStory userStory) {
		this.userStories.remove(userStory);
	}
	
	public UserStory getUserStory(int usID) {
		return userStories.get(usID);
	}
	
	@Transient
	public int getFinishedUserStories() {
		
		int finishedUserStories = 0;

		for(UserStory us:userStories) {
			if(us.getRemainingMinutes() == 0);
			finishedUserStories++;
		}	
		return finishedUserStories;	
	}
	
	public UserStory getUserStoriesByState(PlanState plan) {
			
		return null;		
	}
	
	@Transient
	public int getPlannedMinutes() {
		int plannedMinutes = 0;		
		for(UserStory us:userStories) {
			plannedMinutes += us.getPlannedMinutes();
		}		
		return plannedMinutes;
	}
	
	@Transient
	public int getWorkedMinutes() {
		int workedMinutes = 0;
		for(UserStory us:userStories) {
			workedMinutes += us.getWorkedMinutes();
		}
		
		return workedMinutes;
	}
	
	@Transient
	public int getRemainingMinutes() {
		int remainingMinutes = 0;
		for(UserStory us:userStories) {
			remainingMinutes += us.getRemainingMinutes();
		}
		return remainingMinutes;
	}
	
}
