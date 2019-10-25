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
    user: 'skyedu_19752',
    pwd: 'e8be5d5fc5f1436fb494391efb29b806',
    appKey: WebIM.config.appkey
};
conn.open(options);
conn.listen({
    onOpened: function ( message ) {          //连接成功回调
        // 如果isAutoLogin设置为false，那么必须手动设置上线，否则无法收消息
        // 手动上线指的是调用conn.setPresence(); 如果conn初始化时已将isAutoLogin设置为true
        // 则无需调用conn.setPresence();
        conn.getRoster({
            success: function ( roster ) {
                sessionStorage.setItem('user',conn.user);
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
                        $('.chatBox-list').append(a);
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
                console.log(r);
            },
            error:function (e) {
                console.log(e);
            }
        });
        // var listGroups = function () {
        //     var option = {
        //         success: function (rooms) {
        //             console.log(rooms);
        //         },
        //         error: function () {
        //             console.log('List groups error');
        //         }
        //     };
        //     conn.listRooms(option);
        // };
        // listGroups();
    },
    onClosed: function ( message ) {},         //连接关闭回调
    onTextMessage: function ( message ) {
        setTimeout(function () {
            console.log(message);
            var userId = $('#'+message.from+'');
            $('.list_icon4').addClass('redBoll');
            var msgNum = parseInt(userId.find('.message-num').attr('data-cont'));
            if(!msgCont){
                userId.find('.message-num').show().addClass('active').attr('data-cont',msgNum+1).text(msgNum+1);
            }
            else{
                userId.find('.message-num').hide().attr('data-cont','0').text('0');
            }
            userId.find('.thisMsg').html(message.data);
            if(localStorage.getItem('chat')){
                list = JSON.parse(localStorage.getItem('chat'));
            }
            times = getMyDate();

            localStorage.setItem('chat',JSON.stringify(list));
            if(msgCont){
                times = getMyDate();
                var html = '<div class="clearfloat">'+
                    '<div class="author-name">'+times+''+
                    '<small class="chat-date"></small>'+
                    '</div>'+
                    '<div class="left">'+
                    '<div class="chat-avatars"><img src="images/icon01.png" alt="头像"></div>'+
                    '<div class="chat-message">'+message.data+''+
                    '</div>'+
                    '</div>'+
                    '</div>';
                $('.chatBox-content-demo').append(html);
                $(document).ready(function () {
                    $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
                });
            }
        },1000);

    },    //收到文本消息
    onEmojiMessage: function ( message ) {},   //收到表情消息
    onPictureMessage: function ( message ) {}, //收到图片消息
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
    onPresence: function ( message ) {},       //处理“广播”或“发布-订阅”消息，如联系人订阅请求、处理群组、聊天室被踢解散等消息
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




var sendPrivateText = function (msgs,times) {
    var id = conn.getUniqueId();                 // 生成本地消息id
    var msg = new WebIM.message('txt', id);      // 创建文本消息
    var user = sessionStorage.getItem('user');
    var chatId = sessionStorage.getItem('chatId');
    msg.set({
        msg: msgs,                  // 消息内容
        to: chatId,                          // 接收消息对象（用户id）
        roomType: false,
        success: function (id, serverMsgId) {
            var a= {
                from:user,
                to:msg.body.to,
                time:times,
                text:msgs,
                type:'right'
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








screenFuc();
function screenFuc() {
    var topHeight = $(".chatBox-head").innerHeight();//聊天头部高度
    //屏幕小于768px时候,布局change
    var winWidth = $(window).innerWidth();
    if (winWidth <= 768) {
        var totalHeight = $(window).height(); //页面整体高度
        $(".chatBox-info").css("height", totalHeight - topHeight);
        var infoHeight = $(".chatBox-info").innerHeight();//聊天头部以下高度
        //中间内容高度
        $(".chatBox-content").css("height", infoHeight - 46);
        $(".chatBox-content-demo").css("height", infoHeight - 46);

        $(".chatBox-list").css("height", totalHeight - topHeight);
        $(".chatBox-kuang").css("height", totalHeight - topHeight);
        $(".div-textarea").css("width", winWidth - 106);
    } else {
        $(".chatBox-info").css("height", 495);
        $(".chatBox-content").css("height", 448);
        $(".chatBox-content-demo").css("height", 448);
        $(".chatBox-list").css("height", 495);
        $(".chatBox-kuang").css("height", 495);
        $(".div-textarea").css("width", 260);
    }
}
(window.onresize = function () {
    screenFuc();
})();
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


//打开/关闭聊天框
$(".chatBtn").click(function () {
    $(".chatBox").toggle(10);
})
$(".chat-close").click(function () {
    $(".chatBox").toggle(10);
})
//进聊天页面
$(".chatBox-list").on('click','.chat-list-people',function () {
    msgCont = true;
    var n = $(this).index();
    sessionStorage.setItem('chatId',$(this).attr('id'));
    $(".chatBox-head-one").toggle();
    $(".chatBox-head-two").toggle();
    $(".chatBox-list").fadeToggle();
    $(".chatBox-kuang").fadeToggle();
    //传名字
    $(".ChatInfoName").text($(this).children(".chat-name").children("p").eq(0).html());

    //传头像
    $(".ChatInfoHead>img").attr("src", $(this).children().eq(0).children("img").attr("src"));

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

    // for ( var i = 0, l = list.length; i < l; i++ ) {
    //     var elem = list[i];
    //     var html = '<div class="clearfloat">'+
    //         '<div class="author-name">'+elem.time+''+
    //         '<small class="chat-date"></small>'+
    //         '</div>'+
    //         '<div class="'+elem.type+'">'+
    //         '<div class="chat-avatars"><img src="images/icon01.png" alt="头像"></div>'+
    //         '<div class="chat-message">'+elem.text+''+
    //         '</div>'+
    //         '</div>'+
    //         '</div>';
    //     $('.chatBox-content-demo').append(html);
    // }
    var chatBox = $('.chatBox-content-demo');
    var html = '';
    chatBox.html(' ');
    var chatId = sessionStorage.getItem('chatId');
    var oldMsg = JSON.parse(localStorage.getItem('chat'));
    console.log(oldMsg);
    if(oldMsg!==null){
        $.each(oldMsg,function (i, elem) {
            if(elem.to===chatId){
                if(elem.type ==='right'){
                    html += '<div class="clearfloat">'+
                        '<div class="author-name">'+elem.time+''+
                        '<small class="chat-date"></small>'+
                        '</div>'+
                        '<div class="'+elem.type+'">'+
                        '<div class="chat-message">'+elem.text+'</div>'+
                        '<div class="chat-avatars">' +
                        '<img src="images/icon01.png" alt="头像">' +
                        '</div>'+
                        '</div>'+
                        '</div>';
                }
                else{
                    html += '<div class="clearfloat">'+
                        '<div class="author-name">'+elem.time+''+
                        '<small class="chat-date"></small>'+
                        '</div>'+
                        '<div class="'+elem.type+'">'+
                        '<div class="chat-avatars">' +
                        '<img src="images/icon01.png" alt="头像">' +
                        '</div>'+
                        '<div class="chat-message">'+elem.text+'</div>'+
                        '</div>'+
                        '</div>';
                }

            }
            if(elem.to===sessionStorage.getItem('user')&&elem.from===chatId){
                if(elem.type ==='right'){
                    html += '<div class="clearfloat">'+
                        '<div class="author-name">'+elem.time+''+
                        '<small class="chat-date"></small>'+
                        '</div>'+
                        '<div class="left">'+
                        '<div class="chat-avatars">' +
                        '<img src="images/icon01.png" alt="头像">' +
                        '</div>'+
                        '<div class="chat-message">'+elem.text+'</div>'+
                        '</div>'+
                        '</div>';
                }
                else{
                    html += '<div class="clearfloat">'+
                        '<div class="author-name">'+elem.time+''+
                        '<small class="chat-date"></small>'+
                        '</div>'+
                        '<div class="right">'+
                        '<div class="chat-message">'+elem.text+'</div>'+
                        '<div class="chat-avatars">' +
                        '<img src="images/icon01.png" alt="头像">' +
                        '</div>'+
                        '</div>'+
                        '</div>';
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

//返回列表
$(".chat-return").click(function () {
    $(".chatBox-head-one").toggle(1);
    $(".chatBox-head-two").toggle(1);
    $(".chatBox-list").fadeToggle(1);
    $(".chatBox-kuang").fadeToggle(1);
    msgCont = false;
});

//      发送信息
$("#chat-fasong").click(function () {
    times = getMyDate();
    var textContent = $(".div-textarea").html().replace(/[\n\r]/g, '<br>')
    if (textContent != "") {
        $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
            "<div class=\"author-name\"><small class=\"chat-date\">"+times+"</small> </div> " +
            "<div class=\"right\"> <div class=\"chat-message\"> " + textContent + " </div> " +
            "<div class=\"chat-avatars\"><img src=\"images/icon01.png\" alt=\"头像\" /></div> </div> </div>");
        //发送后清空输入框
        $(".div-textarea").html("");
        //聊天框默认最底部
        $(document).ready(function () {
            $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
        });
        sendPrivateText(textContent,times);
    }
    else{
        console.log('请输入内容')
    }

});

//      发送表情
$("#chat-biaoqing").click(function () {
    $(".biaoqing-photo").toggle();
});
$(document).click(function () {
    $(".biaoqing-photo").css("display", "none");
});
$("#chat-biaoqing").click(function (event) {
    event.stopPropagation();//阻止事件
});

$(".emoji-picker-image").each(function () {
    $(this).click(function () {
        var bq = $(this).parent().html();
        console.log(bq)
        $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
            "<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
            "<div class=\"right\"> <div class=\"chat-message\"> " + bq + " </div> " +
            "<div class=\"chat-avatars\"><img src=\"images/icon01.png\" alt=\"头像\" /></div> </div> </div>");
        //发送后关闭表情框
        $(".biaoqing-photo").toggle();
        //聊天框默认最底部
        $(document).ready(function () {
            $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
        });
    })
});

//      发送图片
function selectImg(pic) {
    if (!pic.files || !pic.files[0]) {
        return;
    }
    var reader = new FileReader();
    reader.onload = function (evt) {
        var images = evt.target.result;
        $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
            "<div class=\"author-name\"><small class=\"chat-date\">"+times+"</small> </div> " +
            "<div class=\"right\"> <div class=\"chat-message\"><img src=" + images + "></div> " +
            "<div class=\"chat-avatars\"><img src=\"images/icon01.png\" alt=\"头像\" /></div> </div> </div>");
        //聊天框默认最底部
        $(document).ready(function () {
            $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
        });
    };
    reader.readAsDataURL(pic.files[0]);

}




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