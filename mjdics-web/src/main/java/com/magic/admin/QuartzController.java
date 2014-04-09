package com.magic.admin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magic.promotion.card.domain.Card;
import com.magic.promotion.quartz.service.QuartzServiceImpl;
import com.magic.promotion.util.enumUtil.SysParamStatusEnum;


@Controller
@RequestMapping(value="admin/quartz")
@Scope("prototype")
public class QuartzController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(QuartzController.class);
	
	@Autowired
	private QuartzServiceImpl quartzService;
	@RequestMapping(value = "gotoSelectQuartz")
	public String gotoSelectSysParam(ModelMap map){
		map.put("sysParamStatusEnum", SysParamStatusEnum.values());
		return "quartz/quartz";
	}
	@RequestMapping(value = "start", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> start() {
		Map<String,String> map = new HashMap<String,String>();
		boolean r = quartzService.start();
		msg = r ? "定时器启动成功" : "定时器启动失败";
	    map.put("msg", msg);
		return map;
	}
	@RequestMapping(value = "stop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> stop() {
		Map<String,String> map = new HashMap<String,String>();
		boolean r = quartzService.stop();
		msg = r ? "定时器停止成功" : "定时器停止失败";
	    map.put("msg", msg);
		return map;
	}
	@RequestMapping(value = "isRunning")
	@ResponseBody
	public Map<String,Object> isRunning() {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> map1 = new HashMap<String,String>();
		boolean r = quartzService.isRunning();
		msg = r ? " 定时器在运行中" : "定时器没有运行";
	    map1.put("msg", msg);
	    List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	    list.add(map1);
	    map.put("rows", list);
		return map;
	}	

	
}
