<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>编辑</title>
    <link rel="stylesheet" href="<%=path%>/sys/imageTag/css/start.css">
    <link rel="stylesheet" href="<%=path%>/sys/imageTag/css/daw.css">
    <link rel="stylesheet" href="<%=path%>/sys/imageTag/css/common.css">
</head>
<body onload="choose(event)">


<!--头部-->
<div class="header">

</div>
<!--头部end-->

<!-- begin 略缩图列表 -->

<div class="left wrap">
<!--     <i class="addPPT">上传图片</i> -->
<input type="hidden" id="filePath" name="filePath" value="${filePath}">
<input type="hidden" id="tempFilePath" name="tempFilePath" value="${tempFilePath}">
<form id="uploadForm">
	<label>
	    <span class="addImg">上传图片</span>
		<input type="hidden" id="bookId" name="bookId" value="${param.bookId}">
		<input type="hidden" id="period" name="period" value="${param.period}">  
		<input type="hidden" id="bookName" name="bookName" value="${bookName}">  
	    <input id="uploadFile" style="display: none" type="file" size="30" name="uploadFiles" onchange="upLoad(this)" multiple="multiple" accept="image/jpeg,image/png,image/gif" />
	</label>
</form>
    <ul class="leftUl">
    	<c:forEach var="url" items="${list}" varStatus="pageIndex">
    		<c:choose>
    			<c:when test="${pageIndex.count eq 1 }">
    				<li class="page_box" name="1" pageIndex="${pageIndex.count}">
			               <div class="selectCont after" style="width: 42px;height: 31px;float:left;margin-right: 5px">
			               	   <select class="active">
				                   <c:forEach var="lesson" items="${lesson}" varStatus="lessonCount">
				                   		<option lessonId="${lesson.id}" lessonTitle="${lesson.name}" lessonDesc="${lesson.description}" lessonPop="${lesson.lessonPop}" value="${lesson.orderNo}">${lesson.orderNo}.${lesson.name}</option>
				                   </c:forEach>
				               </select>
			               </div>
			            <span class="closePPT">×</span>
			            <div class="page" pageNum="${pageIndex.count}" pageIndex="${pageIndex.count}"><img class="thumbImg imgNum" name="${url}" src="${filePath}${url}" alt=""></div>
			        </li>
			    </c:when>
    			<c:otherwise>
    				<li class="page_box" name="1" pageIndex="${pageIndex.count}">
			               <div class="selectCont" style="width: 42px;height: 31px;float:left;margin-right: 5px">
			               	   <select>
				                   <c:forEach var="lesson" items="${lesson}" varStatus="lessonCount">
				                   		<option lessonId="${lesson.id}" lessonTitle="${lesson.name}" lessonDesc="${lesson.description}" lessonPop="${lesson.lessonPop}" value="${lesson.orderNo}">${lesson.orderNo}.${lesson.name}</option>
				                   </c:forEach>
				               </select>
			               </div>
			            <span class="closePPT">×</span>
			            <div class="page" pageNum="${pageIndex.count}" pageIndex="${pageIndex.count}"><img class="thumbImg imgNum" name="${url}" src="${filePath}${url}" alt=""></div>
			        </li>
    			</c:otherwise>
    		</c:choose>
    	</c:forEach>
    </ul>
</div>

<!-- end 略缩图列表 -->

<!-- begin 编辑器区域 -->
<div class="right" spellcheck="false">
     <c:forEach items="${list}" var="url" varStatus="pageIndex">
   		<div class="editor_box" pageIndex="${pageIndex.count}">
	        <div class="bigImgCont" >
	            <img id="img_1" class="imgNum" name="${url}" style="height: auto;width: auto" draggable="false" src="${filePath}${url}" name="1" alt="">
	        </div>
	    </div>
   	</c:forEach>
</div>
<div class="shapeChoose">
    <h1>形状选择</h1>
    <span class="rectangle"></span>
    <span class="circular"></span>
<!--     <span class="submitBBBB">打包</span> -->
<!--     <span class="continueUpload">继续上传图片</span> -->
    <span class="submit">提交</span>
</div>
<!-- end 编辑器区域 -->

<!--右键菜单-->
<div class="rightClick" style="position: absolute;left: 0;top:0;display: none;">
    <ul>
    	<li class="rightClickSet">设置</li>
    </ul>
</div>



