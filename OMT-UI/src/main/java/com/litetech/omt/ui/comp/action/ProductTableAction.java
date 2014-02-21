package com.litetech.omt.ui.comp.action;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.ui.comp.listener.TableCellListener;

public class ProductTableAction extends AbstractAction{

	DefaultTableModel tableModel;
	JTextField qtyField;
	
	public ProductTableAction(DefaultTableModel tableModel, JTextField qtyField){
		this.tableModel = tableModel;
		this.qtyField = qtyField;
		
		registerQtyTextFieldActionListener();
	}
	
	private void registerQtyTextFieldActionListener(){
		qtyField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				resetQtyDependency(tableModel, new Double(qtyField.getText()));				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TableCellListener tcl = (TableCellListener)e.getSource();
		
		int row = tcl.getRow();
	    int column = tcl.getColumn();
	    DefaultTableModel tableModel = (DefaultTableModel)tcl.getTable().getModel();
		resetModelValues(tableModel, row, column);
	}
	
	public void resetModelValues(DefaultTableModel tableModel, int triggeredRow, int triggeredCol){
		if(triggeredRow == 0 && triggeredCol == 4){		
			resetSubsequentFields(tableModel, triggeredRow);
		}else if(triggeredCol == 3){
			Object primaryUnitPriceObj = tableModel.getValueAt(0, 4);  
			if(primaryUnitPriceObj != null){
				Double primaryUnitPrice = new Double(primaryUnitPriceObj.toString());
				Object priceRatioObj = tableModel.getValueAt(triggeredRow, 3);
				if(priceRatioObj != null){
					Double priceRatio = new Double(priceRatioObj.toString());
					double resetValue = priceRatio * primaryUnitPrice;
					tableModel.setValueAt(resetValue, triggeredRow, 4);
				}else{
					tableModel.setValueAt(0, triggeredRow, 4);
				}
			}else{
				tableModel.setValueAt(0, triggeredRow, 4);
			}
		}else if(triggeredCol == 5){
			Object unitQty = tableModel.getValueAt(triggeredRow, 5);
			Double qty = new Double(qtyField.getText());
			Double newQty = qty * new Double(unitQty.toString());
			tableModel.setValueAt(newQty, triggeredRow, 6);
		}
		
	}
	
	
	private void resetQtyDependency(DefaultTableModel tableModel, double qty){
		int totalRow = tableModel.getRowCount() - 1;
		if(totalRow > 0){
			for(int i = 0; i <= totalRow; i++){
				Object totalRatioObj = tableModel.getValueAt(i, 5);
				Double totalQtyAvl = new Double(totalRatioObj.toString()) * qty;
				tableModel.setValueAt(totalQtyAvl, i, 6);
			}
		}
	}
	
	
	private void resetSubsequentFields(DefaultTableModel tableModel, int startRow){
		int totalRow = tableModel.getRowCount() - 1;
		
		if(totalRow > 0){
			Object primaryUnitPriceObj = tableModel.getValueAt(0, 4);  
			if(primaryUnitPriceObj != null){
				Double primaryUnitPrice = new Double(primaryUnitPriceObj.toString());
				for(int i = 1; i <= totalRow; i++){
					Object priceRatioObj = tableModel.getValueAt(i, 3);
					
					if(priceRatioObj != null){
						Double priceRatio = new Double(priceRatioObj.toString());
						tableModel.setValueAt((priceRatio * primaryUnitPrice), i, 4);
					}else{
						tableModel.setValueAt(0, i, 4);
					}
					
				}
			}
		}		
	}

}
