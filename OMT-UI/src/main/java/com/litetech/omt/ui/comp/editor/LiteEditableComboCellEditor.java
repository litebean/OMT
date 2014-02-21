package com.litetech.omt.ui.comp.editor;

import java.awt.Component;
import java.awt.Dimension;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import com.jgoodies.looks.common.ComboBoxEditorTextField;
import com.litetech.omt.ui.comp.AppContext;
import com.litetech.omt.ui.comp.ComponentRegistry;
import com.litetech.omt.ui.model.LiteComboModel;


public class LiteEditableComboCellEditor extends AbstractCellEditor implements TableCellEditor{

	private JComboBox comboBox;
    //private ComboBoxEditorTextField textField;
	private Vector<LiteComboModel> comboDataLs;
	public LiteEditableComboCellEditor(Vector<LiteComboModel> modelLs){
		comboBox = new JComboBox(modelLs);
		//textField = (ComboBoxEditorTextField)comboBox.getComponent(2);
		
		this.comboBox.setEditable(true);
		comboDataLs = new Vector<LiteComboModel>();
		comboDataLs.addAll(modelLs);
		comboBox.getEditor().getEditorComponent().addFocusListener(new LiteEditableComboCellEditorFocusAdapter(this));
	    comboBox.getEditor().getEditorComponent().addKeyListener(new LiteEditableComboCellEditorKeyAdapter(this));
	}
	
	
	
	@Override
	public Object getCellEditorValue() {
		return comboBox.getEditor().getItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		this.comboBox.setEditable(true);
		
		String strValue = value != null ? value.toString() : null; 
		populateDropDown(strValue);
				
		return comboBox;
		
	}
	
	public  void comboBox_focusGained(FocusEvent e){
		onclickEvent();
	}	

	public void comboBox_keyReleased(KeyEvent evt){
		JTextField tf = (JTextField) evt.getSource();
		String text = tf.getText();
		int caretPos = tf.getCaretPosition();
		
	    if(evt.getKeyCode() == KeyEvent.VK_SHIFT || evt.getKeyCode() == KeyEvent.VK_TAB){
	        return;
	    }
	      
	    if(evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP){
	      return;
	    }
	    
	    if(evt.getKeyCode() == KeyEvent.VK_LEFT || evt.getKeyCode() == KeyEvent.VK_RIGHT){
		      return;
		}
	    
	    populateDropDown(text);
	   
	    if(evt.getKeyCode() != KeyEvent.VK_ENTER){
	    	tf.setText(text);
	    	tf.setCaretPosition(caretPos);
	    }	    
	}
	
	
	private void onclickEvent(){
		ComboBoxEditorTextField textField = (ComboBoxEditorTextField)this.comboBox.getComponent(2);
		String text = textField.getText();
		int caretPos = textField.getCaretPosition();
		
		populateDropDown(text);
		
		textField.setText(text);
		textField.setCaretPosition(caretPos);
		
		comboBox.showPopup();
	}
	
	private void populateDropDown(String text){
		int elementAdded = 0;
		Vector<LiteComboModel> searchDataLs = getSearchList(comboBox, text);
		DefaultComboBoxModel model = (DefaultComboBoxModel)(comboBox.getModel());
		model.removeAllElements();
			
		
		for(LiteComboModel liteComboModel : searchDataLs){
			model.addElement(liteComboModel);
			elementAdded++;
		}	
		
		for(int i = elementAdded; i < 5; i++){
			model.addElement(new LiteComboModel(-1l, ""));
		}
		comboBox.setMaximumRowCount(5);
	}
	
	public Vector<LiteComboModel> getSearchList(JComboBox combo, String head){
		Vector<LiteComboModel> searchList = new Vector<LiteComboModel>();
		
		if(head != null && !head.trim().equals("")){
			for (int i = 0; i < comboDataLs.size(); i++) {
				String s = comboDataLs.get(i).toString();
					
				if(s.toLowerCase().contains(head.toLowerCase())){
					searchList.add((LiteComboModel)comboDataLs.get(i));
				}		
			}
		}else{
			searchList.addAll(comboDataLs);
		}
		
		return searchList;
	}
	

	
	class LiteEditableComboCellEditorFocusAdapter extends java.awt.event.FocusAdapter {
		LiteEditableComboCellEditor adaptee;

		LiteEditableComboCellEditorFocusAdapter(LiteEditableComboCellEditor adaptee){
	      this.adaptee = adaptee;
	    }
		
	    public void focusGained(FocusEvent e) {
	      adaptee.comboBox_focusGained(e);
	    
	    }	  
	    
	  }

	  	
		
	  class LiteEditableComboCellEditorKeyAdapter extends java.awt.event.KeyAdapter {
		LiteEditableComboCellEditor adaptee;
		LiteEditableComboCellEditorKeyAdapter(LiteEditableComboCellEditor adaptee){
	      this.adaptee = adaptee;
	    }
		
	    public void keyReleased(KeyEvent e) {
	    	adaptee.comboBox_keyReleased(e);
	    }
	    
	    
	    @Override
	    public void keyPressed(KeyEvent e){
	    	//adaptee.comboBox_keyReleased(e);
	    }
	  }

}
