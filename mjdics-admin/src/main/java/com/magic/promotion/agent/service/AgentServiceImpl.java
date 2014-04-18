package com.magic.promotion.agent.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.promotion.agent.dao.AgentMapper;
import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.agentTrade.domain.AgentTrade;
import com.magic.promotion.agentTrade.service.AgentTradeServiceImpl;
import com.magic.promotion.applyCash.domain.ApplyCash;
import com.magic.promotion.applyCash.service.ApplyCashServiceImpl;
import com.magic.promotion.card.service.CardServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.initUser.domain.InitUser;
import com.magic.promotion.initUser.service.InitUserServiceImpl;
import com.magic.promotion.inviteCode.domain.InviteCode;
import com.magic.promotion.inviteCode.service.InviteCodeServiceImpl;
import com.magic.promotion.sysParam.SysParamUtil;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.user.domain.User1;
import com.magic.promotion.user.service.UserServiceImpl;
import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.promotion.util.enumUtil.AgentTradeTypeEnum;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.promotion.util.enumUtil.ApplyCashStatusEnum;
import com.magic.promotion.util.enumUtil.InviteStatusEnum;
import com.magic.util.PagePO;

@Service("agentService")
public class AgentServiceImpl  {
	@Autowired
	AgentMapper agentMapper;
	@Autowired
	ApplyCashServiceImpl applyCashService;
	@Autowired
	AgentTradeServiceImpl agentTradeService;
	@Autowired
	InviteCodeServiceImpl inviteCodeService;
	@Autowired
	SysParamServiceImpl sysParamService;
	@Autowired
	UserServiceImpl userService;
	@Autowired
	CardServiceImpl cardService;
	@Autowired
	InitUserServiceImpl initUserService;
	
	public int countByExample(Agent example){
    	return agentMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return agentMapper.deleteByPrimaryKey(id);
   }

    public int insert(Agent record){
    	return agentMapper.insert(record);
    }


    public List<Agent> selectByExample(@Param("example") Agent example,@Param("page")PagePO page){
    	List<Agent> agentList = agentMapper.selectByExample(example, page);
    	return agentList;
    }

    public int updateByPrimaryKeySelective(Agent record){
    	return agentMapper.updateByPrimaryKeySelective(record);
    }


	public Agent selectByPrimaryKey(Integer id) {
		return agentMapper.selectByPrimaryKey(id);
	}
	@Transactional
	public  boolean applyCash(BigDecimal money,Agent agent)throws BusinessException{
		agent = selectByPrimaryKey(agent.getId());
		boolean falg = true;
		
		agent.setFreezeMoney(agent.getFreezeMoney().add(money));
		if(updateByPrimaryKeySelective(agent)==0){
			throw new BusinessException("充值失败！");
		}
		ApplyCash applyCash = new ApplyCash();
		applyCash.setAddPerson(agent.getAgentId());
		applyCash.setAddTime(new Date());
		applyCash.setAgentId(agent.getAgentId());
		applyCash.setName(agent.getName());
		applyCash.setStatus(ApplyCashStatusEnum.APPLY);
		applyCash.setMoney(money);
		applyCash.setBank(agent.getBank());
		applyCash.setBankCard(agent.getBankCard());
		applyCashService.insert(applyCash);
		
		return falg;
	   }
	public Agent selectByAgentId(String agentId){
		return agentMapper.selectByAgentId(agentId);
	}
	/**
	 * 提现成功处理
	 * @param applyCash
	 * @param addPerson
	 * @return
	 */
	@Transactional
	public void transferMoney(ApplyCash applyCash,Agent agentLogin) throws BusinessException{
		Agent agent = selectByAgentId(applyCash.getAgentId());
		applyCash.setStatus(ApplyCashStatusEnum.APPLY_SUCCESS);
		applyCashService.updateByPrimaryKeySelective(applyCash);
		//
		agent.setFreezeMoney(agent.getFreezeMoney().subtract(applyCash.getMoney()));
		
		AgentTrade agentTrade = new AgentTrade();
		agentTrade.setAgentId(applyCash.getAgentId());
		agentTrade.setMoney(applyCash.getMoney());
		agentTrade.setSerial(applyCash.getId()+"");
		agentTrade.setType(AgentTradeTypeEnum.APPLY_CASH);
		agentTrade.setAddTime(new Date());
		agentTrade.setAddPerson(agent.getAgentId());
		if(updateAgentMoney(agentTrade,agent)==0){
			throw new BusinessException("转账失败！");
		}
	}
	@Transactional
	public  boolean recharge(BigDecimal money,Agent agent)throws BusinessException{
		boolean falg = true;
		agent = selectByPrimaryKey(agent.getId());
		AgentTrade agentTrade = new AgentTrade();
		agentTrade.setAgentId(agent.getAgentId());
		agentTrade.setMoney(money);
		agentTrade.setSerial(java.util.UUID.randomUUID().toString());
		agentTrade.setType(AgentTradeTypeEnum.RECHARGE);
		agentTrade.setAddTime(new Date());
		agentTrade.setAddPerson(agent.getAgentId());
		if(updateAgentMoney(agentTrade,agent)==0){
			throw new BusinessException("充值失败！");
		}
		return falg;
	   }

