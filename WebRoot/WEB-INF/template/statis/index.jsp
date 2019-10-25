<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">

<title>统计数据</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/reset.pchlin.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/public.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/index.css">
</head>

<body>
	<!-- begin 头部 -->
	<div class="header">
		<div class="h_left">
			<a href="<%=path%>/user/prepareLessons.jhtml"> <img
				src="sys/images/logo.png">
			</a>
		</div>
		<div class="h_right h_right2">
			<div class="h_right_l"></div>
			<div class="h_right_r">
				<a href="javascript:;" class="r_item manager"> <span
					class="manager_avatar">&nbsp;</span> <font>${teacherName}</font>
				</a>
				<div class="current_state">
					<span>数据分析</span>
					<ul class="r_menu_list">
						<li><a href="<%=path%>/prepareLesson/toPrepareLesson.jhtml">备课平台</a></li>
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
		<div class="nav nav1">
			<span class="title">作业统计管理</span>
			<ul>
				<li><a href="<%=path%>/work/scheduleClass.jhtml">班级进度查询</a></li>
				<li><a href="<%=path%>/work/scheduleStudent.jhtml">个人进度查询</a></li>
			</ul>
		</div>
		<div class="nav nav2">
			<span class="title">统计管理</span>
			<ul>
				<li><a href="<%=path%>/statistics/lessonStatisticsV2.jhtml">课件上传记录</a>
				</li>
				<li><a href="<%=path%>/feedback/toFeedBack.jhtml">课件评价</a></li>
			</ul>
		</div>
		<div class="nav nav7">
			<span class="title">App统计</span>
			<ul>
				<li><a href="<%=path%>/useAppStat/goAppAndWorkStat.jhtml?type=1">未提交作业学生</a></li>
				<li><a href="<%=path%>/useAppStat/goAppAndWorkStat.jhtml?type=2">次数少于4次学生</a></li>
				<li><a href="<%=path%>/useAppStat/goAppAndWorkStat.jhtml?type=3">App版本不是最新</a></li>
				<li><a href="<%=path%>/useAppStat/goAppAndWorkStat.jhtml?type=4">未登录App学生</a></li>
<%-- 				<li><a href="<%=path%>/useAppStat/goAppAndWorkStat.jhtml?type=5">登录统计</a></li> --%>
<%-- 				<li><a href="<%=path%>/useAppStat/goAppAndWorkStat.jhtml?type=6">作业统计</a></li> --%>
			</ul>
		</div>
	</div>
	<!-- end 左边导航栏 -->

	<!-- begin 右边内容 -->
	<div class="right">
		<iframe class="iframe" src="<%=path%>/work/scheduleClass.jhtml"
			frameborder="0" height="100%" width="100%" marginheight="0"
			marginwidth="0" scrolling="auto"></iframe>
	</div>
	<!-- end 右边内容 -->
	<!-- end 右边内容 -->
	<script type="text/javascript"
		src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/index.js"></script>
</body>
</html>
