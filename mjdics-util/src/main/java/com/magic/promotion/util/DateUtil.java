package com.magic.promotion.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	public static int compareDate(Date startDate,int addDate){
		Calendar addTime=Calendar.getInstance(); 
		addTime.setTime(startDate);
		addTime.add(Calendar.DATE, addDate);
		
		Calendar nowTime=Calendar.getInstance(); 
		nowTime.setTime(new Date());
		return nowTime.compareTo(addTime);
			
	}
	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(String pattern) {
		Date dateDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String dateString = formatter.format(dateDate);
		return dateString;
	}	
	public static Date getFormatDate(String date,String pattern) {
		SimpleDateFormat formart = new SimpleDateFormat(pattern);
		java.util.Date d = null;
		try {
			d = formart.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d ;
	}	
	/**
	 * 当前时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static Date getPreTime(Date date,String jj) {
		Date d = new Date() ;
		try {
			long  c = date.getTime();
			long Time = (c / 1000) + Integer.parseInt(jj) * 60;
			d.setTime(Time * 1000);
		} catch (Exception e) {
		}
		return d;
	}
	
}
