package com.litetech.omt.vo;

import java.util.Date;

import com.litetech.omt.constant.OrderStatusEnum;

public class SearchOrderRequestVO {
	private long orderId;
	private Date orderDate;
	private long customerId;
	private OrderStatusEnum orderStatus;
	private boolean purchase;
	
	public SearchOrderRequestVO(boolean purchase){
		this.purchase = purchase;
	}
	
	public boolean isPurchase() {
		return purchase;
	}
	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}
	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatusId(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
