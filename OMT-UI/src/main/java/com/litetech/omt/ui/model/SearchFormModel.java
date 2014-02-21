package com.litetech.omt.ui.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.vo.OrderVO;

public class SearchFormModel {

	
	
	private Long orderId;
	private Date orderDate;
	private int selectedStatusId;
	private long selectedCustomerId;
	private int selectedPaymentStatus;
	private List<OrderVO> result;
	private Map<Long, String> orderedCustomerMap;
	
	
	public List<OrderVO> getResult() {
		return result;
	}

	public void setResult(List<OrderVO> result) {
		this.result = result;
	}

	public String getOrderDateAsString(){
		return getDateAsString(this.orderDate);
	}
	
	private String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getSelectedStatusId() {
		return selectedStatusId;
	}

	public void setSelectedStatusId(int selectedStatusId) {
		this.selectedStatusId = selectedStatusId;
	}

	public long getSelectedCustomerId() {
		return selectedCustomerId;
	}

	public void setSelectedCustomerId(long selectedCustomerId) {
		this.selectedCustomerId = selectedCustomerId;
	}

	public int getSelectedPaymentStatus() {
		return selectedPaymentStatus;
	}

	public void setSelectedPaymentStatus(int selectedPaymentStatus) {
		this.selectedPaymentStatus = selectedPaymentStatus;
	}
		
	
	public String[][] getResultRows(){
		String[][] rows = new String[result.size()][4];
		
		int rowFilled = 0;
		for(OrderVO orderVO : result){
			rows[rowFilled][0] = String.valueOf(orderVO.getId());
			rows[rowFilled][1] = orderedCustomerMap.get(orderVO.getCustomerId());
			rows[rowFilled][2] = getDateAsString(orderVO.getOrderDate());
			rows[rowFilled][3] = orderVO.getOrderStatus().getName();
			//rows[rowFilled][4] = "-";		
			
			rowFilled++;		
		}
		
		return rows;
	}

	public Map<Long, String> getOrderedCustomerMap() {
		return orderedCustomerMap;
	}

	public void setOrderedCustomerMap(Map<Long, String> orderedCustomerMap) {
		this.orderedCustomerMap = orderedCustomerMap;
	}
	
	
	
}
