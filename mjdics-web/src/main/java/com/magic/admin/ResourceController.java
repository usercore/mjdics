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

import com.magic.promotion.resource.domain.Resource;
import com.magic.promotion.resource.service.ResourceServiceImpl;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/resource")
@Scope("prototype")
public class ResourceController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	ResourceServiceImpl resourceService;
	List<Resource> resourceList;
	
	@RequestMapping(value = "insertResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertResource(HttpSession session, Resource resource) {
		Map<String,String> map = new HashMap<String,String>();	
		resource.setAddTime(new Date());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		resource.setAddPerson("admin");
		resourceService.insert(resource);
		map.put("info", msg);
		map.put("status", "Y");
		return map;	
	}
	
	
	@RequestMapping(value = "isExistResource")
	@ResponseBody
	public String isExistResource(Resource resource,String param){
		msg="y";
		resource.setRemark(param);
		resourceList = (List<Resource>) resourceService.selectByExample(resource, null);
		if (null != resourceList && resourceList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectResource")
	public String gotoSelectResource(){
		return "resource/resource";
	}	
	@RequestMapping(value = "selectResource")
	@ResponseBody
	public Map<String,Object> selectResource(Resource resource,ModelMap map,PagePO pagePo){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		if(pagePo==null){
			pagePo = new PagePO();
			pagePo.setCurrentPage(1);
		}
		int totalCount = resourceService.countByExample(resource);
		pagePo.initPage(totalCount);
		resourceList = resourceService.selectByExample(resource, pagePo);
		mapData.put("total", totalCount);
		mapData.put("rows",  resourceList);
		return mapData;
		
	}	
	@RequestMapping(value = "updateResource", method = RequestMethod.POST)
	@ResponseBody
	public 	Map<String,String>  updateResource(Resource resource ){
		Map<String,String> map = new HashMap<String,String>();	
		logger.info("update resource");
		resource.setUpdatePersion("admin");
		resource.setUpdateTime(new Date());
		resourceService.updateByPrimaryKeySelective(resource);
		msg = "修改成功！";
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value = "deleteResource", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteResource(String id){
		resourceService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
}
