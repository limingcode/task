var isOnline = false;
var list = [];
var onTime = true;
var msgCont = false;
var times;
var conn = new WebIM.connection({
    isMultiLoginSessions: WebIM.config.isMultiLoginSessions,
    https: typeof WebIM.config.https === 'boolean' ? WebIM.config.https : location.protocol === 'https:',
    url: WebIM.config.xmppURL,
    heartBeatWait: WebIM.config.heartBeatWait,
    autoReconnectNumMax: WebIM.config.autoReconnectNumMax,
    autoReconnectInterval: WebIM.config.autoReconnectInterval,
    apiUrl: WebIM.config.apiURL,
    isAutoLogin: true
});

var options = {
    apiUrl: WebIM.config.apiURL,
    user: $('.userMsg').attr('username'),
    pwd: $('.userMsg').attr('password'),
    appKey: WebIM.config.appkey
};
conn.open(options);
conn.listen({
    onOpened: function ( message ) {          //连接成功回调
        // 如果isAutoLogin设置为false，那么必须手动设置上线，否则无法收消息
        // 手动上线指的是调用conn.setPresence(); 如果conn初始化时已将isAutoLogin设置为true
        // 则无需调用conn.setPresence();
        // console.log(conn);
        // sessionStorage.setItem('user',conn.user);
        // if(message.type==='chat'){
        //     var html = '<li id="ljj" class="active">' +
        //     '<img src="'+message.ext.em_avatar?message.ext.em_avatar:"images/icon01.png"+'" alt="">' +
        //         '<span><i class="username">'+message.ext.em_nickname+'</i><i class="thisMsg">'+message.data+'</i></span>' +
        //         '<i class="message-num active" data-cont="0" >1</i>' +
        //         '</li>';
        //     $('.roster').append(html);
        // }
        conn.getRoster({
            success: function ( roster ) {
                //获取好友列表，并进行好友列表渲染，roster格式为：
                /** [
                 {
                   jid:'asemoemo#chatdemoui_test1@easemob.com',
                   name:'test1',
                   subscription: 'both'
                 }
                 ]
                 */
                for ( var i = 0, l = roster.length; i < l; i++ ) {
                    var ros = roster[i];
                    //ros.subscription值为both/to为要显示的联系人，此处与APP需保持一致，才能保证两个客户端登录后的好友列表一致
                    if ( ros.subscription === 'both' ||ros.subscription === 'from'|| ros.subscription === 'to' ) {
                        var a = '<div class="chat-list-people" id = "'+ros.name+'">'+
                            '<div><img src="images/icon01.png" alt="头像"></div>'+
                            '<div class="chat-name">'+
                            '<p>'+ros.name+'</p>'+
                            '<p class="thisMsg"></p>'+
                            '</div>'+
                            '<div class="message-num" data-cont="0"></div>'+
                            '</div>';

                        var b = '<li id="'+ros.name+'">'+
                            '<img src="../images/manager_icon.png" alt="">'+
                            '<span>'+
                            '<i class="username">'+ros.name+'</i>'+
                            '<i class="thisMsg"></i>'+
                            '</span>'+
                            '<i class="message-num" data-cont="0"></i>'+
                            '</li>';

                        // $('.chatBox-list').append(a);
                        $('.roster').append(b);
                    }
                }
                isOnline = true;
            },
            error:function (e) {
                console.log(e);
                isOnline = false;
            }
        });
        conn.getGroup({
            success:function (r) {
                var groupHtml = '';

                $.each(r.data,function (i,elem) {
                    groupHtml += '<li id="'+elem.groupid+'" groupid="'+elem.groupid+'">'+
                        '<img src="../images/manager_icon.png" alt="">'+
                        '<span>'+
                        '<i class="username">'+elem.groupname+'</i>'+
                        '<i class="thisMsg"></i>'+
                        '</span>'+
                        '<i class="message-num" data-cont="0"></i>'+
                        '</li>';
                });
                $('.group').append(groupHtml);
            },
            error:function (e) {
                console.log(e);
            }
        });

    },
    onClosed: function ( message ) {},         //连接关闭回调
    onTextMessage: function ( message ) {
        setTimeout(function () {
            console.log(message);
            var userId = $('#'+message.from+'');
            var chatType;
            var chatId = sessionStorage.getItem('chatId');
            var user = sessionStorage.getItem('user');
            $('.list_icon4').addClass('redBoll');
            var msgNum = parseInt(userId.find('.message-num').attr('data-cont'));

            // 标题新消息提示调用
                var timerArr = $.blinkTitle.show();
                setTimeout(function() {//此处是过一定时间后自动消失
                    $.blinkTitle.clear(timerArr);
                }, 10000);
                //若人为操作消失，只需如此调用：$.blinkTitle.clear(timerArr);


            if($('#'+chatId+'').hasClass('active')){
                $.blinkTitle.clear(timerArr);
                userId.find('.message-num').hide().attr('data-cont','0').text('0');
            }
            else{
                userId.find('.message-num').show().addClass('active').attr('data-cont',msgNum+1).text(msgNum+1);
            }
            var a= {
                from:message.from,
                to:message.to,
                time:message.ext.time,
                text:message.data,
                float:'left',
                chatType:message.type,
                ext:message.ext
            };
            if(localStorage.getItem('chat')){
                list = JSON.parse(localStorage.getItem('chat'));
            }
            list.push(a);

            localStorage.setItem('chat',JSON.stringify(list));

            if($('.choose').find('a').eq(0).hasClass('active')){
                if(msgCont&&chatId===message.from&&user===message.to){
                    userId.find('.thisMsg').html(message.data);
                    times = getMyDate();
                    var html = '<div class="clearfloat">'+
                        '<div class="author-name">'+times+''+
                        '<small class="chat-date"></small>'+
                        '</div>'+
                        '<div class="leftMsg">'+
                        '<div class="chat-avatars"><img src="images/icon01.png" alt="头像"></div>'+
                        '<div class="msgCont">'+
                        '<span class="nickname">'+message.ext.em_nickname +'</span>'+
                        '<div class="chat-message">'+message.data+''+
                        '</div>'+
                        '</div>'+
                        '</div>'+
                        '</div>';
                    $('.msgTop').append(html);
                    $(document).ready(function () {
                        $(".msgTop").scrollTop($(".msgTop")[0].scrollHeight);
                    });
                }
            }
            else{
                var groupid = sessionStorage.getItem('groupId');
                if(msgCont&&groupid===message.to){
                    userId.find('.thisMsg').html(message.data);
                    times = getMyDate();
                    var html = '<div class="clearfloat">'+
                        '<div class="author-name">'+times+''+
                        '<small class="chat-date"></small>'+
                        '</div>'+
                        '<div class="leftMsg">'+
                        '<div class="chat-avatars"><img src="images/icon01.png" alt="头像"></div>'+
                        '<div class="msgCont">'+
                        '<span class="nickname">'+message.ext.em_nickname +'</span>'+
                        '<div class="chat-message">'+message.data+''+
                        '</div>'+
                        '</div>'+
                        '</div>'+
                        '</div>';
                    $('.msgTop').append(html);
                    $(document).ready(function () {
                        $(".msgTop").scrollTop($(".msgTop")[0].scrollHeight);
                    });
                }
            }


        },1000);

    },    //收到文本消息
    onEmojiMessage: function ( message ) {},   //收到表情消息
    onPictureMessage: function ( message ) {
        var msg = '<img src="'+message.url+'" alt="">';
        var a= {
            from:message.from,
            to:message.to,
            time:message.ext.time,
            text:msg,
            float:'left',
            chatType:message.type
        };
        if(localStorage.getItem('chat')){
            list = JSON.parse(localStorage.getItem('chat'));
        }
        list.push(a);

        localStorage.setItem('chat',JSON.stringify(list));

        if(msgCont){
            var messages = '<img src="'+message.url+'" alt="">';
            times = getMyDate();
            var html = '<div class="clearfloat">'+
                '<div class="author-name">'+times+''+
                '<small class="chat-date"></small>'+
                '</div>'+
                '<div class="leftMsg">'+
                '<div class="chat-avatars"><img src="images/icon01.png" alt="头像"></div>'+
                '<div class="chat-message">'+messages+''+
                '</div>'+
                '</div>'+
                '</div>';
            $('.msgTop').append(html);
            $(document).ready(function () {
                $(".msgTop").scrollTop($(".msgTop")[0].scrollHeight);
            });
        }
    }, //收到图片消息
    onCmdMessage: function ( message ) {
        console.log('收到命令消息');

    },     //收到命令消息
    onAudioMessage: function ( message ) {},   //收到音频消息
    onLocationMessage: function ( message ) {},//收到位置消息
    onFileMessage: function ( message ) {},    //收到文件消息
    onVideoMessage: function (message) {
        var node = document.getElementById('privateVideo');
        var option = {
            url: message.url,
            headers: {
                'Accept': 'audio/mp4'
            },
            onFileDownloadComplete: function (response) {
                var objectURL = WebIM.utils.parseDownloadResponse.call(conn, response);
                node.src = objectURL;
            },
            onFileDownloadError: function () {
                console.log('File down load error.')
            }
        };
        WebIM.utils.download.call(conn, option);
    },   //收到视频消息
    onPresence: function ( message ) {

    },       //处理“广播”或“发布-订阅”消息，如联系人订阅请求、处理群组、聊天室被踢解散等消息
    onRoster: function ( message ) {},         //处理好友申请
    onInviteMessage: function ( message ) {},  //处理群组邀请
    onOnline: function () {},                  //本机网络连接成功
    onOffline: function () {},                 //本机网络掉线
    onError: function ( message ) {},          //失败回调
    onBlacklistUpdate: function (list) {       //黑名单变动
        // 查询黑名单，将好友拉黑，将好友从黑名单移除都会回调这个函数，list则是黑名单现有的所有好友信息
        console.log(list);
    },
    onReceivedMessage: function(message){},    //收到消息送达服务器回执
    onDeliveredMessage: function(message){},   //收到消息送达客户端回执
    onReadMessage: function(message){},        //收到消息已读回执
    onCreateGroup: function(message){},        //创建群组成功回执（需调用createGroupNew）
    onMutedMessage: function(message){}        //如果用户在A群组被禁言，在A群发消息会走这个回调并且消息不会传递给群其它成员
});



