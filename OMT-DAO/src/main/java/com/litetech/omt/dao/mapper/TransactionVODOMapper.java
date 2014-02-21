package com.litetech.omt.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.constant.PaymentStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.dao.model.core.InvoicePayment;
import com.litetech.omt.dao.model.payment.Bank;
import com.litetech.omt.dao.model.payment.PaymentMode;
import com.litetech.omt.dao.model.payment.PaymentTransaction;
import com.litetech.omt.dao.model.user.Customer;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;

public class TransactionVODOMapper {
	
	public PaymentTransaction mapTransactionVOTODO(final PaymentTransactionVO paymentTransactionVO){
		PaymentTransaction paymentTransaction = new PaymentTransaction();
		
		paymentTransaction.setId(paymentTransactionVO.getId());
		
		Customer customer = new Customer();
		customer.setId(paymentTransactionVO.getCustomerVO().getId());
		paymentTransaction.setCustomer(customer);
		
		paymentTransaction.setTransactionType(paymentTransactionVO.getTransType().getId());
		
		Bank bank = new Bank();
		bank.setId(paymentTransactionVO.getBankVO().getId());
		paymentTransaction.setPaymentBank(bank);
		
		PaymentMode paymentMode = new PaymentMode();
		paymentMode.setId(paymentTransactionVO.getPaymentModeVO().getId());
		paymentTransaction.setPaymentMode(paymentMode);
		
		paymentTransaction.setPaymentModeRefText(paymentTransactionVO.getPaymentRefText());
		paymentTransaction.setStatus(paymentTransactionVO.getTransactionStaus().getId());
		paymentTransaction.setTransactionAmount(paymentTransactionVO.getTransactionAmt());
		paymentTransaction.setUnUtilizedAmount(paymentTransactionVO.getUnutilizedAmount());
		paymentTransaction.setTransactionDate(paymentTransactionVO.getTransactionDate());
		paymentTransaction.setTransactionDesc(paymentTransactionVO.getTransactionDesc());
		paymentTransaction.setCreationDate(paymentTransactionVO.getCreationDate());
		paymentTransaction.setLastModifiedDate(paymentTransactionVO.getLastModifiedDate());
		
		return paymentTransaction;
	}
	
	
	
	public PaymentTransactionVO mapTransactionDOTOVO(PaymentTransaction paymentTransactionDO){
		PaymentTransactionVO paymentTransactionVO = new PaymentTransactionVO();
		paymentTransactionVO.setId(paymentTransactionDO.getId());
		
		BankVO bankVO = new BankVO(paymentTransactionDO.getPaymentBank().getId(), paymentTransactionDO.getPaymentBank().getName());
		paymentTransactionVO.setBankVO(bankVO);
		
		CustomerVO customerVO = new CustomerVO(paymentTransactionDO.getCustomer().getId(), 
				paymentTransactionDO.getCustomer().getCustomerName());
		paymentTransactionVO.setCustomerVO(customerVO);
		
		PaymentModeVO paymentModeVO = new PaymentModeVO(paymentTransactionDO.getPaymentMode().getId(), 
				paymentTransactionDO.getPaymentMode().getName());
		paymentTransactionVO.setPaymentModeVO(paymentModeVO);
		
		paymentTransactionVO.setPaymentRefText(paymentTransactionDO.getPaymentModeRefText());
		paymentTransactionVO.setTransactionAmt(paymentTransactionDO.getTransactionAmount());
		paymentTransactionVO.setUnutilizedAmount(paymentTransactionDO.getUnUtilizedAmount());
		paymentTransactionVO.setTransactionDate(paymentTransactionDO.getTransactionDate());
		paymentTransactionVO.setTransactionDesc(paymentTransactionDO.getTransactionDesc());
		paymentTransactionVO.setCreationDate(paymentTransactionDO.getCreationDate());
		paymentTransactionVO.setLastModifiedDate(paymentTransactionDO.getLastModifiedDate());
		paymentTransactionVO.setTransactionStaus(PaymentStatusEnum.getById(paymentTransactionDO.getStatus()));
		paymentTransactionVO.setTransType(TransactionTypeEnum.getById(paymentTransactionDO.getTransactionType()));
		
		return paymentTransactionVO;
	}
	
	
	public List<PaymentTransactionVO> mapTransactionDOTOVOs(List<PaymentTransaction> paymentTransactionDOs){
		List<PaymentTransactionVO> paymentTransactionVOs = new ArrayList<PaymentTransactionVO>();
		
		for(PaymentTransaction paymentTransactionDO : paymentTransactionDOs){
			paymentTransactionVOs.add(mapTransactionDOTOVO(paymentTransactionDO));
		}
		
		return paymentTransactionVOs;
	}
	
