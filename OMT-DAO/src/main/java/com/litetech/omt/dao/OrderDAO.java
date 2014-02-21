package com.litetech.omt.dao;

import java.util.Date;
import java.util.List;

import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.SearchOrderRequestVO;

public interface OrderDAO {

	public void persistOrder(final OrderVO orderVO);
	public OrderVO retrieveOrder(final long orderId);
	public List<OrderVO> searchOrders(final SearchOrderRequestVO searchOrderRequestVO);
}
