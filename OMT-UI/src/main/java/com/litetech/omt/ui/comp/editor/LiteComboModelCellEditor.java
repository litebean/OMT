package com.litetech.omt.ui.comp.editor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;

import com.litetech.omt.ui.model.LiteComboModel;

public class LiteComboModelCellEditor extends DefaultCellEditor{

	private DefaultComboBoxModel model;
	private Map<Integer, List<LiteComboModel>> comboModelMap; 
	
	public LiteComboModelCellEditor(){
		super(new JComboBox());
		this.model = (DefaultComboBoxModel)(((JComboBox)getComponent()).getModel());
		comboModelMap = new TreeMap<Integer, List<LiteComboModel>>();
	}
	
	public LiteComboModelCellEditor(Map<Integer, List<LiteComboModel>> comboModelMap){
		super(new JComboBox());
		this.model = (DefaultComboBoxModel)(((JComboBox)getComponent()).getModel());
		this.comboModelMap = comboModelMap;
	}
	
	public void setComboModelMap(Map<Integer, List<LiteComboModel>> comboModelMap){
		this.comboModelMap = comboModelMap;
	}
	
	public void addModelValue(int row, List<LiteComboModel> comboModels){
		comboModelMap.put(row, comboModels);
	}
	
	
	public void removeAndRenIndexMap(List<Integer> rowsRemoved){
		for(Integer integer : rowsRemoved){
			comboModelMap.remove(integer);
		}
		
		Map<Integer, List<LiteComboModel>> tempMap = new TreeMap<Integer, List<LiteComboModel>>();
		Set<Integer> keys = comboModelMap.keySet();
		int index = 0;
		for(Integer key : keys){
			tempMap.put(index, comboModelMap.get(key));
			index++;
		}
		
		this.comboModelMap = tempMap;
		
	}
	
	
	public List<LiteComboModel> getModelValue(int row){
		return comboModelMap.get(row);
	}
	
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    	model.removeAllElements();
    	List<LiteComboModel> comboModels = comboModelMap.get(row);
    	if(comboModels != null){
	        for(int i = 0; i < comboModels.size(); i++) {
	            model.addElement(comboModels.get(i));
	        }
    	}
        
        /*} else {
	        	model.removeAllElements();
	        	LiteComboModel selectedItem = (LiteComboModel) table.getValueAt(row, 0);
	             for(int i = 0; i < comboModels.size(); i++) {
	                    if(!selectedItem.equals(comboModels.get(i)))
	                    	model.addElement(comboModels.get(i));
	             } 
	         } 
*/
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
     }
	    
	
}
