package com.litetech.omt;

import java.io.File;

public class ClasspathTest {

	
	public static void main(String[] args) {
		File file = new File("F:/product/OrderManagementTool/maven-src/omt/OMT-UI/target/test/lib");
		File[] files = file.listFiles();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SET CLASSPATH=.;");
		for(File fileName : files){
			stringBuilder.append(fileName.getAbsolutePath());
			stringBuilder.append(";");
		}
		System.out.println(stringBuilder.toString());
	}
}
