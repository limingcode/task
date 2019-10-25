
/** 
* json对象转字符串形式 
*/ 
$.MY_JSON = {
	jsonToStr:function(o){
		var arr = []; 
		var fmt = function(s) { 
		if (typeof s == 'object' && s != null) return $.MY_JSON.jsonToStr(s); 
		return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s; 
		};
		for (var i in o) arr.push("'" + i + "':" + fmt(o[i])); 
		return '{' + arr.join(',') + '}'; 
	}
};
/**
 * 分页工具类
 * @author xj.chen
 * @version 3.0.1
 * 由2.0.1升级，
 * 升级内容：
 * 1、解决多次点击上下分页bug（跳转到位置页数）
 * 2、新增前端内置库分页功能
 * 3、初始化数据方法(init)更改，以前是数据直接初始化、init方法只更新页码数和数据量。更新后所有的数据全部由init方法实现
 */

/**
 * 分页器配置详解
 * new PAGE({
 *	page : 1,              			//请求页码
 *	maxResults : 10,       			//每页显示条数
 *	pageName : 'page',     			//Action接收page名称
 *	maxResultsName : 'maxResults', 	//Action接收maxResults名称
 *	url : PATH+'/classManager/classManager/classqueryCourse' ,//请求地址
 *	async : true,          			//是否开启异步请求
 *	type : "POST",         			//请求类型
 *	contentType : 'application/x-www-form-urlencoded;charset=UTF-8',//请求规范参数
 *	pageId : 'pageHtml',   			//分页布局id（布局信息放置的位置id）
 *	pageInfoNum : 10,      			//分页布局数量
 *	pageListHtmlId : '', 			//分页列表数据显示位置
 *	cache : true, 					//是否使用缓存数据
 *	pageInfoCss :  {index:true,go:false,type:'index'},//分页样式布局next
 *	pageInfoModel:{
 *		show:false,        			//是否显示分页消息
 *		model:'<li>共<em>$count$</em>条记录，当前第<em>$page$</em>/共<em>$countPage$</em>页，每页<em>$maxResults$</em>条记录 </li>',
 *		                   			//分页消息模型盒子 
 *						   			//$count$      总页数
 *						   			//$page$       当前页
 *						   			//$countPage$  总页数
 *						   			//$maxResults$ 每页显示记录数
 *		location:'front'   			//back：在分页的后面显示   front：在分页的前面显示
 *	},
 *	returns :function(e,e1){alert(e);},//回调函数  e:data e1:分页器本身对象 
 *	data :function(e){return null;}//传递参数为方法体 返回json类型数据 e:分页器本身对象
 * });
 * 
 * 
 * 
 * @param {Object} data_info
 */
