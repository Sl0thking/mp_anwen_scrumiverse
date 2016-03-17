package com.scrumiverse.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoProjectFoundException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Message;
import com.scrumiverse.persistence.DAO.MessageDAO;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Controller for User-Message interactions
 * @author Toni Serfling
 * @version 17.03.2016
 */
@Controller
public class MessageController extends MetaController {
	
	@Autowired
	private MessageDAO messageDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * Sends a Message to the recievers
	 * @param message
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/sendMessage.htm")
	public ModelAndView sendMessage(@RequestParam Message message, HttpSession session, HttpServletRequest request) {
		try {
			checkInvalidSession(session);
			messageDAO.saveMessage(message);
			Set<User> recievers = message.getRecievers();
			for(User u: recievers) {
				u.addMessage(message);
				userDAO.updateUser(u);
			}
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
			
		} catch (InvalidSessionException e) {
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
			messageDAO.saveMessage(m);
			
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
			User u = this.loadActiveUser(session);
			Set<Message> messageList = u.getMessages();
			for(Message m:messageList){
				if(m.isSeen()==false) {
					m.setSeen(true);
					messageDAO.updateMessage(m);
				}
			}
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
		} catch (InvalidSessionException | NoSuchUserException e) {
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
			Message m =messageDAO.getMessage(id);
			messageDAO.deleteMessage(m);
			User u = this.loadActiveUser(session);
			u.removeMessage(m);
			userDAO.updateUser(u);
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
		} catch (InvalidSessionException | NoSuchUserException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
}
