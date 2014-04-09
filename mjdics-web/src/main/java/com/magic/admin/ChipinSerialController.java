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

import com.magic.promotion.chipinSerial.domain.ChipinSerial;
import com.magic.promotion.chipinSerial.service.ChipinSerialServiceImpl;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/chipinSerial")
@Scope("prototype")
public class ChipinSerialController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(ChipinSerialController.class);
	
	@Autowired
	ChipinSerialServiceImpl chipinSerialService;
	List<ChipinSerial> chipinSerialList;
	
	@RequestMapping(value = "insertChipinSerial", method = RequestMethod.POST)
	@ResponseBody
	public String insertChipinSerial(HttpSession session, ChipinSerial chipinSerial,String firstPriceEndDateq) {
//		chipinSerial.setAddTime(new Date());
		System.out.println(firstPriceEndDateq);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		chipinSerial.setAddPerson("admin");
		chipinSerialService.insert(chipinSerial);
		msg = "添加成功！"; 
		return msg;
	}
	
	
	@RequestMapping(value = "isExistChipinSerial")
	@ResponseBody
	public String isExistChipinSerial(ChipinSerial chipinSerial){
		chipinSerialList = (List<ChipinSerial>) chipinSerialService.selectByExample(chipinSerial, null);
		if (null != chipinSerialList && chipinSerialList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectChipinSerial")
	public String gotoSelectChipinSerial(){
		return "chipinSerial/chipinSerial";
	}	
	@RequestMapping(value = "selectChipinSerial")
	@ResponseBody
	public List selectchipinSerial(ChipinSerial chipinSerial,ModelMap map,PagePO pagePo){
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = chipinSerialService.countByExample(chipinSerial);
		pagePo.initPage(totalCount);
		chipinSerialList = chipinSerialService.selectByExample(chipinSerial, pagePo);
		map.put("chipinSerialList", chipinSerialList);
		map.put("pagePo", pagePo);
		//map.put("isAndNotEnum",  IsAndNotEnum.values());
		return chipinSerialList;
		
	}	
	@RequestMapping(value = "updateChipinSerial", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateChipinSerial(ChipinSerial chipinSerial ){
		logger.info("update chipinSerial");
		chipinSerialService.updateByPrimaryKeySelective(chipinSerial);
		msg = "修改成功！";
		return msg;
	}
	@RequestMapping(value = "deleteChipinSerial", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteChipinSerial(String id){
		chipinSerialService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
}
