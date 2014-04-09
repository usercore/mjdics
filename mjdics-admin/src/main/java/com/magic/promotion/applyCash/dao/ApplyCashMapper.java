package com.magic.promotion.applyCash.dao;

import com.magic.promotion.applyCash.domain.ApplyCash;
import com.magic.util.PagePO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ApplyCashMapper {
    int countByExample(@Param("example")ApplyCash example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplyCash record);

    List<ApplyCash> selectByExample(@Param("example") ApplyCash example,@Param("page") PagePO page);

    ApplyCash selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplyCash record);

}