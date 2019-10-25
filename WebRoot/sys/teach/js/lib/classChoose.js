$(function () {
    /**
     * Created by bruce on 2017/7/12.
     */
    var user = $('.classChooseH .user');
    $(".user").hover(function(){
        $(".userMsg").stop().fadeIn(1000);
    },function(){
        $(".userMsg").stop().fadeOut(1000);
    });

    $('.grade li').each(function (i, elem) {
        $(elem).on('click', function () {
            $(this).addClass('active').siblings().removeClass('active');
            $('.classTime ul').eq(i).addClass('dis').siblings().removeClass('dis');
        })
    });
    $('.classTime li').on('click', function () {
        window.location.href = 'functionChoose.html';
    });

    $('.functionChoose .ul_one li').on('click', function () {
        window.location.href = 'pptPro.html';
    });

    var $change = true;
    $(document).on('click','.change', function () {
        if($change){
            $('.penNum').fadeIn();
            var $penNum_i = $('.penNum i');
            $penNum_i.eq(0).click(function () {
                sessionStorage.setItem('penNum',4);
                sessionStorage.setItem('canvas',true);
                $('.canvasCont').load('../sys/teach/module/canvasCont.html');
            });
            $penNum_i.eq(1).click(function () {
                sessionStorage.setItem('penNum',8);
                sessionStorage.setItem('canvas',true);
                $('.canvasCont').load('../sys/teach/module/canvasCont.html');
            });
            $penNum_i.eq(2).click(function () {
                sessionStorage.setItem('penNum',12);
                sessionStorage.setItem('canvas',true);
                $('.canvasCont').load('../sys/teach/module/canvasCont.html');
            });
            $change = false;
        }
        else{
            $('.penNum').fadeOut();
            $change = true;
        }

    });
    $(document).on('click','.pptPro .star', function () {
        var audio = document.getElementById("audio");
        if(audio.paused){
            audio.play();
            $(this).find('img').attr('src','../sys/teach/images/stop_1.png');
            return;
        }
        audio.pause();
        $(this).find('img').attr('src','../sys/teach/images/star.png');
    });

    var $volume = false;
    $(document).on('click','.radio', function (e) {
        e.stopPropagation();
        if(!$volume){
            $('.volume').css('display','block').removeClass('bounceOut').addClass('bounceIn'+' '+'animated');
            $(this).find('img').attr('src','../sys/teach/images/audio_1.png');
            $volume = true;
        }
        else if($volume){
            $('.volume').removeClass('bounceIn').addClass('bounceOut'+' '+'animated');
            $(this).find('img').attr('src','../sys/teach/images/audio.png');
            $volume = false;
        }
        $( "#slider-vertical" ).slider({
            orientation: "vertical",
            range: "min",
            min: 0,
            max: 100,
            value: 60,
            slide: function( event, ui ) {
                document.getElementById("audio").volume = ui.value/100;
            }
        });
    });
    //$(document).on('click', function () {
    //    volume.removeClass('bounceIn').addClass('bounceOut'+' '+'animated');
    //    $volume = false;
    //});
    $('.pptPro .left_1 li').each(function () {
        $(this).on('mouseover', function () {
            $(this).addClass('bounceIn'+' '+'animated').removeClass('bounceIn'+' '+'animated',1000);
        })
    });
    var toBig_1 = false;
    $(document).on('click','.toBig_1 img', function (e) {
        e.stopPropagation();
        if(!toBig_1){
            $('.listCont').addClass('active').removeClass('animated');
            $(this).attr('src','../sys/teach/images/list_1.png');
            toBig_1 = true;
            
        }
        else{
            $('.listCont').removeClass('active').removeClass('animated');
            $(this).attr('src','../sys/teach/images/list.png');
            toBig_1 = false;
        }
    });


    //监听键盘Delete键,对当前选中的内容框进行删除
    $(document).keydown(function(event){
        if(sessionStorage['canvas']){
            if(event.keyCode==27){
                $('.canvasCont').html('');
                sessionStorage.clear('canvas');
            }
        }
    });
    $()
});