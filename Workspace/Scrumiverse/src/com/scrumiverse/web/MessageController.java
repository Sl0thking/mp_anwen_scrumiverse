package com.scrumiverse.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.binder.UserBinder;
import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Message;
import com.scrumiverse.persistence.DAO.MessageDAO;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Controller for User-Message interactions
 * @author Toni Serfling, Joshua Ward
 * @version 19.04.2016
 */
@Controller
public class MessageController extends MetaController {
	
	@Autowired
	private MessageDAO messageDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(User.class, new UserBinder(userDAO));
	}
	
	/**
	 * Sends a Message to the recievers
	 * @param message the message that shall be sent
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/sendMessage.htm")
	public ModelAndView sendMessage(Message message, HttpSession session, HttpServletRequest request) {
		try {
			checkInvalidSession(session);
			message.setSender(loadActiveUser(session));
			messageDAO.saveMessage(message);
			Set<User> recievers = message.getRecievers();
			for (User u: recievers) {
				u.addMessage(message);
				userDAO.updateUser(u);
			}
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");			
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}		
	}
	
	/**
	 * Marks a message as read/seen
	 * @param id
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/markAsRead.htm")
	public ModelAndView markMessageAsRead(@RequestParam int id, HttpSession session, HttpServletRequest request) {
		try {
			checkInvalidSession(session);
			Message m = messageDAO.getMessage(id);
			m.setSeen(true);
			messageDAO.updateMessage(m);
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Marks all messages of an user as read
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/markAllAsRead.htm")
	public ModelAndView markAllAsRead(HttpSession session, HttpServletRequest request) {
		try {
			checkInvalidSession(session);
			User currentUser = this.loadActiveUser(session);
			Set<Message> messageList = currentUser.getMessages();
			for(Message m:messageList){
				if(!m.isSeen()) {
					m.setSeen(true);
					messageDAO.updateMessage(m);
				}
			}
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Deletes a Message
	 * @param id
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/deleteMessage.htm")
	public ModelAndView deleteMessage(@RequestParam int id, HttpSession session, HttpServletRequest request) {
		try {
			checkInvalidSession(session);
			Message message = messageDAO.getMessage(id);			
			// remove message from user
			User currentUser = this.loadActiveUser(session);
			currentUser.removeMessage(message);
			userDAO.updateUser(currentUser);			
			// delete message if the current user was the last user attached to it
			message = messageDAO.getMessage(id);
			if (wasLastRecieverInMessage(message))
				messageDAO.deleteMessage(message);
			
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Iterates over all recievers of the Message m, and checks if they
	 * are still attached to the 
	 * @param m The message that shall be checked
	 * @return
	 */
	private boolean wasLastRecieverInMessage(Message m) {
		boolean toReturn = true;
		// iterate over recievers to check if they are still attached to the message
		for (User reciever : m.getRecievers()) {
			// check if the reciever still has this message
			if (reciever.getMessages().contains(m))
				toReturn = false;
		}
		return toReturn;
	}
}