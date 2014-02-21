package com.litetech.omt.constant;

public enum InvoiceStatusEnum {

	PENDING(1, "Pending"),
	PARTIAL_PAYMENT(2, "Partial Payment"),
	COMPLETED(3, "Completed"),
	CANCELLED(4, "Cancelled");	
	
	
	private int id;
	private String name;
	private InvoiceStatusEnum(final int id, final String name){
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public static InvoiceStatusEnum getById(final int id){
		InvoiceStatusEnum[] statuses = values();
		
		for (int i = 0; i < statuses.length; i++) {
			if(statuses[i].getId() == id){
				return statuses[i];
			}
		}
		return null;
	}
	
}
