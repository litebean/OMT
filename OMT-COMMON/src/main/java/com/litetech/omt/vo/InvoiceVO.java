package com.litetech.omt.vo;

import java.util.Date;
import java.util.List;

public class InvoiceVO {
	
	public InvoiceVO(boolean purchase){
		this.purchase = purchase;
	}
	
	public InvoiceVO(long id, boolean purchase){
		this.id = id;
		this.purchase = purchase;
	}
	
	private boolean purchase;
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
	private double paidAmount;
	private double invoiceAmountExclCharges;
	private Date creationDate;
	private Date lastModifiedDate;
	
	private List<LineItemVO> invoiceLineItems;
	private List<InvoiceChargeVO> invoiceCharges;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
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
	public long getShipToAdd() {
		return shipToAdd;
	}
	public void setShipToAdd(long shipToAdd) {
		this.shipToAdd = shipToAdd;
	}
	public long getBillToAdd() {
		return billToAdd;
	}
	public void setBillToAdd(long billToAdd) {
		this.billToAdd = billToAdd;
	}
	public String getInvDesc() {
		return invDesc;
	}
	public void setInvDesc(String invDesc) {
		this.invDesc = invDesc;
	}
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	public long getTaxId() {
		return taxId;
	}
	public void setTaxId(long taxId) {
		this.taxId = taxId;
	}
	public double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
		
	public double getInvoiceAmountExclCharges() {
		return invoiceAmountExclCharges;
	}
	public void setInvoiceAmountExclCharges(double invoiceAmountExclCharges) {
		this.invoiceAmountExclCharges = invoiceAmountExclCharges;
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
	public List<LineItemVO> getInvoiceLineItems() {
		return invoiceLineItems;
	}
	public void setInvoiceLineItems(List<LineItemVO> invoiceLineItems) {
		this.invoiceLineItems = invoiceLineItems;
	}
	public List<InvoiceChargeVO> getInvoiceCharges() {
		return invoiceCharges;
	}
	public void setInvoiceCharges(List<InvoiceChargeVO> invoiceCharges) {
		this.invoiceCharges = invoiceCharges;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public boolean isPurchase() {
		return purchase;
	}

	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Date getDcDate() {
		return dcDate;
	}

	public void setDcDate(Date dcDate) {
		this.dcDate = dcDate;
	}
	
	
	
}
