$('.menu_box .fileTitle').on('click',function(){
	window.history.go(-1); 
});

$(document).on('click','.savePpt',function () {
	addLoading("数据保存中...");
	setTimeout(function(){
		var file_name = $('.file_name').html();
		if(isEmpty(file_name)){
			save();
			return false;
		}
		saveUploadCoureware();
	}, 500);
});

function save(){
	//加载层-风格4
    var end_1 = new Array;
    var end_2 = new Array();
    var _end = new Array();
    var content = '';
    $.each($('.module'),function(){
    	if($(this).css('border') == '2px dotted rgb(212, 212, 212)'){
    		$(this).css('border','none');
    	}
    });
    $.each($('.animateList li'), function (i, elem) {
    	var className = $(elem).attr('class');
        saveListText[i] = {
        	'className':className,
            'text':$(elem).attr('text')
        };
        var a  = txtArr[className];
        $(elem).attr('text',a);
        saveListTow[i] = {
            "className":$(this).attr('class'),
            'type':$(this).attr('type'),
            "animationName":$(this).find('.index').attr('animationname'),
            "animateName_CN":$(this).find('.cnt').html()
        };
    });

    $('.videoRRR').each(function (i, elem) {
        var c = $(this).find('video').html();
        var b = '<video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="100%" height="100%">'+
            ''+c+''+
            '</video>';
        $(this).html(b);
    });

    var backgroundArr = new Array();
    $('.editor_box').each(function (i, elem) {
        var a = $(elem)[0].outerHTML;
        end_1[i] = {
            html:a
        };
        var background = $(this).css('background');
        
        var reg = new RegExp("/upload/teach/courseware/\\d{0,10}/theme/(\\w|-)+\\.(jpg|jpeg|bmp|png|gif)\\b");
     	var bg = background.match(reg);
     	console.log(bg);
     	if(bg != null){
     		backgroundArr.push(bg[0]);
     	}
    });
    $('.page_box').each(function (i, elem) {
        var b = $(elem).find('img').attr('src');
        end_2.push(b);
    });
    
//    localStorage.setItem('animateList',saveListTow);
    _end = {
        html:end_1,
        animate:saveListTow,
        text:saveListText,
        img:end_2
    };
    console.log(backgroundArr);
    savePptInfo(JSON.stringify(end_1),JSON.stringify(saveListCeShi),JSON.stringify(saveListText),JSON.stringify(end_2), backgroundArr);
}

function savePptInfo(content, animation, aboveWords, img, background){
	$(".savePpt").attr('diabled', true);
	var id = $("#id").val();
	var lessonId = $("#lessonId").val();
	var formData = new FormData(); 
	formData.append('id',id);
	formData.append('lessonId',lessonId);
	formData.append('content',content);
	formData.append('animation',animation);
	formData.append('aboveWords',aboveWords);
	formData.append('thumbnail',img);
	formData.append('background',background);
	$.ajax({
		type : "POST",
		url : getRootPath() + "/tpCourseware/saveCourse.jhtml",
		async: false,                //true为异步加载，false为同步加载，参数为空时，默认异步加载
		cache: false,
		data : formData,
		processData : false,
		contentType : false,
		dataType : "json",
//		timeout : 5000,
		success : function(data) {
			$("#id").val(data.id);
			$(".savePpt").attr('diabled', false);
			removeLoading();
			layer.msg('保存成功!');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("保存出错，请重试！");
			removeLoading();
		}
	});
}

$(document).on('click','.lookPpt',function () {
	previewPpt();
});

function previewPpt(){
	var id = $("#id").val();
	if(id.length==0){
		layer.msg('请先保存数据！');
		return false;
	}
	var url = getRootPath() + "/tpCourseware/toPreviewPpt.jhtml?id="+id;
	window.open(url);
}


