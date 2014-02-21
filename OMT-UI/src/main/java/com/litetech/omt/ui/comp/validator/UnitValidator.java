package com.litetech.omt.ui.comp.validator;

import java.util.List;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.vo.UnitVO;

public class UnitValidator extends Validator{

	
	public boolean validate(UnitVO unitVO){
		boolean success = validateNotNullOrEmpty(unitVO.getName(), 
				"Please Enter Unit Name");
		if(!success){
			return success;
		}
		
		success = isUnitNameUnique(unitVO);
		if(!success){
			return success;
		}
		
		return success;
	}
	
	
	private boolean isUnitNameUnique(UnitVO unitVO){
		boolean unique = true;
		List<Long> unitIds = ServiceClient.getInstance().findUnitIds(unitVO.getName());
		for(Long unitId : unitIds){
			if(unitId != unitVO.getId()){
				displayErrorMsg("Unit with same name "+unitVO.getName() + " already exist");
				unique = false;
				break;
			}
		}		
		return unique;
	}
}
