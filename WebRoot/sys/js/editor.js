/*
 * @Author: Hlin
 * @Date:   2017-05-29 15:16:41
 */

// 初始化
var editor = new wangEditor('editor');

// 上传图片（举例）
editor.config.uploadImgUrl = '/task/upload/temp.jhtml';

// 过滤样式
editor.config.pasteFilter = true;
// 只粘贴纯文本
editor.config.pasteText = true;
// 隐藏网络图片
editor.config.hideLinkImg = true;
// 菜单吸顶：false - 不吸顶；number - 吸顶，值为top值
editor.config.menuFixed = 0;
//关闭log打印功能
wangEditor.config.printLog = false;

// 菜单
editor.config.menus = [
    // 'source',
    'json',
    // '|',
    // 'undo',
    // 'redo',
    // '|',
    "menuC",
    "menuE",
    "menuL",
    "menuJ",
    "menuA",
    '|',
    'img',
    'audio',
    '|',
    'analy'
];
editor.create();

// 隐藏json
var jsonMenu = editor.menus.json.$domNormal.parent();
jsonMenu.hide();

// 监控input输入变化并给其value赋值
$(".editor_box").on('input', 'input', function (event) {
    event.preventDefault();
    $(this).attr("value", $(this).val())
    $(this).attr("title", $(this).val())
});
// 监控判断题checkbox变化
$("#editor").on('change', 'input[type="checkbox"]', function (event) {
    event.preventDefault();
    if ($(this).prop("checked")) {
        $(this).attr('checked', "checked");
    } else {
        $(this).removeAttr('checked');
    }
});
// 监控选择题radio变化
$("#editor").on('change', 'input[type="radio"]', function (event) {
    event.preventDefault();
    $(this).attr('checked', "checked");
    $(this).parents(".j_item").siblings('.j_item').find('input[type="radio"]').removeAttr('checked')
});
// 监控editor输入变化执行事件
$("#editor").on('input', function () {
    // 监控编辑器中是否有录入选择题、填空题或连线题，若没有则恢复所有题型菜单的使用
    if (!editor.$txt.find('.add_question_e').length && !editor.$txt.find('.add_question_c').length && !editor.$txt.find('.add_question_l').length && !editor.$txt.find('.add_question_j').length && !editor.$txt.parent().find(".add_question_a input").val() && $(".submit_item_btn").attr("name") != "q_item0") {
        editor.enableMenusExcept();
    }
});
// 模拟点击生成初始化panel
$(".editor_menu_a").click();
$(".editor_menu_audio").click();
$(".wangeditor-menu-img-picture0").click();
$(".editor_menu_analy").click();

if ($(".q_box .q_item").length) {
    $(".add_q_item").css("cursor", "pointer").attr("disabled", false);
}

