package com.mjdics.sysManager.dao;

import com.magic.promotion.util.PagePO;
import com.mjdics.sysManager.domain.TradeType;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TradeTypeMapper {
    int countByExample(@Param("example")TradeType example);

    int deleteByPrimaryKey(Integer id);

    int insert(TradeType record);

    List<TradeType> selectByExample(@Param("example")TradeType example,@Param("page")PagePO page);

    TradeType selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(TradeType record);
}