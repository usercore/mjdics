package com.magic.promotion.card.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.agent.service.AgentServiceImpl;
import com.magic.promotion.alipayRecords.domain.AlipayRecords;
import com.magic.promotion.alipayRecords.service.AlipayRecordsServiceImpl;
import com.magic.promotion.card.dao.CardMapper;
import com.magic.promotion.card.domain.Card;
import com.magic.promotion.cardType.domain.CardType;
import com.magic.promotion.cardType.service.CardTypeServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.sysParam.SysParamUtil;
import com.magic.promotion.sysParam.domain.SysParam;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.user.domain.User;
import com.magic.promotion.user.service.UserServiceImpl;
import com.magic.promotion.util.DateUtil;
import com.magic.promotion.util.StringUtil;
import com.magic.promotion.util.enumUtil.AgentTradeTypeEnum;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.promotion.util.enumUtil.AlipayTradeStatusEnum;
import com.magic.promotion.util.enumUtil.AlipayTradeTypeEnum;
import com.magic.promotion.util.enumUtil.ApplyCardStatusEnum;
import com.magic.promotion.util.enumUtil.CardEnum;
import com.magic.promotion.util.enumUtil.CardTypeEnum;
import com.magic.promotion.util.enumUtil.CardTypeStatusEnum;
import com.magic.promotion.util.enumUtil.CommStatusEnum;
import com.magic.util.PagePO;

@Service("cardService")
public class CardServiceImpl  {
	@Autowired
	CardMapper cardMapper;
	@Autowired
	AgentServiceImpl agentService;
	@Autowired
	SysParamServiceImpl sysParamService;
	@Autowired
	UserServiceImpl userService;	
	@Autowired
	CardTypeServiceImpl cardTypeService;
	@Autowired
	AlipayRecordsServiceImpl alipayRecordsService;
	public int countByExample(Card example){
    	return cardMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return cardMapper.deleteByPrimaryKey(id);
   }

    public int insert(Card record){
    	return cardMapper.insert(record);
    }
    @Transactional
    public int insertBatch(Card card,int batch,CardType ct,Agent agentLogin){
		Integer num = card.getNum();
    	for (int i = 0; i < num; i++) {
    	    StringBuffer sb = new StringBuffer();
    		sb.append(formatBit(batch+"",4));
    		sb.append(formatBit(i+"",4));
    		
    		card.setCardId(sb.toString());
    		card.setPassword(StringUtil.genInviteCode());
    		card.setCardTypeId(ct.getId());
    		card.setStatus(CardEnum.Init);
    		card.setCommStatus(CommStatusEnum.NOT_ADD_COMM);
    		card.setAgentId(agentLogin.getAgentId());
    		card.setMoney(ct.getMoney());
    		card.setName(ct.getName());
    		card.setPrice(ct.getPrice());
    		card.setWebPrice(ct.getWebPrice());
    		card.setFirstCommision(ct.getFirstCommision());
    		card.setSecCommision(ct.getSecCommision());
    		card.setSaleCommision(ct.getSaleCommision());
    		card.setSaleCardCommision(ct.getSaleCardCommision());
//    		card.setAllowBack(ct.getAllowBack());
    		card.setPrizeCount(ct.getPrizeCount());
    		card.setLotteryMoney(ct.getLotteryMoney());
    		card.setBetCount(ct.getBetCount());
    		card.setBuyCount(0);
    		card.setBetAddress(ct.getBetAddress());
    		card.setResourceId(ct.getResourceId());
    		
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    		card.setAddPerson(agentLogin.getAgentId());
    		card.setAddTime(new Date());
    		
    		card.setBatch(formatBit(batch+"",4));
//          card.setApplyId(applyId);
    		insert(card);			
    	}
		return num;
    }   
    @Transactional
    public void freeBuyCard(User user) {
	    CardType cardType = new CardType();
	    cardType.setStatus(CardTypeStatusEnum.Active);
	    cardType.setType(CardTypeEnum.Charge);
	    List<CardType> ctl = cardTypeService.selectByExample(cardType, null);
		String alipayId = (UUID.randomUUID().toString()).replaceAll("-","");
    	for (int i = 0; i < ctl.size(); i++) {  
    		CardType ct = ctl.get(i);
    		Card card = new Card();
	    	card.setUserId(user.getPhone());	
	    	card.setCardId((UUID.randomUUID().toString()).replaceAll("-",""));
	        card.setName(ct.getName());
	        card.setStatus(CardEnum.Sell);
	        card.setCommStatus(CommStatusEnum.NOT_ADD_COMM);
	        card.setAlipayId(alipayId);
			card.setAgentId(user.getAgentId());
			
			card.setMoney(ct.getMoney());
			card.setPrice(ct.getPrice());
			card.setWebPrice(ct.getWebPrice());
			card.setFirstCommision(ct.getFirstCommision());
			card.setSecCommision(ct.getSecCommision());
			card.setSaleCommision(ct.getSaleCommision());
			card.setSaleCardCommision(ct.getSaleCardCommision());
//			card.setAllowBack(ct.getAllowBack());
			card.setPrizeCount(ct.getPrizeCount());
			card.setLotteryMoney(ct.getLotteryMoney());
			card.setBetCount(ct.getBetCount());
			card.setBuyCount(0);
			card.setBetAddress(ct.getBetAddress());
			card.setResourceId(ct.getResourceId());
	    	
			card.setAddPerson(user.getPhone());
			card.setAddTime(new Date());
			insert(card);	
    	}
	}    
    
