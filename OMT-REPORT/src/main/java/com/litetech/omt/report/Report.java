package com.litetech.omt.report;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.InputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.litetech.omt.report.ds.invoice.InvoiceDS;
import com.litetech.omt.report.ds.invoice.InvoiceFooterDS;
import com.litetech.omt.report.ds.invoice.InvoiceLineItemDS;
import com.litetech.omt.report.ds.invoice.TaxDS;
import com.litetech.omt.vo.AddressVO;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoiceChargeVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.OrgVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.UnitVO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;

public class Report {

	private static List<LineItemVO> mockItems(){
		List<LineItemVO> lineItemVOs = new ArrayList<LineItemVO>();
		
		for (int i = 0; i < 10; i++) {
			LineItemVO lineItemVO = new LineItemVO();
			lineItemVO.setProductVO(new ProductVO(new Long(100+i), "Test Prouct"+i));
			lineItemVO.setLineItemDesc("This is test product desc for long type "+i);
			ProductUnitVO productUnitVO = new ProductUnitVO();
			productUnitVO.setUnitVO(new UnitVO(101, "Box"));
			lineItemVO.setUnitVO(productUnitVO);
			lineItemVO.setQuantity(10+i);
			lineItemVO.setPrice(100+i);
			lineItemVO.setTotalCost(lineItemVO.getQuantity() * lineItemVO.getPrice());
			
			lineItemVOs.add(lineItemVO);
		}
		
		return lineItemVOs;
	}
	
	
	private static List<InvoiceChargeVO> mockCharges(){
		List<InvoiceChargeVO> invoiceChargeVOs = new ArrayList<InvoiceChargeVO>();
		
		InvoiceChargeVO total1 = new InvoiceChargeVO();
		total1.setCharge(new ChargeVO(1, "Total"));
		total1.setChargedAmount(2300);
		
		InvoiceChargeVO serviceCharge = new InvoiceChargeVO();
		serviceCharge.setCharge(new ChargeVO(2, "Service Charge @ 5%"));
		serviceCharge.setChargedAmount(500);
		
		InvoiceChargeVO total2 = new InvoiceChargeVO();
		total2.setCharge(new ChargeVO(1, "Total"));
		total2.setChargedAmount(2800);
		
		InvoiceChargeVO cessCharge = new InvoiceChargeVO();
		cessCharge.setCharge(new ChargeVO(1, "Cess Charge @ 2%"));
		cessCharge.setChargedAmount(20);
		
		InvoiceChargeVO total3 = new InvoiceChargeVO();
		total3.setCharge(new ChargeVO(1, "Total"));
		total3.setChargedAmount(2820);
		
		invoiceChargeVOs.add(total1);
		invoiceChargeVOs.add(serviceCharge);
		invoiceChargeVOs.add(total2);
		invoiceChargeVOs.add(cessCharge);
		invoiceChargeVOs.add(total3);
		
		return invoiceChargeVOs;
	}
	
	
	private static Date getStringAsDate(String strDate){
		Date date = null;
			try {
				date = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return date;
	}
	
	private static InvoiceVO mockInvoice(){
		InvoiceVO invoiceVO = new InvoiceVO(false);
		invoiceVO.setId(1001);
		invoiceVO.setInvoiceDate(getStringAsDate("12-02-2013"));
		invoiceVO.setDcNo("DC123 Dt. 20-1-2002");
		
		
		
		return invoiceVO;
	}
	
	
	private static CustomerVO mockCustomer(){
		CustomerVO customerVO = new CustomerVO(1l, "Kannia Rubbers");
		AddressVO primaryAdd = new AddressVO();
		primaryAdd.setAddressLine1("No.3, Srinivasa Nagar");
		primaryAdd.setAddressLine2("Periyar ST.,");
		primaryAdd.setAddressLine3("Padi");
		
		
		primaryAdd.setCity("Chennai");
		primaryAdd.setState("Tamil Nadu");
		primaryAdd.setCountry("India");
		primaryAdd.setPincode("600050");
		primaryAdd.setPhone1("34534543");
		primaryAdd.setPhone2("9832345431");
		
		customerVO.setPrimaryAddress(primaryAdd);
		
		return customerVO;
	}
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
	
	
	private static OrderVO mockOrderVO(){
		OrderVO orderVO = new OrderVO(false);
		orderVO.setPartyOrderId("ORD-9832");
		orderVO.setPartyOrderDate(new Date());
		return orderVO;
	}
	
	
	public static void main(String[] args)
	  {
	    JasperReport jasperReport;
	    JasperPrint jasperPrint;
	    try{
	    	 InputStream is = new Report().getClass().getResourceAsStream("/template/sampleInvoice_subreport2.jrxml");
	         //JasperDesign design = JRXmlLoader.load(is);
	         JasperReport report = JasperCompileManager.compileReport(is);

	    	
	         /*InputStream subReportIs = new Report().getClass().getResourceAsStream("/datasource/invoice-line-item.csv");
	    	 JRCsvDataSource subReportDS = new JRCsvDataSource(subReportIs);	
	    	 subReportDS.setUseFirstRowAsHeader(true);
	    	 subReportDS.setFieldDelimiter(';');*/
	    	 
	         JRDataSource subReportDS = new InvoiceLineItemDS(mockItems());
	         
	    	 InputStream invTaxTemplStream = new Report().getClass().getResourceAsStream("/template/inv-tax-template.jrxml");
	    	 JasperReport invTaxReport = JasperCompileManager.compileReport(invTaxTemplStream);
	    	 
	    	 /*InputStream invTaxDSStream = new Report().getClass().getResourceAsStream("/datasource/invoice-tax.csv");
	    	 JRCsvDataSource invTaxDS = new JRCsvDataSource(invTaxDSStream);
	    	 invTaxDS.setUseFirstRowAsHeader(true);
	    	 invTaxDS.setFieldDelimiter(';');*/
	    	 JRDataSource invTaxDS = new TaxDS(mockCharges());
	    	 //invoice-tax.csv
	    	 
	    	 
	    	 
	    	 InputStream footerTemplStream = new Report().getClass().getResourceAsStream("/template/inv-footer.jrxml");
	    	 JasperReport footerReport = JasperCompileManager.compileReport(footerTemplStream);
	    	 
	    	 /*InputStream invFooterDSStream = new Report().getClass().getResourceAsStream("/datasource/inv-footer.csv");
	    	 JRCsvDataSource invFooterDS = new JRCsvDataSource(invFooterDSStream);
	    	 invFooterDS.setUseFirstRowAsHeader(true);
	    	 invFooterDS.setFieldDelimiter(';');*/
	    	 
	    	 JRDataSource invFooterDS = new InvoiceFooterDS(mockCharges());
	    	 
	    	 Map parameters = new HashMap();
	    	 parameters.put("SubreportDataSource", subReportDS);
	    	 parameters.put("SubReportParam", report);
	    	 
	    	 parameters.put("TaxreportDataSource", invTaxDS);
	    	 parameters.put("TaxReportParam", invTaxReport);
	    	 
	    	 parameters.put("FooterReportDataSource", invFooterDS);
	    	 parameters.put("FooterReportParam", footerReport);
	    	 
	    	 //String value = (java.lang.String)((((java.lang.String)"").replace("", " "))); //$JR_EXPR_ID=8$
	    	 
	    	 InputStream jasperReportIS = new Report().getClass().getResourceAsStream("/template/invoice-template.jrxml");	 
	    	 jasperReport = JasperCompileManager.compileReport(jasperReportIS);
	    	 
	    	 
	    	 /*InputStream dataSourceIS = new Report().getClass().getResourceAsStream("/datasource/invoice-ds.csv");
	    	 JRCsvDataSource dataSource = new JRCsvDataSource(dataSourceIS);*/
	      
	    	 JRDataSource dataSource = new InvoiceDS(mockOrgVO(), mockCustomer(), mockOrderVO(), mockInvoice(), null);
	        
		      /*dataSource.setUseFirstRowAsHeader(true);
		      dataSource.setFieldDelimiter(';');*/
	    	 
		      jasperPrint = JasperFillManager.fillReport(
		          jasperReport, parameters, dataSource);
		      
		     //JasperPrintManager.printReport(jasperPrint, true);
		     JasperPrintManager.printReport(jasperPrint, true);
		     
		      //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();



		    	/*JFrame frame = new JFrame("Report");
		    	frame.setPreferredSize(screenSize);
		    	
		    	JRViewer jrViewer = new JRViewer(jasperPrint);
		    	//((JPanel)jrViewer.getComponent(0)).remove(1);
		    	//jrViewer.setSize(screenSize);
		    	frame.getContentPane().add(jrViewer);
		    	frame.pack();
		    	frame.setVisible(true);*/
		      /*JasperExportManager.exportReportToPdfFile(
		          jasperPrint, "F:\\product\\OrderManagementTool\\maven-src\\omt\\OMT-REPORT\\target\\classes\\simple_report7.pdf");*/
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

}
