package com.litetech.omt.ui.comp;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.InvoiceStatusEnum;
import com.litetech.omt.ui.comp.editor.LiteEditableComboBoxEditor;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.model.CustomerFormModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.CustomerVO;

public class CustomerForm extends JPanel implements PanelTabComp{
	
	private JPanel rootPanel;
	
	private JTextField FIELD_CUSTOMER_ID;
	private JTextField FIELD_CUSTOMER_NAME;
	private JTextField FIELD_TIN_NO;
	private JTextField FIELD_VENDOR_CODE;
	private JTextField FIELD_CST_NO;
	
	private JTextField FIELD_ADD1_LINE1;
	private JTextField FIELD_ADD1_LINE2;
	private JTextField FIELD_ADD1_LINE3;
	private JTextField FIELD_ADD1_CITY;
	private JTextField FIELD_ADD1_STATE;
	private JTextField FIELD_ADD1_COUNTRY;
	private JTextField FIELD_ADD1_PINCODE;
	private JTextField FIELD_ADD1_CONT_NAME;
	private JTextField FIELD_ADD1_PHONE1;
	private JTextField FIELD_ADD1_PHONE2;
	
	private JCheckBox FIELD_ADD2_SAME_CHK;
	
	private JTextField FIELD_ADD2_LINE1;
	private JTextField FIELD_ADD2_LINE2;
	private JTextField FIELD_ADD2_LINE3;
	private JTextField FIELD_ADD2_CITY;
	private JTextField FIELD_ADD2_STATE;
	private JTextField FIELD_ADD2_COUNTRY;
	private JTextField FIELD_ADD2_PINCODE;
	private JTextField FIELD_ADD2_CONT_NAME;
	private JTextField FIELD_ADD2_PHONE1;
	private JTextField FIELD_ADD2_PHONE2;
	
	private long address1Id;
	private long address2Id;
	
	JPanel myPanelTab;
	@Override
	public void setMyPanelTab(JPanel myPanelTab) {
		this.myPanelTab = myPanelTab;
	}

	@Override
	public JPanel getMyPanelTab() {
		return myPanelTab;
	}
	
	private CustomerFormModel customerFormModel;
	
	public CustomerForm(CustomerFormModel customerFormModel){
		
		this.customerFormModel = customerFormModel;
		this.address1Id = customerFormModel.getAdd1Id();
		this.address2Id = customerFormModel.getAdd2Id();
		
		rootPanel = new JPanel();
		rootPanel.setBackground(Color.WHITE);
		rootPanel.setPreferredSize(AppContext.getInstance().getBaseFormPanelDimension());
			
		rootPanel.add(constructCutomerMetaDataPanel());
		rootPanel.add(constructAddressPanel());
		rootPanel.add(constructControlPanel());
		add(rootPanel);
	}
	
