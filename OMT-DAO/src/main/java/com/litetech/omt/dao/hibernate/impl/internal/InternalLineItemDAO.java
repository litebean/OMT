package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.litetech.omt.constant.LineItemRefTypeEnum;
import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.dao.mapper.LineItemVODOMapper;
import com.litetech.omt.dao.model.core.LineItem;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;


public class InternalLineItemDAO {
	
	private SessionFactory sessionFactory;
	private LineItemVODOMapper lineItemVODOMapper;
	private InternalInventoryDAO internalInventoryDAO;
	
	public InternalLineItemDAO(SessionFactory sessionFactory,
			LineItemVODOMapper lineItemVODOMapper, 
			InternalInventoryDAO internalInventoryDAO){
		this.sessionFactory = sessionFactory;
		this.lineItemVODOMapper = lineItemVODOMapper;
		this.internalInventoryDAO = internalInventoryDAO;
	}
	
	private void updateOrderStatus(OrderStatusEnum status, long orderId, boolean purchase){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("updateOrderStatus");
		if(purchase){
			query = session.getNamedQuery("updatePurchaseOrderStatus");
		}
		query.setInteger("orderStatus", status.getId());
		query.setLong("id", orderId);
		query.executeUpdate();
	}
	
	
	private Map<String, Double> resetInvoiceQty(List<LineItem> orderLineItemDOs, List<LineItem> existingInvLineItemDOs){
		Map<String, Double> resetProductQtyMap = new HashMap<String, Double>();
		
		Session session = sessionFactory.getCurrentSession();
		for(LineItem existingInvLineItemDO : existingInvLineItemDOs){
			for(LineItem orderLineItemDO : orderLineItemDOs){
				
				//reduce qty if product and unit are the same
				if(orderLineItemDO.getProductId() == existingInvLineItemDO.getProductId() 
						&& orderLineItemDO.getUnitId() == existingInvLineItemDO.getUnitId()){
					
					double invQty = orderLineItemDO.getInvoicedQty() - existingInvLineItemDO.getQuantity();
					invQty = invQty >= 0 ? invQty : 0;
					orderLineItemDO.setInvoicedQty(invQty);
					session.saveOrUpdate(orderLineItemDO);
					
					String key = existingInvLineItemDO.getProductId() + "-"+ existingInvLineItemDO.getUnitId();
					resetProductQtyMap.put(key, existingInvLineItemDO.getQuantity());
					
					break;
				}
			}
		}
		
		return resetProductQtyMap;
	}
	
	
	
	private Map<String, Double> updateInvoiceQty(List<LineItem> orderLineItemDOs, List<LineItemVO> invLineItemVOs){
		Map<String, Double> updateProductQtyMap = new HashMap<String, Double>();
		
		Session session = sessionFactory.getCurrentSession();
		for(LineItemVO invLineItemVO : invLineItemVOs){
			for(LineItem orderLineItemDO : orderLineItemDOs){
				
				//reduce qty if product and unit are the same
				if(orderLineItemDO.getProductId() == invLineItemVO.getProductVO().getProductId() 
						&& orderLineItemDO.getUnitId() == invLineItemVO.getUnitVO().getUnitId()){
					
					double invQty = orderLineItemDO.getInvoicedQty() + invLineItemVO.getQuantity();
					orderLineItemDO.setInvoicedQty(invQty);
					session.saveOrUpdate(orderLineItemDO);
					
					String key = orderLineItemDO.getProductId() + "-"+ orderLineItemDO.getUnitId();
					updateProductQtyMap.put(key, invLineItemVO.getQuantity());
					break;
				}
			}
		}
		
		return updateProductQtyMap;
	}
	
	
	private void updateOrderStatus(long orderId, boolean purchase, 
			List<LineItem> lineItemDOs){
		boolean noneInvoiced = true;
		boolean allInvoiced = true;
		for(LineItem lineItemDO : lineItemDOs){
			if(lineItemDO.getInvoicedQty() > 0){
				noneInvoiced = false;
			}	
			if(lineItemDO.getQuantity() > lineItemDO.getInvoicedQty()){
				allInvoiced = false;
			}
		}
			
		if(noneInvoiced){
			updateOrderStatus(OrderStatusEnum.PENDING, orderId, purchase);
		}else if(allInvoiced){
			updateOrderStatus(OrderStatusEnum.COMPLETED, orderId, purchase);
		}else{
			updateOrderStatus(OrderStatusEnum.PARTIAL_INVOCIED, orderId, purchase);
		}
	}
	
