package com.magic.util;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BaseController {
	
	protected String tip = "";

	@RequestMapping("/{jsp}.htm")
	public @ResponseBody String jsp(@PathVariable(value="jsp") String jsp){
		System.out.println(jsp);
		return jsp;
	}
	
	/*public Integer getLoginUserId(SysUser user){
		return user.getId();
	}
	
	public String getLoginUserName(SysUser user){
		return user.getUserId();
	}
	
	public Integer getLoginAgentId(SysUser user){
		return user.getAgentId();
	}
	
	public SysUser getSysUser(HttpSession session){
		return (SysUser)session.getAttribute(WebConstant.USER);
	}*/
	
}
