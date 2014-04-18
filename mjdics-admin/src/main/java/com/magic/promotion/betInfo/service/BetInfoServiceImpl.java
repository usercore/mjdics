package com.magic.promotion.betInfo.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.lottery.ws.SelfServiceWebService;
import com.magic.promotion.betInfo.dao.BetInfoMapper;
import com.magic.promotion.betInfo.domain.BetInfo;
import com.magic.promotion.card.domain.Card;
import com.magic.promotion.card.service.CardServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.issue.domain.Issue;
import com.magic.promotion.issue.service.IssueServiceImpl;
import com.magic.promotion.user.domain.User1;
import com.magic.promotion.util.enumUtil.BetStatusEnum;
import com.magic.promotion.util.enumUtil.CardEnum;
import com.magic.promotion.util.enumUtil.IssueStatusEnum;
import com.magic.promotion.util.enumUtil.PrizeStatusEnum;
import com.magic.util.PagePO;
@Service("betInfoService")
public class BetInfoServiceImpl  {
	private static final Logger logger = LoggerFactory.getLogger(BetInfoServiceImpl.class);
	@Autowired
	BetInfoMapper betInfoMapper;
	@Autowired
	CardServiceImpl cardService;
	@Autowired
	SelfServiceWebService selfServiceWebService;
	@Autowired
	IssueServiceImpl issueService;	
	String gameCode;
	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public int countByExample(BetInfo example){
    	return betInfoMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return betInfoMapper.deleteByPrimaryKey(id);
   }

    public int insert(BetInfo record){
    	return betInfoMapper.insert(record);
    }


    public List<BetInfo> selectByExample(@Param("example") BetInfo example,@Param("page")PagePO page){
    	List<BetInfo> betInfoList = betInfoMapper.selectByExample(example, page);
    	return betInfoList;
    }

    public int updateByPrimaryKeySelective(BetInfo record){
    	return betInfoMapper.updateByPrimaryKeySelective(record);
    }


	public BetInfo selectByPrimaryKey(Integer id) {
		return betInfoMapper.selectByPrimaryKey(id);
	}
	
	public BetInfo lotteryCustom(User1 user,BetInfo betInfo) {
		Issue issue = issueService.currnetIssue(betInfo.getGame());	
		if(issue==null){
			throw new BusinessException("已过系统投注时间！");
		}
		Card example = new Card();
		example.setUserId(user.getPhone());
		example.setStatus(CardEnum.Sell);
		example.setCardId(betInfo.getCardId());
		List<Card> list = cardService.selectByExample(example, null);
		if(list!=null&&list.size()>0){
			Card c = list.get(0);
			c.setNums(betInfo.getNum());
			c.setGameCode(betInfo.getGame());
			c.setIssue(issue.getIssue());
			return bet(c);
		}else{
			throw new BusinessException("此卡投注已送完！");
		}
		
	}
	
	public void selectByBet() {
		logger.debug("============selectByBet===============");
		Card example = new Card();
		example.setStatus(CardEnum.Sell);
		List<Card> list = cardService.selectByExample(example, null);	
		for (int i = 0; i < list.size(); i++) {
			Card c = list.get(i);
			bet(c);
		}
	}
	
