package com.litetech.omt.ui.comp;

import static com.litetech.omt.ui.model.OrderItemModel.header;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.LineItemRefTypeEnum;
import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.ui.comp.action.LineItemTableAction;
import com.litetech.omt.ui.comp.editor.LiteComboModelCellEditor;
import com.litetech.omt.ui.comp.editor.LiteEditableComboBoxEditor;
import com.litetech.omt.ui.comp.editor.LiteEditableComboCellEditor;
import com.litetech.omt.ui.comp.listener.CloseTabListener;
import com.litetech.omt.ui.comp.listener.TableCellListener;
import com.litetech.omt.ui.comp.renderer.CheckBoxRenderer;
import com.litetech.omt.ui.comp.renderer.LiteComboModelCellRenderer;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.comp.validator.OrderValidator;
import com.litetech.omt.ui.model.InvoiceFormModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.OrderFormModel;
import com.litetech.omt.ui.model.OrderItemModel;
import com.litetech.omt.ui.model.ds.InvoiceFormDSModel;
import com.litetech.omt.ui.model.ds.OrderFormDSModel;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.UnitVO;

public class OrderForm extends JPanel implements PanelTabComp{

	private OrderFormModel orderFormModel;
	private OrderFormDSModel orderFormDSModel;
	
	private JTable orderTable;
	private JPanel displayPanel;
	private JPanel rootPanel;
	private JPanel myPanelTab;
	private LiteComboModelCellEditor unitCellEditor; 
	
