package com.magic.promotion.util;

public class StringUtil {
	public static String genInviteCode(){
		String inviteCode = "";
		inviteCode = (int)((Math.random()*9+1)*100000)+"";
		return inviteCode;
	}
	
}
