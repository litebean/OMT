package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;

public class InvoiceTaxTableModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;
	public final static Object[]  taxHeader = {"Name", "Value", "Icon", "Id"};
	
	private Object[][] rows;
	
	public InvoiceTaxTableModel(Object[][] rows){
		super(rows, taxHeader);
		this.rows = rows;
	}
	
	
	public Object[] getRow(int rowNo){
		return rows[rowNo];
	}
}