// 单聊发送文本消息
var sendPrivateText = function (msgs,times) {
    var id = conn.getUniqueId();                 // 生成本地消息id
    var msg = new WebIM.message('txt', id);      // 创建文本消息
    var user = sessionStorage.getItem('user');
    var chatId = sessionStorage.getItem('chatId');
    var userMsg = $('.userMsg');
    var chatType;
    msg.set({
        msg: msgs,                  // 消息内容
        to: 'ljj',                          // 接收消息对象（用户id）
        roomType: false,
        ext:{
            em_avatar:userMsg.attr('em_avatar'),
            em_nickname:userMsg.attr('em_nickname'),
            em_time:times
        },
        success: function (id, serverMsgId) {
            var a= {
                from:user,
                to:chatId,
                text:msgs,
                float:'right',
                time:times,
                chatType:'chat'
            };
            if(localStorage.getItem('chat')){
                list = JSON.parse(localStorage.getItem('chat'));
            }
            list.push(a);
            localStorage.setItem('chat',JSON.stringify(list));
            $('#'+chatId+'').find('.thisMsg').html(msgs);
            console.log('send private text Success');
        },
        fail: function(e){
            console.log("Send private text error");
        }
    });




    msg.body.chatType = 'singleChat';
    conn.send(msg.body);
};

