package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.litetech.omt.constant.InvoiceStatusEnum;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.constant.PaymentStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.dao.mapper.InvoiceVODOMapper;
import com.litetech.omt.dao.mapper.PurchaseInvoiceVODOMapper;
import com.litetech.omt.dao.mapper.TransactionVODOMapper;
import com.litetech.omt.dao.model.core.Invoice;
import com.litetech.omt.dao.model.core.InvoicePayment;
import com.litetech.omt.dao.model.core.PurchaseInvoice;
import com.litetech.omt.dao.model.payment.Bank;
import com.litetech.omt.dao.model.payment.PaymentMode;
import com.litetech.omt.dao.model.payment.PaymentTransaction;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;
import com.litetech.omt.vo.SearchTransactionRequestVO;

public class InternalPaymentDAO {
	
	private SessionFactory sessionFactory;
	private TransactionVODOMapper transactionVODOMapper;
	private InvoiceVODOMapper invoiceVODOMapper;
	private PurchaseInvoiceVODOMapper purchaseInvVODOMapper;
	
	
	public InternalPaymentDAO(SessionFactory sessionFactory,
			TransactionVODOMapper transactionVODOMapper,
			InvoiceVODOMapper invoiceVODOMapper,
			PurchaseInvoiceVODOMapper purchaseInvVODOMapper){
		this.sessionFactory = sessionFactory;
		this.transactionVODOMapper = transactionVODOMapper;
		this.invoiceVODOMapper = invoiceVODOMapper;
		this.purchaseInvVODOMapper = purchaseInvVODOMapper;
	}
	
	public void saveOrUpdateTransaction(final PaymentTransactionVO paymentTransactionVO){
		PaymentTransaction paymentTransaction = transactionVODOMapper.mapTransactionVOTODO(paymentTransactionVO);
				
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(paymentTransaction);
		paymentTransactionVO.setId(paymentTransaction.getId());
		
		if(PaymentStatusEnum.CONFIRMED.equals(paymentTransactionVO.getTransactionStaus())){
			saveOrUpdateInvoicePayment(paymentTransactionVO.getId(), paymentTransactionVO.getInovicePaymentVOs());
		}else{
			cancelPaymentTransaction(paymentTransactionVO.getId());
		}
	}
	
	
	private void cancelPaymentTransaction(long transactionId){
		Session session = sessionFactory.getCurrentSession();
		List<InvoicePayment> prevInvPaymentDOs = retrieveTransactionPayment(transactionId);
		
		Date currentTime = new Date();
		InvoicePayment invoicePaymentDO = null;
		double prevPayAmt = 0;
		
		for(InvoicePayment prevInvPaymentDO : prevInvPaymentDOs){
			invoicePaymentDO = prevInvPaymentDO;
			invoicePaymentDO.setStatus(PaymentStatusEnum.CANCELLED.getId());
			prevPayAmt = prevInvPaymentDO.getPaymentAmount();
			//invoicePaymentDO.setPaymentAmount(invoicePaymentVO.getPaymentAmount());
				
			invoicePaymentDO.setLastModifiedDate(currentTime);
			session.saveOrUpdate(invoicePaymentDO);
			
			//reset the amount to 0
			updateInvoice(invoicePaymentDO.getInvoiceId(), prevPayAmt, 0, OMTSwitchEnum.getById(invoicePaymentDO.getIsPurchase()));
		}
		
	}
	
