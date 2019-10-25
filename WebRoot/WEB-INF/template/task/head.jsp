 <%@ page language="java" pageEncoding="UTF-8"%>
 <%String path = request.getContextPath();%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
 <!--  导航 -->
<style>
.index,.statistical,.userinfo{display:none;}
</style>
<header data-am-widget="header" class="am-header am-header-default app-fixed">
    <div class="am-header-left am-header-nav">
      <a href="<%=path %>/wx/user/index.jhtml?wx_openid=${sessionScope.LOGIN_EMPLOYEE.wxOpenid}" class="">
        <i class="am-header-icon am-icon-home"></i>
      </a>
    </div>
    <h1 class="am-header-title">
      <a href="#title-link" id="title_link">${title }</a>
    </h1>

    <div class="am-header-right am-header-nav" style="display: none;">
      <a href="<%=path %>/wx/user/search.jhtml?type=1&name=" id="srarch_a"><i class="am-icon-search"></i></a>
      <a href="#right-link" class="">
        <i class="am-header-icon am-icon-bars"></i>
      </a>
    </div>


    <div class="am-header-right am-header-nav am-dropdown" style="display: none;" data-am-dropdown>
      <a class="am-btn am-btn-primary am-dropdown-toggle" data-am-dropdown-toggle><i
          class="am-header-icon am-icon-bars"></i></a>
      <ul class="am-dropdown-content">
      	<!-- 首页菜单 -->
        <li class="index user-info"><a href="<%=path%>/wx/user/userInfo.jhtml"><span class="am-icon-bar-chart"></span>个人中心</a></li>
        <li class="index user-info" id="applyScore"><a href="<%=path%>/wx/applyScore/index.jhtml"><span class="am-icon-bar-chart">申请加分</span></a></li>
        <c:if test="${sessionScope.LOGIN_EMPLOYEE.allotIs>0 }">
        	<li class="index am-divider"></li>
	        <li class="index"><a href="<%=path%>/wx/user/score.jhtml?t=1"><span class="am-icon-bar-chart"></span>群开积分</a></li>
        </c:if>
        <%-- <li class="index am-divider"></li>
        <li class="index"><a href="<%=path%>/wx/project/add.jhtml"><span class="am-icon-bar-chart"></span>发起项目</a></li> --%>
        <li class="index am-divider"></li>
        <li class="index"><a href="<%=path %>/wx/statistic/deptList.jhtml"><span class="am-icon-user-secret"></span>部门分类</a></li>
        <li class="index"><a href="<%=path %>/wx/statistic/deptScore.jhtml?type=1"><span class="am-icon-user-secret"></span>部门比拼</a></li>
        <%-- <li class="index"><a href="<%=path %>/wx/statistic/squad.jhtml?type=0"><span class="am-icon-user-secret"></span>小组排名</a></li> --%>
        
        <!-- 统计菜单 -->
        <li class="statistical dept"><a href="<%=path %>/wx/statistic/dept.jhtml?type=3&id="><span class="am-icon-bar-chart"></span> 累计积分排名</a></li>
	    <li class="statistical dept am-divider"></li>
	    <li class="statistical dept"><a href="<%=path %>/wx/statistic/dept.jhtml?type=1&id="><span class="am-icon-bar-chart"></span> 本月积分排名</a></li>
	    <li class="statistical dept am-divider"></li>
	    <li class="statistical dept"><a href="<%=path %>/wx/statistic/dept.jhtml?type=2&id="><span class="am-icon-bar-chart"></span> 本学期积分排名</a></li>		    
	    
        <li class="statistical squad"><a href="<%=path %>/wx/statistic/squad.jhtml?type=3"><span class="am-icon-bar-chart"></span> 累计积分排名</a></li>
	    <li class="statistical squad am-divider"></li>
	    <li class="statistical squad"><a href="<%=path %>/wx/statistic/squad.jhtml?type=1"><span class="am-icon-bar-chart"></span> 本月积分排名</a></li>
	    <li class="statistical squad am-divider"></li>
	    <li class="statistical squad"><a href="<%=path %>/wx/statistic/squad.jhtml?type=2"><span class="am-icon-bar-chart"></span> 本学期积分排名</a></li>	
	    
	    <!-- 个人得分详细记录菜单由程序单独控制，不做统一处理 -->
	    <li class="userinfo am-divider"></li>
	    <li class="userinfo"><a href="<%=path %>/wx/user/scoreFlow.jhtml?id=${sessionScope.LOGIN_EMPLOYEE.id}"><span class="am-icon-user-secret"></span> 个人得分详细记录</a></li>
      </ul>
    </div>
  </header>
  <!--  导航 end -->
  <script type="text/javascript">
  	$("#title_link").html($("title").html());
  	$("#srarch_a").hide();
  	var url = window.location.href;
	if(url.indexOf("index.jhtml")>0||url.indexOf("userInfo.jhtml")>0){//首页菜单
		if(url.indexOf("index.jhtml")>0)
			$("#srarch_a").show();
		$("div.am-header-right").show();
		$("ul.am-dropdown-content li.index").show();
		if(url.indexOf("userInfo.jhtml")>0)
			$("ul.am-dropdown-content li.user-info").hide();
	}
	/* 
	else if(url.indexOf("user/")>0){
		$("ul.am-dropdown-content li.dept").show();
	} */
	else if(url.indexOf("statistic/squad")>0){
		$("div.am-header-right").show();
		$("ul.am-dropdown-content li.squad").show();
	}
	else if((url.indexOf("statistic/dept")>0&&url.indexOf("deptList.jhtml")<0)){
		$("div.am-header-right").show();
		$("ul.am-dropdown-content li.dept").show();
	}
	var search_dept_id = $("#search_dept_id").val();
  	$("ul.am-dropdown-content li.dept a").each(function(){
	  	$(this).attr("href",$(this).attr("href")+search_dept_id);
  	});
  </script>