// 群组发送文本消息
var sendGroupText = function (msgs,times) {
    var id = conn.getUniqueId();            // 生成本地消息id
    var msg = new WebIM.message('txt', id); // 创建文本消息
    var user = sessionStorage.getItem('user');
    var groupId = sessionStorage.getItem('groupId');
    var userMsg = $('.userMsg');
    var option = {
        msg: msgs,             // 消息内容
        to: groupId,                     // 接收消息对象(群组id)
        roomType: false,
        chatType: 'chatRoom',
        ext:{
            em_avatar:userMsg.attr('em_avatar'),
            em_nickname:userMsg.attr('em_nickname'),
            em_time:times
        },
        success: function () {
            var a= {
                from:user,
                to:groupId,
                text:msgs,
                float:'right',
                time:times,
                chatType:'groupchat'
            };
            if(localStorage.getItem('chat')){
                list = JSON.parse(localStorage.getItem('chat'));
            }
            list.push(a);
            localStorage.setItem('chat',JSON.stringify(list));
            $('#'+chatId+'').find('.thisMsg').html(msgs);
            console.log('send room text success');
        },
        fail: function () {
            console.log('failed');
        }
    };
    console.log(groupId);
    msg.set(option);
    msg.setGroup('groupchat');
    conn.send(msg.body);
};