function uploadAllTypeFile(obj,fileType){
	var file = $(obj)[0].files[0];
	if(file == undefined || file == ""){
		return false;
	}
	var lessonId = $("#lessonId").val();
	var formData = new FormData();
	formData.append('uploadFile', file);
	formData.append('lessonId', lessonId);
	formData.append('fileType', fileType);
	
	$.ajax({
		type : "POST",
		url : getRootPath() + "/tpFileUpload/uploadFile.jhtml",
		data : formData,
		dataType : "json",
		processData : false,
		contentType : false,
		//这里我们先拿到jQuery产生的 XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
//		xhr : xhr_provider,
		success : function(data){
			if(data.success){
				writeData(data);
			}else{
				layer.msg(data.message);
			}
			$(obj).val('');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg('上传失败，请重试');
		}
	});
	
}

function writeData(data){
	var type = data.fileType;
	var classname = sessionStorage['className'];
	layer.msg(data.message);
	switch (type) {
	case 1:  //图片
		writeImageDate(data.url)
		break;
	case 2:
		writeAudioData(data.url);
		break;
	case 3:  //动画
		break;
	case 4:  //视频
		writeVideoDate(data.url);
		break;
	case 7:
		writeTheme(data.url);
//		$('.editor_box').css('background', 'url('+data.url+') center center / 100% 100% no-repeat');
//		$('.page').find('img').attr('src', data.url);
//		sessionStorage.setItem('listImg', data.url);
		break;
	default:
		break;
	}
}

function writeTheme(url){
	layer.confirm('<div style="width: 100%;text-align: center">选一个吧</div>',{
        btn:['应用','全部应用','取消'],
        title:'<div style="padding-left: 22px">页眉和页脚</div>'
    }, function (e) {
        $('.editor_box:visible').css({
            background:'url('+ url +') no-repeat center center',
            backgroundSize:'100% 100%'
        });
        $('.page[name="'+$('.editor_box:visible').attr('name')+'"]').find('img').attr({
            src:url,
        });
        layer.close(e);
    }, function () {
        $('.editor_box').css({
            background:'url('+ url +') no-repeat center center',
            backgroundSize:'100% 100%'
        });
        $('.page').find('img').attr({
            src:url,
        });
        sessionStorage.setItem('listImg',url);
    });
}

function writeImageDate(url){
	 var type = '图片占位符';
     var $thisName = 'box'+imgIndex+'';
     var text = '<div className="box'+imgIndex+'" class="module moduleImg"><img style="cursor:auto;width:100%;height:100%;" src='+url+' alt=""></div>';
     E.insert(text,type,$thisName);
     imgIndex++;
}

function writeVideoDate(url){
	var videoCont = '<div style="width:60%;height:45%" className="box'+videoBoxIndex+'" class="module videoBoxIndex">' +
	    '<div class="videoRRR" style="width:100%;height: 100%;margin:0 auto;position: relative;">'+
	    '<video id="video'+videoBoxIndex+'" class="video-js vjs-default-skin" controls preload="none" width="100%" height="100%">'+
	    '<source src="'+ url +'" type="video/mp4" />'+
	    '<source src="'+ url +'" type="video/webm" />'+
	    '<source src="'+ url +'" type="video/ogg" />'+
	    '</video>'+
	    '</div>'+
	    '</div>';
	E.insert(videoCont);
	var id = "video"+videoBoxIndex;
	videojs(id, {}, function(){
    });
	videoBoxIndex++;
}

