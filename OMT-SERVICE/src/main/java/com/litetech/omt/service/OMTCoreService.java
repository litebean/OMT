package com.litetech.omt.service;

import java.util.List;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.SearchInvoiceRequestVO;
import com.litetech.omt.vo.SearchOrderRequestVO;

public interface OMTCoreService {

	public OrderVO retrieveOrder(final long orderId, boolean purchase);
	public void createOrder(final OrderVO orderVO);
	
	public List<OrderVO> searchOrder(SearchOrderRequestVO searchOrderRequestVO);
	
	public InvoiceVO retrieveInvoice(final long invoiceId, boolean purchase);
	public void createInvoice(final InvoiceVO invoiceVO);
	
	public void detachInvoiceFromOrder(long invoiceId, boolean purchase);
	public void saveAndPrintInvoice(OrderVO orderVO, InvoiceVO invoiceVO);
	
	public List<ChargeVO> retrieveAllCharges();
	public List<InvoiceVO> searchInvoices(SearchInvoiceRequestVO searchInvoiceRequestVO);
	
	public void saveCharge(final ChargeVO chargeVO);
	public void deleteCharge(final long chargeId);
	
	
	public List<InvoiceVO> retrievePendingInvoices(final long customerId, TransactionTypeEnum transType);
	public List<InvoiceVO> retrievePendingInvoices(final long customerId, TransactionTypeEnum transType, 
			List<Long> exclInvoices);
		
	public List<InvoicePaymentVO> retrieveInvoicePayments(final long invoiceId, TransactionTypeEnum transType);
}
