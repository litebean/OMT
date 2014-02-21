package com.litetech.omt.ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;

public class SearchOrderResultModel {

	
	public final static String[]  header = {"Order Id", "Customer Name", "Order Date", "Order Status"};
	
	private DefaultTableModel defaultTableModel;
	private String[][] rows;
		
	public SearchOrderResultModel(String[][] rows){
		this.rows = rows;
		this.defaultTableModel = new DefaultTableModel(rows, header){
			 @Override
             public boolean isCellEditable ( int row, int column ){
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

	public String getCell(int row, int col) {
		return rows[row][col];
	}

	
	
	
	
	
	
	
	
}
