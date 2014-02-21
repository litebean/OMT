package com.litetech.omt;

import java.io.File;
import java.io.InputStream;

import net.sf.jasperreports.engine.JasperCompileManager;

public class JasperConveterTest {

	private static void generateInvJasperFile(String loc) throws Exception{
		JasperCompileManager.compileReportToFile(
	            loc+"template/invoice-template.jrxml",
	            loc+"/jasper/invoice/invoice-template.jasper");
		
		JasperCompileManager.compileReportToFile(
	            loc+"template/inv-tax-template.jrxml",
	            loc+"/jasper/invoice/inv-tax-template.jasper");
		
		JasperCompileManager.compileReportToFile(
	            loc+"template/inv-footer.jrxml",
	            loc+"/jasper/invoice/inv-footer.jasper");
		
		JasperCompileManager.compileReportToFile(
	            loc+"template/sampleInvoice_subreport2.jrxml",
	            loc+"/jasper/invoice/sampleInvoice_subreport2.jasper");
		   
	}
	
	private static void generateTransactionJasper(String loc) throws Exception{
		JasperCompileManager.compileReportToFile(
	            loc+"template/statement/payment_trans.jrxml",
	            loc+"jasper/statement/payment_trans.jasper");
	}
	
	
	public static void main(String[] args) throws Exception {
		
		String loc = "F:/product/OrderManagementTool/maven-src/omt/OMT-REPORT/src/main/resources/";
		generateInvJasperFile(loc);
		//generateTransactionJasper(loc);
	}
	
}
