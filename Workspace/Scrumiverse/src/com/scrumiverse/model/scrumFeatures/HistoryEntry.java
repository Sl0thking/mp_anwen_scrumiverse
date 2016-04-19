package com.scrumiverse.model.scrumFeatures;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.scrumiverse.model.account.User;

/**
 * Entry of an event
 * 
 * @author Kevin Jolitz
 * @version 29.02.2016
 */
@Entity
public class HistoryEntry implements Comparable<HistoryEntry> {
	private int id;
	private User user;
	private ChangeEvent changeEvent;
	private Date date;
	
	public HistoryEntry() {
		super();
	}
	
	public HistoryEntry(User user, ChangeEvent changeEvent) {
		this.user = user;
		this.changeEvent = changeEvent;
		this.date = new Date();
	}

	@Id
	@GeneratedValue
	@Column(name = "HistoryEntryID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "UserID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JoinColumn(name = "ChangeEvent")
	@Enumerated(EnumType.STRING)
	public ChangeEvent getChangeEvent() {
		return changeEvent;
	}

	public void setChangeEvent(ChangeEvent changeEvent) {
		this.changeEvent = changeEvent;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(HistoryEntry o) {
		return (this.date.compareTo(o.getDate())*-1);
	}

	@Override
	public String toString() {
		return "HistoryEntry [id=" + id + ", user=" + user + ", changeEvent=" + changeEvent + ", date=" + date + "]";
	}
	
	
}