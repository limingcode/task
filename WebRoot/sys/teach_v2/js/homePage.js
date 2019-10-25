/*
* @Author: Hlin
* @Date:   2018-01-25 10:03:35
* @Last Modified by:   Hlin
* @Last Modified time: 2018-01-25 10:45:03
*/
// 课次选择事件绑定
$(".course_box .left").on('click', 'li.course_item', function(event) {

    $(this).addClass('active').siblings('.course_item').removeClass('active');
    
    var lessonId = $(this).attr('name');

    sessionStorage.setItem('teach_lessonId', lessonId);
    
    // 给左边入口添加链接和状态
    $(this).find('.icon_list .icon_item').each(function(index, el) {
        var state = $(this).attr("state");
        var sourcesrc = $(this).attr("sourcesrc");
        $(".course_box .right li.enter_item").eq(index).attr("state", state).find('a').attr("href",sourcesrc);
    });
});

//$('.course_box .left').on('click', 'i.icon_item', function(){
//	var url = $(this).attr('sourcesrc');
//	if (isNotEmpty(url)) {
//		window.location.href = url;
//	}
//})

$('.filter_item').on('change', 'input', function(){
	getLesson();
	sessionStorage.clear("teach_lessonId");
});

function getLesson() {
	var subject = $('input[name="subject"]:checked').val();//科目
	var grade = $('input[name="grade"]:checked').val();//年级
	var cate = $('input[name="level"]:checked').val();//层次
	var period = $('input[name="term"]:checked').val();//学期
	
	if (isEmpty(subject) || isEmpty(grade) || isEmpty(cate) || isEmpty(period)) {
		$('.right').children().hide();
		return false;
	}
	
	var index = layer.load(0, {shade: false});
	
	var title =  $('input[name="term"]:checked').attr('title')
				+$('input[name="subject"]:checked').attr('title')
	           	+$('input[name="grade"]:checked').attr('title')
				+$('input[name="level"]:checked').attr('title') + '第&&次课';
	
   	$.ajax({
        url: getRootPath() + "/prepareLesson/getLesson.jhtml",
        type: 'POST',
        data: {'subject':subject,'grade':grade,'cate':cate,'period':period},
        dataType: "json",
        success: function (data) {
        	if (data.lessonList != null && data.lessonList.length > 0) {
        		var html = '<ul class="course_list">';
	        	$.each(data.lessonList, function(i, elem){
	        		var status = '';
	        		data.lessonStatus.forEach(function(ele){
	        			if (elem.iD == ele.id) {
	        				status = ele;
						}
	        		});
	        		html += '<li class="course_item" name="'+ elem.iD +'" index="'+ elem.sortNo +'">'+ 
	        				 '    <span class="order">'+ elem.sortNo +'</span>'+ 
			        		 '    <span class="cnt">'+ (isEmpty(elem.brief) ? '<input type="button" class="hol_btn" value="点击填写大纲" />' : elem.brief) +'</span>'+ 
			        		 '    <div class="icon_list">'+ 
			        		 '		<i class="icon_item icon1" sourcesrc="'+ getRootPath() + '/tpCourseware/editPpt.jhtml?lessonId='+ elem.iD +'&title='+ title.replace('&&', elem.sortNo) +'" state="'+ status.course +'"></i>'+ 
			        		 '		<i class="icon_item icon2" sourcesrc="'+ getRootPath() + '/prepareLesson/toUploadFileV2.jhtml?lessonId='+ elem.iD +'&type='+2+'&title='+ title.replace('&&', elem.sortNo) +'" state="'+ status.lessonVideo +'"></i>'+ 
			        		 '		<i class="icon_item icon3" sourcesrc="'+ getRootPath() + '/prepareLesson/toUploadFileV2.jhtml?lessonId='+ elem.iD +'&type='+3+'&title='+ title.replace('&&', elem.sortNo) +'" state="'+ status.memoirVideo +'"></i>'+ 
			        		 '		<i class="icon_item icon4" sourcesrc="'+ getRootPath() + '/feedback/toFeedBack.jhtml?lessonId='+ elem.iD +'&title='+ title.replace('&&', elem.sortNo) +'" state="1"></i>'+ 
			        		 '    </div>'+ 
			        		 '</li>';
	        	});
	        	html += '</ul>';
	        	$('.left').html(html);
	        	$('.right').children().show();
	        	var lessonId = sessionStorage.getItem('teach_lessonId');
	        	if (lessonId) {
	        		$('.left').find('.course_item[name="'+ lessonId +'"]').click();
	        		//滚动到当前选中li
	        		$(".course_list").animate({scrollTop: $(".course_list").scrollTop() + $('.course_item[name="'+ lessonId +'"]').offset().top - $(".course_list").offset().top}, 300); //有动画效果
				} else {
					$('.left').find('.course_item').eq(0).click();
				}
			} else {
				$('.left').html('');
				$('.right').children().hide();
			}
        	layer.close(index);
        }
        
    });
   	
}

//课次大纲修改
$('.course_box').on('click', 'ul.course_list li.course_item .order, .cnt input[type="button"]', function(event) {
	var obj = $(this);
	var lessonId = $(this).parents('.course_item').attr('name');
    // 弹窗填写视频链接
    layer.prompt({
        title: '请填写课次大纲',
        skin: "layer-sky-skin",
        value: $(this).parent().find('.cnt').text(),
    }, function(pass, index){
    	if (isEmpty(pass.trim())) {
			layer.msg('课次大纲不能为全空格');
			return false;
		}
    	
        $.ajax({
            url: getRootPath() + '/tpCourseware/updateLessonName.jhtml',
            type: 'POST',
            dataType: 'json',
            data: {
            	'lessonId': lessonId, 
            	'name': pass
            },
            success: function(data){
            	$(obj).parents('.course_item').find('.cnt').html(pass);
            	layer.msg(data.message);
                layer.close(index); //关闭弹窗
            },
            error: function(error){
                console.error(error)
                layer.close(index); //关闭弹窗
                layer.alert("修改课次大纲异常", {
                    title: "提示", //标题
                    skin: "layer-sky-skin", //皮肤
                })
            }
        })
    });
});

$('.enter_list').on('click', '.enter_item.item1 a', function(event) {
    var liActive = $('.course_box ul.course_list li.active');
    if(liActive.length == 0) {
        event.preventDefault();
        return;
    }
    if(!!liActive.find('.cnt').text()){
        return;
    }else{
        layer.alert("上传课件前请先填写课次大纲", {
            title: "提示", //标题
            skin: "layer-sky-skin", //皮肤
        })
        event.preventDefault();
    }
});


