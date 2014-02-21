package com.litetech.omt.dao;

import java.util.List;

import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.SearchOrderRequestVO;

public interface PurchaseOrderDAO {

	public void persistPurchaseOrder(final OrderVO orderVO);
	public OrderVO retrievePurchaseOrder(final long purchaseOrderId);
	public List<OrderVO> searchOrders(SearchOrderRequestVO searchOrderRequestVO); 
}
