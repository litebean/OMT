package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.litetech.omt.dao.mapper.UserVODOMapper;
import com.litetech.omt.dao.model.user.Address;
import com.litetech.omt.dao.model.user.Customer;
import com.litetech.omt.util.StringUtil;
import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.SearchUserRequestVO;

public class InternalUserDAO {

	private SessionFactory sessionFactory;
	private UserVODOMapper userVODOMapper;
	
	public InternalUserDAO(SessionFactory sessionFactory, 
			UserVODOMapper userVODOMapper){
		this.sessionFactory = sessionFactory;
		this.userVODOMapper = userVODOMapper;
	}
	
	
	public List<CustomerVO> retrieveAllCustomers(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchAllCustomers");
		List<Customer> customerDOs = query.list();
		
		List<CustomerVO> customerVOs = new ArrayList<CustomerVO>();
		for(Customer customerDO : customerDOs){
			customerVOs.add(userVODOMapper.mapDOToVO(customerDO));
		}
		
		return customerVOs;
	}
	
	
	public CustomerVO retrieveCustomer(final long customerId){
		Session session = sessionFactory.getCurrentSession();
		Customer customerDO = (Customer)session.load(Customer.class, customerId);
		return userVODOMapper.mapDOToVO(customerDO);
	}
	
	
	public void saveCustomerAddress(CustomerVO customerVO){
		//save primary address
		AddressVO primaryAddressVO = customerVO.getPrimaryAddress();
		saveOrUpdateAddress(primaryAddressVO);
		
		AddressVO secAddressVO = customerVO.getSecondaryAddress();
		if(secAddressVO == null || secAddressVO.isSameAsPrimaryAddress()){
			secAddressVO.setId(primaryAddressVO.getId());
		}else{
			saveOrUpdateAddress(secAddressVO);
		}
		saveOrUpdateCustomer(customerVO);
	}
	
	private void saveOrUpdateCustomer(CustomerVO customerVO){
		Session session = sessionFactory.getCurrentSession();
		Customer customerDO = null;
		Date currentTime = new Date();
		if(customerVO.getId() > 0){
			customerDO = (Customer)session.load(Customer.class, customerVO.getId());
		}else{
			customerDO = new Customer();
			customerDO.setCreationDate(currentTime);
		}
		customerDO.setCustomerName(customerVO.getName());
		customerDO.setTinNo(customerVO.getTinNo());
		customerDO.setCstNo(customerVO.getCstNo());
		customerDO.setVendorCode(customerVO.getVendorCode());
		customerDO.setLastModifiedDate(currentTime);
		
		Address address1 = new Address();
		address1.setId(customerVO.getPrimaryAddress().getId());
		
		Address address2 = new Address();
		address2.setId(customerVO.getSecondaryAddress().getId());
		
		customerDO.setAddress1(address1);
		customerDO.setAddress2(address2);
		
		session.saveOrUpdate(customerDO);
		
		//reset id
		customerVO.setId(customerDO.getId());
	}
	
	
	private void saveOrUpdateAddress(AddressVO addressVO){
		Session session = sessionFactory.getCurrentSession();
		Address addressDO = null;
		 
		Date currentTime = new Date();
		if(addressVO.getId() > 0){
			addressDO = (Address)session.load(Address.class, addressVO.getId());
			userVODOMapper.populateFromVOToDO(addressVO, addressDO);
		}else{
			addressVO.setCreationDate(currentTime);
			addressDO = userVODOMapper.constructDOFromVO(addressVO);
		}
		addressDO.setLastModifiedDate(currentTime);
		session.saveOrUpdate(addressDO);
		
		//reset id
		addressVO.setId(addressDO.getId());
	}
	
	
	
	public List<CustomerVO> searchCustomers(SearchUserRequestVO searchUserRequestVO){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Customer.class, "customer")	
							.createAlias("customer.address1", "primaryAdd")
							.createAlias("customer.address2", "secAdd");
		
		if(searchUserRequestVO.getCustomerId() > 0){
			criteria.add(Restrictions.eq("customer.id", searchUserRequestVO.getCustomerId()));
		}
		