	private void updateLineItems(List<LineItemVO> lineItemVOs, List<LineItem> existingLineItemDOs,
			final long refId, final LineItemRefTypeEnum refTypeEnum, LineItemRefTypeEnum parentRefType){
		Session session = sessionFactory.getCurrentSession();
		List<Long> updatedLineItemIds = new ArrayList<Long>();
		
		//add new line items
		for(LineItemVO lineItemVO : lineItemVOs){
			LineItem lineItemDO = null;
			for(LineItem exLineItem : existingLineItemDOs){
				if(lineItemVO.getProductVO().getProductId() == exLineItem.getProductId() &&
						lineItemVO.getUnitVO().getUnitId() == exLineItem.getUnitId()){
					
					exLineItem.setLastModifiedDate(new Date());
					exLineItem.setLineItemDesc(lineItemVO.getLineItemDesc());
					exLineItem.setPrice(lineItemVO.getPrice());
					exLineItem.setQuantity(lineItemVO.getQuantity());
					exLineItem.setTotalCost(lineItemVO.getTotalCost());
					updatedLineItemIds.add(exLineItem.getId());
					lineItemDO = exLineItem;
				}
			}
			if(lineItemDO == null){
				lineItemVO.setRefId(refId);
				lineItemVO.setRefTypeEnum(refTypeEnum);
				lineItemDO = lineItemVODOMapper.mapVOToDO(lineItemVO);				
			}
			session.saveOrUpdate(lineItemDO);
			lineItemVO.setId(lineItemDO.getId());
		}
		
		//delete unreferenced items
		for(LineItem exLineItem : existingLineItemDOs){
			if(!updatedLineItemIds.contains(exLineItem.getId())){
				session.delete(exLineItem);
			}
		}
	}
	
	
	
	
	public void persistLineItems(List<LineItemVO> lineItemVOs, final long refId, 
			final LineItemRefTypeEnum refTypeEnum, final long parentRefId){
		List<LineItem> existingLineItemDOs = retrieveLineItems(refId, refTypeEnum);
		
		LineItemRefTypeEnum parentRefType = null;
		List<LineItem> orderLineItemDOs = null;
		if(parentRefId > 0 && (LineItemRefTypeEnum.INVOICE.equals(refTypeEnum) || LineItemRefTypeEnum.PURCHASE_INVOICE.equals(refTypeEnum))){
			parentRefType = LineItemRefTypeEnum.INVOICE.equals(refTypeEnum) ? LineItemRefTypeEnum.ORDER :
				LineItemRefTypeEnum.PURCHASE_ORDER;
			orderLineItemDOs = retrieveLineItems(parentRefId, parentRefType);
		}
		
		
		//reset order inv qty
		Map<String, Double> prevInvProdQuanties = Collections.emptyMap(); 
		if(parentRefType != null){
			prevInvProdQuanties = resetInvoiceQty(orderLineItemDOs, existingLineItemDOs);
		}
		
		//update line item
		updateLineItems(lineItemVOs, existingLineItemDOs, refId, refTypeEnum, parentRefType);
		
		long orderId = refId;
		LineItemRefTypeEnum orderRefType = refTypeEnum;
		Map<String, Double> latestInvProdQuanties = Collections.emptyMap();
		if(parentRefType != null){
			latestInvProdQuanties = updateInvoiceQty(orderLineItemDOs, lineItemVOs);
			orderId = parentRefId;
			orderRefType = parentRefType;
		}		
		boolean purchase = LineItemRefTypeEnum.PURCHASE_ORDER.equals(orderRefType) ? true : false;
		List<LineItem> ordLineItemDOs = retrieveLineItems(orderId, orderRefType);
		
		updateOrderStatus(orderId, purchase, ordLineItemDOs);
		
		if(parentRefType != null){
			manageInventory(prevInvProdQuanties, latestInvProdQuanties, refId,
					purchase);
		}
	}
	
