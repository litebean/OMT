package com.litetech.omt.dao;

import java.util.List;

import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.SearchProductRequestVO;
import com.litetech.omt.vo.UnitVO;

public interface InventoryDAO {
	
	public List<ProductVO> retrieveActiveProducts();
	public List<ProductVO> fetchProducts(List<Long> productIds);
	public List<ProductVO> searchProduct(SearchProductRequestVO searchProductRequestVO);
	public ProductVO retrieveProduct(final long productId);
	public List<Long> findProductIdByName(String prodName);
	
	public List<UnitVO> retrieveAllUnits();
	public void saveProduct(ProductVO productVO);
	public void saveUnit(UnitVO unitVO);
	public void deleteUnit(final long unitId);
	public List<Long> findUnitIds(String unitName);
}
