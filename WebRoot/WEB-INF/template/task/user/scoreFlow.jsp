 <%@ page language="java" pageEncoding="UTF-8"%>
 <%String path = request.getContextPath();%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <!doctype html>
<html class="no-js">
<head>
  <jsp:include page="../link_script_head.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="<%=path%>/sys/admin/assets/css/page.css"/>
  <script type="text/javascript" src="<%=path%>/sys/js/util/pageUtil-3.0.1.js"></script>
 <%--  <script type="text/javascript" src="<%=path%>/sys/js/public/select.js"></script> --%>
  <style type="text/css">
  		body {background: url(<%=path %>/${info.coverImage}) left top; background-size: 100% auto;}
  		.am-box {
		    border: 1px solid #E5E5E5; border-radius: 5px; margin: 10px; margin-top: 50%; background: rgba(255,255,255,0.9); 
		    position: relative;
		}		
		.am-box-hd {
		    margin: -75px auto 0 auto;
		    padding: 5px 10px;
		    text-align: center;
		}		
		.am-box-bd {
		    margin-top: 20px;
		}
		.am-box-bd-top { background: rgba(200,200,200,0.5); height: 30px; line-height: 30px; font-weight: bold;}

		.am-list li .shuzi{line-height: 40px;}
		.am-list li .am-progress{ margin-bottom: 0;}
		.am-list-item-desced {padding-top: 1rem;}
		.text-left {text-align: left;}
  	</style>
</head>
<body>
<c:set var="title" value="个人得分详细记录" scope="request" />
<input type="hidden" id="search_dept_id" value="${sessionScope.LOGIN_EMPLOYEE.dept.id}"/>
<jsp:include page="../head.jsp"></jsp:include>
<div class="am-box">
  <div class="am-box-hd">
 		<a href="javascript:;"><img onerror="this.src=default_user_path;" style="width: 150px;height: 150px;" src="<%=path %>/${info.portrait}" alt="${info.name}" class="am-img-thumbnail am-circle app-img-4" /></a>
 		<p class="teacher-name">${info.name} <span class="am-badge am-badge-warning am-round">${info.job.name}</span>
 		<c:if test="${sessionScope.LOGIN_EMPLOYEE.allotIs>info.allotIs }">
 			<a class="am-btn am-btn-success am-round"  data-am-modal="{target: '#kaifeng'}"><i class="am-icon-edit"></i> 开分</a>
 		</c:if>
 		</p>

	<!--弹出框-->
	<div class="am-modal am-modal-alert" tabindex="-1" id="kaifeng">
	  <div class="am-modal-dialog">
	    <!--<div class="am-modal-hd">开分</div>-->
	    <div class="am-modal-bd am-text-left">
	    	<div class="am-form">
				    <div class="am-form-group">
				      <label for="doc-ipt-email-1">开分人员：</label>
				      	${sessionScope.LOGIN_EMPLOYEE.name}
				    </div>
				    <div class="am-form-group">
				      <label for="doc-ipt-email-1">可用积分：</label>
				      ${sessionScope.LOGIN_EMPLOYEE.allotScore}分 (本月剩余)
				    </div>

				    <div class="am-form-group">
				      <label for="doc-ipt-email-1">积分数量${sessionScope.LOGIN_EMPLOYEE.id}</label>
				      <select id="score">
				      <option value=5>5</option>
				      <option value=10>10</option>
					  <option value=20>20</option>
					  <option value=50>50</option>
					  <option value=100>100</option>
				      </select>
				      <!-- <input type="text" class="am-radius" placeholder="输入积分数量" id="score"> -->
				    </div>
				    
				    <div class="am-form-group" id="pictureDiv">
				    </div>

				    <div class="am-form-group">
				      <label for="doc-ipt-pwd-1">加分说明</label>
				      <input type="text" class="am-radius"  placeholder="请填写增加分的说明" id="content">
				    </div>
				    <div class="am-form-group am-form-file" id="upload">
					  <button type="button" class="am-btn am-btn-default am-text-center" style="width: 130px; height: 90px;">
					    <i class="am-icon-plus am-icon-lg"></i> <br />添加图片文件
					  </button>
					</div>
				    <div class="am-form-group">
				    	<button type="submit" id="addScore" class="am-btn am-btn-block am-radius am-btn-lg am-btn-warning"> <i class="am-icon-cog"></i> 确定开分</button>
				    </div>
				</div>
	    </div>
	  </div>
	</div>
	<!--end弹出框-->

 	</div>
 <div class="am-box-bd user">

 <div class="am-g">
	 <div class="am-u-sm-12">
		 <div class="am-btn-group shortcut-navs2">
			 <a class="am-btn am-btn-default" href="<%=path %>/wx/user/scoreFlow.jhtml?id=${info.id}"><em class="am-text-xl"><fmt:formatNumber type='number' pattern="0" value='${acc.base+acc.fixed+acc.business+acc.project+acc.collect}' maxFractionDigits="1"></fmt:formatNumber></em>总积分</a>
			 <a class="am-btn am-btn-default" href="<%=path %>/wx/statistic/dept.jhtml?type=2&id=${info.dept.id}"><em class="am-text-xl">${rqd.orderNo}</em>学期排名</a>
			 <a class="am-btn am-btn-default" href="<%=path %>/wx/statistic/dept.jhtml?type=1&id=${info.dept.id}"><em class="am-text-xl">${rmd.orderNo}</em>月度排名</a>
		 </div>
	 </div>
 </div>


