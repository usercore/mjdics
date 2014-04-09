package com.magic.admin;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.inviteCode.domain.InviteCode;
import com.magic.promotion.inviteCode.service.InviteCodeServiceImpl;
import com.magic.promotion.util.StringUtil;
import com.magic.promotion.util.enumUtil.AgentTradeTypeEnum;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/inviteCode")
@Scope("prototype")
public class InviteCodeController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(InviteCodeController.class);
	
	@Autowired
	InviteCodeServiceImpl inviteCodeService;
	List<InviteCode> inviteCodeList;
	
	@RequestMapping(value = "inviteUser")
	@ResponseBody
	public String inviteUser(HttpSession session, String phone,int initUserId,AgentTypeEnum type) {
		Agent agent =  (Agent)session.getAttribute("agent");
		InviteCode inviteCode = new InviteCode();
		inviteCode.setPhone(phone);
		inviteCode.setType(type);
		inviteCode.setInviteCode(StringUtil.genInviteCode());
		inviteCode.setAgentId(agent.getAgentId());
		inviteCode.setAddPerson(agent.getAgentId());
		inviteCode.setAddTime(new Date());
		try{
			inviteCodeService.inviteUser(inviteCode,initUserId);
		}catch(BusinessException e){
			msg = e.getMessage();
			return msg;
		}
		msg = "邀请成功！"; 
		return msg;
	}
	
	
	@RequestMapping(value = "isExistInviteCode")
	@ResponseBody
	public String isExistInviteCode(InviteCode inviteCode){
		inviteCodeList = (List<InviteCode>) inviteCodeService.selectByExample(inviteCode, null);
		if (null != inviteCodeList && inviteCodeList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectInviteCode")
	public String gotoSelectInviteCode(){
		return "inviteCode/inviteCode";
	}	
	@RequestMapping(value = "selectInviteCode")
	@ResponseBody
	public List selectinviteCode(InviteCode inviteCode,ModelMap map,PagePO pagePo){
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = inviteCodeService.countByExample(inviteCode);
		pagePo.initPage(totalCount);
		inviteCodeList = inviteCodeService.selectByExample(inviteCode, pagePo);
		map.put("inviteCodeList", inviteCodeList);
		map.put("pagePo", pagePo);
		//map.put("isAndNotEnum",  IsAndNotEnum.values());
		return inviteCodeList;
		
	}	
	@RequestMapping(value = "updateInviteCode", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateInviteCode(InviteCode inviteCode ){
		logger.info("update inviteCode");
		inviteCodeService.updateByPrimaryKeySelective(inviteCode);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteInviteCode", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteInviteCode(String id){
		inviteCodeService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
}
