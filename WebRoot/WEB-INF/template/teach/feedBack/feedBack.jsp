<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" class="feedBack">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>用户反馈</title>
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/animate.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/date.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/page.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/style.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/index.css">
    <!--[if IE]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/feedBack.css">
    <script src="<%=path%>/sys/teach/js/jquery-3.2.1.min.js"></script>
    <script src="<%=path%>/sys/teach/js/layer.js"></script>
    <script src="<%=path%>/sys/teach/js/date.js"></script>
    <style type="text/css">
    	.disabled {
    		background-color: #efefef;
    	}
    	.feedBack .headerCont .logo img{
    		margin-top: 30px;    
    	    transform: rotate(90deg);
	        width: 32px;
	    }
	    
    </style>
</head>
<body>
<header>
	<c:if test="${not empty param.lessonId}">
	    <div class="headerCont">
	        <div class="logo fl">
	       		<img src="<%=path%>/sys/teach/images/talkVideo/downIcon.png" alt="">
	        </div>
	        <div class="user fr">
	            <span class="userName">
	                <i></i>
	                <i>${teacherName}</i>
	                <ul class="userName_opre">
	                    <li><a href="<%=path%>/question/input.jhtml">作业平台</a></li>
	                    <li><a href="<%=path%>/user/statis.jhtml">数据统计</a></li>
	                    <li><a href="<%=path%>/user/logout.jhtml">退出</a></li>
	                </ul>
	            </span>
	        </div>
	    </div>
	</c:if>
</header>
<div class="content">
    <div class="topCont">
    	<form id="selectForm">
    		<input type="hidden" id="currPage" name="currPage">
    		<input type="hidden" id="pageSize" name="pageSize" value="10">
    		<input type="hidden" id="lessonId" name="lessonId" value="${param.lessonId}">
	        <label for="beginTime">
	            <span>时间</span>
	            <input type="text" id="beginTime" name="beginTime" class="kbtn" placeholder="--请选择时间--"/>
	        </label>
	        <label for="lessons">
	            <span>科目</span>
	            <select name="lessons" id="lessons" name="lessons" class="lessons">
	                <option style="color: #ccc;" value="">--请选择科目--</option>
	                <option value="A04">语文</option>
	                <option value="A02">数学</option>
	                <option value="A01">英语</option>
	                <option value="A05">物理</option>
	                <option value="A06">化学</option>
	            </select>
	        </label>
	        <label for="semester">
	            <span>学期</span>
	            <select name="semester" id="semester" name="semester" class="semester">
	                <option style="color: #ccc;" value="">--请选择学期--</option>
	                <c:forEach items="${periodList}" var="period">
	               		<option value="${period.id}">${period.year}${period.name}</option>
	               	</c:forEach>
	            </select>
	        </label>
	        <label for="grade">
	            <span>年级</span>
	            <select name="grade" id="grade" name="grade" class="grade">
	                <option style="color: #ccc;" value="">--请选择年级--</option>
	                <c:forEach items="${gradeList}" var="grade">
	               		<option value="${grade.code}">${grade.name}</option>
	               	</c:forEach>
	            </select>
	        </label>
	        <label for="arrangement">
	            <span>层次</span>
	            <select name="arrangement" id="arrangement" name="arrangement" class="arrangement">
	                <option style="color: #ccc;" value="">--请选择层次--</option>
	                <c:forEach items="${cateList}" var="cate">
	               		<option value="${cate.code}">${cate.name}</option>
	               	</c:forEach>
	            </select>
	        </label>
	        <label for="classTime">
	            <span>课次</span>
	            <select name="classTime" id="classTime" name="classTime" class="classTime" >
	                <option style="color: #ccc;" value="">--请选择课次--</option>
	            </select>
	        </label>
	        <label for="name">
	            <span>姓名</span>
	            <input type="text" id="name" name="name" class="name"/>
	        </label>
	        <label for="state">
	            <span>状态</span>
	            <select name="state" id="state" class="state">
	                <option style="color: #ccc;" value="">--请选择状态--</option>
	                <option value="1">赞</option>
	                <option value="2">不赞</option>
	            </select>
	        </label>
	    </form>
    </div>
    <div class="msg">
        <ul class="msg_ul">
            <li class="title_s">
                <i>姓名</i>
                <i>时间</i>
                <i>科目</i>
                <i>年级</i>
                <i>学期</i>
                <i>层次</i>
                <i>课次</i>
                <i class="state">状态</i>
                <i class="proposal">建议</i>
            </li>
        </ul>
        <div class="pageChange">
            <div class="pageBox">
                <ul class="pageDiv clearfix">

                </ul>
                <div class="notContent hide">
                    无数据
                </div>
                <div class="page">
                    <ul class="pageMenu clearfix">
                        <li class="firstPage">首页</li>
                        <li class="prevPage">上一页 </li>
                        <div class="pageObj ">
<!-- 							<li class="pageNum active" style="">1</li> -->
<!-- 							<li class="pageNum" style="">2</li> -->
<!-- 							<li class="pageNum" style="">3</li> -->
<!-- 							<li class="pageNum" style="">4</li> -->
<!-- 							<li class="pageNum" style="">5</li> -->
                        </div>
                        <li class="nextPage"> 下一页  </li>
                        <li class="lastPage">尾页</li>
                        <li class="last" style="font-size: 14px;">
                           	 共<span class="totalPage"></span>页，
                           	 跳转至 <input style="font-size: 14px" type="number" id="number" placeholder="输入页数" class="keuInput" />
                            <button type="button" class="btnSure">确定</button>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

    </div>
    <div id="datePlugin"></div>

</div>
</body>
<script src="<%=path%>/sys/teach/js/util/feedBack.js"></script>
<script src="<%=path%>/sys/teach/js/common.js"></script>
<script>

	$('.headerCont .logo').on('click', function(){
		window.location.href = '<%=path%>/prepareLesson/toPrepareLesson.jhtml';
	});

    $('#beginTime').date();
    
    function getPage(currPage){
    	
    	$('#currPage').val(currPage);
		$.ajax({
		     type: "POST",
		     url:  getRootPath() + "/feedback/getFeedbackList.jhtml",
		     cache: false,
		     data: $("#selectForm").serialize(),
		     dataType: "json",
		     success: function(data){
		    	 if(data.status){
			    	 write(data);
			    	 toolBarCtrl(currPage, data.totalPage, data.hasPrePage, data.hasNextPage);
		    	 }
		     },
		     error: function (XMLHttpRequest, textStatus, errorThrown) {
		     	 layer.msg('请求出错，请稍后重试!');  		
		     }
	  	});
	}
    
    $(function(){
    	getPage(1);
    	var lessonId = '${param.lessonId}';
    	if(isNotEmpty(lessonId)){
     		$('.lessons,.semester,.grade,.arrangement,.classTime').addClass('disabled');
    		$('.lessons,.semester,.grade,.arrangement,.classTime').attr('disabled',true);
    		
    		$('#lessons').val('${data.subject}'.trim());
    		$('#semester').val('${data.perName}');
    		$('#grade').val('${data.graName}');
    		$('#arrangement').val('${data.catName}');
    		$('#classTime').html('<option value="${data.id}">第${data.sortNo}次</option>');
    	}
    });
    
</script>

</html>