<ul class="am-list am-list-border jf-list">
  <li><a href="<%=path %>/wx/statistic/squad.jhtml?type=2">本学期小组排名:<i class="am-icon-chevron-right"></i><span class="am-badge am-badge-success am-round am-text-default">${rqs.orderNo}</span></a></li>
  <li><a href="<%=path %>/wx/statistic/squad.jhtml?type=1">本月小组排名:<i class="am-icon-chevron-right"></i><span class="am-badge am-badge-danger am-round am-text-default">${rms.orderNo}</span></a></li>
</ul>

 	<ul class="am-list am-list-border">
 		<li style="padding: 0;">
   			<div class="am-box-bd-top">
		   		<div class="am-g">
				  <div class="am-u-sm-3 am-center">开分人</div>
				  <div class="am-u-sm-7">说明</div>
				  <div class="am-u-sm-2 am-center">得分</div>
				</div>
		   	</div>
   		</li>
		<div id="listHtml"></div>
	</ul>
	<div class="page" id="pageHtml"></div>
   </div>
</div>
<input type="hidden" id="add_images"/>
<!--图片幻灯片切换-弹出框-->
<div class="am-modal am-modal-alert" tabindex="-1" id="show-img"style="height: 70%;">
	<div class="am-modal-dialog">
		<div class="am-panel am-panel-default">
			<div class="am-panel-hd">
				<div class="am-slider am-slider-default show-img" data-am-flexslider id="demo-slider-0">
					<ul class="am-slides" id="images_html">
						<li><img src="/sog/sys/sog/images/3c7f6cd9d57388a722d1b171d8a21e91.jpg"></li>
						<li><img src="/sog/sys/sog/images/3c7f6cd9d57388a722d1b171d8a21e91.jpg"></li>
						<li><img src="/sog/sys/sog/images/3c7f6cd9d57388a722d1b171d8a21e91.jpg"></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!--end图片幻灯片切换-弹出框-->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