// 编辑
$(".q_box").on('click', '.editor_btn', function (event) {
    event.preventDefault();
    // if (editor.txt.$txt.html() != '<p><br></p>') {
    //     $(".q_box .q_item[name='"+$(".editor_box .submit_item_btn").attr("name")+"']").find(".q_item_cnt .q_item_cnt_").html(editor.txt.$txt.html());
    // }
    // 展开编辑器
    $(".editor_box").hide();
    $(".editor_box").find(".submit_item_btn").attr("name", $(this).parents(".q_item").attr("name"));
    // 赋予该小题编辑内容
    $(".editor_box .editor").html($(this).parents(".q_item").find(".q_item_cnt .q_item_cnt_").html())
    $(".editor_box .ananly_cnt").val($(this).parents(".q_item").find(".q_item_cnt .q_item_cnt_analy").html())
    $(".editor_box .audio_panel .audio_box").html($(this).parents(".q_item").find(".q_item_cnt .q_item_cnt_audio").html())
    $(".editor_box .qusetion_time input").val($(this).parents(".q_item").find(".q_item_cnt .q_item_cnt_time").html())
    $(".editor_box .add_question_a input").val($(this).parents(".q_item").find(".q_item_cnt .q_item_question_a").html())
    $(this).parents(".q_item").after($(".editor_box").slideDown())
    // 开启小标题编辑状态
    $(this).parents(".q_item").find(".q_item_title .title_cnt").replaceWith(function () {
        return $("<input class='title_cnt' type='text' value='" + $(this).html() + "'/>");
    });
    // 禁止其他小题编辑
    $(this).parents(".q_item").siblings('.q_item').find(".q_item_oper button").css("cursor", "not-allowed").attr("disabled", true);
    //禁止当前小题再次编辑
    $(this).css("cursor", "not-allowed").attr("disabled", true);
    // 禁止添加小题
    $(".add_q_item").css("cursor", "not-allowed").attr("disabled", true);

    //根据题型启动菜单
    editor.menus.menuC.disabled(true);
    editor.menus.menuE.disabled(true);
    editor.menus.menuL.disabled(true);
    editor.menus.menuJ.disabled(true);
    editor.menus.menuA.disabled(true);
    if (editor.$txt.find(".add_question_c").length) {
        editor.menus.menuC.disabled(false);
    } else if (editor.$txt.find(".add_question_e").length) {
        editor.menus.menuE.disabled(false);
    } else if (editor.$txt.find(".add_question_l").length) {
        editor.menus.menuL.disabled(false);
    } else if (editor.$txt.find(".add_question_j").length) {
        editor.menus.menuJ.disabled(false);
    } else if (editor.$txt.parent().find(".add_question_a input").val()) {
        editor.menus.menuA.disabled(false);
    } else {
        editor.enableMenusExcept();
    }
    //如果展开的是组合题题干，则不能添加题型
    if (!$(this).siblings('button').length && $(".q_box .q_item").length > 1) {
        editor.menus.menuC.disabled(true);
        editor.menus.menuE.disabled(true);
        editor.menus.menuL.disabled(true);
        editor.menus.menuJ.disabled(true);
        editor.menus.menuA.disabled(true);
        $(".editor_box .editor").width(639);
    }else if($(".q_box .q_item").length == 1){
        $(".editor_box .editor").width(639);
    }else{
        $(".editor_box .editor").width(678);
    }
});
// 校验按钮
var checkInfoList = new Array();
var alertCnt = "";
var questionTitle = new String; //每小题标题
$(document).keydown(function (e) {
    if (e.keyCode == 27 && $(".editor_box .submit_item_btn").is(':visible') && !$(".alert_box").length) {
        $(".editor_box .submit_item_btn").click();
    }
});
$(".editor_box").on("click", ".submit_item_btn", function (event) {
    if (questionCheck($(".editor_box"))) {
        $.each(checkInfoList, function (index, el) {
            alertCnt += checkInfoList[index] + "<br>";
        });
        if ($(".q_item").length) {
            if ($(".q_box .q_item[name='" + $(this).attr("name") + "']").length) {
                // 如何编辑器的name与小题的name对上了，则将编辑器内容保存到该小题中
                questionTitle = $(".q_box .q_item[name='" + $(this).attr("name") + "'] .q_item_title .title_cnt").val();
                $(".q_box .q_item[name='" + $(this).attr("name") + "']").find(".q_item_cnt .q_item_cnt_").html(editor.txt.$txt.html().replace(/<div style="text-align: left;">/g,""));
                $(".q_box .q_item[name='" + $(this).attr("name") + "']").find(".q_item_cnt .q_item_cnt_analy").html($(".ananly_cnt").val());
                $(".q_box .q_item[name='" + $(this).attr("name") + "']").find(".q_item_cnt .q_item_cnt_audio").html($(".audio_panel .audio_box").html());
                $(".q_box .q_item[name='" + $(this).attr("name") + "']").find(".q_item_cnt .q_item_cnt_time").html($(".qusetion_time input").val());
                $(".q_box .q_item[name='" + $(this).attr("name") + "']").find(".q_item_cnt .q_item_question_a").html($(".add_question_a input").val());
                $(".q_box .q_item[name='" + $(this).attr("name") + "']").find(".q_item_title .check_true_").html("校验通过");
                $(".q_box .q_item[name='" + $(this).attr("name") + "']").find(".q_item_title .analy_false").html(checkInfoList[0] || "");
                $(".q_box .q_item[name='" + $(this).attr("name") + "']").find(".q_item_json").html(editorToJSON());
                // 收起编辑器
                $(".editor_box").hide();
                // $(".editor_box").slideUp(300,function(){
                //     $(".editor_box").find(".editor").html('<p><br></p>');
                // });
                // 关闭小标题编辑
                $(".q_box .q_item[name='" + $(this).attr("name") + "']").find(".q_item_title .title_cnt").replaceWith(function () {
                    return $("<span class='title_cnt'>" + $(this).val() + "</span>");
                });
                //恢复所有小题编辑
                $(".q_box .q_item_oper button").css("cursor", "pointer").attr("disabled", false);
                // 恢复小题添加
                $(".add_q_item").css("cursor", "pointer").attr("disabled", false);
            }
        } else {
            //保存题干
            $(".q_box").append('<div class="q_item q_item0" name="q_item0">' +
                '<div class="q_item_title"><span class="title_index">题干.</span><span class="title_cnt">' + $(".q_editor .title input").val() + '</span>' +
                '<div class="check_true">' +
                '<span class="check_true_">校验通过</span>&nbsp;&nbsp;' +
                '<span class="analy_false">' + checkInfoList[0] + '</span>' +
                '</div>' +
                '</div>' +
                '<div class="q_item_cnt hide">' +
                '<div class="q_item_cnt_">' + editor.txt.$txt.html().replace(/<div style="text-align: left;">/g,"") + '</div>' +
                '<div class="q_item_cnt_analy">' + $(".ananly_cnt").val() + '</div>' +
                '<div class="q_item_cnt_audio">' + $(".audio_panel .audio_box").html() + '</div>' +
                '<div class="q_item_cnt_time">' + $(".qusetion_time input").val() + '</div>' +
                '<div class="q_item_question_a">' + $(".add_question_a input").val() + '</div>' +
                '</div>' +
                '<div class="q_item_json hide">' + editorToJSON() + '</div>' +
                '<div class="q_item_oper hide">' +
                '<button class="editor_btn" style="border-right:0;">编辑</button>' +
                '</div>' +
                '</div>');
            $(".editor_box").hide();
            // 恢复小题添加
            $(".add_q_item").css("cursor", "pointer").attr("disabled", false);
        }
        checkInfoList = [];
        alertCnt = ""
    } else {
        checkInfoList.push("<font style='color:red'>校验不通过！</font>")
        $.each(checkInfoList, function (index, el) {
            alertCnt += checkInfoList[index] + "<br>";
        });
        skyAlert.success(alertCnt);
        checkInfoList = [];
        alertCnt = ""
    }
});
// 修改层次
$(".alter_level").click(function(event) {
    $(".filter_list").css("marginBottom",10).show();
});
//添加子题
$(".add_q_item").click(function (event) {
    var addCheck = $(".q_box .q_item0 .q_item_cnt .q_item_cnt_");
    if (addCheck.find(".add_question_c").length || addCheck.find(".add_question_e").length || addCheck.find(".add_question_l").length || addCheck.find(".add_question_j").length || addCheck.parent().find(".q_item_question_a").html()) {
        skyAlert.success("该题不属于组合题，因为题干中已经包含其他题型，不能再添加小题！");
        return;
    }
    $(".q_box").append('<div class="q_item" name="q_item' + ($(".q_box .q_item").length + 1) + '">' +
        '<div class="q_item_title"><span class="title_index">' + ($(".q_box .q_item").length) + '.</span><span class="title_cnt">新增小题</span>' +
        '<div class="check_true">' +
        '<span class="check_true_"></span>&nbsp;&nbsp;' +
        '<span class="analy_false"></span>' +
        '</div>' +
        '</div>' +
        '<div class="q_item_cnt hide">' +
        '<div class="q_item_cnt_"><p><br></p></div>' +
        '<div class="q_item_cnt_analy"></div>' +
        '<div class="q_item_cnt_audio"></div>' +
        '<div class="q_item_cnt_time"></div>' +
        '<div class="q_item_question_a"></div>' +
        '</div>' +
        '<div class="q_item_json hide"></div>' +
        '<div class="q_item_oper hide">' +
        '<button class="editor_btn">编辑</button>' +
        '<button class="delete_btn">删除</button>' +
        '</div>' +
        '</div>');
});
//删除子题
var qItemIdList = [];
$(".q_box").on('click', '.delete_btn', function (event) {
    var deleteThis = this;
    event.preventDefault();
    skyAlert.alert({
        content: "删除将无法恢复，是否删除？",
        cancel: function () {},
        confirm: function () {
            qItemIdList.push($(deleteThis).parents(".q_item").attr("id"));
            $(deleteThis).parents(".q_item").remove();
            $(".q_box .q_item_title .title_index:not(.title_index:first)").each(function (index, el) {
                $(el).html((index + 1) + ".");
            });
            //若删除的小题处于编辑状态，则清空编辑器
            if ($(deleteThis).parents(".q_item").attr("name") == $(".editor_box .submit_item_btn").attr("name")) {
                $(".editor_box").find(".editor").html('<p><br></p>');
            }
            //恢复所有小题编辑
            $(".q_box .q_item_oper button").css("cursor", "pointer").attr("disabled", false);
            // 恢复小题添加
            $(".add_q_item").css("cursor", "pointer").attr("disabled", false);
            // 判断最后一个小题删除后显示编辑器
            $(".editor_box").slideUp();
        }
    });
});
// 标题与题干内容相互绑定
$(".q_editor").on('input', '.q_item0 .q_item_title .title_cnt', function (event) {
    event.preventDefault();
    $(".q_editor .title input").val($(this).val())
});
$(".q_editor").on('input', '.title input', function (event) {
    event.preventDefault();
    if ($(".q_item0 .q_item_title input").length) {
        $(".q_item0 .q_item_title .title_cnt").val($(this).val())
    } else {
        $(".q_item0 .q_item_title .title_cnt").html($(this).val())
    }
});

