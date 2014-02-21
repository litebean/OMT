package com.litetech.omt.constant;


public enum OrderStatusEnum {
	
	PENDING(1, "Pending"),
	PARTIAL_INVOCIED(2, "Partial Invoiced"),
	COMPLETED(3, "Completed"),
	CANCELLED(4, "Cancelled");
	
	
	
	private final int id;
	private final String name;
	 
	
	private OrderStatusEnum(int id, String name){
		this.id = id;
		this.name = name;
	}
		
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	
	public static OrderStatusEnum getById(final int id){
		OrderStatusEnum[] orderStatuses = OrderStatusEnum.values();
		for(OrderStatusEnum orderStatus : orderStatuses){
			if(orderStatus.getId() == id){
				return orderStatus;
			}
		}
		return null;
	}
	
}
