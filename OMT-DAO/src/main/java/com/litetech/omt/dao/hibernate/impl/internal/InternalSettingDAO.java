package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.litetech.omt.dao.model.setting.Setting;
import com.litetech.omt.vo.SettingVO;

public class InternalSettingDAO {

	private SessionFactory sessionFactory;
	public InternalSettingDAO(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	public SettingVO fetchSetting(String key){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchSetting");
		query.setParameter("key", key);
		List<Setting> settingDOs = query.list();
		if(settingDOs != null && !settingDOs.isEmpty()){
			return mapToVO(settingDOs.get(0));
		}
		return null;
	}
	
	private SettingVO mapToVO(Setting settingDO){
		SettingVO settingVO = new SettingVO();
		settingVO.setSettingId(settingDO.getId());
		settingVO.setKey(settingDO.getKey());
		settingVO.setValue(settingDO.getValue());
		settingVO.setDesc(settingDO.getDesc());
		settingVO.setCreationDate(settingDO.getCreationDate());
		settingVO.setLastModifiedDate(settingVO.getLastModifiedDate());
		
		return settingVO;
	}
}
