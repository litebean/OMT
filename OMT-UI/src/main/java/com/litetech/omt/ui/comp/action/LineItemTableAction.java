package com.litetech.omt.ui.comp.action;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.ui.comp.editor.LiteComboModelCellEditor;
import com.litetech.omt.ui.comp.listener.TableCellListener;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.model.LiteComboBoxModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.util.NumberUtil;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;

public class LineItemTableAction extends AbstractAction{
	
	private DefaultTableModel registeredTaxModel;
	private TaxTableAction taxTableAction;
	private List<LineItemVO> lineItemVOs = Collections.emptyList();
	private boolean qtyWarningRequired = false;
	
	public LineItemTableAction(){
		
	}
	
	public LineItemTableAction(List<LineItemVO> lineItemVOs, boolean qtyWarningRequired){
		this.lineItemVOs = lineItemVOs;
		this.qtyWarningRequired = qtyWarningRequired;
	}
	
	public void registerTaxComponent(DefaultTableModel registeredTaxModel, TaxTableAction taxTableAction){
		this.registeredTaxModel = registeredTaxModel;
		this.taxTableAction = taxTableAction;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		TableCellListener tcl = (TableCellListener)e.getSource();
		
	    int row = tcl.getRow();
	    int column = tcl.getColumn();
	    
	    if(column == 2){
	    	//product selected/changed
	    	
	    	Object cellObj = tcl.getTable().getModel().getValueAt(row, column);
	    	LiteComboModel productCombo = (LiteComboModel)cellObj;
	    	ProductVO productVO = (ProductVO)productCombo.getRelativeObj();
	    	
	    	List<ProductUnitVO> productUnitVOs = productVO.getProductUnitVOs();
			Vector<LiteComboModel> unitComboBoxModels = constructUnitComboBoxModel(productUnitVOs);
			//unitComboBox.setSelectedIndex(0);
			
			//reset product unit
			Object obj = tcl.getTable().getCellEditor(row, 3);
			LiteComboModelCellEditor cellEditor = (LiteComboModelCellEditor)tcl.getTable().getCellEditor(row, 3);
			cellEditor.addModelValue(row, unitComboBoxModels);
			
			//reset unit
			tcl.getTable().getModel().setValueAt(new LiteComboModel(0, "Select Unit"), row, 4);
			//reset price
			tcl.getTable().getModel().setValueAt(0, row, 5);
			//reset total cost
			tcl.getTable().getModel().setValueAt(0, row, 7);
	    }else if(column == 4){
	    	//product unit modified
	    	Object cellObj = tcl.getTable().getModel().getValueAt(row, column);
	    	LiteComboModel productUnitCombo = (LiteComboModel)cellObj;
	    	Double priceRatio = (Double)productUnitCombo.getRelativeObj();
	    	
	    	Object prodCellObj = tcl.getTable().getModel().getValueAt(row, 2);
	    	LiteComboModel productCombo = (LiteComboModel)prodCellObj;
	    	ProductVO productVO = (ProductVO)productCombo.getRelativeObj();
	    	
	    	Map<Long, Double> orderedPrice = productVO.getOrderedPrice();
	    	Double price = 0d;
	    	if(orderedPrice != null && orderedPrice.get(productUnitCombo.getId()) != null){
	    		price = orderedPrice.get(productUnitCombo.getId());
	    	}else{
	    		Double prodPrice = productVO.getPrice();
	    		price = NumberUtil.roundToTwoDecimal(priceRatio * prodPrice);
	    	}
	    	//reset price
			tcl.getTable().getModel().setValueAt(price, row, 5);
	    	
			//reset total cost
			double selectedQty = 0;		
			Object quantityObj = tcl.getTable().getModel().getValueAt(row, 6);
			if(quantityObj != null && !quantityObj.toString().equals("")){
				selectedQty = new Double(quantityObj.toString());
			}
			
			double quantityPrice = NumberUtil.roundToTwoDecimal(selectedQty * price);
			tcl.getTable().getModel().setValueAt(quantityPrice, row, 7);
			
	    }else if(column == 5){
	    	//product price modified
	    	Double price = (Double)tcl.getTable().getModel().getValueAt(row, column);
	    	
	    	//reset total cost
			double selectedQty = 0;		
			Object quantityObj = tcl.getTable().getModel().getValueAt(row, 6);
			if(quantityObj != null && !quantityObj.toString().equals("")){
				selectedQty = new Double(quantityObj.toString());
			}
			double quantityPrice = NumberUtil.roundToTwoDecimal(selectedQty * price);
			tcl.getTable().getModel().setValueAt(quantityPrice, row, 7);
			
	    }else if(column == 6){
	    	Double selectedQty = (Double)tcl.getTable().getModel().getValueAt(row, column);
	    	
	    	if(qtyWarningRequired){
		    	Object prodCellObj = tcl.getTable().getModel().getValueAt(row, 2);
		    	LiteComboModel productCombo = (LiteComboModel)prodCellObj;
		    	ProductVO productVO = (ProductVO)productCombo.getRelativeObj();
		    	
		    	//product unit 
		    	Object prodUnitcellObj = tcl.getTable().getModel().getValueAt(row, 4);
		    	LiteComboModel productUnitCombo = (LiteComboModel)prodUnitcellObj;
		    	
		    	double alreadyInvQty = alreadyInvoicedQty(productVO.getProductId(), productUnitCombo.getId());
		    	double quantityAvail = quantityAvailable(productUnitCombo.getId(), productVO);
		    	
		    	double totalQtyAllowed = alreadyInvQty + quantityAvail;
		    	
		    	if(totalQtyAllowed < selectedQty){
		    		int choice = JOptionPane.showConfirmDialog(null, 
							"Invoiced Qty is [ " +(selectedQty - totalQtyAllowed)+"] Greater than Exisiting product Qty, Do you want to proceed ?", 
							"Confirm", JOptionPane.YES_NO_OPTION);
		        
					if(choice == JOptionPane.NO_OPTION){
						selectedQty = alreadyInvQty;
						tcl.getTable().getModel().setValueAt(alreadyInvQty, row, column);
						
					}
		    	}
	    	}
	    	
	    	Double price = (Double)tcl.getTable().getModel().getValueAt(row, 5);
	    	double quantityPrice = NumberUtil.roundToTwoDecimal(selectedQty * price);
	    	tcl.getTable().getModel().setValueAt(quantityPrice, row, 7);
	    }
	    
	    if(this.registeredTaxModel != null){
	    	resetTotalCell((DefaultTableModel)(tcl.getTable().getModel()));
	    }
	    
	}
	
	
	private double alreadyInvoicedQty(long productId, long unitId){
		for(LineItemVO lineItemVO : lineItemVOs){
			if(productId == lineItemVO.getProductVO().getProductId() &&
					unitId == lineItemVO.getUnitVO().getUnitId()){
				return lineItemVO.getQuantity();
			}
		}
		
		return 0;
	}
	
