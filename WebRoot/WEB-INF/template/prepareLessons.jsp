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

<html lang="en" class="prepareLessons">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>用户登录</title>
<link rel="stylesheet" href="<%=path%>/sys/css/animate.css">
<link rel="stylesheet" href="<%=path%>/sys/css/style.css">
<link rel="stylesheet" href="<%=path%>/sys/css/prepareLessons.css">
</head>
<body>
	<img class="prepareLessonsBg" src="<%=path%>/sys/images/icon2_4.png"
		alt="">
	<div class="content">
		<ul>
			<li class="mr150"><a
				href="<%=path%>/prepareLesson/toPrepareLesson.jhtml"> <img
					src="<%=path%>/sys/images/icon2_1.png" alt=""> <i>备课</i>
			</a></li>
			<li class="mr150"><a href="<%=path%>/question/input.jhtml">
					<img src="<%=path%>/sys/images/icon2_2.png" alt=""> <i>蓝天SEES作业平台</i>
			</a></li>
			<li><a href="<%=path%>/user/statis.jhtml">
			<img src="<%=path%>/sys/images/icon2_3.png" alt=""> <i>数据统计</i>
			</a></li>

		</ul>
	</div>
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if IE]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<script src="<%=path%>/sys/js/jquery-3.2.1.min.js"></script>
	<script src="<%=path%>/sys/js/prepareLessons.js"></script>
	<script type="text/javascript">
		window.onload = function() {
			sessionStorage.removeItem("iframeSrc");
		    sessionStorage.removeItem("liParentActive");
		    sessionStorage.removeItem("liActive");
		};
	</script>
</body>
</html>