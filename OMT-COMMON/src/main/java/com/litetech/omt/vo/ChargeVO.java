package com.litetech.omt.vo;

import java.util.Date;

import com.litetech.omt.constant.ChargeOperationEnum;

public class ChargeVO {

	public ChargeVO(long id){
		this.id = id;
	}
	
	public ChargeVO(long id, String name){
		this.id = id;
		this.name = name;
	}
	
	public ChargeVO(long id, String name, Double percentValue,
			ChargeOperationEnum operation){
		this.id = id;
		this.name = name;
		this.percentValue = percentValue;
		this.operation = operation;
	}
	
	public ChargeVO(long id, String name, Double percentValue,
			ChargeOperationEnum operation, ChargeVO applyOnCharge){
		this.id = id;
		this.name = name;
		this.percentValue = percentValue;
		this.operation = operation;
		this.applyOnCharge = applyOnCharge;
	}
	
	private long id;
	private String name;
	private Double percentValue;
	private ChargeOperationEnum operation;
	private ChargeVO applyOnCharge;
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
	
	
	public Double getPercentValue() {
		return percentValue;
	}
	public void setPercentValue(Double percentValue) {
		this.percentValue = percentValue;
	}
	public ChargeOperationEnum getOperation() {
		return operation;
	}
	public void setOperation(ChargeOperationEnum operation) {
		this.operation = operation;
	}
	public ChargeVO getApplyOnCharge() {
		return applyOnCharge;
	}
	public void setApplyOnCharge(ChargeVO applyOnCharge) {
		this.applyOnCharge = applyOnCharge;
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
