<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>课件上传</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/pptUpload.css">
  </head>
  <body>
  
  	<input type="hidden" id="lessonId" value="${param.lessonId}"> 
    <!-- begin 标题和文件选择 -->
    <div class="wa_box">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span><a href="<%=path%>/prepareLesson/toHomePage.jhtml">首页</a></span>
                <span class="current">课件上传  - ${param.title}</span>
            </div>
        </div>
        <div class="upload_box">
            <span class="file_name">
            	${courseware.uploadPptName}
            	<c:if test="${not empty courseware.teachCaseName}">
            		+ ${courseware.teachCaseName }
            	</c:if>
           	</span>
            <label class="coursefile_box file_box hol_btn">
                选择文件
                <input type="file" />
            </label>
            <label class="abovefile_box file_box hol_btn">
               上传教案
                <input type="file" />
            </label>
            <span class="delete_file_btn hol_btn">删除</span>
        </div>
    </div>
    <!-- end 标题和文件选择 -->

    <!-- ppt上传预览 -->
    <div class="wa_box ppt_box">
        <div class="outline_box not_scrollbar">
            <ul class="outline_list">
            </ul>
        </div>
        <div class="iframe_box">
            <iframe class="ppt_iframe" src="" dataSrc="" ></iframe>
        </div>
        <div class="note">
            <div class="note_item not_scrollbar">
            </div>
            <button class="close_btn"></button>
        </div>
    </div>
    <!-- ppt上传预览 -->
    
	<!-- 教案盒子 -->
    <div class='case_box hide'>${courseware.teachCaseContent}</div>
    
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/layer.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/public.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/common.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/pptUpload.js"></script>
    <script type="text/javascript">
    	$(function(){
    		if (${courseware ne null} && ${courseware.pptType ne 0}){
    			renderUploadPage('${param.lessonId}', '${courseware.uploadPptName}');
    		} else {
    			$('.ppt_box').addClass('force_hide');
    			$('.file_name').addClass('force_hide');
    			$('.abovefile_box').addClass('force_hide');
    			$('.delete_file_btn').addClass('force_hide');
    		}
    	});
    </script>
  </body>
</html>