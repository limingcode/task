/**
 * app设置
 * @Author   Liang
 * @DateTime 2018-01-10
 */
$(function () {
    // 关闭弹窗
    $('.alert').on('click','.close_btn',function(e){
        $(this).parents('.alert').addClass('hide');
    });
    $('.alert').on('click',function(e){
        e.stopPropagation();
    });
    // 基础设置
    // 拖拽
    dragFun($('.nav_icon'));
    dragFun($('.my_icon_list'));
    // 右键菜单
    $('.device_list').on('contextmenu',function(e){
        e.preventDefault();
    });
    $('.device_list').on('contextmenu','.device_box .icon',function(e){
        e.preventDefault();
        closeCntMent();
        $(this).addClass('on').siblings().removeClass('on');
        $(this).parents('.content').siblings('.context_ment').removeClass('hide').css({'top':e.clientY,'left':e.clientX});
    });
    $('.device_list').on('click',function(e){
        closeCntMent();
        $('.device_box .icon').removeClass('on');
    });

    // 活动设置
    // 查看图片
    $('.view_img').on('click',function(){
        layer.photos({
          photos: '.acti_list table',
          anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        }); 
    });
    // 广告图按钮
    $('.acti_edit').on('change','.img_file .sol_btn input',function(){
        var file = $(this)[0].files[0];  
        var reader = new FileReader();  
        var imgFile;  
        reader.onload=function(e) {
            imgFile = e.target.result;
            // console.log(imgFile);  
            $(".acti_edit .img_file img").prop('src', imgFile).removeClass('hide');
        };  
        reader.readAsDataURL(file);
    });
    // 预览按钮
    if(!$('.acti_edit').prop('checked')){
        $('.acti_edit .device_box').addClass('hide');
    }
    $('.acti_edit').on('click','#preview',function(){
        if($(this).prop('checked')){
            $('.device_box').removeClass('hide');
        }else{
            $('.device_box').addClass('hide');
        }
    });
    // 链接
    $('.acti_edit').on('click','.link_btn',function(e){
        $('.context_ment').removeClass('hide');
        e.preventDefault();
    });

    $('.acti_edit').on('click',function(e){
        var target=$(e.target);
        if(!target.is('.link_btn')){
            closeCntMent();
        }
    });
    $('.acti_edit').on('click','.context_ment',function(e){
        $('.link_input').val($(this).find('td').eq(1).html());
    });

    //群管理
    //切换
    $('.qq_group').on('click','.qq_nav input',function(){
        var idx=$(this).parent().index();
        $('.qq_con').eq(idx).removeClass('hide').siblings('.qq_con').addClass('hide');
        $('.edit_box').hide();
    });
    // 操作
    $('.qq_group').on('click','.alert_btn',function(e){
        var editBoxTop = $(this).parents('tr').offset().top + $(this).parents('tr').height()-5;
        $('.edit_box').css('top',editBoxTop).slideDown();
        $('.shade_pop').removeClass('hide');
        e.stopPropagation();
    });
    $('.qq_group').on('click','.edit_box',function(e){
        e.stopPropagation();
    });
    $('.shade_pop').on('click',function(e){
        $('.edit_box').slideUp(function(){
            $('.shade_pop').addClass('hide');
        });
    });
    // 新增
    $('.qq_group').on('click','.add_user_btn',function(){
        $('.add_user_pop').removeClass('hide');
    });
    // 删除
    $('.qq_group').on('click','.remove_user_btn',function(){
        $('.remove_user_pop').removeClass('hide');
    });

    // 时间选择
    $.jeDate('#issue_time',{
        format:"YYYY年MM月DD日 hh:mm:ss",
        minDate:$.nowDate({DD:0})
    });
});
// 关闭右键菜单
function closeCntMent(){
    $('.context_ment').addClass('hide');
}
// 拖拽
function dragFun(dom){
    var srcdiv = null; 
    var icon = dom.find('.icon');
    [].forEach.call(icon, function (el,e){
        el.draggable=true;

        // 拖拽前触发
        el.addEventListener('dragstart',function(e){
            srcdiv=this;
            e.dataTransfer.setData("text","www.baidu.com");
        },false);

        // 在目标元素上释放鼠标触发
        el.addEventListener('drop',function(e){
            e.preventDefault(); 
            var ep1 = $(srcdiv).parent()[0],
                ep2 = $(this).parent()[0],
                oldIdx=$(srcdiv).index(),
                newIdx=$(this).index();
            if(srcdiv != this){  
                if(newIdx > oldIdx || ep1 != ep2){
                    ep2.insertBefore(srcdiv,ep2.children[newIdx]);
                    ep1.insertBefore(this,ep1.children[oldIdx]); 
                }else{
                    ep2.insertBefore(srcdiv,ep2.children[newIdx]);
                    ep1.insertBefore(this,ep1.children[oldIdx+1]);
                }
            } 
            e.dataTransfer.clearData("text")
        },false);

        // 进入目标，离开目标之间连续触发
        el.addEventListener('dragover',function(e){
            e.preventDefault(); 
        },false);
    });
    // 防止火狐打开新页面
    document.body.addEventListener('drop',function (event) {  
        event.preventDefault();  
        event.stopPropagation();  
    },false);
}