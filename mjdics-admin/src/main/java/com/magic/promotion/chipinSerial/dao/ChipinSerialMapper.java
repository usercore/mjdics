package com.magic.promotion.chipinSerial.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.chipinSerial.domain.ChipinSerial;
import com.magic.util.PagePO;

public interface ChipinSerialMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(ChipinSerial record);

	List<ChipinSerial> selectByExample(@Param("example") ChipinSerial example,@Param("page") PagePO page);

	int countByExample(@Param("example") ChipinSerial example);

	ChipinSerial selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ChipinSerial record);
}