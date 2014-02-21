package com.litetech.omt.ui.model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.litetech.omt.constant.ProductStatusEnum;
import com.litetech.omt.constant.ProductUnitLevelEnum;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.UnitVO;

public class ProductFormModel {
	
	private List<ProductVO> productResultVOs = Collections.emptyList();
	private ProductDetailModel productDetailModel;
	
	
	public List<ProductVO> getProductResultVOs() {
		return productResultVOs;
	}
	public void setProductResultVOs(List<ProductVO> productResultVOs) {
		this.productResultVOs = productResultVOs;
	}
	

	private String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}
	
	public Object[][] getResultRows(){
		Object[][] rows = new Object[productResultVOs.size()][8];
		
		for (int i = 0; i < productResultVOs.size(); i++) {
			ProductVO productResultVO = productResultVOs.get(i);
			rows[i][0] = productResultVO.getProductId();
			rows[i][1] = productResultVO.getProductName();
			rows[i][2] = productResultVO.getQuantityAvl();
			
			String unitName = productResultVO.getMeasurableUnitVO().getName();
			rows[i][3] = unitName;
			rows[i][4] = productResultVO.getPrice();
			rows[i][5] = productResultVO.getStatus().name();
			rows[i][6] = getDateAsString(productResultVO.getLastModifiedDate());
			rows[i][7] = "";
		}
		
		return rows;
	}
	
	public ProductVO getProductVO() {
		return null;
	}
	
	
	
	public void setProductDetail(ProductVO productVO) {
		if(productDetailModel == null){
			this.productDetailModel = new ProductDetailModel();
		}
		this.productDetailModel.setProductId(String.valueOf(productVO.getProductId()));
		this.productDetailModel.setProductName(productVO.getProductName());
		this.productDetailModel.setProductDesc(productVO.getProductDesc());
		this.productDetailModel.setProductCode(productVO.getProductCode());
		
		ProductStatusEnum prodStatus = productVO.getStatus() != null ? productVO.getStatus() : ProductStatusEnum.ACTIVE;  
		this.productDetailModel.setProductStatus(prodStatus.getId());
		this.productDetailModel.setQuantityAvailable(String.valueOf(productVO.getQuantityAvl()));
		
		long measurableUnitId = productVO.getMeasurableUnitVO() != null ? productVO.getMeasurableUnitVO().getId() : 0;
		this.productDetailModel.setQuantityUnit(measurableUnitId);
		
		List<ProductUnitPriceDetail> priceDetails = getProductUnitPriceDetails(productVO);
		this.productDetailModel.setProductUnitPriceDetails(priceDetails);
					
		
	}
	
	
	private List<ProductUnitPriceDetail> getProductUnitPriceDetails(ProductVO productVO){
		List<ProductUnitPriceDetail> priceDetails = new ArrayList<ProductUnitPriceDetail>();
		double basePrice = productVO.getPrice();
		List<ProductUnitVO> productUnitVOs = productVO.getProductUnitVOs();
		
		for(ProductUnitVO productUnitVO : productUnitVOs){
			ProductUnitPriceDetail detail = new ProductUnitPriceDetail();
			detail.setProductUnitId(productUnitVO.getId());
			detail.setProductBasePrice(basePrice);
			detail.setProductUnitLevel(productUnitVO.getLevel().toString());
			detail.setUnitVO(productUnitVO.getUnitVO());
			detail.setPriceRatio(productUnitVO.getPriceRatio());
			detail.setQuantityRatio(productUnitVO.getQuanityRatio());
			priceDetails.add(detail);
		}
		
		return priceDetails;
	}
	

	public ProductDetailModel getProductDetailModel() {
		if(productDetailModel == null){
			this.productDetailModel = new ProductDetailModel();
		}
		return productDetailModel;
	}
	public void setProductDetailModel(ProductDetailModel productDetailModel) {
		this.productDetailModel = productDetailModel;
	}




	public class ProductDetailModel{
		private String productId;
		private String productName;
		private String productDesc;
		private String productCode;
		private long productStatus;
		private String quantityAvailable;
		private long quantityUnit;
		private List<ProductUnitPriceDetail> productUnitPriceDetails = Collections.emptyList();
		
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
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
		public String getProductCode() {
			return productCode;
		}
		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		public long getProductStatus() {
			return productStatus;
		}
		public void setProductStatus(long productStatus) {
			this.productStatus = productStatus;
		}
		public String getQuantityAvailable() {
			return quantityAvailable;
		}
		public void setQuantityAvailable(String quantityAvailable) {
			this.quantityAvailable = quantityAvailable;
		}
		public long getQuantityUnit() {
			return quantityUnit;
		}
		public void setQuantityUnit(long quantityUnit) {
			this.quantityUnit = quantityUnit;
		}
		public List<ProductUnitPriceDetail> getProductUnitPriceDetails() {
			return productUnitPriceDetails;
		}
		public void setProductUnitPriceDetails(
				List<ProductUnitPriceDetail> productUnitPriceDetails) {
			this.productUnitPriceDetails = productUnitPriceDetails;
		}
		
		
		public Object[][] getUnitPriceRows(){
			Object[][] rows = null;
			if(productUnitPriceDetails.isEmpty()){
				rows = new Object[2][9];
				
				rows[0] = new Object[]{0, ProductUnitLevelEnum.PRIMARY.name(), new LiteComboModel(0, "select a unit"), 1, 0.0, 0d, 0d, "", ""};
				rows[1] = new Object[]{0, ProductUnitLevelEnum.SECONDARY.name(), new LiteComboModel(0, "select a unit"), 0, 0.0, 0d, 0d, "", ""};
			}else{
				rows = new Object[productUnitPriceDetails.size()][9];
				for(int i = 0; i < productUnitPriceDetails.size(); i++){
					ProductUnitPriceDetail unitPriceDetail = productUnitPriceDetails.get(i);
					rows[i][0] = unitPriceDetail.getProductUnitId();
					rows[i][1] = unitPriceDetail.getProductUnitLevel();
					rows[i][2] = new LiteComboModel(unitPriceDetail.getUnitVO().getId(), unitPriceDetail.getUnitVO().getName());
					rows[i][3] = unitPriceDetail.getPriceRatio();
					rows[i][4] = unitPriceDetail.getProductPrice();
					rows[i][5] = unitPriceDetail.getQuantityRatio();
					rows[i][6] = (unitPriceDetail.getQuantityRatio() * (Double.valueOf(quantityAvailable)));
					
					rows[i][7] = "";
					rows[i][8] = "";
				}
			}
			
			return rows;
		}
		
		//private 
	}
	
	
	public class ProductUnitPriceDetail{
		
		private long productUnitId;
		private String productUnitLevel;
		private UnitVO unitVO;
		private double priceRatio;
		private double productBasePrice;
		private double quantityRatio;
		
		public long getProductUnitId(){ 
			return productUnitId;
		}
		
		public void setProductUnitId(long productUnitId) {
			this.productUnitId = productUnitId;
		}
		public String getProductUnitLevel() {
			return productUnitLevel;
		}
		public void setProductUnitLevel(String productUnitLevel) {
			this.productUnitLevel = productUnitLevel;
		}
		public UnitVO getUnitVO() {
			return unitVO;
		}
		public void setUnitVO(UnitVO unitVO) {
			this.unitVO = unitVO;
		}
		public double getPriceRatio() {
			return priceRatio;
		}
		public void setPriceRatio(double priceRatio) {
			this.priceRatio = priceRatio;
		}
		public double getProductPrice() {
			return productBasePrice * priceRatio;
		}
		
		private void setProductBasePrice(double productBasePrice){
			this.productBasePrice = productBasePrice; 
		}

		public double getQuantityRatio() {
			return quantityRatio;
		}

		public void setQuantityRatio(double quantityRatio) {
			this.quantityRatio = quantityRatio;
		}
		
		
		
		
	}
	
}
