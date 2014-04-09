package com.magic.promotion.priviledges.dao;

import com.magic.promotion.priviledges.domain.UserGroup;
import com.magic.util.PagePO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserGroupMapper {
	   int countByExample(@Param("example")UserGroup example);

	    int deleteByPrimaryKey(Integer id);

	    int insert(UserGroup record);

	    List<UserGroup> selectByExample(@Param("example") UserGroup example,@Param("page") PagePO page);

	    UserGroup selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(UserGroup record);
}