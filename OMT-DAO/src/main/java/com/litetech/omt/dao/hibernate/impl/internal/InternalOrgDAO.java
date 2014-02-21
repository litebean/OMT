package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import com.litetech.omt.dao.model.org.Org;
import com.litetech.omt.dao.model.org.OrgReportMetaData;
import com.litetech.omt.vo.OrgVO;

public class InternalOrgDAO {

	private SessionFactory sessionFactory;
	
	
	public InternalOrgDAO(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	public OrgVO retrieveOrg(){
		Session session = sessionFactory.getCurrentSession();
		Org orgDO = (Org)session.load(Org.class, 1l);
		OrgVO orgVO = mapDOToVO(orgDO);
		populateReportMetaData(orgVO);
		return orgVO;
	}
	
	
	private void populateReportMetaData(OrgVO orgVO){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("fetchAllReportMetaData");
		List<OrgReportMetaData> reportMetaDataDO = query.list();
		populateMetaDataToVO(orgVO, reportMetaDataDO);
	}
	
	private OrgVO mapDOToVO(Org orgDO){
		OrgVO orgVO = new OrgVO();
		orgVO.setId(orgDO.getId());
		orgVO.setName(orgDO.getOrgName());
		orgVO.setSsiNo(orgDO.getSsiNo());
		orgVO.setTinNo(orgDO.getTinNo());
		orgVO.setCstNo(orgDO.getCstNo());
		orgVO.setAreaCode(orgDO.getAreaCode());		
		return orgVO;
	}
	
	
	private void populateMetaDataToVO(OrgVO orgVO, 
			List<OrgReportMetaData> reportMetaDataDOs){
		
		for(int index = 0; index < reportMetaDataDOs.size();
				index++){
			OrgReportMetaData orgReportMetaDataDO = reportMetaDataDOs.get(index);
			switch(index){
				case 0:
					orgVO.setOrgHeader1(orgReportMetaDataDO.getReportValue());
					break;
				case 1:
					orgVO.setOrgHeader2(orgReportMetaDataDO.getReportValue());	
					break;
				case 2:
					orgVO.setOrgHeader3(orgReportMetaDataDO.getReportValue());
					break;
				case 3:
					orgVO.setOrgHeader4(orgReportMetaDataDO.getReportValue());
					break;
				
			}
		}
	}
	
}

