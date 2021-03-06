package com.scrumiverse.model.scrumFeatures;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;


import com.scrumiverse.model.account.User;
/**
 * Datamodel of Messages
 * 
 * @author Toni Serfling, Kevin Jolitz
 * @version 19.04.2016
 */
@Entity
public class Message implements Comparable<Message> {
	
	private int messageID;
	private Date date;
	private User sender;
	private Set<User> recievers;
	private String content;
	private boolean isSeen;
	
	public Message() {
		this.date = new Date();
		this.recievers = new TreeSet<User>();
		this.content = "";
		this.isSeen = false;
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
	
	@OneToOne(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinColumn(name = "UserID")
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}
	
	@ManyToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER,  mappedBy = "messages")
	public Set<User> getRecievers() {
		return recievers;
	}

	public void setRecievers(Set<User> recievers) {
		this.recievers = recievers;
	}
	
	@Column(columnDefinition="TEXT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSeen() {
		return isSeen;
	}

	public void setSeen(boolean seen) {
		this.isSeen = seen;
	}
	
	@Override
	public int compareTo(Message mes){
		return ((Integer)this.getMessageID()).compareTo((Integer)mes.getMessageID());
	}
}
