package com.litetech.omt.dao.mapper;

import com.litetech.omt.constant.LineItemRefTypeEnum;
import com.litetech.omt.dao.model.core.LineItem;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.ProductUnitVO;

public class LineItemVODOMapper {

	public LineItem mapVOToDO(LineItemVO lineItemVO){
		LineItem lineItemDO = new LineItem();
		populateDO(lineItemDO, lineItemVO);
		lineItemDO.setLastModifiedDate(lineItemVO.getLastModifiedDate());
		lineItemDO.setCreationDate(lineItemVO.getCreationDate());
		return lineItemDO;
	}
	
	
	public LineItemVO mapDOToVO(LineItem lineItem){
		LineItemVO lineItemVO = new LineItemVO();
		lineItemVO.setId(lineItem.getId());
		lineItemVO.setLineItemDesc(lineItem.getLineItemDesc());
		lineItemVO.setLineItemStatus(lineItem.getLineItemStatus());
		lineItemVO.setPrice(lineItem.getPrice());
		lineItemVO.setTotalCost(lineItem.getTotalCost());
		//lineItemVO.setProductVO(new ProductVO(lineItem.getProductId(), "productName"));
		lineItemVO.setQuantity(lineItem.getQuantity());
		lineItemVO.setInvoicedQty(lineItem.getInvoicedQty());
		lineItemVO.setRefId(lineItem.getRefId());
		lineItemVO.setRefTypeEnum(LineItemRefTypeEnum.getById(lineItem.getRefType()));
		lineItemVO.setTaxAmt(lineItem.getTaxAmt());
		//lineItemVO.setUnitVO(new ProductUnitVO(lineItem.getUnitId(), "unitName"));
		lineItemVO.setLastModifiedDate(lineItem.getLastModifiedDate());
		lineItemVO.setCreationDate(lineItem.getCreationDate());
		
		return lineItemVO;
	}
	
	
	public void populateDO(LineItem lineItemDO, LineItemVO lineItemVO){
		lineItemDO.setId(lineItemVO.getId());
		lineItemDO.setLineItemDesc(lineItemVO.getLineItemDesc());
		lineItemDO.setLineItemStatus(lineItemVO.getLineItemStatus());
		lineItemDO.setPrice(lineItemVO.getPrice());
		lineItemDO.setTotalCost(lineItemVO.getTotalCost());
		lineItemDO.setProductId(lineItemVO.getProductVO().getProductId());
		lineItemDO.setQuantity(lineItemVO.getQuantity());
		lineItemDO.setInvoicedQty(lineItemVO.getInvoicedQty());
		lineItemDO.setRefId(lineItemVO.getRefId());
		lineItemDO.setRefType(lineItemVO.getRefTypeEnum().getId());
		lineItemDO.setTaxAmt(lineItemVO.getTaxAmt());
		lineItemDO.setUnitId(lineItemVO.getUnitVO().getUnitId());
		
	}
}
