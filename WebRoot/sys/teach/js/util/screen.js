$(function () {













    var text_1;
    var $css = new Array();
    var $formatBrush = false;
    $(document).on('mouseup','.text', function () {
        var $fontSize,$fontWeight,$fontFamily,$textDecoration,$fontStyle,$color,$bgColor;
            if(window.getSelection&&!$formatBrush) {
                $fontSize = $(window.getSelection().anchorNode.parentNode).css('font-size');
                $fontWeight = $(window.getSelection().anchorNode.parentNode).css('font-weight');
                $fontFamily = $(window.getSelection().anchorNode.parentNode).css('font-family');
                $textDecoration = $(window.getSelection().anchorNode.parentNode).css('text-decoration');
                $fontStyle = $(window.getSelection().anchorNode.parentNode).css('font-style');
                $color = $(window.getSelection().anchorNode.parentNode).css('color');
                $bgColor = $(window.getSelection().anchorNode.parentNode).css('background-color');
                $css = {
                    fontSize: $fontSize,
                    fontWeight: $fontWeight,
                    fontFamily: $fontFamily,
                    textDecoration: $textDecoration,
                    fontStyle: $fontStyle,
                    color: $color,
                    bgColor: $bgColor
                };
            }
        if($formatBrush){
            if(window.getSelection().getRangeAt(0).commonAncestorContainer.children){
                $.each(window.getSelection().getRangeAt(0).commonAncestorContainer.children,function(){
                    var text = this.innerText;
                    $(this).html(text);
                    $(this).css({
                        fontSize: $css.fontSize,
                        fontWeight: $css.fontWeight,
                        fontFamily: $css.fontFamily,
                        textDecoration: $css.textDecoration,
                        fontStyle: $css.fontStyle,
                        color: $css.color,
                        bgColor: $css.bgColor
                    });
                });
            }
            else{
                $(window.getSelection().anchorNode.parentNode).css({
                    fontSize: $css.fontSize,
                    fontWeight: $css.fontWeight,
                    fontFamily: $css.fontFamily,
                    textDecoration: $css.textDecoration,
                    fontStyle: $css.fontStyle,
                    color: $css.color,
                    backgroundColor: $css.bgColor
                });
            }
            $('.text_1').css('background-color','#fff');
            $formatBrush = false;
        }
    });

    $('.menu_cnt').on('click','.text_1', function () {
        if(!$formatBrush){
            $formatBrush = true;
            $(this).css('background-color','#ccc');
        }
        else{
            $formatBrush = false;
            $(this).css('background-color','#fff');
        }
    });
});
















