/**
 * Created by Administrator on 2017/8/24.
 */
$(function () {
    var createShap = '0';
    $(document).on('click','.shapChoose',function(e) {
        e = e || window.event;
        // startX, startY 为鼠标点击时初始坐标
        // diffX, diffY 为鼠标初始坐标与 box 左上角坐标之差，用于拖动
        var startX, startY, diffX, diffY;
        // 是否拖动，初始为 false
        var dragging = false;

        // 鼠标按下
        document.onmousedown = function(e) {
            startX = e.pageX;
            startY = e.pageY;

            // 如果鼠标在 box 上被按下
            if(e.target.className.match(/box/)) {
                // 允许拖动
                dragging = true;

                // 设置当前 box 的 id 为 moving_box
                if(document.getElementById("moving_box") !== null) {
                    document.getElementById("moving_box").removeAttribute("id");
                }
                e.target.id = "moving_box";

                // 计算坐标差值
                diffX = startX - e.target.offsetLeft;
                diffY = startY - e.target.offsetTop;
            }
            else {
                // 在页面创建 box
                var type = '文本框';
                var $thisName = 'createShap'+createShap+'';
                var e = "<div style='width: 0;height: 0;' class='module' className='createShap"+createShap+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:100%'><p style='min-height: 16px;width: 100%'></p></div></div>"
                E.insert(e,type,$thisName);
                //var active_box = document.createElement("div");
                //active_box.id = "active_box";
                //active_box.className = "box";
                //active_box.style.top = startY + 'px';
                //active_box.style.left = startX + 'px';
                active_box.style.top = startY + 'px';
                active_box.style.left = startX + 'px';
                active_box.innerHTML = '<div style="width: calc(100% - 2px);height: calc(100% - 2px);border-radius: 50%;border:1px solid #000"></div>';
                document.body.appendChild(active_box);

                createShap++;
                active_box = null;
            }
        };

        // 鼠标移动
        document.onmousemove = function(e) {
            // 更新 box 尺寸
            if(document.getElementById("active_box") !== null) {
                var ab = document.getElementById("active_box");
                ab.style.width = e.pageX - startX + 'px';
                ab.style.height = e.pageY - startY + 'px';
            }

            // 移动，更新 box 坐标
            if(document.getElementById("moving_box") !== null && dragging) {
                var mb = document.getElementById("moving_box");
                mb.style.top = e.pageY - diffY + 'px';
                mb.style.left = e.pageX - diffX + 'px';
            }
        };

        // 鼠标抬起
        document.onmouseup = function(e) {
            // 禁止拖动
            dragging = false;
            if(document.getElementById("active_box") !== null) {
                var ab = document.getElementById("active_box");
                ab.removeAttribute("id");
                // 如果长宽均小于 3px，移除 box
                if(ab.offsetWidth < 3 || ab.offsetHeight < 3) {
                    document.body.removeChild(ab);
                }
            }
        };
    });
});