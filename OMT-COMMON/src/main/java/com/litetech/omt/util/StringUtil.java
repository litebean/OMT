package com.litetech.omt.util;

public class StringUtil {
	
	public static boolean isNullOrEmpty(String str){
		if(str == null || str.trim().equals("")){
			return true;
		}
		return false;
	}
	
	
	public static String toInitCap(String word){
		if(!isNullOrEmpty(word)){
			char[] charArray = word.toCharArray(); 
			charArray[0] = Character.toUpperCase(charArray[0]);
			word = new String(charArray);
		}
		return word;
	}
}
