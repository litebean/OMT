package com.litetech.omt.dao.hibernate.impl;


import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.litetech.omt.constant.LineItemRefTypeEnum;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.dao.PurchaseInvoiceDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalInvoiceChargeDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalLineItemDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalPurchaseInvoiceDAO;
import com.litetech.omt.vo.InvoiceChargeVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.SearchInvoiceRequestVO;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class PurchaseInvoiceDAOImpl implements PurchaseInvoiceDAO{

	private InternalPurchaseInvoiceDAO internalPurchaseInvoiceDAO;
	private InternalLineItemDAO internalLineItemDAO;
	private InternalInvoiceChargeDAO internalInvoiceChargeDAO;
	
	public PurchaseInvoiceDAOImpl(InternalPurchaseInvoiceDAO internalPurchaseInvoiceDAO,
			InternalLineItemDAO internalLineItemDAO, InternalInvoiceChargeDAO internalInvoiceChargeDAO){
		this.internalPurchaseInvoiceDAO = internalPurchaseInvoiceDAO;
		this.internalLineItemDAO = internalLineItemDAO;
		this.internalInvoiceChargeDAO = internalInvoiceChargeDAO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void persistPurchaseInvoice(InvoiceVO invoiceVO) {
		internalPurchaseInvoiceDAO.persistPurchaseInvoice(invoiceVO);
		internalLineItemDAO.persistLineItems(invoiceVO.getInvoiceLineItems(), invoiceVO.getId(), LineItemRefTypeEnum.PURCHASE_INVOICE,
				invoiceVO.getOrderId());
		
		//TODO add type to differentiate purchase/invoice
		internalInvoiceChargeDAO.persistInvoiceCharges(invoiceVO.getId(), invoiceVO.getInvoiceCharges(), OMTSwitchEnum.ON);
	}
	
	

	@Override
	public InvoiceVO retrievePurchaseInvoice(long invoiceId) {
		InvoiceVO invoiceVO = internalPurchaseInvoiceDAO.retrievePurchaseInvoice(invoiceId);
		
		List<LineItemVO> invoiceLineItems = internalLineItemDAO.retrieveLineItemVOs(invoiceId, LineItemRefTypeEnum.PURCHASE_INVOICE);
		invoiceVO.setInvoiceLineItems(invoiceLineItems);
		
		//TODO add type to differentiate btw purchase/invoice
		
		List<InvoiceChargeVO> invoiceCharges = internalInvoiceChargeDAO.retrieveInvoiceCharges(invoiceId, OMTSwitchEnum.ON);
		Collections.sort(invoiceCharges);
		invoiceVO.setInvoiceCharges(invoiceCharges);
				
		return invoiceVO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void detachInvoiceFromOrder(long invoiceId){
		InvoiceVO invoiceVO = internalPurchaseInvoiceDAO.retrievePurchaseInvoice(invoiceId);
		internalLineItemDAO.detachInvoiceFromOrder(invoiceVO.getId(), invoiceVO.getOrderId(), true);
	}

	@Override
	public List<InvoiceVO> searchPurchaseInvoices(
			SearchInvoiceRequestVO searchInvoiceRequestVO) {
		
		return internalPurchaseInvoiceDAO.searchPurchaseInvoices(searchInvoiceRequestVO);
	}

}
