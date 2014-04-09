package com.magic.promotion.makeCard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.promotion.applyCard.domain.ApplyCard;
import com.magic.promotion.applyCard.service.ApplyCardServiceImpl;
import com.magic.promotion.card.domain.Card;
import com.magic.promotion.card.service.CardServiceImpl;
import com.magic.promotion.makeCard.dao.MakeCardMapper;
import com.magic.promotion.makeCard.domain.MakeCard;
import com.magic.promotion.util.enumUtil.ApplyCardStatusEnum;
import com.magic.promotion.util.enumUtil.CardEnum;
import com.magic.promotion.util.enumUtil.MakeCardStatusEnum;
import com.magic.util.PagePO;


@Service("makeCardService")
public class MakecardServiceImpl  {
	@Autowired
	MakeCardMapper makeCardMapper;
	@Autowired
	CardServiceImpl cardService;	
	@Autowired
	ApplyCardServiceImpl applyCardService;
	public int countByExample(MakeCard example){
    	return makeCardMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return makeCardMapper.deleteByPrimaryKey(id);
   }

    public int insert(MakeCard record){
    	return makeCardMapper.insert(record);
    }


    public List<MakeCard> selectByExample(@Param("example") MakeCard example,@Param("page")PagePO page){
    	List<MakeCard> makeCardList = makeCardMapper.selectByExample(example, page);
    	return makeCardList;
    }

    public int updateByPrimaryKeySelective(MakeCard record){
    	return makeCardMapper.updateByPrimaryKeySelective(record);
    }
    
    public int updateMakeCard(MakeCard record){
    	record.setStatus(MakeCardStatusEnum.MAKE_SUCCESS);
    	makeCardMapper.updateByPrimaryKeySelective(record);
    	
    	Card card = new Card();
    	card.setStatus(CardEnum.fulCard);
    	
    	Card example= new Card();
    	example.setMakeId(record.getId());
    	cardService.updateByExampleSelective(card, example);
    	
    	List<Card> cl = cardService.cardByApplyId(record.getId());
    	for (int i = 0; i < cl.size(); i++) {
    		Card c = cl.get(i);
        	ApplyCard ac = new ApplyCard();
        	ac.setStatus(ApplyCardStatusEnum.MAKE_SUCCESS);
        	ac.setId(c.getApplyId());
        	applyCardService.updateByPrimaryKeySelective(ac);			
		}
		return 1;
    }    


	public MakeCard selectByPrimaryKey(Integer id) {
		return makeCardMapper.selectByPrimaryKey(id);
	}
    @Transactional
	public synchronized int makeCard(MakeCard makeCard) {

		insert(makeCard);
		
		Card c = new Card();
		c.setStatus(CardEnum.EntityCard);
		c.setMakeId(makeCard.getId());
		
		Card example = new Card();
		example.setStatus(CardEnum.AppCard);
//		example.setNum(makeCard.getAmount());
		
		return cardService.updateByExampleSelective(c, example);
		
	}
	

    
}
