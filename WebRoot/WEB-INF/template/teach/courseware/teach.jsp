<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" class="attendClass">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <script src="<%=path%>/sys/teach/js/jquery-3.2.1.min.js"></script>
    <script src="<%=path%>/sys/teach/js/flexible.js"></script>
    <script src="<%=path%>/sys/teach/js/common.js"></script>
    <script src="<%=path%>/sys/teach/js/layer.js"></script>
    <script src="<%=path%>/sys/teach/js/video.js"></script>
    <script src="<%=path%>/sys/teach/js/util/teach.js"></script>
    <script src="<%=path%>/sys/teach/js/lib/iconfont.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/jquery-ui.min.css">
<%--     <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/music.css"> --%>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/video-js.css">
<%--     <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/reset.pchlin.css"> --%>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/animate.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/start.css">
<%--     <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/pptPreview.css"> --%>
    
<%--     <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/jquery-ui.min.css"> --%>
<%--     <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/video-js.css"> --%>
<%--     <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/animate.css"> --%>
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/attendClass.css">
<%--     <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/kaoqin.css" /> --%>
    <style type="text/css">
    	.attendClass .editor_box {
		    height: 100%;
		    margin: auto;
		    position: relative;
		    top: 0;
		    left: 0;
		    background: none;
		    border: none;
		}
		
		.attendClass .module {
		    position: absolute;
		}
		
		.dis_block{
		    display: block!important;
		}
		
		.icon {
		    width: 100%;
		    height: 100%;
		    fill: currentColor;
		    overflow: hidden;
		}
		
		.video-js .vjs-tech {
		    box-shadow: 0 5px 20px #5d5d5d;
		}
/* 		.vjs-default-skin .vjs-control-bar{ */
/* 			height:80px; */
/* 		} */
    </style>
</head>
<body style="height: 100%;position: relative;">
<input type="hidden" id="id" value="${param.id}">
<div class="pptViewDiv" style="width: 100%;height: 100%;position: absolute;-index: 1;top: 0;left: 0"></div>
<div style="position: absolute;z-index: 10001;" class="canvasCont"></div>
    <div style="z-index: 10000;" class="attendClassCont">
        <div class="listCont">
            <div class="pepLogo">
                <ul class="numOne">
                    <i></i>
                    <li><span></span></li>
                </ul>
                <ul class="numTow">
                    <li><span></span></li>
                    <li><span></span></li>
                </ul>
            </div>
            <ul class="listContLeft">
                <li class="classTime">
                    <span class="thumbnail">
                        <i><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></i>
                        <i><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></i>
                        <i><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></i>
                        <i><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></i>
                        <i><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""></i>
                    </span>
                </li>
                <li class="addNum"></li>
                <li></li>
                <li class="pen">
                    <span class="penNum">
                        <i></i>
                        <i></i>
                        <i></i>
                    </span>
                </li>
            </ul>
        </div>
    </div>
<div class="addBranch">
    <div class="student_list">
        <ul>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon">
                    <img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg">
                </span>

            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon1"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
            </li>
            <li>
                <span class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </span>
                <span class="user_icon1"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon1"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
        </ul>
        <ul>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon1"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
        </ul>
        <ul>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
        </ul>
        <ul>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
            <li><div class="zhedan">
                <span class="guangHuan"></span>
                <span class="guangHuanTow"></span>
            </div>

                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
            <li>
                <div class="zhedan">
                    <span class="guangHuan"></span>
                    <span class="guangHuanTow"></span>
                </div>
                <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
            </li>
        </ul>
    </div>
</div>
<div class="kaoqin_box">
    <!-- begin 考勤弹窗 -->
    <div class="kaoqin">
        <!-- 每个list表示一页，一页有两排学生，一排5个 -->
        <div class="student_list">
            <ul>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="1" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon2.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
                <li sex="2" state="2">
                    <span class="user_icon"><img src="<%=path%>/sys/teach/images/kaoqin/user_icon.jpg"></span>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
