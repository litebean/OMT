package com.litetech.omt.ui.comp.validator;

import javax.swing.JOptionPane;

public class Validator {
	
	protected void displayErrorMsg(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	protected boolean validateLong(long var, String errorMessage){
		boolean success = true;
		if(var <= 0){
			displayErrorMsg(errorMessage);
			success = false;
		}
		
		return success;
	}
	
	
	protected boolean validateNotNullOrEmpty(Object obj, String errorMessage){
		boolean success = true;
		if(obj == null || obj.toString().trim().equals("")){
			displayErrorMsg(errorMessage);
			success = false;
		}
		
		return success;
	}
}
