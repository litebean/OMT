package com.litetech.omt.report.ds.invoice;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.litetech.omt.report.ds.BaseDS;
import com.litetech.omt.util.StringUtil;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.OrgVO;

public class InvoiceDS extends BaseDS{

	private InvoiceVO invoiceVO;
	private OrgVO orgVO;
	private CustomerVO customerVO;
	private OrderVO orderVO;
	private String reportMode;
	
	public InvoiceDS(OrgVO orgVO, CustomerVO customerVO,
			OrderVO orderVO, InvoiceVO invoiceVO, String reportMode){
		this.orgVO = orgVO;
		this.customerVO = customerVO;
		this.invoiceVO = invoiceVO;
		this.orderVO = orderVO;
		this.reportMode = reportMode;
	}
	
	
	public String getReportMode(){
		String mode = "";
		if(reportMode != null){
			mode = reportMode;
		}
		return mode;
	}
	
	
	public String getOrgName(){
		return orgVO.getName();
	}
	
	public String getOrgHeaderKey1(){
		return orgVO.getOrgHeader1();
	}
	
	public String getOrgHeaderKey2(){
		return orgVO.getOrgHeader2();
	}
	
	public String getOrgHeaderKey3(){
		return orgVO.getOrgHeader3();
	}
	
	public String getOrgHeaderKey4(){
		return orgVO.getOrgHeader4();
	}
	
	
	public String getOrgSSINo(){
		return orgVO.getSsiNo();
	}
	
	public String getOrgTINNo(){
		return orgVO.getTinNo();
	}
	
	public String getOrgCSTNo(){
		return orgVO.getCstNo();
	}
	
	public String getOrgAreaCode(){
		return orgVO.getAreaCode();
	}
	
	//invoiceId;invoiceDate;yourOderNo;yourOrderDate;dcNoDate;
	public String getInvoiceId(){
		return String.valueOf(invoiceVO.getId());
	}
	
	
	
	public String getInvoiceDate(){
		return getDateAsString(invoiceVO.getInvoiceDate());
	}
	
	public String getYourOderNo(){
		return orderVO.getPartyOrderId();
	}
	
	public String getYourOrderDate(){
		return getDateAsString(orderVO.getPartyOrderDate());
	}
	
	public String getDcNoDate(){
		//return invoiceVO.getDcNo();
		String text = "";
		String dcNo = invoiceVO.getDcNo();
		if(dcNo != null){
			text += dcNo;
			String dcDate = getDateAsString(invoiceVO.getDcDate());
			if(dcDate != null){
				text += "/"+dcDate;
			}
		}
		
		return text;
	}
	
	//;;custAddLine2;custAddLine3;cust-city-state;cust-country-pincode;
	public String getCustomerName(){
		return customerVO.getName();
	}
	
	public String getCustAddLine1(){
		return customerVO.getPrimaryAddress().getAddressLine1();
	}
	
	public String getCustAddLine2(){
		return customerVO.getPrimaryAddress().getAddressLine2();
	}
	
	public String getCustAddLine3(){
		return customerVO.getPrimaryAddress().getAddressLine3();
	}
	
	public String getCustCityState(){
		return customerVO.getPrimaryAddress().getCity() +", " +
				customerVO.getPrimaryAddress().getState();
	}
	public String getCustCountryPincode(){
		return customerVO.getPrimaryAddress().getCountry() + ", "+
				customerVO.getPrimaryAddress().getPincode();
	}
	
	//custPhoneNos;customerTin;vendorCode;
	public String getCustPhoneNos(){
		String text = customerVO.getPrimaryAddress().getPhone1();
		if(!StringUtil.isNullOrEmpty(customerVO.getPrimaryAddress().getPhone2())){
			text += ", "+customerVO.getPrimaryAddress().getPhone2();
		}
		return text;
	}
	
	public String getCustomerTin(){
		return customerVO.getTinNo();
	}
	public String getVendorCode(){
		return customerVO.getVendorCode();
	}
	

	
	public String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}

}
