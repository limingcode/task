<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<c:if test="${param.type eq 6}">
	<title>学生作业统计</title>
</c:if>
<c:if test="${param.type ne 6}">
	<title>学生登录统计</title>
</c:if>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/reset.pchlin.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/sys/js/jedate/skin/jedate.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/public.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/questionManage.css">
</head>
<style>
	.filter_item span{
		color:#451168;
		display:inline-block;
		width: 60px;
		text-align: right;
	}
	.filter_item select{
		padding:6px;
		width: 166px;
	}
	
	.filter_item label{
		margin-right:20px;
		display: block;
		float:left;
		margin-bottom: 10px;
		margin-top: 10px;
	}
	.filter_list .filter_item {
    	border-bottom: none;
	}
</style>
<body>
	<!-- begin 筛选列表 -->
	<div class="wa_box filter_list">
		<div class="filter_item" style="overflow: hidden;">
				<label for="class">
					<span>班级</span>
					<input type="text" id="className" placeholder="请输入班级">
				</label>
				<label for="studentNum">
					<span>学生编号</span>
					<input type="text" id="studentNum" placeholder="请输入学生编号">
				</label>
				<label for="studentName">
					<span>学生姓名</span>
					<input type="text" id="studentName" placeholder="请输入学生姓名">
				</label>
				<label for="teacherName">
					<span>教师</span>
					<input type="text" id="teacherName" placeholder="请输入教师">
				</label>
				<label for="nianJi">
					<span>年级</span>
					<select id="grade">
						<option value="">请选择</option>
						<c:forEach var="grade" items="${gradeList}">
							<c:if test="${grade.id lt 7 }">
								<option value="${grade.code}">${grade.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</label>
				<c:if test="${param.type eq 5 or param.type eq 6}">
					<label for="loginDate">
						<span>${param.type eq 5 ? '登录' : '提交'}时间</span>
						<input class="date_select" id="loginDateStart" type="text" placeholder="" readonly name="openTime"> -
						<input class="date_select" id="loginDateEnd" type="text" placeholder="" readonly name="openTime">
					</label>
				</c:if>
		</div>
		<div class="filter_btn">
			<button class="sol_btn" onclick="getPage(1)">查询</button>
			<button class="sol_btn" onclick="exportExcel()">导出</button>
			<!-- <button class="sol_btn" onclick="input()">录入</button> -->
		</div>
	</div>
	<!-- end 筛选列表 -->

	<!-- begin 题库列表 -->
	<div class="wa_box q_list">
		<div class="wa_table_oper">
			<div class="table_name">
				<span class="current">学生登录统计</span>
			</div>
		</div>
		<table class="wa_table">
			<thead>
				<tr>
					<th style="min-width: 1em;">序号</th>
					<th style="min-width: 4em;">学生编号</th>
			        <th style="">学生姓名</th>
			        <th style="min-width: 4em;">在读班级</th>
			        <th style="min-width: 4em;">教师</th>
			        <th style="min-width: 4em;">年级</th>
			        <c:if test="${param.type eq 3}">
			        	<th style="min-width: 10em;">App版本</th>
			        </c:if>
			        <c:if test="${param.type eq 5}">
			        	<th style="min-width: 4em;">登录次数</th>
			        </c:if>
			        <c:if test="${param.type eq 6}">
			        	<th style="min-width: 4em;">完成作业次数</th>
			        </c:if>
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
	<!-- end 题库列表 -->
	<script type="text/javascript" src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/jedate/jquery.jedate.min.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/space_underline.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/public.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/layer.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/pagination.js"></script>
	<script type="text/javascript">
		$(function(){
			$.jeDate('#loginDateStart',{
		        format:"YYYY-MM-DD",
		        maxDate:$.nowDate({DD:0})
		    });
			$.jeDate('#loginDateEnd',{
		        format:"YYYY-MM-DD",
		        maxDate:$.nowDate({DD:0})
		    });
			
			getPage(1);
		});
		
		function getPage(currPage){
			var index = layer.load(1, {
				shade : [ 0.1, '#fff' ] // 0.1透明度的白色背景
			});
			var className = $("#className").val();
			var studentNum = $("#studentNum").val();
			var studentName = $("#studentName").val();
			var teacherName = $("#teacherName").val();
			var grade = $("#grade").val();
			var loginDateStart = $("#loginDateStart").val();
			var loginDateEnd = $("#loginDateEnd").val();
			$.ajax({
				type : "POST",
				url : getRootPath() + "/useAppStat/getAppAndWorkStat.jhtml",
				cache : false,
				data : {
					'type' : '${param.type}',
					'currPage' : currPage,
					'pageSize' : 16,
					'className' : className,
					'studentNum' : studentNum,
					'studentName' : studentName,
					'teacherName' : teacherName,
					'grade' : grade,
					'loginDateStart' : loginDateStart,
					'loginDateEnd' : loginDateEnd
				},
				dataType : "json",
				success : function(data) {
					console.log(data);
					write(data);
					$('.pagination').children('ul').html(toolBarCtrl(data));
					layer.close(index);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					layer.close(index);
					layer.msg('请求出错，请稍后重试!');
				}
			});
		}

		var type = '${param.type}';
		
		function write(data){
			var html = "";
			$.each(data.dataList, function(i, elem){
				html += '<tr>';
				html += '<td>'+ (i+1) +'</td>';
				html += '<td>'+ elem.code +'</td>';
				html += '<td>'+ elem.name +'</td>';
				html += '<td>'+ elem.courseName +'</td>';
				html += '<td>'+ elem.teacherName +'</td>';
				html += '<td>'+ elem.gradeName +'</td>';
				if (type == 3){
					html += '<td>'+ elem.message +'</td>';
				}
				if (type == 5 || type == 6) {
					html += '<td>'+ elem.num +'</td>';
				}
				html += '</tr>';
			});
			$('.wa_table').find('tbody').html(html);
		}
		
		function exportExcel(){
			window.open(getRootPath() + "/useAppStat/exportExcel.jhtml?type=${param.type}");
		}
	</script>
</body>
</html>
