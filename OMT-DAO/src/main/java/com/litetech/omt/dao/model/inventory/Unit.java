package com.litetech.omt.dao.model.inventory;

import java.util.Date;

public class Unit {

	private long id;
	private String name;
	private Date creationDate;
	private Date lastModifiedDate;

	public Unit(){
		
	}
	
	public Unit(long id){
		this.id = id;
	}
	
	public Unit(long id, String name){
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
