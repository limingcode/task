<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

    <title>个人操作列表新增</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->

    <link rel="stylesheet" type="text/css"
          href="<%=path%>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/sys/css/public.css">
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/sys/css/questionManage.css">

</head>

<body>
<!-- begin 筛选列表 -->
<div class="wa_box filter_list">
    <div class="wa_box filter_list">

        <div class="filter_item">
            <span class="filter_type filter_type9">学生名称</span>

            <ul>
                <li><input type="text" id="name">

                </li>
            </ul>
        </div>

    </div>
    <div class="filter_item">
        <span class="filter_type filter_type9">学生编号</span>

        <ul>
            <li><input type="text" id="code">

            </li>
        </ul>
    </div>
    <button class="sol_btn" onclick="var query1 = function () {
            alert('将进行学生的新增查询操作');
             //根据学生编号
        var name = $('#name').val();//学生编号
        var code = $('#code').val();//学生名称
            if (typeof(name)=='undefined' || isNull(name)) {
            alert('请输入学生姓名');
            return;
            }
            if (typeof(code)=='undefined'||isNull(code)) {
            alert('请输入学生编码');
            return;
            }

            window.location.href='<%=path%>/permission/addEverPermission.jhtml?name='+name+'&code='+code;
            };

    query1()">查询
    </button>
</div>
</div>
<!-- end 筛选列表 -->

<!-- begin 题库列表 -->
<div class="wa_box q_list">
    <div class="wa_table_oper">
        <div class="table_name" id="2">
            <span class="current">个人权限操作列表</span>
            <span class="current">ps:请勾选以下需要进行权限设置的学生或者从上面针对查询进行设置</span>

            <button class="sol_btn" onclick=" var setUp = function () {

                    window.location.href = '<%=path%>/permission/setEverPermission.jhtml';

                    };
                    //设置条件

                    setUp()">开始设置
            </button>


        </div>

    </div>

    <table class="wa_table">
        <thead>
        <tr>
            <th style="min-width: 2em;">
                <input type="checkbox" name="all" id="all" onclick="var alll = function () {
            //完成全选/全不选功能
		$('#all').click(function(){
                //attr获取checked是undefined;
                //我们这些dom原生的属性；attr获取自定义属性的值；
                //prop修改和读取dom原生属性的值
                $('.check_item').prop('checked',$(this).prop("checked"));
                });

                //check_item
                $(document).on('click','.check_item',function(){
                //判断当前选择中的元素是否5个
                var flag = $('.check_item:checked').length==$('.check_item"'.length;
                $('#check_all').prop("checked",flag);
                });


                };
            alll()">全选设置

            </th>

            <th style="min-width: 4em;">学生名称</th>
            <th style="min-width: 4em;">学生编号</th>
            <th style="min-width: 12em;">阅读权限</th>


        </tr>
        </thead>
        <tbody>
        <c:forEach items="${permissionList }" var="permission">
            <tr aria-checked="mixed" style="background-color: #00b500">
                <td><input type="checkbox"></td>
                <td>${permission.name }</td>
                <td>${permission.code }</td>
                <td>${permission.bookName }</td>

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
            <li class="turn_page">到<input type="number" id="pageNo"/>页</li>
            <li class="go_btn" onclick="forword()">GO</li>
        </ul>
    </div>
    <!-- end 分页条 -->
</div>
<!-- end 题库列表 -->
<script type="text/javascript"
        src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
        src="<%=path%>/sys/js/space_underline.js"></script>
<script type="text/javascript" src="<%=path%>/sys/js/public.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script type="text/javascript">


    function isNull(str) {
        if (str == "") return true;
        var regu = "^[ ]+$";
        var re = new RegExp(regu);
        return re.test(str);
    }

    function goPage(pageNo) {
        var name = "${permission.name}";
        var code = "${permission.code}";
        if (pageNo > totalPage) {
            pageNo = totalPage;
        }
        window.location.href="<%=path%>/permission/setEverPermission.jhtml?name="+name+"&code="+code+"&pageNo="+pageNo;


    }

    function forword() {
        var pageNo = $("#pageNo").val();
        goPage(pageNo);
    }


    $("#title").bind('keydown', function (event) {
        if (event.keyCode == "13") {

            this.blur();
        }
    });


</script>
</body>
</html>
