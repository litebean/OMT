package com.litetech.omt.ui.comp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ListDataListener;
import javax.swing.table.TableCellRenderer;


import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.ui.comp.editor.LiteEditableComboBoxEditor;
import com.litetech.omt.ui.comp.listener.CloseTabListener;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.model.LiteComboBoxModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.OrderFormModel;
import com.litetech.omt.ui.model.SearchFormModel;
import com.litetech.omt.ui.model.SearchOrderResultModel;
import com.litetech.omt.ui.model.ds.OrderFormDSModel;
import com.litetech.omt.ui.model.ds.SearchFormDSModel;
import com.litetech.omt.ui.model.mapper.OrderModelVOMapper;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.SearchOrderRequestVO;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.UnitVO;

import static com.litetech.omt.ui.model.SearchOrderResultModel.header;

public class OrderSearchFrame extends JPanel implements PanelTabComp{
	
	private JPanel searchPanel;
	private JPanel resultPanel;
	private JPanel displayPanel;
	private SearchFormModel searchFormModel;
	private SearchFormDSModel searchFormDSModel;
		
	private JTextField FIELD_ORDER_ID;
	private OMTCalendar FIELD_ORDER_DATE;
	private JComboBox FIELD_ORDER_STATUS;
	private JComboBox FIELD_ORDER_CUSTOMER;
	
	
	private OrderModelVOMapper orderModelVOMapper;
	private boolean purchase;

	private JPanel myPanel;
	
	@Override
	public void setMyPanelTab(JPanel myPanel) {
		this.myPanel = myPanel;	
	}


	@Override
	public JPanel getMyPanelTab() {
		return myPanel;
	}
	
