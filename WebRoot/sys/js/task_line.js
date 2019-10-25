/*
 * @Author: Hlin
 * @Date:   2017-07-02 17:02:18
 */
// 时间进度定时器
var currentIimeAction;
// 轮播初始化
var swiper = new Swiper('.swiper-container', {
    pagination: '.swiper-pagination',
    nextButton: '.swiper-button-next',
    prevButton: '.swiper-button-prev',
    slidesPerView: 1,
    paginationClickable: true,
    spaceBetween: 30,
    loop: true,
    pagination: '.swiper-pagination',
    paginationType: 'fraction',
    noSwiping: true,
    //切换停止音频播放，重置定时器和时间进度
    onSlideChangeStart: function(swiper) {
        clearInterval(currentIimeAction);
        $(".swiper-wrapper .audio").each(function(index, el) {
            $(this)[0].pause();
            $(this).attr("src", "");
        });
        $(".swiper-wrapper .audio_currentTime").each(function(index, el) {
            $(this).html("00:00");
        });
    }
});
// 音频播放
$(".swiper-wrapper .audio").each(function(index, el) {
    if (!$(this).length) {
        return;
    }
    var audio = $(this);
    var audioSrcList = audio.attr("src-data").split(",").reverse();
    var audioWaitList = audio.attr("wait-data").split(",").reverse();
    var audioDuration = audio.parent().find(".audio_duration");
    var audioCurrentTime = audio.parent().find(".audio_currentTime");
    var audioSrc = "";
    var audioWait = 0;
    var currentTime = 0;
    countAllAudioTime(audioSrcList, audioWaitList, audioDuration);
    // 播放按钮
    audio.siblings('.player_btn').on('click', function(event) {
        audioSrcList = audio.attr("src-data").split(",").reverse();
        audioWaitList = audio.attr("wait-data").split(",").reverse();
        audioDuration = audio.parent().find(".audio_duration");
        audioCurrentTime = audio.parent().find(".audio_currentTime");
        audioSrc = "";
        audioWait = 0;
        currentTime = 0;

        if (audio.attr("src")) {
            audio[0].pause();
            audio[0].src = "";
            clearInterval(currentIimeAction);
            audioCurrentTime.html("00:00")
            return;
        }

        audioWait = audioWaitList.pop();
        audioSrc = audioSrcList.pop()
        audio[0].src = audioSrc;

        // 点击播放第一段音频
        if (audio.attr("src")) {
            console.log(audioWait + "秒后播放音频：" + audioSrc)
            setTimeout(function() {
                    audio[0].play();
            }, Number(audioWait) * 1000);
        }

        //监控一段音频播放结束后播放另一段音频
        audio.on('ended', function(event) {
            if (!audioSrcList.length) {
                return;
            }
            currentTime += audio[0].currentTime;
            audioWait = audioWaitList.pop();
            audioSrc = audioSrcList.pop();
            audio[0].src = audioSrc;
            if (audio.attr("src")) {
                console.log(audioWait + "秒后播放音频：" + audioSrc);
                setTimeout(function() {
                        audio[0].play();
                }, Number(audioWait) * 1000);
            }
        });

        // 当前播放时间
        var timeIndex = 0;
        currentIimeAction = setInterval(function() {
            timeIndex++;
            if (timeIndex > timeToInt(audioDuration.text())) {
                return;
            }
            audioCurrentTime.html(intToTime(timeIndex))
        }, 1000);

    });
});
// 时间戳转时间格式 123 -> 00:00
function intToTime(time) {
    return (parseInt(Math.ceil(time) / 60) > 9 ? (parseInt(Math.ceil(time) / 60)) : ("0" + parseInt(Math.ceil(time) / 60))) + ":" + ((Math.ceil(time) % 60) > 9 ? Math.ceil(time) % 60 : "0" + Math.ceil(time) % 60);
}
// 时间格式转时间戳 00:00 -> 123
function timeToInt(time) {
    return parseInt(time.split(":")[0]) * 60 + parseInt(time.split(":")[1]);
}
// 计算多个音频时长，包含等待时间
function countAllAudioTime(srcList, waitlist, audioDuration) {
    // 音频个数小于2则返回
    if (srcList < 2) {
        return;
    }
    var allTiem = 0;
    $.each(srcList, function(index, el) {
        var audio = document.createElement("audio");
        audio.src = srcList[index];
        allTiem += Number(waitlist[index]);
        audio.addEventListener("canplaythrough",
            function() {
                allTiem += Math.ceil(this.duration);
                audioDuration.html(intToTime(allTiem));
            },
            false);
    });
    // return console.log(allTiem);

}
// 题干下拉
var stemHeight = 0;
$(".swiper-wrapper .swiper-slide").each(function(index, el) {
    $(this).find('.herding_text li').each(function(index, el) {
        stemHeight += Number(this.scrollHeight);
    });
    if (stemHeight < 96) {
        // $(this).find('.herding_text').parent().css("padding-bottom", "0");
        $(this).find('.herding_text').siblings('.getDown').remove()
    }
    stemHeight = 0;
});
$(".swiper-wrapper").on('click', '.getDown', function(event) {
    $(this).siblings('.herding_text').find('li').each(function(index, el) {
        stemHeight += Number(this.scrollHeight);
    });
    if (stemHeight < 95) {
        stemHeight = 0;
        return;
    }
    if ($(this).hasClass('active')) {
        $(this).removeClass('active').parents(".stem_cnt").animate({
                height: 95
            },
            200);
    } else {
        $(this).addClass('active').parents(".stem_cnt").animate({
                height: (stemHeight > 550 ? 550 : stemHeight)
            },
            200);
    }
    stemHeight = 0;
});
//填空题内容超出横线截取
$(".item .anwer").each(function(index, el) {
    $(this).attr("title",$(this).text());
    if ($(this).text().length > 4) {
       $(this).html($(this).text().substring(0,4) + "...");
    }
});
// 显示和隐藏填空题完整答案
$(".oper_list").on('click', '.show_e_anwer', function(event) {
    if ($(this).hasClass('sol_btn')) {
        $(this).html("隐藏填空题完整答案");
        $(this).removeClass('sol_btn').addClass('hol_btn');
        $(".item .anwer").each(function(index, el) {
            $(this).html($(this).attr("title"));
        });
    }else{
        $(this).html("显示填空题完整答案");
        $(this).removeClass('hol_btn').addClass('sol_btn');
        $(".item .anwer").each(function(index, el) {
            $(this).attr("title",$(this).text());
            if ($(this).text().length > 4) {
               $(this).html($(this).text().substring(0,4) + "...");
            }
        });
    }
});
//显示和隐藏解析
$(".oper_list").on('click', '.show_analy', function(event) {
    if (!$(this).hasClass('sol_btn')) {
        $(this).html("显示解析");
        $(this).removeClass('hol_btn').addClass('sol_btn');
        $(".answerCont .item").each(function(index, el) {
            $(this).removeClass('item_analy');
        });
        $(".stem_cnt").each(function(index, el) {
            $(this).removeClass('item_analy');
        });
    }else{
        $(this).html("隐藏解析");
        $(this).removeClass('sol_btn').addClass('hol_btn');
        $(".answerCont .item").each(function(index, el) {
            $(this).addClass('item_analy');
        });
        $(".stem_cnt").each(function(index, el) {
            $(this).addClass('item_analy');
        });
    }
});
//转换切换
$(".oper_list").on('click', '.ff_replace', function(event) {
    if (!$(this).hasClass('sol_btn')) {
        $(this).html("字体(粗体)");
        $(this).removeClass('hol_btn').addClass('sol_btn');
        $(".swiper-container").css("font-family","Helvetica LT");
    }else{
        $(this).html("字体(细体)");
        $(this).removeClass('sol_btn').addClass('hol_btn');
        $(".swiper-container").css("font-family","HelveticaNeueLTStd-Lt");
    }
})