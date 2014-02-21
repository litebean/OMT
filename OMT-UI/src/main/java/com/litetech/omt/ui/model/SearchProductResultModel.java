package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;

public class SearchProductResultModel {
	
	public final static String[]  header = {"Product Id", "Product Name", "Quantity Available", "Quantity Unit", "Product Price", "Status", "Last Modified",""};
	
	private DefaultTableModel defaultTableModel;
	private Object[][] rows;
	
	public SearchProductResultModel(Object[][] rows){
		this.rows = rows;
		this.defaultTableModel = new DefaultTableModel(rows, header){
			 @Override
            public boolean isCellEditable ( int row, int column ){
				 if(column == 7){
					 return true;
				 }
				 return false;
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
	
	
	
	
}
