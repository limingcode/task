var animateArray = new Array(), aboveWordArray = '';
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
function animateBind(){
    $.each($('.editor_box'), function (i, elem) {  //遍历页面数，给当前页面添加内容及动画
    	var totaPage = $('.editor_box').length;//总页数
        $(this).on('click', function () {
            var $editor_box = $('.editor_box');
            var a = $(this).find('.choose').length;
            var b = '';
            if(a == j && (i+1) < totaPage){// 当前页面所有动画播放完毕，且当前页数小于总页数进入下一页
                $(this).css('display','none');     //判断：当前页面拥有的动画执行完毕后隐藏当前页面，显示下一个页面
                $editor_box.not(this).css('display','none');
                $editor_box.eq(i+1).css('display','block');
//                $('.currPage').html(i+2);
                j = 0;
                return false;
            }
            
            if(a == j && (i+1) == totaPage){// 当前页面所有动画播放完毕，且当前页数等于总页数，不作任何处理
            	 $(this).css('display','block');     //判断：当前页面拥有的动画执行完毕后隐藏当前页面，显示下一个页面
            	 layer.msg('没有下一页了');
                 return false;
            }
            if(m==animateArray.length) {
				$(this).css('display','none');     //判断：当前页面拥有的动画执行完毕后隐藏当前页面，显示下一个页面
                $editor_box.not(this).css('display','none');
                $editor_box.eq(i+1).css('display','block');
                j = 0;
                if((i+1)!= totaPage){
//                	$('.currPage').html(i+2);
                }
                return false;
            }
            j++;
            if(sessionStorage['suolueImg']){
                k = sessionStorage.getItem('suolueImg');
                sessionStorage.clear('suolueImg');
            }
            var className = animateArray[k]['className'];
            var animationName = animateArray[k]['animationName'];
//            if(aboveWordArray[k]['text']){
//            	$('.rightCont p').html(aboveWordArray[k]['text']);
//            }
//            else{
//            	$('.rightCont p').html('');
//            }
            
            add(className,animationName);
            k++;
        });
    });
}

var m = 0;
function add(className,animationName) {        //动态添加动画事件（基于animate.css）
	$(".module[className='"+className+"']").addClass( animationName+" "+"animated"+" "+"dis_block").removeClass('animated',1000);
	m++;
}

function reDrawPage(data){
	//重绘页面
	var html = $('.pptViewDiv').html(),a = '';
    var contentJson = $.parseJSON(data.content);
    $.each(contentJson, function (i,elem) {
        html += elem.html;
    });
    $('.pptViewDiv').html(html);
  
    
    $('.pptViewDiv').find('.editor_box').css('background',data.background);
    
    $('.pptViewDiv').find('.editor_box').css('background',data.background).css('display','none').eq(0).css('display','block');
    
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
    
    var $module = $('.choose');
    $module.css({
    	'display':'none',
    	'animation-duration':'0.8s'
    });       //初始化内容框隐藏   

    $('.module').on('click', function (e) {      //阻止事件冒泡
        e.stopPropagation();
    });
    
    aboveWordArray = $.parseJSON(data.aboveWords);
    var thumbnailJson = $.parseJSON(data.thumbnail);
    var thumbnailStr = "";
    $.each(thumbnailJson, function(i,elem){
    	if(elem != null && elem != ''){
        	thumbnailStr += '<span style="display:block;margin-top:10px;"><img width=100 height=85 src="'+ elem +'" /></span>';
    	}
    });
    $(".thumbnail").html(thumbnailStr);
    
    $('.thumbnail span').click(function(){
	   	var p = 0;
	    var $index = $(this).index();
	    for(var o = 0; o<$index ;o++){
	        p += Number($('.editor_box').eq(o).find('.choose').length);
	    }
	    m = p;
	    j = 0;
	    $('.currPage').html($index+1);
	    sessionStorage.setItem('suolueImg',p);
	    $('.editor_box').eq($index).css('display', 'block').siblings('.editor_box').css('display','none');
	    $('.editor_box').find('.choose').removeClass('dis_block');
	    $('.listCont').removeClass('active');
	    $('.rightCont p').html('');
	    $('.listBtnConti').css({
    	  'background':'url(../sys/teach/images/ppt/iconsPPT_05.png) no-repeat',
          'width': '0.375rem',
          'height':'0.6855rem',
          'background-size': '100%'
      });
      $('.thumbnail').fadeOut();
      $thumbnail = true;
	});
    
}