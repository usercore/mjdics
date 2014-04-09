package com.magic.promotion.agentTrade.dao;

import com.magic.promotion.agentTrade.domain.AgentTrade;
import com.magic.util.PagePO;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AgentTradeMapper {

	@Delete(" delete from agent_trade where ID = #{id}")
	int deleteByPrimaryKey(Integer id);

	int insert(AgentTrade record);

	List<AgentTrade> selectByExample(@Param("example") AgentTrade example,@Param("page") PagePO page);

	int countByExample(@Param("example") AgentTrade example);

	@Select(" select * from agent_trade where ID = #{id}")
	AgentTrade selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AgentTrade record);
	
	int countSendMessage(AgentTrade record);

}