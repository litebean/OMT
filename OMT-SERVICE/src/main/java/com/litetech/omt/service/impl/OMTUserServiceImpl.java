package com.litetech.omt.service.impl;

import java.util.List;


import com.litetech.omt.dao.UserDAO;
import com.litetech.omt.service.OMTUserService;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.SearchUserRequestVO;

public class OMTUserServiceImpl implements OMTUserService{

	private UserDAO userDAO;
	public OMTUserServiceImpl(UserDAO userDAO){
		this.userDAO = userDAO;
	}
	
	@Override
	public List<CustomerVO> retrieveAllCustomers() {
		return userDAO.retrieveAllCustomers();
	}

	@Override
	public CustomerVO retrieveCustomer(long customerId) {
		return userDAO.retrieveCustomer(customerId);
	}

	@Override
	public void saveOrUpdateCustomer(CustomerVO customerVO) {
		userDAO.saveOrUpdateCustomer(customerVO);
	}
	
	@Override
	public List<CustomerVO> searchCustomers(SearchUserRequestVO searchUserRequestVO){
		return userDAO.searchCustomers(searchUserRequestVO);
	}
	

}
