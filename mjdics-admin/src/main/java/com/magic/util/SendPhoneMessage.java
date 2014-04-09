package com.magic.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SendPhoneMessage {
	
	public static String sendMessage(String phone,String msg) throws Exception{
		String url = ConfigInfoProperties.getSystemParam("phoneMessageUrl");
		URL u = null;

		HttpURLConnection con = null;

		//构建请求参数

		StringBuffer sb = new StringBuffer();
		sb.append("action=sendmsg&phone="+phone);
		sb.append("&msg="+msg);

		//尝试发送请求

		try {

		u = new URL(url);

		con = (HttpURLConnection) u.openConnection();

		con.setRequestMethod("POST");

		con.setDoOutput(true);

		con.setDoInput(true);

		con.setUseCaches(false);

		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");

		osw.write(sb.toString());

		osw.flush();

		osw.close();

		} finally {

		if (con != null) {

		con.disconnect();

		}

		}

		//读取返回内容

		StringBuffer buffer = new StringBuffer();


		BufferedReader br = new BufferedReader(new InputStreamReader(con

		.getInputStream(), "UTF-8"));

		String temp;

		while ((temp = br.readLine()) != null) {

		buffer.append(temp);

		buffer.append("\n");

		}


		 

		return buffer.toString();

		}

		 

}
