package com.litetech.omt.vo.report;

import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoiceVO;


public class InvoiceReportVO {
	
	private CustomerVO supplierVO;
	private InvoiceVO invoiceVO;
	private CustomerVO billTo;
	private CustomerVO shipTo;
		
	public CustomerVO getSupplierVO() {
		return supplierVO;
	}
	public void setSupplierVO(CustomerVO supplierVO) {
		this.supplierVO = supplierVO;
	}
	public InvoiceVO getInvoiceVO() {
		return invoiceVO;
	}
	public void setInvoiceVO(InvoiceVO invoiceVO) {
		this.invoiceVO = invoiceVO;
	}
	public CustomerVO getBillTo() {
		return billTo;
	}
	public void setBillTo(CustomerVO billTo) {
		this.billTo = billTo;
	}
	public CustomerVO getShipTo() {
		return shipTo;
	}
	public void setShipTo(CustomerVO shipTo) {
		this.shipTo = shipTo;
	}
		
	
}
