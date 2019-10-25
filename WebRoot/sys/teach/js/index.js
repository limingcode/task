$(function () {
        // 菜单切换
        $(document).on('click', '.nav', function(event) {
        	// 判断是否已上传PPT文件，若已上传则点击其他菜单无效
//            if ($(".upload_file.file_info").css("visibility") == "visible") {
//                return;
//            }
            $(this).addClass('active').siblings('span').removeClass('active');
            $(".menu_cnt .tabs").eq($(this).index()).addClass('active').siblings(".tabs").removeClass('active');
            
            var index = $(this).index();
            var total = $('.nav').length;
        	if(index == total-1){ //上传ppt下面的内容隐藏
        		$(".left").hide();
                $(".right").hide();
        	}else{
        		$(".left").show();
                $(".right").show();
        	}
        });
        // 左边ppt列表序号重置
        $(".left .page_box").each(function(index, el) {
            $(this).find(".page_index").html(index+1);
        });
        // ppt列表缩略图生成


    //点击切换画布
    $(".left").on('click', '.page_box .page', function(event) {
        $(".editor_box[name='"+$(this).attr("name")+"']").show().attr('id','domImg').siblings('.editor_box').hide().attr('id','');
    });
    var isTrue = true;
    function rightLIst(e){
        var rightClick_right = $(".rightClick_right");
        if(e.which == 3&&isTrue){
            $html = '<div style="height: 60px;width: 45px;float:left;">'+
                '<a href="javascript:;" name="pptBgc1" title="背景颜色">'+
                '<span class="mark"></span>'+
                '<span class="select">'+
                '<input style="width: 40px;height: 55px;margin-left: -30px;opacity: 0" type="submit" id="pptBgc1">'+
                '</span>'+
                '</a>'+
                '<span style="margin-left: 8px;color:#515151;float:left;height: 26px;line-height: 10px;">填充</span>'+
                '</div>'+
                '<div style="height: 60px;width: 45px;float:left;">'+
                '<a href="javascript:;" name="pptBgc2" title="背景颜色">'+
                '<span class="mark"></span>'+
                '<span class="select">'+
                '<input style="width: 40px;height: 55px;margin-left: -30px;opacity: 0" type="submit" id="pptBgc2">'+
                '</span>'+
                '</a>'+
                '<span style="margin-left: 8px;color:#515151;float:left;height: 26px;line-height: 10px;">轮廓</span>'+
                '</div>'+
                '<div style="height: 60px;width: 90px;float:left;">'+
                '<div style="margin-top:15px;overflow:hidden;width: 100%;">'+
                '<span style="color:#515151;float:left;height: 32px;line-height: 32px;">粗细</span>'+
                '<input style="width: 40%;" type="text" name="shape_size" value="5" />'+
                '<span style="right: 16px" class="choosePptOpc shape_size ">'+
                '<i class="pptOpacity_up"></i>'+
                '<i class="pptOpacity_down"></i>'+
                '</span>'+
                '</div>'+
                '</div>'+
                '<div class="copyText">克隆clone</div>';
            rightClick_right.html($html);
            rightClick_right.show();
            isTrue = false;
        }
        else if(e.which == 1){
            rightClick_right.hide();
            isTrue = true;
            return;
        }
        //获取右键点击坐标
        var x = e.clientX;
        var y = e.clientY;

        rightClick_right.show().css({left:x,top:(y-50),zIndex:999999999});
        var fontcolorId = $("#pptBgc1");
        var fontcolorId2 = $("#pptBgc2");
        fontcolorId.colorpicker({
            hideButton: true,
            showOn: 'button',
            strings: "主题颜色,标准颜色,其他颜色,主题颜色,返回调色板,历史记录,还没有历史记录"
        });
        fontcolorId2.colorpicker({
            hideButton: true,
            showOn: 'button',
            strings: "主题颜色,标准颜色,其他颜色,主题颜色,返回调色板,历史记录,还没有历史记录"
        });
        fontcolorId.on("change.color", function(event, color){
            $(".ppt a[name='pptBgc1'] .mark").css('background-color',color);
            if(e.target.localName == "div"){
                if(e.target.attributes[2].value == sessionStorage['className']){
                    $(e.target).css('background',color);
                }
            }
            else if(e.target.localName == "p"){
                if(e.target.parentElement.parentElement.attributes[2].value == sessionStorage['className']){
                    $(e.target).parents('.module').css('background',color);
                }
            }
        });
        fontcolorId2.on("change.color", function(event, color){
            $(".ppt a[name='pptBgc2'] .mark").css('background-color',color);
            if(e.target.localName == "div"){
                if(e.target.attributes[2].value == sessionStorage['className']){
                    $(e.target).css('border','1px solid '+color+'');
                }
            }
            else if(e.target.localName == "p"){
                if(e.target.parentElement.parentElement.attributes[2].value == sessionStorage['className']){
                    $(e.target).parents('.module').css('border','1px solid '+color+'');
                }
            }
        });
        var $shape_size = $('.shape_size'),$valueTow;
        var $shape_yy = $('.shape_yy'),$valueThree;
        var $shapeVal = $('input[name="shape_size"]');
        var $shapeValTow = $('input[name="shape_yy"]');
        if(e.target.localName == "div"){
            if(e.target.attributes[2].value == sessionStorage['className']){
                $valueTow = parseInt($(e.target).css('borderWidth'));
            }
        }
        else if(e.target.localName == "p"){
            if(e.target.parentElement.parentElement.attributes[2].value == sessionStorage['className']){
                $valueTow = parseInt($(e.target).parents('.module').css('borderWidth'));
            }
        }
        $shapeVal.val($valueTow?$valueTow:1);
        $shape_size.on('click','.pptOpacity_up', function () {
            var $value = parseInt($shapeVal.val());
            $shapeVal.val($valueTow);
            if($value<100){
                $('input[name="shape_size"]').val($value+1);
                $('.module[className="'+sessionStorage['className']+'"]').css('borderWidth',$value+1);
            }
            else{
                layer.tips('已到达最大值', '.pptOpacity_up', {
                    tips: [1, '#3595CC'],
                    time: 2000
                });
            }

        });
        $shape_size.on('click','.pptOpacity_down', function () {
            var $value = parseInt($shapeVal.val());
            $shapeVal.val($valueTow);
            if($value>0){
                $('input[name="shape_size"]').val($value-1);
                $('.module[className="'+sessionStorage['className']+'"]').css('borderWidth',$value-1);
            }
            else{
                layer.tips('已到达最小值', '.pptOpacity_down', {
                    tips: [1, '#3595CC'],
                    time: 2000
                });
            }
        });
        var isChoose = '',r = '',$thisStyle = '',$thisHtml = '',type = '',$thisName = '',$thisAnimate = '',$thisType = '',$thisAnimateName = '',a1 = '',a3 = '',j = 1,arrPpt = [];

        //鼠标右键克隆当前文本框
        $('.copyText').on('click', function () {
            $(e.target).find('.rotate').remove();
            $(e.target).find('.rRightDown').remove();
            $(e.target).find('.rLeftDown').remove();
            $(e.target).find('.rRightUp').remove();
            $(e.target).find('.rLeftUp').remove();
            $(e.target).find('.rRight').remove();
            $(e.target).find('.rLeft').remove();
            $(e.target).find('.rUp').remove();
            $(e.target).find('.rDown').remove();
            $(e.target).find('.bottomBorder').remove();
            $(e.target).removeClass('ui-draggable');
            $thisHtml = e.target.outerHTML;
            $thisStyle = $(e.target).attr('style');
                a3 = '';
                j = $('.animateList li').length;
                $thisAnimate = $(e.target).attr('animationname');
                $thisType = $('.'+$(e.target).attr('className')+'').attr('type');
                $thisAnimateName = $('.'+$(e.target).attr('className')+'').find('.cnt').text();
                arrPpt.push('boxNew'+textBoxIndex+'');
            if($(e.target).hasClass('choose')){
                isChoose = 'choose'
            }
            else isChoose = '';
                r = "<div style='"+$thisStyle+"' class='module"+' '+isChoose+"' className='boxNew"+textBoxIndex+"' >"+e.target.innerHTML+"</div></div>";
                var a = {
                    "className":arrPpt,
                    "type":$thisType,
                    "animationName":$thisAnimate,
                    "animateName_CN":$thisAnimateName
                };
                saveListCeShi.push(a);
                $.each(saveListCeShi, function (i, elem) {
                    if(elem.className.length>1){
                        $.each(elem.className, function (q, el) {
                            a3 += el;
                        })
                    }
                    else{
                        a3 = elem.className;
                    }
                    a1+='<li type="'+elem.type+'" class="'+a3+'" index="'+i+'"><span class="index" animationName="'+elem.animationName+'">'+j+'</span><span class="cnt">'+elem.animateName_CN+'</span><span class="close_btn hide"></span></li>'
                    $('.animateList').html(a1);
                    j++;
                });
            type = '文本框占位符';
            $thisName = 'boxNew'+textBoxIndex+'';
            E.insert(r,type,$thisName);
            textBoxIndex++;

                    $(e.target).append("<div class='rotate'></div><div class='rRightDown'> </div><div class='rLeftDown'> </div><div class='rRightUp'> </div><div class='rLeftUp'> </div><div class='rRight'> </div><div class='rLeft'> </div><div class='rUp'> </div><div class='rDown'></div><div class='bottomBorder'></div>");
                    // 禁止子元素冒泡
                    $(e.target).children().each(function(index, el) {
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
                        return e.target.querySelector(id);
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
                    var R = new Resize(e.target);
                    R.set(_$('.rUp'),'up').set(_$('.rDown'),'down').set(_$('.rLeft'),'left').set(_$('.rRight'),'right').set(_$('.rLeftUp'),'leftUp').set(_$('.rLeftDown'),'leftDown').set(_$('.rRightDown'),'rightDown').set(_$('.rRightUp'),'rightUp');
                    //添加旋转按钮
                    function addRotate(){
                        //鼠标按下添加监听
                     e.target.querySelector(".rotate").addEventListener("mousedown",function(event) {
                            document.addEventListener('mousemove',domRotate)
                        });
                        //鼠标弹起移除监听
                        document.addEventListener("mouseup",function(event) {
                            document.removeEventListener('mousemove',domRotate)
                        });
                        //鼠标拖动并旋转
                        var domRotate = function(t){
                            //获取盒子中心坐标
                            var dx = e.target.offsetLeft+e.target.parentNode.offsetLeft-e.target.parentNode.parentNode.scrollLeft + Number($(e.target).css("width").slice(0, -2))/2;
                            var dy = e.target.offsetTop+e.target.parentNode.offsetTop-e.target.parentNode.parentNode.scrollTop + Number($(e.target).css("height").slice(0, -2))/2;
                            //获取鼠标当前位置坐标
                            var mx = t.clientX;
                            var my = t.clientY;
                            var angle = getAngle(dx,dy,mx,my);
                            $(e.target).css('rotate', angle);
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
                                        e.target.style[property] = '';
                                    } else {
                                        e.target.style[property] = 'rotate(' + value%360 + 'deg)';
                                    }
                                } else {
                                    return '';
                                }
                            }
                        };
                    }
                    addRotate(e.target);
            layer.msg('已粘贴至编辑区，拖动即可');
            rightClick_right.hide();
        });
    }



    //选中盒子事件
    var ooo = false;
    $(".right")[0].addEventListener("mousedown",function(e){
        var rightClick_right = $(".rightClick_right");
        if($(e.target).hasClass('svg')){
            if(e.which==3){
                $html = '<div style="height: 60px;width: 45px;float:left;">'+
                    '<a href="javascript:;" name="pptBgc1" title="背景颜色">'+
                    '<span class="mark"></span>'+
                    '<span class="select">'+
                    '<input style="width: 40px;height: 55px;margin-left: -30px;opacity: 0" type="submit" id="pptBgc1">'+
                    '</span>'+
                    '</a>'+
                    '<span style="margin-left: 8px;color:#515151;float:left;height: 26px;line-height: 10px;">填充</span>'+
                    '</div>'+
                    '<div style="height: 60px;width: 45px;float:left;">'+
                    '<a href="javascript:;" name="pptBgc2" title="背景颜色">'+
                    '<span class="mark"></span>'+
                    '<span class="select">'+
                    '<input style="width: 40px;height: 55px;margin-left: -30px;opacity: 0" type="submit" id="pptBgc2">'+
                    '</span>'+
                    '</a>'+
                    '<span style="margin-left: 8px;color:#515151;float:left;height: 26px;line-height: 10px;">轮廓</span>'+
                    '</div>'+
                    '<div style="height: 60px;width: 90px;float:left;">'+
                    '<div style="margin-top:15px;overflow:hidden;width: 100%;">'+
                    '<span style="color:#515151;float:left;height: 32px;line-height: 32px;">粗细</span>'+
                    '<input style="float: rightl;width: 40%;" type="text" name="svg_size" value="5" />'+
                    '<span style="right: 16px" class="choosePptOpc svg_size ">'+
                    '<i class="pptSvg_up"></i>'+
                    '<i class="pptSvg_down"></i>'+
                    '</span>'+
                    '</div>'+
                    '</div>'+
                    '<div class="copyText">克隆clone</div>';
                rightClick_right.html($html);
                //获取右键点击坐标
                var x = e.clientX;
                var y = e.clientY;

                rightClick_right.show().css({left:x,top:(y-50),zIndex:10001});
                var fontcolorId = $("#pptBgc1");
                var fontcolorId2 = $("#pptBgc2");
                fontcolorId.colorpicker({
                    hideButton: true,
                    showOn: 'button',
                    strings: "主题颜色,标准颜色,其他颜色,主题颜色,返回调色板,历史记录,还没有历史记录"
                });
                fontcolorId2.colorpicker({
                    hideButton: true,
                    showOn: 'button',
                    strings: "主题颜色,标准颜色,其他颜色,主题颜色,返回调色板,历史记录,还没有历史记录"
                });
                fontcolorId.on("change.color", function(event, color){
                    $(".ppt a[name='pptBgc1'] .mark").css('background-color',color);
                    $(e.target).parents('.module').find('svg').css('fill',color);
                });
                fontcolorId2.on("change.color", function(event, color){
                    $(".ppt a[name='pptBgc2'] .mark").css('background-color',color);
                    $(e.target).parents('.module').find('svg').css('stroke',color);
                });
                $('.svg_size').on('click','.pptSvg_up', function () {
                    var $svgVal = $('input[name="svg_size"]');
                    var $value = parseInt($svgVal.val());
                    if($value<100){
                        $('input[name="svg_size"]').val($value+1);
                        $(e.target).parents('.module').find('svg').css('stroke-width',$value+1);
                    }
                    else{
                        layer.tips('已到达最大值', '.pptOpacity_up', {
                            tips: [1, '#3595CC'],
                            time: 2000
                        });
                    }
                });
                $('.svg_size').on('click','.pptSvg_down', function () {
                    var $svgVal = $('input[name="svg_size"]');
                    var $value = parseInt($svgVal.val());
                    if($value>0){
                        $('input[name="svg_size"]').val($value-1);
                        $(e.target).parents('.module').find('svg').css('stroke-width',$value-1);
                    }
                    else{
                        layer.tips('已到达最小值', '.pptOpacity_up', {
                            tips: [1, '#3595CC'],
                            time: 2000
                        });
                    }
                });
                var isChoose = '',r = '',$thisStyle = '',$target,$thisHtml = '',type = '',$thisName = '',$thisAnimate = '',$thisType = '',$thisAnimateName = '',a1 = '',a3 = '',j = 1,arrPpt = [];

                //鼠标右键克隆当前文本框
                $('.copyText').on('click', function () {
                    var $this = $(e.target).parents('.module')?$(e.target).parents('.module'):$(e.target);
                   
                    $this.find('.rotate').remove();
                    $this.find('.rRightDown').remove();
                    $this.find('.rLeftDown').remove();
                    $this.find('.rRightUp').remove();
                    $this.find('.rLeftUp').remove();
                    $this.find('.rRight').remove();
                    $this.find('.rLeft').remove();
                    $this.find('.rUp').remove();
                    $this.find('.rDown').remove();
                    $this.find('.bottomBorder').remove();
                    $this.removeClass('ui-draggable');
                    if(e.target.nodeName=="use"){
                    	$target = e.target.parentNode.parentNode;
                    }
                    else if(e.target.nodeName=="P"){
                    	$target = e.target.parentNode.parentNode;
                    }
                    else if(e.target.nodeName=="DIV"){
                    	$target = e.target;
                    }
                    else if(e.target.nodeName=="svg"){
                    	$target = e.target.parentNode;
                    }
                    $thisHtml = $this.get(0).outerHTML;
                    $thisStyle = $this.attr('style');
                    a3 = '';
                    j = $('.animateList li').length;
                    $thisAnimate = $this.attr('animationname');
                    $thisType = $('.'+$this.attr('className')+'').attr('type');
                    $thisAnimateName = $('.'+$this.attr('className')+'').find('.cnt').text();
                    arrPpt.push('boxNew'+textBoxIndex+'');
                    if($this.hasClass('choose')){
                        isChoose = 'choose'
                    }
                    else isChoose = '';
                    r = "<div style='"+$thisStyle+"' class='module"+' '+isChoose+"' className='boxNew"+textBoxIndex+"' >"+$this.get(0).innerHTML+"</div></div>";
                    var a = {
                        "className":arrPpt,
                        "type":$thisType,
                        "animationName":$thisAnimate,
                        "animateName_CN":$thisAnimateName
                    };
                    saveListCeShi.push(a);
                    $.each(saveListCeShi, function (i, elem) {
                        if(elem.className.length>1){
                            $.each(elem.className, function (q, el) {
                                a3 += el;
                            })
                        }
                        else{
                            a3 = elem.className;
                        }
                        a1+='<li type="'+elem.type+'" class="'+a3+'" index="'+i+'"><span class="index" animationName="'+elem.animationName+'">'+j+'</span><span class="cnt">'+elem.animateName_CN+'</span><span class="close_btn hide"></span></li>'
                        $('.animateList').html(a1);
                        j++;
                    });
                    type = '文本框占位符';
                    $thisName = 'boxNew'+textBoxIndex+'';
                    E.insert(r,type,$thisName);
                    textBoxIndex++;

                    $this.append("<div class='rotate'></div><div class='rRightDown'> </div><div class='rLeftDown'> </div><div class='rRightUp'> </div><div class='rLeftUp'> </div><div class='rRight'> </div><div class='rLeft'> </div><div class='rUp'> </div><div class='rDown'></div><div class='bottomBorder'></div>");
                    // 禁止子元素冒泡
                    $($target).children().each(function(index, el) {
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
                        return $target.querySelector(id);
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
                    var R = new Resize($target);
                    R.set(_$('.rUp'),'up').set(_$('.rDown'),'down').set(_$('.rLeft'),'left').set(_$('.rRight'),'right').set(_$('.rLeftUp'),'leftUp').set(_$('.rLeftDown'),'leftDown').set(_$('.rRightDown'),'rightDown').set(_$('.rRightUp'),'rightUp');
                    //添加旋转按钮
                    function addRotate(){
                        //鼠标按下添加监听
                    	$target.querySelector(".rotate").addEventListener("mousedown",function(event) {
                            document.addEventListener('mousemove',domRotate)
                        });
                        //鼠标弹起移除监听
                        document.addEventListener("mouseup",function(event) {
                            document.removeEventListener('mousemove',domRotate)
                        });
                        //鼠标拖动并旋转
                        var domRotate = function(t){
                            //获取盒子中心坐标
                            var dx = $target.offsetLeft+$target.parentNode.offsetLeft-$target.parentNode.parentNode.scrollLeft + Number($($target).css("width").slice(0, -2))/2;
                            var dy = $target.offsetTop+$target.parentNode.offsetTop-$target.parentNode.parentNode.scrollTop + Number($($target).css("height").slice(0, -2))/2;
                            //获取鼠标当前位置坐标
                            var mx = t.clientX;
                            var my = t.clientY;
                            var angle = getAngle(dx,dy,mx,my);
                            $($target).css('rotate', angle);
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
                                    	$target.style[property] = '';
                                    } else {
                                    	$target.style[property] = 'rotate(' + value%360 + 'deg)';
                                    }
                                } else {
                                    return '';
                                }
                            }
                        };
                    }
                    addRotate($target);
                    layer.msg('已粘贴至编辑区，拖动即可');
                    rightClick_right.hide();
                });
                return;
            }
            if(e.which==1){
                rightClick_right.hide();
            }
            return;
        }
        if (e.target.className.split(" ")[0] == "module") {
            $(e.target).addClass('focus');
            $(e.target).siblings('.module').removeClass('focus');
            sessionStorage.removeItem("classNameList");
            sessionStorage.setItem("className",$(e.target).attr("classname"));
            rightLIst(e);

        }
        else if(e.target.localName.split(" ")[0] == "p"){
            $(e.target).parents('.module').addClass('focus');
            $(e.target).parents('.module').siblings('.module').removeClass('focus');
            sessionStorage.removeItem("classNameList");
            sessionStorage.setItem("className",$(e.target).parents('.module').attr("classname"));
            rightLIst(e);

        }
        else{
            $('.right .module').removeClass('focus');
            sessionStorage.removeItem("className");
            $('.module_after').remove();
        }
    },true);

    $('.right').on("click",".text",function(e){
        e.stopPropagation();
            $(this).parent().addClass('focus');
            sessionStorage.removeItem("classNameList");
            $(this).parent().siblings('.module').removeClass('focus');
            sessionStorage.setItem("className",$(this).parent().attr("classname"));
    });
    $('.right').on("click","img",function(e){
        e.stopPropagation();
            $(this).parent().addClass('focus');
            sessionStorage.removeItem("classNameList");
            $(this).parent().siblings('.module').removeClass('focus');
            sessionStorage.setItem("className",$(this).parent().attr("classname"));
    });
    $('.right').on("click","svg",function(e){
        e.stopPropagation();
            $(this).parent().addClass('focus');
            sessionStorage.removeItem("classNameList");
            $(this).parent().siblings('.module').removeClass('focus');
            sessionStorage.setItem("className",$(this).parent().attr("classname"));
    });


    var scale = false;
    $(document).on('click','.listRight', function () {
        var $rightCont = $('.right');
    	// 判断是否已上传PPT文件，若已上传则点击其他菜单无效
//        if ($(".upload_file.file_info").css("visibility") == "visible") {
//            return;
//        }
        if(scale==false){
            $('.chooseViewCont').css('z-index','0');
            $('.setPptStyleCont').css('z-index','0');
            localStorage.setItem('scale',true);
                $('.animateCont').addClass('animateRight');
                $rightCont.addClass('active');
            scale = true;
        }
            setInterval(function () {
                var contHeight = $('.ppt').height()-$('.header').height();
                $rightCont.height($rightCont.width()*9/16+'px');
                $rightCont.css('margin-top',(contHeight-$rightCont.height())/2-15+'px');
            },10);
    });
    
    $(document).on('click','.listRight_1', function (e) {
        e.stopPropagation();
        $('.chooseViewCont').css('z-index','1');
        if(localStorage['scale']){
            $('.animateCont').removeClass('animateRight');
            $('.right').removeClass('active');
            localStorage.removeItem('scale');
            scale = false;
        }
    });

    var $choose = false;
    var $html= '';
    var $leftCont = '';
    var pptContWidth = '';
    var rem = '';
    $('.fontStyle').on('click','a[name=chooseView]', function () {
        if($choose==false){
            var $rightCont = $('.right');
            $leftCont = $('.editor_box');
            $('.animateCont').css('z-index','0');
            $('.setPptStyleCont').css('z-index','0');
            $('.chooseViewCont').addClass('chooseViewRight');
            $('.right').addClass('actives');
            $leftCont.addClass('active');
            setInterval(function () {
                var contHeight = $('.ppt').height()-$('.header').height();
                $rightCont.height($rightCont.width()*9/16+'px');
                $rightCont.css('margin-top',(contHeight-$rightCont.height())/2-15+'px');
            },10);
            $choose = true;
        }
        else{
            $('.animateCont').css('z-index','1');
            $('.chooseViewCont').removeClass('chooseViewRight');
            $('.right').removeClass('actives');
            $leftCont.removeClass('active');
            localStorage.removeItem('scale');
            $choose = false;
        }
    });

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

    var $eye = false;
    $.each($('.viewCont_list .close_btn'), function (i,elem) {
        $(this).click(function () {
            if(!$eye){
                $(this).css('background','url(../../images/ppt/closeEye.png) no-repeat');
                $eye = true;
            }
            else {
                $(this).css('background','url(../../images/ppt/openEye.png) no-repeat');
                $eye = false;
            }
        });
    });

    var pageNum = 0;
    var pageFooter = 0;
    var pageTime = 0;
    var type,$thisName,e;
    $('.menu_cnt').on('click','.pageTopFooter', function () {
        var $text= '<div class="checkCont">' +
                   '<div style="padding-left: 40px">日期和时间</div>' +
                   '<input name="pageTime" style="margin-left: 12px" type="text" placeholder="请输入时间" />' +
                   '<div class="roundedOne">' +
                   '<input type="checkbox" value="None" id="roundedOne" name="check"  />' +
                   '<label for="roundedOne"></label>' +
                   '</div>'+
                   '</div>'+
                   '<div class="checkCont">' +
                   '<div style="padding-left: 40px">页脚</div>' +
                   '<input name="pageFooter" style="margin-left: 12px" type="text" placeholder="请输入内容" />' +
                   '<div class="roundedOne">' +
                   '<input type="checkbox" value="None" id="roundedThree" name="check"  />' +
                   '<label for="roundedThree"></label>' +
                   '</div>'+
                   '</div>'+
                   '<div class="checkCont">' +
                   '<div class="roundedOne">' +
                   '<input type="checkbox" value="None" id="roundedTow" name="check"  />' +
                   '<label for="roundedTow"></label>' +
                   '</div>'+
                   '<div style="float:left;height: 30px;line-height: 30px;padding-left: 12px">幻灯片编号</div>' +
                   '</div>';
        layer.confirm($text,{
            btn:['应用','全部应用','取消'],
            title:'<div style="padding-left: 22px">页眉和页脚</div>'
        }, function (e) {
            var $checked = $('input[name="check"]');
            if($checked.eq(0).is(':checked')){
                type = '日期占位符';
                $thisName = 'boxPageTime'+pageTime+'';
                e = "<div style='width:20%;height: 30px;top: 93%;left: 2%' class='module' className='boxPageTime"+pageTime+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(e,type,$thisName);
                $('.module[className="boxPageTime'+pageTime+'"]').find('p').html($('input[name="pageTime"]').val()).css('text-align','center');
                pageTime++;
            }
            if($checked.eq(1).is(':checked')){
                type = '页脚占位符';
                $thisName = 'boxPageTop'+pageFooter+'';
                e = "<div style='width:20%;height: 30px;top: 93%;left: 40%' class='module' className='boxPageTop"+pageFooter+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(e,type,$thisName);
                $('.module[className="boxPageTop'+pageFooter+'"]').find('p').html($('input[name="pageFooter"]').val()).css('text-align','center');
                pageFooter++;
            }
            if($checked.eq(2).is(':checked')){
                type = '幻灯片编号占位符';
                $thisName = 'boxPageNum'+pageNum+'';
                e = "<div style='width:20%;height: 30px;top: 93%;left: 77%' class='module' className='boxPageNum"+pageNum+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(e,type,$thisName);
                $('.module[className="boxPageNum'+pageNum+'"]').find('p').html('1').css('text-align','center');
                pageNum++;
            }
            $('.layui-layer').hide();
            $('.layui-layer-shade').hide();
        }, function () {
            var $checked = $('input[name="check"]');
            if($checked.eq(0).is(':checked')){
                type = '日期占位符';
                $thisName = 'boxPageTime'+pageTime+'';
                e = "<div style='width:20%;height: 30px;top: 93%;left: 2%' class='module' className='boxPageTime"+pageTime+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(e,type,$thisName,true);
                $('.module[className="boxPageTime'+pageTime+'"]').find('p').html($('input[name="pageTime"]').val()).css('text-align','center');
                pageTime++;
            }
            if($checked.eq(1).is(':checked')){
                type = '页脚占位符';
                $thisName = 'boxPageTop'+pageFooter+'';
                e = "<div style='width:20%;height: 30px;top: 93%;left: 40%' class='module' className='boxPageTop"+pageFooter+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(e,type,$thisName,true);
                $('.module[className="boxPageTop'+pageFooter+'"]').find('p').html($('input[name="pageFooter"]').val()).css('text-align','center');
                pageFooter++;
            }
            if($checked.eq(2).is(':checked')){
                type = '幻灯片编号占位符';
                $thisName = 'boxPageNum'+pageNum+'';
                e = "<div style='width:20%;height: 30px;top: 93%;left: 77%' class='module' className='boxPageNum"+pageNum+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(e,type,$thisName,true);
                $('.module[className="boxPageNum'+pageNum+'"]').find('p').html('1').css('text-align','center');
                pageNum++;
            }
        })
    });

    var $pptStyleCont = $('.pptStyleCont');
    $pptStyleCont.on('click','li', function (e) {
        e.stopPropagation();
        $(this).css('background','#ccc').siblings().css('background','#fff');
            $(this).addClass('pptStyleCheck').siblings().removeClass('pptStyleCheck');
    });
    var $thisIndex, n, m, o, p, q,l = 8;
    $pptStyleCont.on('click','.pptStyleIn', function (e) {
        e.stopPropagation();
        $.each($('.editor_box:visible .module'), function () {
            var $this = $(this).attr('className');
            $(this).remove();
            $('li.'+$this+'').remove();
            $('.animateList li').each(function(i,el){
                $(this).find(".index").html(i+1);
            });
            $('.viewCont_list li').each(function(i,el){
                $(this).find(".index").html(i+1);
            });
            sessionStorage.removeItem('className');
        })
        $('.editor_box:visible').html('');
        $('.pptStyleCont').css('display','none');
        $.each($('.pptStyleCont li'), function () {
            if($(this).hasClass('pptStyleCheck')){
                $thisIndex = $(this).index();
            }
        });
        if($thisIndex==0){
            type = '文本占位符';
            n = "<div style='width:80%;height: 40%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            m = "<div style='width:80%;height: 30%;top: 52%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
            E.insert(m,type,'boxStyle2');
        }if($thisIndex==1){
            type = '文本占位符';
            n = "<div style='width:80%;height: 21%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            m = "<div style='width:80%;height: 49%;top: 33%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
            E.insert(m,type,'boxStyle2');
        }if($thisIndex==2){
            type = '文本占位符';
            n = "<div style='width:80%;height: 53%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            m = "<div style='width:80%;height: 16%;top: 65%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
            E.insert(m,type,'boxStyle2');
        }if($thisIndex==3){
            type = '文本占位符';
            n = "<div style='width:80%;height: 21%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            m = "<div style='width:39%;height: 49%;top: 33%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            o = "<div style='width:40%;height: 49%;top: 33%;left: 50%' class='module' className='boxStyle3"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
            E.insert(m,type,'boxStyle2');
            E.insert(o,type,'boxStyle3');
        }if($thisIndex==4){
            type = '文本占位符';
            n = "<div style='width:80%;height: 21%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            m = "<div style='width:39%;height: 6%;top: 33%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            o = "<div style='width:40%;height: 6%;top: 33%;left: 50%' class='module' className='boxStyle3"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            p = "<div style='width:39%;height: 49%;top: 41%;left: 10%' class='module' className='boxStyle4"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            q = "<div style='width:40%;height: 49%;top: 41%;left: 50%' class='module' className='boxStyle5"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
            E.insert(m,type,'boxStyle2');
            E.insert(o,type,'boxStyle3');
            E.insert(p,type,'boxStyle4');
            E.insert(q,type,'boxStyle5');
        }if($thisIndex==5){
            type = '文本占位符';
            n = "<div style='width:80%;height: 10%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
        }if($thisIndex==6){
            $.each($('.editor_box:visible .module'), function () {
                var $this = $(this).attr('className');
                    $(this).remove();
                    $('li.'+$this+'').remove();
                    $('.animateList li').each(function(i,el){
                        $(this).find(".index").html(i+1);
                    });
                    $('.viewCont_list li').each(function(i,el){
                        $(this).find(".index").html(i+1);
                    });
                    sessionStorage.removeItem('className');
            })
            $('.editor_box:visible').html('');
        }if($thisIndex==7){
            type = '文本占位符';
            n = "<div style='width:25%;height: 20%;top: 6%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            m = "<div style='width:25%;height: 60%;top: 28%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            o = "<div style='width:60%;height: 82%;top: 6%;left: 36%' class='module' className='boxStyle3"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
            E.insert(m,type,'boxStyle2');
            E.insert(o,type,'boxStyle3');
        }if($thisIndex==8){
            type = '文本占位符';
            n = "<div style='width:25%;height: 20%;top: 6%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            m = "<div style='width:25%;height: 60%;top: 28%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            o = "<div style='width:60%;height: 82%;top: 6%;left: 36%' class='module' className='boxStyle3"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
            E.insert(m,type,'boxStyle2');
            E.insert(o,type,'boxStyle3');
        }if($thisIndex==9){
            type = '文本占位符';
            n = "<div style='width:80%;height: 16%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            m = "<div style='width:80%;height: 65%;top: 27.62%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
            E.insert(m,type,'boxStyle2');
        }if($thisIndex==10){
            type = '文本占位符';
            n = "<div style='width:64%;height: 80%;top: 6%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            m = "<div style='width:15%;height: 80%;top: 6%;left: 75%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
            E.insert(n,type,'boxStyle1');
            E.insert(m,type,'boxStyle2');
        }
        l++;
    });
    $pptStyleCont.on('click','.pptStyleInAll', function (e) {
        e.stopPropagation();
        layer.confirm('<div style="color: red;font-weight: 700">应用全部将清空整个PPT内容，是否确定应用</div>',{
            title:'<div style="text-align: center">提醒</div>',
            btn:['是的，我确定应用','我考虑一下']
        }, function (e) {
            $('.editor_box').html('');
            $('.viewCont_list').html('');
            $('.pptStyleCont').css('display','none');
            $.each($('.pptStyleCont li'), function () {
                if($(this).hasClass('pptStyleCheck')){
                    $thisIndex = $(this).index();
                }
            });
            if($thisIndex==0){
                type = '文本占位符';
                n = "<div style='width:80%;height: 40%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                m = "<div style='width:80%;height: 30%;top: 52%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                E.insert(m,type,'boxStyle2',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }if($thisIndex==1){
                type = '文本占位符';
                n = "<div style='width:80%;height: 21%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                m = "<div style='width:80%;height: 49%;top: 33%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                E.insert(m,type,'boxStyle2',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }if($thisIndex==2){
                type = '文本占位符';
                n = "<div style='width:80%;height: 53%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                m = "<div style='width:80%;height: 16%;top: 65%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                E.insert(m,type,'boxStyle2',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }if($thisIndex==3){
                type = '文本占位符';
                n = "<div style='width:80%;height: 21%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                m = "<div style='width:39%;height: 49%;top: 33%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                o = "<div style='width:40%;height: 49%;top: 33%;left: 50%' class='module' className='boxStyle3"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                E.insert(m,type,'boxStyle2',true);
                E.insert(o,type,'boxStyle3',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }if($thisIndex==4){
                type = '文本占位符';
                n = "<div style='width:80%;height: 21%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                m = "<div style='width:39%;height: 6%;top: 33%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                o = "<div style='width:40%;height: 6%;top: 33%;left: 50%' class='module' className='boxStyle3"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                p = "<div style='width:39%;height: 49%;top: 41%;left: 10%' class='module' className='boxStyle4"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                q = "<div style='width:40%;height: 49%;top: 41%;left: 50%' class='module' className='boxStyle5"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                E.insert(m,type,'boxStyle2',true);
                E.insert(o,type,'boxStyle3',true);
                E.insert(p,type,'boxStyle4',true);
                E.insert(q,type,'boxStyle5',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }if($thisIndex==5){
                type = '文本占位符';
                n = "<div style='width:80%;height: 10%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }if($thisIndex==6){
                $('.editor_box').html('');
                $('.viewCont_list').html('');
            }if($thisIndex==7){
                type = '文本占位符';
                n = "<div style='width:25%;height: 20%;top: 6%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                m = "<div style='width:25%;height: 60%;top: 28%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                o = "<div style='width:60%;height: 82%;top: 6%;left: 36%' class='module' className='boxStyle3"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                E.insert(m,type,'boxStyle2',true);
                E.insert(o,type,'boxStyle3',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }if($thisIndex==8){
                type = '文本占位符';
                n = "<div style='width:25%;height: 20%;top: 6%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                m = "<div style='width:25%;height: 60%;top: 28%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                o = "<div style='width:60%;height: 82%;top: 6%;left: 36%' class='module' className='boxStyle3"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                E.insert(m,type,'boxStyle2',true);
                E.insert(o,type,'boxStyle3',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }if($thisIndex==9){
                type = '文本占位符';
                n = "<div style='width:80%;height: 16%;top: 10%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                m = "<div style='width:80%;height: 65%;top: 27.62%;left: 10%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                E.insert(m,type,'boxStyle2',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }if($thisIndex==10){
                type = '文本占位符';
                n = "<div style='width:64%;height: 80%;top: 6%;left: 10%' class='module' className='boxStyle1"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                m = "<div style='width:15%;height: 80%;top: 6%;left: 75%' class='module' className='boxStyle2"+l+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(n,type,'boxStyle1',true);
                E.insert(m,type,'boxStyle2',true);
                $('.editor_box').children('.module').each(function (i, elem) {
                    var $thisClass = $(this).attr('className');
                    $(this).attr('className',$thisClass+i)
                })
            }
            l++;
            layer.close(e);
        });


    });
    $(document).on('click','.pptStyle',function (e) {
        e.stopPropagation();
        $('.pptStyleCont').css('display','block');
    });
    $(document).on('click','html',function (e) {
        e.stopPropagation();
        $('.pptStyleCont').css('display','none');
    });
    $('.right').on('click','.editor_box', function (e) {
        e.stopPropagation();
        $('.pptStyleCont').css('display','none');
    });

    $(document).on('click','.wordArt',function (e) {
        e.stopPropagation();
        $('.wordArtCont').css('display','block');
    });
    $(document).on('click','html',function (e) {
        e.stopPropagation();
        $('.wordArtCont').css('display','none');
    });
    $('.right').on('click','.editor_box', function (e) {
        e.stopPropagation();
        $('.wordArtCont').css('display','none');
    });
    $('.wordArtCont').on('click','h1',function (e) {
        e.stopPropagation();
        $('.wordArtCont').css('display','none');
    });

    $(document).on('click','.huaShu',function (e) {
        e.stopPropagation();
        var $className = sessionStorage.getItem('className');
        var className = $('.module[classname="'+$className+'"]');
        var $text = className.attr('text');
        var text_login = '<div><textarea name="" id="textarea" cols="30" rows="10">'+$text+'</textarea></div>';
        layer.confirm(text_login , {
            skin:'index_skin',
            title:'<div style="text-align: center">话述添加</div>',
            btn: ['保 存'] //按钮
        }, function(e){
            if($text&&$text!=''&&$text!=undefined){
                $('#textarea').val($text);
            }
            //txtArr.push($('textarea').val());
            className.attr('text',$('textarea').val());
            layer.close(e);
            //$.each($('.animateList li'), function (i, elem) {
            //    var className = $(elem).attr('class');
            //    //var a  = txtArr[className];
            //    //var b = a[className];
            //    $(elem).attr('text',a)
            //});
        });
    });
    var wordArt = 0;
    $('.wordArtCont div h1').each(function (i, elem) {
        $(this).click(function () {
            var $class = $(this).attr('class');
            type = '艺术字占位符';
            $thisName = 'boxWordArt'+wordArt+'';
            e = "<div style='width:50%;height: 10%;top: 20%;left: 25%' class='module' className='boxWordArt"+wordArt+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><h1 class='"+$class+"' style='min-height: 16px;width: 100%'>在此放置您的文字</h1></div></div>"
            E.insert(e,type,$thisName);
            //$('.module[className="boxPageTime'+wordArt+'"]').find('p').html($('input[name="pageTime"]').val()).css('text-align','center');
            $('.wordArtCont').css('display','none');
            pageTime++;
        })
    });

    //双击形状添加文本框输入
    $(document).on('dblclick','.module', function () {
        if($(this).find('icon').length){
            if(!$(this).attr('contenteditable')){
                $(this).attr('contenteditable','true');
                $(this).css({
                    'display':'flex',
                    'align-items':'center'
                });
                var $iconText = '<p style="cursor:auto;position: absolute;top: 40%;left: 10%;color: #fff;font-size: 30px">请输入文字</p>';
                $(this).append($iconText);
            }
        }
    })
});