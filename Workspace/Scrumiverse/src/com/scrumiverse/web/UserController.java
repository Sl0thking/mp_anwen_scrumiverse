package com.scrumiverse.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.UserDAO;

@Controller
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping("/login.htm")
	public ModelAndView login(){
		ModelMap map=new ModelMap();
		map.addAttribute("user", new User());
		return new ModelAndView("login", map);
	}
	
	@RequestMapping("/login_check.htm")
	public ModelAndView checkLogin(){
		ModelMap map=new ModelMap();
		map.addAttribute("user", new User());
		return new ModelAndView("login", map);
	}
	
	@RequestMapping("/register_user.htm")
	public ModelAndView addUser(User user){
		//Add user to db
		System.out.println(user.getName());
		System.out.println(user);
		userDAO.addUser(user);
		return new ModelAndView("redirect:login.htm");
	}

	@RequestMapping("/register.htm")
	public ModelAndView register(){
		ModelMap map=new ModelMap();
		map.addAttribute("user", new User());
		return new ModelAndView("register", map);
	}
}
