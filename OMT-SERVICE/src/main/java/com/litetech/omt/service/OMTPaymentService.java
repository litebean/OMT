package com.litetech.omt.service;

import java.util.List;

import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.IncomeExpenseVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;
import com.litetech.omt.vo.SearchTransactionRequestVO;


public interface OMTPaymentService {
	
	public void saveTransaction(final PaymentTransactionVO paymentTransactionVO);
	public PaymentTransactionVO retrieveTransaction(final long transactionId);
	
	public List<BankVO> retrieveAllBank();
	public void saveBank(BankVO bankVO);
	
	public List<BankVO> retrieveActiveBanks();
	public void deleteBank(long bankId);
	
	public List<PaymentModeVO> retrieveAllPaymentModes();
	public List<PaymentModeVO> retrieveActivePaymentModes();
	
	public void savePaymentMode(PaymentModeVO paymentModeVO);
	public void deletePaymentMode(long paymentModeId);
	
	public List<PaymentTransactionVO> searchPaymentTransaction(SearchTransactionRequestVO searchTransactionRequestVO);
	public void printProfitLossReport(ProfitLossSearchRequest searchRequest,
			List<PaymentTransactionVO> paymentTransactionVOs,
			IncomeExpenseVO incomeExpenseVO);
	
	public List<PaymentTransactionVO> searchPaymentTransaction(ProfitLossSearchRequest request);
}
