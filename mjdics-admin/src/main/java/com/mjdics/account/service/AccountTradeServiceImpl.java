package com.mjdics.account.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.util.PagePO;
import com.mjdics.account.dao.AccountTradeMapper;
import com.mjdics.account.domain.AccountTrade;
import com.mjdics.sysManager.domain.TradeType;
import com.mjdics.sysManager.service.TradeTypeServiceImpl;

@Service("accountTradeService")
public class AccountTradeServiceImpl {
	@Autowired
	AccountTradeMapper accountTradeMapper;
	@Autowired
	TradeTypeServiceImpl tradeTypeService;
	
	SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-mm-dd 00:00:00");
	SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-mm-dd 23:59:59");
	
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
		List<AccountTrade> accountTradeList = accountTradeMapper.selectByExample(example, page);
		initListData(accountTradeList);
		return accountTradeList;
	}

	public AccountTrade selectByPrimaryKey(Integer id) {
		return accountTradeMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(AccountTrade record) {
		return accountTradeMapper.updateByPrimaryKey(record);
	}
	
	public List<AccountTrade>  statTradeByTime(AccountTrade accountTrade){
		if(accountTrade.getStartTime()!=null&&!accountTrade.getStartTime().equals("")){
			accountTrade.setStartTime(accountTrade.getStartTime()+" 00:00:00");
		}
		if(accountTrade.getEndTime()!=null&&!accountTrade.getEndTime().equals("")){
			accountTrade.setEndTime(accountTrade.getEndTime()+" 23:59:59");
		}
		
		List<AccountTrade> accountTradeList =  accountTradeMapper.statTradeByTime(accountTrade);
		initListData(accountTradeList);
		return accountTradeList;
	}

	private void initListData(List<AccountTrade> accountTradeList ){
		for(int i=0;i<accountTradeList.size();i++){
			AccountTrade accountTrade = accountTradeList.get(i);
			TradeType tradeType = tradeTypeService.selectByTypeId(accountTrade.getTypeId());
			accountTrade.setType(tradeType.getType());
			accountTrade.setTypeName(tradeType.getName());
		}
	}
}
