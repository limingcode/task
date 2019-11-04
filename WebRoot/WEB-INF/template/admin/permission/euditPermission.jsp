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

    <title>个人权限编辑操作</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/sys/css/public.css">
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/sys/css/questionManage.css">
</head>

<body>
<div class="wa_box filter_list">
    <div class="filter_btn">
        <!--放弃修改-->
        <button class="sol_btn" onclick="var giveup1 = function () {
                window.location.href='<%=path%>/permission/everPermission.jhtml';
        };
        giveup1()">放弃修改</button>
        <!--保存修改-->
        <button class="sol_btn" onclick="hold()">保存修改</button>
    </div>

    <div class="filter_item">
        <!--修改后的地区-->
        <span class="filter_type filter_type5">地区</span>
        <ul>
            <c:forEach items="${getCity }" var="city" varStatus="index">
                <li><label class="sky_radio">
                    <input type="radio"
                           name="city" value=${city.CityName }
                                   <c:if test="${city.CityName==null&& index.index==0}">checked="checked"</c:if>
                           <c:if test="${city.CityName eq city.CityName}">checked="checked"</c:if> /><span
                        class="mark"></span>${city.CityName }</label></li>
            </c:forEach>
        </ul>
    </div>
    <div class="filter_item">
        <span class="filter_type filter_type3">年级</span>
        <ul>
            <c:forEach items="${gradeList }" var="grade" varStatus="index">
                <li>
                        <label class="sky_radio">
                        <input type="radio" name="grade"
                               value=${grade.code } <c:if test="${condition.grade==null && index.index==0}">checked="checked"</c:if>
                               <c:if test="${fn:trim(grade.code) eq condition.grade }">checked="checked"</c:if> />
                        <span class="mark"></span>${grade.name }</label>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="filter_item">
        <span class="filter_type filter_type4">科目</span>
        <ul>
            <c:forEach items="${subjectList }" var="subject" varStatus="index">
                <li><label class="sky_radio"><input type="radio"
                                                    name="subject" value=${subject.code }
                                                            <c:if test="${condition.subject==null&& index.index==0}">checked="checked"</c:if>
                                                    <c:if test="${fn:trim(subject.code) eq condition.subject }">checked="checked"</c:if> /><span
                        class="mark"></span>${subject.name }</label></li>
            </c:forEach>
        </ul>
    </div>
</div>
<!-- end 筛选列表 -->
<!--展示编辑内容区域-->
<div class="wa_box q_list">
    <div class="wa_table_oper">
        <div class="table_name">
            <span class="current">个人权限编辑操作</span>
        </div>

    </div>
    <!-- begin 分页条 -->
    <div class="pagination">

    </div>
    <!-- end 分页条 -->
</div>

</div>
<!-- end 题库列表 -->
<script type="text/javascript"
        src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
        src="<%=path%>/sys/js/space_underline.js"></script>
<script type="text/javascript" src="<%=path%>/sys/js/public.js"></script>
<script type="text/javascript">
    function search() {
        var title = $("#title").val();
        var grade = $("input[name='grade']:checked").val();
        var subject = $("input[name='subject']:checked").val();
        var cate = $("input[name='cate']:checked").val();
        window.location.href = "<%=path%>/question/questionList.jhtml?grade=" + grade + "&subject=" + subject + "&cate=" + cate + "&title=" + title;
    }

    function isNull(str) {
        if (str == "") return true;
        var regu = "^[ ]+$";
        var re = new RegExp(regu);
        return re.test(str);
    }

    function goPage(pageNo) {
        var grade = "${condition.grade}";
        var subject = "${condition.subject}";
        var cate = "${condition.cate}";
        var title = "${condition.title}";
        var totalPage = ${condition.totalPage };
        if (pageNo > totalPage) {
            pageNo = totalPage;
        }
        window.location.href = "<%=path%>/question/questionList.jhtml?grade=" + grade + "&subject=" + subject + "&cate=" + cate + "&pageNo=" + pageNo + "&title=" + title;
    }

    function forword() {
        var pageNo = $("#pageNo").val();
        goPage(pageNo);
    }

    function input() {
        var grade = $("input[name='grade']:checked").val();
        var subject = $("input[name='subject']:checked").val();
        var cate = $("input[name='cate']:checked").val();
        window.location.href = "<%=path%>/question/inputQuestion.jhtml?grade=" + grade + "&subject=" + subject + "&cate=" + cate;
    }

    function delQ(questionId) {

        if (confirm("是否确定删除此题")) {
            $.ajax({
                url: "<%=path%>/question/delQuestion.jhtml",
                type: "post",
                data: {
                    questionId: questionId
                },
                dataType: 'JSON',
                success: function (result) {
                    var code = result.code;
                    var message = result.message;
                    alert(message);
                    if (code == 100) {
                        $(".wa_table tr[id ='" + questionId + "']").remove();
                    }
                }
            })
        }
    }

    $("#title").bind('keydown', function (event) {
        if (event.keyCode == "13") {
            this.blur();
        }
    });

    $(function () {
        $(":radio").change(function () {
            //...statement
            search();
        })
    })
</script>
</body>
</html>
