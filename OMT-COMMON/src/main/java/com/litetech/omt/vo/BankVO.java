package com.litetech.omt.vo;

import java.util.Date;

import com.litetech.omt.constant.OMTSwitchEnum;

public class BankVO {
	
	private long id;
	private String name;
	private OMTSwitchEnum active;
	private Date creationDate;
	private Date lastModifiedDate;
	
	
	public BankVO(long id){
		this.id = id;
	}
	
	public BankVO(long id, String name){
		this.id = id;
		this.name = name;
	}
	
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
