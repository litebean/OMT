package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;

public class UnitTableModel {

	public final static String[]  header = {"Unit Id", "Unit Name", "Last Modified","E", "D", "A"};
	
	private DefaultTableModel defaultTableModel;
	private Object[][] rows;
	private int editableRow = -1;
	
	public UnitTableModel(Object[][] rows){
		this.rows = rows;
		
		this.defaultTableModel = new DefaultTableModel(rows, header){
			 @Override
           public boolean isCellEditable ( int row, int column ){
				 int totalRow = getRowCount() - 1;
				 boolean editable = false;
				
				 if(editableRow == -1){
					 if(column == 0 || column == 2){
						 editable = false;
					 }else if(column == 3){
						 editable = true;
					 }else if(row != 0 && column == 4){
						 editable = true;
					 }else if(row == totalRow && column == 5){
						 editable = true;
					 }
				 }else if(row == editableRow && (column == 1 || column == 3)){
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
		return header;
	}

	public int getEditableRow() {
		return editableRow;
	}

	public void setEditableRow(int editableRow) {
		this.editableRow = editableRow;
	}
	
	
}
