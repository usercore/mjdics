package com.magic.admin;


import java.math.BigDecimal;
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

import com.magic.promotion.exception.BusinessException;
import com.magic.promotion.priviledges.domain.UserGroup;
import com.magic.promotion.priviledges.service.UserGroupServiceImpl;
import com.magic.promotion.util.enumUtil.AgentTypeEnum;
import com.magic.util.PagePO;


@Controller
@RequestMapping(value="admin/userGroup")
@Scope("prototype")
public class UserGroupController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);
	
	@Autowired
	UserGroupServiceImpl userGroupService;
	
	List<UserGroup> userGroupList;
	
	@RequestMapping(value = "insertUserGroup", method = RequestMethod.POST)
	@ResponseBody
	public String insertUserGroup(HttpSession session, UserGroup userGroup) throws Exception {
		userGroupService.insert(userGroup);
		msg = "添加成功！"; 
		return msg;
	}
	
	@RequestMapping(value = "isExistUserGroup")
	@ResponseBody
	public String isExistUserGroup(UserGroup userGroup){
		userGroupList = (List<UserGroup>) userGroupService.selectByExample(userGroup, null);
		if (null != userGroupList && userGroupList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectUserGroup")
	public String gotoSelectUserGroup(ModelMap map,String type,String isAccount){
		map.put("levels", AgentTypeEnum.values());
		
		return "manager/group_list";
	}	
	@RequestMapping(value = "selectUserGroup")
	@ResponseBody
	public Map<String,Object> selectuserGroup(UserGroup userGroup,ModelMap map,PagePO page){
		if(page==null){
			page = new PagePO();
			page.setCurrentPage(1);
		}
		Map<String,Object> mapData = new HashMap<String,Object> ();
		int totalCount = userGroupService.countByExample(userGroup);
		page.initPage(totalCount);
		userGroupList = userGroupService.selectByExample(userGroup, page);
		mapData.put("total", totalCount);
		mapData.put("rows",  userGroupList);
		return mapData;
		
	}	
	@RequestMapping(value = "updateUserGroup", method = RequestMethod.POST)
	@ResponseBody
	public 	String  updateUserGroup(UserGroup userGroup ){
		logger.info("update userGroup");
		userGroupService.updateByPrimaryKeySelective(userGroup);
		Map<String,String> map= new HashMap<String,String>();
		msg = "修改成功！";
		map.put("msg", msg);
		return msg;
	}
	@RequestMapping(value = "deleteUserGroup", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteUserGroup(String id){
		userGroupService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
	
	@RequestMapping(value = "selectUserGroupName")
	@ResponseBody
	public UserGroup selectUserGroupName(UserGroup userGroup,ModelMap map){
		userGroupList = userGroupService.selectByExample(userGroup, null);
		if(userGroupList!=null && userGroupList.size()==1)
			return userGroupList.get(0);
		return null;
		
	}	
}
