package com.litetech.omt.constant;

public enum LineItemRefTypeEnum {
	ORDER(1, "Order Line Item"),
	INVOICE(2, "Invoice Line Item"),
	PURCHASE_ORDER(3, "Purchase Order Line Item"),
	PURCHASE_INVOICE(1, "Order Line Item");
	
	private final int id;
	private final String name;
	
	private  LineItemRefTypeEnum(final int id, final String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	
	public static LineItemRefTypeEnum getById(final int id){
		LineItemRefTypeEnum[] enums = LineItemRefTypeEnum.values();
		for(LineItemRefTypeEnum refType : enums){
			if(id == refType.getId()){
				return refType;
			}
		}
		return null;
	}
	
}
