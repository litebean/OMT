package com.litetech.omt.ui.comp.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.UnitVO;

public class ProductValidator extends Validator{
	
	public boolean validateProduct(ProductVO productVO){
		boolean success = validateNotNullOrEmpty(productVO.getProductName(), 
				"Please Enter Product Name");
		if(!success){
			return success;
		}
		
		success = validateNotNullOrEmpty(productVO.getProductCode(), 
				"Please Enter Product Code");
		if(!success){
			return success;
		}
		
		success = validateProductUnits(productVO);
		if(!success){
			return success;
		}
		
		success = validateProdNameUniqueness(productVO);
		if(!success){
			return success;
		}
		
		return success;
	}
	
	private boolean validateProdNameUniqueness(ProductVO productVO){
		List<Long> productIds = ServiceClient.getInstance().findProductIdByName(
				productVO.getProductName());
		boolean success = true;
		
		for(Long productId : productIds){
			if(productId != productVO.getProductId()){
				displayErrorMsg("Product with same name already exist "+productVO.getProductName());
				success = false;
				break;				
			}
		}		
		return success;
	}
	
	private boolean validateProductUnits(ProductVO productVO){
		boolean success = true;
		
		List<ProductUnitVO> productUnitVOs = productVO.getProductUnitVOs();
		if(productUnitVOs == null || productUnitVOs.isEmpty()){
			displayErrorMsg("Please Choose at least one product unit");
			success = false;
		}
		if(!success){
			return success;
		}
		
		List<Long> unitIds = new ArrayList<Long>();
		Set<String> duplicatedUnits = new HashSet<String>(); 
		
		for(ProductUnitVO productUnitVO : productUnitVOs){
			UnitVO unitVO = productUnitVO.getUnitVO();
			if(unitVO.getId() <= 0){
				displayErrorMsg("Please Choose a Valid Unit");
				success = false;
				break;
			}
			
			if(unitVO.getId() == productVO.getMeasurableUnitVO().getId()){
				//measurable quantity's ratio should be always one
				if(1 != productUnitVO.getQuanityRatio()){
					displayErrorMsg("Quantity Ratio for Unit "+unitVO.getName() +" should be 1");
					success = false;
					break;
				}
			}
			
			if(unitIds.contains(unitVO.getId())){
				duplicatedUnits.add(unitVO.getName());
			}else{
				unitIds.add(unitVO.getId());
			}			
		}
		
		if(!success){
			return success;
		}
		
		if(!duplicatedUnits.isEmpty()){
			displayErrorMsg("Units ratios are duplicated "+duplicatedUnits);
			success = false;
		}
		
		if(!success){
			return success;
		}
		
		//add any other validation, if any...
		return success;
	}
	
}
