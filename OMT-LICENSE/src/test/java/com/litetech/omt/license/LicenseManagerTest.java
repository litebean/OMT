package com.litetech.omt.license;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.litetech.omt.license.AbstractLicense;
import com.litetech.omt.license.LicenseManager;

public class LicenseManagerTest {

	public static void main1(String[] args) {
		System.out.println(" system env properties "+System.getenv());
		System.out.println(" user profile "+System.getenv("USERPROFILE"));
		
		System.out.println(" user.dir system properties "+System.getProperty("user.dir"));
	}
	
	public static void main(String[] args) throws Exception{
		LicenseManager licenseManager = new LicenseManager(
				"F:/product/OrderManagementTool/license/public-key/omtkey.der", "F:/product/OrderManagementTool/license/privkey.der");
				//"F:/research/new-openssl/pubkey.der", null);
		
		MyLicense license = new MyLicense();
		Calendar expireTime = Calendar.getInstance();
		expireTime.set(2014, 11, 23);
		license.setExpiration(expireTime.getTime());
		
		license.setEmail("ajuholyhans@gmail.com");
		license.setName("Aju Bhaskaran");
		licenseManager.writeLicense(license, new File("F:/product/OrderManagementTool/license/license"));
		AbstractLicense iLicense = (AbstractLicense)licenseManager.readLicenseFile(new File("F:/product/OrderManagementTool/license/license"));
		System.out.println("iLicense "+iLicense.getEmail());
		System.out.println("Expiration "+iLicense.getExpiration());
		iLicense.validateExpiration(new Date());
	}
}
