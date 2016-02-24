package com.scrumiverse.model.scrumCore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


/**
 * Datamodel of Sprints
 * @author Toni Serfling
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
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
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
	
	/**
	 * Returns the number of finished User Stories 
	 * @return int finishedUserStories
	 */
	@Transient
	public int getFinishedUserStories() {
		
		int finishedUserStories = 0;

		for(UserStory us:userStories) {
			if(us.getRemainingMinutes() == 0);
			finishedUserStories++;
		}	
		return finishedUserStories;	
	}
	
	/**
	 * Returns all User Stories of the given PlanState
	 * @param plan
	 * @return List<UserStory> userStoriesByState;
	 */
	public List<UserStory> getUserStoriesByState(PlanState plan) {
		List<UserStory> userStoriesByState = new ArrayList<UserStory>();
		for(UserStory us:userStories){
			if(us.getPlanState()== plan) {
				userStoriesByState.add(us);
			}
		}		
		return userStoriesByState;		
	}
	
	/**
	 * Returns the planned time of the sprint in minutes
	 * @return int plannedMinutes
	 */
	@Transient
	public int getPlannedMinutes() {
		int plannedMinutes = 0;		
		for(UserStory us:userStories) {
			plannedMinutes += us.getPlannedMinutes();
		}		
		return plannedMinutes;
	}
	
	/**
	 * Returns the already spent time of the sprint in minutes
	 * @return int workedMinutes
	 */
	@Transient
	public int getWorkedMinutes() {
		int workedMinutes = 0;
		for(UserStory us:userStories) {
			workedMinutes += us.getWorkedMinutes();
		}
		
		return workedMinutes;
	}
	
	/**
	 * Returns the remaining time of the sprint in minutes
	 * @return int remainingMinutes
	 */
	@Transient
	public int getRemainingMinutes() {
		int remainingMinutes = 0;
		for(UserStory us:userStories) {
			remainingMinutes += us.getRemainingMinutes();
		}
		return remainingMinutes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sprint other = (Sprint) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}
	
}
