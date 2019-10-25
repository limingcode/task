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

<title>My JSP 'editQuestion.jsp' starting page</title>

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
	href="<%=path%>/sys/css/public.css?879">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/wangEditor.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/jquery.jsonview.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/editor_plug.css?20170919">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/questionManage.css">
</head>

<body>
	<!-- begin 筛选列表 -->
	<div class="wa_box filter_list">
		<div class="filter_item">
			<span class="filter_type filter_type3">年级</span>
			<ul>
				<c:forEach items="${gradeList }" var="grade" varStatus="index">
					<li><label class="sky_radio"><input type="radio"
							name="grade" value=${grade.code }
							<c:if test="${fn:trim(grade.code) eq condition.grade }">checked="checked"</c:if> /><span
							class="mark"></span>${grade.name }</label></li>
				</c:forEach>
			</ul>
		</div>
		<div class="filter_item">
			<span class="filter_type filter_type4">科目</span>
			<ul>
				<c:forEach items="${subjectList }" var="subject" varStatus="index">
					<li><label class="sky_radio"><input type="radio"
							name="subject" value=${subject.code }
							<c:if test="${fn:trim(subject.code) eq condition.subject }">checked="checked"</c:if> /><span
							class="mark"></span>${subject.name }</label></li>
				</c:forEach>
			</ul>
		</div>
		<div class="filter_item">
			<span class="filter_type filter_type5">层次</span>
			<ul>
				<c:forEach items="${cateList }" var="cate" varStatus="index">
					<li><label class="sky_radio"><input type="radio"
							name="cate" value=${cate.code }
							<c:if test="${cate.code eq condition.cate}">checked="checked"</c:if> /><span
							class="mark"></span>${cate.name }</label></li>
				</c:forEach>
			</ul>
		</div>
		<!-- <div class="filter_btn">
			<button class="hol_btn" onclick="search()">查询</button>
			<button class="sol_btn" onclick="input()">录入</button>
		</div> -->
	</div>
	<!-- end 筛选列表 -->

	<!-- begin 编辑器 -->
	<div class="wa_box q_editor">
		<div class="wa_table_oper">
			<div class="table_name">
				<span class="current">题库录入</span>
			</div>
			<div class="title">
				<label>标题：<input type="text" />
				</label>
			</div>
			<div class="oper_btn">
				<button class="sol_btn add_q_item" disabled="true"
					style="cursor:not-allowed">添加</button>
			</div>
		</div>
		<!-- begin 小题容器 -->
		<div class="q_box"></div>
		<!-- end 小题容器 -->

		<!-- begin 编辑器 -->
		<div class="editor_box">
			<div class="qusetion_time">
				<label>答题时间：<input type="number">
				</label>秒
			</div>
			<div id="editor" class="editor" style="height:800px;width:681px;border: 1px solid #888;margin: 10px auto;"></div>
			<div class="box_bottom_btn">
				<button class="sol_btn submit_item_btn">校验</button>
			</div>
		</div>
		<div class="box_bottom_btn submit_all_btn">
			<button class="hol_btn" name="q_item0">提交</button>
		</div>
	</div>
	<!-- end 编辑器 -->
	<script type="text/javascript"
		src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript"
		src="<%=path%>/sys/js/space_underline.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/cache.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/wangEditor.js"></script>
	<script type="text/javascript"
		src="<%=path%>/sys/js/jquery.jsonview.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/editor_plug.js?20171122"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/editor.js?20171106"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/questionEditor.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/public.js"></script>
	<script type="text/javascript">
    	function search(){
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
    		if (typeof(grade)=="undefined" || isNull(grade)) {
				alert("请选择年级");
				return;
			}
			if (typeof(subject)=="undefined"||isNull(subject)) {
				alert("请选择科目");
				return;
			}
			if (typeof(cate)=="undefined"||isNull(cate)) {
				alert("请选择层次");
				return;
			}
    		window.location.href="<%=path%>/question/questionList.jhtml?grade="+grade+"&subject="+subject+"&cate="+cate;
    	}
    	
    	function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		
		function commitQuestion(){
			var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var cate = $("input[name='cate']:checked").val();
    		if (typeof(grade)=="undefined" || isNull(grade)) {
				alert("请选择年级");
				return;
			}
			if (typeof(subject)=="undefined"||isNull(subject)) {
				alert("请选择科目");
				return;
			}
			if (typeof(cate)=="undefined"||isNull(cate)) {
				alert("请选择层次");
				return;
			}
			if($(".editor_box").is(":visible")){
				alert("请先校验题目");
				return;
			}
			if(isNull($(".q_editor .title input").val())){
				alert("标题不能为空");
				return;
			}
			var qItemList = new Array();
			
			$(".q_box .q_item").each(function(){
				qItemList.push({
					"qItemCnt":$(this).find(".q_item_cnt ").html(),
					"qItemJson":$(this).find(".q_item_json ").html()
				});
			});
			var gradet = $(".sky_radio input[name='grade']:checked").parent().text();
			var subjectt = $(".sky_radio input[name='subject']:checked").parent().text();
			var catet = $(".sky_radio input[name='cate']:checked").parent().text();
			
			if (confirm("题目即将录入	"+gradet+" "+subjectt+" "+catet+"	层次")) {
				skyAlert.loading("");
					$.ajax({
						url:"<%=path%>/question/commitQuestion.jhtml",
						type : "post",
						data : {
							qItemList : JSON.stringify(qItemList),
							grade : grade,
							subject : subject,
							cate : cate
						},
						dataType : 'JSON',
						success : function(result) {
							var code = result.code;
							var message = result.message;
							if (code == 100) {
								var $qEditor = $(".q_editor");
								$qEditor.find(".wa_table_oper .title input")
										.val("");
								$(".q_box").after($(".editor_box").slideDown())
								$qEditor.find(".q_box").html("");
								$qEditor.find(".qusetion_time input").val("");
								$qEditor.find(".ananly_cnt").val("");
								$qEditor.find(".editor").html("<p><br></p>");
								$qEditor.find(".audio_panel .space").remove();
								$qEditor.find(".audio_panel .audio_item")
										.remove();
								//清除缓存
								//localStorage.clear();
								localStorage.removeItem("filterListCnt",
										"qBoxCnt", "editorCnt",
										"editorPosition", "audioBox",
										"ananlyCnt", "questionTitle",
										"qusetionTime", "questionAScore",
										"editorState");
							};
							skyAlert.loading(false);
							alert(message);
						},
						error:function(data){
							skyAlert.loading(false);
							var msg = data.responseText
							eval(msg.replace(/(<script>|<\/script>)/g,''))
						}
					});
			}
			

	}
		$(".submit_all_btn button").click(function() {
			commitQuestion();
		})
	</script>
</body>
</html>
