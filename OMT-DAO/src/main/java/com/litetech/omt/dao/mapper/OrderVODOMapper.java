package com.litetech.omt.dao.mapper;

import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.dao.model.core.Order;
import com.litetech.omt.vo.OrderVO;

public class OrderVODOMapper {

	public Order mapVOToDO(final OrderVO orderVO){
		Order order = new Order();
		order.setCreationDate(orderVO.getCreationDate());
		order.setCustomerId(orderVO.getCustomerId());
		order.setId(orderVO.getId());
		order.setPartyOrderId(orderVO.getPartyOrderId());
		order.setPartyOrderDate(orderVO.getPartyOrderDate());
		order.setLastModifiedDate(orderVO.getLastModifiedDate());
		order.setOrderAmount(orderVO.getOrderAmount());
		order.setOrderDate(orderVO.getOrderDate());
		order.setOrderDesc(orderVO.getOrderDesc());
		order.setOrderStatus(orderVO.getOrderStatus().getId());
		order.setTaxId(orderVO.getTaxId());
		
		return order;
	}
	
	
	public OrderVO mapDOToVO(final Order order){
		
		OrderVO orderVO = new OrderVO(false);
		orderVO.setCreationDate(order.getCreationDate());
		orderVO.setCustomerId(order.getCustomerId());
		orderVO.setId(order.getId());
		orderVO.setLastModifiedDate(order.getLastModifiedDate());
		orderVO.setOrderAmount(order.getOrderAmount());
		orderVO.setOrderDate(order.getOrderDate());
		orderVO.setOrderDesc(order.getOrderDesc());
		orderVO.setOrderStatus(OrderStatusEnum.getById(order.getOrderStatus()));
		orderVO.setTaxId(order.getTaxId());
		orderVO.setPartyOrderId(order.getPartyOrderId());
		orderVO.setPartyOrderDate(order.getPartyOrderDate());
		return orderVO;
	}
}
