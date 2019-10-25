/*
 * @(#)TimeEnum.java Time: 2013-1-3
 *
 * Copyright 2013 xuedou.com All rights reserved.
 */

package com.util;

/**
 *<pre>类说明</pre>
 *<b>功能描述：</b>
 * 主要用来规范一些标记,比如规范时间格式, 规范保存标记
 * @author  meiguanghui meiguanghui@xuedou.com
 * @version 1.0, 2013-1-4
 */
public enum TargetEnum {
	
	/**
	 * 表示把当前时间以yyyy-MM 格式标准输出 <br>例如:2012-12
	 */
	yyyy_MM("yyyy-MM"),
	
	/**
	 * 表示把当前时间以yyyy-MM-dd 格式标准输出 <br>例如:2012-12-31
	 */
	yyyy_MM_dd("yyyy-MM-dd"),
	
	/**
	 * 表示把当前时间以HH:mm:ss 格式格式标准输出 <br>例如:18:50:01
	 */
	HH_mm_ss("HH:mm:ss"),
	
	/**
	 * 格式为HHmmss 例如:130104
	 */
	HHmmss("HHmmss"),
	
	/**
	 * 表示把当前时间以yyyy-MM-dd HH:mm:ss 格式格式标准输出 <br>例如:2012-12-31 18:50:01
	 */
	yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
	
	/**
	 * 表示把当前时间以yyyy-MM-dd HH:mm:ss.S 格式格式标准输出 <br>例如:2012-12-31 18:50:01.120
	 */
	yyyy_MM_dd_HH_mm_ss_S("yyyy-MM-dd HH:mm:ss.S"),
	
	/**
	 *表示把当前时间以yyyyMMddHHmmssS 格式格式标准输出 <br>例如:201212311850013001
	 */
	YYYMMDDHHMMS("yyyyMMddHHmmssS"),
	
	/**
	 * 表示把当前时间以yyyyMMdd 格式格式标准输出 <br>例如:20121231
	 */
	yyyyMMdd("yyyyMMdd"),
	
	/**
	 * 表示把当前时间以yyyyMM 格式格式标准输出 <br>例如:201212
	 */
	yyyyMM("yyyyMM"),
	
	/**
	 * 表示把当前时间以yyyy 格式格式标准输出 <br>例如:2012
	 */
	yyyy("yyyy"),
	
	/**
	 * 表示把当前时间以yyyy年MM月dd日HH:mm:ss 格式格式标准输出 <br>例如:2012年12月31日 18:50:01
	 */
	yyyynianMMyueddriHH_mm_ss("yyyy年MM月dd日HH:mm:ss"), 
	
	/**
	 * 就表示 uploads
	 */
	UPLOADS("uploads"),
	;
	
	private String mob;
	TargetEnum(String str){
	   mob = str;
	}
   /**
    * 重写
    */
   @Override
	public String toString() {
		return mob;
	}
}