</body>
<script src="<%=path%>/sys/imageTag/js/jquery-2.2.3.min.js"></script>
<script src="<%=path%>/sys/imageTag/js/layer.js"></script>
<script src="<%=path%>/sys/imageTag/js/ddsort.min.js"></script>
<script src="<%=path%>/sys/imageTag/js/flexible.js"></script>
<script src="<%=path%>/sys/imageTag/js/daw.js"></script>
<script src="<%=path%>/sys/imageTag/js/common.js"></script>
<script src="<%=path%>/sys/imageTag/js/editImageTag.js"></script>
<script src="<%=path%>/sys/js/sky-aleart.js"></script>
<script src="<%=path%>/sys/imageTag/js/tagClass.js"></script>
<script src="<%=path%>/sys/imageTag/js/index.js"></script>
<script>
	$(document).ready(function(){
		$(document).bind("contextmenu",function(e){
	    	return false;
		});
		$(document).on("click",function(e){
	    	$('.rightClick').hide();
		});
	});

    $(document).on('contextmenu','.bigImgCont',function(){
        return false;
    });

    $(function(){
    	lessonStr = '${lessonStr}';
    	lessonStr = lessonStr.replace("`", "'");
    	debugger
    	var json = '${json}';
    	if(isNotEmpty(json)){

            json = Base64.decode(json);
            console.log(json);
            var parse = json.replace("`", "'");
            console.log(parse);
   			drawPage(JSON.parse(parse));

   			//drawPage(JSON.parse(json));
    	}
    });

    var Base64 = {
        _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
        encode: function(e) {
            var t = "";
            var n, r, i, s, o, u, a;
            var f = 0;
            e = Base64._utf8_encode(e);
            while (f < e.length) {
                n = e.charCodeAt(f++);
                r = e.charCodeAt(f++);
                i = e.charCodeAt(f++);
                s = n >> 2;
                o = (n & 3) << 4 | r >> 4;
                u = (r & 15) << 2 | i >> 6;
                a = i & 63;
                if (isNaN(r)) {
                    u = a = 64
                } else if (isNaN(i)) {
                    a = 64
                }
                t = t + this._keyStr.charAt(s) + this._keyStr.charAt(o) + this._keyStr.charAt(u) + this._keyStr.charAt(a)
            }
            return t
        },
        decode: function(e) {
            var t = "";
            var n, r, i;
            var s, o, u, a;
            var f = 0;
            e = e.replace(/[^A-Za-z0-9+/=]/g, "");
            while (f < e.length) {
                s = this._keyStr.indexOf(e.charAt(f++));
                o = this._keyStr.indexOf(e.charAt(f++));
                u = this._keyStr.indexOf(e.charAt(f++));
                a = this._keyStr.indexOf(e.charAt(f++));
                n = s << 2 | o >> 4;
                r = (o & 15) << 4 | u >> 2;
                i = (u & 3) << 6 | a;
                t = t + String.fromCharCode(n);
                if (u != 64) {
                    t = t + String.fromCharCode(r)
                }
                if (a != 64) {
                    t = t + String.fromCharCode(i)
                }
            }
            t = Base64._utf8_decode(t);
            return t
        },
        _utf8_encode: function(e) {
            e = e.replace(/rn/g, "n");
            var t = "";
            for (var n = 0; n < e.length; n++) {
                var r = e.charCodeAt(n);
                if (r < 128) {
                    t += String.fromCharCode(r)
                } else if (r > 127 && r < 2048) {
                    t += String.fromCharCode(r >> 6 | 192);
                    t += String.fromCharCode(r & 63 | 128)
                } else {
                    t += String.fromCharCode(r >> 12 | 224);
                    t += String.fromCharCode(r >> 6 & 63 | 128);
                    t += String.fromCharCode(r & 63 | 128)
                }
            }
            return t
        },
        _utf8_decode: function(e) {
            var t = "";
            var n = 0;
            var r = c1 = c2 = 0;
            while (n < e.length) {
                r = e.charCodeAt(n);
                if (r < 128) {
                    t += String.fromCharCode(r);
                    n++
                } else if (r > 191 && r < 224) {
                    c2 = e.charCodeAt(n + 1);
                    t += String.fromCharCode((r & 31) << 6 | c2 & 63);
                    n += 2
                } else {
                    c2 = e.charCodeAt(n + 1);
                    c3 = e.charCodeAt(n + 2);
                    t += String.fromCharCode((r & 15) << 12 | (c2 & 63) << 6 | c3 & 63);
                    n += 3
                }
            }
            return t
        }
    }

</script>
</html>