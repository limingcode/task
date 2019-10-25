/*
 * author: 黄德彬
 */
function renderUploadPage(lessonId, uploadPptName){
    $.ajax({
		type : "POST",
		url : getRootPath() + "/tpCourseware/getUploadCourseware.jhtml",
		async: false,                //true为异步加载，false为同步加载，参数为空时，默认异步加载
		cache: false,
		data : {'lessonId': lessonId},
		dataType : "json",
		success : function(data) {
			writeData(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}


function writeData(data){
	$('.file_box').addClass('force_hide');
    //显示删除文件按钮
    $(".upload_box .delete_file_btn").removeClass('force_hide');
    $(".upload_box .abovefile_box").removeClass('force_hide');

    //$('iframe').attr('src', data.url+'/../index.html').attr('dataSrc', data.url+'/../index2.html');
    $('iframe').attr('dataSrc', data.url+'/../index.html');

    var html = "";
    $.each(data.list, function(i, elem){
    	html += ' <li class="outline'+ (i==0 ? ' active': '') +'" order="'+ (i+1) +'"><img src="'+ data.url + '/' + elem +'"></li>';
    });

    $('.outline_list').html(html);

    $('.ppt_box').removeClass('force_hide');

    // 初始化ppt加载
    loadPPT();
}

/*
*
* @Author: Hlin
* @Date:   2018-01-11 16:14:51
* @Last Modified by:   Hlin
* @Last Modified time: 2018-05-14 10:51:44
*/
// 上传教案
$(".upload_box").on('change', '.abovefile_box input[type="file"]', function(event) {
    var file = this.files[0]; //文件对象
    if (!file) { //没有选择文件则返回
        return;
    }
    var fileName = file.name; //文件名
    var fileType = fileName.substring(fileName.lastIndexOf(".")+1); //文件类型
    if (!(/\.(doc)|(docx)$/).test(fileName.toLowerCase())) { //格式检测
        layer.msg('不支持该文件格式！',{time:1000});
        return;
    }
    var formData = new FormData();
    formData.append('uploadFile', file);
    formData.append('lessonId', $('#lessonId').val());
    var layerIndex  = layer.load(2,{
        content: '<span class="uploadProcess"></span>',
        shade: 0.05
    });
    $.ajax({
        url : getRootPath() + '/tpFileUpload/uploadAboveWord.jhtml',
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
//        	//显示课件名称+教案名称
//        	$(".upload_box .file_name").html(function(index, oldHtml){
//        		return oldHtml.split('+')[0] + '+' + data.name;
//        	})
//        	$(".case_box").html(data.ret)
        	//关闭加载弹窗
        	layer.close(layerIndex);
        	 // 刷新
            window.location.reload();
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

// 格式化教案内容
function formatCaseData(){
    var data = {}
    $("table tr:nth-of-type(1) td").each(function(index, item) {
        var tdIndex = '';
        if($(this).text().trim() == '教师教学行为和语言'){
            tdIndex = $(this).index();
            $(this).parents('table').children().children('tr:not(:nth-of-type(1))').each(function(index, td) {
                var i = $(this).find('td').eq(0).text().trim();
                data[i] = $(this).find('td').eq(tdIndex).html()
            });
        }
    });

    $.each(data, function(index, item) {
        var a = '<tr><td>'+index+'</td><td>'+item+'</td></tr>'
        $('.abc').append(a)
    });
    return data;
}

/*
*
* @Author: Hlin
* @Date:   2018-01-11 16:14:51
* @Last Modified by:   Hlin
* @Last Modified time: 2018-01-26 09:26:53
*/
// 上传课件
$(".upload_box").on('change', '.coursefile_box input[type="file"]', function(event) {
    var file = this.files[0]; //文件对象
    if (!file) { //没有选择文件则返回
        return;
    }
    var fileName = file.name; //文件名
    var fileType = fileName.substring(fileName.lastIndexOf(".")+1); //文件类型
    if (!(/\.(zip)$/).test(fileName.toLowerCase())) { //格式检测
        layer.msg('不支持该文件格式！',{time:1000});
        return;
    }
    var formData = new FormData();
    formData.append('uploadFile', file);
    formData.append('lessonId', $('#lessonId').val());
    var layerIndex  = layer.load(2,{
        content: '<span class="uploadProcess"></span>',
        shade: 0.05
    });
    $.ajax({
        url : getRootPath() + '/tpCourseware/uploadCoureware.jhtml',
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
        	//接收回传的文件名并显示
            $(".upload_box .file_name").removeClass('force_hide').html(data.message);

        	writeData(data);
        	//关闭加载弹窗
        	layer.close(layerIndex);
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

// 删除文件
$(".upload_box").on('click', '.delete_file_btn', function(event) {

    layer.alert("是否删除？",{
        title: "提示", //标题
        skin: "layer-sky-skin",
        btn: ['确定','取消'], // 按钮
        closeBtn: 0, //右上角的关闭按钮，0为不显示，1为显示
        btn1: function(index, dom){ //index是该弹窗的索引
            layer.close(index); //关闭弹窗
            var lessonId = $('#lessonId').val();
            $.ajax({
    			type: "POST",
    			url: getRootPath() + "/tpCourseware/deleteUploadCoursewareFile.jhtml",
    			data: {
    				'lessonId' : lessonId
    			},
    			dataType : 'json',
    			//这里我们先拿到jQuery产生的 XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
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

            //隐藏文件名
            $(".upload_box .file_name").addClass('force_hide');
            //隐藏删除文件按钮
            $(".upload_box .delete_file_btn").addClass('force_hide');
        },
        btn2: function(index, dom){
            //按钮【按钮二】的回调
        }
    });
});

// 话述状态切换
$(".note").on('click', '.close_btn', function(event) {
    if ($(this).attr("state") == 0) {
        $(this).attr("state",1);
        $(this).parents(".note").attr("state",1);
        $(".ppt_box .iframe_box").removeAttr("note-show");
    }else{
        $(this).attr("state",0);
        $(this).parents(".note").attr("state",0);
        $(".ppt_box .iframe_box").attr("note-show","0");
    }
});

/**
 * ppt加载及加载后一些事件的监听
 */
//loadPPT(); //@测试 发布时删除
function loadPPT(){
    var pptIframe = $(".ppt_iframe");
    // 加载ppt内容
    pptIframe.attr("src",pptIframe.attr("dataSrc") + "?" + (new Date()).valueOf());
    // ppt加载完后执行的事件
    pptIframe[0].onload = function(){
        var pptIframeWindow = $(".ppt_iframe")[0].contentWindow;
        var pptIframeDocument = $(".ppt_iframe")[0].contentWindow.document;

        // 兼容ispring7和8.7
        pptIframeWindow.Q = pptIframeWindow.Q ? pptIframeWindow.Q : {
            Mc: function(){return pptIframeWindow.N.xe}, // 当前播放页
            gotoSlide: function(index) { // 跳转到某页
                pptIframeWindow.N.gotoSlide(index)
            },
            gotoNextStep: function() { // 下一步
                pptIframeWindow.N.gotoNextStep()
            },
            gotoPreviousStep: function() { // 上一步
                pptIframeWindow.N.gotoPreviousStep()
            },
            Xp:function(){ // 课件是否播放结束
                return pptIframeWindow.N.zt
            }
        };

        // 添加预设样式
        $(pptIframeDocument).find("head").append("<style>svg[height='95px'][width='207px'][version='1.1']{display: none;}</style>");
        // 获取ppt播放的当前页
        var currentPPTPage = typeof pptIframeWindow.Q.Mc == "number"?pptIframeWindow.Q.Mc:pptIframeWindow.Q.Mc();
        var outlineClick = false; //阻止用户点击目录时乱跳动
        var lastViewedSlideFlag = null;
        // 监听localStorage的变化，若变化则再次获取
        window.addEventListener("storage", function (e){

            // 当前播放页
            currentPPTPage = typeof pptIframeWindow.Q.Mc == "number"?pptIframeWindow.Q.Mc:pptIframeWindow.Q.Mc();

            if (lastViewedSlideFlag == currentPPTPage) {
                return;
            }
            lastViewedSlideFlag = currentPPTPage;
            // 话述切换
            try {
                var previewCnt = pptIframeWindow.Q.ef?(pptIframeWindow.Q.ef().$.ra.lA?pptIframeWindow.Q.ef().$.ra.lA.wf:''):pptIframeWindow.N.Rb().ma.Ca.GL.Yd
            } catch (error) {}

            var pptNoteData = formatCaseData();
            //填充话术
            $(".note .note_item:first-child").removeClass('hide').html(!$.isEmptyObject(pptNoteData) ? pptNoteData[lastViewedSlideFlag+1] : previewCnt)

            // 给当前页的缩略图添加状态
            setTimeout(function(){
                if (outlineClick) {return;}
                $(".outline").eq(currentPPTPage).addClass('active').siblings('li').removeClass('active');
                $(".outline_box").scrollTop($(".outline").eq(currentPPTPage).addClass('active')[0].offsetTop - 120);
            });
        });
        // 判断ppt是否播放结束
        $($(".ppt_iframe")[0].contentWindow.document).find("#playerView").on("click",function(){
            if ((typeof pptIframeWindow.Q.Xp == 'function'?pptIframeWindow.Q.Xp():pptIframeWindow.Q.Xp)) {
                layer.msg('播放结束！', {
                    shade : 0.01,
                    time:1000
                });
            }
        });

        // ppt切换事件
        $(".outline_box .outline_list").on('click', '.outline', function(event) {
            $(this).addClass('active').siblings('li').removeClass("active");
            var slideIndex = $(this).index();
            // 返回上一步
            pptIframeWindow.Q.gotoSlide(slideIndex);
        });

        //左键下一步
        $(pptIframeDocument).on('click', function(event) {
            pptIframeWindow.Q.gotoNextStep();
        });
        // 右键下一步
        pptIframeDocument.oncontextmenu = function(event) {
            pptIframeWindow.Q.gotoPreviousStep();
            event.returnValue=false;
        }

        // 禁止图片拖动
        $(pptIframeDocument).on('mousedown', 'img', function(event) {
            event.preventDefault();
        });
    }
}