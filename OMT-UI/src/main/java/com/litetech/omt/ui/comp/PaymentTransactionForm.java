package com.litetech.omt.ui.comp;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.constant.PaymentStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.ui.comp.action.PaymentTransAction;
import com.litetech.omt.ui.comp.editor.LiteEditableComboBoxEditor;
import com.litetech.omt.ui.comp.listener.TableCellListener;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.comp.validator.PaymentTransactionValidator;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.OrderItemModel;
import com.litetech.omt.ui.model.PaymentTransFormModel;
import com.litetech.omt.ui.model.ds.PaymentTransFormDSModel;
import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;

public class PaymentTransactionForm extends JPanel implements PanelTabComp{

	private JTextField FIELD_PAY_TRAN_ID;
	private JComboBox FIELD_TRANS_CUSTOMER;
	private JComboBox FIELD_TRANS_TYPE;
	private JComboBox FIELD_PAY_STATUS;

	private JComboBox FIELD_PAY_BANK;
	private JComboBox FIELD_PAY_MODE;
	private JTextField FIELD_PAY_MODE_REF_TXT;
	private JTextField FIELD_PAY_TRAN_AMT;
	private OMTCalendar FIELD_PAY_TRAN_DATE;
	private JTextField FIELD_PAY_TRAN_DESC;
	private JTextField FIELD_UNUTILIZED_AMT;
	private JTable paymentTable;
	
	private JPanel rootPanel;
	private PaymentTransFormDSModel formDSModel;
	private PaymentTransFormModel formModel;
	private JPanel addressPanel;
	private JPanel tempAddressPanel;
	private JPanel invoicePayemntPanel;
	private PaymentTransAction paymentTransAction;
	private PaymentTransactionValidator transactionValidator; 
	private boolean pullFreshInvoices;
	
	private JPanel myPanel;
	@Override
	public void setMyPanelTab(JPanel myPanel) {
		this.myPanel = myPanel;	
	}


	@Override
	public JPanel getMyPanelTab() {
		return myPanel;
	}
	
