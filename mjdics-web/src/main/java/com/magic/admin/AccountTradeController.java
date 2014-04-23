package com.magic.admin;


import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.magic.promotion.makeCard.domain.MakeCard;
import com.magic.promotion.util.DateUtil;
import com.magic.promotion.util.ExportExcel;
import com.magic.promotion.util.PagePO;
import com.magic.promotion.util.enumUtil.TradeTypeEnum;
import com.mjdics.account.domain.AccountTrade;
import com.mjdics.account.domain.User;
import com.mjdics.account.service.AccountTradeServiceImpl;
import com.mjdics.sysManager.domain.TradeType;
import com.mjdics.sysManager.service.TradeTypeServiceImpl;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


@Controller
@RequestMapping(value="admin/accountTrade")
@Scope("prototype")
public class AccountTradeController{
	String tip ="";
	private static final Logger logger = LoggerFactory.getLogger(AccountTradeController.class);
	
	@Autowired
	AccountTradeServiceImpl accountTradeService;
	@Autowired
	TradeTypeServiceImpl tradeTypeService;
	
	List<AccountTrade> accountTradeList;
	
	@RequestMapping(value = "insertAccountTrade", method = RequestMethod.POST)
	@ResponseBody
	public String insertAccountTrade(HttpSession session, AccountTrade accountTrade,String firstPriceEndDateq) {
		accountTrade.setAddTime(new Date());
		System.out.println(firstPriceEndDateq);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		accountTrade.setAddPerson("admin");
		accountTradeService.insert(accountTrade);
		tip = "添加成功！"; 
		return tip;
	}
	
	
	@RequestMapping(value = "isExistAccountTrade")
	@ResponseBody
	public String isExistAccountTrade(AccountTrade accountTrade){
		accountTradeList = (List<AccountTrade>) accountTradeService.selectByExample(accountTrade, null);
		if (null != accountTradeList && accountTradeList.size() != 0) {
			tip = "已存在，请重新输入";
			return tip;
		}
		return tip;
	}
	@RequestMapping(value = "gotoSelectAccountTrade")
	public String gotoSelectaccountTrade(ModelMap map){
		
		TradeType tradeType = new TradeType();
		tradeType.setType(TradeTypeEnum.INCOME);
		List<TradeType> inComeTypeList = tradeTypeService.selectByExample(tradeType, null);
		tradeType.setType(TradeTypeEnum.OUTCOME);
		List<TradeType> outComeTypeList = tradeTypeService.selectByExample(tradeType, null);
		
		map.put("outComeTypeList", outComeTypeList);
		map.put("inComeTypeList", inComeTypeList);
		return "account/accountTrade";
		
	}	
	@RequestMapping(value = "selectAccountTrade")
	@ResponseBody
	public List selectAccountTrade(AccountTrade accountTrade,ModelMap map,PagePO pagePo,HttpSession session){
		User user = (User)session.getAttribute("user");
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = accountTradeService.countByExample(accountTrade);
		pagePo.initPage(totalCount);
		accountTradeList = accountTradeService.selectByExample(accountTrade, pagePo);
	
		map.put("accountTradeList", accountTradeList);
		map.put("pagePo", pagePo);
		return accountTradeList;
		
	}	
	/**
	 * 导出报表
	 * 
	 * @return
	 * @throws IOException 
	 * @throws ParseException
	 */
	@RequestMapping(value = "exportAccountTradeByTime")	
	public void exportAccountTradeByTime(HttpServletRequest request,HttpServletResponse response,AccountTrade accountTrade){
		List<AccountTrade> list = accountTradeService.statTradeByTime(accountTrade);
		try {
			String fileName = "mjdics财务统计数据.xls";
			String[] headers = { "项目", "收入","支出"};

			ExportExcel excel = new ExportExcel();
			excel.begin(fileName);
			excel.defineFieldTitle(headers, 0);		
			BigDecimal inCome = new BigDecimal(0);
			BigDecimal outCome = new BigDecimal(0);
			for (int i = 0; i < list.size(); i++) {
				AccountTrade c = list.get(i);
				excel.writeData(excel.getCell(i + 1, 0), c.getTypeName());
				if(c.getType()==TradeTypeEnum.INCOME){
					inCome = inCome.add(c.getMoney());
					excel.writeData(excel.getCell(i + 1, 1), c.getMoney().toString());
				}
				if(c.getType()==TradeTypeEnum.OUTCOME){
					outCome = outCome.add(c.getMoney());
					excel.writeData(excel.getCell(i + 1, 2), c.getMoney().toString());
				}
				
			}
			excel.writeData(excel.getCell(list.size() + 1, 0),"合计");
			excel.writeData(excel.getCell(list.size() + 1, 1), inCome.toString());
			excel.writeData(excel.getCell(list.size() + 1, 2), outCome.toString());
			response.addHeader("Content-Disposition", "attachment;filename="+ toUtf8String(fileName,request));
			OutputStream out = response.getOutputStream();
			excel.getWorkBook().write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
