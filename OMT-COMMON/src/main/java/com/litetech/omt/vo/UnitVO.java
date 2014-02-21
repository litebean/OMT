package com.litetech.omt.vo;

import java.util.Date;

public class UnitVO {

	private long id;
	private String name;
	private Date creationDate;
	private Date lastModifiedDate;
	
	public UnitVO(long id){
		this.id = id;
	}
	
	public UnitVO(long id, String name){
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
