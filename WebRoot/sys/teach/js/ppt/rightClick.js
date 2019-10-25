/**
 * Created by Administrator on 2017/8/25.
 */
$(function () {
    var $page_box = $('.page_box');
    $(document).on('mousedown','.left.wrap',function(e){//自定义鼠标右键菜单
        var key = e.which; //获取鼠标键位
        var $this = e.target;
        var $html;
        var $rightClick_left = $(".rightClick_left");
        if(key == 3)  //(1:代表左键； 2:代表中键； 3:代表右键)
        {
            switch ($($this).attr('class')){
                case 'thumbImg':
                    $html = '<a class="hidPpt" style="display:block;line-height: 30px;width: 100%;height: 30px;text-align: center">隐藏幻灯片</a>'+
                            '<a class="showPpt" style="display:block;line-height: 30px;width: 100%;height: 30px;text-align: center">显示幻灯片</a>'+
                            '<a class="createPpt" style="display:block;line-height: 30px;width: 100%;height: 30px;text-align: center">新建幻灯片</a>'+
                            '<a class="deletePpt" style="display:block;line-height: 30px;width: 100%;height: 30px;text-align: center">删除幻灯片</a>'+
                            '<a class="setPptStyle" style="display:block;line-height: 30px;width: 100%;height: 30px;text-align: center">设置背景格式</a>';
                    break;
                case 'left wrap':
                    $html = '<a class="createPpt" style="display:block;line-height: 30px;width: 100%;height: 30px;text-align: center">新建幻灯片</a>';
                    break;
                case 'text':
            }
            $rightClick_left.html($html);
            //获取右键点击坐标
            var x = e.clientX;
            var y = e.clientY;

            $rightClick_left.show().css({left:x,top:(y-100),zIndex:999999999});
            $.each($('.page_box'), function (i, elem) {
                $(this).mousedown(function (e) {
                    var key = e.which;
                    if(key==3){
                        $(this).find('div').addClass('border_org');
                        $(this).siblings().find('div').removeClass('border_org');
                    }
                })
            });
        }
    });
    $(document).on('click','html',function(){
        $(".rightClick_left").hide();
    });
    $('.right').on('click','.editor_box:visible',function(){
        $(".rightClick_left").hide();
    });
    $('.rightClick_left').on('click','a', function (e) {                       //缩略图区域鼠标右键菜单内容事件
        switch ($(this).attr('class')){
            case 'hidPpt':                                                     //隐藏幻灯片
                $.each($page_box, function (i) {
                    if($(this).find('div').hasClass('border_org')){
                        $(this).addClass('pptHidLeftList');
                        $(this).find('i').css('display','block');
                        $('.editor_box').eq(i).attr('pptHide',true);
                    }
                });
                break;
            case 'showPpt':                                                    //显示幻灯片
                $.each($page_box, function (i, elem) {
                    if($(this).find('div').hasClass('border_org')){
                        $(this).removeClass('pptHidLeftList');
                        $(this).find('i').css('display','none');
                    }
                });
                break;
            case 'createPpt':                                                  //新建幻灯片
                var $listImg = '';
                var pageBox_length = $(".left .page_box").length+1;
                var text_1 = '';

                if(sessionStorage['listImg']){
                    $listImg = sessionStorage.getItem('listImg');
                    text_1 = '<div class="editor_box hide" name='+pageBox_length+' style="display: none;background: url('+$listImg+') center center / 100% 100% no-repeat;"></div>';

                }
                else{
                    $listImg = ' ';
                    text_1 = '<div class="editor_box hide" name='+pageBox_length+' style="display: none;"></div>';
                }

                var text = '<li class="page_box">' +
                    '<i></i>' +
                    '<span class="page_index">'+pageBox_length+'</span>' +
                    '<span class="closePPT">×</span>' +
                    '<div class="page" name='+pageBox_length+'><img src="'+$listImg+'" alt="" /></div> ' +
                    '</li>';
                // text:左侧缩略图    text_1:中间内容区块
                var $textCont = $('.wrap ul');
                var $txtCont_1 = $('.right');
                $txtCont_1.append(text_1);
                $textCont.append(text);
                $('.page[name="'+pageBox_length+'"]').addClass('border_org').parent('.page_box').siblings('.page_box').find('.page').removeClass('border_org');
                $(".editor_box[name='"+pageBox_length+"']").show().attr('id','domImg').siblings('.editor_box').hide().attr('id','');
                var type = '文本框';
                var $thisName = 'box'+newText+'';
                var $thisName1 = 'box110'+newText+'';
                var newText1 = "<div style='left: 6%;top: 7%;width: 85%;height: 24%;' class='module' className='box"+newText+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                var newText2 = "<div style='left: 6%;top: 47%;width: 85%;height: 24%;' class='module' className='box110"+newText+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(newText1,type,$thisName);
                E.insert(newText2,type,$thisName1);
                //让新建的幻灯片绑定删除函数
                $('.deletePpt').on('click', function () {
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
                //左侧选中更改边框颜色
                $(".left .page_box").each(function(i, elem) {
                    $(this).on('click', function () {
                        $(this).find('.page').addClass('border_org');
                        $(this).siblings().find('.page').removeClass('border_org');
                    });
                });
                //删除幻灯片：同时删除左侧和中间的幻灯片
                $('.closePPT').on('click', function () {
                    var $thisIndex = $(this).siblings('.page').attr('name');
                    $(this).parent('.page_box').remove();

                    $(".left .page_box").each(function(index, el) {
                        $(this).find(".page_index").html(index+1);
                    });

                    $.each($('.editor_box'), function (i,elem) {
                        if($(elem).attr('name')==$thisIndex){
                            $(elem).children('.module').each(function (j,item) {
                                $('.'+$(item).attr('className')+'').remove();
                                $(".left .page_box").eq(i-1).find('.page').click();
                            });
                            $(elem).remove();
                        }
                    })
                });
                break;
            case 'deletePpt':                                                  //删除幻灯片
                $('.page_box').each(function () {
                    if($(this).find('div').hasClass('border_org')){
                        var $thisIndex = $(this).find('.page').attr('name');
                        $(this).remove();
                        $(".left .page_box").each(function(index, el) {
                            $(this).find(".page_index").html(index+1);
                        });
                        $.each($('.editor_box'), function (i,elem) {
                            if($(elem).attr('name')==$thisIndex){
                                $(elem).children('.module').each(function (j,item) {
                                    $('.'+$(item).attr('className')+'').remove();
                                    $(".left .page_box").eq(i-1).find('.page').click();
                                });
                                $(elem).remove();
                            }
                        })
                    }
                });
                break;
            case 'setPptStyle':
                var $rightCont = $('.right');
                $('.chooseViewCont').css('z-index','0');
                $('.animateCont').css('z-index','0');
                $('.setPptStyleCont').addClass('setPptStyleRight');
                $('.right').addClass('active');
                $('.editor_box').addClass('active');
                setInterval(function () {
                    var contHeight = $('.ppt').height()-$('.header').height();
                    $rightCont.height($rightCont.width()*9/16+'px');
                    $rightCont.css('margin-top',(contHeight-$rightCont.height())/2-15+'px');
                },10);
                break;
        }

    });

    //点击背景格式关闭按钮
    $('.pptStyleCont_title').on('click','i', function () {
        $('.setPptStyleCont').removeClass('setPptStyleRight');
        $('.right').removeClass('active');
        $('.editor_box').removeClass('active');
    });

    //背景格式界面的颜色选择事件
    var fontcolorId = $("#pptBgc");
    var oldFontColor;
    fontcolorId.colorpicker({
        hideButton: true,
        showOn: 'button',
        strings: "主题颜色,标准颜色,其他颜色,主题颜色,返回调色板,历史记录,还没有历史记录"
    });
    $('.pptStyleCont_list').on('click','.select', function () {
        $('.pptStyleCont_list .ui-widget.evo-pop.ui-widget.ui-widget-content.ui-corner-all').css({
            left:'-190px'
        });
    });
    fontcolorId.on("mouseenter.color", function(event, color){
        $('.editor_box:visible').css('background',color);
    });
    fontcolorId.on("change.color", function(event, color){
        layer.confirm('<div>选一个吧~</div>',{
            title:'<div style="text-align: center">请选择</div>',
            btn:['全部应用','当前页面','取消']
        }, function (e) {
            $('.editor_box').css('background',color);
            layer.close(e);
        }, function (e) {
            $('.editor_box:visible').css('background',color);
            layer.close(e);
        })
    });
    fontcolorId.on("change.color", function(event, color){
        $(".pptStyleCont_list a[name='pptBgc'] .mark").css('background-color',color);
    });

    //背景格式界面的透明度调整
    var $choosePptOpc = $('.choosePptOpc');
    $choosePptOpc.on('click','.pptOpacity_up', function () {
        var $value = parseInt($('input[name="pptOpacity"]').val());
        if($value<100){
            $('input[name="pptOpacity"]').val($value+1+'%');
        }
        else{
            layer.tips('已到达最大值', '.pptOpacity_up', {
                tips: [1, '#3595CC'],
                time: 2000
            });
        }

    });
    $choosePptOpc.on('click','.pptOpacity_down', function () {
        var $value = parseInt($('input[name="pptOpacity"]').val());
        if($value>0){
            $('input[name="pptOpacity"]').val($value-1+'%');
        }
        else{
            layer.tips('已到达最小值', '.pptOpacity_down', {
                tips: [1, '#3595CC'],
                time: 2000
            });
        }
    });

    //设置背景格式尚未完成功能
    $('.pptStyleCont_list').on('click','label', function () {
        var $index = $(this).index();
       if($index==1){
           layer.tips('开发ing...', '.cantClick_1', {
               tips: [1, '#3595CC'],
               time: 2000
           });
       }
        if($index==2){
           layer.tips('开发ing...', '.cantClick_2', {
               tips: [1, '#3595CC'],
               time: 2000
           });
       }
        if($index==3){
            layer.tips('开发ing...', '.cantClick_3', {
               tips: [1, '#3595CC'],
               time: 2000
           });
       }
    });

    });



















