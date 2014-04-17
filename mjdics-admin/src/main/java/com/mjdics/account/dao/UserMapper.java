package com.mjdics.account.dao;

import com.magic.promotion.util.PagePO;
import com.mjdics.account.domain.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByExample(@Param("example")User example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    List<User> selectByExample(@Param("example")User example,@Param("page")PagePO page);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(User record);
}