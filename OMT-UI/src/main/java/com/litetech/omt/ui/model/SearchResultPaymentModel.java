package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;


public class SearchResultPaymentModel {
	public final static String[]  header = {"Transaction Id", "Customer Name", "Transaction Type", "Status", "Bank", "Payment Mode", "Transaction Amount", "Transaction Date"};
	private DefaultTableModel defaultTableModel;
	private Object[][] rows;
		
	public SearchResultPaymentModel(Object[][] rows){
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

	public Object getCell(int row, int col) {
		return rows[row][col];
	}

}
