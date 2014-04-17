package com.mjdics.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.util.PagePO;
import com.mjdics.account.dao.AccountMapper;
import com.mjdics.account.domain.Account;

@Service("accountService")
public class AccountServiceImpl {
	
	@Autowired
	AccountMapper accountMapper;
	
	public int countByExample(Account example) {
		return accountMapper.countByExample(example);
	}

	public int deleteByPrimaryKey(Integer id) {
		return accountMapper.deleteByPrimaryKey(id);
	}

	public int insert(Account record) {
		return accountMapper.insert(record);
	}

	public List<Account> selectByExample(Account example, PagePO page) {
		return accountMapper.selectByExample(example, page);
	}

	public Account selectByPrimaryKey(Integer id) {
		return accountMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(Account record) {
		return accountMapper.updateByPrimaryKey(record);
	}

}
