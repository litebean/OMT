package com.litetech.omt.dao.hibernate.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.litetech.omt.dao.SettingDAO;
import com.litetech.omt.dao.hibernate.impl.internal.InternalSettingDAO;
import com.litetech.omt.vo.SettingVO;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class SettingDAOImpl implements SettingDAO{

	private InternalSettingDAO internalSettingDAO;
	
	public SettingDAOImpl(InternalSettingDAO internalSettingDAO){
		this.internalSettingDAO = internalSettingDAO;
	}
	
	@Override
	public SettingVO fetchSetting(String key) {
		return internalSettingDAO.fetchSetting(key);
	}

}
