package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.litetech.omt.constant.InvoiceStatusEnum;
import com.litetech.omt.dao.mapper.InvoiceVODOMapper;
import com.litetech.omt.dao.model.core.Invoice;
import com.litetech.omt.dao.model.user.Customer;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.SearchInvoiceRequestVO;

public class InternalInvoiceDAO {
	
	private SessionFactory sessionFactory;
	private InvoiceVODOMapper invoiceVODOMapper;
	
	public InternalInvoiceDAO(SessionFactory sessionFactory, 
			InvoiceVODOMapper invoiceVODOMapper){
		this.sessionFactory = sessionFactory;
		this.invoiceVODOMapper = invoiceVODOMapper;
	}
	
	public List<InvoiceVO> retrieveInvoices(final long orderId){
		List<Invoice> invoiceDOs = retrieveInvoiceDOs(orderId);
		List<InvoiceVO> invoiceVOs = new ArrayList<InvoiceVO>();
		for(Invoice invoiceDO : invoiceDOs){
			invoiceVOs.add(invoiceVODOMapper.mapDOToVO(invoiceDO));
		}
		return invoiceVOs;
	}
	
	private List<Invoice> retrieveInvoiceDOs(final long orderId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findByOrderId");
		query.setLong("orderId", orderId);
		
		return query.list();
	}
	
	
	public List<InvoiceVO> retrieveInvoices(final long customerId, 
			List<Integer> invoiceStatuses){
		List<Invoice> invoiceDOs = retrieveInvoiceDOs(customerId, invoiceStatuses);
		List<InvoiceVO> invoiceVOs = new ArrayList<InvoiceVO>();
		for(Invoice invoiceDO : invoiceDOs){
			invoiceVOs.add(invoiceVODOMapper.mapDOToVO(invoiceDO));
		}
		return invoiceVOs;
	}
	
	public List<InvoiceVO> retrieveInvoices(final long customerId, 
			List<Integer> invoiceStatuses, List<Long> excludeInvoiceIds){
		List<Invoice> invoiceDOs = retrieveInvoiceDOs(customerId, invoiceStatuses, excludeInvoiceIds);
		List<InvoiceVO> invoiceVOs = new ArrayList<InvoiceVO>();
		for(Invoice invoiceDO : invoiceDOs){
			invoiceVOs.add(invoiceVODOMapper.mapDOToVO(invoiceDO));
		}
		return invoiceVOs;
	}
	
	private List<Invoice> retrieveInvoiceDOs(final long customerId,
			List<Integer> invoiceStatuses){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findByCustomerInvStatuses");
		query.setParameterList("invoiceStatuses", invoiceStatuses);
		query.setParameter("customerId", customerId);
		return query.list();		
	}
	
	private List<Invoice> retrieveInvoiceDOs(final long customerId,
			List<Integer> invoiceStatuses, List<Long> excludeInvoiceIds){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findByCustomerInvStatusesExclInv");
		query.setParameterList("invoiceStatuses", invoiceStatuses);
		query.setParameterList("ids", excludeInvoiceIds);
		query.setParameter("customerId", customerId);
		return query.list();		
	}
	
	public void persistInvoice(final InvoiceVO invoiceVO){
		Session session = sessionFactory.getCurrentSession();
		Invoice invoiceDO = null;
		if(invoiceVO.getId() > 0){
			invoiceDO = (Invoice)session.load(Invoice.class, invoiceVO.getId());
			invoiceVODOMapper.populateSaveInvoiceDO(invoiceDO, invoiceVO);
		}else{
			invoiceDO = invoiceVODOMapper.mapVOToDO(invoiceVO);
		}
		session.saveOrUpdate(invoiceDO);
		invoiceVO.setId(invoiceDO.getId());
	}
	
	public InvoiceVO retrieveInvoice(final long invoiceId){
		Session session = sessionFactory.getCurrentSession();
		Invoice invoiceDO = (Invoice)session.load(Invoice.class, invoiceId);
		return invoiceVODOMapper.mapDOToVO(invoiceDO);
	}
	
	
	public List<InvoiceVO> searchInvoices(SearchInvoiceRequestVO searchInvoiceRequestVO){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Invoice.class);
						
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
		
		List<Invoice> invoiceDOs = criteria.list();
		
		List<InvoiceVO> invoiceVOs = new ArrayList<InvoiceVO>();
		for(Invoice invoiceDO :  invoiceDOs){
			InvoiceVO invoiceVO = invoiceVODOMapper.mapDOToVO(invoiceDO);
			invoiceVOs.add(invoiceVO);
		}
		
		return invoiceVOs;
	}
}
