/**
 * 前端json数据筛选工具
 * 功能描述：
 * 	1、该工具可以将json当做一个表来进行查询。
 * 	2、json中数据格式要和表数据格式类似，在这里用Array来表示
 * 	3、Array中每个单元“[n]”中的数据相当于数据库表中的每一行数据
 * 	4、筛选语法参照SQL国际标准。select * from XXX where a=1 and b like 'alskjf%' or c not in (1,2,3)
 * 	5、insert语句不能省略key（字段）名称
 * @version: 2.1.0
 * @author: xj.chen
 * @data: 2013-9-5 
 */

/**
 * 工具初始化说明
 * data_info：{} json数据对象
 * data_info属性：
 * 	isLoad      ：是否开启自动初始化数据，默认false
 * 	url         ：当不设置筛选初始化工具时可设置该参数让其自动获取初始化数据。
 * 	type        ：请求类型（get or post）
 * 	contentType ：请求规范参数 application/x-www-form-urlencoded;charset=UTF-8
 * 	returns     ：当执行数据筛选后的回调函数
 * 	data        ：请求参数，传递参数为方法体 返回json类型数据e:obj
 * 	loadHtml    ：加载状态显示位置，为某html节点的id
 */
function Search(data_info){

	this.S = 0;//请求控制参数
	var loadBol = typeof data_info !== 'undefined';                                                              	  //判断初始化时是否传入了data_info参数
	this.isLoad = loadBol && typeof data_info.isLoad !== 'undefined' ?  data_info.isLoad: false;
	this.url = loadBol && typeof data_info.url !== 'undefined' ?  data_info.url: PATH+'/classManager/classManager/classqueryCourse' ;//请求地址
	this.type = loadBol && typeof data_info.type !== 'undefined' ?  data_info.type: "POST";         				  //请求类型
	this.contentType = loadBol && typeof data_info.contentType !== 'undefined' ?  data_info.contentType: 'application/x-www-form-urlencoded;charset=UTF-8';//请求规范参数
	this.returnData = loadBol && typeof data_info.returnData !== 'undefined' ?  data_info.returnData: null;     	  //请求所产生的返回数据json类型  如果请求失败返回数据为：{'errorInfo':"数据请求失败！"}
	this.success = false;       																					  //请求是否成功
	this.returns = loadBol && typeof data_info.returns !== 'undefined' ?  data_info.returns:function(e,e1){};//回调函数 e:data e1:obj 
	this.data = loadBol && typeof data_info.data !== 'undefined' ?  data_info.data:function(e){return null;};         //传递参数为方法体 返回json类型数据e:obj
	this.loadHtml = loadBol && typeof data_info.loadHtml !== 'undefined' ?  data_info.loadHtml: ""; 
	this.loadFun = loadBol && typeof data_info.loadFun !== 'undefined' ?  data_info.loadFun: function(e){return e;};  //请求得到数据后的装载方法（将请求所得的数据经过该方法处理后返回一个新结果,默认返回本身） 
	var obj = this;
	this.init = function(){//初始化数据
		obj.returnData = new Array();//只要调用过init方法一次，就将obj.returnData转换为一个数组
		var usersCacheName = "cache_"+obj.url;
		if( $("body").data(usersCacheName) == undefined ) {
			$.Skyedu.WIN.load(obj.loadHtml);//正在加载状态
			$.ajax({type:obj.type,url:obj.url,data:obj.data(obj),async:false,contentType:obj.contentType,
				success:function(data){
					if(data == null){
						//系统异常
						obj.success = false;
						$.Skyedu.WIN.noValue(obj.loadHtml);
					}else{
						data = obj.loadFun(data);
						obj.success = true;
						obj.returnData = data;
						$("body").data(usersCacheName,data);
					}
				},
				error:function(){
					$.Skyedu.WIN.info('初始化数据失败，请按CTRL+F5刷新本页面！');
					obj.success = false;
					obj.returnData = {'errorInfo':"数据请求失败！"};
				}
			});
		}else{obj.returnData=data = obj.loadFun($("body").data(usersCacheName));}
		return obj.returnData;
	};
	if (this.isLoad) {
		this.init();
	}
	
	/**
	 * 解析where条件
	 * 当返回为空的情况则表示没有where条件
	 */
	this.analysisWhere = function(sql){//返回执行条件
		var whe = "";
		sql = $.trim(sql);
		if(sql.indexOf("where")>-1){//表示有where条件
			//解析查询条件
			whe = sql.split("where")[1].replace(/and/g,"\&\&").replace(/or/g,"\|\|").replace(/(\w*)\s*((\>\=)|(\<\=)|\<|>|(=))\s*(?!$)/gi,'this\[\'$1\'\] $2 ').replace(/\s=\s/gi,' == ').replace(/([\S]*)\slike\s*([\S]*)/gi," this\[\'$1\'\]\.indexOf\($2\)>-1");//.replace(/\s{1,}([\S]*)\s{1,}\.index/gi," this\[\'$1\'\]\.index");
			//解析 in
			var inData = whe.match(/\S*\s[not\s]*in\s*\(([\S]*)\)(?!$)/gi);
			$(inData).each(function(){
				var inSql = "";
				var notIn = /^\S*\s*not\sin\s*\S*/;
				var key = "this['"+this.split(" ")[0]+"']";
				var inner = "||";
				if(notIn.test(this)){//not in   not in is and
					inner = "&&";key += "!==";
				}else{key += "==";}
				var strDa = this.substring(this.indexOf("(")+1,this.lastIndexOf(")")).split(",");
				$.each(strDa,function(){inSql += key+this+inner;});
				whe = whe.replace(this,"("+inSql.substring(0, inSql.length-inner.length)+")");
			});
		}
		return whe;
	};
	/**
	 * 解析insert into
	 * 返回执行json对象
	 */
	this.analysisInsert = function(sql){//返回执行json对象
		if(sql.indexOf("insert into")>-1){
			var whe = {};
			var strs = sql.replace("insert into","").replace(/\s*\(\s*/g,"").replace(/\s*\)\s*/g,"").split("values");
			var keys = strs[0].split(",");
			var vals = strs[1].split(",");
			$(keys).each(function(i){
				whe[this] = eval(vals[i]);
			});
			return whe;
		}else{
			return null;
		}
	};
	/**
	 * 解析update set
	 * 返回set执行语句 id=1,name=2,sex=3
	 */
	this.analysisUpdate = function(sql){
		if(sql.indexOf(" set ")>-1){
			var whe = "";
			var strs = sql.split(" set ")[1].split("where")[0];
			//alert(strs)
			whe = strs/*.replace(/\,/g,";")*/.replace(/(\w*)\s*=(?!$)/gi,"this[\'$1\']=")+";";
			return whe;
		}else{
			return "";
		}
	};
	this.search = function(sql){
		if(obj.returnData==null){this.init();}
		var rs = new Array();
		if(sql.indexOf("select")>-1){
			var limit = sql.indexOf("limit");
			if(limit>0){
				var sqls = sql.split("limit");
				sql = sqls[0];
				limit = sqls[1];
			}
			var whe = obj.analysisWhere(sql);
			if(whe.length>0){//表示有where条件
				$(obj.returnData).each(function(){
					if(eval(whe)) rs.push(this);
				});  
			}else {rs = obj.returnData;}
			var countzz = /([\s\S]*)count\(([\s\S]*)/;
			if(countzz.test(sql)){
				rs = rs.length;
			}else if(limit.length>0){
				var fors = limit.split(",");
				var endfors = $.trim(fors[1])*1;
				if($.trim(fors[0])*1>rs.length){
					rs = [];
				}else if($.trim(fors[1])*1>rs.length){
					endfors = rs.length;
				}
				if(rs.length>0){
					var rss = new Array();
					for(var i = $.trim(fors[0])*1 ; i < endfors ; i++){
						rss.push(rs[i]);
					}
					rs = rss;
				}
			}
		}
		obj.returns(rs,obj);//回调函数
		return rs;
	};
	this.del = function(sql){
		if(obj.returnData==null){this.init();}
		var rs = new Array();//影响数据数
		if(sql.indexOf("delete")>-1){
			var whe = obj.analysisWhere(sql);
			//alert(whe);
			if(whe.length>0){//表示有where条件
				$(obj.returnData).each(function(i){
					if(eval(whe)){
						rs.push(i);
					}
				});  
				$(rs).each(function(i){
					obj.returnData.splice(this-i,1);
				});
			}else {rs.length = obj.returnData.length;obj.returnData = new Array();}//表示删除所有数据
		}
		obj.returns(rs.length,obj);//回调函数
		return rs.length;
	};
	this.insert = function(sql){
		if(sql.indexOf("insert")>-1) obj.returnData.push(obj.analysisInsert(sql));
		return 1;
	};
	this.update = function(sql){//没有where条件不执行
		var rs = 0;
		if(sql.indexOf("update")>-1){
			var whe = obj.analysisWhere(sql);
			if(whe.length>0){//表示有where条件
				$(obj.returnData).each(function(){
					if(eval(whe)) {
						eval(obj.analysisUpdate(sql));
						rs ++;
					};
				});  
			}
		}
		return rs;
	};
};
$(function(){
//	var s = new Search({isLoad:false});
//	s.returnData = [{id:1,name:'name',sex:1},{id:2,name:'name',sex:1},{id:3,name:'name3',sex:2}];
//	s.insert("insert into (id,name,sex) values (55,'新增元素','男')");
//	s.update("update set sex='女' where id<55");
//	s.del("delete where id=1 or sex='男' or id=2");
//	$(s.returnData).each(function(){
//		alert("{id:"+this.id+",sex:"+this.sex+",name="+this.name+"}");
//	});
//	rs = s.analysisInsert("insert into (id,name,sex) values (1,'name111','sex111')");
//	alert("id:"+rs.id+",name:"+rs.name+",sex:"+rs.sex);
});

