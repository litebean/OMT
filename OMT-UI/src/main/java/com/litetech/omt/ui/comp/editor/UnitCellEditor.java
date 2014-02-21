package com.litetech.omt.ui.comp.editor;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.ui.comp.renderer.SettingUnitCellRenderer.UnitRendererEnum;
import com.litetech.omt.ui.comp.validator.UnitValidator;
import com.litetech.omt.ui.model.UnitTableModel;
import com.litetech.omt.vo.UnitVO;

public class UnitCellEditor extends DefaultCellEditor {

	private UnitTableModel unitTableModel;
	private UnitRendererEnum cellOperation;
	private UnitValidator unitValidator;
	
	public UnitCellEditor(JCheckBox arg0, UnitTableModel unitTableModel,
			UnitRendererEnum cellOperation) {
		super(arg0);
		this.unitTableModel = unitTableModel;
		this.cellOperation = cellOperation;
		this.unitValidator = new UnitValidator(); 
	}	

	
	 public Component getTableCellEditorComponent(JTable table, Object value,
             boolean isSelected, int row, int column) {
		 DefaultTableModel defaultTableModel = ((DefaultTableModel)table.getModel());
		if(UnitRendererEnum.EDIT.equals(cellOperation)){
			 if(unitTableModel.getEditableRow() == row){
				 //save here
				 UnitVO unitVO = new UnitVO(new Long(defaultTableModel.getValueAt(row, 0).toString()), defaultTableModel.getValueAt(row, 1).toString());
				 if(unitValidator.validate(unitVO)){
					 ServiceClient.getInstance().saveUnit(unitVO);
					 JOptionPane.showMessageDialog(null, "Unit Saved "+unitVO.getId());
					 defaultTableModel.setValueAt(unitVO.getId(), row, 0);
					 defaultTableModel.setValueAt(getDateAsString(unitVO.getLastModifiedDate()), row, 2);
					 unitTableModel.setEditableRow(-1);
				 }
			 }else{
				 unitTableModel.setEditableRow(row);				 
			 }			 
		 }else if(UnitRendererEnum.ADD.equals(cellOperation)){
			 defaultTableModel.addRow(new Object[]{0l, "", "", "", "", ""});
			 unitTableModel.setEditableRow(row+1);
		}else if(UnitRendererEnum.DELETE.equals(cellOperation)){
			
			int choice = JOptionPane.showConfirmDialog(null, 
					"Please ensure none of the product is using this Unit\nAre you sure you want to delete", "Confirm", JOptionPane.YES_NO_OPTION);
        
			if(choice == JOptionPane.YES_OPTION){
				//delete
				deleteUnit(new Long(defaultTableModel.getValueAt(row, 0).toString()));
				defaultTableModel.removeRow(row);
			}
			unitTableModel.setEditableRow(-1);
		}
		 
		table.repaint();
		return null;
	 }
	 
	 
	 private UnitVO saveUnit(Long unitId, String unitName){
		 UnitVO unitVO = new UnitVO(unitId, unitName);
		 ServiceClient.getInstance().saveUnit(unitVO);
		 return unitVO; 
	 }
	 
	 private void deleteUnit(Long unitId){
		 if(unitId > 0){
			 ServiceClient.getInstance().deleteUnit(unitId);
		 }
	 }
	 
	 public String getDateAsString(Date date){
			String orderDateStr = "";
			if(date != null){
				orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
			}
			return orderDateStr;
		}
	 
}


