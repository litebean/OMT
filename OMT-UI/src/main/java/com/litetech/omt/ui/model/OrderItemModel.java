package com.litetech.omt.ui.model;

import javax.swing.table.DefaultTableModel;

public class OrderItemModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static Object[]  header = {"Id", "", "Product Name", "Desc", "Units", "Product Price", "Qty", "Total Cost", "Invoiced Qty"};
	
	private Object[][] rows;
	
	
	public OrderItemModel(Object[][] rows){
		super(rows, header);
		this.rows = rows;		
	}
	
	 @Override
     public boolean isCellEditable ( int row, int column ){
		/* if(column == 6 || column == 7){
			 return false;	 
		 }else{ */
		 
		 	//return isEditable(row, column);
		 return true;
		// }
	}
	 
	 private boolean isEditable(int row, int column){
		 boolean editable = true;
		 int invoicedQty = (Integer)getValueAt(row, 8);
		 
		 if(invoicedQty > 0){
			 editable = false;
		 }
		 return editable;
	 }
	 
	
	
     @Override
	 public Class getColumnClass(int column) {
    	 return (getValueAt(0, column).getClass());
	 }

	
     public Object[] getRow(int rowNo){
    	 return rows[rowNo];
     }

	
	

	
}
