package com.scrumiverse.persistence.DAO;

import java.util.Set;

import com.scrumiverse.model.scrumFeatures.Message;
import com.scrumiverse.model.scrumFeatures.Notification;

/**
 * DAO Interface of Messages
 * @author DoctorWhose
 * @version 11.03.2016
 */
public interface MessageDAO {	
	public void saveMessage(Message m);
	public Message getMessage(int messageID);
	public Set<Message> getMessagesFromUser(int userID);
	void updateMessage(Message m);
	void deleteMessage(Message m);
	void saveNotification(Notification m);
	Notification getNotification(int notificationID);
	Set<Notification> getNotificationsFromUser(int userID);
	void updateNotification(Notification m);
	void deleteNotification(Notification m);
}
