package com.litetech.omt.vo;

import java.util.Date;

import javax.lang.model.type.ReferenceType;

import com.litetech.omt.constant.LineItemRefTypeEnum;

public class LineItemVO {

	private long id;
	private long refId;
	private LineItemRefTypeEnum refTypeEnum;
	private ProductVO productVO;
	private String lineItemDesc;
	private int lineItemStatus;
	private ProductUnitVO unitVO;
	private double quantity;
	private double price;
	private double totalCost;
	private double taxAmt;
	private double invoicedQty;
	private Date creationDate;
	private Date lastModifiedDate;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getRefId() {
		return refId;
	}
	public void setRefId(long refId) {
		this.refId = refId;
	}
	public LineItemRefTypeEnum getRefTypeEnum() {
		return refTypeEnum;
	}
	public void setRefTypeEnum(LineItemRefTypeEnum refTypeEnum) {
		this.refTypeEnum = refTypeEnum;
	}
	public ProductVO getProductVO() {
		return productVO;
	}
	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}
	public String getLineItemDesc() {
		return lineItemDesc;
	}
	public void setLineItemDesc(String lineItemDesc) {
		this.lineItemDesc = lineItemDesc;
	}
	public int getLineItemStatus() {
		return lineItemStatus;
	}
	public void setLineItemStatus(int lineItemStatus) {
		this.lineItemStatus = lineItemStatus;
	}
	
	
	public ProductUnitVO getUnitVO() {
		return unitVO;
	}
	public void setUnitVO(ProductUnitVO unitVO) {
		this.unitVO = unitVO;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public double getInvoicedQty() {
		return invoicedQty;
	}
	public void setInvoicedQty(double invoicedQty) {
		this.invoicedQty = invoicedQty;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	
	
}
