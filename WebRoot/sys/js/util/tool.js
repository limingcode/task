// 当前时间
(function($){
	$.fn.dateStyle = function(str){
		var thisObj = this;
		
		 var m = function(val){
				return val<10?('0'+ val):val;
		  };
		  
         var t = function(){       
		     var today = new Date(); 
             var weekday=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
			 thisObj.html(str.replace(/\$yyyy\$/g,today.getFullYear())
			 .replace(/\$MM\$/g,m(today.getMonth()+1))
			 .replace(/\$dd\$/g,m(today.getDate()))
			 .replace(/\$EEE\$/g,m(weekday[today.getDay()]))
			 .replace(/\$hh\$/g,m(today.getHours()))
			 .replace(/\$mm\$/g,m(today.getMinutes()))
			 .replace(/\$ss\$/g,m(today.getSeconds())));
        };
		if(str.indexOf("$ss$")!=-1) {setInterval(t, 1000);};
		if(str.indexOf("$mm$")!=-1) {setInterval(t, 60000);};
		if(str.indexOf("$hh$")!=-1) {setInterval(t, 3600000);}; 	
		t();
		return this;
	};
})(jQuery);

//全选，全不选
(function($){
$.fn.checkboxAll=function(selectAll,clearAll,inverse)
{
	  var checkObj=this;
	  checkObj.find(selectAll).click(
	  	function()
		{
			checkObj.find(":checkbox").attr("checked", true);
		}
	  );
	  checkObj.find(clearAll).click(
	  function(){
		  	checkObj.find(":checkbox").attr("checked", false);
		  }
	  );
	 checkObj.find(inverse).click(
	 function(){
			checkObj.find(":checkbox").each(
			function()
			{	
				$(this).attr("checked",!($(this).attr("checked")));
			}
			);
	});
	  return this;
};
})(jQuery);

// 点击除自身之外的其他任何地方，自身消失
function hideSelf(hideElement,fun){
	$(document).mouseup(function(event){ // 点击不是ul的其他任何地方，ul隐藏
		if($(event.target).parents(hideElement).length==0){
			$(hideElement).hide();
			if(typeof fun !== "undefined"){
				fun();
			}
		}
	});
}

// input 灰色提示
function inputGrayTip(inputbox){
	
	$(inputbox).click(function() {
		$(this).children("em").hide();
		$(this).children("input").focus();
	});
	$(inputbox).children("input").keyup(function(event){
		$(this).prev("em").hide();
	});
	
	$(inputbox).children("input").blur(function() {
		if($(this).val()==""){
			$(this).prev("em").show(); 
		}   
	});
	if($(inputbox).children("input").val().length>0){
	$(inputbox).children("input").prev("em").hide(); 
	}

};


// 弹窗居中，适合有蒙层的弹窗
function centerPopup(popupElement){
	$(window).resize(function(){
		// popupElement，居中的元素
		$(popupElement).css({
			left: ($(window).width() - $(popupElement).outerWidth())/2,
			top: ($(window).height() - $(popupElement).outerHeight())/3
		});
	});
	// 初始化函数
	$(window).resize();
}

// 新增
function newAddSlide(clickElement,slideElement,fun){
	$(clickElement).live("click",function() {
		if(typeof fun !== 'undefined'){
			fun($(this));
		}
		$(slideElement).show();
		var sideLayout = $(".realityHeight").height()-$(slideElement).height()-$(slideElement).next().height();
        $(slideElement).slideDown("fast",function(){$(".rightMain").animate({ scrollTop: sideLayout }, 600);});	
    });
}


//异步请求,拦截的返回值进行处理
$.ajaxSetup({
    dataFilter : function(data, type){
    	if(data.indexOf('<title>登录-竞争力一对一学员管理系统</title>')>-1){
    		$.Skyedu.WIN.info('登陆已失效，请重新登陆！');
			throw new Error("SESSION!");
    	}
		if(data=='{"PERMISSION":"NO"}'){
			$.Skyedu.WIN.info('当前操作没有权限哦！');
			//window.location.href = PATH + "/employee/noPermission";
			throw new Error("NOPERMISSION!");
		}else{
			return data;
		}
   }
});


//异步请求,拦截的返回值(所有的json请求返回后执行的方法)
//$(document).ajaxComplete(function(event,xhr,settings){  
//	if(xhr.responseText=='{"PERMISSION":"NO"}'){
//	    window.location.href = PATH + "/employee/noPermission";
//	}  
//}); 

//inputNumberCount方法说明========================================================= 
$.fn.inputNumberCount=function(d)
{
		var tipObj,maxInputLen,format,language;
		
		//初始默认值
		var defaultDate = {tipObj:$("#tip"),maxInputLen:140,format:"您已输<span class=\"nu\">$x$</span>字，还可以输入<span class=\"nu\">$y$</span>字",language:'en'};
		
		if( d != undefined) {
			tipObj = (d.tipObj==undefined)?defaultDate.tipObj:d.tipObj;
			maxInputLen = (d.maxInputLen==undefined)?defaultDate.maxInputLen:d.maxInputLen;
			format = (d.format==undefined)?defaultDate.format:d.format;
			language = (d.language==undefined)?defaultDate.language:d.language;
		} else {
			tipObj=defaultDate.tipObj;
			maxInputLen=defaultDate.maxInputLen;
			format=defaultDate.format;
			language=defaultDate.language;
		}
		
		var inputStr=""; //已经输入的内容
		var inputLength=0; //输入框的字数
		var inputObj=this;
		
		//写入提示信息
		var writeTip = function()
		{
			tipObj.html(format.replace(/\$x\$/g,inputLength).replace(/\$y\$/g,(maxInputLen-inputLength)).replace(/\$z\$/g,maxInputLen));
		};
		writeTip();
		
		//用于检测当前输入的字数(2个非中文,算一个字),还能输入的字数
		var writeInput = function()
		{
				inputStr=inputObj.val(); //输入内容
				//获取内容的总字符
				if(inputStr!=undefined)
				{
					inputLength=inputStr.length;	
					if(language=="cn")
					{
						//计算非中文的字符长度
						//过滤中文
						inputStr=inputStr.replace(/[\u4E00-\u9FA5]/g, '');
						inputLength=(inputLength-inputStr.length)+(inputStr.length)/2;
						if(inputLength%1==0.5)
						{
							inputLength=inputLength-0.5;
						}	
					}
				}
				if(inputLength<maxInputLen||inputLength==maxInputLen)
				{
					//把提示字数写入指定位置:tipObj
					writeTip();
					inputStr=inputObj.val(); //输入内容
					//把inputStr保存起来
					inputObj.data("inputStrC",inputStr);
				}
				else
				{
					inputObj.val(inputObj.data("inputStrC"));					
				}
		};
		writeInput();
		
		//聚焦
		this.focus(function(){
			writeInput();
		});
		
		//鼠标松开时
		inputObj.keyup(
		function(){				
				writeInput();						
			}
		);

	return this;
};
//inputNumberCount方法说明=====================================================  En