		if(!StringUtil.isNullOrEmpty(searchUserRequestVO.getCustomerName())){
			criteria.add(Restrictions.like("customer.customerName", searchUserRequestVO.getCustomerName(), MatchMode.ANYWHERE));
		}
		
		if(!StringUtil.isNullOrEmpty(searchUserRequestVO.getTinNo())){
			criteria.add(Restrictions.like("customer.tinNo", searchUserRequestVO.getTinNo(), MatchMode.ANYWHERE));
		}
		
		if(!StringUtil.isNullOrEmpty(searchUserRequestVO.getContactName())){
			Criterion primaryAddCrit = Restrictions.like("primaryAdd.firstName", searchUserRequestVO.getContactName(), MatchMode.ANYWHERE);
			Criterion secAddCrit = Restrictions.like("secAdd.firstName", searchUserRequestVO.getContactName(), MatchMode.ANYWHERE);
			
			criteria.add(Restrictions.or(primaryAddCrit, secAddCrit));
		}
		
		if(!StringUtil.isNullOrEmpty(searchUserRequestVO.getCity())){
			Criterion primaryAddCrit = Restrictions.like("primaryAdd.city", searchUserRequestVO.getCity(), MatchMode.ANYWHERE);
			Criterion secAddCrit = Restrictions.like("secAdd.city", searchUserRequestVO.getCity(), MatchMode.ANYWHERE);
			
			criteria.add(Restrictions.or(primaryAddCrit, secAddCrit));
		}
		
		if(!StringUtil.isNullOrEmpty(searchUserRequestVO.getState())){
			Criterion primaryAddCrit = Restrictions.like("primaryAdd.state", searchUserRequestVO.getState(), MatchMode.ANYWHERE);
			Criterion secAddCrit = Restrictions.like("secAdd.state", searchUserRequestVO.getState(), MatchMode.ANYWHERE);
			
			criteria.add(Restrictions.or(primaryAddCrit, secAddCrit));
		}
		
		if(!StringUtil.isNullOrEmpty(searchUserRequestVO.getCountry())){
			Criterion primaryAddCrit = Restrictions.like("primaryAdd.country", searchUserRequestVO.getCountry(), MatchMode.ANYWHERE);
			Criterion secAddCrit = Restrictions.like("secAdd.country", searchUserRequestVO.getCountry(), MatchMode.ANYWHERE);
			
			criteria.add(Restrictions.or(primaryAddCrit, secAddCrit));
		}
		
		if(!StringUtil.isNullOrEmpty(searchUserRequestVO.getPincode())){
			Criterion primaryAddCrit = Restrictions.like("primaryAdd.pincode", searchUserRequestVO.getPincode(), MatchMode.ANYWHERE);
			Criterion secAddCrit = Restrictions.like("secAdd.pincode", searchUserRequestVO.getPincode(), MatchMode.ANYWHERE);
			
			criteria.add(Restrictions.or(primaryAddCrit, secAddCrit));
		}
		
		if(!StringUtil.isNullOrEmpty(searchUserRequestVO.getPhone())){
			Criterion primaryPhone1 = Restrictions.like("primaryAdd.phone1", searchUserRequestVO.getPhone(), MatchMode.ANYWHERE);
			Criterion primaryPhone2 = Restrictions.like("primaryAdd.phone2", searchUserRequestVO.getPhone(), MatchMode.ANYWHERE);
			Criterion secPhone1 = Restrictions.like("secAdd.phone1", searchUserRequestVO.getPhone(), MatchMode.ANYWHERE);
			Criterion secPhone2 = Restrictions.like("secAdd.phone2", searchUserRequestVO.getPhone(), MatchMode.ANYWHERE);
			
			criteria.add(Restrictions.or(primaryPhone1, primaryPhone2, secPhone1, secPhone2));
		}
		
		List<Customer> customerDOs = criteria.list();
		List<CustomerVO> customerVOs = new ArrayList<CustomerVO>();
		for(Customer customerDO : customerDOs){
			customerVOs.add(userVODOMapper.mapDOToVO(customerDO));
		}
		
		return customerVOs;
	}
	
}
