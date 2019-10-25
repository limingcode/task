<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html  lang="en" class="upVideo">
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
	<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <title>视频</title>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if IE]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/style.css">
    <script src="<%=path%>/sys/teach/js/jquery-3.2.1.min.js"></script>
    <script src="<%=path%>/sys/teach/js/layer.js"></script>
    <script src="<%=path%>/sys/teach/js/common.js"></script>
</head>
<style>
    .upVideo {
        height: 100%;
    }
    .upVideo body{
        height: 100%;
    }
    .upVideo header{
        width: 100%;
        background-color: #6455d3;
        height: 75px;
    }
    .upVideo .headerCont{
        width: 1200px;
        height: 75px;
        margin: 0 auto;
        position: relative;
    }
    .upVideo .headerCont .logo{
    	padding-top: 4px;
        width: 115px;
        height: 100%;
        line-height: 75px;
        color: #fff;
        font-size: 1rem;
        font-style: italic;
        cursor: pointer;
    }
    .upVideo .headerCont .logo img
    {
        transform: rotate(90deg);
        width: 32px;
    }
    .upVideo .headerCont .classTitle{
        float: left;
        height: 75px;
        line-height: 75px;
        color: #fff;
        font-size: 2rem;
        font-weight: 700;
        text-align: center;
    }
    .upVideo content{
        display: block;
        width: 1200px;
        margin: 0 auto;
        height: calc(100% - 205px);
        padding-top: 130px;
        text-align: center;
    }
    .upVideo .videoSrcAdd,
    .upVideo .fileAdd{
        font-size: 1.3rem;
        padding: 10px 40px;
        border: 1px solid #8b7cfb;
        border-radius: 25px;
        background: #8b7cfb;
        color: #fff;
        cursor: pointer;
        margin: 15px;
    }
    .upVideo .jindutiao{
        position: absolute;
        width: 400px;
        height: 30px;
        top: 450px;
        left: 50%;
        margin-left: -175px;
        z-index:1;
    }
    .upVideo .cont{
        position: relative;
        width: 340px;
        height: 30px;
        padding-right: 20px;
        border:1px solid #ccc;
        border-radius: 25px;
        box-sizing: border-box;
        overflow: hidden;
    }
    .upVideo .cont2{
        position: absolute;
        width: 0;
        height: 30px;
        left: 0;
        background: #ff8300;
        border-radius: 25px;
    }
    .upVideo i{
        position: absolute;
        right: 5px;
        bottom: 6px;
        font-size: 1rem;
        color: skyblue;

    }
    
    .modal{
    	display: none;
    	position: fixed;
    	top: 0;
    	left: 0;
    	width: 100%;
    	height: 100%;
    	background-color: rgba(0,0,0,0.7);
    }
    .modal .wrap{
    	width: 400px;
    	height: 180px;
    	position: absolute;
    	top: 50%;
    	left: 50%;
    	margin-left: -200px;
    	margin-top: -90px;
    	background-color: #fff;
    	border-radius: 4px;
    	text-align: center;
    }
    .modal .wrap .video_src{
	    height: 33px;
	    width: 300px;
	    display: block;
	    margin: 35px auto;
	    padding: 6px;
    }
    .modal .wrap .submit,
    .modal .wrap .cancel{
        font-size: 1.3rem;
        padding: 10px 40px;
        border: 1px solid #8b7cfb;
        border-radius: 25px;
        background: #8b7cfb;
        color: #fff;
        cursor: pointer;
        margin: 0 10px;
        display: inline-block;
        outline: none;
    }
    
    progress{
	   color:orange; /*兼容IE10的已完成进度背景*/
	   border:none;
	   background:#d7d7d7;/*这个属性也可当作Chrome的已完成进度背景，只不过被下面的::progress-bar覆盖了*/      
	   
	}
	
	progress::-webkit-progress-bar{
	   background:#d7d7d7;
	}
	
	progress::-webkit-progress-value,
	progress::-moz-progress-bar	{
	     background:orange;
	}
	@media screen and (max-width: 1024px){
    .upVideo .headerCont{
        width: 100%;
    }
    .upVideo content{
        width: 100%;
    }
    .upVideo .headerCont .classTitle{
        font-size: 24px;
    }
}
</style>
<body>
<header>
    <div class="headerCont">
        <div class="logo fl">
        	<a href="<%=path%>/prepareLesson/toPrepareLesson.jhtml">
       			<img src="<%=path%>/sys/teach/images/talkVideo/downIcon.png" alt="">
        	</a>
        </div>
        <div class="classTitle">蓝天教学备课后台</div>
    </div>