	private void saveOrUpdateInvoicePayment(long transactionId, List<InvoicePaymentVO> invoicePaymentVOs){
		Session session = sessionFactory.getCurrentSession();
		
		List<InvoicePayment> prevInvPaymentDOs = retrieveTransactionPayment(transactionId);
		List<Long> activePaymentsIds = new ArrayList<Long>();
		
		for(InvoicePaymentVO invoicePaymentVO :  invoicePaymentVOs){
			invoicePaymentVO.setPaymentTransactionId(transactionId);
			
			Date currentTime = new Date();
			InvoicePayment invoicePaymentDO = null;
			double prevPayAmt = 0;
			if(invoicePaymentVO.getId() == 0){
				//new entry
				invoicePaymentVO.setCreationDate(currentTime);
				invoicePaymentDO = transactionVODOMapper.mapInvoicePaymentVOTODO(invoicePaymentVO);
			}else{
				for(InvoicePayment prevInvPaymentDO : prevInvPaymentDOs){
					if(prevInvPaymentDO.getId() == invoicePaymentVO.getId()){
						
						invoicePaymentDO = prevInvPaymentDO;
						invoicePaymentDO.setStatus(invoicePaymentVO.getStatus().getId());
						prevPayAmt = prevInvPaymentDO.getPaymentAmount();
						invoicePaymentDO.setPaymentAmount(invoicePaymentVO.getPaymentAmount());
						
						break;
					}
				}
			}
			invoicePaymentDO.setLastModifiedDate(currentTime);
			session.saveOrUpdate(invoicePaymentDO);
			invoicePaymentVO.setId(invoicePaymentDO.getId());
			
			InvoiceVO invoiceVO = updateInvoice(invoicePaymentDO.getInvoiceId(), prevPayAmt, invoicePaymentDO.getPaymentAmount(),
					invoicePaymentVO.getPurchase());
			invoicePaymentVO.setInvoiceVO(invoiceVO);
			activePaymentsIds.add(invoicePaymentVO.getId());
		}
		
		//delete nonexistent payment
		for(InvoicePayment prevInvPaymentDO : prevInvPaymentDOs){
			if(!activePaymentsIds.contains(prevInvPaymentDO.getId())){
				updateInvoice(prevInvPaymentDO.getInvoiceId(), prevInvPaymentDO.getPaymentAmount(), 0,
						OMTSwitchEnum.getById(prevInvPaymentDO.getIsPurchase()));
				session.delete(prevInvPaymentDO);
			}
		}
		
	}
	
	private InvoiceVO updateInvoice(long invoiceId, double substractAmt, double newAmt, OMTSwitchEnum purchase){
		InvoiceVO invoiceVO = null;
		if(OMTSwitchEnum.ON.equals(purchase)){
			invoiceVO = updatePurchaseInvoice(invoiceId, substractAmt, newAmt);
		}else{
			invoiceVO = updateGeneralInvoice(invoiceId, substractAmt, newAmt);
		}
		return invoiceVO;
	}
	private InvoiceVO updateGeneralInvoice(long invoiceId, double substractAmt, double newAmt){
		Session session = sessionFactory.getCurrentSession();
		Invoice invoice = (Invoice)session.load(Invoice.class, invoiceId);
		
		double paidAmt = invoice.getPaidAmount(); 
		paidAmt = paidAmt - substractAmt + newAmt;
		invoice.setPaidAmount(paidAmt);
		
		if(paidAmt >= invoice.getInvoiceAmount()){
			invoice.setInvoiceStatus(InvoiceStatusEnum.COMPLETED.getId());
		}else if(paidAmt > 0){
			invoice.setInvoiceStatus(InvoiceStatusEnum.PARTIAL_PAYMENT.getId());
		}else{
			invoice.setInvoiceStatus(InvoiceStatusEnum.PENDING.getId());
		}
		
		session.saveOrUpdate(invoice);
		return invoiceVODOMapper.mapDOToVO(invoice);
	}
	
	
	private InvoiceVO updatePurchaseInvoice(long invoiceId, double substractAmt, double newAmt){
		Session session = sessionFactory.getCurrentSession();
		PurchaseInvoice invoice = (PurchaseInvoice)session.load(PurchaseInvoice.class, invoiceId);
		
		double paidAmt = invoice.getPaidAmount(); 
		paidAmt = paidAmt - substractAmt + newAmt;
		invoice.setPaidAmount(paidAmt);
		
		if(paidAmt >= invoice.getInvoiceAmount()){
			invoice.setInvoiceStatus(InvoiceStatusEnum.COMPLETED.getId());
		}else if(paidAmt > 0){
			invoice.setInvoiceStatus(InvoiceStatusEnum.PARTIAL_PAYMENT.getId());
		}else{
			invoice.setInvoiceStatus(InvoiceStatusEnum.PENDING.getId());
		}
		
		session.saveOrUpdate(invoice);
		return purchaseInvVODOMapper.mapDOToVO(invoice);
	}
	
