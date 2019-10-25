<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">

<title>题库录入后台</title>

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
			<a href="<%=path%>/user/prepareLessons.jhtml">
				<img src="sys/images/logo.png">
			</a>
		</div>
		<div class="h_right">
			<div class="h_right_l"></div>
			<div class="h_right_r">
                <a href="javascript:;" class="r_item manager">
                    <span class="manager_avatar">&nbsp;</span>
                    <font>${teacherName}</font>
                </a>
                <div class="current_state">
                    <span>作业平台</span>
                    <ul class="r_menu_list">
                        <li><a href="<%=path%>/user/statis.jhtml">数据分析</a></li>
                        <li><a href="<%=path%>/prepareLesson/toPrepareLesson.jhtml">备课平台</a></li>
                    </ul>
                </div>
                <a class="quit" href="<%=path %>/user/logout.jhtml">退出</a>
            </div>
		</div>
	</div>
	<!-- end 头部 -->

	<!-- begin 左边导航栏 -->
	<div class="left">
		<div class="nav nav1">
			<span class="title">题库管理</span>
			<ul>
				<li><a href="<%=path%>/question/questionList.jhtml">题库列表</a>
				</li>
				<li><a href="<%=path%>/question/inputQuestion.jhtml">题库录入</a>
				</li>
			</ul>
		</div>
		<div class="cut_line">. . .</div>
		<div class="nav nav2">
			<span class="title">组卷管理</span>
			<ul>
				<li><a href="<%=path%>/lesson/lessonList.jhtml">课次列表</a>
				</li>
			</ul>
		</div>
		<div class="cut_line">. . .</div>
		<div class="nav nav3">
			<span class="title">作业管理</span>
			<ul>
				<li><a href="<%=path%>/work/goPublishWork.jhtml">作业发布</a>
				</li>
			</ul>
		</div>
		<div class="cut_line">. . .</div>
		<div class="nav nav4">
			<span class="title">消息管理</span>
			<ul>
				<li><a href="<%=path%>/message/messageList.jhtml">消息列表</a>
				</li>
				<li><a href="<%=path%>/message/goPublishMessage.jhtml">消息发布</a>
				</li>
			</ul>
		</div>
		<div class="cut_line">. . .</div>
		<div class="nav nav5">
			<span class="title">图书管理</span>
			<ul>
				<li><a href="<%=path%>/imageTag/chooseBook.jhtml">图书录入</a>
				</li>
			</ul>
		</div>
		<div class="nav nav4">
			<span class="title">权限操作</span>
			<ul>
				<li><a href="<%=path%>/permission/everPermission.jhtml">个人权限操作</a>
				</li>
				<li><a href="<%=path%>/permission/classPermission.jhtml">班级权限操作</a>
				</li>
			</ul>
		</div>

		<c:if test="${fn:contains(userRoleUrls, '/role/index.jhtml')}">
			<div class="cut_line">. . .</div>
			<div class="nav nav6">
				<span class="title">权限管理</span>
				<ul>
					<li><a href="<%=path%>/role/index.jhtml">权限管理</a>
					</li>
				</ul>
			</div>
		</c:if>
	</div>
	<!-- end 左边导航栏 -->

	<!-- begin 右边内容 -->
	<div class="right">
		<iframe class="iframe" src="<%=path%>/question/questionList.jhtml"
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
