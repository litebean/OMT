package com.litetech.omt.client;

import java.io.File;

import com.litetech.omt.license.ILicense;
import com.litetech.omt.license.LicenseManager;
import com.litetech.omt.license.MyLicense;
import com.litetech.omt.license.exception.LicenseExpiredException;

public class LicenseClient {
	
	private static String OMT_LICENSE_FOLDER = "omt-license";
	private static String OMT_PUB_KEY_FILE = "omtkey.der";
	private static String OMT_LICENSE_FILE = "license";
	private static String publicKey = null;
	private static File licenseFile = null;
	
	
	
	static{
		String userDir = System.getProperty("user.dir");
		File omtJarFold = new File(userDir, OMT_LICENSE_FOLDER);
		initPubLicenseKeys(omtJarFold);
		
		
		if(publicKey == null || licenseFile == null){
			String userProfileLoc = System.getenv("USERPROFILE");
			File omtUserProfileFold = new File(userProfileLoc, OMT_LICENSE_FOLDER);
			initPubLicenseKeys(omtUserProfileFold);			
		}		
	}
	
	
	private static void initPubLicenseKeys(File omtLicenseFold){
		if(omtLicenseFold.exists() && omtLicenseFold.isDirectory()){
			if(publicKey == null){
				File userPbFile = new File(omtLicenseFold, OMT_PUB_KEY_FILE);
				if(userPbFile.exists() && userPbFile.isFile()){
					publicKey = userPbFile.getAbsolutePath();
				}
			}
			
			if(licenseFile == null){
				File userLicenseFile = new File(omtLicenseFold, OMT_LICENSE_FILE);
				if(userLicenseFile.exists() && userLicenseFile.isFile()){
					licenseFile = userLicenseFile;
				}
			}
		}
	}
	
	public static MyLicense validateLicense() throws Exception{
		if(publicKey == null || licenseFile == null){
			throw new Exception(" License Not Found");
		}	
		MyLicense license = null;
		try{
			LicenseManager licenseManager = new LicenseManager(publicKey, null);
			license = (MyLicense)licenseManager.readLicenseFile(licenseFile);
			license.validate();
		}catch(LicenseExpiredException e){
			throw new Exception(" License Expired please contact administrator ");
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(" License Problem please contact administrator");
		}
		
		return license;
	}
	
}
