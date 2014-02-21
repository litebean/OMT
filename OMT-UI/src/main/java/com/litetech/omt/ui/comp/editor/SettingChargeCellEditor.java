package com.litetech.omt.ui.comp.editor;

import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.ChargeOperationEnum;
import com.litetech.omt.ui.comp.renderer.SettingChargeCellRenderer.ChargeRendererEnum;
import com.litetech.omt.ui.model.ChargeTableModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.vo.ChargeVO;

public class SettingChargeCellEditor extends DefaultCellEditor{

	
	private ChargeRendererEnum cellOperation;
	private ChargeTableModel chargeTableModel;
	
	public SettingChargeCellEditor(JCheckBox arg0, ChargeTableModel chargeTableModel,
			ChargeRendererEnum cellOperation) {
		super(arg0);
		this.cellOperation = cellOperation;
		this.chargeTableModel = chargeTableModel;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
		 DefaultTableModel defaultTableModel = ((DefaultTableModel)table.getModel());
		if(ChargeRendererEnum.EDIT.equals(cellOperation)){
			 if(chargeTableModel.getEditableRow() == row){
				 //save here
				 ChargeVO chargeVO = saveCharge(defaultTableModel.getValueAt(row, 0), defaultTableModel.getValueAt(row, 1),
						 defaultTableModel.getValueAt(row, 2), defaultTableModel.getValueAt(row, 3),
						 defaultTableModel.getValueAt(row, 4));
				 //JOptionPane.showMessageDialog(null, "Unit Saved "+unitVO.getId());
				 defaultTableModel.setValueAt(chargeVO.getId(), row, 0);
				 //defaultTableModel.setValueAt(getDateAsString(unitVO.getLastModifiedDate()), row, 2);
				 chargeTableModel.setEditableRow(-1);
			 }else{
				 chargeTableModel.setEditableRow(row);				 
			 }
			 resetAppliedOnCharges(table, row);
		 }else if(ChargeRendererEnum.ADD.equals(cellOperation)){
			 defaultTableModel.addRow(new Object[]{0l, "", 0d, new LiteComboModel(ChargeOperationEnum.ADD.getId(), ChargeOperationEnum.ADD.toString()), new LiteComboModel(-1, "Total"), "", "", ""});
			 chargeTableModel.setEditableRow(row+1);
		}else if(ChargeRendererEnum.DELETE.equals(cellOperation)){
			
			int choice = JOptionPane.showConfirmDialog(null, 
					"Please ensure none of the product is using this Unit\nAre you sure you want to delete", "Confirm", JOptionPane.YES_NO_OPTION);
       
			if(choice == JOptionPane.YES_OPTION){
				//delete
				deleteCharge(defaultTableModel.getValueAt(row, 0));
				defaultTableModel.removeRow(row);
			}
			chargeTableModel.setEditableRow(-1);
			resetAppliedOnCharges(table, row);
		}
		 
		table.repaint();
		return null;
	 }
	

	
	private void resetAppliedOnCharges(JTable table, int row){
		List<ChargeVO> chargeVOs = ServiceClient.getInstance().retrieveAllCharges();
		List<LiteComboModel> chargeCombos = constructApplyChargeList(chargeVOs);
		
		DynamicComboCellEditor cellEditor = (DynamicComboCellEditor)table.getCellEditor(row, 4);
		cellEditor.setComboModels(chargeCombos);
	}
	
	
	private Vector<LiteComboModel> constructApplyChargeList(List<ChargeVO> activeCharges){
		Vector<LiteComboModel> applyOnChargeLs = new Vector<LiteComboModel>();
		applyOnChargeLs.add(new LiteComboModel(-1, "Total"));
		
		for(ChargeVO chargeVO : activeCharges){
			applyOnChargeLs.add(new LiteComboModel(chargeVO.getId(), chargeVO.getName()));
		}
		
		return applyOnChargeLs;
	}
	
	private ChargeVO saveCharge(Object chargeIdObj, Object chargeNameObj, Object percentageObj, 
			Object operationObj, Object applyOnObj){
		long chargeId =  Long.valueOf(chargeIdObj.toString());
		ChargeVO chargeVO = new ChargeVO(chargeId);
		chargeVO.setName(chargeNameObj.toString());
		chargeVO.setPercentValue(new Double(percentageObj.toString()));
		
		String operationName = ((LiteComboModel)operationObj).getName();  
		chargeVO.setOperation(ChargeOperationEnum.valueOf(operationName));
		
		long appliedOnChargeId = ((LiteComboModel)applyOnObj).getId();
		if(appliedOnChargeId > 0){
			chargeVO.setApplyOnCharge(new ChargeVO(appliedOnChargeId));
		}
		
		//service call
		ServiceClient.getInstance().saveCharge(chargeVO);
		return chargeVO;
		
	}

	
	private void deleteCharge(Object chargeId){
		ServiceClient.getInstance().deleteCharge(new Long(chargeId.toString()));
	}
	
}
