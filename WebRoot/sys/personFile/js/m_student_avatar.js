/*
* @Author: Hlin
* @Date:   2018-01-03 11:36:16
* @Last Modified by:   Hlin
* @Last Modified time: 2018-01-10 14:16:16
*/
// 上传图片
window.addEventListener('DOMContentLoaded', function () {
    var image = $('#cropImage')[0];
    var previewRect = $('.preview_rect img');
    var previewRound = $('.preview_round img');
    var croppable = false;
    var blodData;
    // 初始化裁剪
    var cropper = new Cropper(image, {
        dragMode: 'move',
        aspectRatio: 1,
        autoCropArea: 0.9,
        restore: false,
        guides: false,
        center: false,
        highlight: false,
        cropBoxMovable: false,
        cropBoxResizable: false,
        toggleDragModeOnDblclick: false,
        ready: function () {
        croppable = true;
        },
        crop: function(e){
            var croppedCanvas;
            var roundedCanvas;
            var roundedImage;

            if (!croppable) {
                return;
            }
            setTimeout(function(){
                // Crop
                croppedCanvas = cropper.getCroppedCanvas();
                // Round
                roundedCanvas = getRoundedCanvas(croppedCanvas);
                // response
                blodData = roundedCanvas.toDataURL("image/jpeg",0.6);
                previewRect.attr("src",blodData);
                previewRound.attr("src",blodData);
            }, 20)
        }
    });

    // 绑定上传按钮的事件并显示上传的图片到裁剪区域
    var studentId;
    $(".student_list").on('change', '.item [type="file"]', function(event) {
        var file = this.files[0]; //文件对象
        if (!file) { //没有选择文件则返回
            return;
        }
        if (!(/\.(png|jpg)$/).test(file.name)) { //格式检测
            layer.msg('不支持该文件格式！',{time:1000});
            return;
        }
        studentId = $(this).parents('li').attr('studentid'); // 学生id
        // 显示图片裁剪
        $(".crop_box").addClass('active');

        //获取文件以base64形式呈现
        var reader = new FileReader();
        reader.readAsDataURL(file);

        reader.onload = function(e){
            cropper.replace(this.result); //将文件显示到裁剪区域
        }

        // 清空input[type="file"]
        $(this).val('');
    });

    // 确认裁剪并上传
    $('.crop_btn').on('click', function(event) {
        var formData = new FormData();

        formData.append('file', blodData);
        formData.append('userStudentId', studentId);
        var layerIndex = layer.load(1, {
      	  shade: [0.1,'#fff'] //0.1透明度的白色背景
      	});
        $.ajax({  
            type:"POST",  
//            url: "http://localhost:9080/task/appUser/saveImgByTask.jhtml",  
            url: "http://teaching.skyedu99.com:9081/AppTask/appUser/saveImgByTask.jhtml",  
            data: {'userStudentId': studentId, 'imgStr': blodData},
            crossDomain: true,  
            dataType: 'json',
            success : function(data) {  
                 $(".student_list li[studentid='"+studentId+"'] .avatar").attr('src', 'http://teaching.skyedu99.com:9081/AppTask' + data.imageSrc)
                 layer.close(layerIndex); //关闭加载弹窗
                 $(".crop_box").removeClass('active');
                 layer.msg('上传成功！',{time:1000});
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
            	layer.close(layerIndex); //关闭加载弹窗
                layer.msg('上传失败，网络异常！',{time:1000});
            }
        }); 
        
        
    });

    // 取消裁剪
    $(".btn_box .cancel_btn").on('click', function(event) {
        $(".crop_box").removeClass('active');
    });
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
// 将剪切区域渲染到canvas中再读取成base64
function getRoundedCanvas(sourceCanvas) {
    var canvas = document.createElement('canvas');
    var context = canvas.getContext('2d');
    canvas.width = 600;
    canvas.height = 600;

    context.imageSmoothingEnabled = true;
    context.drawImage(sourceCanvas, 0, 0, 600, 600);
    context.globalCompositeOperation = 'destination-in';

    return canvas;
}

// base64转blob
function convertBase64UrlToBlob(urlData){
    //去掉url的头，并转换为byte
    var bytes=window.atob(urlData.split(',')[1]);

    //处理异常,将ascii码小于0的转换为大于0
    var ab = new ArrayBuffer(bytes.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < bytes.length; i++) {
        ia[i] = bytes.charCodeAt(i);
    }

    return new Blob( [ab] , {type : 'image/jpeg'});
}


//-----------------------------------------------------------------------------------------------------------------
//author 黄德彬
$('.class_list').on('click', 'li', function(){
	$(this).addClass('active').siblings().removeClass('active');
	var id = $(this).attr('id');
	//loading层
	var index = layer.load(1, {
	  shade: [0.1,'#fff'] //0.1透明度的白色背景
	});
	
	$.ajax({
        url : getRootPath() + "/personFile/getStudents.jhtml",
        type : "POST",
        data : {'courseId': id},
        dataType:'json',
        success : function(data){
        	var html = "";
        	$.each(data, function(i, elem){
        		html += '<li studentid='+ elem.id +'>'+ 
		        		 '    <label class="item">'+ 
		        		 '	  	  <input type="file" />'+ 
		        		 '	      <img class="avatar" src="'+ (isNotEmpty(elem.img) ? elem.img : getRootPath() + '/sys/personFile/images/v2_user_icon5.jpg') +'">'+ 
		        		 '    </label>'+ 
		        		 '    <span class="name">'+ elem.name +'</span>'+ 
		        		 '</li>';
        	});
        	$('.student_list').html(html);
        	layer.close(index); //关闭加载弹窗
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.close(index); //关闭加载弹窗
            layer.msg('加载失败，网络异常！',{time:1000});
        }
    });
});




