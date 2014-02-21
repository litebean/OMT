package com.litetech.omt.util;

import java.text.DecimalFormat;

public class CurrencyUtil {

	  private static String mask = "0000000000";
	  private static final String[] tensNames = {
	    "",
	    " ten",
	    " twenty",
	    " thirty",
	    " forty",
	    " fifty",
	    " sixty",
	    " seventy",
	    " eighty",
	    " ninety"
	  };

	private static final String[] numNames = {
	    "",
	    " one",
	    " two",
	    " three",
	    " four",
	    " five",
	    " six",
	    " seven",
	    " eight",
	    " nine",
	    " ten",
	    " eleven",
	    " twelve",
	    " thirteen",
	    " fourteen",
	    " fifteen",
	    " sixteen",
	    " seventeen",
	    " eighteen",
	    " nineteen"
	};

	private static String convertLessThanOneThousand(int number) {
	    String soFar;
	
	    if (number % 100 < 20){
	      soFar = numNames[number % 100];
	      number /= 100;
	    }else {
	      soFar = numNames[number % 10];
	      number /= 10;
	
	      soFar = tensNames[number % 10] + soFar;
	      number /= 10;
	    }
	    if (number == 0) return soFar;
	    return numNames[number] + " hundred" + soFar;
	}
	
	private static String convertLessThanOneHundred(int number) {
	    String soFar;
	
	    if (number % 100 < 20){
	      soFar = numNames[number % 100];
	      number /= 100;
	    }else {
	      soFar = numNames[number % 10];
	      number /= 10;
	
	      soFar = tensNames[number % 10] + soFar;
	      number /= 10;
	    }
	    if (number == 0) return soFar;
	    return numNames[number] + " hundred" + soFar;
	}


	public static String convert(long number) {
	    // 0 to 999 999 999 999
	    if (number == 0) { return "zero"; }

	    String snumber = Long.toString(number);

	    
	    DecimalFormat df = new DecimalFormat(mask);
	    snumber = df.format(number);

	    int crores = Integer.parseInt(snumber.substring(0,3));
	    int lakhs  = Integer.parseInt(snumber.substring(3,5));
	    int thousands = Integer.parseInt(snumber.substring(5,7));
	    int hundreds = Integer.parseInt(snumber.substring(7,10));
	    	    
	    String tradCrores;
	    switch (crores) {
	    case 0:
	      tradCrores = "";
	      break;
	    case 1 :
	    	tradCrores = convertLessThanOneThousand(crores) 
	      + " crore ";
	      break;
	    default :
	    	tradCrores = convertLessThanOneThousand(crores) 
	      + " crore ";
	    }
	    String result =  tradCrores;
	    
	    String tradLakhs;
	    switch (lakhs) {
	    case 0:
	      tradLakhs = "";
	      break;
	    case 1 :
	      tradLakhs = convertLessThanOneHundred(lakhs) 
	      + " lakh ";
	      break;
	    default :
	      tradLakhs = convertLessThanOneHundred(lakhs) 
	      + " lakh ";
	    }
	    result =  result + tradLakhs;

	    String tradHundredThousands;
	    switch (thousands) {
	    case 0:
	      tradHundredThousands = "";
	      break;
	    case 1 :
	      tradHundredThousands = "one thousand ";
	      break;
	    default :
	      tradHundredThousands = convertLessThanOneHundred(thousands) 
	      + " thousand ";
	    }
	    result =  result + tradHundredThousands;
		
	    String tradHundred;
	    tradHundred = convertLessThanOneHundred(hundreds);
	    result =  result + tradHundred;

	    // remove extra spaces!
	    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}
	
	public static String convertINR(double amt){
		String ruppePaiseMask = mask + ".00";
	    DecimalFormat df = new DecimalFormat(ruppePaiseMask);
	    String ruppePaiseStr = df.format(amt);
		String[] ruppePaise = ruppePaiseStr.split("\\.");
		
		String inr = convert(Long.valueOf(ruppePaise[0]));
		//inr += " ruppes ";
		if(ruppePaise.length == 2){
			
			if(Integer.valueOf(ruppePaise[1]) > 0){
				String paise = convertLessThanOneThousand(Integer.valueOf(Integer.valueOf(ruppePaise[1])));
				inr += "and"+ paise + " paise";
			}
		}		
		inr ="ruppes " + inr + " only/- ";
		return inr;
	}
	
	
	public static void main(String[] args) {
		System.out.println("5149.5 >> "+ convertINR(5149.5));
		System.out.println("21581232232l >> "+convertINR(21581232232l));
		System.out.println("799987.199 >> "+convertINR(799987.19));
	}

}
