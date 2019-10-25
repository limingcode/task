<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html lang="en">
  <head>
	<meta charset="UTF-8">
	<title>作业助手后台</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/sys/css/entryway.css">
  </head>
  <body>
    <!-- begin 各模块入口 -->
    <div class="entry_box">
        <ul class="enter_list">
            <li class="item item1"><a href="<%=path%>/prepareLesson/toPrepareLesson.jhtml" title="备课"></a></li>
            <li class="item item2"><a href="<%=path%>/question/input.jhtml" title="作业平台"></a></li>
            <li class="item item3"><a href="<%=path%>/user/statis.jhtml" title="数据统计"></a></li>
            <li class="item item4"><a href="<%=path%>/personFile/toPersonFile.jhtml?userId=${oaId}" title="个人文件夹"></a></li>
        </ul>
    </div>
    <!-- end 各模块入口 -->
  </body>
  <script type="text/javascript">
	sessionStorage.clear("iframeSrc");
  </script>
</html>