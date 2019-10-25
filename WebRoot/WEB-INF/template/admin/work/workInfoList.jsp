<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    
    <title>作业详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/js/jedate/skin/jedate.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/workManage.css">
  </head>
  
  <body>
  <!-- begin 组卷详情 -->
    <div class="wa_box eaxm_details">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span><a href="<%=path%>/lesson/lessonList.jhtml?lessonId=${lesson.id}">课次列表</a></span>
                <span><a href="<%=path%>/work/workList.jhtml?lesson=${lesson.id}">组卷列表</a></span>
                <span class="current">组卷详情</span>
            </div>
            <div  class="oper_btn">
                <a href="<%=path%>/question/selectQuestion.jhtml?hierarchy=${lesson.hierarchy}&workId=${work.id}" class="sol_btn">修改 / 添加</a>
            </div>
        </div>
        <table class="wa_table">
            <thead>
                <caption>${lesson.gradeName}${lesson.subjectName}科目${lesson.cateName}层次第${lesson.sortNo}课次${work.categoryName}题类</caption>
                <tr>
                    <th>排序</th>
                    <th>题目ID</th>
                    <th>题目标题</th>
                    <th>分值</th>
                    <th>标准作答时间/s</th>
                    <th>创建时间</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach items="${workInfoList }" var="workInfo">
	                <tr>
	                    <td>${workInfo.sortNo}</td>
	                    <td>${workInfo.question.iD}</td>
	                    <td>
	                    	<c:if test="${fn:length(workInfo.question.brief)<=30}">${workInfo.question.brief}</c:if> 
	                    	<c:if test="${fn:length(workInfo.question.brief)>30}">${fn:substring(workInfo.question.brief,0,30)}...</c:if> 
                    	</td>
	                    <td>${workInfo.question.score}</td>
	                    <td>${workInfo.question.standardTime}</td>
	                    <td><fmt:formatDate value="${workInfo.createDate}" type="date" dateStyle="long"/></td>
	                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- end 组卷详情 -->
    <script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
  </body>
</html>
