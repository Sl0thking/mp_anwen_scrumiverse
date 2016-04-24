package com.scrumiverse.binder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Binder class for Dates
 * 
 * @author Lasse Jacobs
 * @version 08.04.2016
 *
 */
public class DateBinder extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String arg0) throws IllegalArgumentException {
	      SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd"); 
	      try {
			setValue(ft.parse(arg0));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}