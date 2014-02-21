package com.litetech.omt.license;

import java.util.Date;

import com.litetech.omt.license.exception.LicenseExpiredException;

public class MyLicense extends AbstractLicense{

	
	public void validate()
			throws LicenseExpiredException {
		super.validateExpiration(new Date());
	}
}
