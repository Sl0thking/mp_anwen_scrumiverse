package com.scrumiverse.persistence.DAO;

import java.util.Set;

import com.scrumiverse.model.scrumFeatures.Message;

/**
 * DAO Interface of Messages
 * @author Toni Serfling
 * @version 19.04.2016
 */
public interface MessageDAO {	
	public void saveMessage(Message m);
	public Message getMessage(int messageID);
	public Set<Message> getMessagesFromUser(int userID);
	void updateMessage(Message m);
	void deleteMessage(Message m);
}