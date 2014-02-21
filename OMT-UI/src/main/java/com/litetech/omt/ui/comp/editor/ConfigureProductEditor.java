package com.litetech.omt.ui.comp.editor;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.ui.comp.ProductForm;
import com.litetech.omt.vo.ProductVO;

public class ConfigureProductEditor extends DefaultCellEditor{
	
	private ProductForm form; 
	
	public ConfigureProductEditor(JCheckBox checkBox, ProductForm form){
		super(checkBox);
		this.form = form;
	}
	
	 public Component getTableCellEditorComponent(JTable table, Object value,
             boolean isSelected, int row, int column) {
		 
		 Long productId = (Long)table.getModel().getValueAt(row, 0);
				
		 ProductVO productVO = ServiceClient.getInstance().retrieveProduct(productId);
		 form.populateProductPanel(productVO);
		 
		 return null;
	 }
}
