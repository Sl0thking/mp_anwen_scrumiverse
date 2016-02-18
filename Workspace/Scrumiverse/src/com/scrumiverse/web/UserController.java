package com.scrumiverse.web;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.UserDAO;
import com.scrumiverse.persistence.DAO.impl.NoSuchUserException;
import com.scrumiverse.utility.Security;
import com.scrumiverse.utility.Utility;

/**
 * Controller for user account interactions
 * 
 * @author Kevin Jolitz
 * @version 16.02.2016
 *
 */
@Controller
public class UserController {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private UserDAO userDAO;

	@RequestMapping("/login.htm")
	public ModelAndView login(){
		ModelMap map = new ModelMap();
		map.addAttribute("user", new User());
		map.addAttribute("action", Action.login);
		return new ModelAndView("index", map);
	}
	
	@RequestMapping("/login_check.htm")
	public ModelAndView checkLogin(User user, HttpSession session) throws NoSuchAlgorithmException{
		ModelMap map = new ModelMap();
		try {
			User relatedUser = userDAO.getUserByEmail(user.getEmail().toLowerCase());
			String userHash = Security.hashString(user.getPassword());
			if(userHash.equals(relatedUser.getPassword())) {
				//Successfull login!
				session.setAttribute("loggedUser", relatedUser);
				//now use from Utility generated map with needed data
				//and active user
				map = Utility.generateModelMap(session);
				map.addAttribute("action", Action.backlog);
			} else {
				//Failed login!
				map.addAttribute("action", Action.login);
			}
		} catch (NoSuchUserException e) {
			e.printStackTrace();
			map.addAttribute("action", Action.login);
		}
		return new ModelAndView("index", map);
	}
	
	@RequestMapping("/register_user.htm")
	public ModelAndView addUser(User user, BindingResult result){
		ModelMap map = new ModelMap();
		try {
			validator.validate(user, result);
			if(result.hasErrors()) {
				throw new ValidationException("");
			}
			String password = user.getPassword();
			user.setPassword(Security.hashString(password));
			String lowerCaseEmail = user.getEmail().toLowerCase();
			user.setEmail(lowerCaseEmail);
			userDAO.getUserByEmail(lowerCaseEmail);
			//user already there, throw exception
			throw new Exception();
		//Expect this exception
		} catch (NoSuchUserException e) {
			userDAO.addUser(user);
			map.addAttribute("action", Action.login);
			map.addAttribute("user", new User());
		} catch (ValidationException e) {
			map.addAttribute("user", user);
			map.addAttribute("action", Action.register);
		} catch (Exception e) {
			//Other page? display error?
			map.addAttribute("action", Action.login);
		}
		return new ModelAndView("index", map);
	}

	@RequestMapping("/register.htm")
	public ModelAndView register(){
		ModelMap map = new ModelMap();
		map.addAttribute("user", new User());
		map.addAttribute("action", Action.register);
		return new ModelAndView("index", map);
	}
}