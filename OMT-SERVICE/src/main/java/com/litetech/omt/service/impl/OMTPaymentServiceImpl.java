package com.litetech.omt.service.impl;

import java.util.List;

import com.litetech.omt.dao.PaymentDAO;
import com.litetech.omt.dao.UserDAO;
import com.litetech.omt.report.ReportService;
import com.litetech.omt.service.OMTPaymentService;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.IncomeExpenseVO;
import com.litetech.omt.vo.OrgVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;
import com.litetech.omt.vo.SearchTransactionRequestVO;

public class OMTPaymentServiceImpl implements OMTPaymentService{

	private PaymentDAO paymentDAO;
	private UserDAO userDAO;
	private ReportService reportService;
	public OMTPaymentServiceImpl(PaymentDAO paymentDAO, UserDAO userDAO,
			ReportService reportService){
		this.paymentDAO = paymentDAO;
		this.userDAO = userDAO;
		this.reportService = reportService;
	}
	
	@Override
	public void saveTransaction(final PaymentTransactionVO paymentTransactionVO) {
		paymentDAO.saveOrUpdateTransaction(paymentTransactionVO);
	}

	@Override
	public PaymentTransactionVO retrieveTransaction(long transactionId) {
		return paymentDAO.retrieveTransaction(transactionId);
	}

	@Override
	public List<BankVO> retrieveAllBank() {
		return paymentDAO.retrieveAllBank();
	}

	@Override
	public void saveBank(BankVO bankVO) {
		paymentDAO.saveOrUpdate(bankVO);		
	}

	@Override
	public List<BankVO> retrieveActiveBanks() {
		return paymentDAO.retrieveActiveBanks();
	}

	@Override
	public void deleteBank(long bankId) {
		paymentDAO.deleteBank(bankId);
	}

	@Override
	public List<PaymentModeVO> retrieveAllPaymentModes() {
		return paymentDAO.retrieveAllPaymentModes();
	}

	@Override
	public List<PaymentModeVO> retrieveActivePaymentModes() {
		return paymentDAO.retrieveActivePaymentModes();
	}

	@Override
	public void savePaymentMode(PaymentModeVO paymentModeVO) {
		paymentDAO.saveOrUpdate(paymentModeVO);		
	}

	@Override
	public void deletePaymentMode(long paymentModeId) {
		paymentDAO.deletePaymentMode(paymentModeId); 		
	}
	
	@Override
	public List<PaymentTransactionVO> searchPaymentTransaction(SearchTransactionRequestVO searchTransactionRequestVO){
		return paymentDAO.searchPaymentTransaction(searchTransactionRequestVO);
	}

	@Override
	public void printProfitLossReport(ProfitLossSearchRequest searchRequest,
			List<PaymentTransactionVO> paymentTransactionVOs,
			IncomeExpenseVO incomeExpenseVO) {
		OrgVO orgVO = userDAO.retrieveOrgMetaData();
		reportService.printProfitLossReport(searchRequest, orgVO, 
				incomeExpenseVO, paymentTransactionVOs);
		
	}

	@Override
	public List<PaymentTransactionVO> searchPaymentTransaction(
			ProfitLossSearchRequest request) {
		return paymentDAO.searchPaymentTransaction(request);
	}
	
	
	
	

}
