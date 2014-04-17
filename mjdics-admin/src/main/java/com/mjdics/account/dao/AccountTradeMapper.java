package com.mjdics.account.dao;

import com.magic.promotion.util.PagePO;
import com.mjdics.account.domain.AccountTrade;
import com.mjdics.account.domain.AccountTrade;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AccountTradeMapper {
    int countByExample(@Param("example")AccountTrade example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountTrade record);

    List<AccountTrade> selectByExample(@Param("example")AccountTrade example,@Param("page")PagePO page);

    AccountTrade selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(AccountTrade record);
}