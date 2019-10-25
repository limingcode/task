var currTagNumValue;

function checkNum(obj){
	if($(obj).val() == '0' || isEmpty($(obj).val().replace(/\D/g, ''))){
		$(obj).val('');
		return false;
	}
	$(obj).val(parseInt($(obj).val().replace(/\D/g, '')));
}

function remeberValue(obj){
	$.each($('.editor_box:visible').find('.number'), function() {
		$(this).attr('value', $(this).val());
	});
	currTagNumValue = $(obj).val();
}

function changeCount(obj){
	var index = $(obj).val();
//	if(index>=99){
//		layer.msg('序号值不能大于100！');
//		$(obj).val(currTagNumValue);
//		return false;
//	}
	if(isEmpty(index)){
		layer.msg('序号值不能为空！');
		$(obj).val(currTagNumValue);
		return false;
	}
	if(index != currTagNumValue){
		while (index <= bigNum) {
			if($(obj).parents('.drsElement').siblings('.drsElement').find('.number[value="'+ index +'"]').length > 0){
				$('.number[value="'+ index +'"]').val(++index);
			} else {
				break;
			}
		}
	}
}


$(function(){
	$('.page_box').eq(0).children('.page').addClass('border_org');
	$(".editor_box").eq(0).show().addClass('show').siblings('.editor_box').hide().removeClass('show');
});

$(".submit").click(function(){
	if($(".page_box").length == 0){
		layer.msg('未上传图片，不能提交！');
		return false;
	}

	$('.drsElement').each(function(i, elem){
		if(isEmpty($(this).find('.number').val())){
			layer.msg('存在未设置序号值的标注点，不能提交！');
			return false;
		}

	});

	var pageNum;
	var number;
	var b = false;
	$('.drsElement').each(function(i, elem){
		if($(this).width() == 0 || $(this).height() == 0){ //宽或高为0时，忽略该标注点
			return true;
		}

		if(isEmpty($(this).find('.yinPing').attr('audioname'))){
			var pageIndex = $(this).parents('.editor_box').attr('pageindex');
			pageNum = $('.page[pageindex="'+ pageIndex +'"]').attr('pageNum');
			number = $(this).find('.number').val();
			b = true;

			//当前页点击
			$('.page[pageindex="'+ pageIndex +'"]').click();
			//左侧目录栏滚动到当前页
			//31为<span class="addImg">上传图片</span>的高度、 10为<li class="page_box" name="1" pageindex="1">的margin
			$(".left").animate({ scrollTop: $(".left").scrollTop() + $('.page[pageindex="'+ pageIndex +'"]').offset().top - $(".left").offset().top - 31 - 10 }, 500); //有动画效果
			return false;
		}
	});

	if(b){
		layer.msg('第'+ pageNum +'页，序号为'+ number +'的标注点未上传音频，不能提交！');
		return false;
	}

	save();

});

