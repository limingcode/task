<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>班级进度查询</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/js/jedate/skin/jedate.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/teachData.css?20180919">
  </head>
  <body>
    <!-- begin 筛选列表 -->
    <div class="wa_box filter_list">
        <div class="filter_item">
            <span class="filter_type filter_type2">学期</span>
            <ul>
            	<c:forEach var="period" items="${periodList}">
               		<li><label class="sky_radio"><input type="radio" name="period" value="${period.id}"/><span class="mark"></span>${period.year}${period.name}</label></li>
            	</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type4">科目</span>
            <ul>
            	<c:forEach var="subject" items="${subjectList}">
               		<li><label class="sky_radio"><input type="radio" name="subject" value="${subject.code}"/><span class="mark"></span>${subject.name}</label></li>
            	</c:forEach>
            </ul>
        </div>
    </div>
    <!-- end 筛选列表 -->
    <!-- begin 班级筛选结果 -->
    <div class="wa_box teach_data">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span class="current">班级筛选</span>
            </div>
        </div>
        <table class="wa_table teach_table">
            <thead>
                <tr>
                    <th rowspan="2">课次</th>
                    <th rowspan="2">层次</th>
                    <th colspan="4">一年级</th>
                    <th colspan="4">二年级</th>
                    <th colspan="4">三年级</th>
                    <th colspan="4">四年级</th>
                    <th colspan="4">五年级</th>
                    <th colspan="4">六年级</th>
                    <th colspan="4">七年级</th>
                    <th colspan="4">八年级</th>
                    <th colspan="4">九年级</th>
                    <th colspan="4">高一</th>
                    <th colspan="4">高二</th>
                    <th colspan="4">高三</th>
                </tr>
                <tr>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                    <th>预演</th>
                    <th>课件</th>
                    <th>说课</th>
                    <th>实录</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
    </div>
    <!-- end 班级筛选结果 -->
    <script type="text/javascript" src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/layer.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/public.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach/js/common.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/teachData.js?20180919"></script>
  </body>
</html>