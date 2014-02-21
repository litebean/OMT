package com.litetech.omt.ui.comp;

import static com.litetech.omt.ui.model.InvoiceTaxTableModel.taxHeader;
import static com.litetech.omt.ui.model.OrderItemModel.header;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.ChargeOperationEnum;
import com.litetech.omt.constant.InvoiceStatusEnum;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.ui.comp.action.LineItemTableAction;
import com.litetech.omt.ui.comp.action.TaxTableAction;
import com.litetech.omt.ui.comp.editor.LiteButtonCellEditor;
import com.litetech.omt.ui.comp.editor.LiteComboModelCellEditor;
import com.litetech.omt.ui.comp.editor.LiteEditableComboBoxEditor;
import com.litetech.omt.ui.comp.editor.LiteEditableComboCellEditor;
import com.litetech.omt.ui.comp.listener.CloseTabListener;
import com.litetech.omt.ui.comp.listener.TableCellListener;
import com.litetech.omt.ui.comp.renderer.CheckBoxRenderer;
import com.litetech.omt.ui.comp.renderer.InvoiceTaxCellRederer;
import com.litetech.omt.ui.comp.renderer.LiteComboModelCellRenderer;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.comp.validator.InvoiceValidator;
import com.litetech.omt.ui.model.InvoiceFormModel;
import com.litetech.omt.ui.model.InvoiceTaxTableModel;
import com.litetech.omt.ui.model.LiteComboBoxModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.OrderItemModel;
import com.litetech.omt.ui.model.PaymentTransFormModel;
import com.litetech.omt.ui.model.ds.InvoiceFormDSModel;
import com.litetech.omt.ui.model.ds.PaymentTransFormDSModel;
import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoiceChargeVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.UnitVO;

public class InvoiceForm extends JPanel  implements PanelTabComp{

	
	private JPanel rootPanel;
	private InvoiceFormModel formModel;
	private InvoiceFormDSModel formDSModel; 
	private LiteComboModelCellEditor unitCellEditor;	
	
	
	private JTextField FIELD_INV_ID;
	private OMTCalendar FIELD_INV_DATE;
	private JComboBox FIELD_INV_CUSTOMER;
	private JComboBox FIELD_INV_STATUS;
	private JTextField FIELD_INV_DC_NO;
	private OMTCalendar FIELD_INV_DC_DATE;
	
	private JTextField FIELD_ORD_ID;
	private OMTCalendar FIELD_ORD_DATE;
	private JTextField FIELD_PARTY_ORD_ID;
	private OMTCalendar FIELD_PARTY_ORD_DATE;
	private JTextArea FIELD_DESC;
	
	private JTable lineItemTable;
	private LineItemTableAction lineItemTableAction;
	private JTable taxTable;
	private boolean purchase;
	private InvoiceValidator invoiceValidator;
	JPanel myPanelTab;
	
	public InvoiceForm(InvoiceFormModel formModel, InvoiceFormDSModel formDSModel){
		this.formModel = formModel;
		this.formDSModel = formDSModel;
		this.purchase = formModel.isPurchase();
		this.setBackground(Color.WHITE);
		
		rootPanel = new JPanel();
		
		rootPanel.setBackground(Color.WHITE);
		rootPanel.setPreferredSize(AppContext.getInstance().getBaseFormPanelDimension());
		
		invoiceValidator = new InvoiceValidator();
		rootPanel.add(constructInvoiceMetaDataPanel());
		rootPanel.add(constructOrderItemPanel());
		rootPanel.add(constructPaymentHistoryPanel());
		//rootPanel.add(constructInvoiceControlPanel());
		add(rootPanel);
	}
	
	
	@Override
	public void setMyPanelTab(JPanel myPanelTab) {
		this.myPanelTab = myPanelTab;
	}

	@Override
	public JPanel getMyPanelTab() {
		return myPanelTab;
	}
	