	private JTextField FIELD_ORDER_ID;
	private OMTCalendar FIELD_ORDER_DATE;
	private JComboBox FIELD_ORDER_STATUS;
	private JComboBox FIELD_ORDER_CUSTOMER;
	private JTextField FIELD_PARTY_ORDER_ID;
	private OMTCalendar FIELD_PARTY_ORDER_DATE;
	private boolean purchase;
	private OrderValidator orderValidator;
	public OrderForm(OrderFormModel orderFormModel, OrderFormDSModel orderFormDSModel, JPanel displayPanel){ 
		this.orderFormModel = orderFormModel;
		this.orderFormDSModel = orderFormDSModel;
		this.displayPanel = displayPanel;
		this.purchase = orderFormModel.isPurchase();
		this.orderValidator = new OrderValidator();
		//setBackground(Color.WHITE);
		//setSize(1500, 1500);
		
		rootPanel = new JPanel();
		rootPanel.setBackground(Color.WHITE);
		rootPanel.setPreferredSize(AppContext.getInstance().getBaseFormPanelDimension());
		
		//setPreferredSize(new Dimension(100, 100));
		rootPanel.add(constructOrderMetaDataPanel());
		rootPanel.add(constructOrderItemPanel());
		rootPanel.add(constructInvoicePanel());
		add(rootPanel);
	}

	
	public JPanel constructOrderMetaDataPanel(){
		JPanel orderMetaData = new JPanel();
		//orderMetaData.setBackground(Color.WHITE);
		orderMetaData.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 100));
		orderMetaData.setOpaque(true);
		
		populateOrderMetaDataFields(orderMetaData);
		return orderMetaData;
	}
	
	
	public JPanel constructInvoicePanel(){
		
		JPanel invoicePanel = new JPanel();
		//orderItemPanel.setBackground(Color.GREEN);
		invoicePanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 200));
		JLabel label = new JLabel("List of Invoices");
		invoicePanel.add(label);
		
		populateInvoices(invoicePanel);
		//invoicePanel.add(constuctTableControlPanel());
		return invoicePanel;
	}	
	
	
	
	public void populateDisplayTab(Long invoiceId){
		String command = String.valueOf(invoiceId);
		String title = purchase ? "Puchase Invoice: "+command : "Invoice: "+command;
		
		OMTTabbedPane tabbedPane = (OMTTabbedPane)displayPanel.getComponent(0);
		Component[] tabComps = tabbedPane.getComponents();
			
		boolean tabAlreadyOpened = false;
		if(tabComps != null){
			for (int i = 0; i < tabComps.length; i++) {
				if(title.equals(tabComps[i].getName())){
					tabAlreadyOpened = true;
					tabbedPane.resetSelectedComponent(tabComps[i]);
					break;
				}
			}
		}
			
		if(!tabAlreadyOpened){
			Icon compIcon = MainMenu.NOTE_ICON;
			InvoiceFormModel invoiceFormModel = constructInvoiceModel(invoiceId);
			JComponent comp = new InvoiceForm(invoiceFormModel, 
					constructInvocieFormDSModel(invoiceFormModel.getOrderId(), invoiceFormModel.getCustomerId(),
							invoiceFormModel.getLineItemLs()));
			//comp.setName(command);
			//comp.setOpaque(true);
			//comp.setBackground(Color.WHITE);
			
			
			comp.setName(title);
			Util.buildCloseTab(command, title, tabbedPane, comp, compIcon,
					new CloseTabListener(displayPanel, comp));
			//tabbedPane.setSelectedComponent(newContainer);
		}
		
		ComponentRegistry.instance().repaintComponents();
	}
	
	
	private InvoiceFormModel constructInvoiceModel(final long invoiceId){
		InvoiceVO invoiceVO = ServiceClient.getInstance().retrieveInvoice(invoiceId, purchase);
		
		TransactionTypeEnum typeEnum = purchase ? TransactionTypeEnum.DEBIT : TransactionTypeEnum.CREDIT;
		List<InvoicePaymentVO> invoicePaymentVOs = ServiceClient.getInstance().retrieveInvoicePayments(invoiceId,
				typeEnum);
				
		InvoiceFormModel invoiceFormModel = new InvoiceFormModel(purchase);
		invoiceFormModel.setInvoiceId(invoiceVO.getId());
		invoiceFormModel.setInvoiceDate(invoiceVO.getInvoiceDate());
		invoiceFormModel.setCustomerId(orderFormModel.getCustomerId());
		invoiceFormModel.setStatus(invoiceVO.getInvoiceStatus());
		
		invoiceFormModel.setOrderId(invoiceVO.getOrderId());
		invoiceFormModel.setOrderDate(orderFormModel.getOrderDate());
		invoiceFormModel.setPartyOrderId(orderFormModel.getPartyOrderId());
		invoiceFormModel.setPartyOrderDate(orderFormModel.getPartyOrderDate());
		invoiceFormModel.setDcNo(invoiceVO.getDcNo());
		invoiceFormModel.setDescription(invoiceVO.getInvDesc());
		invoiceFormModel.setLineItemLs(invoiceVO.getInvoiceLineItems());
		invoiceFormModel.setInvoiceChargeVOs(invoiceVO.getInvoiceCharges());
		
		invoiceFormModel.setInvoicePaymentVOs(invoicePaymentVOs);
		invoiceFormModel.setAmountPaid(invoiceVO.getPaidAmount());
		
		return invoiceFormModel;
	}
	
	private InvoiceFormDSModel constructInvocieFormDSModel(final long orderId, 
			final long customerId, List<LineItemVO> invoiceLineItemVOs){
		InvoiceFormDSModel invoiceFormDSModel = new InvoiceFormDSModel();
		
		List<ChargeVO> chargeVOs = ServiceClient.getInstance().retrieveAllCharges();
		invoiceFormDSModel.setChargeVOs(chargeVOs);
		
		List<CustomerVO> customerLs = new ArrayList<CustomerVO>();
		CustomerVO customerVO = ServiceClient.getInstance().retrieveCustomer(customerId);
		customerLs.add(customerVO);
		invoiceFormDSModel.setCustomers(customerLs);
		
		OrderVO orderVO = ServiceClient.getInstance().retrieveOrder(orderId, purchase);
		List<LineItemVO> orderedLineItemVOs = orderVO.getOrderLineItems();
		
		List<ProductVO> orderedProductVOs = new ArrayList<ProductVO>();
		Map<Long, List<Long>> orderedProductUnitMap = new TreeMap<Long, List<Long>>();
		Map<Long, Map<Long, Double>> productUnitPriceMap = new HashMap<Long, Map<Long,Double>>();
		
		for(LineItemVO orderedLineItemVO : orderedLineItemVOs){
			ProductVO orderedProductVO = orderedLineItemVO.getProductVO();
			List<Long> orderedUnitIds = orderedProductUnitMap.get(orderedProductVO.getProductId());
			if(orderedUnitIds == null){
				orderedUnitIds = new ArrayList<Long>();
				orderedProductUnitMap.put(orderedProductVO.getProductId(), orderedUnitIds);
			}
			orderedUnitIds.add(orderedLineItemVO.getUnitVO().getUnitId());
			
			Map<Long, Double> orderedUnitPrice = productUnitPriceMap.get(orderedProductVO.getProductId());
			if(orderedUnitPrice == null){
				orderedUnitPrice = new HashMap<Long, Double>();
				productUnitPriceMap.put(orderedProductVO.getProductId(), orderedUnitPrice);
			}
			
			orderedUnitPrice.put(orderedLineItemVO.getUnitVO().getUnitId(), orderedLineItemVO.getPrice());
			orderedProductVO.setOrderedPrice(orderedUnitPrice);
			
			if(!orderedProductVOs.contains(orderedProductVO)){
				orderedProductVOs.add(orderedProductVO);
			}
		}
		
		removeUnorderedUnits(orderedProductVOs, orderedProductUnitMap);
		invoiceFormDSModel.setProductVOs(orderedProductVOs);
		
		//now we have product with only selected units
		Map<Integer, List<LiteComboModel>> defaultDropDownMap = new TreeMap<Integer, List<LiteComboModel>>();
		
		for(int row = 0; row < invoiceLineItemVOs.size(); row++){
			LineItemVO lineItemVO = invoiceLineItemVOs.get(row);
			ProductVO productVO = getProductVO(orderedProductVOs, lineItemVO.getProductVO());
			lineItemVO.setProductVO(productVO);
			
			
			List<ProductUnitVO> productUnitVOs = productVO.getProductUnitVOs();
			List<LiteComboModel> comboModels = new ArrayList<LiteComboModel>();
			for(ProductUnitVO productUnitVO : productUnitVOs){
				LiteComboModel unitComboModel = new LiteComboModel(productUnitVO.getUnitId(), 
						productUnitVO.getUnitName(), productUnitVO.getPriceRatio());
				comboModels.add(unitComboModel);
			}
			
			defaultDropDownMap.put(row, comboModels);
		}
		
		invoiceFormDSModel.setUnitModelMap(defaultDropDownMap);
		
		return invoiceFormDSModel;
	}
	
	
	private ProductVO getProductVO(List<ProductVO> productVOs, ProductVO defaultProductVO){
		for(ProductVO productVO : productVOs){
			if(defaultProductVO.getProductId() == productVO.getProductId()){
				return productVO;
			}
		}
		//if product is removed from ordered line items.
		String prodName = defaultProductVO.getProductName(); 
		defaultProductVO.setProductName("ATTN >> "+prodName);
		return defaultProductVO;
		
	}
	
	
	private void populateInvoices(JPanel invoicePanel){
		/*final Object[][] rows = {{"10001","Tiles1", "1-1-2013", "New", "100.0"}, {"10002","Tiles2", "1-1-2013", "New", "100.0"},
				{"10003","Tiles3", "1-1-2013", "New", "100.0"}};*/
		
		final Object[] invoiceHeader = {"Invoice Id","Invoice Date", "Invoice Status", "Invoice Amount"};		
		final Object[][] rows = orderFormModel.getInvoiceRows();
		DefaultTableModel defaultTableModel = new DefaultTableModel(rows, invoiceHeader){
			 @Override
            public boolean isCellEditable ( int row, int column ){
				 return false;
            }
		};
		
		JTable invoiceTable = new JTable(defaultTableModel); 
		invoiceTable.setRowHeight(20);
		invoiceTable.setBorder(LineBorder.createBlackLineBorder());
		invoiceTable.setRowSelectionAllowed(true);
		invoiceTable.setPreferredScrollableViewportSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 100));
		invoiceTable.setFillsViewportHeight(true);
		
		invoiceTable.getColumn(invoiceHeader[0]).setPreferredWidth(100);
		invoiceTable.getColumn(invoiceHeader[1]).setPreferredWidth(100);
		invoiceTable.getColumn(invoiceHeader[2]).setPreferredWidth(100);
		invoiceTable.getColumn(invoiceHeader[3]).setPreferredWidth(100);
		
		invoiceTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		invoiceTable.setDragEnabled(false);
		
		invoiceTable.addMouseListener(new MouseAdapter() {
	        	
    	 @Override
    	  public void mouseClicked(MouseEvent e) {
    	    JTable target = (JTable)e.getSource();
    	    int row = target.getSelectedRow();
    	    int column = target.getSelectedColumn();
    	    
    	   // if(column == 0){
    	    	Object object = e.getSource();
    	    	Long invoiceId = (Long)rows[row][0];
    	    	//String actionCommand = button.getActionCommand();
    	    	populateDisplayTab(invoiceId);
    	   // }
    	    // do some action if appropriate column
    	    
    	  }
    	});
		
		 
        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        invoicePanel.add(scrollPane);
	}
	
	public JPanel constructOrderItemPanel(){
		
		JPanel orderItemPanel = new JPanel();
		//orderItemPanel.setBackground(Color.GREEN);
		orderItemPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 200));
		
		populateOrderItems(orderItemPanel);
		orderItemPanel.add(constuctTableControlPanel());
		return orderItemPanel;
	}
	
	
	
	private Vector<LiteComboModel> constructProductList(){
		Vector<LiteComboModel> productLs = new Vector<LiteComboModel>();
		List<ProductVO> productVOs = orderFormDSModel.getProductVOs();
		for(ProductVO productVO : productVOs){
			productLs.add(new LiteComboModel(productVO.getProductId(), productVO.getProductName(), productVO));
		}
		return productLs;
	}
	
	
	
	private void populateOrderItems(JPanel orderItemPanel){
		/*final Object[][] rows = {{101, Boolean.FALSE, new LiteComboModel(1, "Tiles1"), "No", "5", "10.0", "5"}, {102, Boolean.FALSE, new LiteComboModel(2, "Tiles2"), "No", "10", "10.0", "2"},
				{103, Boolean.FALSE, new LiteComboModel(3, "Tiles3"), "No", "5", "10.0", "2"}
				  };*/
		final Vector<LiteComboModel> productLs = constructProductList();
		
		JComboBox productNameCombo = new JComboBox(productLs);
		productNameCombo.setEditable(true);
		//JComboBox productNameCombo = new JComboBox(productNames);
		
		//productNameCombo.setRenderer(new LiteComboModelRenderer());
				
		final Object[][] rows = orderFormModel.getLineItemRows();
		
		unitCellEditor = new LiteComboModelCellEditor();
		if(orderFormModel.getUnitCombiMap() != null && !orderFormModel.getUnitCombiMap().isEmpty()){
			unitCellEditor.setComboModelMap(orderFormModel.getUnitCombiMap());
		}
		
		orderTable = new JTable(new OrderItemModel(rows)); 
		orderTable.setRowHeight(20);
		orderTable.setBorder(LineBorder.createBlackLineBorder());
		orderTable.setRowSelectionAllowed(false);
		orderTable.setPreferredScrollableViewportSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 100));
		orderTable.setFillsViewportHeight(true);
		orderTable.getColumnModel().getColumn(1).setHeaderRenderer(new CheckBoxRenderer(orderTable.getTableHeader()));
        //table.getColumn(header[0]).setCellRenderer(new CheckBoxRenderer());
       // table.getColumn(header[0]).setPreferredWidth(100);
        
		orderTable.removeColumn(orderTable.getColumn(header[0]));
                
		orderTable.getColumn(header[2]).setPreferredWidth(200);
		//orderTable.getColumn(header[2]).setCellEditor(new DefaultCellEditor(productNameCombo));
		orderTable.getColumn(header[2]).setCellEditor(new LiteEditableComboCellEditor(productLs));
		orderTable.getColumn(header[2]).setCellRenderer(new LiteComboModelCellRenderer());
        
		orderTable.getColumn(header[3]).setPreferredWidth(200);
		orderTable.getColumn(header[4]).setPreferredWidth(100);
		orderTable.getColumn(header[4]).setCellEditor(unitCellEditor);
		orderTable.getColumn(header[4]).setCellRenderer(new LiteComboModelCellRenderer());
		
		orderTable.getColumn(header[5]).setPreferredWidth(200);
		orderTable.getColumn(header[6]).setPreferredWidth(100);
		orderTable.getColumn(header[7]).setPreferredWidth(100);
        
		orderTable.setDragEnabled(false);
		List<Integer> listenIndexes = new ArrayList<Integer>();
		listenIndexes.add(2);
		listenIndexes.add(4);
		listenIndexes.add(5);
		//TableCellListener cellListener = new TableCellListener(orderTable, new LineItemTableAction(listenIndexes, 5, 3));
		TableCellListener cellListener = new TableCellListener(orderTable, new LineItemTableAction());
		
        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderItemPanel.add(scrollPane);
        
        //JTable footerTable = new JTable(new OrderItemModel());
        //footerTable.addR
        
	}
	
	private JPanel constuctTableControlPanel(){
		JPanel orderItemControlPanel = new JPanel();
		//orderItemControlPanel.setBackground(Color.BLUE);
		orderItemControlPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 50));
		
		
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
		controlBox.add(Box.createHorizontalStrut(200));
		
		JButton saveBt = new JButton("Save");
		controlBox.add(saveBt);
		controlBox.add(Box.createHorizontalStrut(200));
		saveBt.addActionListener(new SaveOrderListener());
		
		JButton invoiceBt = new JButton("Invoice Selected Items");
		invoiceBt.addActionListener(new InvoiceItemListener(this));
		controlBox.add(invoiceBt);
		controlBox.add(Box.createHorizontalStrut(50));
		
		orderItemControlPanel.add(controlBox);
		return orderItemControlPanel;
		
	}
	
	public void populateOrderMetaDataFields(JPanel orderMetaData){
		
		JPanel orderMetaDataPanel = new JPanel();
		orderMetaDataPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 55));
		
		GridLayout gridLayout = new GridLayout(2, 6, 10, 6);
		orderMetaDataPanel.setLayout(gridLayout);
		
				
		JLabel orderId = new JLabel("Order ID", 10);
		String orderIdStr = orderFormModel.getOrderId() != 0 ? String.valueOf(orderFormModel.getOrderId()) : "";
		FIELD_ORDER_ID = new JTextField(orderIdStr, 15);
		FIELD_ORDER_ID.setEditable(false);
		
		orderMetaDataPanel.add(orderId);
		orderMetaDataPanel.add(FIELD_ORDER_ID);
			
		
		JLabel orderDate = new JLabel("Order Date",10);
		FIELD_ORDER_DATE = new OMTCalendar(orderFormModel.getOrderDate());
		orderMetaDataPanel.add(orderDate);
		orderMetaDataPanel.add(FIELD_ORDER_DATE);
		
		
		
		JLabel orderStatus = new JLabel("Order Status",10);
		
		Vector<LiteComboModel> orderStatusList = new Vector<LiteComboModel>();
		OrderStatusEnum[] orderStatuses = OrderStatusEnum.values();
		for(OrderStatusEnum orderStatusEnum : orderStatuses){
			orderStatusList.add(new LiteComboModel(orderStatusEnum.getId(), orderStatusEnum.getName()));
		}
		
		
		FIELD_ORDER_STATUS = new JComboBox(orderStatusList);
		FIELD_ORDER_STATUS.setSelectedItem(new LiteComboModel(orderFormModel.getOrderStatus().getId()));
		FIELD_ORDER_STATUS.setRenderer(new LiteComboModelRenderer());
		
		orderMetaDataPanel.add(orderStatus);
		orderMetaDataPanel.add(FIELD_ORDER_STATUS);
			

		
		JLabel customerLabel = new JLabel("Customer", 10);
		
		Vector<LiteComboModel> customerList = new Vector<LiteComboModel>();
		List<CustomerVO> customerVOLs = orderFormDSModel.getCustomers();
		for(CustomerVO customerVO : customerVOLs){
			customerList.add(new LiteComboModel(customerVO.getId(), customerVO.getName()));
		}		
		
		FIELD_ORDER_CUSTOMER = new JComboBox(customerList);
		LiteComboModel selectedCustomer = new LiteComboModel(orderFormModel.getCustomerId());
		FIELD_ORDER_CUSTOMER.setSelectedItem(selectedCustomer);
		FIELD_ORDER_CUSTOMER.setRenderer(new LiteComboModelRenderer());
		FIELD_ORDER_CUSTOMER.setEditor(new LiteEditableComboBoxEditor(FIELD_ORDER_CUSTOMER, customerList));
		
		
		orderMetaDataPanel.add(customerLabel);
		orderMetaDataPanel.add(FIELD_ORDER_CUSTOMER);
		
		JLabel partysOrderId = new JLabel("Party's Ref/Order",10);
		FIELD_PARTY_ORDER_ID = new JTextField(orderFormModel.getPartyOrderId(), 10);
		orderMetaDataPanel.add(partysOrderId);
		orderMetaDataPanel.add(FIELD_PARTY_ORDER_ID);
		
		
		JLabel partysOrderDate = new JLabel("Ref/Order Date",10);
		FIELD_PARTY_ORDER_DATE = new OMTCalendar(orderFormModel.getPartyOrderDate());
		orderMetaDataPanel.add(partysOrderDate);
		orderMetaDataPanel.add(FIELD_PARTY_ORDER_DATE);
		
		orderMetaData.add(orderMetaDataPanel);
	}
	
	
	
	private void removeRow(){
		int rows = orderTable.getModel().getRowCount();
		List<Integer> rowsRemoved = new ArrayList<Integer>();
		
		for(int i=rows; i > 0; i--){
			Boolean valueObject = (Boolean)orderTable.getModel().getValueAt(i-1, 1);
			if(Boolean.TRUE.equals(valueObject)){
				((DefaultTableModel)orderTable.getModel()).removeRow(i-1);
				rowsRemoved.add(i-1);
			}
		}
		unitCellEditor.removeAndRenIndexMap(rowsRemoved);
		
	}
	
	
	private void addRow(int totalRow){
		for(int i=0; i < totalRow; i++){
			Object[] row = new Object[]{0l, Boolean.TRUE, new LiteComboModel(0, "Select A product"), " ",new LiteComboModel(0, "Select Unit"), 0d, 0d, 0d, 0d};
			((DefaultTableModel)orderTable.getModel()).addRow(row);
		}
	}
	
	public class RemoveLineItemListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			removeRow();
		//	((JTable)arg0.getSource()).repaint();
		}
	}
	
	private boolean isNullOrEmpty(String string){
		boolean isEmpty = true;
		if(string != null && !string.trim().equals("")){
			isEmpty = false;
		}
		return isEmpty;
	}
	
	private OrderVO constructOrderMetaData(){
		
		long orderId = isNullOrEmpty(FIELD_ORDER_ID.getText()) ? 0 : Long.valueOf(FIELD_ORDER_ID.getText());
		Date orderDate = FIELD_ORDER_DATE.getDate();
		/*try {
			orderDate = new SimpleDateFormat("dd-MM-yyyy").parse(FIELD_ORDER_DATE.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		long customerId = ((LiteComboModel)FIELD_ORDER_CUSTOMER.getSelectedItem()).getId();
		int statusId = (int)((LiteComboModel)FIELD_ORDER_STATUS.getSelectedItem()).getId();
		//int taxId = (int)((LiteComboModel)FIELD_ORDER_TAX.getSelectedItem()).getId();
		
		String partyOrderId = FIELD_PARTY_ORDER_ID.getText();
		
		Date partyOrderDate = FIELD_PARTY_ORDER_DATE.getDate();
		/*try {
			partyOrderDate = new SimpleDateFormat("dd-MM-yyyy").parse(FIELD_PARTY_ORDER_DATE.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		
		OrderVO orderVO = new OrderVO(purchase);
		orderVO.setId(orderId);
		orderVO.setCreationDate(new Date());
		orderVO.setCustomerId(customerId);
		orderVO.setLastModifiedDate(new Date());
		orderVO.setOrderAmount(100);
		orderVO.setOrderDate(orderDate);
		orderVO.setOrderDesc("Test Order Desc");
		orderVO.setOrderStatus(OrderStatusEnum.getById(statusId));
		//orderVO.setTaxId(taxId);
		orderVO.setPartyOrderId(partyOrderId);
		orderVO.setPartyOrderDate(partyOrderDate);
		
		List<LineItemVO> orderLineItems = constructLineItemData(orderId);
		orderVO.setOrderLineItems(orderLineItems);
		return orderVO;
	}
	
	
	private LineItemVO constructLineItemVO(Object[] row, boolean toInvoice){
		LineItemVO lineItemVO = new LineItemVO();
		for (int j = 0; j < row.length; j++) {
						
			switch (j) {
			case 0:
				lineItemVO.setId((Long)row[j]);
				break;
			case 2:
				LiteComboModel productCombo = (LiteComboModel)row[j];
		    	ProductVO productVO = (ProductVO)productCombo.getRelativeObj();
		    	if(productVO == null){
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
				double quantity = (Double)row[j];
				if(toInvoice){
					double alreadyInvoicedQty = (Double)row[8];
					if(alreadyInvoicedQty < quantity){
						quantity = quantity - alreadyInvoicedQty;
					}
				}
				
				if(quantity <= 0){
					return null;
				}
				lineItemVO.setQuantity(quantity);
				break;
			case 7:
				double totalCost = (Double)row[j];
				if(toInvoice){
					totalCost = lineItemVO.getPrice() * lineItemVO.getQuantity();
				}
				lineItemVO.setTotalCost(totalCost);
				
				break;
			case 8:
				double invoicedQty = (Double)row[j];
				lineItemVO.setInvoicedQty(invoicedQty);
				break;	
			default:
				//do nothing
				break;
			}
		}
		return lineItemVO;
	}
	
	private List<LineItemVO> constructLineItemData(long orderId){
		int rows = orderTable.getModel().getRowCount();
		List<LineItemVO> lineItems = new ArrayList<LineItemVO>();
		
		for(int i= 0; i < rows; i++){
			Object[] row = retrieveRow((OrderItemModel)orderTable.getModel(), i);
			LineItemVO lineItemVO = constructLineItemVO(row, false);
			if(lineItemVO != null){
				lineItemVO.setRefId(orderId);
				lineItemVO.setRefTypeEnum(LineItemRefTypeEnum.ORDER);
						
				lineItemVO.setCreationDate(new Date());
				lineItemVO.setLastModifiedDate(new Date());
				lineItems.add(lineItemVO);
			}
		}
		
		return lineItems;
	}
	
	
	private Object[] retrieveRow(OrderItemModel itemModel, int row){
		int requiredColumn = 9;
		Object[] invoiceRow = new Object[requiredColumn];
		
		for (int i = 0; i < invoiceRow.length; i++) {
			invoiceRow[i] = itemModel.getValueAt(row, i);
		}
		
		return invoiceRow;
	}
	
	public class SaveOrderListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			saveOrder(true);
		}
		
	}
	
	private boolean saveOrder(boolean showSuccessMsg){
		OrderVO orderVO = constructOrderMetaData();
		boolean validationSuccess = orderValidator.validate(orderVO);
		if(validationSuccess){
			ServiceClient.getInstance().createOrder(orderVO);
			populateFormFields(orderVO);
			if(showSuccessMsg){
				JOptionPane.showMessageDialog(null, "Order Saved "+orderVO.getId());
			}
		}
		
		return validationSuccess;
	}
	
	private void populateFormFields(final OrderVO orderVO){
		FIELD_ORDER_ID.setText(String.valueOf(orderVO.getId()));
		orderFormModel.setOrderId(orderVO.getId());
		
		List<LineItemVO> orderLineItemVOs = orderVO.getOrderLineItems();
		if(orderLineItemVOs != null && !orderLineItemVOs.isEmpty()){
			for(int i = 0; i < orderLineItemVOs.size(); i++){
				LineItemVO lineItemVO = orderLineItemVOs.get(i); 
				orderTable.getModel().setValueAt(lineItemVO.getId(), i,  0);
				System.out.println("setting value "+lineItemVO.getId() +" @ index "+i);
			}
		}
		
		
	}
	
	public class InvoiceItemListener implements ActionListener{

		JPanel parentPanel;
		
		public InvoiceItemListener(JPanel parentPanel){
			this.parentPanel = parentPanel;
		}
		
		
	
		private List<LiteComboModel> retrieveSelectedUnit(Object[] row){
			List<LiteComboModel> comboModels = new ArrayList<LiteComboModel>();
			comboModels.add((LiteComboModel)row[4]);
			return comboModels;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			
			boolean success = saveOrder(false);
			if(!success){
				return;
			}
			
			List<LineItemVO> selectedLineItems = new ArrayList<LineItemVO>();
			Map<Integer, List<LiteComboModel>> unitModelMap = new HashMap<Integer, List<LiteComboModel>>();
			int rows = orderTable.getModel().getRowCount();
			int rowsAdded = 0;
			for(int i=0; i < rows; i++){
				Boolean valueObject = (Boolean)orderTable.getModel().getValueAt(i, 1);
				if(Boolean.TRUE.equals(valueObject)){
					Object[] row = retrieveRow((OrderItemModel)orderTable.getModel(), i);
					LineItemVO lineItemVO = constructLineItemVO(row, true);
					
					if(lineItemVO != null){
						//always set the id as zero - as this needs to be transfered to invoice form 
						lineItemVO.setId(0);
						
						selectedLineItems.add(lineItemVO);
						//unitModelMap.put(rowsAdded, unitCellEditor.getModelValue(i));
						unitModelMap.put(rowsAdded, retrieveSelectedUnit(row));
						rowsAdded++;
					}
				}
			}
			
			success = orderValidator.validateToBeInvoicedItems(selectedLineItems);
			if(!success){
				return;
			}
			
			parentPanel.removeAll();
			//set order id
			JLabel label = (JLabel)myPanelTab.getComponent(0);
			
			String orderName = purchase ? "Purchase Order: " : "Order: ";
			orderName += orderFormModel.getOrderId();
			label.setName(orderName);
			parentPanel.setName(orderName);
			label.setText(orderName);
			
			label.repaint();
			myPanelTab.repaint();
						
			InvoiceFormModel invFormModel = constructInvoiceModel(selectedLineItems);
			JPanel invoiceForm = new InvoiceForm(invFormModel, constructInvoiceDSModel(unitModelMap));
			parentPanel.add(invoiceForm);
			parentPanel.repaint();			
		}
		
	}
	
	private InvoiceFormModel constructInvoiceModel(List<LineItemVO> selectedLineItemVOs){
		long orderId = isNullOrEmpty(FIELD_ORDER_ID.getText()) ? 0 : Long.valueOf(FIELD_ORDER_ID.getText());
		Date orderDate = FIELD_ORDER_DATE.getDate();
		/*try {
			orderDate = new SimpleDateFormat("dd-MM-yyyy").parse(FIELD_ORDER_DATE.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		String partyOrderId = FIELD_PARTY_ORDER_ID.getText();
		Date partyOrderDate = FIELD_PARTY_ORDER_DATE.getDate();
		/*try {
			partyOrderDate = new SimpleDateFormat("dd-MM-yyyy").parse(FIELD_PARTY_ORDER_DATE.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		
		
		long customerId = ((LiteComboModel)FIELD_ORDER_CUSTOMER.getSelectedItem()).getId();
		
		
		InvoiceFormModel formModel = new InvoiceFormModel(purchase);
		formModel.setInvoiceDate(new Date());
		formModel.setLineItemLs(selectedLineItemVOs);
		formModel.setOrderId(orderId);
		formModel.setOrderDate(orderDate);
		formModel.setPartyOrderId(partyOrderId);
		formModel.setPartyOrderDate(partyOrderDate);
		formModel.setCustomerId(customerId);
		
		return formModel;
	}
	
	private List<ProductVO> getOrderedProducts(){
		
		List<Long> productIds = new ArrayList<Long>();
		int rows = orderTable.getModel().getRowCount();
		for(int i=0; i < rows; i++){
			LiteComboModel productComboBox = (LiteComboModel)orderTable.getModel().getValueAt(i, 2);
			if(productComboBox.getId() > 0){
				productIds.add(productComboBox.getId());
			}
		}	
		
		return extractOrderedProducts(productIds);
	}
	
	
	private Map<Long, List<Long>> orderedProductUnitMap(){
		Map<Long, List<Long>> productUnits = new HashMap<Long, List<Long>>();
		
		int rows = orderTable.getModel().getRowCount();
		for(int i=0; i < rows; i++){
			LiteComboModel productComboBox = (LiteComboModel)orderTable.getModel().getValueAt(i, 2);
			if(productComboBox.getId() > 0){
				Long selectedProductId = productComboBox.getId();
				LiteComboModel productUnitComboBox = (LiteComboModel)orderTable.getModel().getValueAt(i, 4);
				Long selectedProductUnitId = productUnitComboBox.getId();
				
				List<Long> unitIds = productUnits.get(selectedProductId);
				if(unitIds == null){
					unitIds = new ArrayList<Long>();
					productUnits.put(selectedProductId, unitIds);
				}
				
				unitIds.add(selectedProductUnitId);
			}
		}	
		return productUnits;
	}
	
	
	private Map<Long, Map<Long, Double>> orderedProductUnitPriceMap(){
		Map<Long, Map<Long, Double>> productUnitPrices = new HashMap<Long, Map<Long,Double>>();
		
		int rows = orderTable.getModel().getRowCount();
		for(int i=0; i < rows; i++){
			LiteComboModel productComboBox = (LiteComboModel)orderTable.getModel().getValueAt(i, 2);
			if(productComboBox.getId() > 0){
				Long selectedProductId = productComboBox.getId();
				
				LiteComboModel productUnitComboBox = (LiteComboModel)orderTable.getModel().getValueAt(i, 4);
				Long selectedProductUnitId = productUnitComboBox.getId();
				
				Map<Long, Double> unitPrice = productUnitPrices.get(selectedProductId);
				if(unitPrice == null){
					unitPrice = new HashMap<Long, Double>();
					productUnitPrices.put(selectedProductId, unitPrice);
				}
				
				Double price = unitPrice.get(selectedProductUnitId);
				Object selectedPriceObj = orderTable.getModel().getValueAt(i, 5);
				Double selectedPrice = new Double(selectedPriceObj.toString());
				
				if(price == null || price != selectedPrice){
					unitPrice.put(selectedProductUnitId, selectedPrice);
				}			
			}
		}	
		return productUnitPrices;
	}
	
	
	private void removeUnorderedUnits(List<ProductVO> orderedProducts){
		Map<Long, Map<Long, Double>> productUnitPriceMap = orderedProductUnitPriceMap();
		removeUnorderedUnitPrice(orderedProducts, productUnitPriceMap);
	}
	
	private void removeUnorderedUnitPrice(List<ProductVO> orderedProducts, 
			Map<Long, Map<Long, Double>> productUnitPriceMap){
				
		for(ProductVO productVO :  orderedProducts){
			Map<Long, Double> unitPriceMap = productUnitPriceMap.get(productVO.getProductId());
			List<ProductUnitVO> supportedUnitVOs = new ArrayList<ProductUnitVO>();
			
			if(unitPriceMap != null && !unitPriceMap.isEmpty()){
				productVO.setOrderedPrice(unitPriceMap);
				
				List<ProductUnitVO> dbProductUnitVOs = productVO.getProductUnitVOs();
				for(ProductUnitVO productUnitVO : dbProductUnitVOs){
					if(unitPriceMap.get(productUnitVO.getUnitId()) != null){
						supportedUnitVOs.add(productUnitVO);
					}
					
				}
			}
			productVO.setProductUnitVOs(supportedUnitVOs);
		}
	}
	
	private void removeUnorderedUnits(List<ProductVO> orderedProducts, 
			Map<Long, List<Long>> orderedProductUnits){
		for(ProductVO productVO :  orderedProducts){
			List<Long> orderedUnitIds = orderedProductUnits.get(productVO.getProductId());
			List<ProductUnitVO> supportedUnitVOs = new ArrayList<ProductUnitVO>();
			
			if(orderedUnitIds != null && !orderedUnitIds.isEmpty()){
				List<ProductUnitVO> dbProductUnitVOs = productVO.getProductUnitVOs();
				for(ProductUnitVO productUnitVO : dbProductUnitVOs){
					if(orderedUnitIds.contains(productUnitVO.getUnitId())){
						supportedUnitVOs.add(productUnitVO);
					}
				}
			}
			productVO.setProductUnitVOs(supportedUnitVOs);
		}
	}
	
	private List<ProductVO> extractOrderedProducts(List<Long> productIds){
		List<ProductVO> productVOs = ServiceClient.getInstance().retrieveProducts(productIds);
		removeUnorderedUnits(productVOs);
		
		return productVOs;
	}
	
	
	
	private List<CustomerVO> getOrderedCustomer(){
		List<CustomerVO> customerVOs = new ArrayList<CustomerVO>();
		
		LiteComboModel selectedCustomerModel = (LiteComboModel)FIELD_ORDER_CUSTOMER.getSelectedItem();
		CustomerVO selectedCustomer = new CustomerVO(selectedCustomerModel.getId(), selectedCustomerModel.getName());
		customerVOs.add(selectedCustomer);
		
		return customerVOs;
	}
	
	private InvoiceFormDSModel constructInvoiceDSModel(Map<Integer, List<LiteComboModel>> unitModelMap){
		InvoiceFormDSModel invoiceFormDSModel = new InvoiceFormDSModel();
		invoiceFormDSModel.setProductVOs(getOrderedProducts());
		invoiceFormDSModel.setCustomers(getOrderedCustomer());
		invoiceFormDSModel.setClientDefaultTin("100000");
		invoiceFormDSModel.setUnitModelMap(unitModelMap);
		
		List<ChargeVO> chargeVOs = ServiceClient.getInstance().retrieveAllCharges();
		invoiceFormDSModel.setChargeVOs(chargeVOs);
		
		return invoiceFormDSModel;
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
	
	
	

	public JPanel getMyPanelTab() {
		return myPanelTab;
	}


	public void setMyPanelTab(JPanel panel) {
		this.myPanelTab = panel;		
	}
	
	
}
