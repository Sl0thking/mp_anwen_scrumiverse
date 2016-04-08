package com.scrumiverse.web;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.InvalidContentTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.scrumiverse.exception.InvalidSessionException;
import com.scrumiverse.exception.NoSuchUserException;
import com.scrumiverse.exception.SessionIsNotClearedException;
import com.scrumiverse.exception.WrongPasswordException;
import com.scrumiverse.model.account.User;
import com.scrumiverse.persistence.DAO.UserDAO;
import com.scrumiverse.utility.Utility;


/**
 * Controller for user account interactions
 * 
 * @author Kevin Jolitz, Toni Serfling
 * @version 01.03.2016
 *
 */
@Controller
public class UserController extends MetaController{
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private UserDAO userDAO;

	/**
	 * show login site and prepare form object
	 * @return ModelAndView
	 */
	@RequestMapping("/login.htm")
	public ModelAndView login(HttpSession session){
		try {
			checkValidSession(session);
			ModelMap map = new ModelMap();
			map.addAttribute("user", new User());
			map.addAttribute("action", Action.login);
			map.addAttribute("loginError", false);
			return new ModelAndView("index", map);
		} catch (SessionIsNotClearedException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
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
			checkValidSession(session);
			User loadedUser = userDAO.getUserByEmail(formLoginUser.getEmail().toLowerCase());
			//login successful when no exception is thrown
			comparePasswords(formLoginUser, loadedUser);
			session.setAttribute("userId", loadedUser.getUserID());
			session.setAttribute("isLogged", true);
			return new ModelAndView("redirect:projectOverview.htm");
		//No User with given email address, wrong password or fatal algorithm exception
		} catch (NoSuchUserException | WrongPasswordException | NoSuchAlgorithmException e) {
			map.addAttribute("loginError", true);
			map.addAttribute("action", Action.login);
			return new ModelAndView("index", map);
		} catch (SessionIsNotClearedException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:projectOverview.htm");
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
			checkEmailAvailability(formRegUser);
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
			formRegUser.setPassword("");
			map.addAttribute("regError", true);
		}
		map.addAttribute("action", Action.register);
		map.addAttribute("user", formRegUser);
		return new ModelAndView("index", map);
	}
	
	private boolean checkEmailAvailability(User formRegUser) throws NoSuchUserException {
		String lowerCaseEmail = formRegUser.getEmail().toLowerCase();
		formRegUser.setEmail(lowerCaseEmail);
		userDAO.getUserByEmail(lowerCaseEmail);
		return true;
	}

	/**
	 * Prepare registration formular
	 * @return ModelAndView
	 */
	@RequestMapping("/register.htm")
	public ModelAndView register(HttpSession session){
		try {
			checkValidSession(session);
			ModelMap map = new ModelMap();
			map.addAttribute("user", new User());
			map.addAttribute("action", Action.register);
			return new ModelAndView("index", map);
		} catch(SessionIsNotClearedException e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	/**
	 * Create example user for display in registration
	 * @return User user
	 */
//	private User createExampleUser() {
//		User exampleUser = new User();
//		exampleUser.setEmail("example@mail.com");
//		exampleUser.setName("Firstname Lastname");
//		return exampleUser;
//	}
	
	/**
	 * logout and invalidate the session
	 * @return ModelAndView
	 */
	@RequestMapping("/logout.htm")
	private ModelAndView logout(HttpSession session) {
		session.invalidate();
		return new ModelAndView("redirect:login.htm");
	}
	
	/**
	 * Shows the users account settings
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/userSettings.htm")
	public ModelAndView userSettings(HttpSession session) {
		try {
			checkInvalidSession(session);
			ModelMap map = this.prepareModelMap(session);		
			map.addAttribute("user", this.loadActiveUser(session));
			map.addAttribute("action", Action.accountSettings);
			return new ModelAndView("index", map);
		} catch(InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		} catch (Exception e) {
			return new ModelAndView("redirect:projectOverview.htm");
		}
	}
	
	/**
	 * Change the users username TODO fix workflow
	 * @param user
	 * @param name
	 * @return ModelAndView
	 * @throws NoSuchUserException 
	 */
	@RequestMapping("/changeUser.htm")
	public ModelAndView changeUserName(HttpSession session, User newUserData, BindingResult result) {
		try {
			checkInvalidSession(session);
			User currentUserData = this.loadActiveUser(session);
			//Set random valid password to pass through validator
			//necessary when no pw change is required
			if(newUserData.getPassword() == null || newUserData.getPassword().equals("")) {
				newUserData.setPassword("aBaB1!");
			}
			validator.validate(newUserData, result);
			if(result.hasErrors()) {
				throw new ValidationException("");
			}
			//When validation workaround was necessary, load pw from current Session Data
			//and set password of new user data
			if(newUserData.getPassword().equals("aBaB1!")) {
				newUserData.setPassword(currentUserData.getPassword());
			}
			//Set projects of new user data to projects from current user data
			newUserData.setProjects(currentUserData.getProjects());
			if(!currentUserData.getEmail().equals(newUserData.getEmail())) {
				checkEmailAvailability(newUserData);
				throw new Exception();
			} else {
				throw new NoSuchUserException();
			}
		} catch(InvalidSessionException e) {
			return new ModelAndView("redirect:login.htm");
		} catch(NoSuchUserException e) {
			userDAO.updateUser(newUserData);
			session.setAttribute("loggedUser", newUserData);
			return new ModelAndView("redirect:userSettings.htm");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:userSettings.htm");
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/changeUserPic")
	public ModelAndView changeUserPic(HttpServletRequest request, HttpSession session, @RequestParam("image") MultipartFile file) {
		try {
			checkInvalidSession(session);
			User user = this.loadActiveUser(session);
			checkContentType(file.getContentType());
			String ending = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
			File contextPath = new File(request.getServletContext().getRealPath(""));
			File image = new File(contextPath + File.separator + "resources" 
											  + File.separator + "userPictures" 
					                          + File.separator + "userPic_" + user.getUserID() + ending);
			String relativePath = "resources/userPictures/userPic_" + user.getUserID() + ending;
			image.createNewFile();
			file.transferTo(image);
			user.setProfileImagePath(relativePath);
			userDAO.updateUser(user);
			return new ModelAndView("redirect:userSettings.htm");
		} catch(InvalidSessionException | NoSuchUserException | IllegalStateException | IOException | InvalidContentTypeException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:userSettings.htm");
		}
	}

	private void checkContentType(String contentType) throws InvalidContentTypeException {
		if(!contentType.equals("image/png") && !contentType.equals("image/jpg")) {
			throw new InvalidContentTypeException();
		}
	}
}