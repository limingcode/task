/*
* @Author: Hlin
* @Date:   2017-05-02 14:55:27 14:55:27
*/

var editor2 = new wangEditor('editor2');
var editor3 = new wangEditor('editor3');
var editor4 = new wangEditor('editor4');
var editor5 = new wangEditor('editor5');
// 上传图片（举例）
editor2.config.uploadImgUrl = '/upload';
editor3.config.uploadImgUrl = '/upload';
editor4.config.uploadImgUrl = '/upload';
editor5.config.uploadImgUrl = '/upload';

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
editor3.config.menus = ['img'];
editor4.config.menus = ['img'];
editor5.config.menus = ['img'];
editor2.create();
editor3.create();
editor4.create();
editor5.create();

// 设置每个编辑的z-index,逐个增加1,互不叠加
$(".wangEditor-container").each(function(index, el) {
    $(this).css("z-index",$(".wangEditor-container").length - index)
});