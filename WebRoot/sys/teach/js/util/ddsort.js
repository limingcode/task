/**
 * Created by Administrator on 2017/6/27.
 */
//var newText = 5416;
var textBoxIndex = 3;
var imgIndex = 1000;
var svgIndex = 2000;
var videoBoxIndex = 3000;
var audioBoxIndex = 4000;
var sheetIndex = 5000;
var saveList = new Object();
var saveList1 = new Object();
var saveList2 = new Object();
var saveListTow = new Array();
var saveListText = new Array();
var saveListCeShi = [];            //测试用
var txtArr = new Array;
var boxCutImg = 0;
//var animateNum = 1;
$(function () {

    if(localStorage['html']){
        var animateIndex = localStorage.getItem('animateIndex');
        textBoxIndex = 100*animateIndex;
        imgIndex = 1000*animateIndex;
        svgIndex = 10000*animateIndex;
        videoBoxIndex = 100000*animateIndex;
        audioBoxIndex = 1000000*animateIndex;
        sheetIndex = 10000000*animateIndex;
    }
        if(localStorage['html']){
            var $html = localStorage.getItem('html');
            //saveList = JSON.stringify(localStorage.getItem('saveList'));
            $('body').html($html);
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
            //begin 判断页面是否拥有缓存

            function eightResize(elem){
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
                var $editor_box = $('.editor_box:visible');
                var bW = $editor_box.width();
                var bH = $editor_box.height();
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
                        this.height>e.clientY-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop?Css(this.obj,{top:(e.clientY-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop)/bH*100 + "%",height:(this.height-e.clientY+this.obj.parentNode.offsetTop-this.obj.parentNode.parentNode.scrollTop)/bH*100 + "%"}):this.turnDown(e);
                    },
                    down : function(e){
                        e.clientY-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop>this.original[3]?Css(this.obj,{top:this.original[3]/bH*100+'%',height:(e.clientY-this.original[3]-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop - 10)/bH*100 +'%'}):this.turnUp(e);
                    },
                    left : function(e){
                        e.clientX-this.obj.parentNode.offsetLeft+this.obj.parentNode.parentNode.scrollLeft<this.width?Css(this.obj,{left:(e.clientX-this.obj.parentNode.offsetLeft+this.obj.parentNode.parentNode.scrollLeft)/bW*100 +'%',width:(this.width-e.clientX+this.obj.parentNode.offsetLeft-this.obj.parentNode.parentNode.scrollLeft)/bW*100 + "%"}):this.turnRight(e);
                    },
                    right : function(e){
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
                        //var bW = $('.editor_box:visible').width();
                        //var aW = parseInt($(this.obj).css('left'))/bW*100;
                        //var aH = parseInt($(this.obj).css('top'))/bW*100;
                        Css(this.obj,{top:this.height/bH*100+'%',height:(e.clientY-this.obj.parentNode.offsetTop - this.height+this.obj.parentNode.parentNode.scrollTop)/bH*100 + '%'});
                    },
                    turnUp : function(e){
                        Css(this.obj,{top : (e.clientY-this.obj.parentNode.offsetTop+this.obj.parentNode.parentNode.scrollTop)/bH*100 +'%',height : (this.original[3] - e.clientY+this.obj.parentNode.offsetTop-this.obj.parentNode.parentNode.scrollTop)/bH*100 +'%'});
                    },
                    turnRight : function(e){
                        Css(this.obj,{left:this.width/bW*100+'%',width:(e.clientX-this.obj.parentNode.offsetLeft- this.width - this.obj.parentNode.parentNode.scrollLeft)/bW*100 +'%'});
                    },
                    turnLeft : function(e){
                        Css(this.obj,{left:(e.clientX-this.obj.parentNode.offsetLeft+this.obj.parentNode.parentNode.scrollLeft)/bW*100 +'%',width:(this.original[2]-e.clientX+this.obj.parentNode.offsetLeft-this.obj.parentNode.parentNode.scrollLeft)/bW*100+'%'});
                    }
                });
                var R = new Resize(elem);
                R.set(_$('.rUp'),'up').set(_$('.rDown'),'down').set(_$('.rLeft'),'left').set(_$('.rRight'),'right').set(_$('.rLeftUp'),'leftUp').set(_$('.rLeftDown'),'leftDown').set(_$('.rRightDown'),'rightDown').set(_$('.rRightUp'),'rightUp');
            }


            var $overHidden = true;
            $(document).on('click','.moreIcon', function () {
                if($overHidden){
                    $('.moreAnimateCont').css('overflow','visible');
                    $overHidden = false;
                }
                else {
                    $('.moreAnimateCont').css('overflow','hidden');
                    $overHidden = true;
                }
            });
            $(".left").on('click', '.page_box .page', function(event) {
                $(".editor_box[name='"+$(this).attr("name")+"']").show().siblings('.editor_box').hide();
            });
            $('.animateList li').each(function (i,elem) {
                var className = $(this).attr('class');
                saveList[className] = {
                    "className":$(this).attr('class'),
                    "animationName":$(this).find('.index').attr('animationname'),
                    "animateName_CN":$(this).find('.cnt').html()
                };
                saveListTow[i] = {
                    "className":$(this).attr('class'),
                    "animationName":$(this).find('.index').attr('animationname'),
                    "animateName_CN":$(this).find('.cnt').html()
                };
            });
            //videojs("example_video_1", {}, function(){
            //});
            // end 判断页面是否拥有缓存

        }
    //setInterval(function(){
    //    html2canvas($('.editor_box:visible'),{
    //        allowTaint: true,
    //        onrendered: function(canvas) {
    //            var img = $('.left li .page[name="'+$('.editor_box:visible').attr("name")+'"] img');
    //            img.attr("src",canvas.toDataURL("image/jpg"))
    //        },
    //        timeout: 10000,useCORS:true
    //    });
    //
    //}, 3000);


    setInterval(function(){
        var node = document.getElementById('domImg');
        domtoimage.toPng(node)
            .then(function (dataUrl) {
                var img = new Image();
                img.src = dataUrl;
                //document.body.appendChild(img);
                $('.left li .page[name="'+$('#domImg').attr("name")+'"] img').attr("src",dataUrl);
            })
    }, 3000);

    //左侧缩略图绑定拖拽事件
    $( '.wrap' ).DDSort({
        target: 'li',
        floatStyle: {
            'border': '1px solid #ccc',
            'background-color': '#fff',
            'webkitTransform': 'rotate(0)',
            'mozTransform': 'rotate(0)',
            'msTransform': 'rotate(0)',
            'transform': 'rotate(0)'
        }
    });

    //右侧动画窗格绑定拖拽事件
    $( '.animateList' ).DDSort({
        target: 'li',
        floatStyle: {
            'border': '1px solid #ccc',
            'background-color': '#fff',
            'webkitTransform': 'rotate(0)',
            'mozTransform': 'rotate(0)',
            'msTransform': 'rotate(0)',
            'transform': 'rotate(0)'
        }
    });


    //新建幻灯片
    var $listImg = '';
    $('.addPPT').on('click', function () {
        var pageBox_length = $(".left .page_box").length+1;
        var text_1 = '';

        if(sessionStorage['listImg']){
            $listImg = sessionStorage.getItem('listImg');
            text_1 = '<div class="editor_box hide" name='+pageBox_length+' style="display: none;background: url('+$listImg+') center center / 100% 100% no-repeat;" animatenum="0"></div>';

        }
        else{
            $listImg = ' ';
            text_1 = '<div class="editor_box hide" name='+pageBox_length+' style="display: none;" animatenum="0">' +
                '</div>';
        }

        var text = '<li class="page_box">' +
            '<i></i>' +
            '<span class="page_index">'+pageBox_length+'</span>' +
            '<span class="closePPT">×</span>' +
            '<div class="page" name='+pageBox_length+'><img class="thumbImg" src="'+$listImg+'" alt="" /></div> ' +
            '</li>';
        // text:左侧缩略图    text_1:中间内容区块
        var $textCont = $('.wrap ul');
        var $txtCont_1 = $('.right');
        $txtCont_1.append(text_1);
        $textCont.append(text);
        $('.page[name="'+pageBox_length+'"]').addClass('border_org').parent('.page_box').siblings('.page_box').find('.page').removeClass('border_org');
        $(".editor_box[name='"+pageBox_length+"']").show().attr('id','domImg').siblings('.editor_box').hide().attr('id','');
        var type = '文本框';
        var $thisName = 'box'+ textBoxIndex++ +'';
        var $thisName1 = 'box'+ textBoxIndex++ +'';
        var newText1 = "<div style='left: 6%;top: 7%;width: 85%;height: 24%;' class='module textBoxIndex' className='"+$thisName+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:auto'><p style='min-height: 16px;width: 100%;font-size:0.203125rem'></p></div></div>"
        var newText2 = "<div style='left: 6%;top: 47%;width: 85%;height: 24%;' class='module textBoxIndex'  className='"+$thisName1+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:auto'><p style='min-height: 16px;width: 100%;font-size:0.203125rem'></p></div></div>"
        E.insert(newText1,type,$thisName);
        E.insert(newText2,type,$thisName1);
//        newText++;
        //让新建的幻灯片绑定删除函数
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
            });

        });
        //左侧选中更改边框颜色
        $(".left .page_box").each(function(i, elem) {
            $(this).on('click', function () {
                $(this).find('.page').addClass('border_org');
                $(this).siblings().find('.page').removeClass('border_org');
            });
        });

        $.each($('.page_box'), function (i, elem) {
            $(this).mousedown(function (e) {
                var key = e.which;
                if(key==3){
                    $(this).find('div').addClass('border_org');
                    $(this).siblings().find('div').removeClass('border_org');
                }
            })
        });
        var $page_box = $('.page_box');
