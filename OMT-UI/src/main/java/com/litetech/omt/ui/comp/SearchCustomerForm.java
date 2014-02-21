package com.litetech.omt.ui.comp;

import static com.litetech.omt.ui.model.SearchResultUserModel.header;

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
import java.util.List;
import java.util.Vector;

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
import com.litetech.omt.constant.PaymentStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.ui.comp.PaymentSearchFrame.PaymentSearchListener;
import com.litetech.omt.ui.comp.editor.LiteEditableComboBoxEditor;
import com.litetech.omt.ui.comp.listener.CloseTabListener;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.model.CustomerFormModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.PaymentSearchFormModel;
import com.litetech.omt.ui.model.SearchResultPaymentModel;
import com.litetech.omt.ui.model.SearchResultUserModel;
import com.litetech.omt.util.StringUtil;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.SearchUserRequestVO;

public class SearchCustomerForm extends JPanel implements PanelTabComp{
	
	
	private JPanel searchPanel;
	private JPanel resultPanel;
	
	private JTextField FIELD_CUSTOMER_ID;
	private JTextField FIELD_CUSTOMER_NAME;
	private JTextField FIELD_CUSTOMER_TIN;
	private JTextField FIELD_ADD_CONTACT_NAME;
	private JTextField FIELD_ADD_CITY;
	private JTextField FIELD_ADD_COUNTRY;
	private JTextField FIELD_ADD_STATE;
	private JTextField FIELD_ADD_PINCODE;
	private JTextField FIELD_ADD_PHONE;
	
	private JPanel myPanel;
	@Override
	public void setMyPanelTab(JPanel myPanel) {
		this.myPanel = myPanel;	
	}


	@Override
	public JPanel getMyPanelTab() {
		return myPanel;
	}
	
