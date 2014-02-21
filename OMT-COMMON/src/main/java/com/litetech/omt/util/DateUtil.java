package com.litetech.omt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	
	public static String getDateAsString(Date date){
		String dateStr = "";
		if(date != null){
			dateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return dateStr;
	}
	
	
	public static Date convertStringToDate(String dateString){
		Date transDate = null;
		if(dateString != null && !dateString.equals("")){
			try {
				transDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}		
		}
		return transDate;
	}
}
