package com.scrumiverse.model.account;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

import com.scrumiverse.model.scrumCore.Project;
import com.scrumiverse.model.scrumFeatures.Message;
import com.scrumiverse.model.scrumFeatures.Notification;

/**
 * Datamodell for a scrumiverse user account
 * 
 * @author Kevin Jolitz, Toni Serfling
 * @version 23.04.2016
 */
@Entity
public class User {
	private int userID;
	
	@NotBlank(message="please enter a valid email adress.")
	@RegExp(value="^[A-Za-z0-9.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
			message="not a valid email adress.")
	private String email;
	
	@NotBlank(message="please enter a surname and name.")
	@RegExp(value="^[\\p{L}]{3,30}[ ]{1,}[\\p{L}]{3,30}$",
			message="split name and surname with whitespace.")
	private String name;
	
	private boolean emailNotification;
	private Set<Project> projects;
	
	@NotBlank(message="must enter a valid password.")
	@RegExp(value="^(?=.*[\\d])(?=.*[A-Z])(?=.*[a-z])[\\w\\d!@#$%_]{6,40}$",
			message="must have at least 6 characters, no spaces, at least 1 digit, at least 1 uppercase and 1 lowercase letter.")
	private String password;
	private String profileImagePath;
	private SortedSet<Message> messages;
	private SortedSet<Notification> notifications;
	
	public User() {
		email = "";
		name = "";
		emailNotification = false;
		password = "";
		projects = new HashSet<Project>();
		profileImagePath = "./resources/userPictures/default.png";
		messages = new TreeSet<Message>();
	}

	@Id
	@GeneratedValue
	@Column(name="UserID")
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isEmailNotification() {
		return emailNotification;
	}
	
	public void setEmailNotification(boolean emailNotification) {
		this.emailNotification = emailNotification;
	}
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public Set<Project> getProjects() {
		return projects;	
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	public void addProject(Project project) {
		this.projects.add(project);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Leaves a given project
	 * 
	 * @param project project to leave
	 */
	public void leaveProject(Project project) {
		this.projects.remove(project);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userID != other.userID)
			return false;
		return true;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = "message_user", joinColumns={
			@JoinColumn(name = "UserID", nullable = false) },
			inverseJoinColumns = { @JoinColumn(name = "messageID", nullable = false)})
	@Sort(type=SortType.NATURAL)
	public SortedSet<Message> getMessages() {
		return messages;
	}

	public void setMessages(SortedSet<Message> messages) {
		this.messages = messages;
	}
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public void removeMessage(Message message) {
		this.messages.remove(message);
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="UserID")
	@Sort(type=SortType.NATURAL)
	public SortedSet<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(SortedSet<Notification> notifications) {
		this.notifications = notifications;
	}
	
	public void addNotification(Notification notify) {
		this.notifications.add(notify);
	}
	
	public void removeNotification(Notification notify) {
		this.notifications.remove(notify);
	}
	
	@Override
	public String toString() {
		return email;
	}	
}