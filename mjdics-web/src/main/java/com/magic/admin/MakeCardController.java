package com.magic.admin;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.makeCard.domain.MakeCard;
import com.magic.promotion.makeCard.service.MakecardServiceImpl;
import com.magic.promotion.util.DateUtil;
import com.magic.promotion.util.ExportExcel;
import com.magic.promotion.util.enumUtil.CardEnum;
import com.magic.promotion.util.enumUtil.MakeCardStatusEnum;
import com.magic.util.PagePO;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


@Controller
@RequestMapping(value="admin/makeCard")
@Scope("prototype")
public class MakeCardController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(MakeCardController.class);
	
	@Autowired
	MakecardServiceImpl makeCardService;
	@Autowired
	CardServiceImpl cardService;
	List<MakeCard> makeCardList;
	
	@RequestMapping(value = "insertMakeCard")
	public String insertMakeCard(HttpServletRequest request,HttpServletResponse response,HttpSession session, MakeCard makeCard,ModelMap map) throws Exception {
		Agent agent = (Agent)session.getAttribute("agent");
		map.put("makeCardStatusEnum", MakeCardStatusEnum.values());
		Card c1 = new Card();
		c1.setStatus(CardEnum.AppCard);
		int count = cardService.countByExample(c1);
		if (count > 0) {
			Card example = new Card();
			example.setStatus(CardEnum.AppCard);
			List<Card> list = cardService.selectByExample(example, null);

			makeCard.setAmount(count);
			makeCard.setAddTime(new Date());
			makeCard.setAddPerson(agent.getAgentId());
			makeCard.setStatus(MakeCardStatusEnum.MAKE);
			makeCardService.makeCard(makeCard);
			return exportExcel(request, response, list);
		}else{
			map.put("msg", "当前无制卡请求");
			return "makeCard/makeCard";
		}
		
	}
	
	
	@RequestMapping(value = "isExistMakeCard")
	@ResponseBody
	public String isExistMakeCard(MakeCard makeCard){
		makeCardList = (List<MakeCard>) makeCardService.selectByExample(makeCard, null);
		if (null != makeCardList && makeCardList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectMakeCard")
	public String gotoSelectMakeCard(ModelMap map){
		Card c = new Card();
		c.setStatus(CardEnum.AppCard);
		List <Card> cardTypeList = cardService.selectByCardTypeId(c);
		map.put("cardTypeList", cardTypeList);
		map.put("makeCardStatusEnum", MakeCardStatusEnum.values());
		return "makeCard/makeCard";
	}
	@RequestMapping(value = "gotoStatMakeCard")
	public String gotoStatMakeCard(){
		return "makeCard/statMakeCard";
	}	
	@RequestMapping(value = "selectMakeCard")
	@ResponseBody
	public Map<String,Object> selectmakeCard(MakeCard makeCard,ModelMap map,PagePO pagePo){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = makeCardService.countByExample(makeCard);
		pagePo.initPage(totalCount);
		makeCardList = makeCardService.selectByExample(makeCard, pagePo);
		mapData.put("total", totalCount);
		mapData.put("rows",  makeCardList);
		return mapData;
		
	}	
	@RequestMapping(value = "updateMakeCard", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateMakeCard(MakeCard makeCard ){
		logger.info("update makeCard");
		makeCardService.updateMakeCard(makeCard);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteMakeCard", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteMakeCard(String id){
		makeCardService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
	
	/**
	 * 导出excel
	 * 
	 * @return
	 * @throws IOException 
	 * @throws ParseException
	 */
	@RequestMapping(value = "exportExcelByMakeId")	
	public String exportExcelByMakeId(HttpServletRequest request,HttpServletResponse response,MakeCard makeCard){
		Card example = new Card();
		example.setMakeId(makeCard.getId());
		List<Card> list = cardService.selectByExample(example, null);
		return exportExcel(request, response, list);
	}
	
	public String exportExcel(HttpServletRequest request,HttpServletResponse response,List<Card> list){
		try {
			String d = DateUtil.dateToStrLong("yyyy-MM-dd");
			String fileName = d+"制片数据.xls";
			String[] headers = { "卡ID", "密码","面值"};

			ExportExcel excel = new ExportExcel();
			excel.begin(fileName);
			excel.defineFieldTitle(headers, 0);		
			
			for (int i = 0; i < list.size(); i++) {
				Card c = list.get(i);
				excel.writeData(excel.getCell(i + 1, 0), c.getCardId());
				excel.writeData(excel.getCell(i + 1, 1), c.getPassword());
				excel.writeData(excel.getCell(i + 1, 2), c.getMoney().toString());
			}
			
			response.addHeader("Content-Disposition", "attachment;filename="+ toUtf8String(fileName,request));
			OutputStream out = response.getOutputStream();
			excel.getWorkBook().write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			return null;
		}
		return null;
	}	

	private String toUtf8String(String fileName, HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
		try {
			if (isMSIE) {
				fileName = java.net.URLEncoder.encode(fileName, "UTF8");
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
}
