package com.skyedu.exception;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.util.DatabaseContextHolder;

public class SqlExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception ex) {
		// TODO Auto-generated method stub
		ex.printStackTrace();
		if (ex instanceof SQLServerException) {
			// AthenaException为一个自定义异常
			String message = ex.getMessage();
			String customerType = DatabaseContextHolder.getCustomerType();
			if ("该连接已关闭。".equals(message)) {
				if ("backupDataSource".equals(customerType)) {
					DatabaseContextHolder.setCustomerType("dataSource");
				} else {
					DatabaseContextHolder.setCustomerType("backupDataSource");
				}
			}
			printWrite("服务器IP变更,请重试一次"+customerType, response);
		} else if (ex instanceof CannotCreateTransactionException) {
			// AthenaException为一个自定义异常
			String message = ex.getMessage();
			String customerType = DatabaseContextHolder.getCustomerType();
			if (message.indexOf("Could not open Hibernate Session for transaction")!=-1) {
				if ("backupDataSource".equals(customerType)) {
					DatabaseContextHolder.setCustomerType("dataSource");
				} else {
					DatabaseContextHolder.setCustomerType("backupDataSource");
				}
			}
			printWrite("服务器IP变更,请重试一次"+customerType, response);
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
