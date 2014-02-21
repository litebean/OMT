package com.litetech.omt.service.impl;

import java.util.List;

import com.litetech.omt.dao.InventoryDAO;
import com.litetech.omt.service.OMTInventoryService;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.SearchProductRequestVO;
import com.litetech.omt.vo.UnitVO;

public class OMTInventoryServiceImpl implements OMTInventoryService{
	
	private InventoryDAO inventoryDAO;
	
	public OMTInventoryServiceImpl(InventoryDAO inventoryDAO){
		this.inventoryDAO = inventoryDAO;
	}
	
	@Override
	public List<ProductVO> fetchActiveProducts() {
		return inventoryDAO.retrieveActiveProducts();
	}
	
	@Override
	public List<ProductVO> fetchProducts(List<Long> productIds){
		return inventoryDAO.fetchProducts(productIds);
	}

	

	@Override
	public List<ProductVO> searchProduct(
			SearchProductRequestVO searchProductRequestVO) {
		return inventoryDAO.searchProduct(searchProductRequestVO);
	}

	@Override
	public ProductVO fetchProduct(long productId) {
		return inventoryDAO.retrieveProduct(productId);
	}

	@Override
	public List<Long> findProductIdByName(String prodName){
		return inventoryDAO.findProductIdByName(prodName);
	}
	@Override
	public List<UnitVO> retrieveAllUnits() {
		return inventoryDAO.retrieveAllUnits();
	}

	@Override
	public void saveProduct(ProductVO productVO) {
		inventoryDAO.saveProduct(productVO);		
	}

	@Override
	public void saveUnit(UnitVO unitVO) {
		inventoryDAO.saveUnit(unitVO);
	}

	@Override
	public void deleteUnit(long unitId) {
		inventoryDAO.deleteUnit(unitId);
	}

	@Override
	public List<Long> findUnitIds(String unitName){
		return inventoryDAO.findUnitIds(unitName);
	}
	
}
