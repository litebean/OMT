package com.litetech.omt.ui.model.ds;

import java.util.List;
import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.ProductUnitVO;


public class OrderFormDSModel {
	
	private List<CustomerVO> customers;
	private List<ChargeVO> salesAcctVOs;
	
	private List<ProductVO> productVOs;
	
	
	
	public List<CustomerVO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerVO> customers) {
		this.customers = customers;
	}

	public List<ChargeVO> getSalesAcctVOs() {
		return salesAcctVOs;
	}

	public void setSalesAcctVOs(List<ChargeVO> salesAcctVOs) {
		this.salesAcctVOs = salesAcctVOs;
	}
		
	public List<ProductVO> getProductVOs(){
		return this.productVOs;
	}

	public void setProductVOs(List<ProductVO> productVOs) {
		this.productVOs = productVOs;
	}	

}
