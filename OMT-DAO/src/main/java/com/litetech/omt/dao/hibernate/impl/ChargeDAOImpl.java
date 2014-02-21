package com.litetech.omt.dao.hibernate.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.litetech.omt.dao.ChargeDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalChargeDAO;
import com.litetech.omt.vo.ChargeVO;
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ChargeDAOImpl implements ChargeDAO {

	private InternalChargeDAO internalChargeDAO;
	
	public ChargeDAOImpl(InternalChargeDAO internalChargeDAO){
		this.internalChargeDAO = internalChargeDAO;
	}
	
	@Override
	public List<ChargeVO> retrieveAllCharges() {
		return internalChargeDAO.retreiveAllCharges();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void saveCharge(ChargeVO chargeVO) {
		internalChargeDAO.saveCharge(chargeVO);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void deleteCharge(long chargeId) {
		internalChargeDAO.deleteCharge(chargeId);
	}

}