    @Transactional
    public String userBuyCard(Card card,CardType ct,User user) {
		Integer num = card.getNum();
		BigDecimal n = new BigDecimal(num);
		AlipayRecords alipayRecords = new AlipayRecords();
		alipayRecords.setMoney(n.multiply(ct.getWebPrice()));
		alipayRecords.setForeignKey(user.getPhone());
		alipayRecords.setType(AlipayTradeTypeEnum.USER_RECHARGE);
		alipayRecords.setStatus(AlipayTradeStatusEnum.INIT);
		alipayRecords.setAddTime(new Date());
		alipayRecords.setAddPerson(user.getPhone());
		alipayRecordsService.insert(alipayRecords);
		
		String alipayId = alipayRecords.getId().toString();
    	for (int i = 0; i < num; i++) {    	
	    	card.setUserId(user.getPhone());	
	    	card.setCardId((UUID.randomUUID().toString()).replaceAll("-",""));
	        card.setName(ct.getName());
	        card.setStatus(CardEnum.Not_Pay);
	        card.setCommStatus(CommStatusEnum.NOT_ADD_COMM);
	        card.setAlipayId(alipayId);
			card.setAgentId(user.getAgentId());
			
			card.setMoney(ct.getMoney());
			card.setPrice(ct.getPrice());
			card.setWebPrice(ct.getWebPrice());
			card.setFirstCommision(ct.getFirstCommision());
			card.setSecCommision(ct.getSecCommision());
			card.setSaleCommision(ct.getSaleCommision());
			card.setSaleCardCommision(ct.getSaleCardCommision());
//			card.setAllowBack(ct.getAllowBack());
			card.setPrizeCount(ct.getPrizeCount());
			card.setLotteryMoney(ct.getLotteryMoney());
			card.setBetCount(ct.getBetCount());
			card.setBuyCount(0);
			card.setBetAddress(ct.getBetAddress());
			card.setResourceId(ct.getResourceId());
	    	
			card.setAddPerson(user.getPhone());
			card.setAddTime(new Date());
			insert(card);	
    	}
    	return alipayId;
	}
    
	public String formatBit(String str, int num) {
		String[] nums = { "", "0", "00", "000" ,"0000"};
		return nums[num - str.length()] + str;
	}
    
    public List<Card> selectByExample(@Param("example") Card example,@Param("page")PagePO page){
    	List<Card> cardList = cardMapper.selectByExample(example, page);
    	for(int i=0;i<cardList.size();i++){
    		setCardTypeData(cardList.get(i));
    	}
    	return cardList;
    }
    public List<Card> selectByExample1(@Param("example") Card example,@Param("page")PagePO page){
    	List<Card> cardList = cardMapper.selectByExample1(example, page);
    	for(int i=0;i<cardList.size();i++){
    		setCardTypeData(cardList.get(i));
    	}
    	return cardList;
    }    

    public int updateByPrimaryKeySelective(Card record){
    	return cardMapper.updateByPrimaryKeySelective(record);
    }


	public Card selectByPrimaryKey(Integer id) {
		Card card = cardMapper.selectByPrimaryKey(id);
		setCardTypeData(card);
		return card;
	}
	
    public int updateByCard(Card record){
    	return cardMapper.updateByCard(record);
    }

    public Map<String,String> selectCardScope(Card record) {
    	return cardMapper.selectCardScope(record);
	}
    public List<Card> selectByCardTypeId(Card record){
    	List<Card> cardList = cardMapper.selectByCardTypeId(record);
    	for(int i=0;i<cardList.size();i++){
    		setCardTypeData(cardList.get(i));
    	}
    	return cardList;
    }
    public int updateByExampleSelective(Card record,Card example){
    	return cardMapper.updateByExampleSelective(record, example);
    }

