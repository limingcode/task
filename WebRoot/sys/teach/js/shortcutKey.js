/**
 * Created by Administrator on 2017/8/15.
 */
$(function () {
    var range, a,dom;
    var toBig = false;           //切换字母大小写   （shift+F6）

    $(document).keydown(function(event){
        if(event.keyCode==16){
            toBig = true;
        }
        if(toBig&&event.keyCode==117){
            range = window.getSelection().getRangeAt(0);
            a = window.getSelection().toString();
            dom = document.createElement('span');
            range.extractContents();
            dom.innerHTML = a;
            $(dom).css("text-transform", 'uppercase');
            range.insertNode(dom);
            toBig = false;
        }
        else if(!toBig&&event.keyCode==117){
            $(dom).css("text-transform", 'lowercase');
            range.insertNode(dom);
            toBig = true;
        }

    });



    //快捷键====>Ctrl+鼠标移动   复制当前拖动区块 （暂不支持多选复制）
    var textBoxIndex = 100;
    var w = false;
    var eventCoad = '';
    $(document).keydown(function(event){
        w = true;
        eventCoad = event.keyCode;
    });
    $(document).keyup(function(event){
        w = true;
        eventCoad = '';
    });
    var $thisClass,$thisHtml,$thisHtml2,$thisStyle,$thisStyleNew;
    $(document).on('mousedown','.module',function () {
        var e = '',type = '',$thisName = '',$thisAnimate = '',$thisType = '',$thisAnimateName = '',a1 = '',a3 = '',j = 1,arrPpt = [];
        $thisClass = $(this).attr('className');
        $thisStyle = $(this).attr('style');
        if(w&&eventCoad==17){
            if($(this).children('div').hasClass('text')&&$(this).children('svg').hasClass('icon')){
                $thisHtml = $(this).children('div').eq(0);
                $thisHtml2 = $(this).children('svg');
                e = "<div style='"+$thisStyle+"' class='module' className='boxNew"+textBoxIndex+"' >"+$thisHtml[0].outerHTML+$thisHtml2[0].outerHTML+"</div></div>"
                type = '形状占位符';
                $thisName = 'boxNew'+textBoxIndex+'';
                E.insert(e,type,$thisName);
                textBoxIndex++;
                return w = false;
            }
            if($(this).children('div').hasClass('text')){
                $thisHtml = $(this).children('div').eq(0);
                if($(this).hasClass('choose')){
                    a3 = '';
                    j = $('.animateList li').length;
                    $thisAnimate = $(this).attr('animationname');
                    $thisType = $('.'+$(this).attr('className')+'').attr('type');
                    $thisAnimateName = $('.'+$(this).attr('className')+'').find('.cnt').text();
                    arrPpt.push('boxNew'+textBoxIndex+'');
                    e = "<div animationname='"+$thisAnimate+"' style='"+$thisStyle+"' class='module choose' className='boxNew"+textBoxIndex+"' >"+$thisHtml[0].outerHTML+"</div></div>"
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
                }
                else{
                    e = "<div style='"+$thisStyle+"' class='module' className='boxNew"+textBoxIndex+"' >"+$thisHtml[0].outerHTML+"</div></div>"
                }
                type = '文本框占位符';
                $thisName = 'boxNew'+textBoxIndex+'';
                E.insert(e,type,$thisName);
                textBoxIndex++;
                return w = false;
            }
            else if($(this).hasClass('moduleImg')){
                $thisHtml = $(this).children('img');
                e = "<div style='"+$thisStyle+"' class='module moduleImg' className='boxNewIMg"+textBoxIndex+"' >"+$thisHtml[0].outerHTML+"</div></div>"
                type = '图片占位符';
                $thisName = 'boxNewIMg'+textBoxIndex+'';
                E.insert(e,type,$thisName);
                textBoxIndex++;
                return w = false;
            }
        }
        $('.module').on('dblclick', function (e) {
            $(this).find('.text').focus();
        })
    });

});


//2017 2.28 修改=====解决文本框无法复制bug；













