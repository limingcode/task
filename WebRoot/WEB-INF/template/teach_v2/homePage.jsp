<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>备课-首页</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/homePage.css?20180919">
  </head>
  <body>
    <!-- begin 筛选列表 -->
    <div class="wa_box filter_list">
        <div class="filter_item">
            <span class="filter_type filter_type2">学期</span>
            <ul>
            	<c:forEach items="${periodList}" var="period">
	                <li><label class="sky_radio"><input type="radio" name="term" value="${period.id}" title="${period.year}${period.name}"/><span class="mark"></span>${period.year}${period.name}</label></li>
              	</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type4">科目</span>
            <ul>
            	<c:forEach items="${subjectList}" var="subject">
                	<li><label class="sky_radio"><input type="radio" name="subject" value="${subject.code}" title="${subject.name}"/><span class="mark"></span>${subject.name}</label></li>
              	</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type3">年级</span>
            <ul>
            	<c:forEach items="${gradeList}" var="grade">
	                <li><label class="sky_radio"><input type="radio" name="grade" value="${grade.code}" title="${grade.name}"/><span class="mark"></span>${grade.name}</label></li>
               	</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type5">层次</span>
            <ul>
            	<c:forEach items="${cateList}" var="cate">
	                <li><label class="sky_radio"><input type="radio" name="level" value="${cate.code}" title="${cate.name}"/><span class="mark"></span>${cate.name}</label></li>
               	</c:forEach>
            </ul>
        </div>
    </div>
    <!-- end 筛选列表 -->
    <!-- begin 课次列表 -->
    <div class="course_box clear">
        <div class="left">
        </div>
        <div class="right">
            <ul class="enter_list clear">
                <li class="enter_item item1" state="0">
                    <a href="javascript:;" cnt="课件"></a>
                </li>
                <li class="enter_item item2" state="0">
                    <a href="javascript:;" cnt="说课视频"></a>
                </li>
                <li class="enter_item item3" state="0">
                    <a href="javascript:;" cnt="实录视频"></a>
                </li>
                <li class="enter_item item4" state="0">
                    <a href="javascript:;" cnt="反馈"></a>
                </li>
            </ul>
        </div>
    </div>
    <!-- end 课次列表 -->
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/layer.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/public.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/common.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/homePage.js?2018091901"></script>
    <script type="text/javascript">
    	getLesson();
    </script>
  </body>
</html>