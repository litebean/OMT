package com.litetech.omt.vo;

import com.litetech.omt.constant.ProductUnitLevelEnum;

public class ProductUnitVO {
	
	private long id;
	private long productId;
	private double priceRatio = 1;
	private double quanityRatio = 0;
	private UnitVO unitVO;
	
	private ProductUnitLevelEnum level;
	
	public UnitVO getUnitVO() {
		return unitVO;
	}
	public void setUnitVO(UnitVO unitVO) {
		this.unitVO = unitVO;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUnitId() {
		return unitVO.getId();
	}
	
	
	public String getUnitName(){
		return unitVO.getName();
	}
	public double getPriceRatio() {
		return priceRatio;
	}
	public void setPriceRatio(double priceRatio) {
		this.priceRatio = priceRatio;
	}
	public ProductUnitLevelEnum getLevel() {
		return level;
	}
	public void setLevel(ProductUnitLevelEnum level) {
		this.level = level;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public double getQuanityRatio() {
		return quanityRatio;
	}
	public void setQuanityRatio(double quanityRatio) {
		this.quanityRatio = quanityRatio;
	}
	
	
	
	
}