	public PaymentTransactionForm(PaymentTransFormDSModel formDSModel,
			PaymentTransFormModel formModel){
		
		this.formDSModel = formDSModel;
		this.formModel = formModel;
		
		this.setBackground(Color.WHITE);
		
		rootPanel = new JPanel();
		rootPanel.setBackground(Color.WHITE);
		rootPanel.setPreferredSize(AppContext.getInstance().getBaseFormPanelDimension());

		transactionValidator = new PaymentTransactionValidator();
		
		rootPanel.add(constructCustomerMetaDataPanel());
		rootPanel.add(constructPaymentMetaDataPanel());
		rootPanel.add(constructInvoicePaymentPanel());
		add(rootPanel);
	}
	
	
	private JPanel constructInvoicePaymentPanel(){
		invoicePayemntPanel = new JPanel();
		invoicePayemntPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 300));
		populateInvoicePayment(invoicePayemntPanel);
		populateTransControlPanel(invoicePayemntPanel);
		return invoicePayemntPanel;
	}
	
	
	private void populateTransPayment(){
		if(invoicePayemntPanel == null){
			constructInvoicePaymentPanel();
		}else{
			invoicePayemntPanel.removeAll();
			
			populateInvoicePayment(invoicePayemntPanel);
			populateTransControlPanel(invoicePayemntPanel);
		}
		
		ComponentRegistry.instance().repaintComponents();
	}
	
	private void populateTransControlPanel(JPanel invoicePayemntPanel){
		JPanel controlPanel = new JPanel();
		
		controlPanel.setPreferredSize(new Dimension((AppContext.getInstance().getWorkAreaWidth()), 35));
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		Box controlRow = Box.createHorizontalBox();
		
		
		
		JButton saveTransBt = new JButton("Save");
		saveTransBt.addActionListener(new SaveTransactionListener());
		controlRow.add(saveTransBt);
		controlRow.add(Box.createHorizontalStrut(30));
		
		JButton cancelTransBt = new JButton("Cancel");
		//controlRow.add(cancelTransBt);
		controlRow.add(Box.createHorizontalStrut(100));
		
		int selectedStatus = (int)((LiteComboModel)FIELD_PAY_STATUS.getSelectedItem()).getId();
		if(PaymentStatusEnum.CANCELLED.getId() == selectedStatus){
			saveTransBt.setEnabled(false);
			cancelTransBt.setEnabled(false);
		}
		
		controlPanel.add(controlRow);
		
		invoicePayemntPanel.add(controlPanel);
	}
	
	private void populateInvoicePayment(JPanel invoicePayemntPanel){
		/*final Object[][] rows = {{"10001","Tiles1", "1-1-2013", "New", "100.0"}, {"10002","Tiles2", "1-1-2013", "New", "100.0"},
				{"10003","Tiles3", "1-1-2013", "New", "100.0"}};*/
		
		final Object[] invoiceHeader = {"Payment Id", "OrderId", "Invoice Id", "Invoice Date", "Invoice Amount", "Pending Amount", "Pay Amount"};		
		final Object[][] rows  = formModel.getPaymentRows();
		
		DefaultTableModel defaultTableModel = new DefaultTableModel(rows, invoiceHeader){
			 @Override
            public boolean isCellEditable ( int row, int column ){
				 if(column == 6){
					 /*long payId = new Long(this.getValueAt(row, 0).toString());
					 if(payId == 0) {
						 return true;
					 }*/
					 return true;
				 }
				 return false;
            }
		};
		
		paymentTable = new JTable(defaultTableModel); 
		paymentTable.setRowHeight(20);
		paymentTable.setBorder(LineBorder.createBlackLineBorder());
		paymentTable.setRowSelectionAllowed(true);
		paymentTable.setPreferredScrollableViewportSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 180));
		paymentTable.setFillsViewportHeight(true);
		
		paymentTable.getColumn(invoiceHeader[1]).setPreferredWidth(100);
		paymentTable.getColumn(invoiceHeader[2]).setPreferredWidth(100);
		paymentTable.getColumn(invoiceHeader[3]).setPreferredWidth(100);
		paymentTable.getColumn(invoiceHeader[4]).setPreferredWidth(100);
		paymentTable.getColumn(invoiceHeader[5]).setPreferredWidth(100);
		paymentTable.getColumn(invoiceHeader[6]).setPreferredWidth(100);
		//paymentTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		paymentTable.setDragEnabled(false);
		
		paymentTable.addMouseListener(new MouseAdapter() {
	        	
    	 @Override
    	  public void mouseClicked(MouseEvent e) {
    	    JTable target = (JTable)e.getSource();
    	    int row = target.getSelectedRow();
    	    int column = target.getSelectedColumn();
    	    
    	    if(column == 0){
    	    	String invoiceId = rows[row][1].toString();
    	    	System.out.println("invoice clicked "+invoiceId);
    	    } 
    	  }
    	});
		
		 
        JScrollPane scrollPane = new JScrollPane(paymentTable);
        invoicePayemntPanel.add(scrollPane);
        
        JPanel remainingAmtPanel = new JPanel();
        remainingAmtPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        remainingAmtPanel.setPreferredSize(new Dimension((AppContext.getInstance().getWorkAreaWidth()-20), 30));
        
        JLabel remainingAmtLabel = new JLabel("Unutilized Amount: ");
        FIELD_UNUTILIZED_AMT = new JTextField(String.valueOf(formModel.getUnutilizedAmount()), 15);
        FIELD_UNUTILIZED_AMT.setEditable(false);
        
        remainingAmtPanel.add(remainingAmtLabel);
        remainingAmtPanel.add(FIELD_UNUTILIZED_AMT);
        invoicePayemntPanel.add(remainingAmtPanel);
        
        paymentTransAction = new PaymentTransAction(FIELD_PAY_TRAN_AMT, FIELD_UNUTILIZED_AMT);
        paymentTransAction.registerTextFieldActionListener(paymentTable);
        
        TableCellListener cellListener = new TableCellListener(paymentTable, paymentTransAction);
	}
	
	
	
	public JPanel constructPaymentMetaDataPanel(){
		JPanel paymentMetaDataPanel = new JPanel();
		//paymentMetaDataPanel.setBackground(Color.WHITE);
		paymentMetaDataPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 90));
		
		JPanel bankDataPanel = new JPanel();
		populatePaymentMetaDataFields(bankDataPanel);
		paymentMetaDataPanel.add(bankDataPanel);
		
		populatePaymentControlPanel(paymentMetaDataPanel);
		
		return paymentMetaDataPanel;
	}
	
	private void populatePaymentControlPanel(JPanel paymentMetaDataPanel){
		JPanel controlPanel = new JPanel();
		//controlPanel.setBackground(Color.GREEN);
		controlPanel.setPreferredSize(new Dimension((AppContext.getInstance().getWorkAreaWidth()), 35));
		controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		Box controlRow = Box.createHorizontalBox();
		//controlRow.add(Box.createHorizontalStrut(150));
		
		JButton searchInvoiceBt = new JButton("Find Pending Invoices");
		searchInvoiceBt.addActionListener(new FindPendingInvoiceListener());
		//searchInvoiceBt.setPreferredSize(new Dimension(100, 50));
		controlRow.add(searchInvoiceBt);
		controlRow.add(Box.createHorizontalStrut(100));
		controlPanel.add(controlRow);
		paymentMetaDataPanel.add(controlPanel);
	}
	
	private void populatePaymentMetaDataFields(JPanel paymentMetaDataPanel){
		
		GridLayout gridLayout = new GridLayout(2, 8, 2, 2);
		paymentMetaDataPanel.setLayout(gridLayout);
			
		JLabel bankNameLbl = new JLabel("Bank");
		
		Vector<LiteComboModel> bankList = new Vector<LiteComboModel>();
		List<BankVO> bankVOLs = formDSModel.getBankVOs();
		for(BankVO bankVO : bankVOLs){
			bankList.add(new LiteComboModel(bankVO.getId(), bankVO.getName()));
		}		
		
		FIELD_PAY_BANK = new JComboBox(bankList);
		 
		LiteComboModel selectedBank = new LiteComboModel(formModel.getBankId());
		FIELD_PAY_BANK.setSelectedItem(selectedBank);
		FIELD_PAY_BANK.setRenderer(new LiteComboModelRenderer());
		
		
		paymentMetaDataPanel.add(bankNameLbl);
		paymentMetaDataPanel.add(FIELD_PAY_BANK);
		paymentMetaDataPanel.add(Box.createHorizontalStrut(10));
		
		JLabel paymentModeLbl = new JLabel("Payment Mode");
		
		Vector<LiteComboModel> paymentModeList = new Vector<LiteComboModel>();
		List<PaymentModeVO> paymentModeVOs = formDSModel.getPaymentModeVOs();
		for(PaymentModeVO paymentModeVO : paymentModeVOs){
			paymentModeList.add(new LiteComboModel(paymentModeVO.getId(), paymentModeVO.getName()));
		}
		FIELD_PAY_MODE = new JComboBox(paymentModeList);
		LiteComboModel selectedPaymentMode = new LiteComboModel(formModel.getPaymentModeId());
		FIELD_PAY_MODE.setSelectedItem(selectedPaymentMode);
		FIELD_PAY_MODE.setRenderer(new LiteComboModelRenderer());
		
		paymentMetaDataPanel.add(paymentModeLbl);
		paymentMetaDataPanel.add(FIELD_PAY_MODE);
		paymentMetaDataPanel.add(Box.createHorizontalStrut(10));
		
		JLabel paymentRefLbl = new JLabel("Payment Ref");
		FIELD_PAY_MODE_REF_TXT = new JTextField(formModel.getPayRefText());
		
		paymentMetaDataPanel.add(paymentRefLbl);
		paymentMetaDataPanel.add(FIELD_PAY_MODE_REF_TXT);
		
		//paymentMetaDataPanel.add(Box.createHorizontalStrut(10));
				
		JLabel transactionAmtLbl = new JLabel("Amount");
		FIELD_PAY_TRAN_AMT = new JTextField(String.valueOf(formModel.getTransactionAmount()));
		
		paymentMetaDataPanel.add(transactionAmtLbl);
		paymentMetaDataPanel.add(FIELD_PAY_TRAN_AMT);
		paymentMetaDataPanel.add(Box.createHorizontalStrut(10));
		
		JLabel transactionDateLbl = new JLabel("Date");
		FIELD_PAY_TRAN_DATE = new OMTCalendar(formModel.getTransactionDate());
		
		paymentMetaDataPanel.add(transactionDateLbl);
		paymentMetaDataPanel.add(FIELD_PAY_TRAN_DATE);
		paymentMetaDataPanel.add(Box.createHorizontalStrut(10));
		
		JLabel transactionDescLbl = new JLabel("Desc");
		FIELD_PAY_TRAN_DESC = new JTextField(formModel.getDesc());
		
		paymentMetaDataPanel.add(transactionDescLbl);
		paymentMetaDataPanel.add(FIELD_PAY_TRAN_DESC);
				
	}
	
	public JPanel constructCustomerMetaDataPanel(){
		JPanel customerMetaData = new JPanel();
		customerMetaData.setBackground(Color.WHITE);
		customerMetaData.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 120));
		
		populateCustomerMetaDataFields(customerMetaData);
		return customerMetaData;
	}
	
	public void populateCustomerMetaDataFields(JPanel customerMetaData){
		
		JPanel customerTransPanel = new JPanel(); 
		int midArea = (AppContext.getInstance().getWorkAreaWidth() - 10) /  3;
		customerTransPanel.setPreferredSize(new Dimension(midArea, 110));
		//invoicePanel.setBackground(Color.WHITE);
		populateCustomerMetaData(customerTransPanel);
		
		addressPanel = new JPanel();
		//orderPanel.setBackground(Color.WHITE);
		addressPanel.setPreferredSize(new Dimension(((AppContext.getInstance().getWorkAreaWidth() - 10) - midArea), 110));
		populateCustomerAddressFields(addressPanel);
		
				
		customerMetaData.add(customerTransPanel);
		customerMetaData.add(addressPanel);
				
	}
	
	
	private void populateCustomerMetaData(JPanel  customerTransPanel){
		GridLayout gridLayout = new GridLayout(4, 2, 4, 6);
		customerTransPanel.setLayout(gridLayout);
		
		JLabel transactionIdLabel = new JLabel("Transaction Id", 10);
		FIELD_PAY_TRAN_ID = new JTextField(String.valueOf(formModel.getTransactionId()));
		FIELD_PAY_TRAN_ID.setEditable(false);
		customerTransPanel.add(transactionIdLabel);
		customerTransPanel.add(FIELD_PAY_TRAN_ID);
		
		JLabel customerName = new JLabel("Customer", 10);
		
		
		Vector<LiteComboModel> customerList = new Vector<LiteComboModel>();
		List<CustomerVO> customerVOLs = formDSModel.getCustomerVOLs();
		for(CustomerVO customerVO : customerVOLs){
			customerList.add(new LiteComboModel(customerVO.getId(), customerVO.getName()));
		}		
		
		FIELD_TRANS_CUSTOMER = new JComboBox(customerList);
		long customerId = formModel.getCustomerId(); 
		LiteComboModel selectedCustomer = new LiteComboModel(customerId);
		FIELD_TRANS_CUSTOMER.setSelectedItem(selectedCustomer);
		FIELD_TRANS_CUSTOMER.setRenderer(new LiteComboModelRenderer());
		FIELD_TRANS_CUSTOMER.setEditor(new LiteEditableComboBoxEditor(FIELD_TRANS_CUSTOMER, customerList));

		/*if(formModel.getTransactionId() > 0){
			FIELD_TRANS_CUSTOMER.setEditable(false);
		}else{*/
			FIELD_TRANS_CUSTOMER.addItemListener(new OnChangeCustomer());
		//}
		customerTransPanel.add(customerName);
		customerTransPanel.add(FIELD_TRANS_CUSTOMER);
		
		JLabel transactionTypeLbl = new JLabel("Transaction Type", 10);
		
		Vector<LiteComboModel> transTypeList = new Vector<LiteComboModel>();
		
		if(formModel.getTransactionTypeId() == 0){
			TransactionTypeEnum[] transTypes = TransactionTypeEnum.values();
		
			for(int i = 0; i < transTypes.length; i++){
				transTypeList.add(new LiteComboModel(transTypes[i].getId(), transTypes[i].getName()));
			}		
		}else{
			TransactionTypeEnum type = TransactionTypeEnum.getById((int)formModel.getTransactionTypeId());
			transTypeList.add(new LiteComboModel(type.getId(), type.getName()));
		}
		
		FIELD_TRANS_TYPE = new JComboBox(transTypeList);
		FIELD_TRANS_TYPE.setSelectedItem(new LiteComboModel(formModel.getTransactionTypeId()));
		FIELD_TRANS_TYPE.setRenderer(new LiteComboModelRenderer());
		
		/*if(formModel.getTransactionId() > 0){
			FIELD_TRANS_TYPE.setEditable(false);
		}else{*/
			FIELD_TRANS_TYPE.addItemListener(new OnChangePaymentType());
		//}
		
		
		customerTransPanel.add(transactionTypeLbl);
		customerTransPanel.add(FIELD_TRANS_TYPE);
		
		
		JLabel transactionStatusLbl = new JLabel("Transaction Status", 10);
		
		Vector<LiteComboModel> transStatusList = new Vector<LiteComboModel>();
		transStatusList.add(new LiteComboModel(1, "Confirmed"));
		transStatusList.add(new LiteComboModel(2, "Cancelled"));
		
		FIELD_PAY_STATUS = new JComboBox(transStatusList);
		FIELD_PAY_STATUS.setSelectedItem(new LiteComboModel(formModel.getTransactionStatusId()));
		FIELD_PAY_STATUS.setRenderer(new LiteComboModelRenderer());
		
		customerTransPanel.add(transactionStatusLbl);
		customerTransPanel.add(FIELD_PAY_STATUS);
		
	}
	
	private void resetAddressPanel(AddressVO addressVO){
		formModel.setAddressVO(addressVO);
		populateCustomerAddressFields(this.addressPanel);
		//this.addressPanel.getParent().repaint();
		ComponentRegistry.instance().repaintComponents();
	}
	
	
	private void populateCustomerAddressFields(JPanel addressPanel){
		
		if(tempAddressPanel != null){
			addressPanel.remove(tempAddressPanel);
			
		}
		
		tempAddressPanel = new JPanel();
		tempAddressPanel.setPreferredSize(addressPanel.getPreferredSize());
		GridLayout gridLayout = new GridLayout(5, 1, 2, 2);
		tempAddressPanel.setLayout(gridLayout);
		
		AddressVO addressVO = formModel.getAddressVO();
		
		if(addressVO != null){
			JLabel name = new JLabel(addressVO.getFirstName());
			JLabel addLine1 = new JLabel(addressVO.getAddressLine1());
			JLabel addLine2 = new JLabel(addressVO.getAddressLine2());
			
			String locationStr = addressVO.getCity() + ", "+ addressVO.getState();
			JLabel location =  new JLabel(locationStr);
			JLabel country =  new JLabel(addressVO.getCountry());
			
			tempAddressPanel.add(name);
			tempAddressPanel.add(addLine1);
			//addressPanel.add(new JLabel());
			tempAddressPanel.add(addLine2);
			//addressPanel.add(new JLabel());
			tempAddressPanel.add(location);
			//addressPanel.add(new JLabel());
			tempAddressPanel.add(country);
			//addressPanel.add(new JLabel());
		}
		
		addressPanel.add(tempAddressPanel);
	}
	
	public class OnChangeCustomer implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				LiteComboModel selectedModel = (LiteComboModel)event.getItem();
				if(selectedModel != null){
					long customerId = selectedModel.getId();
					CustomerVO customerVO = ServiceClient.getInstance().retrieveCustomer(customerId);
					resetAddressPanel(customerVO.getPrimaryAddress());
					
					pullFreshInvoices = true;
				}
			}
			
		}
	}
	
	public class OnChangePaymentType implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				LiteComboModel selectedModel = (LiteComboModel)event.getItem();
				if(selectedModel != null){
					pullFreshInvoices = true;
				}
			}
			
		}
	}
	
	public class FindPendingInvoiceListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			List<Long> existingInvoiceIds = retrieveExistingInvoiceIds();
			
			long customerId = ((LiteComboModel)FIELD_TRANS_CUSTOMER.getSelectedItem()).getId();
			int transTypeId = (int)((LiteComboModel)FIELD_TRANS_TYPE.getSelectedItem()).getId();
			TransactionTypeEnum transType = TransactionTypeEnum.getById(transTypeId);
			List<InvoiceVO> invoices = Collections.emptyList();
			
			
			if(existingInvoiceIds.isEmpty()){
				invoices = ServiceClient.getInstance().retrievePaymentPendingInvcs(customerId, transType);
			}else{
				invoices = ServiceClient.getInstance().retrievePaymentPendingInvcs(customerId, transType, existingInvoiceIds);
			}
			
			OMTSwitchEnum purchaseSwitch = transType.equals(TransactionTypeEnum.DEBIT) ?
					OMTSwitchEnum.ON : OMTSwitchEnum.OFF;
						
			if(pullFreshInvoices){
				formModel.setInovoicePaymentVOs(null);
			}
			
			formModel.addInvoicePaymentVOs(invoices, purchaseSwitch);
			populateTransPayment();
			if(paymentTransAction != null && paymentTable!= null){
				paymentTransAction.resetUntilizedAmt(paymentTable);
			}
			
			//reset to false
			pullFreshInvoices = false;
		}
		
	}
	
	private List<Long> retrieveExistingInvoiceIds(){
		List<Long> existingInvoices = new ArrayList<Long>();
		
		if(!pullFreshInvoices && paymentTable.getModel() != null){
			int rows = paymentTable.getModel().getRowCount();
			for(int i= 0; i < rows; i++){
				Object[] row = retrieveRow((DefaultTableModel)paymentTable.getModel(), i);
				existingInvoices.add(new Long(row[2].toString()));
			}
		}
		return existingInvoices;
	}
	
	class SaveTransactionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			PaymentTransactionVO paymentTransactionVO = constructPaymentTransactionVO();
			if(transactionValidator.validate(paymentTransactionVO)){
				ServiceClient.getInstance().saveTransaction(paymentTransactionVO);
				resetFormModelValues(paymentTransactionVO);
			}
		}
		
	}
	
	
	private List<InvoicePaymentVO> constructInvoicePaymentVOs(TransactionTypeEnum transactionType){
		List<InvoicePaymentVO> invoicePaymentVOs = new ArrayList<InvoicePaymentVO>();
		
		OMTSwitchEnum purchaseSwitch = transactionType.equals(TransactionTypeEnum.DEBIT) ?
				OMTSwitchEnum.ON : OMTSwitchEnum.OFF;
		
		int rows = paymentTable.getModel().getRowCount();
		for(int i= 0; i < rows; i++){
			Object[] row = retrieveRow((DefaultTableModel)paymentTable.getModel(), i);
			InvoicePaymentVO invoicePaymentVO = constructInvoicePaymentVO(row, purchaseSwitch);
			if(invoicePaymentVO != null){
				invoicePaymentVOs.add(invoicePaymentVO);
			}
		}
		return invoicePaymentVOs;
	}
	
	private InvoicePaymentVO constructInvoicePaymentVO(Object[] row, 
			OMTSwitchEnum purchase){
		double amount = new Double(row[6].toString());
		
		if(amount <= 0){
			return null;
		}
		
		InvoicePaymentVO invoicePaymentVO = new InvoicePaymentVO(purchase);
		invoicePaymentVO.setPaymentAmount(amount);
		
		int transactionStatus = (int)((LiteComboModel)FIELD_PAY_STATUS.getSelectedItem()).getId();
		invoicePaymentVO.setStatus(PaymentStatusEnum.getById(transactionStatus));
		
		invoicePaymentVO.setId(new Long(row[0].toString()));
		InvoiceVO invoiceVO = new InvoiceVO(new Long(row[2].toString()), false);
		
		long orderId = new Long(row[1].toString());
		Date invoiceDate = getStringAsDate(row[3].toString());
		double invoiceAmount = new Double(row[4].toString());
		
		invoiceVO.setOrderId(orderId);
		invoiceVO.setInvoiceDate(invoiceDate);
		invoiceVO.setInvoiceAmount(invoiceAmount);
			
		invoicePaymentVO.setInvoiceVO(invoiceVO);
		
		return invoicePaymentVO;
	}
	
	private Object[] retrieveRow(DefaultTableModel itemModel, int row, int requiredColumn){
		Object[] invoiceRow = new Object[requiredColumn];
		
		for (int i = 0; i < invoiceRow.length; i++) {
			invoiceRow[i] = itemModel.getValueAt(row, i);
		}
		
		return invoiceRow;
	}
	
	private Object[] retrieveRow(DefaultTableModel itemModel, int row){
		int requiredColumn = 7;
		return retrieveRow(itemModel, row, requiredColumn);
	}
	
	private PaymentTransactionVO constructPaymentTransactionVO(){
		PaymentTransactionVO paymentTransactionVO = new PaymentTransactionVO();
		
		long transactionID = FIELD_PAY_TRAN_ID.getText() == null || FIELD_PAY_TRAN_ID.getText().equals("") ? 0 : new Long(FIELD_PAY_TRAN_ID.getText());
		long customerId = ((LiteComboModel)FIELD_TRANS_CUSTOMER.getSelectedItem()).getId();
		int transactionType = (int)((LiteComboModel)FIELD_TRANS_TYPE.getSelectedItem()).getId();
		int transactionStatus = (int)((LiteComboModel)FIELD_PAY_STATUS.getSelectedItem()).getId();
		long bankId = ((LiteComboModel)FIELD_PAY_BANK.getSelectedItem()).getId();
		long payModeId = ((LiteComboModel)FIELD_PAY_MODE.getSelectedItem()).getId();
		String payRefText = FIELD_PAY_MODE_REF_TXT.getText();
		double tranAmount = FIELD_PAY_TRAN_AMT.getText() == null ? 0 : new Double(FIELD_PAY_TRAN_AMT.getText());
		Date transDate = FIELD_PAY_TRAN_DATE.getDate();
		String desc = FIELD_PAY_TRAN_DESC.getText();
		double unutilizedAmount = FIELD_UNUTILIZED_AMT.getText() == null ? 0 : new Double(FIELD_UNUTILIZED_AMT.getText());
		
		paymentTransactionVO.setId(transactionID);
		paymentTransactionVO.setCustomerVO(new CustomerVO(customerId));
		paymentTransactionVO.setTransType(TransactionTypeEnum.getById(transactionType));
		paymentTransactionVO.setTransactionStaus(PaymentStatusEnum.getById(transactionStatus));
		paymentTransactionVO.setBankVO(new BankVO(bankId));
		paymentTransactionVO.setPaymentModeVO(new PaymentModeVO(payModeId));
		paymentTransactionVO.setPaymentRefText(payRefText);
		paymentTransactionVO.setTransactionAmt(tranAmount);
		paymentTransactionVO.setUnutilizedAmount(unutilizedAmount);
		paymentTransactionVO.setTransactionDate(transDate);
		paymentTransactionVO.setTransactionDesc(desc);
		paymentTransactionVO.setCreationDate(new Date());
		paymentTransactionVO.setLastModifiedDate(new Date());
		
		List<InvoicePaymentVO> invoicePaymentVOs = constructInvoicePaymentVOs(paymentTransactionVO.getTransType());
		paymentTransactionVO.setInovicePaymentVOs(invoicePaymentVOs);
		
		return paymentTransactionVO;
		
	}
	
	private Date getStringAsDate(String dateString){
		Date date = null;
		if(dateString != null){
			try {
				date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	private void resetFormModelValues(PaymentTransactionVO paymentTransactionVO){
		formModel.setTransactionId(paymentTransactionVO.getId());
		FIELD_PAY_TRAN_ID.setText(String.valueOf(paymentTransactionVO.getId()));
		
		List<InvoicePaymentVO> invoicePaymentVOs = paymentTransactionVO.getInovicePaymentVOs();
		formModel.setInovoicePaymentVOs(invoicePaymentVOs);
		formModel.setUnutilizedAmount(paymentTransactionVO.getUnutilizedAmount());
		populateTransPayment();
		
	
	}
}
