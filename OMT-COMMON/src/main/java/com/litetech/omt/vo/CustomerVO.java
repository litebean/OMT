package com.litetech.omt.vo;

public class CustomerVO {

	public CustomerVO(long id){
		this.id = id;
	}
	
	public CustomerVO(long id, String name){
		this.id = id;
		this.name = name;
	}
	
	private long id;
	private String name;
	private AddressVO primaryAddress;
	private AddressVO secondaryAddress;
	private String tinNo;
	private String cstNo;
	private String vendorCode;
	
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
	public AddressVO getPrimaryAddress() {
		return primaryAddress;
	}
	public void setPrimaryAddress(AddressVO primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public AddressVO getSecondaryAddress() {
		return secondaryAddress;
	}

	public void setSecondaryAddress(AddressVO secondaryAddress) {
		this.secondaryAddress = secondaryAddress;
	}

	public String getTinNo() {
		return tinNo;
	}

	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}
	
	

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	
	
	
	
}
