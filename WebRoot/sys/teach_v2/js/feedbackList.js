/*
* @Author: Hlin
* @Date:   2018-01-26 10:56:25
* @Last Modified by:   Hlin
* @Last Modified time: 2018-01-26 10:58:24
*/
// 作业发布日期选择
$.jeDate('#start_time',{
    format:"YYYY-MM-DD",
    minDate:$.nowDate({DD:0})
});

$('.filter_item').on('change', 'input',  function(){
	getPage(1);
});

function getPage(currPage){
	var lessonId = $('#lessonId').val();
	var createTime = $('#start_time').val();
	var subject = $('input[name="subject"]:checked').val();
	var period = $('input[name="term"]:checked').val();
	var grade = $('input[name="grade"]:checked').val();//年级
	var cate = $('input[name="level"]:checked').val();//层次
	var classTime = $('input[name="lesson"]:checked').val();
	var state = $('input[name="feedbackstate"]:checked').val();
	var name = $('#name').val();//层次
	
	$.ajax({
	     type: "POST",
	     url:  getRootPath() + "/feedback/getFeedbackList.jhtml",
	     cache: false,
	     data: {
	    	 'currPage': currPage,
	    	 'pageSize': 10,
	    	 'lessonId': lessonId,
	    	 'beginTime': createTime,
	    	 'lessons': subject,
	    	 'semester': period,
	    	 'grade': grade,
	    	 'arrangement': cate,
	    	 'state': state,
	    	 'name': name,
	    	 'classTime': classTime
	     },
	     dataType: "json",
	     success: function(data){
	    	 if(data.status){
		    	 write(data);
		    	 $('.pagination').children('ul').html(toolBarCtrl(data));
	    	 }
	     },
	     error: function (XMLHttpRequest, textStatus, errorThrown) {
	     	 layer.msg('请求出错，请稍后重试!');  		
	     }
  	});
}

function write(data){
	var html = "";
	$.each(data.dataList, function(i, elem){
		html += '<tr>'+ 
				 '    <td>'+elem.name+'</td>'+ 
				 '    <td>'+elem.perName+'</td>'+ 
				 '    <td>'+elem.subject+'</td>'+ 
				 '    <td>'+elem.graName+'</td>'+ 
				 '    <td>'+elem.catName+'</td>'+ 
				 '    <td>第'+elem.sortNo+'次</td>'+ 
				 '    <td>'+elem.createDate+'</td>'+ 
				 '    <td>'+ (elem.comment == '1' ? '赞' : '不赞') +'</td>'+ 
				 '    <td title="'+ trimBlock(elem.content) +'">'+ trimBlock(elem.content) +'</td>'+ 
				 '</tr>';
	});
	
	$('.wa_table').find('tbody').html(html);
}


$('.select').on('change', 'input',  function(){
	getLesson();
});

function getLesson(){
	var subject = $('input[name="subject"]:checked').val();  //科目
	var period = $('input[name="term"]:checked').val();  //学期
	var grade = $('input[name="grade"]:checked').val();  //年级
	var cate = $('input[name="level"]:checked').val(); //层次
	if(isEmpty(subject) || isEmpty(period) || isEmpty(grade) || isEmpty(cate)){
		$('.lessonDiv').addClass('force_hide');
		return false;
	}
	$.ajax({
        url: getRootPath() + "/prepareLesson/getLesson.jhtml",
        type: 'POST',
        data: {'subject':subject,'grade':grade,'cate':cate,'period':period},
        dataType: "json",
        success: function (data) {
        	if (data.lessonList == null || data.lessonList.length == 0) {
        		$('.lessonDiv').addClass('force_hide');
        		return false;
			}
        	
        	var html = "";
        	$.each(data.lessonList, function(i, elem){
        		html += '<li><label class="sky_radio"><input type="radio" name="lesson" value="'+ elem.iD +'"/><span class="mark"></span>'+ elem.sortNo +'课次</label></li>'
        	});
        	
        	$('.lessonDiv').removeClass('force_hide').children('ul').html(html);
        }
    });
}
