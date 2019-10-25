/**
 * Created by Administrator on 2017/7/11.
 */
    var server = function () {};
        $("#loadingAmt").load("module/loading.html");
        //弹出登录框及登录处理
        $('.userServer').on('click', function () {
            var text_login = '<div>' +
                '<label style="position: relative;float:left;text-align: center;width: 100%;margin: 15px 0" for="username"><i></i>' +
                '<input placeholder="请输入用户名" style="border-radius:6px;padding: 10px 10px 10px 35px;width: 180px;font-size: 1rem" id="username" type="text"></label>' +
                '<label style="position: relative;float:left;text-align: center;width: 100%;margin: 15px 0" for="password"><i></i>' +
                '<input placeholder="请输入密码" style="border-radius:6px;padding: 10px 10px 10px 35px;width: 180px;font-size: 1rem" id="password" type="password"></label>' +
                '</div>';
            layer.confirm(text_login , {
                skin:'index_skin',
                title:'<div style="text-align: center">用户名登陆</div>',
                btn: ['登 陆'] //按钮
            }, function(){
                var username = $('input[id=username]').val();
                var password = $('input[id=password]').val();
                if(username==''||password==''){
                    layer.tips('用户名或密码不能为空 。', '.layui-layer-btn0', {
                        tips: [1, '#3595CC'],
                        time: 2000
                    });
                }
                else if(username=='bruce'&&password=='123456'){
                    layer.msg('登陆成功',{time:1000,icon:6}, function () {
                        window.location.href='classChoose.html';
                    });
                }
                else{
                    layer.tips('用户名或密码错误 。', '.layui-layer-btn0', {
                        tips: [1, '#3595CC'],
                        time: 2000
                    });
                }
            });
            $('body').keyup(function (event) {
                if(event.keyCode=='13'){
                    $(this).find('.layui-layer-btn0').click();
                }
                else if(event.keyCode=='27'){
                    layer.closeAll();
                }
            })
        });

