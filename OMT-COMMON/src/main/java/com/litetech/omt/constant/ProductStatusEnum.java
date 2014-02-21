package com.litetech.omt.constant;

public enum ProductStatusEnum {
	
	INACTIVE(0),
	ACTIVE(1);
	
	
	private int id;
	private ProductStatusEnum(int id){
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static ProductStatusEnum getById(int id){
		ProductStatusEnum[] statuses = ProductStatusEnum.values();
		for(ProductStatusEnum status : statuses){
			if(id == status.getId()){
				return status;
			}
		}
		return null;
	}
}
