package com.litetech.omt.dao.mapper;

import java.util.Date;

import com.litetech.omt.constant.ChargeOperationEnum;
import com.litetech.omt.dao.model.core.Charge;
import com.litetech.omt.vo.ChargeVO;

public class ChargeVODOMapper {

	
	public ChargeVO mapChargeDOTOVO(Charge chargeDO){
		ChargeVO chargeVO = new ChargeVO(chargeDO.getId());
		chargeVO.setName(chargeDO.getName());
		chargeVO.setOperation(ChargeOperationEnum.getEnumById(chargeDO.getOperation()));
		chargeVO.setPercentValue(chargeDO.getPercent());
		//chargeVO.setApplyOnCharge(chargeDO.getApplyOn());
		return chargeVO;
	}
	
	
	
	public Charge mapChargeVOToDO(ChargeVO chargeVO){
		Charge chargeDO = new Charge();
		chargeDO.setId(chargeVO.getId());
		chargeDO.setCreationDate(chargeVO.getCreationDate());
		chargeDO.setLastModifiedDate(chargeVO.getLastModifiedDate());
		populateDOValues(chargeVO, chargeDO);
		
		return chargeDO;
	}
	
	public void populateDOValues(ChargeVO chargeVO, Charge chargeDO){
		chargeDO.setName(chargeVO.getName());
		chargeDO.setPercent(chargeVO.getPercentValue());
		chargeDO.setOperation(chargeVO.getOperation().getId());
		if(chargeVO.getApplyOnCharge() == null){
			chargeDO.setApplyOn(-1);
		}else{
			chargeDO.setApplyOn(chargeVO.getApplyOnCharge().getId());
		}
	}
}
