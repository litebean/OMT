package com.litetech.omt.vo;

import com.litetech.omt.constant.ProductStatusEnum;

public class SearchProductRequestVO {

	private long productId;
	private String productNameLike;
	private ProductStatusEnum status;
	private long primaryUnitId;
	private double priceGrTh;
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductNameLike() {
		return productNameLike;
	}
	public void setProductNameLike(String productNameLike) {
		this.productNameLike = productNameLike;
	}
	public ProductStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ProductStatusEnum status) {
		this.status = status;
	}
	public long getPrimaryUnitId() {
		return primaryUnitId;
	}
	public void setPrimaryUnitId(long primaryUnitId) {
		this.primaryUnitId = primaryUnitId;
	}
	public double getPriceGrTh() {
		return priceGrTh;
	}
	public void setPriceGrTh(double priceGrTh) {
		this.priceGrTh = priceGrTh;
	}	
	
}
