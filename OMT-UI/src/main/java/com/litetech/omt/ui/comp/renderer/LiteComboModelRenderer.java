package com.litetech.omt.ui.comp.renderer;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.litetech.omt.ui.model.LiteComboModel;

public class LiteComboModelRenderer extends BasicComboBoxRenderer {
	
	
	public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value != null) {
        	LiteComboModel item = (LiteComboModel)value;
        	setText(item.getName());
        }
        /*if (index == -1){
        	LiteComboModel item = (LiteComboModel)value;
            setText( "" + item.getId() );
        }*/
        return this;
    }
}
