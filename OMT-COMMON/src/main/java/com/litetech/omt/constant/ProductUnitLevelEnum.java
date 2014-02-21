package com.litetech.omt.constant;

public enum ProductUnitLevelEnum {
	
	PRIMARY(1),
	SECONDARY(2);
	
	private int id;
	private ProductUnitLevelEnum(int id){
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static ProductUnitLevelEnum getById(int id){
		ProductUnitLevelEnum[] prUnitLevelEnums = ProductUnitLevelEnum.values();
		for(ProductUnitLevelEnum prUnitLevelEnum : prUnitLevelEnums){
			if(id == prUnitLevelEnum.getId()){
				return prUnitLevelEnum;
			}
		}
		return null;
	}
}
