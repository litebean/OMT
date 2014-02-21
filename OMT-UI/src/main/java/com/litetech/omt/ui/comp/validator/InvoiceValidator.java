package com.litetech.omt.ui.comp.validator;

import java.util.List;
import com.litetech.omt.vo.InvoiceChargeVO;
import com.litetech.omt.vo.InvoiceVO;

public class InvoiceValidator extends OrderValidator{
	
	public boolean validate(InvoiceVO invoiceVO){
		
		boolean isNewOrder = invoiceVO.getOrderId() == 0 ? true : false;
		
		boolean success = validateNotNullOrEmpty(invoiceVO.getInvoiceDate(), "Please Enter Invoice Date");
		if(!success){
			return success;
		}		
		
		//customer id, line items is expected to be validated by order validation
		//validate it only if the invoice for existing order where order validation is not required
		if(!isNewOrder){
			success = validateLong(invoiceVO.getCustomerId(), "Please Select Customer");
			if(!success){
				return success;
			}
			
			success = validateLineItem(invoiceVO.getInvoiceLineItems());
			if(!success){
				return success;
			}
		}
		
		success = validateCharges(invoiceVO.getInvoiceCharges());
		if(!success){
			return success;
		}
		
		//add future validation here
		return success;
	}	
	
	
	private boolean validateCharges(List<InvoiceChargeVO> chargeVOs){
		if(chargeVOs.size() < 2){
			displayErrorMsg("Please choose atleast one tax component");
			return false;
		}
		
		for(int i = 1; i < chargeVOs.size(); i = i+2){
			InvoiceChargeVO invoiceChargeVO = chargeVOs.get(i);
			if(invoiceChargeVO.getCharge().getId() <= 0){
				displayErrorMsg("Please do not leave none of the Tax component as empty");
				return false;
			}
		}
		
		return true;
	}
	
	
	
	
	
}
