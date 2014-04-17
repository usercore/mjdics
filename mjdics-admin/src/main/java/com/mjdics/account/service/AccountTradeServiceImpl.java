package com.mjdics.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.util.PagePO;
import com.mjdics.account.dao.AccountTradeMapper;
import com.mjdics.account.domain.AccountTrade;

@Service("accountTradeService")
public class AccountTradeServiceImpl {
	@Autowired
	AccountTradeMapper accountTradeMapper;
	
	public int countByExample(AccountTrade example) {
		return accountTradeMapper.countByExample(example);
	}

	public int deleteByPrimaryKey(Integer id) {
		return accountTradeMapper.deleteByPrimaryKey(id);
	}

	public int insert(AccountTrade record) {
		return accountTradeMapper.insert(record);
	}

	public List<AccountTrade> selectByExample(AccountTrade example, PagePO page) {
		return accountTradeMapper.selectByExample(example, page);
	}

	public AccountTrade selectByPrimaryKey(Integer id) {
		return accountTradeMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(AccountTrade record) {
		return accountTradeMapper.updateByPrimaryKey(record);
	}

}
