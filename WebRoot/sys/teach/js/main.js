/*
 * @Author: bruce
 * @Date:   2017-06-22 20:12:41
 */
require.config({
    baseUrl: './js',
    paths: {
        jquery: "../js/jquery-3.2.1.min",
        layer:'../js/layer',
        server:'lib/server',
        classChoose:'lib/classChoose',
        jqUI:"lib/jquery-ui.min",
        iocnFont:'lib/iconfont',
        lessonsChoose: "util/lessonsChoose",
        html2canvas:'lib/html2canvas.min',
        index: 'index',
        editor: 'util/editor',
        ddsortMin: 'lib/ddsort.min',
        ddsort:'util/ddsort',
        menuEvent:'util/menuEvent',
        colorpicker:'lib/evol-colorpicker.min'

    },
    shim: {
        layer:['jquery'],
        jqUI:['jquery'],
        ddsortMin:['jquery'],
        ddsort:['ddsortMin','iocnFont'],
        index:['jquery','html2canvas'],
        'html2canvas':{
            deps:[],
            exports: 'html2canvas'
        },
        'menuEvent':['jquery'],
        colorpicker:{
            deps:['jqUI'],
            exports:'colorpicker'
        }
    }
});

require([
    'jquery',
    'layer',
    'server',
    'classChoose',
    'jqUI',
    'lessonsChoose',
    'index',
    'editor',
    'ddsort',
    'iocnFont',
    'menuEvent'
],function($,layer,server,classChoose,jqUI,lessonsChoose,index,E,ddsort,iocnFont,menuEvent){
    //server.server();
    //classChoose.classChoose();
    //classChoose.functionChoose();
    //classChoose.pptPro();
    //ddsort.removeList();
    lessonsChoose.firstFunc();
});