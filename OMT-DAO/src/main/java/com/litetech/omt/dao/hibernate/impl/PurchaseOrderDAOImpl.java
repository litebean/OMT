package com.litetech.omt.dao.hibernate.impl;

import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.litetech.omt.constant.LineItemRefTypeEnum;
import com.litetech.omt.dao.PurchaseOrderDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalLineItemDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalPurchaseInvoiceDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalPurchaseOrderDAO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.SearchOrderRequestVO;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class PurchaseOrderDAOImpl implements PurchaseOrderDAO{

	
	private InternalPurchaseOrderDAO internalPurchaseOrderDAO;
	private InternalLineItemDAO internalLineItemDAO;
	private InternalPurchaseInvoiceDAO internalPurchaseInvoiceDAO;
	
	public PurchaseOrderDAOImpl(InternalPurchaseOrderDAO internalPurchaseOrderDAO,
			InternalLineItemDAO internalLineItemDAO, 
			InternalPurchaseInvoiceDAO internalPurchaseInvoiceDAO){
		this.internalPurchaseOrderDAO = internalPurchaseOrderDAO;
		this.internalLineItemDAO = internalLineItemDAO;
		this.internalPurchaseInvoiceDAO = internalPurchaseInvoiceDAO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void persistPurchaseOrder(OrderVO orderVO) {
		internalPurchaseOrderDAO.persistOrder(orderVO);		
		internalLineItemDAO.persistLineItems(orderVO.getOrderLineItems(), orderVO.getId(), LineItemRefTypeEnum.PURCHASE_ORDER, 0);		
	}

	@Override
	public OrderVO retrievePurchaseOrder(long purchaseOrderId) {
		
		OrderVO orderVO = internalPurchaseOrderDAO.retrieveOrder(purchaseOrderId);
		List<LineItemVO> lineItemVOs = internalLineItemDAO.retrieveLineItemVOs(orderVO.getId(), LineItemRefTypeEnum.PURCHASE_ORDER);
		orderVO.setOrderLineItems(lineItemVOs);
		
		List<InvoiceVO> invoices = internalPurchaseInvoiceDAO.retrievePurchaseInvoices(purchaseOrderId);
		orderVO.setInvoices(invoices);
		
		return orderVO;
	}
	
	@Override
	public List<OrderVO> searchOrders(SearchOrderRequestVO searchOrderRequestVO) {
		return internalPurchaseOrderDAO.searchOrder(searchOrderRequestVO);		
	}
	
}
