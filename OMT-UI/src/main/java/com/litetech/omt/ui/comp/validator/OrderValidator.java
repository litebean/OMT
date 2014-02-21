package com.litetech.omt.ui.comp.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.OrderVO;

public class OrderValidator extends Validator{
	
	public boolean validate(OrderVO orderVO){
		boolean success = validateNotNullOrEmpty(orderVO.getOrderDate(), "Please Enter Order Date");
		if(!success){
			return success;
		}
		
		success = validateLong(orderVO.getCustomerId(), "Please Select Customer");
		if(!success){
			return success;
		}
		
		success = validateLineItem(orderVO.getOrderLineItems());
		if(!success){
			return success;
		}
		
		//add any future validation here..
		
		return success;
	}
	
	
	public boolean validateToBeInvoicedItems(List<LineItemVO> lineItemVOs){
		boolean success = true;
		if(lineItemVOs == null || lineItemVOs.isEmpty()){
			displayErrorMsg("Please choose valid line item for invoicing");
			success = false;
		}
		
		return success;
	}
	protected boolean validateLineItem(List<LineItemVO> lineItemVOs){
		boolean success = true;
		if(lineItemVOs == null || lineItemVOs.isEmpty()){
			displayErrorMsg("Please choose at least one valid line item");
			success = false;
		}else {
			if(lineItemVOs.size() > 16){
				displayErrorMsg("The line item exceeded maximum count 12 ");
				success = false;
			}else{
				Set<String> duplicateProds = findDuplicateProductUnits(lineItemVOs);
				if(!duplicateProds.isEmpty()){
					displayErrorMsg("Duplicated Product Unit combinations found for "+duplicateProds);
					success = false;
				}		
			}
		}
		
		return success;
	}
	
	private Set<String> findDuplicateProductUnits(List<LineItemVO> lineItemVOs){
		Set<String> dupProdUnits = new HashSet<String>();
		List<String> prodUnitIds = new ArrayList<String>();
		for(LineItemVO lineItemVO : lineItemVOs){
			String prodUnitIdStr = lineItemVO.getProductVO().getProductId()+"-"+
					lineItemVO.getUnitVO().getUnitId();
			if(!prodUnitIds.contains(prodUnitIdStr)){
				prodUnitIds.add(prodUnitIdStr);
			}else{
				String prodUnitName = lineItemVO.getProductVO().getProductName() +"-"+
						lineItemVO.getUnitVO().getUnitName();
				dupProdUnits.add(prodUnitName);
			}
		}
		return dupProdUnits;
	}
	
}
