package com.magic.promotion.recommendNum.dao;

import com.magic.promotion.recommendNum.domain.RecommendNum;
import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.util.PagePO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RecommendNumMapper {
    
    int countByExample(@Param("example")RecommendNum example);

    int deleteByPrimaryKey(Integer id);

    int insert(RecommendNum record);

    List<RecommendNum> selectByExample(@Param("example")RecommendNum example,@Param("page")PagePO page);

    int updateByPrimaryKeySelective(RecommendNum record);
    
    int updateStatusByPhone(@Param("phone")String phone, @Param("status")ActiveEnum status);
    
    RecommendNum selectByPrimaryKey(Integer id);
}