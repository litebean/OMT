package com.litetech.omt.ui.comp;

import static com.litetech.omt.ui.model.SearchInvoiceResultModel.header;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.InvoiceStatusEnum;
import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.ui.comp.editor.LiteEditableComboBoxEditor;
import com.litetech.omt.ui.comp.listener.CloseTabListener;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.model.InvoiceFormModel;
import com.litetech.omt.ui.model.InvoiceSearchFormModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.SearchInvoiceResultModel;
import com.litetech.omt.ui.model.SearchOrderResultModel;
import com.litetech.omt.ui.model.ds.InvoiceFormDSModel;
import com.litetech.omt.ui.model.ds.InvoiceSearchFormDSModel;
import com.litetech.omt.ui.model.mapper.InvoiceModelVOMapper;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.SearchInvoiceRequestVO;

public class InvoiceSearchFrame extends JPanel implements PanelTabComp {
	
	private JPanel searchPanel;
	private JPanel resultPanel;
	private JPanel displayPanel;
	private InvoiceSearchFormModel searchFormModel;
	private InvoiceSearchFormDSModel searchFormDSModel;
		
	private JTextField FIELD_INVOICE_ID;
	private OMTCalendar FIELD_INVOICE_DATE;
	private JComboBox FIELD_INVOICE_STATUS;
	private JComboBox FIELD_INVOICE_CUSTOMER;
	
	private boolean purchase;
	
	
	public InvoiceSearchFrame(JPanel displayPanel, InvoiceSearchFormModel searchFormModel,
			InvoiceSearchFormDSModel searchFormDSModel, boolean purchase){
		this.displayPanel = displayPanel;
		this.searchFormModel = searchFormModel;
		this.searchFormDSModel = searchFormDSModel;
		this.purchase = purchase;	
		
		setBackground(Color.WHITE);
		setPreferredSize(AppContext.getInstance().getBaseFormPanelDimension());
		add(constructSearchPanel(), BorderLayout.NORTH);
	}
	
	private JPanel myPanel;
	@Override
	public void setMyPanelTab(JPanel myPanel) {
		this.myPanel = myPanel;	
	}


