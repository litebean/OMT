package com.litetech.omt.dao;

import java.util.List;

import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.SearchInvoiceRequestVO;

public interface PurchaseInvoiceDAO {

	public void persistPurchaseInvoice(final InvoiceVO invoiceVO);
	
	public InvoiceVO retrievePurchaseInvoice(final long invoiceId);
	
	public void detachInvoiceFromOrder(long invoiceId);
	
	public List<InvoiceVO> searchPurchaseInvoices(SearchInvoiceRequestVO searchInvoiceRequestVO);
}