function writeAudioData(url){
	// 点击添加音频
	
	var length = $('.editor_box:visible').find('audio').length;
    var audioCont = '<div style="width:70px;height:70px;" className="box'+audioBoxIndex+'" class="module audio'+ audioBoxIndex+' audioBoxIndex ">' +
			    '<div class="audioIcon">'+
    			'<div class="audioCont">'+
			    '<audio id="audio_'+audioBoxIndex+'" style="display: none;" src="'+url+'"></audio>'+
			    '<a class="star" href="javascript:;" title="播放/暂停"></a>'+
			    '<div class="slider">'+
			    '<div class="sliderTow"></div>'+
			    '</div>'+
			    '<span class="time">00:00</span>'+
			    '<a class="yinliang" href="javascript:;" title="静音/取消静音"></a>'+
			    '<div class="sliderY">'+
			    '<div class="sliderTowY"></div>'+
			    '</div>'+
			    '<div class="more"></div>'+
			    '<div class="textCont" contenteditable="true"></div>'+
			    '</div>'+
			    '</div>'+
               	'</div>';
    E.insert(audioCont);
    audioAnimation('audio_'+audioBoxIndex);
    
    audioBoxIndex++;
}

function audioAnimation(audioId){
	var isDown = false;
    var isDownY = false;
    var isYin = false;
    var audio = document.getElementById(audioId);
    var volume = audio.volume;
    $(audio).siblings('.sliderY').find('.sliderTowY').css('width', $('.sliderY').width()*volume+'px');
    
    //音频进度
    $(audio).siblings('.slider').mousedown(function (e) {
        e.stopPropagation();
        var sliderT = $(this).find('.sliderTow');
        sliderT.css('width', e.offsetX+'px');
        var sliderTowW = sliderT.width();
        var w = $(this).width();
        audio.currentTime = (sliderTowW/w).toFixed(2)*audio.duration;
        isDown = true;
    }).mousemove(function (e) {
        e.stopPropagation();
//        var audioId = $(this).siblings('audio').attr('id');
//    	var audio = document.getElementById(audioId);
        if(isDown){
            var sliderT = $(this).find('.sliderTow');
            sliderT.css('width', e.offsetX+'px');
            var sliderTowW = sliderT.width();
            var w = $(this).width();
            audio.currentTime = (sliderTowW/w).toFixed(2)*audio.duration;
        }
    }).on('mouseup',function () {
        isDown = false;
    });

    //音频音量调整
    $(audio).siblings('.sliderY').mousedown(function (e) {
        var sliderT = $(this).find('.sliderTowY');
        sliderT.css('width', e.offsetX+'px');
        var sliderTowW = sliderT.width();
        var w = $(this).width();
        audio.volume = (sliderTowW/w).toFixed(1);
        isDownY = true;
        if(audio.volume <0){
            audio.volume = 0;
            $('.yinliang').css({
                'background':'url(../sys/teach/images/ppt/iconfont/pptIcon_34.png)',
                'backgroundSize':'100%'
            });
            isYin = true;
        }
        else if(audio.volume>0){
            $('.yinliang').css({
                'background':'url(../sys/teach/images/ppt/iconfont/pptIcon_35.png)',
                'backgroundSize':'100%'
            });
            isYin = false;
        }
    }).mousemove(function (e) {
        e.stopPropagation();
        if(isDownY){
            var sliderT = $(this).find('.sliderTowY');
//            console.log(sliderT)
            sliderT.css('width', e.offsetX+'px');
            var sliderTowW = sliderT.width();
            var w = $(this).width();
            audio.volume = (sliderTowW/w).toFixed(1);
        }
    }).on('mouseup',function () {
        isDownY = false;
    });
    
    //音频播放/暂停按钮
    $(audio).siblings('.star').on('click',function(){
    	if(audio.paused){
            audio.play();
            $(this).css({
                'background':'url(../sys/teach/images/ppt/iconfont/pptIcon_33.png)',
                'backgroundSize':'100%'
            });
        }else{
            audio.pause();
            $(this).css({
                'background':'url(../sys/teach/images/ppt/iconfont/pptIcon_32.png)',
                'backgroundSize':'100%'
            });
        }
    })

    $(audio).siblings('.yinliang').on('click', function (e) {
        e.stopPropagation();
        if(!isYin){
            $(this).css({
                'background':'url(../sys/teach/images/ppt/iconfont/pptIcon_34.png)',
                'backgroundSize':'100%'
            });
            audio.volume = 0;
            $(this).siblings('.sliderY').find('.sliderTowY').css('width', $('.sliderY').width()*volume+'px');
            isYin = true;
        }
        else{
            $(this).css({
                'background':'url(../sys/teach/images/ppt/iconfont/pptIcon_35.png)',
                'backgroundSize':'100%'
            });
            audio.volume = 1;
            $(this).siblings('.sliderY').find('.sliderTowY').css('width', $('.sliderY').width()*volume+'px');
            isYin = false;
        }
    });
    setInterval(function () {
        var currentTime = audio.currentTime;
        setTimeShow(currentTime);
    },1000);

    //设置时间
    function setTimeShow(t){
        t = Math.floor(t);
        var t2 = Math.floor(audio.duration);
        var playTime = secondToMin(audio.currentTime);
        var playTime2 = secondToMin(audio.duration);
        var silderW = $(audio).siblings('.slider').width();

        $(audio).siblings('.time').html(playTime);
        $(audio).siblings('.slider').find('.sliderTow').css('width',''+Math.floor(t/t2*silderW)+'px');
//        console.log(Math.floor(t/t2*silderW)+'px')
    }


    //转换时间
    function secondToMin(s){
        var MM = Math.floor(s / 60);
        var SS = s % 60;
        if(MM < 10){
            MM = '0' +MM;
        }
        if(SS < 10){
            SS = '0' + SS;
        }
        var min = MM + ':' +SS;
        return min.split('.')[0];
    }
    var isShow = false;
    $('.audioCont').on('click','.more', function () {
        if(!isShow){
            $('.textCont').show();
            isShow = true;
        }
        else{
            $('.textCont').hide();
            isShow = false;
        }
    });
    
//    显隐播放进度
    $('.audioBoxIndex').on('mouseenter',function(e){
    	$(this).find('.audioCont').css('display','block');
    });
    $('.audioBoxIndex').on('mouseleave',function(e){
    	$(this).find('.audioCont').css('display','none');
    });
}

