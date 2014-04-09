package com.magic.lottery.ws;



import net.sf.json.JSONObject;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.magic.promotion.util.AESKit;
import com.magic.promotion.util.StringUtil;


public class TestSelfServiceWebService {
	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", "C:\\TestKeyStore");// C:\\server.jks

		System.setProperty("javax.net.ssl.trustStorePassword", "jcmt2013");

		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress("https://busi.yipaibaiwan.com/busi/ws/ss");
		// factoryBean.setAddress("http://busi.yipaibaiwan.com:8000/busi/ws/ss");

		factoryBean.setServiceClass(ISelfServiceWSBo.class);

		ISelfServiceWSBo ws = (ISelfServiceWSBo) factoryBean.create();

		String channelCode = "500175510008";

		String initSecret = "test1234";
		String mobile = "13944369945";

		boolean useXML = false;

		String userID = "9999999";
		String orderID = "";//StringUtil.getGUID();

		try {
			String sign = AESKit.encrypt(channelCode, initSecret);
			System.out.println("sign:" + sign);
			String result = ws.getSecuretKeyByID(channelCode, sign);
			System.out.println("result:" + result);
			String key = null;
			if (useXML) {
				SAXReader reader = new SAXReader();
				Document doc = DocumentHelper.parseText(result);
				Element root = doc.getRootElement();// 获取根元素
				Element keyElement = root.element("key");
				key = keyElement.getText().trim();
			} else {
				JSONObject json = JSONObject.fromObject(result);
				key = json.getString("key");
			}
			key = AESKit.decrypt(key, initSecret);
			System.out.println("key:" + key);
			String issue = "2013085";
			int type = 0;
			int page = 1;
			int pageSize = 10;

			int amount = 20;
			int num = 2;

			String body = null;
			String commSign = null;

			// System.out.println("------------------------------------");
			// int totalSellAmount = 40;
			// int fromSeqNo = 9468;
			// int toSeqNo = 9469;
			// body = totalSellAmount + channelCode + fromSeqNo + toSeqNo;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("sellChecking body:" + body + ",commSign:" +
			// commSign);
			// String sellChecking = ws.sellChecking(channelCode, fromSeqNo,
			// toSeqNo, totalSellAmount, commSign);
			// System.out.println("sellChecking result:" + sellChecking);
			//

			// System.out.println("------------------------------------");
			// body = issue + channelCode;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("body:" + body + ",commSign:" + commSign);
			// String getAwardInfo = ws.getAwardInfo(channelCode, issue,
			// commSign);
			// System.out.println("getAwardInfo:" + getAwardInfo);

			// System.out.println("------------------------------------");
			// body = userID + channelCode + type + page + pageSize;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("body:" + body + ",commSign:" + commSign);
			// String getLotteryListByUserID =
			// ws.getLotteryListByUserID(channelCode, userID, type, page,
			// pageSize,
			// commSign);
			// System.out.println("getLotteryListByUserID:" +
			// getLotteryListByUserID);

			// System.out.println("------------------------------------");
			// body = issue + channelCode + page + pageSize;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("body:" + body + ",commSign:" + commSign);
			// String getLotteryResult = ws.getLotteryResult(channelCode, issue,
			// page, pageSize, commSign);
			// System.out.println("getLotteryResult:" + getLotteryResult);
			//

			// System.out.println("------------------------------------");
			// body = userID + channelCode + newMobile;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("body:" + body + ",commSign:" + commSign);
			// String modifyMobile = ws.updateMobile(channelCode, userID,
			// newMobile, commSign);
			// System.out.println("modifyMobile:" + modifyMobile);

			// System.out.println("------------------------------------");
			// String cardID = "410782198007211579";
			// body = userID + channelCode + cardID;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("body:" + body + ",commSign:" + commSign);
			// String updateUserCardID = ws.updateUserCardID(channelCode,
			// userID, cardID, commSign);
			// System.out.println("updateUserCardID:" + updateUserCardID);
			//
			// System.out.println("------------------------------------");
			// body = userID + channelCode;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("body:" + body + ",commSign:" + commSign);
			// String getAccountInfo = ws.getAccountInfo(channelCode, userID,
			// commSign);
			// System.out.println("getAccountInfo:" + getAccountInfo);
			//
			// System.out.println("------------------------------------");
			// String encashOrderID = StringUtil.getGUID();
			// int encashAmount = 100;
			// body = userID + channelCode + encashOrderID + encashAmount;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("body:" + body + ",commSign:" + commSign);
			// String encash = ws.encash(channelCode, userID, encashOrderID,
			// encashAmount, commSign);
			// System.out.println("encash:" + encash);

			//
			// System.out.println("------------------------------------");
			// int dayLimit = 1 * 100;
			// body = dayLimit + channelCode;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("body:" + body + ",commSign:" + commSign);
			// String setParams = ws.setParams(channelCode, dayLimit, commSign);
			// System.out.println("setParams:" + setParams);
			//
			// System.out.println("------------------------------------");
			// body = channelCode;
			// commSign = AESKit.encrypt(body, key);
			// System.out.println("body:" + body + ",commSign:" + commSign);
			// String getParams = ws.getParams(channelCode, commSign);
			// System.out.println("getParams:" + getParams);

			// System.out.println("------------------------------------");
			// body = userID + channelCode + orderID + amount + num;
			// System.out.println("body:" + body + ",sKey:" + key);
			// commSign = AESKit.encrypt(body, key);
			// String getLottery = ws.getLottery(channelCode, userID, amount,
			// num, orderID, commSign);
			// System.out.println("getLottery:" + getLottery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void testService(){
		System.setProperty("javax.net.ssl.trustStore", "C:\\TestKeyStore");// C:\\server.jks

		System.setProperty("javax.net.ssl.trustStorePassword", "jcmt2013");

		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress("https://busi.yipaibaiwan.com/busi/ws/ss");
		// factoryBean.setAddress("http://busi.yipaibaiwan.com:8000/busi/ws/ss");

		factoryBean.setServiceClass(ISelfServiceWSBo.class);

		ISelfServiceWSBo ws = (ISelfServiceWSBo) factoryBean.create();

		String channelCode = "500175510008";

		String initSecret = "test1234";
		String mobile = "13944369945";

		boolean useXML = false;

		String userID = "9999999";
		String orderID = "";//StringUtil.getGUID();

		try {
			String sign = AESKit.encrypt(channelCode, initSecret);
			System.out.println("sign:" + sign);
			String result = ws.getSecuretKeyByID(channelCode, sign);
			System.out.println("result:" + result);
			String key = null;
			if (useXML) {
				SAXReader reader = new SAXReader();
				Document doc = DocumentHelper.parseText(result);
				Element root = doc.getRootElement();// 获取根元素
				Element keyElement = root.element("key");
				key = keyElement.getText().trim();
			} else {
				JSONObject json = JSONObject.fromObject(result);
				key = json.getString("key");
			}
			key = AESKit.decrypt(key, initSecret);
			System.out.println("key:" + key);
			String issue = "2013085";
			int type = 0;
			int page = 1;
			int pageSize = 10;

			int amount = 20;
			int num = 2;

			String body = null;
			String commSign = null;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
