// 选择题 菜单
(function () {
    var E = window.wangEditor;
    var $ = window.jQuery;
    E.createMenu(function (check) {
        var menuId = 'menuC';
        if (!check(menuId)) {
            return;
        }
        var editor = this;
        // 创建 menu 对象
        var menu = new E.Menu({
            editor: editor,  // 编辑器对象
            id: menuId,  // 菜单id
            title: '选择题', // 菜单标题

            // 正常状态和选中状态下的dom对象，样式需要自定义
            $domNormal: $('<a href="#" tabindex="-1"><i class="editor_menu_c">选择题</i></a>'),
            $domSelected: $('<a href="#" tabindex="-1" class="selected"><i class="editor_menu_c"></i></a>')
        });
        // 菜单正常状态下，点击将触发该事件
        menu.clickEvent = function (e) {
            // 添加选择题选项
            var s = "<div class='add_question_c' contenteditable='false'>"+
                "<span class='close_q'></span>"+
                "<div class='c_item'>"+
                    "<label class='sky_checkbox'>"+
                        "<input type='checkbox' name='checkbox' />"+
                        "<span class='mark'></span>A"+
                    "</label>"+
                    "<input class='option' type='text' />"+
                    "<span class='remove_btn'></span>"+
                "</div>"+
                "<div class='c_item'>"+
                    "<label class='sky_checkbox'>"+
                        "<input type='checkbox' name='checkbox' />"+
                        "<span class='mark'></span>B"+
                    "</label>"+
                    "<input class='option' type='text' />"+
                    "<span class='remove_btn'></span>"+
                "</div>"+
                "<div class='c_item'>"+
                    "<label class='sky_checkbox'>"+
                        "<input type='checkbox' name='checkbox' />"+
                        "<span class='mark'></span>C"+
                    "</label>"+
                    "<input class='option' type='text' />"+
                    "<span class='remove_btn'></span>"+
                "</div>"+
                "<div class='c_item'>"+
                    "<label class='sky_checkbox'>"+
                        "<input type='checkbox' name='checkbox' />"+
                        "<span class='mark'></span>D"+
                    "</label>"+
                    "<input class='option' type='text' />"+
                    "<span class='remove_btn'></span>"+
                "</div>"+
                "<div class='score'><input type='number' placeholder='请填写分数' /></div>"+
                "<button class='add_btn'>+</button>"+
            "</div>"
            // 判断是否有其他类型的题目并执行插入的命令
            if (editor.$txt.find('.add_question_e').length > 0||editor.$txt.find('.add_question_c').length > 0||editor.$txt.find('.add_question_l').length > 0||editor.$txt.find('.add_question_j').length > 0) {
                editor.$txt.find('.add_question_e,.add_question_l').fadeOut().css("backgroundColor","#B9EFCF").fadeIn(function(){
                    $(this).css("backgroundColor","unset")
                });
            }else{
                // 禁用其他题型菜单
                editor.menus.menuE.disabled(true);
                editor.menus.menuL.disabled(true);
                editor.menus.menuJ.disabled(true);
                editor.menus.menuA.disabled(true);

                editor.$txt.append(s);
            }
        };
        // 添加选项
        var letterList = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
        $(".editor").on('click', '.add_btn', function(event) {
            event.preventDefault();
            var cLength = editor.$txt.find(".add_question_c .c_item").length;
            $(this).siblings('.score').before("<div class='c_item'>"+
                "<label class='sky_checkbox'>"+
                    "<input type='checkbox' name='checkbox' />"+
                    "<span class='mark'></span>"+letterList[cLength]+
                "</label>"+
                "<input class='option' type='text' />"+
                "<span class='remove_btn'></span>"+
            "</div>");
            if (cLength == letterList.length-1) {
                $(this).hide();
            }
        });
        // 删除选项
        $(".editor").on('click', '.remove_btn', function(event) {
          $(this).parent().slideUp(function(){
            $(this).remove();
            editor.$txt.find('.add_question_c .sky_checkbox').each(function(index, el) {
              $(this).html($(this).html().substring(0, $(this).html().length - 1)+letterList[index]);
            });
          });
          $(".add_btn").show();
        });
        // 删除整个选择题
        $(".editor").on('click', '.close_q', function(event) {
          $(this).parent().slideUp(function(){
            $(this).remove();
            editor.$txt.find('.add_question_c .sky_checkbox').each(function(index, el) {
              $(this).html("<span class='mark'></span>"+letterList[index])
            });

            editor.enableMenusExcept();
          });
        });
        // 增加到editor对象中
        editor.menus[menuId] = menu;
    });
})();
// 填空题 菜单
(function () {
    var E = window.wangEditor;
    var $ = window.jQuery;
    var initLength = 7;
    E.createMenu(function (check) {
        var menuId = 'menuE';
        if (!check(menuId)) {
            return;
        }
        var editor = this;
        var menu = new E.Menu({
            editor: editor,  // 编辑器对象
            id: menuId,  // 菜单id
            title: '填空题', // 菜单标题
            // 正常状态和选中状态下的dom对象，样式需要自定义
            $domNormal: $('<a href="#" tabindex="-1">'+
                    '<i class="editor_menu_e">填空题</i>'+
                    '<div class="menu_e_length">'+
                        '<button class="add">+</button>'+
                        '<button class="reduce">-</button>'+
                    '</div>'+
                '</a>'),
            $domSelected: $('<a href="#" tabindex="-1" class="selected"><i class="editor_menu_e"></i></a>')
        });
        // 菜单正常状态下，点击将触发该事件
        menu.clickEvent = function (e) {
            if (!editor.$txt.find('.add_question_e').length||!editor.$txt.find('.add_question_e').attr("length")) {
                initLength = 7;
            }else{
                initLength = editor.$txt.find('.add_question_e').attr("length");
            }
            var s = "<input class='add_question_e' type='text' style='width:calc(0.5em * "+initLength+")' length='"+initLength+"' />";
            var sScore = "<input type='number' step='0.5' class='score' />&nbsp;"
            // 判断是否有其他类型的题目并执行插入的命令
            if (editor.$txt.find('.add_question_c').length > 0||editor.$txt.find('.add_question_l').length > 0||editor.$txt.find('.add_question_j').length > 0) {
                editor.$txt.find('.add_question_c,.add_question_l').fadeOut().css("backgroundColor","#B9EFCF").fadeIn(function(){
                    $(this).css("backgroundColor","unset")
                });
            }else{
                // 禁用其他题型菜单
                editor.menus.menuC.disabled(true);
                editor.menus.menuL.disabled(true);
                editor.menus.menuJ.disabled(true);
                editor.menus.menuA.disabled(true);

                editor.command(e, 'insertHtml',s);
                editor.command(e, 'insertHtml',sScore);
            }
        };
        // 长度增加事件绑定
        $(menu.$domNormal).on("click",".menu_e_length .add",function(event) {
            event.stopPropagation();
            // 没有题目则直接返回
            if (!editor.$txt.find('.add_question_e').length) {
                return false;
            }
            var attrLength = editor.$txt.find('.add_question_e').attr("length");
            if (attrLength == "NaN" || attrLength == "undefined") {
                initLength = 7;
            }else{
                initLength = attrLength;
            }
            // 文字大小
            var fontSize = Number(editor.$txt.find('.add_question_e').css("fontSize").slice(0,-2));
            if (initLength > 64) {
                return false;
            }
            initLength++;
            editor.$txt.find('.add_question_e').css("width",function(index,value){return (fontSize * 0.5)*initLength
            });
            editor.$txt.find('.add_question_e').attr("length", initLength);
            // $(menu.$domNormal).find('.editor_menu_e').html("填空题("+initLength+")");
            return false;
        });
        // 长度减少事件绑定
        $(menu.$domNormal).on("click",".menu_e_length .reduce",function(event) {
            event.stopPropagation();
            // 没有题目则直接返回
            if (!editor.$txt.find('.add_question_e').length) {
                return false;
            }
            var attrLength = editor.$txt.find('.add_question_e').attr("length");
            if (attrLength == "NaN" || attrLength == "undefined") {
                initLength = 7;
            }else{
                initLength = attrLength;
            }
            // 文字大小
            var fontSize = Number(editor.$txt.find('.add_question_e').css("fontSize").slice(0,-2));
            if (initLength < 8) {
                return false;
            }
            initLength--;
            editor.$txt.find('.add_question_e').css("width",function(index,value){return (fontSize * 0.5)*initLength
            });
            editor.$txt.find('.add_question_e').attr("length", initLength);
            // $(menu.$domNormal).find('.editor_menu_e').html("填空题("+initLength+")");
            return false;
        });
        // 增加到editor对象中
        editor.menus[menuId] = menu;
    });

})();
// 连线题 菜单
(function () {
    var E = window.wangEditor;
    var $ = window.jQuery;
    var initLength = 7;
    E.createMenu(function (check) {
        var menuId = 'menuL';
        if (!check(menuId)) {
            return;
        }
        var editor = this;
        // 创建 menu 对象
        var menu = new E.Menu({
            editor: editor,  // 编辑器对象
            id: menuId,  // 菜单id
            title: '连线题', // 菜单标题
            // 正常状态和选中状态下的dom对象，样式需要自定义
            $domNormal: $('<a href="#" tabindex="-1">'+
                    '<i class="editor_menu_l">连线题</i>'+
                    '<div class="menu_l_length">'+
                        '<button class="add">+</button>'+
                        '<button class="reduce">-</button>'+
                    '</div>'+
                '</a>'),
            $domSelected: $('<a href="#" tabindex="-1"><i class="editor_menu_l"></i></a>')
        });
        // 菜单正常状态下，点击将触发该事件
        menu.clickEvent = function (e) {
            if (!editor.$txt.find('.add_question_l').length||!editor.$txt.find('.add_question_l').attr("length")) {
                initLength = 7;
            }else{
                initLength = editor.$txt.find('.add_question_e').attr("length");
            }
            var s = "<input class='add_question_l' type='text' style='width:calc(0.5em * "+initLength+")' length='"+initLength+"' />";
            var sScore = "<input type='number' step='0.5' class='score' />&nbsp;"
            // 判断是否有其他类型的题目并执行插入的命令
            if (editor.$txt.find('.add_question_e').length > 0||editor.$txt.find('.add_question_c').length > 0||editor.$txt.find('.add_question_j').length > 0) {
                editor.$txt.find('.add_question_e,.add_question_c').fadeOut().css("backgroundColor","#B9EFCF").fadeIn(function(){
                    $(this).css("backgroundColor","unset")
                });
            }else{
                // 禁用其他题型菜单
                editor.menus.menuC.disabled(true);
                editor.menus.menuE.disabled(true);
                editor.menus.menuJ.disabled(true);
                editor.menus.menuA.disabled(true);

                editor.command(e, 'insertHtml', s);
                editor.command(e, 'insertHtml', sScore);
            }
        };
        // 长度增加事件绑定
        $(menu.$domNormal).on("click",".menu_l_length .add",function(event) {
            event.stopPropagation();
            // 没有题目则直接返回
            if (!editor.$txt.find('.add_question_l').length) {
                return false;
            }
            var attrLength = editor.$txt.find('.add_question_l').attr("length");
            if (attrLength == "NaN" || attrLength == "undefined") {
                initLength = 7;
            }else{
                initLength = attrLength;
            }
            // 文字大小
            var fontSize = Number(editor.$txt.find('.add_question_l').css("fontSize").slice(0,-2));
            if (initLength > 64) {
                return false;
            }
            initLength++;
            editor.$txt.find('.add_question_l').css("width",function(index,value){return (fontSize * 0.5)*initLength
            });
            editor.$txt.find('.add_question_l').attr("length", initLength);
            return false;
        });
        // 长度减少事件绑定
        $(menu.$domNormal).on("click",".menu_l_length .reduce",function(event) {
            event.stopPropagation();
            // 没有题目则直接返回
            if (!editor.$txt.find('.add_question_l').length) {
                return;
            }
            var attrLength = editor.$txt.find('.add_question_l').attr("length");
            if (attrLength == "NaN" || attrLength == "undefined") {
                initLength = 7;
            }else{
                initLength = attrLength;
            }
            // 文字大小
            var fontSize = Number(editor.$txt.find('.add_question_l').css("fontSize").slice(0,-2));
            if (initLength < 8) {
                return false;
            }
            initLength--;
            editor.$txt.find('.add_question_l').css("width",function(index,value){return (fontSize * 0.5)*initLength;
            });
            editor.$txt.find('.add_question_l').attr("length", initLength);
            return false;
        });
        // 增加到editor对象中
        editor.menus[menuId] = menu;
    });
})();
// 判断题 菜单
(function () {
    var E = window.wangEditor;
    var $ = window.jQuery;
    E.createMenu(function (check) {
        var menuId = 'menuJ';
        if (!check(menuId)) {
            return;
        }
        var editor = this;
        // 创建 menu 对象
        var menu = new E.Menu({
            editor: editor,  // 编辑器对象
            id: menuId,  // 菜单id
            title: '判断题', // 菜单标题

            // 正常状态和选中状态下的dom对象，样式需要自定义
            $domNormal: $('<a href="#" tabindex="-1"><i class="editor_menu_j">判断题</i></a>'),
            $domSelected: $('<a href="#" tabindex="-1" class="selected"><i class="editor_menu_j"></i></a>')
        });
        // 菜单正常状态下，点击将触发该事件
        menu.clickEvent = function (e) {
            var s = "<div class='add_question_j' contenteditable='false'>"+
                "<span class='close_q'></span>"+
                "<div class='j_item'>"+
                    "<label class='sky_radio'>"+
                        "<input type='radio' name='radio' />"+
                        "<span class='mark'></span>True &nbsp;&nbsp;"+
                        "<span class='cnt'></span>"+
                    "</label>"+
                "</div>"+
                "<div class='j_item'>"+
                    "<label class='sky_radio'>"+
                        "<input type='radio' name='radio' />"+
                        "<span class='mark'></span>False &nbsp;&nbsp;"+
                        "<span class='cnt'></span>"+
                    "</label>"+
                "</div>"+
                "<div class='score'><input type='number' placeholder='请填写分数' /></div>"+
            "</div>"
            // 判断是否有其他类型的题目并执行插入的命令
            if (editor.$txt.find('.add_question_e').length > 0||editor.$txt.find('.add_question_c').length > 0||editor.$txt.find('.add_question_l').length > 0||editor.$txt.find('.add_question_j').length > 0) {
                editor.$txt.find('.add_question_e,.add_question_l').fadeOut().css("backgroundColor","#B9EFCF").fadeIn(function(){
                    $(this).css("backgroundColor","unset")
                });
            }else{
                // 禁用其他题型菜单
                editor.menus.menuC.disabled(true);
                editor.menus.menuE.disabled(true);
                editor.menus.menuL.disabled(true);
                editor.menus.menuA.disabled(true);

                editor.$txt.append(s);
            }
        };
        // 删除整个判断题
        $(".editor").on('click', '.close_q', function(event) {
          $(this).parent().slideUp(function(){
            $(this).remove();
            editor.enableMenusExcept();
          });
        });
        // 增加到editor对象中
        editor.menus[menuId] = menu;
    });
})();
// json 菜单
(function () {
    var E = window.wangEditor;
    var $ = window.jQuery;
    E.createMenu(function (check) {
        var menuId = 'json';
        if (!check(menuId)) {
            return;
        }
        var editor = this;
        var lang = editor.config.lang;
        var txtHtml;
        // 创建 menu 对象
        var menu = new E.Menu({
            editor: editor,  // 编辑器对象
            id: menuId,  // 菜单id
            title: 'JSON数组', // 菜单标题
            // 正常状态和选中状态下的dom对象，样式需要自定义
            $domNormal: $('<a href="#" tabindex="-1"><i class="editor_menu_json"></i></a>'),
            $domSelected: $('<a href="#" tabindex="-1" class="selected"><i class="editor_menu_json"></i></a>')
        });

        // 菜单正常状态下，点击将触发该事件
        menu.clickEvent = function (e) {
            var self = this;
            var editor = self.editor;
            var $txt = editor.txt.$txt;
            var txtOuterHeight = $txt.outerHeight();
            var txtHeight = $txt.height();

            if (!self.$codeTextarea) {
                self.$codeTextarea = $('<div class="code-textarea"style="outline:none;overflow-y:scroll;"></div>');
            }
            var $code = self.$codeTextarea;
            $code.css({
                height: txtHeight,
                'margin-top': txtOuterHeight - txtHeight
            });
            // 记录当前html值
            txtHtml = $txt.html().replace(/<div style="text-align: left;">/g,"");
            // 赋值
            $code.JSONView(editorToJSON());
            // 渲染
            $txt.after($code).hide();
            $code.show();
            // 更新状态
            menu.isShowCode = true;
            // 执行 updateSelected 事件
            this.updateSelected();
            // 禁用其他菜单
            editor.disableMenusExcept('json');
        };
        // 菜单选中状态下，点击将触发该事件
        menu.clickEventSelected = function (e) {
            var self = this;
            var editor = self.editor;
            var $txt = editor.txt.$txt;
            var $code = self.$codeTextarea;
            var value;
            if (!$code) {
                return;
            }
            // 渲染
            $code.after($txt).hide();
            $txt.show();
            $txt.html(txtHtml);
            // 更新状态
            menu.isShowCode = false;
            // 执行 updateSelected 事件
            this.updateSelected();
            // 启用其他菜单
            editor.enableMenusExcept('json');
            // 判断是否执行 onchange 事件
            if ($txt.html() !== txtHtml) {
                if (editor.onchange && typeof editor.onchange === 'function') {
                    editor.onchange.call(editor);
                }
            }
        };
        // 自定义更新菜单的选中状态或者正常状态
        menu.updateSelectedEvent = function () {
            return this.isShowCode;
        }
        // 增加到editor对象中
        editor.menus[menuId] = menu;
    });
})();

