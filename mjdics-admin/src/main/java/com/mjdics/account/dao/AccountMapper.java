package com.mjdics.account.dao;

import com.magic.promotion.util.PagePO;
import com.mjdics.account.domain.Account;
import com.mjdics.account.domain.Account;
import com.mjdics.account.domain.AccountTrade;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    int countByExample(@Param("example")Account example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    List<Account> selectByExample(@Param("example") Account example,@Param("page")PagePO page);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Account record);

}