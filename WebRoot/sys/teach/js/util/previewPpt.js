var animateArray = new Array(), aboveWordArray = '';
$('.pptPreview').bind('contextmenu',function(){
    return false;
});
$(function(){
    var id = $("#id").val();
    $.ajax({
        url : getRootPath() + '/tpCourseware/getCourseware.jhtml',
        type : "POST",
        data : {'id' : id}, 
        dataType : "json",
        cache : false,
        async : false,
        success : function(data) {
        	reDrawPage(data);
        },
        error : function(XMLHttpRequest) {  
        	
        }
    });

    animateBind();
    
});

var k = 0 ,j = 0;
var editorBox = new Array(); 
var tempAnimate = new Array();
function animateBind(){
    $.each($('.editor_box'), function (i, elem) {  //遍历页面数，给当前页面添加内容及动画
    	var totaPage = $('.editor_box').length;//总页数
        $(this).on('click', function () {
        	var $listBtnContW = $('.listBtnCont').width();
        	//功能栏缩进去
                 $('.listBtnCont').animate({
                     left:'-'+$listBtnContW+'px'
                 },500);
                 $listBtn = false;
             $('.listBtnCont').fadeOut();
             $('.listBtn').fadeIn();
             
            var $editor_box = $('.editor_box');
            var a = $(this).attr('animatenum');
            if(isEmpty(a)){
            	a = 0;
            }
            var b = '';
            
            if(a == j){// 当前页面所有动画播放完毕，且当前页数小于总页数进入下一页
                $(this).css('display','none');     //判断：当前页面拥有的动画执行完毕后隐藏当前页面，显示下一个页面
                if($(this).hasClass('pptLink')){
                	var editorBoxName = editorBox[editorBox.length-1];
                	editorBox.length = editorBox.length-1;
                	var index = $('div[name="'+ editorBoxName +'"]').index();
                	$('div[name="'+ editorBoxName +'"]').css('display','block');
                	var p = getAnimateCount(index-1);
                	j = tempAnimate[tempAnimate.length-1];
                	tempAnimate.length = tempAnimate.length-1;
             	    m = p + j;
             	    k = m;
             	    sessionStorage.clear('suolueImg');
                	return false;
                }
                while ($editor_box.eq(i+1).hasClass('pptLink')) {
                	i++;
				}
                if((i+1) == totaPage){
                	$(this).css('display','block');
                	layer.msg('没有下一页了');
                    return false;
                }
                $editor_box.eq(i+1).css('display','block');
//                $('.currPage').html(i+2);
                var p = getAnimateCount(i+1);
         	    m = p;
         	    k = m;
                j = 0;
                return false;
            }
            
            if(a == j && (i+1) == totaPage){// 当前页面所有动画播放完毕，且当前页数等于总页数，不作任何处理
            	 $(this).css('display','block');     //判断：当前页面拥有的动画执行完毕后隐藏当前页面，显示下一个页面
            	 layer.msg('没有下一页了');
                 return false;
            }
            
//            if(m==animateArray.length) {
//				$(this).css('display','none');     //判断：当前页面拥有的动画执行完毕后隐藏当前页面，显示下一个页面
//                $editor_box.not(this).css('display','none');
//                $editor_box.eq(i+1).css('display','block');
//                j = 0;
//                return false;
//            }
            j++;
            if(sessionStorage['suolueImg']){
                k = sessionStorage.getItem('suolueImg');
                sessionStorage.clear('suolueImg');
            }
            var className = animateArray[k]['className'];
            var type = animateArray[k]['type']
            var animationName = animateArray[k]['animationName'];
            if(aboveWordArray[k]['text']){
            	$('.rightCont p').html(aboveWordArray[k]['text']);
            }
            else{
            	$('.rightCont p').html('');
            }
            
            add(className,type,animationName);
            k++;
        });
//        $(this).on('mousedown',function(e){
//        	if(e.button == 2){
//        		pageJump(name, true);
//        	}
//        })
    });
}

var m = 0;
function add(className,type,animationName) {        //动态添加动画事件（基于animate.css）
	if('in' == type){
		$.each(className, function(i, ele){
			$(".module[className='"+ele+"']").addClass( animationName+" "+"animated"+" "+"dis_block").removeClass('animated',1000);
			m++;
		});
	} else {
		$.each(className, function(i, ele){
			$(".module[className='"+ele+"']").addClass( animationName+" "+"animated"+" "+"dis_block");
			m++;
		});
	}
}


