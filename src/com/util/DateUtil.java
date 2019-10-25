/*
 * @(#)TimeStyle.java Time: 2013-1-3
 *
 * Copyright 2013 xuedou.com All rights reserved.
 */

package com.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 *<pre>类说明</pre>
 *<b>功能描述：</b>
 * 时间样式
 * @author  meiguanghui meiguanghui@xuedou.com
 * @author  weidan weidan@xuedou.com
 * @version 1.0, 2013-1-4
 */
public class DateUtil {
	
	/**
	 * 返回Timestamp固定格式的当前时间
	 * 格式：yyyy-MM-dd HH:mm:ss:fff
	 * @return
	 */
	public static Timestamp getTime(){
		return new Timestamp(new Date().getTime());
	}
	
	/**
	 * 按指定的枚举模格式来获得当前时间
	 * @param timeEnum 枚举,里面很多格式,这样做的好处:<br>
	     程序员没有办法传入一个非法的格式,增强了程序的强壮性.
	 * @return  返回用当前时间组成的字符串
	 */
	public static  String getFormatTime(TargetEnum timeEnum) {
		return new SimpleDateFormat(timeEnum.toString()).format(new Date());//按指定的模式取出时间
	}
	
	/**
	 * 按指定的枚举模格式来获得当前时间
	 * @param timeEnum 枚举,里面很多格式,这样做的好处:<br>
	     程序员没有办法传入一个非法的格式,增强了程序的强壮性.
	 * @return  返回用当前时间组成的字符串
	 */
	public static  String getFormatTime(TargetEnum timeEnum, Date date) {
		return new SimpleDateFormat(timeEnum.toString()).format(date);//按指定的模式取出时间
	}
	
