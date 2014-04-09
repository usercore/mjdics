package com.magic.promotion.cardType.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.promotion.card.domain.Card;
import com.magic.promotion.card.service.CardServiceImpl;
import com.magic.promotion.cardType.dao.CardTypeMapper;
import com.magic.promotion.cardType.domain.CardType;
import com.magic.util.PagePO;

@Service("cardTypeService")
public class CardTypeServiceImpl  {
	@Autowired
	CardTypeMapper cardTypeMapper;
	@Autowired
	CardServiceImpl cardService;
	public int countByExample(CardType example){
    	return cardTypeMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return cardTypeMapper.deleteByPrimaryKey(id);
   }

    public int insert(CardType record){
    	return cardTypeMapper.insert(record);
    }

    public List<CardType> selectByExample(@Param("example") CardType example,@Param("page")PagePO page){
    	List<CardType> cardTypeList = cardTypeMapper.selectByExample(example, page);
    	return cardTypeList;
    }

    public int updateByPrimaryKeySelective(CardType record){
    	return cardTypeMapper.updateByPrimaryKeySelective(record);
    }


	public CardType selectByPrimaryKey(Integer id) {
		return cardTypeMapper.selectByPrimaryKey(id);
	}
	@Transactional
    public int update(CardType record){
	    Card c = new Card();
	    c.setName(record.getName());
	    List<Card> list = cardService.selectByExample(c, null);
    	for (int i = 0; i < list.size(); i++) {
			Card c1 = list.get(i);
			c1.setResourceId(record.getResourceId());
			cardService.updateByPrimaryKeySelective(c1);
		}
    	return updateByPrimaryKeySelective(record);
    }	
    
}
