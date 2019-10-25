/*
* @Author: Hlin
* @Date:   2017-05-02 14:55:27 14:55:27
*/

var editor2 = new wangEditor('editor2');
// 上传图片（举例）
editor2.config.uploadImgUrl = '/upload';

editor2.config.menus = [
    // 'source',
    '|',     // '|' 是菜单组的分割线
    'bold',
    'underline',
    // 'italic',
    // 'strikethrough',
    // 'eraser',
    // 'forecolor',
    // 'bgcolor',
    '|',
    'fontsize',
    'unorderlist',
    'orderlist',
    'alignleft',
    'aligncenter',
    'alignright',
    '|',
    'img',
    'symbol'
 ];
// 关闭菜单栏fixed
editor2.config.menuFixed = false;
editor2.create();

// 设置每个编辑的z-index,逐个增加1,互不叠加
$(".wangEditor-container").each(function(index, el) {
    $(this).css("z-index",$(".wangEditor-container").length - index)
});