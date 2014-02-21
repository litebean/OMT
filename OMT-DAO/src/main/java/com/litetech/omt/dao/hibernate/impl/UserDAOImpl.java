package com.litetech.omt.dao.hibernate.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.litetech.omt.dao.UserDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalOrgDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalUserDAO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.OrgVO;
import com.litetech.omt.vo.SearchUserRequestVO;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UserDAOImpl implements UserDAO{

	private InternalUserDAO internalUserDAO;
	private InternalOrgDAO internalOrgDAO;
	public UserDAOImpl(InternalUserDAO internalUserDAO, 
			InternalOrgDAO internalOrgDAO){
		this.internalUserDAO = internalUserDAO;
		this.internalOrgDAO = internalOrgDAO;
	}
	
	@Override
	public List<CustomerVO> retrieveAllCustomers() {
		return internalUserDAO.retrieveAllCustomers();
	}
 
	@Override
	public CustomerVO retrieveCustomer(long customerId) {
		return internalUserDAO.retrieveCustomer(customerId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveOrUpdateCustomer(CustomerVO customerVO) {
		internalUserDAO.saveCustomerAddress(customerVO);		
	}
	
	@Override
	public List<CustomerVO> searchCustomers(SearchUserRequestVO searchUserRequestVO){
		return internalUserDAO.searchCustomers(searchUserRequestVO);
	}

	@Override
	public OrgVO retrieveOrgMetaData() {
		return internalOrgDAO.retrieveOrg();
	}
	
	

}
