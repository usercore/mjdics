package com.magic.promotion.user.service;

import java.util.Date;
import java.util.List;

import org.apache.cxf.bus.BusState;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.agent.service.AgentServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.initUser.service.InitUserServiceImpl;
import com.magic.promotion.inviteCode.domain.InviteCode;
import com.magic.promotion.inviteCode.service.InviteCodeServiceImpl;
import com.magic.promotion.user.dao.UserMapper1;
import com.magic.promotion.user.domain.User1;
import com.magic.promotion.util.MD5Util;
import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.promotion.util.enumUtil.UserStatusEnum;
import com.magic.util.PagePO;

@Service("userService1")
public class UserServiceImpl  {
	@Autowired
	UserMapper1 userMapper;
	@Autowired
	InviteCodeServiceImpl inviteCodeService;
	@Autowired
	InitUserServiceImpl initUserService;
	
	@Autowired
	AgentServiceImpl agentService;
	
	public int countByExample(User1 example){
    	return userMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return userMapper.deleteByPrimaryKey(id);
   }

    public int insert(User1 record){
    	return userMapper.insert(record);
    }


    public List<User1> selectByExample(@Param("example") User1 example,@Param("page")PagePO page){
    	List<User1> userList = userMapper.selectByExample(example, page);
    	return userList;
    }

    public int updateByPrimaryKeySelective(User1 record){
    	return userMapper.updateByPrimaryKeySelective(record);
    }


	public User1 selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
    
	public List<User1> selectUserBySecAgent(String agentId,PagePO page){
		List<User1> userList = userMapper.selectUserBySecAgent(agentId,page);
		for(int i=0;i<userList.size();i++){
			User1 user = userList.get(i);
			Agent agent = agentService.selectByAgentId(user.getAgentId());
			user.setAgentName(agent.getName());
			
		}
		return  userList;
	}
	
	public int countUserBySecAgent(String agentId){
		return  userMapper.countUserBySecAgent(agentId);
	}
	@Transactional
	public void regUser(User1 user)throws BusinessException{
		InviteCode invite = new InviteCode();
		invite.setPhone(user.getPhone());
		invite.setStatus(ActiveEnum.NOT_ACTIVE);
		List<InviteCode> inviteCodeList = inviteCodeService.selectByExample(invite, null);
		if(inviteCodeList!=null&&inviteCodeList.size()!=0){
			invite = inviteCodeList.get(0);
			user.setIsAddCommision(true);
		}else{
			user.setIsAddCommision(false);
		}
		invite.setStatus(ActiveEnum.ACTIVE);
		inviteCodeService.updateByPrimaryKeySelective(invite);//邀请码已激活
		
		initUserService.updateStatusByPhone(invite.getPhone(),ActiveEnum.ACTIVE);
		
		user.setAgentId(invite.getAgentId());
		user.setAddTime(new Date());
		try {
			user.setPass(MD5Util.genMd5String(user.getPass()));
		} catch (Exception e) {
			throw new BusinessException("系统出错！");
		}
		user.setStatus(UserStatusEnum.NORMAL);
		insert(user);
	}
}
