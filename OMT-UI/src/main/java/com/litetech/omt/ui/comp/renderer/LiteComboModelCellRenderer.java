package com.litetech.omt.ui.comp.renderer;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.litetech.omt.ui.model.LiteComboModel;

public class LiteComboModelCellRenderer extends DefaultTableCellRenderer{


	
	@Override
	public Component getTableCellRendererComponent(JTable tbl, Object val,
			boolean arg2, boolean arg3, int row, int col) {
		
		
		super.getTableCellRendererComponent(tbl, val, arg2, arg3, row, col);

        if (val != null) {
        	if(val instanceof LiteComboModel){
        		LiteComboModel item = (LiteComboModel)val;
        		setText(item.getName());
        	}else{
        		setText(val.toString());
        	}
        	
        	
        }
        
        return this;
	}

}