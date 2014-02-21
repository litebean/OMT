package com.litetech.omt.ui.comp.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.litetech.omt.ui.comp.MainMenu.MenuListener;


public class InvoiceTaxCellRederer extends DefaultTableCellRenderer {

	public static final Icon ADD_ICON = new ImageIcon(MenuListener.class.getResource("/img/add_small.jpg"));
	private static final Icon CLOSE_TAB_ICON = new ImageIcon(MenuListener.class.getResource("/img/closeTabButton.png"));

	@Override
	public Component getTableCellRendererComponent(JTable tbl, Object val,
			boolean arg2, boolean arg3, int row, int col) {
				
		//super.getTableCellRendererComponent(tbl, val, arg2, arg3, row, col);
		int totalRow = tbl.getModel().getRowCount() - 1;
		if (val != null) {
        	if(totalRow == row){
        		setIcon(ADD_ICON);
        	}else if((totalRow - 1) == row && row > 2){
        		setIcon(CLOSE_TAB_ICON);
        	}else{
        		setIcon(null);
        	}
        }
        
        return this;
	}	
	
}
