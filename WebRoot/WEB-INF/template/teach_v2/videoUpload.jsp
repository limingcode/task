<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>视频上传</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/video-js.min.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach_v2/css/videoUpload.css">
  </head>
  <body>
  	<input type="hidden" id="lessonId" value="${param.lessonId}"> 
  	<input type="hidden" id="fileType" value="<c:choose><c:when test="${param.type eq 2}">5</c:when><c:otherwise>6</c:otherwise></c:choose>"> 
    <!-- begin 标题和文件选择 -->
    <div class="wa_box">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span><a href="<%=path%>/prepareLesson/toHomePage.jhtml">首页</a></span>
                <span class="current">课件上传  - ${param.title}</span>
            </div>
        </div>
        <div class="upload_box">
            <span class="file_name"><c:choose><c:when test="${param.type eq 2}">说课视频</c:when><c:otherwise>实录视频</c:otherwise></c:choose></span>
            <label class="file_box hol_btn">
                选择文件
                <input type="file" />
            </label>
            <span class="input_link_btn hol_btn">上传视频链接</span>
            <c:if test="${file ne null or not empty url }">
            	<span class="delete_file_btn hol_btn">删除</span>
            </c:if>
        </div>
    </div>
    <!-- end 标题和文件选择 -->

    <!--视频上传和预览 -->
       	<c:choose>
       		<c:when test="${file ne null }">
			    <div class="wa_box video_box">
			        <!-- begin 视频文件播放 -->
			        <div class="playVideo">
			          <video id="my-video" class="video-js" width="820px" height="496px" controls preload="auto" width="100%" height="100%" data-setup="{}">
			            <source src="${file.fileUrl}" type='video/mp4'>
			          </video>
			        </div>
			    </div>
       		</c:when>
      			<c:when test="${!empty url}">
			    <div class="wa_box video_box">
			        <!-- begin 视频文件播放 -->
			        <div class="playVideo">
       					<iframe width="100%" height="100%" src="${url}?nickName=任彦阳&password=123456"></iframe>
			        </div>
			    </div>
       		</c:when>
       	</c:choose>
        <!-- end 视频文件播放 -->
        <!-- begin 外网视频链接 -->
        <!-- <iframe class="video_iframe" src="http://lantian.gensee.com/webcast/site/vod/play-959a334d93c24192af9117b5a68fd03e?nickName=任彦阳&password=123456"></iframe> -->
        <!-- end 外网视频链接 -->
    <!-- 视频上传和预览 -->
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/layer.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/video.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/public.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/common.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/teach_v2/js/videoUpload.js?123456"></script>
  </body>
</html>