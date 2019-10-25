var classNameList = new Array();
$(function () {
    var E = function(){};
    var $box;
    var j = 1;
    var $thisClass,$thisHtml,$thisStyle,$thisStyleNew,textBoxIndex=789456132;
    var textCont = new Object();
    E.fn = E.prototype;
    //往编辑器插入内容
    E.fn.insert = function(html,type,thisName,all,copy){
        if(all){
            $box = $('.editor_box');
            $.each($box, function (i, elem) {
                bindCreateElem($box[i]);
            })
        }
        else{
            $box = $('.editor_box:visible');
            if(copy){
                bindCreateElem($box[0],copy);
            }
            else{
                bindCreateElem($box[0]);
            }
        }
        // 监听,为编辑框内的新增的子元素绑定拖动及拖拽事件
        $box.append(html);
        var $html ='<li class="'+thisName+'"><span class="index">'+j+'</span><span class="cnt">'+type?type:''+'</span><span class="close_btn"></span></li>'
        $('.viewCont_list').append($html);

        j++;
        $.each($('.viewCont_list .close_btn'), function (i,elem) {
            var $eye = $eye+i;
            $eye = false;
            var $thisName = $(this).parent('li').attr('class');
            $(this).click(function (e) {
                e.stopPropagation();
                if(!$eye){
                    $(this).css({
                        'background':'url(images/ppt/closeEye.png) 60% no-repeat',
                        'backgroundSize':'100%'
                    });
                    $('.module[className='+$thisName+']').addClass('eyeHid');
                    $eye = true;
                }
                else {
                    $(this).css({
                        'background':'url(images/ppt/openEye.png) no-repeat',
                        'backgroundSize':'100%'
                    });
                    $(".module[className='"+$thisName+"']").removeClass('eyeHid');
                    $eye = false;
                }
            })
        });
        $.each($('.viewCont_list li'), function (i,elem) {
            var $thisName = $(this).attr('class');
            $(this).click(function () {
                var $this = $('.module[className='+$thisName+']');
                $(this).css('borderColor','red').siblings('li').css('borderColor','#000');
                $this.addClass('focus');
                $this.siblings('.module').removeClass('focus');
                sessionStorage.setItem("className",$thisName);
                })
        })
    };
    //键盘快捷键定义
    E.fn.keydown = (function($box){
        window.onkeydown = function(e){
            // 如果没有选中盒子则返回
            if (!sessionStorage.getItem("className")&&!sessionStorage.getItem("classNameList")) {
                return;
            }
            $box = $('.editor_box:visible');
            // 判断是多选还是单选
            if (sessionStorage.getItem("classNameList")) {
                classNameList = JSON.parse(sessionStorage.getItem("classNameList"));
            }else{
                classNameList.push(sessionStorage.getItem("className"));
            }
            switch(e.keyCode){
                case 37://左
                    $.each(classNameList,function(index, el) {
                        $box.find('.module[className="'+classNameList[index]+'"]').css("left",function(index,value){
                            return ((Number(value.slice(0,-2)) - 2)/$box.width())*100 + "%";
                        });
                    });
                break;
                case 38://上
                    $.each(classNameList,function(index, el) {
                        $box.find('.module[className="'+classNameList[index]+'"]').css("top",function(index,value){
                            return ((Number(value.slice(0,-2)) - 2)/$box.height())*100 + "%";
                        });
                    });
                break;
                case 39://右
                    $.each(classNameList,function(index, el) {
                        $box.find('.module[className="'+classNameList[index]+'"]').css("left",function(index,value){
                            return ((Number(value.slice(0,-2)) + 2)/$box.width())*100 + "%";
                        });
                    });
                break;
                case 40://下
                    $.each(classNameList,function(index, el) {
                        $box.find('.module[className="'+classNameList[index]+'"]').css("top",function(index,value){
                            return ((Number(value.slice(0,-2)) + 2)/$box.height())*100 + "%";
                        });
                    });
                break;
            }
            classNameList = [];
        }
    })();

    //监控元素内容变化
    function bindCreateElem(elem,copy){
        var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
        // 选择目标节点
        var target = elem;
        var newNode;
        // 创建观察者对象
        var textBox = 100;
        var observer = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                $.each(mutation.addedNodes,function(index, el) {
                    //判断是否为新增，是则给其绑定拖动和拖拽大小事件
                    if (!$(this).hasClass('ui-draggable')&&$(this).hasClass('module')||$(this).hasClass('bottomBorder')) {
                        $(this).draggable();
                        eightResize(this);
                        $(this).draggable({
                        	start: function() {
                                $.each($('.module'), function (i,elem) {
                                    var $this = $(this).attr('className');
                                    console.log($this);
                                    if ($this == sessionStorage['className']) {
                                        console.log($(this));
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
                                                console.log($(this.height))
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
                                    }
                                })
                            },
                            stop:function(event,ui){
                                var $thisHtml = $(this).html();
                                var $editor = $('.editor_box:visible');
                                var $editorW = $editor.width();
                                var $editorH = $editor.height();
                                var $thisL = parseInt($(this).css('left'))/$editorW*100;
                                var $thisT = parseInt($(this).css('top'))/$editorH*100;
                                var $thisW = parseInt($(this).css('width'))/$editorW*100;
                                var $thisH = parseInt($(this).css('height'))/$editorH*100;
                                $(this).css({
                                    'left':$thisL+'%',
                                    'top':$thisT+'%',
                                    'width':$thisW+'%',
                                    'height':$thisH+'%'
                                });
                                $(this).addClass('focus').siblings('.module').removeClass('focus');
                            }
                        });
                    }
                });
            });
        });
        // 配置观察选项:
        var config = {
            'childList': true
        };
        // 传入目标节点和观察选项
        observer.observe(target, config);
    }
    //生成可拖动边框，具有8个拖动点
    function eightResize(elem){
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


    }

    E = new E();
    window.E = E;

});