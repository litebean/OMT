package com.litetech.omt.ui.comp.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.constant.ChargeOperationEnum;
import com.litetech.omt.ui.comp.listener.TableCellListener;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.util.NumberUtil;
import com.litetech.omt.vo.ChargeVO;

public class TaxTableAction extends AbstractAction{
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TableCellListener tcl = (TableCellListener)e.getSource();
		
	    int row = tcl.getRow();
	    int column = tcl.getColumn();
		    
	    DefaultTableModel tableModel = (DefaultTableModel)tcl.getTable().getModel();
	    resetModelValues(tableModel, row, column);
	}
	
	
	public void resetModelValues(DefaultTableModel tableModel, int startRow, int startCol){
		if(startCol == 1){
			startRow++;
		}
		
		resetSubsequentFields(tableModel, startRow);
	}
	
	private Double getDependentTaxValue(DefaultTableModel tableModel, int endRow, ChargeVO dependantTax){
		Double value = 0d;
		for(int i = endRow; i >= 0;  i = i-2){
			Object fieldNameObj = tableModel.getValueAt(i, 0);
			if(fieldNameObj instanceof LiteComboModel){
				LiteComboModel taxComboModel = (LiteComboModel)fieldNameObj;
				if(taxComboModel.getId() == dependantTax.getId()){
					Object valObj = tableModel.getValueAt(i, 1);
					if(valObj instanceof String){
						value = new Double(valObj.toString());
					}else{
						value = (Double)valObj;
					}
					break;
				}
			}
		}
		return value;
	}
	
	private void resetSubsequentFields(DefaultTableModel tableModel, int startRow){
		int totalRow = tableModel.getRowCount() - 1;
		for (int i = startRow; i <= totalRow; i++) {
			
			Object fieldNameObj = tableModel.getValueAt(i, 0);
			if(fieldNameObj instanceof LiteComboModel){
				LiteComboModel taxComboModel = (LiteComboModel)fieldNameObj;
				ChargeVO taxVO = (ChargeVO)taxComboModel.getRelativeObj();
				
				if(taxVO == null){
					continue;
				}
				
				if(taxVO.getApplyOnCharge() == null){
					Double prevTotalValue = new Double(tableModel.getValueAt(i-1, 1).toString());
					if(taxVO.getPercentValue() != -1){
						Double perentageAmount = percentage(prevTotalValue, taxVO.getPercentValue());
						tableModel.setValueAt(perentageAmount, i, 1);
					}
				}else{
					Double prevTotalValue = getDependentTaxValue(tableModel, i, taxVO.getApplyOnCharge());
					Double perentageAmount = percentage(prevTotalValue, taxVO.getPercentValue());
					tableModel.setValueAt(perentageAmount, i, 1);
				}
			}else{
				//get previous operation
				LiteComboModel aboveTaxCombo = (LiteComboModel)tableModel.getValueAt(i-1, 0);
				Object perTotalObj = tableModel.getValueAt(i-2, 1);
				Double prevTotalValue = 0d;
				if(perTotalObj instanceof String){
					prevTotalValue = new Double(perTotalObj.toString());
				}else{
					prevTotalValue = (Double)perTotalObj;
				}
				ChargeVO taxVO = (ChargeVO)aboveTaxCombo.getRelativeObj();
				if(taxVO != null){
					ChargeOperationEnum taxOperationEnum = taxVO.getOperation();
					Double taxValue = null;
					Object taxValueObj = tableModel.getValueAt(i-1, 1);
					if(taxValueObj instanceof Double){
						taxValue =  (Double)taxValueObj;
					}else{
						System.out.println("object is of type "+taxValueObj);
						taxValue = new Double(taxValueObj.toString());
					}
					
					
					Double calValue = calculateTotalAmt(prevTotalValue, taxValue, taxOperationEnum);
					tableModel.setValueAt(calValue, i, 1);
				}else{
					tableModel.setValueAt(prevTotalValue, i, 1);
				}
			}
			
		}
	}
	
	private Double calculateTotalAmt(Double amount, Double value,
			ChargeOperationEnum operation){
		
		
		Double calAmount = new Double(0);
		if(ChargeOperationEnum.ADD.equals(operation)){
			calAmount = add(amount, value);
		}else if(ChargeOperationEnum.SUB.equals(operation)){
			calAmount = sub(amount, value);
		}else if(ChargeOperationEnum.PERCENTAGE.equals(operation)){
			calAmount = percentage(amount, value);
		}
		calAmount = NumberUtil.roundToTwoDecimal(calAmount);
		return calAmount;
	}
	
	private Double percentage(Double amount, double percentageBy){
		double amt = (amount * percentageBy)/100;
		amt = NumberUtil.roundToTwoDecimal(amt);
		return amt;
	}
	
	private Double add(Double amount, double addBy){
		double amt = (amount + addBy);
		amt = NumberUtil.roundToTwoDecimal(amt);
		return amt;
	}
	
	private Double sub(Double amount, double subBy){
		double amt = (amount - subBy);
		amt = NumberUtil.roundToTwoDecimal(amt);
		return amt;
	}

}
