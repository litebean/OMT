package com.litetech.omt.report.ds;

import java.lang.reflect.Method;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class BaseDS implements JRDataSource{

	protected int index = 0;
	
	
	public Object getFieldValue(JRField arg0) throws JRException {
		Object returnValue = null;
		String methodName = arg0.getName();
				
		String initChar = (""+methodName.charAt(0)).toUpperCase();
		try {
			methodName = "get" + initChar + methodName.substring(1, methodName.length());
			Method method = this.getClass().getMethod(methodName);
			returnValue = method.invoke(this);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}
	
	
	public boolean next() throws JRException {
		if(index == 0){
			index++;
			return true;
		}
		return false;
	}
}
