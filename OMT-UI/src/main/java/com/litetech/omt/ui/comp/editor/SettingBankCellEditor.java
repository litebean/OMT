package com.litetech.omt.ui.comp.editor;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.ui.comp.renderer.SettingUnitCellRenderer.UnitRendererEnum;
import com.litetech.omt.ui.model.BankTableModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.UnitTableModel;
import com.litetech.omt.vo.BankVO;
import com.litetech.omt.vo.UnitVO;

public class SettingBankCellEditor extends DefaultCellEditor{
	private BankTableModel bankTableModel;
	private UnitRendererEnum cellOperation;
	
	public SettingBankCellEditor(JCheckBox arg0, BankTableModel bankTableModel,
			UnitRendererEnum cellOperation) {
		super(arg0);
		this.bankTableModel = bankTableModel;
		this.cellOperation = cellOperation;
		
	}	

	
	 public Component getTableCellEditorComponent(JTable table, Object value,
             boolean isSelected, int row, int column) {
		 DefaultTableModel defaultTableModel = ((DefaultTableModel)table.getModel());
		if(UnitRendererEnum.EDIT.equals(cellOperation)){
			 if(bankTableModel.getEditableRow() == row){
				 //save here
				 BankVO bankVO = saveBank(new Long(defaultTableModel.getValueAt(row, 0).toString()), 
						 defaultTableModel.getValueAt(row, 1).toString(), (LiteComboModel)defaultTableModel.getValueAt(row, 2));
				 JOptionPane.showMessageDialog(null, "Bank Saved "+bankVO.getId());
				 defaultTableModel.setValueAt(bankVO.getId(), row, 0);
				 //defaultTableModel.setValueAt(getDateAsString(unitVO.getLastModifiedDate()), row, 2);
				 bankTableModel.setEditableRow(-1);
			 }else{
				 bankTableModel.setEditableRow(row);				 
			 }			 
		 }else if(UnitRendererEnum.ADD.equals(cellOperation)){
			 defaultTableModel.addRow(new Object[]{0l, "", new LiteComboModel(OMTSwitchEnum.ON.getId(), OMTSwitchEnum.ON.getName()), "", "", ""});
			 bankTableModel.setEditableRow(row+1);
		}else if(UnitRendererEnum.DELETE.equals(cellOperation)){
			
			int choice = JOptionPane.showConfirmDialog(null, 
					"Please ensure none of the order / invoice is using this Bank\nAre you sure you want to delete", "Confirm", JOptionPane.YES_NO_OPTION);
        
			if(choice == JOptionPane.YES_OPTION){
				//delete
				deleteBank(new Long(defaultTableModel.getValueAt(row, 0).toString()));
				defaultTableModel.removeRow(row);
			}
			bankTableModel.setEditableRow(-1);
		}
		 
		table.repaint();
		return null;
	 }
	
	 
	 private BankVO saveBank(final long bankId, String bankName, LiteComboModel statusCombo){
		 BankVO bankVO = new BankVO(bankId);
		 bankVO.setName(bankName);
		 bankVO.setActive(OMTSwitchEnum.getById((int)statusCombo.getId()));
		 
		 //call service
		 ServiceClient.getInstance().saveBank(bankVO);
		 return bankVO;
	 }
	 
	 
	 private void deleteBank(final long bankId){
		 if(bankId > 0){
			 ServiceClient.getInstance().deleteBank(bankId);
		 }
	 }
	 
}
