/*
* @Author: Hlin
* @Date:   2017-11-15 15:23:47
* @Last Modified by:   Hlin
* @Last Modified time: 2017-11-15 15:35:01
*/
$(".result").on('click', '.result_item a', function(event) {
    if ($(this).hasClass('active')) {
        $(this).removeClass("active");
    }else{
        $(this).addClass('active').parent().siblings('li').find('a').removeClass('active');
    }
});