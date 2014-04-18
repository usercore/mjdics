package com.magic.admin;


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
import com.magic.promotion.priviledges.domain.Attributes;
import com.magic.promotion.priviledges.domain.Children;
import com.magic.promotion.priviledges.domain.GroupPriviledges;
import com.magic.promotion.priviledges.domain.SysPriviledges;
import com.magic.promotion.priviledges.domain.SysPriviledgesEdit;
import com.magic.promotion.priviledges.service.GroupPriviledgesServiceImpl;
import com.magic.promotion.priviledges.service.SysPriviledgesServiceImpl;
import com.magic.util.PagePO;
import com.mjdics.account.domain.User;


@Controller
@RequestMapping(value="admin/sysPriviledges")
@Scope("prototype")
public class SysPriviledgesController{
	String msg ="";
	private static final Logger logger = LoggerFactory.getLogger(SysPriviledgesController.class);
	
	@Autowired
	SysPriviledgesServiceImpl sysPriviledgesService;
	@Autowired
	GroupPriviledgesServiceImpl groupPriviledgesService;
	List<SysPriviledges> sysPriviledgesList;
	
	@RequestMapping(value = "insertSysPriviledges", method = RequestMethod.POST)
	@ResponseBody
	public String insertSysPriviledges(HttpSession session, SysPriviledges sysPriviledges) throws Exception {
		sysPriviledgesService.insert(sysPriviledges);
		msg = "添加成功！"; 
		return msg;
	}
	
	@RequestMapping(value = "isExistSysPriviledges")
	@ResponseBody
	public String isExistSysPriviledges(SysPriviledges sysPriviledges){
		sysPriviledgesList = (List<SysPriviledges>) sysPriviledgesService.selectByExample(sysPriviledges, null);
		if (null != sysPriviledgesList && sysPriviledgesList.size() != 0) {
			msg = "已存在，请重新输入";
			return msg;
		}
		return msg;
	}
	
	@RequestMapping(value = "gotoSelectSysPriviledges")
	public String gotoSelectSysPriviledges(ModelMap map,String type,String isAccount){
		/*map.put("sysPriviledgesTyepEnum", SysPriviledgesTypeEnum.values());
		map.put("type", type);
		if(isAccount!=null)
			return "sysPriviledges/sysPriviledgesAccount";*/
		return "priv/list";
	}	
	@RequestMapping(value = "selectSysPriviledges")
	@ResponseBody
	public Map<String,Object> selectsysPriviledges(SysPriviledges sysPriviledges,ModelMap map,PagePO page){
		if(page==null){
			page = new PagePO();
			page.setCurrentPage(1);
		}
		Map<String,Object> mapData = new HashMap<String,Object> ();
		int totalCount = sysPriviledgesService.countByExample(sysPriviledges);
		page.initPage(totalCount);
		List<SysPriviledgesEdit> sysPriviledgesEditList = sysPriviledgesService.selectEditByExample(null, null);
		mapData.put("total", totalCount);
		mapData.put("rows",  sysPriviledgesEditList);
		return mapData;
		
	}	
	@RequestMapping(value = "updateSysPriviledges", method = RequestMethod.POST)
	@ResponseBody
	public 	Map<String,String>  updateSysPriviledges(SysPriviledges sysPriviledges ){
		logger.info("update sysPriviledges");
		Map<String,String> map = new HashMap<String,String>();
		sysPriviledgesService.updateByPrimaryKeySelective(sysPriviledges);
		msg = "修改成功！";
		map.put("info", msg);
		map.put("status", "Y");
		return map;
	}
	@RequestMapping(value = "deleteSysPriviledges", method = RequestMethod.POST)
	@ResponseBody
	public String  deleteSysPriviledges(String id){
		sysPriviledgesService.deleteByPrimaryKey(Integer.parseInt(id));
		msg = "删除成功！";
		return msg;
	}
	
