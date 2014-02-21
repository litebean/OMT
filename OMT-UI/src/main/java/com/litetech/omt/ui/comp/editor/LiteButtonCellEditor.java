package com.litetech.omt.ui.comp.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.ui.model.LiteComboModel;

public class LiteButtonCellEditor extends DefaultCellEditor {
	  	  
	 
	  public LiteButtonCellEditor(JCheckBox checkBox) {
	    super(checkBox);
	  }
	  
	  public Component getTableCellEditorComponent(JTable table, Object value,
              boolean isSelected, int row, int column) {
		  
		  int totalRow = table.getModel().getRowCount() - 1;
		  	 	 
		  if(row > 1){
			  if(row == totalRow){
				  Object prevTotalCellObj = ((DefaultTableModel)table.getModel()).getValueAt(totalRow, 1);
				  double totalVal = new Double(prevTotalCellObj.toString());
				  Object[] taxArray = {new LiteComboModel(-1, "Select Option"), 0d, "", 0l};
				  ((DefaultTableModel)table.getModel()).addRow(taxArray);
				  Object[] totalArray = {"Total", totalVal, "", 0l};
				  ((DefaultTableModel)table.getModel()).addRow(totalArray);
			  }else if(row == totalRow - 1){
				  ((DefaultTableModel)table.getModel()).removeRow(row);
				  ((DefaultTableModel)table.getModel()).removeRow(row);
			  }
		  }
		  
		  return null;
	  }

	 
	  
}
