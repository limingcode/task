/*
* @Author: Hlin
* @Date:   2018-01-11 16:14:51
* @Last Modified by:   Hlin
* @Last Modified time: 2018-01-11 17:24:59
*/
// 功能templet

// layer弹窗
$("body").on('click', '.sol_btn', function(event) {
    layer.alert("内容",{
        title: "提示", //标题
        skin: "layer-sky-skin", //皮肤
        btn: ['确定','取消'], // 按钮
        closeBtn: 0, //右上角的关闭按钮，0为不显示，1为显示
        // cancel: function(index, dom){ //右上角关闭按钮的回调
        //     console.log("关闭弹窗")
        // },
        // time: 1000, //自动关闭的时间
        // anim: 4, //弹出动画 1~6
        // resize: true, //右下角拖动大小
        btn1: function(index, dom){ //index是该弹窗的索引
            console.log("1"); //按钮【按钮一】的回调
            layer.close(index); //关闭弹窗
        },
        btn2: function(index, dom){
            console.log("2");//按钮【按钮二】的回调
        }
    })
});