	@Transactional
	public BetInfo bet(Card c) {
		logger.debug("============bet==============="); 
		
		    BetInfo betInfo = new BetInfo();
		    betInfo.setCardId(c.getCardId());
		    betInfo.setUserId(c.getUserId());   
		    betInfo.setAgencyId(c.getAgentId());
//		    betInfo.setHotlineTime(new Date());
		    betInfo.setAddTime(new Date());
		    betInfo.setStatus(BetStatusEnum.NO_PRIZE);
		    betInfo.setPrizeStatus(PrizeStatusEnum.UNKNOW_PRIZE);
		    
			betInfo.setGame(c.getGameCode());
			//betInfo.setManner();
			betInfo.setIssue(c.getIssue());
			betInfo.setMoney(c.getLotteryMoney());
			betInfo.setMutiple(1);
			betInfo.setNum(c.getNums());		    
		    
		    insert(betInfo);
		    if(c.getNums()!=null&&!(c.getNums()).equals("")){
		    	String lotteryData = c.getNums();//+"|"+((c.getLotteryMoney()).multiply(new BigDecimal(100))).intValue();
		    	betInfo = selfServiceWebService.getLotteryCustom(c.getUserId(), lotteryData, Integer.parseInt(c.getGameCode()), ((c.getLotteryMoney()).multiply(new BigDecimal(100))).intValue(), betInfo.getId()+"", betInfo);
		    }else{
			    betInfo = selfServiceWebService.getLottery(c.getUserId(),((c.getLotteryMoney()).multiply(new BigDecimal(100))).intValue(), 1, betInfo.getId()+"",betInfo);
		    }
            if(null==(betInfo.getErrorCode())){
            	betInfo.setStatus(BetStatusEnum.NO_RESULT);
            }else{
	            if((betInfo.getErrorCode()).equals("0000")){
	            	betInfo.setStatus(BetStatusEnum.BET_SUCC);
	    			c.setBuyCount(c.getBuyCount()+1);
	    			if(c.getBetCount()-c.getBuyCount()<=0){
	    				c.setStatus(CardEnum.Used);
	    			}			
	    			cardService.updateByPrimaryKeySelective(c);            	
	            }else{
	            	betInfo.setStatus(BetStatusEnum.BET_FAIL);
	            }
            }
            betInfo.setHotlineTime(new Date());
            updateByPrimaryKeySelective(betInfo);
		logger.debug("============bet end===============");
		return betInfo;
	}
	
	public void selectByAwardInfo() {
		logger.debug("============selectByAwardInfo===============");
//		BetInfo example = new BetInfo();
//		example.setStatus(BetStatusEnum.BET_SUCC);
//		example.setPrizeStatus(PrizeStatusEnum.UNKNOW_PRIZE);
//		List<BetInfo> list = betInfoMapper.betInfoByIssue(example);
//		for (int i = 0; i < list.size(); i++) {
//			BetInfo b = list.get(i);
//			System.out.println(b.getIssue());
//			getAwardInfo(b.getIssue());
//		}
		
		List<Issue> list = issueService.curDateBeforeIssue(gameCode,IssueStatusEnum.SELL);
		for (int i = 0; i < list.size(); i++) {
			Issue issue = list.get(i);
			Map<String, String> map =selfServiceWebService.getIssueStatus(Integer.parseInt(gameCode), issue.getIssue());
			if(map.get("errorCode")!=null&&map.get("errorCode").equals("0000")&&map.get("status").equals(""+IssueStatusEnum.SentAwardL.getIndex())){
				getAwardInfo(issue.getIssue());
			}
		}
	}
	@Transactional
	public void getAwardInfo(String issue){
		Map<String,Object> map =selfServiceWebService.getAwardInfo(issue);
		String code = (String) map.get("errorCode");
		if(null!=code&&code.equals("0000")){
			List list = (List) map.get("list");
			for (int i = 0; i < list.size(); i++) {
				BetInfo betInfo = (BetInfo) list.get(i);
				betInfo.setPrizeStatus(PrizeStatusEnum.PRIZE);
				
				betInfo.setPrize((betInfo.getPrize().divide(new BigDecimal("100"))));
				BetInfo example = new BetInfo();
				example.setGame(betInfo.getGame());
				example.setIssue(betInfo.getIssue());
				example.setHotlineId(betInfo.getHotlineId());
				System.out.println("issue-->"+betInfo.getIssue()+"---"+betInfo.getHotlineId());
				betInfoMapper.updateByExampleSelective(betInfo,example);
				
				List<BetInfo> lb = betInfoMapper.selectByExample(example, null);
				Card c = new Card();
				Card ex = new Card();
				c.setPrizeCount(1);
				ex.setCardId(lb.get(0).getCardId());
				cardService.updateByExampleSelective(c, ex);
				
			}
			BetInfo example1 = new BetInfo();
			example1.setPrizeStatus(PrizeStatusEnum.UNKNOW_PRIZE);
			example1.setStatus(BetStatusEnum.BET_SUCC);
			BetInfo betInfo1 = new BetInfo();
			betInfo1.setPrizeStatus(PrizeStatusEnum.NOT_PRIZE);
			betInfoMapper.updateByExampleSelective(betInfo1,example1);
			
			Issue record = new Issue();
			record.setStatus(IssueStatusEnum.SentAwardL);
			Issue example = new Issue();
			example.setGame(gameCode);
			example.setIssue(issue);
			issueService.updateByExampleSelective(record, example);
		}
	}
}
