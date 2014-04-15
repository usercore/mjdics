package com.mjdics.account.dao;

import com.mjdics.account.domain.AccountTrade;
import com.mjdics.account.domain.AccountTradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountTradeMapper {
    int countByExample(AccountTradeExample example);

    int deleteByExample(AccountTradeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountTrade record);

    int insertSelective(AccountTrade record);

    List<AccountTrade> selectByExample(AccountTradeExample example);

    AccountTrade selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountTrade record, @Param("example") AccountTradeExample example);

    int updateByExample(@Param("record") AccountTrade record, @Param("example") AccountTradeExample example);

    int updateByPrimaryKeySelective(AccountTrade record);

    int updateByPrimaryKey(AccountTrade record);
}