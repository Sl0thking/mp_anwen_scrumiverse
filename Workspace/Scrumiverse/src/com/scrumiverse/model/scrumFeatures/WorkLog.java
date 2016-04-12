package com.scrumiverse.model.scrumFeatures;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.scrumiverse.model.account.User;

@Entity
public class WorkLog implements Comparable<WorkLog> {
	private int logId;
	private User user;
	private int loggedMinutes;
	private String logText;
	private Date date;
	

	public WorkLog() {
		loggedMinutes = 0;
		logText = "No text";
		date = new Date();
	}
	
	@Id
	@GeneratedValue
	@Column(name ="LogId")
	public int getLogId() {
		return logId;
	}
	
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getLoggedMinutes() {
		return loggedMinutes;
	}
	
	public void setLoggedMinutes(int loggedMinutes) {
		this.loggedMinutes = loggedMinutes;
	}
	
	public String getLogText() {
		return logText;
	}
	
	public void setLogText(String logText) {
		this.logText = logText;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(WorkLog o) {
		return this.getDate().compareTo(o.getDate());
	}
}