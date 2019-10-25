<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html">
<html lang="en" class="pptPreview">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>PPT预览</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/video-js.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/animate.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/start.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/pptPreview.css?20170929">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/wordArt.css">
    <script src="<%=path%>/sys/teach/js/jquery-3.2.1.min.js"></script>
    <script src="<%=path%>/sys/teach/js/flexible.js"></script>
    <script src="<%=path%>/sys/teach/js/ppt/music.js"></script>
    <c:if test="${courseware.pptType eq 1}">
   		<script src="<%=path%>/sys/teach/js/util/previewPpt.js"></script>
	</c:if>
    <script src="<%=path%>/sys/teach/js/common.js"></script>
    <script src="<%=path%>/sys/teach/js/layer.js"></script>
    <script src="<%=path%>/sys/teach/js/video.js"></script>
    <script src="<%=path%>/sys/teach/js/lib/iconfont.js"></script>
</head>
<body style="background: none">
<input type="hidden" id="id" value="${param.id}">
<input type="hidden" id="lessonId" value="${courseware.lessonId}">
<div style="position: absolute;z-index: 10;" class="canvasCont"></div>
    <div class="pptPreviewCont">
    	<c:if test="${courseware.pptType eq 1}">
	        <div class="leftCont">
	           <div>
	                <div class="listBtnCont">
	                    <i class="listBtnConti"></i>
	                    <i></i>
	                    <ul class="thumbnail">
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                    </ul>
	                    <ul class="penNum">
	                        <li><i></i></li>
	                        <li><i></i></li>
	                        <li><i></i></li>
	                    </ul>
	                </div>
                    <span class="listBtn"></span>
	            </div> 
	        </div>
        </c:if>
        <c:if test="${courseware.pptType eq 2}">
	        <div class="leftCont" style="overflow: hidden;">
	           <div>
	                <div class="listBtnCont">
	                    <i class="listBtnConti"></i>
	                    <i></i>
	                    <ul class="thumbnail">
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                        <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></li>
	                    </ul>
	                    <ul class="penNum">
	                        <li><i></i></li>
	                        <li><i></i></li>
	                        <li><i></i></li>
	                    </ul>
	                </div>
	              <span class="listBtn"></span>
	            </div>
	        	<div class="leftIframe" style="height:100%;overflow: hidden;"> 
	        		<iframe width="100%" height="100%" style="border:0" src="../../upload/teach/courseware/${courseware.lessonId}/type2/index2.html"></iframe>
	        	</div>
	        </div>
        </c:if>
        <div class="rightCont">
            <p style="font-size: 20px"></p>
        </div>
        <c:if test="${courseware.pptType eq 2 }">
	        <script>
		     	// ppt切换栏添加拖动监听
		        var cy;
		        var cScrollTop;
		        $(".thumbnail")[0].onmousedown = function(e){
		            e.preventDefault();
		            cy = e.clientY;
		            cScrollTop = $(".thumbnail").scrollTop();
		            document.addEventListener("mousemove",pptSlideDrag);
		        };
		        document.addEventListener("mouseup",function(e){
		            setTimeout(function(){
		                eventIndex = true;
		            }, 100);
		            document.removeEventListener("mousemove",pptSlideDrag);
		        });
		        function pptSlideDrag(e){
		            var y = e.clientY;
		            if (Math.abs(cy - y) > 10) {
		                eventIndex = false;
		            }
		            $(".thumbnail").scrollTop(cScrollTop + (cy - y));
		        }
		        $(".thumbnail").on('mousedown', 'img', function(event) {
		            event.preventDefault();
		        });
	        	$('.leftCont iframe')[0].onload = function(){
	        		setTimeout(function(){
		     			var aboveWords = '${courseware.pageAboveWords}';
				        var changeDate = {};
				        window.addEventListener("storage", function (e){
				            changeDate = JSON.parse(e.newValue);
				            // 当前播放页
				            var lastViewedSlide = Number(changeDate.lastViewedSlide);
				            if(changeDate.slideStates[0]){
				            	$(".rightCont").html('<p style="padding:30px">'+JSON.parse(aboveWords)[lastViewedSlide]+'</p>');
				            }else{
				            	$(".rightCont").html('<p style="padding:30px">'+JSON.parse(aboveWords)[lastViewedSlide]+'</p>');
				            }
				        });
				        // 判断ppt是否播放结束
				        $($(".leftCont iframe")[0].contentWindow.document).find("#playerView>div:eq(1)>div:first>div>div").on("click",".kern",function(){
				        	// 总长度
				            var slideLenght = $($(".leftCont iframe")[0].contentWindow.document).find("#playerView>div:eq(1)>div:first>div>div").length - 2;
				            // 当前播放页
				            var lastViewedSlide = Number(changeDate.lastViewedSlide);
				            // 当前已知最大页数(没有播放到最后一页时该页数与当前页相同)
				            var lastKey = Object.keys(changeDate.slideStates).pop();
				            // 当前页的状态
				            var lastSlideState = changeDate.slideStates[lastKey];
				        	if (lastViewedSlide == slideLenght&&lastSlideState.completed == true) {
				                console.log("播放结束！");
				            }
		                    $("body").click();
				        });
		                // ppt切换
		                $('.thumbnail').on('click',"li", function (e) {
		                	if(!eventIndex){
		                		return;
		                	}
		                    e.stopPropagation();
		                    var slideIndex = $(this).index();
		                    if (changeDate.slideStates["0"]) {
		                    	$(".leftCont iframe")[0].contentWindow.Q.pg(slideIndex);
		                    }else{
		                    	$(".leftCont iframe")[0].contentWindow.Q.pg(slideIndex-1);
		                    }
		                    $("body").click();
				            
				            $('.listCont').removeClass('active');
			    	    	$('.listBtnConti').css({
			    	    		'background':'url(../sys/teach/images/ppt/iconsPPT_05.png) no-repeat',
			    	    		'width': '0.375rem',
			    	    		'height':'0.6855rem',
			    	    		'background-size': '100%'
			    	    	});
			    	    	$('.thumbnail').fadeOut();
			    	    	$thumbnail = true;
		                });
	
		                // 右键放回上一页PPT
		                $(".leftCont iframe")[0].contentWindow.document.oncontextmenu = function(event){
		                    if (changeDate.lastViewedSlide == 0||(changeDate.lastViewedSlide == 0&&changeDate.slideStates["0"])) {
		                        return event.returnValue=false;
		                    }
		                    $(".leftCont iframe")[0].contentWindow.Q.pg(Number(changeDate.lastViewedSlide) - 1);
		                    event.returnValue=false;
		                }
		                
		     		},200);
	        		
	        		var baseUrl = '${baseUrl}';
	        		var list = '${list}';
	        		list = list.substring(1,list.length-1);
	        		var listArr = list.split(",");
	        		var thumbnailStr = "";
	        		$.each(listArr,function(i, elem){
	        			thumbnailStr += '<li><img width=100 height=85 src="'+ baseUrl + "/" + elem.trim() +'" /></li>';
	        		});
	        		$(".thumbnail").html(thumbnailStr);
	        		
	        	};
	        </script>
        </c:if>
    </div>
