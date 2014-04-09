package com.magic.admin;


import java.text.SimpleDateFormat;
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

import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.issue.domain.Issue;
import com.magic.promotion.issue.service.IssueServiceImpl;
import com.magic.promotion.util.enumUtil.IssueStatusEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/issue")
@Scope("prototype")
public class IssueController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(IssueController.class);
	
	@Autowired
	IssueServiceImpl issueService;
	List<Issue> issueList;
	
	@RequestMapping(value = "insertIssue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertIssue(HttpSession session, Issue issue) {
		Map<String,String> map = new HashMap<String,String>();	
		Agent agentLogin = (Agent)session.getAttribute("agent");
		issue.setAddTime(new Date());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		issue.setStatus(IssueStatusEnum.SELL);
		issueService.insert(issue);
		msg = "添加成功！"; 
		map.put("info", msg);
		map.put("status", "Y");
		return map;	
	}
	
	
	@RequestMapping(value = "isExistIssue")
	@ResponseBody
	public String isExistIssue(Issue issue,String param){
		msg="y";
		issueList = (List<Issue>) issueService.selectByExample(issue, null);
		if (null != issueList && issueList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectIssue")
	public String gotoSelectIssue(ModelMap map){
		map.put("issueStatusEnum", IssueStatusEnum.values());
		return "issue/issue";
	}	
	@RequestMapping(value = "selectIssue")
	@ResponseBody
	public Map<String,Object> selectissue(Issue issue,ModelMap map,PagePO pagePo){
		Map<String,Object> mapData = new HashMap<String,Object>();
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = issueService.countByExample(issue);
		pagePo.initPage(totalCount);
		issueList = issueService.selectByExample(issue, pagePo);
		mapData.put("total", totalCount);
		mapData.put("rows",  issueList);
		return mapData;
	}	
	@RequestMapping(value = "updateIssue", method = RequestMethod.POST)
	@ResponseBody
	public 	Map<String,String>  updateIssue(Issue issue ){
		Map<String,String> map = new HashMap<String,String>();	
		logger.info("update issue");
		issueService.updateByPrimaryKeySelective(issue);
		msg = "修改成功！";
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value = "deleteIssue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String>  deleteIssue(String id){
		Map<String,String> map = new HashMap<String,String>();	
		issueService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		map.put("msg", msg);
		return map;
	}
}