	private void manageInventory(Map<String, Double> prevInvProdQuanties, 
			Map<String, Double> latestInvProdQuanties, boolean purchase, String desc){
		Set<String> prevInvProdUnits = prevInvProdQuanties.keySet();
		for(String prevInvProdUnit : prevInvProdUnits){
			Double prevQty = prevInvProdQuanties.get(prevInvProdUnit);
			Double newQty = latestInvProdQuanties.get(prevInvProdUnit);
			String[] productUnitIds = prevInvProdUnit.split("-");
			Long productId = new Long(productUnitIds[0]);
			Long unitId = new Long(productUnitIds[1]);
			double changeInQty = 0;
			boolean increase = true;
			if(newQty != null){
				if(newQty > prevQty){
					changeInQty = newQty - prevQty;
					increase = false;				
				}else if(newQty < prevQty){
					changeInQty = prevQty - newQty;
					increase = true;					
				}				
			}else{
				changeInQty = prevQty;
				increase = true;				
			}
			if(changeInQty != 0){
				
				//if it is purchase, the invoiced qty needs to be added 
				//else qty needs to be substracted
				boolean operation = purchase ? !increase : increase;
				internalInventoryDAO.manageInventoryQty(productId, unitId, changeInQty, operation, desc);
			}
		}
		
		Set<String> latestInvProdUnits = latestInvProdQuanties.keySet();
		latestInvProdUnits.removeAll(prevInvProdUnits);
		
		//now the set contains only prod units which are added newly to the invoice
		for(String latestInvProdUnit : latestInvProdUnits){
			String[] productUnitIds = latestInvProdUnit.split("-");
			Long productId = new Long(productUnitIds[0]);
			Long unitId = new Long(productUnitIds[1]);
			
			Double newQty = latestInvProdQuanties.get(latestInvProdUnit);
			//new qty, reduce (newQty * quantity ration) from quantity available
			boolean operation = purchase ? true : false;
			if(newQty > 0){
				internalInventoryDAO.manageInventoryQty(productId, unitId, newQty, operation, desc);
			}
		}
	}
	
	private void manageInventory(Map<String, Double> prevInvProdQuanties, 
			Map<String, Double> latestInvProdQuanties, long invoiceId, boolean purchase){
		String desc = "Invoiced Quantity changed For Invoice "+invoiceId;
		manageInventory(prevInvProdQuanties, latestInvProdQuanties, purchase, desc);
	}
	
	public void detachInvoiceFromOrder(long invoiceId, long orderId, boolean purchase){
		
		LineItemRefTypeEnum invRefTypeEnum = LineItemRefTypeEnum.INVOICE;
		LineItemRefTypeEnum orderRefTypeEnum = LineItemRefTypeEnum.ORDER;
		
		if(purchase){
			invRefTypeEnum = LineItemRefTypeEnum.PURCHASE_INVOICE;
			orderRefTypeEnum = LineItemRefTypeEnum.PURCHASE_ORDER;
		}
		
		List<LineItem> existingLineItemDOs = retrieveLineItems(invoiceId, invRefTypeEnum);
		List<LineItem> orderLineItemDOs = retrieveLineItems(orderId, orderRefTypeEnum);
		
		Map<String, Double> resetInvoiceQties = resetInvoiceQty(orderLineItemDOs, existingLineItemDOs);
		Map<String, Double> latestInvoiceQties = new HashMap<String, Double>(); 
		updateOrderStatus(orderId, purchase, orderLineItemDOs);
		
		manageInventory(resetInvoiceQties, latestInvoiceQties, purchase,
				"Invoice "+invoiceId+" Detached from Order "+orderId);
	}
	
	
	public List<LineItemVO> retrieveLineItemVOs(final long refId, LineItemRefTypeEnum refType){
		List<LineItem> lineItemDOs = retrieveLineItems(refId, refType);
		List<LineItemVO> lineItemVOs = new ArrayList<LineItemVO>();
		
		for(LineItem lineItem : lineItemDOs){
			LineItemVO lineItemVO = lineItemVODOMapper.mapDOToVO(lineItem);
			ProductVO productVO = internalInventoryDAO.retrieveProduct(lineItem.getProductId());
			lineItemVO.setProductVO(productVO);
			
			lineItemVO.setUnitVO(getUnitVO(lineItem.getUnitId(), productVO.getProductUnitVOs()));
			lineItemVOs.add(lineItemVO);
		}
		return lineItemVOs;
	}
	
	
	private ProductUnitVO getUnitVO(final long requiredUnitId, List<ProductUnitVO> productUnitVOs){
		for(ProductUnitVO productUnitVO : productUnitVOs){
			if(productUnitVO.getUnitId() == requiredUnitId){
				return productUnitVO;
			}
		}
		return null;
	}
	
	private List<LineItem> retrieveLineItems(final long refId, final LineItemRefTypeEnum refType){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findByRefIdAndType");
		query.setLong("refId", refId);
		query.setInteger("refType", refType.getId());
		return query.list();
	}
	
}
