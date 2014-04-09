package com.magic.promotion.inviteCode.service;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.promotion.agent.service.AgentServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.initUser.domain.InitUser;
import com.magic.promotion.initUser.service.InitUserServiceImpl;
import com.magic.promotion.inviteCode.dao.InviteCodeMapper;
import com.magic.promotion.inviteCode.domain.InviteCode;
import com.magic.promotion.sysParam.SysParamUtil;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.promotion.util.enumUtil.InviteStatusEnum;
import com.magic.util.ConfigInfoProperties;
import com.magic.util.PagePO;
import com.magic.util.SendPhoneMessage;
import com.sun.xml.internal.ws.resources.SenderMessages;

@Service("inviteCodeService")
public class InviteCodeServiceImpl  {
	@Autowired
	InviteCodeMapper inviteCodeMapper;
	@Autowired
	SysParamServiceImpl sysParamService;
	
	@Autowired
	AgentServiceImpl agentService;
	@Autowired
	InitUserServiceImpl initUserService;
	public int countByExample(InviteCode example){
    	return inviteCodeMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return inviteCodeMapper.deleteByPrimaryKey(id);
   }

    public int insert(InviteCode record){
    	return inviteCodeMapper.insert(record);
    }


    public List<InviteCode> selectByExample(@Param("example") InviteCode example,@Param("page")PagePO page){
    	List<InviteCode> inviteCodeList = inviteCodeMapper.selectByExample(example, page);
    	return inviteCodeList;
    }

    public int updateByPrimaryKeySelective(InviteCode record){
    	return inviteCodeMapper.updateByPrimaryKeySelective(record);
    }


	public InviteCode selectByPrimaryKey(Integer id) {
		return inviteCodeMapper.selectByPrimaryKey(id);
	}
	@Transactional
	public void inviteUser(InviteCode record,int initUserid)throws BusinessException{
		InviteCode inviteCode = new InviteCode();
		inviteCode.setPhone(record.getPhone());
		List<InviteCode> listInviteCode = selectByExample(inviteCode,null);
		if(listInviteCode!=null&&listInviteCode.size()==1){
			inviteCode = listInviteCode.get(0);
			
			Calendar addTime=Calendar.getInstance(); 
			addTime.setTime(inviteCode.getAddTime());
			addTime.add(Calendar.DATE, Integer.parseInt(sysParamService.selectByName(SysParamUtil.USER_REG_TIME_LIMIT).getValue()));
			
			Calendar nowTime=Calendar.getInstance(); 
			nowTime.setTime(new Date());
			
			if(nowTime.compareTo(addTime)==1){//邀请码过期
				record.setId(inviteCode.getId());
				updateByPrimaryKeySelective(record);
				//给用户发短信
				sendInviteMessage(record);
			}else if(record.getAgentId().equals(inviteCode.getAgentId())){//第一次邀请的人再一次发送短信
				//给用户发短信
				sendInviteMessage(record);
			}else{//邀请码未过期
				throw new BusinessException("该用户已被别人邀请！");
			}
			
			
		}else{
			insert(record);
			InitUser initUser = new InitUser();
			initUser.setId(initUserid);
			initUser.setInviteStatus(InviteStatusEnum.INVITE);
			initUserService.updateByPrimaryKeySelective(initUser);
			//sendInviteMessage(record);
		}
		//扣款
		agentService.sendMessage(record.getAgentId(), new BigDecimal(sysParamService.selectByName(SysParamUtil.MESSAGE_MONEY).getValue()));
		
		
	}
	//给用户发短信
	public void sendInviteMessage(InviteCode inviteCode){
		try{
			if(inviteCode.getType()==AgentTypeEnum.SALER){
				SendPhoneMessage.sendMessage(inviteCode.getPhone(), ConfigInfoProperties.getSystemParam("userMessageContent"));
			}else{
				SendPhoneMessage.sendMessage(inviteCode.getPhone(), ConfigInfoProperties.getSystemParam("bussMessageContent"));
			}
		}catch(Exception  e){
			throw new BusinessException("邀请短信发送失败！");
		}
		
	}
}
