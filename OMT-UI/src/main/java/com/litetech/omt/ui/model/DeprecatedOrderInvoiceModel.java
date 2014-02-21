package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;

public class DeprecatedOrderInvoiceModel  extends DefaultTableModel{

	private static final long serialVersionUID = 1L;
	public final static Object[]  invoiceHeader = {"Company Name", "Invoice Date", "Invoice Status", "Price"};
	
	private Object[][] rows;
	
	public DeprecatedOrderInvoiceModel(Object[][] rows){
		super(rows, invoiceHeader);
		this.rows = rows;		
	}
	
	 @Override
     public boolean isCellEditable ( int row, int column ){
		return false;
	}
	
}
