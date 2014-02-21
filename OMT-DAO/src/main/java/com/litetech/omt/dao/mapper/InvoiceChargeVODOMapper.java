package com.litetech.omt.dao.mapper;

import com.litetech.omt.constant.ChargeOperationEnum;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.dao.model.core.Charge;
import com.litetech.omt.dao.model.core.InvoiceCharge;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.InvoiceChargeVO;

public class InvoiceChargeVODOMapper {
	
	public InvoiceCharge mapInvoiceChargeVOToDO(InvoiceChargeVO invoiceChargeVO){
		InvoiceCharge invoiceChargeDO = new InvoiceCharge();
		invoiceChargeDO.setId(invoiceChargeVO.getId());
		invoiceChargeDO.setInvoiceId(invoiceChargeVO.getInvoiceId());
		long chargeId = invoiceChargeVO.getCharge() != null ? invoiceChargeVO.getCharge().getId() : -1l;
		invoiceChargeDO.setChargeId(chargeId);
		invoiceChargeDO.setAmount(invoiceChargeVO.getChargedAmount());
		invoiceChargeDO.setIsPurchase(invoiceChargeVO.getPurchase().getId());
		invoiceChargeDO.setCreationDate(invoiceChargeVO.getCreationDate());
		invoiceChargeDO.setLastModifiedDate(invoiceChargeVO.getLastModifiedDate());
		return invoiceChargeDO;
	}
	
	public InvoiceChargeVO mapInvoiceChargeDOToVO(InvoiceCharge invoiceChargeDO){
		InvoiceChargeVO invoiceChargeVO = new InvoiceChargeVO();
		invoiceChargeVO.setId(invoiceChargeDO.getId());
		invoiceChargeVO.setInvoiceId(invoiceChargeDO.getInvoiceId());
		//Charge charge = new Charge();
		long chargeId = invoiceChargeDO.getChargeId();
		//invoiceChargeVO.setChargeId(.setId(chargeId));
		invoiceChargeVO.setPurchase(OMTSwitchEnum.getById(invoiceChargeDO.getIsPurchase()));
		invoiceChargeVO.setChargedAmount(invoiceChargeDO.getAmount());
		invoiceChargeVO.setCreationDate(invoiceChargeDO.getCreationDate());
		invoiceChargeVO.setLastModifiedDate(invoiceChargeDO.getLastModifiedDate());
		return invoiceChargeVO;
	}
	
	
	
	
}