// 将editor中的内容转化为json
function editorToJSON() {
    var cnt = editor.$txt[0]; //编辑器内容
    var jsonArray = new Object(); //与后台交互的json对象组

    // 基础属性
    var questionId = "";
    var standTime = 0; //答题时间
    var score = 0; //题目分数
    var analysis = new String(); //解析
    var questionType = new String(); //题目类型
    var contents = new Array(); //题干内容
    var result = new Array(); //答案
    var audio = new Array(); //音频
    var tags = new Object(); //标签
    var indexId = 0;
    var answerLength = 7; // 填空题或者连线题长度，默认为7个字符长度(2017/9/11添加)


    $(cnt).children('p').each(function (index, el) {
        var PObject = new Object; //段落对象
        var Pcnt = new String(); //段落内容对象
        var Pimgs = new Array(); //图片对象集合
        var Pimg = new Object(); //段落图片对象
        var Pstyle = new Object(); //段落布局
        var PstyleAlign = 0;
        var Pstylepercent = 0;
        if (index + 1 == $(cnt).children('p').length && $(this).html() == "<br>") {
            return;
        }
        // 遍历每一段的图片
        $(this).find("img").each(function (index, el) {
            Pimg = {
                "url": $(this).attr("src"),
                "height": Math.round($(this).height()),
                "width": Math.round($(this).width()),
                "scale": null
            }
            Pimgs.push(Pimg);
        });

        // 段落布局
        if ($(this).find(".p_img").attr("class")) {
            switch ($(this).find(".p_img").attr("class").split(' ').pop()) {
                case "top_img":
                    PstyleAlign = 0;
                    $(this).find(".p_img").find("img").each(function (index, el) {
                        Pstylepercent = Pstylepercent > $(this).height() ? Pstylepercent : $(this).height();
                    });
                    Pstylepercent = (Pstylepercent / Number($(this).height())).toFixed(2);
                    break;
                case "bottom_img":
                    PstyleAlign = 1;
                    $(this).find(".p_img").find("img").each(function (index, el) {
                        Pstylepercent = Pstylepercent > $(this).height() ? Pstylepercent : $(this).height();
                    });
                    Pstylepercent = (Pstylepercent / Number($(this).height())).toFixed(2);
                    break;
                case "left_img":
                    PstyleAlign = 2;
                    $(this).find(".p_img").find("img").each(function (index, el) {
                        Pstylepercent = Pstylepercent > $(this).width() ? Pstylepercent : $(this).width();
                    });
                    Pstylepercent = (Pstylepercent / editor.$txt.width()).toFixed(2);
                    break;
                case "right_img":
                    PstyleAlign = 3;
                    $(this).find(".p_img").find("img").each(function (index, el) {
                        Pstylepercent = Pstylepercent > $(this).width() ? Pstylepercent : $(this).width();
                    });
                    Pstylepercent = (Pstylepercent / editor.$txt.width()).toFixed(2);
                    break;
            }
            // $(this).find(".p_img").find("img").each(function(index, el) {
            //     Pstylepercent += $(this).width();
            // });
        }
        Pstyle = {
            "align": PstyleAlign,
            "percent": Pstylepercent,
            "totalwidth": editor.$txt.width()
        };
        // 填空题
        $(this).find(".add_question_e").each(function (index, el) {
            indexId++;
            $(this).before("<@T id='" + indexId + "' score='" + $(this).next(".score").val() + "'>" + $(this).val() + "<@/T>");

            result.push({
                "sortNo": indexId,
                "anwer": $.trim($(this).val()),
                "score": $.trim($(this).next(".score").val())
            });

            questionType = "3";
            score += Number($(this).next(".score").val());

            answerLength = Number($(this).attr("length"));
        });

        // 连线题
        $(this).find(".add_question_l").each(function (index, el) {
            indexId++;
            $(this).before("<@T id='" + indexId + "' score='" + $(this).next(".score").val() + "'>" + $(this).val() + "<@/T>");

            result.push({
                "sortNo": indexId,
                "anwer": $.trim($(this).val()),
                "score": $.trim($(this).next(".score").val())
            });
            questionType = "4";
            score += Number($(this).next(".score").val());

            answerLength = Number($(this).attr("length"));
        });

        // 段落内容
        $(this).html(function(index,oldcontent){
            var returnCnt = oldcontent.replace(/\n/g,' ').replace(/\t/g,'').replace(/<br>/g,'\n');
            if (returnCnt.indexOf("\n<label") >= 0) {
                returnCnt = returnCnt.replace(/\n<label/g,"<label");
            }
            if (returnCnt.indexOf("\n",returnCnt.length - 1) >= 0) {
                return returnCnt.substring(0,returnCnt.length - 1)
            }
            return returnCnt;
        })
        Pcnt = $(this).text();
        PObject = {
            "index": index + 1,
            "content": Pcnt,
            "imgs": Pimgs,
            "style": Pstyle
        };
        contents.push(PObject);

        PObject = {};
        Pcnt = null;
        Pimg = null;
        Pimgs = [];
        Pstyle = null;
    });
    // 遍历选择题答案
    var qcAnswer; //选择题答案
    var qcScore = 0;
    var qcAnswerTrueIndex = 0;
    $(cnt).find(".add_question_c .c_item").each(function (index, el) {
        if ($(this).find(".sky_checkbox input").prop("checked")) {
            score = Number($.trim($(cnt).find(".add_question_c .score input").val()));
            qcScore = score;
            qcAnswer = 1;
            qcAnswerTrueIndex++;
        } else {
            qcAnswer = 0;
            qcScore = 0;
        }
        result.push({
            "sortNo": $(this).find(".sky_checkbox").text(),
            "content": $.trim($(this).find(".option").val()),
            "score": qcScore,
            "answer": qcAnswer
        });
        if (qcAnswerTrueIndex > 1) {
            questionType = "2";
        } else {
            questionType = "1";
        }
    });
    //音频题
    if ($(cnt).parent().find(".add_question_a input").val()) {
        score = Number($(cnt).parent().find(".add_question_a input").val());
        questionType = "5";
    }

    //遍历判断题答案
    var qjScore = 0;
    var qjAnswer = 0;
    $(cnt).find(".add_question_j .j_item").each(function (index, el) {
        if ($(this).find(".sky_radio input").attr("checked")) {
            score = Number($.trim($(cnt).find(".add_question_j .score input").val()));
            qjScore = score;
            qjAnswer = 1;
        }

        result.push({
            "sortNo": $(this).find(".sky_radio").text().substring(0, 1) == "T"?"True":"False",
            "content": $.trim($(this).find(".cnt").html()),
            "score": qjScore,
            "answer": qjAnswer
        });
        qjAnswer = 0;
        qjScore = 0;
        questionType = "6";
    });

    // 遍历音频
    $(cnt).parent().find(".audio_panel .audio_item").each(function (index, el) {
        audio.push({
            "name": $(this).find("audio").attr("name").split('/').pop(),
            "url": $(this).find("audio").attr("src"),
            "time": Math.ceil($(this).find("audio")[0].duration),
            "wait": $(this).prev(".space").find("input").val()
        });
    });
    // 获取questionId
    if ($(".q_box .q_item[name='" + $(".editor_box .submit_item_btn").attr("name") + "']").attr("id")) {
        questionId = $(".q_box .q_item[name='" + $(".editor_box .submit_item_btn").attr("name") + "']").attr("id");
    }

    jsonArray["questionId"] = questionId;
    jsonArray["questionTitle"] = questionTitle != "" ? questionTitle : $(".q_editor .title input").val();
    jsonArray["standTime"] = $(".qusetion_time input").val();
    jsonArray["score"] = score;
    jsonArray["analysis"] = analysis != "" ? analysis : $(".ananly_cnt").val();
    jsonArray["questionType"] = questionType != "" ? questionType : "0";
    jsonArray["answerLength"] = answerLength ? answerLength : 7;
    jsonArray["contents"] = contents;
    jsonArray["result"] = result;
    jsonArray["audio"] = audio;
    jsonArray["tags"] = {
        "tags": 476
    };

    return JSON.stringify(jsonArray);
}

