<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
 <meta charset="utf-8">
<base href="<%=basePath%>">

<title>权限管理</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/js/jedate/skin/jedate.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/authorityManage.css">
  </head>
  <body>
    <!-- begin 筛选列表 -->
    <div class="wa_box filter_list">
        <div class="filter_item">
            <span class="filter_type">姓名</span>
            <ul>
                <li><input type="text" id="username" value=""></li>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type">部门</span>
            <ul>
                <li><input type="text" id="deptName" value=""></li>
            </ul>
        </div>
        <div class="filter_btn">
            <button class="sol_btn" onclick="search()">查询</button>
        </div>
    </div>
    <!-- end 筛选列表 -->
    <!-- begin templet表格 -->
    <div class="wa_box authority_box">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span class="current">人员筛选</span>
            </div>
        </div>
        <table class="wa_table">
            <thead>
                <tr>
                    <th style="width:10em">部门</th>
                    <th style="width:5em">姓&nbsp;&nbsp;&nbsp;&nbsp;名</th>
                    <c:forEach items="${roleMapList}" var="roleMap"> 
								<th>${roleMap.roleName }</th>
							</c:forEach>
                </tr>
            </thead>
            <tbody>
            		<c:forEach items="${employeeList}" var="employee">
						<tr>
							<td>${employee.deptName}</td>
							<td>${employee.employeeName}</td>
							<c:forEach items="${roleMapList}" var="roleMap"> 
								<td><label class="sky_checkbox"><input type="checkbox" name="" oaId="${employee.oaId}" roleId="${roleMap.id}" <c:if test="${fn:contains(employee.roleUrls, roleMap.roleUrl)}">checked="checked"</c:if>/><span class="mark"></span></label></td>
							</c:forEach>
						</tr>
					</c:forEach>

            </tbody>
        </table>
        <!-- begin 分页条 -->
		<div class="pagination">
			<ul>
				<li class="first_page" onclick="goPage(1)">&nbsp;</li>
				<c:if test="${condition.pageNo>1 }">
					<li class="prev_page" onclick="goPage(${condition.pageNo-1 })">&nbsp;</li>
				</c:if>
				<c:if test="${condition.pageNo-4>0 }">
					<li onclick="goPage(${condition.pageNo-4 })">${condition.pageNo-4}</li>
				</c:if>
				<c:if test="${condition.pageNo-3>0 }">
					<li onclick="goPage(${condition.pageNo-3 })">${condition.pageNo-3}</li>
				</c:if>
				<c:if test="${condition.pageNo-2>0 }">
					<li onclick="goPage(${condition.pageNo-2 })">${condition.pageNo-2}</li>
				</c:if>
				<c:if test="${condition.pageNo-1>0 }">
					<li onclick="goPage(${condition.pageNo-1 })">${condition.pageNo-1}</li>
				</c:if>
				<li class="current_page">${condition.pageNo}</li>
				<c:if test="${condition.pageNo< condition.totalPage}">
					<li onclick="goPage(${condition.pageNo+1 })">${condition.pageNo+1}</li>
				</c:if>
				<c:if test="${condition.pageNo+1< condition.totalPage}">
					<li onclick="goPage(${condition.pageNo+2 })">${condition.pageNo+2}</li>
				</c:if>
				<c:if test="${condition.pageNo+2< condition.totalPage}">
					<li onclick="goPage(${condition.pageNo+3 })">${condition.pageNo+3}</li>
				</c:if>
				<c:if test="${condition.pageNo+4< condition.totalPage}">
					<li onclick="goPage(${condition.pageNo+4 })">${condition.pageNo+4}</li>
				</c:if>
				<c:if test="${(condition.totalPage-condition.pageNo)>5}">
					<li class="ellipsis">....</li>
				</c:if>
				<c:if test="${(condition.totalPage-condition.pageNo)>4}">
					<li onclick="goPage(${condition.totalPage })">${condition.totalPage
						}</li>
				</c:if>
				<c:if test="${condition.pageNo<condition.totalPage }">
					<li class="next_page" onclick="goPage(${condition.pageNo+1})">&nbsp;</li>
				</c:if>
				<li class="last_page" onclick="goPage(${condition.totalPage })">&nbsp;</li>
				<li class="all_page">共${condition.totalPage }页</li>
				<li class="turn_page">到<input type="number" id="pageNo" />页</li>
				<li class="go_btn" onclick="forword()">GO</li>
			</ul>
		</div>
		<!-- end 分页条 -->
    </div>
    <!-- end templet表格 -->
    <script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/layer.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/authorityManage.js"></script>
    <script type="text/javascript">
    function search(){
		var username = $("#username").val();
		var deptName = $("#deptName").val();
		window.location.href="<%=path%>/role/index.jhtml?username="+username+"&deptName="+deptName;
	}
	
	function isNull( str ){
		if ( str == "" ) return true;
		var regu = "^[ ]+$";
		var re = new RegExp(regu);
		return re.test(str);
	}
	
	function goPage(pageNo){
		var pageSize = "${condition.pageSize}";
		var username = "${condition.username}";
		var deptName = "${condition.depeName}";
		var totalPage = ${condition.totalPage };
		if (pageNo>totalPage) {
			pageNo = totalPage;
		}
		window.location.href="<%=path%>/role/index.jhtml?pageNo="+pageNo+"&pageSize="+pageSize+"&username="+username+"&deptName="+deptName;
	}
	
	function forword(){
		var pageNo = $("#pageNo").val();
		goPage(pageNo);
	}
	
	 $(function(){
		    $(".authority_box .wa_table").on("change",".sky_checkbox input",function(){
		    		var _this = this;
		            $.ajax({
		            	url:"<%=path%>/role/change.jhtml",
		            	type:"post",
		            	data:{
		            		eoaId:$(_this).attr("oaId"),
		            		roleId:$(_this).attr("roleId"),
		            		state:$(_this).prop("checked")?1:0
		            	},
		            	dataType:"json",
		            	success:function (result){
		            		var code = result.code;
		            		if (code!=100) {
		            			var message = result.message;
			            		alert(message)
							}
		            	}
		            })
		     })
		})
    </script>
  </body>
</html>