	private List<InvoicePayment> retrieveTransactionPayment(long transactionId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findByTransactionId");
		query.setLong("paymentTransactionId", transactionId);
		return query.list();
	}
	
	
	public List<InvoicePaymentVO> retrieveTransactionPaymentVO(long transactionId, boolean purchase){
		List<InvoicePayment> invoicePaymentDOs = retrieveTransactionPayment(transactionId);
		return transactionVODOMapper.mapInvoicePaymentDOTOVO(invoicePaymentDOs, purchase);
	}
	
	public List<InvoicePaymentVO> retrieveInvoicePayment(long invoiceId, TransactionTypeEnum transactionType){
		boolean purchase = transactionType.equals(TransactionTypeEnum.DEBIT) ? true : false;
		OMTSwitchEnum purchaseSwitch = purchase ? OMTSwitchEnum.ON : OMTSwitchEnum.OFF;
		List<InvoicePayment> invoicePaymentDOs = retrieveInvoicePaymentDOs(invoiceId, purchaseSwitch); 
		
		return transactionVODOMapper.mapInvoicePaymentDOTOVO(invoicePaymentDOs, purchase);
	}
	
	
	private List<InvoicePayment> retrieveInvoicePaymentDOs(long invoiceId, OMTSwitchEnum purchase){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findByInvoiceId");
		query.setLong("invoiceId", invoiceId);
		query.setInteger("purchase", purchase.getId());
		return query.list();
	}
	
	
	public PaymentTransactionVO retrieveTransaction(final long transactionId, boolean purchase){
		Session session = sessionFactory.getCurrentSession();
		PaymentTransaction paymentTransactionDO = (PaymentTransaction)session.load(PaymentTransaction.class, transactionId);
		PaymentTransactionVO paymentTransactionVO = transactionVODOMapper.mapTransactionDOTOVO(paymentTransactionDO);
		
		List<InvoicePaymentVO> invoicePaymentVOs = retrieveTransactionPaymentVO(paymentTransactionVO.getId(), purchase);
		paymentTransactionVO.setInovicePaymentVOs(invoicePaymentVOs);
		
		return paymentTransactionVO;
	}
	
	
	
	public List<BankVO> retrieveAllBanks(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchAllBank");
		List<Bank> banDOs = query.list();
		
		return transactionVODOMapper.mapBankDOToVOs(banDOs);
	}
	
	public List<BankVO> retrieveBanks(OMTSwitchEnum status){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchBanksByStatus");
		query.setInteger("status", status.getId());
		List<Bank> banDOs = query.list();
		
		return transactionVODOMapper.mapBankDOToVOs(banDOs);
	}
	
	public void saveOrUpdateBank(BankVO bankVO){
		Session session = sessionFactory.getCurrentSession();
		Bank bankDO = null;
		if(bankVO.getId() > 0){
			bankDO = (Bank)session.load(Bank.class, bankVO.getId());
			bankDO.setName(bankVO.getName());
			bankDO.setStatus(bankVO.getActive().getId());
		}else{
			bankVO.setCreationDate(new Date());
			bankDO = transactionVODOMapper.mapBankVOToDO(bankVO);
		}		
		bankDO.setLastModifiedDate(new Date());
		session.saveOrUpdate(bankDO);
		
		bankVO.setId(bankDO.getId());
	}
	
	
	public void deleteBank(long bankId){
		Session session = sessionFactory.getCurrentSession();
		Bank bankDO = new Bank();
		bankDO.setId(bankId);
		session.delete(bankDO);
	}
	
	public List<PaymentModeVO> retrieveAllPaymentModes(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchAllPaymentModes");
		
		List<PaymentMode> paymentModeDOs = query.list();
		return transactionVODOMapper.mapDOToVOs(paymentModeDOs);
	}
	
	
	public List<PaymentModeVO> retrievePaymentModes(OMTSwitchEnum status){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchPaymentModesByStatus");
		query.setInteger("status", status.getId());
		
		List<PaymentMode> paymentModeDOs = query.list();
		return transactionVODOMapper.mapDOToVOs(paymentModeDOs);
	}
	
