package com.litetech.omt.ui.model;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.litetech.omt.constant.ChargeOperationEnum;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.PaymentModeVO;
import com.litetech.omt.vo.UnitVO;

public class SettingFormModel {

	private List<UnitVO> unitVOs = Collections.emptyList();
	private List<ChargeVO> chargeVOs = Collections.emptyList();
	private List<BankVO> bankVOs = Collections.emptyList();
	private List<PaymentModeVO> paymentModeVOs = Collections.emptyList();
	
	public List<UnitVO> getUnitVOs() {
		return unitVOs;
	}
	public void setUnitVOs(List<UnitVO> unitVOs) {
		this.unitVOs = unitVOs;
	}
	public List<ChargeVO> getChargeVOs() {
		return chargeVOs;
	}
	public void setChargeVOs(List<ChargeVO> chargeVOs) {
		this.chargeVOs = chargeVOs;
	}
	
	public List<BankVO> getBankVOs() {
		return bankVOs;
	}
	public void setBankVOs(List<BankVO> bankVOs) {
		this.bankVOs = bankVOs;
	}
	
	
	
	public List<PaymentModeVO> getPaymentModeVOs() {
		return paymentModeVOs;
	}
	public void setPaymentModeVOs(List<PaymentModeVO> paymentModeVOs) {
		this.paymentModeVOs = paymentModeVOs;
	}
	public String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}
	
	
	public Object[][] getUnitRows(){
		Object[][] unitRows = new Object[unitVOs.size()][6];
		
		int row = 0;
		for(UnitVO unitVO : unitVOs){
			unitRows[row][0] = unitVO.getId();
			unitRows[row][1] = unitVO.getName();
			unitRows[row][2] = getDateAsString(unitVO.getLastModifiedDate());
			unitRows[row][3] = "";
			unitRows[row][4] = "";
			unitRows[row][5] = "";
			row++;
		}
		
		if(row == 0){
			unitRows = new Object[1][6];
			unitRows[row][0] = 0l;
			unitRows[row][1] = "";
			unitRows[row][2] = getDateAsString(new Date());
			unitRows[row][3] = "";
			unitRows[row][4] = "";
			unitRows[row][5] = "";
		}
		
		return unitRows;
	}
	
	
	public Object[][] getChargeRows(){
		Object[][] chargeRows = new Object[chargeVOs.size()][8];
		
		int row = 0;
		for(ChargeVO chargeVO : chargeVOs){
			chargeRows[row][0] = chargeVO.getId();
			chargeRows[row][1] = chargeVO.getName();
			chargeRows[row][2] = chargeVO.getPercentValue();
			chargeRows[row][3] = new LiteComboModel(chargeVO.getOperation().getId(), chargeVO.getOperation().toString());
			
			LiteComboModel applyOnComboModel = null;
			if(chargeVO.getApplyOnCharge() != null && chargeVO.getApplyOnCharge().getId() > 0){
				ChargeVO applyOnCharge = chargeVO.getApplyOnCharge();
				applyOnComboModel = new LiteComboModel(applyOnCharge.getId(), applyOnCharge.getName());
			}else{
				applyOnComboModel = new LiteComboModel(-1, "Total");
			}
			
			chargeRows[row][4] = applyOnComboModel;
			chargeRows[row][5] = "";
			chargeRows[row][6] = "";
			chargeRows[row][7] = "";
			
			row++;
		}
		
		if(row == 0){
			chargeRows = new Object[1][8];
			chargeRows[row][0] = 0l;
			chargeRows[row][1] = "";
			chargeRows[row][2] = 0d;
			chargeRows[row][3] = new LiteComboModel(ChargeOperationEnum.ADD.getId(), ChargeOperationEnum.ADD.toString());
			chargeRows[row][4] = new LiteComboModel(-1, "Total");
			chargeRows[row][5] = "";
			chargeRows[row][6] = "";
			chargeRows[row][7] = "";
		}
		return chargeRows;
	}
	
	
	public Object[][] getBankRows(){
		Object[][] bankRows = new Object[bankVOs.size()][6];
		
		int row = 0;
		for(BankVO bankVO : bankVOs){
			bankRows[row][0] = bankVO.getId();
			bankRows[row][1] = bankVO.getName();
			
			OMTSwitchEnum active = bankVO.getActive();
			bankRows[row][2] = new LiteComboModel(active.getId(), active.getName());
			bankRows[row][3] = "";
			bankRows[row][4] = "";
			bankRows[row][5] = "";
			
			row++;
		}
		
		if(row == 0){
			bankRows = new Object[1][6];
			bankRows[row][0] = 0l;
			bankRows[row][1] = "";
			
			OMTSwitchEnum active = OMTSwitchEnum.ON;
			bankRows[row][2] = new LiteComboModel(active.getId(), active.getName());
			bankRows[row][3] = "";
			bankRows[row][4] = "";
			bankRows[row][5] = "";
		}
		return bankRows;
	}
	
	public Object[][] getPaymentModeRows(){
		Object[][] paymentModeRows = new Object[paymentModeVOs.size()][8];
		
		int row = 0;
		for(PaymentModeVO paymentModeVO : paymentModeVOs){
			paymentModeRows[row][0] = paymentModeVO.getId();
			paymentModeRows[row][1] = paymentModeVO.getName();
			paymentModeRows[row][2] = paymentModeVO.getDesc();
			
			OMTSwitchEnum active = paymentModeVO.getActive();
			paymentModeRows[row][3] = new LiteComboModel(active.getId(), active.getName());
			paymentModeRows[row][4] =  getDateAsString(paymentModeVO.getLastModifiedDate());
					
			paymentModeRows[row][5] = "";
			paymentModeRows[row][6] = "";
			paymentModeRows[row][7] = "";
			
			row++;
		}
		
		
		if(row == 0){
			paymentModeRows = new Object[1][8];
			
			paymentModeRows[row][0] = 0l;
			paymentModeRows[row][1] = "";
			paymentModeRows[row][2] = "";
			
			OMTSwitchEnum active = OMTSwitchEnum.ON;
			paymentModeRows[row][3] = new LiteComboModel(active.getId(), active.getName());
			paymentModeRows[row][4] =  getDateAsString(new Date());
					
			paymentModeRows[row][5] = "";
			paymentModeRows[row][6] = "";
			paymentModeRows[row][7] = "";
			
		}
		return paymentModeRows;
	}

	
}
