package com.magic.promotion.agent.dao;

import com.magic.promotion.agent.domain.Agent;
import com.magic.util.PagePO;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AgentMapper {
	
	int deleteByPrimaryKey(Integer id);

	int insert(Agent record);

	List<Agent> selectByExample(@Param("example") Agent example,@Param("page") PagePO page);

	int countByExample(@Param("example") Agent example);

	Agent selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Agent record);
	
	Agent selectByAgentId(String agentId);
	
	List<Agent> selectSalerByFirAgentId(@Param("agentId") String agentId,@Param("page") PagePO page);
	
	int countSalerByFirAgentId(@Param("agentId") String agentId);

}