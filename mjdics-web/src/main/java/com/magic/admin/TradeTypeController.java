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

import com.magic.promotion.util.PagePO;
import com.magic.promotion.util.enumUtil.TradeTypeEnum;
import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.resource.domain.Resource;
import com.magic.promotion.resource.service.ResourceServiceImpl;
import com.mjdics.account.domain.User;
import com.mjdics.sysManager.domain.TradeType;
import com.mjdics.sysManager.service.TradeTypeServiceImpl;


@Controller
@RequestMapping(value="admin/tradeType")
@Scope("prototype")
public class TradeTypeController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(TradeTypeController.class);
	
	@Autowired
	TradeTypeServiceImpl tradeTypeService;
	@Autowired
	ResourceServiceImpl resourceService;

	List<TradeType> tradeTypeList;
	
	@RequestMapping(value = "insertTradeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertTradeType(HttpSession session, TradeType tradeType) {
		Map<String,String> map = new HashMap<String,String>();	
		User user = (User)session.getAttribute("user");
		
		tradeType.setAddTime(new Date());
		tradeType.setAddPerson(user.getUserId());
		
		tradeTypeService.insert(tradeType);
		msg = "添加成功！"; 
		map.put("info", msg);
		map.put("status", "Y");
		return map;	
	}
	
	
	@RequestMapping(value = "isExistTradeType")
	@ResponseBody
	public String isExistTradeType(TradeType tradeType,String param){
		msg="y";
		tradeType.setName(param);
		tradeTypeList = (List<TradeType>) tradeTypeService.selectByExample(tradeType, null);
		if (null != tradeTypeList && tradeTypeList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectTradeType")
	public String gotoSelectTradeType(ModelMap map){
		map.put("tradeTypeEnum", TradeTypeEnum.values());
		return "tradeType/tradeType";
	}	
	@RequestMapping(value = "selectTradeType")
	@ResponseBody
	public Map<String,Object> selecttradeType(TradeType tradeType,ModelMap map,PagePO pagePo){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = tradeTypeService.countByExample(tradeType);
		pagePo.initPage(totalCount);
		tradeTypeList = tradeTypeService.selectByExample(tradeType, pagePo);
		mapData.put("total", totalCount);
		mapData.put("rows",  tradeTypeList);
		return mapData;
		
	}	
/*	@RequestMapping(value = "updateTradeType", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateTradeType(TradeType tradeType ){
		logger.info("update tradeType");
		List resourceListId = tradeType.getResourceListId();
		String resourceId ="";
		tradeType.setResourceId(resourceId);
		StringBuffer str = new StringBuffer();
	    if(resourceListId!=null){
			for (int i = 0; i < resourceListId.size(); i++) {
				str.append(resourceListId.get(i));
				str.append(",");
			}
			if(resourceListId.size()>0){
				System.out.println(str.substring(0,str.length()-1));
				tradeType.setResourceId(str.substring(0,str.length()-1));
			}	    	
	    }	
		tradeTypeService.update(tradeType);
		msg = "修改成功！";
		return msg;
	}*/
	@RequestMapping(value = "deleteTradeType", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteTradeType(String id){
		tradeTypeService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
	
}
