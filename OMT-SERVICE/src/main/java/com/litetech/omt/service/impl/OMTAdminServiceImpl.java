package com.litetech.omt.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Map;

import com.litetech.omt.constant.SettingEnum;
import com.litetech.omt.dao.SettingDAO;
import com.litetech.omt.service.OMTAdminService;
import com.litetech.omt.util.DateUtil;
import com.litetech.omt.vo.SettingVO;

public class OMTAdminServiceImpl implements OMTAdminService{

	private SettingDAO settingDAO;
	public OMTAdminServiceImpl(SettingDAO settingDAO){
		this.settingDAO = settingDAO;
	}
	
	@Override
	public String dumpDB(boolean force) throws IOException {
		SettingVO settingVO = settingDAO.fetchSetting(SettingEnum.PG_DUMP_EXE.name());
		if(settingVO != null){
			String pgDumpExe = settingVO.getValue();
			settingVO = settingDAO.fetchSetting(SettingEnum.PG_DUMP_DB_NAME.name());
			String dbName = settingVO == null ? null : settingVO.getValue();
			
			settingVO = settingDAO.fetchSetting(SettingEnum.PG_DUMP_USER.name());
			String userName = settingVO == null ? null : settingVO.getValue();
			
			settingVO = settingDAO.fetchSetting(SettingEnum.PG_DUMP_PASSWORD.name());
			String pwd = settingVO == null ? null : settingVO.getValue();
				
			settingVO = settingDAO.fetchSetting(SettingEnum.PG_DUMP_LOCATION.name());
			String location = settingVO == null ? null : settingVO.getValue();
				
			return callPGDumpProcess(pgDumpExe, dbName, userName, pwd, location);
			
		}else{
			throw new IOException("DB Dump Exe not configured");
		}
	}
	
	
	private String callPGDumpProcess(String exe,
			String dbName, String userName, String password, String location) throws IOException{
		String currentDate = DateUtil.getDateAsString(Calendar.getInstance().getTime());
		location += File.separator+dbName+"_dump_"+currentDate+".sql";
		
		File file = new File(location);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		
		ProcessBuilder pb = new ProcessBuilder(exe,
				"-U", userName, "-f", location, dbName);
		final Map<String, String> env = pb.environment();
		env.put("PGPASSWORD", password);
	    
	    final Process process = pb.start();
	    final BufferedReader r = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));
	    String line = r.readLine();
	    while (line != null) {
	    	System.err.println(line);
	    	throw new IOException(line);
	    }
	    
		return location;
	}

}
