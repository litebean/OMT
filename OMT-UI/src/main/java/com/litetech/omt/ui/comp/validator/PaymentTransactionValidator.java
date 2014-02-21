package com.litetech.omt.ui.comp.validator;

import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.PaymentTransactionVO;

public class PaymentTransactionValidator extends Validator{

	private boolean validateCustomer(CustomerVO customerVO){
		boolean success = true;
		if(customerVO == null || customerVO.getId() <= 0){
			displayErrorMsg("Please select valid customer");
			success = false;
		}
		
		return success;
	}
	
	public boolean validate(PaymentTransactionVO paymentTransactionVO){
		boolean success = validateCustomer(paymentTransactionVO.getCustomerVO());
		if(!success){
			return success;
		}
		
		success = validateNotNullOrEmpty(paymentTransactionVO.getTransactionDate(), 
				"Please Enter Transction Date");
		if(!success){
			return success;
		}
		
		//add any future validation, if any...
		return success;
	}
}
