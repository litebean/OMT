package com.litetech.omt.constant;

public enum SettingEnum {
	INVOICE_REPORT_MODE("INVOICE_REPORT_MODE", 
			"Different types of inovice to be printed"),
	PG_DUMP_EXE("PG_DUMP_EXE",
			"Location of postgres dump exe"),
	PG_DUMP_DB_NAME("PG_DUMP_DB_NAME",
			"Name of the database to be dumped"),
	PG_DUMP_USER("PG_DUMP_USER",
			"db user name"),
	PG_DUMP_PASSWORD("PG_DUMP_PASSWORD",
			"db password"),
	PG_DUMP_LOCATION("PG_DUMP_LOCATION",
			"location where the dump will be stored");		
					
	
	private String mode;
	private String desc;
	private SettingEnum(String mode, String desc){
		this.mode = mode;
		this.desc = desc;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
