window.skyAlert = (function(window, document, undefined) {
    var aStyleNode,
    lStyleNode,
    alertBox,
    closeBtn,
    alertCnt,
    cancelBtn,
    confirmBtn,
    opts,
    content,
    cancel,
    confirm;
    //初始化样式
    var _alertStyle = function(){
        aStyleNode = document.createElement("style"),
        css = "html,body{height:100%;}.alert_box {z-index:1000;position: fixed;top: 0;left: 0;width: 100%;height: 100%;background: rgba(0, 0, 0, 0.6);display: -webkit-flex;display: -webkit-box;display: -moz-box;display: -ms-flexbox;display: flex;align-items: center;}.alert_box .warp {position: relative;width: 300px;margin: 0 auto;background: #fff;border-radius: 5px;padding: 25px 10px 0;text-align: center;}.alert_box .warp .close{position: absolute;top: 0;right: 0;width: 25px;height: 25px;display: block;background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAABI0lEQVRYR8XX2w3CMBBE0UkHdAKUQgVAZ9ABHUAHUBLaKEaW8WNnYiv8ICDhnliKV5mw8WvauI8Y8AHwBnAdjLoB2AM4WicG2A9nAPY+ChEadwCXFGCfRyL+4jnAKEQ2XgL0RhTjNUAvRDXeAqxFNOMegIpwxb0AFuGOMwAvgoqzgBaCjiuAHYDXspXGO6YUVwB2Toqw72xb/W2vzCxRp2GMsJ4UV1cgrMITwGG52geAE3Pl4VhlBezqQ9yu3F7yFGUBaXweqWumKAMoxcNqSqPcC2jFZYQXwNzn1Ep4AEycXokWQIlTiBpgTdyNKAF6xF2IHKBnvIlIASPiVUTuwUQeLI5Z8HeLpo9m9ngWtlfH/0mHGMKG2DzIWrehVGBO2hzwBQdFaCHF4EFLAAAAAElFTkSuQmCC);background-repeat: no-repeat;background-position: center;background-size: 16px;cursor: pointer;}.alert_box .warp p{padding: 0;margin: 0;}.alert_box .warp .cnt{line-height: 20px;margin: 0 0 20px;padding: 10px 15px;}.alert_box .warp .oper_btn{display: -webkit-flex;display: -webkit-box;display: -moz-box;display: -ms-flexbox;display: flex;width: 100%;text-align: center;}.alert_box .warp .oper_btn a{display:none;float: left;width: 100%;height: 40px;line-height: 40px;border-top: 1px solid #ccc;}";
        aStyleNode.type="text/css";
        if(aStyleNode.styleSheet){
            aStyleNode.styleSheet.cssText = css;
        } else {
            aStyleNode.innerHTML = css;
        }
        document.getElementsByTagName("head")[0].appendChild(aStyleNode);
    };
    var _loadingstyle = function(){
        lStyleNode = document.createElement("style"),
        css = "html,body{height:100%;}.loading_box {z-index:1000;position: fixed;top: 0;left: 0;width: 100%;height: 100%;background: rgba(0, 0, 0, 0.6);display: -webkit-flex;display: -webkit-box;display: -moz-box;display: -ms-flexbox;display: flex;align-items: center;}.loading{position: absolute;top:0;bottom:0;right:0;left: 0;margin: auto;max-width:200px;height:100px;padding: 50px;background-color: #fff;border-radius: 10px;}.loading_txt{margin-top: 40px;text-align: center;}.spinner {margin: 0 auto;width: 50px;height: 50px;position: relative;}.container1 > div, .container2 > div, .container3 > div {width: 6px;height: 6px;background-color: #333;border-radius: 100%;position: absolute;-webkit-animation: bouncedelay 1.2s infinite ease-in-out;animation: bouncedelay 1.2s infinite ease-in-out;-webkit-animation-fill-mode: both;animation-fill-mode: both;}.spinner .spinner-container {position: absolute;width: 100%;height: 100%;}.container2 {-webkit-transform: rotateZ(45deg);transform: rotateZ(45deg);}.container3 {-webkit-transform: rotateZ(90deg);transform: rotateZ(90deg);}.circle1 { top: 0; left: 0; }.circle2 { top: 0; right: 0; }.circle3 { right: 0; bottom: 0; }.circle4 { left: 0; bottom: 0; }.container2 .circle1 {-webkit-animation-delay: -1.1s;animation-delay: -1.1s;}.container3 .circle1 {-webkit-animation-delay: -1.0s;animation-delay: -1.0s;}.container1 .circle2 {-webkit-animation-delay: -0.9s;animation-delay: -0.9s;}.container2 .circle2 {-webkit-animation-delay: -0.8s;animation-delay: -0.8s;}.container3 .circle2 {-webkit-animation-delay: -0.7s;animation-delay: -0.7s;}.container1 .circle3 {-webkit-animation-delay: -0.6s;animation-delay: -0.6s;}.container2 .circle3 {-webkit-animation-delay: -0.5s;animation-delay: -0.5s;}.container3 .circle3 {-webkit-animation-delay: -0.4s;animation-delay: -0.4s;}.container1 .circle4 {-webkit-animation-delay: -0.3s;animation-delay: -0.3s;}.container2 .circle4 {-webkit-animation-delay: -0.2s;animation-delay: -0.2s;}.container3 .circle4 {-webkit-animation-delay: -0.1s;animation-delay: -0.1s;}@-webkit-keyframes bouncedelay {0%, 80%, 100% { -webkit-transform: scale(0.0) }40% { -webkit-transform: scale(1.0) }}@keyframes bouncedelay {0%, 80%, 100% {transform: scale(0.0);-webkit-transform: scale(0.0);} 40% {transform: scale(1.0);-webkit-transform: scale(1.0);}};";
        lStyleNode.type="text/css";
        if(lStyleNode.styleSheet){
            lStyleNode.styleSheet.cssText = css;
        } else {
            lStyleNode.innerHTML = css;
        }
        document.getElementsByTagName("head")[0].appendChild(lStyleNode);
    };
    // 生成弹窗及内容
    var _alertCreate = function(){
        var nod = document.createElement("div");
        nod.className = "alert_box";
        var alertBoxHtml = '<div class="warp">'+
            '<span class="close"></span>'+
            '<div class="cnt"></div>'+
            '<div class="oper_btn">'+
                '<a class="cancel" href="javascript:;">取消</a>'+
                '<a class="confirm" href="javascript:;">确定</a>'+
            '</div>'+
        '</div>'
        if(nod.styleSheet){
            nod.styleSheet.cssText = alertBoxHtml;
        } else {
            nod.innerHTML = alertBoxHtml;
        }
        document.getElementsByTagName("body")[0].appendChild(nod);
    }
    var _loadingCreate = function(txt){
        var nod = document.createElement("div");
        nod.className = "loading_box";
        var alertBoxHtml = '<div class="loading">'+
                '<div class="spinner">'+
                '<div class="spinner-container container1">'+
                '<div class="circle1"></div>'+
                '<div class="circle2"></div>'+
                '<div class="circle3"></div>'+
                '<div class="circle4"></div>'+
                '</div>'+
                '<div class="spinner-container container2">'+
                '<div class="circle1"></div>'+
                '<div class="circle2"></div>'+
                '<div class="circle3"></div>'+
                '<div class="circle4"></div>'+
                '</div>'+
                '<div class="spinner-container container3">'+
                '<div class="circle1"></div>'+
                '<div class="circle2"></div>'+
                '<div class="circle3"></div>'+
                '<div class="circle4"></div>'+
                '</div>'+
                '</div>'+
                '<p class="loading_txt">'+txt+'</p>'+
            '</div>'
        if(nod.styleSheet){
            nod.styleSheet.cssText = alertBoxHtml;
        } else {
            nod.innerHTML = alertBoxHtml;
        }
        document.getElementsByTagName("body")[0].appendChild(nod);
    }
    // 事件定义
    var _alertEvent = function(content,cancel,confirm){
        alertBox = document.getElementsByClassName("alert_box")[0];
        closeBtn = alertBox.getElementsByClassName("close")[0];
        alertCnt = alertBox.getElementsByClassName("cnt")[0];
        cancelBtn = alertBox.getElementsByClassName("cancel")[0];
        confirmBtn = alertBox.getElementsByClassName("confirm")[0];
        //赋值
        alertCnt.innerHTML = content;
        closeBtn.onclick = function(){
            alertBox.remove();
            aStyleNode.remove();
        }
        if (typeof cancel == "function") {
            cancelBtn.style.display = "block";
            cancelBtn.onclick = function(){
                alertBox.remove();
                cancel();
                aStyleNode.remove();
            }
        }
        if (typeof confirm == "function") {
            confirmBtn.style.display = "block";
            confirmBtn.onclick = function(){
                alertBox.remove();
                aStyleNode.remove();
                confirm();
            }
        }
        if (typeof cancel == "function"&&typeof confirm == "function") {
            cancelBtn.style.borderRight = "1px solid #ccc"
        }
        if (!typeof confirm == "function"&&!typeof cancel == "function") {
            setTimeout(function(){
                alertBox.remove();
                aStyleNode.remove();
                confirm();
            }, 2000)
        }
    }

    var alert = function(obj) {
        if (typeof obj == "object") {
            opts = obj||{};
            content = opts.content;
            cancel = opts.cancel;
            confirm = opts.confirm;
        }else{
            content = obj;
        }
        _alertStyle();
        _alertCreate();
        _alertEvent(content,cancel,confirm);
    };

    var loading = function(obj){
        if (typeof obj == "string") {
            _loadingstyle();
            _loadingCreate(obj||"正在上传中。。。");
        }else{
            document.getElementsByClassName("loading_box")[0].remove();
            lStyleNode.remove();
        }
    }
    var success = function(obj){
        _alertStyle();
        _alertCreate();
        alertBox = document.getElementsByClassName("alert_box")[0];
        closeBtn = alertBox.getElementsByClassName("close")[0];
        alertCnt = alertBox.getElementsByClassName("cnt")[0];
        cancelBtn = alertBox.getElementsByClassName("cancel")[0];
        confirmBtn = alertBox.getElementsByClassName("confirm")[0];
        //赋值
        alertCnt.innerHTML = obj;
        closeBtn.onclick = function(){
            alertBox.remove();
            aStyleNode.remove();
        }
        setTimeout(function(){
            alertBox.remove();
            aStyleNode.remove();
        }, 2000)
    }

    return {
        alert: alert,
        loading: loading,
        success: success
    };
})(window, document);