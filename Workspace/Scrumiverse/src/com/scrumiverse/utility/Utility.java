package com.scrumiverse.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.springframework.ui.ModelMap;

import com.scrumiverse.model.account.User;
import com.scrumiverse.web.Action;

/**
 * Utility collection.
 * 
 * @author Kevin Jolitz
 * @version 18.02.2016
 */
public class Utility {
	
	/**
	 * Generate and fill a model map with necessary session
	 * data for view purposes.
	 * 
	 * @param session Current Session
	 * @return ModelMap
	 */
	public static ModelMap generateModelMap(HttpSession session) {
		ModelMap map = new ModelMap();
		User loggedInUser = (User) session.getAttribute("loggedUser");
		boolean isLogged = loggedInUser != null;
		map.addAttribute("loggedUser", loggedInUser);
		map.addAttribute("isLogged", isLogged);
		//std action
		map.addAttribute("action", Action.login);
		return map;
	}
	
	/**
	 * Returns a md5 hash of a given string
	 * @param string source string
	 * @return hashed string
	 * @throws NoSuchAlgorithmException
	 */
	public static String hashString(String string) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(string.getBytes());
		byte md5ByteHash[] = md.digest();
		return DatatypeConverter.printHexBinary(md5ByteHash);
	}
}