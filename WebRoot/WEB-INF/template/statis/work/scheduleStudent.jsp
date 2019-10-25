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
<title>个人进度查询</title>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/reset.pchlin.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/public.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/js/jedate/skin/jedate.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/workManage.css">
</head>
<body>
	<!-- begin 筛选列表 -->
	<div class="wa_box filter_list">
		<div class="filter_item">
			<span class="filter_type filter_type8">姓名</span>
			<ul>
				<li><label><input type="text" id="studentName"
						onblur="search()" /> </label>
				</li>
			</ul>
		</div>
		<div class="filter_btn">
			<button class="sol_btn" onclick="search();">查询</button>
		</div>
	</div>
	<!-- end 筛选列表 -->
	<!-- begin 学生筛选结果 -->
	<div class="wa_box student_result">
		<div class="wa_table_oper">
			<div class="table_name">
				<span class="current">学生筛选</span>
			</div>
		</div>
		<table class="wa_table">
			<thead>
				<tr>
					<th>姓名</th>
					<th>班级</th>
					<th>教师</th>
					<th>学科</th>
					<th>年级</th>
					<th>层次</th>
					<th>学期</th>
					<th>学年</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="studentList">
			</tbody>
		</table>
	</div>
	<!-- end 学生筛选结果 -->
</body>
<script type="text/javascript"
	src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/sys/js/public.js"></script>
<script type="text/javascript">
  function search(){
  	var studentName = $("#studentName").val();
  	$.ajax({
  		url : "<%=path%>/work/searchStudent.jhtml",
  		type : "post",
  		data : {
  			studentName : studentName
  		},
  		dataType : 'JSON',
  		success : function(result){
  			var studentList = result.studentList;
  			var html = "";
  			for (var i=0;i<studentList.length;i++){
    			var student = studentList[i];
    			html = html +"<tr><td>"+student.studentName+"</td><td>"+student.courseName+"</td><td>"+student.teacherName+"</td><td>"+student.subjectName+"</td><td>"+student.gradeName+"</td><td>"+student.cateName+"</td><td>"+student.periodName+"</td><td>"+student.periodYear+"</td><td><a href=\"<%=path%>/work/scheduleStudentDetail.jhtml?studentId="+ student.studentId + "&courseId="+ student.courseId + "&studentName="+ student.studentName+ "\">详情</a></td></tr>";
						}
						$("#studentList").html(html);
					}
				});

	}

	$("#studentName").bind('keydown', function(event) {
		if (event.keyCode == "13") {
			this.blur();
		}
	});
</script>
</html>