function save(){
	addLoading("提交中...");
	var bookId = $("#bookId").val();
	var period = $("#period").val();
	var bookName = $("#bookName").val();
	var lessonList = new Array();
	var pageList = new Array();
	var lessonId;
	var lessonTitle;
	var lessonDesc;
	var lessonPop;
	var pageIndex = 1;
	$(".page_box").each(function(i, elem){
		var currPageLessonId = $(this).find('option[value="'+$(this).find('select').val()+'"]').attr('lessonId');
		var currPageLessonTitle = $(this).find('option[value="'+$(this).find('select').val()+'"]').attr('lessonTitle');
		var currPageLessonDesc = $(this).find('option[value="'+$(this).find('select').val()+'"]').attr('lessonDesc');
		var currPageLessonPop = $(this).find('option[value="'+$(this).find('select').val()+'"]').attr('lessonPop');
		if (isEmpty(lessonId)) {
			lessonId = currPageLessonId;
			lessonTitle = currPageLessonTitle;
			lessonDesc = currPageLessonDesc;
			lessonPop = currPageLessonPop;
		}
		if(lessonId != currPageLessonId){
			var lesson = Lesson(parseInt(lessonId), lessonTitle, isEmpty(lessonDesc) ? null : lessonDesc, isEmpty(lessonPop) ? null: lessonPop, pageList);
			lessonList.push(lesson);
			lessonId = currPageLessonId;
			lessonTitle = currPageLessonTitle;
			lessonDesc = currPageLessonDesc;
			lessonPop = currPageLessonPop;
			pageList = new Array();
			pageIndex = 1;
		}
		var rectList = new Array();
		$('.editor_box[pageIndex="'+ $(this).children('.page').attr("pageIndex") +'"]').find(".drsElement").each(function(i, elem) {
			if($(this).width() == 0 || $(this).height() == 0){ //宽或高为0时，忽略该标注点
				return true;
			}

			if ($(this).attr("type") == 'circle'){
                debugger
				var longw = parseFloat($(this).css('width'))/2;
				var smallw = parseFloat($(this).css('height'))/2;
				var x = parseFloat($(this).css('left')) + longw;
				var y = parseFloat($(this).css('top')) + smallw;
				var circlePoint = new Point(x, y);
				var circle = new Circle(circlePoint, longw, smallw);
				var audioName = trimBlock($(this).find(".yinPing").attr("audioname"));
                var describe = trimBlock($(this).find(".ds_n").attr("text"));
                var duration = trimBlock($(this).find(".ds_n").attr("time"));
				// var describe = trimBlock($(this).find(".zhuShi").attr("text")).replace(/\n/g,"<BR>");
				// var duration = trimBlock($(this).find(".zhuShi").attr("text")).replace(/\n/g,"<BR>");
				var sortNo = Number($(this).find('.number').val());
				var pointTag = new PointTag(1, sortNo, circle, audioName, describe,duration);
				rectList.push(pointTag);
			} else {
			    debugger
				var x1 = parseFloat($(this).css('left'));
				var x2 = x1+parseInt($(this).css('width'));
				var y1 = parseFloat($(this).css('top'));
				var y2 = y1+parseFloat($(this).css('height'));
				var points = new Array();
				points.push(new Point(x1, y1));
				points.push(new Point(x2, y1));
				points.push(new Point(x1, y2));
				points.push(new Point(x2, y2));
				var polygon = Polygon(points);
				var audioName = trimBlock($(this).find(".yinPing").attr("audioname"));
				// var describe = trimBlock($(this).find(".zhuShi").attr("text")).replace(/\n/g,"<BR>");
				var describe = trimBlock($(this).find(".ds_n").attr("text"));
				var duration = trimBlock($(this).find(".ds_n").attr("time"));
				var sortNo = Number($(this).find('.number').val());
				var pointTag = new PointTag(2, sortNo, polygon, audioName, describe,duration);
				rectList.push(pointTag);
			}
		});
		rectList.sort(function(a, b){
			return a.sortNo - b.sortNo;
		});
		var imageName = $(this).find('img').attr('name');
		var imagePage = ImagePage(pageIndex++, imageName, rectList);
		pageList.push(imagePage);
	});
	var img = $('.editor_box:first').find('img');
	var width = img[0].naturalWidth;
	var height = img[0].naturalHeight;
	var lesson = Lesson(parseInt(lessonId), lessonTitle, isEmpty(lessonDesc) ? null : lessonDesc, isEmpty(lessonPop) ? null: lessonPop, pageList);
	lessonList.push(lesson);
	//var type = (width != 835 && width !=1670) ? 3 : width > height ? 1 : 2;
    var type=1;
    debugger;
    // 横：1670*1080比例 1
    // 竖：835*1080比例 2
    //迷你 655*820   3
    if(width>830&&width<840){
        type=2;
    }else if (width<660) {
        type=3;
    }
	var book = Book(parseInt(bookId), type, width, height, lessonList);
	console.log(JSON.stringify(book));
	saveImageTag(bookId, period, bookName, JSON.stringify(book));
//	removeLoading();
}

function saveImageTag(bookId, period, bookName, jsonString){
	$.ajax({
		type : "post",
		url : getRootPath() + "/imageTag/saveImageTag.jhtml",
		data : {"bookId": bookId, "period":period, "bookName":bookName, "jsonString": jsonString},
		dataType : "json",
		success : function(data) {
			removeLoading();
			layer.msg("提交成功！");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			removeLoading();
			layer.msg("提交失败，请重试！");
		}
	});
}



