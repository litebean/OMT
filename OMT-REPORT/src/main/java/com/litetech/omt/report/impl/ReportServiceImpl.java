package com.litetech.omt.report.impl;

import java.io.FileWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.litetech.omt.report.Report;
import com.litetech.omt.report.ReportService;
import com.litetech.omt.report.ds.invoice.InvoiceDS;
import com.litetech.omt.report.ds.invoice.InvoiceFooterDS;
import com.litetech.omt.report.ds.invoice.InvoiceLineItemDS;
import com.litetech.omt.report.ds.invoice.TaxDS;
import com.litetech.omt.report.ds.transaction.ProfitLossDS;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.IncomeExpenseVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.OrgVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;

public class ReportServiceImpl implements ReportService{

	public void printInvoice(OrgVO orgVO, CustomerVO customerVO,
			OrderVO orderVO, InvoiceVO invoiceVO, String[] reportModes) {
		try{
			InputStream lineItemTemplateIS = new Report().getClass().getResourceAsStream("/jasper/invoice/sampleInvoice_subreport2.jasper");
			JasperReport lineItemReport = (JasperReport)JRLoader.loadObject(lineItemTemplateIS);
			
			InputStream taxTemplIS = new Report().getClass().getResourceAsStream("/jasper/invoice/inv-tax-template.jasper");
	    	JasperReport taxReport = (JasperReport)JRLoader.loadObject(taxTemplIS);
	    	
	    	InputStream footerTemplIS = new Report().getClass().getResourceAsStream("/jasper/invoice/inv-footer.jasper");
	    	JasperReport footerReport = (JasperReport)JRLoader.loadObject(footerTemplIS);
	    	
	    	InputStream jasperReportIS = new Report().getClass().getResourceAsStream("/jasper/invoice/invoice-template.jasper");	 
	    	JasperReport jasperReport = (JasperReport)JRLoader.loadObject(jasperReportIS);
	    	
	    	if(reportModes == null || reportModes.length == 0){
	    		reportModes = new String[1];
	    		reportModes[0] = "";
	    	}    	
	    	
    		for(int indx = 0; indx < reportModes.length; indx++){
    			JRDataSource lineItemDS = new InvoiceLineItemDS(invoiceVO.getInvoiceLineItems());
    			JRDataSource taxDS = new TaxDS(invoiceVO.getInvoiceCharges());
    			JRDataSource invFooterDS = new InvoiceFooterDS(invoiceVO.getInvoiceCharges());
    			JRDataSource dataSource = new InvoiceDS(orgVO, customerVO, orderVO, invoiceVO, reportModes[indx]);
    			
    			
    			Map<String, Object> parameters = new HashMap<String, Object>();
    	    	parameters.put("SubreportDataSource", lineItemDS);
    	    	parameters.put("SubReportParam", lineItemReport);
    	    	 
    	    	parameters.put("TaxreportDataSource", taxDS);
    	    	parameters.put("TaxReportParam", taxReport);
    	    	 
    	    	parameters.put("FooterReportDataSource", invFooterDS);
    	    	parameters.put("FooterReportParam", footerReport);
    	    	
    			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    			JasperPrintManager.printReport(jasperPrint, true);
    		}
	    	
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void printProfitLossReport(ProfitLossSearchRequest searchRequest,
			OrgVO orgVO, IncomeExpenseVO incomeExpenseVO,
			List<PaymentTransactionVO> paymentTransactionVOs) {
		try{
			InputStream is = new Report().getClass().getResourceAsStream("/jasper/statement/payment_trans.jasper");
	        JasperReport report = (JasperReport)JRLoader.loadObject(is);
	        
	        JRDataSource dataSource = new ProfitLossDS(searchRequest, orgVO, incomeExpenseVO, paymentTransactionVOs);
	        
	        JasperPrint jasperPrint = JasperFillManager.fillReport(
			          report, new HashMap(), dataSource);
	        JasperPrintManager.printReport(jasperPrint, true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	

}
