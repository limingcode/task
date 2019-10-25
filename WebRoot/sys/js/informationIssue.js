/*
* @Author: Hlin
* @Date:   2017-06-08 09:45:40
*/
// 作业发布日期选择
$(function () {
    $.jeDate('#issue_time',{
        format:"YYYY年MM月DD日 hh:mm:ss",
        minDate:$.nowDate({DD:0})
    })
});
//定时发布
$(".sky_checkbox").on('change', 'input', function(event) {
    if ($(this).prop("checked")) {
        $(this).parent().siblings('.date_select').css("visibility","visible");
    }else{
        $(this).parent().siblings('.date_select').css("visibility","hidden");
    }
});