package com.litetech.omt.constant;

import java.util.List;

public enum ChargeOperationEnum {
	
	ADD(1, "Addition"),
	SUB(2, "Substraction"),
	PERCENTAGE(3, "Percentage");
	
	
	private int id;
	private String desc;
		
	private ChargeOperationEnum(final int id, final String desc){
		this.id = id;
		this.desc = desc;
	}
	
	
	
	
	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getDesc() {
		return desc;
	}




	public void setDesc(String desc) {
		this.desc = desc;
	}




	public static ChargeOperationEnum getEnumById(final int id){
		ChargeOperationEnum[]  operations = values();
		for(ChargeOperationEnum operationEnum : operations){
			if(operationEnum.getId() == id){
				return operationEnum;
			}
		}
		
		return null;
	}
	
	
	
	
	
}
