package com.magic.admin;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.magic.promotion.betInfo.domain.BetInfo;
import com.magic.promotion.betInfo.service.BetInfoServiceImpl;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/betInfo")
@Scope("prototype")
public class BetInfoController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(BetInfoController.class);
	
	@Autowired
	BetInfoServiceImpl betInfoService;
	List<BetInfo> betInfoList;
	
	@RequestMapping(value = "insertBetInfo", method = RequestMethod.POST)
	@ResponseBody
	public String insertBetInfo(HttpSession session, BetInfo betInfo,String firstPriceEndDateq) {
		betInfo.setAddTime(new Date());
		System.out.println(firstPriceEndDateq);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		betInfoService.insert(betInfo);
		msg = "添加成功！"; 
		return msg;
	}
	
	
	@RequestMapping(value = "isExistBetInfo")
	@ResponseBody
	public String isExistBetInfo(BetInfo betInfo){
		betInfoList = (List<BetInfo>) betInfoService.selectByExample(betInfo, null);
		if (null != betInfoList && betInfoList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectBetInfo")
	public String gotoSelectBetInfo(){
		return "betInfo/betInfo";
	}	
	@RequestMapping(value = "selectBetInfo")
	@ResponseBody
	public List selectbetInfo(BetInfo betInfo,ModelMap map,PagePO pagePo){
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = betInfoService.countByExample(betInfo);
		pagePo.initPage(totalCount);
		betInfoList = betInfoService.selectByExample(betInfo, pagePo);
		map.put("betInfoList", betInfoList);
		map.put("pagePo", pagePo);
		//map.put("isAndNotEnum",  IsAndNotEnum.values());
		return betInfoList;
		
	}	
	@RequestMapping(value = "updateBetInfo", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateBetInfo(BetInfo betInfo ){
		logger.info("update betInfo");
		betInfoService.updateByPrimaryKeySelective(betInfo);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteBetInfo", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteBetInfo(String id){
		betInfoService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
}
