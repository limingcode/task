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
    
    <title>选题界面</title>
    
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
    <!-- begin 选题界面 -->
    <div class="wa_box choice_ql">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span><a href="<%=path%>/lesson/lessonList.jhtml?lesson=${work.lesson}">课次列表</a></span>
                <span><a href="<%=path%>/work/workList.jhtml?lesson=${work.lesson}">组卷列表</a></span>
                <span><a href="<%=path%>/work/workInfoList.jhtml?work=${work.id}">组卷详情</a></span>
                <span class="current">选题界面</span>
            </div>
            <div  class="oper_btn">
                <a href="javascript:;" class="sol_btn" id="submit">提交</a>
            </div>
        </div>
        <form id="myform">
        	<input type="hidden" name="workId" value="${work.id}">
	        <table class="wa_table">
		        <caption>
	                <input class="search" type="text" id="brief" onblur="select()" />
	                <a href="javascript:;" class="sol_btn" onclick="select()">搜索</a>
	            </caption>
	            <thead>
	                <tr>
	                    <th>选择<a href="javascript:;" class="all_select"></a></th>
						<th>排序<a href="javascript:;" class="q_sort_btn"></a></th>
	                    <th>题目ID</th>
	                    <th>题型</th>
	                    <th>题目简介</th>
	                    <th>创建时间</th>
	                    <th>创建人</th>
	                </tr>
	            </thead>
	            <tbody>
	           		<c:forEach items="${questionList }" var="ques">
		                <tr>
		            		<td><label class="sky_checkbox"><input type="checkbox" name="questions" value="${ques.id}" <c:if test="${ques.sortNo >0 }">checked='checked'</c:if> ><span class="mark"></span></label> </td>
		            		<td class="q_sort"><input class="hide" type="number" name="${ques.id}" value="${ques.sortNo}" > </td>
		                    <td><c:if test="${fn:length(ques.childQuestion)>0 }"><span class="open_btn">展开</span></c:if> ${ques.id}</td>
		                    <td>
			                    <c:if test="${ques.questionType==0 }">组合题</c:if>
			                    <c:if test="${ques.questionType==1 }">单选题</c:if>
			                    <c:if test="${ques.questionType==2 }">多选题</c:if>
			                    <c:if test="${ques.questionType==3 }">填空题</c:if>
			                    <c:if test="${ques.questionType==4 }">连线题</c:if>
			                    <c:if test="${ques.questionType==5 }">语音题</c:if>
			                    <c:if test="${ques.questionType==6 }">判断题</c:if>
		                    </td>
		                    <td>
		                    	<c:if test="${fn:length(ques.brief)<=30}">${ques.brief}</c:if> 
		                    	<c:if test="${fn:length(ques.brief)>30}">${fn:substring(ques.brief,0,30)}...</c:if> 
		                    </td>
		                    <td><fmt:formatDate value="${ques.createDate}" type="date" dateStyle="long"/> </td>
		                    <td>${ques.teacherName}</td>
		                </tr>
	                </c:forEach>
	            </tbody>
	        </table>
        </form>
    </div>
    <!-- end 选题界面 -->
    <script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/space_underline.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/choiceQuestionList.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
    <script type="text/javascript">
    	function commit(){
			var inputHasVal = 0;
			$(".q_sort input").each(function(){
				if($(this).val()){
					inputHasVal += 1;
			    }
			});
    		if($(".sky_checkbox input:checked").length == inputHasVal){
    			console.log("已填完");
    			document.getElementById("myform").submit();
    		}else{
    			alert("排序字段未填完！");
    		}
    	}
    	
    	$(function() {
			$("#submit").click(function() {
			var inputHasVal = 0;
			$(".q_sort input").each(function(){
				if($(this).val()){
					inputHasVal += 1;
			    }
			});
    		if($(".sky_checkbox input:checked").length == inputHasVal){
    			skyAlert.loading("组卷生成中");
				$.ajax({
					url: "<%=path%>/work/commitWorkInfo.jhtml",
					type: 'post',
					dataType:'json',
					data: $("#myform").serializeArray(),
					success: function(msg) {
						var code = msg.code;
						if (code==100) {
							window.location.href="<%=path%>/work/workInfoList.jhtml?work=${work.id}";
						} else {
							var message = msg.message;
							alert(message);
							window.location.href="<%=path%>/work/workInfoList.jhtml?work=${work.id}";
						}
						skyAlert.loading(false);
					}
				});
    		}else{
    			alert("排序字段未填完！");
    		}
			});
		});
		
		$("#brief").bind('keydown',function(event){
	    	if(event.keyCode == "13") {
		        select();
		        this.blur();
	   		}
    	});
		
		function select(){
			var brief = $("#brief").val();
			var workId = '${work.id}';
			window.location.href="<%=path%>/question/selectQuestion.jhtml?brief="+brief+"&workId="+workId;
		}
		
    </script>
  </body>
</html>
