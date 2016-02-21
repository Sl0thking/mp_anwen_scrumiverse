package com.scrumiverse.model.scrumCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.MapKeyManyToMany;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.WorkLog;

/**
 * Task data-model of a scrum implementation.
 * 
 * @author Kevin Jolitz
 * @version 21.02.2016
 *
 */
@Entity
public class Task extends PlanElement {
	private List<WorkLog> workLogs;
	private Map<User, Integer> estimatedWorkMinOfUsers;
	private List<String> tags;
	
	public Task() {
		this.setDescription("New Task");
		this.setPlanState(PlanState.Planning);
		estimatedWorkMinOfUsers = new HashMap<User, Integer>();
		tags = new ArrayList<String>();
	}
	
	public int getEstimatedWorkTimeOfUser(User user) {
		return estimatedWorkMinOfUsers.get(user);
	}
	
	@OneToMany
	@JoinColumn(name = "TaskID")
	public List<WorkLog> getWorkLogs() {
		return workLogs;
	}

	public void setWorkLogs(List<WorkLog> workLogs) {
		this.workLogs = workLogs;
	}

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name="est_user_work_time", joinColumns = @JoinColumn(name = "TaskID"))
	@Column(name = "WorkTimeInMin")
	@MapKeyManyToMany(targetEntity = User.class, joinColumns=@JoinColumn(name="UserID"))
	public Map<User, Integer> getEstimatedWorkMinOfUsers() {
		return estimatedWorkMinOfUsers;
	}

	public void setEstimatedWorkMinOfUsers(Map<User, Integer> estimatedWorkMinOfUsers) {
		this.estimatedWorkMinOfUsers = estimatedWorkMinOfUsers;
	}

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "Task_Tags", joinColumns = @JoinColumn(name = "TaskID"))
	@Column(name = "Tag")
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setEstimatedWorkTimeOfUser(User user, int workTimeInMin) {
		this.estimatedWorkMinOfUsers.put(user, workTimeInMin);
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
	public List<WorkLog> getWorkLogsOfUser(User user) {
		List<WorkLog> workLogsOfUser = new ArrayList<WorkLog>();
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
	public Set<User> getResponsibleUser() {
		return this.estimatedWorkMinOfUsers.keySet();
	}
	
	/**
	 * Returns logged work time of a specific user
	 * @param user related user
	 * @return logged work time of related user in minutes
	 */
	public int getLoggedTimeOfUser(User user) {
		int loggedTimeInMin = 0;
		List<WorkLog> workLogs = this.getWorkLogsOfUser(user);
		for(WorkLog log : workLogs) {
			loggedTimeInMin += log.getLoggedMinutes();
		}
		return loggedTimeInMin;
	}
	
	/**
	 * Get remaining time for this task of related user
	 * @param user related user
	 * @return remaining time in minutes
	 */
	public int getRemainingMinOfUser(User user) {
		int estimatedTimeInMin = this.getEstimatedWorkTimeOfUser(user);
		int loggedTimeInMin = this.getLoggedTimeOfUser(user);
		return estimatedTimeInMin - loggedTimeInMin;
	}

	@Override
	public String toString() {
		return "Task [tags=" + tags + "]";
	}

	/**
	 * Add a new search tag to this task
	 * @param tag new tag
	 */
	public void addTag(String tag) {
		this.tags.add(tag);
	}
}