package com.litetech.omt.dao.hibernate.impl;

import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.litetech.omt.constant.LineItemRefTypeEnum;
import com.litetech.omt.dao.OrderDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalInvoiceDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalLineItemDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalOrderDAO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.SearchOrderRequestVO;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class OrderDAOImpl implements OrderDAO {

	private InternalOrderDAO internalOrderDAO;
	private InternalLineItemDAO internalLineItemDAO;
	private InternalInvoiceDAO internalInvoiceDAO;
	public OrderDAOImpl(InternalOrderDAO internalOrderDAO, 
			InternalLineItemDAO internalLineItemDAO, InternalInvoiceDAO internalInvoiceDAO){
		this.internalOrderDAO = internalOrderDAO;
		this.internalLineItemDAO = internalLineItemDAO;
		this.internalInvoiceDAO = internalInvoiceDAO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void persistOrder(OrderVO orderVO) {
		internalOrderDAO.persistOrder(orderVO);		
		internalLineItemDAO.persistLineItems(orderVO.getOrderLineItems(), orderVO.getId(), LineItemRefTypeEnum.ORDER,  0);
		
	}

	@Override
	public OrderVO retrieveOrder(long orderId) {
		OrderVO orderVO = internalOrderDAO.retrieveOrder(orderId);
		List<LineItemVO> lineItemVOs = internalLineItemDAO.retrieveLineItemVOs(orderVO.getId(), LineItemRefTypeEnum.ORDER);
		orderVO.setOrderLineItems(lineItemVOs);
		
		List<InvoiceVO> invoices = internalInvoiceDAO.retrieveInvoices(orderId);
		orderVO.setInvoices(invoices);
		
		return orderVO;
	}

	@Override
	public List<OrderVO> searchOrders(SearchOrderRequestVO searchOrderRequestVO) {
		return internalOrderDAO.searchOrder(searchOrderRequestVO);		
	}

}
