package com.magic.promotion.agentTrade.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.agentTrade.dao.AgentTradeMapper;
import com.magic.promotion.agentTrade.domain.AgentTrade;
import com.magic.util.PagePO;

@Service("agentTradeService")
public class AgentTradeServiceImpl  {
	@Autowired
	AgentTradeMapper agentTradeMapper;
	public int countByExample(AgentTrade example){
    	return agentTradeMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return agentTradeMapper.deleteByPrimaryKey(id);
   }

    public int insert(AgentTrade record){
    	return agentTradeMapper.insert(record);
    }


    public List<AgentTrade> selectByExample(@Param("example") AgentTrade example,@Param("page")PagePO page){
    	List<AgentTrade> agentTradeList = agentTradeMapper.selectByExample(example, page);
    	return agentTradeList;
    }

    public int updateByPrimaryKeySelective(AgentTrade record){
    	return agentTradeMapper.updateByPrimaryKeySelective(record);
    }


	public AgentTrade selectByPrimaryKey(Integer id) {
		return agentTradeMapper.selectByPrimaryKey(id);
	}
	public int countSendMessage(AgentTrade record){
	    return agentTradeMapper.countSendMessage(record);
	}
}
