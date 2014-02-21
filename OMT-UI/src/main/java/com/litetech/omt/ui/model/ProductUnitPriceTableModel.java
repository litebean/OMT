package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;

public class ProductUnitPriceTableModel {

	public final static String[]  UNIT_PRICE_HEADER = {"ProductUnitId", "Unit Level", "Unit", "Price Ratio", "Product Price", "Quantity Ratio", "Quantity Available", "D", "A"};
	private DefaultTableModel defaultTableModel;
	private Object[][] rows;
	
	public ProductUnitPriceTableModel(Object[][] rows){
		this.rows = rows;
		this.defaultTableModel = new DefaultTableModel(rows, UNIT_PRICE_HEADER){
			 @Override
           public boolean isCellEditable (int row, int column){
				 if(row == 0){
					 if(column == 2 || column == 4 || column == 5 || column == 8){
						 return true;
					 }
				 }else{
					 if(column == 2 || column == 3 || column == 5 || column == 7 || column == 8){
						 return true;
					 }
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

}
