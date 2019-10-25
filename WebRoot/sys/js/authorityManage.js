/*
* @Author: Hlin
* @Date:   2018-01-08 11:17:12
* @Last Modified by:   Hlin
* @Last Modified time: 2018-03-05 15:34:09
*/

// 表头浮动
var headerDom = $(".authority_box .wa_table thead")[0].outerHTML;
$(".authority_box .wa_table").append('<table class="wa_table authority_table_header hide">'+headerDom+'</table>')
$(window).scroll(function(event){
    if($(window).scrollTop() > $(".authority_box .wa_table")[0].offsetTop){
        $(".authority_table_header").removeClass('hide');
    }else{
        $(".authority_table_header").addClass('hide');
    }
});

$(".authority_box .wa_table tbody tr").on('click', 'td', function(event) {
    if ($(this).parent().hasClass('active')) {
        $(this).parent().removeClass('active');
    }else{
        $(this).parent().addClass('active').siblings('tr').removeClass('active');
    }
});