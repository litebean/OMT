package com.litetech.omt.dao;

import java.util.List;

import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.OrgVO;
import com.litetech.omt.vo.SearchUserRequestVO;

public interface UserDAO {
	public List<CustomerVO> retrieveAllCustomers();
	
	public CustomerVO retrieveCustomer(final long customerId);
	
	public void saveOrUpdateCustomer(CustomerVO customerVO);
	
	public List<CustomerVO> searchCustomers(SearchUserRequestVO searchUserRequestVO);
	
	public OrgVO retrieveOrgMetaData();
}
