<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/imageTag/css/reset.pchlin.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/imageTag/css/public.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/imageTag/css/classList.css">
    </head>
    <body>
        <!-- begin 筛选列表 -->
        <div class="wa_box filter_list">
        	<div class="filter_item">
	            <span class="filter_type filter_type1">地区</span>
	            <ul>
                	<li><label class="sky_radio"><input type="radio" name="city" value="1" checked="checked"/><span class="mark"></span>深圳</label></li>
                	<li><label class="sky_radio"><input type="radio" name="city" value="2"/><span class="mark"></span>杭州</label></li>
                	<li><label class="sky_radio"><input type="radio" name="city" value="3"/><span class="mark"></span>深圳+</label></li>
                	<li><label class="sky_radio"><input type="radio" name="city" value="4"/><span class="mark"></span>即刻说</label></li>
	            </ul>
	        </div>
	        
            <div class="filter_item">
                <span class="filter_type filter_type3">年级</span>
                <ul>
	                <c:forEach var="grade" items="${gradeList}">
	                	<c:if test="${fn:trim(grade.code) lt 'A07' }">
		                	<li><label class="sky_radio"><input type="radio" name="grade" value="${grade.code}"/><span class="mark"></span>${grade.name}</label></li>
	                	</c:if>
	                </c:forEach>
                </ul>
            </div>
	        
        	<div class="filter_item">
	            <span class="filter_type filter_type2">等级</span>
	            <ul>
                	<li><label class="sky_radio"><input type="radio" name="level" value="111" checked="checked"/><span class="mark"></span>level1+</label></li>
                	<li><label class="sky_radio"><input type="radio" name="level" value="112"/><span class="mark"></span>level2</label></li>
                	<li><label class="sky_radio"><input type="radio" name="level" value="113"/><span class="mark"></span>level3</label></li>
                	<li><label class="sky_radio"><input type="radio" name="level" value="114"/><span class="mark"></span>level4</label></li>
                	<li><label class="sky_radio"><input type="radio" name="level" value="115"/><span class="mark"></span>level5</label></li>
	            </ul>
	        </div>
	        
	        <div class="filter_item">
                <span class="filter_type filter_type3">等级</span>
                <ul>
                	<li><label class="sky_radio"><input type="radio" name="grade_level" value="A01" checked="checked"/><span class="mark"></span>level1+</label></li>
                	<li><label class="sky_radio"><input type="radio" name="grade_level" value="A02"/><span class="mark"></span>level2</label></li>
                	<li><label class="sky_radio"><input type="radio" name="grade_level" value="A03"/><span class="mark"></span>level3</label></li>
                	<li><label class="sky_radio"><input type="radio" name="grade_level" value="A04"/><span class="mark"></span>level4</label></li>
                	<li><label class="sky_radio"><input type="radio" name="grade_level" value="A05"/><span class="mark"></span>level5</label></li>
                    <li><label class="sky_radio"><input type="radio" name="grade_level" value="A06"/><span class="mark"></span>level6</label></li>
                </ul>
            </div>
        	
        	<!-- 即可说 -->
        	 <div class="filter_item">
                <span class="filter_type filter_type5">等级</span>
                <ul>
                	<li><label class="sky_radio"><input type="radio" name="speak_level" value="411" checked="checked" _gradeCode="2"/><span class="mark"></span>k1</label></li>
                	<li><label class="sky_radio"><input type="radio" name="speak_level" value="412"/><span class="mark" _gradeCode="3"></span>k2</label></li>
                	<li><label class="sky_radio"><input type="radio" name="speak_level" value="413"/><span class="mark" _gradeCode="4"></span>k3</label></li>
                </ul>
            </div>
            <div class="filter_item">
                <span class="filter_type filter_type4">科目</span>
                <ul>
                	<c:forEach var="subject" items="${subjectList}">
                		<c:if test="${subject.code eq 'A01   ' }">
		                	<li><label class="sky_radio"><input type="radio" name="subject" value="${subject.code}"/><span class="mark"></span>${subject.name}</label></li>
                		</c:if>
	                </c:forEach>
                </ul>
            </div>
        </div>
        <!-- end 筛选列表 -->
			
        <!-- begin 班级筛选结果 -->
        <div class="wa_box class_book">
            <ul class="result_list clear">
            	
            </ul>
        </div>
        <!-- end 班级筛选结果 -->
    </body>
    <script type="text/javascript" src="<%=path%>/sys/imageTag/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/imageTag/js/classList.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/imageTag/js/common.js"></script>
    <script type="text/javascript" src="<%=path%>/sys/imageTag/js/layer.js"></script>
    <script type="text/javascript">
    	$('.sky_radio').on('click', "input", function(e){
    		getStatus(e.target.name == 'city');
    	});
    	
    	function getStatus(bool){
    		
    		var city = $("input[name='city']:checked").val();
    		if (bool) {  //页面加载或者点击地区选项时执行
	    		if (city == 1) { //深圳
	    			$('input[name="level"]').removeAttr('checked');
	    			$('input[name="grade_level"]').removeAttr('checked');
	    			$('input[name="speak_level"]').removeAttr('checked');
	    			
	    			$("input[name='level']").parents('.filter_item').hide();
	    			$("input[name='grade_level']").parents('.filter_item').hide();	    				    			
	    			$("input[name='speak_level']").parents('.filter_item').hide();
	    			var grade = localStorage.getItem('imageTag_grade');
	    			if (isNotEmpty(grade)){
	         			$("input[name='grade'][value='"+ grade +"']").get(0).checked=true;
	         		} else {
	         			$("input[name='grade']").get(0).checked=true; 
	         		}
	    			$("input[name='grade']").parents('.filter_item').show();
				} else if (city == 2) {  //杭州
					$('input[name="grade"]').removeAttr('checked');
					$('input[name="grade_level"]').removeAttr('checked');
					$('input[name="speak_level"]').removeAttr('checked');
					
	    			$("input[name='grade']").parents('.filter_item').hide();
	    			$("input[name='grade_level']").parents('.filter_item').hide();
	    			$("input[name='speak_level']").parents('.filter_item').hide();
	    			
	    			var level = localStorage.getItem('imageTag_level');
	    			if (isNotEmpty(level)){
	         			$("input[name='level'][value='"+ level +"']").get(0).checked=true;
	         		} else {
	         			$("input[name='level']").get(0).checked=true; 
	         		}
	    			$("input[name='level']").parents('.filter_item').show();
				} else if(city==3){
					$('input[name="grade"]').removeAttr('checked');
					$('input[name="level"]').removeAttr('checked');
					$('input[name="speak_level"]').removeAttr('checked');
	    			$("input[name='grade']").parents('.filter_item').hide();
	    			$("input[name='level']").parents('.filter_item').hide();
	    			$("input[name='speak_level']").parents('.filter_item').hide();
	    			var grade_level = localStorage.getItem('imageTag_grade_level');
	    			if (isNotEmpty(grade_level)){
	         			$("input[name='grade_level'][value='"+ grade_level +"']").get(0).checked=true;
	         		} else {
	         			$("input[name='grade_level']").get(0).checked=true; 
	         		}
	    			$("input[name='grade_level']").parents('.filter_item').show();
				} else{
					$('input[name="grade"]').removeAttr('checked');
					$('input[name="level"]').removeAttr('checked');
					$('input[name="grade_level"]').removeAttr('checked');
	    			$("input[name='grade']").parents('.filter_item').hide();
	    			$("input[name='level']").parents('.filter_item').hide();
	    			$("input[name='grade_level']").parents('.filter_item').hide();
	    			var speak_level = localStorage.getItem('imageTag_speak_level');
	    			if (isNotEmpty(speak_level)){
	         			$("input[name='speak_level'][value='"+ speak_level +"']").get(0).checked=true;
	         		} else {
	         			$("input[name='speak_level']").get(0).checked=true; 
	         		}
	    			$("input[name='speak_level']").parents('.filter_item').show();
				}
			}
    		var index = layer.load(1, {shade: [0.2,'#fff']}); //0代表加载的风格，支持0-2
    		memory();
    		var grade = $("input[name='grade']:checked").val();
    		var subject = $("input[name='subject']:checked").val();
    		var level = $("input[name='level']:checked").val();
    		if (city == 3) {
				grade = $("input[name='grade_level']:checked").val();
				level = 201;
			}
    		if (city == 4) {
				grade = $("input[name='speak_level']:checked").attr("_gradeCode");
				level = $("input[name='speak_level']:checked").val();
			}
    		$.ajax({
    	        url: getRootPath() + "/imageTag/getCateAndStatus.jhtml",
    	        type: 'POST',
    	        data: {'subject':subject,'grade':grade,'period':76,'level':level},
    	        dataType: "json",
    	        success: function (data) {
    	        	$('.result_list').html('');
    	        	var htmlStr = "";
    	        	$.each(data.list, function(i, elem) {
    	        		var stuta = elem.status == 2 ? '1': elem.status == 0 ? '0': '2'; 
    	        		htmlStr += '<li class="result_item" bookId="'+ elem.id +'" state="'+ stuta +'" lessonCount="'+ elem.lessonCount +'"> '+ 	
		    	        		 '    <a class="result_item_a" href="javascript:; ">'+ 
		    	        		 '      <span class="book_bg"><img src="../sys/imageTag/pop/'+ elem.pop +'"></span>' +
		    	        		 '		<span class="big_title" title="'+ elem.bookName +'" name="'+ elem.cateCode +'">'+ elem.bookName +'</span>'+ 
		    	        		 '		<span class="small_title">'+ getCateName(elem.name, elem.cateCode, elem.gradeCode,city) +'</span>'+ 
		    	        		 '		<span class="book_btn"></span>'+ 
		    	        		 '    </a>'+ 
		    	        		 '</li>';
					});
    	        	$('.result_list').append(htmlStr);
    	        	$('.result_item_a').click(function(){
    	        		if($(this).parent('.result_item').attr('lessonCount') == 0){
    	        			layer.msg('当前层次无课次！');
    	        			return false;
    	        		}
    	        		var bookId = $(this).parent('.result_item').attr('bookId');
    	   				var bookName = $(this).find('.big_title').html().replace('amp;','');
    	   				var goUrl = getRootPath() + "/imageTag/editImageTag.jhtml?" +
    	   					"period=76&bookId="+ bookId;
    	   				window.open(goUrl);
    	        	});
    	        	layer.close(index);
    	        }
    	    });
       		layer.close(index);
    	}
    	
    	function getCateName(name, level, grade,city){
    		console.log(grade);
    		console.log(city);
    		if (isNotEmpty(name)) {
				return name;
			}
    		if(city==4){
    			level = Number(level) - 410;
    			return "k"+level;
    		}
    		if (level == 201){
    			if (grade == 'A01') {
    				return "level1+"; 
				}
    			return "level"+grade.replace('A0', '');
    		}
    		if (level == 111){
    			return "level1+";
    		}
    		level = Number(level) - 110;
    		return "level"+ level;
    	}
    	
    	$(function(){
    		radioSelect();
     		getStatus(true);
    	});
    	
    	function radioSelect(){
    		var city = localStorage.getItem('imageTag_city');
    		var subject = localStorage.getItem('imageTag_subject');
     		var grade = localStorage.getItem('imageTag_grade');
     		var level = localStorage.getItem('imageTag_level');
     		var grade_level = localStorage.getItem('imageTag_grade_level');
     		var speak_level = localStorage.getItem('imageTag_speak_level');
     		
     		if (isNotEmpty(city)){
     			$("input[name='city'][value='"+ city +"']").get(0).checked=true;
     		} else {
     			$("input[name='city']").get(0).checked=true; 
     		}
     		
     		if (isNotEmpty(subject)){
     			$("input[name='subject'][value='"+ subject +"']").get(0).checked=true;
     		} else {
     			$("input[name='subject']").get(0).checked=true; 
     		}
     		
     		if (isNotEmpty(grade)){
     			$("input[name='grade'][value='"+ grade +"']").get(0).checked=true;
     		} else {
     			$("input[name='grade']").get(0).checked=true; 
     		}
     		
     		if (isNotEmpty(level)){
     			$("input[name='level'][value='"+ level +"']").get(0).checked=true;
     		} else {
     			$("input[name='level']").get(0).checked=true; 
     		}
     		
     		if (isNotEmpty(grade_level)){
     			$("input[name='grade_level'][value='"+ grade_level +"']").get(0).checked=true;
     		} else {
     			$("input[name='grade_level']").get(0).checked=true; 
     		}
     		if (isNotEmpty(speak_level)){
     			$("input[name='speak_level'][value='"+ speak_level +"']").get(0).checked=true;
     		} else {
     			$("input[name='speak_level']").get(0).checked=true; 
     		}
    	}
    	
    	function memory(){
       		var city = $("input[name='city']:checked").val();//科目
       		var subject = $("input[name='subject']:checked").val();//科目
      		var grade = $("input[name='grade']:checked").val();//年级
      		var level = $("input[name='level']:checked").val();//学期
      		var grade_level = $("input[name='grade_level']:checked").val();//学期
      		var speak_level = $("input[name='speak_level']:checked").val();//学期
			if (isNotEmpty(city)) {
	    	   	localStorage.setItem('imageTag_city', city);
			}
			if (isNotEmpty(subject)) {
	    	   	localStorage.setItem('imageTag_subject', subject);
			}
			if (isNotEmpty(grade)) {
	    	   	localStorage.setItem('imageTag_grade', grade);
			}
			if (isNotEmpty(level)) {
	    	   	localStorage.setItem('imageTag_level', level);
			}
			if (isNotEmpty(grade_level)) {
	    	   	localStorage.setItem('imageTag_grade_level', grade_level);
			}
			if (isNotEmpty(speak_level)) {
	    	   	localStorage.setItem('imageTag_speak_level', speak_level);
			}
       }
    	
       window.onbeforeunload = memory;
   	   window.onunload = memory;
    </script>
</html>