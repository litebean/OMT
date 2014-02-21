package com.litetech.omt.service;

import java.io.IOException;

public interface OMTAdminService {
	public String dumpDB(boolean force) throws IOException;
}
