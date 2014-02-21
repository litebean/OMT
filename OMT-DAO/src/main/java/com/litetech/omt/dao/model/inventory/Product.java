package com.litetech.omt.dao.model.inventory;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Product implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private long id;
	private String productName;
	private String productDesc;
	private String productCode;
	private int status;
	private double quantityAvl;
	private Unit measurableUnit;
	private Date creationDate;
	private Date lastModifiedDate;
	private Set<ProductPrice> productPriceSet;
	private Set<ProductUnit> productUnitSet;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getQuantityAvl() {
		return quantityAvl;
	}
	public void setQuantityAvl(double quantityAvl) {
		this.quantityAvl = quantityAvl;
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
	public Unit getMeasurableUnit() {
		return measurableUnit;
	}
	public void setMeasurableUnit(Unit measurableUnit) {
		this.measurableUnit = measurableUnit;
	}
	public Set<ProductPrice> getProductPriceSet() {
		return productPriceSet;
	}
	public void setProductPriceSet(Set<ProductPrice> productPriceSet) {
		this.productPriceSet = productPriceSet;
	}
	public Set<ProductUnit> getProductUnitSet() {
		return productUnitSet;
	}
	public void setProductUnitSet(Set<ProductUnit> productUnitSet) {
		this.productUnitSet = productUnitSet;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
}
