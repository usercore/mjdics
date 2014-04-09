package com.magic.promotion.sysParam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.sysParam.domain.SysParam;
import com.magic.util.PagePO;

public interface SysParamMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(SysParam record);

	List<SysParam> selectByExample(@Param("example") SysParam example,@Param("page") PagePO page);

	int countByExample(@Param("example") SysParam example);

	SysParam selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysParam record);
	
	SysParam selectByName(String name);
	
	int updateByName(SysParam record);
}