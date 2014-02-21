package com.litetech.omt.ui.comp;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import com.jgoodies.looks.common.RGBGrayFilter;
import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.ui.comp.listener.CloseTabListener;
import com.litetech.omt.ui.model.CustomerFormModel;
import com.litetech.omt.ui.model.InvoiceFormModel;
import com.litetech.omt.ui.model.InvoiceSearchFormModel;
import com.litetech.omt.ui.model.OrderFormModel;
import com.litetech.omt.ui.model.PaymentSearchFormDSModel;
import com.litetech.omt.ui.model.PaymentTransFormModel;
import com.litetech.omt.ui.model.ProductFormModel;
import com.litetech.omt.ui.model.SearchFormModel;
import com.litetech.omt.ui.model.SettingFormModel;
import com.litetech.omt.ui.model.ds.InvoiceFormDSModel;
import com.litetech.omt.ui.model.ds.InvoiceSearchFormDSModel;
import com.litetech.omt.ui.model.ds.OrderFormDSModel;
import com.litetech.omt.ui.model.ds.PaymentTransFormDSModel;
import com.litetech.omt.ui.model.ds.ProductFormDSModel;
import com.litetech.omt.ui.model.ds.SearchFormDSModel;
import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.UnitVO;


public class MainMenu extends JMenuBar{

	private JPanel displayPanel;
	private MenuListener menuListener;
	
	
	public static final Icon NOTE_ICON = new ImageIcon(MenuListener.class.getResource("/img/notepad-2.jpg"));
	public static final Icon SEARCH_ICON = new ImageIcon(MenuListener.class.getResource("/img/search-icon.jpg"));
	public static final Icon SETTING_ICON = new ImageIcon(MenuListener.class.getResource("/img/setting.jpg"));
	public static final Icon PAYMENT_ICON = new ImageIcon(MenuListener.class.getResource("/img/rupee_v1.jpg"));
	public static final Icon PEOPLE_ICON = new ImageIcon(MenuListener.class.getResource("/img/people.jpg"));
	public static final Icon SEARCH_PEOPLE_ICON = new ImageIcon(MenuListener.class.getResource("/img/search_people.jpg"));
	
	public MainMenu(JPanel displayPanel){
		super();
		StartUpProgressBar progressBar = new StartUpProgressBar();
		progressBar.start();
		ServiceClient.getInstance();
		progressBar.endProcess();	
		this.displayPanel = displayPanel;
		
		menuListener = new MenuListener();
		LayoutManager grid = new GridLayout(8,1);
		setLayout(grid);
		
		setOpaque(false);
		buildLeftMenu();
		setPreferredSize(new Dimension(125, 200));
		//setBorder(BorderFactory.createLineBorder(Color.GREEN));
		//setBackground(Color.GREEN); 
	}
	
	class StartUpProgressBar extends Thread{
		private ProgressBar progressBar;
		public StartUpProgressBar(){
			this.progressBar = new ProgressBar(100);
		}
		@Override
		public void run() {
			progressBar.showProgress();
		}
		