var xhr_provider = function() {
	var xhr = $.ajaxSettings.xhr();
	if (onprogress && xhr.upload) {
		xhr.upload.addEventListener("progress" , onprogress, false);
	}
	return xhr;
}

function onprogress(evt) {
    var progressBar = document.getElementById("progressBar");
    var percentageDiv = document.getElementById("percentage");
    if (evt.lengthComputable) {
        progressBar.max = evt.total;
        progressBar.value = evt.loaded;
        percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
        if(evt.loaded==evt.total){
//             alert("上传完成100%");
        }
    }
} 

function countCtrl(){
	var textBoxIndexCount = 1;
	$('.textBoxIndex').each(function(){
		var className = $(this).attr('className');
		var index = className.replace('box','');
		if(Number(index) > textBoxIndexCount){
			textBoxIndexCount = index;
		}
	});
	textBoxIndex += ++textBoxIndexCount;
    
    var imgIndexCount = 1;
	$('.imgIndex').each(function(){
		var className = $(this).attr('className');
		var index = className.replace('box','');
		if(Number(index) > imgIndexCount){
			imgIndexCount = index;
		}
	});
	imgIndex += ++imgIndexCount;
    
    var svgIndexCount = 1;
	$('.svgIndex').each(function(){
		var className = $(this).attr('className');
		var index = className.replace('box','');
		if(Number(index) > svgIndexCount){
			svgIndexCount = index;
		}
	});
    svgIndex += ++svgIndexCount;
    
    var videoCount = 1;
	$('.videoBoxIndex').each(function(){
		var className = $(this).attr('className');
		var index = className.replace('box','');
		if(Number(index) > videoCount){
			videoCount = index;
		}
	});
    videoBoxIndex += ++videoCount;
    
    var audioCount = 1;
	$('.audioBoxIndex').each(function(){
		var className = $(this).attr('className');
		var index = className.replace('box','');
		if(Number(index) > audioCount){
			audioCount = index;
		}
	});
	audioBoxIndex += ++audioCount;
    
    var sheetIndexCount = 1;
	$('.sheetIndex').each(function(){
		var className = $(this).attr('className');
		var index = className.replace('box','');
		if(Number(index) > sheetIndexCount){
			sheetIndexCount = index;
		}
	});
    sheetIndex += ++sheetIndexCount;
    
    var boxCutImgCount = 1;
	$('.boxCutImg').each(function(){
		var className = $(this).attr('className');
		var index = className.replace('box','');
		if(Number(index) > boxCutImgCount){
			boxCutImgCount = index;
		}
	});
    boxCutImg += ++boxCutImgCount;
}

