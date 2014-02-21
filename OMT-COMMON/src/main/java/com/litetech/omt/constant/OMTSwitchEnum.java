package com.litetech.omt.constant;

public enum OMTSwitchEnum {

	ON(1, "Active"),
	OFF(2, "Inactive");

	
	private int id;
	private String name;
	
	private OMTSwitchEnum(final int id, final String name){
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
	
	public static OMTSwitchEnum getById(final int id){
		OMTSwitchEnum[] statusEnums = OMTSwitchEnum.values();
		for(OMTSwitchEnum statusEnum : statusEnums){
			if(id == statusEnum.getId()){
				return statusEnum;
			}
		}
		
		return null;
	}
}
