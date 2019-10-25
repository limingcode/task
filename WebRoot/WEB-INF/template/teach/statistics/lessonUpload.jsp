<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" class="lessonUpload">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/lessonUpload.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/page.css">
	<title>课次上传统计</title>
</head>
<body>

	<div class="topCont">
    	<form id="selectForm">
    		<input type="hidden" id="pageSize" name="pageSize" value="15">
    		<input type="hidden" id="currPage" name="currPage">
	        <label>
	            <span>科目</span>
	            <select id="subject" name="subject" class="lessons">
	                <option style="color: #ccc;" value="">--请选择科目--</option>
	                <option value="A04">语文</option>
	                <option value="A02">数学</option>
	                <option value="A01">英语</option>
	                <option value="A05">物理</option>
	                <option value="A06">化学</option>
	            </select>
	        </label>
	        <label>
	            <span>学期</span>
	            <select id="period" name="period" class="semester">
	                <option style="color: #ccc;" value="">--请选择学期--</option>
	                <c:forEach items="${periodList}" var="period">
	               		<option value="${period.id}">${period.year}${period.name}</option>
	               	</c:forEach>
	            </select>
	        </label>
	        <label>
	            <span>年级</span>
	            <select id="grade" name="grade" class="grade">
	                <option style="color: #ccc;" value="">--请选择年级--</option>
	                <c:forEach items="${gradeList}" var="grade">
	               		<option value="${grade.code}">${grade.name}</option>
	               	</c:forEach>
	            </select>
	        </label>
	        <label>
	            <span>层次</span>
	            <select id=cate name="cate" class="arrangement">
	                <option style="color: #ccc;" value="">--请选择层次--</option>
	                <c:forEach items="${cateList}" var="cate">
	               		<option value="${cate.code}">${cate.name}</option>
	               	</c:forEach>
	            </select>
	        </label>
	    </form>
    </div>

	<div class="table" style="overflow: auto; width: 100%;">
		<table border="1" cellspacing="0" cellpadding="5" style="text-align: center;width: 90%;margin:0 28px;">
			<tr class="titleTr">
				<td rowspan="2" style="width: 100px;height: 30px">学期</td>
				<td rowspan="2" style="width: 100px;height: 30px">科目</td>
				<td rowspan="2" style="width: 100px;height: 30px">年级</td>
				<td rowspan="2" style="width: 100px;height: 30px">层次</td>
			</tr>
		</table>
	</div>

    <div class="notContent" style="width: 100%; text-align: center;float: left;">
                    无数据
    </div>
	<div class="page">
        <ul class="pageMenu clearfix">
            <li class="firstPage">首页</li>
            <li class="prevPage">上一页 </li>
            <div class="pageObj ">
            </div>
            <li class="nextPage"> 下一页  </li>
            <li class="lastPage">尾页</li>
            <li class="last">
               	 共<span class="totalPage"></span>页，
               	 跳转至 <input type="number" id="number" placeholder="输入页数" class="keuInput" />
                <button type="button" class="btnSure">确定</button>
            </li>
        </ul>
    </div>
</body>
<script src="<%=path%>/sys/teach/js/jquery-3.2.1.min.js"></script>
<script src="<%=path%>/sys/teach/js/layer.js"></script>
<script src="<%=path%>/sys/teach/js/util/lessonUpload.js"></script>
<script type="text/javascript">

	$(function() {
		getPage(1);
	});

	function getPage(currPage) {
		$("#currPage").val(currPage);
		$.ajax({
		     type: "POST",
		     url:  "<%=path%>/statistics/getLessonUploadStatus.jhtml",
		     cache: false,
		     data: $("#selectForm").serialize(),
		     dataType: "json",
		     success: function(data){
		    	 if (data.dataList.length){
		    		 $('.notContent').hide();
		    		 $('.table').show();
		    		 $('.page').show();
			    	 write(data);
			    	 toolBarCtrl(currPage, data.totalPage, data.hasPrePage, data.hasNextPage);
		    	 } else {
		    		$('.notContent').show();
		    		$('.table').hide();
		    		$('.page').hide();
		    	 }
		     },
		     error: function (XMLHttpRequest, textStatus, errorThrown) {
		     	 layer.msg('请求出错，请稍后重试!');  		
		     }
	  	});
	}
	
</script>
</html>