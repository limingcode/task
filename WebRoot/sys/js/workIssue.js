/*
* @Author: Hlin
* @Date:   2017-06-08 09:45:40
*/
// 作业发布日期选择
$(function () {
    $.jeDate('#start_time',{
        format:"YYYY年MM月DD日 hh:mm:ss",
        minDate:$.nowDate({DD:0})
    })
});