	/**
	 * 将一个符合时间格式的字符串按照一个枚举类型转化成Date
	 * 如果传入的字符串不是一个标准时间格式将会抛出ParseException异常.
	 * @param strTime 符合标准时间格式的字符串
	 * @param timeEnum 枚举
	 * @return 根据字符串转换后的时间
	 */
	public static Date strToTime(String strTime,TargetEnum timeEnum){
		SimpleDateFormat sdf=new SimpleDateFormat(timeEnum.toString());
		Date date=null;
		try {
			date=sdf.parse(strTime);
		} catch (ParseException e) {
			Logger.getLogger(DateUtil.class).warn(e.toString());
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 计算出当前时间与传入时间的时间间隔
	 * @param timeStr: 接受的时间格式为 yyyy-MM-dd HH:mm:ss
	 * @return 当前时间与传入时间的时间间隔(long型,单位毫秒)
	 */
	public static long getIntervalTime(String timeStr){
		SimpleDateFormat sdf = new SimpleDateFormat(TargetEnum.yyyy_MM_dd_HH_mm_ss.toString());  
		Date strTime = null;
		try {
			 strTime = sdf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cl = Calendar.getInstance();
				 cl.setTime(strTime);
				 
		Calendar clNow = Calendar.getInstance();
		return (clNow.getTimeInMillis() - cl.getTimeInMillis());
	}
	
	/**
	 * 获取当前时间的毫秒数，补全3位
	 * @return
	 */
	public static String getIntervalTime(){
		String str = new SimpleDateFormat("S").format(new Date());
		for(int i = 0 ; i < 3 ; i ++){
			if(str.length()>2){
				break;
			}
			str = "0"+str;
		}
		return str;
	}
	
	/**
	 * 主要用于显示发布的时间
	 * timeStr格式:yyyy-MM-dd HH:mm:ss
	 * @return 经过处理之后的时间字符串
	 */
	public static String getShowTime(String timeStr){
		if(null == timeStr || "".equals(timeStr) || timeStr.length() < 1){
				Logger.getLogger(DateUtil.class).warn("获取时间时,传入的str有问题");
				return null;
		}
		
		long st = getIntervalTime(timeStr);
		
		/*
		编写返回显示时间
		若:发布时间距离现在的时间间隔为st(单位为毫秒)
		当st>=0 && st<60000 则:刚刚               
		当st>=60000 && st<3600000 则:(st/1000/60)=(st/60000)分钟前
		当st>=3600000 && st<24*3600000 则:(st/1000/60/60)=(st/3600000)小时前
		当st>=24*3600000 && st<96*3600000 则:(st/1000/60/60/24)=(st/86400000)天前 HH:mm
		当st>=96*3600000 则: yyyy-MM-dd HH:mm
		*/
		if(st < 60000){
			return "刚刚";
		}
		
		if(st >= 60000 && st<3600000){
			return (int)(st/60000) + "分钟前";
		}
		
		if(st >= 3600000 && st<24*3600000){
			return (int)(st/3600000) + "小时前";
		}
		
		int indexOf = timeStr.indexOf(":");
		int lastIndexOf = timeStr.lastIndexOf(":");
		
		if(st  >=  24 * 3600000  &&  st<96 * 3600000){
			return new StringBuffer().append((int)(st/86400000)).append("天前").
					append(timeStr.substring(indexOf-2,indexOf)).append(":").
					append(timeStr.substring(lastIndexOf-2,lastIndexOf)).toString();
		}
		
		if(st>=96*3600000){
			return  timeStr.substring(0,lastIndexOf); 
		}
		
		Logger.getLogger(DateUtil.class).warn("获取时间时,传入的str有问题");
		
		return null;
		
	}
	
	/**
	 * 查询该毫秒数是否超过某时刻到当前时间的距离
	 * @param num
	 * @return
	 */
	  public static boolean isDefferSup(int num,String dateStr){
			SimpleDateFormat sdf=new SimpleDateFormat(TargetEnum.yyyy_MM_dd_HH_mm_ss.toString());  
			try {
				Date strTime=sdf.parse(dateStr);
				Calendar cl=Calendar.getInstance();
				 cl.setTime(strTime);
				Calendar clNow=Calendar.getInstance();
				long st=clNow.getTimeInMillis()-cl.getTimeInMillis();
				if(num<st){
					return true;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		  return false;
	  }
	  
	  /**
	   * 两个时间对比
	   * @param date1
	   * @param date2
	   * @return 返回负数则表示date1小于date2 反之则大  返回0表示相等
	   */
	  public static long compareDate(Date date1, Date date2){
		  	Calendar c1 = Calendar.getInstance();
			c1.setTime(date1);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(date1);
			return (c1.getTimeInMillis() - c2.getTimeInMillis());
	  }
	  
	  /**
	   * 两个时间对比
	   * @param date1
	   * @param date2
	   * @return 返回负数则表示date1小于date2 反之则大  返回0表示相等
	   */
	  public static long compareDate(String date1, String date2){
		  SimpleDateFormat sdf = new SimpleDateFormat(TargetEnum.yyyy_MM_dd_HH_mm_ss.toString());  
			Date d1 = null;
			Date d2 = null;
			try {
				 d1 = sdf.parse(date1);
				 d2 = sdf.parse(date2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		  	Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(d2);
			return (c1.getTimeInMillis() - c2.getTimeInMillis());
	  }
	  
	  /**
	   * 获取该月第一天
	   * @param date
	   * @return
	   */
	  public static Date getFirstDay(Date date){
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  //calendar.add(Calendar.MONTH, -1);
		  calendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		  return calendar.getTime();
	  }
	  
	  /**
	   * 获取该月最后一天
	   * @param date
	   * @return
	   */
	  public static Date getLastDay(Date date){
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.MONTH, 1);
		  calendar.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
		  return calendar.getTime();
	  }
	  
	  /**
	   * 获取该月第一天
	   * @param target 格式
	   * @param date 时间
	   * @return
	 * @throws ParseException 
	   */
	  public static Date getFirstDay(TargetEnum target, String date){
		  try {
			return getFirstDay(new SimpleDateFormat(target.toString()).parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  return getFirstDay();
	  }

	  /**
	   * 获取该月最后一天
	   * @param target 格式
	   * @param date 时间
	   * @return
	 * @throws ParseException 
	   */
	  public static Date getLastDay(TargetEnum target, String date){
		  try {
			return getLastDay(new SimpleDateFormat(target.toString()).parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  return getLastDay();
	  }
	  
	  /**
	   * 获取该月第一天
	   * @param date
	   * @return
	   */
	  public static Date getFirstDay(){
		  return getFirstDay(new Date());
	  }

	  /**
	   * 获取该月最后一天
	   * @param date
	   * @return
	   */
	  public static Date getLastDay(){
		  return getLastDay(new Date());
	  }
	  
	/**
	 * String类型转成Date类型
	 * @param dateStr
	 * @return
	 */
	public static Date getFormatDate(String dateStr) {
		Date date = null;
		try {
			date = new SimpleDateFormat(TargetEnum.yyyy_MM_dd.toString()).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getNow(){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		return sdf.format(now);
	}
	
	public static String getYear(){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(now);
	}
	
	public static String getOneYearAgo(){
		Calendar c1 = Calendar.getInstance();
		Date now = new Date();
		c1.setTime(now);
		c1.add(Calendar.YEAR,-1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(c1.getTime());
	}
	
	public static String getTwoYearAgo(){
		Calendar c1 = Calendar.getInstance();
		Date now = new Date();
		c1.setTime(now);
		c1.add(Calendar.YEAR,-2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(c1.getTime());
	}
	
	/** 
	* start 
	* 周开始时间戳 
	*/  
	public static String getWeekStartTime(int count) {  
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyyMMdd", Locale.getDefault());  
	    Calendar calnow=Calendar.getInstance();
	    calnow.setTime(new Date()); 
	    int week=calnow.get(Calendar.DAY_OF_WEEK);
	    Calendar cal = Calendar.getInstance(); 
	    // 获取星期一开始时间戳  
	    if (week==1) {
	    	cal.add(Calendar.DATE, -7*(1-count));
	    	cal.set(Calendar. DAY_OF_WEEK, Calendar.MONDAY);  
		}else{
			cal.add(Calendar.DATE, 7*count);
			cal.set(Calendar. DAY_OF_WEEK, Calendar.MONDAY); 
		}
	    return simpleDateFormat.format(cal.getTime()) + " 00:00:00.000";  
	}  
	  
	/** 
	* end 
	* 周结束时间戳 
	*/  
	public static String getWeekEndTime(int count) {  
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyyMMdd", Locale.getDefault());
	    Calendar calnow=Calendar.getInstance();
	    calnow.setTime(new Date()); 
	    int week=calnow.get(Calendar.DAY_OF_WEEK);
	    Calendar cal = Calendar.getInstance();  
	    // 获取星期日结束时间戳  
	    if (week==1){
	    	cal.add(Calendar.DATE, 7*count);
	    	cal.set(Calendar. DAY_OF_WEEK, Calendar.SUNDAY );  
	    }else{
	    	cal.add(Calendar.DATE, 7*(count+1));
	    	cal.set(Calendar. DAY_OF_WEEK, Calendar.SUNDAY);  
	    }
	    return simpleDateFormat.format(cal.getTime()) + " 23:59:59.999";  
	} 
	
	public static void main(String[] args) throws ParseException {
		//System.out.println(new SimpleDateFormat(TargetEnum.yyyy_MM.toString()).parse("2014-02"));
		//System.out.println(getFormatTime(TargetEnum.yyyy_MM_dd_HH_mm_ss,getFirstDay(new Date())));
		System.out.println(getWeekStartTime(0)+"-"+getWeekEndTime(0));
		System.out.println(getWeekStartTime(-1)+"-"+getWeekEndTime(-1));
		System.out.println(getWeekStartTime(-2)+"-"+getWeekEndTime(-2));
		System.out.println(getWeekStartTime(3)+"-"+getWeekEndTime(3));
		System.out.println(getWeekStartTime(4)+"-"+getWeekEndTime(4));
		System.out.println(getWeekStartTime(5)+"-"+getWeekEndTime(5));
	}
}
