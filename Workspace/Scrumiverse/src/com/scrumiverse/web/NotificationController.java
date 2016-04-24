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
import com.scrumiverse.exception.UserPersistenceException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.model.scrumFeatures.Notification;
import com.scrumiverse.persistence.DAO.NotificationDAO;

/**
 * Controller for User-Notification interactions
 * @author Lasse Jacobs, Joshua Ward
 * @version 19.04.2016
 */
@Controller
public class NotificationController extends MetaController {
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	/**
	 * Deletes a Notification
	 * @param int a requested notification id
	 * @param HttpSession the current session
	 * @param HttpServletRequest a HttpServletRequest
	 * @return 
	 */
	@RequestMapping("/deleteNotification.htm")
	public ModelAndView deleteNotification(@RequestParam int id, HttpSession session, HttpServletRequest request) {
		try {
			checkInvalidSession(session);
			Notification n = notificationDAO.getNotification(id);
			notificationDAO.deleteNotification(n);
			User u = this.loadActiveUser(session);
			u.removeNotification(n);
			userDAO.updateUser(u);
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Marks a Notification as seen
	 * @param int requested notification id
	 * @param HttpSession the current session
	 * @param HttpServletRequest a HttpServletRequest
	 * @return 
	 */
	@RequestMapping("/markAsSeen.htm")
	public ModelAndView markAsSeen(@RequestParam int id, HttpSession session, HttpServletRequest request) {
		try {
			checkInvalidSession(session);
			Notification n = notificationDAO.getNotification(id);
			n.setSeen(true);
			notificationDAO.updateNotification(n);
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
		} catch (InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
	
	/**
	 * Marks all notifications of the current user as seen
	 * @param HttpSession the current session
	 * @param HttpServletRequest a HttpServletRequest
	 * @return
	 */
	@RequestMapping("/markAllAsSeen.htm")
	public ModelAndView markAllAsRead(HttpSession session, HttpServletRequest request) {
		try {
			checkInvalidSession(session);
			User currentUser = this.loadActiveUser(session);
			Set<Notification> notificationList = currentUser.getNotifications();
			for(Notification n : notificationList){
				if(!n.isSeen()) {
					n.setSeen(true);
					notificationDAO.updateNotification(n);
				}
			}
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer +"");
		} catch (InvalidSessionException | UserPersistenceException e) {
			return new ModelAndView("redirect:login.htm");
		}
	}
}