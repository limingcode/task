<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" class="lessonsChoose">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <title>产品教学系统(后端)</title>
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/animate.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/style.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/index_V2.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/css/common.css">
    <link rel="stylesheet" href="<%=path%>/sys/teach/js/skin/default/layer.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if IE]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="<%=path%>/sys/teach/js/jquery-3.2.1.min.js"></script>
    <script src="<%=path%>/sys/teach/js/layer.js"></script>
    <script src="<%=path%>/sys/teach/js/lib/classChoose.js"></script>
    <script src="<%=path%>/sys/teach/js/util/lessonsChoose_V2.js"></script>
    <script src="<%=path%>/sys/teach/js/common.js"></script>
    <!--<script src="js/flexible.js"></script>-->
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
                    <li><a href="<%=path%>/user/statis.jhtml">大数据</a></li>
                    <li><a href="<%=path%>/user/logout.jhtml">退出</a></li>
                </ul>
            </span>
        </div>
        <div class="classTitle">
            <span class="subject" name="A01">
                <i class="iconImg"></i>
                <i class="iconTxt">英语</i>
            </span>
            <span class="subject" name="A04">
                <i class="iconImg"></i>
                <i class="iconTxt">语文</i>
            </span>
            <span class="subject" name="A02">
                <i class="iconImg"></i>
                <i class="iconTxt">数学</i>
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
        <div class="term hid" style="border: none">
            <i class="fl">学期 ：</i>
            <div class="fl w1000">
                <c:forEach items="${periodList}" var="period">
              		<span class="period" name="${period.id}">${period.year}${period.name}</span>
              	</c:forEach>
            </div>
        </div>
    </div>
</content>
<div class="bottomCont">
    <div class="bottom no_select">
        <div class="bottomTitle">
            <span>
                <p>语法重点课程</p>
                <i class="iconCont">
                    <img src="<%=path%>/sys/teach/images/lin/indexNewIcon_1.png" alt="">
                    <img src="<%=path%>/sys/teach/images/lin/indexNewIcon_2.png" alt="">
                    <img src="<%=path%>/sys/teach/images/lin/indexNewIcon_3.png" alt="">
                </i>
            </span>
        </div>
        <div class="bottomIcon">
            <span name="1" style="float: left;"><img src="<%=path%>/sys/teach/images/lin/indexNewIcon_4.png" alt=""></span>
            <span name="2" style="float: left;"><img src="<%=path%>/sys/teach/images/lin/indexNewIcon_5.png" alt=""></span>
            <span name="3" style="float: left;"><img src="<%=path%>/sys/teach/images/lin/indexNewIcon_6.png" alt=""></span>
            <span name="4" style="float: left;"><img src="<%=path%>/sys/teach/images/lin/indexNewIcon_7.png" alt=""></span>
        </div>
        <div class="peopleOne"></div>
        <div class="peopleTow"></div>
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
        $('.bottom').css('height',''+_htmlHeight-_header-_content-60+'px');
        $('.bottomTitle').css('height',''+_htmlHeight-_header-_content-100+'px');

        $('.headerCont .logo').on('click',function(){
        	window.location.href = getRootPath() + "/user/prepareLessons.jhtml";
        });

        var filterList = $(".grade span,.levels span,.term span,.classTime span");
        var activeNum = 0;
        $(".top").on('click', filterList, function(event) {
            setTimeout(function(){
                filterList.each(function(index, el) {
                    if ($(this).hasClass('active')) {
                        activeNum++;
                    }
                });
                if (activeNum == $(".top .w1000").length) {
//                     $(".bottomCont .bottom").addClass('bounceIn animated').css({
<%--                         background: 'url(<%=path%>/sys/teach/images/lin/bottomBg_2all.png)', --%>
//                         backgroundSize: '1024px 100%',
//                         position: 'relative'
//                     });
//                     $('.bottomTitle').css('display','block');
//                     $('.bottomIcon').css('display','block');
//                     $('.peopleOne').css('display','block');
//                     $('.peopleTow').css('display','block');
                }
                activeNum = 0;

            }, 10);
        });
    });
</script>
</html>