    public int activationCard(Card example,User user){
    	example.setStatus(CardEnum.getCard);
    	List<Card> list = cardMapper.selectByExample2(example, ApplyCardStatusEnum.APPLY_SUCCESS,AgentTypeEnum.SALER);
		if(list==null||list.size()>1||list.size()==0){
			throw new BusinessException("卡激活失败！");
		}
		Card c = list.get(0);
		return addComm(c,user);
    }
    public void payment(Card example,User user){
    	List<Card> list = selectByExample(example,null);
		if(list==null||list.size()==0){
			throw new BusinessException("支付失败！");
		}
	    for (int i = 0; i < list.size(); i++) {
	    	Card c = list.get(i); 
	    	addComm(c,user);
		}
    }
    //@Transactional(rollbackFor=Exception.class)
    @Transactional
    public int addComm(Card c,User user){
		Card card = new Card();
		card.setUserId(user.getPhone());
		card.setStatus(CardEnum.Sell);
		card.setId(c.getId());
		
	    SysParam sysParam = sysParamService.selectByName(SysParamUtil.COM_TIME_LIMIT);
	
	    if((DateUtil.compareDate(user.getAddTime(),Integer.parseInt(sysParam.getValue())))>0){
	    	User us = new User();
	    	us.setIsAddCommision(false);
	    	us.setId(user.getId());
	    	userService.updateByPrimaryKeySelective(us);
	    }

	    if(user.getIsAddCommision()&&null!=user.getAgentId()&&!"".equals(user.getAgentId())){
	     	 Agent agent = agentService.selectByAgentId(user.getAgentId());
	    	 String agentSecId = agent.getParaAgent();
	    	 Agent agentSec = agentService.selectByAgentId(agentSecId);
	    	 String agentSaleId = agentSec.getParaAgent();
	    	 Agent agentSale = agentService.selectByAgentId(agentSaleId);
	    	
	    	 agentService.addComm(c.getAgentId(), c.getSaleCardCommision(), c.getCardId(),AgentTradeTypeEnum.SALE_CARD_COMMISSION);
	    	 agentService.addComm(user.getAgentId(), c.getSaleCommision(), c.getCardId(),AgentTradeTypeEnum.ADD_COMM);
	    	 agentService.addComm(agentSec.getAgentId(), c.getSecCommision(), c.getCardId(),AgentTradeTypeEnum.ADD_COMM);
	    	 agentService.addComm(agentSale.getAgentId(), c.getFirstCommision(), c.getCardId(),AgentTradeTypeEnum.ADD_COMM);
	    	 card.setCommStatus(CommStatusEnum.ADD_COMM);
	    }
		return updateByPrimaryKeySelective(card);
    }    
    
    
    public List<Card> cardByApplyId(Integer makeId){
    	List<Card> cardList = cardMapper.cardByApplyId(makeId);
    	for(int i=0;i<cardList.size();i++){
    		setCardTypeData(cardList.get(i));
    	}
    	return cardList;
    }
    public Map<String,String> statCardByAgentId(String agentId){
    	return cardMapper.statCardByAgentId(agentId);
    }
    public BigDecimal statCardBySecAgentId(String agentId){
    	return cardMapper.statCardBySecAgentId(agentId);
    }
    
    public Map<String,Object> statCardBySalerAgentId(String agentId){
    	return cardMapper.statCardBySalerAgentId(agentId);
    }
    
    private void setCardTypeData(Card card){
    		CardType cardType = cardTypeService.selectByPrimaryKey(card.getCardTypeId());
    		card.setMoney(cardType.getMoney());
    		card.setName(cardType.getName());
    		card.setPrice(cardType.getPrice());
    		card.setWebPrice(cardType.getWebPrice());
    		card.setFirstCommision(cardType.getFirstCommision());
    		card.setSecCommision(cardType.getSecCommision());
    		card.setSaleCommision(cardType.getSaleCommision());
    		card.setSaleCardCommision(cardType.getSaleCardCommision());
    		card.setLotteryMoney(cardType.getLotteryMoney());
    		card.setBetCount(cardType.getBetCount());
    		card.setBetAddress(cardType.getBetAddress());
    		card.setResourceId(cardType.getResourceId());
    }
    /**
     * 统计用户总投注次数、总中奖次数、已投注次数
     * @param userId
     * @return
     */
   public Card statCardByUserId(String userId){
	  return cardMapper.statCardByUserId(userId);
    }
}
