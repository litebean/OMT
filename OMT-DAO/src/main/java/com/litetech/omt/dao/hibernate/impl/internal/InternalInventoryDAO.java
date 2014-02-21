package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;

import com.litetech.omt.constant.ProductStatusEnum;
import com.litetech.omt.constant.ProductUnitLevelEnum;
import com.litetech.omt.dao.mapper.InventoryVODOMapper;
import com.litetech.omt.dao.mapper.LineItemVODOMapper;
import com.litetech.omt.dao.model.inventory.Product;
import com.litetech.omt.dao.model.inventory.ProductPrice;
import com.litetech.omt.dao.model.inventory.ProductUnit;
import com.litetech.omt.dao.model.inventory.Unit;
import com.litetech.omt.util.comparator.ProductUnitComparator;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.SearchProductRequestVO;
import com.litetech.omt.vo.UnitVO;

public class InternalInventoryDAO {
	private SessionFactory sessionFactory;
	private InventoryVODOMapper inventoryVODOMapper;
	private InternalInventoryTransactionDAO inventoryTransactionDAO;
	
	public InternalInventoryDAO(SessionFactory sessionFactory,
			InventoryVODOMapper inventoryVODOMapper,
			InternalInventoryTransactionDAO inventoryTransactionDAO){
		this.sessionFactory = sessionFactory;
		this.inventoryVODOMapper = inventoryVODOMapper;
		this.inventoryTransactionDAO = inventoryTransactionDAO;
	}
	
	public List<ProductVO> retrieveActiveProducts(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchAllProducts");
		query.setInteger("status", ProductStatusEnum.ACTIVE.getId());
		List<Product> products = query.list();
		
		return inventoryVODOMapper.mapProductDOToVO(products);
	}
	
	
	public List<ProductVO> retrieveProducts(List<Long> productIds){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchProductByIds");
		query.setParameterList("ids", productIds);
		List<Product> products = query.list();
		return inventoryVODOMapper.mapProductDOToVO(products);
	}
	
	private Product retrieveProductDO(final long productId){
		Session session = sessionFactory.getCurrentSession();
		return (Product)session.load(Product.class, productId);
			
	}
	
	public ProductVO retrieveProduct(final long productId){
		Session session = sessionFactory.getCurrentSession();
		Product productDO = (Product)session.load(Product.class, productId);
		ProductVO productVO = inventoryVODOMapper.mapProductDOToVO(productDO);
		sortByProductUnitId(productVO);
		
		return productVO;
	}
	
	
	
	public List<ProductVO> searchProducts(SearchProductRequestVO searchProductRequestVO){
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Product.class, "product")
				.createAlias("product.productPriceSet", "price")
				//.createAlias("product.productUnitSet", "unit")
				.add(Restrictions.eq("product.status", searchProductRequestVO.getStatus().getId()));
				//.add(Restrictions.eq("unit.level", ProductUnitLevelEnum.PRIMARY.getId()));
		
		if(searchProductRequestVO.getProductId() > 0){
			criteria.add(Restrictions.eq("product.id", searchProductRequestVO.getProductId()));
		}
		
		if(searchProductRequestVO.getProductNameLike() != null){
			criteria.add(Restrictions.like("product.productName", searchProductRequestVO.getProductNameLike(), MatchMode.ANYWHERE));
		}
		
		/*if(searchProductRequestVO.getPrimaryUnitId() > 0){
			criteria.add(Restrictions.eq("unit.unit.id", searchProductRequestVO.getPrimaryUnitId()));
		}*/
		
		if(searchProductRequestVO.getPriceGrTh() > 0){
			criteria.add(Restrictions.ge("price.price", searchProductRequestVO.getPriceGrTh()));
		}
		
		List<Product> products = criteria.list();
		List<ProductVO> productVOs = inventoryVODOMapper.mapProductDOToVO(products);
		
