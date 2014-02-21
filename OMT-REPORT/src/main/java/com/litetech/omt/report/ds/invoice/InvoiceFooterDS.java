package com.litetech.omt.report.ds.invoice;

import java.util.List;

import com.litetech.omt.report.ds.BaseDS;
import com.litetech.omt.util.CurrencyUtil;
import com.litetech.omt.util.StringUtil;
import com.litetech.omt.vo.InvoiceChargeVO;


public class InvoiceFooterDS extends BaseDS{
	
	List<InvoiceChargeVO> invoiceCharges;
	public InvoiceFooterDS(List<InvoiceChargeVO> invoiceCharges){
		this.invoiceCharges = invoiceCharges;
	}
	//grNo;amountInWords;cstCharge;
	
	public String getGrNo(){
		return "";
	}
	
	
	
	public String getAmountInWords(){
		double amount = invoiceCharges.get(invoiceCharges.size() - 1).
				getChargedAmount();
		String amt = CurrencyUtil.convertINR(amount);
		return StringUtil.toInitCap(amt);
	}
	
	public String getCstCharge(){
		return "";
	}

}