	@Override
	public JPanel getMyPanelTab() {
		return myPanel;
	}
	
	
	public JPanel constructSearchPanel(){
		searchPanel = new JPanel();
		//searchPanel.setBackground(Color.WHITE);
		searchPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 600));
		searchPanel.setOpaque(true);
		buildSearchComponent(searchPanel);
		return searchPanel;
	}
	
	public void buildSearchComponent(JPanel searchPanel){
		
		JPanel searchDataPanel = new JPanel(); 
		//searchDataPanel.setBackground(Color.WHITE);
		searchDataPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth() - 20, 60));
		
		populateSearchFields(searchDataPanel);		
		searchPanel.add(searchDataPanel);
		
		//build result panel
		
	}
	
	
	private void populateSearchFields(JPanel searchDataPanel){
		GridLayout gridLayout = new GridLayout(2, 5, 20, 5);
		searchDataPanel.setLayout(gridLayout);
		
		JLabel orderId = new JLabel("Invocie ID", 10);
		String orderIdStr = (searchFormModel.getInvoiceId() != null && searchFormModel.getInvoiceId() > 0 ) ? 
				String.valueOf(searchFormModel.getInvoiceId()) : "";  
		FIELD_INVOICE_ID = new JTextField(orderIdStr, 15);
		searchDataPanel.add(orderId);
		searchDataPanel.add(FIELD_INVOICE_ID);
		
		
		JLabel orderDate = new JLabel("Invoice Date",10);
		FIELD_INVOICE_DATE = new OMTCalendar(searchFormModel.getInvoiceDate());
		searchDataPanel.add(orderDate);
		searchDataPanel.add(FIELD_INVOICE_DATE);
		
		searchDataPanel.add(Box.createHorizontalStrut(100));
		
		JLabel customer = new JLabel("Customer", 10);
		Vector<LiteComboModel> customerList = new Vector<LiteComboModel>();
		customerList.add(new LiteComboModel(-1, ""));
		List<CustomerVO> customerVOLs = searchFormDSModel.getCustomers();
		for(CustomerVO customerVO : customerVOLs){
			customerList.add(new LiteComboModel(customerVO.getId(), customerVO.getName()));
		}		
		FIELD_INVOICE_CUSTOMER = new JComboBox(customerList);
		FIELD_INVOICE_CUSTOMER.setRenderer(new LiteComboModelRenderer());
		FIELD_INVOICE_CUSTOMER.setEditor(new LiteEditableComboBoxEditor(FIELD_INVOICE_CUSTOMER, customerList));
		//FIELD_INVOICE_CUSTOMER.setPreferredSize(new Dimension(150, 20));
		
		searchDataPanel.add(customer);
		searchDataPanel.add(FIELD_INVOICE_CUSTOMER);
		
		JLabel orderStatus = new JLabel("Invoice Status",10);
		Vector<LiteComboModel> invoiceStatusList = new Vector<LiteComboModel>();
		invoiceStatusList.add(new LiteComboModel(-1, "-"));
		InvoiceStatusEnum[] invoiceStatuses = InvoiceStatusEnum.values();
		for(InvoiceStatusEnum invoiceStatusEnum : invoiceStatuses){
			invoiceStatusList.add(new LiteComboModel(invoiceStatusEnum.getId(), invoiceStatusEnum.getName()));
		}
		
		
		FIELD_INVOICE_STATUS = new JComboBox(invoiceStatusList);
		FIELD_INVOICE_STATUS.setRenderer(new LiteComboModelRenderer());
		
		searchDataPanel.add(orderStatus);
		searchDataPanel.add(FIELD_INVOICE_STATUS);
		
		
		JButton searchButton = new JButton("Search");
		//searchButton.setPreferredSize(new Dimension(300, 20));		
		searchButton.addActionListener(new InvoiceSearchListener());
		searchDataPanel.add(searchButton);
	}
		
	
	public class InvoiceSearchListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			List<InvoiceVO> invoiceVOs =  ServiceClient.getInstance().searchInvoices(constructInvoiceSearchRequest());
			searchFormModel.setSearchResult(invoiceVOs);
			
			searchFormModel.setCustomerMap(searchFormDSModel.getCustomerNameMap());
			populateSearch(new SearchInvoiceResultModel(searchFormModel.getResultRows()));
			
			/*List<OrderVO> orderVOs = ServiceClient.getInstance().searchOrder(constructSearchRequestVO());
			searchFormModel.setResult(orderVOs);
			Map<Long, String> customers = new HashMap<Long, String>();
			searchFormModel.setOrderedCustomerMap(customers);
			
			populateSearch(new SearchOrderResultModel(searchFormModel.getResultRows()));*/
					
		}
		
	}
	
	
	
	private SearchInvoiceRequestVO constructInvoiceSearchRequest(){
		SearchInvoiceRequestVO invoiceRequestVO = new SearchInvoiceRequestVO(purchase);
		if(FIELD_INVOICE_ID.getText() != null && !FIELD_INVOICE_ID.getText().equals("")){
			invoiceRequestVO.setInvoiceId(Long.valueOf(FIELD_INVOICE_ID.getText()));
		}
		if(FIELD_INVOICE_CUSTOMER.getSelectedItem() != null){
			invoiceRequestVO.setCustomerId(((LiteComboModel)FIELD_INVOICE_CUSTOMER.getSelectedItem()).getId());
		}
		
		if(FIELD_INVOICE_STATUS.getSelectedItem() != null){
			LiteComboModel status = (LiteComboModel)FIELD_INVOICE_STATUS.getSelectedItem();
			if(status.getId() > 0){
				invoiceRequestVO.setInvoiceStatus(InvoiceStatusEnum.getById((int)status.getId()));
			}
		}
		
		invoiceRequestVO.setInvoiceDate(FIELD_INVOICE_DATE.getDate());
		/*if(FIELD_INVOICE_DATE.getText() != null && !FIELD_INVOICE_DATE.getText().equals("")){ 
			try {
				Date invoiceDate = new SimpleDateFormat("dd-MM-yyyy").parse(FIELD_INVOICE_DATE.getText());
				invoiceRequestVO.setInvoiceDate(invoiceDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}*/
		
		return invoiceRequestVO;
	}
	
	public void populateSearch(SearchInvoiceResultModel searchInvoiceResultModel){
		constructResultPanel(searchInvoiceResultModel);
	}
	
	
	public JPanel constructResultPanel(final SearchInvoiceResultModel searchInvoiceResultModel){
		
		//remove existing panel
		if(resultPanel != null){
			searchPanel.remove(resultPanel);
		}
		
		resultPanel = new JPanel();
		//resultPanel.setBackground(Color.GREEN);
		resultPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 400));
		
		JTable table = new JTable(searchInvoiceResultModel.getDefaultTableModel());
		table.setRowHeight(25);
		
		table.setPreferredScrollableViewportSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-40, 375));
        table.setFillsViewportHeight(true);
       // table.getColumn("Order Id").setCellRenderer(new SearchOrderCellRenderer());
        table.getColumn(header[0]).setPreferredWidth(100);
        table.getColumn(header[1]).setPreferredWidth(300);
        table.getColumn(header[2]).setPreferredWidth(100);
        table.getColumn(header[3]).setPreferredWidth(100);
        //table.getColumn(header[4]).setPreferredWidth(100);