<script>
	$(function () {
	    var $html= $('html');
	    var $attendClass = $('.attendClass');
	    $('body').height($attendClass.width()*9/16+'px');
		var contH = $html.height()-$('body').height();
		$('body').css('marginTop',contH/2+'px');
		if($('body').height()>$html.height()){
			$('body').css('width',$html.height()*16/9+'px');
			console.log($('body').width(),$html.height()*16/9+'px');
			var contW = $html.width()-$('body').width();
			$('body').css({
				marginLeft:contW/2+'px',
				marginTop:0
			});
		}
	});
        $(function () {
            var listBtn1 = true;
            var listBtn2 = true;
            var listBtn3 = true;
            var listBtn4 = true;
            var pepPic = true;
            //左侧菜单点击事件
            $(document).on('click','.listContLeft li', function () {
                if($(this).index()==0){
                    if(listBtn1){
                        $(this).css({
                            background:'url(../sys/teach/images/ppt/icons_01.png) no-repeat',
                            backgroundSize:'100% 100%'
                        });
                        $('.thumbnail').fadeIn();
                        listBtn1 = false;
                    }
                    else{
                        $(this).css({
                            background:'url(../sys/teach/images/ppt/icons_05.png) no-repeat',
                            backgroundSize:'100% 100%'
                        });
                        $('.thumbnail').fadeOut();
                        listBtn1 = true;
                    }
                }
                if($(this).index()==1){
                    if(listBtn2){
                        $(this).css({
                            background:'url(../sys/teach/images/ppt/icons_02.png) no-repeat',
                            backgroundSize:'100% 100%'
                        });
                        $('.addBranch').css('display','block').removeClass('zoomOut').addClass('zoomIn'+' '+'animated');
                        listBtn2 = false;
                    }
                    else{
                        $(this).css({
                            background:'url(../sys/teach/images/ppt/icons_06.png) no-repeat',
                            backgroundSize:'100% 100%'
                        });
                        listBtn2 = true;
                    }
                }
                if($(this).index()==2){
                    if(listBtn3){
                        $(this).css({
                            background:'url(../sys/teach/images/ppt/icons_03.png) no-repeat',
                            backgroundSize:'100% 100%'
                        });
                        $('.kaoqin_box').css('display','block').removeClass('zoomOut').addClass('zoomIn'+' '+'animated');
                        listBtn3 = false;
                    }
                    else{
                        $(this).css({
                            background:'url(../sys/teach/images/ppt/icons_07.png) no-repeat',
                            backgroundSize:'100% 100%'
                        });
                        listBtn3 = true;
                    }
                }
                if($(this).index()==3){
                    if(listBtn4){
                        $(this).css({
                            background:'url(../sys/teach/images/ppt/icons_04.png) no-repeat',
                            backgroundSize:'100% 100%'
                        });
                        $('.penNum').fadeIn();
                        listBtn4 = false;
                    }
                    else{
                        $(this).css({
                            background:'url(../sys/teach/images/ppt/icons_08.png) no-repeat',
                            backgroundSize:'100% 100%'
                        });
                        $('.penNum').fadeOut();
                        listBtn4 = true;
                    }
                }
            });
            //头像点击事件



            //更改画笔的大小
            var $penNum_i = $('.penNum i');
            $penNum_i.eq(0).click(function (e) {
                e.stopPropagation();
                sessionStorage.setItem('penNum',4);
                sessionStorage.setItem('canvas',true);
                $('.canvasCont').load('canvasCont1.html');
                $('.penNum').fadeOut();
                $(this).parents('.pen').css({
                    background:'url(../sys/teach/images/ppt/icons_08.png) no-repeat',
                    backgroundSize:'100% 100%'
                });
                listBtn4 = true;
            });
            $penNum_i.eq(1).click(function (e) {
                e.stopPropagation();
                sessionStorage.setItem('penNum',8);
                sessionStorage.setItem('canvas',true);
                $('.canvasCont').load('canvasCont1.html');
                $('.penNum').fadeOut();
                $(this).parents('.pen').css({
                    background:'url(../sys/teach/images/ppt/icons_08.png) no-repeat',
                    backgroundSize:'100% 100%'
                });
                listBtn4 = true;
            });
            $penNum_i.eq(2).click(function (e) {
                e.stopPropagation();
                sessionStorage.setItem('penNum',12);
                sessionStorage.setItem('canvas',true);
                $('.canvasCont').load('../sys/teach/module/canvasCont1.html');
                $('.penNum').fadeOut();
                $(this).parents('.pen').css({
                    background:'url(../sys/teach/images/ppt/icons_08.png) no-repeat',
                    backgroundSize:'100% 100%'
                });
                listBtn4 = true;
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

            $.each($('.thumbnail i'), function (i,elem) {
                $(this).on('click', function (e) {
                    e.stopPropagation();
                    $(this).parent('.thumbnail').fadeOut();
                    $(this).parents('.classTime').css({
                        background:'url(../sys/teach/images/ppt/icons_05.png) no-repeat',
                        backgroundSize:'100% 100%'
                    });
                    listBtn1 = true;
                })
            });

            $(document).on('click','.numOne', function () {
                if(pepPic){
                    $('.numTow').css('display','block').removeClass('zoomOut').addClass('zoomIn'+' '+'animated');
                    $('.listContLeft').css('display','block').removeClass('zoomOut').addClass('zoomIn'+' '+'animated');
                    pepPic = false;
                }
                else{
                    $('.numTow').removeClass('zoomIn').addClass('zoomOut');
                    $('.listContLeft').removeClass('zoomIn').addClass('zoomOut');
                    pepPic = true;
                }
            });
//            $.each($('.user_icon'), function (i, elem) {
//                $(this).on('click',function () {
//                    $(this).siblings('.zhedan').animate({
//                        width:'1.984375rem',
//                        height:'1.984375rem',
//                        left:'0',
//                        top:'0'
//                    },500);
//                    $(this).siblings('.zhedan').find('span').animate({
//                        left:'-4px',
//                        top:'-2px'
//                    },500)
//                })
//            })
            $.each($('.user_icon'), function (i, elem) {
                $(this).on('click',function () {
                    $(this).siblings('.zhedan').css('animation','user_icon 0.3s forwards');
                    $(this).siblings('.zhedan').find('span').css({
                        'animation':'guangHuanTow 5s forwards 10'
                    });
                })
            })
            $.each($('.user_icon1'), function (i, elem) {
                $(this).on('click',function () {
                    $(this).siblings('.zhedan').css('animation','user_icon 0.3s forwards');
                    $(this).siblings('.zhedan').find('span').css({
                        'animation':'guangHuanTow 5s forwards 10'
                    });
                })
            })
        });


        // 考勤状态切换
        $(".kaoqin .student_list").on('click', 'li', function(event) {
            if ($(this).attr("state") == "1") {
                $(this).attr("state","2");
            }else{
                $(this).attr("state","1");
            }
        });
        // 点击最后一个学生触发下一页事件(出最后一排的最后一个学生)
        $(".kaoqin .student_list:not(:last)").on('click', 'li:last', function(event) {
            var wthis = $(this);
            setTimeout(function(){
                wthis.parents('.student_list').hide().next(".student_list").removeClass('hide').addClass('show')
            }, 1000);
        });
        // 点击最后一排的最后一个学生触发事件
        $(".kaoqin .student_list:last").on('click', 'li:last', function(event) {
            console.log("点击最后一排的最后一个学生触发事件")
        });
        
//        //进入全屏
//        // 判断各种浏览器，找到正确的方法
//        function requestFullscreen( elem ) {
//            if (elem.requestFullscreen) {
//                elem.requestFullscreen();
//            }
//            else if (elem.webkitRequestFullScreen) {
//                // 对 Chrome 特殊处理，
//                // 参数 Element.ALLOW_KEYBOARD_INPUT 使全屏状态中可以键盘输入。
//                if ( window.navigator.userAgent.toUpperCase().indexOf( 'CHROME' ) >= 0 ) {
//                    elem.webkitRequestFullScreen( Element.ALLOW_KEYBOARD_INPUT );
//                }
//                // Safari 浏览器中，如果方法内有参数，则 Fullscreen 功能不可用。
//                else {
//                    elem.webkitRequestFullScreen();
//                }
//            }
//            else if (elem.mozRequestFullScreen) {
//                elem.mozRequestFullScreen();
//            }
//        }
////        $(document).on('click','.attendClassCont', function () {
////            requestFullscreen(document.documentElement);
////        });
//// 启动全屏!
////退出全屏
//        $(document).keydown(function (event) {
//            if(event.keyCode==27){
//                exitFullscreen();
//            }
//        });
//        function exitFullscreen() {
//            var de = document;
//            if (de.exitFullscreen) {
//                de.exitFullscreen();
//            } else if (de.mozCancelFullScreen) {
//                de.mozCancelFullScreen();
//            } else if (de.webkitCancelFullScreen) {
//                de.webkitCancelFullScreen();
//            }
//        }
</script>
</html>