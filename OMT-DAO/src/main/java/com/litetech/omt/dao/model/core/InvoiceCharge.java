package com.litetech.omt.dao.model.core;

import java.util.Date;

public class InvoiceCharge {
	
	private long id;
	private long invoiceId;
	private long chargeId;
	private double amount;
	private Date creationDate;
	private Date lastModifiedDate;
	private int isPurchase;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public long getChargeId() {
		return chargeId;
	}
	public void setChargeId(long chargeId) {
		this.chargeId = chargeId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public int getIsPurchase() {
		return isPurchase;
	}
	public void setIsPurchase(int isPurchase) {
		this.isPurchase = isPurchase;
	}
	
	
	
	
}