	public JPanel constructInvoiceControlPanel(){
		JPanel invoiceControlPanel = new JPanel();
		//invoiceControlPanel.setBackground(Color.GREEN);
		invoiceControlPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 60));
		
		populateInvoiceControlFields(invoiceControlPanel);
		return invoiceControlPanel;
	}
	
	
	private void populateInvoiceControlFields(JPanel invoiceControlPanel){
		Box controlBox = Box.createHorizontalBox();
		controlBox.add(Box.createHorizontalStrut(50));
			
		
		JButton saveBt = new JButton("Save");
		saveBt.addActionListener(new SaveInvoiceListener());
		controlBox.add(saveBt);
		controlBox.add(Box.createHorizontalStrut(20));
		
		JButton makePaymentBt = new JButton("Make Payment");
		makePaymentBt.addActionListener(new MakePaymentListener());
		controlBox.add(makePaymentBt);
		controlBox.add(Box.createHorizontalStrut(20));
		
		JButton printInvBt = new JButton("Print Invoice");
		printInvBt.addActionListener(new PrintInvoiceListener());
		controlBox.add(printInvBt);
		controlBox.add(Box.createHorizontalStrut(20));
		
		JButton detachOrderBt = new JButton("Detach From Order");
		detachOrderBt.addActionListener(new DetachOrderListerner());
		controlBox.add(detachOrderBt);
		
		invoiceControlPanel.add(controlBox);
	}
	
	public JPanel constructInvoiceMetaDataPanel(){
		JPanel invoiceMetaData = new JPanel();
		invoiceMetaData.setBackground(Color.WHITE);
		invoiceMetaData.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 110));
		
		populateInvoiceMetaDataFields(invoiceMetaData);
		return invoiceMetaData;
	}
	
	public void populateInvoiceMetaDataFields(JPanel invoiceMetaData){
		
		JPanel invoicePanel = new JPanel(); 
		int midArea = (AppContext.getInstance().getWorkAreaWidth() - 20) /  2;
		invoicePanel.setPreferredSize(new Dimension(midArea, 105));
		//invoicePanel.setBackground(Color.WHITE);
		populateInvoiceFields(invoicePanel);
		
		JPanel orderPanel = new JPanel();
		//orderPanel.setBackground(Color.WHITE);
		orderPanel.setPreferredSize(new Dimension(((AppContext.getInstance().getWorkAreaWidth() - 20) - midArea), 105));
		populateOrderFields(orderPanel);
		
		
		invoiceMetaData.add(invoicePanel);
		invoiceMetaData.add(orderPanel);
		
	}
	
	
	public void populateInvoiceFields(JPanel invoiceMetaData){
		GridLayout gridLayout = new GridLayout(4, 4, 4, 6);
		invoiceMetaData.setLayout(gridLayout);
		
		
		JLabel invoiceId = new JLabel("Invoice ID", 10);
		String invoiceIdStr = formModel.getInvoiceId() > 0 ? String.valueOf(formModel.getInvoiceId()) : "";
		FIELD_INV_ID = new JTextField(invoiceIdStr, 10);
		FIELD_INV_ID.setEditable(false);
		
		invoiceMetaData.add(invoiceId);
		invoiceMetaData.add(FIELD_INV_ID);
		
		JLabel invoiceDate = new JLabel("Invoice Date",10);
		FIELD_INV_DATE = new OMTCalendar(formModel.getInvoiceDate());
		
		invoiceMetaData.add(invoiceDate);
		invoiceMetaData.add(FIELD_INV_DATE);
		
		JLabel customerName = new JLabel("Customer", 10);
		
		
		Vector<LiteComboModel> customerList = new Vector<LiteComboModel>();
		List<CustomerVO> customerVOLs = formDSModel.getCustomers();
		for(CustomerVO customerVO : customerVOLs){
			customerList.add(new LiteComboModel(customerVO.getId(), customerVO.getName()));
		}		
		
		FIELD_INV_CUSTOMER = new JComboBox(customerList);
		LiteComboModel selectedCustomer = new LiteComboModel(formModel.getCustomerId());
		FIELD_INV_CUSTOMER.setSelectedItem(selectedCustomer);
		FIELD_INV_CUSTOMER.setRenderer(new LiteComboModelRenderer());
		FIELD_INV_CUSTOMER.setEditor(new LiteEditableComboBoxEditor(FIELD_INV_CUSTOMER, customerList));
		FIELD_INV_CUSTOMER.addItemListener(new OnchangeCustomerDropDown());
		
		//FIELD_INV_CUSTOMER.setPreferredSize(new Dimension(150, 23));

		invoiceMetaData.add(customerName);
		invoiceMetaData.add(FIELD_INV_CUSTOMER);
				
				
		JLabel invoiceStatus = new JLabel("Status", 10);
		
		Vector<LiteComboModel> invoiceStatusList = new Vector<LiteComboModel>();
		InvoiceStatusEnum[] invoiceStatuses = InvoiceStatusEnum.values();
		for(InvoiceStatusEnum invoiceStatusEnum : invoiceStatuses){
			invoiceStatusList.add(new LiteComboModel(invoiceStatusEnum.getId(), invoiceStatusEnum.getName()));
		}
		FIELD_INV_STATUS = new JComboBox(invoiceStatusList);
		
		LiteComboModel selectedStatus = new LiteComboModel(formModel.getStatus());
		FIELD_INV_STATUS.setSelectedItem(selectedStatus);
		FIELD_INV_STATUS.setRenderer(new LiteComboModelRenderer());
		//FIELD_INV_STATUS.setPreferredSize(new Dimension(125, 23));
		
		invoiceMetaData.add(invoiceStatus);
		invoiceMetaData.add(FIELD_INV_STATUS);
		
		
		//seconddRow.add(Box.createHorizontalStrut(interSpace));
		/*		
		seconddRow.add(customerName);
		seconddRow.add(Box.createHorizontalStrut(10));
		seconddRow.add(customerNameField);
		seconddRow.add(Box.createHorizontalStrut(interSpace));
		*/
			
		
		
		//third row
		JLabel dcNoLabel = new JLabel("DC No",10);
		FIELD_INV_DC_NO = new JTextField(formModel.getDcNo(), 10);
		
		invoiceMetaData.add(dcNoLabel);
		invoiceMetaData.add(FIELD_INV_DC_NO);
				
		JLabel dcDateLabel = new JLabel("DC Date",10);
		//String dcDateStr = formModel.getDateAsString(formModel.getDcDate());
		FIELD_INV_DC_DATE = new OMTCalendar(formModel.getDcDate());
		
		invoiceMetaData.add(dcDateLabel);
		invoiceMetaData.add(FIELD_INV_DC_DATE);
		
		invoiceMetaData.add(Box.createHorizontalStrut(10));
		invoiceMetaData.add(Box.createHorizontalStrut(10));
		invoiceMetaData.add(Box.createHorizontalStrut(10));
		invoiceMetaData.add(Box.createHorizontalStrut(10));
	}
	
	
	public void populateOrderFields(JPanel invoiceRightPanel){
		
		JPanel invoiceMetaData = new JPanel();
		GridLayout gridLayout = new GridLayout(2, 4, 4, 6);
		invoiceMetaData.setLayout(gridLayout);
		
		JLabel orderId = new JLabel("Order ID",10);
		String selectedOrderId = formModel.getOrderId() != 0 ? String.valueOf(formModel.getOrderId()) : "";
		FIELD_ORD_ID = new JTextField(selectedOrderId, 10);
		FIELD_ORD_ID.setEditable(false);
		invoiceMetaData.add(orderId);
		invoiceMetaData.add(FIELD_ORD_ID);

		JLabel orderDate = new JLabel("Order Date",10);
		FIELD_ORD_DATE = new OMTCalendar(formModel.getOrderDate());
		invoiceMetaData.add(orderDate);
		invoiceMetaData.add(FIELD_ORD_DATE);
			
		JLabel partyOrdIDLabel = new JLabel("Party Order ID", 10);
		FIELD_PARTY_ORD_ID = new JTextField(formModel.getPartyOrderId(), 10);
		invoiceMetaData.add(partyOrdIDLabel);
		invoiceMetaData.add(FIELD_PARTY_ORD_ID);
		
		
		JLabel partyOrdDateLabel = new JLabel("Party Order Date", 10);
		FIELD_PARTY_ORD_DATE = new OMTCalendar(formModel.getPartyOrderDate());
		invoiceMetaData.add(partyOrdDateLabel);
		invoiceMetaData.add(FIELD_PARTY_ORD_DATE);
		
		invoiceRightPanel.add(invoiceMetaData);
		
		JPanel descriptionPanel = new JPanel();
		
		JLabel descriptionLabel = new JLabel("Description:");
		descriptionPanel.add(descriptionLabel);
				
		FIELD_DESC = new JTextArea(formModel.getDescription(), 2, 30); 
		//textArea.setPreferredSize(new Dimension(5, 5));
		JScrollPane scrollPane = new JScrollPane(FIELD_DESC);
		FIELD_DESC.setLineWrap(true);
		descriptionPanel.add(scrollPane);
		
		invoiceRightPanel.add(descriptionPanel);
				
	}
	
	
	private JPanel constuctTableControlPanel(){
		JPanel orderItemControlPanel = new JPanel();
		//orderItemControlPanel.setBackground(Color.BLUE);
		//orderItemControlPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 50));
		
		
		Box controlBox = Box.createHorizontalBox();
		JButton deleteBt = new JButton("Remove");
		deleteBt.addActionListener(new RemoveLineItemListener());
		
		controlBox.add(deleteBt);
		controlBox.add(Box.createHorizontalStrut(10));
		
		JTextField noOfItems = new JTextField("1", 3);
		controlBox.add(noOfItems);
		
		JButton addRowBt = new JButton("Add Rows");
		addRowBt.addActionListener(new AddLineItemListener(noOfItems));
		controlBox.add(addRowBt);
		controlBox.add(Box.createHorizontalStrut(100));
					
		
		
		orderItemControlPanel.add(controlBox);
		return orderItemControlPanel;
		
	}
	
	public JPanel constructPaymentHistoryPanel(){
		JPanel paymentHistoryPanel = new JPanel();
		paymentHistoryPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 200));
		//paymentHistoryPanel.setBackground(Color.green);
		JLabel label = new JLabel("Payment History");
		//paymentHistoryPanel.add(label);
		
		populatePaymentHisyory(paymentHistoryPanel);
		
		return paymentHistoryPanel;
	}
	
	private void populatePaymentHisyory(JPanel paymentHistoryPanel){
	
		final Object[] paymentHeader = {"Transaction Id", "Payment Id", "Status", "Payment Recorded Date", "Amount Paid"};
		//final Object[][] rows = {{"1001", "12001", 2000, "Confirmed", "13-10-2013"}, {"1002", "12002", 5000, "Cancelled", "18-10-2013"}};
		final Object[][] rows = formModel.getPaymentRows();
		DefaultTableModel defaultTableModel = new DefaultTableModel(rows, paymentHeader){
			 @Override
           public boolean isCellEditable ( int row, int column ){
				 return false;
           }
		};
		
		JTable paymentTable = new JTable(defaultTableModel); 
		paymentTable.setRowHeight(20);
		paymentTable.setBorder(LineBorder.createBlackLineBorder());
		paymentTable.setRowSelectionAllowed(true);
		paymentTable.setPreferredScrollableViewportSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 70));
		paymentTable.setFillsViewportHeight(true);
		
		paymentTable.getColumn(paymentHeader[0]).setPreferredWidth(100);
		paymentTable.getColumn(paymentHeader[1]).setPreferredWidth(100);
		paymentTable.getColumn(paymentHeader[2]).setPreferredWidth(100);
		paymentTable.getColumn(paymentHeader[3]).setPreferredWidth(100);
		paymentTable.getColumn(paymentHeader[4]).setPreferredWidth(100);
		
		paymentTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		paymentTable.setDragEnabled(false);
		
		paymentTable.addMouseListener(new MouseAdapter() {
	        	
    	 @Override
    	  public void mouseClicked(MouseEvent e) {
    	    JTable target = (JTable)e.getSource();
    	    int row = target.getSelectedRow();
    	    int column = target.getSelectedColumn();
    	    
    	   // if(column == 0){
    	    	Object object = e.getSource();
    	    	Long transactionId = new Long(rows[row][0].toString());
    	    	System.out.println("Transaction Id "+transactionId);
    	    	//String actionCommand = button.getActionCommand();
    	    	populateDisplayTab(transactionId);
    	   // }
    	    // do some action if appropriate column
    	    
    	  }
    	});
		
		 
        JScrollPane scrollPane = new JScrollPane(paymentTable);
        paymentHistoryPanel.add(scrollPane);
        
        JPanel amountPaidPanel = new JPanel();
        amountPaidPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        amountPaidPanel.setPreferredSize(new Dimension((AppContext.getInstance().getWorkAreaWidth()-20), 30));
        
        JLabel paidAmtLabel = new JLabel("Total Amount Paid: ");
        JTextField FIELD_TOTAL_PAID_AMT = new JTextField(String.valueOf(formModel.getAmountPaid()), 20);
        FIELD_TOTAL_PAID_AMT.setEditable(false);
        
        amountPaidPanel.add(paidAmtLabel);
        amountPaidPanel.add(FIELD_TOTAL_PAID_AMT);
        paymentHistoryPanel.add(amountPaidPanel);
		
	}
	
	
	public void populateDisplayTab(Long transactionId){
		String command = String.valueOf(transactionId);
		JPanel displayPanel = ComponentRegistry.instance().getDisplayPanel();
		
		OMTTabbedPane tabbedPane = (OMTTabbedPane)displayPanel.getComponent(0);
		Component[] tabComps = tabbedPane.getComponents();
			
		boolean tabAlreadyOpened = false;
		if(tabComps != null){
			for (int i = 0; i < tabComps.length; i++) {
				if(command.equals(tabComps[i].getName())){
					tabAlreadyOpened = true;
					tabbedPane.resetSelectedComponent(tabComps[i]);
					break;
				}
			}
		}
			
		if(!tabAlreadyOpened){
			Icon compIcon = MainMenu.PAYMENT_ICON;
			
			long customerId = ((LiteComboModel)FIELD_INV_CUSTOMER.getSelectedItem()).getId();
			CustomerVO customerVO = ServiceClient.getInstance().retrieveCustomer(customerId);
			
			JComponent comp = new PaymentTransactionForm(constructPaymentTransDSModel(customerVO), 
					constructPaymentTransModel(transactionId, customerVO));
			comp.setName(command);
			//comp.setOpaque(true);
			//comp.setBackground(Color.WHITE);
			
			String title = purchase ? "Purchase Payment: "+command : "Payment: "+command;
			Util.buildCloseTab(command, title, tabbedPane, comp, compIcon,
					new CloseTabListener(displayPanel, comp));
			//tabbedPane.setSelectedComponent(newContainer);
		}
		
		ComponentRegistry.instance().repaintComponents();
	}
	
	
	private PaymentTransFormDSModel constructPaymentTransDSModel(CustomerVO customerVO){
		PaymentTransFormDSModel formDSModel = new PaymentTransFormDSModel();
		
		long customerId = ((LiteComboModel)FIELD_INV_CUSTOMER.getSelectedItem()).getId();
		
		List<CustomerVO> customerLs = new ArrayList<CustomerVO>();
		customerLs.add(customerVO);
		
		formDSModel.setCustomerVOLs(customerLs);
				
		
		List<BankVO> bankVOs = ServiceClient.getInstance().retrieveActiveBanks();
		formDSModel.setBankVOs(bankVOs);
		
		List<PaymentModeVO> paymentModeVOs = ServiceClient.getInstance().retrieveActivePaymentModes();
		formDSModel.setPaymentModeVOs(paymentModeVOs);
		
		
		
		return formDSModel;
	}
	
	
	private PaymentTransFormModel constructInvPaymentTransModel(final long invoiceId, CustomerVO customerVO){
		PaymentTransFormModel formModel = new PaymentTransFormModel();
		TransactionTypeEnum transType = purchase ? TransactionTypeEnum.DEBIT : TransactionTypeEnum.CREDIT;
		OMTSwitchEnum purchaseSwitch = purchase ? OMTSwitchEnum.ON : OMTSwitchEnum.OFF;
		
		InvoiceVO invoiceVO = ServiceClient.getInstance().retrieveInvoice(invoiceId, purchase);
		formModel.addInvoicePaymentVO(invoiceVO, purchaseSwitch);
		
		
		formModel.setTransactionTypeId(transType.getId());
		formModel.setCustomerId(customerVO.getId());
		
		
		formModel.setAddressVO(customerVO.getPrimaryAddress());
		
		return formModel;
	}
	
	private PaymentTransFormModel constructPaymentTransModel(final long transactionId, CustomerVO customerVO){
		PaymentTransFormModel formModel = new PaymentTransFormModel();
		PaymentTransactionVO paymentTransactionVO = ServiceClient.getInstance().retrieveTransaction(transactionId);
		formModel.setCustomerId(paymentTransactionVO.getCustomerVO().getId());
		formModel.setBankId(paymentTransactionVO.getBankVO().getId());
		formModel.setInovoicePaymentVOs(paymentTransactionVO.getInovicePaymentVOs());
		formModel.setPaymentModeId(paymentTransactionVO.getPaymentModeVO().getId());
		formModel.setTransactionId(paymentTransactionVO.getId());
		formModel.setTransactionTypeId(paymentTransactionVO.getTransType().getId());
		formModel.setTransactionAmount(paymentTransactionVO.getTransactionAmt());
		formModel.setUnutilizedAmount(paymentTransactionVO.getUnutilizedAmount());
		formModel.setPayRefText(paymentTransactionVO.getPaymentRefText());
		formModel.setTransactionDate(paymentTransactionVO.getTransactionDate());
		formModel.setDesc(paymentTransactionVO.getTransactionDesc());
		formModel.setTransactionStatusId(paymentTransactionVO.getTransactionStaus().getId());
		
		
		formModel.setAddressVO(customerVO.getPrimaryAddress());
		
		return formModel;
	}
	
	

	public JPanel constructOrderItemPanel(){
		JPanel orderItemPanel = new JPanel();
		//orderItemPanel.setBackground(Color.RED);
		orderItemPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 285));
		populateOrderItems(orderItemPanel);
		populateLineItemBottomLayer(orderItemPanel);
		orderItemPanel.add(constructInvoiceControlPanel());
		
		//orderItemPanel.add(constuctTableControlPanel());
		return orderItemPanel;
	}
	
	private void populateOrderItems(JPanel orderItemPanel){
		/*final Object[][] rows = {{101, Boolean.FALSE, new LiteComboModel(1, "Tiles1"), "No", "5", "10.0", "5"}, {102, Boolean.FALSE, new LiteComboModel(2, "Tiles2"), "No", "10", "10.0", "2"},
				{103, Boolean.FALSE, new LiteComboModel(3, "Tiles3"), "No", "5", "10.0", "2"}
				  };*/
		final Vector<LiteComboModel> productLs = constructProductList();
		
		JComboBox companyNameLs = new JComboBox(productLs);
		companyNameLs.setRenderer(new LiteComboModelRenderer());
		
		
		if(formDSModel.getUnitModelMap() == null){
			unitCellEditor = new LiteComboModelCellEditor();
		}else{
			unitCellEditor = new LiteComboModelCellEditor(formDSModel.getUnitModelMap());
		}
		
		
		
		//unitBoxLs.set
		final Object[][] rows = formModel.getLineItemRows();
		
		lineItemTable = new JTable(new OrderItemModel(rows)); 
		lineItemTable.setRowHeight(20);
		lineItemTable.setBorder(LineBorder.createBlackLineBorder());
		lineItemTable.setRowSelectionAllowed(false);
		lineItemTable.setPreferredScrollableViewportSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 100));
		lineItemTable.setFillsViewportHeight(true);
		lineItemTable.getColumnModel().getColumn(1).setHeaderRenderer(new CheckBoxRenderer(lineItemTable.getTableHeader()));
        //table.getColumn(header[0]).setCellRenderer(new CheckBoxRenderer());
       // table.getColumn(header[0]).setPreferredWidth(100);
        
		lineItemTable.removeColumn(lineItemTable.getColumn(header[0]));
		lineItemTable.removeColumn(lineItemTable.getColumn(header[8]));
		
		lineItemTable.getColumn(header[2]).setPreferredWidth(200);
		//lineItemTable.getColumn(header[2]).setCellEditor(new DefaultCellEditor(companyNameLs));
		lineItemTable.getColumn(header[2]).setCellEditor(new LiteEditableComboCellEditor(productLs));
		lineItemTable.getColumn(header[2]).setCellRenderer(new LiteComboModelCellRenderer());
        
		lineItemTable.getColumn(header[3]).setPreferredWidth(200);
		lineItemTable.getColumn(header[4]).setPreferredWidth(100);
		lineItemTable.getColumn(header[4]).setCellEditor(unitCellEditor);
		lineItemTable.getColumn(header[4]).setCellRenderer(new LiteComboModelCellRenderer());
		lineItemTable.getColumn(header[5]).setPreferredWidth(100);
		lineItemTable.getColumn(header[6]).setPreferredWidth(100);
		lineItemTable.getColumn(header[7]).setPreferredWidth(100);
        
		boolean warningRequired = purchase ? false : true;
		List<LineItemVO> lineItemVOs = (List<LineItemVO>)(isNewInvoice() ? Collections.emptyList() : formModel.getLineItemLs()); 
		lineItemTableAction = new LineItemTableAction(lineItemVOs, warningRequired);
		
		
		TableCellListener cellListener = new TableCellListener(lineItemTable, lineItemTableAction);
		
        JScrollPane scrollPane = new JScrollPane(lineItemTable);
        orderItemPanel.add(scrollPane);
        
        //JTable footerTable = new JTable(new OrderItemModel());
        //footerTable.addR
        
	}
	
	private boolean isNewInvoice(){
		boolean newInv = false;
		long invoiceId = isNullOrEmpty(FIELD_INV_ID.getText()) ? 0 : Long.valueOf(FIELD_INV_ID.getText());
		if(invoiceId == 0){
			newInv = true;
		}
		
		return newInv;
	}
	
	public void populateLineItemBottomLayer(JPanel lineItemPanel){
		
		JPanel lineItemControlPanel = new JPanel(); 
		int midArea = ((AppContext.getInstance().getWorkAreaWidth() - 20) /  2) + 150;
		lineItemControlPanel.setPreferredSize(new Dimension(midArea, 105));
		lineItemControlPanel.add(constuctTableControlPanel());
		
		JPanel lineItemTaxPanel = new JPanel();
		//orderPanel.setBackground(Color.WHITE);
		int width = (AppContext.getInstance().getWorkAreaWidth() - 20) - midArea;
		int height = 110;
		lineItemTaxPanel.setPreferredSize(new Dimension((width), height));
		lineItemTaxPanel.add(constructTaxPanel(width, height));
		lineItemTaxPanel.setBackground(Color.GREEN);
		//lineItemTaxPanel.setBorder(LineBorder.createGrayLineBorder());
		lineItemPanel.add(lineItemControlPanel);
		lineItemPanel.add(lineItemTaxPanel);
	}
	

	
	private JPanel constructTaxPanel(int width, final int height){
		JPanel taxPanel = new JPanel();
		
		List<ChargeVO> chargeVOs = formDSModel.getChargeVOs();
		Object[][] rows = formModel.getInvoiceChargeRows();
			
		InvoiceTaxTableModel invoiceTaxTableModel = new InvoiceTaxTableModel(rows);
		taxTable  = new JTable(invoiceTaxTableModel);
		taxTable.setTableHeader(null);
				
		final Vector<LiteComboModel> taxLs = new Vector<LiteComboModel>();
		
		for(ChargeVO chargeVO : chargeVOs){
			taxLs.add(new LiteComboModel(chargeVO.getId(), chargeVO.getName(), chargeVO));
		}	
		
		
		JComboBox taxNameLs = new JComboBox(taxLs);
		//taxTable.setRowHeight(20);
		
		if(rows.length > 6){
			width = width - 13;
		}
		taxTable.setPreferredScrollableViewportSize(new Dimension(width, height - 8));
		taxTable.getColumn(taxHeader[0]).setPreferredWidth(75);
		taxTable.getColumn(taxHeader[0]).setCellEditor(new DefaultCellEditor(taxNameLs));
		taxTable.getColumn(taxHeader[0]).setCellRenderer(new LiteComboModelCellRenderer());
		
		taxTable.getColumn(taxHeader[1]).setPreferredWidth(20);
		
		taxTable.getColumn(taxHeader[2]).setMinWidth(15);
		taxTable.getColumn(taxHeader[2]).setMaxWidth(15);
		taxTable.getColumn(taxHeader[2]).setPreferredWidth(15);
		taxTable.getColumn(taxHeader[2]).setCellRenderer(new InvoiceTaxCellRederer());
		taxTable.getColumn(taxHeader[2]).setCellEditor(new LiteButtonCellEditor(new JCheckBox()));
		
		//do not display id
		taxTable.removeColumn(taxTable.getColumn(taxHeader[3]));
		
		TaxTableAction taxTableAction = new TaxTableAction();
		TableCellListener cellListener = new TableCellListener(taxTable, taxTableAction);
		
		lineItemTableAction.registerTaxComponent(invoiceTaxTableModel, taxTableAction);
		JScrollPane scrollPane = new JScrollPane(taxTable);
		taxPanel.add(scrollPane);
				 
		return taxPanel;
	}
	
	private Vector<LiteComboModel> constructProductList(){
		Vector<LiteComboModel> productLs = new Vector<LiteComboModel>();
		List<ProductVO> productVOs = formDSModel.getProductVOs();
		for(ProductVO productVO : productVOs){
			productLs.add(new LiteComboModel(productVO.getProductId(), productVO.getProductName(), productVO));
		}
		return productLs;
	}
	
	private void removeRow(){
		int rows = lineItemTable.getModel().getRowCount();
		List<Integer> rowsRemoved = new ArrayList<Integer>();
		
		for(int i=rows; i > 0; i--){
			Boolean valueObject = (Boolean)lineItemTable.getModel().getValueAt(i-1, 1);
			if(Boolean.TRUE.equals(valueObject)){
				((DefaultTableModel)lineItemTable.getModel()).removeRow(i-1);
				rowsRemoved.add(i-1);
			}
		}
		unitCellEditor.removeAndRenIndexMap(rowsRemoved);
		lineItemTableAction.resetTotalCell((DefaultTableModel)lineItemTable.getModel());
		lineItemTable.repaint();
	}
	
	
	private void addRow(int totalRow){
		for(int i=0; i < totalRow; i++){
			Object[] row = new Object[]{0l, Boolean.TRUE, new LiteComboModel(0, "Select A product"), " ",new LiteComboModel(0, "Select Unit"), 0d, 0d, 0d, 0d};
			((DefaultTableModel)lineItemTable.getModel()).addRow(row);
		}
	}
	
	
	
	public class RemoveLineItemListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			removeRow();
		//	((JTable)arg0.getSource()).repaint();
		}
	}

	public class AddLineItemListener implements ActionListener{

		private JTextField noOfItems;
		public AddLineItemListener(JTextField noOfItems){
			this.noOfItems = noOfItems;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			addRow(new Integer(noOfItems.getText()));
		}
	}
	
	
	private void resetInvoiceFields(List<CustomerVO> customerVOs, List<ProductVO> productVOs){
		Date currentDate = new Date();
		formDSModel.setCustomers(customerVOs);
		formDSModel.setProductVOs(productVOs);
		
		formModel.setOrderId(0l);
		formModel.setOrderDate(currentDate);
		formModel.setInvoiceDate(currentDate);
		formModel.setDcNo("");
		formModel.setDcDate(null);
		formModel.setDescription("");
		formModel.setPartyOrderId("");
		formModel.setPartyOrderDate(null);
		formModel.setStatus(InvoiceStatusEnum.PENDING.getId());
		
		formModel.setInvoiceChargeVOs(new ArrayList<InvoiceChargeVO>());
		formModel.setLineItemLs(new ArrayList<LineItemVO>());
		formModel.setInvoicePaymentVOs(new ArrayList<InvoicePaymentVO>());
		
		rootPanel.removeAll();
		rootPanel.add(constructInvoiceMetaDataPanel());
		rootPanel.add(constructOrderItemPanel());
		rootPanel.add(constructPaymentHistoryPanel());
		
		ComponentRegistry.instance().repaintComponents();
	}
	
	public class DetachOrderListerner implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//are you sure do you want to untag from order?
			List<CustomerVO> customerVOs = ServiceClient.getInstance().retrieveAllCustomers();
			List<ProductVO> productVOs = ServiceClient.getInstance().retrieveAllProducts();
			resetInvoiceFields(customerVOs, productVOs);
		}
	}
	
	public class PrintInvoiceListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			InvoiceVO invoiceVO = constructInvoiceMetaData();
			boolean validationSuccess = true;
			
			boolean newOrder = invoiceVO.getOrderId() == 0 ? true : false;
			if(invoiceVO.getId() > 0 && newOrder){
				//detach invoice from old order
				ServiceClient.getInstance().detachInvoiceFromOrder(invoiceVO.getId(), invoiceVO.isPurchase());
			}
			
			OrderVO orderVO = constructOrderMetaData(invoiceVO);
			if(newOrder){
				validationSuccess = invoiceValidator.validate(orderVO);
				if(validationSuccess){
					ServiceClient.getInstance().createOrder(orderVO);
					invoiceVO.setOrderId(orderVO.getId());
				}
			}			
			if(validationSuccess){
				validationSuccess = invoiceValidator.validate(invoiceVO);
				if(validationSuccess){
					ServiceClient.getInstance().saveAndPrintInvoice(orderVO, invoiceVO);
					populateFormFields(invoiceVO);
				}
			}
		}
	}
	public class SaveInvoiceListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			InvoiceVO invoiceVO = constructInvoiceMetaData();
			boolean validationSuccess = true;
						
			boolean newOrder = invoiceVO.getOrderId() == 0 ? true : false;
			if(invoiceVO.getId() > 0 && newOrder){
				//detach invoice from old order
				ServiceClient.getInstance().detachInvoiceFromOrder(invoiceVO.getId(), invoiceVO.isPurchase());
			}
			
			if(newOrder){
				OrderVO orderVO = constructOrderMetaData(invoiceVO);
				validationSuccess = invoiceValidator.validate(orderVO);
				if(validationSuccess){
					ServiceClient.getInstance().createOrder(orderVO);
					invoiceVO.setOrderId(orderVO.getId());
				}
			}			
			
			if(validationSuccess){
				validationSuccess = invoiceValidator.validate(invoiceVO);
				if(validationSuccess){
					ServiceClient.getInstance().createInvoice(invoiceVO);
					JOptionPane.showMessageDialog(null, "Invoice Saved "+invoiceVO.getId());
					populateFormFields(invoiceVO);
				}
			}			
		}		
	}
	
	public class OnchangeCustomerDropDown implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
			if(FIELD_INV_CUSTOMER.getSelectedItem() != null){
				long customerId = ((LiteComboModel)FIELD_INV_CUSTOMER.getSelectedItem()).getId();
				//CustomerVO customerVO = ServiceClient.getInstance().retrieveCustomer(customerId);
				/*
				FIELD_INV_CUSTOMER_TIN.setText(customerVO.getTinNo());
				FIELD_VENDOR_CODE.setText(customerVO.getVendorCode());
				
				FIELD_VENDOR_CODE.getParent().getParent().repaint();
				*/
			}
			}
		}
	}
	
	public class MakePaymentListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			long invoiceId = isNullOrEmpty(FIELD_INV_ID.getText()) ? 0 : Long.valueOf(FIELD_INV_ID.getText());
			if(invoiceId == 0){
				return;
			}
			
			populatePaymentDisplayTab(invoiceId);
		}
		
	}
	
	
	public void populatePaymentDisplayTab(Long invoiceId){
		//String command = String.valueOf(invoiceId);
		String command = "Make New Payment";
		JPanel displayPanel = ComponentRegistry.instance().getDisplayPanel();
		
		JTabbedPane tabbedPane = (JTabbedPane)displayPanel.getComponent(0);
		Component[] tabComps = tabbedPane.getComponents();
			
		boolean tabAlreadyOpened = false;
		if(tabComps != null){
			for (int i = 0; i < tabComps.length; i++) {
				if(command.equals(tabComps[i].getName())){
					tabAlreadyOpened = true;
					tabbedPane.setSelectedComponent(tabComps[i]);
					break;
					
				}
			}
		}
			
		if(!tabAlreadyOpened){
			Icon compIcon = MainMenu.PAYMENT_ICON;
			long customerId = ((LiteComboModel)FIELD_INV_CUSTOMER.getSelectedItem()).getId();
			CustomerVO customerVO = ServiceClient.getInstance().retrieveCustomer(customerId);
			
			JComponent comp = new PaymentTransactionForm(constructPaymentTransDSModel(customerVO), 
					constructInvPaymentTransModel(invoiceId, customerVO));
			comp.setName(command);
			//comp.setOpaque(true);
			//comp.setBackground(Color.WHITE);
			
			Util.buildCloseTab(command, "Payment: "+command, tabbedPane, comp, compIcon,
					new CloseTabListener(displayPanel, comp));
			//tabbedPane.setSelectedComponent(newContainer);
		}
		
		ComponentRegistry.instance().repaintComponents();
	}
	
	private void populateFormFields(final InvoiceVO invoiceVO){
		FIELD_INV_ID.setText(String.valueOf(invoiceVO.getId()));
		formModel.setInvoiceId(invoiceVO.getId());
		
		FIELD_ORD_ID.setText(String.valueOf(invoiceVO.getOrderId()));
		formModel.setOrderId(invoiceVO.getOrderId());
		
		List<LineItemVO> orderLineItemVOs = invoiceVO.getInvoiceLineItems();
		if(orderLineItemVOs != null && !orderLineItemVOs.isEmpty()){
			for(int i = 0; i < orderLineItemVOs.size(); i++){
				LineItemVO lineItemVO = orderLineItemVOs.get(i); 
				lineItemTable.getModel().setValueAt(lineItemVO.getId(), i,  0);
				System.out.println("setting value "+lineItemVO.getId() +" @ index "+i);
			}
		}
		
		List<InvoiceChargeVO> invoiceCharges = invoiceVO.getInvoiceCharges();
		if(invoiceCharges != null && !invoiceCharges.isEmpty()){
			for (int i = 0; i < invoiceCharges.size(); i++) {
				InvoiceChargeVO invoiceChargeVO = invoiceCharges.get(i);
				taxTable.getModel().setValueAt(invoiceChargeVO.getId(), i, 3);
			}
		}
		
	}
	
	
	
	private InvoiceVO constructInvoiceMetaData(){
		InvoiceVO invoiceVO = new InvoiceVO(purchase);
		long invoiceId = isNullOrEmpty(FIELD_INV_ID.getText()) ? 0 : Long.valueOf(FIELD_INV_ID.getText());
		invoiceVO.setId(invoiceId);
		
		Date invoiceDate = FIELD_INV_DATE.getDate();
		/*try {
			invoiceDate = new SimpleDateFormat("dd-MM-yyyy").parse(FIELD_INV_DATE.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		invoiceVO.setInvoiceDate(invoiceDate);
		
		long customerId = ((LiteComboModel)FIELD_INV_CUSTOMER.getSelectedItem()).getId();
		invoiceVO.setCustomerId(customerId);
		
		int statusId = (int)((LiteComboModel)FIELD_INV_STATUS.getSelectedItem()).getId();
		invoiceVO.setInvoiceStatus(statusId);
		
		
		
		long orderId = isNullOrEmpty(FIELD_ORD_ID.getText()) ? 0 : Long.valueOf(FIELD_ORD_ID.getText());
		invoiceVO.setOrderId(orderId);
		

		String dcNo = FIELD_INV_DC_NO.getText();
		invoiceVO.setDcNo(dcNo);
		
		invoiceVO.setDcDate(FIELD_INV_DC_DATE.getDate());
		/*if(FIELD_INV_DC_DATE.getDate() != null){
			try {
				Date dcDate = new SimpleDateFormat("dd-MM-yyyy").parse(FIELD_INV_DC_DATE.getText());
				invoiceVO.setDcDate(dcDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}*/
		
		String description = FIELD_DESC.getText();
		invoiceVO.setInvDesc(description);
		
		invoiceVO.setInvoiceLineItems(constructLineItemData());
		invoiceVO.setInvoiceCharges(constructInvoiceCharges());
		invoiceVO.setCreationDate(new Date());
		invoiceVO.setLastModifiedDate(new Date());
		populateInoviceAmts(invoiceVO);
		
		//TODO - to be modified later
		invoiceVO.setBillToAdd(1);
		invoiceVO.setShipToAdd(1);
				
		return invoiceVO;
	}
	
	
	private void populateInoviceAmts(InvoiceVO invoiceVO){
		
		Double invoiceAmtExclCharges = new Double(taxTable.getModel().getValueAt(0, 1).toString());
		invoiceVO.setInvoiceAmountExclCharges(invoiceAmtExclCharges);
		
		int rows = taxTable.getModel().getRowCount();
		Double invoiceAmt = new Double(taxTable.getModel().getValueAt(rows-1, 1).toString());
		invoiceVO.setInvoiceAmount(invoiceAmt);
		
	}
	
	
	private OrderVO constructOrderMetaData(InvoiceVO invoiceVO){
		OrderVO orderVO = new OrderVO(purchase);
						
		Date orderDate = FIELD_ORD_DATE.getDate();
		/*try {
			orderDate = new SimpleDateFormat("dd-MM-yyyy").parse(FIELD_ORD_DATE.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		orderVO.setOrderDate(orderDate);
		orderVO.setCreationDate(invoiceVO.getCreationDate());
		orderVO.setLastModifiedDate(invoiceVO.getCreationDate());
		
		
		long customerId = ((LiteComboModel)FIELD_INV_CUSTOMER.getSelectedItem()).getId();
		orderVO.setCustomerId(customerId);
		
		orderVO.setOrderAmount(invoiceVO.getInvoiceAmount());
		orderVO.setOrderDesc(invoiceVO.getInvDesc());
		orderVO.setOrderLineItems(constructLineItemData());
			
		orderVO.setOrderStatus(OrderStatusEnum.COMPLETED);
		
		String partyOrderId = FIELD_PARTY_ORD_ID.getText();
		orderVO.setPartyOrderId(partyOrderId);
		orderVO.setPartyOrderDate(FIELD_PARTY_ORD_DATE.getDate());
		/*try {
			Date partyOrderDate = new SimpleDateFormat("dd-MM-yyyy").parse(FIELD_PARTY_ORD_DATE.getText());
			orderVO.setPartyOrderDate(partyOrderDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		return orderVO;
	}



	private List<InvoiceChargeVO> constructInvoiceCharges(){
		int rows = taxTable.getModel().getRowCount();
		List<InvoiceChargeVO> invoiceChargeVOs = new ArrayList<InvoiceChargeVO>();
		
		for (int i = 0; i < rows; i++) {
			Object[] row = retrieveRow((InvoiceTaxTableModel)taxTable.getModel(), i, 4);
			InvoiceChargeVO invoiceChargeVO = constructInvoiceChargeVO(row);
			if(invoiceChargeVO != null){
				invoiceChargeVOs.add(invoiceChargeVO);
			}
		}
		
		return invoiceChargeVOs;
	}
	
	private InvoiceChargeVO constructInvoiceChargeVO(Object[] row){
		InvoiceChargeVO invoiceChargeVO = new InvoiceChargeVO();
		for (int j = 0; j < row.length; j++) {
			switch (j) {
			case 0:
				long chargeId = -1;
				String chargeName = null;
				if(row[j] instanceof LiteComboModel){
					LiteComboModel comboModel = (LiteComboModel)row[j];
					chargeId = comboModel.getId();
					if(chargeId == 0){
						return null;
					}
					
					
					chargeName = comboModel.getName();
				}
				
				invoiceChargeVO.setCharge(new ChargeVO(chargeId, chargeName));
				break;
			case 1:
				double amount = 0d;
				if(row[j] instanceof String){
					amount = new Double(((String)row[j]).trim());
				}else{
					amount = (Double)row[j];
				}
				invoiceChargeVO.setChargedAmount(amount);
				break;
			case 3:	
				invoiceChargeVO.setId((Long)row[j]);
				break;
			default:
				//do nothing				
				break;
			}
			
			invoiceChargeVO.setCreationDate(new Date());
			invoiceChargeVO.setLastModifiedDate(new Date());
		}
		return invoiceChargeVO;
	}
	
	private List<LineItemVO> constructLineItemData(){
		int rows = lineItemTable.getModel().getRowCount();
		List<LineItemVO> lineItems = new ArrayList<LineItemVO>();
		
		for(int i= 0; i < rows; i++){
			Object[] row = retrieveRow((OrderItemModel)lineItemTable.getModel(), i);
			System.out.println("row retrieved "+row[3]);
			LineItemVO lineItemVO = constructLineItemVO(row);
			//lineItemVO.setRefId(refId);
			//lineItemVO.setRefTypeEnum(refType);
						
			if(lineItemVO != null){
				lineItemVO.setCreationDate(new Date());
				lineItemVO.setLastModifiedDate(new Date());
				lineItems.add(lineItemVO);
			}
		}
		
		return lineItems;
	}
	
	private LineItemVO constructLineItemVO(Object[] row){
		LineItemVO lineItemVO = new LineItemVO();
		for (int j = 0; j < row.length; j++) {
			double quantity = 0;
			switch (j) {
			case 0:
				lineItemVO.setId((Long)row[j]);
				break;
			case 2:
				ProductVO productVO = new ProductVO(((LiteComboModel)row[j]).getId(), ((LiteComboModel)row[j]).getName());
				if(productVO.getProductId() <= 0){
					return null;
				}
				lineItemVO.setProductVO(productVO);
				break;
			case 3:
				String lineItemDesc = row[j].toString();
				lineItemVO.setLineItemDesc(lineItemDesc);
				break;
			case 4:
				UnitVO unitVO = new UnitVO(((LiteComboModel)row[j]).getId(), ((LiteComboModel)row[j]).getName());
				if(unitVO.getId() <= 0){
					return null;
				}
				
				ProductUnitVO prodUnitVO = new ProductUnitVO();
				prodUnitVO.setUnitVO(unitVO);
				lineItemVO.setUnitVO(prodUnitVO);
				break;
			case 5:
				double price = (Double)row[j];
				lineItemVO.setPrice(price);
				break;
			case 6:
				quantity = (Double)row[j];
				if(quantity <= 0){
					return null;
				}
				lineItemVO.setQuantity(quantity);
				lineItemVO.setInvoicedQty(quantity);
				break;
			case 7:
				double totalCost = (Double)row[j];
				lineItemVO.setTotalCost(totalCost);
				break;
			/*case 8:
				int invoicedQty = (Integer)row[j];
				lineItemVO.setInvoicedQty((invoicedQty + quantity));
				break;*/	
			default:
				//do nothing
				break;
			}
		}
		return lineItemVO;
	}
	
	private Object[] retrieveRow(DefaultTableModel itemModel, int row, int requiredColumn){
		Object[] invoiceRow = new Object[requiredColumn];
		
		for (int i = 0; i < invoiceRow.length; i++) {
			invoiceRow[i] = itemModel.getValueAt(row, i);
		}
		
		return invoiceRow;
	}
	
	private Object[] retrieveRow(DefaultTableModel itemModel, int row){
		int requiredColumn = 8;
		return retrieveRow(itemModel, row, requiredColumn);
	}
	
	private boolean isNullOrEmpty(String string){
		boolean isEmpty = true;
		if(string != null && !string.trim().equals("")){
			isEmpty = false;
		}
		return isEmpty;
	}
	
	
	
}
