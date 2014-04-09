package com.magic.promotion.util;


import java.security.MessageDigest;






public class MD5Util {
	
	public static byte[] genMd5Byte(String data)throws Exception{
		//Security.addProvider(new BouncyCastleProvider());
		MessageDigest   hash = MessageDigest.getInstance("MD5");
		byte[] input=data.getBytes("ISO-8859-1");
		hash.update(input);
		return hash.digest();
	}
	
	public static byte[] genMd5Byte(byte[] data)throws Exception{
		//Security.addProvider(new BouncyCastleProvider());
		MessageDigest   hash = MessageDigest.getInstance("MD5");
		hash.update(data);
		return hash.digest();
	}
	
	public static String genMd5String(String data)throws Exception{
		//Security.addProvider(new BouncyCastleProvider());
		MessageDigest   hash = MessageDigest.getInstance("MD5");
		byte[] input=data.getBytes("ISO-8859-1");
		hash.update(input);
		return Utils.toHex(hash.digest());
	}
	
	public static boolean checkMd5Sign(byte[] src,byte[] md5)throws Exception {
		//Security.addProvider(new BouncyCastleProvider());
		boolean val=false;
		MessageDigest   hash = MessageDigest.getInstance("MD5");
		hash.update(src);
		String t=Utils.toHex(hash.digest());
		String l=Utils.toHex(md5);
		val=t.equals(l);
		return val;
	}
}