// 试题校验
function questionCheck($editor_box) {
    //标题校验
    var questionTitleCheck = (function () {
        if ($(".q_editor .title input").val()) {
            return true;
        } else {
            checkInfoList.push("<font style='color:red'>没有填写标题！");
            return false;
        }
    })()
    //试题时间校验
    var standTimeCheck = (function () {
        if ($editor_box.find(".qusetion_time input").val() <= 0 || $.trim($editor_box.find(".qusetion_time input").val()).indexOf(".") != -1) {
            checkInfoList.push("<font style='color:red'>答题时间填写不正确！(只能填写正整数)</font>");
            return false;
        }
        if ($editor_box.find(".qusetion_time input").val()) {
            return true;
        } else {
            checkInfoList.push("<font style='color:red'>答题时间没有填写！</font>");
            return false;
        }
    })()
    // 图片位置校验
    var imgPositionCheck = (function () {
        if ($editor_box.find(".editor p>img").length > 0) {
            checkInfoList.push("<font style='color:red'>图片位置错误！<font style='color:red'>！有图片未选择放置位置");
            return false;
        } else {
            return true;
        }
    })()
    //音频间隔校验
    var audioWaitCheck = (function () {
        if (!$editor_box.find(".audio_panel .space input").length) {
            return true;
        }
        var audioSpace = 0;
        $editor_box.find(".audio_panel .space input").each(function (index, el) {
            if ($(this).val()) {
                audioSpace += 1;
            }
            if ($.trim($(this).val()).substring(0, 1) == "0" && $.trim($(this).val()).substring(1, 2) != ".") {
                audioSpace--;
            }
        });
        if (audioSpace == $editor_box.find(".audio_panel .space input").length) {
            return true;
        } else {
            checkInfoList.push("<font style='color:red'>音频间隔时间填写不正确！</font>")
            return false;
        }
    })()
    //选择题校验
    var qcCheck = (function () {
        //若题目不是选择题，直接跳过检测
        if (!$editor_box.find(".editor .add_question_c").length) {
            return true;
        }
        var optionIndexCheck = (function () {
            var checkedIndex = 0;
            $editor_box.find(".editor .add_question_c .c_item .sky_checkbox input").each(function (index, el) {
                if ($(this).prop("checked")) {
                    checkedIndex++;
                }
            });
            if (checkedIndex > 0) {
                return true;
            } else {
                checkInfoList.push("<font style='color:red'>选择题没有选择答案！</font>")
                return false;
            }
        })()
        var optionCntCheck = (function () {
            var optionIndex = 0;
            $editor_box.find(".editor .add_question_c .c_item .option").each(function (index, el) {
                if (!$(this).val()) {
                    optionIndex++;
                }
            });
            if (optionIndex > 0) {
                checkInfoList.push("<font style='color:red'>选择题选项内容没有填写完整！</font>")
                return false;
            } else {
                return true;
            }
        })()
        var scoreCheck = (function () {
            if (!$editor_box.find(".editor .add_question_c .score input").val() || ($.trim($editor_box.find(".editor .add_question_c .score input").val()).substring(0, 1) == "0" && $.trim($editor_box.find(".editor .add_question_c .score input").val()).substring(1, 2) != ".")) {
                checkInfoList.push("<font style='color:red'>选择题分数填写不正确！</font>");
                return false;
            } else {
                return true;
            }
        })()
        if (optionIndexCheck && optionCntCheck && scoreCheck) {
            return true;
        } else {
            return false;
        }
    })();
    //填空题校验
    var qeCheck = (function () {
        //若题目不是填空题，直接跳过检测
        if (!$editor_box.find(".editor .add_question_e").length) {
            return true;
        }
        var answerIndex = 0;
        var answerCheck = (function () {
            answerIndex = 0;
            $editor_box.find(".editor .add_question_e").each(function (index, el) {
                if ($.trim($(this).val())) {
                    answerIndex++;
                }
            });
            if ($editor_box.find(".editor .add_question_e").length != answerIndex) {
                checkInfoList.push("<font style='color:red'>填空题答案填写不完整！</font>");
                return false;
            } else {
                return true;
            }
        })()
        var scoreCheck = (function () {
            var scoreIndex = 0;
            $editor_box.find(".editor .add_question_e").next(".score").each(function (index, el) {
                if ($.trim($(this).val())) {
                    scoreIndex++;
                }
                if (Number($.trim($(this).val())) <= 0 || ($.trim($(this).val()).substring(0, 1) == "0" && $.trim($(this).val()).substring(1, 2) != ".")) {
                    scoreIndex--;
                }
            });
            if ($(".editor .add_question_e").next(".score").length != answerIndex||$(".editor .add_question_e").next(".score").length != scoreIndex) {
                checkInfoList.push("<font style='color:red'>填空题分数填写不正确！</font>");
                return false;
            } else {
                return true;
            }
        })()
        if (answerCheck && scoreCheck) {
            return true;
        } else {
            return false;
        }
    })();
    //连线题校验
    var qlCheck = (function () {
        //若题目不是连线题，直接跳过检测
        if (!$editor_box.find(".editor .add_question_l").length) {
            return true;
        }
        var answerIndex = 0;
        var answerCheck = (function () {
            answerIndex = 0;
            $editor_box.find(".editor .add_question_l").each(function (index, el) {
                if ($.trim($(this).val())) {
                    answerIndex++;
                }
            });
            if ($editor_box.find(".editor .add_question_l").length != answerIndex) {
                checkInfoList.push("<font style='color:red'>连线题答案填写不完整！</font>");
                return false;
            } else {
                return true;
            }
        })()
        var scoreCheck = (function () {
            var scoreIndex = 0;
            $editor_box.find(".editor .add_question_l").next(".score").each(function (index, el) {
                if ($.trim($(this).val())) {
                    scoreIndex++;
                }
                if (Number($.trim($(this).val())) <= 0 || ($.trim($(this).val()).substring(0, 1) == "0" && $.trim($(this).val()).substring(1, 2) != ".")) {
                    scoreIndex--;
                }
            });
            if ($(".editor .add_question_l").next(".score").length != answerIndex || $(".editor .add_question_l").next(".score").length != scoreIndex) {
                checkInfoList.push("<font style='color:red'>连线题分数填写不正确！</font>");
                return false;
            } else {
                return true;
            }
        })()
        if (answerCheck && scoreCheck) {
            return true;
        } else {
            return false;
        }
    })();
    //判断题校验
    var qjCheck = (function () {
        //若题目不是连线题，直接跳过检测
        if (!$editor_box.find(".editor .add_question_j").length) {
            return true;
        }
        var optionCheck = (function () {
            if (!$editor_box.find(".editor .add_question_j .j_item .sky_radio input:checked").length) {
                checkInfoList.push("<font style='color:red'>判断题答案未选择！</font>");
                return false;
            } else {
                return true;
            }
        })()
        var scoreCheck = (function () {
            if (!$editor_box.find(".editor .add_question_j .score input").val() || ($.trim($editor_box.find(".editor .add_question_j .score input").val()).substring(0, 1) == "0" && $.trim($editor_box.find(".editor .add_question_j .score input").val()).substring(1, 2) != ".")) {
                checkInfoList.push("<font style='color:red'>判断题分数填写不正确！</font>");
                return false;
            } else {
                return true;
            }
        })()
        if (scoreCheck && optionCheck) {
            return true;
        } else {
            return false;
        }
    })();
    // 音频题检测
    var qaCheck = (function () {
        //若题目不是音频题，直接跳过检测
        if (!$editor_box.find(".add_question_a input").val()) {
            return true;
        }
        if ($.trim($editor_box.find(".add_question_a input").val()).substring(0, 1) == "0" && $.trim($editor_box.find(".add_question_a input").val()).substring(1, 2) != ".") {
            checkInfoList.push("<font style='color:red'>音频分数填写不正确！</font>");
            return false;
        } else {
            return true;
        }
    })();
    // 音频题字符长度检测（最大600个字符）
    var qaCheck = (function () {
        //若题目不是音频题，直接跳过检测
        if (!$editor_box.find(".add_question_a input").val()) {
            return true;
        }
        if (editor.$txt.find("p").text().length > 600) {
            checkInfoList.push("<font style='color:red'>音频题内容长度为"+editor.$txt.find("p").text().length+"个字符,超出限定（最多600个字符，包含空格）！</font>");
            return false;
        } else {
            return true;
        }
    })();
    // 解析校验
    var ananlyCheck = (function () {
        if (!$.trim($editor_box.find(".ananly_cnt").val())) {
            checkInfoList.push("解析未填写！");
        }
        return true;
    })();
    //编辑器内容校验
    var editorCntCheck = (function () {
        if (!editor.$txt.find("p").text()) {
            checkInfoList.push("<font style='color:red'>内容不能为空！</font>");
            return false;
        } else {
            return true;
        }
    })();
    //题目类型校验
    var qTypeCheck = (function () {
        if (!$(".q_box .q_item[name='q_item0']").length || $(".submit_item_btn").attr("name") == "q_item0" || editor.$txt.find('.add_question_e').length || editor.$txt.find('.add_question_c').length || editor.$txt.find('.add_question_l').length || editor.$txt.find('.add_question_j').length || editor.$txt.parent().find(".add_question_a input").val()) {
            return true;
        } else {
            checkInfoList.push("<font style='color:red'>请选择题型！</font>");
            return false;
        }
    })();
    if (editorCntCheck && questionTitleCheck && standTimeCheck && imgPositionCheck && audioWaitCheck && qcCheck && qeCheck && qlCheck && qjCheck && ananlyCheck && qaCheck&&qTypeCheck) {
        return true;
    } else {
        return false;
    }
}

// 页面加载完成执行事件
window.onload = function(){
    // 隐藏图片上传drop-panel
    $(".wangEditor-container .wangeditor-menu-img-upload").parents(".wangEditor-drop-panel").css("visibility","hidden");
    // 模拟点击图片上传按钮
    $(".menu-item .wangeditor-menu-img-picture0").on('click'
        , function(event) {
        $(".wangEditor-container .wangeditor-menu-img-upload").click();
    });
}()