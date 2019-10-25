<%@page import="org.springframework.util.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String message = (String)request.getSession().getAttribute("message");
if(StringUtils.isEmpty(message)){
	message = "";
}
%>
<html lang="en">
    <head>
    <base href="<%=basePath%>">
        <meta charset="utf-8">
        <title>登录</title>
        <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/reset.pchlin.css">
        <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/public.css">
        <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/login.css?22">
    </head>
    <body>
     <!-- begin 登陆框 -->
    <div class="login_box">
        <div class="login_wrap">
    	<form id="myform" action="<%=path%>/user/login.jhtml" method="post">
            <div class="title">用户登录</div>
            <label class="username">
                <input type="text" placeholder="账号" name="username" id="username" />
            </label>
            <label class="password">
                <input type="password" placeholder="****" name="password" id="password"/>
            </label>
            <label class="sky_checkbox">
                <input type="checkbox" checked />
                <span class="mark"></span>
                记住账户和密码
            </label>
            <button class="submit">登录</button>
            </form>
        </div>
    </div>
    <!-- end 登陆框 -->
    <!-- begin 弹窗 -->
    <div class="modal">
        <div class="wrap">
            <h2 class="title">提示</h2>
            <p class="cnt">${message}</p>
            <div class="btn_box">
                <button class="can_btn" onclick="closeM()">确认</button>
            </div>
        </div>
    </div>
    <!-- end 弹窗 -->

    <script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/login.js"></script>
    <script type="text/javascript">
    
    window.onload = function() {
		var oaId = '${oaId}';
		if (oaId!='') {
			window.top.location.href="<%=path%>/user/prepareLessons.jhtml";
		}
		
		var message = '${message}';
		if (message!=''){
			$(".modal").show();
		}
	};
    
    	<%-- function commit(){
    		var username = $("#username").val();
    		var password = $("#password").val();
    		if (isNull(username)) {
				$("#message").html("请输入账号");
				return false;
			}
			if (isNull(password)) {
				$("#message").html("请输入密码");
				return false;
			}
			$.ajax({
				url : "<%=path%>/user/login.jhtml",
                type : "post",
                data : {
                    username : $("#username").val(),
                    password : $("#password").val()
                },
                dataType : 'JSON',
                success : function(result) {
                    //显示结果：msg
                    //通过code来输出导出记录
                    if (result.code == 100) {
                    	window.top.location.href="<%=path%>/user/prepareLessons.jhtml";
                    } else {
                    	var message = result.message;
                    	$(".cnt").html(message);
                    	$(".modal").show();
                    }
                }
			})
    	} --%>
    	
    	function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		
		$("#password").bind('keydown',function(event){
	    	if(event.keyCode == "13") {
		        this.blur();
		        document.getElementById("myform").submit();
	   		}
   		 });
		
		function closeM(){
			$(".modal").hide();
		}
    </script>
    </body>
</html>