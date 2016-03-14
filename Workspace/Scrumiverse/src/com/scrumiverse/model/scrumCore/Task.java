package com.scrumiverse.model.scrumCore;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.MapKeyManyToMany;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.WorkLog;

/**
 * Task data-model of a scrum implementation.
 * 
 * @author Kevin Jolitz
 * @version 27.02.2016
 *
 */
@Entity
public class Task extends PlanElement {
	private SortedSet<WorkLog> workLogs;
	private Map<User, Integer> plannedMinOfUsers;
	private SortedSet<String> tags;
	
	public Task() {
		this.setDescription("New Task");
		this.setPlanState(PlanState.Planning);
		plannedMinOfUsers = new HashMap<User, Integer>();
		tags = new TreeSet<String>();
		workLogs = new TreeSet<WorkLog>();
	}
	
	public int getPlannedMinOfUser(User user) {
		return plannedMinOfUsers.get(user);
	}
	
	@Transient
	public int getPlannedMin() {
		int planTime = 0;
		for(User user : plannedMinOfUsers.keySet()) {
			planTime += plannedMinOfUsers.get(user);
		}
		return planTime;
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "TaskID")
	@Sort(type=SortType.NATURAL)
	public SortedSet<WorkLog> getWorkLogs() {
		return workLogs;
	}

	public void setWorkLogs(SortedSet<WorkLog> workLogs) {
		this.workLogs = workLogs;
	}

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name="est_user_work_time", joinColumns = @JoinColumn(name = "TaskID"))
	@Column(name = "WorkTimeInMin")
	@MapKeyManyToMany(targetEntity = User.class, joinColumns=@JoinColumn(name="UserID"))
	public Map<User, Integer> getPlannedMinOfUsers() {
		return plannedMinOfUsers;
	}

	public void setPlannedMinOfUsers(Map<User, Integer> plannedMinOfUsers) {
		this.plannedMinOfUsers = plannedMinOfUsers;
	}

	@CollectionOfElements(fetch = FetchType.EAGER, targetElement = String.class)
	@JoinTable(name = "Task_Tags", joinColumns = @JoinColumn(name = "TaskID"))
	@Column(name = "Tag", nullable=false)
	@Sort(type=SortType.NATURAL)
	public SortedSet<String> getTags() {
		return tags;
	}

	public void setTags(SortedSet<String> tags) {
		this.tags = tags;
	}

	public void setPlannedTimeOfUser(User user, int planTimeInMin) {
		this.plannedMinOfUsers.put(user, planTimeInMin);
	}
	
	/**
	 * Log work progress on this task
	 * @param log new work log entry
	 */
	public void logWork(WorkLog log) {
		this.workLogs.add(log);
	}
	
	/**
	 * Returns work logs of a specific user
	 * @param user related user
	 * @return List of work logs from related user
	 */
	public SortedSet<WorkLog> getWorkLogsOfUser(User user) {
		SortedSet<WorkLog> workLogsOfUser = new TreeSet<WorkLog>();
		for(WorkLog log : this.workLogs) {
			if(log.getUser().equals(user)) {
				workLogsOfUser.add(log);
			}
		}
		return workLogsOfUser;
	}
	
	/**
	 * Delete a specific worklog entry
	 * @param log
	 */
	public void delWorkLog(WorkLog log) {
		this.workLogs.remove(log);
	}
	
	/**
	 * Return list of responsible users for this task
	 * @return List of users
	 */
	@Transient
	public Set<User> getResponsibleUsers() {
		return this.plannedMinOfUsers.keySet();
	}
	
	/**
	 * Returns logged work time of a specific user
	 * @param user related user
	 * @return logged work time of related user in minutes
	 */
	public int getWorkTimeOfUser(User user) {
		int workTimeInMin = 0;
		SortedSet<WorkLog> workLogs = this.getWorkLogsOfUser(user);
		for(WorkLog log : workLogs) {
			workTimeInMin += log.getLoggedMinutes();
		}
		return workTimeInMin;
	}
	
	@Transient
	public int getWorkMin() {
		int workTime = 0;
		for(User user : this.getResponsibleUsers()) {
			workTime += this.getWorkTimeOfUser(user);
		}
		return workTime;
	}
	
	/**
	 * Get remaining time for this task of related user
	 * @param user related user
	 * @return remaining time in minutes
	 */
	public int getRemainingMinOfUser(User user) {
		int estimatedTimeInMin = this.getPlannedMinOfUser(user);
		int loggedTimeInMin = this.getWorkTimeOfUser(user);
		if(estimatedTimeInMin - loggedTimeInMin < 0) {
			return 0;
		}
		return estimatedTimeInMin - loggedTimeInMin;
	}
	
	@Transient
	public int getRemainingMin() {
		int estimatedTimeInMin = this.getPlannedMin();
		int loggedTimeInMin = this.getWorkMin();
		if(estimatedTimeInMin - loggedTimeInMin < 0) {
			return 0;
		}
		return estimatedTimeInMin - loggedTimeInMin;
	}

//	@Override
//	public int compareTo(PlanElement o) {
//		return new Integer(getId()).compareTo(new Integer(o.getId()));
//	}
	
	@Override
	public String toString() {
		return "Task [tags=" + tags + ", getId()=" + getId() + "]";
	}

	/**
	 * Add a new search tag to this task
	 * @param tag new tag
	 */
	public void addTag(String tag) {
		this.tags.add(tag);
	}
}