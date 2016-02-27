package com.scrumiverse.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;


/**
 * Utility collection.
 * 
 * @author Kevin Jolitz
 * @version 27.02.2016
 */
public class Utility {
	
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