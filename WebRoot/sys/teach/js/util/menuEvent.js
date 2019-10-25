var $superLinkIndex;
$(function () {
    (function($){
        var right = $(".right");
        var selectionNode;
        var selectionParent;
        var selectionOffsetParent;
        //单击操作全局事件
        $(".menu_cnt").on('click', '.tabs .tabs_cnt a', function(event) {
            switch($(this).attr("name"))
            {
                case "grid": //网格线
                    if ($(".right").find('.grid_canvas').length) {
                        $(".right").find('.grid_canvas').remove();
                        return;
                    }
                    var canvas = document.createElement('canvas');
                    canvas.className = "grid_canvas"
                	$('.editor_box:visible').append(canvas);
                    var ctx=canvas.getContext('2d');
                    canvas.width = $(".right").width();
                    canvas.height = $(".right").height();
                    ctx.translate(0.5, 0.5);
                    ctx.setLineDash([0.5,7]);
                    ctx.strokeStyle="#333";
                    ctx.beginPath();
                    var i = 0;
                    var s = setInterval(function(){
                        if (i>parseInt(canvas.width) / 60 + 1) {
                            clearInterval(s)
                          return;
                        }
                        ctx.moveTo(i*60 + 23,0);
                        ctx.lineTo(i*60 + 23,canvas.height);
                        ctx.moveTo(0,i*60 + 23);
                        ctx.lineTo(canvas.width,i*60 +23);
                        ctx.stroke();
                        i++;
                    }, 100);
                    break;
            }
        });
        // 鼠标点击、进入、离开事件判断
        var oldBGC;
        var oldBS;
        var oldBD;
        var oldColor;
        $(".menu_cnt").on('click mouseenter mouseleave', '.tabs .box_style .tabs_cnt a', function(event) {
            if (event.type == "mouseenter") {
                $(this).parent().parent().addClass('active');
            }
            // 如果没有选中盒子则返回
            var classNameList = new Array();
            if (!sessionStorage.getItem("className")&&!sessionStorage.getItem("classNameList")) {
                return;
            }
            // 判断是多选还是单选
            if (sessionStorage.getItem("classNameList")) {
                classNameList = JSON.parse(sessionStorage.getItem("classNameList"));
            }else{
                classNameList.push(sessionStorage.getItem("className"));
            }
            switch($(this).attr("name"))
            {
                case "boxstyle"://选择盒子样式
                    var _this = this
                    $.each(classNameList,function(index, el) {
                        var $box = $(".right").find('.editor_box:visible .module[classname="'+classNameList[index]+'"]');
                        if (event.type == "click") {
                            oldBGC = $(_this).css("backgroundColor");
                            oldBS = $(_this).css("boxShadow");
                            oldBD = $(_this).css("border");
                            oldColor = $(_this).css("color");
                            $box.css({
                                backgroundColor: $(_this).css("backgroundColor"),
                                boxShadow: $(_this).css("boxShadow"),
                                border: $(_this).css("border"),
                                color: $(_this).css("color")
                            });
                            $(_this).parent().parent().removeClass('active');
                        }
                        if (event.type == "mouseenter") {
                            oldBGC = $box.css("backgroundColor");
                            oldBS = $box.css("boxShadow");
                            oldBD = $box.css("border");
                            oldColor = $box.css("color");
                            $box.css({
                                backgroundColor: $(_this).css("backgroundColor"),
                                boxShadow: $(_this).css("boxShadow"),
                                border: $(_this).css("border"),
                                color: $(_this).css("color")
                            });
                        }
                        if (event.type == "mouseleave") {
                            $box.css({
                                backgroundColor: oldBGC,
                                boxShadow: oldBS,
                                border: oldBD,
                                color: oldColor
                            });
                        }
                    });
                    break;
            }
            //清空数组
            classNameList = [];
        });
        // 配合上面的操作，鼠标进入或离开菜单时去掉或加上的盒子的focus状态
        $(".menu_cnt").on('click mouseenter mouseleave', '.tabs .box_style .tabs_cnt', function(event) {
            // 如果没有选中盒子则返回
            if (!sessionStorage.getItem("className")&&!sessionStorage.getItem("classNameList")&&event.type == "mouseenter") {
                return;
            }
            var classNameList = new Array();
            // 判断是多选还是单选
            if (sessionStorage.getItem("classNameList")) {
                classNameList = JSON.parse(sessionStorage.getItem("classNameList"));
            }else{
                classNameList.push(sessionStorage.getItem("className"));
            }
            $.each(classNameList,function(index, el) {
                var $box = $(".right").find('.editor_box:visible .module[classname="'+classNameList[index]+'"]');
                if (event.type == "mouseenter") {
                    $box.removeClass('focus');
                }
                if (event.type == "mouseleave") {
                    $box.addClass('focus');
                }
            });
        });
        // 单击处理事件
        var pageIndex;
        $(".menu_cnt").on('click', '.tabs .tabs_cnt a', function(event) {
            selectionNode = window.getSelection().anchorNode;
            //如果焦点不是在编辑框里面则返回
            if (!$(selectionNode).parents(".editor_box").length) {
                return;
            }
            selectionParent = selectionNode.parentElement;
            selectionOffsetParent = selectionNode.parentElement.offsetParent;
            switch($(this).attr("name"))
            {
                case "fontssize": //增大字号
                    var fontSize = $(selectionParent).css("font-size");
                    if (selectionParent.nodeName.toLowerCase() == 'font') {
                        $(selectionParent).css("font-size",Number(fontSize.substring(0,fontSize.length-2))+1)
                    }else{
                        document.execCommand('FontSize',"false",3);
                    }
                    break;
                case "fontisize": //减小字号
                    var fontSize = $(selectionParent).css("font-size");
                    if (selectionParent.nodeName.toLowerCase() == 'font') {
                        $(selectionParent).css("font-size",Number(fontSize.substring(0,fontSize.length-2))-1)
                    }else{
                        document.execCommand('FontSize',"false",0);
                    }
                    break;
                case "underline": //下滑线
                    document.execCommand('underline',false,null);
                    var $underline = $(selectionParent).attr('underline');
                    if($underline){
                        $(selectionParent).removeAttr('underline');
                        return false;
                    }
                    $(selectionParent).attr('underline','underline');
                    break;
                case "bold": //加粗
                    document.execCommand("Bold","false",null);
                    break;
                case "italic": //斜体
                    document.execCommand('Italic',"false",null);
                    break;
                case "strikethrough": //删除线
                    document.execCommand('StrikeThrough',false,null);
                    break;
                case "removeformat": //清除字体格式
                    document.execCommand('RemoveFormat',false);
                    break;
                case "fontcolor": //字体颜色
                    document.execCommand('ForeColor',"false",$(this).find('.mark').css("background-color"));
                    break;
                case "alignleft": //左对齐
                    selectionOffsetParent.style.textAlignLast = "left";
                    selectionOffsetParent.style.textAlign = "left";
                    break;
                case "aligncenter": //居中
                    selectionOffsetParent.style.textAlignLast = "center";
                    selectionOffsetParent.style.textAlign = "center";
                    break;
                case "alignright": //右对齐
                    selectionOffsetParent.style.textAlignLast = "right";
                    selectionOffsetParent.style.textAlign = "right";
                    break;
                case "alignjustify": //两端对齐
                    selectionOffsetParent.style.textAlign= "justify";
                    selectionOffsetParent.style.textAlignLast= "justify";
                    break;
                case 'superLink':
                    var a = '',b='';
                    var range = window.getSelection().getRangeAt(0);
                    var d = window.getSelection().toString();
                    var f = document.createElement('a');

                    $.each($('.page_box'), function (i,elem) {
                        var c = $(elem).find("img").attr('src');
                        a += '<li style="text-align:center;border: 1px solid #fff;cursor:pointer;width: 96px;">幻灯片 <i style="font-style: normal">'+(i+1)+'</i></li>';
                        b += '<li class="eyeHid" style="margin-top: 15px;background: #fff;height: 120px;"><img style="width: 200px;height: 120px;" src="'+c+'" alt=""></li>';
                    });
                    var $html = '<div class="superLinkView">' +
                        '<p><i style="margin-left: 25px;font-style: normal">选择<i><img style="width: 20px;transform: rotate(181deg);margin-top: 4px;" src="../sys/teach/images/ppt/hand.png" alt=""></i></i><i style="font-style: normal;margin-left: 72px">幻灯片预览</i></p>' +
                        '<ul class="list_l" style="float:left;">'+a+'</ul>' +
                        '<ul class="list_r" style="background:#efefef;padding: 10px;width: 190px;height: 150px;margin-left: 90px;overflow: hidden">'+b+'</ul>' +
                        '</div>';
                    layer.confirm($html,{
                        title:'插入超链接',
                        btn:['确定','取消']
                    }, function (e) {
                        //$.each($('.list_l li'), function (i, elem) {
                        //   if()
                        //});
                        //document.execCommand('CreateLink',true,'true');
                        f.innerHTML = d;
                        $(f).addClass('superLink_a');
                        $(f).attr('index',$superLinkIndex+1);
                        range.deleteContents();
                        range.insertNode(f);
                        $.each($('.list_l').children('li'),function(){
                        	if($(this).hasClass('hover')){
                        		$('.page_box').eq($(this).index()).addClass('pptHidLeftList').find('i').css('display','block');
                        		$('.editor_box').eq($(this).index()).addClass('pptLink');
                        	}
                        })
                        layer.close(e);
                    }, function (e) {
                        layer.close(e);
                    });

                    $('.list_l li').on('click', function (e) {
                        e.stopPropagation();
                        if($(this).index()==$('.editor_box:visible').index()){
                        	layer.tips('不可选择本身页面',$(this));
                        }
                       $superLinkIndex = $(this).index();
                        $(this).addClass('hover').siblings().removeClass('hover');
                        $('.list_r li').eq($superLinkIndex).removeClass('eyeHid').siblings().addClass('eyeHid');
                    });
                    break;
                case "unorderedlist": //无序列表
                    document.execCommand('InsertUnorderedList');
                    break;
                case "orderedlist": //有序列表
                    document.execCommand('InsertOrderedList');
                    break;
            }
        });
        //下拉列表选择事件
        var $choose = false;
        $(".menu_cnt").on('change', '.tabs .tabs_cnt select', function(event) {

            selectionNode = window.getSelection().anchorNode;
            //如果焦点不是在编辑框里面则返回
            if (!$(selectionNode).parents(".editor_box").length) {
                return;
            }
            selectionParent = selectionNode.parentElement;
            selectionOffsetParent = selectionNode.parentElement.offsetParent;
            switch($(this).attr("name"))
            {
                case "fontstyle": //字体
                    document.execCommand('FontName',false,$(this).val());
                    break;
                case "fontsize": //字号
                	document.execCommand('FontSize',false,$(this).val());
                    var seleFont = $(selectionNode.parentElement).css('font-size');
                    var fontSize = Number(seleFont.substring(0,seleFont.length-2))/Number($('html').css('font-size').substring(0,$('html').css('font-size').length-2)).toFixed(6)+'rem';
                    $(selectionNode.parentElement).css('font-size',fontSize);
                    break;
                case "lineheight": //行距
                    selectionOffsetParent.style.lineHeight = $(this).val();
                    break;
                case "verticalalign": //文本对齐（上，中，下）
                    if ($(this).val() == "顶端") {
                        // $(selectionOffsetParent).children('.text').css({
                        //     display: 'block'
                        // });
                        $(selectionOffsetParent).css({
                            display: 'block'
                        });
                        return;
                    }
                    if ($(this).val() == "中部") {
                        // $(selectionOffsetParent).children('.text').css({
                        //     display: 'flex',
                        //     alignItems: 'center'
                        // });
                        $(selectionOffsetParent).css({
                            display: 'flex',
                            alignItems: 'center'
                        });
                        return;
                    }
                    if ($(this).val() == "底端") {
                        // $(selectionOffsetParent).children('.text').css({
                        //     display: 'flex',
                        //     alignItems: 'flex-end'
                        // });
                        $(selectionOffsetParent).css({
                            display: 'flex',
                            alignItems: 'flex-end'
                        });
                        return;
                    }
                    break;

            }
            this.options[0].innerHTML = $(this).val();
            this.options.selectedIndex = 0;
        });
        //焦点变换事件 (待完善)
        //$(".right").on('click', '.editor_box', function(event) {
        //    selectionNode = window.getSelection().anchorNode;
        //    selectionParent = selectionNode.parentElement;
        //    selectionOffsetParent = selectionNode.parentElement.offsetParent
        //});
        //字体颜色选择
        var fontcolorId = $("#fontcolor");
        var oldFontColor;
        fontcolorId.colorpicker({
            hideButton: true,
            showOn: 'button',
            strings: "主题颜色,标准颜色,其他颜色,主题颜色,返回调色板,历史记录,还没有历史记录"
        });
        fontcolorId.on("mouseenter.color", function(event, color){
            //如果焦点不是在编辑框里面则返回
            selectionNode = window.getSelection().anchorNode;
            if (!$(selectionNode).parents(".editor_box").length) {
                return;
            }
            selectionParent = selectionNode.parentElement;
            document.execCommand('ForeColor',"false",color);
        });
        fontcolorId.parent().on("mouseenter",".evo-pop", function(event){
            selectionNode = window.getSelection().anchorNode;
            if (!$(selectionNode).parents(".editor_box").length) {
                return;
            }
            selectionParent = selectionNode.parentElement;
            oldFontColor = selectionParent.color;
        });
        fontcolorId.parent().on("mouseleave",".evo-pop", function(event){
            document.execCommand('ForeColor',"false",oldFontColor);
        });
        fontcolorId.on("change.color", function(event, color){
            $(".menu_cnt a[name='fontcolor'] .mark").css('background-color',color);
        });
        //调色板失去焦点时隐藏
        right.on("click","*",function(e){
            $('.colorPicker').colorpicker('hidePalette');
        });
        $('.fontStyle').children('a[name="symbol"]').on('click', function () {
            layer.open({
                title:'<div style="text-align: center;color: #000;font-weight: 700">符号</div>',
                type: 2,
                area: ['700px', '540px'],
                fixed: false, //不固定
                maxmin: true,
                content: '../sys/teach/module/specialSymbol.html'
            });
        });
        $('select.line_height').on('change', function () {
            var $val = $(this).val();
            if (sessionStorage.getItem("classNameList")) {
                classNameList = JSON.parse(sessionStorage.getItem("classNameList"));
                $.each(classNameList, function (i,elem) {
                    $('.module[className="'+elem+'"]').css('line-height',$val);
                    console.log(elem);
                })
            }else{
                classNameList.push(sessionStorage.getItem("className"));
                selectionOffsetParent.style.lineHeight = $val;
            }
            classNameList = [];
        });
        $('select.letter_spacing').on('change', function () {
            var $val = $(this).val();
            console.log($val);
            if (sessionStorage.getItem("classNameList")) {
                classNameList = JSON.parse(sessionStorage.getItem("classNameList"));
                $.each(classNameList, function (i,elem) {
                    $('.module[className="'+elem+'"]').css('letter-spacing',$val+'px');
                    console.log(elem);
                })
            }else{
                classNameList.push(sessionStorage.getItem("className"));
                selectionOffsetParent.style.letterSpacing = $val+'px';
            }
            classNameList = [];
        });
    }($))

});