package com.litetech.omt.dao.model.core;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long orderId;
	private Date invoiceDate;
	private int invoiceStatus;
	private long shipToAdd;
	private long billToAdd;
	private String invDesc;
	private String dcNo;
	private Date dcDate;
	private long customerId;
	private long taxId;
	private double invoiceAmount;
	private Double paidAmount;
	private double invoiceAmountExclCharges;
	private Date creationDate;
	private Date lastModifiedDate;
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}	
	public long getBillToAdd() {
		return billToAdd;
	}
	public void setBillToAdd(long billToAdd) {
		this.billToAdd = billToAdd;
	}
	public long getShipToAdd() {
		return shipToAdd;
	}
	public void setShipToAdd(long shipToAdd) {
		this.shipToAdd = shipToAdd;
	}
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	
	
	public Date getDcDate() {
		return dcDate;
	}
	public void setDcDate(Date dcDate) {
		this.dcDate = dcDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInvDesc() {
		return invDesc;
	}
	public void setInvDesc(String invDesc) {
		this.invDesc = invDesc;
	}
	public double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public int getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public long getTaxId() {
		return taxId;
	}
	public void setTaxId(long taxId) {
		this.taxId = taxId;
	}
	
	public double getInvoiceAmountExclCharges() {
		return invoiceAmountExclCharges;
	}
	public void setInvoiceAmountExclCharges(double invoiceAmountExclCharges) {
		this.invoiceAmountExclCharges = invoiceAmountExclCharges;
	}
	public double getPaidAmount() {
		if(paidAmount == null){
			this.paidAmount = 0d;
		}
		return paidAmount;
	}
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	
	
	
	
	
}
