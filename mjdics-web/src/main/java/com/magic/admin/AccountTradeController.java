package com.magic.admin;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.util.PagePO;
import com.magic.promotion.util.enumUtil.TradeTypeEnum;
import com.mjdics.account.domain.AccountTrade;
import com.mjdics.account.service.AccountTradeServiceImpl;
import com.mjdics.sysManager.domain.TradeType;
import com.mjdics.sysManager.service.TradeTypeServiceImpl;


@Controller
@RequestMapping(value="admin/accountTrade")
@Scope("prototype")
public class AccountTradeController{
	String tip ="";
	private static final Logger logger = LoggerFactory.getLogger(AccountTradeController.class);
	
	@Autowired
	AccountTradeServiceImpl accountTradeService;
	@Autowired
	TradeTypeServiceImpl tradeTypeService;
	
	List<AccountTrade> accountTradeList;
	
	@RequestMapping(value = "insertAccountTrade", method = RequestMethod.POST)
	@ResponseBody
	public String insertAccountTrade(HttpSession session, AccountTrade accountTrade,String firstPriceEndDateq) {
		accountTrade.setAddTime(new Date());
		System.out.println(firstPriceEndDateq);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		accountTrade.setAddPerson("admin");
		accountTradeService.insert(accountTrade);
		tip = "添加成功！"; 
		return tip;
	}
	
	
	@RequestMapping(value = "isExistAccountTrade")
	@ResponseBody
	public String isExistAccountTrade(AccountTrade accountTrade){
		accountTradeList = (List<AccountTrade>) accountTradeService.selectByExample(accountTrade, null);
		if (null != accountTradeList && accountTradeList.size() != 0) {
			tip = "已存在，请重新输入";
			return tip;
		}
		return tip;
	}
	@RequestMapping(value = "gotoSelectAccountTrade")
	public String gotoSelectaccountTrade(ModelMap map){
		//map.put("accountTradeTypeEnum", AccountTradeTypeEnum.values());
		
		TradeType tradeType = new TradeType();
		tradeType.setType(TradeTypeEnum.INCOME);
		List<TradeType> inComeTypeList = tradeTypeService.selectByExample(tradeType, null);
		tradeType.setType(TradeTypeEnum.OUTCOME);
		List<TradeType> outComeTypeList = tradeTypeService.selectByExample(tradeType, null);
		
		map.put("outComeTypeList", outComeTypeList);
		map.put("inComeTypeList", inComeTypeList);
		return "account/accountTrade";
		
	}	
	@RequestMapping(value = "selectAccountTrade")
	@ResponseBody
	public List selectAccountTrade(AccountTrade accountTrade,ModelMap map,PagePO pagePo,HttpSession session){
		//Agent agent = (Agent)session.getAttribute("agent");
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		//accountTrade.setAgentId(agent.getAgentId());
		int totalCount = accountTradeService.countByExample(accountTrade);
		pagePo.initPage(totalCount);
		accountTradeList = accountTradeService.selectByExample(accountTrade, pagePo);
	
		map.put("accountTradeList", accountTradeList);
		map.put("pagePo", pagePo);
		return accountTradeList;
		
	}	
/*	@RequestMapping(value = "updateAccountTrade", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateAccountTrade(AccountTrade accountTrade ){
		logger.info("update accountTrade");
		accountTradeService.updateByPrimaryKeySelective(accountTrade);
		tip = "修改成功！";
		return tip;
	}*/
	@RequestMapping(value = "gotoPromotionList")
	public String gotoPromotionList(ModelMap map,String agentId){
		map.put("agentId", agentId);
		return "agent/salerAgentPromotion";
	}
}
