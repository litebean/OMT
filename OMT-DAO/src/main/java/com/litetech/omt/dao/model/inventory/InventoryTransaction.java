package com.litetech.omt.dao.model.inventory;

import java.io.Serializable;
import java.util.Date;

public class InventoryTransaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long productId;
	private long unitId;
	private double quantityAdded;
	private double avlQuantity;
	private long avlQuantityUnitId;
	
	private String transDesc;
	private Date creationDate;
	private Date lastModifiedDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getTransDesc() {
		return transDesc;
	}
	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
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
	public double getQuantityAdded() {
		return quantityAdded;
	}
	public void setQuantityAdded(double quantityAdded) {
		this.quantityAdded = quantityAdded;
	}
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	public double getAvlQuantity() {
		return avlQuantity;
	}
	public void setAvlQuantity(double avlQuantity) {
		this.avlQuantity = avlQuantity;
	}
	public long getAvlQuantityUnitId() {
		return avlQuantityUnitId;
	}
	public void setAvlQuantityUnitId(long avlQuantityUnitId) {
		this.avlQuantityUnitId = avlQuantityUnitId;
	}
}
