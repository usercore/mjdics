package com.magic.promotion.resource.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.resource.domain.Resource;
import com.magic.util.PagePO;

public interface ResourceMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Resource record);

	List<Resource> selectByExample(@Param("example") Resource example,@Param("page") PagePO page);

	int countByExample(@Param("example") Resource example);

	Resource selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Resource record);
}