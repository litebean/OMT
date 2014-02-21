package com.litetech.omt.ui.comp;

import static com.litetech.omt.ui.model.ProfitLossReportModel.header;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.PaymentStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.ui.comp.PaymentSearchFrame.PaymentSearchListener;
import com.litetech.omt.ui.model.ProfitLossReportModel;
import com.litetech.omt.util.DateUtil;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.IncomeExpenseVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;
import com.litetech.omt.vo.SearchTransactionRequestVO;

public class ProfitLossReportForm extends JPanel implements PanelTabComp{

	private JPanel searchPanel;
	private JPanel resultPanel;
	private JPanel myPanelTab;
	
	
	private OMTCalendar FIELD_TRN_FROM_DATE;
	private OMTCalendar FIELD_TRN_TO_DATE;
	
	public ProfitLossReportForm(){
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
		searchDataPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth() - 20, 25));
		buildSearchFields(searchDataPanel);
		searchPanel.add(searchDataPanel);
		
		//searchButton.addActionListener(new PaymentSearchListener());
		//searchControlPanel.add(searchButton);
		//searchPanel.add(searchControlPanel);
	}
	
	private void buildSearchFields(JPanel searchDataPanel){
		GridLayout gridLayout = new GridLayout(1, 5, 50, 50);
		searchDataPanel.setLayout(gridLayout);	
		
		
		JLabel tranFrom = new JLabel("Transaction From");
		FIELD_TRN_FROM_DATE = new OMTCalendar(null);
		searchDataPanel.add(tranFrom);
		searchDataPanel.add(FIELD_TRN_FROM_DATE);
		
		JLabel tranTo = new JLabel("Transaction To");
		FIELD_TRN_TO_DATE = new OMTCalendar(null);
		searchDataPanel.add(tranTo);
		searchDataPanel.add(FIELD_TRN_TO_DATE);
		
		JButton reportButton = new JButton("Generate Report");
		reportButton.addActionListener(new PaymentSearchListener());
		searchDataPanel.add(reportButton);
		
		
	}

	private ProfitLossSearchRequest constructIncomeExpenseReq(){
		Date transFromDate = FIELD_TRN_FROM_DATE.getDate();
		Date transToDate = FIELD_TRN_TO_DATE.getDate();
		
		ProfitLossSearchRequest profitLossSearchRequest = new ProfitLossSearchRequest();
		profitLossSearchRequest.setStartDate(transFromDate);
		profitLossSearchRequest.setEndDate(transToDate);
		
		return profitLossSearchRequest;
	}
	
	public class PaymentSearchListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			ProfitLossSearchRequest profitLossRequest = constructIncomeExpenseReq();
			List<PaymentTransactionVO> paymentTransactionVOs = ServiceClient.getInstance().searchPaymentTransaction(profitLossRequest);
			populateSearch(profitLossRequest, paymentTransactionVOs);
		}
	}
	
	private List<PaymentTransactionVO> mockTransactions(){
		List<PaymentTransactionVO> paymentTransactionVOs = new ArrayList<PaymentTransactionVO>();
		
		for (int i = 1; i < 100; i++) {
			PaymentTransactionVO paymentTransactionVO = new PaymentTransactionVO();
			paymentTransactionVO.setId(1000*i);
			paymentTransactionVO.setCustomerVO(new CustomerVO(100+i, "Customer Name "+i));
			paymentTransactionVO.setPaymentModeVO(new PaymentModeVO(1001, "Online"));
			paymentTransactionVO.setTransactionAmt(550.75*i);
			paymentTransactionVO.setTransactionDate(new Date());
			paymentTransactionVO.setTransactionStaus(PaymentStatusEnum.CONFIRMED);
			TransactionTypeEnum type = TransactionTypeEnum.CREDIT;
			if(i%5 == 0){
				type = TransactionTypeEnum.DEBIT;
			}
			paymentTransactionVO.setTransType(type);
			
			paymentTransactionVOs.add(paymentTransactionVO);
		}
		return paymentTransactionVOs;
	}
	
	
	public void populateSearch(ProfitLossSearchRequest profitLossRequest, 
			List<PaymentTransactionVO> paymentTransactionVOs){
		if(resultPanel != null){
			searchPanel.remove(resultPanel);
		}
		
		resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 500));
		ProfitLossReportModel profitLossReportModel = new ProfitLossReportModel(paymentTransactionVOs);
        
		constructTransactionTable(profitLossReportModel);
		constructIncomeExpensePanel(profitLossRequest, profitLossReportModel);
		
		
		
		searchPanel.add(resultPanel);
		ComponentRegistry.instance().repaintComponents();		
	}
	
	private void constructTransactionTable(ProfitLossReportModel profitLossReportModel){
		JTable table = new JTable(profitLossReportModel.getDefaultTableModel());
		
		table.setRowHeight(25);
		table.setPreferredScrollableViewportSize(new Dimension(AppContext.getInstance().getWorkAreaWidth()-20, 350));
        table.setFillsViewportHeight(true);
				
       // table.getColumn("Order Id").setCellRenderer(new SearchOrderCellRenderer());
        table.getColumn(header[0]).setPreferredWidth(100);
        table.getColumn(header[1]).setPreferredWidth(100);
        table.getColumn(header[2]).setPreferredWidth(300);
        table.getColumn(header[3]).setPreferredWidth(100);
        table.getColumn(header[4]).setPreferredWidth(100);
        table.getColumn(header[5]).setPreferredWidth(100);
        
//      Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        resultPanel.add(scrollPane);
	}
	
	
	
	private void constructIncomeExpensePanel(ProfitLossSearchRequest profitLossRequest,
			ProfitLossReportModel profitLossReportModel){
		JPanel incomeExpPanel = new JPanel();
		//incomeExpPanel.setBackground(Color.GREEN);
		int totalWidth = AppContext.getInstance().getWorkAreaWidth() - 20;
		int midArea = ((totalWidth) /  2) + 150;
		int width = (AppContext.getInstance().getWorkAreaWidth() - 20) - midArea;
		int height = 85;
		
		incomeExpPanel.setPreferredSize(new Dimension((totalWidth), height));
		
		Object[] myHeader = {"Name", "Value"};
		DefaultTableModel expTableModel = new DefaultTableModel(profitLossReportModel.getIncomeExpenseRow(), 
				myHeader){
			@Override
	          public boolean isCellEditable ( int row, int column ){
				return false;
			}
		};
		
		JTable table = new JTable(expTableModel);
		
		table.setTableHeader(null);
		table.setRowHeight(25);
		table.setPreferredScrollableViewportSize(new Dimension(width-5, height - 8));
        table.setFillsViewportHeight(true);
				
       // table.getColumn("Order Id").setCellRenderer(new SearchOrderCellRenderer());
        table.getColumn(myHeader[0]).setPreferredWidth(100);
        table.getColumn(myHeader[1]).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(table);
        incomeExpPanel.add(scrollPane);
		
        JPanel printPanel = new JPanel();
        printPanel.setLayout(new GridLayout(3, 1));
        
        printPanel.add(Box.createHorizontalBox());
        printPanel.add(Box.createHorizontalBox());
		JButton printReportBt = new JButton("Print Report");
		printReportBt.addActionListener(new PrintReportListener(
				profitLossRequest,
				profitLossReportModel.getPaymentTransactionVOs(),
				profitLossReportModel.getIncomeExpense()));
		printPanel.add(printReportBt);
		
		incomeExpPanel.add(printPanel);
		resultPanel.add(incomeExpPanel);
		
		resultPanel.add(incomeExpPanel);
	}

	
	
	public class PrintReportListener implements ActionListener{

		private ProfitLossSearchRequest profitLossRequest;
		private List<PaymentTransactionVO> paymentTransactionVOs;
		private IncomeExpenseVO incomeExpenseVO;
		
		public PrintReportListener(ProfitLossSearchRequest profitLossRequest,
				List<PaymentTransactionVO> paymentTransactionVOs, 
				IncomeExpenseVO incomeExpenseVO){
			this.profitLossRequest = profitLossRequest;
			this.paymentTransactionVOs = paymentTransactionVOs;
			this.incomeExpenseVO = incomeExpenseVO;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			ServiceClient.getInstance().printProfitLossReport(
					profitLossRequest, paymentTransactionVOs, incomeExpenseVO);
		}
	}
	
	@Override
	public void setMyPanelTab(JPanel myPanelTab) {
		this.myPanelTab = myPanelTab;
	}

	@Override
	public JPanel getMyPanelTab() {
		return myPanelTab;
	}
	
	

}
