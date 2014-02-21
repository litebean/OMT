package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;

public class SearchInvoiceResultModel {
	
	public final static String[]  header = {"Invoice Id", "Customer Name", "Invoice Date", "Invoice Status"};
	private DefaultTableModel defaultTableModel;
	private String[][] rows;
	
	public SearchInvoiceResultModel(String[][] rows){
		this.rows = rows;
		this.defaultTableModel = new DefaultTableModel(rows, header){
			 @Override
             public boolean isCellEditable ( int row, int column ){
				 return false;
             }
		};
	}	
	
	public DefaultTableModel getDefaultTableModel(){
		return this.defaultTableModel;
	}
	
	public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
		this.defaultTableModel = defaultTableModel;
	}

	public String getCell(int row, int col) {
		return rows[row][col];
	}
}

