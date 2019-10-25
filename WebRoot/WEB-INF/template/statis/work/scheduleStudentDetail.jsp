<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    
    <title>班级进度查询</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/js/jedate/skin/jedate.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/workManage.css">
  </head>
  <body>
    <!-- begin 班级进度详情列表 -->
    <div class="wa_box s_detail_list">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span><a href="<%=path %>/work/scheduleStudent.jhtml">个人进度查询</a></span>
                <span class="current">班级筛选</span>
            </div>
            <div  class="oper_btn">
                <input type="number" id="sortNo" placeholder="请输入课次" onblur="select()"/>
                <a href="javascript:;" class="sol_btn" onclick="select()">查询</a>
            </div>
        </div>
        <table class="wa_table">
            <thead>
                <caption>${studentName }的${lesson.gradeName}${lesson.cateName }${course.name}班第${lesson.sortNo}课次${category.categoryName}作业</caption>
                <tr>
                    <th>题类</th>
                    <th>完成</th>
                    <th>得分</th>
                    <th>作答用时</th>
                    <th>正确率</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach items="${resultCardList }" var="resultCard">
            		<tr>
	                    <td>${resultCard.categoryName }</td>
	                    <td><c:if test="${resultCard.hasDealed==0 }"><font color="red">未完成</font></c:if><c:if test="${resultCard.hasDealed==1 }"><font color="green">完成</font></c:if> </td>
	                    <td><c:if test="${resultCard.hasDealed==1 }">${resultCard.score }</c:if></td>
	                    <td><c:if test="${resultCard.hasDealed==1 }">${resultCard.totalTime }s</c:if></td>
	                    <td><c:if test="${resultCard.hasDealed==1 }">${resultCard.correct }</c:if></td>
	                    <td><c:if test="${resultCard.hasDealed==1 }"><a href="<%=path%>/work/scheduleQuestionDetail2.jhtml?studentId=${studentId}&courseId=${course.id }&sortNo=${lesson.sortNo }&resultCardId=${resultCard.id}">详情</a></c:if></td>
               		</tr>
            	</c:forEach>
            </tbody>
        </table>
    </div>
    <!-- end 班级进度详情列表 -->

    <script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/jedate/jquery.jedate.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/space_underline.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/workIssue.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
    <script type="text/javascript">
    	function select(){
    		var studentId = '${studentId}';
    		var sortNo = $("#sortNo").val();
    		var studentName = '${studentName}';
    		var courseId = '${course.id}';
    		if (typeof(sortNo)=="undefined" || isNull(sortNo)) {
				alert("请输入课次");
				return;
			}
			window.location.href = "<%=path%>/work/scheduleStudentDetail.jhtml?studentId="+studentId+"&courseId="+courseId+"&sortNo="+sortNo+"&studentName="+studentName;
    	}
    	
    	function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
    	
    	$("#sortNo").bind('keydown', function(event) {
			if (event.keyCode == "13") {
				select();
				this.blur();
			}
		});
    </script>
  </body>
</html>
