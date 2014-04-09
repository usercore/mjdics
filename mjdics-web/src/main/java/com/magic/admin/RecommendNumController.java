package com.magic.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magic.promotion.recommendNum.domain.RecommendNum;
import com.magic.promotion.recommendNum.service.RecommendNumServiceImpl;
import com.magic.promotion.sysParam.SysParamUtil;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.util.enumUtil.ActiveAndInvalidEnum;
import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.issue.domain.Issue;
import com.magic.promotion.issue.service.IssueServiceImpl;
import com.magic.promotion.priviledges.service.UserGroupServiceImpl;
import com.magic.util.GameUtil;
import com.magic.util.PagePO;

@Controller
@RequestMapping(value="admin/recommendNum")
@Scope("prototype")
public class RecommendNumController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(RecommendNumController.class);
	
	@Autowired
	RecommendNumServiceImpl recommendNumService;
	@Autowired
	UserGroupServiceImpl userGroupService;
	@Autowired
	IssueServiceImpl issueService;
	@Autowired
	SysParamServiceImpl sysParamService;
	List<RecommendNum> recommendNumList;
	
	@RequestMapping(value = "insertRecommendNum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertRecommendNum(HttpSession session, RecommendNum recommendNum) throws Exception {
		Map<String,String> map = new HashMap<String,String>();	
		Agent agentLogin = (Agent)session.getAttribute("agent");
		recommendNum.setAddTime(new Date());
		Issue issue = issueService.currnetIssue(recommendNum.getGame());
		if(null==issue){
			map.put("info", "没有当前期");
			map.put("status", "N");
			return map;
		}
		RecommendNum selRecommendNum = new RecommendNum();
		selRecommendNum.setIssue(issue.getIssue());
		selRecommendNum.setStatus(ActiveAndInvalidEnum.Active);
		int issueTotalCount = recommendNumService.countByExample(selRecommendNum);
		selRecommendNum.setAddPerson(agentLogin.getAgentId());
		int userTotalCount = recommendNumService.countByExample(selRecommendNum);
		
		String totalRecommendNumLimit = sysParamService.selectByName(SysParamUtil.TOTAL_RECOMMEND_NUM_LIMIT).getValue();
		String userRecommendNumLimit = sysParamService.selectByName(SysParamUtil.USER_RECOMMEND_NUM_LIMIT).getValue();
		
		if(Integer.parseInt(totalRecommendNumLimit)<=issueTotalCount){
			map.put("info", "专家号码数量已达系统上线！");
			map.put("status", "N");
			return map;
		}
		if(Integer.parseInt(userRecommendNumLimit)<=userTotalCount){
			map.put("info", "您发布的专家号已达个人上限！每个人最多可发"+userRecommendNumLimit+"个专家号");
			map.put("status", "N");
			return map;
		}
		
		recommendNum.setIssue(issue.getIssue());
		recommendNum.setStatus(ActiveAndInvalidEnum.Active);
		recommendNum.setAddPerson(agentLogin.getAgentId());
		recommendNumService.insert(recommendNum);
		map.put("info", "添加成功！");
		map.put("status", "Y");
		return map;
	}
	
	@RequestMapping(value = "gotoSelectRecommendNum")
	public String gotoSelectRecommendNum(HttpSession session,ModelMap map){
		map.put("activeAndInvalidEnum", ActiveAndInvalidEnum.values());
		map.put("game", GameUtil.gameMap);
		return "recommendNum/recommendNum";
	}
	
	@RequestMapping(value = "selectRecommendNum")
	@ResponseBody
	public Map<String,Object> selectrecommendNum(RecommendNum recommendNum,ModelMap map,HttpSession session,PagePO page){
		Agent agent = (Agent)session.getAttribute("agent");
		if(page==null){
			page = new PagePO();
			page.setCurrentPage(1);
		}
		recommendNum.setAddPerson(agent.getAgentId());
		Map<String,Object> mapData = new HashMap<String,Object> ();
		int totalCount = recommendNumService.countByExample(recommendNum);
		page.initPage(totalCount);
		recommendNumList = recommendNumService.selectByExample(recommendNum, page);
		mapData.put("total", totalCount);
		mapData.put("rows",  recommendNumList);
		return mapData;
		
	}
	@RequestMapping(value = "updateRecommendNum")
	@ResponseBody
	public 	String  updateRecommendNum(RecommendNum recommendNum ){
		logger.info("update recommendNum");
		recommendNumService.updateByPrimaryKeySelective(recommendNum);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteRecommendNum", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteRecommendNum(String id){
		recommendNumService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
}