function aa(e) {
	var _this = $('#'+e);
    var textA = isEmpty(_this.attr('text'))?'':_this.attr('text');
    var timeA = isEmpty(_this.attr('time'))?'':_this.attr('time');

    var audioName = _this.parent().parent().attr('audioname');
    var status=isEmpty(audioName)?'未上传':'已上传';
    // if(isEmpty(textA)){
    //     layer.msg("请输入描述!")
    //     return false
    // }
    // if(isEmpty(timeA)){
    //     layer.msg("请输入时长!")
    //     return false
    // }
    // if(isEmpty(audioName)){
    //     layer.msg("请上传音频!")
    // }
  console.log(textA,timeA)
    var html1= '<ul>' +
        '<li><label><span>音频状态</span><input type="text" class="audio-input-status"  disabled="disabled" value="'+status+'"></label></li>' +
        '<li><label for="uploadFile1"><input id="uploadFile1"  type="file" class="ds_n" onchange="audioUpload(this)" accept="audio/mpeg"/><span>选择上传文件</span><i>只能上传MP3格式文件</i></label></li>' +
        '<li><label><span>评测文字</span><textarea name="" id="text" cols="30" rows="10"></textarea></label></li>' +
        '<li><label><span>评测时长</span><input type="text" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,\'\');}).call(this)" onblur="this.v();" class="time"><span>秒</span></label></li></ul>';
    layer.confirm(html1,{
        title:'资料编辑',
        skin:'pop',
        closeBtn:0,
        btn:['保存','取消']
    },function (e) {

        var text = $('#text').val();
        var time = $('.time').val();
        var audioStatus = $('.audio-input-status').val()
        if(audioStatus!=='已上传'){
            alert("请上传音频!");
            return false;
        }
        if(!isEmpty(text)||!isEmpty(time)){
            if(isEmpty(text)||isEmpty(time)){
                alert("请时长与描述必须同时填写!")
                return false;
            }else {
                _this.find('.audioLabel').find('.textImg-span').attr('style','display:inline-block'    );
            }
        }else{
            _this.find('.audioLabel').find('.textImg-span').attr('style','display:none'    );
        }


        _this.find('.audioLabel').find('input').attr('text',text);
      _this.find('.audioLabel').find('input').attr('time',time);


          layer.close(e);

    },function (e) {
        // var continer=$('.bigImgCont>.drsElement:last-child');
        var audioName = _this.parent().parent().attr('audioname'); //_this.parent().parent().attr('audioname')
        // var text=continer.find('');
        var id=_this.closest('.drsElement').attr('id');
        audioName || _this.closest('.drsElement').remove('#'+id);
        layer.close(e);

    })
}

function ab(e) {
  var _this = $(e);
  var textA = isEmpty(_this.attr('text'))?'':_this.attr('text');
  var timeA = isEmpty(_this.attr('time'))?'':_this.attr('time');
    var audioName =_this.parent().parent().attr('audioname');
    var status=isEmpty(audioName)?'未上传':'已上传';
    // if(isEmpty(textA)){
    //     layer.msg("请输入描述!")
    //     return false
    // }
    // if(isEmpty(timeA)){
    //     layer.msg("请输入时长!")
    //     return false
    // }
    /*if(isEmpty(audioName)){
        layer.msg("请上传音频!")
    }*/
  console.log(textA,timeA)
  var html1= '<ul>' +
      '<li><label><span>音频状态</span><input class="audio-input-status" type="text" disabled="disabled" value="'+status+'"></label></li>' +
      '<li><label for="uploadFile1"><input id="uploadFile1" type="file" class="ds_n" onchange="audioUpload(this)" accept="audio/mpeg"/><span>选择上传文件</span><i>只能上传MP3格式文件</i></label></li>' +
      '<li><label><span>评测文字</span><textarea name="" id="text" cols="30" rows="10">'+textA+'</textarea></label></li>' +
      '<li><label><span>评测时长</span><input type="text" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,\'\');}).call(this)" onblur="this.v();"  class="time" value="'+timeA+'"><span>秒</span></label></li></ul>';
  layer.confirm(html1,{
    title:'资料编辑',
    skin:'pop',
      closeBtn:0,
    btn:['保存','取消']
  },function (e) {

      var text = $('#text').val();
      var time = $('.time').val();
      var audioStatus = $('.audio-input-status').val()
      if(audioStatus!=='已上传'){
          alert("请上传音频!");

          return false;
      }
      if(!isEmpty(text)||!isEmpty(time)){
          if(isEmpty(text)||isEmpty(time)){
              alert("请时长与描述必须同时填写!")
              return false;
          }else {
              _this.prev().attr('style','display:inline-block'    );
          }
      }else{
          _this.prev().attr('style','display:none'    );
      }

      _this.attr('text',text);
      _this.attr('time',time);
          layer.close(e);


  },function (e) {
      // var continer=$('.bigImgCont>.drsElement:last-child');
      var audioName = _this.parent().parent().attr('audioname'); //_this.parent().parent().attr('audioname')
      // var text=continer.find('');
      var id=_this.closest('.drsElement').attr('id');
      audioName || _this.closest('.drsElement').remove('#'+id);
    layer.close(e);
  })
}

