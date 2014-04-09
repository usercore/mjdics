package com.magic.promotion.applyCard.dao;

import com.magic.promotion.applyCard.domain.ApplyCard;
import com.magic.util.PagePO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ApplyCardMapper {
	
	int deleteByPrimaryKey(Integer id);

	int insert(ApplyCard record);

	List<ApplyCard> selectByExample(@Param("example") ApplyCard example,@Param("page") PagePO page);

	int countByExample(@Param("example") ApplyCard example);

	ApplyCard selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ApplyCard record);

}