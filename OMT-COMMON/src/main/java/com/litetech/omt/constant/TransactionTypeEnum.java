package com.litetech.omt.constant;

public enum TransactionTypeEnum {
	
	
	CREDIT(1, "CREDIT/SALE"),
	DEBIT(2, "DEBIT/PURCHASE");
	
	private int id;
	private String name;
	
	private TransactionTypeEnum(int id, String name){
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
	
	
	public static TransactionTypeEnum getById(final int id){
		TransactionTypeEnum[] transTypes = TransactionTypeEnum.values();
		for(TransactionTypeEnum type : transTypes){
			if(id == type.getId()){
				return type;
			}
		}
		
		return null;
	}
	
}
