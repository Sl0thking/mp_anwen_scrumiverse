package com.scrumiverse.model.scrumCore;

import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;


/**
 * Datamodel of Sprints
 * @author Toni Serfling
 * @version 23.02.2016
 */
@Entity
public class Sprint extends PlanElement {

	private SortedSet<UserStory> userStories;
	private Date startDate;
	private Date endDate;
	
	public Sprint(){
		userStories = new TreeSet<UserStory>();
		startDate = new Date();
		endDate = new Date();
		setDescription("new Sprint");
		setHistory(new TreeSet());
		setPlanState(PlanState.Planning);
		setAcceptanceCriteria("");
	}
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name="SprintID", nullable=true)
	@Sort(type=SortType.NATURAL)
	public SortedSet<UserStory> getUserStories() {
		return userStories;
	}
	public void setUserStories(SortedSet<UserStory> userStories) {
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
		for(UserStory userStory : userStories) {
			if(userStory.getId() == usID) {
				return userStory;
			}
		}
		return null;
	}
	
	/**
	 * Returns the combined Effort needed for all Userstories in the sprint
	 * @return int combinedEffort
	 */
	@Transient
	public int getCombinedEffort() {
		int combinedEffort = 0;
		for(UserStory us:userStories) {
			combinedEffort += us.getEffortValue();
		}
		return combinedEffort;
		
	}
	/**
	 * Returns the effort of all finished Userstories in the sprint
	 * @return int completedEffort
	 */
	@Transient
	public int getCompletedEffort() {
		int completedEffort = 0;
		for(UserStory us:userStories) {
			if(us.getPlanState().equals(PlanState.Done))
			completedEffort =+ us.getEffortValue();
		}		
		return completedEffort;
	}
	
	/**
	 * Returns the combined Businessvalue of all Userstories in the sprint
	 * @return int combinedBusinessValue
	 */
	@Transient
	public int getCombinedBusinessValue() {
		int combinedBusinessValue = 0;
		for(UserStory us:userStories) {
			combinedBusinessValue += us.getBusinessValue();
		}
		return combinedBusinessValue;
		
	}
	/**
	 * Returns the Businessvalue of all finished Userstories in the sprint
	 * @return int completedBusinessValue
	 */
	@Transient
	public int getCompletedBusinessValue() {
		int completedBusinessValue = 0;
		for(UserStory us:userStories) {
			if(us.getPlanState().equals(PlanState.Done))
			completedBusinessValue =+ us.getBusinessValue();
		}		
		return completedBusinessValue;
	}
	
	/**
	 * Returns the number of finished User Stories 
	 * @return int finishedUserStories
	 */
	@Transient
	public int getFinishedUserStories() {
		int finishedUserStories = 0;
		for(UserStory us:userStories) {
			if(us.getPlanState() == PlanState.Done)
				finishedUserStories++;
		}	
		return finishedUserStories;	
	}
	
	/**
	 * Returns all User Stories of the given PlanState
	 * @param plan
	 * @return List<UserStory> userStoriesByState;
	 */
	public SortedSet<UserStory> getUserStoriesByState(PlanState plan) {
		SortedSet<UserStory> userStoriesByState = new TreeSet<UserStory>();
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
	
	@Override
	public int compareTo(PlanElement o) {
		return this.getStartDate().compareTo(((Sprint) o).getStartDate());
	}
}
