/*
* @Author: Hlin
* @Date:   2017-06-11 09:27:50
*/

// 题目已选显示填写排序
var checkedList = new Array();
$(".choice_ql").on('click', '.sky_checkbox input', function(event) {
    if ($(this).prop("checked")) {
        $(this).parent().parent().next(".q_sort").find(" input").show();
    }else{
        var thisVal = $(this).parent().parent().next(".q_sort").find(" input").val();
        $(".choice_ql .sky_checkbox input:checked").parent().parent().next(".q_sort").find("input").each(function(index, el) {
            if (thisVal&&$(this).val()&&$(this).val() > thisVal) {
                $(this).val(function(){
                    return Number($(this).val())-1;
                });
            }
        });
        $(this).parent().parent().next(".q_sort").find(" input").val('').hide();
    }
});
$(".choice_ql").on('focusout', '.q_sort input', function(event) {
    var parentThis = this;
    if ($(this).val() > $(".choice_ql .sky_checkbox input:checked").length) {
        $(this).val($(".choice_ql .sky_checkbox input:checked").length);
        $(this).parent().parent().siblings('tr').find(".q_sort input").each(function(index, el) {
            if ($(this).val() == $(parentThis).val()) {
                $(this).val("");
            }
        });
    }else{
        $(this).parent().parent().siblings('tr').find(".q_sort input").each(function(index, el) {
            if ($(this).val() == $(parentThis).val()) {
                $(this).val("");
            }
        });
    }
});
$(".choice_ql .sky_checkbox input:checked").each(function(index, el) {
    $(this).parent().parent().parent().find(".q_sort input").show();
});
//全选按钮
var selectCount = 0;
var $selectDomList;
var $qSort;
$(".choice_ql .all_select").on('click', function(event) {
    $selectDomList = $(".choice_ql .sky_checkbox");
    $qSort = $(".choice_ql .q_sort input");
    $selectDomList.find("input:checked").each(function(index, el) {
        selectCount++;
    });
    if (selectCount == $selectDomList.find('input').length) {
        $selectDomList.find("input:checked").each(function(index, el) {
            $(this).prop("checked",false);
        });
        $qSort.hide();
        $qSort.each(function(index, el) {
            $(this).val("");
        });
    }else{
        $selectDomList.find("input").each(function(index, el) {
            $(this).prop("checked",true);
        });
        $qSort.show();
        $qSort.each(function(index, el) {
            $(this).val($qSort.length - index);
        });
    }
    selectCount = 0;
});
//排序按钮
$(".choice_ql .q_sort_btn").on('click', function(event) {
    $qSort = $(".choice_ql .q_sort input:visible");
    if ($($qSort[0]).val() > $($qSort[1]).val()) {
        $qSort.each(function(index, el) {
            $(this).val(index+1);
        });
    }else{
        $qSort.each(function(index, el) {
            $(this).val($qSort.length - index);
        });
    }
});