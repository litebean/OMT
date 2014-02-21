package com.litetech.omt.client;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.service.OMTAdminService;
import com.litetech.omt.service.OMTCoreService;
import com.litetech.omt.service.OMTInventoryService;
import com.litetech.omt.service.OMTPaymentService;
import com.litetech.omt.service.OMTUserService;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.IncomeExpenseVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;
import com.litetech.omt.vo.SearchInvoiceRequestVO;
import com.litetech.omt.vo.SearchOrderRequestVO;
import com.litetech.omt.vo.SearchProductRequestVO;
import com.litetech.omt.vo.SearchTransactionRequestVO;
import com.litetech.omt.vo.SearchUserRequestVO;
import com.litetech.omt.vo.UnitVO;

public class ServiceClient {
	
	
	private  static ServiceClient instance = new ServiceClient();
	
	private OMTCoreService coreService;
	private OMTUserService userService;
	private OMTInventoryService inventoryService;
	private OMTPaymentService paymentService;
	private OMTAdminService adminService;
	
	private ServiceClient(){
		ApplicationContext context = new ClassPathXmlApplicationContext("config/applicationcontext.xml");
		//BeanFactory context = new XmlBeanFactory(new ClassPathResource("config/applicationcontext.xml"));
		this.coreService = (OMTCoreService)context.getBean("omtCoreService");
		this.userService = (OMTUserService)context.getBean("omtUserService");
		this.inventoryService = (OMTInventoryService)context.getBean("omtInventoryService");
		this.paymentService = (OMTPaymentService)context.getBean("omtPaymentService");
		this.adminService = (OMTAdminService)context.getBean("omtAdminService");
	}
	
	public static ServiceClient getInstance(){
		return instance;
	}
	
	
	public OrderVO retrieveOrder(final long orderId, boolean purchase){
		return coreService.retrieveOrder(orderId, purchase);
	}
	
	public void createOrder(OrderVO orderVO){
		coreService.createOrder(orderVO);
	}
	
	public InvoiceVO retrieveInvoice(final long invoiceId, boolean purchase){
		return coreService.retrieveInvoice(invoiceId, purchase);
	}
	
	public void createInvoice(InvoiceVO  invoiceVO){
		coreService.createInvoice(invoiceVO);
	}
	
	
	public void detachInvoiceFromOrder(long invoiceId, boolean purchase){
		coreService.detachInvoiceFromOrder(invoiceId, purchase);
	}
	
	public void saveAndPrintInvoice(OrderVO orderVO, InvoiceVO  invoiceVO){
		coreService.saveAndPrintInvoice(orderVO, invoiceVO);
	}
	
	public List<OrderVO> searchOrder(SearchOrderRequestVO searchOrderRequestVO){
		 return coreService.searchOrder(searchOrderRequestVO);		
	}
	
	
	public List<CustomerVO> retrieveAllCustomers(){
		return userService.retrieveAllCustomers();
	}
	
	public CustomerVO retrieveCustomer(final long customerId){
		return userService.retrieveCustomer(customerId);
	}
	
	public List<ProductVO> retrieveAllProducts(){
		return inventoryService.fetchActiveProducts();
	}
	
	
	public List<ProductVO> retrieveProducts(List<Long> productIds){
		return inventoryService.fetchProducts(productIds);
	}
	
	
	public List<ChargeVO> retrieveAllCharges(){
		return coreService.retrieveAllCharges();
	}
	
	public List<InvoiceVO> searchInvoices(SearchInvoiceRequestVO searchInvoiceRequestVO){
		return coreService.searchInvoices(searchInvoiceRequestVO);
	}
	
	public List<ProductVO> searchProducts(SearchProductRequestVO searchProductRequestVO){
		return inventoryService.searchProduct(searchProductRequestVO);
	}
	
	public List<Long> findProductIdByName(String prodName){
		return inventoryService.findProductIdByName(prodName);
	}
	
