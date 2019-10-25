/*
* @Author: Hlin
* @Date:   2017-09-18 16:52:40
* @Last Modified by:   Hlin
* @Last Modified time: 2017-09-19 09:24:03
*/

function renderUploadPage(uploadPptName,aboveWords){
	var lessonId = $("#lessonId").val();
	$('.uploadCourseware').click();
	$(".upload_ppt .file_info .file_name").html(uploadPptName);
    $(".upload_ppt .file_info").css("visibility","visible");
    $(".left").hide();
    $(".right").hide();
    $(".upload_ppt .ppt_file").hide();
    $('.upload_ppt .file_progress i').css("width","100%");
    $.ajax({
		type : "POST",
		url : getRootPath() + "/tpCourseware/getUploadCourseware.jhtml",
		async: false,                //true为异步加载，false为同步加载，参数为空时，默认异步加载
		cache: false,
		data : {'lessonId': lessonId},
		dataType : "json",
		success : function(data) {
			uploadImgIndex = 1;
            requestImg(data.url,data.list);
            $('.outline_item').each(function(i,elem){
            	$(this).children('div').html(aboveWords[i]);
            });
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

function saveUploadCoureware(){
//	addLoading("数据保存中...");
	$(".savePpt").attr('diabled', true);
	var id = $("#id").val();
	var lessonId = $("#lessonId").val();
	var fileName = $('.file_name').html();
	
	var slideDesList = new Array();
	$(".slide_outline .outline_item").each(function(index,el){
		slideDesList.push($(this).find(".des").html());
	})
	var aboveWords = JSON.stringify(slideDesList);
	$.ajax({
		type : "POST",
		url : getRootPath() + "/tpCourseware/saveUploadCoureware.jhtml",
		async: false,                //true为异步加载，false为同步加载，参数为空时，默认异步加载
		cache: false,
		beforeSend:function(){
//			addLoading("数据保存中...");
		},
		data : {'id': id, 'lessonId':lessonId,'aboveWords':aboveWords, 'fileName':fileName},
		dataType : "json",
		success : function(data) {
			$("#id").val(data.id);
			$(".savePpt").attr('diabled', false);
			removeLoading();
			layer.msg('保存成功!');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			removeLoading();
			layer.msg("保存出错，请重试！");
		}
	});
}

var uploadImgIndex = 1;
$(".upload_ppt").on('change', '.ppt_file input', function(event){
	// 显示上传文件
	$(".upload_ppt .ppt_file").hide();
	$(".left").hide();
	$(".right").hide();
	$(".upload_ppt .file_info").css("visibility","visible");
	
    var self = this;
    var file = $(this)[0].files[0];
    if(file == ""){
        layer.msg("请选择文件！");
        return false;
    }
    var fileName = file.name;
    $(".upload_ppt .file_info .file_name").html(fileName);
    if(!isSupportType(fileName)){
        layer.msg('该文件格式不支持上传,请上传zip压缩文件!');
        return false;
    }

    var formData = new FormData();
    var lessonId = $("#lessonId").val();
    formData.append('uploadFile', file);
    formData.append('lessonId', lessonId);
    $.ajax({
        type: "POST",
        url: getRootPath() + "/tpCourseware/uploadCoureware.jhtml",
        data: formData,
        dataType: 'json',
        processData: false,
        contentType: false,
        //这里我们先拿到jQuery产生的 XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
        xhr: xhr_provider,
        beforeSend : function() {
        	addLoading("上传及解压中...");
		},
        success: function(data){
        	removeLoading();
        	console.log(data);
        	if(data.success){
        		// 重置话述列表
        		$(".slide_outline").html('');
        		uploadImgIndex = 1;
        		requestImg(data.url,data.list);
        		// 清空input内容
        		$(self).val('');
        	}else{
        		$(".upload_ppt .ppt_file").show();
        		$(".left").hide();
        		$(".right").hide();
        		$(".upload_ppt .file_info").css("visibility","hidden");
        		layer.msg(data.message);
        	}
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	removeLoading();
            layer.msg('上传失败，请重试');
        }
    });
});
// 格式校验
function isSupportType(fileName){
	if(isEmpty(fileName))
		return false;
	var suffixName = fileName.substring(fileName.lastIndexOf('.'), fileName.length)
    var suffixName = suffixName.toUpperCase();
    if('.ZIP' == suffixName){
        return true;
    }else{
        return false;
    }
}

var xhr_provider = function() {
    var xhr = $.ajaxSettings.xhr();
    if (onprogress && xhr.upload) {
        xhr.upload.addEventListener("progress" , onprogress, false);
    }
    return xhr;
}

//上传进度
function onprogress(evt) {
    if (evt.lengthComputable) {
        $('.upload_ppt .file_progress i').css("width",(evt.loaded/evt.total)*100+"%");
    } 
}
// 遍历图片略缩图并显示
function requestImg(url,list){
	$.each(list,function(){
        $(".slide_outline").append('<div class="outline_item">'+
            '<span class="order">'+uploadImgIndex+'</span>'+
            '<img ondragstart="return false;" src="'+url+'/'+this+'?'+Date.parse(new Date())+'">'+
            '<div class="des" contenteditable="true"></div>'+
        '</div>');
        uploadImgIndex++;
    })
}
// 删除按钮
$(".upload_ppt").on('click', '.delete_file', function(event) {
    deleteFile();
});

function deleteFile(){
	layer.confirm('确认删除吗？', {
		btn : [ '删除', '取消' ]
	//按钮
	}, function() {
		var id = $("#id").val();
		var lessonId = $("#lessonId").val();
		$.ajax({
			type: "POST",
			url: getRootPath() + "/tpCourseware/deleteUploadCoursewareFile.jhtml",
			data: {
				'id' : id,
				'lessonId' : lessonId
			},
			dataType : 'json',
			//这里我们先拿到jQuery产生的 XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
			xhr : xhr_provider,
			beforeSend : function() {
				addLoading("删除中...");
			},
			success : function(data) {
				removeLoading();
				layer.msg('删除成功！');
				location.reload();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				removeLoading();
				layer.msg('删除失败，请重试');
			}
		});
	}, function() {
		return true;
	});
	
}

