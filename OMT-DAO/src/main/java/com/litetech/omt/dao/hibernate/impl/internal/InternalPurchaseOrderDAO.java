package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.litetech.omt.dao.mapper.PurchaseOrderVODOMapper;
import com.litetech.omt.dao.model.core.Order;
import com.litetech.omt.dao.model.core.PurchaseOrder;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.SearchOrderRequestVO;

public class InternalPurchaseOrderDAO {

	private SessionFactory sessionFactory;
	private PurchaseOrderVODOMapper orderVODOMapper;
	
	public InternalPurchaseOrderDAO(SessionFactory sessionFactory, 
			PurchaseOrderVODOMapper orderVODOMapper){
		this.sessionFactory = sessionFactory;
		this.orderVODOMapper = orderVODOMapper;
	}
	
	
	public void persistOrder(final OrderVO orderVO){
		Session session = sessionFactory.getCurrentSession();
		PurchaseOrder orderDO = orderVODOMapper.mapVOToDO(orderVO);
		
		session.saveOrUpdate(orderDO);
		orderVO.setId(orderDO.getId());
	}
	
	
	public OrderVO retrieveOrder(final long purchaseOrderId){
		Session session = sessionFactory.getCurrentSession();
		PurchaseOrder orderBO = (PurchaseOrder)session.load(PurchaseOrder.class, purchaseOrderId);
		return orderVODOMapper.mapDOToVO(orderBO);
	}
	
	
	public List<OrderVO> searchOrder(SearchOrderRequestVO searchOrderRequestVO){
		List<OrderVO> orderVOs = new ArrayList<OrderVO>();
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(PurchaseOrder.class);
		if(searchOrderRequestVO.getOrderId() > 0 ){
			criteria.add(Restrictions.eq("id", searchOrderRequestVO.getOrderId()));
		}
		
		if(searchOrderRequestVO.getOrderDate() != null){
			criteria.add(Restrictions.eq("orderDate", searchOrderRequestVO.getOrderDate()));
		}
		
		if(searchOrderRequestVO.getCustomerId() > 0){
			criteria.add(Restrictions.eq("customerId", searchOrderRequestVO.getCustomerId()));
		}
		
		if(searchOrderRequestVO.getOrderStatus() != null){
			criteria.add(Restrictions.eq("orderStatus", searchOrderRequestVO.getOrderStatus().getId()));
		}
		
		List<PurchaseOrder> orderDOs = criteria.list();
		for(PurchaseOrder orderDO : orderDOs){
			orderVOs.add(orderVODOMapper.mapDOToVO(orderDO));
		}
		
		return orderVOs;
	}
	
}
