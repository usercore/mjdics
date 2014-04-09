package com.magic.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AlipayProperties {

	//支付接口
	public static String payGateway = "";
	
	// partner和key提取方法：登陆签约支付宝账户--->点击“商家服务”就可以看到
	public static String partnerID = "";
	public static String key = "";
	// 卖家支付宝帐户,例如：gwl25@126.com
	public static String sellerEmail = "";

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
			prop.load(AlipayProperties.class.getResourceAsStream("/alipay.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("读取属性文件alipay.properties失败");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("装载文件--->失败!");
			e.printStackTrace();
		}
		payGateway = prop.getProperty("payGateway");
		partnerID = prop.getProperty("partnerID");
		key = prop.getProperty("key");
		sellerEmail = prop.getProperty("sellerEmail");

		notifyURL = prop.getProperty("alipayNotifyURL");
		returnURL = prop.getProperty("alipayReturnURL");
		
		subject = prop.getProperty("subject");
		body = prop.getProperty("body");
		System.out.println(subject+"====="+body);
		showURL = prop.getProperty("showURL");
	}

	
}
