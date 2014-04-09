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
import com.magic.promotion.cardType.domain.CardType;
import com.magic.promotion.cardType.service.CardTypeServiceImpl;
import com.magic.promotion.resource.domain.Resource;
import com.magic.promotion.resource.service.ResourceServiceImpl;
import com.magic.promotion.util.enumUtil.CardTypeEnum;
import com.magic.promotion.util.enumUtil.CardTypeStatusEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/cardType")
@Scope("prototype")
public class CardTypeController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(CardTypeController.class);
	
	@Autowired
	CardTypeServiceImpl cardTypeService;
	@Autowired
	ResourceServiceImpl resourceService;

	List<CardType> cardTypeList;
	
	@RequestMapping(value = "insertCardType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertCardType(HttpSession session, CardType cardType,String firstPriceEndDateq) {
		Map<String,String> map = new HashMap<String,String>();	
		Agent agentLogin = (Agent)session.getAttribute("agent");
		cardType.setAddTime(new Date());
		List resourceListId = cardType.getResourceListId();
		String resourceId ="";
		cardType.setResourceId(resourceId);
		StringBuffer str = new StringBuffer();
	    if(resourceListId!=null){
			for (int i = 0; i < resourceListId.size(); i++) {
				str.append(resourceListId.get(i));
				str.append(",");
			}
			if(resourceListId.size()>0){
				System.out.println(str.substring(0,str.length()-1));
				cardType.setResourceId(str.substring(0,str.length()-1));
			}	    	
	    }
	    cardType.setPrice(cardType.getMoney());
	    //cardType.setBetCount(cardType.getMoney().divide(cardType.getLotteryMoney()).intValue());
	    cardType.setStatus(CardTypeStatusEnum.Active);
	    cardType.setPrizeCount(0);
		System.out.println(firstPriceEndDateq);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		cardType.setAddPerson(agentLogin.getAgentId());
		cardTypeService.insert(cardType);
		msg = "添加成功！"; 
		map.put("info", msg);
		map.put("status", "Y");
		return map;	
	}
	
	
	@RequestMapping(value = "isExistCardType")
	@ResponseBody
	public String isExistCardType(CardType cardType,String param){
		msg="y";
		cardType.setName(param);
		cardTypeList = (List<CardType>) cardTypeService.selectByExample(cardType, null);
		if (null != cardTypeList && cardTypeList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectCardType")
	public String gotoSelectCardType(ModelMap map){
		List<Resource> resourceList = resourceService.selectByExample(new Resource(), null);
		map.put("resourceList", resourceList);
		map.put("cardTypeStatusEnum", CardTypeStatusEnum.values());
		map.put("cardTypeEnum", CardTypeEnum.values());
		return "cardType/cardType";
	}	
	@RequestMapping(value = "selectCardType")
	@ResponseBody
	public Map<String,Object> selectcardType(CardType cardType,ModelMap map,PagePO pagePo){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = cardTypeService.countByExample(cardType);
		pagePo.initPage(totalCount);
		cardTypeList = cardTypeService.selectByExample(cardType, pagePo);
		mapData.put("total", totalCount);
		mapData.put("rows",  cardTypeList);
		return mapData;
		
	}	
	@RequestMapping(value = "updateCardType", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateCardType(CardType cardType ){
		logger.info("update cardType");
		List resourceListId = cardType.getResourceListId();
		String resourceId ="";
		cardType.setResourceId(resourceId);
		StringBuffer str = new StringBuffer();
	    if(resourceListId!=null){
			for (int i = 0; i < resourceListId.size(); i++) {
				str.append(resourceListId.get(i));
				str.append(",");
			}
			if(resourceListId.size()>0){
				System.out.println(str.substring(0,str.length()-1));
				cardType.setResourceId(str.substring(0,str.length()-1));
			}	    	
	    }	
		cardTypeService.update(cardType);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteCardType", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteCardType(String id){
		cardTypeService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
	
}
