/*
* @Author: Hlin
* @Date:   2017-08-22 18:23:32
* @Last Modified by:   Hlin
* @Last Modified time: 2017-08-23 09:49:02
*/
// 考勤状态切换
$(".kaoqin .student_list").on('click', 'li', function(event) {
    if ($(this).attr("state") == "1") {
        $(this).attr("state","2");
    }else{
        $(this).attr("state","1");
    }
});
// 点击最后一个学生触发下一页事件(出最后一排的最后一个学生)
$(".kaoqin .student_list:not(:last)").on('click', 'li:last', function(event) {
    var wthis = $(this);
    setTimeout(function(){
        wthis.parents('.student_list').hide().next(".student_list").removeClass('hide').addClass('show')
    }, 1000);
});
// 点击最后一排的最后一个学生触发事件
$(".kaoqin .student_list:last").on('click', 'li:last', function(event) {
});