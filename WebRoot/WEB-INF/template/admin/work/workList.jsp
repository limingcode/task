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
    
    <title>作业列表</title>
    
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
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/examManage.css">
  </head>
  
  <body>
  <!-- begin 组卷列表 -->
    <div class="wa_box exam_list">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span><a href="<%=path %>/lesson/lessonList.jhtml?lessonId=${lesson.id}">课次列表</a></span>
                <span class="current">组卷列表</span>
            </div>
        </div>
        <table class="wa_table">
            <thead>
                <caption id="title">${lesson.gradeName}${lesson.subjectName}${lesson.cateName}层次第${lesson.sortNo}课次</caption>
                <tr>
                    <th>编号</th>
                    <th>题类</th>
                    <th>总分</th>
                    <th>标准做答时间</th>
                    <th>创建时间</th>
                    <th>修改时间</th>
                    <th>创建人</th>
                    <th>打包状态</th>
                    <th colspan="3">操作</th>
                </tr>
            </thead>
            <tbody>
           		 <c:forEach items="${workList }" var="work">
           		 	<tr>
	                    <td>${work.id}</td>
	                    <td>${work.categoryName}</td>
	                    <td>${work.score}</td>
	                    <td>${work.standardTime}</td>
	                    <td><fmt:formatDate value="${work.createDate}" pattern="yyyy年MM月dd日 HH:mm"/></td>
	                    <td><fmt:formatDate value="${work.modifyDate}" pattern="yyyy年MM月dd日 HH:mm"/></td>
	                    <td>${work.teacherName}</td>
	                    <td class="zipState"><c:if test="${work.zipState==0}">未打包</c:if><c:if test="${work.zipState==1}">打包进行中</c:if><c:if test="${work.zipState==2}">已打包</c:if> </td>
	                    <td colspan="3"><a href="<%=path%>/question/previewWork.jhtml?workId=${work.id}">预览组卷</a><a href="<%=path%>/work/workInfoList.jhtml?work=${work.id}">查看组题</a><a onclick="zipWork(${work.id})" class="zipWork${work.id}" href="javascript:;">组卷打包</a><a href="javascript:;" class="delete_btn" onclick="delWorkInfo(${work.id},'${work.categoryName}')">删除组题</a></td>
	                </tr>
                </c:forEach>
                <c:if test="${fn:length(categoryList)>0}">
	                <tr class="add_exam_tr">
	                    <td></td>
	                    <td>
	                        <select id="category">
				                <option value="">--请选择--</option>
				                <c:forEach items="${categoryList}" var="category">
				                	<option value="${category.id }">${category.categoryName }</option>
				                </c:forEach>
				            </select>
	                    </td>
	                    <td>0</td>
	                    <td>0</td>
	                    <td><fmt:formatDate value="<%=new Date()%>" pattern="yyyy年MM月dd日 HH:mm"/></td>
	                    <td><fmt:formatDate value="<%=new Date()%>" pattern="yyyy年MM月dd日 HH:mm"/></td>
	                    <td></td>
	                    <td></td>
	                    <td colspan="3"><a href="javascript:;" onclick="commitWork()">添加</a></td>
	                </tr>
                </c:if>
            </tbody>
        </table>
    </div>
    <!-- end 组卷列表 -->
    <script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/examList.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
    <script type="text/javascript">
    	function goback(){
    		window.location.href="JavaScript:history.go(-1)";
    	}
    	
    	function commitWork(){
    		var category = $("#category option:selected").val();
    		if(isNull(category)){
    			alert("请选择作业类型");
    			return;
    		}
    		window.location.href="<%=path%>/work/commitWork.jhtml?lesson=${lesson.id}&category="+category;
    	}
    	
    	function delWork(workId,categoryName){
    		var title = $("#title").html();
    		var lesson = '${lesson.id}';
    		if(confirm("是否删除"+title+"的"+categoryName+"作业")){
	    		$.ajax({
	    			url:"<%=path%>/work/delWork.jhtml",
	    			type:"post",
	    			data:{
	    				workId : workId,
	    			},
	    			dataType:'JSON',
	    			success:function(result){
	    				var code = result.code;
	    				var message = result.message;
	    				alert(message);
	    				if (code == 100) {
							window.location.href="<%=path%>/work/workList.jhtml?lesson="+lesson;
						};
	    			},
	    			error : function(result) {
						var msg = result.responseText;
						eval(msg.replace(/(<script>|<\/script>)/g,''));
					}
	    		});
    		};
    	}
    	
    	function delWorkInfo(workId,categoryName){
    		var title = $("#title").html();
    		var lesson = '${lesson.id}';
    		if(confirm("是否删除"+title+"的"+categoryName+"作业组题，该操作会使学生的答题卡撤销")){
	    		$.ajax({
	    			url:"<%=path%>/work/delWorkInfo.jhtml",
	    			type:"post",
	    			data:{
	    				workId : workId,
	    			},
	    			dataType:'JSON',
	    			success:function(result){
	    				var code = result.code;
	    				var message = result.message;
	    				alert(message);
	    				if (code == 100) {
							window.location.href="<%=path%>/work/workList.jhtml?lesson="+lesson;
						};
	    			},
	    			error : function(result) {
						var msg = result.responseText;
						eval(msg.replace(/(<script>|<\/script>)/g,''));
					}
	    		});
    		};
    	}
    	
    	function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		
		function zipWork(workId){
			$.ajax({
	    			url:"<%=path%>/work/zipWork.jhtml",
	    			type:"post",
	    			data:{
	    				workId : workId,
	    			},
	    			dataType:'JSON',
	    			success:function(result){
	    				var code = result.code;
	    				var message = result.message;
	    				if (code==100) {
	    					$(".zipWork"+workId).parent().siblings(".zipState").html("打包进行中");
						}
	    			},
	    			error : function(result) {
						var msg = result.responseText;
						eval(msg.replace(/(<script>|<\/script>)/g,''));
					}
	    		});
		}
    </script>
  </body>
</html>