//        $('.rightClick_left').on('click','a', function (e) {                       //缩略图区域鼠标右键菜单内容事件
//            switch ($(this).attr('class')){
//                case 'hidPpt':                                                     //隐藏幻灯片
//                    $.each($page_box, function (i) {
//                        if($(this).find('div').hasClass('border_org')){
//                            $(this).addClass('pptHidLeftList');
//                            $(this).find('i').css('display','block');
//                            $('.editor_box').eq(i).attr('pptHide',true);
//                        }
//                    });
//                    break;
//                case 'showPpt':                                                    //显示幻灯片
//                    $.each($page_box, function (i, elem) {
//                        if($(this).find('div').hasClass('border_org')){
//                            $(this).removeClass('pptHidLeftList');
//                            $(this).find('i').css('display','none');
//                        }
//                    });
//                    break;
//                case 'createPpt':                                                  //新建幻灯片
//                    var $listImg = '';
//                    var pageBox_length = $(".left .page_box").length+1;
//                    var text_1 = '';
//
//                    if(sessionStorage['listImg']){
//                        $listImg = sessionStorage.getItem('listImg');
//                        text_1 = '<div class="editor_box hide" name='+pageBox_length+' style="display: none;background: url('+$listImg+') center center / 100% 100% no-repeat;"></div>';
//
//                    }
//                    else{
//                        $listImg = ' ';
//                        text_1 = '<div class="editor_box hide" name='+pageBox_length+' style="display: none;"></div>';
//                    }
//
//                    var text = '<li class="page_box">' +
//                        '<i></i>' +
//                        '<span class="page_index">'+pageBox_length+'</span>' +
//                        '<span class="closePPT">×</span>' +
//                        '<div class="page" name='+pageBox_length+'><img src="'+$listImg+'" alt="" /></div> ' +
//                        '</li>';
//                    // text:左侧缩略图    text_1:中间内容区块
//                    var $textCont = $('.wrap ul');
//                    var $txtCont_1 = $('.right');
//                    $txtCont_1.append(text_1);
//                    $textCont.append(text);
//                    $('.page[name="'+pageBox_length+'"]').addClass('border_org').parent('.page_box').siblings('.page_box').find('.page').removeClass('border_org');
//                    $(".editor_box[name='"+pageBox_length+"']").show().attr('id','domImg').siblings('.editor_box').hide().attr('id','');
//                    var type = '文本框';
//                    
//                    var $thisName = 'box'+ textBoxIndex++ +'';
//                    var $thisName1 = 'box'+ textBoxIndex++ +'';
//                    var newText1 = "<div style='left: 6%;top: 7%;width: 85%;height: 24%;' class='module textBoxIndex' className='"+$thisName+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:auto'><p style='min-height: 16px;width: 100%'></p></div></div>"
//                    var newText2 = "<div style='left: 6%;top: 47%;width: 85%;height: 24%;' class='module textBoxIndex'  className='"+$thisName1+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:auto'><p style='min-height: 16px;width: 100%'></p></div></div>"
//                    E.insert(newText1,type,$thisName);
//                    E.insert(newText2,type,$thisName1);
//                    
////                    var $thisName = 'box'+newText+'';
////                    var $thisName1 = 'box110'+newText+'';
////                    var newText1 = "<div style='left: 6%;top: 7%;width: 85%;height: 24%;' class='module' className='box"+newText+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
////                    var newText2 = "<div style='left: 6%;top: 47%;width: 85%;height: 24%;' class='module' className='box110"+newText+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
////                    E.insert(newText1,type,$thisName);
////                    E.insert(newText2,type,$thisName1);
//                    //让新建的幻灯片绑定删除函数
//                    $('.deletePpt').on('click', function () {
//                        var $thisIndex = $(this).siblings('.page').attr('name');
//                        $(this).parent('.page_box').remove();
//
//                        $(".left .page_box").each(function(index, el) {
//                            $(this).find(".page_index").html(index+1);
//                        });
//
//                        $.each($('.editor_box'), function (i,elem) {
//                            if($(elem).attr('name')==$thisIndex){
//                                $(elem).remove();
//                            }
//                        })
//                    });
//                    //左侧选中更改边框颜色
//                    $(".left .page_box").each(function(i, elem) {
//                        $(this).on('click', function () {
//                            $(this).find('.page').addClass('border_org');
//                            $(this).siblings().find('.page').removeClass('border_org');
//                        });
//                    });
//                    break;
//                case 'deletePpt':                                                  //删除幻灯片
//                    $('.page_box').each(function (i, elem) {
//                        if($(this).find('div').hasClass('border_org')){
//                            var $thisIndex = $(this).find('.page').attr('name');
//                            $(this).remove();
//                            $(".left .page_box").each(function(index, el) {
//                                $(this).find(".page_index").html(index+1);
//                            });
//                            $.each($('.editor_box'), function (i,elem) {
//                                if($(elem).attr('name')==$thisIndex){
//                                    $(elem).remove();
//                                }
//                            })
//                        }
//                    });
//                    break;
//                case 'setPptStyle':
//                    $('.chooseViewCont').css('z-index','0');
//                    $('.animateCont').css('z-index','0');
//                    $('.setPptStyleCont').addClass('setPptStyleRight');
//                    $('.right').addClass('active');
//                    $('.editor_box').addClass('active');
//                    break;
//            }
//
//        });
    });

    //左侧选中更改边框颜色
    $(".left .page_box").each(function(i, elem) {
        $(this).on('click', function () {
            $(this).find('.page').addClass('border_org');
            $(this).siblings().find('.page').removeClass('border_org');
        });
    });

    //右边选中更改边框颜色
    //$(".animateList li").each(function(i, elem) {
    //    $(this).on('click', function () {
    //        $(this).addClass('border_org').siblings().removeClass('border_org');
    //    });
    //});

    //主题选中改变边框颜色
    $('.theme li').each(function (i, elem) {
        $(this).on('click', function () {
            $(this).find('img').addClass('border_org');
            $(this).siblings().find('img').removeClass('border_org');
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
                $(elem).remove();
            }
        })
    });

    //左侧拖拽释放后初始化下标
    $(".left").on('mouseup', '.page_box', function(event) {
        setTimeout(function(){
            $(".left .page_box").each(function(index, el) {
                $(this).find(".page_index").html(index+1);
            });
        },10)
    });

    //右侧拖拽释放后初始化下标
    $(".animateList").on('mouseup', 'li', function(event) {
        setTimeout(function(){
            $(".animateList li").each(function(index, el) {
                $(this).find(".index").html(index+1);
            });
        },10)
    });


    var arr = new Array();
    var animationList = {
        "addClass": function (className,animationName) {$("."+className).addClass( animationName+" "+"animated" )}
    };
    var moreAnimate = false;


