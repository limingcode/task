/*
 * @(#)Validator.java Time: 2013-8-22
 *
 * Copyright 2013 xuedou.com All rights reserved.
 */

package com.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字段校验工具类
 * @author jinmingming jin_ming_ming@qq.com
 * @version 1.0, 2013-8-22
 */
public class Validator {

	/**
	 * 必填字段
	 * @param value
	 * @return
	 */
	public static boolean validateRequired(String value) {
		boolean isFieldValid = false;
		if (value != null && value.trim().length() > 0) {
			isFieldValid = true;
		}
		return isFieldValid;
	}

	/**
	 * 验证是否为数字字段
	 * @param value
	 * @return
	 */
	public static boolean validateInt(String value) {
		boolean isFieldValid = false;
		try {
			Integer.parseInt(value);
			isFieldValid = true;
		} catch (Exception e) {
			isFieldValid = false;
		}
		return isFieldValid;
	}

	/**
	 * 字段串长度限制
	 * @param value
	 * @param minLength	最大长度
	 * @param maxLength	最小长度
	 * @return
	 */
	public static boolean validateLength(String value, int minLength,
			int maxLength) {
		String validatedValue = value;
		if (!validateRequired(value)) {
			validatedValue = "";
		}
		return (validatedValue.length() >= minLength && validatedValue.length() <= maxLength);
	}

	/**
	 * 字段范围
	 * @param value
	 * @param min 最小数
	 * @param max 最大数
	 * @return
	 */
	public static boolean validateRange(int value, int min, int max) {
        return (value >= min && value <= max);
    }

	/**
	 * 判断针对允许的选项列表进行的选择
	 * @param options
	 * @param value
	 * @return
	 */
	public static boolean validateOption(Object[] options, Object value) {
        boolean isValidValue = false;
        try {
            List<?> list = Arrays.asList(options);
            if (list != null) {
                isValidValue = list.contains(value);
            }
        } catch (Exception e) {
        }
        return isValidValue;
    }
	
	/**
	 * 判断内容是否为日期时间格式
	 * @param value
	 * @return
	 */
	public static boolean validateDate(String value){
        return matchPattern(value, ValidatorEnum.CHECK_DATA_ISO);
	}
	
	/**
	 * 正则表达式验证
	 * @param value
	 * @param expression
	 * @return
	 */
	public static boolean matchPattern(String value, ValidatorEnum expression) {
        boolean match = false;
        if (validateRequired(value)) {
            match = Pattern.matches(expression.getValue(), value);
        }
        return match;
    }

	/**
	 * 过滤用户输入，敏感字符转换为其对应的字符实体来清理 HTML
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String filter(String value) {
		if (value == null) {
			return null;
		}
		StringBuffer result = new StringBuffer(value.length());
		long len = value.length();
		for (int i = 0; i < len ; i ++) {
			switch (value.charAt(i)) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#39;");
				break;
			case '%':
				result.append("&#37;");
				break;
			case ';':
				result.append("&#59;");
				break;
			case '(':
				result.append("&#40;");
				break;
			case ')':
				result.append("&#41;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '+':
				result.append("&#43;");
				break;
			default:
				result.append(value.charAt(i));
				break;
			}
			return result.toString();
		}
		return value;
	}
	
	public static void main(String[] args) {
		System.out.println(ValidatorEnum.CHECK_EMAIL.getValue());
		System.out.println(matchPattern("2013/09/24 17:34:23:1222", ValidatorEnum.CHECK_DATA_ISO));
	}
}