	public ProductVO retrieveProduct(final long productId){
		return inventoryService.fetchProduct(productId);
	}
	
	
	public List<UnitVO> retrieveAllUnits(){
		return inventoryService.retrieveAllUnits();
	}
	
	
	public void saveProduct(ProductVO productVO){
		inventoryService.saveProduct(productVO);
	}
	
	public void saveUnit(UnitVO unitVO){
		inventoryService.saveUnit(unitVO);
	}
	
	public void deleteUnit(final long unitId){
		inventoryService.deleteUnit(unitId);
	}
	
	
	public void saveCharge(final ChargeVO chargeVO){
		coreService.saveCharge(chargeVO);
	}
	
	public void deleteCharge(final long chargeId){
		coreService.deleteCharge(chargeId);
	}
	
	public List<InvoiceVO> retrievePaymentPendingInvcs(final long customerId, 
			TransactionTypeEnum transType){
		return coreService.retrievePendingInvoices(customerId, transType);
	}
	
	public List<InvoiceVO> retrievePaymentPendingInvcs(final long customerId, 
			TransactionTypeEnum transType, List<Long> exclInvoices){
		return coreService.retrievePendingInvoices(customerId, transType, exclInvoices);
	}
	
	public void saveTransaction(PaymentTransactionVO paymentTransactionVO){
		paymentService.saveTransaction(paymentTransactionVO);
	}
	
	public List<InvoicePaymentVO> retrieveInvoicePayments(final long invoiceId, TransactionTypeEnum transType){
		return coreService.retrieveInvoicePayments(invoiceId, transType);
	}
	
	
	public PaymentTransactionVO retrieveTransaction(final long transactionId){
		return paymentService.retrieveTransaction(transactionId);
	}
	
	public List<BankVO> retrieveAllBanks(){
		return paymentService.retrieveAllBank();
	}
	
	public void saveBank(BankVO bankVO){
		paymentService.saveBank(bankVO);
	}
	
	public List<BankVO> retrieveActiveBanks(){
		return paymentService.retrieveActiveBanks();
	}
	
	public void deleteBank(long bankId){
		paymentService.deleteBank(bankId);
	}
	
	
	public List<PaymentModeVO> retrieveAllPaymentModes(){
		return paymentService.retrieveAllPaymentModes();
	}
	
	public List<PaymentModeVO> retrieveActivePaymentModes(){
		return paymentService.retrieveActivePaymentModes();
	}
	
	public void savePaymentMode(PaymentModeVO paymentModeVO){
		paymentService.savePaymentMode(paymentModeVO);
	}
	
	public void deletePaymentMode(long paymentModeId){
		paymentService.deletePaymentMode(paymentModeId);
	}
	
	public List<PaymentTransactionVO> searchPaymentTransaction(SearchTransactionRequestVO searchTransactionRequestVO){
		return paymentService.searchPaymentTransaction(searchTransactionRequestVO);
	}
	
	
	public void saveCustomer(CustomerVO customerVO){
		userService.saveOrUpdateCustomer(customerVO);
	}
	
	public List<CustomerVO> searchCustomers(SearchUserRequestVO searchUserRequestVO){
		return userService.searchCustomers(searchUserRequestVO);
	}
	
	public List<Long> findUnitIds(String unitName){
		return inventoryService.findUnitIds(unitName);
	}
	
	public void printProfitLossReport(ProfitLossSearchRequest searchRequest,
			List<PaymentTransactionVO> paymentTransactionVOs,
			IncomeExpenseVO incomeExpenseVO){
		paymentService.printProfitLossReport(searchRequest, paymentTransactionVOs, 
				incomeExpenseVO);
	}
	
	public List<PaymentTransactionVO> searchPaymentTransaction(ProfitLossSearchRequest request){
		return paymentService.searchPaymentTransaction(request);
	}
	
	
	public String takeBackUp() throws IOException{
		return adminService.dumpDB(true);
	}
}
