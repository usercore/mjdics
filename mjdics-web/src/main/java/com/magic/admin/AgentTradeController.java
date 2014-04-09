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
import com.magic.promotion.agentTrade.domain.AgentTrade;
import com.magic.promotion.agentTrade.service.AgentTradeServiceImpl;
import com.magic.promotion.util.enumUtil.AgentTradeTypeEnum;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/agentTrade")
@Scope("prototype")
public class AgentTradeController{
	String tip ="";
	private static final Logger logger = LoggerFactory.getLogger(AgentTradeController.class);
	
	@Autowired
	AgentTradeServiceImpl agentTradeService;
	List<AgentTrade> agentTradeList;
	
	@RequestMapping(value = "insertAgentTrade", method = RequestMethod.POST)
	@ResponseBody
	public String insertAgentTrade(HttpSession session, AgentTrade agentTrade,String firstPriceEndDateq) {
		agentTrade.setAddTime(new Date());
		System.out.println(firstPriceEndDateq);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		agentTrade.setAddPerson("admin");
		agentTradeService.insert(agentTrade);
		tip = "添加成功！"; 
		return tip;
	}
	
	
	@RequestMapping(value = "isExistAgentTrade")
	@ResponseBody
	public String isExistAgentTrade(AgentTrade agentTrade){
		agentTradeList = (List<AgentTrade>) agentTradeService.selectByExample(agentTrade, null);
		if (null != agentTradeList && agentTradeList.size() != 0) {
			tip = "已存在，请重新输入";
			return tip;
		}
		return tip;
	}
	@RequestMapping(value = "gotoSelectAgentTrade")
	public String gotoSelectagentTrade(ModelMap map){
		map.put("agentTradeTypeEnum", AgentTradeTypeEnum.values());
		return "agent/agentTrade";
		
	}	
	@RequestMapping(value = "selectAgentTrade")
	@ResponseBody
	public List selectagentTrade(AgentTrade agentTrade,ModelMap map,PagePO pagePo,HttpSession session){
		Agent agent = (Agent)session.getAttribute("agent");
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		agentTrade.setAgentId(agent.getAgentId());
		int totalCount = agentTradeService.countByExample(agentTrade);
		pagePo.initPage(totalCount);
		agentTradeList = agentTradeService.selectByExample(agentTrade, pagePo);
		map.put("agentTradeList", agentTradeList);
		map.put("pagePo", pagePo);
		return agentTradeList;
		
	}	
	@RequestMapping(value = "updateAgentTrade", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateAgentTrade(AgentTrade agentTrade ){
		logger.info("update agentTrade");
		agentTradeService.updateByPrimaryKeySelective(agentTrade);
		tip = "修改成功！";
		return tip;
	}
	@RequestMapping(value = "gotoPromotionList")
	public String gotoPromotionList(ModelMap map,String agentId){
		map.put("agentId", agentId);
		return "agent/salerAgentPromotion";
	}
	/**
	 * 业务员推广行为列表
	 * @param session
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "promotionList")
	@ResponseBody
	public Map<String,Object> promotionList(HttpSession session,String agentId,PagePO page){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		Agent agent = (Agent)session.getAttribute("agent");
		
		AgentTrade agentTrade = new AgentTrade();
		agentTrade.setAgentId(agentId);
		agentTrade.setType(AgentTradeTypeEnum.SEND_MESSAGE);
		int totalCount = agentTradeService.countByExample(agentTrade);
		page.initPage(totalCount);
		agentTradeList = agentTradeService.selectByExample(agentTrade, page);
		
		mapData.put("total", totalCount);
		mapData.put("rows",  agentTradeList);
		return mapData;
	}
}
