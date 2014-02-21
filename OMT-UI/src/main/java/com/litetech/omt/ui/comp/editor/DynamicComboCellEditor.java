package com.litetech.omt.ui.comp.editor;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;

import com.litetech.omt.ui.model.LiteComboModel;

public class DynamicComboCellEditor extends DefaultCellEditor{

	private DefaultComboBoxModel model;
	private List<LiteComboModel> comboModels;
	
	public DynamicComboCellEditor(List<LiteComboModel> comboModels) {
		super(new JComboBox());
		this.model = (DefaultComboBoxModel)(((JComboBox)getComponent()).getModel());
		this.comboModels = comboModels;
	}


	@Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		model.removeAllElements();
		if(comboModels != null){
	        for(int i = 0; i < comboModels.size(); i++) {
	            model.addElement(comboModels.get(i));
	        }
		}  
	    return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }	
	
	public List<LiteComboModel> getComboModels() {
		return comboModels;
	}

	public void setComboModels(List<LiteComboModel> comboModels) {
		this.comboModels = comboModels;
	}


}