//      Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	//	table.setColumnSelectionInterval(5, 10);
        table.addMouseListener(new MouseAdapter() {
        	
        	 @Override
        	  public void mouseClicked(MouseEvent e) {
        	    JTable target = (JTable)e.getSource();
        	    int row = target.getSelectedRow();
        	    int column = target.getSelectedColumn();
        	    
        	   // if(column == 0){
        	    	Object object = e.getSource();
        	    	String invoiceId = searchInvoiceResultModel.getCell(row, 0);
        	    	//String actionCommand = button.getActionCommand();
        	    	populateDisplayTab(invoiceId);
        	   // }
        	    // do some action if appropriate column
        	    
        	  }
        	});
		
		resultPanel.add(scrollPane);
		
		searchPanel.add(resultPanel);
		ComponentRegistry.instance().repaintComponents();
		return resultPanel;
	}

	public void populateDisplayTab(String command){
		
		String title = purchase ? "Puchase Invoice: "+command : "Invoice: "+command;
		OMTTabbedPane tabbedPane = (OMTTabbedPane)displayPanel.getComponent(0);
		Component[] tabComps = tabbedPane.getComponents();
			
		boolean tabAlreadyOpened = false;
		if(tabComps != null){
			for (int i = 0; i < tabComps.length; i++) {
				if(title.equals(tabComps[i].getName())){
					System.out.println("Tab Already openend");
					
					tabAlreadyOpened = true;
					tabbedPane.resetSelectedComponent(tabComps[i]);
					break;
				}
			}
		}
			
		if(!tabAlreadyOpened){
			Icon compIcon = MainMenu.NOTE_ICON;
			
			InvoiceVO invoiceVO = ServiceClient.getInstance().retrieveInvoice(Long.valueOf(command), purchase);
			OrderVO orderVO = ServiceClient.getInstance().retrieveOrder(invoiceVO.getOrderId(), purchase);
			//JComponent comp =  new  OrderForm(constructOrderComp(Long.valueOf(command)), constructFormDSModel(), displayPanel);
			JComponent comp =  new InvoiceForm(constructInvoiceModel(invoiceVO, orderVO, purchase), 
					constructInvocieFormDSModel(invoiceVO, orderVO));
			comp.setName(title);
			//comp.setOpaque(true);
			//comp.setBackground(Color.WHITE);
			
			Util.buildCloseTab(command, title, tabbedPane, comp, compIcon,
					new CloseTabListener(displayPanel, comp));
			//tabbedPane.setSelectedComponent(newContainer);
		}
		
		ComponentRegistry.instance().repaintComponents();
	}
	
	
	private InvoiceFormModel constructInvoiceModel(final InvoiceVO invoiceVO, final OrderVO orderVO, boolean purchase){
		//InvoiceVO invoiceVO = ServiceClient.getInstance().retrieveInvoice(invoiceId);
		
		InvoiceFormModel invoiceFormModel = new InvoiceFormModel(purchase);
		invoiceFormModel.setInvoiceId(invoiceVO.getId());
		invoiceFormModel.setInvoiceDate(invoiceVO.getInvoiceDate());
		invoiceFormModel.setCustomerId(orderVO.getCustomerId());
		invoiceFormModel.setStatus(invoiceVO.getInvoiceStatus());
		
		invoiceFormModel.setOrderId(invoiceVO.getOrderId());
		invoiceFormModel.setOrderDate(orderVO.getOrderDate());
		invoiceFormModel.setPartyOrderId(orderVO.getPartyOrderId());
		invoiceFormModel.setPartyOrderDate(orderVO.getPartyOrderDate());
		invoiceFormModel.setDcNo(invoiceVO.getDcNo());
		invoiceFormModel.setDcDate(invoiceVO.getDcDate());
		invoiceFormModel.setDescription(invoiceVO.getInvDesc());
		invoiceFormModel.setLineItemLs(invoiceVO.getInvoiceLineItems());
		invoiceFormModel.setInvoiceChargeVOs(invoiceVO.getInvoiceCharges());
		
		TransactionTypeEnum transType = purchase ? TransactionTypeEnum.DEBIT : TransactionTypeEnum.CREDIT;
		
		List<InvoicePaymentVO> invoicePaymentVOs = ServiceClient.getInstance().retrieveInvoicePayments(invoiceVO.getId(),
				transType);
		invoiceFormModel.setInvoicePaymentVOs(invoicePaymentVOs);
		
		invoiceFormModel.setAmountPaid(invoiceVO.getPaidAmount());
		
		return invoiceFormModel;
	}
	
	
	
	private InvoiceFormDSModel constructInvocieFormDSModel(final InvoiceVO invoiceVO, final OrderVO orderVO){
		InvoiceFormDSModel invoiceFormDSModel = new InvoiceFormDSModel();
		
		List<ChargeVO> chargeVOs = ServiceClient.getInstance().retrieveAllCharges();
		invoiceFormDSModel.setChargeVOs(chargeVOs);
		
		List<CustomerVO> customerLs = new ArrayList<CustomerVO>();
		CustomerVO customerVO = ServiceClient.getInstance().retrieveCustomer(orderVO.getCustomerId());
		customerLs.add(customerVO);
		invoiceFormDSModel.setCustomers(customerLs);
		
		List<LineItemVO> orderLineItemVOs = orderVO.getOrderLineItems();
		List<ProductVO> productVOs = new ArrayList<ProductVO>();
		Map<Long, List<Long>> orderedProductUnitMap = new TreeMap<Long, List<Long>>();
		Map<Long, Map<Long, Double>> productUnitPriceMap = new HashMap<Long, Map<Long,Double>>();
		
		for(LineItemVO orderLineItemVO : orderLineItemVOs){
			ProductVO productVO = orderLineItemVO.getProductVO();
			List<Long> unitIds = orderedProductUnitMap.get(productVO.getProductId());
			if(unitIds == null){
				unitIds = new ArrayList<Long>();
				orderedProductUnitMap.put(productVO.getProductId(), unitIds);
			}		
			
			unitIds.add(orderLineItemVO.getUnitVO().getUnitId());
			
			Map<Long, Double> orderedUnitPrice = productUnitPriceMap.get(productVO.getProductId());
			if(orderedUnitPrice == null){
				orderedUnitPrice = new HashMap<Long, Double>();
				productUnitPriceMap.put(productVO.getProductId(), orderedUnitPrice);
			}
			
			orderedUnitPrice.put(orderLineItemVO.getUnitVO().getUnitId(), orderLineItemVO.getPrice());
			productVO.setOrderedPrice(orderedUnitPrice);
			
			if(!productVOs.contains(productVO)){
				productVOs.add(productVO);
			}
		}
		
		removeUnorderedUnits(productVOs, orderedProductUnitMap);
		invoiceFormDSModel.setProductVOs(productVOs);
		
		//now we have product with only selected units
		Map<Integer, List<LiteComboModel>> defaultDropDownMap = new TreeMap<Integer, List<LiteComboModel>>();
		List<LineItemVO> invoiceLineItemVOs = invoiceVO.getInvoiceLineItems();
		
		for(int row = 0; row < invoiceLineItemVOs.size(); row++){
			LineItemVO lineItemVO = invoiceLineItemVOs.get(row);
			ProductVO productVO = getProductVO(productVOs, lineItemVO.getProductVO().getProductId());
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
	
	private ProductVO getProductVO(List<ProductVO> productVOs, long productId){
		for(ProductVO productVO : productVOs){
			if(productId == productVO.getProductId()){
				return productVO;
			}
		}
		
		return new ProductVO(0, "Un ordered Product");
		
	}
	
	private void removeUnorderedUnits(List<ProductVO> orderedProducts, 
			Map<Long, List<Long>> supportedProductUnits){
		for(ProductVO productVO :  orderedProducts){
			List<Long> unitIds = supportedProductUnits.get(productVO.getProductId());
			List<ProductUnitVO> selectedUnitVOs = new ArrayList<ProductUnitVO>();
			
			if(unitIds != null && !unitIds.isEmpty()){
				List<ProductUnitVO> dbProductUnitVOs = productVO.getProductUnitVOs();
				for(ProductUnitVO productUnitVO : dbProductUnitVOs){
					if(unitIds.contains(productUnitVO.getUnitId())){
						selectedUnitVOs.add(productUnitVO);
					}
				}
			}
			productVO.setProductUnitVOs(selectedUnitVOs);
		}
	}




	
}
