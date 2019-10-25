var lessonStr;
var restIndex = 0;
var relId = 0;
var bigNum = 0, newNum = 0;
var _left = $('.left');
$(function () {
    var imgLength = $('.imgNum').length;
    var t_img; // 定时器
    var isLoad = true; // 控制变量

// 		判断图片加载状况，加载完成后回调
    isImgLoad(function () {
        // 加载完成
        $('.editor_box').each(function () {
            var thisImg = $(this).find('img');
            thisImg.width(thisImg[0].naturalWidth / 2);
        });
    });

// 		判断图片加载的函数
    function isImgLoad(callback) {


        // 注意我的图片类名都是cover，因为我只需要处理cover。其它图片可以不管。
        $('.imgNum').each(function () {
            if (this.height === 0) {
                isLoad = false;
                return false;
            }
        });
        // 为true，没有发现为0的。加载完毕
        if (isLoad) {
            clearTimeout(t_img);
            // 回调函数
            callback();
            // 为false，因为找到了没有加载完成的图，将调用定时器递归
        } else {
            isLoad = true;
            t_img = setTimeout(function () {
                isImgLoad(callback);
            }, 500);
        }
    }

    var length;
    var isZ = false;
    var undoList = new Array();


    //左侧栏移动效果代码
    $('.wrap').DDSort({
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


    //缩略图点击选中效果
    $('.page_box').each(function () {
        $(this).click(function () {
            imgW = $('.editor_box:visible').find('img').width();
            imgH = $('.editor_box:visible').find('img').height();
            $(this).find('.selectCont').addClass('after').parents('.page_box').siblings().find('.selectCont').removeClass('after');
            $(this).find('select').addClass('active').parents('.page_box').siblings().find('select').removeClass('active');
            $(this).find('.page').addClass('border_org').parents('.page_box').siblings().find('.page').removeClass('border_org');
        });
    });

    $(document).on('change', 'select', function (e) {
        var thisVal = $(e.target).val();
        var _thisVal;
        var one = false;
        var smallVal = '';
        var bigVal = '';
        var gg = 15;
        $(e.target).parents('.page_box').attr('name', thisVal);
        $(e.target).find("option[value=" + thisVal + "]").attr("selected", true).siblings().removeAttr('selected');
        $(e.target).parents('.page_box').siblings().each(function (j, item) {
            _thisVal = $(this).attr('name');
            if (_thisVal == thisVal) {
                return one = true;
            }
        });
        if (one) {
            var outHtml = $(e.target).parents('.page_box')[0].outerHTML;
            var thisPage = $('.page_box[name=' + thisVal + ']');
            var thisLength = thisPage.length;
            thisPage.each(function (i, elem) {
                if (thisLength > 2) {
                    if (i == thisLength - 2) {
                        $(elem).after(outHtml);
                        return false;
                    }
                } else {
                    if (i == thisLength - 1) {
                        $(elem).after(outHtml);
                        return false;
                    }
                }
            });
            $(e.target).parents('.page_box').remove();
            //缩略图点击选中效果
            $('.page_box').each(function () {
                $(this).click(function () {
                    $(this).find('.selectCont').addClass('after').parents('.page_box').siblings().find('.selectCont').removeClass('after');
                    $(this).find('select').addClass('active').parents('.page_box').siblings().find('select').removeClass('active');
                    $(this).find('.page').addClass('border_org').parents('.page_box').siblings().find('.page').removeClass('border_org');
                });
            });
            return false;
        } else {
            $(e.target).parents('.page_box').siblings().each(function (j, item) {
                var newVal = $(item).attr('name');
                if (thisVal > newVal) {
                    if (newVal > bigVal) {
                        bigVal = newVal;
                    }
                }
            });
            var out = $(e.target).parents('.page_box')[0].outerHTML;
            var page_box = $('.page_box[name=' + bigVal + ']');
            page_box.eq(page_box.length - 1).after(out);
            $(e.target).parents('.page_box').remove();
            //缩略图点击选中效果
            $('.page_box').each(function () {
                $(this).click(function () {
                    $(this).find('.selectCont').addClass('after').parents('.page_box').siblings().find('.selectCont').removeClass('after');
                    $(this).find('select').addClass('active').parents('.page_box').siblings().find('select').removeClass('active');
                    $(this).find('.page').addClass('border_org').parents('.page_box').siblings().find('.page').removeClass('border_org');
                });
            });
            setTimeout(function () {
                $(".left .page_box").each(function (index, el) {
                    $(this).find(".page").attr('pageNum', index + 1);
                });
            }, 10);
        }
    });
    //左侧拖拽释放后初始化下标
    _left.on('mouseup', '.page_box', function (event) {
        setTimeout(function () {
            $(".left .page_box").each(function (index, el) {
                $(this).find(".page").attr('pageNum', index + 1);
            });
        }, 10);
    });

    //点击切换画布
    _left.on('click', '.page_box .page', function (event) {
        relId++;
        imgW = $('.editor_box:visible').find('img').width();
        imgH = $('.editor_box:visible').find('img').height();
        var dragresize = new DragResize('dragresize',
            {minWidth: 20, minHeight: 10, minLeft: 0, minTop: 0});
        var editor_box = $(".editor_box[pageIndex='" + $(this).attr("pageIndex") + "']");
        editor_box.show().addClass('show').siblings('.editor_box').hide().removeClass('show');
        restIndex = 0;
        bigNum = 0;
        newNum = 0;
    });

    //图形拖动和改变大小的代码引用
    imgW = $('.editor_box:visible').find('img').width() / 2;
    imgH = $('.editor_box:visible').find('img').height() / 2;
    var dragresize = new DragResize('dragresize',
        {minWidth: 20, minHeight: 10, minLeft: 0, minTop: 0});

    dragresize.isElement = function (elm) {
        if (elm.className && elm.className.indexOf('drsElement') > -1) return true;
    };
    dragresize.isHandle = function (elm) {
        if (elm.className && elm.className.indexOf('drsMoveHandle') > -1) return true;
    };

    dragresize.ondragfocus = function () {
        sessionStorage.setItem('id', $(this)[0].element.id);
    };
    dragresize.ondragstart = function (isResize) {
        sessionStorage.setItem('id', $(this)[0].element.id);
        $('input').blur();
        $('#' + $(this)[0].element.id).click();
    };
    dragresize.ondragmove = function (isResize) {
    };

//        改变图形区域后执行
    dragresize.ondragend = function (isResize) {
    };
    dragresize.ondragblur = function () {
    };
    dragresize.apply(document);


//         以下为鼠标画图形区域的代码（勿动）
    var index = 0;
    var startX = 0, startY = 0;
    var circular = false;
    var leftW = parseInt(_left.width()) + 15;
    var leftH = 65;
    var marginLeft = parseInt($('body').css('marginLeft'));
    var isDown = false;
    var isMove = false;
    var isUp = false;
    var isOutImg = false;
    var inputDown = false;
    var diffX, diffY, scroll;
    // 是否拖动，初始为 false
    var dragging = false;
    $('.shapeChoose .rectangle').click(function (e) {
        e.stopPropagation();
        $(this).css('background', 'red');
        $('.circular').css('background', '#707070');
        $('.editor_box').css('cursor', 'crosshair');
        circular = false;
//          isCheck = true;
    });
    $('.circular').click(function (e) {
        e.stopPropagation();
        $(this).css('background', 'red');
        $('.rectangle').css('background', '#707070');
        $('.editor_box').css('cursor', 'crosshair');
        circular = true;
//          isCheck = true;
    });
    $('.move').click(function () {


    });

    // 鼠标按下
    var boxA = '';
    $('.right').on('mousedown', '.bigImgCont', function (e) {
        inputDown = false;
        if ($(e.target).hasClass('drsElement')) {
            sessionStorage.setItem('id', $(e.target).attr('id'));
        } else {
            sessionStorage.setItem('id', $(e.target).parents('.drsElement').attr('id'));
        }
        if (e.target.className.match(/number/)) {
            $(e.target).focus();
            dragging = true;
            return;
        }
        if (e.which == 1) {
            var scrollLeft = $('.bigImgCont').scrollLeft();
            isDown = true;
            isUp = false;
            startX = e.pageX;
            startY = e.pageY;
            length = $('.bigImgCont').children('div').length;
            // 如果鼠标在 box 上被按下
            if (e.target.className.match(/box/) ||
                $(e.target).hasClass('drsElement') ||
                e.target.className.match(/yinPing/) ||
                e.target.className.match(/audioLabel/) ||
                e.target.innerHTML.match(/音/) ||
                e.target.innerHTML.match(/注释/)) {

                // 允许拖动
                dragging = true;
                // 设置当前 box 的 id 为 moving_box
//                         if(document.getElementById("moving_box") !== null) {
//                             document.getElementById("moving_box").removeAttribute("id");
//                         }
//                         e.target.id = "moving_box";
                // 计算坐标差值
                var id = sessionStorage['id'];
                if ($(e.target).hasClass('sameType') || $(e.target).hasClass('sameTypeTow') || $(e.target).hasClass('number') || $(e.target).hasClass('xuHao')) {
                    diffX = startX - parseInt($('#' + id).css('left')) - leftW;
                    diffY = startY - parseInt($('#' + id).css('top')) - leftH;
                    return;
                }
//                         if($(e.target).hasClass('sameTypeTow')){
//                         	diffX = startX - parseInt($('#'+id).css('left'))- leftW;
//                         	diffY = startY - parseInt($('#'+id).css('top')) - leftH;
//                         	return;
//                         }
//                         if($(e.target).hasClass('number')){
//                         	diffX = startX - parseInt($('#'+id).css('left'))- leftW;
//                         	diffY = startY - parseInt($('#'+id).css('top')) - leftH;
//                         	return;
//                         }
                else {
                    diffX = startX - e.target.offsetLeft - leftW;
                    diffY = startY - e.target.offsetTop - leftH;
                    return;
                }
            }
            if ($(e.target).hasClass('dragresize')) {
//                     console.log('dragresize')
            } else {
                // 在页面创建 box
                if ($('.editor_box:visible').scrollLeft() != 0 && $('.editor_box:visible').scrollLeft() != '') {
                    scroll = $('.editor_box:visible').scrollLeft();
                } else {
                    scroll = 0;
                }
                var active_box = document.createElement("div");
                if ($('.drsElement')) {
                    $('.editor_box').children('.bigImgCont').children('div').each(function () {
                        if (parseInt($(this).attr('id').split('x')[1]) > relId) {
                            relId = parseInt($(this).attr('id').split('x')[1]);
                        }
                    })
                } else {
                    $('.editor_box:visible').children('.bigImgCont').children('div').each(function () {
                        if (parseInt($(this).attr('id').split('x')[1]) > relId) {
                            relId = parseInt($(this).attr('id').split('x')[1]);
                        }
                    })
                }
                var pageIndex = $('.editor_box:visible').index();
                active_box.id = "active_box" + parseInt(relId ? relId + 1 : length + 1) + "";
//                        active_box.id = "active_box"+parseInt(length?length+1:index+1)+"";
                active_box.className = "box" + parseInt(length ? length + 1 : index + 1) + " drsElement";
                active_box.style.top = startY - leftH + 'px';
                active_box.style.left = startX - leftW - marginLeft + scrollLeft + 'px';
                if (circular) {
                    // active_box.style.borderRadius = '50%';
                    active_box.style.backgroundColor='transparent';
                    var div='<div class="circle-bg" ' +
                        'style="position: absolute;top: 0;left: 0;width: 100%;height: 100%;' +
                        'z-index: 0;background-color: rgba(249, 2, 2, 0.45);border-radius: 50%;"></div>';
                    $(active_box).append(div);
                    active_box.setAttribute('type', 'circle');
                }
                $('.editor_box:visible').find('.bigImgCont').append(active_box);
//                        sessionStorage.setItem('id',"active_box"+parseInt(length?length+1:index+1)+"");
                dragging = false;
            }
            inputDown = false;
        }
        if (e.which == 3) {
            if (sessionStorage['id'] && sessionStorage['id'] != "undefined" && isUp) {
                var _this = $('#' + sessionStorage['id'] + '');
                var _thisLeft = e.clientX;
                var _thisTop = e.clientY;
                var i = 0;
                $('.rightClick').css('left', _thisLeft);
                $('.rightClick').css('top', _thisTop);
                $('.rightClick').show();
                $('.rightClickSet').unbind('click').click(function () {
                    i++;
                    var valueWidth = parseInt(_this.css('width'));
                    var valueHeight = parseInt(_this.css('height'));
                    var _html = '<div>' +
                        '<label><span>长：</span><input id="long" style="width:50px" type="number" value="' + valueWidth + '" /></label>' +
                        '<label style="margin-left:60px"><span>宽：</span><input id="height" style="width:50px" type="number" value="' + valueHeight + '" /></label>' +
                        '</div>';
                    layer.confirm(_html, {
                        btn: ['应用', '确定'] //按钮
                    }, function () {
                        var _thisLong = $('#long').val();
                        var _thisWidth = $('#height').val();
                        _this.css({
                            'width': _thisLong,
                            'height': _thisWidth
                        });
                    }, function () {
                        var _thisLong = $('#long').val();
                        var _thisWidth = $('#height').val();
                        _this.css({
                            'width': _thisLong,
                            'height': _thisWidth
                        });
                    });
                });
            }
        }
    });


    // 鼠标移动
    $('.right').on('mousemove', '.bigImgCont', function (e) {

//            if(isCheck){}
        // 更新 box 尺寸
        if (isDown) {
            if (!dragging) {
                var scrollLeft = $('.bigImgCont').scrollLeft();
                var scrollTop = $('.bigImgCont').scrollTop();
                var ab = document.getElementById("active_box" + parseInt(relId ? relId + 1 : length + 1) + "");
                $(ab).width("" + e.pageX - startX + "px");
                $(ab).height("" + e.pageY - startY + "px");
                var bodyMarginLeft = parseInt($('body').css('marginLeft'));
                if (e.pageX <= startX) {
                    $(ab).width("" + startX - e.pageX + "px");
                    $(ab).height("" + startY - e.pageY + "px");
                    $(ab).css("left", "" + e.pageX - leftW - bodyMarginLeft + scrollLeft + "px");
                    $(ab).css("top", "" + e.pageY - leftH + scrollTop + "px");
                }
                if (e.pageY <= startY && e.pageX >= startX) {
                    $(ab).width("" + e.pageX - startX + "px");
                    $(ab).height("" + startY - e.pageY + "px");
                    $(ab).css("left", "" + startX - leftW - bodyMarginLeft + scrollLeft + "px");
                    $(ab).css("top", "" + e.pageY - leftH + scrollTop + "px");
                }
                if (e.pageY >= startY && e.pageX <= startX) {
                    $(ab).width("" + startX - e.pageX + "px");
                    $(ab).height("" + e.pageY - startY + "px");
                    $(ab).css("left", "" + e.pageX - leftW - bodyMarginLeft + scrollLeft + "px");
                    $(ab).css("top", "" + startY - leftH + scrollTop + "px");
                }
            } else {
                var mb = document.getElementById(sessionStorage['id']);
                $(mb).css("left", "" + e.pageX - diffX - leftW + "px");
                $(mb).css("top", "" + e.pageY - diffY - leftH + "px");
                if (parseInt(mb.style.left) >= (parseInt($('.editor_box:visible').find('img').width()) - parseInt(mb.style.width))) {
                    $(mb).css('left', parseInt($('.editor_box:visible').find('img').width()) - parseInt(mb.style.width));
                }

                if (parseInt(mb.style.top) >= (parseInt($('.editor_box:visible').find('img').height()) - parseInt(mb.style.height))) {
                    $(mb).css('top', parseInt($('.editor_box:visible').find('img').height()) - parseInt(mb.style.height));
                }

                if (parseInt(mb.style.left) <= 0) {
                    $(mb).css('left', 0);
                }

                if (parseInt(mb.style.top) <= 0) {
                    $(mb).css('top', 0);
                }
            }
        }
        // 移动，更新 box 坐标
    });

//        鼠标在图片区域外抬起
//        $('.right').on('mouseup','.editor_box',function(e) {
    $(document).on('mouseup', function (e) {
        var ab = document.getElementById("active_box" + parseInt(relId ? relId + 1 : length + 1) + "");
        if (isDown) {
            if (!dragging) {
                // 禁止拖动

                // 如果长宽均小于 3px，移除 box
                if ($(ab).width() < 20 || $(ab).height() < 10) {
                    $(ab).remove();
                    index--;
                    restIndex--;
                }
                $.each($('.editor_box:visible .number'), function (i, elem) {
                    if ($(this).val() > bigNum) {
                        bigNum = parseInt($(this).val());
                    }
                });
                if (bigNum >= restIndex) {
                    newNum = bigNum + 1;
                } else {
                    newNum = restIndex;
                }
                $(ab).html('<div class="bar">' +
                    '<li class="yinPing sameType flex"><label class="sameType audioLabel"><span class="sameType">音</span><input type="text" class="ds_n" onclick="ab(this)"/></label></li>' +
                    '<li boxIndex="' + newNum + '" class="xuHao"><input type="text" name="number" class="number" onkeyup="checkNum(this)" onfocus="remeberValue(this)" onblur="changeCount(this)" value="' + newNum + '" /></li>' +
                    '</div>');
                index++;
                restIndex++;
                isOutImg = false;

                if ($(e.target).hasClass('drsElement')) {
                    sessionStorage.setItem('id', $(e.target).attr('id'));
                    $(e.target).find('.yinPing').css('display', 'block');
                }

                var _bigImgCont = $('.bigImgCont');
                var _leftL = Number(_bigImgCont[0].parentElement.offsetLeft);
                var _leftT = Number(_bigImgCont[0].parentElement.offsetTop);
                var _this = $(e.target);

                if (e.target.className && $(e.target).hasClass('drsElement')) {
                    dragresize.elmX = parseInt(_this.css('left'));
                    dragresize.elmY = parseInt(_this.css('top'));
                }

                if (e.target.className && $(e.target).hasClass('xuHao')) {
                    dragresize.elmX = parseInt(_this.parents('.drsElement').css('left'));
                    dragresize.elmY = parseInt(_this.parents('.drsElement').css('top'));
                }

                if (e.target.className && $(e.target).hasClass('sameType')) {
                    dragresize.elmX = parseInt(_this.parents('.drsElement').css('left'));
                    dragresize.elmY = parseInt(_this.parents('.drsElement').css('top'));
                }
            }
        }
        if (parseInt($(ab).css('left') < 0) || parseInt($(ab).css('top') < 0)) {
            $(ab).css({
                'left': 0,
                'top': 0
            })
        }
        isDown = false;
        isUp = true;
    });

    // 鼠标抬起
    $('.right').on('mouseup', '.bigImgCont', function (e) {
        // console.log(e.target)
        // console.log(e)
        var ab = document.getElementById("active_box" + parseInt(relId ? relId + 1 : length + 1) + "");
        var boxA = "active_box" + parseInt(relId ? relId + 1 : length + 1);
        console.log(isDown);
        if (isDown) {
            // 禁止拖动

            // 如果长宽均小于 3px，移除 box
            if (($(ab).width() < 20 || $(ab).height() < 10) && !$(e.target).hasClass('drsElement')) {
                $(ab).remove();
                index--;
                restIndex--;
                isDown = false;
                isUp = true;
                return false;
                // 	console.log(222);

            }
            if ($(e.target).hasClass('bar') || $(e.target).hasClass('yinPing') || $(e.target).hasClass('audioLabel') || $(e.target).hasClass('textA') || $(e.target).hasClass('sameType') || $(e.target).hasClass('xuHao')) {
                isDown = false;
                isUp = true;
                return false;
            }
            if (!dragging) {
                $(ab).removeClass('drsElement').addClass('drsElement');
                $.each($('.editor_box:visible .number'), function (i, elem) {
                    if ($(this).val() > bigNum) {
                        bigNum = parseInt($(this).val());
                    }
                });
                if (bigNum >= restIndex) {
                    newNum = bigNum + 1;
                } else {
                    newNum = restIndex;
                }
                $(ab).html('<div class="bar" >' +
                    '<li class="yinPing sameType flex"><label class="sameType audioLabel" ><span class="sameType spanStyle">音</span> <span class="textImg-span spanStyle" style="display: none;" >文</span><input type="text" class="ds_n" onclick="ab(this)"  text="" time="" /></label></li>' +
                    '<li boxIndex="' + newNum + '" class="xuHao"><input type="text" name="number" class="number" onkeyup="checkNum(this)" onfocus="remeberValue(this)" onblur="changeCount(this)" value="' + newNum + '" /></li>' +
                    '</div>');
                index++;
                restIndex++;
                console.log(boxA)
                aa(boxA);
            }

            if ($(e.target).hasClass('drsElement')) {
                $(e.target).attr('type')==='circle' &&
                $(e.target).css({

                    backgroundColor:function(){
                       var a= $(e.target).attr('id').find('.yinPing').attr('audioname');
                        if(isEmpty(a)){
                            return 'rgba(249, 2, 2, 0.45)';
                        }
                        return 'rgba(1, 73, 162, 0.45)';

                    },
                    'border-radius':'50%'
                });
                sessionStorage.setItem('id', $(e.target).attr('id'));
                $(e.target).find('.yinPing').css('display', 'block');
            }

            var _bigImgCont = $('.bigImgCont');
            var _leftL = Number(_bigImgCont[0].parentElement.offsetLeft);
            var _leftT = Number(_bigImgCont[0].parentElement.offsetTop);
            var _this = $(e.target);

            if (e.target.className && $(e.target).hasClass('drsElement')) {
                dragresize.elmX = parseInt(_this.css('left'));
                dragresize.elmY = parseInt(_this.css('top'));
            }

            if (e.target.className && $(e.target).hasClass('xuHao')) {
                dragresize.elmX = parseInt(_this.parents('.drsElement').css('left'));
                dragresize.elmY = parseInt(_this.parents('.drsElement').css('top'));
            }

            if (e.target.className && $(e.target).hasClass('sameType')) {
                dragresize.elmX = parseInt(_this.parents('.drsElement').css('left'));
                dragresize.elmY = parseInt(_this.parents('.drsElement').css('top'));
            }
            debugger
            isDown = false;
            isUp = true;
        }
        if (parseInt($(ab).css('left') < 0) || parseInt($(ab).css('top') < 0)) {
            $(ab).css({
                'left': 0,
                'top': 0
            })
        }


    });

    var _ = function (id) {
        return document.getElementById(id);
    };

    $('.drsElement').click(function () {
        $(this).find('.putCont').addClass('show');
    });

    $(document).keydown(function (e) {
        if (e.keyCode == 46) {
            if (sessionStorage['id'] && sessionStorage['id'] != "undefined") {
                if (e.target.className.match(/number/)) {
                    $(e.target).val('');
                    return;
                }
                if ($('.bigImgCont div').length > 0) {
                    var id = sessionStorage.getItem('id');
                    var _this = $('#' + id);
                    var _innerHtml = _this.get(0).outerHTML;
                    undoList.push(_innerHtml);
                    _this.remove();
                }
            }
        }
        if (e.target.className.match(/number/)) {
            return;
        } else {
            if (sessionStorage['id']) {
                var sessionId = sessionStorage['id'];
                var _thisLeft = parseInt($('#' + sessionId + '').css('left'));
                var _thisTop = parseInt($('#' + sessionId + '').css('top'));
                if (e.keyCode == 37) {
                    $('#' + sessionId + '').css('left', '' + parseInt(_thisLeft - 1) + 'px');
                }
                if (e.keyCode == 38) {
                    $('#' + sessionId + '').css('top', '' + parseInt(_thisTop - 1) + 'px');
                }
                if (e.keyCode == 39) {
                    $('#' + sessionId + '').css('left', '' + parseInt(_thisLeft + 1) + 'px');
                }
                if (e.keyCode == 40) {
                    $('#' + sessionId + '').css('top', '' + parseInt(_thisTop + 1) + 'px');
                }
                if (parseInt($('#' + sessionId + '').css('left')) >= (parseInt($('.editor_box:visible').find('img').width()) - parseInt($('#' + sessionId + '').css('width')))) {
                    $('#' + sessionId + '').css('left', parseInt($('.editor_box:visible').find('img').width()) - parseInt($('#' + sessionId + '').css('width')));
                }

                if (parseInt($('#' + sessionId + '').css('top')) >= (parseInt($('.editor_box:visible').find('img').height()) - parseInt($('#' + sessionId + '').css('height')))) {
                    $('#' + sessionId + '').css('top', parseInt($('.editor_box:visible').find('img').height()) - parseInt($('#' + sessionId + '').css('height')));
                }

                if (parseInt($('#' + sessionId + '').css('left')) <= 0) {
                    $('#' + sessionId + '').css('left', 0);
                }
                if (parseInt($('#' + sessionId + '').css('top')) <= 0) {
                    $('#' + sessionId + '').css('top', 0);
                }
            }
        }
    });
    $(document).on('contextmenu', '.bigImgCont', function () {
        return false;
    });

    $(document).keydown(function (e) {
        isZ = false;
        var evet = e.keyCode;
        if (evet == 90) {
            isZ = true;
            var newList = undoList.pop();
            $('.editor_box:visible').find('.bigImgCont').append(newList);
        }

    });
    $(document).keyup(function (e) {
        if (e.keyCode == 17) {
            isZ = false;
        }
    });
    $(document).on('mouseenter', '.drsElement', function () {
        if ($(this).find('.yinPing').attr('audioname') == '' || $(this).find('.yinPing').attr('audioname') == undefined) {
            $(this).find('.yinPing').stop().fadeIn();
        } else {
            $(this).find('.yinPing').css('display', 'block');
        }
    }).on('mouseleave', '.drsElement', function () {
        if ($(this).find('.yinPing').attr('audioname') == '' || $(this).find('.yinPing').attr('audioname') == undefined) {
            $(this).find('.yinPing').stop().fadeOut();
        } else {
            $(this).find('.yinPing').css('display', 'block');
        }
    });
//      页面刷新/后退/关闭提示保存
    var unloadPageTip = function () {
        return "您编辑的文章内容还没有进行保存!";
    };
    window.onbeforeunload = unloadPageTip;

});
