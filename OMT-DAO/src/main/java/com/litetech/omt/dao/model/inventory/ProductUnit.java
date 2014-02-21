package com.litetech.omt.dao.model.inventory;

import java.io.Serializable;
import java.util.Date;

public class ProductUnit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long productId;
	private Unit unit;
	private int level;
	private double priceRatio;
	private double quantityRatio;
	private Date creationDate;
	private Date lastModifiedDate;
	
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public double getPriceRatio() {
		return priceRatio;
	}
	public void setPriceRatio(double priceRatio) {
		this.priceRatio = priceRatio;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public double getQuantityRatio() {
		return quantityRatio;
	}
	public void setQuantityRatio(double quantityRatio) {
		this.quantityRatio = quantityRatio;
	}
	
	
	
	
	
	
}