function reDrawPage(data){
	//重绘页面
	var html = $('.leftCont').html(),a = '';
    var contentJson = $.parseJSON(data.content);
    $.each(contentJson, function (i,elem) {
        html += elem.html;
    });
    $('.leftCont').html(html);
    
    $('.moduleAudio').remove();
    
    $('.leftCont').find('.editor_box').css('background',data.background).css('display','none').eq(0).css('display','block');
    $('.text').find('ul').children('li').css('list-style-type','disc');
    $('.text').find('ol').children('li').css('list-style-type','decimal');
//    $.each($('.text').children('ul'),function(){
//    	$(this).css('font-size','0.203125rem');
//    });
//    $.each($('.text').children('ol'),function(){
//    	$(this).css('font-size','0.203125rem');
//    });
    $('p').attr('contenteditable',false);
    $('h1').attr('contenteditable',false);
    $('ul').attr('contenteditable',false);
    $('ol').attr('contenteditable',false);
    
    $.each($('svg'),function(){
    	var a = $(this).find('use').attr('xlink:href')
    	$(''+a+'').attr('preserveAspectRatio','none');
    });
    
    animateArray = $.parseJSON(data.animation);
    
    //视频绑定
    $('video').each(function(i,elem){
    	var id = $(this).attr('id');
    	videojs(id, {}, function(){  //视频播放绑定
    		$('.vjs-fullscreen-control').each(function(i,elem){
    			$(this).on('click',function(e){
    				e.stopPropagation();
    			});
    		});
      	});
    });
    
    $('audio').each(function(i,elem){
		var id = $(this).attr('id');
		audioAnimation(id);
		var audio = document.getElementById(id);
	    $(audio).siblings('.star').css({
	        'background':'url(../sys/teach/images/ppt/iconfont/pptIcon_32.png)',
	        'backgroundSize':'100%'
	    });
	});
    
    var $module = $('.choose');
    $module.css({
    	'display':'none',
    	'animation-duration':'0.8s'
    });       //初始化内容框隐藏   

    $('.videoBoxIndex').on('click', function (e) {      //阻止事件冒泡
        e.stopPropagation();
    });
    $('.superLink_a').on('click', function (e) {      //阻止事件冒泡
    	e.stopPropagation();
    });
    
    aboveWordArray = $.parseJSON(data.aboveWords);
    var thumbnailJson = $.parseJSON(data.thumbnail);
    var thumbnailStr = "";
    $.each(thumbnailJson, function(i,elem){
    	if(elem != null && elem != ''){
    		if(!$('.editor_box:eq('+i+')').hasClass('pptLink')){
    			var name = $('.editor_box:eq('+i+')').attr('name');
    			thumbnailStr += '<li name="'+name+'"><img width=100 height=85 src="'+ elem +'" /></li>';
    		}
    	}
    });
    $(".thumbnail").html(thumbnailStr);
    
    $('.thumbnail li').click(function(){
    	 editorBox.length = 0;
    	 tempAnimate.length = 0;
    	 var $index = $(this).attr('name');
    	 pageJump($index, true);
	});
    
    $('.superLink_a').click(function(){
    	var name = $(this).parents('.editor_box').attr('name');
    	editorBox.push(name);
    	tempAnimate.push(j);
    	var index = $(this).attr('index');
    	pageJump(index);
    });
    
    function pageJump(name, isTurnPage){
	    $('.editor_box[name="'+name+'"]').css('display', 'block').siblings('.editor_box').css('display','none');
	    var index = $('.editor_box[name="'+name+'"]').index();
	    var p = getAnimateCount(index-1);
	    sessionStorage.setItem('suolueImg',p);
	    m = p;
	    j = 0;
	    if(isTurnPage){
	    	$('.editor_box').find('.choose').removeClass('dis_block');
	    	$('.listCont').removeClass('active');
	    	$('.listBtnConti').css({
	    		'background':'url(../sys/teach/images/ppt/iconsPPT_05.png) no-repeat',
	    		'width': '0.375rem',
	    		'height':'0.6855rem',
	    		'background-size': '100%'
	    	});
	    	$('.thumbnail').fadeOut();
	    	$thumbnail = true;
	    }
        $('.rightCont p').html('');
    };
    
    $('.editor_box').each(function(){
    	$(this).on('mousedown',function(e){
		if($('.editor_box:visible').prev('.editor_box').length != 0){
    			if(e.button == 2){
    				var name = $('.editor_box:visible').prev('.editor_box').attr('name');
    				pageJump(name, true);
    			}
    		}
        })
    })
}



function getAnimateCount(index){
	var p = 0;
    for(var o = 0; o<index ;o++){
    	var currPageAnimate = $('.editor_box').eq(o).attr('animatenum');
    	if(isEmpty(currPageAnimate)){
    		currPageAnimate = 0;
    	}
        p += Number(currPageAnimate);
    }
    return p;
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