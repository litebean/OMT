package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.dao.mapper.OrderVODOMapper;
import com.litetech.omt.dao.model.core.Order;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.SearchOrderRequestVO;

public class InternalOrderDAO {
	
	private SessionFactory sessionFactory;
	private OrderVODOMapper orderVODOMapper;
	
	public InternalOrderDAO(SessionFactory sessionFactory, OrderVODOMapper orderVODOMapper){
		this.sessionFactory = sessionFactory;
		this.orderVODOMapper = orderVODOMapper;
	}
	
	public void persistOrder(final OrderVO orderVO){
		Session session = sessionFactory.getCurrentSession();
		Order orderBO = orderVODOMapper.mapVOToDO(orderVO);
		session.saveOrUpdate(orderBO);
		
		orderVO.setId(orderBO.getId());
	}
	
	public OrderVO retrieveOrder(final long orderId){
		Session session = sessionFactory.getCurrentSession();
		Order orderBO = (Order)session.load(Order.class, orderId);
		return orderVODOMapper.mapDOToVO(orderBO);
	}
	
	public List<OrderVO> searchOrder(SearchOrderRequestVO searchOrderRequestVO){
		List<OrderVO> orderVOs = new ArrayList<OrderVO>();
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Order.class);
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
		
		List<Order> orderDOs = criteria.list();
		for(Order orderDO : orderDOs){
			orderVOs.add(orderVODOMapper.mapDOToVO(orderDO));
		}
		
		return orderVOs;
	}

}
