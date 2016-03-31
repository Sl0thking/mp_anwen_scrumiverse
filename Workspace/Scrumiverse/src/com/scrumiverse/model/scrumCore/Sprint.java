package com.scrumiverse.model.scrumCore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.json.JSONArray;
import org.json.JSONException;

import com.scrumiverse.model.scrumFeatures.ChangeEvent;
import com.scrumiverse.model.scrumFeatures.HistoryEntry;


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
	
	/**
	 * Returns a JSONArray of the ideal amount of User Stories that should be completed by day
	 * @return JSONArray
	 */
	@Transient
	public JSONArray getIdealRemainingUS() {
		
		JSONArray idealRemaining = new JSONArray();
		double idealUserStoryCount = (double)getUserStories().size();
		// Calculate Sprint Length in Days
		int wholeSprintDayCount =  (int) ((endDate.getTime()/1000/60/60/24) - (startDate.getTime()/1000/60/60/24));
		// Calculate the amount of User Stories that should be completed every day
		double decrement = idealUserStoryCount/(double)wholeSprintDayCount;
		// For every day in the sprint, decrease the amount of user stories in the sprint by the decrement and add the result to the Array
		for(int i = 0; i<=wholeSprintDayCount;i++) {			
			try {
				idealRemaining.put(idealUserStoryCount);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			idealUserStoryCount = idealUserStoryCount-decrement;
		}
		return idealRemaining;
		
	}
	
	/**
	 * Returns a JSONArray of the amount of items in the Sprint Backlog every day
	 * @return JSONArray
	 */
	@Transient
	public JSONArray getBacklogScope() {
		JSONArray backlogScope = new JSONArray();
		// Initializing Counters
		int userStoryCount = 0;
		
		SortedSet<HistoryEntry> he = this.getHistory();
		// Prepare Dates as iterator variables
		Calendar sprintEnd = Calendar.getInstance();
		sprintEnd.setTime(endDate);
		Calendar currentDay = Calendar.getInstance();
		currentDay.setTime(startDate);
		// Date formatting for date comparison
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
		// For each day since Sprint start, until sprint end, unless current Day is before sprint end
		for(Date date = currentDay.getTime(); (currentDay.getTimeInMillis()/1000/60/60/24) <= (sprintEnd.getTimeInMillis()/1000/60/60/24) && (currentDay.getTimeInMillis()/1000/60/60/24) <=  (Calendar.getInstance().getTimeInMillis()/1000/60/60/24); currentDay.add(Calendar.DATE, 1), date = currentDay.getTime()) {
			int userStoriesAssigned = 0;
			int userStoriesRemoved = 0;
			// For Each HistoryEntry
			for(HistoryEntry entry:he) {
				// Check if HistoryEntry happened on the current date 
				//and if it contains a ChangeEvent that signals that a UserStory has been added to the Sprint
				if(sdf.format(entry.getDate()).equals(sdf.format(date)) && entry.getChangeEvent() == ChangeEvent.USER_STORY_ASSIGNED) {
					userStoriesAssigned++;
					// Check if HistoryEntry happened on the current date 
					//and if it contains a ChangeEvent that signals that a UserStory has been removed from the Sprint
				} else if (sdf.format(entry.getDate()).equals(sdf.format(date)) && entry.getChangeEvent() == ChangeEvent.USER_STORY_REMOVED) {
					userStoriesRemoved++;
				}
			}
			// Put current counter value into the Array
			userStoryCount += userStoriesAssigned - userStoriesRemoved;
			backlogScope.put(userStoryCount);
		}
		return backlogScope;
	}
	/**
	 * Returns a JSONArray of the amount of done items in the Sprint every day
	 * @return JSONArray
	 */
	@Transient
	public JSONArray getDoneItems() {
		JSONArray doneItems = new JSONArray();
		// Initialize Counter
		int doneItemsCount = 0;
		SortedSet<UserStory> userstories = this.getUserStories();
		// Prepare dates as iterator variables
		Calendar sprintEnd = Calendar.getInstance();
		sprintEnd.setTime(endDate);
		Calendar currentDay = Calendar.getInstance();
		currentDay.setTime(startDate);
		// Date formatting for date comparison
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
		// For each day since Sprint start, until the end of the sprint, unless current Day is before the sprint end
		for(Date date = currentDay.getTime(); (currentDay.getTimeInMillis()/1000/60/60/24) <= (sprintEnd.getTimeInMillis()/1000/60/60/24) && (currentDay.getTimeInMillis()/1000/60/60/24) <=  (Calendar.getInstance().getTimeInMillis()/1000/60/60/24); currentDay.add(Calendar.DATE, 1), date = currentDay.getTime()) {
				// For each UserStory in the Sprint
				for(UserStory us:userstories) {
					// Prepare a Set of the User Stories HistoryEntries
					SortedSet<HistoryEntry> entries = us.getHistory();
					// For each HistoryEntry
					for(HistoryEntry he:entries) {
						// Check if HistoryEntry happened on the current date, if it contains a ChangeEvent that indicates an UserStory Update
						//and whether the its UserStory has the PlanState Done
						if(sdf.format(he.getDate()).equals(sdf.format(date)) && he.getChangeEvent() == ChangeEvent.USER_STORY_UPDATED && us.getPlanState() == PlanState.Done) {
						//If true, increase counter
							doneItemsCount++;
						}					
					}
				
			}
			// Put current counter value into the array
			doneItems.put(doneItemsCount);
		}
		return doneItems;
	}
	/**
	 * Returns a JSONArray of the amount of remaining items in the Sprint each day
	 * @return JSONArray
	 */
	@Transient
	public JSONArray getRemainingItems() {
		JSONArray remainingItems = new JSONArray();
		// Initializing counter
		int remainingItemsCount = 0;
		
		SortedSet<HistoryEntry> sprintEntries = this.getHistory();
		SortedSet<UserStory> userstories = this.getUserStories();
		// Prepare dates as iterator variables
		Calendar sprintEnd = Calendar.getInstance();
		sprintEnd.setTime(endDate);
		Calendar currentDay = Calendar.getInstance();
		currentDay.setTime(startDate);
		// Date formatting for date comparison
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
		// For each day since Sprint start, until the end of the sprint, unless the current Day is before the end of the sprint
		for (Date date = currentDay.getTime(); (currentDay.getTimeInMillis()/1000/60/60/24) <= (sprintEnd.getTimeInMillis()/1000/60/60/24) && (currentDay.getTimeInMillis()/1000/60/60/24) <= (Calendar.getInstance().getTimeInMillis()/1000/60/60/24); currentDay.add(Calendar.DATE, 1), date = currentDay.getTime()) {
			// Internal counters
			int itemsAssigned = 0, itemsRemoved = 0, itemsDone = 0;
			// For each HistoryEntry
			for (HistoryEntry he:sprintEntries) {
				//Check if HistoryEntry happened on the current date, it contains a ChangeEvent that indicates a UserStory has been removed
				//or added to the Sprint
				if(sdf.format(he.getDate()).equals(sdf.format(date)) && he.getChangeEvent() == ChangeEvent.USER_STORY_REMOVED)
					itemsRemoved++;
				else if (sdf.format(he.getDate()).equals(sdf.format(date)) && he.getChangeEvent() == ChangeEvent.USER_STORY_ASSIGNED)
					itemsAssigned++;
			}
			// For each UserStory in the Sprint
			for (UserStory us:userstories) {
				// Prepare a Set of the User Stories HistoryEntries
				SortedSet<HistoryEntry> usEntries = us.getHistory();
				for (HistoryEntry he:usEntries) {
					//If a UserStory has an update and is Done, increase the counter
					if (sdf.format(he.getDate()).equals(sdf.format(date)) && he.getChangeEvent() == ChangeEvent.USER_STORY_UPDATED && us.getPlanState() == PlanState.Done)
						itemsDone++;
				}
			}
			// Put current counter value into the array
			remainingItemsCount += itemsAssigned - itemsDone - itemsRemoved;
			remainingItems.put(remainingItemsCount);
		}
		return remainingItems;
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
		int comp = this.getStartDate().compareTo(((Sprint) o).getStartDate());
		if(comp == 0) {
			return super.compareTo(o);
		}
		return comp;
	}
}
