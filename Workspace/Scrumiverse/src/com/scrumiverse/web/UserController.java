package com.scrumiverse.web;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.UserDAO;
import com.scrumiverse.persistence.DAO.impl.NoSuchUserException;
import com.scrumiverse.utility.Security;

@Controller
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping("/login.htm")
	public ModelAndView login(){
		ModelMap map=new ModelMap();
		map.addAttribute("user", new User());
		map.addAttribute("action", Action.login);
		return new ModelAndView("index", map);
	}
	
	@RequestMapping("/login_check.htm")
	public ModelAndView checkLogin(User user) throws NoSuchAlgorithmException{
		ModelMap map=new ModelMap();
		try {
			User relatedUser = userDAO.getUserByEmail(user.getEmail().toLowerCase());
			String userHash = Security.hashString(user.getPassword());
			if(userHash.equals(relatedUser.getPassword())) {
				//Successfull login!
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
	public ModelAndView addUser(User user){
		ModelMap map=new ModelMap();
		try {
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
		} catch (Exception e) {
			//Other page? display error?
			map.addAttribute("action", Action.login);
		}
		return new ModelAndView("index", map);
	}

	@RequestMapping("/register.htm")
	public ModelAndView register(){
		ModelMap map=new ModelMap();
		map.addAttribute("user", new User());
		return new ModelAndView("register", map);
	}
}
