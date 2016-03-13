package com.scrumiverse.model.scrumFeatures;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Entity;

import com.scrumiverse.model.account.User;
/**
 * Datamodel of Messages
 * @author Toni Serfling
 * @version 10.03.2016
 */
@Entity
public class Message {
	
	private int messageID;
	private Date date;
	private User sender;
	private Set<User> recievers;
	private String content;
	private boolean isRead;
	
	public Message() {
		date = new Date();
		sender = new User();
		recievers = new TreeSet<User>();
		content = "Content";
		isRead = false;
	}
	@Id
	@GeneratedValue
	@Column(name="MessageID")
	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	@OneToOne
	@JoinColumn(name="UserID")
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Set<User> getRecievers() {
		return recievers;
	}

	public void setRecievers(Set<User> recievers) {
		this.recievers = recievers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
}
