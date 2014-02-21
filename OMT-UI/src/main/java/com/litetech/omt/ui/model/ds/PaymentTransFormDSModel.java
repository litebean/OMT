package com.litetech.omt.ui.model.ds;

import java.util.List;

import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.PaymentModeVO;

public class PaymentTransFormDSModel {

	private List<CustomerVO> customerVOLs;
	private List<BankVO> bankVOs;
	private List<PaymentModeVO> paymentModeVOs;
	
	
	public List<CustomerVO> getCustomerVOLs() {
		return customerVOLs;
	}

	public void setCustomerVOLs(List<CustomerVO> customerVOLs) {
		this.customerVOLs = customerVOLs;
	}

	public List<BankVO> getBankVOs() {
		return bankVOs;
	}

	public void setBankVOs(List<BankVO> bankVOs) {
		this.bankVOs = bankVOs;
	}

	public List<PaymentModeVO> getPaymentModeVOs() {
		return paymentModeVOs;
	}

	public void setPaymentModeVOs(List<PaymentModeVO> paymentModeVOs) {
		this.paymentModeVOs = paymentModeVOs;
	}
	
	
	
	
	
	
}
