package com.litetech.omt.ui.comp.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.litetech.omt.ui.comp.MainMenu.MenuListener;
import com.litetech.omt.ui.model.ChargeTableModel;

public class SettingChargeCellRenderer extends DefaultTableCellRenderer {

	private static final Icon ADD_ICON = new ImageIcon(MenuListener.class.getResource("/img/add_small.jpg"));
	private static final Icon CLOSE_ICON = new ImageIcon(MenuListener.class.getResource("/img/closeTabButton.png"));
	public static final Icon EDIT_ICON = new ImageIcon(MenuListener.class.getResource("/img/edit-icon.jpg"));
	public static final Icon SAVE_ICON = new ImageIcon(MenuListener.class.getResource("/img/save.jpg"));
	
	private ChargeRendererEnum cellOperation;
	private ChargeTableModel chargeTableModel;
	
	public SettingChargeCellRenderer(ChargeTableModel chargeTableModel,
			ChargeRendererEnum cellOperation){
		this.chargeTableModel = chargeTableModel;
		this.cellOperation = cellOperation;
	}
	@Override
	public Component getTableCellRendererComponent(JTable tbl, Object val,
			boolean arg2, boolean arg3, int row, int col) {
		
		int totalRow = tbl.getModel().getRowCount() - 1;
		
		if (val != null) {
			if(ChargeRendererEnum.EDIT.equals(cellOperation)){
				if(row == chargeTableModel.getEditableRow()){
					setIcon(SAVE_ICON);
				}else{
					setIcon(EDIT_ICON);
				}
			}else if(ChargeRendererEnum.ADD.equals(cellOperation)){
				if(totalRow == row && chargeTableModel.getEditableRow() == -1){
					setIcon(ADD_ICON);
	        	}else{
	        		setIcon(null);
	        	}
			}else if(ChargeRendererEnum.DELETE.equals(cellOperation)){
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
	
	
	public enum ChargeRendererEnum{
		
		ADD(1, "Add"),
		EDIT(2, "Edit"),
		DELETE(3, "Delete");
		
		private int id;
		private String name;
		
		private ChargeRendererEnum(int id, String name){
			this.id = id;
			this.name= name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
	}

}
