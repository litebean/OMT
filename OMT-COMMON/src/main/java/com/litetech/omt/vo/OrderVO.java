package com.litetech.omt.vo;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.litetech.omt.constant.OrderStatusEnum;

public class OrderVO {

	
	private long id;
	private Date orderDate;
	private OrderStatusEnum orderStatus;
	private long customerId;
	private String orderDesc;
	private long taxId;
	private double orderAmount;
	private Date creationDate;
	private Date lastModifiedDate;
	private List<LineItemVO> orderLineItems = Collections.emptyList();
	private List<InvoiceVO> invoices = Collections.emptyList();
	private boolean purchase;
	private String partyOrderId;
	private Date partyOrderDate;
	
	public OrderVO(boolean purchase){
		this.purchase = purchase;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public long getTaxId() {
		return taxId;
	}
	public void setTaxId(long taxId) {
		this.taxId = taxId;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
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
	public List<LineItemVO> getOrderLineItems() {
		return orderLineItems;
	}
	public void setOrderLineItems(List<LineItemVO> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	public List<InvoiceVO> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<InvoiceVO> invoices) {
		this.invoices = invoices;
	}
	public boolean isPurchase() {
		return purchase;
	}
	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
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
