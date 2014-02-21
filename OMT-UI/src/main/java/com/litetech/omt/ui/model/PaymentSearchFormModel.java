package com.litetech.omt.ui.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.litetech.omt.vo.PaymentTransactionVO;

public class PaymentSearchFormModel {

	List<PaymentTransactionVO> paymentTransactionVOs;

	public List<PaymentTransactionVO> getPaymentTransactionVOs() {
		return paymentTransactionVOs;
	}

	public void setPaymentTransactionVOs(
			List<PaymentTransactionVO> paymentTransactionVOs) {
		this.paymentTransactionVOs = paymentTransactionVOs;
	}
	
	private String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}
	
	public Object[][] getResultRows(){
		Object[][] rows = new Object[paymentTransactionVOs.size()][8];
		
		int row = 0;
		for(PaymentTransactionVO transactionVO : paymentTransactionVOs){
			
			rows[row][0] = transactionVO.getId();
			rows[row][1] = transactionVO.getCustomerVO().getName();
			rows[row][2] = transactionVO.getTransType().getName();
			rows[row][3] = transactionVO.getTransactionStaus();
			rows[row][4] = transactionVO.getBankVO().getName();
			rows[row][5] = transactionVO.getPaymentModeVO().getName();
			//rows[row][6] = transactionVO.getPaymentRefText();
			rows[row][6] = transactionVO.getTransactionAmt();
			rows[row][7] = getDateAsString(transactionVO.getTransactionDate());
			
			row++;
		}
		
		return rows;
	}
}
