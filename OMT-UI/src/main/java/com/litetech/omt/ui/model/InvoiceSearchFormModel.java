package com.litetech.omt.ui.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.litetech.omt.constant.InvoiceStatusEnum;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.OrderVO;

public class InvoiceSearchFormModel {
	
	private Long invoiceId;
	private Date invoiceDate;
	private long customerId;
	private long status;
	private String descriptionLike;
	private List<InvoiceVO> searchResult;
	private Map<Long, String> customerMap;
	
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getDescriptionLike() {
		return descriptionLike;
	}
	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}
	
	
	public String getInvoiceDateAsString(){
		return getDateAsString(this.invoiceDate);
	}
	
	private String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}
	public List<InvoiceVO> getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(List<InvoiceVO> searchResult) {
		this.searchResult = searchResult;
	}
	public Map<Long, String> getCustomerMap() {
		return customerMap;
	}
	public void setCustomerMap(Map<Long, String> customerMap) {
		this.customerMap = customerMap;
	}
	
	
	public String[][] getResultRows(){
		String[][] rows = new String[searchResult.size()][4];
		
		int rowFilled = 0;
		for(InvoiceVO invoiceVO : searchResult){
			rows[rowFilled][0] = String.valueOf(invoiceVO.getId());
			rows[rowFilled][1] = customerMap.get(invoiceVO.getCustomerId());
			rows[rowFilled][2] = getDateAsString(invoiceVO.getInvoiceDate());
			rows[rowFilled][3] = InvoiceStatusEnum.getById(invoiceVO.getInvoiceStatus()).getName();
			rowFilled++;		
		}
		
		return rows;
	}
	
}