function PAGE(data_info){
	this.s4 = function(){return Math.floor((1 + Math.random()) * 0x10000).toString(16);};
	this.serialVersionUID = "cache_page_"+this.s4()+'_'+this.s4()+'_'+this.s4()+'_'+this.s4();//''+dt.getHours()+dt.getMinutes()+dt.getSeconds();
	this.S = 0;//请求控制参数
	this.service = {type:0,query:function(){return null;}};//库类型，默认为web-service(type:0),js-service(type:1)
	this.page = 1;              					//请求页码
	this.maxResults = 10;       					//每页显示条数
	this.pageName = 'page';     					//Action接收page名称
	this.maxResultsName = 'maxResults'; 			//Action接收maxResults名称
	this.url = '' ;//请求地址
	this.async = true;          					//是否开启异步请求
	this.type = "POST";         					//请求类型
	this.contentType = 'application/x-www-form-urlencoded;charset=UTF-8';//请求规范参数
	this.pageId = 'pageHtml';   					//分页布局id（布局信息放置的位置id）
	this.pageInfoNum = 10;      					//分页布局数量
	this.pageListHtmlId = ''; 						//分页列表数据显示位置
	this.cache=true;            					//是否使用缓存数据
	this.myDate = null;     						//内置库
	this.success = false;       					//请求是否成功
	this.pageCount = 10;        					//总记录数    (在回调函数中请务必给该属性赋值)
	this.pageMaxPage=0;          					//总页数
	this.dataNames = new Array();					//缓存组
	this.returns = function(e,e1){alert(e);};		//回调函数 e:data e1:obj 
	this.data = function(e){return null;};         	//传递参数为方法体 返回json类型数据e:obj
	this.onLoad_v = false;
	this.onLoad_b = false; //是否开启滚动自动分页控制
	
	this.pageInfoModel={
		show:false,        //是否显示分页消息
		model:'<li>共<em>$count$</em>条记录，当前第<em>$page$</em>/共<em>$countPage$</em>页，每页<em>$maxResults$</em>条记录 </li>',
		                   //分页消 息模型盒子  ,$count$总页数 ,$page$ 当前页, $countPage$  总页数 ,$maxResults$ 每页显示记录数
		location:'front'   //back：在分页的后面显示   front：在分页的前面显示
	};
	this.pageInfoCss =  {index:true,go:false,type:'index'};	//分页样式布局 type:index 普通分页样式，使用索引分页 1,2,3。。  type:next 只有下一页按钮的分页（查看更多）
	var obj = this;
	this.onLoad = function(){
		$(window).scroll(function(){
			if(obj.onLoad_v) return;
			var scrollTop = $(this).scrollTop();               //滚动条距离顶部的高度
			var scrollHeight = $(document).height();           //当前页面的总高度
			var windowHeight = $(this).height();               //当前可视的页面高度
			if(scrollTop + windowHeight >= scrollHeight){//距离顶部+当前高度 >=文档总高度 即代表滑动到底部
				if(obj.pageMaxPage>obj.page) {
					obj.page++;
					obj.submit(function(){obj.onLoad_v=false;});
					obj.onLoad_v = true;
				}
			}
		});
	};
	this.init = function(e){
		var loadBol = typeof e !== 'undefined'; 	//判断初始化时是否传入了data_info参数
		this.page = loadBol && typeof e.page !== 'undefined'&& e.page>0?   e.page>this.pageMaxPage ? this.pageMaxPage : e.page: this.page;
		this.maxResults = loadBol && typeof e.maxResults !== 'undefined' ?  e.maxResults: this.maxResults;
		this.pageName = loadBol && typeof e.pageName !== 'undefined' ?  e.pageName: this.pageName;
		this.maxResultsName = loadBol && typeof e.maxResultsName !== 'undefined' ?  e.maxResultsName: this.maxResultsName;
		this.url = loadBol && typeof e.url !== 'undefined' ?  e.url: this.url ;
		this.async = loadBol && typeof e.async !== 'undefined' ?  e.async: this.async;
		this.type = loadBol && typeof e.type !== 'undefined' ?  e.type: this.type;
		this.contentType = loadBol && typeof e.contentType !== 'undefined' ?  e.contentType: this.contentType;
		this.pageId = loadBol && typeof e.pageId !== 'undefined' ?  e.pageId: this.pageId;
		this.pageInfoNum = loadBol && typeof e.pageInfoNum !== 'undefined' ?  e.pageInfoNum: this.pageInfoNum;
		this.pageListHtmlId = loadBol && typeof e.pageListHtmlId !== 'undefined' ?  e.pageListHtmlId: this.pageListHtmlId;
		this.cache=loadBol && typeof e.cache !== 'undefined' ?  e.cache: this.cache;
		this.myDate = loadBol && typeof e.myDate !== 'undefined' ?  e.myDate: this.myDate;
		this.returns = loadBol && typeof e.returns !== 'undefined' ?  e.returns:this.returns;//回调函数 e:data e1:obj 
		this.data = loadBol && typeof e.data !== 'undefined' ?  e.data:this.data;         //传递参数为方法体 返回json类型数据e:obj
		this.onLoad_b = loadBol && typeof e.onLoad !== 'undefined' ?  e.onLoad:this.onLoad_b;//是否开启滚动到底部自动加载
		
		if (loadBol && typeof e.service !== 'undefined'){//库数据初始化
			if(typeof e.service.query !== 'undefined') this.service.query = e.service.query;
			if(typeof e.service.type !== 'undefined') this.service.type = e.service.type;
		}
		if (loadBol && typeof e.pageInfoCss !== 'undefined') {//分页样式布局
			if(typeof e.pageInfoCss.index !== 'undefined') this.pageInfoCss.index = e.pageInfoCss.index;
			if(typeof e.pageInfoCss.go !== 'undefined') this.pageInfoCss.go = e.pageInfoCss.go;
			if(typeof e.pageInfoCss.type !== 'undefined') this.pageInfoCss.type = e.pageInfoCss.type;
		}
		if(loadBol && typeof e.pageInfoModel !== 'undefined'){//初始化分页消息
			if(typeof e.pageInfoModel.show !== 'undefined') this.pageInfoModel.show = e.pageInfoModel.show;
			if(typeof e.pageInfoModel.model !== 'undefined') this.pageInfoModel.model = e.pageInfoModel.model;
			if(typeof e.pageInfoModel.location !== 'undefined') this.pageInfoModel.location = e.pageInfoModel.location;
		}
	};
	this.init(data_info);
	if(this.onLoad_b) this.onLoad();
	this.submit = function(_fn){//请求发送函数
		if(typeof _fn == 'undefined') _fn = function(){};
		if(obj.service.type==0){//web-service
			obj.usersCacheName = obj.serialVersionUID+ "_" + Math.abs(this.maxResults)+ "_" + Math.abs(this.page)+ "_" + $.MY_JSON.jsonToStr(this.data()).replace(/\-/g,"\-/").replace(/null/g,"\'\'");
			if( $("body").data(obj.usersCacheName) == undefined || !this.cache) {
				clearTimeout(this.S);
				$('#'+obj.pageListHtmlId+' li.html-loding').remove();
				this.S = setTimeout(function(){
					//如果分页样式为next则不清除原有的html
					if("next"!=obj.pageInfoCss.type) {
						$('#'+obj.pageListHtmlId).html('');
						try{$.Skyedu.WIN.load(obj.pageListHtmlId);}catch (e) {}
					}else{
						if(obj.page==1)
							$('#'+obj.pageListHtmlId).html("<li class=\"html-loding\" style=\"text-align: center;\"><i class=\"am-icon-spinner am-icon-spin\"></i></li>");
						$('#'+obj.pageId+" div.viewmore").addClass("loding");
					}
						$.ajax({
							type:obj.type,
							url:obj.url+'?'+obj.pageName+'='+obj.page+'&'+obj.maxResultsName+'='+obj.maxResults,
							data:obj.data(obj),
							async:obj.async,
							contentType:obj.contentType,
							success:function(data){
								$('#'+obj.pageListHtmlId+' li.html-loding').remove();
								obj.success = true;
								obj.returns(data, obj);
								if(Math.floor(obj.pageCount/obj.maxResults)+(obj.pageCount%obj.maxResults>0?1:0)-obj.page < 0 && obj.page-1>0){
									obj.page --;
									obj.submit();
								}
								$('#'+obj.pageId).children().remove();//清除分页布局信息
								obj.pageInfo.cssInit();
								try{$.Skyedu.WIN.removeLoad(obj.pageListHtmlId);}catch (e) {}
								$('#'+obj.pageId+" div.viewmore").removeClass("loding");
								$("body").data(obj.usersCacheName,data);
								obj.dataNames.push(obj.usersCacheName);
								_fn();
							},
							error:function(){
								$('#'+obj.pageListHtmlId+' li.html-loding').remove();
								try{$.Skyedu.WIN.info('初始化数据失败，请按CTRL+F5刷新本页面！');}catch (e) {}
								$('#'+obj.pageId+" div.viewmore").removeClass("loding");
								obj.success = false;
								obj.returns({'errorInfo':"数据请求失败！"}, obj);
							}
						});
				},300);
			} else {
				obj.returns($("body").data(obj.usersCacheName), obj);
				$('#'+obj.pageId).find('ul').remove();//清除分页布局信息
				obj.pageInfo.cssInit();
			}
		}else if(obj.service.type==1){//js-service
			//如果分页样式为next则不清除原有的html
			if('next'!=obj.pageInfoCss.type) $('#'+obj.pageListHtmlId).html('');
			try{$.Skyedu.WIN.load(obj.pageListHtmlId);}catch (e) {}
			var retDate = obj.service.query.search(obj.data()+" limit "+(obj.page-1)*obj.maxResults+","+obj.page*obj.maxResults);
			var retCount = obj.service.query.search("select count(*) from xxx "+(obj.data().indexOf("where")>0?obj.data().substr(obj.data().indexOf("where")):""));
			retDate = {list:retDate,count:retCount};
			obj.success = true;
			obj.returns(retDate, obj);
			if(Math.floor(obj.pageCount/obj.maxResults)+(obj.pageCount%obj.maxResults>0?1:0)-obj.page < 0 && obj.page-1>0){
				obj.page --;
				obj.submit();
			}
			$('#'+obj.pageId).children().remove();//清除分页布局信息
			obj.pageInfo.cssInit();
//			$.Skyedu.WIN.removeLoad(obj.pageListHtmlId);
		}
	}; 
	this.refresh = function(e,e1){//刷新整个分页缓存数据e:清除缓存范围，0：所有，1当前。e1：是否提交请求 ，默认为清除所有并提交请求.
		var isSend = e1 != undefined? e1 : true;
		var isAll = e != undefined? e:0;
		if(isAll == 0) {$(obj.dataNames).each(function(){//清除所有
			$("body").removeData(this);
		}); obj.dataNames = new Array();}
		else {$("body").removeData(obj.usersCacheName); obj.dataNames.splice(obj.dataNames.indexOf(obj.usersCacheName),1);}//清除当前
		if(isSend) obj.submit();//提交
	};
	this.pageInfo = function(){
		//计算总页数
		//var maxPage = 0;
		this.pageMaxPage = Math.floor(this.pageCount/this.maxResults)+(this.pageCount%this.maxResults>0?1:0);
		//maxPage = maxPage<=pageInfoNum?maxPage:pageInfoNum;
		//计算总页数完成
		var pageNum = this.pageMaxPage<this.pageInfoNum?this.pageMaxPage:this.pageInfoNum;//一共显示多少个页码
		
		//起始页计算
		var indexPage = this.page-Math.floor(pageNum/2) > 0 ? this.page-Math.floor(pageNum/2):1;
		//结束索引计算
		var endIndex = indexPage+this.pageInfoNum;
		if(endIndex > this.pageMaxPage){
			endIndex = this.pageMaxPage+1;
			indexPage = this.pageMaxPage-this.pageInfoNum+1;
		}
		if(indexPage<1){
			indexPage=1;
		}
		//$('#'+this.pageId).find('ul').remove();//清除分页布局信息    modify by xj.cjen info:清楚分页布局信息现由submit控制
		//分页索引样式控制
		if('next'==this.pageInfoCss.type){
			if (this.page < this.pageMaxPage) {
				$('#'+this.pageId).append("<div class=\"viewmore am-btn am-radius am-btn-block am-btn-blue\"><i class=\"am-icon-spinner am-icon-spin\"></i>查看更多</div>");
				$('#'+this.pageId).find('div.viewmore').on('click',function(){
					obj.init({page: obj.page + 1,maxResults: obj.maxResults});
					obj.submit();
				});
			}
		}else{
			var pageHtml = "<ul>";
			//分页消息
			if(this.pageInfoModel.show && this.pageInfoModel.location=='front'){
				pageHtml+= this.pageInfoModel.model
				.replace('$count$',this.pageCount)
				.replace('$page$',this.page)
				.replace('$countPage$',this.pageMaxPage)
				.replace('$maxResults$',this.maxResults)
				;
			}
			if(this.page<2){
				pageHtml+="<li><span>首页</span></li> <li><span>上一页</span></li>";
			}else{
				pageHtml+="<li><a>首页</a></li> <li><a>上一页</a></li> ";
			}
			if(this.pageInfoCss.index){
				for(var i = indexPage ; i < endIndex ; i ++){
					if(this.page == i){
						pageHtml+="<li class=\"thisclass\">"+i+"</li>";
					}else{
						pageHtml+="<li><a>"+i+"</a></li>";
					}
				}
			}
			if(this.page < this.pageMaxPage){
				pageHtml+="<li><a>下一页</a></li> <li><a>末页</a></li>";
			}else{
				pageHtml+="<li><span>下一页</span></li> <li><span>末页</span></li>";
			}
			//show go
			if (this.pageInfoCss.go) {
				pageHtml+="<li> 转到 <input type=\"text\" class=\"iparea\" value=\"1\"> 页</li>";
				pageHtml+="<li><div class=\"gobtn\">go</div></li>";
			}
			//分页消息
			if(this.pageInfoModel.show && this.pageInfoModel.location=='back'){
				pageHtml+= this.pageInfoModel.model
				.replace('$count$',this.pageCount)
				.replace('$page$',this.page)
				.replace('$countPage$',this.pageMaxPage)
				.replace('$maxResults$',this.maxResults)
				;
			}
			$('#'+this.pageId).append(pageHtml+"</ul>");
			//为分页按钮添加事件
			
			//add click to go
			if (this.pageInfoCss.go) {
				$('#'+this.pageId).find('div.gobtn').on('click',function(){
					var inputvalue = Math.abs($($('#'+obj.pageId).find('input.iparea')[0]).val());
					if(inputvalue>0&&inputvalue!=obj.page&&inputvalue<=obj.pageMaxPage){
						obj.init({page: inputvalue,maxResults: obj.maxResults});
						obj.submit();
					}
				});
				$('#'+this.pageId).find('input.iparea').keypress(function (e) { 
				    var key = e.which; //e.which是按键的值
				    if (key == 13) {
						var inputValue = $(this).val();
						if(inputValue>0&&inputValue!=obj.page&&inputValue<=obj.pageMaxPage){
							obj.init({page: inputValue,maxResults: obj.maxResults});
							obj.submit();
						}
			    	}
				});
			}
			var ae = $('#'+this.pageId).find('a');
			var aLen = ae.length;
			for(var i = 0 ; i <aLen ; i ++){
				var aObj = $(ae[i]);
				var aHtml = $.trim(aObj.html());
				if('首页'==aHtml){
					aObj.on('click',function(){
						obj.init({page: 1,maxResults: obj.maxResults});
						obj.submit();
					});
				}else if('末页'==aHtml){
					aObj.on('click',function(){
						obj.init({page: obj.pageMaxPage,maxResults: obj.maxResults});
						obj.submit();
					});
				}else if('上一页'==aHtml){
					aObj.on('click',function(){
						obj.init({page: obj.page-1,maxResults: obj.maxResults});
						obj.submit();
					});
				}else if('下一页'==aHtml){
					aObj.on('click',function(){
						obj.init({page: obj.page+1,maxResults: obj.maxResults});
						obj.submit();
					});
				}else{
					aObj.on('click',function(){
						obj.init({page: $(this).html(),maxResults: obj.maxResults});
						obj.submit();
					});
				}
			}
		}
		//分页索引样式控制 end
	};
	this.pageInfo.cssInit = function(){
		if(obj.pageCount>obj.maxResults){
			var thisPageE = $('#'+obj.pageId);
			thisPageE.css({display:'block'});
			thisPageE.parent().css({'padding-bottom':'15px'});
			obj.pageInfo();
		}else{
			var thisPageE = $('#'+obj.pageId);
			thisPageE.css({display:'none'});
			//thisPageE.parent().attr('style','padding-bottom:0;');
		}
	};
}