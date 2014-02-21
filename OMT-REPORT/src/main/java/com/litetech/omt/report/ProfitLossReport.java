package com.litetech.omt.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;

import com.litetech.omt.constant.PaymentStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;
import com.litetech.omt.report.ds.transaction.ProfitLossDS;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.IncomeExpenseVO;
import com.litetech.omt.vo.OrgVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;

public class ProfitLossReport {

	
	private static OrgVO mockOrgVO(){
		OrgVO orgVO = new OrgVO();
		orgVO.setName("KANNIA RUBBERS");
		orgVO.setOrgHeader1("Office: No. 18-A, Bharathiyar St., Srinivasa Nagar, Padi, Chennai - 600 050");
		orgVO.setOrgHeader2("Phone: 044 - 26245553, Cell : 98840 33645");
		orgVO.setOrgHeader3("Factory : No. 236/7, Nageswara Rao Road, 1st Cross, Vanagram Road, Athipet, Ambattur");
		orgVO.setOrgHeader4("Chennai - 600 058. Email: kanniarubbers@yahoo.com Web : www.kanniarubbers.com");
		
		orgVO.setSsiNo("330011100324 E");
		orgVO.setTinNo("33161322938");
		orgVO.setCstNo("795666/Dt. 20-1-2003");
		orgVO.setAreaCode("067");
		
		
		return orgVO;
	}
	
	
	private static ProfitLossSearchRequest mockSearchRequest(){
		ProfitLossSearchRequest searchRequest = new ProfitLossSearchRequest();
		searchRequest.setStartDate(new Date());
		searchRequest.setEndDate(new Date());
		
		return searchRequest;
	}
	
	private static List<PaymentTransactionVO> mockTransactions(){
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
	
	private static IncomeExpenseVO mockIncomeExpense(){
		IncomeExpenseVO incExpenseVO = new IncomeExpenseVO();
		incExpenseVO.setExpense(10000.50);
		incExpenseVO.setIncome(150000.50);
		return incExpenseVO;
	}
	
	private static void generateReport() throws Exception{
		InputStream is = new Report().getClass().getResourceAsStream("/template/statement/payment_trans.jrxml");
        JasperReport report = JasperCompileManager.compileReport(is);
        
        JRDataSource dataSource = new ProfitLossDS(
        		mockSearchRequest(), mockOrgVO(), mockIncomeExpense(), mockTransactions());
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(
		          report, new HashMap(), dataSource);
       // JasperPrintManager.printReport(jasperPrint, true);
        JasperExportManager.exportReportToPdfFile(
		          jasperPrint, "C:\\Users\\Arun\\Desktop\\report\\profit-loss\\sample-profit-loss.pdf");
	}
	
	
	public static void main(String[] args) throws Exception{
		generateReport();
	}
}
