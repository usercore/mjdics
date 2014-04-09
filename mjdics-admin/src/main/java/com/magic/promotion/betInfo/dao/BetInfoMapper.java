package com.magic.promotion.betInfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.betInfo.domain.BetInfo;
import com.magic.util.PagePO;

public interface BetInfoMapper {
	
	int deleteByPrimaryKey(Integer id);

	int insert(BetInfo record);

	List<BetInfo> selectByExample(@Param("example") BetInfo example,@Param("page") PagePO page);

	int countByExample(@Param("example")BetInfo example);

	BetInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(BetInfo record);
	
	List<BetInfo> betInfoByIssue(@Param("example") BetInfo example);
	
	int updateByExampleSelective(@Param("record") BetInfo record, @Param("example") BetInfo example);
	
}