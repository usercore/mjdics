package com.magic.promotion.cardType.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.magic.promotion.cardType.domain.CardType;
import com.magic.util.PagePO;

public interface CardTypeMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(CardType record);

	List<CardType> selectByExample(@Param("example") CardType example,@Param("page") PagePO page);

	int countByExample(@Param("example") CardType example);

	CardType selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CardType record);
}