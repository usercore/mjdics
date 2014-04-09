package com.magic.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigInfoProperties {	
	static Properties prop = null ;  
	static Properties statistics = new Properties();
	static {
	    prop = new Properties();
		try {
			prop.load(ConfigInfoProperties.class.getResourceAsStream("/configInfo.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("读取属性文件失败");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("装载文件--->失败!");
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	public static String getSystemParam(String str) {
		return prop.getProperty(str);
	}	


}
