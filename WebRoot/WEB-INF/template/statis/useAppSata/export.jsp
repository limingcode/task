<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="zh-cn">
<head>
<style type="text/css">
	body {
		font-family: 宋体;
		border: 0.5px solid;
		border-color: #C5C5C5;
	}
</style>
</head>

<body>
<%
	response.setContentType("application/vnd.ms-excel");
	int type = Integer.valueOf(request.getParameter("type"));
	if (type == 1) {
		response.setHeader("Content-Disposition", "inline; filename=" + new String("未提交作业学生.xls".getBytes(), "ISO-8859-1"));
	} else if (type == 2) {
		response.setHeader("Content-Disposition", "inline; filename=" + new String("次数少于4次学生.xls".getBytes(), "ISO-8859-1"));
	} else if (type == 3) {
		response.setHeader("Content-Disposition", "inline; filename=" + new String("App版本不是最新.xls".getBytes(), "ISO-8859-1"));
	} else if (type == 4){
		response.setHeader("Content-Disposition", "inline; filename=" + new String("未登录App学生.xls".getBytes(), "ISO-8859-1"));
	} else if (type == 5){
		response.setHeader("Content-Disposition", "inline; filename=" + new String("登录统计.xls".getBytes(), "ISO-8859-1"));
	} else {
		response.setHeader("Content-Disposition", "inline; filename=" + new String("作业统计.xls".getBytes(), "ISO-8859-1"));
	}
%>
	  <table>
        <tr>
            <td>序号</td>
            <td>学生编号</td>
            <td>学生姓名</td>
            <td>在读班级</td>
            <td>教师</td>
            <td>年级</td>
            <c:if test="${param.type eq 3}">
            	<td>App版本</td>
            </c:if>
            <c:if test="${param.type eq 5}">
	        	<td>登录次数</td>
	        </c:if>
	        <c:if test="${param.type eq 6}">
	        	<td>完成作业次数</td>
	        </c:if>
        </tr>
        <c:forEach var="data" items="${list}" varStatus="status">
	        <tr>
				<td>${status.count}</td>          
				<td>${data.code}</td>          
				<td>${data.name}</td>          
				<td>${data.courseName}</td>          
				<td>${data.teacherName}</td>          
				<td>${data.gradeName}</td>
				<c:if test="${param.type eq 3}">
	            	<td>${data.message}</td>
	            </c:if>
	            <c:if test="${param.type eq 5 or param.type eq 6}">
		        	<td>${data.num}</td>
		        </c:if>
	        </tr>
        </c:forEach>
      </table>
</body>

</html>