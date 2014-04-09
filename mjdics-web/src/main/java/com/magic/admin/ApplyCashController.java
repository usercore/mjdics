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
import com.magic.promotion.agent.service.AgentServiceImpl;
import com.magic.promotion.applyCash.domain.ApplyCash;
import com.magic.promotion.applyCash.service.ApplyCashServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/applyCash")
@Scope("prototype")
public class ApplyCashController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(ApplyCashController.class);
	
	@Autowired
	ApplyCashServiceImpl applyCashService;
	@Autowired
	AgentServiceImpl agentService;
	List<ApplyCash> applyCashList;
	
	@RequestMapping(value = "insertApplyCash", method = RequestMethod.POST)
	@ResponseBody
	public String insertApplyCash(HttpSession session, ApplyCash applyCash) throws Exception {
		Agent agent = (Agent)session.getAttribute("agent");
		applyCash.setAddTime(new Date());
		applyCash.setAddPerson(agent.getAgentId());
		applyCash.setAgentId(agent.getAgentId());
		
		applyCashService.insert(applyCash);
		msg = "添加成功！"; 
		return msg;
	}
	
	
	@RequestMapping(value = "isExistApplyCash")
	@ResponseBody
	public String isExistApplyCash(ApplyCash applyCash){
		applyCashList = (List<ApplyCash>) applyCashService.selectByExample(applyCash, null);
		if (null != applyCashList && applyCashList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectApplyCash")
	public String gotoSelectApplyCash(){
		return "agent/applyCash";
	}
	@RequestMapping(value = "gotoStatApplyCash")
	public String gotoStatApplyCash(){
		return "applyCash/statApplyCash";
	}	
	@RequestMapping(value = "selectApplyCash")
	@ResponseBody
	public Map<String,Object> selectapplyCash(HttpSession session,ApplyCash applyCash,ModelMap map,PagePO pagePo){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		
		Agent agent = (Agent)session.getAttribute("agent");
		if(agent.getAgentId()!="admin"){
			applyCash.setAgentId(agent.getAgentId());
		}
		
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = applyCashService.countByExample(applyCash);
		pagePo.initPage(totalCount);
		applyCashList = applyCashService.selectByExample(applyCash, pagePo);
		mapData.put("total", totalCount);
		mapData.put("rows",  applyCashList);
		return mapData;
		
	}	
	@RequestMapping(value = "updateApplyCash", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateApplyCash(ApplyCash applyCash ){
		logger.info("update applyCash");
		applyCashService.updateByPrimaryKeySelective(applyCash);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "transferMoney", method = RequestMethod.POST)
	@ResponseBody
	public 	String   transferMoney(int id,HttpSession session){
		msg = "转账成功！";
		Agent agent = (Agent)session.getAttribute("agent");
		try{
			agentService.transferMoney(applyCashService.selectByPrimaryKey(id), agent);
		}catch(BusinessException e){
			msg = e.getMessage();
			return msg;
		}
		return msg;
	}
}
