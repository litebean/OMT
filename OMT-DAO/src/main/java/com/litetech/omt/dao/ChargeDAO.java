package com.litetech.omt.dao;

import java.util.List;

import com.litetech.omt.vo.ChargeVO;

public interface ChargeDAO {
	public List<ChargeVO> retrieveAllCharges();
	public void saveCharge(final ChargeVO chargeVO);
	public void deleteCharge(final long chargeId);
	
}
