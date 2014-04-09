package com.magic.promotion.quartz.service;

import java.util.Iterator;
import java.util.Map;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("quartzService")
public class QuartzServiceImpl  {
	private static final Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);
	@Autowired
	private Scheduler service;
	private  Map<String,Scheduler> serviceMaps ;
	
	public boolean isRunning() {
		boolean r = false;
		try {
			r = !service.isInStandbyMode();
			if (r)
				logger.info(service.getSchedulerName()+"正在运行中...");
			else
				logger.info(service.getSchedulerName()+"没有运行...");
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return r;
	}

	public boolean start() {
		boolean r=true;
		try {
			if (!service.isStarted()){
				service.start();				
				logger.info(service.getSchedulerName()+"启动了");	
			}else{
				if (service.isInStandbyMode()){
					service.startDelayed(0);
					logger.info(service.getSchedulerName()+"启动了");
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			r=false;
		}
		return r;
	}
	
	public boolean start(String key) {
		boolean r=true;
		try {
			for(Iterator<Scheduler> it=serviceMaps.values().iterator();it.hasNext();){
				if(key==null||key.equals("")){
					if(!it.next().isStarted())
						it.next().start();					
				}else{
					Map.Entry entry = (Map.Entry) it.next();
				    String mapKey = (String) entry.getKey();
//				    Object val = entry.getValue(); 						
					if(mapKey.equals(key)){
						if(!it.next().isStarted())
							it.next().start();	
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			r=false;
		}	
		return r;
	}	

	public boolean stop() {
		boolean r=true;
		try {
			service.standby();
			logger.info(service.getSchedulerName()+"停止了");
		} catch (Exception e) {
			e.printStackTrace();
			r=false;
		}
		return r;
	}
	public Map<String, Scheduler> getServiceMaps() {
		return serviceMaps;
	}

	public void setServiceMaps(Map<String, Scheduler> serviceMaps) {
		this.serviceMaps = serviceMaps;
	}
    
}