	public JPanel constructCutomerMetaDataPanel(){
		JPanel customerMetaData = new JPanel();
		customerMetaData.setBackground(Color.WHITE);
		customerMetaData.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 25));
		
		populateMetaDataFields(customerMetaData);
		return customerMetaData;
	}
	
	
	public void populateMetaDataFields(JPanel customerMetaData){
		GridLayout gridLayout = new GridLayout(1, 10, 2, 2);
		customerMetaData.setLayout(gridLayout);
		
		
		JLabel customerId = new JLabel("Customer ID", 10);
		FIELD_CUSTOMER_ID = new JTextField(String.valueOf(customerFormModel.getCustomerId()), 10);
		FIELD_CUSTOMER_ID.setEditable(false);
		
		customerMetaData.add(customerId);
		customerMetaData.add(FIELD_CUSTOMER_ID);
		
		JLabel customerName = new JLabel("Customer Name",10);
		FIELD_CUSTOMER_NAME = new JTextField(customerFormModel.getCustomerName(), 20);
		
		customerMetaData.add(customerName);
		customerMetaData.add(FIELD_CUSTOMER_NAME);
					
		JLabel tinNo = new JLabel("TIN No", 10);
		FIELD_TIN_NO = new JTextField(customerFormModel.getTinNo());
				
		customerMetaData.add(tinNo);
		customerMetaData.add(FIELD_TIN_NO);
		
		
		JLabel cstNo = new JLabel("CST No", 10);
		FIELD_CST_NO = new JTextField(customerFormModel.getCstNo());
				
		customerMetaData.add(cstNo);
		customerMetaData.add(FIELD_CST_NO);
					
		JLabel vendorCode = new JLabel("Vendor Code",10);
		FIELD_VENDOR_CODE = new JTextField(customerFormModel.getVendorCode());
		
		customerMetaData.add(vendorCode);
		customerMetaData.add(FIELD_VENDOR_CODE);
		
	}
	
	
	public JPanel constructControlPanel(){
		JPanel controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth() - 20, 30));
		int midArea = ((AppContext.getInstance().getWorkAreaWidth() - 20) /  2) / 10;
		
		controlPanel.add(Box.createHorizontalStrut(midArea));
		JButton saveBt = new JButton("Save");
		saveBt.addActionListener(new SaveCustomerListener());
		controlPanel.add(saveBt);
		
		if(customerFormModel.isCreateCustomer()){
			JButton createBt = new JButton("Add New Customer");
			createBt.addActionListener(new CreateNewCustomerListener());
			controlPanel.add(createBt);
		}
		return controlPanel;
	}
	
	
	public JPanel constructAddressPanel(){
		JPanel addressPanel = new JPanel();
		//orderItemPanel.setBackground(Color.RED);
		int height =  450;
		int totalWidth = (AppContext.getInstance().getWorkAreaWidth() - 20); 
		int midArea = (totalWidth /  2);
		addressPanel.setBackground(Color.WHITE);
		addressPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), height));
		
		JPanel address1Panel = new JPanel();
		address1Panel.setPreferredSize(new Dimension(midArea, height));
		
		JPanel address1Header = new JPanel();
		address1Header.setPreferredSize(new Dimension(midArea, 25));
		JLabel address1Label = new JLabel("Primary Address");
		address1Label.setFont(Font.getFont(Font.SANS_SERIF));
		address1Header.add(address1Label);
		
		
		address1Panel.add(address1Header);
		address1Panel.add(constructAdd1Fields());
		
		int remWidth = totalWidth - midArea;
		JPanel address2Panel = new JPanel();
		address2Panel.setPreferredSize(new Dimension(remWidth, height));
		
		JPanel address2Header = new JPanel();
		address2Header.setPreferredSize(new Dimension(remWidth, 25));
		JLabel address2Label = new JLabel("Secondary Address");
		address2Label.setFont(Font.getFont(Font.SANS_SERIF));
		address2Header.add(address2Label);
		
		address2Panel.add(address2Header);
		populateAdd2Fields(address2Panel);
		
		addressPanel.add(address1Panel);
		addressPanel.add(address2Panel);
		
		return addressPanel;
	}
	
	
	private JPanel constructAdd1Fields(){
		
		JPanel address1Panel = new JPanel();
		GridLayout gridLayout = new GridLayout(11, 2, 6, 6);
		address1Panel.setLayout(gridLayout);
		
		address1Panel.add(Box.createHorizontalStrut(10));
		address1Panel.add(Box.createHorizontalStrut(10));
		
		JLabel contactName = new JLabel("Contact Name", 10);
		FIELD_ADD1_CONT_NAME = new JTextField(customerFormModel.getAdd1ContactName(), 20);
		address1Panel.add(contactName);
		address1Panel.add(FIELD_ADD1_CONT_NAME);
		
		JLabel line1 = new JLabel("Address Line 1", 10);
		FIELD_ADD1_LINE1 = new JTextField(customerFormModel.getAdd1Line1(), 20);
		address1Panel.add(line1);
		address1Panel.add(FIELD_ADD1_LINE1);
		
		JLabel line2 = new JLabel("Address Line 2", 10);
		FIELD_ADD1_LINE2 = new JTextField(customerFormModel.getAdd1Line2(), 20);
		address1Panel.add(line2);
		address1Panel.add(FIELD_ADD1_LINE2);
		
		JLabel line3 = new JLabel("Address Line 3", 10);
		FIELD_ADD1_LINE3 = new JTextField(customerFormModel.getAdd1Line3(), 20);
		address1Panel.add(line3);
		address1Panel.add(FIELD_ADD1_LINE3);
		
		JLabel city = new JLabel("City", 10);
		FIELD_ADD1_CITY = new JTextField(customerFormModel.getAdd1City(), 10);
		address1Panel.add(city);
		address1Panel.add(FIELD_ADD1_CITY);
		
		JLabel state = new JLabel("State", 10);
		FIELD_ADD1_STATE = new JTextField(customerFormModel.getAdd1State(), 10);
		address1Panel.add(state);
		address1Panel.add(FIELD_ADD1_STATE);
		
		JLabel country = new JLabel("Country", 10);
		FIELD_ADD1_COUNTRY = new JTextField(customerFormModel.getAdd1Country(), 10);
		address1Panel.add(country);
		address1Panel.add(FIELD_ADD1_COUNTRY);
		
		JLabel pincode = new JLabel("Pincode", 10);
		FIELD_ADD1_PINCODE = new JTextField(customerFormModel.getAdd1Pincode(), 10);
		address1Panel.add(pincode);
		address1Panel.add(FIELD_ADD1_PINCODE);
		
		
		
		JLabel phone1 = new JLabel("Phone1", 10);
		FIELD_ADD1_PHONE1 = new JTextField(customerFormModel.getAdd1phone1(), 10);
		address1Panel.add(phone1);
		address1Panel.add(FIELD_ADD1_PHONE1);
		
		JLabel phone2 = new JLabel("Phone2", 10);
		FIELD_ADD1_PHONE2 = new JTextField(customerFormModel.getAdd1phone2(), 10);
		address1Panel.add(phone2);
		address1Panel.add(FIELD_ADD1_PHONE2);
		
		return address1Panel;
	}
	
	
	
	
	private void populateAdd2Fields(JPanel address2Panel){
		
		JPanel address2SameAs1Panel = new JPanel();
		GridLayout add2GridLayout = new GridLayout(1, 2, 6, 6);
		address2SameAs1Panel.setLayout(add2GridLayout);
		
		FIELD_ADD2_SAME_CHK = new JCheckBox();
		JLabel sameAsPrimaryAdd = new JLabel("Same As Primary Address", 10);
		if(customerFormModel.isSameAsAdd1()){
			FIELD_ADD2_SAME_CHK.setSelected(true);
		}
		address2SameAs1Panel.add(FIELD_ADD2_SAME_CHK);
		address2SameAs1Panel.add(sameAsPrimaryAdd);
		
		address2Panel.add(address2SameAs1Panel);
		
		JPanel address1Panel = new JPanel();
		GridLayout gridLayout = new GridLayout(10, 2, 6, 6);
		address1Panel.setLayout(gridLayout);
		
		
		
		JLabel contactName = new JLabel("Contact Name", 10);
		FIELD_ADD2_CONT_NAME = new JTextField(customerFormModel.getAdd2ContactName(), 20);
		address1Panel.add(contactName);
		address1Panel.add(FIELD_ADD2_CONT_NAME);
		
		JLabel line1 = new JLabel("Address Line 1", 10);
		FIELD_ADD2_LINE1 = new JTextField(customerFormModel.getAdd2Line1(), 20);
		address1Panel.add(line1);
		address1Panel.add(FIELD_ADD2_LINE1);
		
		JLabel line2 = new JLabel("Address Line 2", 10);
		FIELD_ADD2_LINE2 = new JTextField(customerFormModel.getAdd2Line2(), 20);
		address1Panel.add(line2);
		address1Panel.add(FIELD_ADD2_LINE2);
		
		JLabel line3 = new JLabel("Address Line 3", 10);
		FIELD_ADD2_LINE3 = new JTextField(customerFormModel.getAdd2Line3(), 20);
		address1Panel.add(line3);
		address1Panel.add(FIELD_ADD2_LINE3);
		
		JLabel city = new JLabel("City", 10);
		FIELD_ADD2_CITY = new JTextField(customerFormModel.getAdd2City(), 10);
		address1Panel.add(city);
		address1Panel.add(FIELD_ADD2_CITY);
		
		JLabel state = new JLabel("State", 10);
		FIELD_ADD2_STATE = new JTextField(customerFormModel.getAdd2State(), 10);
		address1Panel.add(state);
		address1Panel.add(FIELD_ADD2_STATE);
		
		JLabel country = new JLabel("Country", 10);
		FIELD_ADD2_COUNTRY = new JTextField(customerFormModel.getAdd2Country(), 10);
		address1Panel.add(country);
		address1Panel.add(FIELD_ADD2_COUNTRY);
		
		JLabel pincode = new JLabel("Pincode", 10);
		FIELD_ADD2_PINCODE = new JTextField(customerFormModel.getAdd2Pincode(), 10);
		address1Panel.add(pincode);
		address1Panel.add(FIELD_ADD2_PINCODE);
		
		
		
		JLabel phone1 = new JLabel("Phone1", 10);
		FIELD_ADD2_PHONE1 = new JTextField(customerFormModel.getAdd2phone1(), 10);
		address1Panel.add(phone1);
		address1Panel.add(FIELD_ADD2_PHONE1);
		
		JLabel phone2 = new JLabel("Phone2", 10);
		FIELD_ADD2_PHONE2 = new JTextField(customerFormModel.getAdd2phone2(), 10);
		address1Panel.add(phone2);
		address1Panel.add(FIELD_ADD2_PHONE2);
		
		address2Panel.add(address1Panel);
	}
	
	
	
	private CustomerVO constructCustomerVO(){
		long id = new Long(FIELD_CUSTOMER_ID.getText());
		String name = FIELD_CUSTOMER_NAME.getText();
		String tinNo = FIELD_TIN_NO.getText();
		String cstNo = FIELD_CST_NO.getText();
		String vendorCode = FIELD_VENDOR_CODE.getText();
		
		CustomerVO customerVO = new CustomerVO(id, name);
		customerVO.setTinNo(tinNo);
		customerVO.setCstNo(cstNo);
		customerVO.setVendorCode(vendorCode);
		
		customerVO.setPrimaryAddress(constructPrimaryAddressVO());
		customerVO.setSecondaryAddress(constructSecAddressVO());
		
		return customerVO;
	}
	
	
	private AddressVO constructPrimaryAddressVO(){
		String name = FIELD_ADD1_CONT_NAME.getText();
		String line1 = FIELD_ADD1_LINE1.getText();
		String line2 = FIELD_ADD1_LINE2.getText();
		String line3 = FIELD_ADD1_LINE3.getText();
		
		String city = FIELD_ADD1_CITY.getText();
		String country = FIELD_ADD1_COUNTRY.getText();
		String state = FIELD_ADD1_STATE.getText();
		String pincode = FIELD_ADD1_PINCODE.getText();
		
		String phone1 = FIELD_ADD1_PHONE1.getText();
		String phone2 = FIELD_ADD1_PHONE2.getText();
		
		AddressVO addressVO = new AddressVO();
		
		addressVO.setId(address1Id);
		addressVO.setFirstName(name);
		addressVO.setAddressLine1(line1);
		addressVO.setAddressLine2(line2);
		addressVO.setAddressLine3(line3);
		
		addressVO.setCity(city);
		addressVO.setCountry(country);
		addressVO.setState(state);
		addressVO.setPincode(pincode);
		
		addressVO.setPhone1(phone1);
		addressVO.setPhone2(phone2);
		
		return addressVO;
	}
	
	
	private AddressVO constructSecAddressVO(){
		AddressVO addressVO = new AddressVO();
		if(FIELD_ADD2_SAME_CHK.isSelected()){
			addressVO.setSameAsPrimaryAddress(true);
		}else{
			String name = FIELD_ADD2_CONT_NAME.getText();
			String line1 = FIELD_ADD2_LINE1.getText();
			String line2 = FIELD_ADD2_LINE2.getText();
			String line3 = FIELD_ADD2_LINE3.getText();
			String city = FIELD_ADD2_CITY.getText();
			String country = FIELD_ADD2_COUNTRY.getText();
			String state = FIELD_ADD2_STATE.getText();
			String pincode = FIELD_ADD2_PINCODE.getText();
			String phone1 = FIELD_ADD2_PHONE1.getText();
			String phone2 = FIELD_ADD2_PHONE2.getText();
			
			addressVO.setId(address2Id);
			addressVO.setFirstName(name);
			addressVO.setAddressLine1(line1);
			addressVO.setAddressLine2(line2);
			addressVO.setAddressLine3(line3);
			
			addressVO.setCity(city);
			addressVO.setCountry(country);
			addressVO.setState(state);
			addressVO.setPincode(pincode);
			
			addressVO.setPhone1(phone1);
			addressVO.setPhone2(phone2);
		}
		return addressVO;
	}
	
	
	public class SaveCustomerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			CustomerVO customerVO = constructCustomerVO();
			ServiceClient.getInstance().saveCustomer(customerVO);
			JOptionPane.showMessageDialog(null, "Customer Saved "+customerVO.getId());
			populateFormFields(customerVO);
		}
	}
	
	public class CreateNewCustomerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			resetFormFields();
		}
	}

	private void resetFormFields(){
		FIELD_CUSTOMER_ID.setText("0");
		this.address1Id = 0;
		this.address2Id = 0;
		
		FIELD_CUSTOMER_NAME.setText("");
		FIELD_TIN_NO.setText("");
		FIELD_CST_NO.setText("");
		FIELD_VENDOR_CODE.setText("");
		
		FIELD_ADD1_LINE1.setText("");
		FIELD_ADD1_LINE2.setText("");
		FIELD_ADD1_LINE3.setText("");
		FIELD_ADD1_CITY.setText("");
		FIELD_ADD1_STATE.setText("");
		FIELD_ADD1_COUNTRY.setText("");
		FIELD_ADD1_PINCODE.setText("");
		FIELD_ADD1_CONT_NAME.setText("");
		FIELD_ADD1_PHONE1.setText("");
		FIELD_ADD1_PHONE2.setText("");
		
		FIELD_ADD2_SAME_CHK.setSelected(false);
		
		FIELD_ADD2_LINE1.setText("");
		FIELD_ADD2_LINE2.setText("");
		FIELD_ADD2_LINE3.setText("");
		FIELD_ADD2_CITY.setText("");
		FIELD_ADD2_STATE.setText("");
		FIELD_ADD2_COUNTRY.setText("");
		FIELD_ADD2_PINCODE.setText("");
		FIELD_ADD2_CONT_NAME.setText("");
		FIELD_ADD2_PHONE1.setText("");
		FIELD_ADD2_PHONE2.setText("");
		
	}
	
	private void populateFormFields(CustomerVO customerVO){
		FIELD_CUSTOMER_ID.setText(String.valueOf(customerVO.getId()));
		this.address1Id = customerVO.getPrimaryAddress().getId();
		this.address2Id = customerVO.getSecondaryAddress().getId();
	}


}
