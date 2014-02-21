package com.litetech.omt.ui.model.mapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.OrderFormModel;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.ProductUnitVO;

public class OrderModelVOMapper {


	
	public OrderFormModel mapOrderVOToModel(OrderVO orderVO){
		OrderFormModel orderFormModel = new OrderFormModel(orderVO.isPurchase());
		orderFormModel.setOrderId(orderVO.getId());
		orderFormModel.setOrderDate(orderVO.getOrderDate());
		orderFormModel.setOrderStatus(orderVO.getOrderStatus());
		orderFormModel.setPartyOrderId(orderVO.getPartyOrderId());
		orderFormModel.setPartyOrderDate(orderVO.getPartyOrderDate());
		orderFormModel.setLineItemLs(orderVO.getOrderLineItems());
		orderFormModel.setInvoiceLs(orderVO.getInvoices());
		orderFormModel.setCustomerId(orderVO.getCustomerId());
		orderFormModel.setUnitCombiMap(getLineItemsProductUnits(orderVO.getOrderLineItems()));
		return orderFormModel;
	}
	
	
	private Map<Integer, List<LiteComboModel>> getLineItemsProductUnits(List<LineItemVO> lineItemVOs){
		Map<Integer, List<LiteComboModel>> unitComboMap = new TreeMap<Integer, List<LiteComboModel>>();
		
		int index = 0;
		for(LineItemVO lineItemVO : lineItemVOs){
			List<ProductUnitVO> unitVOs = lineItemVO.getProductVO().getProductUnitVOs();
			List<LiteComboModel> liteComboModels = constructUnitComboBoxModel(unitVOs);
			unitComboMap.put(index, liteComboModels);
			
			index++;
		}
		
		return unitComboMap;
	}
	
	private Vector<LiteComboModel> constructUnitComboBoxModel(List<ProductUnitVO> unitVOs){
		Vector<LiteComboModel> unitLs = new Vector<LiteComboModel>();
		if(unitVOs != null && !unitVOs.isEmpty()){
			for(ProductUnitVO unitVO: unitVOs){
				unitLs.add(new LiteComboModel(unitVO.getUnitId(), unitVO.getUnitName(), unitVO.getPriceRatio()));
			}
		}
		return unitLs;
	}
	
}
