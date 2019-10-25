/*
* @Author: Hlin
* @Date:   2017-06-26 10:04:15
*/
   $(function () {
       $(document).on('click','.lessonsChoose  span', function () {
    	   $(this).addClass('active').siblings().removeClass('active');
       });
       
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
	   	
	   	$(".bottomIcon span").on('click', function(){
	   		var state = $(this).attr('state');
	   		if(0 == state){
	   			return; 
	   		}
	   		var lessonId = $(".lesson.active").attr("name");
//	   		if (isEmpty(lessonId)) { 
//	   			return false; 
//  			} 
	   		var type = $(this).attr("name");
	   		if ("1" == type) {//新增或修改ppt
	   			window.location.href = getRootPath() + "/tpCourseware/editPpt.jhtml?lessonId="+lessonId;
	   		} else if ("2" == type || "3" == type) {//上传说课、实录视频
	   			window.location.href = getRootPath() + "/prepareLesson/toUploadFile.jhtml?lessonId="+lessonId+"&type="+type+"";
			} else {
				window.location.href = getRootPath() + "/feedback/toFeedBack.jhtml?lessonId="+lessonId;
			}
	   	});
  	});
  	
  	function getLesson() {
  		var subject = $(".subject.active").attr("name");//科目
  		var grade = $(".grades.active").attr("name");//年级
  		var cate = $(".cate.active").attr("name");//层次
  		var period = $(".period.active").attr("name");//学期
  		
	   	$.ajax({
	        url: getRootPath() + "/prepareLesson/getLesson.jhtml",
	        type: 'POST',
	        data: {'subject':subject,'grade':grade,'cate':cate,'period':period},
	        dataType: "json",
	        success: function (data) {
	        	var lessonList = data.lessonList;
	        	var lessonStatus = data.lessonStatus;
	        	var htmlString = "";
	        	lessonList.forEach(function(lesson){  
	        		htmlString += "<span class=\"lesson\" name=\""+ lesson.iD +"\" sortNo=\""+ lesson.sortNo +"\">"+ lesson.sortNo +"</span>";
	    		});
	        	$(".classTime").children("div").html(htmlString);
	        	var lesson = localStorage.getItem("lesson");
	        	var lessonSpan = $(".classTime").find('span[name="'+ lesson +'"]');
	        	if(isNotEmpty(lesson) && lesson != 'undefined' && lessonSpan.length != 0){
	        		$(".classTime").find('span[name="'+ lesson +'"]').addClass('active');
	        		memory();
	        	}else {
	        		$(".lesson:eq(0)").addClass('active');
				}
	        	$(".bottomCont .bottom").addClass('bounceIn animated');
                $(".bottomCont .bottom").removeClass('no_select');
	        	
	        	$(".bottomTitle").children(".num").html($(".lesson.active").attr("sortNo"));
	        	
	        	var lessonId = $('.lesson.active').attr('name');
	        	if(isNotEmpty(lessonId)){
	        		getFileSynchStatus(lessonId);
	        	}
	        	
	        	$(".lesson").on('click', function () {
	        		$(".bottomCont .bottom").addClass('bounceIn animated');
                    $(".bottomCont .bottom").removeClass('no_select');
	        		
	        		var lessonId = $(this).attr('name');
	        		getFileSynchStatus(lessonId);
	    	   		$(this).addClass('active').siblings().removeClass('active');
	    	   		$(".bottomTitle").children(".num").html($(".lesson.active").attr("sortNo"));
	    	   		var bottomTitleW = ($('.bottomTitle').width()+60)/2;
	    	   		$('.bottom .bottomTitle').css('marginLeft','-'+bottomTitleW+'px');
	    	   		var lesson = $(this).attr('name');
	    	   		localStorage.setItem('lesson', lesson);
	    	   	});
	            
	       		if(data.length > 0){
		        	getCourseTitle();
	        	} else {
	        		$(".bottomCont .bottom").removeClass('bounceIn animated');
                    $(".bottomCont .bottom").addClass('no_select');
				}
	       		var bottomTitleW = ($('.bottomTitle').width()+60)/2;
	       		$('.bottom .bottomTitle').css('marginLeft','-'+bottomTitleW+'px');
	        }
	    });
	}
  	
  	function getCourseTitle(){
  		var title = "";
  		var period = $(".period.active").html();//学期
  		title += period.substring(0, period.length-1);
  		var subject = $(".subject.active").attr("name");//科目
  		if ("A04" == subject) {  //语文
			title += "CHI";
		} else if ("A02" == subject) { //数学
			title += "M";
	   	} else if ("A01" == subject) { //英语
	   		title += "E";
	   	} else if ("A05" == subject) { //物理
	   		title += "P";
	   	} else { //化学
	   		title += "CHE";
	   	}
  		var grade = $(".grades.active").attr("name");//年级
  		title += grade.substring(1,grade.length).trim();
  		var cate = $(".cate.active").html();//层次
  		title += cate;
  		$(".bottomTitle").children("span").html(title);
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
  		var lesson = $(".lesson.active").attr("name");
	   	localStorage.setItem('subject', subject);
	   	localStorage.setItem('grade', grade);
	   	localStorage.setItem('cate', cate);
	   	localStorage.setItem('period', period);
	   	localStorage.setItem('lesson', lesson);
   }