		return productVOs;
	}
	
	
	public void sortByProductUnitId(ProductVO productVO){
		List<ProductUnitVO> productUnitVOs = productVO.getProductUnitVOs();
		Collections.sort(productUnitVOs, new ProductUnitComparator());
		
		productVO.setProductUnitVOs(productUnitVOs);
	}
	
	
	public List<UnitVO> retrieveAllUnits(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchAllUnits");
		List<Unit> unitDOs = query.list();
		
		return inventoryVODOMapper.mapUnitVOToDOs(unitDOs);
	}
	
	
	public void saveUnit(UnitVO unitVO){
		unitVO.setLastModifiedDate(new Date());
		Session session = sessionFactory.getCurrentSession();
		Unit unitDO = null;
		if(unitVO.getId() > 0){
			unitDO = (Unit)session.load(Unit.class, unitVO.getId());
			unitDO.setName(unitVO.getName());
			unitDO.setLastModifiedDate(unitVO.getLastModifiedDate());
		}else{
			unitVO.setCreationDate(new Date());
			unitDO = inventoryVODOMapper.mapUnitDOToVO(unitVO);
		}
		session.saveOrUpdate(unitDO);
		
		unitVO.setId(unitDO.getId());
	}
	
	
	public void deleteUnit(final long unitId){
		Session session = sessionFactory.getCurrentSession();
		Unit unitDO = new Unit(unitId);
		session.delete(unitDO);
	}
	
	
	private void saveProductDO(Product productDO){
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(productDO);
	}
	
	public void saveProduct(ProductVO productVO){
		
		boolean newProduct = productVO.getProductId() > 0 ? false : true;
		
		Product productDO = null;
		double transQty = 0;
		if(newProduct){
			productDO = inventoryVODOMapper.mapProductVOToDO(productVO);
			transQty = productDO.getQuantityAvl();
		}else{
			productDO = retrieveProductDO(productVO.getProductId());
			double prevQty = productDO.getQuantityAvl();
			double currentQty = productVO.getQuantityAvl();
			
			inventoryVODOMapper.populateProductDO(productDO, productVO);
			transQty = currentQty - prevQty;
		}
		
		saveProductDO(productDO);
		productVO.setProductId(productDO.getId());
		
		//add transaction history
		saveProductPrice(productVO.getProductId(), productVO.getPrice(), newProduct);
		saveProductUnit(productVO.getProductId(), productVO.getProductUnitVOs(), newProduct);
		
		long unitId = productVO.getMeasurableUnitVO().getId();
		//TODO - create new transaction entry
		registerNewTransaction(productVO.getProductId(), unitId,
				transQty, transQty, unitId, "Updated through Product screen");
	}
	
	
	private void registerNewTransaction(long productId, long unitId,
			double transQty, double availQty, long availQtyUnitId, String desc){
		if(transQty != 0){
			inventoryTransactionDAO.addNewTransaction(productId, unitId, transQty, 
					availQty, availQtyUnitId, desc);
		}
	}
	
	private void registerTransaction(long productId, long unitId, double changedQty, double availQty, long availQtyUnitId, 
			String desc, boolean increase){
		double transQty = changedQty;
		if(!increase){
			transQty = -1 * changedQty;
		}
		registerNewTransaction(productId, unitId, transQty, availQty, availQtyUnitId, desc);
	}
	
	
	public void manageInventoryQty(long productId, long unitId,
			double changeInQty, boolean increase, String desc){
		Product productDO = retrieveProductDO(productId);
		double qtyRatio = 1;
		Set<ProductUnit> productUnitDOs = productDO.getProductUnitSet();
		for(ProductUnit productUnitDO : productUnitDOs){
			if(unitId == productUnitDO.getUnit().getId()){
				qtyRatio = productUnitDO.getQuantityRatio();
				break;
			}
		}
		
		double availQty = productDO.getQuantityAvl();
		double changedQty = (double)(changeInQty / qtyRatio);
		
		double newAvailQty = availQty;
		if(increase){
			newAvailQty = availQty + changedQty;
		}else{
			newAvailQty = availQty - changedQty;
		}
		
		productDO.setQuantityAvl(newAvailQty); 
		//Date currentDate = new Date();
		//productDO.setLastModifiedDate(currentDate);
		saveProductDO(productDO);
		
		registerTransaction(productId, unitId, changeInQty, 
				newAvailQty, productDO.getMeasurableUnit().getId(),
				desc, increase);
	}
	
	private void saveProductPrice(final long productId, final double price, boolean isNewProduct){
		Session session = sessionFactory.getCurrentSession();
		ProductPrice productPrice = null;
		
		if(isNewProduct){
			productPrice = new ProductPrice();
			productPrice.setPrice(price);
			productPrice.setProductId(productId);
			productPrice.setCreationDate(new Date());
			productPrice.setLastModifiedDate(new Date());
			productPrice.setStartDate(new Date());
		}else{
			Query query = session.getNamedQuery("findProductPrice");
			query.setParameter("productId", productId);
			
			List<ProductPrice> prices = query.list();
			
			//As of now only one price is supported
			productPrice = prices.get(0);
			productPrice.setPrice(price);
			productPrice.setLastModifiedDate(new Date());
		}
		
		session.saveOrUpdate(productPrice);
	}
	
	
	private void saveProductUnit(long productId, List<ProductUnitVO> productUnitVOs, boolean isNewProduct){
		insertOrUpdateProductUnit(productId, productUnitVOs);
		if(!isNewProduct){
			deleteUnusedProductRefs(productId, productUnitVOs);
		}
	}
	
	private void deleteUnusedProductRefs(long productId, List<ProductUnitVO> requiredProductUnitVOs){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchProductUnits");
		query.setParameter("productId", productId);
		
		List<ProductUnit> productUnitDOs = query.list();
		for(ProductUnit productUnitDO : productUnitDOs){
			if(!contains(requiredProductUnitVOs, productUnitDO.getId())){
				session.delete(productUnitDO);
			}
		}
	}
	
	
	private boolean contains(List<ProductUnitVO> productUnitVOs, long productUnitId){
		for(ProductUnitVO productUnitVO : productUnitVOs){
			if(productUnitId == productUnitVO.getId()){
				return true;
			}
		}
		
		return false;
	}
	
	
	private void insertOrUpdateProductUnit(long productId, List<ProductUnitVO> productUnitVOs){
		Session session = sessionFactory.getCurrentSession();
		for(ProductUnitVO productUnitVO : productUnitVOs){
			ProductUnit productUnit = inventoryVODOMapper.mapProductUnitVOToDO(productUnitVO);
			productUnit.setProductId(productId);
			session.saveOrUpdate(productUnit);
			
			productUnitVO.setId(productUnit.getId());
		}
	}
	
	public List<Long> findProductIdByName(String prodName){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findProductIdsByName");
		query.setParameter("productName", prodName);
		
		
		List<Long> productIds = query.list();
		return productIds;
	}
	
	
	public List<Long> findUnitIds(String unitName){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findUnitIdsByName");
		query.setParameter("name", unitName);
		
		List<Long> unitIds = query.list();
		return unitIds;
	}
	
	
}
