package com.litetech.omt.vo;

import java.util.Date;

import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.constant.PaymentStatusEnum;

public class InvoicePaymentVO {
	
	private long id;
	private InvoiceVO invoiceVO;
	private long paymentTransactionId;
	private double paymentAmount;
	private PaymentStatusEnum status;
	private OMTSwitchEnum purchase;
	private Date creationDate;
	private Date lastModifiedDate;
	
	
	public InvoicePaymentVO(OMTSwitchEnum purchase){
		this.purchase = purchase;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public InvoiceVO getInvoiceVO() {
		return invoiceVO;
	}
	public void setInvoiceVO(InvoiceVO invoiceVO) {
		this.invoiceVO = invoiceVO;
	}
	public long getPaymentTransactionId() {
		return paymentTransactionId;
	}
	public void setPaymentTransactionId(long paymentTransactionId) {
		this.paymentTransactionId = paymentTransactionId;
	}
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public PaymentStatusEnum getStatus() {
		return status;
	}
	public void setStatus(PaymentStatusEnum status) {
		this.status = status;
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
	public OMTSwitchEnum getPurchase() {
		return purchase;
	}
	public void setPurchase(OMTSwitchEnum purchase) {
		this.purchase = purchase;
	}
	
	
	
	
}
