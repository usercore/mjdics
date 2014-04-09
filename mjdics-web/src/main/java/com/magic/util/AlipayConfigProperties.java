package com.magic.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AlipayConfigProperties {

	public static String notifyURL = "";
	public static String returnURL = "";
	
	public static String subject = "";
	public static String body = "";
	
	public static String showURL= "";
	// 页面编码
	public static String CharSet = "utf-8";
	
	
	
	static {
		// 取得服务连接路径
		Properties prop = new Properties();
		try {
			prop.load(AlipayConfigProperties.class.getResourceAsStream("/alipayConfig.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("读取属性文件alipay.properties失败");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("装载文件--->失败!");
			e.printStackTrace();
		}

		notifyURL = prop.getProperty("alipayNotifyURL");
		returnURL = prop.getProperty("alipayReturnURL");
		
		subject = prop.getProperty("subject");
		body = prop.getProperty("body");
		System.out.println(subject+"====="+body);
		showURL = prop.getProperty("showURL");
	}

	
}
