package com.litetech.omt.dao.model.payment;

import java.io.Serializable;
import java.util.Date;

import com.litetech.omt.dao.model.user.Customer;

public class PaymentTransaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private int transactionType;
	
	private Customer customer;
	private PaymentMode paymentMode;
	private String paymentModeRefText;
	
	private Bank paymentBank;
	private double transactionAmount;
	private double unUtilizedAmount;
	private Date transactionDate;
	private String transactionDesc;
	private int status;
	private Date creationDate;
	private Date lastModifiedDate;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getPaymentModeRefText() {
		return paymentModeRefText;
	}
	public void setPaymentModeRefText(String paymentModeRefText) {
		this.paymentModeRefText = paymentModeRefText;
	}
	public Bank getPaymentBank() {
		return paymentBank;
	}
	public void setPaymentBank(Bank paymentBank) {
		this.paymentBank = paymentBank;
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
	public double getUnUtilizedAmount() {
		return unUtilizedAmount;
	}
	public void setUnUtilizedAmount(double unUtilizedAmount) {
		this.unUtilizedAmount = unUtilizedAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