</body>
<script>
var $listBtn = false;
var eventIndex = true; // 防止与PPT切换事件冲突
var $thumbnail = true;
    $(function () {
        var doc = window.document;
        var docEl = doc.documentElement;
        var $html= $('html');
        var $leftCont = $('.leftCont');
        var $rightCont =  $('.rightCont');
        var pptContWidth = $leftCont.width();
        var rem = pptContWidth / 10;
        var docHeight = $html.height();
        
        
        $leftCont.css({
            'height':pptContWidth/16*9+'px'
        });
        var $leftContPadd = $('.leftCont').css('padding');
       
        $rightCont.css({
            'height':pptContWidth/16*9+2*parseInt($leftContPadd)+'px',
            'width':$html.width()-$leftCont.width()-2*parseInt($leftContPadd)+'px'
        });
        var marginTop = (docHeight-$leftCont.height())/2;
        $html.css('font-size',rem+'px');
        $leftCont.css({
            'margin-top':marginTop+'px'
        });
        $rightCont.css({
            'margin-top':marginTop+'px'
        });
    });
    $(function () {
        
        
        var $change = true;
        $(document).on('click','.listBtn', function (e) {
            e.stopPropagation();
            $('.listBtnCont').fadeIn();
            if(!$listBtn){
                $('.listBtnCont').animate({
                    left:'0'
                },500);
                $listBtn = true;
                $(this).fadeOut();
            }
            else{
                $('.listBtnCont').animate({
                    left:'-0.46875rem'
                },500);
                $listBtn = false;
            }
        });
        $(document).on('click','html', function (e) {
        	if(!eventIndex){
        		return;
        	}
            e.stopPropagation();
            $('.listBtnCont').animate({
                left:'-0.735981rem'
            },500);
            $listBtn = false;
            $('.listBtnCont').fadeOut();
            $('.listBtn').fadeIn();
            $('.thumbnail').fadeOut();
            $('.penNum').fadeOut();
        });
        $(document).on('click', '.listBtnCont i:eq(0)',function () {
            if($thumbnail){
                $(this).css({
                    'background':'url(../sys/teach/images/ppt/iconsPPT_01.png) no-repeat',
                    'width': '0.375rem',
                    'height':'0.6855rem',
                    'background-size': '100%'
            });
                $('.thumbnail').fadeIn();
                $thumbnail = false;
                return false;
            }
            else{
                $(this).css({
                    'background':'url(../sys/teach/images/ppt/iconsPPT_05.png) no-repeat',
                    'width': '0.375rem',
                    'height':'0.6855rem',
                    'background-size': '100%'
                });
                $('.thumbnail').fadeOut();
                $thumbnail = true;
                return false;
            }
        });
        $(document).on('click', '.listBtnCont i:eq(1)',function () {
            if($change){
                $(this).css({
                    'background':'url(../sys/teach/images/ppt/iconsPPT_02.png) no-repeat',
                    'width': '0.375rem',
                    'height':'0.759346rem',
                    'background-size': '100%'
                });
                $('.penNum').fadeIn();
                var $penNum_i = $('.penNum i');
                $penNum_i.eq(0).click(function () {
                    sessionStorage.setItem('penNum',4);
                    sessionStorage.setItem('canvas',true);
                    $('.canvasCont').load('<%=path%>/sys/teach/module/canvasCont.html');
                    $('.penNum').fadeOut();
                });
                $penNum_i.eq(1).click(function () {
                    sessionStorage.setItem('penNum',8);
                    sessionStorage.setItem('canvas',true);
                    $('.canvasCont').load('<%=path%>/sys/teach/module/canvasCont.html');
                    $('.penNum').fadeOut();
                });
                $penNum_i.eq(2).click(function () {
                    sessionStorage.setItem('penNum',12);
                    sessionStorage.setItem('canvas',true);
                    $('.canvasCont').load('<%=path%>/sys/teach/module/canvasCont.html');
                    $('.penNum').fadeOut();
                });
                $change = false;
            }
            else{
                $(this).css({
                    'background':'url(../sys/teach/images/ppt/iconsPPT_06.png) no-repeat',
                    'width': '0.375rem',
                    'height':'0.759346rem',
                    'background-size': '100%'
                });
                $('.penNum').fadeOut();
                $change = true;
            }
            return false;
        });
        $(document).keydown(function(event){      //键盘按下esc退出画笔
            if(sessionStorage['canvas']){
                console.log(sessionStorage['canvas']);
                if(event.keyCode==27){
                    $('.canvasCont').html('');
                    sessionStorage.removeItem('canvas');
                }
            }
        });
        
	     // 标注时鼠标右键为取消标注
	     document.oncontextmenu = function(event){
	         if (event.target.id == "_canvas") {
	             $('.canvasCont').hide().html('');
	             sessionStorage.removeItem('canvas');
	             event.returnValue=false;
	         }else{
	             event.returnValue=false;
	         }
	     }
    });
</script>
</html>