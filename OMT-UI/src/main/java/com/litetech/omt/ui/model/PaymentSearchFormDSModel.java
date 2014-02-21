package com.litetech.omt.ui.model;

import java.util.List;

import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.PaymentModeVO;

public class PaymentSearchFormDSModel {

	private List<CustomerVO> customers;
	private List<BankVO> banks;
	private List<PaymentModeVO> modes;
	public List<CustomerVO> getCustomers() {
		return customers;
	}
	public void setCustomers(List<CustomerVO> customers) {
		this.customers = customers;
	}
	public List<BankVO> getBanks() {
		return banks;
	}
	public void setBanks(List<BankVO> banks) {
		this.banks = banks;
	}
	public List<PaymentModeVO> getModes() {
		return modes;
	}
	public void setModes(List<PaymentModeVO> modes) {
		this.modes = modes;
	}
	
	
	
}