//    //监听键盘Delete键,对当前选中的内容框进行删除
//    $(document).keydown(function(event){
//        if(sessionStorage['className']){
//            if(event.keyCode==46){
//                $.each($('.module'), function () {
//                    var $this = $(this).attr('className');
//                    if($this==sessionStorage['className']){
//                        $(this).remove();
//                        $('li.'+$this+'').remove();
//                        $('.animateList li').each(function(i,el){
//                            $(this).find(".index").html(i+1);
//                        });
//                        $(this).remove();
//                        $('.viewCont_list li').each(function(i,el){
//                            $(this).find(".index").html(i+1);
//                        });
//
//                        sessionStorage.removeItem('className');
//                    }
//                })
//            }
//        }
//    });
    
    //监听键盘Delete键,对当前选中的内容框进行删除
    var deleteList = [];
    var deleteList1 = [];
    var deleteList2 = [];
    var deleteList3 = [];
    var e = '',$outHtml = '',type = '',$thisName = '',$thisAnimate = '',$thisType = '',$thisAnimateName = '',a1 = '',a3 = '',j = 1,arrPpt = [];
    $(document).keydown(function(event){
        if(sessionStorage['className']){
        var a = sessionStorage['className'];
            if(event.keyCode==46){
                    deleteList.push(sessionStorage.getItem("className"));
                    $.each(saveListCeShi, function (i, elem) {
                        $.each(elem.className, function (j, ele) {
                            if(ele==deleteList[0]){
                                deleteList1.push(j);
                            }
                        })
                    });
                    $.each($('.module'), function (i,item) {
                        var $this = $(this).attr('className');
                        if($this==sessionStorage['className']){
                            var a = $(this).get(0).outerHTML;

                            //if($(this).hasClass('choose')){
                                a3 = '';
                                j = $('.animateList li').length;
                                //$outHtml = $(this).children('.text').get(0).outerHTML;
                                $thisAnimate = $(this).attr('animationname');
                                $thisType = $('.'+$(this).attr('className')+'').attr('type');
                                $thisAnimateName = $('.'+$(this).attr('className')+'').find('.cnt').text();
                                //arrPpt = $(this).attr('className');
                                arrPpt.push('boxNew'+textBoxIndex+'');
                                var b = {
                                    "className":arrPpt,
                                    "type":$thisType,
                                    "animationName":$thisAnimate,
                                    "animateName_CN":$thisAnimateName,
                                    "html":$outHtml
                                };
                                //saveListCeShi.push(b);
                            //}
                            $(this).find('.rotate').remove();
                            $(this).find('.rRightDown').remove();
                            $(this).find('.rLeftDown').remove();
                            $(this).find('.rRightUp').remove();
                            $(this).find('.rLeftUp').remove();
                            $(this).find('.rRight').remove();
                            $(this).find('.rLeft').remove();
                            $(this).find('.rUp').remove();
                            $(this).find('.rDown').remove();
                            $(this).find('.bottomBorder').remove();
                            $(this).removeClass('ui-draggable');
                            var undoLength = undoList.length;
                            undoList[undoLength?undoLength:0] = {
                                thisClass:$(this).attr('className'),
                                outHtml:$(this)[0].outerHTML,
                                type:$(this).hasClass('choose')?$('.'+$(this).attr('className')).attr('type'):false
                            };
                            var length = $('.animateList li.'+$this+'').length;
                        	if(0 != length){
	                        	subAnimateNum(length); 
                        	}
                            $(this).remove();
                            $('li.'+$this+'').remove();
                            $('.animateList li').each(function(i,el){
                                $(this).find(".index").html(i+1);
                            });
                            $('.viewCont_list li').each(function(i,el){
                                $(this).find(".index").html(i+1);
                            });
                            sessionStorage.removeItem('className');
                         
                        }
                    });

                if(deleteList1.length){
                    $.each(deleteList1, function (o, el) {
                        saveListCeShi.splice(el,1);
                    });
                }
            }
            if(event.keyCode==8){
                $.each($('.module'), function (i,elem) {
                    var $this = $(this).attr('className');
                    if ($this == sessionStorage['className']) {
                        $(this).find('.rotate').remove();
                        $(this).find('.rRightDown').remove();
                        $(this).find('.rLeftDown').remove();
                        $(this).find('.rRightUp').remove();
                        $(this).find('.rLeftUp').remove();
                        $(this).find('.rRight').remove();
                        $(this).find('.rLeft').remove();
                        $(this).find('.rUp').remove();
                        $(this).find('.rDown').remove();
                        $(this).find('.bottomBorder').remove();
                        $(this).removeClass('ui-draggable');
                        var undoLength = undoList.length;
                        undoList[undoLength ? undoLength : 0] = {
                            thisClass: $(this).attr('className'),
                            outHtml: $(this)[0].outerHTML,
                            type: $(this).hasClass('choose') ? $('.' + $(this).attr('className')).attr('type') : false
                        };
                        $(elem).append("<div class='rotate'></div><div class='rRightDown'> </div><div class='rLeftDown'> </div><div class='rRightUp'> </div><div class='rLeftUp'> </div><div class='rRight'> </div><div class='rLeft'> </div><div class='rUp'> </div><div class='rDown'></div><div class='bottomBorder'></div>");
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
                        }
                        function removeListener(element,e,fn){
                            element.removeEventListener?element.removeEventListener(e,fn,false):element.detachEvent("on" + e,fn)
                        }
                        var Class = function(properties){
                            var _class = function(){return (arguments[0] !== null && this.initialize && typeof(this.initialize) == 'function') ? this.initialize.apply(this, arguments) : this;};
                            _class.prototype = properties;
                            return _class;
                        };

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
                                this.class.css('cursor','crosshair').addClass('focus');
                            },
                            resize : function(e){
                                this.fun(e);
                                Sys.IE?(this.resizeelm.onlosecapture=function(){this.fS()}):(this.resizeelm.onblur=function(){this.fS()})
                            },
                            stop : function(even){
                                removeListener(document, "mousemove", this.fR);
                                removeListener(document, "mousemove", this.fS);
                                this.class.css('cursor','move');
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
                        //添加旋转按钮
                        function addRotate(elem){
                            //鼠标按下添加监听
                            elem.querySelector(".rotate").addEventListener("mousedown",function(event) {
                                document.addEventListener('mousemove',domRotate)
                            });
                            //鼠标弹起移除监听
                            document.addEventListener("mouseup",function(event) {
                                document.removeEventListener('mousemove',domRotate)
                            });
                            //鼠标拖动并旋转
                            var domRotate = function(e){
                                //获取盒子中心坐标
                                var dx = elem.offsetLeft+elem.parentNode.offsetLeft-elem.parentNode.parentNode.scrollLeft + Number($(elem).css("width").slice(0, -2))/2;
                                var dy = elem.offsetTop+elem.parentNode.offsetTop-elem.parentNode.parentNode.scrollTop + Number($(elem).css("height").slice(0, -2))/2;
                                //获取鼠标当前位置坐标
                                var mx = e.clientX;
                                var my = e.clientY;
                                var angle = getAngle(dx,dy,mx,my);
                                $(elem).css('rotate', angle);
                            };
                            //鼠标移动距离转化角度
                            function getAngle(dx,dy,mx,my){
                                var x = Math.abs(dx-mx);
                                var y = Math.abs(dy-my);
                                var z = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
                                var cos = y/z;
                                var radina = Math.acos(cos);//用反三角函数求弧度
                                var angle = Math.floor(180/(Math.PI/radina));//将弧度转换成角度
                                if(mx>dx&&my>dy){//鼠标在第四象限
                                    angle = 180 - angle;
                                }
                                if(mx==dx&&my>dy){//鼠标在y轴负方向上
                                    angle = 180;
                                }
                                if(mx>dx&&my==dy){//鼠标在x轴正方向上
                                    angle = 90;
                                }
                                if(mx<dx&&my>dy){//鼠标在第三象限
                                    angle = 180+angle;
                                }
                                if(mx<dx&&my==dy){//鼠标在x轴负方向
                                    angle = 270;
                                }
                                if(mx<dx&&my<dy){//鼠标在第二象限
                                    angle = 360 - angle;
                                }
                                return angle;
                            }
                            //判断浏览器支持哪种旋转属性
                            var property = (function() {
                                var element = document.createElement('div');
                                var properties = ['transform', 'WebkitTransform',
                                    'MozTransform', 'msTransform',
                                    'OTransform'];
                                var p;
                                while (p = properties.shift()) {
                                    if (element.style[p] !== undefined) {
                                        return p;
                                    }
                                }
                                return false;
                            })();
                            //在jq的css()方法上添加rotate
                            $.cssHooks['rotate'] = {
                                get: function(elem, computed, extra){
                                    if (property) {
                                        var transform = elem.style[property];
                                        if (transform) {
                                            return transform.replace(/.*rotate\((.*)deg\).*/, '$1');
                                        } else {
                                            var matrix = getComputedStyle(elem, null)[property];
                                            if (matrix.match(/matrix\(1, *0, *0, *1, *0, *0\)/)) {
                                                return '';
                                            } else {
                                                var m = matrix.match(/matrix\([^,]+, *([^,]+),/);
                                                var angle = Math.round(Math.asin(m[1]) * (180/Math.PI));
                                                return angle;
                                            }
                                        }
                                    } else {
                                        return '';
                                    }
                                },
                                set: function(elem, value){
                                    if (property) {
                                        value = parseInt(value);
                                        if (value == 0) {
                                            elem.style[property] = '';
                                        } else {
                                            elem.style[property] = 'rotate(' + value%360 + 'deg)';
                                        }
                                    } else {
                                        return '';
                                    }
                                }
                            };
                        }
                        addRotate(elem);
                        //$(this).append("<div class='rotate'></div><div class='rRightDown'> </div><div class='rLeftDown'> </div><div class='rRightUp'> </div><div class='rLeftUp'> </div><div class='rRight'> </div><div class='rLeft'> </div><div class='rUp'> </div><div class='rDown'></div><div class='bottomBorder'></div>");

                    }
                })
            }
        }
        else if (sessionStorage.getItem("classNameList")) {
            if (event.keyCode == 46) {
         
                deleteList = JSON.parse(sessionStorage.getItem("classNameList"));
                var temp = '';
                $.each(deleteList, function (p, item) {
                    $.each(saveListCeShi, function (i, elem) {
                        $.each(elem.className, function (j, ele) {
                            if (ele == item) {
                                deleteList2.push(j);
                            }
                        });
                    });
                    $('.viewCont_list .'+item+'').remove();
                });
                $.each(deleteList, function (i, elem) {
                    deleteList3 += elem;
                });
                $.each(deleteList, function (i, elem) {
                    $.each($('.module'), function () {
                        var $this = $(this).attr('className');
                        if ($this == elem) {
                            $(this).remove();
                        }
                    });
                });
                
                var length = $('.animateList li.'+deleteList3+'').length;
            	if(0 != length){
                	subAnimateNum(length); 
            	}
                
                $('li.' + deleteList3 + '').remove();
                $('.animateList li').each(function (i, el) {
                    $(this).find(".index").html(i + 1);
                });
                $('.viewCont_list li').each(function (i, el) {
                    $(this).find(".index").html(i + 1);
                });
                if (deleteList2.length) {
                    $.each(deleteList2, function (o, el) {
                        saveListCeShi.splice(el, 1);
                    });
                }
                sessionStorage.removeItem('classNameList');
             }
            deleteList = [];
            deleteList1 = [];
            deleteList2 = [];
            deleteList3 = [];
        }
    });

    //添加话述：将话述内容绑定在标签内，弹窗时可显示和修改
    $(document).on('click','.animateList li', function (even) {
        var $text = $(even.target).attr('text');
        if($(even.target).attr('text')==undefined||$(even.target).attr('text')==''){
            $text = '';
        }
        var text_login = '<div><textarea name="" id="textarea" cols="30" rows="10">'+$text+'</textarea></div>';
        layer.confirm(text_login , {
            skin:'index_skin',
            title:'<div style="text-align: center">话述添加</div>',
            btn: ['保 存'] //按钮
        }, function(e){
            var $text = $(even.target).attr('text');
            var className = $(even.target).attr('class');
            txtArr [className]=$('textarea').val();
            layer.close(e);
            $.each($('.animateList li'), function (i, elem) {
                var className = $(elem).attr('class');
                var a  = txtArr[className];
                //var b = a[className];
                $(elem).attr('text',a)
            });
        });


    });

    
    function addAnimateNum(){
    	var animateNum = $('.editor_box:visible').attr('animateNum');
    	if(isEmpty(animateNum)){
    		animateNum = 0;
    	}else{
    		animateNum = Number(animateNum);
    	}
    	$('.editor_box:visible').attr('animateNum', ++animateNum);
    }
    
    function subAnimateNum(length) {
    	var animateNum = Number($('.editor_box:visible').attr('animateNum'));
    	animateNum -= length;
    	$('.editor_box:visible').attr('animateNum', animateNum);
	}

    //添加动画函数
    function add(className,animationName) {
    	var hdb = '';
    	if (sessionStorage.getItem("classNameList")) {
            classNameList = JSON.parse(sessionStorage.getItem("classNameList"));
            $.each(classNameList,function(i,elem){
        		hdb += elem;
        	})
        }else{
            classNameList.push(sessionStorage.getItem("className"));
            hdb = classNameList[0];
        }
    
        $(".module[className='"+className+"']").attr('allclass',hdb).attr('animationName',animationName).addClass( animationName+" "+"animated"+" "+"choose").removeClass( animationName+" "+"animated",3000)
        //$(".module[className='"+className+"']").addClass( animationName+" "+"animated").removeClass( animationName,3000)
        classNameList=[];
    }

    //绑定动画到所选框
    var animateCheck = true,classNameList = [];
    $('.moreAnimateCont_1 li').on('click', function () {
        var arrPPT = [];
        if (sessionStorage.getItem("classNameList")) {
            classNameList = JSON.parse(sessionStorage.getItem("classNameList"));
        }else if(sessionStorage.getItem("className")){
            classNameList.push(sessionStorage.getItem("className"));
        }
        else{
        	layer.msg('未选中哦~');
        	return;
        }
        var animationName = $(this).attr('animationname');
        var animateName_CN = $(this).find('i').html();
        var type = $(this).attr('type');
        $.each(classNameList, function (i,elem) {
            arrPPT.push(elem);
        });
        if(!classNameList||!classNameList.length){
            layer.msg('未选中哦~',{time: 1000});
            return false;
        }
        $.each(arrPPT, function (i,elem) {
            add(elem,animationName);
        });
        var a = {
            "className":arrPPT,
            "type":type,
            "animationName":animationName,
            "animateName_CN":animateName_CN
        };

        //判断动画是新增还是替换
        var p = false;
        if(saveListCeShi.length){
            $.each(saveListCeShi, function (i, elem) {
                var b_box= 0;
                $.each(saveListCeShi[i].className, function (j, ele) {
                    if(ele==arrPPT[j]){
                        b_box++;
                    }
                });
                if(saveListCeShi[i].type== a.type&&b_box==arrPPT.length){
                    saveListCeShi.splice(i,1,a);
                    return p = true;
                }
            });
            if(!p){
                saveListCeShi.push(a);
                addAnimateNum();
            }
        }
        else{
            saveListCeShi.push(a);
            addAnimateNum();
        }


        var a1 = '',j = 1,a2 = '',a3 = '',k=1;
        if(localStorage['animateList']&&animateCheck){
            j = $('.animateList li').length;
            a1 = $('.animateList').html();
            animateCheck = false;
        }
        $.each(saveListCeShi, function (i, elem) {
            a3 = '';
            if(elem.className.length){
                $.each(elem.className, function (j, el) {
                    a3 += el;
                })
            }
            else{
                a3 = elem.className;
            }
            a1+='<li type="'+elem.type+'" class="'+a3+'" index="'+i+'"><span class="index" animationName="'+elem.animationName+'">'+j+'</span><span class="cnt">'+elem.animateName_CN+'</span><span class="close_btn hide"></span></li>'
            $('.animateList').html(a2+a1);
            j++;
        });
        $.each($('.animateList li'), function (i, elem) {
            saveListTow[i] = {
                "className":$(this).attr('class'),
                "animationName":$(this).find('.index').attr('animationname'),
                "animateName_CN":$(this).find('.cnt').html()
            };
            var className = $(elem).attr('class');
            var a  = txtArr[className];
            //var b = a[className];
            $(elem).attr('text',a)
        });

        //动画窗格删除动画
            $('.animateList .close_btn').on('click',function () {
            	var $this = $(this);
                var saveListChoose = false;
                $this.parent().remove();
                $.each($('.module'), function (i,elem) {
                    if($(elem).attr('allclass')==$this.parent().attr("class")){
                        $(elem).removeAttr('animationName').removeClass('choose');
                    }
                });
                $('.animateList li').each(function(i,el){
                    $(this).find(".index").html(i+1);
                });
                var _type = $(this).parent('li').attr('type');
                var _class = $(this).parent().attr('class');
                $.each(saveListCeShi, function (i, elem) {
                    //if(saveListCeShi[i].className== _class&&saveListCeShi[i].type== _type){
                    if(!saveListChoose){
                        if(elem.type== _type){
                            $.each(saveListCeShi[i].className, function (i,elem) {
                                if(elem==_class){
                                    saveListCeShi.splice(i,1);
                                    return saveListChoose = true;
                                }
                            });
                        }
                    }
                });
                delete saveListTow[$(this).parent().find('.index').html()-1];
                
                var allclass = $this.parent().attr("class");
                var animateNum = parseInt($('.module[allclass="'+allclass+'"]').parent('.editor_box').attr('animateNum'))-1;
                $('.module[allclass="'+allclass+'"]').parent('.editor_box').attr('animateNum',animateNum);
            });
        classNameList = [];
    });

    //形状区域显隐
    $('.shape').on('click', function (e) {
        e.stopPropagation();
        $('.shape .icon_cont').css('display','block')
    });
    $(document).on('click','html', function (e) {
        e.stopPropagation();
        $('.shape .icon_cont').css('display','none')
    });
    $('.right').on('click','.editor_box', function (e) {
        e.stopPropagation();
        $('.shape .icon_cont').css('display','none')
    });
    $('.icon_cont').on('click','i',function(e){
        e.stopPropagation();
        $('.shape .icon_cont').css('display','none')
    });
    //添加形状
    $.each($('.iconFont_cont i'), function (i, elem) {
        $(elem).on('click', function () {
            var iconName = $(this).find('use').attr('xlink:href');
            var iconHtml = '<div className="box'+svgIndex+'" class="module svgIndex svg" style="display: flex;align-items: center;width: 20%;height: 20%;">' +
                '<div class="text svg" contenteditable="true" style="position: absolute;cursor:auto;width:100%;height:auto"><p class="shape_p svg" style="padding: 10px;font-size: 0.203125rem;min-height: 16px;width: 100%;box-sizing: border-box"></p></div>'+
                '<svg style="fill: #fff;stroke: black;stroke-width: 5;" class="icon svg" aria-hidden="true">' +
                '<use class="svg" xlink:href="'+iconName+'"></use> </svg> </div>';
            var d = $('.editor_box:visible');
            var type = '形状占位符';
            var $thisName = 'box'+svgIndex+'';
            E.insert(iconHtml,type,$thisName);
            $(''+iconName+'').attr('preserveAspectRatio','none');
            svgIndex++;
            $shape = true;
            
            $('.module').on('dblclick','svg', function (e) {
                $(this).siblings('.text').focus();
            })
        });
    });


    //显隐表格
    var sheet = false;
    $('.sheet').on('click', function (e) {
        e.stopPropagation();
        if(!sheet){
            $('.sheetCont').css('display','block');
            sheet = true;
        }
        else if(sheet){
            $('.sheetCont').css('display','none');
            sheet = false;
        }
    });

    //插入标签动画效果
    $('.span_animate').mouseenter(function(e){
        var _this = $(this);
        _this.addClass('bounceIn'+'  '+'animated');
        setTimeout(function () {
            _this .removeClass('bounceIn');
        },1000);
    });

    //表格区域选择
    var a = '',b = '',x = '',y = '';
    var $sheetI = $('.sheetCont i');
    $.each($sheetI, function (i,elem) {
        $(elem).hover(function () {
            a = $(this).index()+1;
            y = parseInt(a/11);  x = a - y*10;
            b = 10 - x;
            if(x > 10) {
                x = x - 10;
                y = y + 1 ;
            }
            if(y+1==1){
                $sheetI.slice(0,x).css('background','red');
                $sheetI.slice(x,80).css('background','#fff');
            }
            else if(y+1>1){
                $sheetI.slice(0,x).css('background','red');
                $sheetI.slice(x,80).css('background','#fff');
                for(var j = 1;j <= y ; j++){
                    $sheetI.slice(10*j,10*j+x).css('background','red');
                    $sheetI.slice(10*j+x,80).css('background','#fff');
                }
            }
        });
        //点击添加表格
        $(elem).on('click', function () {
            var sheetTh = '',sheetTd = '',sheetTr = '';
            for(var k=0;k <x ; k++){
                sheetTh += '<th style="font-size:0.200463rem" contenteditable="true"></th>';
            }
            for(var p=0;p <x ; p++){
                sheetTd += '<td style="font-size:0.200463rem" contenteditable="true"></td>';
            }
            for(var q=1;q <y+1 ; q++){
                sheetTr += '<tr style="font-size:0.200463rem">'+sheetTd+'</tr>';
            }
            var sheetTable = '<div style="width:63%;height:56%" class="module sheetIndex" classname="box'+sheetIndex+'">'+
                '<table style="cursor:auto;height: 100%;" border="1">'+
                '<tr>'+sheetTh+'</tr>'+sheetTr+
                '</table>'+
                '</div>';
            var type = '表格占位符';
            var $thisName = 'box'+sheetIndex+'';
            E.insert(sheetTable,type,$thisName);
            sheetIndex++;
        })
    });

    //点击添加视频
