package com.litetech.omt.constant;

public enum PaymentStatusEnum {
	
	CONFIRMED(1),
	CANCELLED(2);
	
	private int id;
	private PaymentStatusEnum(int id){
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public static PaymentStatusEnum getById(int id){
		PaymentStatusEnum[] statuses = PaymentStatusEnum.values();
		for(PaymentStatusEnum status : statuses){
			if(id == status.getId()){
				return status;
			}
		}
		return null;
	}
	
	
}
