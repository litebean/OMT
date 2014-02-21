package com.litetech.omt.report.ds;

import java.lang.reflect.Method;




public class TestRefl {

	
	public Object getFieldValue(String arg0)  {
		Object returnValue = null;
		String methodName = arg0;
		String initChar = (""+methodName.charAt(0)).toUpperCase();
		try {
			methodName = "get" + initChar + methodName.substring(1, methodName.length());
			System.out.println("Method to be invoked "+methodName);
			Method method = this.getClass().getMethod(methodName);
			returnValue = method.invoke(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}
	
	
	public String getQuote(){
		return "BABA Drive Us!";
	}
	
	public static void main(String[] args) {
		TestRefl refl = new TestRefl();
		System.out.println("Message : " +refl.getFieldValue("quote"));
	}
}
