package com.litetech.omt.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtil {
	
	public static double roundToTwoDecimal(double value){
		return roundDecimals(value, 2);
	}
	
	public static double roundDecimals(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	
	public static String convertInTwoDecimalFormat(double number){
		DecimalFormat format = new DecimalFormat("#0.00");
		return format.format(number);
	}
	
	
	
	
}
