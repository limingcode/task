/*
 * @(#)StringUtil.java Time: 2013-4-24
 *
 * Copyright 2013 xuedou.com All rights reserved.
 */

package com.util;


/**
 *<pre>String处理类</pre>
 * 主要处理一些String串数据格式
 * @author  xj.chen
 * @version 1.0, 2013-4-24
 */
public class StringUtil {

	/**
	 * 判断某字符串是否为空或''值
	 * @param str 传入String串
	 * @return 返回Boolean值 true表示为空或'' false则反之
	 */
	public static boolean isEmpty(String str){
		boolean bol = false;
		if(null == str || "".equals(str.trim())){
			bol = true;
		}
		return bol;
	}
	
}
