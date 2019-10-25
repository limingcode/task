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
<link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/examManage.css">
</head>

<body>
	<!-- begin 筛选列表 -->
	<div class="wa_box filter_list">
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
			<button class="sol_btn add_course" onclick="commit(this)">添加</button>
            <button class="sol_btn delete_course" onclick="del(this)">删除</button>
		</div>
	</div>
	<!-- end 筛选列表 -->

	<!-- begin 课次列表 -->
	<div class="wa_box course_list">
		<div class="wa_table_oper">
			<div class="table_name">
				<span class="current">课次列表</span>
			</div>
			<div class="oper_btn">
				<a href="javascript://" class="sol_btn" onclick="commit(this)">添加</a>
			</div>
		</div>
		<table class="wa_table">
			<thead>
				<tr>
					<th>课次</th>
					<th>创建时间</th>
					<th>开放时间</th>
					<th>修改时间</th>
					<th>大纲</th>
					<th>列表</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${lessonList }" var="lesson" varStatus="index">
					<tr name="${lesson.iD}">
						<td>${lesson.sortNo }</td>
						<td><fmt:formatDate value="${lesson.createDate }" pattern="yyyy年MM月dd日 HH:mm"/> </td>
						<td class="start_time"> <input class="date_select" type="text" placeholder=""  readonly value="<fmt:formatDate value="${lesson.openTime }" pattern="yyyy年MM月dd日 HH:mm"/>"></td>
						<td><fmt:formatDate value="${lesson.modifyDate }"  pattern="yyyy年MM月dd日 HH:mm"/> </td>
						<td><input type="text" value="${lesson.brief }" title="${lesson.brief }" onblur="changeBrief(this,${lesson.iD})" style="width: calc(100% - 12px);border: 0;text-align:center" placeholder="请填写大纲"></td>
						<td><a href="<%=path%>/work/workList.jhtml?lesson=${lesson.iD}">详情</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- end 课次列表 -->
	<script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/jedate/jquery.jedate.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/space_underline.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/courseList.js?22"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
	<script type="text/javascript">
    	function search(){
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
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
    		window.location.href="<%=path%>/lesson/lessonList.jhtml?grade="+grade+"&subject="+subject+"&cate="+cate+"&period="+period;
    	}
    	
    	function searchForR(){
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
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
    		window.location.href="<%=path%>/lesson/lessonList.jhtml?grade="+grade+"&subject="+subject+"&cate="+cate+"&period="+period;
    	}
    	
    	function commit(el){
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
			var periodt =$("input[name='period']:checked").parent().text();
			var gradet =$("input[name='grade']:checked").parent().text();
			var subjectt =$("input[name='subject']:checked").parent().text();
			var catet =$("input[name='cate']:checked").parent().text();
			
			el.blur();
			
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
			if(confirm("是否为"+periodt+"学期"+gradet+subjectt+catet+"层次添加一个课次")){
    			window.location.href="<%=path%>/lesson/commitLesson.jhtml?period="+period+"&grade="+grade+"&subject="+subject+"&cate="+cate;
			}
    	}
    	
    	function del(el){
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
			var periodt =$("input[name='period']:checked").parent().text();
			var gradet =$("input[name='grade']:checked").parent().text();
			var subjectt =$("input[name='subject']:checked").parent().text();
			var catet =$("input[name='cate']:checked").parent().text();
			
			el.blur();
			
			if (confirm("是否删除"+periodt+"学期"+gradet+subjectt+catet+"层次的最大课次")) {
				this.blur();
				$.ajax({
		    			url:"<%=path%>/lesson/delLesson.jhtml",
		    			type:"post",
		    			data:{
		    				period : period,
		    				grade : grade,
		    				subject : subject,
		    				cate : cate
		    			},
		    			dataType:'JSON',
		    			success:function(result){
		    				var code = result.code;
		    				var message = result.message;
		    				alert(message);
		    				if (code == 100) {
									// 删除课程
								    $(".course_list .wa_table tbody tr:last").remove();
								}
							},
						error : function(data){
							var msg = data.responseText;
							eval(msg.replace(/(<script>|<\/script>)/g,''));
						}
						})
			}
		}

		function isNull(str) {
			if (str == "")
				return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		
	 $(function(){
	    $(":radio").change(function(){
	            //...statement
	            searchForR();
	     })
	})
	
	function changeBrief(obj,lessonId){
		 var brief = obj.value;
		 obj.title = brief;
		 updateLessonBrief(lessonId,brief);
	 }
	 
	 function updateLessonBrief(id,brief){
		 $.ajax({
	         url : "<%=path%>/lesson/updateLesson.jhtml",
	         type : "post",
	         data : {
	             lessonId : id,
	             brief : brief
	         },
	         dataType : 'JSON',
	         success : function(result) {
	             return true;
	         },
	         error : function(data){
					var msg = data.responseText;
					eval(msg.replace(/(<script>|<\/script>)/g,''));
				}
	     });
	}
	</script>
</body>
</html>
