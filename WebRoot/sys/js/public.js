/*
* @Author: Hlin
* @Date:   2017-07-05 10:23:02
*/
// 恢复筛选信息
var filterList = ['dept','grade','subject','cate','period','course','category'];
var filterInput;
var listEl;
$.each(filterList,function(index, el) {
    filterInput = $('.filter_list .sky_radio input[name="'+el+'"]');
    if (!filterInput.length) {
        return;
    }
    listEl = el;
    $('.filter_list .sky_radio').each(function(index, el) {
        if ($(this).text()==localStorage.getItem(listEl)) {
            $(this).find('input').prop("checked",true);
        }
    });
});
// 记录筛选信息
$('.filter_list').on('change', '.sky_radio input', function(event) {
    switch($(this).attr("name"))
    {
        case "dept":
            localStorage.setItem("dept",$(this).parent().text())
            break;
        case "grade":
            localStorage.setItem("grade",$(this).parent().text())
            break;
        case "subject":
            localStorage.setItem("subject",$(this).parent().text())
            break;
        case "cate":
            localStorage.setItem("cate",$(this).parent().text())
            break;
        case "period":
            localStorage.setItem("period",$(this).parent().text())
            break;
        case "course":
            localStorage.setItem("course",$(this).parent().text())
            break;
        case "category":
            localStorage.setItem("category",$(this).parent().text())
            break;
    }
});

window.onload = function(){
    sessionStorage.setItem("iframeSrc", self.location.href);
}

// 初始化layer自定义皮肤
window.layer && layer.config({
  extend: 'sky-skin/style.css' //加载您的扩展样式
});