function audioUpload(obj){
    // 开启加载层
    layer.load(2)
	var file = $(obj)[0].files[0];
	if(file == undefined || file == ""){
		return false;
	}
	var bookId = $("#bookId").val();
	var period = $("#period").val();
	var bookName = $("#bookName").val();
	var formData = new FormData();
	formData.append('uploadFile', file);
	formData.append('bookId', bookId);
	formData.append('period', period);
	formData.append('bookName', bookName);
	$.ajax({
		type : "POST",
		url : getRootPath() + "/imageTag/uploadAudioFile.jhtml",
		data : formData,
		dataType : "json",
		processData : false,
		contentType : false,
		success : function(data){
			if(data.success){
			   // obj.previousSbiling.value="已上传"
				var id = sessionStorage.getItem('id');

				$('#'+id+'').find('.yinPing').attr('audioName', data.url);
		        $('#'+id+'').find('.yinPing').css('display','block');
		        $('.audio-input-status').val('已上传')
		        $('#'+id+'').css('backgroundColor','rgba(1, 73, 162, 0.45)');
				//layer.msg('音频上传成功！');
			}else{
				//.msg(data.message);
			}
            layer.closeAll('loading')
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg('上传失败，请重试');
		}
	});
	$(obj).val('');
}

// 页面元素操作
$(".closePPT").click(function(i, elem){
	var pageIndex = $(this).siblings('.page').attr('pageIndex');
	$(this).parent().remove();
	$(".editor_box[pageIndex='"+pageIndex+"']").remove();
});

//打包测试
$('.submitBBBB').click(function(){
	var bookId = $("#bookId").val();
	var period = $("#period").val();
	var bookName = $("#bookName").val();
	$.ajax({
		type : "post",
		url : getRootPath() + "/imageTag/pageToZip.jhtml",
		data : {"bookId": bookId, "period": period, "bookName":bookName},
		dataType : "json",
		success : function(data) {
			layer.msg("打包成功！");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			removeLoading();
			layer.msg("打包失败，请重试！");
		}
	});
});


function choose() {
    window.onbeforeunload = function(){
        return "123";
    }
}

window.onunload = choose;

