/*
* @Author: Hlin
* @Date:   2018-01-11 16:14:51
* @Last Modified by:   Hlin
* @Last Modified time: 2018-01-29 15:01:16
*/
// 上传文件
$(".upload_box").on('change', '.file_box input[type="file"]', function(event) {
    var file = this.files[0]; //文件对象
    if (!file) { //没有选择文件则返回
        return;
    }
    var fileName = file.name; //文件名
    var fileType = fileName.substring(fileName.lastIndexOf(".")+1); //文件类型
    if (!(/\.(mp4|ogg|webm)$/).test(fileName.toLowerCase())) { //格式检测
        layer.msg('不支持该文件格式！',{time:1000});
        return;
    }
    var formData = new FormData();
    formData.append('uploadFile', file);
    formData.append('lessonId', $("#lessonId").val());
    formData.append('fileType', $("#fileType").val());
    var layerIndex  = layer.load(2,{
        content: '<span class="uploadProcess"></span>',
        shade: 0.05
    });
    $.ajax({
        url : getRootPath() + '/tpFileUpload/uploadFile.jhtml',
        type : "POST",
        data : formData,
        dataType:'json',
        processData : false,
        contentType : false,
        //拿到XMLHttpRequest对象，增加 progress 事件绑定，然后再返回交给ajax使用
        xhr : function(){
            var xhr = $.ajaxSettings.xhr();
            if (onprogress && xhr.upload) {
                xhr.upload.addEventListener("progress" , onprogress, false);
            }
            return xhr;
        },
        success : function(data){
            //关闭加载弹窗
        	layer.close(layerIndex);
        	window.location.reload()
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.close(layerIndex); //关闭加载弹窗
            layer.msg('上传失败，网络异常！',{time:1000});
        }
    });

    //进度条
    function onprogress(evt) {
        if (evt.lengthComputable) {
            // 上传进度
            setTimeout(function(){
                var uploadProcess = ((evt.loaded / evt.total) * 100).toFixed(2) + "%";
                $('.uploadProcess').html(uploadProcess)
            }, 20);

        }
    }
});

// 视频链接上传
$(".upload_box").on('click', '.input_link_btn', function(event) {
    // 弹窗填写视频链接
    layer.prompt({
        title: '请填写视频链接',
        skin: "layer-sky-skin",
    }, function(pass, index){
    	layer.close(index); //关闭窗口
    	
    	if (isEmpty(trimBlock(pass))) {
			layer.msg('视频地址不能为全空格',{time:1000});
			return false;
		}
    	
    	if (pass.indexOf("lantian.gensee.com") == -1) {
    		layer.msg('请输入有效的视频地址',{time:1000});
			return false;
		}
        
        var lessonId = $("#lessonId").val();
        var fileType = $("#fileType").val();
        $.ajax({
            url : getRootPath() + '/tpCourseware/uploadVideoSrc.jhtml',
            type : "POST",
            data : {
            	'lessonId': lessonId,
            	'type': fileType,
            	'videoSrc': pass
            },
            dataType:'json',
            success : function(data){
            	window.location.reload();
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                layer.close(index); //关闭加载弹窗
                layer.msg('上传失败，网络异常！',{time:1000});
            }
        });
    });
});

// 删除文件
$(".upload_box").on('click', '.delete_file_btn', function(event) {

    layer.alert("是否删除？",{
        title: "提示", //标题
        skin: "layer-sky-skin",
        btn: ['确定','取消'], // 按钮
        closeBtn: 0, //右上角的关闭按钮，0为不显示，1为显示
        btn1: function(index, dom){ //index是该弹窗的索引
            var lessonId = $("#lessonId").val();
            var fileType = $("#fileType").val();
            $.ajax({
                url : getRootPath() + '/tpFileUpload/deleteVideo.jhtml',
                type : "POST",
                data : {
                	'lessonId': lessonId,
                	'type': fileType
                },
                dataType:'json',
                success : function(data){
//                	window.location.reload();
                	layer.close(index); //关闭弹窗
                	layer.msg(data.message,{time:1000});
                    //隐藏文件名
//                    $(".upload_box .file_name").addClass('force_hide');
                    //隐藏删除文件按钮
                    $(".upload_box .delete_file_btn").addClass('force_hide');
                    $(".video_box").remove();
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    layer.close(index); //关闭加载弹窗
                    layer.msg('上传失败，网络异常！',{time:1000});
                }
            });
        },
        btn2: function(index, dom){
            //按钮【按钮二】的回调
        }
    })

});

// 视频控件全屏操作
$(".video_box").on('click', '.vjs-fullscreen-control.vjs-control.vjs-button',function(event) {
    if ($(".video-js").attr("state") != "Fullscreen") {
        $(".video-js").attr("state","Fullscreen");
    }else{
        $(".video-js").removeAttr('state');
    }
});