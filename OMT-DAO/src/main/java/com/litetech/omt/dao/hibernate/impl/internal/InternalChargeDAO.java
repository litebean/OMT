package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.litetech.omt.dao.mapper.ChargeVODOMapper;
import com.litetech.omt.dao.model.core.Charge;
import com.litetech.omt.vo.ChargeVO;

public class InternalChargeDAO {
	
	private SessionFactory sessionFactory;
	private ChargeVODOMapper chargeVODOMapper;
	
	
	public InternalChargeDAO(SessionFactory sessionFactory,
			ChargeVODOMapper chargeVODOMapper){
		this.sessionFactory = sessionFactory;
		this.chargeVODOMapper = chargeVODOMapper;
	}
	
	public ChargeVO retrieveCharge(final long chargeId){
		Session session = sessionFactory.getCurrentSession();
		ChargeVO chargeVO = null;
		try{
			Charge chargeDO = (Charge)session.load(Charge.class, chargeId);
			chargeVO = chargeVODOMapper.mapChargeDOTOVO(chargeDO);
			if(chargeDO.getApplyOn() > 1){
				ChargeVO applyOnCharge = retrieveCharge(chargeDO.getApplyOn());
				chargeVO.setApplyOnCharge(applyOnCharge);
			}
		}catch(Exception e){
			chargeVO = new ChargeVO(chargeId, "Invalid");
		}
		return chargeVO;
	}
	
	
	public List<ChargeVO> retreiveAllCharges(){
		List<ChargeVO> chargeVOs = new ArrayList<ChargeVO>();
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.getNamedQuery("findAllAvlChargeIds");
		List<Long> chargeIds = query.list();
		
		for(Long chargeId : chargeIds){
			chargeVOs.add(retrieveCharge(chargeId));
		}
		return chargeVOs;
	}
	
	
	public void saveCharge(ChargeVO chargeVO){
		chargeVO.setLastModifiedDate(new Date());
		Session session = sessionFactory.getCurrentSession();
		
		Charge chargeDO = null;
		if(chargeVO.getId() > 0){
			chargeDO = (Charge)session.load(Charge.class, chargeVO.getId());
			chargeVODOMapper.populateDOValues(chargeVO, chargeDO);
		}else{
			chargeDO = chargeVODOMapper.mapChargeVOToDO(chargeVO);
			chargeDO.setCreationDate(chargeVO.getLastModifiedDate());
		}
		
		session.saveOrUpdate(chargeDO);
		
		//set id
		chargeVO.setId(chargeDO.getId());
	}
	
	
	public void deleteCharge(final long chargeId){
		Session session = sessionFactory.getCurrentSession();
		Charge chargeDO = new Charge();
		chargeDO.setId(chargeId);
		
		session.delete(chargeDO);
	}
}