	public  void  applyCard(String agentId,BigDecimal money)throws BusinessException{
		Agent agent = selectByAgentId(agentId);
		AgentTrade agentTrade = new AgentTrade();
		agentTrade.setAgentId(agent.getAgentId());
		agentTrade.setMoney(money);
		agentTrade.setSerial(java.util.UUID.randomUUID().toString());
		agentTrade.setType(AgentTradeTypeEnum.APPLY_CARD);
		agentTrade.setAddTime(new Date());
		agentTrade.setAddPerson(agent.getAgentId());
		if(updateAgentMoney(agentTrade,agent)==0){
			throw new BusinessException("扣款失败！");
		}
		String paraAgent = agent.getParaAgent()==null||agent.getParaAgent().equals("")?"admin":agent.getParaAgent();
		if(!paraAgent.equals("admin")){
		agent = selectByAgentId(agent.getParaAgent());
		agentTrade.setAgentId(paraAgent);
		agentTrade.setType(AgentTradeTypeEnum.SALE_CARD);
		
			if(updateAgentMoney(agentTrade,agent)==0){
				throw new BusinessException("售卡失败！");
			}
		}
	   }
	/*
	 * 加提成
	 */
	public  void  addComm(String agentId,BigDecimal money,String cardId,AgentTradeTypeEnum type)throws BusinessException{
		Agent agent = selectByAgentId(agentId);
		AgentTrade agentTrade = new AgentTrade();
		agentTrade.setAgentId(agent.getAgentId());
		agentTrade.setMoney(money);
		agentTrade.setSerial(cardId);
		agentTrade.setType(type);
		agentTrade.setAddTime(new Date());
		if(type==AgentTradeTypeEnum.ADD_COMM){
			agent.setCommission(agent.getCommission().add(agentTrade.getMoney()));
		}else if(type==AgentTradeTypeEnum.SALE_CARD_COMMISSION){
			agent.setSalesCardCommission(agent.getSalesCardCommission().add(agentTrade.getMoney()));
		}
		if(updateAgentMoney(agentTrade,agent)==0){
			throw new BusinessException("加提成失败！");
		}
	   }
	/*
	 * 发短信
	 */
	public  void  sendMessage(String agentId,BigDecimal money )throws BusinessException{
		Agent agent = selectByAgentId(agentId);
		AgentTrade agentTrade = new AgentTrade();
		agentTrade.setAgentId(agent.getAgentId());
		agentTrade.setMoney(money);
		agentTrade.setSerial(java.util.UUID.randomUUID().toString());
		agentTrade.setType(AgentTradeTypeEnum.SEND_MESSAGE);
		agentTrade.setAddTime(new Date());
		if(updateAgentMoney(agentTrade,agent)==0){
			throw new BusinessException("发短信扣款失败！");
		}
	   }
	public int updateAgentMoney(AgentTrade agentTrade,Agent agent){
		
		//更新余额
		if(agentTrade.getType()==AgentTradeTypeEnum.APPLY_CASH||agentTrade.getType()==AgentTradeTypeEnum.APPLY_CARD
				||agentTrade.getType()==AgentTradeTypeEnum.SEND_MESSAGE){
			if(agent.getMoney().subtract(agentTrade.getMoney()).compareTo(new BigDecimal(0))<0){
				throw new BusinessException("余额不足！");
			}
			agent.setMoney(agent.getMoney().subtract(agentTrade.getMoney()));
		}else{
			agent.setMoney(agent.getMoney().add(agentTrade.getMoney()));
		}
			
		int count = updateByPrimaryKeySelective(agent);
		//插入交易记录
		agentTrade.setRemainMoney(agent.getMoney());
		agentTradeService.insert(agentTrade);
		return count;
	}
	
