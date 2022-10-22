package com.friendsandtechnology.leilao.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	public static String md5(String senha)throws NoSuchAlgorithmException {
		
		MessageDigest messagemdig = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, messagemdig.digest(senha.getBytes()));
		
		return hash.toString(16);
		
	}
	
}