$("i.am-header-icon").removeClass("am-icon-home").addClass("am-icon-chevron-left").parent("a").attr("href","javascript:history.back();");
var images;
	window.onload=function(){
		var $upload = $("#upload");
		var $pictureDiv = $("#pictureDiv");
		
		$.getJSON(PATH + "/wxJS/getJSSignature.jhtml?url="+(window.location.href.replace(/\&/ig,"%26")), function(data){
			wx.config({
			    appId: '${wx_appid}',
			    timestamp: data.timestamp,
			    nonceStr: data.nonceStr,
			    signature: data.signature,
			    jsApiList: data.jsApiList
			});
		});
		page = new PAGE({
			 maxResults : 10,       			
			 pageName : 'page',     			
			 maxResultsName : 'maxResults', 
			 url : PATH+'/wx/user/scoreFlowList.jhtml' ,
			 pageListHtmlId : 'listHtml',
			 data:function(e){return {"employee":${info.id}};},
			 pageInfoCss :  {type:'next'},
		     onLoad:true,
			 returns :function(e,e1){
				 e1.pageCount = e.count;
				 var html = "";
				 $(e.list).each(function(){
				 	html += "<li class=\"am-g am-list-item-desced\"><div class=\"am-u-sm-3 am-list-thumb\"><a>"+this.record+"</a></div>";
				 	html += "<div class=\"am-u-sm-7 am-list-main\"><a data-am-modal=\"{target: '#my-alert'}\"><span style=\"color:#ff5d5a;font-weight: 600;\">["+(this.type==1?"基础分":this.type>1&&this.type<10?"固定分":this.type>9&&this.type<12?"业务分":"项目分")+"]</span> "+this.content+"</a><span class=\"am-list-item-text\">"+this.create_time.substr(5,5)+"</span>";
				 	if(this.images!=null&&this.images!="null"&&this.images!="")
				 		html += "<a class=\"images_show\" _val=\""+JSON.stringify(this.images).replace(/\"/g, "\'")+"\" href=\"javascript:;\"><i class=\"am-icon-image js-list-img\"></i></a>";
				 	html += "</div><div class=\"am-u-sm-2 am-list-main\"><span class=\"am-badge am-badge-success am-fr\"> "+this.score+"</span></div>";
				 	html += "</li>";
				 });
				 $("#"+e1.pageListHtmlId).append(html/*.replace(/null/g, "---")*/);
			}
		});
		page.submit();
		//绑定开分事件
		$("#addScore").click(function(){
			if($("#score").val()==''){
				$("#score").focus();
				return;
			}
			if($("#content").val()==''){
				$("#content").focus();
				return;
			}
			//$("#add_images").val(JSON.stringify(images));
			/*images = new Array();
			images.push({"title":"","content":"","path":"sys/sdfskjd/sdfs"});
			images.push({"title":"","content":"","path":"sys/sdfskjd/4123"});*/
			$("#addScore").attr("disabled","disabled");
			$.post(PATH+"/wx/user/addScore.jhtml",
				{
					type:11,
					employee:'${info.id}',
					score:$("#score").val(),
					content:$("#content").val(),
					images:images!=undefined&&images.length>0?JSON.stringify(images):""
				},function(data){
					$("#addScore").removeAttr("disabled");
					//刷新当前页面并刷新登陆用户session
					if(data.flag=="000"){
						$("#kaifeng").addClass("am-modal-out").removeClass("am-modal-active").attr("style","").hide();
						$(".am-dimmer").removeClass("am-active").hide();
						$("body").removeClass("am-dimmer-active");
						alert("恭喜您，开分成功!即将刷新本页面");
						window.location.href=window.location.href+"&wx_openid=${sessionScope.LOGIN_EMPLOYEE.wxOpenid}";
					}else{
						var html = "加分异常，请联系管理员(err)";
						if(data.flag=="001")
							html = "余额不足(001)";
						else if(data.flag=="002")
							html = "加分分数不能为负数(002)";
						else if(data.flag=="003")
							html = "加分类型错误(003)";
						else if(data.flag=="004")
							html = "加分事由为空(004)";
						else if(data.flag=="005")
							html = "加分对象错误(005)";
						else if(data.flag=="006")
							html = "操作人错误(006)";
						else if(data.flag=="007")
							html = "加分权限不足(007)";
						else if(data.flag=="100")
							html = "加分异常(100)，请联系管理员";
						else if(data.flag=="008")
							html = "不允许给自己加分(008)";
							
						alert("对不起，您"+html);
						$("#addScore").attr("disabled",false);
					}
			});
		});
	
		//图片集事件
		$("a.images_show").live("click",function(){
			var html = "";
			var obj = $("#demo-slider-0");
			//移除原有的图片
			var count = obj.flexslider('count');
			for(var i = count ; i >0 ; i --){
				obj.flexslider("removeSlide", i-1);
			}
			$.each($.parseJSON($.parseJSON($(this).attr("_val").replace(/\'/g, "\""))),function(){
				obj.flexslider("addSlide", "<li><img src=\""+PATH+"/"+this.path+"\" /></li>");
			});
			//$("#images_html").html(html);
			$("#show-img").modal('toggle');
		});
		
		// 上传图片
		var count = 0;
		$upload.click(function(){
			count = 0;
			images = new Array();
			wx.chooseImage({
			    count: 3,//上传个数
			    sizeType: ['compressed'],
			    sourceType: ['album'],
			    success: function (res) {
			        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			        
			        //上传多个
			        if (localIds.length > 0) {
			        	var i = 0,length = localIds.length;
			        	function uploadImage(){
			        		wx.uploadImage({
							    localId: localIds[i],
							    isShowProgressTips: 1,
							    success: function (res) {
							        var serverId = res.serverId; // 返回图片的服务器端ID
							        $.getJSON(PATH + "/file/upload.jhtml?mediaId="+serverId, function(data){
										if (data.message.type=="success") {
											images.push({"title":"","content":"","path":data.url});
											// 显示
											$pictureDiv.append("<img src=\""+PATH+"/"+data.url+"\" style=\"width: 60px;\">");
											if(localIds.length==++count)
												alert("上传成功");
										} else {
											alert(data.message.content);
										}
									});
									i++;
									if(i < length){
										uploadImage();
									}
							    }
							});
			        	}
						uploadImage();
						$upload.hide();
			        }
			    }
			});
		});
	};
/*  $(window).scroll(function(){
	var scrollTop = $(this).scrollTop();               //滚动条距离顶部的高度
	var scrollHeight = $(document).height();           //当前页面的总高度
	var windowHeight = $(this).height();               //当前可视的页面高度
	if(scrollTop + windowHeight >= scrollHeight){//距离顶部+当前高度 >=文档总高度 即代表滑动到底部
		if(page!=undefined&&page.pageMaxPage>page.page) {
			page.page++;
			page.submit();
		}
	}
});  */
</script>
</body>
</html>
