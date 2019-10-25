<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>备课-首页</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/cropper.min.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/studentAvatar.css">
  </head>
  <body>
    <!-- begin 筛选列表 -->
    <div class="wa_box filter_list">
        <div class="filter_item">
            <span class="filter_type">班级</span>
            <ul>
            	<c:forEach var="course" items="${allCourse}" varStatus="count">
	                <li><label class="sky_radio"><input type="radio" name="courses" id="${course.id}" <c:if test="${count.index eq 0 }">checked="checked"</c:if>><span class="mark"></span>${course.name}</label></li>
        		</c:forEach>
            </ul>
        </div>
    </div>
    <!-- end 筛选列表 -->
    <!-- begin 学生头像列表 -->
    <div class="wa_box avatar_box">
        <ul class='student_list clear'>
            <c:forEach var="student" items="${students}">
        		<li studentid='${student.id}'>
	                <label class="item">
	                    <input type="file" />
	                    <img class="avatar" src="${not empty student.img ? student.img : '/task/sys/images/v2_user_icon5.jpg'}">
	                </label>
	                <span class="name">${student.name}</span>
	            </li>
        	</c:forEach>
        </ul>
    </div>
    <!-- end 学生头像列表 -->
    <!-- begin 头像截取弹窗 -->
    <div class="crop_box">
        <div class="crop_wrap">
            <div class="img_box">
                <img id="cropImage" src="<%=path%>/sys/images/v2_user_icon1.jpg" alt="Picture">
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
                <button class="hol_btn crop_btn">确定</button>
                <button class="hol_btn cancel_btn">取消</button>
            </div>
        </div>
    </div>
    <!-- end 头像截取弹窗 -->
    <script type="text/javascript" src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/layer.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/public.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/cropper.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/studentAvatar.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/common.js"></script>
  </body>
</html>