package com.mjdics.sysManager.dao;

import com.mjdics.sysManager.domain.TradeType;
import com.mjdics.sysManager.domain.TradeTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradeTypeMapper {
    int countByExample(TradeTypeExample example);

    int deleteByExample(TradeTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TradeType record);

    int insertSelective(TradeType record);

    List<TradeType> selectByExample(TradeTypeExample example);

    TradeType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TradeType record, @Param("example") TradeTypeExample example);

    int updateByExample(@Param("record") TradeType record, @Param("example") TradeTypeExample example);

    int updateByPrimaryKeySelective(TradeType record);

    int updateByPrimaryKey(TradeType record);
}