	private double quantityAvailable(long unitId, ProductVO productVO){
		List<ProductUnitVO> productUnitVOs = productVO.getProductUnitVOs();
		for(ProductUnitVO productUnitVO : productUnitVOs){
			if(unitId == productUnitVO.getUnitId()){
				return productVO.getQuantityAvl() * productUnitVO.getQuanityRatio(); 
			}
		}
		return productVO.getQuantityAvl();
	}
	
	public void resetTotalCell(DefaultTableModel defaultTableModel){
		int totalRow = defaultTableModel.getRowCount() - 1;
		int priceColumn = 7;
		Double totalPrice = 0d;
		for(int i = 0; i <= totalRow; i++){
			Double price = new Double(defaultTableModel.getValueAt(i, priceColumn).toString());
			totalPrice = totalPrice + price;
		}
		totalPrice = NumberUtil.roundToTwoDecimal(totalPrice);
		registeredTaxModel.setValueAt(totalPrice, 0, 1);
		taxTableAction.resetModelValues(registeredTaxModel, 1, 0);
	}
	
	private Vector<LiteComboModel> constructUnitComboBoxModel(List<ProductUnitVO> unitVOs){
		Vector<LiteComboModel> unitLs = new Vector<LiteComboModel>();
		if(unitVOs != null && !unitVOs.isEmpty()){
			for(ProductUnitVO unitVO: unitVOs){
				System.out.println("unt id "+unitVO.getUnitId() + " unit name "+ unitVO.getUnitName() + " ratio "+ unitVO.getPriceRatio());
				unitLs.add(new LiteComboModel(unitVO.getUnitId(), unitVO.getUnitName(), unitVO.getPriceRatio()));
			}
		}
		return unitLs;
	}

}