//    $('.video').on('click', function () {
//        var videoCont = '<div style="min-width: 420px;min-height: 300px" className="box'+videoBoxIndex+'" class="module">' +
//            '<div class="videoRRR" style="cursor:auto;width:100%;height: 100%;margin:0 auto;position: relative;">'+
//            '<video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="100%" height="100%">'+
//            '<source src="video/oceans-clip.mp4" type="video/mp4" />'+
//            '<source src="video/oceans-clip.mp4" type="video/webm" />'+
//            '<source src="video/oceans-clip.mp4" type="video/ogg" />'+
//            '</video>'+
//            '</div>'+
//            '</div>';
//        var type = '视频占位符';
//        var $thisName = 'box'+videoBoxIndex+'';
//        videoBoxIndex++;
//        E.insert(videoCont,type,$thisName);
//        videojs("example_video_1", {}, function(){
//        });
//    });

    //点击添加音频
//    $('.audio').on('click', function () {
//        var length = $('.editor_box:visible').find('audio').length;
//        var audioCont = '<div style="height: 50px;left: initial;right:-370px;top:'+length*100+'px;" className="box'+audioBoxIndex+'" class="moduleAudio">' +
//                            '  <div class="findAudio" style="cursor:auto;width:100%;height: 100%;margin:0 auto;position: relative;">' +
//                                '<div class="audioBegin">' +
//                                    '<i class="audioBg"></i>' +
//                                    '<span class="audioBg_span"><i class="audioStar"></i>' +
//                                    '<i style="display: inline-block;width: 300px;" class="audioLength"></i></span>' +
//                                    '<input class="ds_n" type="file" id="uploadFile" name="uploadFile" />' +
//                                '</div>'+
//                            '<div class="audioText">' +
//                                '<p class="fl">11111111231132312312312312312312312312321321312312312311111111111111</p>' +
//                                '<span class="audioText_btn rt">修 改</span></div>'+
//                            '</div>'+
//                            '<audio style="width: 100%;display: none" src="audio/Kalimba.mp3" id="audio" controls="controls">' +
//                                '<i></i>' +
//                            '</audio>' +
//                        '</div>';
//        var type = '音频占位符';
//        var $thisName = 'box'+audioBoxIndex+'';
//        audioBoxIndex++;
//        E.insert(audioCont,type,$thisName);
//
//
//
//        //播放时间
//        //播放进度
//
//        $('.audioText_btn').on('click', function (even) {
//            var $p = $(this).siblings('p');
//            var $text = $(this).siblings('p').html();
//            var $audioClass = $(this).siblings('p').attr('text');
//            if($text==undefined||$text==''){
//                $text = '';
//            }
//            var text_login = '<div><textarea name="" id="textarea" cols="30" rows="10">'+$text+'</textarea></div>';
//            layer.confirm(text_login , {
//                skin:'index_skin',
//                title:'<div style="text-align: center">音频注释</div>',
//                btn: ['保 存'] //按钮
//            }, function(e){
//               $p.html($('#textarea').val());
//                layer.close(e);
//            });
//        });
//
//        $('.moduleAudio').on('click','.audioStar', function () {
//            var $audio = $(this).parents('.findAudio').siblings('audio').attr('id');
//            var audio =  document.getElementById(''+$audio+'');
//            if(audio.paused){
//                audio.play();
//                $(this).css({
//                    'background':'url(images/ppt/audioStop.png) no-repeat',
//                    'backgroundSize':'100%'
//                });
//                var action=true;
//                $.playBar.addBar($('.audioLength'),audio.duration*1000);//第一个参数是需要显示播放器的容器，第二个参数为时间，单位毫秒
//                $.playBar.changeBarColor("#a995fe");//设置进度条颜色
//
//
//                return;
//            }
//            audio.pause();
//                $(this).css({
//                    'background':'url(images/ppt/auidoStar.png) no-repeat',
//                    'backgroundSize':'100%'
//                });
//        });
//
//
//
//        $('.moduleAudio').each(function (i, elem) {
//            $(this).on('click', function () {
//            });
//            $(this).mouseenter(function (e) {
//                e.stopPropagation();
//                $(this).stop().animate({
//                    right:0
//                },500);
//                    $(this).find('.audioText').stop().slideDown();
//            });
//            $(this).mouseleave(function (e) {
//                e.stopPropagation();
//                $(this).stop().animate({
//                    right:'-370px'
//                });
//                $(this).find('.audioText').stop().slideUp();
//            })
//        });
//    });



    //图片本地预览
