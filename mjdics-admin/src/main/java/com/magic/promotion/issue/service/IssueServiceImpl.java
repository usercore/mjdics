package com.magic.promotion.issue.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magic.lottery.ws.SelfServiceWebService;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.issue.dao.IssueMapper;
import com.magic.promotion.issue.domain.Issue;
import com.magic.promotion.sysParam.SysParamUtil;
import com.magic.promotion.sysParam.domain.SysParam;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.util.DateUtil;
import com.magic.promotion.util.enumUtil.IssueStatusEnum;
import com.magic.util.PagePO;


@Service("issueService")
public class IssueServiceImpl  {
	@Autowired
	IssueMapper issueMapper;
	@Autowired
	SelfServiceWebService selfServiceWebService;	
	@Autowired
	SysParamServiceImpl sysParamService;	
	String gameCode;
	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public int countByExample(Issue example){
    	return issueMapper.countByExample(example);
    }

   public  int deleteByPrimaryKey(Integer id){
	   return issueMapper.deleteByPrimaryKey(id);
   }

    public int insert(Issue record){
    	return issueMapper.insert(record);
    }

    public List<Issue> selectByExample(@Param("example") Issue example,@Param("page")PagePO page){
    	List<Issue> issueList = issueMapper.selectByExample(example, page);
    	return issueList;
    }

    public int updateByPrimaryKeySelective(Issue record){
    	return issueMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByExampleSelective(Issue record,Issue example){
    	return issueMapper.updateByExampleSelective(record, example);
    }    
    
	public Issue selectByPrimaryKey(Integer id) {
		return issueMapper.selectByPrimaryKey(id);
	}
	
	public Issue currnetIssue(String game) {
		 SysParam sysParam = sysParamService.selectByName(SysParamUtil.ISSUE_BEFORE_TIME);
		 Issue issue = issueMapper.currnetIssue(game, new Date());
		 if(issue!=null){
			 Date d = DateUtil.getPreTime(new Date(),sysParam.getValue());
			 if(issue.getEndTime().before(d)){
				 issue = null;
			 }
		 }
		return issue;
	}
	
	public List<Issue> curDateBeforeIssue(String game,IssueStatusEnum status) {
		Issue record = new Issue();
		record.setGame(game);
		record.setStatus(IssueStatusEnum.SELL);
		return issueMapper.curDateBeforeIssue(record, new Date());
	}	
	
	public void syncIssue() {
		
		Map<String,String> map = selfServiceWebService.getCurrentIssue(Integer.parseInt(gameCode));
		if(map.get("errorCode")!=null&&map.get("errorCode").equals("0000")){
			String issue = map.get("issue");
			String beginTime = map.get("beginTime");
			String endTime = map.get("endTime");
			String status = map.get("status");
			String remainsTime = map.get("remainsTime");
			String awardTime = map.get("awardTime");

			Issue example = new Issue();
			example.setGame(gameCode);
			example.setIssue(issue);
			List list = selectByExample(example,null);
			if(list==null||list.size()==0){
				Issue record = new Issue();
				record.setGame(gameCode);
				record.setIssue(issue);
				record.setAddTime(new Date());
				record.setStartTime(DateUtil.getFormatDate(beginTime, "yyyy-MM-dd HH:mm:ss"));
				record.setEndTime(DateUtil.getFormatDate(endTime, "yyyy-MM-dd HH:mm:ss"));
				record.setAwardTime(DateUtil.getFormatDate(awardTime, "yyyy-MM-dd HH:mm:ss"));
				record.setStatus(IssueStatusEnum.SELL);
				insert(record);
			}else if(list.size()==1){
				//updateByExampleSelective(record, example);
			}
		}

	}
    
}
