package com.litetech.omt.ui.comp.editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import com.jgoodies.looks.common.ComboBoxEditorTextField;
import com.litetech.omt.ui.model.LiteComboModel;

public class LiteEditableComboBoxEditor extends BasicComboBoxEditor{

	private JComboBox comboBox;
	private Vector<LiteComboModel> comboDataLs;
	private Object selectedItem;
    private ComboBoxEditorTextField textField;
	
	public LiteEditableComboBoxEditor(JComboBox comboBox, Vector<LiteComboModel> modelLs){
		this.comboBox = comboBox;
		comboBox.setEditable(true);
		
		this.textField = (ComboBoxEditorTextField)comboBox.getComponent(2);
		
		Dimension dimension = new Dimension(160, 25);
		
		textField.setPreferredSize(dimension);
		textField.setMinimumSize(dimension);
		textField.setMaximumSize(dimension);
		
		comboBox.setPreferredSize(dimension);
		comboBox.setMaximumSize(dimension);
		comboBox.setMinimumSize(dimension);
	        
        //panel.setBackground(Color.GREEN);

		
		comboDataLs = new Vector<LiteComboModel>();
		comboDataLs.addAll(modelLs);
		
		this.textField.addFocusListener(new LiteEditableComboEditorFocusAdapter(this));
		this.textField.addKeyListener(new LiteEditableComboEditorKeyAdapter(this));		
	}
	
	@Override
	public Component getEditorComponent() {
        return this.textField;
    }
     
	
	@Override
	public Object getItem() {
		return selectedItem;
	}
	
	
	@Override
	public void setItem(Object selectedItem) {
		this.selectedItem = selectedItem;
		if(selectedItem != null){
			textField.setText(selectedItem.toString());
		}
	}
	
	
/*	@Override
	public void addActionListener(ActionListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component getEditorComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeActionListener(ActionListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItem(Object arg0) {
		// TODO Auto-generated method stub
		
	}*/
	
	public  void comboBox_focusGained(FocusEvent e){
		comboBox.showPopup();
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
	
}

class LiteEditableComboEditorFocusAdapter extends java.awt.event.FocusAdapter {
	LiteEditableComboBoxEditor adaptee;

	LiteEditableComboEditorFocusAdapter(LiteEditableComboBoxEditor adaptee){
      this.adaptee = adaptee;
    }
	
    public void focusGained(FocusEvent e) {
      adaptee.comboBox_focusGained(e);
    
    }	  
    
  }

  	

  class LiteEditableComboEditorKeyAdapter extends java.awt.event.KeyAdapter {
	LiteEditableComboBoxEditor adaptee;
	LiteEditableComboEditorKeyAdapter(LiteEditableComboBoxEditor adaptee){
      this.adaptee = adaptee;
    }
	
	@Override
    public void keyReleased(KeyEvent e) {
    	adaptee.comboBox_keyReleased(e);
    }
    
    
    @Override
    public void keyPressed(KeyEvent e){
    	//adaptee.comboBox_keyReleased(e);
    }
  }


