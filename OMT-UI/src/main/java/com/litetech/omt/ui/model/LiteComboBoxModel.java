package com.litetech.omt.ui.model;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;


public class LiteComboBoxModel implements ComboBoxModel  {

	List<String> dropDownList;
	String selectedValue;
	
	public LiteComboBoxModel(List<String> dropDownList, String selectedValue){
		this.dropDownList = dropDownList;
		this.selectedValue = selectedValue;
	}
	
	public Object getSelectedItem() {
		return selectedValue;
	}

	public void setSelectedItem(Object selectedValue) {
		this.selectedValue = selectedValue.toString();
		
	}

	public void addListDataListener(ListDataListener arg0) {
		
	}

	public Object getElementAt(int arg0) {
		return dropDownList.get(arg0);
	}

	public int getSize() {
		return dropDownList.size();
	}

	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub
		
	}

}
