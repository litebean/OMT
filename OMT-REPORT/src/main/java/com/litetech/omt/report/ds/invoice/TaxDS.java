package com.litetech.omt.report.ds.invoice;

import java.lang.reflect.Method;
import java.util.List;

import com.litetech.omt.report.ds.BaseDS;
import com.litetech.omt.util.CurrencyUtil;
import com.litetech.omt.util.NumberUtil;
import com.litetech.omt.vo.InvoiceChargeVO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class TaxDS extends BaseDS{

	
	private List<InvoiceChargeVO> invoiceCharges;
	private InvoiceChargeVO invoiceChargeVO;
	
	public TaxDS(List<InvoiceChargeVO> invoiceCharges){
		this.invoiceCharges = invoiceCharges;
	}
	
	public boolean next() throws JRException {
		if(index < invoiceCharges.size()){
			invoiceChargeVO = invoiceCharges.get(index);
			
			index++;
			return true;
		}
		return false;
	}
	
	//compName;compValue;
	public String getCompName(){
		String val = invoiceChargeVO.getCharge().getName() == null ? "Total" : invoiceChargeVO.getCharge().getName(); 
		return val;
	}
	
	public String getCompValue(){
		return NumberUtil.convertInTwoDecimalFormat(
				invoiceChargeVO.getChargedAmount());		 
	}
	
	
	
}
