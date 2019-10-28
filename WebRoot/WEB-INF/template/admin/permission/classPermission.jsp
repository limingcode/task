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

    <title>班级权限列表</title>

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
    <div class="filter_item">
        <span class="filter_type ">
            地区&nbsp;&nbsp;&nbsp;
        <select>
            <option>全部</option>
            <option>深圳</option>
            <option>杭州</option>
            <option>外教</option>
        </select>
        </span>
        <ul>
            <c:forEach items="${gradeList }" var="grade" varStatus="index">
                <li><label class="sky_radio"><input type="radio"
                                                    name="grade" value=${grade.code }
                                                            <c:if test="${condition.grade==null&& index.index==0}">checked="checked"</c:if>
                                                    <c:if test="${fn:trim(grade.code) eq condition.grade }">checked="checked"</c:if> /><span
                        class="mark"></span>${grade.name }</label></li>
            </c:forEach>

        </ul>
    </div>
    <div class="filter_item">
        <span class="filter_type filter_type4">学期&nbsp;&nbsp; &nbsp;
            <select>
            <option>2019秋季</option>
        </select>
        </span>
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
    <div class="filter_item">
        <span class="filter_type filter_type5">班级名称:&nbsp;&nbsp;&nbsp;<input type="text">
        </span>
        <ul>
            <c:forEach items="${cateList }" var="cate" varStatus="index">
                <li><label class="sky_radio"><input type="radio"
                                                    name="cate" value=${cate.code }
                                                            <c:if test="${condition.cate==null&& index.index==0}">checked="checked"</c:if>
                                                    <c:if test="${cate.code eq condition.cate}">checked="checked"</c:if> /><span
                        class="mark"></span>${cate.name }</label></li>
            </c:forEach>
        </ul>
    </div>
    <div class="filter_item">
        <span class="filter_type filter_type9">老师名称:&nbsp;&nbsp;&nbsp;</span>

    </div>
    <div class="filter_btn">
        <button class="sol_btn" onclick="search()">查询</button>
        <button class="sol_btn" onclick="add()">新增</button>
    </div>
</div>

<!-- end 筛选列表 -->

<!-- begin 题库列表 -->
<div class="wa_box q_list">
    <div class="wa_table_oper">
        <div class="table_name">
            <span class="current">班级权限列表</span>
        </div>
        <!-- <div class="oper_btn">
            <button class="sol_btn" onclick="input()">录入</button>
        </div> -->
    </div>
    <table class="wa_table">
        <thead>
        <tr>
            <th style="min-width: 4em;">地区</th>
            <th style="">学区</th>
            <th style="min-width: 4em;">班级名次</th>
            <th style="min-width: 4em;">老师名称</th>
            <th style="min-width: 4em;">校区</th>
            <th style="min-width: 4em;">年级</th>
            <th style="min-width: 4em;">层次</th>
            <th style="min-width: 4em;">上课日期</th>
            <th style="min-width: 4em;">时段</th>
            <th style="min-width: 4em;">阅读权限</th>
            <th style="min-width: 4em;">操作时间</th>
            <th style="min-width: 4em;">最后操作人</th>
            <th style="min-width: 10em;">操作</th>

        </tr>
        </thead>
        <tbody>
        <!--动态的进行内容的选择 lm-->
        <c:forEach items="${classPermissionList }" var="question">
            <tr id="${question.id }">
                <td>${question.id }</td>
                <td>${question.codeName }</td>
                <td>${question.teacherName }</td>
                <td><fmt:formatDate value="${question.createDate}"
                                    type="both"/></td>
                <td>${question.editorName }</td>
                <td><fmt:formatDate value="${question.modifyDate}"
                                    type="both"/></td>
                <td>${question.gradeName }${question.subjectName }${question.cateName }</td>
                <td>${question.score }</td>
                <td>
                    &lt;%&ndash; <a href="<%=path%>/question/preview.jhtml?questionId=${question.id }">预览</a> &ndash;%&gt;
                    <a
                            href="<%=path%>/question/editorQuestion.jhtml?questionId=${question.id }">修改/查看</a>
                    <a href="javascript:;" class="delete_btn"
                       onclick="delQ(${question.id})">删除</a>

                </td>
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
<script type="text/javascript">
    function search() {
        var title = $("#title").val();
        var grade = $("input[name='grade']:checked").val();
        var subject = $("input[name='subject']:checked").val();
        var cate = $("input[name='cate']:checked").val();
        window.location.href = "<%=path%>/permission/classPermission.jhtml?grade=" + grade + "&subject=" + subject + "&cate=" + cate + "&title=" + title;
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
