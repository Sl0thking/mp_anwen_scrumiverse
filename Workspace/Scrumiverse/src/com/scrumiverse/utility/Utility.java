package com.scrumiverse.utility;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.scrumiverse.model.account.User;
import com.scrumiverse.web.Action;

public class Utility {
	
	
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

}
