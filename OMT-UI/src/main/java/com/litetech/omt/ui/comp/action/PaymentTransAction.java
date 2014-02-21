package com.litetech.omt.ui.comp.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.litetech.omt.ui.comp.listener.TableCellListener;
import com.litetech.omt.util.NumberUtil;
import com.litetech.omt.vo.InvoicePaymentVO;

public class PaymentTransAction extends AbstractAction{

	private JTextField detectFromField;
	private JTextField resetTextField;
	
	
	public PaymentTransAction(JTextField detectFromField, JTextField resetTextField){
		this.detectFromField = detectFromField;
		this.resetTextField = resetTextField;
 	}
	
	
	
	
	public void registerTextFieldActionListener(final JTable paymentTable){
			
		detectFromField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				resetDependency(paymentTable, 6);				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});		
	}
	
	public void resetUntilizedAmt(JTable table){
		resetDependency(table, 6);
	}
	
	private void resetDependency(JTable table, int column){
		int totalRows = table.getModel().getRowCount();
    	double totalValue = 0;
    	for(int i = 0; i < totalRows; i++){
    		Object cellObj = table.getModel().getValueAt(i, column);
    		if(cellObj != null && !cellObj.toString().trim().equals("")){
    			totalValue += new Double(cellObj.toString());
    		}
    	}
    	String text = detectFromField.getText().equals("") ? "0" : detectFromField.getText(); 	    	   	
    	double transAmt = new Double(text);
    	double remainingAmt = transAmt - totalValue;
    	remainingAmt = NumberUtil.roundToTwoDecimal(remainingAmt);
    	if(remainingAmt == 0){
    		resetTextField.setBackground(Color.WHITE);
    	}else if(remainingAmt > 0){
    		resetTextField.setBackground(Color.GREEN);
    	}else{
    		resetTextField.setBackground(Color.RED);
    	}
    	resetTextField.setText(String.valueOf(remainingAmt));
	}	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		TableCellListener tcl = (TableCellListener)e.getSource();
		int column = tcl.getColumn();
	    
	    if(column == 6){
	    	resetDependency(tcl.getTable(), column);   	
	    }
	}
}
