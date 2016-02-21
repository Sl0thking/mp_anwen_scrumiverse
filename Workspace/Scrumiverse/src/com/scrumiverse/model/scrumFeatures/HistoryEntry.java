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

@Entity
public class HistoryEntry {
	private int id;
	private User user;
	private ChangeEvent changeEvent;
	private Date date;
	
	public HistoryEntry() {
		super();
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
}