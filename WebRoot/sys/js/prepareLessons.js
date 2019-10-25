/**
 * Created by Administrator on 2017/7/28.
 */
$(function(){
    $('.prepareLessons li').mouseenter(function(e){
        var _this = $(this);
        _this.addClass('rubberBand'+'  '+'animated')
        setTimeout(function () {
            _this .removeClass('rubberBand');
        },1000)
    });
});