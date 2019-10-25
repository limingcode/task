<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>学生头像管理</title>
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/personFile/css/normalize.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/personFile/css/m_public.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/personFile/css/cropper.min.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/personFile/css/m_student_avatar.css">
    </head>
    <body>
    <!-- begin 头部 -->
    <div class="m_header_box">
        <div class="header_cnt">
            <div class="logo fl"><a href="<%=path%>/user/prepareLessons.jhtml">Skyedu蓝天教育</a></div>
            <div class="user fr">
                <span class="name">${teacherName}</span>
                <ul class="oper">
                    <li class="item1"><a href="<%=path%>/question/input.jhtml">作业平台</a></li>
                    <li class="item2"><a href="<%=path%>/user/statis.jhtml">数据统计</a></li>
                    <li class="item4"><a href="<%=path%>/user/logout.jhtml"">退出</a></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- end 头部 -->

    <!-- begin 内容 -->
    <div class="wapper">
        <ul class="class_list clear">
        	<c:forEach var="course" items="${allCourse}" varStatus="count">
        		<li id="${course.id}" <c:if test="${count.index eq 0 }">class="active"</c:if> >${course.name}</li>
        	</c:forEach>
        </ul>
        <ul class='student_list clear'>
        	<c:forEach var="student" items="${students}">
        		<li studentid='${student.id}'>
	                <label class="item">
	                    <input type="file" />
	                    <img class="avatar" src="${not empty student.img ? student.img : '/task/sys/personFile/images/v2_user_icon5.jpg'}">
	                </label>
	                <span class="name">${student.name}</span>
	            </li>
        	</c:forEach>
        </ul>
        <div class="crop_box">
            <div class="crop_wrap">
                <div class="img_box">
                    <img id="cropImage" src="<%=path%>/sys/personFile/images/v2_user_icon1.jpg" alt="Picture">
                </div>
                <div class="preview_box clear">
                    <div class="crop_preview preview_rect">
                        <img src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                    </div>
                    <div class="crop_preview preview_round">
                        <img src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                    </div>
                </div>
                <div class="btn_box">
                    <button class="crop_btn">确定</button>
                    <button class="cancel_btn">取消</button>
                </div>
            </div>
        </div>
    </div>
    <!-- end 内容 -->

    <script type="text/javascript" src="<%=path%>/sys/personFile/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/cropper.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/layer.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/m_public.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/m_student_avatar.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/common.js"></script>
    </body>
</html>