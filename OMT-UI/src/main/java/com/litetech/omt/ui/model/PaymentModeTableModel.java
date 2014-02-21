package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;

public class PaymentModeTableModel {
	public final static String[]  PAY_MODE_HEADER = {"Id", "Payment Mode", "Description", "Status", "Last Modified Date", "E", "D", "A"};
	
	private DefaultTableModel defaultTableModel;
	private Object[][] rows;
	private int editableRow = -1;
	
	public PaymentModeTableModel(Object[][] rows){
		this.rows = rows;
		this.defaultTableModel = new DefaultTableModel(rows, PAY_MODE_HEADER){
			 @Override
          public boolean isCellEditable ( int row, int column ){
				 int totalRow = getRowCount() - 1;
				 boolean editable = false;
						
				 
				 if(editableRow == -1){
					 if(column == 0){
						 editable = false;
					 }else if(column == 5){
						 editable = true;
					 }else if(row != 0 && column == 6){
						 editable = true;
					 }else if(row == totalRow && column == 7){
						 editable = true;
					 }
				 }else if(row == editableRow && (column != 0 && column != 4 && column != 6 && column != 7)){
					 editable = true;
				 }
				return editable;
				 
          }
		};
	}
	
	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}

	public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
		this.defaultTableModel = defaultTableModel;
	}

	public Object[][] getRows() {
		return rows;
	}

	public void setRows(String[][] rows) {
		this.rows = rows;
	}

	public static String[] getHeader() {
		return PAY_MODE_HEADER;
	}

	public int getEditableRow() {
		return editableRow;
	}

	public void setEditableRow(int editableRow) {
		this.editableRow = editableRow;
	}

}