// 音频 菜单
(function () {
    var E = window.wangEditor;
    var $ = window.jQuery;
    E.createMenu(function (check) {
        var menuId = 'audio';
        if (!check(menuId)) {
            return;
        }
        var editor = this;
        var lang = editor.config.lang;
        var txtHtml;
        // 创建 menu 对象
        var menu = new E.Menu({
            editor: editor,  // 编辑器对象
            id: menuId,  // 菜单id
            title: '音频', // 菜单标题
            // 正常状态和选中状态下的dom对象，样式需要自定义
            $domNormal: $('<a href="#" tabindex="-1"><i class="editor_menu_audio"></i></a>'),
            $domSelected: $('<a href="#" tabindex="-1" class="selected"><i class="editor_menu_audio"></i></a>')
        });

        // panel 内容
        var $container = $('<div class="audio_panel">'+
        		'<div class="audio_box"></div>'+
                '<form id="uploadForm" enctype="multipart/form-data">'+
                '<label class="add_audio"><input type="file" name="file" accept="audio/*"/></label>'+
                '</form>'+
                '<progress class="audio_progress" value="0"></progress>'+
            '</div>');

        // 添加panel
        menu.dropPanel = new E.DropPanel(editor, menu, {
            $content: $container,
            width: 350
        });

        //音频添加事件
        $(".editor").parent().on('change', '.add_audio input', function(event) {
            upload(this.files[0] ? this.files[0].name:'', this);
            // $(".audio_panel .audio_box").append('<div class="space">'+
            //     '<input type="number" placeholder="音频间隔时间(秒)" />'+
            //     '</div>'+
            //     '<div class="audio_item">'+
            //         '<audio src="'+window.URL.createObjectURL(this.files[0])+'" name="'+this.files[0].name+'" controls="controls" width="100%"></audio>'+
            //         '<span class="remove_btn"></span>'+
            //     '</div>');
            // $(this).val('');
        });
        // 音频上传时间
        function upload(fileName, dom) {
        	var uploadResult;
            //创建FormData对象，初始化为form表单中的数据。需要添加其他数据可使用formData.append("property", "value");
            var formData = new FormData($('#uploadForm')[0]);
            //进度条置零
            $('.audio_progress').attr("value","0");

            //ajax异步上传
            $.ajax({
                url: "/task/upload/temp.jhtml",
                type: "POST",
                data: formData,
                xhr: function(){ //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数

                    myXhr = $.ajaxSettings.xhr();
                    if(myXhr.upload){ //检查upload属性是否存在
                        //绑定progress事件的回调函数
                        myXhr.upload.addEventListener('progress',progressHandlingFunction, false);
                    }
                    return myXhr; //xhr对象返回给jQuery使用
                },
                success: function(result){
                    $(".audio_panel .audio_box").append('<div class="space">'+
                        '<input type="number" value="" placeholder="音频间隔时间(秒)" />'+
                        '</div>'+
                        '<div class="audio_item">'+
                            '<audio src="'+result+'" name="'+fileName+'" controls="controls" width="100%"></audio>'+
                            '<span class="remove_btn"></span>'+
                        '</div>');
                    $(dom).val('');
                },
                contentType: false, //必须false才会自动加上正确的Content-Type
                processData: false  //必须false才会避开jQuery对 formdata 的默认处理
            });
        }
        //上传进度回调函数：
        function progressHandlingFunction(e) {
            if (e.lengthComputable) {
                $('.audio_progress').attr({value : e.loaded, max : e.total}); //更新数据到进度条
            }
        }

        // 删除音频
        $(".editor").parent().on('mousedown', '.audio_item .remove_btn', function(event) {
            $(this).parent().prev('.space').remove();
            $(this).parent().remove();
        });
        // 增加到editor对象中
        editor.menus[menuId] = menu;
    });
})();