//    $("#file0").change(function(){
//        // getObjectURL是自定义的函数，见下面
//        // this.files[0]代表的是选择的文件资源的第一个，因为上面写了 multiple="multiple" 就表示上传文件可能不止一个
//        // ，但是这里只读取第一个
//        var objUrl = getObjectURL(this.files[0]) ;
//        if (objUrl) {
//            // 在这里修改图片的地址属性
//            var type = '图片占位符';
//            var $thisName = 'box'+imgIndex+'';
//            var text = '<div className="box'+imgIndex+'" class="module moduleImg"><img style="cursor:auto;width:100%;height:100%;" src='+objUrl+' alt=""></div>';
//            E.insert(text,type,$thisName);
//            imgIndex++;
//        }
//    });
    //建立一個可存取到該file的url
    function getObjectURL(file) {
        var url = null ;
        // 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
        if (window.createObjectURL!=undefined) { // basic
            url = window.createObjectURL(file) ;
        } else if (window.URL!=undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file) ;
        } else if (window.webkitURL!=undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file) ;
        }
        return url ;
    }


    //添加文本框
    $('.textBox').on('click', function () {
        var type = '文本框';
        var $thisName = 'box'+textBoxIndex+'';
        var e = "<div class='module textBoxIndex' style='' className='box"+textBoxIndex+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:auto'><p style='min-height: 26px;width: 100%;font-size:0.203125rem'></p></div></div>"
        E.insert(e,type,$thisName);
        textBoxIndex++;
        $('.module').on('dblclick', function (e) {
            $(this).find('.text').focus();
        });
        $('.module').on('dblclick', function (e) {
            $(this).find('p').focus();
        })
        
        $(document).keydown(function (e) {
            if(sessionStorage['className']){
                $('.module').each(function () {
                   if($(this).hasClass('focus')){
                       $(this).find('.text').focus();
                   }
                });
            }
        });
    });


    //////////////////////////////////////////////
    //图片音频预览
    $("#file1").change(function(){
        // getObjectURL是自定义的函数，见下面
        // this.files[0]代表的是选择的文件资源的第一个，因为上面写了 multiple="multiple" 就表示上传文件可能不止一个
        // ，但是这里只读取第一个
        var objUrl = getURL(this.files[0]) ;
        // 这句代码没什么作用，删掉也可以
        if (objUrl) {
            // 在这里修改图片的地址属性
            var text = '<div class="module"><audio src='+objUrl+'></audio></div>'
            E.insert(text);
        }
    }) ;
    //建立一個可存取到該file的url
    function getURL(file) {
        var url = null ;
        // 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
        if (window.createObjectURL!=undefined) { // basic
            url = window.createObjectURL(file) ;
        } else if (window.URL!=undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file) ;
        } else if (window.webkitURL!=undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file) ;
        }
        return url ;
    }


