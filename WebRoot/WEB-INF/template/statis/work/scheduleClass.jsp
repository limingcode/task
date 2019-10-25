<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    
    <title>班级进度查询</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/js/jedate/skin/jedate.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/workManage.css">
  </head>
  <body>
    <!-- begin 筛选列表 -->
    <div class="wa_box filter_list">
        <div class="filter_item">
            <span class="filter_type filter_type1">校区</span>
            <ul>
            		<li><label class="sky_radio" ><input
					<c:if test="${condition.dept==null}">checked="checked"</c:if>
							type="radio" name="dept" value="" /><span class="mark"></span>全部校区</label></li>
            	<c:forEach items="${deptList }" var="dept" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.dept==null&& index.index==0}">checked="checked"</c:if>
							type="radio" name="dept" value=${dept.code } <c:if test="${dept.code eq condition.dept }">checked="checked"</c:if> /><span class="mark"></span>${dept.name}</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type2">学期</span>
            <ul>
            	<c:forEach items="${periodList }" var="period" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.period==null && index.index==0}">checked="checked"</c:if>
							type="radio" name="period" value=${period.id } <c:if test="${period.id eq condition.period }">checked="checked"</c:if> /><span class="mark"></span>${period.year}${period.name }</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type3">年级</span>
            <ul>
            	<c:forEach items="${gradeList }" var="grade" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.grade==null&& index.index==0}">checked="checked"</c:if>
							type="radio" name="grade" value=${grade.code } <c:if test="${fn:trim(grade.code) eq condition.grade }">checked="checked"</c:if> /><span class="mark"></span>${grade.name }</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type4">科目</span>
            <ul>
            	<c:forEach items="${subjectList }" var="subject" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.subject==null&& index.index==0}">checked="checked"</c:if>
							type="radio" name="subject" value=${subject.code } <c:if test="${fn:trim(subject.code) eq condition.subject }">checked="checked"</c:if>/><span class="mark"></span>${subject.name }</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type5">层次</span>
            <ul>
            	<c:forEach items="${cateList }" var="cate" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.cate==null&& index.index==0}">checked="checked"</c:if>
							type="radio" name="cate" value=${cate.code } <c:if test="${cate.code eq condition.cate}">checked="checked"</c:if>/><span class="mark"></span>${cate.name }</label>
					</li>
				</c:forEach>
            </ul>
        </div>
        <div class="filter_item">
            <span class="filter_type filter_type7">课次</span>
            <ul id="lessonList">
            	<c:forEach items="${lessonList }" var="lesson" varStatus="index">
					<li><label class="sky_radio"><input
					<c:if test="${condition.lesson==null&& index.index==0}">checked="checked"</c:if>
							type="radio" name="lesson" value="${lesson.iD }" <c:if test="${lesson.iD eq condition.lesson}">checked="checked"</c:if>/><span class="mark"></span>第${lesson.sortNo }课次</label>
					</li>
				</c:forEach>
				<c:if test="${fn:length (lessonList)==0 }"><li>暂无课次</li> </c:if>
            </ul>
        </div>
    	<div class="filter_item">
			<span class="filter_type filter_type8">班级</span>
			<ul>
				<li><label><input type="text" id="className" /> </label>
				</li>
			</ul>
		</div>
        <div class="filter_btn">
            <button class="sol_btn" onclick="searchForButton()">查询</button>
        </div>
    </div>
    <!-- end 筛选列表 -->
    <!-- begin 班级筛选结果 -->
    <div class="wa_box class_result">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span class="current">班级筛选</span>
            </div>
        </div>
        <table class="wa_table">
            <thead>
                <tr>
                    <th>班级</th>
                    <th>阅读</th>
                    <th>口语</th>
                    <th>听力</th>
                    <th>语法</th>
                    <th>词汇</th>
                    <th>最高分</th>
                    <th>最低分</th>
                    <th>平均分</th>
                    <th>最高正确率</th>
                    <th>最低正确率</th>
                    <th>平均正确率</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody id="courseList">
            	<c:forEach items="${courseList }" var="course">
            		<tr><td>${course.name }</td><td>${course.maxScore }</td><td>${course.minScore }</td><td>${course.avgScore }</td><td>${course.maxCorrect }</td><td>${course.minCorrect }</td><td>${course.avgCorrect }</td><td>${course.id }</td></tr>
            	</c:forEach>
            </tbody>
        </table>
    </div>
    <!-- end 班级筛选结果 -->
    <script type="text/javascript" src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/js/space_underline.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
    <script type="text/javascript">
    	function search(){
    		var dept = $("input[name='dept']:checked").val();
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
    		var className = $("#className").val();
    		
    		if (!isNull(className)) {
	    		if (typeof(lesson)=="undefined" || isNull(lesson)) {
					alert("请选择课次");
					return;
				}
			}
    		
    		skyAlert.loading("查找数据中。。。。");
    		$.ajax({
                url : "<%=path%>/work/getScheduleClassList.jhtml",
                type : "post",
                data : {
                    dept : dept,
                    period : period,
                    grade : grade,
                    subject : subject,
                    cate : cate,
                    className : className
                },
                dataType : 'JSON',
                success : function(result) {
                    var courseList = result.courseList;
                    var lessonList = result.lessonList;
                    var flag =result.flag;
                    var lessonId = result.condition.lessonId;
                    doCourseList(courseList,lessonId);
                  	doLessonList(lessonList);
                    skyAlert.loading(false);
                },
                error : function(result){
                	skyAlert.loading(false);
                }
            });
    	}
    	
    	function searchForButton(){
    		var dept = $("input[name='dept']:checked").val();
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
    		var lesson = $("input[name='lesson']:checked").val();
    		var className = $("#className").val();
    		
/*     		if (!isNull(className)) {
	    		if (typeof(lesson)=="undefined" || isNull(lesson)) {
					alert("请选择课次");
					return;
				}
			} */
    		
    		skyAlert.loading("查找数据中。。。。");
    		$.ajax({
                url : "<%=path%>/work/getScheduleClassList.jhtml",
                type : "post",
                data : {
                    dept : dept,
                    period : period,
                    grade : grade,
                    subject : subject,
                    cate : cate,
                    lesson : lesson,
                    className : className
                },
                dataType : 'JSON',
                success : function(result) {
                    var courseList = result.courseList;
                    var lessonList = result.lessonList;
                    var flag =result.flag;
                    var lessonId = result.condition.lessonId;
                    doCourseList(courseList,lessonId);
                    if(flag){
	                  	doLessonList(lessonList);
                    }
                    skyAlert.loading(false);
                },
                error : function(result){
                	skyAlert.loading(false);
                }
            });
    	}
    	
    	function searchForLesson(){
    		var dept = $("input[name='dept']:checked").val();
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
    		var lesson = $("input[name='lesson']:checked").val();
    		var className = $("#className").val();
    		
    		skyAlert.loading("查找数据中。。。。");
    		$.ajax({
                url : "<%=path%>/work/getScheduleClassList.jhtml",
                type : "post",
                data : {
                    dept : dept,
                    period : period,
                    grade : grade,
                    subject : subject,
                    cate : cate,
                    lesson : lesson,
                    className : className
                },
                dataType : 'JSON',
                success : function(result) {
                    var courseList = result.courseList;
                    var lessonId = result.condition.lessonId;
                    doCourseList(courseList,lessonId);
                    skyAlert.loading(false);
                },
                error : function(result){
                	skyAlert.loading(false);
                }
            });
    	}
    	
    	function searchForName(){
    		var lesson = $("input[name='lesson']:checked").val();
    		if (typeof(lesson)=="undefined" || isNull(lesson)) {
				alert("请选择课次");
				return;
			}
    		var dept = $("input[name='dept']:checked").val();
    		var period = $("input[name='period']:checked").val();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
    		var className = $("#className").val();
    		
    		skyAlert.loading("查找数据中。。。。");
    		$.ajax({
                url : "<%=path%>/work/getScheduleClassList.jhtml",
                type : "post",
                data : {
                    dept : dept,
                    period : period,
                    grade : grade,
                    subject : subject,
                    cate : cate,
                    lesson : lesson,
                    className : className
                },
                dataType : 'JSON',
                success : function(result) {
                    var courseList = result.courseList;
                    var lessonId = result.condition.lessonId;
                    doCourseList(courseList,lessonId);
                    skyAlert.loading(false);
                },
                error : function(result){
                	skyAlert.loading(false);
                }
            });
    	}
    	
    	function doCourseList(courseList,lessonId){
    		var html="";
    		for (var i=0;i<courseList.length;i++){
    			var course = courseList[i];
    			var courseId = course.id;
    			var name = course.name;
    			var category1 = course.category1==1?'<font color="green">已发布</font>':'<font color="red">未发布</font>';
    			var category2 = course.category2==1?'<font color="green">已发布</font>':'<font color="red">未发布</font>';
    			var category3 = course.category3==1?'<font color="green">已发布</font>':'<font color="red">未发布</font>';
    			var category4 = course.category4==1?'<font color="green">已发布</font>':'<font color="red">未发布</font>';
    			var category5 = course.category5==1?'<font color="green">已发布</font>':'<font color="red">未发布</font>';
    			var maxScore = course.maxScore.toFixed(2);
    			var minScore = course.minScore.toFixed(2);
    			var avgScore = course.avgScore.toFixed(2);
    			var maxCorrect = (course.maxCorrect*100).toFixed(2);
    			var minCorrect = (course.minCorrect*100).toFixed(2);
    			var avgCorrect = (course.avgCorrect*100).toFixed(2);
    			
    			html = html + " <tr><td>"+name+"</td><td>"+category1+"</td><td>"+category2+"</td><td>"+category3+"</td><td>"+category4+"</td><td>"+category5+"</td><td>"+maxScore+"</td><td>"+minScore+"</td><td>"+avgScore+"</td><td>"+maxCorrect+"%</td><td>"+minCorrect+"%</td><td>"+avgCorrect+"%</td><td><a href='javascript:;' onclick='goDetail("+courseId+","+lessonId+")'>详情</a></td></tr>";
    		}
    		$("#courseList").html(html);
    	}
    	
    	function doLessonList(lessonList){
    		var html="";
    		for (var i=0;i<lessonList.length;i++){
    			var lesson = lessonList[i];
    			var cc = "";
    			if(i==0){
    				cc= "checked='checked'";
    			}
    			html = html + "<li><label class='sky_radio'><input type='radio' name='lesson' value='"+lesson.iD+"' onchange='searchForLesson()' "+cc+" /><span class=\"mark\" ></span>第"+lesson.sortNo+"课次</label></li>";
    		}
    		if (lessonList==null || lessonList.length==0) {
				html = "<li>暂无课次</li>";
			}
    		$("#lessonList").html(html);
    	}
    	
    	function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		
		function goDetail(courseId,lessonId){
			skyAlert.loading("查找数据中。。。。");
			window.location.href="<%=path%>/work/scheduleClassDetail.jhtml?lessonId="+lessonId+"&courseId="+courseId;
		}
		
	 $(function(){
	    $("input[name='dept']").change(function(){
	            search();
	     });
	     $("input[name='period']").change(function(){
	            search();
	     });
	     $("input[name='subject']").change(function(){
	            search();
	     });
	     $("input[name='grade']").change(function(){
	            search();
	     });
	     $("input[name='cate']").change(function(){
	            search();
	     });
	     $("input[name='lesson']").change(function(){
	            searchForLesson();
	     });
	})
	
	$("#className").bind('keydown',function(event){
    	if(event.keyCode == "13") {
	        this.blur();
   		}
    });
    </script>
  </body>
</html>
