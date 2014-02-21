package com.litetech.omt.vo;

import java.util.Date;

import com.litetech.omt.constant.OMTSwitchEnum;

public class PaymentModeVO {
	
	public PaymentModeVO(long id){
		this.id = id;
	}
	
	public PaymentModeVO(long id, String name){
		this.id = id;
		this.name = name;
	}
	
	private long id;
	private String name;
	private String desc;
	private OMTSwitchEnum active;
	private Date creationDate;
	private Date lastModifiedDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public OMTSwitchEnum getActive() {
		return active;
	}

	public void setActive(OMTSwitchEnum active) {
		this.active = active;
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
