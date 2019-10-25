<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="description" content="" />
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta name="author" content="" />
    <title>上传图片</title>
    <link rel="stylesheet" href="<%=path%>/sys/imageTag/css/demo.css" type="text/css" />
    <style>
        body{background: #27323A;}
        ::-webkit-scrollbar {width: 8px;  height: 8px;  }
        ::-webkit-scrollbar-thumb {border-radius: 10px;  background-color:rgba(53, 85, 101, 0);  }
        ::-webkit-scrollbar-track {border-radius: 10px;  background-color:rgba(53, 85, 101, 0);  }
        .upload_box{width:100%; margin:0 auto;}
        .upload_choose{float: left;width: 140px;  height: 55px;  margin-top: 50px;}
        .upload_append_list{height:300px; padding:0 1em; float:left; position:relative;}
        .upload_delete{margin-left:2em;}
        .upload_image{max-height:250px; padding:5px;}
        .upload_submit{float: left}
        .upload_preview{padding:0 100px;height: 700px;  overflow-y: scroll;}
        .upload_progress{display:none; padding:5px; border-radius:10px; color:#fff; background-color:rgba(0,0,0,.6); position:absolute; left:25px; top:45px;}
    </style>
</head>

<body style="overflow: hidden">
<div id="main">
    <div id="body" class="light">
        <div id="content" class="show">
            <div class="demo">
                <form id="uploadForm" action="<%=path%>/imageTag/uploadImageFile.jhtml" method="post" enctype="multipart/form-data">
<!--                 <form id="uploadForm" method="post" enctype="multipart/form-data"> -->
                    <div class="upload_box">
                        <div class="upload_main">
                            <div class="upload_choose">
                                <input type="hidden" id="bookId" name="bookId" value="${bookId}">
                                <input type="hidden" id="period" name="period" value="${param.period}">
                                <input type="hidden" id="bookName" name="bookName" value="${param.bookName}">
                                <label for="fileImage">
                                    <span class="button btn-7">选择图片</span>
                                    <input id="fileImage" style="display: none" type="file" size="30" name="uploadFiles" multiple="multiple" accept="image/jpeg,image/png,image/gif" />
                                </label>
                            </div>
                            <div id="preview" class="upload_preview"></div>
                            <div class="upload_submit">
                                <button type="button" id="fileSubmit" class="button btn-7">确认上传图片</button>
                            </div>
                        </div>
                        <div id="uploadInf" class="upload_inf"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="<%=path%>/sys/imageTag/js/jquery-2.2.3.min.js"></script>
<script src="<%=path%>/sys/imageTag/js/upLoad.js"></script>
<SCRIPT SRC="<%=path%>/sys/imageTag/js/particleground.js"></SCRIPT>
<SCRIPT SRC="<%=path%>/sys/imageTag/js/common.js"></SCRIPT>
<SCRIPT SRC="<%=path%>/sys/imageTag/js/layer.js"></SCRIPT>
<script type="text/javascript">

$('#fileSubmit').click(function() {
	if(isEmpty($('#fileImage').val())){
		layer.msg("请选择图片");
		return false;
	}
	$('#uploadForm').submit();
});

//    图片本地预览效果代码（勿动）
var params = {
    fileInput: $("#fileImage").get(0),
    dragDrop: $("#fileDragArea").get(0),
    upButton: $("#fileSubmit").get(0),
    url: $("#uploadForm").attr("action"),
    filter: function(files) {
        var arrFiles = [];
        for (var i = 0, file; file = files[i]; i++) {
            if (file.type.indexOf("image") == 0) {
            	arrFiles.push(file);
            } else {
                alert('文件"' + file.name + '"不是图片。');
            }
        }
        return arrFiles;
    },
    onSelect: function(files) {
        var html = '', i = 0;
        $("#preview").html('<div class="upload_loading"></div>');
        var funAppendImage = function() {
            file = files[i];
            if (file) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    html = html + '<div id="uploadList_'+ i +'" class="upload_append_list"><p><strong style="color: #fff;font-size: 26px">' + file.name + '</strong>'+
                            '<a style="color: red;font-size: 26px" href="javascript:" class="upload_delete" title="删除" data-index="'+ i +'">删除</a><br />' +
                            '<img id="uploadImage_' + i + '" src="' + e.target.result + '" class="upload_image" /></p>'+
                            '<span id="uploadProgress_' + i + '" class="upload_progress"></span>' +
                            '</div>';

                    i++;
                    funAppendImage();
                };
                reader.readAsDataURL(file);
            } else {
                $("#preview").html(html);
                if (html) {
                    //删除方法
                    $(".upload_delete").click(function() {
                        ZXXFILE.funDeleteFile(files[parseInt($(this).attr("data-index"))]);
                        return false;
                    });
                    //提交按钮显示
                    $("#fileSubmit").show();
                } else {
                    //提交按钮隐藏
                    $("#fileSubmit").hide();
                }
            }
        };
        funAppendImage();
    },
    onDelete: function(file) {
        $("#uploadList_" + file.index).fadeOut();
        $('#uploadForm')[0].reset();
    },
    onDragOver: function() {
        $(this).addClass("upload_drag_hover");
    },
    onDragLeave: function() {
        $(this).removeClass("upload_drag_hover");
    }
};
ZXXFILE = $.extend(ZXXFILE, params);
ZXXFILE.init();
//    图片本地预览效果代码

// function upLoad(){
//     var picFileList = $("#fileImage").get(0).files;
//     var formData = new FormData();
//     formData.append("lessonId", 2);
//     for(var i=0; i< picFileList.length; i++){
//         formData.append("uploadFiles" , picFileList[i] );
//     }
//     $.ajax({
//         type : "POST",
//         url : "http://192.168.40.182:7080/task/imageTag/uploadImageFileAjax.jhtml",
//         data : formData,
//         dataType : "json",
//         processData : false,
//         contentType : false,
//         //这里我们先拿到jQuery产生的 XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
//         // 		xhr : xhr_provider,
//         success : function(data){
//             console.log(data);
//         },
//         error : function() {
//             layer.msg('上传失败，请重试');
//         }
//     });
// }
</script>
</body>
</html>