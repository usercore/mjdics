package com.mjdics.sysManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.promotion.util.PagePO;
import com.mjdics.sysManager.dao.TradeTypeMapper;
import com.mjdics.sysManager.domain.TradeType;
@Service("tradeTypeService")
public class TradeTypeServiceImpl {
	@Autowired
	TradeTypeMapper tradeTypeMapper;
	
	public int countByExample(TradeType example) {
		return tradeTypeMapper.countByExample(example);
	}

	public int deleteByPrimaryKey(Integer id) {
		return tradeTypeMapper.deleteByPrimaryKey(id);
	}

	public int insert(TradeType record) {
		return tradeTypeMapper.insert(record);
	}

	public List<TradeType> selectByExample(TradeType example, PagePO page) {
		return tradeTypeMapper.selectByExample(example, page);
	}

	public TradeType selectByPrimaryKey(Integer id) {
		return tradeTypeMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(TradeType record) {
		return tradeTypeMapper.updateByPrimaryKey(record);
	}

	public TradeType selectByTypeId(String typeId) {
		return tradeTypeMapper.selectByTypeId(typeId);
	}

}
