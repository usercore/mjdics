package com.magic.admin;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.magic.promotion.card.domain.Card;
import com.magic.promotion.card.service.CardServiceImpl;
import com.magic.promotion.cardType.domain.CardType;
import com.magic.promotion.cardType.service.CardTypeServiceImpl;
import com.magic.promotion.util.enumUtil.CardEnum;
import com.magic.promotion.util.enumUtil.CardTypeEnum;
import com.magic.promotion.util.enumUtil.CardTypeStatusEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/card")
@Scope("prototype")
public class CardController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(CardController.class);
	
	@Autowired
	CardServiceImpl cardService;
	@Autowired
	CardTypeServiceImpl cardTypeService;
	List<Card> cardList;
	
	@RequestMapping(value = "insertCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertCard(HttpSession session, Card card) {
		Agent agentLogin = (Agent)session.getAttribute("agent");
		Map<String,String> map = new HashMap<String,String>();
		PagePO page = new PagePO();
		page.setStartRecord(0);
		page.setPageSize(1);
		int batch=0;
		Card c = new Card();
	    List<Card>	list = cardService.selectByExample1(c, page);
	    if(list.size()>0){
	    	batch=Integer.parseInt(list.get(0).getBatch())+1;
	    }else{
	    	batch=batch+1;
	    }
	    CardType cardType = new CardType();
	    cardType.setName(card.getName());
	    cardType.setStatus(CardTypeStatusEnum.Active);
	    List<CardType> ctl = cardTypeService.selectByExample(cardType, null);
	    CardType ct = ctl.get(0);
	    try {
		    cardService.insertBatch(card,batch, ct,agentLogin);
		    msg="添加成功";
			map.put("info", msg);
			map.put("status", "Y");
		} catch (Exception e) {
			msg = e.getMessage();
			map.put("info", msg);
			map.put("status", "N");
			return map;
		}

		return map;
		//return msg;
	}
	
	
	@RequestMapping(value = "isExistCard")
	@ResponseBody
	public String isExistCard(Card card){
		cardList = (List<Card>) cardService.selectByExample(card, null);
		if (null != cardList && cardList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectCard")
	public String gotoSelectCard(HttpSession session,ModelMap map){
		Agent agentLogin = (Agent)session.getAttribute("agent");
		CardType cardType = new CardType();
		cardType.setStatus(CardTypeStatusEnum.Active);
		cardType.setType(CardTypeEnum.Charge);
		List <CardType> cardTypeList = cardTypeService.selectByExample(cardType, null);
		map.put("cardTypeList", cardTypeList);
		map.put("cardEnum", CardEnum.values());
		return "card/card";
	}	
	@RequestMapping(value = "selectCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> selectcard(Card card,ModelMap map,PagePO pagePo,HttpServletRequest request){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setPage(1);
		}
		String name="";
		try {
			name = new String((card.getName()).getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		card.setName(name);
		int totalCount = cardService.countByExample(card);
		pagePo.initPage(totalCount);
		cardList = cardService.selectByExample(card, pagePo);
/*		map.put("cardList", cardList);
		map.put("page", page);
		//map.put("isAndNotEnum",  IsAndNotEnum.values());
*/		
		mapData.put("total", totalCount);
		mapData.put("rows",  cardList);
		
		return mapData;
		
	}	
	@RequestMapping(value = "updateCard", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateCard(Card card ){
		logger.info("update card");
		cardService.updateByPrimaryKeySelective(card);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteCard", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteCard(String id){
		cardService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
	@RequestMapping(value = "gotoStatCardByAgentId")
	public String gotoStatCardByAgentId(ModelMap map){
		return "card/statCardByAgent";
	}	
	@RequestMapping(value = "statCardByAgentId")
	@ResponseBody
	public Map<String,Object> statCardByAgentId(HttpSession session,ModelMap map) {
		Map<String,Object> mapData = new HashMap<String,Object> ();
		Agent agentLogin = (Agent)session.getAttribute("agent");
		mapData.put("total", 1);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list.add(cardService.statCardByAgentId(agentLogin.getAgentId()));
		mapData.put("rows", list);
		return mapData;
	}
}
