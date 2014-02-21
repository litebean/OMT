package com.litetech.omt.vo;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.litetech.omt.constant.ProductStatusEnum;

public class ProductVO {
	
	public ProductVO(){
		
	}
	
	public ProductVO(long productId, String productName){
		this.productId = productId;
		this.productName = productName;
	}
	
	private long productId;
	private String productName;
	private String productDesc;
	private String productCode;
	private ProductStatusEnum status;
	private double price;
	private double quantityAvl;
	private UnitVO measurableUnitVO;
	private List<ProductUnitVO> productUnitVOs = Collections.emptyList();
	private Date creationDate;
	private Date lastModifiedDate;
	private Map<Long, Double> orderedPrice;
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public UnitVO getMeasurableUnitVO() {
		return measurableUnitVO;
	}

	public void setMeasurableUnitVO(UnitVO measurableUnitVO) {
		this.measurableUnitVO = measurableUnitVO;
	}

	public List<ProductUnitVO> getProductUnitVOs() {
		return productUnitVOs;
	}

	public void setProductUnitVOs(List<ProductUnitVO> productUnitVOs) {
		this.productUnitVOs = productUnitVOs;
	}
	
	
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public ProductStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ProductStatusEnum status) {
		this.status = status;
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

	
	
	public double getQuantityAvl() {
		return quantityAvl;
	}

	public void setQuantityAvl(double quantityAvl) {
		this.quantityAvl = quantityAvl;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ProductVO){
			ProductVO productVO2 = (ProductVO)obj;
			return this.productId == productVO2.getProductId();
		}
		return false;
	}

	public Map<Long, Double> getOrderedPrice() {
		return orderedPrice;
	}

	public void setOrderedPrice(Map<Long, Double> orderedPrice) {
		this.orderedPrice = orderedPrice;
	}
	
	
	
	
}
