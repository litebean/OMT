package com.litetech.omt.dao.model.core;

import java.io.Serializable;
import java.util.Date;

public class LineItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long refId;
	private int refType;
	private long productId;
	private String lineItemDesc;
	private int lineItemStatus;
	private long unitId;
	private double quantity;
	private double invoicedQty;
	private double price;
	private double totalCost;
	private double taxAmt;
	private Date creationDate;
	private Date lastModifiedDate;
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getLineItemDesc() {
		return lineItemDesc;
	}
	public void setLineItemDesc(String lineItemDesc) {
		this.lineItemDesc = lineItemDesc;
	}
	public int getLineItemStatus() {
		return lineItemStatus;
	}
	public void setLineItemStatus(int lineItemStatus) {
		this.lineItemStatus = lineItemStatus;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	
	
	public long getRefId() {
		return refId;
	}
	public void setRefId(long refId) {
		this.refId = refId;
	}
	public int getRefType() {
		return refType;
	}
	public void setRefType(int refType) {
		this.refType = refType;
	}
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public double getInvoicedQty() {
		return invoicedQty;
	}
	public void setInvoicedQty(double invoicedQty) {
		this.invoicedQty = invoicedQty;
	}
	
	
	
	
}