//    //添加视频
//    $("#btn").click(function(){
//        var path = $('#filePath').val();
//        alert(path);
//    });

    //更多主题（文本）
    $('.lookMore').on('click', function (e) {
        e.stopPropagation();
        $('.lookMore').parents('.content').siblings('.theme_block').css('display','block');
        $('body').on('click',function () {
            $('.lookMore').parents('.content').siblings('.theme_block').css('display','none');
        })
    });
    
    $.each($('.theme li'), function (i,elem) {
        $(elem).on('click', function () {
            layer.confirm('<div style="width: 100%;text-align: center">选一个吧</div>',{
                btn:['应用','全部应用','取消'],
                title:'<div style="padding-left: 22px">页眉和页脚</div>'
            }, function (e) {
                if(i==9){
                    $('.editor_box:visible').css({
                        background:'#fff'
                    });
                    $('.page[name="'+$('.editor_box:visible').attr('name')+'"]').find('img').attr({
                        src:''
                    });
                    sessionStorage.removeItem('listImg');
                    layer.close(e);
                    return;
                }
                $('.editor_box:visible').css({
                    background:'url(../sys/teach/images/ppt/theme/00'+(i+1)+'.jpg) no-repeat center center',
                    backgroundSize:'100% 100%'
                });
                $('.page[name="'+$('.editor_box:visible').attr('name')+'"]').find('img').attr({
                    src:'../sys/teach/images/ppt/theme/00'+(i+1)+'.jpg'
                });
                layer.close(e);
            }, function () {
                if(i==9){
                    $('.editor_box').css({
                        background:'#fff'
                    });
                    $('.page').find('img').attr({
                        src:''
                    });
                    sessionStorage.removeItem('listImg');
                    return;
                }
                $('.editor_box').css({
                    background:'url(../sys/teach/images/ppt/theme/00'+(i+1)+'.jpg) no-repeat center center',
                    backgroundSize:'100% 100%'
                });
                $('.page').find('img').attr({
                    src:'../sys/teach/images/ppt/theme/00'+(i+1)+'.jpg'
                });
                sessionStorage.setItem('listImg','../sys/teach/images/ppt/theme/00'+(i+1)+'.jpg');
            });
        });

    });


