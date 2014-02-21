package com.litetech.omt.ui.comp;

import static com.litetech.omt.ui.model.SearchResultPaymentModel.header;

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
import com.litetech.omt.ui.comp.editor.LiteEditableComboBoxEditor;
import com.litetech.omt.ui.comp.listener.CloseTabListener;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.PaymentSearchFormDSModel;
import com.litetech.omt.ui.model.PaymentSearchFormModel;
import com.litetech.omt.ui.model.PaymentTransFormModel;
import com.litetech.omt.ui.model.SearchResultPaymentModel;
import com.litetech.omt.ui.model.ds.PaymentTransFormDSModel;
import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.SearchTransactionRequestVO;

public class PaymentSearchFrame extends JPanel implements PanelTabComp{

	
	private JPanel searchPanel;
	private JPanel resultPanel;
	
	
	private PaymentSearchFormDSModel formDSModel;
	
	private JTextField FIELD_TRN_ID;
	private JComboBox FIELD_CUSTOMER;
	private JComboBox FIELD_TRN_TYPE;
	private JComboBox FIELD_TRN_STATUS;
	private JComboBox FIELD_TRN_BANK;
	private JComboBox FIELD_TRN_MODE;
	private JTextField FIELD_TRN_REF;
	private JTextField FIELD_TRN_AMT;
	private OMTCalendar FIELD_TRN_DATE;
	private JTextField FIELD_TRN_DESC;
	
	private JPanel myPanel;
	@Override
	public void setMyPanelTab(JPanel myPanel) {
		this.myPanel = myPanel;	
	}


	@Override
	public JPanel getMyPanelTab() {
		return myPanel;
	}
	
