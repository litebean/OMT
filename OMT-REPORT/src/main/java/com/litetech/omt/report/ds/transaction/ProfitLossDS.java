package com.litetech.omt.report.ds.transaction;

import java.util.List;

import net.sf.jasperreports.engine.JRException;

import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.report.ds.BaseDS;
import com.litetech.omt.util.CurrencyUtil;
import com.litetech.omt.util.DateUtil;
import com.litetech.omt.util.NumberUtil;
import com.litetech.omt.vo.IncomeExpenseVO;
import com.litetech.omt.vo.OrgVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;

public class ProfitLossDS extends BaseDS{

	private OrgVO orgVO;
	private ProfitLossSearchRequest searchRequest;
	private List<PaymentTransactionVO> paymentTransactionVOs;
	private IncomeExpenseVO incomeExpenseVO;

	private PaymentTransactionVO paymentTransactionVO;
	
	public ProfitLossDS(
			ProfitLossSearchRequest searchRequest,
			OrgVO orgVO,
			IncomeExpenseVO incomeExpenseVO,
			List<PaymentTransactionVO> paymentTransactionVOs){
		this.orgVO = orgVO;
		this.searchRequest = searchRequest;
		this.paymentTransactionVOs = paymentTransactionVOs;
		this.incomeExpenseVO = incomeExpenseVO;
	}
	
	
	
	
	public boolean next() throws JRException {
		boolean hasNext = false;
		if(index < paymentTransactionVOs.size()){
			hasNext = true;
			paymentTransactionVO = paymentTransactionVOs.get(index);
			index++;
		}
		
		return hasNext;
	}
	
	public String getSearchDescLine1(){
		return orgVO.getName().replace("", " ").trim() + " - Profit Loss Report"; 
	}
	
	public String getSearchDescLine2(){
		return "For period between "+DateUtil.getDateAsString(searchRequest.getStartDate()) + 
				" and "+DateUtil.getDateAsString(searchRequest.getEndDate());
	}
	
	public String getAmountInWords(){
		double amount = incomeExpenseVO.getProfit();
		boolean negative = false;
		if(amount < 0){
			amount = amount * -1;
			negative = true;
		}
		String amtInWords = CurrencyUtil.convertINR(amount).toUpperCase();
		
		if(negative){
			amtInWords = "[ - ] "+amtInWords;
		}
		
		return amtInWords;
	}
	
	
	public String getTransactionId(){
		return String.valueOf(paymentTransactionVO.getId());
	}
	
	public String getTransactionDate(){
		return DateUtil.getDateAsString(
				paymentTransactionVO.getTransactionDate());
	}
	
	public String getCustomerName(){
		return paymentTransactionVO.getCustomerVO().getName();
	}
	
	private String getAmount(boolean credit){
		String amountStr = "";
		TransactionTypeEnum transType = paymentTransactionVO.getTransType();
		
		if(credit){
			if(TransactionTypeEnum.CREDIT.equals(transType)){
				double amt = paymentTransactionVO.getTransactionAmt();
				amountStr = NumberUtil.convertInTwoDecimalFormat(amt);
			}			
		}else{
			if(TransactionTypeEnum.DEBIT.equals(transType)){
				double amt = paymentTransactionVO.getTransactionAmt();
				amountStr = NumberUtil.convertInTwoDecimalFormat(amt);
			}
		}
		return amountStr;
	}
	
	public String getCreditAmt(){
		return getAmount(true);
	}
	
	public String getDebitAmt(){
		return getAmount(false);	
	}
	
	
	public String getStatus(){
		return paymentTransactionVO.getTransactionStaus().name();
	}
	
	public String getTransactionMode(){
		return paymentTransactionVO.getPaymentModeVO().getName();
	}
	
	/*
	 * <field name="totalCredit" class="java.lang.String"/>
	<field name="totalDebit" class="java.lang.String"/>
	<field name="profitLoss" class="java.lang.String"/>
	 */
	
	public String getTotalCredit(){
		return NumberUtil.convertInTwoDecimalFormat(incomeExpenseVO.getIncome());
	}
	
	public String getTotalDebit(){
		return NumberUtil.convertInTwoDecimalFormat(incomeExpenseVO.getExpense());
	}
	
	public String getProfitLoss(){
		double incExp = incomeExpenseVO.getProfit();
		return NumberUtil.convertInTwoDecimalFormat(incExp);
	}
}
