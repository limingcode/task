<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>首页</title>
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/reset.pchlin.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/public.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/index.css">
    </head>
    <body>
    <!-- begin 头部 -->
    <div class="header">
        <div class="h_left">
            <a class="logo" href="<%=path%>/user/prepareLessons.jhtml"></a>
        </div>
        <div class="h_right">
            <div class="h_right_r">
                <a href="javascript://" class="r_item manager">
                    <span class="manager_avatar">&nbsp;</span>
                    <font>${teacherName}</font>
                </a>
                <div class="current_state">
                    <span>备课平台</span>
                    <ul class="r_menu_list">
                        <li><a href="<%=path%>/user/statis.jhtml">数据分析</a></li>
                        <li><a href="<%=path%>/question/input.jhtml">作业平台</a></li>
                    </ul>
                </div>
                <a class="quit" href="<%=path%>/user/logout.jhtml">退出</a>
            </div>
        </div>
    </div>
    <!-- end 头部 -->

    <!-- begin 左边导航栏 -->
    <div class="left">
        <ul class="nav">
            <li class="item item4 active">
                <a href="<%=path%>/prepareLesson/toHomePage.jhtml">公共课件管理</a>
            </li>
            <c:if test="${isTeacher}">
            	<li class="item item5">
	                <a href="<%=path%>/personFile/toChangeStudentHead.jhtml?oaId=${oaId}">学生头像管理</a>
	            </li>
            </c:if>
        </ul>
    </div>
    <!-- end 左边导航栏 -->

    <!-- begin 右边内容 -->
    <div class="right">
        <iframe class="iframe" src="<%=path%>/prepareLesson/toHomePage.jhtml" frameborder="0" height="100%" width="100%" marginheight="0" marginwidth="0" scrolling="auto"></iframe>
    </div>
    <!-- end 右边内容 -->
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/index.js"></script>
    </body>
</html>