//$(function () {
//    var browser = {};
//    var range = GetSelectionRange(window);
//    var ua = navigator.userAgent.toLowerCase();
//    browser.msie = (/msie ([\d.]+)/).test(ua);
//    browser.firefox = (/firefox\/([\d.]+)/).test(ua);
//    browser.chrome = (/chrome\/([\d.]+)/).test(ua);
//
//    //获取多个节点的HTML
//    function GetInnerHTML(nodes)
//    {
//        var builder = [];
//        for (var i = 0; i < nodes.length; i++)
//        {
//            if (nodes[i].nodeValue != undefined)
//            {
//                builder.push(nodes[i].innerHTML);
//            }
//            else
//            {
//                if (nodes[i].textContent) builder.push(nodes[i].textContent.replace(/\</ig, function() { return "<"; }));
//                else if (nodes[i].nodeValue) builder.push(nodes[i].nodeValue.replace(/\</ig, function() { return "<"; }));
//            }
//        }
//        return builder.join("");
//    }
//
//    function SelectionRange(doc, range) {
//
//        //获取选中的内容的HTML
//        this.GetSelectedHtml = function(){
//            if (range == null) return "";
//            return GetInnerHTML(range.cloneContents().childNodes);
//            //if (browser.msie)
//            //{
//            //    if (range.htmlText != undefined) return range.htmlText;
//            //    else return "";
//            //}
//            //else if (browser.firefox || browser.chrome)
//            //{
//            //    return GetInnerHTML(range.cloneContents().childNodes);
//            //}
//            //else
//            //{
//            //    return "";
//            //}
//        };
//
//        //用html替换选中的内容的HTML
//        this.Replace = function(html)
//        {
//            if (range != null)
//            {
//                if (browser.msie)
//                {
//                    if (range.pasteHTML != undefined)
//                    {
//                        //当前选中html可能以为某种原因（例如点击了另一个DIV）而丢失，重新选中
//                        range.select();
//                        range.pasteHTML(html);
//                        return true;
//                    }
//                }
//                else if (browser.firefox || browser.chrome)
//                {
//                    if (range.deleteContents != undefined && range.insertNode != undefined)
//                    {
//                        //将文本html转换成DOM对象
//                        var temp = doc.createElement("DIV");
//                        temp.innerHTML = html;
//
//                        var elems = [];
//                        for (var i = 0; i < temp.childNodes.length; i++)
//                        {
//                            elems.push(temp.childNodes[i]);
//                        }
//
//                        //删除选中的节点
//                        range.deleteContents();
//
//                        //将html对应的节点(即temp的所有子节点)逐个插入到range中，并从temp中删除
//                        for (var i in elems)
//                        {
//                            temp.removeChild(elems[i]);
//                            range.insertNode(elems[i]);
//                        }
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }
//    //与此同时，还实现了一个函数GetSelectionRange用于获取当前选中文本对应的SelectionRange对象，
////　　[javascript] view plaincopy
//    function GetSelectionRange(win)
//    {
//        var range = null;
//        //
//        //if (browser.msie)
//        //{
//        //    range = win.document.selection.createRange();
//        //    if (range.parentElement().document != win.document)
//        //    {
//        //        range = null;
//        //    }
//        //}
//        //else if (browser.firefox || browser.chrome)
//        //{
//        //    var sel = win.getSelection();
//        //    if (sel.rangeCount > 0) range = sel.getRangeAt(0); else range = null;
//        //}
//        var sel = win.getSelection();
//        if (sel.rangeCount > 0) range = sel.getRangeAt(0); else range = null;
//        return new SelectionRange(win.document, range);
//    }
//    //2.4 修改选中的HTML的样式
//    //修改选中的HTML的样式方法并不复杂，只需要将HTML转成DOM对象，然后递归的设置每一个节点对应的样式的值即可,具体代码如下：
////　　[javascript] view plaincopy
//    function SetNodeStyle(doc, node, name, value)
//    {
//        if (node.innerHTML == undefined)
//        {
//            return node;
//        }
//        else
//        {
//            node.style[name] = value;
//
//            for (var i = 0; i < node.childNodes.length; i++)
//            {
//                var cn = node.childNodes[i];
//                if (node.innerHTML != undefined)
//                {
//                    SetNodeStyle(doc, cn, name, value);
//                }
//            }
//
//            return node;
//        }
//    }
//
//    function SetStyle(doc, html, name, value)
//    {
//        var dom = document.createElement("div");
//        dom.innerHTML = html;
//
//        for (var i = 0; i < dom.childNodes.length; i++)
//        {
//            var node = dom.childNodes[i];
//
//            if (node.innerHTML == undefined)
//            {
//                //如果是文本节点，则转换转换成span
//                var span = doc.createElement("span");
//                span.style[name] = value;
//                if (node.nodeValue != undefined) span.innerHTML = node.nodeValue.replace(/\</ig, function() { return "<"; });
//                else if (node.textContent != undefined) span.innetHTML = node.textContent.replace(/\</ig, function() { return "<"; });
//                //替换掉文本节点
//                dom.replaceChild(span, node);
//            }
//            else
//            {
//                SetNodeStyle(doc, node, name, value);
//            }
//        }
//
//        return dom.innerHTML;
//    }
//
//    $('.menu_cnt').on('click','.text_1', function () {
//        var range = GetSelectionRange(window);
//        //var range = window.getSelection().getRangeAt(0);
//        //var a = range.extractContents();
//        //var sss = window.getSelection().anchorNode;
//        //var span = document.createElement('span');
//        //span.innerHTML = '1111111';
//        //var text = document.createTextNode('match');
//        ////range.insertNode(a.children[0]);
//        ////sss.insertAdjacentHTML('beforeEnd',a.children[0]);
//        //$.each(a.children, function (i,elem) {
//        //});
//        //selection.anchorNode.insetAdjacentElement('afterEnd',a);
//        var html = SetStyle(document, range.GetSelectedHtml(), "fontSize", "32px");
//        range.Replace(html);
//    });
//});