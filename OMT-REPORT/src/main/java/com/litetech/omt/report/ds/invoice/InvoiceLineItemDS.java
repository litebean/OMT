package com.litetech.omt.report.ds.invoice;


import java.util.Collections;
import java.util.List;

import com.litetech.omt.report.ds.BaseDS;
import com.litetech.omt.util.NumberUtil;
import com.litetech.omt.vo.LineItemVO;
import net.sf.jasperreports.engine.JRException;

public class InvoiceLineItemDS extends BaseDS{
	
	private List<LineItemVO> lineItemVOs = Collections.emptyList();
	private int minLineItemCt = 16;
	private LineItemVO lineItemVO;
	
	public InvoiceLineItemDS(List<LineItemVO> lineItemVOs){
		this.lineItemVOs = lineItemVOs;
	}
	
	public InvoiceLineItemDS(){
		
	}
	
	public boolean next() throws JRException {
		if(index < minLineItemCt || index < lineItemVOs.size()){
			
			if(index < lineItemVOs.size()){
				lineItemVO = lineItemVOs.get(index);
			}else{
				lineItemVO = null;
			}
			
			index++;
			return true;
		}
		return false;
	}

		
	public String getSlNo(){
		String indexStr = lineItemVO == null ? "" : String.valueOf(index);
		return indexStr;
	}
	
	public String getProductName(){
		String text = lineItemVO == null ? "" :
			lineItemVO.getProductVO().getProductName();
		
		return text;
	}
	
	public String getDescription(){
		String text = ""; 
		if(lineItemVO != null){
			text = lineItemVO.getProductVO().getProductName()  +" - "+ lineItemVO.getLineItemDesc();
		}
		return text;
	}
	
	public String getUnit(){
		String text = lineItemVO == null ? "" :
			lineItemVO.getUnitVO().getUnitName();
		return text;
	}
	
	public String getPrice(){
		String price = "";
		if(lineItemVO != null){
			price = NumberUtil.convertInTwoDecimalFormat(lineItemVO.getPrice());
		}
		return price; 
	}
	
	public String getQuantity(){
		String text = lineItemVO == null ? "" :
			String.valueOf(lineItemVO.getQuantity());
		return text;
	}
	
	public String getTotalPrice(){
		String totalPriceStr = "";
		if(lineItemVO != null){
			totalPriceStr = NumberUtil.convertInTwoDecimalFormat(
					lineItemVO.getTotalCost());
		}
		return totalPriceStr;
	}

}
