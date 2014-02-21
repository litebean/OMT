package com.litetech.omt.ui.comp.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.litetech.omt.ui.comp.MainMenu.MenuListener;
import com.litetech.omt.ui.comp.renderer.SettingUnitCellRenderer.UnitRendererEnum;
import com.litetech.omt.ui.model.BankTableModel;
import com.litetech.omt.ui.model.UnitTableModel;

public class SettingBankCellRenderer extends DefaultTableCellRenderer{
	private static final Icon ADD_ICON = new ImageIcon(MenuListener.class.getResource("/img/add_small.jpg"));
	private static final Icon CLOSE_ICON = new ImageIcon(MenuListener.class.getResource("/img/closeTabButton.png"));
	public static final Icon EDIT_ICON = new ImageIcon(MenuListener.class.getResource("/img/edit-icon.jpg"));
	public static final Icon SAVE_ICON = new ImageIcon(MenuListener.class.getResource("/img/save.jpg"));
	
	private UnitRendererEnum cellOperation;
	private BankTableModel bankTableModel;
	
	
	
	public SettingBankCellRenderer(UnitRendererEnum cellOperation, BankTableModel bankTableModel){
		this.cellOperation = cellOperation;
		this.bankTableModel = bankTableModel;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable tbl, Object val,
			boolean arg2, boolean arg3, int row, int col) {
		
		int totalRow = tbl.getModel().getRowCount() - 1;
		
		if (val != null) {
			if(UnitRendererEnum.EDIT.equals(cellOperation)){
				if(row == bankTableModel.getEditableRow()){
					setIcon(SAVE_ICON);
				}else{
					setIcon(EDIT_ICON);
				}
			}else if(UnitRendererEnum.ADD.equals(cellOperation)){
				if(totalRow == row && bankTableModel.getEditableRow() == -1){
					setIcon(ADD_ICON);
	        	}else{
	        		setIcon(null);
	        	}
			}else if(UnitRendererEnum.DELETE.equals(cellOperation)){
				if(row != 0){
					setIcon(CLOSE_ICON);
				}else{
					setIcon(null);
				}
			}else{
        		setIcon(null);
        	}
        }
                
        return this;	
	}
}
