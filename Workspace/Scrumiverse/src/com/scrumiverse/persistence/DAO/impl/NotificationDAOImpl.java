package com.scrumiverse.persistence.DAO.impl;

import java.util.Set;
import java.util.TreeSet;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Notification;
import com.scrumiverse.persistence.DAO.NotificationDAO;

/**
 * DAO Implementation of Notifications
 * @author Lasse Jacobs
 * @version 29.03.2016
 */
public class NotificationDAOImpl implements NotificationDAO {
	
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactoryMessage) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactoryMessage); 
	}

	@Override
	public void saveNotification(Notification m) {
		hibernateTemplate.save(m);		
	}
	/**
	 * Returns a specific notification
	 * @param id
	 * @return Notification
	 */
	@Override
	public Notification getNotification(int notificationID) {
		Notification notification = (Notification) hibernateTemplate.find("from Notification where NotificationID='" + notificationID +"'").get(0);
		return notification;
	}
	/**
	 * Returns all notifications from a specific user
	 * @param id
	 * @return Set<Notification>
	 */
	@Override
	public Set<Notification> getNotificationsFromUser(int userID) {
		Set<Notification> notifications = new TreeSet<Notification>();
		User u = (User) hibernateTemplate.find("from User where UserID='" + userID + "'").get(0);
		notifications = u.getNotifications();
		return notifications;
	}

	@Override
	public void updateNotification(Notification m) {
		hibernateTemplate.update(m);
	}

	@Override
	public void deleteNotification(Notification m) {
		hibernateTemplate.delete(m);
	}
}