package com.litetech.omt.ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.util.DateUtil;
import com.litetech.omt.vo.IncomeExpenseVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;

public class ProfitLossReportModel {

	public final static String[]  header = {"Transaction Id", "Transaction Date", "Customer Name", "Transaction Type", "Amount", "Payment Mode"};
	private DefaultTableModel defaultTableModel;
	private Object[][] rows;
	
	private List<PaymentTransactionVO> paymentTransactionVOs;
	private double credit;
	private double debit;
	
	
	
	public ProfitLossReportModel(List<PaymentTransactionVO> paymentTransactionVOs){
		this.paymentTransactionVOs = paymentTransactionVOs;
		this.rows = getResultRows(paymentTransactionVOs);
		this.defaultTableModel = new DefaultTableModel(rows, header){
			 @Override
            public boolean isCellEditable ( int row, int column ){
				 return false;
            }
		};
	}	
	
	public DefaultTableModel getDefaultTableModel(){
		return this.defaultTableModel;
	}
	
	public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
		this.defaultTableModel = defaultTableModel;
	}

	public Object getCell(int row, int col) {
		return rows[row][col];
	}
	
	public List<PaymentTransactionVO> getPaymentTransactionVOs(){
		return this.paymentTransactionVOs;
	}
	
	public Object[][] getResultRows(List<PaymentTransactionVO> paymentTransactionVOs){
		Object[][] result = new Object[paymentTransactionVOs.size()][6];
		
		int row = 0;
		for(PaymentTransactionVO paymentTransactionVO : paymentTransactionVOs){
			result[row][0] = paymentTransactionVO.getId();
			result[row][1] = DateUtil.getDateAsString(paymentTransactionVO.getTransactionDate());
			
			String customerName = paymentTransactionVO.getCustomerVO() == null ?
					"" : paymentTransactionVO.getCustomerVO().getName();
			result[row][2] = customerName;
			
			result[row][3] = paymentTransactionVO.getTransType().getName();
			result[row][4] = paymentTransactionVO.getTransactionAmt();
			
			String transMode = paymentTransactionVO.getPaymentModeVO() == null ?
					" " : paymentTransactionVO.getPaymentModeVO().getName();
			result[row][5] = transMode;
			
			mainpulateCreditDebitAmt(paymentTransactionVO);
			row++;
		}
		
		return result;
	}
	
	
	private void mainpulateCreditDebitAmt(PaymentTransactionVO paymentTransactionVO){
		if(TransactionTypeEnum.CREDIT.equals(paymentTransactionVO.getTransType())){
			credit += paymentTransactionVO.getTransactionAmt();
		}else if(TransactionTypeEnum.DEBIT.equals(paymentTransactionVO.getTransType())){
			debit += paymentTransactionVO.getTransactionAmt();
		}		
	}
	
	
	
	public Object[][] getIncomeExpenseRow(){
		Object[][] result = new Object[3][2];
		
		result[0] = new Object[]{"Total Credit/Sale", credit};
		result[1] = new Object[]{"Total Debit/Purchase", debit};
		result[2] = new Object[]{"Total Profit", credit - debit};
		
		return result;
	}
	
	public IncomeExpenseVO getIncomeExpense(){
		IncomeExpenseVO incomeExpVO = new IncomeExpenseVO();
		incomeExpVO.setIncome(credit);
		incomeExpVO.setExpense(debit);		
		
		return incomeExpVO;
	}
}
