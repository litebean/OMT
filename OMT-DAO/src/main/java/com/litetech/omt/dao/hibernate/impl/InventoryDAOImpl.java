package com.litetech.omt.dao.hibernate.impl;

import java.util.List;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.litetech.omt.dao.InventoryDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalInventoryDAO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.SearchProductRequestVO;
import com.litetech.omt.vo.UnitVO;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class InventoryDAOImpl implements InventoryDAO{

	private InternalInventoryDAO internalInventoryDAO;
	public InventoryDAOImpl(InternalInventoryDAO internalInventoryDAO){
		this.internalInventoryDAO = internalInventoryDAO;
	}
	
	@Override
	public List<ProductVO> retrieveActiveProducts() {
		return internalInventoryDAO.retrieveActiveProducts();
	}

	
	@Override
	public List<ProductVO> fetchProducts(List<Long> productIds) {
		return internalInventoryDAO.retrieveProducts(productIds);
	}

	@Override
	public List<ProductVO> searchProduct(SearchProductRequestVO searchProductRequestVO) {
		return internalInventoryDAO.searchProducts(searchProductRequestVO);
	}

	@Override
	public ProductVO retrieveProduct(long productId) {
		return internalInventoryDAO.retrieveProduct(productId);
	}

	@Override
	public List<Long> findProductIdByName(String prodName){
		return internalInventoryDAO.findProductIdByName(prodName);
	}
	
	@Override
	public List<UnitVO> retrieveAllUnits() {
		return internalInventoryDAO.retrieveAllUnits(); 
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveProduct(ProductVO productVO) {
		internalInventoryDAO.saveProduct(productVO);		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveUnit(UnitVO unitVO) {
		internalInventoryDAO.saveUnit(unitVO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void deleteUnit(long unitId) {
		internalInventoryDAO.deleteUnit(unitId);		
	}
	
	@Override
	public List<Long> findUnitIds(String unitName){
		return internalInventoryDAO.findUnitIds(unitName);
	}

}
