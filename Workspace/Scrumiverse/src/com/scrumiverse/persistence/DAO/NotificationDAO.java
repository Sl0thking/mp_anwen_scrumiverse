package com.scrumiverse.persistence.DAO;

import java.util.Set;

import com.scrumiverse.model.scrumFeatures.Notification;

/**
 * DAO Interface of Notifications
 * @author Lasse Jacobs
 * @version 19.04.2016
 */
public interface NotificationDAO {
	void saveNotification(Notification m);
	Notification getNotification(int notificationID);
	Set<Notification> getNotificationsFromUser(int userID);
	void updateNotification(Notification m);
	void deleteNotification(Notification m);
}