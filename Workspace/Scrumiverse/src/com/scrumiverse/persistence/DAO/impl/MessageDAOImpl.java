package com.scrumiverse.persistence.DAO.impl;

import java.util.Set;
import java.util.TreeSet;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Message;
import com.scrumiverse.persistence.DAO.MessageDAO;

/**
 * DAO Implementation of Messages
 * @author Toni Serfling
 * @version 29.03.2016
 */
public class MessageDAOImpl implements MessageDAO {
	
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactoryMessage) {
		this.hibernateTemplate = 
               new HibernateTemplate(sessionFactoryMessage); }

	@Override
	public void saveMessage(Message m) {
		hibernateTemplate.save(m);		
	}
	/**
	 * Returns a specific message by messageID
	 * @param id
	 * @return Message
	 */
	@Override
	public Message getMessage(int messageID) {
		Message message = (Message) hibernateTemplate.find("from Message where MessageID='" + messageID +"'").get(0);
		return message;
	}
	/**
	 * Returns all messages from a specific user by userID
	 * @param id
	 * @return Set<Message>
	 */
	@Override
	public Set<Message> getMessagesFromUser(int userID) {
		Set<Message> messages = new TreeSet<Message>();
		User u = (User) hibernateTemplate.find("from User where UserID='" + userID + "'").get(0);
		messages = u.getMessages();
		return messages;
	}

	@Override
	public void updateMessage(Message m) {
		hibernateTemplate.update(m);
	}

	@Override
	public void deleteMessage(Message m) {
		hibernateTemplate.delete(m);
	}
}