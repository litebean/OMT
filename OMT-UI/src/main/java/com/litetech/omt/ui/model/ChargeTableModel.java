package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;

public class ChargeTableModel {

	public final static String[]  CHARGE_HEADER = {"Id", "Charge Name", "Percentage", "Operation", "Apply On", "E", "D", "A"};
	
	private DefaultTableModel defaultTableModel;
	private Object[][] rows;
	private int editableRow = -1;
	
	public ChargeTableModel(Object[][] rows){
		this.rows = rows;
		this.defaultTableModel = new DefaultTableModel(rows, CHARGE_HEADER){
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
				 }else if(row == editableRow && (column != 0 && column != 6 && column != 7)){
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
		return CHARGE_HEADER;
	}

	public int getEditableRow() {
		return editableRow;
	}

	public void setEditableRow(int editableRow) {
		this.editableRow = editableRow;
	}
	
	
}
