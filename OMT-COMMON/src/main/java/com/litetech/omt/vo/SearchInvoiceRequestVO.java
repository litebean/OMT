package com.litetech.omt.vo;

import java.util.Date;

import com.litetech.omt.constant.InvoiceStatusEnum;

public class SearchInvoiceRequestVO {
	
	private long invoiceId;
	private Date invoiceDate;
	private long customerId;
	private InvoiceStatusEnum invoiceStatus;
	private boolean purchase;
	
	public SearchInvoiceRequestVO(boolean purchase){
		this.purchase = purchase;
	}
	
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public InvoiceStatusEnum getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(InvoiceStatusEnum invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public boolean isPurchase() {
		return purchase;
	}

	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}
	
	
	
	
}
