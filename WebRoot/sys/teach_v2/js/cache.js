/*
* @Author: Hlin
* @Date:   2017-06-20 14:02:39
*/
var $filter_list = $(".filter_list");
var $editor = $(".editor_box .editor");
var $q_box_item = $(".q_box .q_item");
var filterListCnt,
qBoxCnt = "",
editorCnt,
editorPosition,
questionAScore,
audioBox,
ananlyCnt,
questionTitle,
qusetionTime,
editorState;

//数据恢复
if (localStorage.getItem("filterListCnt")) {
    skyAlert.alert({
        content: "是否恢复页面数据？",
        cancel: function(){
            //清除缓存
            localStorage.removeItem("filterListCnt","qBoxCnt","editorCnt","editorPosition","audioBox","ananlyCnt","questionTitle","qusetionTime","questionAScore","editorState");
        },
        confirm: function(){
            $filter_list.html(localStorage.getItem("filterListCnt"));
            $(".q_box").html(localStorage.getItem("qBoxCnt"));
            $editor.html(localStorage.getItem("editorCnt"));
            $(".q_box .q_item[name='"+localStorage.getItem("editorPosition")+"']").after($(".editor_box"));
            $(".submit_item_btn").attr("name",localStorage.getItem("editorPosition"));
            $(".editor_box .ananly_cnt").val(localStorage.getItem("ananlyCnt"))
            $(".editor_box .audio_box").html(localStorage.getItem("audioBox"))
            $(".q_editor .title input").val(localStorage.getItem("questionTitle"))
            $(".editor_box .qusetion_time input").val(localStorage.getItem("qusetionTime"))
            $(".editor_box .add_question_a input").val(localStorage.getItem("questionAScore"))

            if (localStorage.getItem("editorState") == "1") {
                $(".editor_box").show();
            }else{
                $(".editor_box").hide();
            }

            //根据题型启动菜单
            editor.menus.menuC.disabled(true);
            editor.menus.menuE.disabled(true);
            editor.menus.menuL.disabled(true);
            editor.menus.menuJ.disabled(true);
            editor.menus.menuA.disabled(true);
            if (editor.$txt.find(".add_question_c").length) {
                editor.menus.menuC.disabled(false);
            }else if(editor.$txt.find(".add_question_e").length){
                editor.menus.menuE.disabled(false);
            }else if(editor.$txt.find(".add_question_l").length){
                editor.menus.menuL.disabled(false);
            }else if(editor.$txt.find(".add_question_j").length){
                editor.menus.menuJ.disabled(false);
            }else if(editor.$txt.parent().find(".add_question_a input").val()){
                editor.menus.menuA.disabled(false);
            }else{
                editor.enableMenusExcept();
            }

            //清除缓存
            localStorage.removeItem("filterListCnt","qBoxCnt","editorCnt","editorPosition","audioBox","ananlyCnt","questionTitle","qusetionTime","questionAScore","editorState");
        }
    })
}
//数据保存
function cacheSave(){

    filterListCnt = $filter_list.html();
    $(".q_box .q_item").each(function(index, el) {
        qBoxCnt += '<div class="'+$(this).attr("class")+'" name="'+$(this).attr("name")+'" id="'+$(this).attr("id")+'">'+$(this).html()+'</div>'
    });
    editorCnt = $editor.html();
    editorPosition = $(".submit_item_btn").attr("name");
    audioBox = $(".editor_box .audio_box").html();
    ananlyCnt = $(".editor_box .ananly_cnt").val();
    questionTitle = $(".q_editor .title input").val();
    qusetionTime = $(".editor_box .qusetion_time input").val();
    questionAScore = $(".add_question_a input").val();
    editorState = $(".editor_box").is(":hidden") == true ? 0 : 1;

    localStorage.setItem("filterListCnt",filterListCnt);
    localStorage.setItem("qBoxCnt",qBoxCnt);
    localStorage.setItem("editorCnt",editorCnt);
    localStorage.setItem("editorPosition",editorPosition);
    localStorage.setItem("audioBox",audioBox);
    localStorage.setItem("ananlyCnt",ananlyCnt);
    localStorage.setItem("questionTitle",questionTitle);
    localStorage.setItem("qusetionTime",qusetionTime);
    localStorage.setItem("questionAScore",questionAScore);
    localStorage.setItem("editorState",editorState);

    qBoxCnt = "";
}

// 处罚缓存事件
$("body").on('input', editor.$txt, function(event) {
    cacheSave();
});
$('.q_editor').on('input', '.title', function(event) {
    cacheSave();
});
$('.q_editor').on('click', '.submit_item_btn', function(event) {
    cacheSave();
});

// 筛选列表checked状态记录
$(".filter_list").on('change', 'input', function(event) {
    if ($(this).prop("checked")) {
        $(this).attr("checked","checked");
    }else{
        $(this).parents("li").siblings('li').find("input").removeAttr('checked');
    }
    cacheSave();
});

