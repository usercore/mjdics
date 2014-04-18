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
import com.magic.promotion.user.domain.User1;
import com.magic.promotion.user.service.UserServiceImpl;
import com.magic.promotion.util.MD5Util;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.promotion.util.enumUtil.UserStatusEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/user")
@Scope("prototype")
public class UserController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserServiceImpl userService;
	List<User1> userList;
	
	@RequestMapping(value = "insertUser", method = RequestMethod.POST)
	@ResponseBody
	public String insertUser(HttpSession session, User1 user) throws Exception {
		Agent agent = (Agent)session.getAttribute("agent");
		user.setLoginTime(new Date());
		user.setAddTime(new Date());
		user.setPass(MD5Util.genMd5String(user.getPass()));
		user.setAddPerson(agent.getAgentId());
		user.setAgentId(agent.getAgentId());
		user.setStatus(UserStatusEnum.NORMAL);
		userService.insert(user);
		msg = "添加成功！"; 
		return msg;
	}
	
	
	@RequestMapping(value = "isExistUser")
	@ResponseBody
	public String isExistUser(User1 user){
		userList = (List<User1>) userService.selectByExample(user, null);
		if (null != userList && userList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectUser")
	public String gotoSelectUser(){
		return "user/user";
	}
	@RequestMapping(value = "gotoStatUser")
	public String gotoStatUser(){
		return "user/statUser";
	}	
	@RequestMapping(value = "selectUser")
	@ResponseBody
	public Map<String,Object> selectuser(User1 user,ModelMap map,HttpSession session,PagePO pagePo){
		Agent agent = (Agent)session.getAttribute("agent");
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		if(agent.getType()!=AgentTypeEnum.ADMIN)
			user.setAgentId(agent.getAgentId());
		
		int totalCount = userService.countByExample(user);
		pagePo.initPage(totalCount);
		userList = userService.selectByExample(user, pagePo);
		
		Map<String,Object> mapData = new HashMap<String,Object> ();
		mapData.put("total", totalCount);
		mapData.put("rows",  userList);
		return mapData;
		
	}	
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateUser(User1 user ){
		logger.info("update user");
		userService.updateByPrimaryKeySelective(user);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteUser(String id){
		userService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
	@RequestMapping(value = "gotoSelectUserBySecAgent")
	public String gotoSelectUserBySecAgent(){
		return "user/userBySecAgent";
	}	
	@RequestMapping(value = "selectUserBySecAgent")
	@ResponseBody
	public Map<String,Object> selectUserBySecAgent(ModelMap map,HttpSession session,PagePO pagePo){
		Agent agent = (Agent)session.getAttribute("agent");
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		
		int totalCount = userService.countUserBySecAgent(agent.getAgentId());
		pagePo.initPage(totalCount);
		userList = userService.selectUserBySecAgent(agent.getAgentId(),pagePo);
		
		Map<String,Object> mapData = new HashMap<String,Object> ();
		mapData.put("total", totalCount);
		mapData.put("rows",  userList);
		return mapData;
		
	}	
}
