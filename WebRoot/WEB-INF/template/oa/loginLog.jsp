<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<head>
  <meta charset="utf-8">
  <title>登陆日志</title>
  <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/reset.pchlin.css">
  <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/public.css">
  <link rel="stylesheet" type="text/css" href="<%=path %>/sys/js/jedate/skin/jedate.css">
  <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/swiper.min.css">
  <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/appAdmid.css">
</head>
<style>
  .nodeCont{
    width: 60%;
  }
  .aclist_l{
    width: 100%;
  }
  .nodeCont ul{
    width: 100%;
    height: 100%;
    position: relative;
  }
  .nodeCont ul li{
    position: absolute;
    background: url(<%=path %>/sys/images/nodeCont.png) no-repeat;
    width: 20px;
    height: 20px;
    background-size: 100% 100%;
    top: 20px;
    cursor: pointer;
  }
  .nodeCont ul li i{
    background: url(<%=path %>/sys/images/talk.png) no-repeat;
    width: 50px;
    height: 27px;
    background-size: 100% 100%;
    position: absolute;
    font-style: normal;
    left: 0;
    top: -21px;
    line-height: 19px;
  }
  .z1{
    z-index: 1;
  }
  select {
  width:15%;
    padding: 6px;
        border-color: #c7bcf9;
  }
  .layui-laydate .layui-this {
    background-color: #b7a4da!important;
    color: #fff!important;
  }
</style>
<body>
<%
	Date d = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	String now = df.format(d);
%>
<!-- begin 内容 -->
<div class="acti_list clear">
  <!-- 内容-左 -->
  <div class="wa_box aclist_l">
  	<form id="searchform">
  		<div class="search_box">
  		 <input type="hidden" id="pageNo1" name="pageNo">
	    <input type="hidden" id="totalPage">
	      <input type="text" placeholder="请输入关键词" name="teacherName">
	      <select name="subjectId">
	      	<option value="">请选择学科</option>
	      	<c:forEach items="${subjects }" var="sub">
	      		<option value="${sub.id }">${sub.name }</option>
	      	</c:forEach>
	      </select>
	      <select name="gradeId">
	      	<option value="">请选择学年级</option>
	      	<c:forEach  items="${grades }" var="gra">
	      		<option value="${gra.id }">${gra.name }</option>
	      	</c:forEach>
	      </select>
	      <input type="text" id="test1" placeholder="请选择日期" name="loginDate" value="<%=now %>">
	      <button class="sol_btn" type="button" onclick="search(1)">查询</button>
	      <!-- <a href="activity.html" class="sol_btn">新增</a> -->
	    </div>
  	</form>
    <table class="wa_table">
      <thead>
      <tr>
        <th>老师</th>
        <th>科目</th>
        <th>资料年级</th>
        <th>日期</th>
        <th>登陆节点</th>
        <th>登陆频次</th>
      </tr>
      </thead>
      <tbody class="tableCont">

      </tbody>
    </table>
    <!-- begin 分页条 -->
	<div class="pagination">
	</div>
    <!-- 分页条 end -->
  </div>
  <!-- 内容-左 end -->
</div>
<!-- end 内容 -->

