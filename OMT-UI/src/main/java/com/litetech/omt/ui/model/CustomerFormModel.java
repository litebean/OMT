package com.litetech.omt.ui.model;

import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.CustomerVO;

public class CustomerFormModel {
	
	private boolean createCustomer;
	
	public CustomerFormModel(){
		createCustomer = true;
	}
	
	public CustomerFormModel(CustomerVO customerVO){
		this.customerId = customerVO.getId();
		this.customerName = customerVO.getName();
		this.tinNo = customerVO.getTinNo();
		this.cstNo = customerVO.getCstNo();
		this.vendorCode = customerVO.getVendorCode();
		
		AddressVO primaryAddressVO = customerVO.getPrimaryAddress();
		this.add1Id = primaryAddressVO.getId();
		this.add1ContactName = primaryAddressVO.getFirstName();
		this.add1Line1 = primaryAddressVO.getAddressLine1();
		this.add1Line2 = primaryAddressVO.getAddressLine2();
		this.add1Line3 = primaryAddressVO.getAddressLine3();
		
		this.add1City = primaryAddressVO.getCity();
		this.add1State = primaryAddressVO.getState();
		this.add1Country = primaryAddressVO.getCountry();
		this.add1Pincode = primaryAddressVO.getPincode();
		this.add1phone1 = primaryAddressVO.getPhone1();
		this.add1phone2 = primaryAddressVO.getPhone2();
		
		
		AddressVO secAddressVO = customerVO.getSecondaryAddress();
		if(secAddressVO != null){
			if(secAddressVO.isSameAsPrimaryAddress() || secAddressVO.getId() == this.add1Id){
				this.isSameAsAdd1 = true;
			}else{
				this.add2Id = secAddressVO.getId(); 
				this.add2ContactName = secAddressVO.getFirstName();
				this.add2Line1 = secAddressVO.getAddressLine1();
				this.add2Line2 = secAddressVO.getAddressLine2();
				this.add2Line3 = secAddressVO.getAddressLine3();
				
				this.add2City = secAddressVO.getCity();
				this.add2State = secAddressVO.getState();
				this.add2Country = secAddressVO.getCountry();
				this.add2Pincode = secAddressVO.getPincode();
				this.add2phone1 = secAddressVO.getPhone1();
				this.add2phone2 = secAddressVO.getPhone2();
			}
		}else{
			this.isSameAsAdd1 = true;
		}
	}
	
	private long customerId;
	private String customerName;
	private String tinNo;
	private String cstNo;
	private String vendorCode;
	
	private long add1Id;
	private String add1ContactName;
	private String add1Line1;
	private String add1Line2;
	private String add1Line3;
	private String add1City;
	private String add1State;
	private String add1Country;
	private String add1Pincode;
	private String add1phone1;
	private String add1phone2;
	
	private boolean isSameAsAdd1;
	private long add2Id;
	private String add2ContactName;
	private String add2Line1;
	private String add2Line2;
	private String add2Line3;
	private String add2City;
	private String add2State;
	private String add2Country;
	private String add2Pincode;
	private String add2phone1;
	private String add2phone2;
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public long getAdd1Id() {
		return add1Id;
	}
	public void setAdd1Id(long add1Id) {
		this.add1Id = add1Id;
	}
	public String getAdd1ContactName() {
		return add1ContactName;
	}
	public void setAdd1ContactName(String add1ContactName) {
		this.add1ContactName = add1ContactName;
	}
	public String getAdd1Line1() {
		return add1Line1;
	}
	public void setAdd1Line1(String add1Line1) {
		this.add1Line1 = add1Line1;
	}
	public String getAdd1Line2() {
		return add1Line2;
	}
	public void setAdd1Line2(String add1Line2) {
		this.add1Line2 = add1Line2;
	}
	public String getAdd1Line3() {
		return add1Line3;
	}
	public void setAdd1Line3(String add1Line3) {
		this.add1Line3 = add1Line3;
	}
	public String getAdd1City() {
		return add1City;
	}
	public void setAdd1City(String add1City) {
		this.add1City = add1City;
	}
	public String getAdd1State() {
		return add1State;
	}
	public void setAdd1State(String add1State) {
		this.add1State = add1State;
	}
	public String getAdd1Country() {
		return add1Country;
	}
	public void setAdd1Country(String add1Country) {
		this.add1Country = add1Country;
	}
	public String getAdd1Pincode() {
		return add1Pincode;
	}
	public void setAdd1Pincode(String add1Pincode) {
		this.add1Pincode = add1Pincode;
	}
	public String getAdd1phone1() {
		return add1phone1;
	}
	public void setAdd1phone1(String add1phone1) {
		this.add1phone1 = add1phone1;
	}
	public String getAdd1phone2() {
		return add1phone2;
	}
	public void setAdd1phone2(String add1phone2) {
		this.add1phone2 = add1phone2;
	}

	public long getAdd2Id() {
		return add2Id;
	}

	public void setAdd2Id(long add2Id) {
		this.add2Id = add2Id;
	}

	public String getAdd2ContactName() {
		return add2ContactName;
	}

	public void setAdd2ContactName(String add2ContactName) {
		this.add2ContactName = add2ContactName;
	}

	public String getAdd2Line1() {
		return add2Line1;
	}

	public void setAdd2Line1(String add2Line1) {
		this.add2Line1 = add2Line1;
	}

	public String getAdd2Line2() {
		return add2Line2;
	}

	public void setAdd2Line2(String add2Line2) {
		this.add2Line2 = add2Line2;
	}

	public String getAdd2Line3() {
		return add2Line3;
	}

	public void setAdd2Line3(String add2Line3) {
		this.add2Line3 = add2Line3;
	}

	public String getAdd2City() {
		return add2City;
	}

	public void setAdd2City(String add2City) {
		this.add2City = add2City;
	}

	public String getAdd2State() {
		return add2State;
	}

	public void setAdd2State(String add2State) {
		this.add2State = add2State;
	}

	public String getAdd2Country() {
		return add2Country;
	}

	public void setAdd2Country(String add2Country) {
		this.add2Country = add2Country;
	}

	public String getAdd2Pincode() {
		return add2Pincode;
	}

	public void setAdd2Pincode(String add2Pincode) {
		this.add2Pincode = add2Pincode;
	}

	public String getAdd2phone1() {
		return add2phone1;
	}

	public void setAdd2phone1(String add2phone1) {
		this.add2phone1 = add2phone1;
	}

	public String getAdd2phone2() {
		return add2phone2;
	}

	public void setAdd2phone2(String add2phone2) {
		this.add2phone2 = add2phone2;
	}

	public boolean isSameAsAdd1() {
		return isSameAsAdd1;
	}

	public void setSameAsAdd1(boolean isSameAsAdd1) {
		this.isSameAsAdd1 = isSameAsAdd1;
	}

	public boolean isCreateCustomer() {
		return createCustomer;
	}

	public void setCreateCustomer(boolean createCustomer) {
		this.createCustomer = createCustomer;
	}
	
	
	
}
