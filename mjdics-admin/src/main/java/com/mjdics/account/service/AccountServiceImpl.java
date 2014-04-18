package com.mjdics.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.util.PagePO;
import com.magic.promotion.util.enumUtil.TradeTypeEnum;
import com.mjdics.account.dao.AccountMapper;
import com.mjdics.account.domain.Account;
import com.mjdics.account.domain.AccountTrade;
import com.mjdics.account.domain.User;
import com.mjdics.sysManager.domain.TradeType;
import com.mjdics.sysManager.service.TradeTypeServiceImpl;

@Service("accountService")
public class AccountServiceImpl {
	
	@Autowired
	AccountMapper accountMapper;
	@Autowired
	AccountTradeServiceImpl accountTradeService;
	@Autowired
	TradeTypeServiceImpl tradeTypeService;
	
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
	
	public int updateAccountMoney(Account record) {
		return accountMapper.updateAccountMoney(record);
	}
	
	public Account selectByAccountId(String accountId){
		return accountMapper.selectByAccountId(accountId);
	}
	
	public boolean trade(User user,BigDecimal money,String typeId,String remark){
		
		TradeType tradeType = tradeTypeService.selectByTypeId(typeId);
		BigDecimal remainMoney = new BigDecimal(0);
		Account account = selectByAccountId(user.getAccountId());
		if(tradeType.getType()==TradeTypeEnum.INCOME){
			remainMoney = account.getMoney().add(money);
		}
		if(tradeType.getType()==TradeTypeEnum.OUTCOME){
			remainMoney = account.getMoney().subtract(money);
		}
		account.setMoney(remainMoney);
		account.setUpdateTime(new Date());
		updateAccountMoney(account);
		
		String serial = java.util.UUID.randomUUID().toString().replace("-", "");
		AccountTrade accountTrade = new AccountTrade();
		accountTrade.setAccountId(user.getAccountId());
		accountTrade.setRemainMoney(remainMoney);
		accountTrade.setMoney(money);
		accountTrade.setTypeId(typeId);
		accountTrade.setSerial(serial);
		accountTrade.setAddPerson(user.getUserId());
		accountTrade.setAddTime(new Date());
		accountTrade.setRemark(remark);
		accountTradeService.insert(accountTrade);
		return true;
	}

}
