package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.dao.mapper.InvoiceChargeVODOMapper;
import com.litetech.omt.dao.model.core.Charge;
import com.litetech.omt.dao.model.core.InvoiceCharge;
import com.litetech.omt.vo.ChargeVO;
import com.litetech.omt.vo.InvoiceChargeVO;

public class InternalInvoiceChargeDAO {
	private SessionFactory sessionFactory;
	private InvoiceChargeVODOMapper invChargeVODOMapper;
	private InternalChargeDAO internalChargeDAO;
	
	public InternalInvoiceChargeDAO(SessionFactory sessionFactory,
			InvoiceChargeVODOMapper invChargeVODOMapper,
			InternalChargeDAO internalChargeDAO){
		this.sessionFactory = sessionFactory;
		this.invChargeVODOMapper = invChargeVODOMapper;
		this.internalChargeDAO = internalChargeDAO;
	}
	
	
	public List<InvoiceChargeVO> retrieveInvoiceCharges(final long invoiceId, OMTSwitchEnum purchase){
		List<InvoiceCharge> invocieChargeDOs = retrieveInvoiceCharge(invoiceId, purchase);
		List<InvoiceChargeVO> chargeVOs = new ArrayList<InvoiceChargeVO>();
		for(InvoiceCharge invoiceChargeDO : invocieChargeDOs){
			InvoiceChargeVO invoiceChargeVO = invChargeVODOMapper.mapInvoiceChargeDOToVO(invoiceChargeDO);
			invoiceChargeVO.setCharge(internalChargeDAO.retrieveCharge(invoiceChargeDO.getChargeId()));
			chargeVOs.add(invoiceChargeVO);
		}
		
		return chargeVOs;
	}
	
	public void persistInvoiceCharges(final long invoiceId, List<InvoiceChargeVO> invoiceChargeVOs,
			OMTSwitchEnum purchase){
		
		for(InvoiceChargeVO invoiceChargeVO :  invoiceChargeVOs){
			invoiceChargeVO.setInvoiceId(invoiceId);
			invoiceChargeVO.setPurchase(purchase);
			
			InvoiceCharge invoiceChargeDO = invChargeVODOMapper.mapInvoiceChargeVOToDO(invoiceChargeVO);
			sessionFactory.getCurrentSession().saveOrUpdate(invoiceChargeDO);
			
			invoiceChargeVO.setId(invoiceChargeDO.getId());
		}
		
		List<InvoiceCharge> existingInvoiceChargeDOs = retrieveInvoiceCharge(invoiceId, purchase);
		for(InvoiceCharge invoiceCharge : existingInvoiceChargeDOs){
			boolean isAvailable = false;
			for(InvoiceChargeVO invoiceChargeVO :  invoiceChargeVOs){
				if(invoiceCharge.getId() == invoiceChargeVO.getId()){
					//charge still valid
					isAvailable = true;
					break;
				}
			}
			
			if(!isAvailable){
				sessionFactory.getCurrentSession().delete(invoiceCharge);
			}
		}
	}
	
	private List<InvoiceCharge> retrieveInvoiceCharge(final long invoiceId, OMTSwitchEnum purchase){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("findChargesByInvoiceId");
		query.setLong("invoiceId", invoiceId);
		query.setInteger("isPurchase", purchase.getId());
		return query.list();
	}
	
	

}
