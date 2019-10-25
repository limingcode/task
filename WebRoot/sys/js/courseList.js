/*
* @Author: Hlin
* @Date:   2017-06-12 16:02:32
*/
$.jeDate('.start_time input',{
    format:"YYYY年MM月DD日 hh:mm",
    minDate:$.nowDate({DD:0}),
    choosefun:function(elem, val, date) {
        var id = $(elem).parent().parent().attr("name");
        var time = val;
        updateLesson(id,time);
    },
    okfun:function(elem, val, date) {
        var id = $(elem).parent().parent().attr("name");
        var time = val;
        updateLesson(id,time);
    }
});
/*// 添加课程
$(".add_course").click(function(event) {
    $(".course_list .wa_table tbody").append('<tr>'+
                    '<td>'+(Number($(".course_list .wa_table tbody tr").length)+1)+'</td>'+
                    '<td>2017年5月26日</td>'+
                    '<td class="start_time">'+
                    '<input class="date_select" type="text" placeholder=""  readonly value="2017年6月12日">'+
                    '</td>'+
                    '<td><a href="examList.html">详情</a></td>'+
                '</tr>');

    $.jeDate('.start_time input',{
        format:"YYYY年MM月DD日",
        minDate:$.nowDate({DD:0}),
        choosefun:function(elem, val, date) {
            // $(elem).parent().html($(elem).val());
            // $(alterBtnThis).addClass('alter_btn');
        }
    })
});*/


function updateLesson(id,time){
	 $.ajax({
         url : "/task/lesson/updateLesson.jhtml",
         type : "post",
         data : {
             lessonId : id,
             openTime : time
         },
         dataType : 'JSON',
         success : function(result) {
             return true;
         },
     });
}