//未读信息数量为空时
var totalNum = $(".chat-message-num").html();
if (totalNum == "") {
    $(".chat-message-num").css("padding", 0);
}
$(".message-num").each(function () {
    var wdNum = $(this).html();
    if (wdNum == "") {
        $(this).css("padding", 0);
    }
});


//进聊天页面
$(".pepList").on('click','li',function () {
    msgCont = true;
    var n = $(this).index();
    sessionStorage.setItem('chatId',$(this).attr('id'));
    sessionStorage.setItem('groupId',$(this).attr('groupid'));
    var num = 0;
    var num2;
    $('.chat-list-people').find('.message-num').each(function (i, elem) {
        num2 = i;
        if($(this).attr('data-cont')==='0'){
            num++;
        }
    });
    if(num===num2){
        $('.list_icon4').removeClass('redBoll');
    }

    $(this).find('.message-num').hide().attr('data-cont','0').text('0');

    var chatBox = $('.msgTop');
    var html = '';
    var chatType;
    chatBox.html(' ');
    var chatId = sessionStorage.getItem('chatId');
    var oldMsg = JSON.parse(localStorage.getItem('chat'));
    if(oldMsg!==null){
        if($('.choose').find('a').eq(0).hasClass('active')){
            chatType = 'chat';
        }
        else{
            chatType = 'groupchat';
        }
        $.each(oldMsg,function (i, elem) {
            if(elem.chatType === chatType){
                if(elem.to===chatId){
                    if(elem.float ==='right'){
                        html += '<div class="clearfloat">'+
                            '<div class="author-name">'+elem.ext.times+''+
                            '<small class="chat-date"></small>'+
                            '</div>'+
                            '<div class="rightMsg">'+
                            '<div class="chat-message">'+elem.text+'</div>'+
                            '<div class="chat-avatars">' +
                            '<img src="images/icon01.png" alt="头像">' +
                            '</div>'+
                            '</div>'+
                            '</div>';
                    }
                    else{
                        html += '<div class="clearfloat">'+
                            '<div class="author-name">'+elem.ext.times+''+
                            '<small class="chat-date"></small>'+
                            '</div>'+
                            '<div class="leftMsg">'+
                            '<div class="chat-avatars">' +
                            '<img src="images/icon01.png" alt="头像">' +
                            '</div>'+
                            '<div class="msgCont">'+
                            '<span class="nickname">'+elem.ext.em_nickname +'</span>'+
                            '<div class="chat-message">'+elem.text+''+
                            '</div>'+
                            '</div>'+
                            '</div>'+
                            '</div>'+
                            '</div>';
                    }

                }
                if(elem.to===sessionStorage.getItem('user')&&elem.from===chatId){
                    if(elem.float ==='left'){
                        html += '<div class="clearfloat">'+
                            '<div class="author-name">'+elem.ext.times+''+
                            '<small class="chat-date"></small>'+
                            '</div>'+
                            '<div class="leftMsg">'+
                            '<div class="chat-avatars">' +
                            '<img src="images/icon01.png" alt="头像">' +
                            '</div>'+
                            '<div class="msgCont">'+
                            '<span class="nickname">'+elem.ext.em_nickname +'</span>'+
                            '<div class="chat-message">'+elem.text+''+
                            '</div>'+
                            '</div>'+
                            '</div>'+
                            '</div>';
                    }
                    else{
                        html += '<div class="clearfloat">'+
                            '<div class="author-name">'+elem.ext.times+''+
                            '<small class="chat-date"></small>'+
                            '</div>'+
                            '<div class="rightMsg">'+
                            '<div class="chat-message">'+elem.text+'</div>'+
                            '<div class="chat-avatars">' +
                            '<img src="images/icon01.png" alt="头像">' +
                            '</div>'+
                            '</div>'+
                            '</div>';
                    }

                }
            }
        });
    }
    chatBox.append(html);
    //聊天框默认最底部
    $(document).ready(function () {
        $("#chatBox-content-demo").scrollTop(0);
    });


});


