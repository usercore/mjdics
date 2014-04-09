package com.magic.util;
import java.io.IOException;  
import java.io.PrintWriter;  

import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceEditor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.web.context.support.ServletContextResourceLoader;
import org.springframework.web.filter.OncePerRequestFilter;  
import org.springframework.web.util.NestedServletException;

import com.magic.promotion.agent.domain.Agent;

  
/** 
 * 登录过滤 
 */  
public class SessionFilter extends OncePerRequestFilter {  
  String contextPath;
    @Override  
    protected void doFilterInternal(HttpServletRequest request,  
            HttpServletResponse response, FilterChain filterChain)  
            throws ServletException, IOException {
    	String queryString = "";
		// 不过滤的uri
		String[] notFilter = new String[] {".", "login", "index" ,"verifyCodeServlet","logout","reg","aliNotify"};
		// 请求的uri
		if(request.getParameter("urlType")!=null){
			queryString = "?"+request.getQueryString();
		}
		String uri = request.getRequestURI()+queryString;
		// 是否过滤
		boolean validFlag = true;
		for (String s : notFilter) {
			if (uri.indexOf(s) != -1||uri.equals("/promotion/")) {
				validFlag = false;
				break;
			}
		}
		
		String siteContext = request.getScheme() + "://" + request.getServerName()
				//
						+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + this.getFilterConfig().getServletContext().getContextPath();
				request.setAttribute("siteContext", siteContext);
		/*uri = uri.substring(uri.indexOf("/", 1),uri.length());
		List<SysUserMenu> menuList = (List<SysUserMenu>)request.getSession().getAttribute("menuList");
		if(menuList==null||menuList.size()==0){
			request.getSession().setAttribute("selectMenu",uri);
		}else{
			for(int i=0;i<menuList.size();i++){
				SysUserMenu sysUserMenu = menuList.get(i);
				if(uri.equals(sysUserMenu.getMenu().getUrl())){
					request.getSession().setAttribute("selectMenu",uri);
				}
				
			}
		}*/
		
		
		// 执行过滤
		// 从session中获取登录者实体
		if(validFlag){
			Agent agent = (Agent) request.getSession().getAttribute("agent");
			if (null == agent) {
				returnLoginPage(request, response);
			}else{
				filterChain.doFilter(request, response);
			}

		}else{
			filterChain.doFilter(request, response);
		}
		
	
    	//filterChain.doFilter(request, response);	
    }  

  private void returnLoginPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
	  	response.setCharacterEncoding("utf-8");  
	  	response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\">alert('网页过期，请重新登录！');window.top.location.href='");
		builder.append(request.getContextPath()+ "/admin/index';</script>");
		out.print(builder.toString());
  }
  private void inclidPath(HttpServletRequest request,  
          HttpServletResponse response)throws ServletException, IOException{
	  	response.setCharacterEncoding("utf-8");  
	  	response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\">alert('您无权限访问该页面！');</script>");
		out.print(builder.toString());
  }
}  
