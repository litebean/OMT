package com.litetech.omt.vo;

import java.util.Date;

import com.litetech.omt.constant.OMTSwitchEnum;

public class InvoiceChargeVO implements Comparable<InvoiceChargeVO>{
	private long id;
	private long invoiceId;
	private ChargeVO charge;
	private double chargedAmount;
	private Date creationDate;
	private Date lastModifiedDate;
	private OMTSwitchEnum purchase;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public ChargeVO getCharge() {
		return charge;
	}
	public void setCharge(ChargeVO charge) {
		this.charge = charge;
	}
	public double getChargedAmount() {
		return chargedAmount;
	}
	public void setChargedAmount(double chargedAmount) {
		this.chargedAmount = chargedAmount;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	public OMTSwitchEnum getPurchase() {
		return purchase;
	}
	public void setPurchase(OMTSwitchEnum purchase) {
		this.purchase = purchase;
	}
	@Override
	public int compareTo(InvoiceChargeVO arg0) {
		int diff = 0;
		if(arg0 != null){
			diff = (int)(this.id - arg0.getId());
		}else{
			diff = 1;
		}
		return diff;
	}
	
	
	
}
