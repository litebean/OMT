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
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.ui.comp.renderer.SettingChargeCellRenderer.ChargeRendererEnum;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.PaymentModeTableModel;
import com.litetech.omt.vo.PaymentModeVO;

public class SettingPaymentModeCellEditor extends DefaultCellEditor{
	private ChargeRendererEnum cellOperation;
	private PaymentModeTableModel paymentTableModel;
	
	public SettingPaymentModeCellEditor(JCheckBox arg0, PaymentModeTableModel paymentTableModel,
			ChargeRendererEnum cellOperation) {
		super(arg0);
		this.cellOperation = cellOperation;
		this.paymentTableModel = paymentTableModel;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
		 DefaultTableModel defaultTableModel = ((DefaultTableModel)table.getModel());
		if(ChargeRendererEnum.EDIT.equals(cellOperation)){
			 if(paymentTableModel.getEditableRow() == row){
				 //save here
				 PaymentModeVO paymentModeVO = savePaymentMode(defaultTableModel.getValueAt(row, 0), defaultTableModel.getValueAt(row, 1),
						 defaultTableModel.getValueAt(row, 2), defaultTableModel.getValueAt(row, 3));
				 //JOptionPane.showMessageDialog(null, "Unit Saved "+unitVO.getId());
				 defaultTableModel.setValueAt(paymentModeVO.getId(), row, 0);
				 defaultTableModel.setValueAt(getDateAsString(paymentModeVO.getLastModifiedDate()), row, 4);
				 //defaultTableModel.setValueAt(getDateAsString(unitVO.getLastModifiedDate()), row, 2);
				 paymentTableModel.setEditableRow(-1);
			 }else{
				 paymentTableModel.setEditableRow(row);				 
			 }
		}else if(ChargeRendererEnum.ADD.equals(cellOperation)){
			 defaultTableModel.addRow(new Object[]{0l, "", "", new LiteComboModel(OMTSwitchEnum.ON.getId(), OMTSwitchEnum.ON.getName()), "", "", "", ""});
			 paymentTableModel.setEditableRow(row+1);
		}else if(ChargeRendererEnum.DELETE.equals(cellOperation)){
			
			int choice = JOptionPane.showConfirmDialog(null, 
					"Please ensure none of the Transaction payment is using this Mode\nAre you sure you want to delete", "Confirm", JOptionPane.YES_NO_OPTION);
       
			if(choice == JOptionPane.YES_OPTION){
				//delete
				deletePaymentMode(defaultTableModel.getValueAt(row, 0));
				defaultTableModel.removeRow(row);
			}
			paymentTableModel.setEditableRow(-1);
		}
		 
		table.repaint();
		return null;
	 }
	

	
	
	
	private PaymentModeVO savePaymentMode(Object paymentModeIdObj, Object paymentModeObj, Object paymentModeDescObj, 
			Object paymentModeStatus){
		long paymentModeId =  Long.valueOf(paymentModeIdObj.toString());
		PaymentModeVO paymentModeVO = new PaymentModeVO(paymentModeId);
		paymentModeVO.setName(paymentModeObj.toString());
		paymentModeVO.setDesc(paymentModeDescObj.toString());
		
		LiteComboModel paymentStatusModel = (LiteComboModel)paymentModeStatus;
		paymentModeVO.setActive(OMTSwitchEnum.getById((int)paymentStatusModel.getId()));
		
		ServiceClient.getInstance().savePaymentMode(paymentModeVO);
		
		return paymentModeVO;
		
	}

	
	private void deletePaymentMode(Object paymentModeIdObj){
		ServiceClient.getInstance().deletePaymentMode(new Long(paymentModeIdObj.toString()));
	}
	
	public String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}

}
