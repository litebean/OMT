package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.litetech.omt.dao.mapper.PurchaseInvoiceVODOMapper;
import com.litetech.omt.dao.model.core.Invoice;
import com.litetech.omt.dao.model.core.PurchaseInvoice;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.SearchInvoiceRequestVO;


public class InternalPurchaseInvoiceDAO {
	
	private SessionFactory sessionFactory;
	private PurchaseInvoiceVODOMapper invoiceVODOMapper;
	
	public InternalPurchaseInvoiceDAO(SessionFactory sessionFactory, 
			PurchaseInvoiceVODOMapper invoiceVODOMapper){
		this.sessionFactory = sessionFactory;
		this.invoiceVODOMapper = invoiceVODOMapper;
	}
	
	public void persistPurchaseInvoice(final InvoiceVO invoiceVO){
		Session session = sessionFactory.getCurrentSession();
		PurchaseInvoice invoiceDO = null;
		if(invoiceVO.getId() > 0){
			invoiceDO = (PurchaseInvoice)session.load(PurchaseInvoice.class, invoiceVO.getId());
			invoiceVODOMapper.populateSaveInvoiceDO(invoiceDO, invoiceVO);
		}else{
			invoiceDO = invoiceVODOMapper.mapVOToDO(invoiceVO);
		}
		session.saveOrUpdate(invoiceDO);
		invoiceVO.setId(invoiceDO.getId());
	}
	
	public InvoiceVO retrievePurchaseInvoice(final long invoiceId){
		Session session = sessionFactory.getCurrentSession();
		PurchaseInvoice invoiceDO = (PurchaseInvoice)session.load(PurchaseInvoice.class, invoiceId);
		return invoiceVODOMapper.mapDOToVO(invoiceDO);
	}
	
	
	public List<InvoiceVO> retrievePurchaseInvoices(final long orderId){
		List<PurchaseInvoice> invoiceDOs = retrieveInvoiceDOs(orderId);
		List<InvoiceVO> invoiceVOs = new ArrayList<InvoiceVO>();
		for(PurchaseInvoice invoiceDO : invoiceDOs){
			invoiceVOs.add(invoiceVODOMapper.mapDOToVO(invoiceDO));
		}
		return invoiceVOs;
	}
	
	private List<PurchaseInvoice> retrieveInvoiceDOs(final long orderId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findByPurchaseOrderId");
		query.setLong("orderId", orderId);
		
		return query.list();
	}
	
	
	public List<InvoiceVO> searchPurchaseInvoices(SearchInvoiceRequestVO searchInvoiceRequestVO){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(PurchaseInvoice.class);
		
		if(searchInvoiceRequestVO.getInvoiceId() > 0){
			criteria.add(Restrictions.eq("id", searchInvoiceRequestVO.getInvoiceId()));
		}
		
		if(searchInvoiceRequestVO.getCustomerId() > 0){
			criteria.add(Restrictions.eq("customerId", searchInvoiceRequestVO.getCustomerId()));
		}
		
		if(searchInvoiceRequestVO.getInvoiceDate() != null){
			criteria.add(Restrictions.eq("invoiceDate", searchInvoiceRequestVO.getInvoiceDate()));
		}
		
		if(searchInvoiceRequestVO.getInvoiceStatus() != null){
			criteria.add(Restrictions.eq("invoiceStatus", searchInvoiceRequestVO.getInvoiceStatus().getId()));
		}
		
		List<PurchaseInvoice> invoiceDOs = criteria.list();
		
		List<InvoiceVO> invoiceVOs = new ArrayList<InvoiceVO>();
		for(PurchaseInvoice invoiceDO :  invoiceDOs){
			InvoiceVO invoiceVO = invoiceVODOMapper.mapDOToVO(invoiceDO);
			invoiceVOs.add(invoiceVO);
		}
		
		return invoiceVOs;
	}
	
	
	public List<InvoiceVO> retrievePurchaseInvoices(final long customerId, 
			List<Integer> invoiceStatuses){
		List<PurchaseInvoice> invoiceDOs = retrievePurchaseInvoiceDOs(customerId, invoiceStatuses);
		List<InvoiceVO> invoiceVOs = new ArrayList<InvoiceVO>();
		for(PurchaseInvoice invoiceDO : invoiceDOs){
			invoiceVOs.add(invoiceVODOMapper.mapDOToVO(invoiceDO));
		}
		return invoiceVOs;
	}
	
	public List<InvoiceVO> retrievePurchaseInvoices(final long customerId, 
			List<Integer> invoiceStatuses, List<Long> excludeInvoiceIds){
		List<PurchaseInvoice> invoiceDOs = retrievePurchaseInvoiceDOs(customerId, invoiceStatuses, excludeInvoiceIds);
		List<InvoiceVO> invoiceVOs = new ArrayList<InvoiceVO>();
		for(PurchaseInvoice invoiceDO : invoiceDOs){
			invoiceVOs.add(invoiceVODOMapper.mapDOToVO(invoiceDO));
		}
		return invoiceVOs;
	}
	
	private List<PurchaseInvoice> retrievePurchaseInvoiceDOs(final long customerId,
			List<Integer> invoiceStatuses){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findByCustomerPurInvStatuses");
		query.setParameterList("invoiceStatuses", invoiceStatuses);
		query.setParameter("customerId", customerId);
		return query.list();		
	}
	
	private List<PurchaseInvoice> retrievePurchaseInvoiceDOs(final long customerId,
			List<Integer> invoiceStatuses, List<Long> excludeInvoiceIds){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findByCustomerPurInvStatusesExclInv");
		query.setParameterList("invoiceStatuses", invoiceStatuses);
		query.setParameterList("ids", excludeInvoiceIds);
		query.setParameter("customerId", customerId);
		return query.list();		
	}
}
