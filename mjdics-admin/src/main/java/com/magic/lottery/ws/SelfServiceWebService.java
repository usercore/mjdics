package com.magic.lottery.ws;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import net.sf.json.JSONObject;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.betInfo.domain.BetInfo;
import com.magic.promotion.sysParam.SysParamUtil;
import com.magic.promotion.sysParam.domain.SysParam;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.util.AESKit;
import com.magic.promotion.util.StringUtil;
import com.magic.util.ConfigInfoProperties;

@Service("selfServiceWebService")
public class SelfServiceWebService {
	
	private static final String channelCode = ConfigInfoProperties.getSystemParam("channelCode");
	
	private static String key = null;
	
	private static boolean useXML = Boolean.parseBoolean(ConfigInfoProperties.getSystemParam("useXML"));
	
	static String keyFilePath =  ConfigInfoProperties.getSystemParam("keyFilePath");;
	
	static String trustStorePassword = ConfigInfoProperties.getSystemParam("trustStorePassword");
	
	static String initSecret = ConfigInfoProperties.getSystemParam("initSecret");
	
	String lotteryUrl = ConfigInfoProperties.getSystemParam("lotteryUrl");
	
	@Autowired
	SysParamServiceImpl sysParamService;
	public  void getSecuretKeyByID(){
		
		System.setProperty("javax.net.ssl.trustStore", keyFilePath);// C:\\server.jks

		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress(lotteryUrl);

		factoryBean.setServiceClass(ISelfServiceWSBo.class);

		ISelfServiceWSBo ws = (ISelfServiceWSBo) factoryBean.create();

		try {
			String sign = AESKit.encrypt(channelCode, initSecret);
			System.out.println("sign:" + sign);
			String result = ws.getSecuretKeyByID(channelCode, sign);
			System.out.println("result:" + result);
			if (useXML) {
				Document doc = DocumentHelper.parseText(result);
				Element root = doc.getRootElement();// 获取根元素
				Element keyElement = root.element("key");
				key = keyElement.getText().trim();
			} else {
				JSONObject json = JSONObject.fromObject(result);
				key = json.getString("key");
			}
			key = AESKit.decrypt(key, initSecret);
			/*SysParam sysParam = new SysParam();
			sysParam.setName(SysParamUtil.TRADE_KEY);
			sysParam.setValue(key);
			sysParamService.updateByName(sysParam);*/
			System.out.println("key:" + key);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	/**
	 * 向用户赠送彩票
	 */
	public  BetInfo getLottery(String userID, int amount, int num, String orderID,BetInfo betInfo){
	
		System.setProperty("javax.net.ssl.trustStore", keyFilePath);

		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress(lotteryUrl);

		factoryBean.setServiceClass(ISelfServiceWSBo.class);

		ISelfServiceWSBo ws = (ISelfServiceWSBo) factoryBean.create();

		try {
			String sign = AESKit.encrypt(userID+channelCode+orderID+amount+num, key);
			System.out.println("sign:" + sign);
			String result = ws.getLottery(channelCode, userID, amount, num, orderID, sign);
			
			System.out.println("result:" + result);
			
			JSONObject json = JSONObject.fromObject(result);

			betInfo.setErrorCode(json.getString("errorCode"));
			betInfo.setErrorMsg(json.getString("errorMsg"));
			betInfo.setId(json.getInt("orderID"));
			Object json1[] = json.getJSONArray("datas").toArray();
			for(int i=0;i<1;i++){
				JSONObject lottery = JSONObject.fromObject(json1[i]);
				betInfo.setHotlineId(lottery.getString("flowid"));
				betInfo.setGame(lottery.getString("gameType"));
				betInfo.setManner(lottery.getString("voteType"));
				betInfo.setIssue(lottery.getString("issue"));
				betInfo.setMoney((new BigDecimal(lottery.getInt("amount"))).divide(new BigDecimal(100)));
				betInfo.setMutiple(lottery.getInt("multi"));
				betInfo.setNum(lottery.getString("voteNums").split("\\|")[0]);
				
			}
			
	} catch (Exception e) {
		e.printStackTrace();
	}
		return betInfo;
	}
	/**
	 * 获取中奖记录列表
	 * 返回map
	 * 		map.put("errorCode", json.getString("errorCode"));
			map.put("errorMsg", json.getString("errorMsg"));
			map.put("list", betInfoList);
	 */
	public Map<String,Object> getAwardInfo( String issue){
		Map<String,Object> map = new HashMap<String,Object>();
		List<BetInfo> betInfoList = new ArrayList<BetInfo>();
	
		System.setProperty("javax.net.ssl.trustStore", keyFilePath);

		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress(lotteryUrl);

		factoryBean.setServiceClass(ISelfServiceWSBo.class);

		ISelfServiceWSBo ws = (ISelfServiceWSBo) factoryBean.create();

		try {
			String sign = AESKit.encrypt(issue+channelCode, key);
			String result = ws.getAwardInfo(channelCode, issue, sign);
			
			
			JSONObject json = JSONObject.fromObject(result);
			map.put("errorCode", json.getString("errorCode"));
			map.put("errorMsg", json.getString("errorMsg"));
			
			Object json1[] = json.getJSONArray("datas").toArray();
			for(int i=0;i<json1.length;i++){
				BetInfo betInfo = new BetInfo();
				
			
				
				JSONObject lottery = JSONObject.fromObject(json1[i]);
				betInfo.setUserId(lottery.getString("userID"));
				betInfo.setHotlineId(lottery.getString("flowid"));
				betInfo.setIssue(lottery.getString("issue"));
				betInfo.setPrizeGrade(lottery.getInt("grade"));
				betInfo.setPrize(new BigDecimal(lottery.getString("prize")));
				
				betInfoList.add(betInfo);
				
			}
			map.put("list", betInfoList);
			
	} catch (Exception e) {
		e.printStackTrace();
	}
		return map;
	}
	
	public Map<String,String> getAccountInfo( String userID){
		
		Map<String,String> map = new HashMap<String,String>();
		
		System.setProperty("javax.net.ssl.trustStore", keyFilePath);

		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress(lotteryUrl);

		factoryBean.setServiceClass(ISelfServiceWSBo.class);

		ISelfServiceWSBo ws = (ISelfServiceWSBo) factoryBean.create();

		try {
			String sign = AESKit.encrypt(userID+channelCode,key);
			String result = ws.getAccountInfo(channelCode, userID, sign);
			
			JSONObject json = JSONObject.fromObject(result);
			map.put("errorCode",json.getString("errorCode"));
			map.put("errorMsg",json.getString("errorMsg"));
			map.put("prize",json.getString("prize"));
			map.put("balance",json.getString("balance"));
			
	} catch (Exception e) {
		e.printStackTrace();
	}
		return map;
	}
	public Map<String,String> getCurrentIssue(int gameCode){
		
		Map<String,String> map = new HashMap<String,String>();
		
		System.setProperty("javax.net.ssl.trustStore", keyFilePath);

		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress(lotteryUrl);

		factoryBean.setServiceClass(ISelfServiceWSBo.class);

		ISelfServiceWSBo ws = (ISelfServiceWSBo) factoryBean.create();

		try {
			String sign = AESKit.encrypt(channelCode+""+gameCode,key);
			String result = ws.getCurrentIssue(channelCode, gameCode, sign);
			
			JSONObject json = JSONObject.fromObject(result);
			map.put("errorCode",json.getString("errorCode"));
			map.put("errorMsg",json.getString("errorMsg"));
			
			map.put("issue",json.getString("issue"));
			map.put("beginTime",json.getString("beginTime"));
			map.put("endTime",json.getString("endTime"));
			map.put("status",json.getString("status"));
			map.put("remainsTime",json.getString("remainsTime"));
			map.put("awardTime",json.getString("awardTime"));
			
	} catch (Exception e) {
		e.printStackTrace();
	}
		return map;
	}


	public Map<String,String> getIssueStatus(int gameCode, String issue){
		
		Map<String,String> map = new HashMap<String,String>();
		
		System.setProperty("javax.net.ssl.trustStore", keyFilePath);

		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress(lotteryUrl);

		factoryBean.setServiceClass(ISelfServiceWSBo.class);

		ISelfServiceWSBo ws = (ISelfServiceWSBo) factoryBean.create();

		try {
			String sign = AESKit.encrypt(channelCode+""+gameCode+issue,key);
			String result = ws.getIssueStatus(channelCode, gameCode,issue, sign);
			
			JSONObject json = JSONObject.fromObject(result);
			map.put("errorCode",json.getString("errorCode"));
			map.put("errorMsg",json.getString("errorMsg"));
			
			map.put("issue",json.getString("issue"));
			map.put("beginTime",json.getString("beginTime"));
			map.put("endTime",json.getString("endTime"));
			map.put("status",json.getString("status"));
			map.put("remainsTime",json.getString("remainsTime"));
			map.put("awardTime",json.getString("awardTime"));
			
	} catch (Exception e) {
		e.printStackTrace();
	}
		return map;
	}
	/**
	 * 获取零钱彩票(自选号码)
	 * 
	 * @param id
	 *            渠道ID
	 * @param userID
	 *            被赠用户的ID标识
	 * @param lotteryData
	 *            用户自选彩票号码
	 * @param gameCode
	 *            游戏代码（双色球为201）
	 * @param amount
	 *            赠送金额（单位：分）
	 * @param orderID
	 *            订单号
	 * @param sign
	 *            签名（使用通讯密钥对userID+id+lotteryData+gameCode+orderID+
	 *            amount组成的字串进行AES加密）
	 * @return
	 */
	public BetInfo getLotteryCustom(String userID, String lotteryData,int gameCode, int amount, String orderID,BetInfo betInfo){
	
		System.setProperty("javax.net.ssl.trustStore", keyFilePath);

		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress(lotteryUrl);

		factoryBean.setServiceClass(ISelfServiceWSBo.class);

		ISelfServiceWSBo ws = (ISelfServiceWSBo) factoryBean.create();

		try {System.out.println("key:" + key);
			StringBuffer sb = new StringBuffer();
			sb.append(userID).append(channelCode).append(lotteryData).append(gameCode).append(orderID).append(amount);
			String sign = AESKit.encrypt(sb.toString(), key);
			System.out.println("sign:" + sign);
			String result = ws.getLotteryCustom(channelCode, userID, lotteryData, gameCode, amount, orderID, sign);
			
			System.out.println("result:" + result);
			
			JSONObject json = JSONObject.fromObject(result);

			betInfo.setErrorCode(json.getString("errorCode"));
			betInfo.setErrorMsg(json.getString("errorMsg"));
			betInfo.setId(json.getInt("orderID"));
			Object json1[] = json.getJSONArray("datas").toArray();
			for(int i=0;i<1;i++){
				JSONObject lottery = JSONObject.fromObject(json1[i]);
				betInfo.setHotlineId(lottery.getString("flowid"));
				betInfo.setGame(lottery.getString("gameType"));
				betInfo.setManner(lottery.getString("voteType"));
				betInfo.setIssue(lottery.getString("issue"));
				betInfo.setMoney((new BigDecimal(lottery.getInt("amount"))).divide(new BigDecimal(100)));
				betInfo.setMutiple(lottery.getInt("multi"));
//				betInfo.setNum(lottery.getString("voteNums"));
				betInfo.setNum(lottery.getString("voteNums").split("\\|")[0]);
				
			}
			
	} catch (Exception e) {
		e.printStackTrace();
	}
		return betInfo;
	}
}
