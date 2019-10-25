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
                <span><a href="<%=path %>/work/scheduleClass.jhtml">班级进度查询</a></span>
                <span class="current">班级筛选</span>
            </div>
        </div>
        <table class="wa_table">
            <thead>
                <caption>${lesson.gradeName}${lesson.cateName }${course.name}班第${lesson.sortNo}课次作业</caption>
                <tr>
                    <th></th>
                    <th></th>
                    <th colspan="3">阅读</th>
                    <th colspan="3">口语</th>
                    <th colspan="3">听力</th>
                    <th colspan="3">语法</th>
                    <th colspan="3">词汇</th>
                </tr>
                <tr>
                    <th>编号</th>
                    <th>姓名</th>
                    <th>得分</th>
                    <th>正确率</th>
                    <th>操作</th>
                    <th>得分</th>
                    <th>正确率</th>
                    <th>操作</th>
                    <th>得分</th>
                    <th>正确率</th>
                    <th>操作</th>
                    <th>得分</th>
                    <th>正确率</th>
                    <th>操作</th>
                    <th>得分</th>
                    <th>正确率</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach items="${studentList }" var="student">
            		<tr>
	                    <td>${student.code}</td>
	                    <td>${student.name }</td>
	                    <c:forEach items="${student.resultCardList }" var="resultCard">
	                    	<td>${resultCard.score }</td>
	                    	<td>${resultCard.correct }</td>
	                    	<td><c:if test="${resultCard.score!=null}"><a href="<%=path%>/work/scheduleQuestionDetail.jhtml?studentName=${student.name}&courseId=${course.id }&lessonId=${lesson.id }&resultCardId=${resultCard.id}">详情</a></c:if> </td>
	                    </c:forEach>
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
    		var courseId = "${course.id}";
    		var sortNo = $("#sortNo").val();
    		var categoryId = '${category.id}';
    		if (typeof(sortNo)=="undefined" || isNull(sortNo)) {
				alert("请输入课次");
				return;
			}
			window.location.href = "<%=path%>/work/scheduleClassDetail.jhtml?categoryId="+categoryId+"&courseId="+courseId+"&sortNo="+sortNo;
    	}
    	
    	function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		
		$("#sortNo").bind('keydown',function(event){
	    	if(event.keyCode == "13") {
		        select();
		        this.blur();
	   		}
	    });
    	
    </script>
  </body>
</html>