	public PaymentSearchFrame(PaymentSearchFormDSModel formDSModel){
		this.formDSModel = formDSModel;
		
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
		searchButton.addActionListener(new PaymentSearchListener());
		searchControlPanel.add(searchButton);
		searchPanel.add(searchControlPanel);
	}
	
	
	private void populateSearchFields(JPanel searchDataPanel){
		GridLayout gridLayout = new GridLayout(3, 6, 1, 1);
		searchDataPanel.setLayout(gridLayout);	
		
		JLabel transactionId = new JLabel("Transaction ID");
		FIELD_TRN_ID = new JTextField("");
		
		
		Vector<LiteComboModel> customerComboList = new Vector<LiteComboModel>();
		customerComboList.add(new LiteComboModel(-1, "-"));
		List<CustomerVO> customerVOs = formDSModel.getCustomers();
		for(CustomerVO customerVO : customerVOs){
			customerComboList.add(new LiteComboModel(customerVO.getId(), customerVO.getName()));
		}
		
		JLabel customerId = new JLabel("Customer");
		FIELD_CUSTOMER = new JComboBox(customerComboList);
		FIELD_CUSTOMER.setRenderer(new LiteComboModelRenderer());
		FIELD_CUSTOMER.setEditor(new LiteEditableComboBoxEditor(FIELD_CUSTOMER, customerComboList));
		
		Vector<LiteComboModel> transTypeComboList = new Vector<LiteComboModel>();
		transTypeComboList.add(new LiteComboModel(-1, "-"));
		
		TransactionTypeEnum[] transactionTypeEnums = TransactionTypeEnum.values();
		for(TransactionTypeEnum transactionTypeEnum : transactionTypeEnums){
			transTypeComboList.add(new LiteComboModel(transactionTypeEnum.getId(), transactionTypeEnum.getName()));
		}
		
		JLabel transactionType = new JLabel("Transaction Type");
		FIELD_TRN_TYPE = new JComboBox(transTypeComboList);
		FIELD_TRN_TYPE.setRenderer(new LiteComboModelRenderer());
		
				
		//first row
		searchDataPanel.add(transactionId);
		searchDataPanel.add(FIELD_TRN_ID);
		searchDataPanel.add(customerId);
		searchDataPanel.add(FIELD_CUSTOMER);
		searchDataPanel.add(transactionType);
		searchDataPanel.add(FIELD_TRN_TYPE);
		
		
		
		Vector<LiteComboModel> bankComboList = new Vector<LiteComboModel>();
		bankComboList.add(new LiteComboModel(-1, "-"));
		List<BankVO> bankVOs = formDSModel.getBanks();
		for(BankVO bankVO : bankVOs){
			bankComboList.add(new LiteComboModel(bankVO.getId(), bankVO.getName()));
		}
		
		JLabel bank = new JLabel("Bank");
		FIELD_TRN_BANK = new JComboBox(bankComboList);
		FIELD_TRN_BANK.setRenderer(new LiteComboModelRenderer());
		
		
		Vector<LiteComboModel> payModeComboList = new Vector<LiteComboModel>();
		List<PaymentModeVO> paymentModeVOs = formDSModel.getModes();
		
		payModeComboList.add(new LiteComboModel(-1, "-"));
		for(PaymentModeVO paymentModeVO : paymentModeVOs){
			payModeComboList.add(new LiteComboModel(paymentModeVO.getId(), paymentModeVO.getName()));
		}
		
		JLabel paymentMode = new JLabel("Payment Mode");
		FIELD_TRN_MODE = new JComboBox(payModeComboList);
		FIELD_TRN_MODE.setRenderer(new LiteComboModelRenderer());
		
		Vector<LiteComboModel> statusComboList = new Vector<LiteComboModel>();
		statusComboList.add(new LiteComboModel(-1, "-"));
		
		PaymentStatusEnum[] statuses = PaymentStatusEnum.values();
		for(PaymentStatusEnum statusEnum : statuses){
			statusComboList.add(new LiteComboModel(statusEnum.getId(), statusEnum.toString()));
		}
		
		JLabel status = new JLabel("Status");
		FIELD_TRN_STATUS = new JComboBox(statusComboList);
		FIELD_TRN_STATUS.setRenderer(new LiteComboModelRenderer());
		
		
		//second row
		searchDataPanel.add(bank);
		searchDataPanel.add(FIELD_TRN_BANK);
		searchDataPanel.add(paymentMode);
		searchDataPanel.add(FIELD_TRN_MODE);
		searchDataPanel.add(status);
		searchDataPanel.add(FIELD_TRN_STATUS);
		
		JLabel transDesc = new JLabel("Transaction Desc");
		FIELD_TRN_DESC = new JTextField();
		
		JLabel amount = new JLabel("Transaction Amount > ");
		FIELD_TRN_AMT = new JTextField();
		
		JLabel date = new JLabel("Transaction Date > ");
		FIELD_TRN_DATE = new OMTCalendar(null);
		
		//third row
		searchDataPanel.add(transDesc);
		searchDataPanel.add(FIELD_TRN_DESC);
		searchDataPanel.add(amount);
		searchDataPanel.add(FIELD_TRN_AMT);
		searchDataPanel.add(date);
		searchDataPanel.add(FIELD_TRN_DATE);
	}
	
	
	
	
	public class PaymentSearchListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			SearchTransactionRequestVO searchTransactionRequestVO = constructRequest(); 
			populateSearch(loadSearchFormModel(searchTransactionRequestVO));
		}
	}
	
	
	private SearchTransactionRequestVO constructRequest(){
		SearchTransactionRequestVO searchTransactionRequestVO = new SearchTransactionRequestVO();
		
		long transId = FIELD_TRN_ID.getText().equals("") ? 0 : new Long(FIELD_TRN_ID.getText());
		long customerId = ((LiteComboModel)FIELD_CUSTOMER.getSelectedItem()).getId();
		long transTypeId = ((LiteComboModel)FIELD_TRN_TYPE.getSelectedItem()).getId();
		long bankId = ((LiteComboModel)FIELD_TRN_BANK.getSelectedItem()).getId();
		long paymentModeId = ((LiteComboModel)FIELD_TRN_MODE.getSelectedItem()).getId();
		long statusId = ((LiteComboModel)FIELD_TRN_STATUS.getSelectedItem()).getId();
		String transDesc = FIELD_TRN_DESC.getText().equals("") ? null : FIELD_TRN_DESC.getText();
		Double transAmount = FIELD_TRN_AMT.getText().equals("") ? null : new Double(FIELD_TRN_AMT.getText());
		Date transDate = FIELD_TRN_DATE.getDate();
		/*Date transDate = null;
		if(!transDateStr.equals("")){
			try {
				transDate = new SimpleDateFormat("dd-MM-yyyy").parse(transDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}		
		}*/
		searchTransactionRequestVO.setTransactionId(transId);
		searchTransactionRequestVO.setCustomerId(customerId);
		if(transTypeId >= 0){
			searchTransactionRequestVO.setTransactionTypeEnum(TransactionTypeEnum.getById((int)transTypeId));
		}
		
		searchTransactionRequestVO.setBankId(bankId);
		searchTransactionRequestVO.setPaymentModeId(paymentModeId);
		if(statusId >= 0){
			searchTransactionRequestVO.setStatusEnum(PaymentStatusEnum.getById((int)statusId));
		}
		
		searchTransactionRequestVO.setDescLike(transDesc);
		searchTransactionRequestVO.setTransAmoutGrThan(transAmount);
		searchTransactionRequestVO.setTransDateGrThan(transDate);
		
		return searchTransactionRequestVO;
	}
	
	
	
	private PaymentSearchFormModel loadSearchFormModel(SearchTransactionRequestVO searchTransactionRequestVO){
		PaymentSearchFormModel formModel = new PaymentSearchFormModel();
		
		List<PaymentTransactionVO> paymentTransactionVOs = ServiceClient.getInstance().searchPaymentTransaction(searchTransactionRequestVO);
				
		formModel.setPaymentTransactionVOs(paymentTransactionVOs);
		return formModel;
	}
	
	
	public void populateSearch(PaymentSearchFormModel paymentSearchFormModel){
		if(resultPanel != null){
			searchPanel.remove(resultPanel);
		}
		
		resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 400));
		
		final SearchResultPaymentModel paymentModel = new SearchResultPaymentModel(paymentSearchFormModel.getResultRows());
		JTable table = new JTable(paymentModel.getDefaultTableModel());
		table.setRowHeight(25);
		
		table.setPreferredScrollableViewportSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-30, 350));
        table.setFillsViewportHeight(true);
       // table.getColumn("Order Id").setCellRenderer(new SearchOrderCellRenderer());
        table.getColumn(header[0]).setPreferredWidth(100);
        table.getColumn(header[1]).setPreferredWidth(300);
        table.getColumn(header[2]).setPreferredWidth(100);
        table.getColumn(header[3]).setPreferredWidth(100);
        table.getColumn(header[4]).setPreferredWidth(100);
        table.getColumn(header[5]).setPreferredWidth(100);
        table.getColumn(header[6]).setPreferredWidth(100);
        table.getColumn(header[7]).setPreferredWidth(100);
