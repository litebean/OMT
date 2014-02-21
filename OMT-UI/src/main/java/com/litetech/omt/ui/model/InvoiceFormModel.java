package com.litetech.omt.ui.model;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.litetech.omt.vo.InvoiceChargeVO;
import com.litetech.omt.vo.InvoicePaymentVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;

public class InvoiceFormModel {

	
	private boolean purchase;
	private long invoiceId; 
	private Date invoiceDate;
	private long customerId;
	private int status;
	
	
	private long orderId;
	private Date orderDate;
	private String partyOrderId;
	private Date partyOrderDate;
	
	private String dcNo;
	private Date dcDate;
	private String description;
	private double amountPaid;
	
	private List<LineItemVO> lineItemLs = Collections.emptyList();
	private List<InvoiceChargeVO> invoiceChargeVOs = Collections.emptyList();
	private List<InvoicePaymentVO> invoicePaymentVOs = Collections.emptyList();
	
	public InvoiceFormModel(boolean purchase){
		this.purchase = purchase;
	}
	
	
	
	public boolean isPurchase() {
		return purchase;
	}



	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}



	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	
	
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	
	
	
	public List<LineItemVO> getLineItemLs() {
		return lineItemLs;
	}
	public void setLineItemLs(List<LineItemVO> lineItemLs) {
		this.lineItemLs = lineItemLs;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}
	
	
	
	
	
	public List<InvoiceChargeVO> getInvoiceChargeVOs() {
		return invoiceChargeVOs;
	}
	public void setInvoiceChargeVOs(List<InvoiceChargeVO> invoiceChargeVOs) {
		this.invoiceChargeVOs = invoiceChargeVOs;
	}
	public Object[][] getInvoiceChargeRows(){
		int totalRow = invoiceChargeVOs.isEmpty() ? 3 : invoiceChargeVOs.size();
		Object[][] rows = new Object[totalRow][4];
		
		int rowFilled = 0;
		for (int index = 0; index < invoiceChargeVOs.size(); index++) {
			
			
			InvoiceChargeVO invoiceChargeVO = invoiceChargeVOs.get(index);
			Object chargeName = "Total";
			
			if(invoiceChargeVO.getCharge() != null && invoiceChargeVO.getCharge().getId() != -1){
				chargeName = new LiteComboModel(invoiceChargeVO.getCharge().getId(), invoiceChargeVO.getCharge().getName(), invoiceChargeVO.getCharge());
			}
			
			rows[index][0] = chargeName;
			rows[index][1] = invoiceChargeVO.getChargedAmount();
			rows[index][2] = ""; 
			rows[index][3] = invoiceChargeVO.getId();
			
			rowFilled++;
		}
		
		if(rowFilled < totalRow){
			double total = getTotal();
			rows[0] = new Object[]{"Total", total, "", 0l};
			rows[1] = new Object[]{new LiteComboModel(0, "Select Tax"), 0d, "", 0l};
			rows[2] = new Object[]{"Total", total, "", 0l};
		}
		
		return rows;
	}
	
	private double getTotal(){
		double total = 0d;
		for (int index = 0; index < lineItemLs.size(); index++) {
			LineItemVO lineItemVO = lineItemLs.get(index);
			total += lineItemVO.getTotalCost();
		}
		
		return total;
	}
	
	public Object[][] getLineItemRows(){
		int totalRow = lineItemLs.isEmpty() ? 3: lineItemLs.size();
		Object[][] rows = new Object[totalRow][9];
		
		int rowFilled = 0;
		for(int index = 0; index < lineItemLs.size(); index++){
			LineItemVO lineItemVO = lineItemLs.get(index);
			
			//line item id
			rows[index][0] = lineItemVO.getId();
			
			//check box value always unchecked by default
			rows[index][1] = false;
			
			//product id, name
			ProductVO productVO = lineItemVO.getProductVO();
			rows[index][2] = new LiteComboModel(productVO.getProductId(), productVO.getProductName(), productVO);
			
			rows[index][3] = lineItemVO.getLineItemDesc();
			//set unit id
			ProductUnitVO unitVO = lineItemVO.getUnitVO();
			rows[index][4] = new LiteComboModel(unitVO.getUnitId(), unitVO.getUnitName(), unitVO.getPriceRatio());
			
			rows[index][5] = lineItemVO.getPrice();
			
			rows[index][6] = lineItemVO.getQuantity();
			
			rows[index][7] = lineItemVO.getTotalCost();
			
			rows[index][8]= lineItemVO.getInvoicedQty();
			rowFilled++;
		}
		
		if(rowFilled < totalRow){
			for(int remainingCt = rowFilled; remainingCt < totalRow; remainingCt++){
				rows[remainingCt] = new Object[]{0l, Boolean.TRUE, new LiteComboModel(0, "select a product"), " ", new LiteComboModel(0, "select a unit"), 0d, 0d, 0d, 0d};
			}
		}
		
		return rows;
	}
	public List<InvoicePaymentVO> getInvoicePaymentVOs() {
		return invoicePaymentVOs;
	}
	public void setInvoicePaymentVOs(List<InvoicePaymentVO> invoicePaymentVOs) {
		this.invoicePaymentVOs = invoicePaymentVOs;
	}
	
	
	public Object[][] getPaymentRows(){
		Object[][] rows = null;
		
		int row = 0;
		if(invoicePaymentVOs != null){
			rows = new Object[invoicePaymentVOs.size()][5];
			for(InvoicePaymentVO invoicePaymentVO : invoicePaymentVOs){
				rows[row][0] = invoicePaymentVO.getPaymentTransactionId();
				rows[row][1] = invoicePaymentVO.getId();
				rows[row][2] = invoicePaymentVO.getStatus().toString();
				rows[row][3] = getDateAsString(invoicePaymentVO.getCreationDate());
				rows[row][4] = invoicePaymentVO.getPaymentAmount();				
				row++;
			}
		}		
		return rows;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}



	public String getPartyOrderId() {
		return partyOrderId;
	}



	public void setPartyOrderId(String partyOrderId) {
		this.partyOrderId = partyOrderId;
	}



	public Date getPartyOrderDate() {
		return partyOrderDate;
	}



	public void setPartyOrderDate(Date partyOrderDate) {
		this.partyOrderDate = partyOrderDate;
	}



	public Date getDcDate() {
		return dcDate;
	}



	public void setDcDate(Date dcDate) {
		this.dcDate = dcDate;
	}
	
	
	

}
