<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>反馈列表</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/js/jedate/skin/jedate.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/feedbackList.css">
  </head>
  <body>
  	<input type="hidden" id="lessonId" value="${param.lessonId}">
    <!-- begin 筛选列表 -->
    <div class="wa_box filter_list">
    	<c:if test="${param.lessonId eq null}">
	        <div class="filter_item select">
	            <span class="filter_type filter_type4">科目</span>
	            <ul>
	            	<c:forEach items="${subjectList}" var="subject">
	                	<li><label class="sky_radio"><input type="radio" name="subject" value="${subject.code}" title="${subject.name}"/><span class="mark"></span>${subject.name}</label></li>
	              	</c:forEach>
	            </ul>
	        </div>
	        <div class="filter_item select">
	            <span class="filter_type filter_type2">学期</span>
	            <ul>
	            	<c:forEach items="${periodList}" var="period">
		                <li><label class="sky_radio"><input type="radio" name="term" value="${period.id}" title="${period.year}${period.name}"/><span class="mark"></span>${period.year}${period.name}</label></li>
	              	</c:forEach>
	            </ul>
	        </div>
	        <div class="filter_item select">
	            <span class="filter_type filter_type3">年级</span>
	            <ul>
	            	<c:forEach items="${gradeList}" var="grade">
		                <li><label class="sky_radio"><input type="radio" name="grade" value="${grade.code}" title="${grade.name}"/><span class="mark"></span>${grade.name}</label></li>
	               	</c:forEach>
	            </ul>
	        </div>
	        <div class="filter_item select">
	            <span class="filter_type filter_type5">层次</span>
	            <ul>
	            	<c:forEach items="${cateList}" var="cate">
		                <li><label class="sky_radio"><input type="radio" name="level" value="${cate.code}" title="${cate.name}"/><span class="mark"></span>${cate.name}</label></li>
	               	</c:forEach>
	            </ul>
	        </div>
	        <div class="filter_item lessonDiv force_hide">
	            <span class="filter_type filter_type7">课次</span>
	            <ul>
	            </ul>
	        </div>
	    </c:if>
        <div class="filter_item">
            <span class="filter_type filter_type7">状态</span>
            <ul>
                <li><label class="sky_radio"><input type="radio" name="feedbackstate" value="1"/><span class="mark"></span>赞</label></li>
                <li><label class="sky_radio"><input type="radio" name="feedbackstate" value="0"/><span class="mark"></span>不赞</label></li>
            </ul>
        </div>
<!--         <div class="filter_item"> -->
<!--             <span class="filter_type">时间</span> -->
<!--             <span class="time_picker"><input class="date_select" id="start_time" onblur="getPage(1)" type="text" placeholder=""  readonly></span> -->
<!--         </div> -->
        <div class="filter_item">
            <span class="filter_type filter_type8">姓名</span>
            <ul>
                <li><label class="name_filter"><input type="text" id="name" /></label></li>
            </ul>
        </div>
    </div>
    <!-- end 筛选列表 -->
    <!-- begin templet表格 -->
    <div class="wa_box feedback_box">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span><a href="<%=path%>/prepareLesson/toHomePage.jhtml">首页</a></span>
                <span class="current">课件反馈
                	<c:if test="${param.lessonId ne null}">
                	 - ${param.title}
                	</c:if>
                </span>
            </div>
        </div>
        <table class="wa_table">
            <thead>
                <tr>
                    <th style="width:7%">姓名</th>
                    <th style="width:7%">学期</th>
                    <th style="width:7%">科目</th>
                    <th style="width:7%">年级</th>
                    <th style="width:7%">层次</th>
                    <th style="width:7%">课次</th>
                    <th style="width:10%">时间</th>
                    <th style="width:7%">状态</th>
                    <th>建议</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>

        <!-- begin 分页条 -->
        <div class="pagination">
            <ul>
            </ul>
        </div>
        <!-- end 分页条 -->
    </div>
    <!-- end templet表格 -->
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/jedate/jquery.jedate.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/layer.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/public.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/common.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/feedbackList.js"></script>
    <script type="text/javascript">
    	if (${param.lessonId eq null}) {
			getLesson(); 
		}
    	getPage(1);
    </script>
  </body>
</html>