	public void saveOrUpdatePaymentMode(PaymentModeVO paymentModeVO){
		Session session = sessionFactory.getCurrentSession();
		PaymentMode paymentModeDO = null;
		if(paymentModeVO.getId() > 0){
			paymentModeDO = (PaymentMode)session.load(PaymentMode.class, paymentModeVO.getId());
			paymentModeDO.setName(paymentModeVO.getName());
			paymentModeDO.setDesc(paymentModeVO.getDesc());
			paymentModeDO.setStatus(paymentModeVO.getActive().getId());
		}else{
			paymentModeVO.setCreationDate(new Date());
			paymentModeDO = transactionVODOMapper.mapVOToDO(paymentModeVO);
		}
		paymentModeDO.setLastModifiedDate(new Date());
		session.saveOrUpdate(paymentModeDO);
		
		paymentModeVO.setId(paymentModeDO.getId());
		paymentModeVO.setCreationDate(paymentModeDO.getCreationDate());
		paymentModeVO.setLastModifiedDate(paymentModeDO.getLastModifiedDate());
	}
	
	
	public void deletePaymentMode(long paymentModeId){
		Session session = sessionFactory.getCurrentSession();
		PaymentMode paymentModeDO = new PaymentMode();
		paymentModeDO.setId(paymentModeId);
		
		session.delete(paymentModeDO);
	}
	
	
	public List<PaymentTransactionVO> searchPaymentTransaction(SearchTransactionRequestVO searchTransactionRequestVO){
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(PaymentTransaction.class, "payTrans")
								.createAlias("payTrans.customer", "customer")
								.createAlias("payTrans.paymentMode", "mode")
								.createAlias("payTrans.paymentBank", "bank");
		
		if(searchTransactionRequestVO.getTransactionId() > 0){
			criteria.add(Restrictions.eq("payTrans.id", searchTransactionRequestVO.getTransactionId()));
		}
		
		if(searchTransactionRequestVO.getCustomerId() > 0){
			criteria.add(Restrictions.eq("customer.id", searchTransactionRequestVO.getCustomerId()));
		}
		
		if(searchTransactionRequestVO.getTransactionTypeEnum() != null){
			criteria.add(Restrictions.eq("payTrans.transactionType", searchTransactionRequestVO.getTransactionTypeEnum().getId()));	
		}
		
		if(searchTransactionRequestVO.getBankId() > 0){
			criteria.add(Restrictions.eq("bank.id", searchTransactionRequestVO.getBankId())); 
		}
		
		if(searchTransactionRequestVO.getPaymentModeId() > 0){
			criteria.add(Restrictions.eq("mode.id", searchTransactionRequestVO.getPaymentModeId()));
		}
		
		if(searchTransactionRequestVO.getStatusEnum() != null){
			criteria.add(Restrictions.eq("payTrans.status", searchTransactionRequestVO.getStatusEnum().getId()));
		}
		
		if(searchTransactionRequestVO.getDescLike() != null){
			criteria.add(Restrictions.like("payTrans.transactionDesc", searchTransactionRequestVO.getDescLike(), MatchMode.ANYWHERE));
		}
		
		if(searchTransactionRequestVO.getTransAmoutGrThan() != null){
			criteria.add(Restrictions.ge("payTrans.transactionAmount", searchTransactionRequestVO.getTransAmoutGrThan())); 
		}
		
		if(searchTransactionRequestVO.getTransDateGrThan() != null){
			criteria.add(Restrictions.ge("payTrans.transactionDate", searchTransactionRequestVO.getTransDateGrThan())); 
		}
		
		List<PaymentTransaction> paymentTransactionDOs = criteria.list();
		return transactionVODOMapper.mapTransactionDOTOVOs(paymentTransactionDOs);
	}
	
	
	
	public List<PaymentTransactionVO> searchPaymentTransaction(ProfitLossSearchRequest request){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchConfirmedPaymentBtwDate");
		query.setInteger("status", PaymentStatusEnum.CONFIRMED.getId());
		query.setDate("startDate", request.getStartDate());
		query.setDate("endDate", request.getEndDate());
		
		List<PaymentTransaction> paymentTransactionDOs = query.list(); 
		return transactionVODOMapper.mapTransactionDOTOVOs(paymentTransactionDOs);
	}
	
	
}