	public InvoicePayment mapInvoicePaymentVOTODO(InvoicePaymentVO invoicePaymentVO){
		
		InvoicePayment invoicePayment = new InvoicePayment();
		invoicePayment.setId(invoicePaymentVO.getId());
		invoicePayment.setInvoiceId(invoicePaymentVO.getInvoiceVO().getId());
		invoicePayment.setIsPurchase(invoicePaymentVO.getPurchase().getId());
		invoicePayment.setPaymentAmount(invoicePaymentVO.getPaymentAmount());
		invoicePayment.setPaymentTransactionId(invoicePaymentVO.getPaymentTransactionId());
		invoicePayment.setStatus(invoicePaymentVO.getStatus().getId());
		invoicePayment.setCreationDate(invoicePaymentVO.getCreationDate());
		invoicePayment.setLastModifiedDate(invoicePaymentVO.getLastModifiedDate());
		
		return invoicePayment;
	}
	
	
	
	public List<InvoicePaymentVO> mapInvoicePaymentDOTOVO(List<InvoicePayment> invoicePayments, boolean purchase){
		List<InvoicePaymentVO> invoicePaymentVOs = new ArrayList<InvoicePaymentVO>();
		for(InvoicePayment invoicePayment : invoicePayments){
			InvoicePaymentVO invoicePaymentVO = mapInvoicePaymentDOTOVO(invoicePayment, purchase);
			invoicePaymentVOs.add(invoicePaymentVO);
		}
		
		return invoicePaymentVOs;
	}
	
	public InvoicePaymentVO mapInvoicePaymentDOTOVO(InvoicePayment invoicePayment, boolean purchase){
		OMTSwitchEnum purchaseSwitch = OMTSwitchEnum.getById(invoicePayment.getIsPurchase());
		InvoicePaymentVO invoicePaymentVO = new InvoicePaymentVO(purchaseSwitch);
		
		invoicePaymentVO.setId(invoicePayment.getId());
		invoicePaymentVO.setInvoiceVO(new InvoiceVO(invoicePayment.getInvoiceId(), purchase));
		invoicePaymentVO.setPaymentTransactionId(invoicePayment.getPaymentTransactionId());
		invoicePaymentVO.setPaymentAmount(invoicePayment.getPaymentAmount());
		invoicePaymentVO.setStatus(PaymentStatusEnum.getById(invoicePayment.getStatus()));
		invoicePaymentVO.setCreationDate(invoicePayment.getCreationDate());
		invoicePaymentVO.setLastModifiedDate(invoicePayment.getLastModifiedDate());
		
		return invoicePaymentVO;
	}
	
	
	public List<BankVO> mapBankDOToVOs(List<Bank> bankDOs){
		List<BankVO> bankVOs = new ArrayList<BankVO>(bankDOs.size());
		for(Bank bankDO : bankDOs){
			bankVOs.add(mapBankDOToVO(bankDO));
		}
		return bankVOs;
	}
	
	public BankVO mapBankDOToVO(Bank bankDO){
		BankVO bankVO = new BankVO(bankDO.getId(), bankDO.getName());
		bankVO.setActive(OMTSwitchEnum.getById(bankDO.getStatus()));
		bankVO.setCreationDate(bankDO.getCreationDate());
		bankVO.setLastModifiedDate(bankDO.getLastModifiedDate());
		
		return bankVO;
	}
	
	
	public Bank mapBankVOToDO(BankVO bankVO){
		Bank bankDO = new Bank();
		bankDO.setId(bankVO.getId());
		bankDO.setName(bankVO.getName());
		bankDO.setStatus(bankVO.getActive().getId());
		bankDO.setCreationDate(bankVO.getCreationDate());
		bankDO.setLastModifiedDate(bankVO.getLastModifiedDate());
		
		return bankDO;
	}
	
	public List<PaymentModeVO> mapDOToVOs(List<PaymentMode> paymentModeDOs){
		List<PaymentModeVO> paymentModeVOs = new ArrayList<PaymentModeVO>();
		for(PaymentMode paymentModeDO : paymentModeDOs){
			paymentModeVOs.add(mapDOToVO(paymentModeDO));
		}
		return paymentModeVOs;
	}
	
	public PaymentModeVO mapDOToVO(PaymentMode paymentModeDO){
		PaymentModeVO paymentModeVO = new PaymentModeVO(paymentModeDO.getId());
		paymentModeVO.setName(paymentModeDO.getName());
		paymentModeVO.setDesc(paymentModeDO.getDesc());
		paymentModeVO.setActive(OMTSwitchEnum.getById(paymentModeDO.getStatus()));
		paymentModeVO.setCreationDate(paymentModeDO.getCreationDate());
		paymentModeVO.setLastModifiedDate(paymentModeDO.getLastModifiedDate());
		
		return paymentModeVO;
	}
	
	public PaymentMode mapVOToDO(PaymentModeVO paymentModeVO){
		PaymentMode paymentModeDO = new PaymentMode();
		paymentModeDO.setId(paymentModeVO.getId());
		paymentModeDO.setName(paymentModeVO.getName());
		paymentModeDO.setDesc(paymentModeVO.getDesc());
		paymentModeDO.setStatus(paymentModeVO.getActive().getId());
		paymentModeDO.setCreationDate(paymentModeVO.getCreationDate());
		paymentModeDO.setLastModifiedDate(paymentModeVO.getLastModifiedDate());
		
		return paymentModeDO;
	}
	
}