</header>
<content>
    <div class="result" style="width: 100%;margin-bottom: 160px">
        <img src="<%=path%>/sys/teach/images/upVideo/up_sorry.png" alt="">
    </div>
    <form id='upload' >
	    <label for='uploadFile'>
	    	<span class="fileAdd">视频文件</span>
	    	<input type="hidden" id="lessonId" name="lessonId" value="${param.lessonId}">
	    	<input type="hidden" id="fileType" name="fileType" value="${param.type == 2 ? '5' : '6'}">
	    	<input style="display: none" type="file" id="uploadFile" name="uploadFile" onchange="upLoad()">
	    </label>
    	<span class="videoSrcAdd">视频链接</span>
    </form>
</content>
<div class="mask ds_n" style="position: fixed;background: rgba(204,204,204,.5);width: 100%;height: 100%;top: 0;opacity: 0.5"></div>
<div class="jindutiao ds_n" style="position: absolute;">
    <div class="cont">
        <div id="progressBar" class="cont2"></div>
    </div>
    <i id="percentage"></i>
</div>

<div class="modal">
	<div class="wrap">
		<input class="video_src" type="text" placeholder="请输入视频链接" />
		<button class='submit'>提交</button>
		<button class='cancel'>取消</button>
	</div>
</div>
</body>
<script>
    
    function upLoad(){
    	var file = $("#uploadFile").val();
		if(file == ""){
			layer.msg("请选择文件！");
			return false;
		}
		var fileName = file.substring(file.lastIndexOf('.'), file.length);
		if(!isSupportType(fileName)){
			layer.msg('该文件格式不支持上传!');
			return false;
		}
		
		var formData = new FormData($("#upload")[0]);
		$.ajax({
			type : "POST",
			url : "<%=path%>/tpFileUpload/uploadFile.jhtml",
			data : formData,
			dataType : "json",
			processData : false,
			contentType : false,
			//这里我们先拿到jQuery产生的 XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
			xhr : xhr_provider,
			success : function(data){
				if(data.success){
					window.location.href = "<%=path%>/prepareLesson/toUploadFile.jhtml?lessonId=${param.lessonId}&type=${param.type}";
				}else{
					layer.msg(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.msg('上传失败，请重试');
			}
		});
	}
	
    function isSupportType(fileName){
    	fileName = fileName.toUpperCase();
    	if('.WMV' == fileName || '.MP4' == fileName || '.MKV' == fileName || '.AVI' == fileName)
    		return true;
    	return false;
    }
    
	var xhr_provider = function() {
		var xhr = $.ajaxSettings.xhr();
		if (onprogress && xhr.upload) {
			xhr.upload.addEventListener("progress" , onprogress, false);
		}
		return xhr;
	}
	
	function onprogress(evt) {
		$('.mask').css('display','block');
        $('.jindutiao').css('display','block');
        var percentageDiv = document.getElementById("percentage");
        if (evt.lengthComputable) {
            percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
            $('.cont2').css('width',Math.round(evt.loaded / evt.total * 340)+"px");
            if(evt.loaded==evt.total){
//                 alert("上传完成100%");
            }
        }
    } 
	
	// 点击显示视频链接输入框
	$(".videoSrcAdd").on("click",function(){
		$(".modal").show();
	});
	// 提交
	$(".modal .submit").on("click",function(){
		// 上传成功在页面中间显示视频地址并关闭弹窗
		
		var lessonId = $("#lessonId").val();
		var type = "${param.type == 2 ? '5' : '6'}";
		var videoSrc = $('.modal .video_src').val();
		if (isEmpty(videoSrc)) {
			layer.msg('请输入视频链接!', {time:1500});
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : "<%=path%>/tpCourseware/uploadVideoSrc.jhtml",
			data : {'lessonId': lessonId, 'type': type, 'videoSrc': videoSrc},
			dataType : "json",
			//这里我们先拿到jQuery产生的 XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
			success : function(data){
				$(".modal").hide();
				$(".result").html(videoSrc);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.msg('上传失败，请重试');
			}
		});
	});
	// 取消
	$(".modal .cancel").on("click",function(){
		$(".modal").hide();
		$('.modal .video_src').val('');
	})
	
	var url = '${url}';
	if (isNotEmpty(url)) {
		$(".modal").hide();
		$(".result").html(url);
	}
	
</script>
</html>