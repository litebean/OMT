package com.litetech.omt.dao.model.core;

import java.util.Date;

public class Charge {
	
	private long id;
	private String name;
	private double percent;
	private int operation;
	private long applyOn;
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
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	public long getApplyOn() {
		return applyOn;
	}
	public void setApplyOn(long applyOn) {
		this.applyOn = applyOn;
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
