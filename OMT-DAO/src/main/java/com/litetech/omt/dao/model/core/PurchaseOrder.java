package com.litetech.omt.dao.model.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class PurchaseOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Date orderDate;
	private String partyOrderId;
	private Date partyOrderDate;
	private int orderStatus;
	private long customerId;
	private String orderDesc;
	private long taxId;
	private double orderAmount;
	private Date creationDate;
	private Date lastModifiedDate;
	
	private Set<LineItem> orderLineItems;

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
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

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Set<LineItem> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(Set<LineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getTaxId() {
		return taxId;
	}

	public void setTaxId(long taxId) {
		this.taxId = taxId;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getPartyOrderId() {
		return partyOrderId;
	}

	public void setPartyOrderId(String partyOrderId) {
		this.partyOrderId = partyOrderId;
	}

	public Date getPartyOrderDate() {
		return partyOrderDate;
	}

	public void setPartyOrderDate(Date partyOrderDate) {
		this.partyOrderDate = partyOrderDate;
	}	
	
	
	

}
