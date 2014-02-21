package com.litetech.omt.ui.model.ds;

import java.util.List;
import java.util.Map;

import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.ProductVO;

public class InvoiceFormDSModel extends OrderFormDSModel{

	
	private String clientDefaultTin;
	Map<Integer, List<LiteComboModel>> unitModelMap;
	private List<ChargeVO> chargeVOs;
	
	public String getClientDefaultTin() {
		return clientDefaultTin;
	}
	public void setClientDefaultTin(String clientDefaultTin) {
		this.clientDefaultTin = clientDefaultTin;
	}
	
	
	public Map<Integer, List<LiteComboModel>> getUnitModelMap() {
		return unitModelMap;
	}
	public void setUnitModelMap(Map<Integer, List<LiteComboModel>> unitModelMap) {
		this.unitModelMap = unitModelMap;
	}
	public List<ChargeVO> getChargeVOs() {
		return chargeVOs;
	}
	public void setChargeVOs(List<ChargeVO> chargeVOs) {
		this.chargeVOs = chargeVOs;
	}
	
	
	
}