	public void regAgent(InviteCode inviteCode,Agent agent)throws BusinessException{
		List<InviteCode> inviteCodeList = inviteCodeService.selectByExample(inviteCode, null);
		if(inviteCodeList==null||inviteCodeList.size()==0){
			throw new BusinessException("邀请码输入错误！");
		}
		inviteCode = inviteCodeList.get(0);
		
		Calendar addTime=Calendar.getInstance(); 
		addTime.setTime(inviteCode.getAddTime());
		addTime.add(Calendar.DATE, Integer.parseInt(sysParamService.selectByName(SysParamUtil.USER_REG_TIME_LIMIT).getValue()));
		
		
		Calendar nowTime=Calendar.getInstance(); 
		nowTime.setTime(new Date());
		
		if(nowTime.compareTo(addTime)>0){
			agent.setParaAgent("admin");
			
		}else{
			agent.setParaAgent(inviteCode.getAgentId());
		}
		agent.setType(AgentTypeEnum.valueOf(inviteCode.getType().ordinal()+1));
		insert(agent);
		
		inviteCode.setStatus(ActiveEnum.ACTIVE);
		inviteCodeService.updateByPrimaryKeySelective(inviteCode);
	}
	/*
	 * 一级代销商业务汇总
	 */
	public Agent statTotalFirstAgent(String agentId){
		List<Agent> agentList = new ArrayList<Agent>();
		Agent agent = new Agent();
		agent.setParaAgent(agentId);
		agentList = selectByExample(agent, null);
		for(int i=0;i<agentList.size();i++){
			Agent secAgent = agentList.get(i);
			agent.setParaAgent(secAgent.getAgentId());
			agent.setBussCount(agent.getBussCount()+countByExample(agent));//业务员数量
			
			InitUser initUser = new InitUser();
			initUser.setParaAgent(secAgent.getAgentId());
			agent.setInitUserCount(agent.getInitUserCount()+initUserService.countByExample(initUser));//用户数
			
			List<User1> userList = userService.selectUserBySecAgent(secAgent.getAgentId(),null);
			agent.setUserCount(agent.getUserCount()+userList.size());//用户数量
			
			AgentTrade agentTrade = new AgentTrade();
			agentTrade.setAgentId(secAgent.getAgentId());
			agentTrade.setType(AgentTradeTypeEnum.SEND_MESSAGE);
			agent.setPromotionCount(agent.getPromotionCount()+agentTradeService.countSendMessage(agentTrade));//推广数
			
			
			agent.setUserByCardMoney(agent.getUserByCardMoney().add(cardService.statCardBySecAgentId(secAgent.getAgentId())));//用户购卡金额
			
		}
		agent.setAgentCount(agentList.size());
		return agent;
	}
	public List<Agent> statAgent(String agentId){
		List<Agent> agentList = new ArrayList<Agent>();
		Agent agent = new Agent();
		agent.setParaAgent(agentId);
		agentList = selectByExample(agent, null);
		for(int i=0;i<agentList.size();i++){
			Agent secAgent = agentList.get(i);
			agent.setParaAgent(secAgent.getAgentId());
			secAgent.setBussCount(countByExample(agent));//业务员数量
			
			InitUser initUser = new InitUser();
			initUser.setParaAgent(secAgent.getAgentId());
			secAgent.setInitUserCount(initUserService.countByExample(initUser));//用户数
			
			List<User1> userList = userService.selectUserBySecAgent(secAgent.getAgentId(),null);
			secAgent.setUserCount(userList.size());//用户数量
			
			AgentTrade agentTrade = new AgentTrade();
			agentTrade.setAgentId(secAgent.getAgentId());
			agentTrade.setType(AgentTradeTypeEnum.SEND_MESSAGE);
			secAgent.setPromotionCount(agentTradeService.countSendMessage(agentTrade));//推广数
			
			
			secAgent.setUserByCardMoney(cardService.statCardBySecAgentId(secAgent.getAgentId()));//用户购卡金额
			
		}
		return agentList; 
	}
	/*
	 * 二级代销商业务汇总
	 */
	public Agent statTotalSecAgent(String agentId){
		List<Agent> agentList = new ArrayList<Agent>();
		Agent agent = new Agent();
		agent.setParaAgent(agentId);
		agentList = selectByExample(agent, null);
		for(int i=0;i<agentList.size();i++){
			Agent bussAgent = agentList.get(i);
			
			
			agent.setParaAgent(agentId);
			agent.setBussCount(agent.getBussCount()+countByExample(agent));
			
			InitUser initUser = new InitUser();
			initUser.setAgentId(bussAgent.getAgentId());
			agent.setInitUserCount(agent.getInitUserCount()+initUserService.countByExample(initUser));//用户数
			
			initUser.setInviteStatus(InviteStatusEnum.INVITE);
			agent.setInviteUserCount(agent.getInviteUserCount()+initUserService.countByExample(initUser));
			
			User1 user = new User1();
			user.setAgentId(bussAgent.getAgentId());
			List<User1> userList = userService.selectByExample(user, null);
			agent.setUserCount(agent.getUserCount()+userList.size());//用户数量
			
			AgentTrade agentTrade = new AgentTrade();
			agentTrade.setAgentId(bussAgent.getAgentId());
			agentTrade.setType(AgentTradeTypeEnum.SEND_MESSAGE);
			agent.setPromotionCount(agentTradeService.countByExample(agentTrade));//推广数
			
			Map<String,Object> map = cardService.statCardBySalerAgentId(bussAgent.getAgentId());
			agent.setUserByCardMoney(agent.getUserByCardMoney().add(new BigDecimal(map.get("money").toString())));//用户购卡金额
			
			agent.setBuyCardUserCount(agent.getBuyCardUserCount()+Integer.parseInt(map.get("buyCardUserCount").toString()));
			
		}
		return agent; 
	}
	
