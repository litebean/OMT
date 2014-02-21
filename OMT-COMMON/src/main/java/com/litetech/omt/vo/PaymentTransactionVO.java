package com.litetech.omt.vo;

import java.util.Date;
import java.util.List;

import com.litetech.omt.constant.PaymentStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;

public class PaymentTransactionVO {
	private long id;
	
	private TransactionTypeEnum transType;
	private PaymentStatusEnum transactionStaus;
	private BankVO bankVO;
	private PaymentModeVO paymentModeVO;
	private CustomerVO customerVO;
	
	private String paymentRefText;
	private double transactionAmt;
	private Date transactionDate;
	private String transactionDesc;
	
	private double unutilizedAmount;
	
	private Date creationDate;
	private Date lastModifiedDate;
	
	
	private List<InvoicePaymentVO> inovicePaymentVOs;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public TransactionTypeEnum getTransType() {
		return transType;
	}
	public void setTransType(TransactionTypeEnum transType) {
		this.transType = transType;
	}
	public BankVO getBankVO() {
		return bankVO;
	}
	public void setBankVO(BankVO bankVO) {
		this.bankVO = bankVO;
	}
	public PaymentModeVO getPaymentModeVO() {
		return paymentModeVO;
	}
	public void setPaymentModeVO(PaymentModeVO paymentModeVO) {
		this.paymentModeVO = paymentModeVO;
	}
	public CustomerVO getCustomerVO() {
		return customerVO;
	}
	public void setCustomerVO(CustomerVO customerVO) {
		this.customerVO = customerVO;
	}
	public String getPaymentRefText() {
		return paymentRefText;
	}
	public void setPaymentRefText(String paymentRefText) {
		this.paymentRefText = paymentRefText;
	}
	public double getTransactionAmt() {
		return transactionAmt;
	}
	public void setTransactionAmt(double transactionAmt) {
		this.transactionAmt = transactionAmt;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionDesc() {
		return transactionDesc;
	}
	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public List<InvoicePaymentVO> getInovicePaymentVOs() {
		return inovicePaymentVOs;
	}
	public void setInovicePaymentVOs(List<InvoicePaymentVO> inovicePaymentVOs) {
		this.inovicePaymentVOs = inovicePaymentVOs;
	}
	public double getUnutilizedAmount() {
		return unutilizedAmount;
	}
	public void setUnutilizedAmount(double unutilizedAmount) {
		this.unutilizedAmount = unutilizedAmount;
	}
	public PaymentStatusEnum getTransactionStaus() {
		return transactionStaus;
	}
	public void setTransactionStaus(PaymentStatusEnum transactionStaus) {
		this.transactionStaus = transactionStaus;
	}
	
	
	
	
}
