/**
 * app设置
 * @Author   Liang
 * @DateTime 2018-02-06
 */
$(function(){
    // 轮播
    var bannerSwiper = new Swiper('.acti_phone .banner',{
        loop: true,
        speed:1000,
        autoplay: 3000,
        autoplayDisableOnInteraction : false,
        pagination: '.swiper-pagination',
        initialSlide :0,
        observer:true,//修改swiper自己或子元素时，自动初始化swiper
        observeParents:true,//修改swiper的父元素时，自动初始化swiper
    });
});
    