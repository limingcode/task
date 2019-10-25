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
<!DOCTYPE html>
<html lang="en">
    <head>
    	<base href="<%=basePath%>">
        <meta charset="utf-8">
        <style type="text/css">
            .cnt{
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="cnt">
        	<form action="<%=path%>/appUser/setAppVersion.jhtml">
	            <input type="text" name="version" placeholder="版本号" /><br/>
	            <input type="text" name="path" placeholder="更新地址" /><br/>
	            <a href="javascript:;" class="add_btn">+</a><br/>
	            <button class="submit">提交</button>
	            <input type="hidden" name="userStudentId" value="0">
            </form>
        </div>

        <script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
        <script type="text/javascript">
            document.querySelector('.add_btn').onclick = function(){
                $(".submit").before("<input name='content' type='text' /><br/>");
            }
        </script>
    </body>
</html>