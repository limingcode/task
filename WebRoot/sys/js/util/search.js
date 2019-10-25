function SearchInput(data_info){
	this.inputId = 'searchInput';
	this.id='';
	this.text='';//输入框的值
	this.value='';//选择的档案号
	this.info='请输入关键信息';
	this.searchKey='';
	this.width='166px';
	this.clear=true;//当失去焦点且没有选中值是是否清楚输入项
	this.change=function(){};//当值发生改变时回调函数
	this.returns=function(){};//分页回调函数
	this.data=function(){};//分页data参数请求函数
	this.url='';//数据请求地址
	this.isLoad = false;
	this.pageName = 'page';     			
	this.maxResultsName = 'maxResults';
	var obj = this;
	this.init = function(e){
		if(typeof e == 'undefined') e = {};
		this.inputName=typeof e.inputName == 'undefined' ? this.inputName:e.inputName;
		this.id=typeof e.id == 'undefined' ? this.id:e.id;
		this.text=typeof e.text == 'undefined' ? this.text:e.text;//输入框的值
		this.value=typeof e.value == 'undefined' ? this.value:e.value;//选择的档案号
		this.info=typeof e.info == 'undefined' ? this.info:e.info;
		this.searchKey=typeof e.searchKey == 'undefined' ? this.searchKey:e.searchKey;
		this.width=typeof e.width == 'undefined' ? this.width:e.width;
		this.clear=typeof e.clear == 'undefined' ? this.clear:e.clear;
		this.change=typeof e.change == 'undefined' ? this.change:e.change;//当值发生改变时回调函数
		this.returns=typeof e.returns == 'undefined' ? this.returns:e.returns;//分页回调函数
		this.data=typeof e.data == 'undefined' ? this.data:e.data;//分页data参数请求函数
		this.url=typeof e.url == 'undefined' ? this.url:e.url;//数据请求地址
		this.pageName = typeof e.pageName == 'undefined' ? this.pageName:e.pageName;//数据请求地址'page';     			
		this.maxResultsName = typeof e.maxResultsName == 'undefined' ? this.maxResultsName:e.maxResultsName;//数据请求地址'maxResults';
		this.isLoad=typeof e.isLoad == 'undefined' ? this.isLoad:e.isLoad;//是否初始化工具
	};
	this.init(data_info);//初始化工具值
	this.user_page = new PAGE({
		 maxResults : 8,       			//每页显示条数
		 pageName : obj.pageName,     			
		 maxResultsName : obj.maxResultsName, 
		 url : obj.url ,//请求地址
		 pageListHtmlId : 'user_search_list_html', 			//分页列表数据显示位置
		 pageId : "user_search_list_page",
		 pageInfoCss :  {index:false,go:false,type:'index'}//分页样式布局
	});
	this.load = function(info){
		//分页参数重置
		obj.init(info);
		obj.user_page.pageListHtmlId = 'user_search_list_html_'+obj.id; 			//分页列表数据显示位置
		obj.user_page.pageId = "user_search_list_page_"+obj.id;
		obj.user_page.data = function(e){
			return obj.data(e,obj);
		};//传递参数为方法体 返回json类型数据 e:分页器本身对象
		obj.user_page.returns = function(e,e1){
			obj.returns(e,e1);
			$('#'+e1.pageListHtmlId).parents(".namelist").show();
			$('#'+e1.pageListHtmlId).find('li').click(function(){
				obj.value = $(this).attr("id");
				obj.text = $(this).html();
				obj.change(obj,$(this));
				$('#'+obj.id).find('input').val(obj.text).attr("_value",obj.value);
				$(this).parents(".namelist").hide();
			});
		};
		//分页参数重置 end
		if(this.id != ''){
			this.inputId += obj.id;
			$('#'+this.id).html("<div class=\"stdname\"> <input autocomplete=\"off\" name=\""+(obj.inputName==undefined? obj.inputId:obj.inputName)+"\" id=\""+obj.inputId+"\" style=\"width:"+this.width+";\" type=\"text\" placeholder=\""+this.info+"\"> <ul class=\"namelist\" style=\"width:"+this.width+";display:none;\"><div id=\""+obj.user_page.pageListHtmlId+"\"></div><div id=\""+obj.user_page.pageId+"\" class=\"page\"></div> </ul> </div>");
			//input事件
			$('#'+this.id).find('input').click(function(){
				$(this).next(".namelist").show();
				hideSelf(".namelist",function(){if(obj.value == ''&& obj.clear) $('#'+obj.id).find('input').attr("value",'').attr("_value",'');}); 
			});
			$('#'+this.id).find('input').keydown(function(key){
				if(!$('#'+obj.user_page.pageListHtmlId).is(":hidden")&&key.which==13){//回车
					var liClick = $('#'+obj.user_page.pageListHtmlId).find('li.searchhover');
					if(liClick.length>0){
						obj.value = $(liClick).attr("id");
						obj.text = $(liClick).html();
						obj.change(obj,$(liClick));
						$('#'+obj.id).find('input').val(obj.text).attr("_value",obj.value);
						$(liClick).parents(".namelist").hide();
					}
					return false;
				}else if(!$('#'+obj.user_page.pageListHtmlId).is(":hidden")&&key.which==40||key.which==38){//下上
					var li = $('#'+obj.user_page.pageListHtmlId).find('li');
					if(li.length>0){
						var liClick = $('#'+obj.user_page.pageListHtmlId).find('li.searchhover');
						$(li).removeClass('searchhover');
						if(key.which==40){
							if($(liClick).next().length>0) $(liClick).next().addClass('searchhover');
							else $(li).first().addClass('searchhover');
						}else{
							if($(liClick).prev().length>0) $(liClick).prev().addClass('searchhover');
							else $(li).last().addClass('searchhover');
						}
					}
					return false;
				}
			});
			$('#'+this.id).find('input').keyup(function(key){
				if(key.which==46 || key.which==8){
					obj.text = '';
					obj.value = '';
					obj.change(obj,{});
					//$('#'+obj.id).find('input').attr("value",obj.text);
				}
				if(key.which!=40&&key.which!=38&&key.which!=13){
					var text = $.trim($(this).val());
					if(text != ''){
						obj.searchKey = text;
						obj.user_page.submit();
					}else{
					 	$('#'+obj.user_page.pageListHtmlId).parents(".namelist").hide();
					}
				}
			});
		}
		//初始选择器化值
		obj.change(obj,{});
		$('#'+obj.id).find('input').attr("value",obj.text).attr("_value",obj.value);
		//初始选择器化值 end
		return obj;
	};
	if(this.isLoad) obj.load({id:this.id,width:this.width,change:this.change});//初始化工具
}