//      Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	//	table.setColumnSelectionInterval(5, 10);
        table.addMouseListener(new MouseAdapter() {
        	
        	 @Override
        	  public void mouseClicked(MouseEvent e) {
        		 JTable target = (JTable)e.getSource();
         	     int row = target.getSelectedRow();
        		 String transIdStr = paymentModel.getCell(row, 0).toString();
        		 populateDisplayTab(transIdStr);
        	 }
        });

        resultPanel.add(scrollPane);
		
		searchPanel.add(resultPanel);
		ComponentRegistry.instance().repaintComponents();		
	}
	
	
	
	
	public void populateDisplayTab(String command){
		long transactionId = Long.valueOf(command);
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
			
			PaymentTransactionVO paymentTransactionVO = ServiceClient.getInstance().retrieveTransaction(transactionId);
			
			JComponent comp = new PaymentTransactionForm(constructPaymentTransDSModel(paymentTransactionVO),
					constructPaymentTransModel(paymentTransactionVO));
			comp.setName(command);
			//comp.setOpaque(true);
			//comp.setBackground(Color.WHITE);
			
			Util.buildCloseTab(command, "Payment: "+command, tabbedPane, comp, compIcon,
					new CloseTabListener(displayPanel, comp));
			//tabbedPane.setSelectedComponent(newContainer);
		}
		
		ComponentRegistry.instance().repaintComponents();
	}
	
	
	private PaymentTransFormDSModel constructPaymentTransDSModel(PaymentTransactionVO transactionVO){
		PaymentTransFormDSModel formDSModel = new PaymentTransFormDSModel();
	
		List<CustomerVO> customerLs = new ArrayList<CustomerVO>();
		CustomerVO customerVO = transactionVO.getCustomerVO();
		customerLs.add(customerVO);
		
		formDSModel.setCustomerVOLs(customerLs);
		
		
		List<BankVO> bankVOs = ServiceClient.getInstance().retrieveActiveBanks();
		formDSModel.setBankVOs(bankVOs);
		
		List<PaymentModeVO> paymentModeVOs = ServiceClient.getInstance().retrieveActivePaymentModes();
		formDSModel.setPaymentModeVOs(paymentModeVOs);
		
		return formDSModel;
	}
	
	
	private PaymentTransFormModel constructPaymentTransModel(PaymentTransactionVO paymentTransactionVO){
		PaymentTransFormModel formModel = new PaymentTransFormModel();
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
		
		AddressVO addressVO = new AddressVO();
		addressVO.setAddressLine1("No.3, Ekambara Iyer St.,");
		addressVO.setAddressLine2("Venkatapuram, Ambattur");
		addressVO.setCity("Chennai");
		addressVO.setState("TamilNadu");
		addressVO.setCountry("India");
		formModel.setAddressVO(addressVO);
		
		return formModel;
	}
	
	

}
