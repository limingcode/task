/*
* @Author: Hlin
* @Date:   2017-06-26 10:58:33
*/
$(".wa_table").on('click', '.show_list .show_btn', function(event) {
    $(this).parents('tr').next().toggle();
});