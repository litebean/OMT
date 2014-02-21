package com.litetech.omt.ui.model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.util.NumberUtil;
import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;

public class PaymentTransFormModel {
	
	private long transactionId;
	private long customerId;
	private AddressVO addressVO;
	private long bankId;
	private long transactionTypeId;
	private long paymentModeId;
	private long transactionStatusId;
	private String payRefText;
	private double transactionAmount;
	private double unutilizedAmount;
	private Date transactionDate;
	private String desc;
		
	private List<InvoicePaymentVO> inovoicePaymentVOs;

	
	
	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public AddressVO getAddressVO() {
		return addressVO;
	}

	public void setAddressVO(AddressVO addressVO) {
		this.addressVO = addressVO;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public long getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public long getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(long paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public long getTransactionStatusId() {
		return transactionStatusId;
	}

	public void setTransactionStatusId(long transactionStatusId) {
		this.transactionStatusId = transactionStatusId;
	}

	
	public String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}
	
	
	
	public Object[][] getPaymentRows(){
		Object[][] rows = null;
		if(inovoicePaymentVOs != null){
			rows = new Object[inovoicePaymentVOs.size()][7];
			
			int row = 0;
			for(InvoicePaymentVO invoicePaymentVO : inovoicePaymentVOs){
				rows[row][0] = invoicePaymentVO.getId();
				rows[row][1] = invoicePaymentVO.getInvoiceVO().getOrderId();
				rows[row][2] = invoicePaymentVO.getInvoiceVO().getId();
				rows[row][3] = getDateAsString(invoicePaymentVO.getInvoiceVO().getInvoiceDate());
				double invoiceAmount = invoicePaymentVO.getInvoiceVO().getInvoiceAmount();
				double pendingAmount =  invoiceAmount - invoicePaymentVO.getInvoiceVO().getPaidAmount();
				pendingAmount = NumberUtil.roundToTwoDecimal(pendingAmount);
				rows[row][4] = invoiceAmount;
				rows[row][5] = pendingAmount;
				rows[row][6] = invoicePaymentVO.getPaymentAmount();
				
				row++;
			}
		}
		
		return rows;
	}
	

	public List<InvoicePaymentVO> getInovoicePaymentVOs() {
		return inovoicePaymentVOs;
	}

	public void setInovoicePaymentVOs(List<InvoicePaymentVO> inovoicePaymentVOs) {
		this.inovoicePaymentVOs = inovoicePaymentVOs;
	}
	
	public void addInvoicePaymentVO(InvoiceVO invoiceVO, OMTSwitchEnum purchase){
		if(this.inovoicePaymentVOs == null){
			this.inovoicePaymentVOs = new ArrayList<InvoicePaymentVO>();
		}
		
		InvoicePaymentVO invoicePaymentVO = new InvoicePaymentVO(purchase);
		invoicePaymentVO.setInvoiceVO(invoiceVO);
		invoicePaymentVO.setPaymentAmount(0);
		this.inovoicePaymentVOs.add(invoicePaymentVO);
	}
	
	public void addInvoicePaymentVOs(List<InvoiceVO> invoiceVOs, OMTSwitchEnum purchase){
		if(this.inovoicePaymentVOs == null){
			this.inovoicePaymentVOs = new ArrayList<InvoicePaymentVO>();
		}
		
		for(InvoiceVO invoiceVO : invoiceVOs){
			InvoicePaymentVO invoicePaymentVO = new InvoicePaymentVO(purchase);
			invoicePaymentVO.setInvoiceVO(invoiceVO);
			invoicePaymentVO.setPaymentAmount(0);
			this.inovoicePaymentVOs.add(invoicePaymentVO);
		}
	}

	
	
	public String getPayRefText() {
		return payRefText;
	}

	public void setPayRefText(String payRefText) {
		this.payRefText = payRefText;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	
	public String getTransactionDateAsString(){
		return getDateAsString(transactionDate);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public double getUnutilizedAmount() {
		return unutilizedAmount;
	}

	public void setUnutilizedAmount(double unutilizedAmount) {
		this.unutilizedAmount = unutilizedAmount;
	}
	
	
	
	
}
