package com.litetech.omt.dao.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.litetech.omt.constant.ProductStatusEnum;
import com.litetech.omt.constant.ProductUnitLevelEnum;
import com.litetech.omt.dao.model.inventory.Product;
import com.litetech.omt.dao.model.inventory.ProductPrice;
import com.litetech.omt.dao.model.inventory.ProductUnit;
import com.litetech.omt.dao.model.inventory.Unit;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.UnitVO;

public class InventoryVODOMapper {
	
	public List<ProductVO> mapProductDOToVO(List<Product> productDOs){
		List<ProductVO> productVOs = new ArrayList<ProductVO>(productDOs.size());
		for(Product productDO : productDOs){
			productVOs.add(mapProductDOToVO(productDO));
		}
		
		return productVOs;
	}
	
	
	public Product mapProductVOToDO(ProductVO productVO){
		Product productDO = new Product();
		productDO.setId(productVO.getProductId());
		productDO.setCreationDate(new Date());
		populateProductDO(productDO, productVO);
		return productDO;
	}
	
	public void populateProductDO(Product productDO, ProductVO productVO){
		productDO.setProductName(productVO.getProductName());
		productDO.setProductDesc(productVO.getProductDesc());
		productDO.setProductCode(productVO.getProductCode());
		productDO.setQuantityAvl(productVO.getQuantityAvl());
		productDO.setStatus(productVO.getStatus().getId());
		productDO.setMeasurableUnit(new Unit(productVO.getMeasurableUnitVO().getId()));
		productDO.setLastModifiedDate(new Date());
	}
	
	public ProductVO mapProductDOToVO(Product productDO){
		ProductVO productVO = new ProductVO();
		productVO.setProductId(productDO.getId());
		productVO.setProductName(productDO.getProductName());
		productVO.setProductCode(productDO.getProductCode());
		productVO.setStatus(ProductStatusEnum.getById(productDO.getStatus()));
		productVO.setProductDesc(productDO.getProductDesc());
		productVO.setQuantityAvl(productDO.getQuantityAvl());
		//TODO set product price
		productVO.setPrice(getCurrentPrice(productDO.getProductPriceSet()));
		productVO.setMeasurableUnitVO(mapUnitVOToDO(productDO.getMeasurableUnit()));
		productVO.setProductUnitVOs(mapProductUnitDOToVOs(productDO.getProductUnitSet()));
		productVO.setCreationDate(productDO.getCreationDate());
		productVO.setLastModifiedDate(productDO.getLastModifiedDate());
		
		return productVO;
	}
	
	//TODO to fetch current price based on current date and enddate
	private double getCurrentPrice(Set<ProductPrice> productPrices){
		for(ProductPrice productPrice : productPrices){
			return productPrice.getPrice();
		}
		
		return 0;
	}
	
	
	private List<ProductUnitVO> mapProductUnitDOToVOs(Set<ProductUnit> productUnitDOs){
		List<ProductUnitVO> productUnitVOs = new ArrayList<ProductUnitVO>();
		for(ProductUnit productUnitDO : productUnitDOs){
			productUnitVOs.add(mapProductUnitDOToVO(productUnitDO));
		}
		return productUnitVOs;
	}
	
	
	public ProductUnit mapProductUnitVOToDO(ProductUnitVO productUnitVO){
		ProductUnit productUnitDO = new ProductUnit();
		productUnitDO.setId(productUnitVO.getId());
		productUnitDO.setLevel(productUnitVO.getLevel().getId());
		productUnitDO.setPriceRatio(productUnitVO.getPriceRatio());
		productUnitDO.setQuantityRatio(productUnitVO.getQuanityRatio());
		productUnitDO.setProductId(productUnitVO.getProductId());
		productUnitDO.setUnit(new Unit(productUnitVO.getUnitId()));
		productUnitDO.setCreationDate(new Date());
		productUnitDO.setLastModifiedDate(new Date());
		return productUnitDO;
	}
	
	private ProductUnitVO mapProductUnitDOToVO(ProductUnit productUnitDO){
		ProductUnitVO productUnitVO = new ProductUnitVO();
		productUnitVO.setId(productUnitDO.getId());
		productUnitVO.setUnitVO(mapUnitVOToDO(productUnitDO.getUnit()));
		productUnitVO.setPriceRatio(productUnitDO.getPriceRatio());
		productUnitVO.setQuanityRatio(productUnitDO.getQuantityRatio());
		productUnitVO.setLevel(ProductUnitLevelEnum.getById(productUnitDO.getLevel()));
		return productUnitVO;
	}
	
	public List<UnitVO> mapUnitVOToDOs(List<Unit> unitDOs){
		List<UnitVO> unitVOs = new ArrayList<UnitVO>();
		for(Unit unitDO : unitDOs){
			unitVOs.add(mapUnitVOToDO(unitDO));
		}
		
		return unitVOs;
	}
	
	private UnitVO mapUnitVOToDO(Unit unitDO){
		UnitVO unitVO = new UnitVO(unitDO.getId(), unitDO.getName());
		unitVO.setCreationDate(unitDO.getCreationDate());
		unitVO.setLastModifiedDate(unitDO.getLastModifiedDate());
		
		return unitVO;
	}
	
	public Unit mapUnitDOToVO(UnitVO unitVO){
		Unit unit = new Unit();
		
		unit.setId(unitVO.getId());
		unit.setName(unitVO.getName());
		unit.setCreationDate(unitVO.getCreationDate());
		unit.setLastModifiedDate(unitVO.getLastModifiedDate());
		
		return unit;
	}
	
}
