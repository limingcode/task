/**
 * Created by Administrator on 2017/8/24.
 */
var undoList = [];
    function createNewPpt() {            //鏂板缓骞荤伅鐗�
        var $listImg = '';
        var $leftPage_box = $(".left .page_box");
        var pageBox_length = $leftPage_box.length + 1;
        var text_1 = '';
        if (sessionStorage['listImg']) {
            $listImg = sessionStorage.getItem('listImg');
            text_1 = '<div class="editor_box hide" name=' + pageBox_length + ' style="display: none;background: url(' + $listImg + ') center center / 100% 100% no-repeat;"></div>';

        }
        else {
            $listImg = ' ';
            text_1 = '<div class="editor_box hide" name=' + pageBox_length + ' style="display: none;"></div>';
        }
        var text = '<li class="page_box">' +
            '<i></i>' +
            '<span class="page_index">' + pageBox_length + '</span>' +
            '<span class="closePPT">脳</span>' +
            '<div class="page" name=' + pageBox_length + '><img src="' + $listImg + '" alt="" /></div> ' +
            '</li>';
        // text:宸︿晶缂╃暐鍥�    text_1:涓棿鍐呭鍖哄潡
        var $textCont = $('.wrap ul');
        var $txtCont_1 = $('.right');
        $txtCont_1.append(text_1);
        $textCont.append(text);
        $('.page[name="' + pageBox_length + '"]').addClass('border_org').parent('.page_box').siblings('.page_box').find('.page').removeClass('border_org');
        $(".editor_box[name='" + pageBox_length + "']").show().attr('id', 'domImg').siblings('.editor_box').hide().attr('id', '');
        var type = '鏂囨湰妗�';
        var $thisName = 'box' + newText + '';
        var $thisName1 = 'box110' + newText + '';
        var newText1 = "<div style='left: 6%;top: 7%;width: 85%;height: 24%;' class='module' className='box" + newText + "' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
        var newText2 = "<div style='left: 6%;top: 47%;width: 85%;height: 24%;' class='module' className='box110" + newText + "' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
        E.insert(newText1, type, $thisName);
        E.insert(newText2, type, $thisName1);
        //璁╂柊寤虹殑骞荤伅鐗囩粦瀹氬垹闄ゅ嚱鏁�
        $('.deletePpt').on('click', function () {
            var $thisIndex = $(this).siblings('.page').attr('name');
            $(this).parent('.page_box').remove();

            $(".left .page_box").each(function (index, el) {
                $(this).find(".page_index").html(index + 1);
            });

            $.each($('.editor_box'), function (i, elem) {
                if ($(elem).attr('name') == $thisIndex) {
                    $(elem).remove();
                }
            })
        });
        //宸︿晶閫変腑鏇存敼杈规棰滆壊
        $leftPage_box.each(function (i, elem) {
            $(this).on('click', function () {
                $(this).find('.page').addClass('border_org');
                $(this).siblings().find('.page').removeClass('border_org');
            });
        });
    }
$(function () {
    var $html= $('html');
    var $ppt = $('.ppt');
    var $header = $('.header');
    var $left = $('.left');
    var $rightCont = $('.right');
    var contHeight = $ppt.height()-$header.height();
    var contWidth = $ppt.width()-$left.width();
    var pptContWidth = $rightCont.width();
    var rem = pptContWidth / 10;
    $html.css('font-size',rem+'px');
    $rightCont.height($rightCont.width()*9/16+'px');
    $rightCont.css('margin-top',(contHeight-$rightCont.height())/2-15+'px');
    if($rightCont.height()>contHeight){
        sessionStorage.setItem('small',true);
        $rightCont.height(contHeight-'30'+'px');
        $rightCont.css('max-width',contHeight*16/9+'px');
        $rightCont.css({
            marginTop:0,
            marginLeft:$left.width()+(contWidth-$rightCont.width())/2+'px'
        });
    }
});

//function bindCreateElem(elem){
//    var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
//    // 閫夋嫨鐩爣鑺傜偣
//    var target = elem;
//    // 鍒涘缓瑙傚療鑰呭璞�
//    var observer = new MutationObserver(function(mutations) {
//        mutations.forEach(function(mutation) {
//        });
//    });
//    // 閰嶇疆瑙傚療閫夐」:
//    var config = { attributes: true, childList: true, characterData: true };
//
//    // 浼犲叆鐩爣鑺傜偣鍜岃瀵熼�夐」
//    observer.observe(target, config);
//};

$(function () {
    var title = $('.editor_box:visible');
    $('.tabs_cnt').on('click','a[name="undo"]', function () {
        var oldHtml = undoList.pop();
        //delete undoList[0]
        if($('.module[className="'+oldHtml.thisClass+'"]')){
            $('.module[className="'+oldHtml.thisClass+'"]').remove();
        }
        E.insert(oldHtml.outHtml,oldHtml.type,oldHtml.thisClass);
    });
});


