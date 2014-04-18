package com.magic.promotion.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.user.domain.User1;
import com.magic.util.PagePO;

public interface UserMapper1 {

	int deleteByPrimaryKey(Integer id);

	int insert(User1 record);

	List<User1> selectByExample(@Param("example") User1 example,@Param("page") PagePO page);

	int countByExample(@Param("example") User1 example);

	User1 selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User1 record);
	
	List<User1> selectUserBySecAgent(@Param("agentId")String agentId,@Param("page") PagePO page);
	
	int countUserBySecAgent(String agentId);
}