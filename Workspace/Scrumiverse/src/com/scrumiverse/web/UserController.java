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
import com.scrumiverse.utility.Utility;

/**
 * Controller for user account interactions
 * 
 * @author Kevin Jolitz
 * @version 18.02.2016
 *
 */
@Controller
public class UserController {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private UserDAO userDAO;

	/**
	 * show login site and prepare form object
	 * @return ModelAndView
	 */
	@RequestMapping("/login.htm")
	public ModelAndView login(){
		ModelMap map = new ModelMap();
		map.addAttribute("user", new User());
		map.addAttribute("action", Action.login);
		map.addAttribute("loginError", false);
		return new ModelAndView("index", map);
	}
	
	/**
	 * Check the given login data
	 * @param formLoginUser Send data of login formular
	 * @param session Current Session
	 * @return ModelAndView
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping("/loginCheck.htm")
	public ModelAndView checkLogin(User formLoginUser, HttpSession session) {
		ModelMap map = new ModelMap();
		try {
			User loadedUser = userDAO.getUserByEmail(formLoginUser.getEmail().toLowerCase());
			//login successful when no exception is thrown
			comparePasswords(formLoginUser, loadedUser);
			session.setAttribute("loggedUser", loadedUser);
			return new ModelAndView("redirect:projectOverview.htm");
		//No User with given email address, wrong password or fatal algorithm exception
		} catch (NoSuchUserException | WrongPasswordException | NoSuchAlgorithmException e) {
			map.addAttribute("loginError", true);
			map.addAttribute("action", Action.login);
			return new ModelAndView("index", map);
		} 
	}
	
	/**
	 * Compares formular password data with database password of user
	 * @throws WrongPasswordException
	 * @throws NoSuchAlgorithmException 
	 */
	private void comparePasswords(User formLoginUser, User loadedUser) throws WrongPasswordException, NoSuchAlgorithmException {
		String userHash = Utility.hashString(formLoginUser.getPassword());
		if(!userHash.equals(loadedUser.getPassword())) {
			throw new WrongPasswordException();
		}
	}
	
	/**
	 * Save user from formular and direct to login
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping("/registerUser.htm")
	public ModelAndView addUser(User formRegUser, BindingResult result){
		ModelMap map = new ModelMap();
		try {
			validator.validate(formRegUser, result);
			if(result.hasErrors()) {
				throw new ValidationException("");
			}
			String password = formRegUser.getPassword();
			//try to hash pw and save in object
			formRegUser.setPassword(Utility.hashString(password));
			//set and save lowerCase email for non case-sensitive comparison
			String lowerCaseEmail = formRegUser.getEmail().toLowerCase();
			formRegUser.setEmail(lowerCaseEmail);
			userDAO.getUserByEmail(lowerCaseEmail);
			//expects NoSuchUserException
			//when user already in database, throw exception
			throw new Exception();
		//Expect this exception
		} catch (NoSuchUserException e) {
			userDAO.saveUser(formRegUser);
			return new ModelAndView("redirect:login.htm");
		} catch (ValidationException e) {
			//validation failed
			map.addAttribute("regError", false);
		} catch (Exception e) {
			//User is already there, hash or validation failed 
			map.addAttribute("regError", true);
		}
		map.addAttribute("action", Action.register);
		map.addAttribute("user", formRegUser);
		return new ModelAndView("index", map);
	}

	/**
	 * Prepare registration formular
	 * @return ModelAndView
	 */
	@RequestMapping("/register.htm")
	public ModelAndView register(){
		ModelMap map = new ModelMap();
		map.addAttribute("user", createExampleUser());
		map.addAttribute("action", Action.register);
		return new ModelAndView("index", map);
	}
	
	/**
	 * Create example user for display in registration
	 * @return User user
	 */
	private User createExampleUser() {
		User exampleUser = new User();
		exampleUser.setEmail("example@mail.com");
		exampleUser.setName("Firstname Lastname");
		return exampleUser;
	}
}