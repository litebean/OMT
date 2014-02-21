package com.litetech.omt.ui.model.ds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.litetech.omt.vo.CustomerVO;

public class SearchFormDSModel {
	
	private List<CustomerVO> customers;
	private Map<Long, String> customerNameMap;
	public List<CustomerVO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerVO> customers) {
		this.customers = customers;
		setCustomerMap();
	}

	public Map<Long, String> getCustomerNameMap() {
		return customerNameMap;
	}

	public void setCustomerNameMap(Map<Long, String> customerNameMap) {
		this.customerNameMap = customerNameMap;
	}
	
	
	private void setCustomerMap(){
		this.customerNameMap = new HashMap<Long, String>();
		for(CustomerVO customerVO : customers){
			this.customerNameMap.put(customerVO.getId(), customerVO.getName());
		}
	}
	
	
	

}
