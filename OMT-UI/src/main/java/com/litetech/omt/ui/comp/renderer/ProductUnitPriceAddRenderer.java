package com.litetech.omt.ui.comp.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.litetech.omt.ui.comp.MainMenu.MenuListener;

public class ProductUnitPriceAddRenderer  extends DefaultTableCellRenderer {
	
	public static final Icon ADD_ICON = new ImageIcon(MenuListener.class.getResource("/img/add_small.jpg"));
	
	@Override
	public Component getTableCellRendererComponent(JTable tbl, Object val,
			boolean arg2, boolean arg3, int row, int col) {
				
		//super.getTableCellRendererComponent(tbl, val, arg2, arg3, row, col);
		int totalRow = tbl.getModel().getRowCount() - 1;
		
		if(totalRow == row){
    		setIcon(ADD_ICON);
		}else{
			setIcon(null);
		}
		
		return this;
	}
}
