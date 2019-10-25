package com.skyedu.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

public class QuestionExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception ex) {
		ex.printStackTrace();
		if (ex instanceof QuestionException) {
			printWrite(JSONObject.toJSONString((QuestionException)ex), response);
		} 
		return new ModelAndView();
	}

	/**
	 * 将错误信息添加到response中
	 * 
	 * @param msg
	 * @param response
	 * @throws IOException
	 */
	public static void printWrite(String msg, HttpServletResponse response) {
		try {
			PrintWriter pw = response.getWriter();
			pw.write(msg);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