	public OrderSearchFrame(JPanel displayPanel, SearchFormModel searchFormModel,
			SearchFormDSModel searchFormDSModel, boolean purchase){
		this.displayPanel = displayPanel;
		this.searchFormModel = searchFormModel;
		this.searchFormDSModel = searchFormDSModel;
		this.purchase = purchase;
		this.orderModelVOMapper = new OrderModelVOMapper();
		
		setBackground(Color.WHITE);
		setPreferredSize(AppContext.getInstance().getBaseFormPanelDimension());
		add(constructSearchPanel(), BorderLayout.NORTH);
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
		
	}
	
	
	private void populateSearchFields(JPanel searchDataPanel){
		GridLayout gridLayout = new GridLayout(2, 5, 20, 5);
		searchDataPanel.setLayout(gridLayout);
		
		JLabel orderId = new JLabel("Order ID", 10);
		String orderIdStr = (searchFormModel.getOrderId() != null && searchFormModel.getOrderId() > 0 ) ? 
				String.valueOf(searchFormModel.getOrderId()) : "";  
		FIELD_ORDER_ID = new JTextField(orderIdStr, 15);
		
		searchDataPanel.add(orderId);
		searchDataPanel.add(FIELD_ORDER_ID);
		
		JLabel orderDate = new JLabel("Order Date",10);
		FIELD_ORDER_DATE = new OMTCalendar(searchFormModel.getOrderDate());
		
		searchDataPanel.add(orderDate);
		searchDataPanel.add(FIELD_ORDER_DATE);
		searchDataPanel.add(Box.createHorizontalStrut(50));
		
		
		JLabel customer = new JLabel("Customer", 10);
		Vector<LiteComboModel> customerList = new Vector<LiteComboModel>();
		customerList.add(new LiteComboModel(-1, ""));
		List<CustomerVO> customerVOLs = searchFormDSModel.getCustomers();
		for(CustomerVO customerVO : customerVOLs){
			customerList.add(new LiteComboModel(customerVO.getId(), customerVO.getName()));
		}		
		
		FIELD_ORDER_CUSTOMER = new JComboBox(customerList);
		
		LiteComboModel selectedCustomer = new LiteComboModel(searchFormModel.getSelectedCustomerId());
		FIELD_ORDER_CUSTOMER.setRenderer(new LiteComboModelRenderer());
		FIELD_ORDER_CUSTOMER.setEditor(new LiteEditableComboBoxEditor(FIELD_ORDER_CUSTOMER, customerList));
				
		searchDataPanel.add(customer);
		searchDataPanel.add(FIELD_ORDER_CUSTOMER);
		
		JLabel orderStatus = new JLabel("Order Status",10);
		
		Vector<LiteComboModel> orderStatusList = new Vector<LiteComboModel>();
		orderStatusList.add(new LiteComboModel(-1, "-"));
		OrderStatusEnum[] orderStatuses = OrderStatusEnum.values();
		for(OrderStatusEnum orderStatusEnum : orderStatuses){
			orderStatusList.add(new LiteComboModel(orderStatusEnum.getId(), orderStatusEnum.getName()));
		}
		FIELD_ORDER_STATUS = new JComboBox(orderStatusList);
		//FIELD_ORDER_STATUS.setSelectedItem(new LiteComboModel(searchFormModel.getSelectedStatusId()));
		FIELD_ORDER_STATUS.setRenderer(new LiteComboModelRenderer());
		FIELD_ORDER_STATUS.setPreferredSize(new Dimension(125, 20));
		
		searchDataPanel.add(orderStatus);
		searchDataPanel.add(FIELD_ORDER_STATUS);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new OrderSearchListener());
		searchDataPanel.add(searchButton);
	}
	
	
	public JPanel constructResultPanel(final SearchOrderResultModel searchOrderResultModel){
		
		//remove existing panel
		if(resultPanel != null){
			searchPanel.remove(resultPanel);
		}
		
		resultPanel = new JPanel();
		//resultPanel.setBackground(Color.GREEN);
		int height = AppContext.getInstance().getWorkAreaHeight() - 100;
		resultPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 
				height));
		
		JTable table = new JTable(searchOrderResultModel.getDefaultTableModel());
		table.setRowHeight(25);
		
		table.setPreferredScrollableViewportSize(
				new Dimension(AppContext.getInstance().getWorkAreaWidth()-40, 400));
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
        	    	String orderId = searchOrderResultModel.getCell(row, 0);
        	    	//String actionCommand = button.getActionCommand();
        	    	populateDisplayTab(orderId);
        	   // }
        	    // do some action if appropriate column
        	    
        	  }
        	});
		
		resultPanel.add(scrollPane);
		
		searchPanel.add(resultPanel);
		ComponentRegistry.instance().repaintComponents();
		return resultPanel;
	}
	
	
	public void populateSearch(SearchOrderResultModel searchOrderResultModel){
		constructResultPanel(searchOrderResultModel);
	}	
	
	
	private SearchOrderRequestVO constructSearchRequestVO(){
		
		SearchOrderRequestVO searchOrderRequestVO = new SearchOrderRequestVO(purchase);
		if(FIELD_ORDER_ID.getText() != null && !FIELD_ORDER_ID.getText().equals("")){
			searchOrderRequestVO.setOrderId(Long.valueOf(FIELD_ORDER_ID.getText()));
		}
		if(FIELD_ORDER_CUSTOMER.getSelectedItem() != null){
			searchOrderRequestVO.setCustomerId(((LiteComboModel)FIELD_ORDER_CUSTOMER.getSelectedItem()).getId());
		}
		
		if(FIELD_ORDER_STATUS.getSelectedItem() != null){
			LiteComboModel orderStatus = (LiteComboModel)FIELD_ORDER_STATUS.getSelectedItem();
			if(orderStatus.getId() > 0){
				searchOrderRequestVO.setOrderStatusId(OrderStatusEnum.getById((int)(orderStatus.getId())));
			}
		}
		
		searchOrderRequestVO.setOrderDate(FIELD_ORDER_DATE.getDate());
			
		return searchOrderRequestVO;
	}
	 
	public class OrderSearchListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			List<OrderVO> orderVOs = ServiceClient.getInstance().searchOrder(constructSearchRequestVO());
			searchFormModel.setResult(orderVOs);
			searchFormModel.setOrderedCustomerMap(searchFormDSModel.getCustomerNameMap());
			
			populateSearch(new SearchOrderResultModel(searchFormModel.getResultRows()));
		}
		
	}
	
	
	
	public class SearchOrderCellRenderer implements TableCellRenderer{

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JButton underLinedText = new JButton("<html><u>" + value + "</u></html>");
			underLinedText.setOpaque(false);
			underLinedText.setBorder(BorderFactory.createEmptyBorder());
			underLinedText.setActionCommand((String)value);
			underLinedText.setCursor(new Cursor(Cursor.HAND_CURSOR));
			return underLinedText;
		}
		
	}
	
	
	
	public void populateDisplayTab(String command){
		String title = purchase ? "Puchase Order: "+command : "Order: "+command;
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
			JComponent comp =  new  OrderForm(constructOrderComp(Long.valueOf(command)), constructFormDSModel(), displayPanel);  
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
		
	
	private OrderFormModel constructOrderComp(final long orderId){
		OrderVO orderVO = ServiceClient.getInstance().retrieveOrder(orderId, purchase);
		OrderFormModel orderFormModel = orderModelVOMapper.mapOrderVOToModel(orderVO);
		//populateMockData(orderFormModel);
			
		return orderFormModel;
	}
	
	
	
	
	private OrderFormDSModel constructFormDSModel(){
		OrderFormDSModel orderFormDSModel = new OrderFormDSModel();
		List<CustomerVO> customerLs = ServiceClient.getInstance().retrieveAllCustomers();
		orderFormDSModel.setCustomers(customerLs);
		
		List<ProductVO> productVOs = ServiceClient.getInstance().retrieveAllProducts();
		orderFormDSModel.setProductVOs(productVOs);
		
		return orderFormDSModel;
	}
	
	
	
	
	
}
