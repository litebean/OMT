package com.litetech.omt.service;

import java.util.List;

import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.SearchProductRequestVO;
import com.litetech.omt.vo.UnitVO;

public interface OMTInventoryService {

	public List<ProductVO> fetchActiveProducts();
	public List<ProductVO> fetchProducts(List<Long> productIds);
	public List<ProductVO> searchProduct(SearchProductRequestVO searchProductRequestVO);
	public ProductVO fetchProduct(final long productId);
	public List<Long> findProductIdByName(String prodName);
	
	public List<UnitVO> retrieveAllUnits();
	public void saveProduct(ProductVO productVO);
	
	public void saveUnit(final UnitVO unitVO);
	public void deleteUnit(final long unitId);
	
	public List<Long> findUnitIds(String unitName);
}
