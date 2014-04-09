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

import com.magic.promotion.agent.domain.Agent;
import com.magic.promotion.agent.service.AgentServiceImpl;
import com.magic.promotion.applyCard.domain.ApplyCard;
import com.magic.promotion.applyCard.service.ApplyCardServiceImpl;
import com.magic.promotion.card.domain.Card;
import com.magic.promotion.card.service.CardServiceImpl;
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.promotion.util.enumUtil.ApplyCardStatusEnum;
import com.magic.promotion.util.enumUtil.CardEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/applyCard")
@Scope("prototype")
public class ApplyCardController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(ApplyCardController.class);
	
	@Autowired
	ApplyCardServiceImpl applyCardService;
	@Autowired
	AgentServiceImpl agentService;
	@Autowired
	CardServiceImpl cardService;
	List<ApplyCard> applyCardList;
	
	@RequestMapping(value = "applyCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> applyCard(HttpSession session, ApplyCard applyCard) throws Exception {
		Map<String,String> map = new HashMap<String,String>();	
		Agent agent = (Agent)session.getAttribute("agent");
		applyCard.setAddTime(new Date());
		applyCard.setAddPerson(agent.getAgentId());
		applyCard.setAgentId(agent.getAgentId());
		applyCard.setStatus(ApplyCardStatusEnum.APPLY);
		try{
			String m = applyCardService.applyCard(applyCard,agent);
			msg = "".equals(m)?"领卡请求已成功递交！":m;
			map.put("info", msg);
			map.put("status", "Y");
		}catch(BusinessException e){
			msg = e.getMessage();
			map.put("info", msg);
			map.put("status", "N");
			return map;	
		}
		
		return map;	
	}
	
	
	@RequestMapping(value = "isExistApplyCard")
	@ResponseBody
	public String isExistApplyCard(ApplyCard applyCard){
		applyCardList = (List<ApplyCard>) applyCardService.selectByExample(applyCard, null);
		if (null != applyCardList && applyCardList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectApplyCard")
	public String gotoSelectApplyCard(HttpSession session,ModelMap map){
		Agent agent = (Agent)session.getAttribute("agent");
		Card c = new Card();
//		if(agent.getType()==AgentTypeEnum.FIRST_BUSS){
			c.setAgentId("admin");	
			c.setStatus(CardEnum.Init);
//		}else if (agent.getType()==AgentTypeEnum.SEC_BUSS || agent.getType()==AgentTypeEnum.SALER) {
//			Agent a =agentService.selectByAgentId(agent.getAgentId());
//			c.setAgentId(a.getParaAgent());
//			c.setStatus(CardEnum.getCard);
//		}
		List <Card> cardTypeList = cardService.selectByCardTypeId(c);
		map.put("cardTypeList", cardTypeList);
		map.put("applyCardStatusEnum", ApplyCardStatusEnum.values());
		return "applyCard/applyCard";
	}
	@RequestMapping(value = "selectApplyCard")
	@ResponseBody
	public Map<String,Object> selectapplyCard(HttpSession session,ApplyCard applyCard,ModelMap map,PagePO pagePo){
		Agent agent = (Agent)session.getAttribute("agent");
		if(agent.getType()!=AgentTypeEnum.ADMIN){
			applyCard.setAgentId(agent.getAgentId());
		}
		Map<String,Object> mapData = new HashMap<String,Object> ();
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = applyCardService.countByExample(applyCard);
		pagePo.initPage(totalCount);
		applyCardList = applyCardService.selectByExample(applyCard, pagePo);
		mapData.put("total", totalCount);
		mapData.put("rows",  applyCardList);
		return mapData;
		
	}	
	@RequestMapping(value = "updateApplyCard", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateApplyCard(ApplyCard applyCard ){
		logger.info("update applyCard");
		applyCardService.updateByPrimaryKeySelective(applyCard);
		msg = "修改成功！";
		return msg;
	}
	
	@RequestMapping(value = "applyCardFulfill", method = RequestMethod.POST)
	@ResponseBody
	public 	String  applyCardFulfill(HttpSession session,ApplyCard applyCard ){
		logger.info("applyCardFulfill");
		Agent agent = (Agent)session.getAttribute("agent");
		applyCardService.applyCardFulfill(applyCard,agent);
		msg = "领卡成功！";
		return msg;
	}	
	
}