//发送信息
$(".sendMsg").click(function () {
    times = getMyDate();
    var textContent = $("textarea").val();
    textContent = textContent.replace(/[\n\r]/g, '<br>');
    // textContent = textContent.replace(/<br>/gm,"\n");
    console.log(textContent);
    if (textContent != "") {
        $(".msgTop").append("<div class=\"clearfloat\">" +
            "<div class=\"author-name\">"+times+"<small class=\"chat-date\"></small> </div> " +
            "<div class=\"rightMsg\"> " +
            "<div class=\"chat-message\"> " + textContent + " </div> " +
            "<div class=\"chat-avatars\"><img src=\"images/icon01.png\" alt=\"头像\" /></div>" +
            "</div> </div>");
        //发送后清空输入框
        $("textarea").val(" ");
        //聊天框默认最底部
        $(document).ready(function () {
            $(".msgTop").scrollTop($(".msgTop")[0].scrollHeight);
        });
        if($('.choose').find('a').eq(0).hasClass('active')){
            sendPrivateText(textContent.replace(/<br>/gm,"\n"),times);
        }
        else{
            sendGroupText(textContent.replace(/<br>/gm,"\n"),times);
        }
    }
    else{
        layer.tips('emmmmm...说一点吧','.sendMsg', {
            tips: [1, '#3595CC'],
            time: 2000
        });
    }

});



// 单聊发送图片消息
var sendPrivateImg = function () {
    var id = conn.getUniqueId();                   // 生成本地消息id
    var msg = new WebIM.message('img', id);        // 创建图片消息
    var input = document.getElementById('paste');  // 选择图片的input
    var file = WebIM.utils.getFileUrl(input);      // 将图片转化为二进制文件
    var chatId = sessionStorage.getItem('chatId');
    var user = sessionStorage.getItem('user');
    var userMsg = $('.userMsg');
    var allowType = {
        'jpg': true,
        'gif': true,
        'png': true,
        'bmp': true
    };
    if (file.filetype.toLowerCase() in allowType) {
        times = getMyDate();
        var option = {
            apiUrl: WebIM.config.apiURL,
            file: file,
            to: 'ljj',                       // 接收消息对象
            roomType: false,
            chatType: 'singleChat',
            ext:{
                em_avatar:userMsg.attr('em_avatar'),
                em_nickname:userMsg.attr('em_nickname'),
                times:times
            },
            onFileUploadError: function () {      // 消息上传失败
                console.log('onFileUploadError');
            },
            onFileUploadComplete: function (e) {   // 消息上传成功
                var url = e.uri+'/'+e.entities[0].uuid;
                var messages = '<img src="'+url+'" alt="">';
                times = getMyDate();
                var html = '<div class="clearfloat">'+
                    '<div class="author-name">'+times+''+
                    '<small class="chat-date"></small>'+
                    '</div>'+
                    '<div class="rightMsg">'+
                    '<div class="chat-message">'+messages+''+
                    '</div>'+
                    '<div class="chat-avatars"><img src="images/icon01.png" alt="头像"></div>'+
                    '</div>'+
                    '</div>';
                $('.msgTop').append(html);

                var a= {
                    from:user,
                    to:chatId,
                    time:times,
                    text:messages,
                    float:'right',
                    chatType:'chat'
                };
                if(localStorage.getItem('chat')){
                    list = JSON.parse(localStorage.getItem('chat'));
                }
                list.push(a);
                localStorage.setItem('chat',JSON.stringify(list));


                $(document).ready(function () {
                    $(".msgTop").scrollTop($(".msgTop")[0].scrollHeight);
                });
            },
            success: function (e) {                // 消息发送成功
                $(document).ready(function () {
                    $(".msgTop").scrollTop($(".msgTop")[0].scrollHeight);
                });
            },
            flashUpload: WebIM.flashUpload
        };
        msg.set(option);
        conn.send(msg.body);
    }
};


