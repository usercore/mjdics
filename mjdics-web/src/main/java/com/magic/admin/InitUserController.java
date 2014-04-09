package com.magic.admin;


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
import com.magic.promotion.initUser.domain.InitUser;
import com.magic.promotion.initUser.service.InitUserServiceImpl;
import com.magic.promotion.util.MD5Util;
import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/initUser")
@Scope("prototype")
public class InitUserController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(InitUserController.class);
	
	@Autowired
	InitUserServiceImpl initUserService;
	List<InitUser> initUserList;
	
	@RequestMapping(value = "insertInitUser", method = RequestMethod.POST)
	@ResponseBody
	public String insertInitUser(HttpSession session, InitUser initUser) throws Exception {
		Agent agent = (Agent)session.getAttribute("agent");
		initUser.setAddTime(new Date());
		initUser.setAddPerson(agent.getAgentId());
		initUser.setStatus(ActiveEnum.NOT_ACTIVE);
		initUser.setAgentId(agent.getAgentId());
		initUser.setParaAgent(agent.getParaAgent());
		if(initUserService.countByExample(initUser)==0){
			initUserService.insert(initUser);
			msg = "添加成功！"; 
		}else{
			msg = "用户已存在，请勿重复添加！"; 
		}

		return msg;
	}
	
	
	@RequestMapping(value = "isExistInitUser")
	@ResponseBody
	public String isExistInitUser(InitUser initUser){
		initUserList = (List<InitUser>) initUserService.selectByExample(initUser, null);
		if (null != initUserList && initUserList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectInitUser")
	public String gotoSelectInitUser(){
		return "user/initUser";
	}
	@RequestMapping(value = "gotoStatInitUser")
	public String gotoStatInitUser(){
		return "initUser/statInitUser";
	}	
	@RequestMapping(value = "selectInitUser")
	@ResponseBody
	public Map<String,Object> selectinitUser(InitUser initUser,ModelMap map,HttpSession session,PagePO pagePo){
		Agent agent = (Agent)session.getAttribute("agent");
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		initUser.setAgentId(agent.getAgentId());
		
		int totalCount = initUserService.countByExample(initUser);
		pagePo.initPage(totalCount);
		initUserList = initUserService.selectByExample(initUser, pagePo);
		
		Map<String,Object> mapData = new HashMap<String,Object> ();
		mapData.put("total", totalCount);
		mapData.put("rows",  initUserList);
		return mapData;
		
	}	
	@RequestMapping(value = "updateInitUser", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateInitUser(InitUser initUser ){
		logger.info("update initUser");
		initUserService.updateByPrimaryKeySelective(initUser);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteInitUser", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteInitUser(String id){
		initUserService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
}
