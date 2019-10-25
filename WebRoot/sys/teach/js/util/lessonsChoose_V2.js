/*
* @Author: Hlin
* @Date:   2017-06-26 10:04:15
*/
   $(function () {
//        $(document).on('click','.lessonsChoose  span', function () {
//    	   $(this).addClass('active').siblings().removeClass('active');
//        });
       
	    var subject = localStorage.getItem('subject');
 		var grade = localStorage.getItem('grade');
 		var cate = localStorage.getItem('cate');
 		var period = localStorage.getItem('period');
 		
 		if (isNotEmpty(subject)){
 			$(".classTitle").find('span[name="'+ subject +'"]').addClass('active')
 		} else {
 			$(".subject:eq(0)").addClass('active');
 		}
 		
 		if (isNotEmpty(grade)){
 			$(".grade").find('span[name="'+ grade +'"]').addClass('active')
 		} else {
 			$(".grades:eq(0)").addClass('active');
 		}
 		
 		if (isNotEmpty(cate)){
 			$(".levels").find('span[name="'+ cate +'"]').addClass('active')
 		} else {
 			$(".cate:eq(0)").addClass('active');
 		}
 		
 		if (isNotEmpty(period)){
 			$(".term").find('span[name="'+ period +'"]').addClass('active')
 		} else {
 			$(".period:eq(0)").addClass('active');
 		}
	   
	   	getLesson(); //得到课次
	   	
	   	$(".cate,.period,.grades,.subject").on('click', function () {
	   		$(this).addClass('active').siblings().removeClass('active');
	   		getLesson(); //得到课次
	   	});
	   	
	   	
	   	$(".bottomIcon img").on('click', function(){
	   		var state = $(this).attr('state');
	   		if(0 == state){
	   			return; 
	   		}
	   		var lessonId = $(".bottomTitle").find('.active').attr("name");
	   		var type = $(this).parent().attr("name");
	   		goEdit(lessonId, type);
	   	});
	   	
  	});
   
    function goEdit(lessonId, type){
    	addLoading("加载中...");
    	if ("1" == type) {//新增或修改ppt
   			window.location.href = getRootPath() + "/tpCourseware/editPpt.jhtml?lessonId="+lessonId;
   		} else if ("2" == type || "3" == type) {//上传说课、实录视频
   			window.location.href = getRootPath() + "/prepareLesson/toUploadFile.jhtml?lessonId="+lessonId+"&type="+type+"";
		} else {
			window.location.href = getRootPath() + "/feedback/toFeedBack.jhtml?lessonId="+lessonId;
		}
    }
  	
  	function getLesson() {
  		var subject = $(".subject.active").attr("name");//科目
  		var grade = $(".grades.active").attr("name");//年级
  		var cate = $(".cate.active").attr("name");//层次
  		var period = $(".period.active").attr("name");//学期
  		
  		var index = layer.load(0, {shade: false});
  		
	   	$.ajax({
	        url: getRootPath() + "/prepareLesson/getLesson.jhtml",
	        type: 'POST',
	        data: {'subject':subject,'grade':grade,'cate':cate,'period':period},
	        dataType: "json",
	        success: function (data) {
	        	layer.close(index);
	        	if(isEmpty(data.lessonList)){
	        		bgChange(false);
	        		return false;
	        	}else{
	        		bgChange(true);
	        	}
	        	var lessonList = data.lessonList;
	        	var lessonStatus = data.lessonStatus;
	        	var htmlString = "";
	        	lessonList.forEach(function(lesson){  
	        		htmlString += "<div class='iParents'><span name=\""+ lesson.iD +"\"><i class='numBall'>"+lesson.sortNo+"</i><p>	"+ trimBlock(lesson.brief) +"</p></span><i class=\"iconCont\">";
	        		lessonStatus.forEach(function(status){
	        			if(lesson.iD == status.id){
	        				if(status.course == '1'){
	        					htmlString += '<img type="1" name="course_1" src="../sys/teach/images/lin/course_1.png" alt="">';
	        				}else{
	        					htmlString += '<img type="1" name="course_0" src="../sys/teach/images/lin/course_0.png" alt="">';
	        				}
	        				
	        				if(status.lessonVideo == '1'){
	        					htmlString += '<img type="2" name="lesson_1" src="../sys/teach/images/lin/lesson_1.png" alt="">';
	        				}else{
	        					htmlString += '<img type="2" name="lesson_0" src="../sys/teach/images/lin/lesson_0.png" alt="">';
	        				}
	        				
	        				if(status.memoirVideo == '1'){
	        					htmlString += '<img type="3" name="memoir_1" src="../sys/teach/images/lin/memoir_1.png" alt="">';
	        				}else{
	        					htmlString += '<img type="3" name="memoir_0" src="../sys/teach/images/lin/memoir_0.png" alt="">';
	        				}
	        			}
	        		});
	        		
	        		htmlString += "</i></div>"
	    		});
	        	$('.bottomTitle').html(htmlString);
	        	
	        	var lesson = localStorage.getItem("lesson");
	        	var lessonSpan = $(".bottomTitle").children('span[name="'+ lesson +'"]');
	        	if(lessonSpan.length != 0){
	        		clickLesson(lesson, true);
	        		memory();
	        	} else {
	        		var id = $('.bottomTitle').find('span').eq(0).attr('name');
		        	clickLesson(id);
				}
	        	
	        	$('.bottomTitle span').on('click', function(){
	        		var id = $(this).attr('name');
	        		clickLesson(id);
	    	   	});
	        	
//	        	$('.bottomTitle span').on("mouseenter", function(){
//	        		var id = $(this).attr('name');
//	        		clickLesson(id);
//	        	})
	        	
//	        	$('.bottomTitle span').on("mouseleave", function(){
//	        		var id = $('.bottomTitle').children('span.active').attr('name');
//	        		clickLesson(id);
//	        	});
	        	
	        	$(".iconCont img").on('click', function(){
	    	   		var lessonId = $(this).parent().siblings('span').attr('name');
	    	   		var type = $(this).attr('type');
	    	   		goEdit(lessonId, type);
	    	   	});
	        }
	    });
	}
  	
  	function clickLesson(lessonId) {
  		localStorage.setItem('lesson', lessonId);
  		var lessonSpan = $('.bottomTitle').find('span[name="'+lessonId+'"]');
		lessonSpan.addClass('active').parent('div').siblings().find('span').removeClass('active');
		lessonSpan.find('i').addClass('active').parents('.iParents').siblings().find('i').removeClass('active');
  		if(lessonSpan.siblings('i').find('img[name="course_1"]').length == 0){
  			$('.bottomIcon').find('img').eq(0).attr('src','../sys/teach/images/lin/anniu_01.png');
  		}else{
  			$('.bottomIcon').find('img').eq(0).attr('src','../sys/teach/images/lin/indexNewIcon_4.png');
  		}
  		if(lessonSpan.siblings('i').find('img[name="lesson_1"]').length == 0){
  			$('.bottomIcon').find('img').eq(1).attr('src','../sys/teach/images/lin/anniu_02.png');
  		}else{
  			$('.bottomIcon').find('img').eq(1).attr('src','../sys/teach/images/lin/indexNewIcon_5.png');
  		}
  		if(lessonSpan.siblings('i').find('img[name="memoir_1"]').length == 0){
  			$('.bottomIcon').find('img').eq(2).attr('src','../sys/teach/images/lin/anniu_03.png');
  		}else{
  			$('.bottomIcon').find('img').eq(2).attr('src','../sys/teach/images/lin/indexNewIcon_6.png');
  		}
	}
  	
 	function bgChange(b){
  		if(b){
  			$(".bottomCont .bottom").addClass('bounceIn animated').css({
                background: 'url(../sys/teach/images/lin/bottomBg_2all.png)',
                backgroundSize: '1024px 100%',
                position: 'relative'
            });
            $('.bottomTitle').css('display','block');
            $('.bottomIcon').css('display','block');
            $('.peopleOne').css('display','block');
            $('.peopleTow').css('display','block');
  		}else{
  			$(".bottomCont .bottom").removeClass('bounceIn animated').css({
                background: 'url(../sys/teach/images/lin/bottomBg_2.png)',
                backgroundSize: '1024px 100%',
                position: 'relative'
            });
            $('.bottomTitle').css('display','none');
            $('.bottomIcon').css('display','none');
            $('.peopleOne').css('display','none');
            $('.peopleTow').css('display','none');
  		}
  	}
  	
  	var isSynch = false;
  	
  	function getFileSynchStatus(lessonId){
  		$.ajax({
	        url: getRootPath() + "/prepareLesson/getFileSynchStatus.jhtml",
	        type: 'POST',
	        data: {'lessonId':lessonId},
	        dataType: "json",
	        success: function (data) {
	        	var stats_1 = true;
	        	var stats_5 = true;
	        	var stats_6 = true;
	        	if(data.length != 0){
	        		$.each(data, function(i, elem){
	        			isSynch = true;
	        			if('1' == elem.type){
	        				$('.bottomIcon').find('span').eq(0).attr('state','0');
	        				stats_1 = false;
	        				if(stats_5){
	        					$('.bottomIcon').find('span').eq(1).removeAttr('state');
	        				}
	        				if(stats_6){
	        					$('.bottomIcon').find('span').eq(2).removeAttr('state');
	        				}
	        			}else if('5' == elem.type){
	        				$('.bottomIcon').find('span').eq(1).attr('state','0');
	        				stats_5 = false;
	        				if(stats_1){
	        					$('.bottomIcon').find('span').eq(0).removeAttr('state');
	        				}
	        				if(stats_6){
	        					$('.bottomIcon').find('span').eq(2).removeAttr('state');
	        				}
	        			}else if('6' == elem.type){
	        				$('.bottomIcon').find('span').eq(2).attr('state','0');
	        				stats_6 = false;
	        				if(stats_1){
	        					$('.bottomIcon').find('span').eq(0).removeAttr('state');
	        				}
	        				if(stats_5){
	        					$('.bottomIcon').find('span').eq(1).removeAttr('state');
	        				}
	        			}
	        		});
	        	}else{
	        		$('.bottomIcon').find('span').removeAttr('state');
	        		isSynch = false;
	        	}
	        }
	    });
  	}
  	
  	var int = setInterval(timeWork,10000);
  	function timeWork(){
  		if(isSynch){
  			var lessonId = $('.lesson.active').attr('name');
  			getFileSynchStatus(lessonId);
  		}else{
  			clearInterval(int);
  		}
  	}
	
  	
   window.onbeforeunload = memory;
   window.onunload = memory;
   
   function memory(){
   		var subject = $(".subject.active").attr("name");//科目
  		var grade = $(".grades.active").attr("name");//年级
  		var cate = $(".cate.active").attr("name");//层次
  		var period = $(".period.active").attr("name");//学期v
  		var lesson = $(".bottomTitle").children(".active").attr("name");
	   	localStorage.setItem('subject', subject);
	   	localStorage.setItem('grade', grade);
	   	localStorage.setItem('cate', cate);
	   	localStorage.setItem('period', period);
	   	localStorage.setItem('lesson', lesson);
   }

