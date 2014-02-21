package com.litetech.omt.dao.hibernate.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.litetech.omt.constant.LineItemRefTypeEnum;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.dao.InvoiceDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalInvoiceChargeDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalInvoiceDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalLineItemDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalPaymentDAO;
import com.litetech.omt.util.comparator.InvoicePaymentComparator;
import com.litetech.omt.vo.InvoiceChargeVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.SearchInvoiceRequestVO;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class InvoiceDAOImpl implements InvoiceDAO{

	private InternalInvoiceDAO internalInvoiceDAO;
	private InternalLineItemDAO internalLineItemDAO;
	private InternalInvoiceChargeDAO internalInvoiceChargeDAO;
	private InternalPaymentDAO internalPaymentDAO;
	public InvoiceDAOImpl(InternalInvoiceDAO internalInvoiceDAO, 
			InternalLineItemDAO internalLineItemDAO, 
			InternalInvoiceChargeDAO internalInvoiceChargeDAO,
			InternalPaymentDAO internalPaymentDAO){
		this.internalInvoiceDAO = internalInvoiceDAO;
		this.internalLineItemDAO = internalLineItemDAO;
		this.internalInvoiceChargeDAO = internalInvoiceChargeDAO;
		this.internalPaymentDAO = internalPaymentDAO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void persistInvoice(InvoiceVO invoiceVO) {
		internalInvoiceDAO.persistInvoice(invoiceVO);
		internalLineItemDAO.persistLineItems(invoiceVO.getInvoiceLineItems(), invoiceVO.getId(), LineItemRefTypeEnum.INVOICE,
				invoiceVO.getOrderId());
		internalInvoiceChargeDAO.persistInvoiceCharges(invoiceVO.getId(), invoiceVO.getInvoiceCharges(), OMTSwitchEnum.OFF);
	}
	

	@Override
	public InvoiceVO retrieveInvoice(long invoiceId) {
		InvoiceVO invoiceVO = internalInvoiceDAO.retrieveInvoice(invoiceId);
		
		List<LineItemVO> invoiceLineItems = internalLineItemDAO.retrieveLineItemVOs(invoiceId, LineItemRefTypeEnum.INVOICE);
		invoiceVO.setInvoiceLineItems(invoiceLineItems);
		
		List<InvoiceChargeVO> invoiceCharges = internalInvoiceChargeDAO.retrieveInvoiceCharges(invoiceId, OMTSwitchEnum.OFF);
		Collections.sort(invoiceCharges);
		invoiceVO.setInvoiceCharges(invoiceCharges);
				
		return invoiceVO;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void detachInvoiceFromOrder(long invoiceId){
		InvoiceVO invoiceVO = internalInvoiceDAO.retrieveInvoice(invoiceId);
		internalLineItemDAO.detachInvoiceFromOrder(invoiceVO.getId(), invoiceVO.getOrderId(), false);
	}
	
	@Override
	public List<InvoiceVO> searchInvoices(SearchInvoiceRequestVO searchInvoiceRequestVO){
		return internalInvoiceDAO.searchInvoices(searchInvoiceRequestVO);
	}
	
	
	@Override
	public List<InvoicePaymentVO> retrieveInvoicePayments(final long invoiceId, 
			TransactionTypeEnum transactionType){
		List<InvoicePaymentVO> invoicePaymentVOs = internalPaymentDAO.retrieveInvoicePayment(invoiceId, transactionType);
		Collections.sort(invoicePaymentVOs, new InvoicePaymentComparator());
		
		return invoicePaymentVOs;
	}

}
