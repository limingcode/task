<%@ page language="java" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <meta name="mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
  <meta name="msapplication-TileColor" content="#0e90d2">
  <link rel="icon" href="<%=path%>/sys/admin/images/sky.ico" />
  <link rel="icon" type="image/png" href="<%=path %>/sys/admin/assets/i/favicon.png">
  <link rel="icon" sizes="192x192" href="<%=path %>/sys/admin/assets/i/app-icon72x72@2x.png">
  <link rel="apple-touch-icon-precomposed" href="<%=path %>/sys/admin/assets/i/app-icon72x72@2x.png">
  <meta name="msapplication-TileImage" content="<%=path %>/sys/admin/assets/i/app-icon72x72@2x.png">
  
  <link rel="stylesheet" type="text/css" href="<%=path%>/sys/admin/assets/css/page.css"/>
  <link rel="stylesheet" href="<%=path %>/sys/admin/assets/css/amazeui.min.css">
  <link rel="stylesheet" href="<%=path %>/sys/admin/assets/css/odg-admin.css">
  <script type="text/javascript" src="<%=path%>/sys/js/public/jquery-1.8.3.min.js"></script>
  <script type="text/javascript" src="<%=path%>/sys/js/public/global.js"></script>
  <script type="text/javascript" src="<%=path%>/sys/js/util/pageUtil-3.0.1.js"></script>
  <script type="text/javascript" src="<%=path%>/sys/js/public/select.js"></script>
  
  <!-- 弹窗调用 -->
<link rel="stylesheet" href="<%=request.getContextPath() %>/sys/js/artDialog/skins/default.css"  />
<script type="text/javascript" src="<%=request.getContextPath() %>/sys/js/artDialog/source/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/sys/js/artDialog/source/artDialog.plugins.js"></script>

<!-- 时间控件 -->
<script type="text/javascript" src="<%=path%>/sys/js/My97DatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" src="<%=path%>/sys/js/My97DatePicker/skin/WdatePicker.css">

<script type="text/javascript">
	var default_user_path = PATH+"/sys/sog/images/user-pic.jpg";
</script>
<style>
.am-btn{
	margin: 10px 0 0;
}
.alert-bd{
	overflow-y:auto;
}
</style>
