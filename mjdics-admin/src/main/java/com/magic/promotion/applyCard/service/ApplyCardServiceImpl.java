package com.magic.promotion.applyCard.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.agent.service.AgentServiceImpl;
import com.magic.promotion.applyCard.dao.ApplyCardMapper;
import com.magic.promotion.applyCard.domain.ApplyCard;
import com.magic.promotion.applyCash.service.ApplyCashServiceImpl;
import com.magic.promotion.card.domain.Card;
import com.magic.promotion.card.service.CardServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.promotion.util.enumUtil.ApplyCardStatusEnum;
import com.magic.promotion.util.enumUtil.CardEnum;
import com.magic.util.PagePO;

@Service("applyCardService")
public class ApplyCardServiceImpl  {
	@Autowired
	ApplyCardMapper applyCardMapper;
	@Autowired
	ApplyCashServiceImpl applyCashService;
	@Autowired
	CardServiceImpl cardService;
	@Autowired
	AgentServiceImpl agentService;
	public int countByExample(ApplyCard example){
    	return applyCardMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return applyCardMapper.deleteByPrimaryKey(id);
   }

    public int insert(ApplyCard record){
    	return applyCardMapper.insert(record);
    }
    

    public List<ApplyCard> selectByExample(@Param("example") ApplyCard example,@Param("page")PagePO page){
    	List<ApplyCard> applyCardList = applyCardMapper.selectByExample(example, page);
    	return applyCardList;
    }

    public int updateByPrimaryKeySelective(ApplyCard record){
    	return applyCardMapper.updateByPrimaryKeySelective(record);
    }


	public ApplyCard selectByPrimaryKey(Integer id) {
		return applyCardMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public String  applyCard(ApplyCard applyCard,Agent agent)throws  BusinessException{
		String msg="";
		String cardTypeArr[] =  applyCard.getNameId().split("#");
		String cardTypeId = cardTypeArr[1];
		BigDecimal cardMoney = new BigDecimal(cardTypeArr[2]);
		
		Card c = new Card();
		Card example = new Card();
		example.setCardTypeId(Integer.parseInt(cardTypeId));	
		if(agent.getType()==AgentTypeEnum.FIRST_BUSS){
			example.setAgentId("admin");
			
			c.setStatus(CardEnum.AppCard);
			c.setCardTypeId(Integer.parseInt(cardTypeId));
			example.setStatus(CardEnum.Init);
			
		}else if (agent.getType()==AgentTypeEnum.SEC_BUSS || agent.getType()==AgentTypeEnum.SALER) {
	    	Agent a = agentService.selectByAgentId(agent.getAgentId());
	    	example.setAgentId(a.getParaAgent());	
	    	
	    	applyCard.setStatus(ApplyCardStatusEnum.MAKE_SUCCESS);
			c.setStatus(CardEnum.getCard);
			example.setStatus(CardEnum.getCard);
		}else{
			throw new BusinessException("您没有权限！");
		}
		int count = cardService.countByExample(example);
		if(count==0||count<applyCard.getAmount()){
			throw new BusinessException("申请制卡数量不够！少"+(applyCard.getAmount()-count)+"张");
		}
		//代销商扣款
		agentService.applyCard(applyCard.getAgentId(),cardMoney.multiply(new BigDecimal(applyCard.getAmount())) );
			
		int row = insert(applyCard);
		
		example.setCardTypeId(Integer.parseInt(cardTypeId));
		example.setNum(applyCard.getAmount());
		c.setApplyId(applyCard.getId());
		c.setAgentId(applyCard.getAgentId());
		c.setUpdateTime(new Date());
		cardService.updateByExampleSelective(c,example);
		
		Map<String,String> map = cardService.selectCardScope(c);	
		String min = map.get("min");
		String max = map.get("max");
		applyCard.setCardScope(min+"-"+max);
		updateByPrimaryKeySelective(applyCard);
		return msg;
	}
	@Transactional
	public int applyCardFulfill(ApplyCard applyCard,Agent agent){
		if(agent.getType()==AgentTypeEnum.FIRST_BUSS){
			Card c = new Card();
			c.setStatus(CardEnum.getCard);
			
			Card example = new Card();
			example.setApplyId(applyCard.getId());
			cardService.updateByExampleSelective(c, example);
		}
		applyCard.setStatus(ApplyCardStatusEnum.APPLY_SUCCESS);
		return updateByPrimaryKeySelective(applyCard);
	}	
	
	
}
