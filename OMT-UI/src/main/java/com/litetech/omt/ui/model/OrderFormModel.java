package com.litetech.omt.ui.model;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.LineItemVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.ProductUnitVO;

public class OrderFormModel {

	
	private long orderId;
	private Date orderDate;
	private String partyOrderId;
	private Date partyOrderDate;
	private OrderStatusEnum orderStatus;
	private long customerId;
	private long salesId;
	private List<LineItemVO> lineItemLs = Collections.emptyList();
	private List<InvoiceVO> invoiceLs = Collections.emptyList();
	private Map<Integer,  List<LiteComboModel>> unitCombiMap; 
	private boolean purchase;
	
	public OrderFormModel(boolean purchase){
		this.purchase = purchase;
	}
	
	
	
	public boolean isPurchase() {
		return purchase;
	}



	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}



	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	
	
	public Map<Integer, List<LiteComboModel>> getUnitCombiMap() {
		return unitCombiMap;
	}
	public void setUnitCombiMap(Map<Integer, List<LiteComboModel>> unitCombiMap) {
		this.unitCombiMap = unitCombiMap;
	}
	public String getOrderDateAsString(){
		String orderDateStr = "";
		if(orderDate != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(orderDate);
		}
		return orderDateStr;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getSalesId() {
		return salesId;
	}
	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}
	public List<LineItemVO> getLineItemLs() {
		return lineItemLs;
	}
	public void setLineItemLs(List<LineItemVO> lineItemLs) {
		this.lineItemLs = lineItemLs;
	}
	public List<InvoiceVO> getInvoiceLs() {
		return invoiceLs;
	}
	public void setInvoiceLs(List<InvoiceVO> invoiceLs) {
		this.invoiceLs = invoiceLs;
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
	
	
	public Object[][] getInvoiceRows(){
		if(invoiceLs == null || invoiceLs.isEmpty()){
			return null;
		}
		
		Object[][] rows = new Object[invoiceLs.size()][4];
		for(int index = 0; index < invoiceLs.size(); index++){
			InvoiceVO invoiceVO = invoiceLs.get(index);
			
			rows[index][0] = invoiceVO.getId();
			rows[index][1] = getDateAsString(invoiceVO.getInvoiceDate());
			rows[index][2] = String.valueOf(invoiceVO.getInvoiceAmount());
			rows[index][3] = ""+invoiceVO.getInvoiceStatus();
		}
		
		return rows;
	}
	
	public String getDateAsString(Date date){
		String orderDateStr = "";
		if(date != null){
			orderDateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
		}
		return orderDateStr;
	}
			
}