	@RequestMapping(value = "selectSysPriviledgesName")
	@ResponseBody
	public SysPriviledges selectSysPriviledgesName(SysPriviledges sysPriviledges,ModelMap map){
		sysPriviledgesList = sysPriviledgesService.selectByExample(sysPriviledges, null);
		if(sysPriviledgesList!=null && sysPriviledgesList.size()==1)
			return sysPriviledgesList.get(0);
		return null;
		
	}	
	
	@RequestMapping(value = "selectSysPriviledgesByGroupId")
	@ResponseBody
	public List<SysPriviledges> selectSysPriviledgesByGroupId(SysPriviledges sysPriviledges,ModelMap map,HttpSession session){
		User user = (User)session.getAttribute("user");
		sysPriviledgesList = sysPriviledgesService.selectSysPriviledgesByGroupId(user.getGroupId());
		List<SysPriviledges> sysPriviledgesChlList = sysPriviledgesService.selectSysPriviledgesByGroupId(user.getGroupId());
		for(int i=0;i<sysPriviledgesList.size();i++){
			SysPriviledges sysPriviledgesPar = sysPriviledgesList.get(i);
			
			if(sysPriviledgesPar.get_parentId()==null){//父菜单
				for(int j=0;j<sysPriviledgesChlList.size();j++){
					SysPriviledges sysPriviledgesChl = sysPriviledgesChlList.get(j);
					
					if((sysPriviledgesChl.get_parentId()+"").equals(sysPriviledgesPar.getId()+"")){//子菜单
						Children children = new Children();
						Attributes attributes = new Attributes();
						attributes.setTarger("mainIframe");
						attributes.setUrl(sysPriviledgesChl.getUrl());
						children.setAttributes(attributes);
						children.setId(sysPriviledgesChl.getId());
						children.setText(sysPriviledgesChl.getText());
						sysPriviledgesPar.getChildren().add(children);
						sysPriviledgesChlList.remove(j);
						--j;
					}
				}
			}else{
				sysPriviledgesList.remove(i);
				--i;
			}
		}
		
		return sysPriviledgesList;
		
	}	
	@RequestMapping(value = "gotoEditSysPriviledges")
	public  String gotoEditSysPriviledges(int groupId,ModelMap map,HttpSession session){
		map.put("groupId", groupId);
		return "priv/accredit";
		
	}	
	
	@RequestMapping(value = "editSysPriviledgesByGroupId")
	@ResponseBody
	public   Map<String,Object> editSysPriviledgesByGroupId(int groupId,ModelMap map,HttpSession session){
		Map<String,Object> mapData = new HashMap<String,Object> ();
		List<GroupPriviledges> groupPriviledgesList= groupPriviledgesService.selectByGroupId(groupId);
		List<SysPriviledgesEdit> sysPriviledgesEditList = sysPriviledgesService.selectEditByExample(null, null);
		
		for(int i=0;i<sysPriviledgesEditList.size();i++){
			SysPriviledgesEdit sysPriviledgesEdit = sysPriviledgesEditList.get(i);
			for(int j=0;j<groupPriviledgesList.size();j++){
				GroupPriviledges groupPriviledges = groupPriviledgesList.get(j);
				if((sysPriviledgesEdit.getId()+"").equals(groupPriviledges.getId()+"")){
					sysPriviledgesEdit.setChecked(true);
					groupPriviledgesList.remove(j);
					j--;
				}
			}
		}
		mapData.put("total", sysPriviledgesEditList.size());
		mapData.put("rows",  sysPriviledgesEditList);
		return mapData;
		
	}	
	
	@RequestMapping(value = "updateSysPriviledgesByGroupId")
	@ResponseBody
	public   String updateSysPriviledgesByGroupId(int groupId,String privs,ModelMap map,HttpSession session){
		String[] privsArr = privs.split(",");
		groupPriviledgesService.updateSysPriviledgesByGroupId(groupId,privsArr);
		msg = "修改成功！";
		return msg;
		
	}	
}
