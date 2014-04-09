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
import com.magic.promotion.sysParam.domain.SysParam;
import com.magic.promotion.sysParam.service.SysParamServiceImpl;
import com.magic.promotion.util.enumUtil.SysParamStatusEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/sysParam")
@Scope("prototype")
public class SysParamController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(SysParamController.class);
	
	@Autowired
	SysParamServiceImpl sysParamService;
	List<SysParam> sysParamList;
	
	@RequestMapping(value = "insertSysParam", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertSysParam(HttpSession session, SysParam sysParam) {
		Map<String,String> map = new HashMap<String,String>();	
		Agent agentLogin = (Agent)session.getAttribute("agent");
		sysParam.setAddTime(new Date());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		sysParam.setAddPersion(agentLogin.getAgentId());
		sysParam.setStatus(SysParamStatusEnum.Active);
		sysParamService.insert(sysParam);
		msg = "添加成功！"; 
		map.put("info", msg);
		map.put("status", "Y");
		return map;	
	}
	
	
	@RequestMapping(value = "isExistSysParam")
	@ResponseBody
	public String isExistSysParam(SysParam sysParam,String param){
		msg="y";
		sysParam.setName(param);
		sysParamList = (List<SysParam>) sysParamService.selectByExample(sysParam, null);
		if (null != sysParamList && sysParamList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectSysParam")
	public String gotoSelectSysParam(ModelMap map){
		map.put("sysParamStatusEnum", SysParamStatusEnum.values());
		return "sysParam/sysParam";
	}	
	@RequestMapping(value = "selectSysParam")
	@ResponseBody
	public Map<String,Object> selectsysParam(SysParam sysParam,ModelMap map,PagePO pagePo){
		Map<String,Object> mapData = new HashMap<String,Object>();
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = sysParamService.countByExample(sysParam);
		pagePo.initPage(totalCount);
		sysParamList = sysParamService.selectByExample(sysParam, pagePo);
		mapData.put("total", totalCount);
		mapData.put("rows",  sysParamList);
		return mapData;
	}	
	@RequestMapping(value = "updateSysParam", method = RequestMethod.POST)
	@ResponseBody
	public 	Map<String,String>  updateSysParam(SysParam sysParam ){
		Map<String,String> map = new HashMap<String,String>();	
		logger.info("update sysParam");
		sysParamService.updateByPrimaryKeySelective(sysParam);
		msg = "修改成功！";
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value = "deleteSysParam", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String>  deleteSysParam(String id){
		Map<String,String> map = new HashMap<String,String>();	
		sysParamService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		map.put("msg", msg);
		return map;
	}
}
