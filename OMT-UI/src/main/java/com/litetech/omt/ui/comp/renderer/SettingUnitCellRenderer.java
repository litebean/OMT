package com.litetech.omt.ui.comp.renderer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.litetech.omt.ui.comp.MainMenu.MenuListener;
import com.litetech.omt.ui.model.UnitTableModel;

public class SettingUnitCellRenderer extends DefaultTableCellRenderer {

	private static final Icon ADD_ICON = new ImageIcon(MenuListener.class.getResource("/img/add_small.jpg"));
	private static final Icon CLOSE_ICON = new ImageIcon(MenuListener.class.getResource("/img/closeTabButton.png"));
	public static final Icon EDIT_ICON = new ImageIcon(MenuListener.class.getResource("/img/edit-icon.jpg"));
	public static final Icon SAVE_ICON = new ImageIcon(MenuListener.class.getResource("/img/save.jpg"));
	
	private UnitRendererEnum cellOperation;
	private UnitTableModel unitTableModel;
	
	
	
	public SettingUnitCellRenderer(UnitRendererEnum cellOperation, UnitTableModel unitTableModel){
		this.cellOperation = cellOperation;
		this.unitTableModel = unitTableModel;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable tbl, Object val,
			boolean arg2, boolean arg3, int row, int col) {
		
		int totalRow = tbl.getModel().getRowCount() - 1;
		
		if (val != null) {
			if(UnitRendererEnum.EDIT.equals(cellOperation)){
				if(row == unitTableModel.getEditableRow()){
					setIcon(SAVE_ICON);
				}else{
					setIcon(EDIT_ICON);
				}
			}else if(UnitRendererEnum.ADD.equals(cellOperation)){
				if(totalRow == row && unitTableModel.getEditableRow() == -1){
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
	
	
	public enum UnitRendererEnum{
		
		ADD(1, "Add"),
		EDIT(2, "Edit"),
		DELETE(3, "Delete");
		
		private int id;
		private String name;
		
		private UnitRendererEnum(int id, String name){
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
