package com.litetech.omt.dao.hibernate.impl;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.litetech.omt.constant.InvoiceStatusEnum;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.constant.TransactionTypeEnum;

import com.litetech.omt.dao.PaymentDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalInvoiceDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalPaymentDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalPurchaseInvoiceDAO;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;
import com.litetech.omt.vo.SearchTransactionRequestVO;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class PaymentDAOImpl implements PaymentDAO{
	
	private InternalInvoiceDAO internalInvoiceDAO;
	private InternalPaymentDAO internalPaymentDAO;
	private InternalPurchaseInvoiceDAO internalPurchaseInvoiceDAO;
	
	public PaymentDAOImpl(InternalInvoiceDAO internalInvoiceDAO,
			InternalPaymentDAO internalPaymentDAO, 
			InternalPurchaseInvoiceDAO internalPurchaseInvoiceDAO){
		this.internalInvoiceDAO = internalInvoiceDAO;
		this.internalPaymentDAO = internalPaymentDAO;
		this.internalPurchaseInvoiceDAO = internalPurchaseInvoiceDAO;
	}
	
	
	
	
	@Override
	public List<InvoicePaymentVO> retrieveInvoicePayment(long transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoiceVO> retrievePaymentPending(long customerId,
			TransactionTypeEnum transactionType) {
		List<InvoiceVO> invoiceVOs = Collections.emptyList();
		
		List<Integer> invoiceStatuses = new ArrayList<Integer>();
		invoiceStatuses.add(InvoiceStatusEnum.PENDING.getId());
		invoiceStatuses.add(InvoiceStatusEnum.PARTIAL_PAYMENT.getId());
		
		if(TransactionTypeEnum.CREDIT.equals(transactionType)){
			invoiceVOs = internalInvoiceDAO.retrieveInvoices(customerId, invoiceStatuses);
		}else{
			invoiceVOs = internalPurchaseInvoiceDAO.retrievePurchaseInvoices(
					customerId, invoiceStatuses);
		}
		return invoiceVOs;
	}
	
	
	public List<InvoiceVO> retrievePaymentPending(long customerId, TransactionTypeEnum transactionType,
			List<Long> excludeInvoiceIds){
		List<InvoiceVO> invoiceVOs = Collections.emptyList();
		
		List<Integer> invoiceStatuses = new ArrayList<Integer>();
		invoiceStatuses.add(InvoiceStatusEnum.PENDING.getId());
		invoiceStatuses.add(InvoiceStatusEnum.PARTIAL_PAYMENT.getId());
		
		if(TransactionTypeEnum.CREDIT.equals(transactionType)){
			invoiceVOs = internalInvoiceDAO.retrieveInvoices(customerId, invoiceStatuses, excludeInvoiceIds);
		}else{
			invoiceVOs = internalPurchaseInvoiceDAO.retrievePurchaseInvoices(customerId, invoiceStatuses, 
					excludeInvoiceIds);
		}
		return invoiceVOs;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveOrUpdateTransaction(
			PaymentTransactionVO paymentTransactionVO) {
		internalPaymentDAO.saveOrUpdateTransaction(paymentTransactionVO);		
	}




	@Override
	public PaymentTransactionVO retrieveTransaction(long transactionId) {
		PaymentTransactionVO paymentTransactionVO = internalPaymentDAO.retrieveTransaction(transactionId, false);

		List<InvoicePaymentVO> invoicePaymentVOs = paymentTransactionVO.getInovicePaymentVOs();
		boolean purchase = paymentTransactionVO.getTransType().equals(TransactionTypeEnum.DEBIT) ? true : false;
		
		for(InvoicePaymentVO invoicePaymentVO : invoicePaymentVOs){
			InvoiceVO loadedInvoiceVO = null;
			if(purchase){
				loadedInvoiceVO = internalPurchaseInvoiceDAO.retrievePurchaseInvoice(invoicePaymentVO.getInvoiceVO().getId());
			}else{
				loadedInvoiceVO = internalInvoiceDAO.retrieveInvoice(invoicePaymentVO.getInvoiceVO().getId());
			}
			invoicePaymentVO.setInvoiceVO(loadedInvoiceVO);
		}
		return paymentTransactionVO;
	}




	@Override
	public List<BankVO> retrieveAllBank() {
		return internalPaymentDAO.retrieveAllBanks();
	}




	@Override
	public List<BankVO> retrieveActiveBanks() {
		return internalPaymentDAO.retrieveBanks(OMTSwitchEnum.ON);
	}




	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveOrUpdate(BankVO bankVO) {
		internalPaymentDAO.saveOrUpdateBank(bankVO);		
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void deleteBank(long bankId) {
		internalPaymentDAO.deleteBank(bankId);		
	}




	@Override
	public List<PaymentModeVO> retrieveAllPaymentModes() {
		return internalPaymentDAO.retrieveAllPaymentModes();
	}
	
	@Override
	public List<PaymentModeVO> retrieveActivePaymentModes() {
		return internalPaymentDAO.retrievePaymentModes(OMTSwitchEnum.ON);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveOrUpdate(PaymentModeVO paymentModeVO) {
		internalPaymentDAO.saveOrUpdatePaymentMode(paymentModeVO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void deletePaymentMode(long paymentModeId) {
		internalPaymentDAO.deletePaymentMode(paymentModeId);		
	}
	
	
	@Override
	public List<PaymentTransactionVO> searchPaymentTransaction(SearchTransactionRequestVO searchTransactionRequestVO){
		return internalPaymentDAO.searchPaymentTransaction(searchTransactionRequestVO);
	}




	@Override
	public List<PaymentTransactionVO> searchPaymentTransaction(
			ProfitLossSearchRequest request) {
		return internalPaymentDAO.searchPaymentTransaction(request);
	}
	
	
	
}
