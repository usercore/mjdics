package com.magic.admin;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.util.AlipayNotify;
import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.agent.service.AgentServiceImpl;
import com.magic.promotion.alipayRecords.domain.AlipayRecords;
import com.magic.promotion.alipayRecords.service.AlipayRecordsServiceImpl;
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
import com.magic.promotion.util.enumUtil.AlipayTradeStatusEnum;
import com.magic.promotion.util.enumUtil.AlipayTradeTypeEnum;
import com.magic.util.AliPay;
import com.magic.util.AlipayConfigProperties;
import com.magic.util.PagePO;
import com.mjdics.account.domain.User;
import com.mjdics.account.service.AccountServiceImpl;
import com.mjdics.account.service.AccountTradeServiceImpl;


@Controller
@RequestMapping(value="admin/account")
@Scope("prototype")
public class AccountController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	AgentServiceImpl agentService;
	@Autowired
	UserGroupServiceImpl userGroupService;
	@Autowired
	AliPay aliPay;
	@Autowired
	AlipayRecordsServiceImpl alipayRecordsService;
	
	@Autowired
	AccountServiceImpl accountService;
	
	List<Agent> agentList;
	
/*	@RequestMapping(value = "insertAccount", method = RequestMethod.POST)
	@ResponseBody
	public String insertAgent(HttpSession session, Agent agent) throws Exception {
		Agent agentLogin = (Agent)session.getAttribute("agent");
		agent.setAddTime(new Date());
		agent.setAddPerson(agentLogin.getAgentId());
		agent.setParaAgent(agentLogin.getAgentId());
		agent.setType(AgentTypeEnum.valueOf(agentLogin.getType().ordinal()+1));
		UserGroup userGroup = new UserGroup();
		userGroup.setLevel(agent.getType().ordinal());
		List<UserGroup> userGroupList = userGroupService.selectByExample(userGroup, null);
		if(userGroupList!=null&&userGroupList.size()==1){
			agent.setGroupId(userGroupList.get(0).getGroupId());
		}
		agent.setPass(MD5Util.genMd5String(agent.getPass()));
		agentService.insert(agent);
		msg = "添加成功！"; 
		return msg;
	}
*/	
	@RequestMapping(value = "isExistAgent")
	@ResponseBody
	public String isExistAgent(Agent agent){
		agentList = (List<Agent>) agentService.selectByExample(agent, null);
		if (null != agentList && agentList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectAccount")
	public String gotoSelectAgent(HttpSession session,ModelMap map,String type,String isAccount){
		User user = (User)session.getAttribute("user");
		map.put("agentTyepEnum", AgentTypeEnum.values());
		map.put("type", type);
		//一级代销商的业务员
		/*if(type.equals(AgentTypeEnum.SALER.toString())&&AgentTypeEnum.FIRST_BUSS==agentLogin.getType()){
			return "agent/salerFirAgent";
		}
		//二级代销商的业务员
		if(type.equals(AgentTypeEnum.SALER.toString())&&AgentTypeEnum.SEC_BUSS==agentLogin.getType()){
			return "agent/salerSecAgent";
		}*/
		return "agent/agent";
	}
	
	@RequestMapping(value = "selectAgent")
	@ResponseBody
	public Map<String,Object> selectagent(Agent agent,ModelMap map,HttpSession session,PagePO page){
		if(page==null){
			page = new PagePO();
			page.setCurrentPage(1);
		}
		Agent agentLogin = (Agent)session.getAttribute("agent");
		if(agentLogin.getType()==AgentTypeEnum.FIRST_BUSS&&agent.getParaAgent()==null){
			agent.setType(AgentTypeEnum.SEC_BUSS);
		}
		if(agentLogin.getType()!=AgentTypeEnum.ADMIN&&agent.getParaAgent()==null){
			agent.setParaAgent(agentLogin.getAgentId());
		}
		Map<String,Object> mapData = new HashMap<String,Object> ();
		int totalCount = agentService.countByExample(agent);
		page.initPage(totalCount);
		agentList = agentService.selectByExample(agent, page);
		mapData.put("total", totalCount);
		mapData.put("rows",  agentList);
		return mapData;
		
	}
	
	
	@RequestMapping(value = "updateAgent")
	@ResponseBody
	public 	String  updateAgent(Agent agent ){
		logger.info("update agent");
		agentService.updateByPrimaryKeySelective(agent);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteAgent", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteAgent(String id){
		agentService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
	@RequestMapping(value = "applyCash", method = RequestMethod.POST)
	@ResponseBody
	public String  applyCash(BigDecimal money,HttpSession session){
		msg = "申请失败！";
		Agent agentLogin = (Agent)session.getAttribute("agent");
		try{
			agentService.applyCash(money,agentLogin);
		}catch(BusinessException e){
			msg = e.getMessage();
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "trade")
	@ResponseBody
	public String  trade(BigDecimal money,String typeId,HttpSession session,String remark) throws Exception{
		User user = (User)session.getAttribute("user");
		accountService.trade(user, money, typeId, remark);
		String msg = "成功";
		return msg;
		
	}
	
	@RequestMapping(value = "selectChildAgent")
	public String selectChildAgent(String agentId,ModelMap map,HttpSession session,PagePO page){
		map.put("agentTyepEnum", AgentTypeEnum.values());
		map.put("agentId", agentId);
		return "agent/agent";
		
	}
	
}
