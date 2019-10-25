/*
* @Author: Hlin
* @Date:   2017-07-05 10:23:02
*/
// 恢复筛选信息
var filterList = ['school','grade','subject','level','term','course','qtype'];
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
        case "school":
            localStorage.setItem("school",$(this).parent().text())
            break;
        case "grade":
            localStorage.setItem("grade",$(this).parent().text())
            break;
        case "subject":
            localStorage.setItem("subject",$(this).parent().text())
            break;
        case "level":
            localStorage.setItem("level",$(this).parent().text())
            break;
        case "term":
            localStorage.setItem("term",$(this).parent().text())
            break;
        case "course":
            localStorage.setItem("course",$(this).parent().text())
            break;
        case "qtype":
            localStorage.setItem("qtype",$(this).parent().text())
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