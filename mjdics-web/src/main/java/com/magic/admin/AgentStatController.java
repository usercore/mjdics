package com.magic.admin;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.magic.promotion.agent.service.AgentServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.initUser.service.InitUserServiceImpl;
import com.magic.promotion.inviteCode.domain.InviteCode;
import com.magic.promotion.inviteCode.service.InviteCodeServiceImpl;
import com.magic.promotion.priviledges.domain.UserGroup;
import com.magic.promotion.priviledges.service.GroupPriviledgesServiceImpl;
import com.magic.promotion.priviledges.service.UserGroupServiceImpl;
import com.magic.promotion.util.MD5Util;
import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/agent")
@Scope("prototype")
public class AgentStatController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(AgentStatController.class);
	
	@Autowired
	AgentServiceImpl agentService;
	@Autowired
	UserGroupServiceImpl userGroupService;
	

	
	List<Agent> agentList;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "gotoStatAgent")
	public String gotoStatAgent(){
		return "agent/statAgent";
		
	}
	@RequestMapping(value = "statAgent")
	@ResponseBody
	public Map<String,Object> statAgent(HttpSession session){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		Agent agent = (Agent)session.getAttribute("agent");
		agentList = agentService.statAgent(agent.getAgentId());
		mapData.put("total", agentList.size());
		mapData.put("rows",  agentList);
		return mapData;
	}
	/**
	 * 代销商账户汇总
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "gotoStatTotalAgent")
	public String gotoStatTotalAgent(HttpSession session){
		Agent loginAgent = (Agent)session.getAttribute("agent");
		if(loginAgent.getType()==AgentTypeEnum.FIRST_BUSS){
			return "agent/stat/statTotalFirstAgent";
		}
		if(loginAgent.getType()==AgentTypeEnum.SEC_BUSS){
			return "agent/stat/statTotalSecAgent";
		}
		if(loginAgent.getType()==AgentTypeEnum.SALER){
			return "agent/stat/statTotalSalerAgent";
		}
		
		return "index/welcome";
		
	}
	@RequestMapping(value = "statTotalFirstAgent")
	@ResponseBody
	public Map<String,Object> statTotalFirstAgent(HttpSession session){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		Agent loginAgent = (Agent)session.getAttribute("agent");
		Agent agent = agentService.statTotalFirstAgent(loginAgent.getAgentId());
		agent.setCommission(loginAgent.getCommission());
		List<Agent> agentList = new ArrayList<Agent>();
		agentList.add(agent);
		mapData.put("total", 1);
		mapData.put("rows",  agentList);
		return mapData;
	}
	@RequestMapping(value = "statTotalSecAgent")
	@ResponseBody
	public Map<String,Object> statTotalSecAgent(HttpSession session){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		Agent loginAgent = (Agent)session.getAttribute("agent");
		Agent agent = agentService.statTotalSecAgent(loginAgent.getAgentId());
		agent.setCommission(loginAgent.getCommission());
		List<Agent> agentList = new ArrayList<Agent>();
		agentList.add(agent);
		mapData.put("total", 1);
		mapData.put("rows",  agentList);
		return mapData;
	}
	@RequestMapping(value = "statTotalSalerAgent")
	@ResponseBody
	public Map<String,Object> statTotalSalerAgent(HttpSession session){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		Agent loginAgent = (Agent)session.getAttribute("agent");
		Agent agent = agentService.statTotalSalerAgent(loginAgent.getAgentId());
		agent.setCommission(loginAgent.getCommission());
		agent.setSalesCardCommission(loginAgent.getSalesCardCommission());
		List<Agent> agentList = new ArrayList<Agent>();
		agentList.add(agent);
		mapData.put("total", 1);
		mapData.put("rows",  agentList);
		return mapData;
	}
	
	@RequestMapping(value = "gotoStatSecAgent")
	public String gotoStatSecAgent(){
		return "agent/statSecAgent";
		
	}
	@RequestMapping(value = "statSecAgent")
	@ResponseBody
	public Map<String,Object> statSecAgent(HttpSession session){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		Agent agent = (Agent)session.getAttribute("agent");
		agentList = agentService.statSecAgent(agent.getAgentId());
		mapData.put("total", agentList.size());
		mapData.put("rows",  agentList);
		return mapData;
	}
	@RequestMapping(value = "statSalerInfo")
	@ResponseBody
	public Map<String,Object> statSalerInfo(HttpSession session,String agentId){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		Agent agent = (Agent)session.getAttribute("agent");
		
		agentList = agentService.statSalerInfo(agent.getAgentId());
		mapData.put("total", agentList.size());
		mapData.put("rows",  agentList);
		return mapData;
	}
	
	
	
}
