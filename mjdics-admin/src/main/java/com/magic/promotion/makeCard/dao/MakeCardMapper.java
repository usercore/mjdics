package com.magic.promotion.makeCard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.makeCard.domain.MakeCard;
import com.magic.util.PagePO;

public interface MakeCardMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(MakeCard record);
	
	int insertSelective(MakeCard record);

	List<MakeCard> selectByExample(@Param("example") MakeCard example,@Param("page") PagePO page);

	int countByExample(@Param("example") MakeCard example);

	MakeCard selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(MakeCard record);
}