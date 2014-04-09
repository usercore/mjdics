package com.magic.promotion.initUser.dao;

import com.magic.promotion.initUser.domain.InitUser;
import com.magic.promotion.util.enumUtil.ActiveEnum;
import com.magic.util.PagePO;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

public interface InitUserMapper {
    int countByExample(@Param("example")InitUser example);

    int deleteByPrimaryKey(Integer id);

    int insert(InitUser record);

    List<InitUser> selectByExample(@Param("example")InitUser example,@Param("page")PagePO page);

    int updateByPrimaryKeySelective(InitUser record);
    
    int updateStatusByPhone(@Param("phone")String phone, @Param("status")ActiveEnum status);
    
    InitUser selectByPrimaryKey(Integer id);

}