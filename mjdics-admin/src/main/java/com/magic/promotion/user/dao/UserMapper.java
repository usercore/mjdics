package com.magic.promotion.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.user.domain.User;
import com.magic.util.PagePO;

public interface UserMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	List<User> selectByExample(@Param("example") User example,@Param("page") PagePO page);

	int countByExample(@Param("example") User example);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);
	
	List<User> selectUserBySecAgent(@Param("agentId")String agentId,@Param("page") PagePO page);
	
	int countUserBySecAgent(String agentId);
}