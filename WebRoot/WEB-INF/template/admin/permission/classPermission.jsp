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
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/sys/css/public.css">
    <link rel="stylesheet" type="text/css"
          href="<%=path%>/sys/css/questionManage.css">
    <!--引入bootstrap插件-->
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        function getIiniTtermName() {
            alert("aaa");
            $('#termName').empty();
            var term = $('#termName');
            term.append('<option value="">亲选择地区</option>');
            console.log("aa" + term);
            $.ajax({
                url: '<%=path%>/grades/getIiniTtermName',

                type: 'get',
                dataType: 'json',
                success: function (opts) {
                    if (opts && opts.length > 0) {
                        var html = [];
                        for (var i = 0; i < opts.length; i++) {
                            html.push('<option value="' + opts[i].id + '">' + opts[i].desc + '</option>');
                        }
                        term.append(html.join(''));
                    }
                }
            });
        }

    </script>
</head>
<!--后端maps集合-->
<body>
<!-- begin 筛选列表 -->
<div class="wa_box filter_list">
    <div class="wa_box q_list">
            <span>地区 &nbsp;&nbsp;&nbsp;
                <select id="termName" onclick="getIiniTtermName()"></select>
            </span>
        <span>学期&nbsp;&nbsp; &nbsp;
            <select id="grade" onclick="var getInitGrade = function () {
                alert('aaaaaaaaa');

            };
                    getInitGrade()">
        </select>
        </span>
        <span>班级名称:&nbsp;&nbsp;&nbsp;
        <input type="text" id="dname">
        </span>
        <span>老师名称:&nbsp;&nbsp;&nbsp
        <input type="text" id="jname">
        </span>
    </div>
    <!-- begin 题库列表 -->
    <div class="wa_box q_list">
        <div class="filter_btn">
            <button class="sol_btn" onclick="search()">查询</button>
            <button class="sol_btn" onclick="add()">新增</button>
        </div>
    </div>

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
                <th style="min-width: 4em;">学区</th>
                <th style="min-width: 4em;">班级名称</th>
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
            <c:forEach items="${maps }" var="m">
                <tr id="${m.mapping_Key }">
                    <td>${m.CityName }</td>
                    <td>${m.termName}</td>
                    <td>${m.dname }</td>
                    <td>${m.jname }</td>
                    <td>${m.gname }</td>
                    <td>${m.fname }</td>
                    <td>${m.hname }</td>
                    <td>${m.courseTime }</td>
                    <td>${m.courseTimeFlag }</td>
                    <td>${m.bookName }</td>
                    <td><fmt:formatDate value="${m.operationTime}"/></td>
                    <td>${m.operationPeople }</td>
                    <td>
                        <a class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"
                           data-backdrop="static"
                           href="<%=path%>/permission/setEverPermissionClass.jhtml?classId=${m.mapping_Key }"
                           onclick="">修改</a>
                        <a href="javascript:;" class="delete_btn"
                           onclick="delQ(${m.mapping_Key})">删除</a>

                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        <!--模态区域-->
        <div class="div_main">
            <!-- botton按钮有两个属性是data-toggle="model"   data-target="#myModel"；第一个属性代表我可以调取并展示一个模态框，第二个属性表示我要展示的哪一个模态框，用id来标识-->
            <!-- data-backdrop="static"表示点击空白的地方不会关闭弹窗-->
            <!-- class = "modal"，用来把 <div> 的内容识别为模态框  class = "fade"，当模态框被切换时，它会引起内容淡入淡出-->
            <!-- tabindex=-1代表此元素禁止使用键盘上的tab键对其导航，就是按tab键的时候，会跳过这个div    不设置tabindex的话，就会使Esc退出无效 -->
            <!-- role=“dialog”代表这是一个对话框 -->
            <!-- 属性 aria-hidden="true" 用于保持模态窗口不可见，直到触发器被触发为止（比如点击在相关的按钮上） -->
            <div class="modal inmodal" id="myModal" role="dialog" aria-hidden="true">
                <div class="modal-dialog bacstyle">
                    <div class="modal-content" style="width: 100%;height: 100%;">
                        <!-- modal-header 是为模态窗口的头部定义样式的类 -->
                        <div class="modal-header" style="background: pink; width: 100%; height: 10%;">
                            <!-- close 是一个 CSS class，用于为模态窗口的关闭按钮设置样式 -->
                            <!-- data-dismiss="modal"，是一个自定义的 HTML5 data 属性，在这里它被用于关闭模态窗口 -->
                            <button type="button" class="close" data-dismiss="modal"><span
                                    aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                            <h4 class="modal-title">窗口模态</h4>
                        </div>
                        <!-- class="modal-body"，是 Bootstrap CSS 的一个 CSS class，用于为模态窗口的主体设置样式 -->
                        <div class="modal-body" style="background: green; width: 100%; height: 90%;">
                            模态主题
                        </div>
                    </div>
                </div>
            </div>

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
            search();
        })
    })

</script>

</body>
</html>