<script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path %>/sys/js/jedate/jquery.jedate.min.js"></script>
<script src="<%=path %>/sys/js/layer.js"></script>
<script src="<%=path %>/sys/js/swiper.min.js"></script>
<script src="<%=path %>/sys/js/swiper-user.js"></script>
<script src="<%=path %>/sys/js/laydate.js"></script>
<script type="text/javascript" src="<%=path %>/sys/js/appAdmid.js"></script>
<script>
  $(function() {
  	//日期控件初始化   需引入laydata.js
    laydate.render({
      elem: '#test1',
      value: new Date()
    });
    $(document).on('click','.chooseOne',function() {
        $(this).find('i').addClass('z1').parent('.chooseOne').siblings('.chooseOne').find('i').removeClass('z1');
    })
    search();
  })
  function goPage(pageNo){
	var totalPage = $("#totalPage").val();
		if (pageNo>totalPage) {
		pageNo = totalPage;
	}
	search(pageNo);
  }
  function forword(){
		var pageNo = $("#pageNo").val();
		goPage(pageNo);
	}
  function search(pageNo){
	  $("#pageNo1").val(pageNo);
        $.ajax({
          url:'<%=path %>/loginLog/list.jhtml',
          type:'get',
          async:true,
          dataType:'json',
          data:$("#searchform").serializeArray(),
          beforeSend:function(){
        	index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            //console.log('发送前')
          },
          success:function(data){
            var html = '';
            $.each(data.data,function(i, elem) {
              var liCont = '';
              $.each(elem.loginTime,function(j,item) {
                var b = a(item.time);
                var newTime = (((b-7.5)/13.5)*100).toFixed(2);
                console.log(b);
                liCont += '<li time="'+item.time+'" leftNum="'+newTime+'" style="left: '+newTime+'%;"><i>'+item.time+'</i></li>';
              });
              var ulCont = '<ul>'+liCont+'</ul>';
              html += '<tr>'+
                  '  <td>'+elem.teacherName+'</td>'+
                  '  <td>'+(elem.subjectName==null?'':elem.subjectName)+'</td>'+
                  '  <td>'+(elem.gradeName==null?'':elem.gradeName)+'</td>'+
                  '  <td>'+(elem.loginDate==null?'':elem.loginDate)+'</td>'+
                  '  <td class="nodeCont">'+ulCont+'</td>'+
                  '  <td>'+elem.loginNum+'</td>'+
                  '</tr>'
            });
            $('.tableCont').html(html.replace(/null/g,null));
            $.each($('.nodeCont li'),function() {
                if(Number($(this).next().attr('leftNum'))-Number($(this).attr('leftNum'))<=7){
                  $(this).next().addClass('chooseOne');
                  $(this).addClass('chooseOne');
                }
            });
            var html1="<ul><li class='first_page' onclick='goPage(1)'>&nbsp;</li>"
    			if(data.condition.pageNo>1){
    				html1+="<li class='prev_page' onclick='goPage("+(data.condition.pageNo-1)+")'>&nbsp;</li>";
    			}
    			if(data.condition.pageNo-4>0){
    				html1+="<li onclick='goPage("+(data.condition.pageNo-4)+")'>"+(data.condition.pageNo-4)+"</li>";
    			}
    			
    			if(data.condition.pageNo-3>0){
    				html1+="<li onclick='goPage("+(data.condition.pageNo-3)+")'>"+(data.condition.pageNo-3)+"</li>";
    			}
    			if(data.condition.pageNo-2>0){
    				html1+="<li onclick='goPage("+(data.condition.pageNo-2)+")'>"+(data.condition.pageNo-2)+"</li>";
    			}
    			if(data.condition.pageNo-1>0){
    				html1+="<li onclick='goPage("+(data.condition.pageNo-1)+")'>"+(data.condition.pageNo-1)+"</li>";
    			}
    			html1+="<li class='current_page'>"+data.condition.pageNo+"</li>";
    			if(data.condition.pageNo< data.condition.totalPage){
    				html1+="<li onclick='goPage("+(data.condition.pageNo+1)+")'>"+(data.condition.pageNo+1)+"</li>";
    			}
    			
    			if(data.condition.pageNo+1< data.condition.totalPage){
    				html1+="<li onclick='goPage("+(data.condition.pageNo+2)+")'>"+(data.condition.pageNo+2)+"</li>";
    			}
    			if(data.condition.pageNo+2< data.condition.totalPage){
    				html1+="<li onclick='goPage("+(data.condition.pageNo+3)+")'>"+(data.condition.pageNo+3)+"</li>";
    			}
    			if(data.condition.pageNo+4< data.condition.totalPage){
    				html1+="<li onclick='goPage("+(data.condition.pageNo+4)+")'>"+(data.condition.pageNo+4)+"</li>";
    			}
    			if((data.condition.totalPage-data.condition.pageNo)>5){
    				html1+="<li class='ellipsis'>....</li>";
    			}
    			if((data.condition.totalPage-data.condition.pageNo)>4){
    				html1+="<li onclick='goPage("+data.condition.totalPage+")'>"+(data.condition.totalPage)+"</li>";
    			}
    			if(data.condition.pageNo<data.condition.totalPage){
    				html1+="<li class='next_page' onclick='goPage("+data.condition.pageNo+1+")'>&nbsp;</li>";
    			}
    			html1+="<li class='last_page' onclick='goPage("+data.condition.totalPage+")'>&nbsp;</li>";
    			html1+="<li class='all_page'>共"+data.condition.totalPage+"页</li>";
    			html1+="<li class='turn_page'>到<input type='number' id='pageNo' />页</li>";
    			html1+="<li class='go_btn' onclick='forword()'>GO</li></ul>";
    			$("#totalPage").val(data.condition.totalPage);
    			$(".pagination").html(html1);
          },
          error:function(error){
          },
          complete:function(){
            layer.close(index);
          }
        });
    }
  function a(string) {
      var f = string.split(':', 2);
      return Number(f[0])+f[1]/60;
    }
</script>
</body>
</html>