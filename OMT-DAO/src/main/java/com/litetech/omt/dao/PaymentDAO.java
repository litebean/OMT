package com.litetech.omt.dao;

import java.util.List;

import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;
import com.litetech.omt.vo.SearchTransactionRequestVO;



public interface PaymentDAO {

	public List<InvoicePaymentVO> retrieveInvoicePayment(final long transactionId);
	public List<InvoiceVO> retrievePaymentPending(final long customerId, final TransactionTypeEnum transactionType);
	public List<InvoiceVO> retrievePaymentPending(final long customerId, final TransactionTypeEnum transactionType, 
			final List<Long> excludeInvoiceIds);
	public void saveOrUpdateTransaction(final PaymentTransactionVO paymentTransactionVO);
	public PaymentTransactionVO retrieveTransaction(final long transactionId);
	public List<BankVO> retrieveAllBank();
	public List<BankVO> retrieveActiveBanks();
	
	public void saveOrUpdate(BankVO bankVO);
	public void deleteBank(long bankId);
	
	
	public List<PaymentModeVO> retrieveAllPaymentModes();
	public List<PaymentModeVO> retrieveActivePaymentModes();
	
	public void saveOrUpdate(PaymentModeVO paymentModeVO);
	public void deletePaymentMode(long paymentModeId);
	
	public List<PaymentTransactionVO> searchPaymentTransaction(SearchTransactionRequestVO searchTransactionRequestVO);
	public List<PaymentTransactionVO> searchPaymentTransaction(ProfitLossSearchRequest request);
}