//    function save(){
//        var end_1 = new Array;
//        var end_2 = new Array();
//        var _end = new Array();
//        var content = '';
//        $.each($('.animateList li'), function (i, elem) {
//            saveListText[i] = {
//                text:$(elem).attr('text')
//            };
//            var className = $(elem).attr('class');
//            var a  = txtArr[className];
//            $(elem).attr('text',a)
//        });
//
//        $('.videoRRR').each(function (i, elem) {
//            var c = $(this).find('video').html();
//            var b = '<video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="100%" height="100%">'+
//                ''+c+''+
//                '</video>';
//            $(this).html(b);
//        });
//
//        $('.editor_box').each(function (i, elem) {
//            var a = $(elem).html();
//            end_1[i] = {
//                html:a
//            };
//        });
//        $('.page_box').each(function (i, elem) {
//            var b = $(elem).find('img').attr('src');
//            end_2.push(b);
//        });
//        _end = {
//            html:end_1,
//            animate:saveListTow,
//            text:saveListText,
//            img:end_2
//        };
//    }

//    $(document).on('click','.savePpt',function () {
//        save();
//    });
//    $(document).on('click','.lookPpt',function () {
//        $('.videoRRR').each(function (i, elem) {
//                var c = $(this).find('video').html();
//                var b = '<video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="100%" height="100%">'+
//                    ''+c+''+
//                    '</video>';
//                $(this).html(b);
//        });
//    });
    var r = sessionStorage.getItem('className');
    $($('.'+r).find('input').on('change', function () {
    }));


    //window.onbeforeunload = onbeforeunload_handler;
    //window.onunload = onunload_handler;
    function onbeforeunload_handler() {
        var save = new Array();
        $.each(saveListTow, function (i, elem) {
            save.push(elem);

        });
        var a = $('.videoRRR');
        var c = '';
        var d = '';
        $.each($('.videoRRR'),function (i,elem) {
            c = $(this).find('#example_video_1_html5_api').html();
            d = $(this).find('.vjs-tech').html();
            var b = '<video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="100%" height="100%">'+
                ''+c+''+
                '</video>';
            $(this).html(b);
        });

        localStorage.setItem('html',$('body').html());
        localStorage.setItem('saveList',saveListTow);
        if(localStorage['animateIndex']){
            localStorage['animateIndex'] = localStorage['animateIndex']+1;
        }
        else{
            localStorage.setItem('animateIndex','1');
        }
    }

    function onunload_handler() {
        var save = new Array();
        $.each(saveListTow, function (i, elem) {
            save.push(elem);

        });

        var c = '';
        $.each($('.videoRRR'),function (i,elem) {
            c = $(this).find('#example_video_1').html();
            var b = '<video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="100%" height="100%">'+
                ''+c+''+
                '</video>';
            $(this).html(b);
        });

        localStorage.setItem('html',$('body').html());
        localStorage.setItem('saveList',saveListTow);
        if(localStorage['animateIndex']){
            localStorage['animateIndex'] = localStorage['animateIndex']+1;
        }
        else{
            localStorage.setItem('animateIndex','1');
        }
    }


});