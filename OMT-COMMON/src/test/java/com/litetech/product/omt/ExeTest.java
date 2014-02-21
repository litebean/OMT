package com.litetech.product.omt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Map;

import com.litetech.omt.util.DateUtil;

public class ExeTest {

	
	private static String callPGDumpProcess(String exe,
			String dbName, String userName, String password, String location) throws IOException{
		String currentDate = DateUtil.getDateAsString(Calendar.getInstance().getTime());
		location += File.separator+"dump_"+currentDate+".sql";
		
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
	
	public static void main1(String[] args) throws IOException {
		ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\PostgreSQL\\9.2\\bin\\pg_dump.exe",
				"-U", "postgres", "-f", "F:\\test.sql", "omtdb");

		final Map<String, String> env = pb.environment();
	    env.put("PGPASSWORD", "postgres");
	    
	    final Process process = pb.start();
		final BufferedReader r = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));
      String line = r.readLine();
      while (line != null) {
          System.err.println(line);
          line = r.readLine();
      }
      r.close();

	}
	
	
	public static void main(String[] args) throws IOException{
		String loc = callPGDumpProcess("C:\\Program Files (x86)\\PostgreSQL\\9.2\\bin\\pg_dump.exe",
				"omtdb", "postgres", "postgres", "F:\\test");
		
		System.out.println("loca "+loc);
	}
}
