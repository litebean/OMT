package com.litetech.omt.dao.mapper;

import com.litetech.omt.dao.model.user.Address;
import com.litetech.omt.dao.model.user.Customer;
import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.CustomerVO;

public class UserVODOMapper {

	
	public CustomerVO mapDOToVO(Customer customerDO){
		CustomerVO customerVO = new CustomerVO(customerDO.getId(), customerDO.getCustomerName());
		customerVO.setTinNo(customerDO.getTinNo());
		customerVO.setCstNo(customerDO.getCstNo());
		customerVO.setVendorCode(customerDO.getVendorCode());
		
		customerVO.setPrimaryAddress(
				constructAddressVO(customerDO.getAddress1()));
		
		AddressVO secAddressVO = null;
		if(customerDO.getAddress2().getId() == 
				customerDO.getAddress1().getId()){
			secAddressVO = new AddressVO();
			secAddressVO.setSameAsPrimaryAddress(true);
		}else{
			secAddressVO = constructAddressVO(customerDO.getAddress2());
		}
		
		customerVO.setSecondaryAddress(secAddressVO);
		
		return customerVO;
	}
	
	public AddressVO constructAddressVO(Address addressDO){
		AddressVO addressVO = new AddressVO();
		
		addressVO.setId(addressDO.getId());
		addressVO.setFirstName(addressDO.getFirstName());
		addressVO.setAddressLine1(addressDO.getFirstLine());
		addressVO.setAddressLine2(addressDO.getSecondLine());
		addressVO.setAddressLine3(addressDO.getThirdLine());
		
		addressVO.setCity(addressDO.getCity());
		addressVO.setState(addressDO.getState());
		addressVO.setCountry(addressDO.getCountry());
		addressVO.setPincode(addressDO.getPincode());
		
		addressVO.setPhone1(addressDO.getPhone1());
		addressVO.setPhone2(addressDO.getPhone2());
		addressVO.setPhone3(addressDO.getPhone3());
		
		return addressVO;
	}
	
	public Address constructDOFromVO(AddressVO addressVO){
		Address addressDO = new Address();
		addressDO.setId(addressVO.getId());
		populateFromVOToDO(addressVO, addressDO);
		addressDO.setCreationDate(addressVO.getCreationDate());
		addressDO.setLastModifiedDate(addressVO.getLastModifiedDate());
		
		return addressDO;
	}
	
	public void populateFromVOToDO(AddressVO addressVO, Address addressDO){
		addressDO.setFirstName(addressVO.getFirstName());
		
		addressDO.setFirstLine(addressVO.getAddressLine1());
		addressDO.setSecondLine(addressVO.getAddressLine2());
		addressDO.setThirdLine(addressVO.getAddressLine3());
		
		addressDO.setCity(addressVO.getCity());
		addressDO.setState(addressVO.getState());
		addressDO.setCountry(addressVO.getCountry());
		addressDO.setPincode(addressVO.getPincode());
		
		addressDO.setPhone1(addressVO.getPhone1());
		addressDO.setPhone2(addressVO.getPhone2());
		addressDO.setPhone3(addressVO.getPhone3());

	}
}