// 解析 菜单
(function () {
    var E = window.wangEditor;
    var $ = window.jQuery;
    E.createMenu(function (check) {
        var menuId = 'analy';
        if (!check(menuId)) {
            return;
        }
        var editor = this;
        var lang = editor.config.lang;
        var txtHtml;
        // 创建 menu 对象
        var menu = new E.Menu({
            editor: editor,  // 编辑器对象
            id: menuId,  // 菜单id
            title: '解析', // 菜单标题
            // 正常状态和选中状态下的dom对象，样式需要自定义
            $domNormal: $('<a href="#" tabindex="-1"><i class="editor_menu_analy">解析</i></a>'),
            $domSelected: $('<a href="#" tabindex="-1" class="selected"><i class="editor_menu_analy"></i></a>')
        });

        // panel 内容
        var $container = $('<textarea class="ananly_cnt" placeholder="请输入试题解析"></textarea>');

        // 添加panel
        menu.dropPanel = new E.DropPanel(editor, menu, {
            $content: $container,
            width: 350
        });
        // 增加到editor对象中
        editor.menus[menuId] = menu;
    });
})();
// 音频题 菜单
(function () {
    var E = window.wangEditor;
    var $ = window.jQuery;
    E.createMenu(function (check) {
        var menuId = 'menuA';
        if (!check(menuId)) {
            return;
        }
        var editor = this;
        var lang = editor.config.lang;
        var txtHtml;
        // 创建 menu 对象
        var menu = new E.Menu({
            editor: editor,  // 编辑器对象
            id: menuId,  // 菜单id
            title: '音频题', // 菜单标题
            // 正常状态和选中状态下的dom对象，样式需要自定义
            $domNormal: $('<a href="#" tabindex="-1"><i class="editor_menu_a">音频题</i></a>'),
            $domSelected: $('<a href="#" tabindex="-1" class="selected"><i class="editor_menu_a"></i></a>')
        });

        // panel 内容
        var $container = $('<div class="add_question_a"><input class="" placeholder="请填写音频题分数" type="number" /></div>');

        // 添加panel
        menu.dropPanel = new E.DropPanel(editor, menu, {
            $content: $container,
            width: 200
        });
        $(".editor").parent().on('input', '.add_question_a input', function(event) {
            event.preventDefault();
            if ($(this).val()) {
                // 禁用其他题型菜单
                editor.menus.menuC.disabled(true);
                editor.menus.menuE.disabled(true);
                editor.menus.menuL.disabled(true);
                editor.menus.menuJ.disabled(true);
            }else{
                editor.enableMenusExcept('menuA');
            }
        });
        // 增加到editor对象中
        editor.menus[menuId] = menu;
    });
})();