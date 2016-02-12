package com.scrumiverse.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public abstract class Security {
	
	public static String hashString(String string) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(string.getBytes());
		byte md5ByteHash[] = md.digest();
		return DatatypeConverter.printHexBinary(md5ByteHash);
	}
}