		public void endProcess(){
			System.out.println("progress bar "+progressBar);
			this.progressBar.setHideProgressBar(true);
		}
	}
	
	
	public void buildLeftMenu(){
		
		JMenu orderMenu = new JMenu("Order");
		orderMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		orderMenu.insertSeparator(2);
//		/orderMenu.setSize(1000, 1000);
				
		
		String[] orderOptions = new String[]{"Create New Order", "Search Order"};
		String[] orderOptionsCmd = new String[]{"Create_Order", "Search_Order"};
		
		for (int i = 0; i < orderOptions.length; i++) {
			JMenuItem menuItem = new JMenuItem(orderOptions[i]);
			menuItem.addActionListener(menuListener);
			menuItem.setActionCommand(orderOptionsCmd[i]);
			orderMenu.add(menuItem);
		}
		
		
		JMenu invoiceMenu = new JMenu("Invoice");
		invoiceMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		invoiceMenu.insertSeparator(2);
		
		String[] invoiceOptions = new String[]{"Create New Invoice", "Search Invoice"};
		String[] invoiceOptionsCmd = new String[]{"Create_Invoice", "Search_Invoice"};
		
		for (int i = 0; i < invoiceOptions.length; i++) {
			JMenuItem menuItem = new JMenuItem(invoiceOptions[i]);
			menuItem.addActionListener(menuListener);
			menuItem.setActionCommand(invoiceOptionsCmd[i]);
			invoiceMenu.add(menuItem);
		}
		
		
		JMenu paymentMenu = new JMenu("Payment");
		paymentMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		paymentMenu.insertSeparator(2);
		
		String[] paymentOptions = new String[]{"Make Payment", "Search Payment"};
		String[] paymentOptionsCmd = new String[]{"Make_Payment", "Search_Payment"};
		
		for (int i = 0; i < paymentOptions.length; i++) {
			JMenuItem menuItem = new JMenuItem(paymentOptions[i]);
			menuItem.addActionListener(menuListener);
			menuItem.setActionCommand(paymentOptionsCmd[i]);
			paymentMenu.add(menuItem);
		}
		
		JMenu purchaseOrderMenu = new JMenu("Purchase Order");
		purchaseOrderMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		purchaseOrderMenu.insertSeparator(2);
		
		String[] purchaseOrderOptions = new String[]{"Create Purchase Order", "Search Purchase Order"};
		String[] purchaseOrderOptionsCmd = new String[]{"Create_Purchase_Order", "Search_Purchase_Order"};
		
		for (int i = 0; i < purchaseOrderOptions.length; i++) {
			JMenuItem menuItem = new JMenuItem(purchaseOrderOptions[i]);
			menuItem.addActionListener(menuListener);
			menuItem.setActionCommand(purchaseOrderOptionsCmd[i]);
			purchaseOrderMenu.add(menuItem);
		}
		
		
		JMenu purchaseInvoiceMenu = new JMenu("Purchase Invoice");
		purchaseInvoiceMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		purchaseInvoiceMenu.insertSeparator(2);
		
		String[] purchaseInvoiceOptions = new String[]{"Create Purchase Invoice", "Search Purchase Invoice"};
		String[] purchaseInvoiceOptionsCmd = new String[]{"Create_Purchase_Invoice", "Search_Purchase_Invoice"};
		
		for (int i = 0; i < purchaseInvoiceOptions.length; i++) {
			JMenuItem menuItem = new JMenuItem(purchaseInvoiceOptions[i]);
			menuItem.addActionListener(menuListener);
			menuItem.setActionCommand(purchaseInvoiceOptionsCmd[i]);
			purchaseInvoiceMenu.add(menuItem);
		}
		
		JMenu customerMenu = new JMenu("Manage Customer");
		customerMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		customerMenu.insertSeparator(2);
		
		String[] customerOptions = new String[]{"Add Customer", "Search Customer"};
		String[] customerOptionsCmd = new String[]{"Add_Customer", "Search_Customer"};
		
		for (int i = 0; i < customerOptions.length; i++) {
			JMenuItem menuItem = new JMenuItem(customerOptions[i]);
			menuItem.addActionListener(menuListener);
			menuItem.setActionCommand(customerOptionsCmd[i]);
			customerMenu.add(menuItem);
		}
		
		JMenu settingMenu = new JMenu("Settings");
		settingMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		settingMenu.insertSeparator(2);
		
		String[] settingOptions = new String[]{"Configure Product", "Configure Unit/Tax", "Back-Up"};
		String[] settingOptionsCmd = new String[]{"Configure_Product", "Configure_UnitTax", "Back-Up"};
		
		for (int i = 0; i < settingOptions.length; i++) {
			JMenuItem menuItem = new JMenuItem(settingOptions[i]);
			menuItem.addActionListener(menuListener);
			menuItem.setActionCommand(settingOptionsCmd[i]);
			settingMenu.add(menuItem);
		}
		
		JMenu reportMenu = new JMenu("Report");
		reportMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		reportMenu.insertSeparator(2);
		
		String[] reportOptions = new String[]{"Income Expense Report"};
		String[] reportOptionsCmd = new String[]{"Income_Expense_Report"};
		
		for (int i = 0; i < reportOptions.length; i++) {
			JMenuItem menuItem = new JMenuItem(reportOptions[i]);
			menuItem.addActionListener(menuListener);
			menuItem.setActionCommand(reportOptionsCmd[i]);
			reportMenu.add(menuItem);
		}
		
		add(invoiceMenu);
		add(purchaseInvoiceMenu);
		add(paymentMenu);
		add(customerMenu);
		add(settingMenu);
		add(reportMenu);
		add(orderMenu);
		add(purchaseOrderMenu);
	}
	
	
	private void takeBackUp(){
		try{
			String backupPath = ServiceClient.getInstance().takeBackUp();
			JOptionPane.showMessageDialog(null, "back up successful "+backupPath);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public class MenuListener implements ActionListener{
		
		
		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();
			if("Back-Up".equals(command)){
				takeBackUp();
				return;
			}
			
			OMTTabbedPane tabbedPane = (OMTTabbedPane)displayPanel.getComponent(0);
			Component[] tabComps = tabbedPane.getComponents();
			
			boolean tabAlreadyOpened = false;
			if(tabComps != null){
				for (int i = 0; i < tabComps.length; i++) {
					if(command.equals(tabComps[i].getName())){
						System.out.println("Tab Already openend");
						
						tabAlreadyOpened = true;
						tabbedPane.resetSelectedComponent(tabComps[i]);
						break;
					}
				}
			}
			
			if(!tabAlreadyOpened){
				
				
				Icon compIcon = null;
				
				JComponent comp =  new JLabel("This is "+command);
				String commandNamePrefix = "Name";
				if(command.equals("Create_Order")){ 
					compIcon = NOTE_ICON;
					comp = new OrderForm(constructMockOrder(false), constructFormDSModel(), displayPanel);
					commandNamePrefix = "PO: ";
				}else if(command.equals("Create_Purchase_Order")){ 
					compIcon = NOTE_ICON;
					comp = new OrderForm(constructMockOrder(true), constructFormDSModel(), displayPanel);
					commandNamePrefix = "Order: ";
				}else if(command.equals("Search_Order")){
					compIcon = SEARCH_ICON;
					SearchFormModel searchFormModel = buildSearchFormModel();
					comp = new OrderSearchFrame(displayPanel, searchFormModel, constructSearchDSModel(), false);
					commandNamePrefix = "Search: ";
				}else if(command.equals("Search_Purchase_Order")){
					compIcon = SEARCH_ICON;
					SearchFormModel searchFormModel = buildSearchFormModel();
					comp = new OrderSearchFrame(displayPanel, searchFormModel, constructSearchDSModel(), true);
					commandNamePrefix = "Search: ";
				}else if(command.equals("Create_Invoice")){
					compIcon = NOTE_ICON;
					comp = new InvoiceForm(constructMockInvoice(false), constructInvocieFormDSModel());
					commandNamePrefix = "Invoice: ";
				}else if(command.equals("Create_Purchase_Invoice")){
					compIcon = NOTE_ICON;
					comp = new InvoiceForm(constructMockInvoice(true), constructInvocieFormDSModel());
					commandNamePrefix = "Purchase Invoice: ";
				}else if(command.equals("Search_Invoice")){
					compIcon = SEARCH_ICON;
					comp = new InvoiceSearchFrame(displayPanel, new InvoiceSearchFormModel(), constructInvSearchDSModel(), false);
					commandNamePrefix = "Search: ";
				}else if(command.equals("Search_Purchase_Invoice")){
					compIcon = SEARCH_ICON;
					comp = new InvoiceSearchFrame(displayPanel, new InvoiceSearchFormModel(), constructInvSearchDSModel(), true);
					commandNamePrefix = "Search: ";
				}else if(command.equals("Make_Payment")){
					compIcon = PAYMENT_ICON;
					
					comp = new PaymentTransactionForm(constructPaymentTransDSModel(), constructPaymentTransModel());
					commandNamePrefix = "Payment: ";
				}else if(command.equals("Search_Payment")){
					compIcon = PAYMENT_ICON;
					
					comp = new PaymentSearchFrame(constructPaymentSearchDSModel());
					commandNamePrefix = "Search: ";
				}else if(command.equals("Configure_Product")){
					compIcon = SETTING_ICON;
					comp = new ProductForm(constructProductFormModel(), constructProductDSModel());
					commandNamePrefix = "Setting: ";
				}else if(command.equals("Configure_UnitTax")){
					compIcon = SETTING_ICON;
					comp = new SettingForm(constructSettingModel());
					commandNamePrefix = "Unit Tax:";
				}else if(command.equals("Add_Customer")){
					compIcon = PEOPLE_ICON;
					comp = new CustomerForm(new CustomerFormModel());
					commandNamePrefix = "Customer:";
				}else if(command.equals("Search_Customer")){
					compIcon = SEARCH_PEOPLE_ICON;
					comp = new SearchCustomerForm();
				}else if(command.equals("Income_Expense_Report")){
					compIcon = PAYMENT_ICON;
					comp = new ProfitLossReportForm();
				}
				
				comp.setName(command);
				comp.setOpaque(true);
				comp.setBackground(Color.WHITE);
				
				Util.buildCloseTab(command, command, tabbedPane, comp, compIcon, 
						new CloseTabListener(displayPanel, comp));
				//tabbedPane.setSelectedComponent(newContainer);
			}
			
			ComponentRegistry.instance().repaintComponents();
		}
		
		
		public SettingFormModel constructSettingModel(){
			SettingFormModel formModel = new SettingFormModel();
			List<UnitVO> unitVOs = ServiceClient.getInstance().retrieveAllUnits();
			formModel.setUnitVOs(unitVOs);
			
			List<ChargeVO> chargeVOs = ServiceClient.getInstance().retrieveAllCharges();
			formModel.setChargeVOs(chargeVOs);
			
			List<BankVO> bankVOs = ServiceClient.getInstance().retrieveAllBanks();
			formModel.setBankVOs(bankVOs);
			
			List<PaymentModeVO> paymentModeVOs = ServiceClient.getInstance().retrieveAllPaymentModes();
			formModel.setPaymentModeVOs(paymentModeVOs);
			
			return formModel;
		}
		
		public ProductFormModel constructProductFormModel(){
			ProductFormModel formModel = new ProductFormModel();
					
			
			return formModel;
		}
		
		public ProductFormDSModel constructProductDSModel(){
			ProductFormDSModel dsModel = new ProductFormDSModel();
			
			List<UnitVO> unitVOs = ServiceClient.getInstance().retrieveAllUnits();
			
			dsModel.setUnitVOs(unitVOs);
			return dsModel;
		}
		
		public SearchFormModel buildSearchFormModel(){
			SearchFormModel searchFormModel = new SearchFormModel();
			//searchFormModel.setOrderId();
			//searchFormModel.setOrderDate(new Date());
			//searchFormModel.setSelectedCustomerId(10003l);
			searchFormModel.setSelectedStatusId(OrderStatusEnum.PENDING.getId());
			return searchFormModel;
			
		}
		
		private InvoiceSearchFormDSModel constructInvSearchDSModel(){
			List<CustomerVO> customerVOs = ServiceClient.getInstance().retrieveAllCustomers();
			
			InvoiceSearchFormDSModel invoiceDSModel = new InvoiceSearchFormDSModel();
			invoiceDSModel.setCustomers(customerVOs);
			
			return invoiceDSModel;
		}
		
		private SearchFormDSModel constructSearchDSModel(){
			SearchFormDSModel searchFormDSModel = new SearchFormDSModel();
			List<CustomerVO> customerVOs = ServiceClient.getInstance().retrieveAllCustomers();
			searchFormDSModel.setCustomers(customerVOs);
			return searchFormDSModel;
		}
		
		
		private OrderFormDSModel constructFormDSModel(){
			OrderFormDSModel orderFormDSModel = new OrderFormDSModel();
			List<CustomerVO> customerLs = ServiceClient.getInstance().retrieveAllCustomers();
			orderFormDSModel.setCustomers(customerLs);
			
			List<ProductVO> productVOs = ServiceClient.getInstance().retrieveAllProducts();
			orderFormDSModel.setProductVOs(productVOs);
			populateMockDSModel(orderFormDSModel);
			
			return orderFormDSModel;
		}
		
		private OrderFormDSModel populateMockDSModel(OrderFormDSModel orderFormDSModel){
					
			List<ChargeVO> taxVOs = new ArrayList<ChargeVO>();
			taxVOs.add(new ChargeVO(1001, "Sales Tax 5 %"));
			taxVOs.add(new ChargeVO(1002, "Sales Tax 7 %"));
			taxVOs.add(new ChargeVO(1002, "No Tax"));
			orderFormDSModel.setSalesAcctVOs(taxVOs);
						
			
			return orderFormDSModel;
		}
		
		
		
		private OrderFormModel constructMockOrder(boolean purchase){
			OrderFormModel orderFormModel = new OrderFormModel(purchase);
			orderFormModel.setOrderId(0);
			orderFormModel.setOrderDate(new Date());
			orderFormModel.setOrderStatus(OrderStatusEnum.PENDING);
					
			
			return orderFormModel;
		}
		
		
		private InvoiceFormModel constructMockInvoice(boolean purchase){
			InvoiceFormModel formModel = new InvoiceFormModel(purchase);
			Date currentDate = new Date();
			formModel.setInvoiceDate(currentDate);
			formModel.setOrderDate(currentDate);
			
			return formModel;
		}
		
		private InvoiceFormDSModel constructInvocieFormDSModel(){
			InvoiceFormDSModel invoiceFormDSModel = new InvoiceFormDSModel();
			List<CustomerVO> customerLs = ServiceClient.getInstance().retrieveAllCustomers();
			invoiceFormDSModel.setCustomers(customerLs);
			
			List<ProductVO> productVOs = ServiceClient.getInstance().retrieveAllProducts();
			invoiceFormDSModel.setProductVOs(productVOs);
			
			List<ChargeVO> chargeVOs = ServiceClient.getInstance().retrieveAllCharges();
			invoiceFormDSModel.setChargeVOs(chargeVOs);
			
			return invoiceFormDSModel;
		}
		
		
		private PaymentSearchFormDSModel constructPaymentSearchDSModel(){
			PaymentSearchFormDSModel formDSModel = new PaymentSearchFormDSModel();
			
			List<CustomerVO> customerLs = ServiceClient.getInstance().retrieveAllCustomers();
			formDSModel.setCustomers(customerLs);
			
			
			List<BankVO> bankVOs = ServiceClient.getInstance().retrieveActiveBanks();
			formDSModel.setBanks(bankVOs);
			
			List<PaymentModeVO> paymentModeVOs = ServiceClient.getInstance().retrieveActivePaymentModes();
			formDSModel.setModes(paymentModeVOs);
			
			return formDSModel;
		}
		
		private PaymentTransFormDSModel constructPaymentTransDSModel(){
			PaymentTransFormDSModel formDSModel = new PaymentTransFormDSModel();
			
			List<CustomerVO> customerLs = ServiceClient.getInstance().retrieveAllCustomers();
			formDSModel.setCustomerVOLs(customerLs);
			
			
			List<BankVO> bankVOs = ServiceClient.getInstance().retrieveActiveBanks();
			formDSModel.setBankVOs(bankVOs);
			
			List<PaymentModeVO> paymentModeVOs = ServiceClient.getInstance().retrieveActivePaymentModes();
			formDSModel.setPaymentModeVOs(paymentModeVOs);
			
			return formDSModel;
		}
		
		
		private PaymentTransFormModel constructPaymentTransModel(){
			PaymentTransFormModel formModel = new PaymentTransFormModel();
			
			formModel.setCustomerId(1);
			
			AddressVO addressVO = new AddressVO();
			/*addressVO.setAddressLine1("No.3, Ekambara Iyer St.,");
			addressVO.setAddressLine2("Venkatapuram, Ambattur");
			addressVO.setCity("Chennai");
			addressVO.setState("TamilNadu");
			addressVO.setCountry("India");*/
			formModel.setAddressVO(addressVO);
			
			return formModel;
		}
	}
	
	
}