function drawPage(data) {
	var left,top,width,height;
	var boxId = 1;
    $.each(data.lessonList, function (i, e) {
    	var lessonId = e.lessonId;
    	$.each(e.pageList, function(h, elem) {
    		$('.page').find('img[name="'+ elem.imageName +'"]').parent().siblings('.selectCont').find('option[lessonId="'+ lessonId +'"]').attr('selected', true);
	        var pageNum = elem.pageNumber;
	        $.each(elem.rectList, function (j, ele) {
	        	var locaStr = "";
	        	var active_box = document.createElement("div");
	            if(ele.type==2){
	                $.each(ele.shape.points, function (k, item) {
	                    if(k==0){
	                        top = item.y;
	                        left = item.x;
	                    }
	                    if(k==1){
	                        width = item.x-left;
	                    }
	                    if(k==2){
	                        height = item.y-top;
	                    }
	                });
	            }
	            if(ele.type==1){
	                width = ele.shape.widthRadius*2;
	                height = ele.shape.heightRadius*2;
                    top = parseInt(ele.shape.point.y-height/2);
                    left = parseInt(ele.shape.point.x-width/2);
	            }
	            active_box.id = "active_box"+ boxId +"";
	            active_box.className = "box"+ boxId +" drsElement";
	            active_box.style.top = top+'px';
	            active_box.style.left = left+'px';
	            active_box.style.width = width+'px';
	            active_box.style.height = height+'px';
	            if (isNotEmpty(ele.audioName)) {
	            	active_box.style.backgroundColor = 'rgba(1, 73, 162, 0.45)';
				}
	            active_box.innerHTML = '<div class="bar" >'+
			            '<li class="yinPing sameType flex" audioname="'+ trimBlock(ele.audioName) +'" '+ addCheck(trimBlock(ele.audioName), 'style="display: block;"') +'><label class="sameType audioLabel"><span class="sameType spanStyle" >音</span><span class="textImg-span spanStyle" style="display: none">文</span><input type="text" class="ds_n" onclick="ab(this)"  text="'+ trimBlock(ele.describe) +'" time="'+ trimBlock(ele.duration) +'" /></label></li>'+
	//	                '<li class="zhuShi sameTypeTow" text="'+ trimBlock(ele.describe).replace(/<BR>/g,"\n") +'" '+ addCheck(trimBlock(ele.describe), 'style="display: block;"') +'><span class="a fa sameTypeTow fa-google-plus">注释</span></li>'+
		                '<li boxIndex="'+newNum+'" class="xuHao"><input type="text" name="number" class="number" onkeyup="checkNum(this)" onfocus="remeberValue(this)" onblur="changeCount(this)" value="'+ (j+1) +'" /></li>'+
	                    '</div>';
	            if(ele.type==1){
	                active_box.style.borderRadius = '50%';
	                active_box.setAttribute('type','circle');
	            }
	            if(isEmpty(ele.describe)||isEmpty(ele.duration)){
                    $(active_box).find('.textImg-span').attr('style','display:none'    );
                }else {
                    $(active_box).find('.textImg-span').attr('style','display:inline-block'    );
                }

	            $('.editor_box').find('img[name="'+ elem.imageName +'"]').parent().append(active_box);
	            isChecked = false;
	            circular = false;
	            boxId++;
	            sessionStorage.clear();

	        });
	    });
    });

}

