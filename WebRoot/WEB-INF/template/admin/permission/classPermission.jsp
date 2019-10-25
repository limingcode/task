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
    <title>班级权限</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/informManage.css">
</head>
<body>
<!-- begin 消息列表 -->
<div class="wa_box">
    <div class="wa_table_oper">
        <div  class="table_name">
            <span class="current">班级权限列表</span>
        </div>
    </div>
    <div>
        <thead>
        <tr>
            <th style="min-width: 5em;">地区&nbsp;&nbsp;&nbsp;<select>
                <option >全部</option>
                <option>深圳</option>
                <option>杭州</option>
                <option>外教</option>
            </select></th>
            <th style="min-width: 13em;">学期&nbsp;&nbsp;&nbsp;
                <select>
                    <option>2019秋季</option>
                </select>
            </th>
            <th style="min-width: 13em;">班级名称&nbsp;&nbsp;<input type="text"></th>

            <th style="min-width: 10em;">老师名称&nbsp;&nbsp;<input type="text"></th>

        </tr>


        </thead>
        <button type="" onclick="select()">查询</button>
        <button type="" onclick="add()">新增</button>

    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover">
            <thead>
            <tr>
 <th><input type="checkbox" id="checkAll">全选</th>
                <th>地区</th>
                <th>学期</th>
                <th>班级名称</th>
                <th>老师</th>
                <th>校区</th>
                <th>年级</th>
                <th>层次</th>
                <th>上课时间</th>
                <th>时段</th>
                <th>阅读时段</th>
                <th>操作时间</th>
                <th>最后操作人</th>
                <th>操作</th>
            </tr>

            </thead>
            <tbody id="tbodyId">
<%--            <tr>--%>
<%--               <td colspan="7">数据加载中...</td>--%>
<%--            </tr>--%>

            </tbody>

        </table>
<%--        <c:forEach items="${messageList }" var="message">--%>
<%--            <tr>--%>
<%--                <td>${message.teacherName}</td>--%>
<%--                <td><fmt:formatDate value="${message.createDate }" pattern="yyyy年MM月dd日 HH:mm"/> </td>--%>
<%--                <td><fmt:formatDate value="${message.openTime }" pattern="yyyy年MM月dd日 HH:mm"/></td>--%>
<%--                <td class="hover_show">${message.title }<span class="detail_icon"></span>--%>
<%--                    <span class="mark">${message.message }</span>--%>
<%--                </td>--%>
<%--                <td>${message.courseList }</td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
    </div>
    <!--数据显示内容区域-->
    <div id="pageId" class="box-footer clearfix">

    </div>


<%--    <table class="wa_table infor_list">--%>
<%--        <thead>--%>
<%--        <tr>--%>
<%--            <th style="min-width: 5em;">地区&nbsp;&nbsp;&nbsp;<select>--%>
<%--                <option >全部</option>--%>
<%--                <option>深圳</option>--%>
<%--                <option>杭州</option>--%>
<%--                <option>外教</option>--%>

<%--            </select></th>--%>
<%--            <th style="min-width: 13em;">学期&nbsp;&nbsp;&nbsp;--%>
<%--                <select>--%>
<%--                    <option>2019秋季</option>--%>
<%--                </select>--%>
<%--            </th>--%>
<%--            <th style="min-width: 13em;">班级名称&nbsp;&nbsp;<input type="text"></th>--%>

<%--            <th style="min-width: 10em;">老师名称&nbsp;&nbsp;<input type="text"></th>--%>

<%--        </tr>--%>


<%--        </thead>--%>

<%--      &lt;%&ndash;  <tbody>--%>


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
<!-- end 消息列表 -->

<script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
<script type="text/javascript">
    //页面加载显示只有设置阅读权限的班级列表
$(function () {
  $("pageId").load("",function () {
      
  });
})





</script>
</body>
</html>