// 群聊发送图片消息
var sendGroupImg = function () {
    var id = conn.getUniqueId();                   // 生成本地消息id
    var msg = new WebIM.message('img', id);        // 创建图片消息
    var input = document.getElementById('paste');  // 选择图片的input
    var file = WebIM.utils.getFileUrl(input);      // 将图片转化为二进制文件
    var groupId = sessionStorage.getItem('groupId');
    var user = sessionStorage.getItem('user');
    var userMsg = $('.userMsg');
    var allowType = {
        'jpg': true,
        'gif': true,
        'png': true,
        'bmp': true
    };
    if (file.filetype.toLowerCase() in allowType) {
        var option = {
            apiUrl: WebIM.config.apiURL,
            file: file,
            to: groupId,                       // 接收消息对象
            roomType: false,
            chatType: 'groupchat',
            ext:{
                em_avatar:userMsg.attr('em_avatar'),
                em_nickname:userMsg.attr('em_nickname')
            },
            onFileUploadError: function (e) {      // 消息上传失败
                console.log(e);
            },
            onFileUploadComplete: function (e) {   // 消息上传成功
                var url = e.uri+'/'+e.entities[0].uuid;
                var messages = '<img src="'+url+'" alt="">';
                times = getMyDate();
                var html = '<div class="clearfloat">'+
                    '<div class="author-name">'+times+''+
                    '<small class="chat-date"></small>'+
                    '</div>'+
                    '<div class="rightMsg">'+
                    '<div class="chat-message">'+messages+''+
                    '</div>'+
                    '<div class="chat-avatars"><img src="images/icon01.png" alt="头像"></div>'+
                    '</div>'+
                    '</div>';
                $('.msgTop').append(html);

                var a= {
                    from:user,
                    to:groupId,
                    time:times,
                    text:messages,
                    float:'right',
                    chatType:'groupchat'
                };
                if(localStorage.getItem('chat')){
                    list = JSON.parse(localStorage.getItem('chat'));
                }
                list.push(a);
                localStorage.setItem('chat',JSON.stringify(list));


                $(document).ready(function () {
                    $(".msgTop").scrollTop($(".msgTop")[0].scrollHeight);
                });
            },
            success: function (e) {                // 消息发送成功
                $(document).ready(function () {
                    $(".msgTop").scrollTop($(".msgTop")[0].scrollHeight);
                });
            },
            flashUpload: WebIM.flashUpload
        };
        msg.set(option);
        msg.setGroup('groupchat');
        conn.send(msg.body);
    }
};

$('.paste').on('change',function () {
    if($('.choose').find('a').eq(0).hasClass('active')){
        sendPrivateImg();
    }
    else{
        sendGroupImg();
    }

});

// 字符串检索匹配
function search() {
    var searchCnt = $('.searchInput').val();
    $(".pepList li").each(function(index, el) {
        if ($(this).find('.username').html().search(searchCnt) < 0) {
            $(this).hide();
        }else{
            $(this).show();
        }
    });
}
$('.searchInput').bind('input propertychange', function() {
    search();
});



function getMyDate(){
    var oDate = new Date(),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
};
//补0操作
function getzf(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}


// 新消息提示
;(function($) {
    $.extend({
        /**
         * 调用方法： var timerArr = $.blinkTitle.show();
         *     $.blinkTitle.clear(timerArr);
         */
        blinkTitle : {
            show : function() { //有新消息时在title处闪烁提示
                var step=0, _title = document.title;
                var timer = setInterval(function() {
                    step++;
                    if (step==3) {step=1};
                    if (step==1) {document.title='【　　　】'+_title};
                    if (step==2) {document.title='【新消息】'+_title};
                }, 500);
                return [timer, _title];
            },
            /**
             * @param timerArr[0], timer标记
             * @param timerArr[1], 初始的title文本内容
             */
            clear : function(timerArr) {
                //去除闪烁提示，恢复初始title文本
                if(timerArr) {
                    clearInterval(timerArr[0]);
                    document.title = timerArr[1];
                };
            }
        }
    });
})(jQuery);