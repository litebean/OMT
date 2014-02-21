package com.litetech.omt.ui.comp.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.litetech.omt.ui.comp.MainMenu.MenuListener;

public class ProductUnitPriceCloseRenderer extends DefaultTableCellRenderer {

	private static final Icon CLOSE_TAB_ICON = new ImageIcon(MenuListener.class.getResource("/img/closeTabButton.png"));
	@Override
	public Component getTableCellRendererComponent(JTable tbl, Object val,
			boolean arg2, boolean arg3, int row, int col) {
				
		//super.getTableCellRendererComponent(tbl, val, arg2, arg3, row, col);
		setIcon(CLOSE_TAB_ICON);
		if(row == 0){
			setIcon(null);
    	}
		return this;
	}
}
