package com.magic.promotion.alipayRecords.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.alipayRecords.domain.AlipayRecords;
import com.magic.util.PagePO;

public interface AlipayRecordsMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(AlipayRecords record);

	List<AlipayRecords> selectByExample(@Param("example") AlipayRecords example,@Param("page") PagePO page);

	int countByExample(@Param("example") AlipayRecords example);

	AlipayRecords selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AlipayRecords record);
}