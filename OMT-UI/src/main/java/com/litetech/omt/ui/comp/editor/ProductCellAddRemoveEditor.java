package com.litetech.omt.ui.comp.editor;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.constant.ProductUnitLevelEnum;
import com.litetech.omt.ui.model.LiteComboModel;

public class ProductCellAddRemoveEditor extends DefaultCellEditor {
	
	private ProductCellAddRemoveEditorEnum operation;
	
	public ProductCellAddRemoveEditor(JCheckBox checkBox, ProductCellAddRemoveEditorEnum operation){
		super(checkBox);
		this.operation = operation;
	}
	
	@Override
	 public Component getTableCellEditorComponent(JTable table, Object value,
             boolean isSelected, int row, int column) {
		  
		  int totalRow = table.getModel().getRowCount() - 1;
		  
		  if(ProductCellAddRemoveEditorEnum.ADD.equals(operation)){
			  if(row == totalRow){
				  Object[] unitPriceRow = {0l, ProductUnitLevelEnum.SECONDARY.name(), new LiteComboModel(-1, "Select A Unit"), 0, 0.00, 1, 0, "", ""};
				  ((DefaultTableModel)table.getModel()).addRow(unitPriceRow);
			  }
		  }else{
			  if(row > 0){
				  ((DefaultTableModel)table.getModel()).removeRow(row);  
			  } 
		  }
		  
		  
		  return null;
	 }
	
	
	public enum ProductCellAddRemoveEditorEnum{
		ADD(1),
		REMOVE(2);
		
		private int id;
		private ProductCellAddRemoveEditorEnum(int id){
			this.id = id;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		
	}

}
