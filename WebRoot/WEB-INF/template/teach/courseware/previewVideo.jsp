<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" class="talkVideo">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <title>视频</title>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if IE]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/animate.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/video-js.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/jquery-ui.min.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/style.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/js/skin/default/layer.css">
    <script src="<%=path%>/sys/teach/js/jquery-3.2.1.min.js"></script>
    <script src="<%=path%>/sys/teach/js/video.js"></script>
    <script src="<%=path%>/sys/teach/js/jquery-ui.min.js"></script>
</head>
<style>
    .talkVideo header{
        width: 100%;
        background-color: #6455d3;
        height: 75px;
    }
    .talkVideo .headerCont{
        width: 1200px;
        height: 75px;
        margin: 0 auto;
        position: relative;
    }
    .talkVideo .headerCont .user{
        height: 100%;
        line-height: 75px;
    }
    .talkVideo .headerCont .user .userName{
        color: #fff;
        font-size: 1rem;
        padding: 10px 30px;
        position: relative;
        cursor: pointer;
    }
    .talkVideo .headerCont .user .userName:before{
        content: '';
        position: absolute;
        width: 25px;
        height: 25px;
        background: url(../sys/teach/images/talkVideo/userIcon.png) no-repeat;
        background-size: 100% 100%;
        left: 0;
        top: 7px;
    }
    .talkVideo .headerCont .user .userName:after{
        content: '';
        position: absolute;
        width: 20px;
        height: 10px;
        background: url(../sys/teach/images/talkVideo/downIcon.png) no-repeat;
        background-size: 100% 100%;
        right: 0;
        top: 16px;
        transition: 0.5s
    }
    .talkVideo .headerCont .user:hover .userName::after{
        transform: rotateZ(180deg);
    }
    .talkVideo .headerCont .logo{
        padding-top: 30px;
        width: 115px;
        height: 100%;
        line-height: 75px;
        color: #fff;
        font-size: 1rem;
        font-style: italic;
        cursor: pointer;
    }
    .talkVideo .headerCont .logo img
    {
        transform: rotate(90deg);
        width: 32px;
    }
    .talkVideo .headerCont .classTitle{
        float: left;
        width: 976px;
        height: 75px;
        line-height: 75px;
        color: #fff;
        font-size: 2rem;
        font-weight: 700;
    }
    .talkVideo content{
        display: block;
        width: 1200px;
        margin: 0 auto;
        height: 100%;
        padding-top: 25px;
        position: relative;
    }
    .talkVideo content .videoCont{
        width: 744px;
        height: 542px;
        margin:88px auto 0 auto ;
        color: #fff;
        font-weight:700;
        position: relative;
    }
    .vjs-default-skin .vjs-slider{
        background-color: initial;
    }
    .vjs-default-skin.vjs-has-started .vjs-control-bar{
        height: 70px;
    }
    .vjs-default-skin .vjs-control-bar{
        height: 70px;
        display: block;
        background-color: rgba(30, 30, 103, 0.8);
        padding-top: 12px;
        color: #fff;
        font-weight: 700;
        font-size: 1.2rem;
    }
    .video-js .vjs-tech{
        box-shadow: 0 5px 20px #5d5d5d;
    }
    .vjs-default-skin .vjs-big-play-button{
        left: 50%;
        margin-left: -63px;
        top: 35%;
        background-color: rgba(30, 30, 103, 0.7);
        border:none;
        width: 104px;
        height: 104px;
    }

    .vjs-default-skin .vjs-seek-handle:before{
        width: 15px;
        height: 14px;
        background: #00ffff;
        border-radius: 50%;
    }
    .vjs-default-skin .vjs-big-play-button:before{
        line-height: 3.6em;
    }
    .vjs-default-skin .vjs-progress-control{
        top:0;
        height: 5px;
    }
    .vjs-default-skin .vjs-play-progress{
        background: #06f5aa;
    }


    .videoCont .videoBtn{
        position: absolute;
        width: 50px;
        height: 50px;
        bottom: 15px;
        cursor: pointer;
    }
    .speed{
        background: url(../sys/teach/images/talkVideo/kuaijin.png) no-repeat;
        background-size: 100%;
        left: 35%;
    }
    .rewindDown{
        background: url(../sys/teach/images/talkVideo/kuaitui.png) no-repeat;
        background-size: 100%;
        right: 20%;
    }
    .audioVideo{
        background: url(../sys/teach/images/talkVideo/audioVideo.png) no-repeat;
        background-size: 100%;
	    right: 105px!important;
	    width: 30px!important;
	    height: 30px!important;
	    bottom: 21px!important;
    }
    .volume{
        position: absolute;
        right: 20px;
        top: 141px;
        padding: 0 12px;
        background: #66637b;
        border-radius: 0 0 30px 30px;
        border-top: none;
        z-index: 2;
    }

    /*说课视频中video的样式调整*/
    .talkVideo .volume{
        top: -125px;
        right: 42px;
        border-radius: 25px;
        padding: 0 7px;
        background: #fff338;
    }
    .talkVideo #slider-vertical{
        height: 120px!important;
        margin: 10px 0;
        border-radius: 25px;
        width: 10px;
        background: #ffcf0d;
        border: navajowhite;
        border-left: 1px solid #f2ab0d;
    }
    .talkVideo .ui-slider-vertical .ui-slider-range-min{
        height: 60%;
        background: #fe9532;
        border-radius: 25px;
    }
    .talkVideo .ui-slider-handle{
        height: 10px;
        border-radius: 5px;
        background-color: #fff;
        box-shadow: 0 0 16px #ffc889;
    }
    .talkVideo .changeVideo{
        position: absolute;
        right: 225px;
        top: 40px;
        cursor: pointer;
    }
    
    @media screen and (max-width: 1024px){
    .talkVideo .headerCont{
        width: 100%;
    }
    .talkVideo content{
        width: 100%;
    }
    .talkVideo .headerCont .classTitle{
        font-size: 24px;
    }
</style>
<body>

<header>
    <div class="headerCont">
        <div class="logo fl">
        	<a href="<%=path%>/prepareLesson/toPrepareLesson.jhtml">
        		<img src="<%=path%>/sys/teach/images/talkVideo/downIcon.png" alt="">
        	</a>
        </div>
        <div class="classTitle">蓝天教学备课后台</div>
        <div class="user fr">
            <span class="userName">
                <i></i>
                <i>${teacherName}</i>
            </span>
        </div>
    </div>
</header>
<content>
    <div class="changeVideo">
    	<a href="<%=path%>/prepareLesson/toUploadFile.jhtml?lessonId=${param.lessonId}&type=${param.type}&isUpdate=true">
        	<img src="<%=path%>/sys/teach/images/talkVideo/talkVideo01.png" alt="">
        </a>
    </div>
    <div class="videoCont">
        <video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="100%" height="100%" data-setup="{}">
            <source src="${file.fileUrl }" type='video/mp4' />
            <source src="${file.fileUrl }" type='video/wmv' />
            <source src="${file.fileUrl }" type='video/ogg' />
        </video>
    </div>
</content>
</body>
<script>
    $(function () {
//        对于video原本样式中的无用部件删除
        $('.vjs-mute-control.vjs-control').remove();

//        动态添加自定义按钮
        var a =
                '<div class="videoBtn audioVideo">'+
                '</div>'+
                '<div class="volume ds_n">'+
                '<div id="slider-vertical" style="height:200px;"></div>'+
                '</div>';
        $('.vjs-control-bar').append(a);

//        绑定按钮时间
        var $volume = false;
        $(document).on('click','.audioVideo', function (e) {
            e.stopPropagation();
            if(!$volume){
                $('.volume').css('display','block').removeClass('bounceOut').addClass('bounceIn'+' '+'animated');
                $(this).find('img').attr('src','images/talkVideo/audio_1.png');
                $volume = true;
            }
            else if($volume){
                $('.volume').removeClass('bounceIn').addClass('bounceOut'+' '+'animated');
                $(this).find('img').attr('src','images/talkVideo/audio.png');
                $volume = false;
            }
            $( "#slider-vertical" ).slider({
                orientation: "vertical",
                range: "min",
                min: 0,
                max: 100,
                value: 60,
                slide: function( event, ui ) {
                    document.getElementById("example_video_1_html5_api").volume = ui.value/100;
                }
            });
        });

//        快进快退事件绑定
//         $(document).on('click', '.speed',function (){
//             var video = document.getElementById("example_video_1_html5_api");
//             if(video.currentTime<=5){
//                 video.currentTime = 0;
//             }
//             else video.currentTime -= 5;
//         });
//         $(document).on('click', '.rewindDown',function() {
//             var video = document.getElementById("example_video_1_html5_api");
//             if(video.currentTime>=95){
//                 video.currentTime = 100;
//             }
//             else video.currentTime += 5;
//         });
    })
</script>
</html>