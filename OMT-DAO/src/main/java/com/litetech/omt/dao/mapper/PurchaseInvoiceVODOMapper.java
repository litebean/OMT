package com.litetech.omt.dao.mapper;

import com.litetech.omt.dao.model.core.PurchaseInvoice;
import com.litetech.omt.vo.InvoiceVO;

public class PurchaseInvoiceVODOMapper {

	public InvoiceVO mapDOToVO(PurchaseInvoice invoiceDO){
		InvoiceVO invoiceVO = new InvoiceVO(true);
				
		invoiceVO.setId(invoiceDO.getId());
		invoiceVO.setOrderId(invoiceDO.getOrderId());
		invoiceVO.setCustomerId(invoiceDO.getCustomerId());
		invoiceVO.setInvoiceDate(invoiceDO.getInvoiceDate());
		invoiceVO.setInvoiceStatus(invoiceDO.getInvoiceStatus());
		invoiceVO.setDcNo(invoiceDO.getDcNo());
		invoiceVO.setInvDesc(invoiceDO.getInvDesc());
		invoiceVO.setTaxId(invoiceDO.getTaxId());
		invoiceVO.setInvoiceAmount(invoiceDO.getInvoiceAmount());
		invoiceVO.setInvoiceAmountExclCharges(invoiceDO.getInvoiceAmountExclCharges());
		invoiceVO.setBillToAdd(invoiceDO.getBillToAdd());
		invoiceVO.setShipToAdd(invoiceDO.getShipToAdd());
		invoiceVO.setPaidAmount(invoiceDO.getPaidAmount());
		invoiceVO.setCreationDate(invoiceDO.getCreationDate());
		invoiceVO.setLastModifiedDate(invoiceDO.getLastModifiedDate());
		return invoiceVO;
	}
	
	public PurchaseInvoice mapVOToDO(InvoiceVO invoiceVO){
		PurchaseInvoice invoiceDO = new PurchaseInvoice();
		invoiceDO.setId(invoiceVO.getId());
		invoiceDO.setOrderId(invoiceVO.getOrderId());
		
		invoiceDO.setInvoiceDate(invoiceVO.getInvoiceDate());
		invoiceDO.setInvoiceStatus(invoiceVO.getInvoiceStatus());
		invoiceDO.setDcNo(invoiceVO.getDcNo());
		invoiceDO.setInvDesc(invoiceVO.getInvDesc());
		invoiceDO.setTaxId(invoiceVO.getTaxId());
		invoiceDO.setInvoiceAmount(invoiceVO.getInvoiceAmount());
		invoiceDO.setInvoiceAmountExclCharges(invoiceVO.getInvoiceAmountExclCharges());
		invoiceDO.setCustomerId(invoiceVO.getCustomerId());
		invoiceDO.setBillToAdd(invoiceVO.getBillToAdd());
		invoiceDO.setShipToAdd(invoiceVO.getShipToAdd());
		invoiceDO.setPaidAmount(invoiceVO.getPaidAmount());
		invoiceDO.setCreationDate(invoiceVO.getCreationDate());
		invoiceDO.setLastModifiedDate(invoiceVO.getLastModifiedDate());
		
		return invoiceDO;
	}
	
	
	public PurchaseInvoice populateSaveInvoiceDO(PurchaseInvoice invoiceDO, InvoiceVO invoiceVO){
		invoiceDO.setOrderId(invoiceVO.getOrderId());
		invoiceDO.setCustomerId(invoiceVO.getCustomerId());
		invoiceDO.setInvoiceDate(invoiceVO.getInvoiceDate());
		invoiceDO.setInvoiceStatus(invoiceVO.getInvoiceStatus());
		invoiceDO.setDcNo(invoiceVO.getDcNo());
		invoiceDO.setInvDesc(invoiceVO.getInvDesc());
		invoiceDO.setTaxId(invoiceVO.getTaxId());
		invoiceDO.setInvoiceAmount(invoiceVO.getInvoiceAmount());
		invoiceDO.setInvoiceAmountExclCharges(invoiceVO.getInvoiceAmountExclCharges());
		invoiceDO.setBillToAdd(invoiceVO.getBillToAdd());
		invoiceDO.setShipToAdd(invoiceVO.getShipToAdd());
		//invoiceDO.setPaidAmount(invoiceVO.getPaidAmount());
		//invoiceDO.setCreationDate(invoiceVO.getCreationDate());
		invoiceDO.setLastModifiedDate(invoiceVO.getLastModifiedDate());
		
		return invoiceDO;
	}
}
