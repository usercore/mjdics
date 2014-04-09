package com.magic.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.util.AlipaySubmit;
import com.magic.promotion.sysParam.SysParamUtil;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.util.AESKit;
import com.magic.promotion.util.MD5Util;
import com.magic.util.ConfigInfoProperties;
@Service("aliPay")
public class AliPay {
	@Autowired
	SysParamServiceImpl sysParamService;
	private static String rechargeChannelCode = ConfigInfoProperties.getSystemParam("rechargeChannelCode");
	// 支付类型
	// 必填，不能修改
	// 服务器异步通知页面路径
	// 需http://格式的完整路径，不能加?id=123这类自定义参数

	// 页面跳转同步通知页面路径

	// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

	// 商户订单号
	// 商户网站订单系统中唯一订单号，必填
	// 订单名称
	// 必填
	// 付款金额
	// 必填
	// 订单描述
	// 商品展示地址
	// 需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
	// ////////////////////////////////////////////////////////////////////////////////
	public  String pay(String notify_url,String return_url,String orderId, String subject, String total_fee, String body, String show_url) throws Exception {
		// 把请求参数打包成数组
		String initSecret = ConfigInfoProperties.getSystemParam("initSecret");
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("channel_code", rechargeChannelCode);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("order_id", orderId);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("sign", MD5Util.genMd5String(AESKit.encrypt(rechargeChannelCode+orderId, initSecret)));
		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest("","",sParaTemp);
		return sHtmlText;
		//out.println(sHtmlText);
	}

}