var uploadNum = 0;
function upLoad(obj){
    debugger
	var fileVal = $("#uploadFile").val();
	if(fileVal == ""){
		layer.msg("请选择文件！");
		return false;
	}

	var file = $(obj).get(0).files;
	$.each(file, function(i, elem){
		 if (elem.type.indexOf("image") != 0) {
			 layer.msg('文件"' + elem.name + '"不是图片。');
         	 return false;
         }
	});

	var formData = new FormData($("#uploadForm")[0]);
	addLoading("上传图片...");
	$.ajax({
		type : "POST",
		url : getRootPath() + "/imageTag/uploadImageFileAjax.jhtml",
		data : formData,
		dataType : "json",
		processData : false,
		contentType : false,
		//这里我们先拿到jQuery产生的 XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
//		xhr : xhr_provider,
		success : function(data){
			uploadNum++;
			var tempFilePath = $("#tempFilePath").val()
			var pageIndex = $('.editor_box').length + 1;
			var leftHtml = "";
			var rigthHtml = "";
			$.each(data, function(i, elem) {
				leftHtml += '<li class="page_box" name="1" pageIndex="'+ pageIndex +'">'+
				 '				<div class="selectCont" style="width: 42px;height: 31px;float:left;margin-right: 5px">'+ lessonStr +
				 ' 					</div>'+
				 '			         <span class="closePPT">×</span>'+
				 '			         <div class="page" pageNum="'+ pageIndex +'" pageIndex="'+ pageIndex +'"><img class="thumbImg imgNum" name="'+ elem +'" src="'+ tempFilePath + elem +'" alt=""></div>'+
				 '			     </li>';
				 rigthHtml += '<div class="editor_box" pageIndex="'+ pageIndex +'" style="display: none;">'+
				 '	        <div class="bigImgCont uploadClass'+uploadNum+'" >'+
				 '	            <img class="imgNum" id="img_1" name="'+ elem +'" style="height: auto;width: auto" draggable="false" src="'+ tempFilePath + elem +'" name="1" alt="">'+
				 '	        </div>'+
				 '	    </div>';
				 pageIndex++;
			});
			$('.leftUl').append(leftHtml);
			$('.right').append(rigthHtml);

			if ($('.page.border_org').length == 0) {
				$('.page').eq(0).click().addClass('border_org');
			}


//			图片加载完成之后事件
			var imgLength = $('.imgNum').length;
			var t_img; // 定时器
			var isLoad = true; // 控制变量

//	 		判断图片加载状况，加载完成后回调
			isImgLoad(function(){
			    // 加载完成
			    $('.editor_box').each(function () {
	                var thisImg = $(this).find('img');
	                thisImg.width(thisImg[0].naturalWidth/2);
	            });
			});

//	 		判断图片加载的函数
			function isImgLoad(callback){


			    // 注意我的图片类名都是cover，因为我只需要处理cover。其它图片可以不管。
			    $('.imgNum').each(function(){
			        if(this.height === 0){
			            isLoad = false;
			            return false;
			        }
			    });
			    // 为true，没有发现为0的。加载完毕
			    if(isLoad){
			        clearTimeout(t_img);
			        // 回调函数
			        callback();
			    // 为false，因为找到了没有加载完成的图，将调用定时器递归
			    }else{
			        isLoad = true;
			        t_img = setTimeout(function(){
			            isImgLoad(callback);
			        },500);
			    }
			}


			//点击切换画布
	        _left.on('click', '.page_box .page', function(event) {
		        imgW = $('.editor_box:visible').find('img').width();
		        imgH = $('.editor_box:visible').find('img').height();
		        var dragresize = new DragResize('dragresize',
		        { minWidth: 20, minHeight: 10, minLeft: 0, minTop: 0});
		            var editor_box = $(".editor_box[pageIndex='"+$(this).attr("pageIndex")+"']");
		            editor_box.show().addClass('show').siblings('.editor_box').hide().removeClass('show');
		            restIndex = 0;
		    		bigNum = 0;
		    		newNum = 0;
		    });

			$('.page_box').each(function () {
                $(this).click(function () {
                    $(this).find('.selectCont').addClass('after').parents('.page_box').siblings().find('.selectCont').removeClass('after');
                    $(this).find('select').addClass('active').parents('.page_box').siblings().find('select').removeClass('active');
                    $(this).find('.page').addClass('border_org').parents('.page_box').siblings().find('.page').removeClass('border_org');
                });
            });

			//图形拖动和改变大小的代码引用
	        imgW = $('.editor_box:visible').find('img').width()/2;
	        imgH = $('.editor_box:visible').find('img').height()/2;
	        var dragresize = new DragResize('dragresize',
	                { minWidth: 20, minHeight: 10, minLeft: 0, minTop: 0});

	        dragresize.isElement = function(elm)
	        {
	            if (elm.className && elm.className.indexOf('drsElement') > -1) return true;
	        };
	        dragresize.isHandle = function(elm)
	        {
//	            if (elm.className && elm.className.indexOf('drsMoveHandle') > -1) return true;
	        };

	        dragresize.ondragfocus = function() {
	            sessionStorage.setItem('id',$(this)[0].element.id);
	        };
	        dragresize.ondragstart = function(isResize) {
	        	sessionStorage.setItem('id',$(this)[0].element.id);
	        };
	        dragresize.ondragmove = function(isResize) {
	        };

	        //	        改变图形区域后执行
	        dragresize.ondragend = function(isResize) {

	        };
	        dragresize.ondragblur = function() { };
	        dragresize.apply(document);

	        // 鼠标抬起
	        $('.right').on('mouseup','.bigImgCont',function(e) {
		              var _bigImgCont = $('.bigImgCont');
		              var _leftL = Number(_bigImgCont[0].parentElement.offsetLeft);
		              var _leftT = Number(_bigImgCont[0].parentElement.offsetTop);
		              var _this = $(e.target);

		              if (e.target.className && $(e.target).hasClass('drsElement')){
			              dragresize.elmX = parseInt(_this.css('left'));
			              dragresize.elmY = parseInt(_this.css('top'));
					  }

		              if (e.target.className && $(e.target).hasClass('xuHao')){
	 		              dragresize.elmX = parseInt(_this.parents('.drsElement').css('left'));
	 		              dragresize.elmY = parseInt(_this.parents('.drsElement').css('top'));
	 				  }

		              if (e.target.className && $(e.target).hasClass('sameType')){
	 		              dragresize.elmX = parseInt(_this.parents('.drsElement').css('left'));
	 		              dragresize.elmY = parseInt(_this.parents('.drsElement').css('top'));
	        	}



	        });

	        $(".closePPT").click(function(i, elem){
	        	var pageIndex = $(this).siblings('.page').attr('pageIndex');
	        	$(this).parent().remove();
	        	$(".editor_box[pageIndex='"+pageIndex+"']").remove();
	        });
			removeLoading();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			removeLoading();
			layer.msg('上传失败，请重试');
		}
	});
}