function videoCtrl(){
	$('.videoRRR').each(function (i, elem){
		var id = $(this).find('.video-js').attr('id');
		videojs(id, {}, function(){
		});
	});
}

function renderPage(content, animation, aboveWords, img){
	var html = "";
 	$.each(content, function (i,elem) {
        html += elem.html;
    });
    $('.right').html(html);
    
//    $('.right').find('.editor_box').css('background',background).css('display','none').eq(0).css('display','block');
    
    $.each($('svg'),function(){
    	var a = $(this).find('use').attr('xlink:href')
    	$(''+a+'').attr('preserveAspectRatio','none');
    })
    
//    var reg = new RegExp("/task/sys/teach/images/ppt/theme/\\d{3}.jpg|/upload/teach/courseware/\\d{0,10}/theme/(\\w|-)+\\.(jpg|jpeg|bmp|png|gif)\\b");
// 	var bg = background.match(reg);
// 	if(bg != null){
// 		sessionStorage.setItem('listImg', bg[0]);
// 	}else{
// 		sessionStorage.removeItem('listImg');
// 	}
    
 	var imgHtml = "";
 	$.each(img, function(i,elem){
 		imgHtml += '<li class="page_box">' +
 					'<i></i>'+
 					'<span class="page_index">'+ (i+1) +'</span>' +
 					'<span class="closePPT">×</span>' +
 					'<div class="page '; 
 		if(i == 0){
 			imgHtml += 'border_org';
 		}
 		imgHtml += '" name="'+ (i+1) +'"><img src="'+ elem +'" alt=""></div>' +
 					'</li>';
 	});
 	$('.left').find('ul').html(imgHtml);
 	
 	$('.page_box').find('img').eq(0).click();
 	
 	$(".left").on('click', '.page_box .page', function(event) {
 		$(".editor_box[name='"+$(this).attr("name")+"']").show().attr('id','domImg').siblings('.editor_box').hide().attr('id','');
 		$(this).addClass('border_org').parent('.page_box').siblings('.page_box').find('.page').removeClass('border_org');
 	});
 	
 	$('.closePPT').on('click', function () {
        var $thisIndex = $(this).siblings('.page').attr('name');
        $(this).parent('.page_box').remove();

        $(".left .page_box").each(function(index, el) {
            $(this).find(".page_index").html(index+1);
        });

        $.each($('.editor_box'), function (i,elem) {
            if($(elem).attr('name')==$thisIndex){
                $(elem).remove();
            }
        })
    });
 	
 	var animate = "";
	$.each(animation, function(i,elem) {
		saveListCeShi.push(elem);
		animate += '<li class="'+ elem.className +'" text="'+ trimBlock(aboveWords[i].text) +'"><span class="index" animationname="'+ elem.animationName +'">'+Number(1+Number(i))+'</span><span class="cnt">'+elem.animateName_CN+'</span><span class="close_btn hide"></span>';
	});
	$(".animateList").html(animate);
	
	$('audio').each(function(i,elem){
		var id = $(this).attr('id');
		audioAnimation(id);
		var audio = document.getElementById(id);
	    $(audio).siblings('.star').css({
	        'background':'url(../sys/teach/images/ppt/iconfont/pptIcon_32.png)',
	        'backgroundSize':'100%'
	    });
	});
	
	videoCtrl();
	
	countCtrl();
	
	$.each($('.module'), function (i,elem) {
        $('.animateList .close_btn').on('click',function () {
            $(this).parent().remove();
            if($(elem).attr('className')==$(this).parent().attr("class")){
                $(elem).removeAttr('animationName')
            }
            $('.animateList li').each(function(i,el){
                $(this).find(".index").html(i+1);
            });
            delete saveListTow[$(this).parent().find('.index').html()-1];
            delete saveList[$(this).parent().attr('class')];
        });
    });
	
    $.each($('.module'),function(index, el) {
        $(this).draggable();
        eightResize(this);
        //判断是否为新增，是则给其绑定拖动个拖拽大小事件
        $(this).on('click', function () {
           sessionStorage.setItem('classname',$(this).attr('classname'))
        });
        if ($(this).hasClass('ui-draggable')) {
            $(this).draggable({
                stop:function(event,ui){
                    var $editor = $('.editor_box:visible');
                    var a = $editor.width();
                    var a_1 = $editor.height();
                    var b = parseInt($(this).css('left'));
                    var b_1 = parseInt($(this).css('top'));
                    var c = b/a*100;
                    var c_1 = b_1/a_1*100;
                    $(this).css('left',c+'%');
                    $(this).css('top',c_1+'%');
                }
            });
        }
        $(this).find('.text').attr('contenteditable','true');

    });

    function eightResize(elem){
        // 禁止子元素冒泡
        $(elem).children().each(function(index, el) {
            $(this).mousedown(function(event) {
                event.stopPropagation();
            });
        });
        var Sys = (function(ua){
            var s = {};
            s.IE = ua.match(/msie ([\d.]+)/)?true:false;
            s.Firefox = ua.match(/firefox\/([\d.]+)/)?true:false;
            s.Chrome = ua.match(/chrome\/([\d.]+)/)?true:false;
            s.IE6 = (s.IE&&([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6))?true:false;
            s.IE7 = (s.IE&&([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 7))?true:false;
            s.IE8 = (s.IE&&([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 8))?true:false;
            return s;
        })(navigator.userAgent.toLowerCase());
        var _$ = function (id) {
            return elem.querySelector(id);
        };
        var Css = function(e,o){
            for(var i in o)
                e.style[i] = o[i];
        };
        var Extend = function(destination, source) {
            for (var property in source) {
                destination[property] = source[property];
            }
        };
        var Bind = function(object, fun) {
            var args = Array.prototype.slice.call(arguments).slice(2);
            return function() {
                return fun.apply(object, args);
            }
        };
        var BindAsEventListener = function(object, fun) {
            var args = Array.prototype.slice.call(arguments).slice(2);
            return function(event) {
                return fun.apply(object, [event || window.event].concat(args));
            }
        };
        var CurrentStyle = function(element){
            return element.currentStyle || document.defaultView.getComputedStyle(element, null);
        };
        function addListener(element,e,fn){
            element.addEventListener?element.addEventListener(e,fn,false):element.attachEvent("on" + e,fn);
        };
        function removeListener(element,e,fn){
            element.removeEventListener?element.removeEventListener(e,fn,false):element.detachEvent("on" + e,fn)
        };
        var Class = function(properties){
            var _class = function(){return (arguments[0] !== null && this.initialize && typeof(this.initialize) == 'function') ? this.initialize.apply(this, arguments) : this;};
            _class.prototype = properties;
            return _class;
        };
        // 8-24 
        
        var Resize = new Class({
            initialize : function(obj){
                this.obj = obj;
                this.resizeelm = null;
                this.fun = null; //记录触发什么事件的索引
                this.original = []; //记录开始状态的数组
                this.width = null;
                this.height = null;
                this.fR = BindAsEventListener(this,this.resize);
                this.fS = Bind(this,this.stop);
                this.class = $(obj)
            },
            set : function(elm,direction){
                if(!elm)return;
                this.resizeelm = elm;
                addListener(this.resizeelm,'mousedown',BindAsEventListener(this, this.start, this[direction]));
                return this;
            },
            start : function(e,fun){
                this.fun = fun;
                this.original = [parseInt(CurrentStyle(this.obj).width),parseInt(CurrentStyle(this.obj).height),parseInt(CurrentStyle(this.obj).left),parseInt(CurrentStyle(this.obj).top)];
                this.width = (this.original[2]||0) + this.original[0];
                this.height = (this.original[3]||0) + this.original[1];
                addListener(document,"mousemove",this.fR);

                addListener(document,'mouseup',this.fS);
            },
            resize : function(e){
                this.fun(e);
                Sys.IE?(this.resizeelm.onlosecapture=function(){this.fS()}):(this.resizeelm.onblur=function(){this.fS()})
            },
            stop : function(even){
                removeListener(document, "mousemove", this.fR);
                removeListener(document, "mousemove", this.fS);
            },
            up : function(e){
                var $editor_box = $('.editor_box:visible');
                var bH = $editor_box.height();
                this.height>e.clientY-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop?Css(this.obj,{top:(e.clientY-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop)/bH*100 + "%",height:(this.height-e.clientY+this.obj.parentNode.offsetTop-this.obj.parentNode.parentNode.scrollTop)/bH*100 + "%"}):this.turnDown(e);
            },
            down : function(e){
                var $editor_box = $('.editor_box:visible');
                var bH = $editor_box.height();
                e.clientY-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop>this.original[3]?Css(this.obj,{top:this.original[3]/bH*100+'%',height:(e.clientY-this.original[3]-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop - 10)/bH*100 +'%'}):this.turnUp(e);
            },
            left : function(e){
                var $editor_box = $('.editor_box:visible');
                var bW = $editor_box.width();
                e.clientX-this.obj.parentNode.offsetLeft+this.obj.parentNode.parentNode.scrollLeft<this.width?Css(this.obj,{left:(e.clientX-this.obj.parentNode.offsetLeft+this.obj.parentNode.parentNode.scrollLeft)/bW*100 +'%',width:(this.width-e.clientX+this.obj.parentNode.offsetLeft-this.obj.parentNode.parentNode.scrollLeft)/bW*100 + "%"}):this.turnRight(e);
            },
            right : function(e){
                var $editor_box = $('.editor_box:visible');
                var bW = $editor_box.width();
                e.clientX-this.obj.parentNode.offsetLeft+this.obj.parentNode.parentNode.scrollLeft>this.original[2]?Css(this.obj,{left:this.original[2]/bW*100+'%',width:(e.clientX-this.original[2]-this.obj.parentNode.offsetLeft+this.obj.parentNode.parentNode.scrollLeft - 10)/bW*100 +"%"}):this.turnLeft(e)    ;
            },
            leftUp:function(e){
                this.up(e);this.left(e);
            },
            leftDown:function(e){
                this.left(e);this.down(e);
            },
            rightUp:function(e){
                this.up(e);this.right(e);
            },
            rightDown:function(e){
                this.right(e);this.down(e);
            },
            turnDown : function(e){
                var $editor_box = $('.editor_box:visible');
                var bH = $editor_box.height();
                Css(this.obj,{top:this.height/bH*100+'%',height:(e.clientY-this.obj.parentNode.offsetTop - this.height+this.obj.parentNode.parentNode.scrollTop)/bH*100 + '%'});
            },
            turnUp : function(e){
                var $editor_box = $('.editor_box:visible');
                var bH = $editor_box.height();
                Css(this.obj,{top : (e.clientY-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop)/bH*100 +'%',height : (this.original[3] - e.clientY+this.obj.parentNode.offsetTop-this.obj.parentNode.parentNode.scrollTop)/bH*100 +'%'});
            },
            turnRight : function(e){
                var $editor_box = $('.editor_box:visible');
                var bW = $editor_box.width();
                Css(this.obj,{left:this.width/bW*100+'%',width:(e.clientX-this.obj.parentNode.offsetLeft- this.width - this.obj.parentNode.parentNode.scrollLeft)/bW*100 +'%'});
            },
            turnLeft : function(e){
                var $editor_box = $('.editor_box:visible');
                var bW = $editor_box.width();
                Css(this.obj,{left:(e.clientX-this.obj.parentNode.offsetLeft+this.obj.parentNode.parentNode.scrollLeft)/bW*100 +'%',width:(this.original[2]-e.clientX+this.obj.parentNode.offsetLeft-this.obj.parentNode.parentNode.scrollLeft)/bW*100+'%'});
            }
        });
        var R = new Resize(elem);
        R.set(_$('.rUp'),'up').set(_$('.rDown'),'down').set(_$('.rLeft'),'left').set(_$('.rRight'),'right').set(_$('.rLeftUp'),'leftUp').set(_$('.rLeftDown'),'leftDown').set(_$('.rRightDown'),'rightDown').set(_$('.rRightUp'),'rightUp');
    }
}

$(function(){
	 $(document).on('paste','*',function (e) {
         e.stopPropagation();
         var $className = $(this).attr('classname');
         pasteEvent = e;
         
         var data = pasteEvent.clipboardData || pasteEvent.originalEvent.clipboardData;
         var text;
         var items;

         // -------- 试图获取剪切板中的文字，有文字的情况下，就不处理图片粘贴 --------
         if (data == null) {
             text = window.clipboardData && window.clipboardData.getData('text');
         } else {
             text = data.getData('text/plain') || data.getData('text/html');
         }
         if (text) {
             return;
         }

         items = data && data.items;
         if (items) {
             // -------- chrome 可以用 data.items 取出图片 -----

             $.each(items, function (key, value) {
                 var fileType = value.type || '';
                 if(fileType.indexOf('image') < 0) {
                     // 不是图片
                     return;
                 }

                 var file = value.getAsFile();
                 var reader = new FileReader();

                 reader.onload = function (e) {
                     
                	 // 执行上传
                     var data = uploadBase64File(e.srcElement.result);
                     
                     if(isNotEmpty(data)){
                    	 
                    	 var a = '<img width="100%" height="100%" src="'+ data.url +'" alt="">';
                    	 var type = '图片占位符';
                    	 var $thisName = 'boxCutImg'+boxCutImg+'';
                    	 var cutImg = "<div class='module moduleImg boxCutImg' className='boxCutImg"+boxCutImg+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'>"+a+"</div></div>";
                    	 E.insert(cutImg,type,$thisName);
                    	 boxCutImg++;
                     }
                     
                 };

                 //读取粘贴的文件
                 reader.readAsDataURL(file);
             });
         } else {
             // -------- 非 chrome 不能用 data.items 取图片 -----

             // 获取
             $imgsBeforePaste = $('.text').find('img');

             // 异步上传找到的图片
             setTimeout(findPasteImgAndUpload, 0);
         }
     });
     
    function uploadBase64File(imgStr){
    	var id = $("#id").val();
		var lessonId = $("#lessonId").val();
		var returnData = "";
		$.ajax({
			type : "POST",
			url : getRootPath() + "/tpFileUpload/uploadBasa64File.jhtml",
		    async: false,                //true为异步加载，false为同步加载，参数为空时，默认异步加载
		    cache: false,
			data : {'lessonId': lessonId, 'imgStr': imgStr},
			dataType : "json",
//			timeout : 5000,
			success : function(data) {
				returnData = data;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.msg("上传图片出错，请重试！");
			}
		});
		
		return returnData;
     }
	
});