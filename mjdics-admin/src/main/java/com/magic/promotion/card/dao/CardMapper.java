package com.magic.promotion.card.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.card.domain.Card;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.promotion.util.enumUtil.ApplyCardStatusEnum;
import com.magic.util.PagePO;

public interface CardMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Card record);

	List<Card> selectByExample(@Param("example") Card example,@Param("page") PagePO page);

	int countByExample(@Param("example") Card example);

	Card selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Card record);
	
	int updateByCard(Card record);
	
	Map<String,String> selectCardScope(Card record);
	
	List<Card> selectByCardTypeId(Card record);
	
	int updateByExampleSelective(@Param("record")Card record,@Param("example") Card example);
	
	List<Card> cardByApplyId(Integer makeId);
	
	Map<String,String> statCardByAgentId(String agentId);
	
	BigDecimal statCardBySecAgentId(String agentId);
	
	Map<String,Object> statCardBySalerAgentId(String agentId);
	
	List<Card> selectByExample1(@Param("example") Card example,@Param("page") PagePO page);
	
	List<Card> selectByExample2(@Param("example") Card example,@Param("applyStatus")ApplyCardStatusEnum applyStatus,@Param("agengType")AgentTypeEnum agengType);
	
	Card statCardByUserId(String userId);
}