	public List<Agent> statSecAgent(String agentId){
		List<Agent> agentList = new ArrayList<Agent>();
		Agent agent = new Agent();
		agent.setParaAgent(agentId);
		agentList = selectByExample(agent, null);
		for(int i=0;i<agentList.size();i++){
			Agent bussAgent = agentList.get(i);
			
			InitUser initUser = new InitUser();
			initUser.setAgentId(bussAgent.getAgentId());
			bussAgent.setInitUserCount(initUserService.countByExample(initUser));//用户数
			
			initUser.setInviteStatus(InviteStatusEnum.INVITE);
			bussAgent.setInviteUserCount(initUserService.countByExample(initUser));
			
			User1 user = new User1();
			user.setAgentId(bussAgent.getAgentId());
			List<User1> userList = userService.selectByExample(user, null);
			bussAgent.setUserCount(userList.size());//用户数量
			
			AgentTrade agentTrade = new AgentTrade();
			agentTrade.setAgentId(bussAgent.getAgentId());
			agentTrade.setType(AgentTradeTypeEnum.SEND_MESSAGE);
			bussAgent.setPromotionCount(agentTradeService.countByExample(agentTrade));//推广数
			
			Map<String,Object> map = cardService.statCardBySalerAgentId(bussAgent.getAgentId());
			bussAgent.setUserByCardMoney(new BigDecimal(map.get("money").toString()));//用户购卡金额
			
			bussAgent.setBuyCardUserCount(Integer.parseInt(map.get("buyCardUserCount").toString()));
			
		}
		return agentList; 
	}
	public List<Agent> statSalerInfo(String agentId){
		List<Agent> agentList = new ArrayList<Agent>();
		Agent agent = new Agent();
		agent.setParaAgent(agentId);
		agentList = selectByExample(agent, null);
		for(int i=0;i<agentList.size();i++){
			Agent secAgent = agentList.get(i);
			agent.setParaAgent(secAgent.getAgentId());
			secAgent.setBussCount(countByExample(agent));//业务员数量
			
			List<User1> userList = userService.selectUserBySecAgent(secAgent.getAgentId(),null);
			secAgent.setUserCount(userList.size());//用户数量
			
			secAgent.setUserByCardMoney(cardService.statCardBySecAgentId(secAgent.getAgentId()));
			
		}
		return agentList; 
	}
	
	public Agent statTotalSalerAgent(String agentId) {
		Agent agent = new Agent();
		agent.setParaAgent(agentId);

		agent.setParaAgent(agentId);
		agent.setBussCount(agent.getBussCount() + countByExample(agent));

		InitUser initUser = new InitUser();
		initUser.setAgentId(agentId);
		agent.setInitUserCount(initUserService.countByExample(initUser));// 用户数

		initUser.setInviteStatus(InviteStatusEnum.INVITE);
		agent.setInviteUserCount(initUserService.countByExample(initUser));

		User1 user = new User1();
		user.setAgentId(agentId);
		List<User1> userList = userService.selectByExample(user, null);
		agent.setUserCount(userList.size());// 用户数量

		AgentTrade agentTrade = new AgentTrade();
		agentTrade.setAgentId(agentId);
		agentTrade.setType(AgentTradeTypeEnum.SEND_MESSAGE);
		agent.setPromotionCount(agentTradeService.countByExample(agentTrade));// 推广数

		Map<String, Object> map = cardService.statCardBySalerAgentId(agentId);
		agent.setUserByCardMoney(new BigDecimal(map.get("money").toString()));// 用户购卡金额

		agent.setBuyCardUserCount(Integer.parseInt(map.get("buyCardUserCount").toString()));

		return agent;
	}
	
	public List<Agent> selectSalerByFirAgentId(String agentId,PagePO page){
		return this.agentMapper.selectSalerByFirAgentId(agentId, page);
	}
	
	public int countSalerByFirAgentId( String agentId){
		return this.agentMapper.countSalerByFirAgentId(agentId);
	}
}
