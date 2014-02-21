package com.litetech.omt.dao.model.org;

import java.io.Serializable;

public class OrgReportMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String reportKey;
	private String reportValue;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getReportKey() {
		return reportKey;
	}
	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}
	public String getReportValue() {
		return reportValue;
	}
	public void setReportValue(String reportValue) {
		this.reportValue = reportValue;
	}
	
	

}
