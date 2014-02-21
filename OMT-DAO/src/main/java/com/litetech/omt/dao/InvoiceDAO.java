package com.litetech.omt.dao;

import java.util.List;

import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.SearchInvoiceRequestVO;

public interface InvoiceDAO {
	
	public void persistInvoice(final InvoiceVO invoiceVO);
	
	public InvoiceVO retrieveInvoice(final long invoiceId);

	public List<InvoiceVO> searchInvoices(SearchInvoiceRequestVO searchInvoiceRequestVO);
	
	public void detachInvoiceFromOrder(long invoiceId);
	
	public List<InvoicePaymentVO> retrieveInvoicePayments(final long invoiceId,
			TransactionTypeEnum transactionType);
}
