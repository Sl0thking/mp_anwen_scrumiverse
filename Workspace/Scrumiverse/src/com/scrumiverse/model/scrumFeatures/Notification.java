package com.scrumiverse.model.scrumFeatures;

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

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumCore.PlanElement;

/**
 * Datamodel of notifications
 * @author Lasse Jacobs
 * @version 29.03.2016
 */
@Entity
public class Notification implements Comparable<Notification>{
	private int notificationID;
	private User triggerUser;
	private User alertedUser;
	private String triggerDescription;
	private ChangeEvent changeEvent;
	private boolean isSeen;
	
	public Notification(){
		triggerUser = new User();
		alertedUser = new User();
		setTriggerDescription("");
		changeEvent = null;
		isSeen = false;
	}
	
	public Notification(User triggerUser, User alertedUser, ChangeEvent changeEvent, PlanElement triggerElement){
		this.triggerUser = triggerUser;
		this.changeEvent = changeEvent;
		this.alertedUser = alertedUser;
		this.setTriggerDescription(triggerElement.getDescription());
	}
	
	@Id
	@GeneratedValue
	@Column(name="NotificationID")
	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}
	@OneToOne
	public User getTriggerUser() {
		return triggerUser;
	}
	public void setTriggerUser(User triggerUser) {
		this.triggerUser = triggerUser;
	}
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="UserID")
	public User getAlertedUser() {
		return alertedUser;
	}

	public void setAlertedUser(User alertedUser) {
		this.alertedUser = alertedUser;
	}

	public ChangeEvent getChangeEvent() {
		return changeEvent;
	}

	public void setChangeEvent(ChangeEvent changeEvent) {
		this.changeEvent = changeEvent;
	}

	public boolean isSeen() {
		return isSeen;
	}

	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	public String getTriggerDescription() {
		return triggerDescription;
	}

	public void setTriggerDescription(String triggerDescription) {
		this.triggerDescription = triggerDescription;
	}
	
	@Override
	public String toString(){
		return "";
	}
	
	@Override
	public int compareTo(Notification not){
		return ((Integer)this.getNotificationID()).compareTo((Integer)not.getNotificationID());
	}
}
