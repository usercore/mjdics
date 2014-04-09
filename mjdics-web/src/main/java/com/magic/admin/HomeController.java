package com.magic.admin;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.util.AlipayNotify;
import com.alipay.util.httpClient.HttpRequest;
import com.magic.lottery.ws.SelfServiceWebService;
import com.magic.lottery.ws.TestSelfServiceWebService;
import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.agent.service.AgentServiceImpl;
import com.magic.promotion.alipayRecords.domain.AlipayRecords;
import com.magic.promotion.alipayRecords.service.AlipayRecordsServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.util.AESKit;
import com.magic.promotion.util.MD5Util;
import com.magic.promotion.util.enumUtil.AlipayTradeStatusEnum;
import com.magic.util.ConfigInfoProperties;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin")
@Scope("prototype")
public class HomeController{
	String tip ="";
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	AgentServiceImpl agentService;
	@Autowired
	AlipayRecordsServiceImpl alipayRecordsService;
	
	List<Agent> agentList;
	
	@RequestMapping(value = "index")
	public String index(){
		return "manager/login";
	}
	@RequestMapping(value = "login")
	public String login(Agent agent,String valCode,HttpSession session,ModelMap map){
		String validateC = (String) session.getAttribute("validateCode"); 
			// 判断验证码 
			if (validateC==null) {
				map.put("msg", "页面超时！");
				return  "manager/login";
			}
			if (!validateC.equals(valCode.toUpperCase())) {
				map.put("msg", "验证码输入错误！");
				return "manager/login";
			}
		List<Agent> list = agentService.selectByExample(agent, null);
		if(list!=null&&list.size()==1){
			session.setAttribute("agent", list.get(0));
			return "index/index";
		}
		return "manager/login";
	}
	@RequestMapping(value = "top")
	public String top(){
		return "common/header";
	}
	@RequestMapping(value = "reg")
	public String reg(){
		return "manager/reg";
	}
	@RequestMapping(value = "menu")
	public String menu(){
		return "common/menu";
	}
	
	@RequestMapping(value = "gotoSelectagent")
	public String gotoSelectagent(){
		return "index/index";
	}	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model,SessionStatus sessionStatus) {
		logger.info("logout System...");
		sessionStatus.setComplete();
		return "manager/login";
	}
	
	@RequestMapping(value = "aliNotify")
	public void aliNotify(HttpServletResponse response,HttpServletRequest request) throws BusinessException, Exception{
		logger.info("recharge notice");
		String initSecret = ConfigInfoProperties.getSystemParam("initSecret");
		String rechargeChannelCode = ConfigInfoProperties.getSystemParam("rechargeChannelCode");
		
		PrintWriter  out = response.getWriter();
		
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		logger.info("out_trade_no="+out_trade_no);
		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		logger.info("tradeNo="+tradeNo);
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		logger.info("trade_status="+trade_status);
		//签名
		String sign = new String(request.getParameter("sign").getBytes("ISO-8859-1"),"UTF-8");
		
		AlipayRecords alipayRecords = alipayRecordsService.selectByPrimaryKey(Integer.parseInt(out_trade_no));
		Agent agent = agentService.selectByAgentId(alipayRecords.getForeignKey());
		if(alipayRecords.getStatus().equals(AlipayTradeStatusEnum.SUCCESS)){
			out.println("success");	
		}else if(MD5Util.genMd5String(AESKit.encrypt(rechargeChannelCode+tradeNo, initSecret)).equalsIgnoreCase(sign)){//验证成功
			logger.info("AlipayNotify.verify");
			if(trade_status.equals("TRADE_FINISHED")||trade_status.equals("TRADE_SUCCESS")){
				agentService.recharge(alipayRecords.getMoney(),agent);
				alipayRecords.setStatus(AlipayTradeStatusEnum.SUCCESS);
				alipayRecords.setTradeNo(tradeNo);
				alipayRecordsService.updateByPrimaryKeySelective(alipayRecords);
			} else{
				alipayRecords.setStatus(AlipayTradeStatusEnum.FAIL);
				alipayRecords.setTradeNo(tradeNo);
				alipayRecordsService.updateByPrimaryKeySelective(alipayRecords);
			}
			out.println("success");	
		}else{//验证失败
			out.println("fail");
		}
	}
}
