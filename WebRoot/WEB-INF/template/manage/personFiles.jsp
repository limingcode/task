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
		<title>个人文件管理</title>
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/personFile/css/normalize.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/personFile/css/m_public.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/personFile/css/m_files.css">
    </head>
    <body>
    
    <input type="hidden" id="oaId" value="${oaId}">
    <input type="hidden" id="dirSize" value="${dirSize}">
    
    <!-- begin 头部 -->
    <div class="m_header_box">
        <div class="header_cnt">
            <div class="logo fl"><a href="<%=path%>/user/prepareLessons.jhtml">Skyedu蓝天教育</a></div>
            <div class="user fr">
                <span class="name">${teacherName}</span>
                <ul class="oper">
                    <li class="item1"><a href="<%=path%>/question/input.jhtml">作业平台</a></li>
                    <li class="item2"><a href="<%=path%>/user/statis.jhtml">数据统计</a></li>
                    <li class="item4"><a href="<%=path%>/user/logout.jhtml">退出</a></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- end 头部 -->

    <!-- begin 文件表格 -->
    <div class="wapper">
        <div class="btn_box">
            <label class="upload_btn">
                <span>上传</span>
                <input type="file" />
            </label>
            <span class="files_space">容量：${dirTotalSize}M/500M</span>
            <div class="search">
                <input class="search_cnt" type="text" />
                <button class="search_btn"></button>
            </div>
        </div>
        <table class="files_table">
            <thead>
                <tr>
                    <th>文件</th>
                    <th>大小</th>
                    <th>上传日期</th>
                    <th>下载</th>
                </tr>
            </thead>            
        </table>

        <!-- begin 分页条 -->
        <div class="pagination">
            <ul>
            </ul>
        </div>
        <!-- end 分页条 -->
    </div>
    <!-- end 文件表格 -->

    <script type="text/javascript" src="<%=path%>/sys/personFile/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/layer.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/m_public.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/m_files.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/personFile/js/common.js"></script>
    </body>
</html>