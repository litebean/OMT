package com.litetech.omt.service.impl;

import java.util.Collections;
import java.util.List;

import com.litetech.omt.constant.SettingEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.dao.ChargeDAO;
import com.litetech.omt.dao.InvoiceDAO;
import com.litetech.omt.dao.OrderDAO;
import com.litetech.omt.dao.PaymentDAO;
import com.litetech.omt.dao.PurchaseInvoiceDAO;
import com.litetech.omt.dao.PurchaseOrderDAO;
import com.litetech.omt.dao.SettingDAO;
import com.litetech.omt.dao.UserDAO;
import com.litetech.omt.report.ReportService;
import com.litetech.omt.service.OMTCoreService;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.OrgVO;
import com.litetech.omt.vo.SearchInvoiceRequestVO;
import com.litetech.omt.vo.SearchOrderRequestVO;
import com.litetech.omt.vo.SettingVO;

public class OMTCoreServiceImpl implements OMTCoreService{

	private InvoiceDAO invoiceDAO;
	private OrderDAO orderDAO;
	private ChargeDAO chargeDAO;
	private PaymentDAO paymentDAO;
	private PurchaseOrderDAO purchaseOrderDAO;
	private PurchaseInvoiceDAO purchaseInvoiceDAO;
	private UserDAO userDAO;
	private SettingDAO settingDAO;
	private ReportService reportService;
	
	public OMTCoreServiceImpl(InvoiceDAO invoiceDAO,
			OrderDAO orderDAO, ChargeDAO chargeDAO, PaymentDAO paymentDAO,
			PurchaseOrderDAO purchaseOrderDAO, PurchaseInvoiceDAO purchaseInvoiceDAO,
			UserDAO userDAO, SettingDAO settingDAO, ReportService reportService){
		this.invoiceDAO = invoiceDAO;
		this.orderDAO = orderDAO;
		this.chargeDAO = chargeDAO;
		this.paymentDAO = paymentDAO;
		this.purchaseOrderDAO = purchaseOrderDAO;
		this.purchaseInvoiceDAO = purchaseInvoiceDAO;
		this.userDAO = userDAO;
		this.settingDAO = settingDAO;
		this.reportService = reportService;
	}
	
	@Override
	public OrderVO retrieveOrder(long orderId, boolean purchase) {
		OrderVO orderVO = null;
		if(purchase){
			orderVO = purchaseOrderDAO.retrievePurchaseOrder(orderId);
		}else{
			orderVO = orderDAO.retrieveOrder(orderId);
		}
		
		return orderVO;
	}
	
	@Override
	public void createOrder(OrderVO orderVO) {
		if(orderVO.isPurchase()){
			purchaseOrderDAO.persistPurchaseOrder(orderVO);
		}else{
			orderDAO.persistOrder(orderVO);
		}
	}

	@Override
	public InvoiceVO retrieveInvoice(long invoiceId, boolean purchase) {
		InvoiceVO invoiceVO = null;
		if(purchase){
			invoiceVO = purchaseInvoiceDAO.retrievePurchaseInvoice(invoiceId);
		}else{
			invoiceVO = invoiceDAO.retrieveInvoice(invoiceId);
		}
		return invoiceVO;
	}

	@Override
	public void createInvoice(InvoiceVO invoiceVO) {
		if(invoiceVO.isPurchase()){
			purchaseInvoiceDAO.persistPurchaseInvoice(invoiceVO);
		}else{
			invoiceDAO.persistInvoice(invoiceVO);
		}
	}
	
	@Override
	public void detachInvoiceFromOrder(long invoiceId, boolean purchase){
		if(purchase){
			purchaseInvoiceDAO.detachInvoiceFromOrder(invoiceId);
		}else{
			invoiceDAO.detachInvoiceFromOrder(invoiceId);
		}
	}
	
	
	@Override
	public void saveAndPrintInvoice(OrderVO orderVO, InvoiceVO invoiceVO){
		createInvoice(invoiceVO);
		printInvoice(orderVO, invoiceVO);
	}	
	
	
	public void printInvoice(OrderVO orderVO, InvoiceVO invoiceVO){
		
		OrgVO orgVO = userDAO.retrieveOrgMetaData();
		CustomerVO customerVO = userDAO.retrieveCustomer(orderVO.getCustomerId());
		
		reportService.printInvoice(orgVO, customerVO, orderVO, invoiceVO, fetchReportModes());
	}
	
	
	private String[] fetchReportModes(){
		SettingVO settingVO = settingDAO.fetchSetting(SettingEnum.INVOICE_REPORT_MODE.name());
		String val = settingVO.getValue();
		if(val != null){
			return val.split(";");
		}
		return null;
	}

	@Override
	public List<OrderVO> searchOrder(SearchOrderRequestVO searchOrderRequestVO) {
		
		List<OrderVO> orderVOs = Collections.emptyList();
		
		if(searchOrderRequestVO.isPurchase()){
			orderVOs = purchaseOrderDAO.searchOrders(searchOrderRequestVO);
		}else{
			orderVOs = orderDAO.searchOrders(searchOrderRequestVO);
		}
		 
		return orderVOs;
	}

	@Override
	public List<ChargeVO> retrieveAllCharges(){
		return chargeDAO.retrieveAllCharges();
	}

	@Override
	public List<InvoiceVO> searchInvoices(
			SearchInvoiceRequestVO searchInvoiceRequestVO) {
		List<InvoiceVO> invoices = Collections.emptyList();
		
		if(searchInvoiceRequestVO.isPurchase()){
			invoices = purchaseInvoiceDAO.searchPurchaseInvoices(searchInvoiceRequestVO); 
		}else{
			invoices = invoiceDAO.searchInvoices(searchInvoiceRequestVO);
		}
		return invoices;
	}

	@Override
	public void saveCharge(ChargeVO chargeVO) {
		chargeDAO.saveCharge(chargeVO);		
	}

	@Override
	public void deleteCharge(long chargeId) {
		chargeDAO.deleteCharge(chargeId);	
	}

	@Override
	public List<InvoiceVO> retrievePendingInvoices(long customerId,
			TransactionTypeEnum transactionType) {
		return paymentDAO.retrievePaymentPending(customerId, transactionType);	
	}

	@Override
	public List<InvoiceVO> retrievePendingInvoices(long customerId,
			TransactionTypeEnum transactionType, List<Long> exclInvoices) {
		return paymentDAO.retrievePaymentPending(customerId, transactionType, exclInvoices);
	}
	
	@Override
	public List<InvoicePaymentVO> retrieveInvoicePayments(final long invoiceId, 
			TransactionTypeEnum transactionType){
		return invoiceDAO.retrieveInvoicePayments(invoiceId, transactionType);
	}
}