	public SearchCustomerForm(){
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
		searchDataPanel.setBackground(Color.WHITE);
		searchDataPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth() - 20, 75));
		
		populateSearchFields(searchDataPanel);
		
		searchPanel.add(searchDataPanel);
		
		JPanel searchControlPanel = new JPanel();
		searchControlPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth() - 20, 30));
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new SearchListener());
		searchControlPanel.add(searchButton);
		searchPanel.add(searchControlPanel);
	}
	
	
	private void populateSearchFields(JPanel searchDataPanel){
		GridLayout gridLayout = new GridLayout(3, 6, 1, 1);
		searchDataPanel.setLayout(gridLayout);	
		
		JLabel customerId = new JLabel("Customer ID");
		FIELD_CUSTOMER_ID = new JTextField("");
		
		JLabel customerName = new JLabel("Customer");
		FIELD_CUSTOMER_NAME = new JTextField("");
		
		JLabel tinNo = new JLabel("Tin No");
		FIELD_CUSTOMER_TIN = new JTextField("");
		
		//first row
		searchDataPanel.add(customerId);
		searchDataPanel.add(FIELD_CUSTOMER_ID);
		searchDataPanel.add(customerName);
		searchDataPanel.add(FIELD_CUSTOMER_NAME);
		searchDataPanel.add(tinNo);
		searchDataPanel.add(FIELD_CUSTOMER_TIN);

		JLabel contactName = new JLabel("Contact Person");
		FIELD_ADD_CONTACT_NAME = new JTextField("");
		
		JLabel city = new JLabel("City");
		FIELD_ADD_CITY = new JTextField("");
		
		JLabel state = new JLabel("State");
		FIELD_ADD_STATE = new JTextField("");
		
		//second row
		searchDataPanel.add(contactName);
		searchDataPanel.add(FIELD_ADD_CONTACT_NAME);
		searchDataPanel.add(city);
		searchDataPanel.add(FIELD_ADD_CITY);
		searchDataPanel.add(state);
		searchDataPanel.add(FIELD_ADD_STATE);
	
		JLabel country = new JLabel("Country");
		FIELD_ADD_COUNTRY = new JTextField("");
		
		JLabel pincode = new JLabel("Pincode");
		FIELD_ADD_PINCODE = new JTextField("");
		
		JLabel phone = new JLabel("Phone");
		FIELD_ADD_PHONE = new JTextField("");
				
		//second row
		searchDataPanel.add(country);
		searchDataPanel.add(FIELD_ADD_COUNTRY);
		searchDataPanel.add(pincode);
		searchDataPanel.add(FIELD_ADD_PINCODE);
		searchDataPanel.add(phone);
		searchDataPanel.add(FIELD_ADD_PHONE);	
	}
	
	
	
	public class SearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SearchUserRequestVO searchUserRequestVO = constructSearchRequest();
			List<CustomerVO> customerVOs = ServiceClient.getInstance().searchCustomers(searchUserRequestVO);
			System.out.println("no of customers " + customerVOs.size());
			populateSearch(customerVOs);
		}
		
	}
	
	private SearchUserRequestVO constructSearchRequest(){
		SearchUserRequestVO requestVO = new SearchUserRequestVO();
		
		if(!StringUtil.isNullOrEmpty(FIELD_CUSTOMER_ID.getText())){
			requestVO.setCustomerId(new Long(FIELD_CUSTOMER_ID.getText()));
		}
		
		if(!StringUtil.isNullOrEmpty(FIELD_CUSTOMER_NAME.getText())){
			requestVO.setCustomerName(FIELD_CUSTOMER_NAME.getText());
		}
		
		if(!StringUtil.isNullOrEmpty(FIELD_CUSTOMER_TIN.getText())){
			requestVO.setTinNo(FIELD_CUSTOMER_TIN.getText());
		}
		
		if(!StringUtil.isNullOrEmpty(FIELD_ADD_CONTACT_NAME.getText())){
			requestVO.setContactName(FIELD_ADD_CONTACT_NAME.getText());
		}
		
		if(!StringUtil.isNullOrEmpty(FIELD_ADD_CITY.getText())){
			requestVO.setCity(FIELD_ADD_CITY.getText());
		}

		if(!StringUtil.isNullOrEmpty(FIELD_ADD_COUNTRY.getText())){
			requestVO.setCountry(FIELD_ADD_COUNTRY.getText());
		}
		
		if(!StringUtil.isNullOrEmpty(FIELD_ADD_STATE.getText())){
			requestVO.setState(FIELD_ADD_STATE.getText());
		}
		
		if(!StringUtil.isNullOrEmpty(FIELD_ADD_PINCODE.getText())){
			requestVO.setPincode(FIELD_ADD_PINCODE.getText());
		}
		if(!StringUtil.isNullOrEmpty(FIELD_ADD_PHONE.getText())){
			requestVO.setPhone(FIELD_ADD_PHONE.getText());
		}
		
		return requestVO;
	}
	
	
	public void populateSearch(List<CustomerVO> customerVOs){
		if(resultPanel != null){
			searchPanel.remove(resultPanel);
		}
		
		resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 400));
		
		final SearchResultUserModel userModel = new SearchResultUserModel(customerVOs);
		JTable table = new JTable(userModel.getDefaultTableModel());
		table.setRowHeight(25);
		
		table.setPreferredScrollableViewportSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-30, 350));
        table.setFillsViewportHeight(true);
       // table.getColumn("Order Id").setCellRenderer(new SearchOrderCellRenderer());
        table.getColumn(header[0]).setPreferredWidth(100);
        table.getColumn(header[1]).setPreferredWidth(200);
        table.getColumn(header[2]).setPreferredWidth(100);
        table.getColumn(header[3]).setPreferredWidth(100);
        table.getColumn(header[4]).setPreferredWidth(100);
        table.getColumn(header[5]).setPreferredWidth(100);
        table.getColumn(header[6]).setPreferredWidth(100);
        table.getColumn(header[7]).setPreferredWidth(100);
        table.getColumn(header[8]).setPreferredWidth(200);
//      Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	//	table.setColumnSelectionInterval(5, 10);
        table.addMouseListener(new MouseAdapter() {
        	
        	 @Override
        	  public void mouseClicked(MouseEvent e) {
        		 JTable target = (JTable)e.getSource();
         	     int row = target.getSelectedRow();
        		 String transIdStr = userModel.getCell(row, 0).toString();
        		 populateDisplayTab(transIdStr);
        	 }
        });

        resultPanel.add(scrollPane);
		
		searchPanel.add(resultPanel);
		ComponentRegistry.instance().repaintComponents();		
	}
	
	
	public void populateDisplayTab(String command){
		long customerId = Long.valueOf(command);
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
			Icon compIcon = MainMenu.PEOPLE_ICON;
			
			CustomerVO customerVO = ServiceClient.getInstance().retrieveCustomer(customerId);
			
			JComponent comp = new CustomerForm(new CustomerFormModel(customerVO));
			comp.setName(command);
			//comp.setOpaque(true);
			//comp.setBackground(Color.WHITE);
			
			Util.buildCloseTab(command, "Customer: "+command, tabbedPane, comp, compIcon,
					new CloseTabListener(displayPanel, comp));
			//tabbedPane.setSelectedComponent(newContainer);
		}
		
		ComponentRegistry.instance().repaintComponents();
	}

	
}
