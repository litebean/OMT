package com.litetech.omt.vo;

import java.util.Date;

public class SettingVO {

	private long settingId;
	private String key;
	private String value;
	private String desc;
	private Date creationDate;
	private Date lastModifiedDate;
	public long getSettingId() {
		return settingId;
	}
	public void setSettingId(long settingId) {
		this.settingId = settingId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	
}
