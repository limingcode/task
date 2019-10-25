/*
* @Author: Hlin
* @Date:   2017-08-25 09:12:23
* @Last Modified by:   Hlin
* @Last Modified time: 2017-08-28 14:40:59
* 多选
*/
var $shape;
//判断鼠标是否在编辑区域内
document.addEventListener("mouseover",function(e){
    if (e.target == $(".editor_box:visible")[0]) {
        var box = $(".editor_box:visible")
        checkbox(box);
    }
});
function checkbox(box){
    var x,y,cx,cy,rect,ww,hh,tt,ll,bol1,bol2,bol3,bol4,pt,pl;
    var startX, startY, diffX, diffY;
    var dragging = false;
    var domList;
    var classNameList1;
    var $shapeIndex = 200098;
    // 监听鼠标按下获取起点坐标并添加鼠标移动监听
    box[0].onmousedown = function(e) {
        var $shapeName = sessionStorage.getItem('shapeName');
        if($shape){
            //dragging = true;
            //pt = box[0].offsetTop;
            //pl = box[0].offsetLeft;
            //x = e.clientX - pl;
            //y = e.clientY - pt;
            //rect = document.createElement("div");
            //rect.className = "module";
            //rect.setAttribute('className','box'+$shapeIndex+'');
            ////1：圆形  2：正方形  3：菱形   4：平行四边形  5：多边形  6：直线  7：曲线  8：箭头   9：j
            //if($shapeName==1){
            //    rect.innerHTML = '<div style="width: calc(100% - 8px);height: calc(100% - 8px);border-radius: 50%;border:4px solid #000"></div>';
            //}
            //if($shapeName==2){
            //    rect.innerHTML = '<div style="width: calc(100% - 8px);height: calc(100% - 8px);border:4px solid #000"></div>';
            //}
            //if($shapeName==3){
            //    rect.innerHTML = '<div style="width: calc(100% - 8px);height: calc(100% - 8px);border:4px solid #000"></div>';
            //}
            //
            //$(rect).css({
            //    position:'absolute',
            //    left: x,
            //    top: y,
            //    border: '1px solid #fff'
            //});
            //var type = '形状占位符';
            //E.insert(rect,type,'box856964');
            function move(e){
                if(dragging){
                    cx = e.clientX - pl;
                    cy = e.clientY - pt;
                    ww = Math.abs(cx - x);
                    hh = Math.abs(cy - y);
                    tt = (cy - y)>0?(y):(cy);
                    ll = (cx - x)>0?(x):(cx);
                    $(rect).css({
                        width: ww,
                        height: hh,
                        top:tt,
                        left:ll
                    })
                }
            }
            document.addEventListener('mousemove',move);
            document.onmouseup = function(e) {
                // 禁止拖动
                dragging = false;

            };
            $shape = false;
            $shapeIndex++;
        }
        else{
            domList = new Array();
            classNameList1 = [];
            if (e.target == box[0]) {
                pt = box[0].offsetTop;
                pl = box[0].offsetLeft;
                x = e.clientX - pl;
                y = e.clientY - pt;
                rect = document.createElement("div");
                rect.className = "checkrect";
                $(rect).css({
                    position:'absolute',
                    left: x,
                    top: y,
                    border: '1px solid #888',
                    backgroundColor: 'rgba(169,149,254,0.2)'
                });
                box.append(rect);
                //过滤起点坐标确定后不可能选中的盒子
                box.children('div').each(function(index, el) {
                    var _this = getObxCo(this);
                    // $(this).removeClass('focus');
                    if (((x>_this.x1&&x>_this.x2)||(x<_this.x1&&x<_this.x2))&&((y>_this.y1&&y>_this.y2)||(y<_this.y1&&y<_this.y2))) {
                        //保存没被过滤的盒子
                        domList.push(this);
                        return;
                    }
                });
                function move(e){
                    cx = e.clientX - pl;
                    cy = e.clientY - pt;
                    ww = Math.abs(cx - x);
                    hh = Math.abs(cy - y);
                    tt = (cy - y)>0?(y):(cy);
                    ll = (cx - x)>0?(x):(cx);
                    $(rect).css({
                        width: ww,
                        height: hh,
                        top:tt,
                        left:ll
                    })
                }
                document.addEventListener('mousemove',move);
                // 鼠标弹起获取终点坐标后判断被选中的盒子并移除移动监听
                document.onmouseup = function(e){
                    cx = e.clientX - pl;
                    cy = e.clientY - pt;
                    $.each(domList,function(index, el) {
                        tDom = getObxCo(this);
                        bol1 = x<cx&&x<tDom.x1&&cx>tDom.x2&&y<cy&&y<tDom.y1&&cy>tDom.y2;//左上
                        bol2 = x>cx&&cx<tDom.x1&&x>tDom.x2&&y<cy&&y<tDom.y1&&cy>tDom.y2;//右上
                        bol3 = x<cx&&x<tDom.x1&&cx>tDom.x2&&y>cy&&cy<tDom.y1&&y>tDom.y2;//左下
                        bol4 = x>cx&&cx<tDom.x1&&x>tDom.x2&&y>cy&&cy<tDom.y1&&y>tDom.y2;//右下
                        if (bol1||bol2||bol3||bol4) {
                            classNameList1.push($(this).attr("className"));
                            var self = this;
                            setTimeout(function(){
                                $(self).addClass('focus');
                            },10)
                        }
                    });
                    if (classNameList1.length) {
                        sessionStorage.setItem("classNameList",JSON.stringify(classNameList1))
                    }else{
                        //sessionStorage.removeItem("classNameList")
                    }
                    $(".checkrect").remove();
                    document.removeEventListener('mousemove',move);
                    classNameList1 = [];
                    domList = [];
                }
            }
        }
    };
    //计算盒子左上点的坐标和对应的对角坐标
    function getObxCo(dom){
        var x1 = dom.offsetLeft;
        var y1 = dom.offsetTop;
        var x2 = x1+dom.offsetWidth;
        var y2 = y1+dom.offsetHeight;
        return {
            "x1":x1,
            "y1":y1,
            "x2":x2,
            "y2":y2
        }
    }
}