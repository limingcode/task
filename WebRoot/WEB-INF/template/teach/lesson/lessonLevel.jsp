<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" class="lessonsChoose">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
<!--     <title>产品教学系统(后端)</title> -->
    <title>课次选择</title>
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/animate.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/style.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/index.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/js/skin/default/layer.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if IE]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="<%=path%>/sys/teach/js/jquery-3.2.1.min.js"></script>
    <script src="<%=path%>/sys/teach/js/layer.js"></script>
    <script src="<%=path%>/sys/teach/js/util/lessonsChoose.js"></script>
    <script src="<%=path%>/sys/teach/js/common.js"></script>
</head>
<body>
    <header>
        <div class="headerCont">
            <div class="logo fl">Skyedu蓝天教育</div>
            <div class="user fr">
                <span class="userName">
                    <i></i>
                    <i>${teacherName}</i>
                    <ul class="userName_opre">
                        <li><a href="<%=path%>/question/input.jhtml">作业平台</a></li>
                        <li><a href="<%=path%>/user/statis.jhtml">数据统计</a></li>
                        <li><a href="<%=path%>/user/logout.jhtml">退出</a></li>
                    </ul>
                </span>
            </div>
            <div class="classTitle">
                <span class="subject" name="A04">
                    <i class="iconImg"></i>
                    <i class="iconTxt">语文</i>
                </span>
                <span class="subject" name="A02">
                    <i class="iconImg"></i>
                    <i class="iconTxt">数学</i>
                </span>
                <span class="subject" name="A01">
                    <i class="iconImg"></i>
                    <i class="iconTxt">英语</i>
                </span>
                <span class="subject" name="A05">
                    <i class="iconImg"></i>
                    <i class="iconTxt">物理</i>
                </span>
                <span class="subject" name="A06">
                    <i class="iconImg"></i>
                    <i class="iconTxt">化学</i>
                </span>
            </div>
        </div>
    </header>
    <content>
        <div class="top">
            <div class="grade hid">
                <i class="fl">年级 ：</i>
                <div class="fl w1000">
                    <c:forEach items="${gradeList}" var="grade">
                		<span class="grades" name="${grade.code}">${grade.name}</span>
                	</c:forEach>
                </div>
            </div>
            <div class="levels hid">
                <i class="fl">层次 ：</i>
                <div class="fl w1000">
                    <c:forEach items="${cateList}" var="cate">
                		<span class="cate" name="${cate.code}">${cate.name}</span>
                	</c:forEach>
                </div>
            </div>
            <div class="term hid">
                <i class="fl">学期 ：</i>
                <div class="fl w1000">
                	<c:forEach items="${periodList}" var="period">
                		<span class="period" name="${period.id}">${period.year}${period.name}</span>
                	</c:forEach>
                </div>
            </div>
            <div class="classTime hid">
                <i class="fl">课次 ：</i>
                <div class="fl w1000">
                   
                </div>
            </div>
        </div>
    </content>
    <div class="bottomCont">
        <div class="bottom no_select">
            <div class="bottomTitle">
                <i class="leftDrop drop"></i>
                <i class="rightDrop drop"></i>
                <i class="num">3</i>
                <span>英语精英班12312</span>
            </div>
            <div class="bottomIcon">
                <span name="1"><img src="<%=path%>/sys/teach/images/bottomIcon_1.png" alt=""></span>
                <span name="2"><img src="<%=path%>/sys/teach/images/bottomIcon_2.png" alt=""></span>
                <span name="3"><img src="<%=path%>/sys/teach/images/bottomIcon_3.png" alt=""></span>
                <span name="4"><img src="<%=path%>/sys/teach/images/bottomIcon_4.png" alt=""></span>
            </div>
        </div>
    </div>
</body>
<script>
    $(function () {
    	var _html = $('html');
    	var _htmlHeight = _html.height();
    	var _htmlWidth = _html.width();
    	var _header = $('header').height();
    	var _content = $('content').height();
    	$('.bottomCont').css('height',''+_htmlHeight-_header-_content+48+'px');
    	$('.bottom').css('height',''+_htmlHeight-_header-_content+48+'px');
    	var bottomTitleW = ($('.bottomTitle').width())/2;

        // 泽钦添加(选完所有筛选项后显示课件、视频、反馈等操作)
//         var filterList = $(".classTime span")
//         var activeNum = 0;
//         $(".top").on('click', filterList, function(event) {
//             setTimeout(function(){
//                 filterList.each(function(index, el) {
//                     if ($(this).hasClass('active')) {
//                         activeNum++;
//                     }
//                 });
//                 if (activeNum == $(".top .w1000").length) {
//                     $(".bottomCont .bottom").addClass('bounceIn animated');
//                     $(".bottomCont .bottom").removeClass('no_select');
//                 }
//                 activeNum = 0;
//             }, 10);
//         });
    });
</script>
</html>
