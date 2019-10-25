/*
* @Author: Hlin
* @Date:   2017-05-04 00:04:45
*/
$(".iframe").attr("src",sessionStorage.getItem("iframeSrc") != null?sessionStorage.getItem("iframeSrc"):$(".nav:first a").attr("href"));
$(".left div").eq(sessionStorage.getItem("liActive")).addClass('active');
// 导航栏点击跳转
$(".left").on('click', 'div', function(event) {
    //储存导航栏状态
    // sessionStorage.setItem("liParentActive", $(this).parent().parent().index());
    msgCont = false;
    var index = $(this).index();
    if(index===3){
        isShow = true;
        allMsgNum = 0;
        $('.chat-message-num').hide();
    }
    else{
        isShow = false;
        $('.chat-message-num').show();
    }

    sessionStorage.setItem("liActive", $(this).index());

    $(this).addClass('active').siblings('div').removeClass('active');
    // $(this).parent().parent().siblings('.nav').find("li").removeClass('active');
    if ($(this).find("a").attr("href")) {
        $(".right iframe").attr("src",$(this).find("a").attr("href"));
    }else{
        $(".right iframe").attr("src","404.html");
    }
    return false;
});

//监控iframe属性变化
var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
// 选择目标节点
var target = document.querySelector('.iframe');
// 创建观察者对象
var observer = new MutationObserver(function(mutations) {
  mutations.forEach(function(mutation) {
    sessionStorage.setItem("iframeSrc", mutation.target.src);
  });
});
// 配置观察选项:
var config = { attributes: true}
// 传入目标节点和观察选项
observer.observe(target, config);

// 用户操作菜单时清除缓存
$(".r_menu_list").on('click', 'li', function(event){
    sessionStorage.removeItem("iframeSrc");
    // sessionStorage.removeItem("liParentActive");
    sessionStorage.removeItem("liActive");
});