package com.scrumiverse.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Message;
import com.scrumiverse.persistence.DAO.MessageDAO;
import com.scrumiverse.persistence.DAO.UserDAO;

/**
 * Controller for User-Message interactions
 * @author Toni Laptop
 * @version 16.03.2016
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
	@RequestMapping("/seenMessage.htm")
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
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
}
