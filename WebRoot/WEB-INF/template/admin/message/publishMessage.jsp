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

<title>课次列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/js/jedate/skin/jedate.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/informManage.css">
</head>

<body>
	<!-- begin 筛选列表 -->
    <div class="wa_box filter_list">
        <div class="filter_item">
            <span class="filter_type filter_type1">校区</span>
            <ul>
            	<li><label class="sky_radio"><input type="radio"
							name="dept" value="0" checked="checked"><span
							class="mark"></span>全部校区</label></li>
				<c:forEach items="${deptList }" var="dept" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.dept==null&& index.index==0}">checked="checked"</c:if>
							type="radio" name="dept" value=${dept.code } <c:if test="${dept.code eq condition.dept }">checked="checked"</c:if> /><span class="mark"></span>${dept.name}</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type2">学期</span>
            <ul>
            	<c:forEach items="${periodList }" var="period" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.period==null && index.index==0}">checked="checked"</c:if>
							type="radio" name="period" value=${period.id } <c:if test="${period.id eq condition.period }">checked="checked"</c:if> /><span class="mark"></span>${period.year}${period.name }</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type3">年级</span>
            <ul>
            	<c:forEach items="${gradeList }" var="grade" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.grade==null&& index.index==0}">checked="checked"</c:if>
							type="radio" name="grade" value=${grade.code } <c:if test="${fn:trim(grade.code) eq condition.grade }">checked="checked"</c:if> /><span class="mark"></span>${grade.name }</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type4">科目</span>
            <ul>
            	<c:forEach items="${subjectList }" var="subject" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.subject==null&& index.index==0}">checked="checked"</c:if>
							type="radio" name="subject" value=${subject.code } <c:if test="${fn:trim(subject.code) eq condition.subject }">checked="checked"</c:if>/><span class="mark"></span>${subject.name }</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type5">层次</span>
            <ul>
            	<c:forEach items="${cateList }" var="cate" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.cate==null&& index.index==0}">checked="checked"</c:if>
							type="radio" name="cate" value=${cate.code } <c:if test="${cate.code eq condition.cate}">checked="checked"</c:if>/><span class="mark"></span>${cate.name }</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_btn">
            <button class="sol_btn" onclick="search()">查询</button>
        </div>
    </div>
    <!-- end 筛选列表 -->

	<!-- begin 消息发布 -->
    <div class="wa_box filter_list infor_issue">
    	<form id="myform">
	        <div class="filter_item">
	            <span class="filter_type">班级</span>
	            <ul id="courseList">
	                <li><label class="sky_checkbox no_icon"><span class="mark"></span>暂无班级</label></li>
	            </ul>
	        </div>
	        <div class="filter_item infor_editor">
	            <span class="filter_type">标题</span>
	            <input class="infor_title" type="text" name="title" id="title"/>
	        </div>
	        <div class="filter_item infor_editor">
	            <span class="filter_type">消息</span>
	            <textarea class="infor_cnt" id="message" name="message"></textarea>
	            <ul>
	                <li>
	                    <label class="sky_checkbox no_icon"><input type="checkbox" name="issue"/><span class="mark"></span>定时发布</label>
	                    <input class="date_select" id="issue_time" type="text" placeholder=""  readonly name="openTime">
	                </li>
	            </ul>
	        </div>
	        <div class="filter_btn">
	            <a href="javascript:;" class="sol_btn" onclick="publishMessage()">发布消息</a>
	        </div>
        </form>
    </div>
    <!-- end 消息发布 -->
    <script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/jedate/jquery.jedate.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/space_underline.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/informationIssue.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
	<script type="text/javascript">
    	function search(){
   			var dept = $("input[name='dept']:checked").val();
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
    		if (typeof(dept)=="undefined" || isNull(dept)) {
				alert("请选择校区");
				return;
			}
    		if (typeof(period)=="undefined" || isNull(period)) {
				alert("请选择学期");
				return;
			}
    		if (typeof(grade)=="undefined" || isNull(grade)) {
				alert("请选择年级");
				return;
			}
			if (typeof(subject)=="undefined"||isNull(subject)) {
				alert("请选择科目");
				return;
			}
			if (typeof(cate)=="undefined"||isNull(cate)) {
				alert("请选择层次");
				return;
			}
    		$.ajax({
                url : "<%=path%>/work/getCourseAndLesson.jhtml",
                type : "post",
                data : {
                    dept : dept,
                    period : period,
                    grade : grade,
                    subject : subject,
                    cate : cate,
                },
                dataType : 'JSON',
                success : function(result) {
                    var courseList = result.courseList;
                    var lessonList = result.lessonList;
                    doCourseList(courseList);
                }
            });
    	}
    	
    	function searchForR(){
   			var dept = $("input[name='dept']:checked").val();
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
    		if (typeof(dept)=="undefined" || isNull(dept)) {
				return;
			}
    		if (typeof(period)=="undefined" || isNull(period)) {
				return;
			}
    		if (typeof(grade)=="undefined" || isNull(grade)) {
				return;
			}
			if (typeof(subject)=="undefined"||isNull(subject)) {
				return;
			}
			if (typeof(cate)=="undefined"||isNull(cate)) {
				return;
			}
    		$.ajax({
                url : "<%=path%>/work/getCourseAndLesson.jhtml",
                type : "post",
                data : {
                    dept : dept,
                    period : period,
                    grade : grade,
                    subject : subject,
                    cate : cate,
                },
                dataType : 'JSON',
                success : function(result) {
                    var courseList = result.courseList;
                    var lessonList = result.lessonList;
                    doCourseList(courseList);
                }
            });
    	}
   
   		function doCourseList(courseList){
    		var html="";
    		for (var i=0;i<courseList.length;i++){
    			var course = courseList[i];
    			html = html + "<li><label class='sky_checkbox no_icon'><input type='checkbox' name='courses' value='"+course.id+"'/><span class='mark'></span>"+course.name+"</label></li>";
    		}
    		if(isNull(html)){
    			html = "<li><label class='sky_checkbox no_icon'><span class='mark'></span>暂无班级</label></li>";
    		}
    		$("#courseList").html(html);
    	}

		function isNull(str) {
			if (str == "")
				return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		
		function publishMessage() {
				var courses = new Array();
				$(".infor_issue input[name='courses']:checked").each(function(){
					courses.push($(this).val());
				});
				var title = $("#title").val();
				var message = $("#message").val();
	    		if (typeof(courses)=="undefined" || isNull(courses)) {
					alert("请选择班级");
					return;
				}
				if (typeof(title)=="undefined" || isNull(title)) {
					alert("请填写标题");
					return;
				}
				if (typeof(message)=="undefined" || isNull(message)) {
					alert("请填写需要发布的消息");
					return;
				}
				if ( message.length>40) {
					alert("消息长度应小于40");
					return;
				}
				skyAlert.loading("正在发布中。。。。");
				$.ajax({
					url: "<%=path%>/message/publishMessage.jhtml",
					type: "post",
					dataType:"json",
					async: true,
					data: $("#myform").serializeArray(),
					success: function(result) {
						var message = result.message;
						skyAlert.loading(false);
						alert(message);
					},
					error: function(result) {
						var message = result.message;
						skyAlert.loading(false);
						alert(message);
					}
				});
				
				return false;
			}
			
		$(function(){
	    $(":radio").change(function(){
	            //...statement
	            searchForR();
	     })
	})
	</script>
</body>
</html>
