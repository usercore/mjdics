package com.magic.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import com.magic.lottery.ws.SelfServiceWebService;


public class GetLotteryKey extends HttpServlet implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
	}
	
	public void contextInitialized(ServletContextEvent arg0) {
		SelfServiceWebService selfServiceWebService = new SelfServiceWebService();
		selfServiceWebService.getSecuretKeyByID();
	}
}
