
/**
 * 系统工具
 */
function MisUtil(){
	this.sourceArray=[{id:1,name:"蓝天老生"},
	   			{id:2,name:"大班教师转介绍"},
				{id:3,name:"前台转介绍"},
				{id:4,name:"1对1学员转介绍"},
				{id:5,name:"电话陌拜"},
				{id:6,name:"户外摆展陌拜"},
				{id:7,name:"上门陌拜"},
				{id:8,name:"校区电话咨询"},
				{id:9,name:"521热线咨询"},
				{id:10,name:"短信"},
				{id:11,name:"传单"},
				{id:12,name:"平面广告"},
				{id:13,name:"线下测评试听等活动"},
				{id:14,name:"线上信息收集活动"},
				{id:15,name:"ＱＱ群推广"},
				{id:16,name:"网站推广"},
				{id:17,name:"微信推广"},
				{id:18,name:"论坛互动收集资料"},
				{id:19,name:"路过"},
				{id:21,name:"蓝天口碑"},
				{id:22,name:"关系户"},
				{id:20,name:"其他"}];
	var MisUtilObj = this;
	this.select = {
		dept: function(e){
			$.ajaxSettings.async = false;
			$.getJSON(PATH+"/query/getDept",function(data){
				$(e).append("<option value=\"\">--选择部门--</option>");
				$.each(data,function(i){
					$(e).append("<option value="+this.DeptId+">"+this.DeptName+"</option>");
				});
			});
		},
		grade: function(e){
			$.getJSON(PATH+"/query/getGrade",function(data){
				$(e).append("<option value=\"\">--选择年级--</option>");
				$.each(data,function(i){
					$(e).append("<option value="+this.GradeId+">"+this.GradeName+"</option>");
				});
			});
		},
		subject: function(e,e1,def){
			$.getJSON(PATH + "/query/getSubject", function(data){
				if(e1!=1)
					$(e).append("<option value=\"\">--选择科目--</option>");
				$.each(data, function(i){
					$(e).append("<option value=" + this.SubjectId + " "+(def==this.SubjectId? "selected=\"selected\"":"")+">" + this.SubjectName + "</option>");
				});
			});
		},
		campus: function(e,e1,def){
			$.ajaxSettings.async = false;
			$.getJSON(PATH + "/query/getCampus", function(data){
				if(e1!=1)
					$(e).append("<option value=\"\">--选择校区--</option>");
				$.each(data, function(i){
					$(e).append("<option value=" + this.CampusId  + " "+(def==this.CampusId? "selected=\"selected\"":"") + ">" + this.CampusName + "</option>");
				});
			});
		},
		term: function(e){
			$.getJSON(PATH + "/query/getTerm", function(data){
				$(e).append("<option value=\"\">--选择学期--</option>");
				$.each(data, function(i){
					$(e).append("<option value=" + this.TermId + ">" + this.TermName + "</option>");
				});
			});
		},
		auditType: function(e){
			$.getJSON(PATH + "/audit/searchTypeList", function(data){
				$(e).append("<option value=\"\">--选择申请类型--</option>");
				$.each(data.list, function(i){
					$(e).append("<option value=\"" + this.TypeId + "\" "+(this.Status!=1?"disabled=\"disabled\" style=\"background-color: #D8D8D8;\"":"")+">" + this.TypeName + "</option>");
				});
			});
		},

		//成绩类别
		testType : function(e, def, flag){
			if(!flag)
				$(e).append("<option value=\"\">--选择成绩类别--</option>");
			else
				$.ajaxSettings.async = false;
			$.getJSON(PATH+"/query/getTestType",function(data){
				$.ajaxSettings.async = true;
				$.each(data,function(i){
					$(e).append("<option value="+this.ttId+" "+(this.ttId==def?" selected":"")+">"+this.ttDate+this.ttName+"</option>");
				});
			});
		},
		/**
		 * 校区、课室联动下拉
		 * @param campus 校区元素ID
		 * @param classroom 课室元素ID
		 */
		campusClassroom: function(campus,classroom){
			this.campus(campus);
			$(classroom).html("<option value=\"\">--选择课室--</option>");
			$(campus).change(function(){
				$(classroom).html("<option value=\"\">--选择课室--</option>");
				$.getJSON(PATH + "/query/getClassroom?queryVO.campusId="+$(this).val(), function(data){
					$.each(data, function(i){
						$(classroom).append("<option value=" + this.ClassroomId + ">" + this.ClassroomName + "</option>");
					});
				});
			});
		},
		/**
		 * 部门、职位联动下拉
		 * @param dept 部门元素ID
		 * @param position 职位元素ID
		 * 
		 * 动态赋值调用方式：
		 * var test = new misUtil.select.deptPosition('#deptSelect',"#positionSelect");
		 * test.value(1,1);
		 */
		deptPosition: function(dept,position){
			//初始化
			var obj = this;
			misUtil.select.dept(dept);
			$(position).html("<option value=\"\">--选择职位--</option>");
			//根据部门获得职位
			this.position = function(deptId){
				$(position).html("<option value=\"\">--选择职位--</option>");
				$.ajaxSettings.async = false;
				$.getJSON(PATH + "/query/getPositionByDept?queryVO.deptId="+deptId, function(data){
					$.each(data, function(i){
						$(position).append("<option value=" + this.PositionId + ">" + this.PositionName + "</option>");
					});
				});
			},
			$(dept).bind("change",function(){
				obj.position($(this).val());
			});
			/**
			 * 动态赋值
			 * @param deptId 部门Id，必填
			 * @param positionId 职位Id，可填
			 */
			this.value = function(deptId,positionId){
				if(deptId!=undefined) $(dept).val(deptId); this.position(deptId);
				if(positionId!=undefined) $(position).val(positionId);
			};
		},
		/**
		 * 部门、员工联动下拉
		 * @param dept 部门元素ID
		 * @param position 员工元素ID
		 */
		deptEmployee: function(dept,employee){
			//初始化
			var obj = this;
			misUtil.select.dept(dept);
			$(employee).html("<option value=\"\">--选择员工--</option>");
			this.employee = function(deptId){
				$(employee).html("<option value=\"\">--选择员工--</option>");
				$.ajaxSettings.async = false;
				$.getJSON(PATH + "/query/getEmployeeByDeptId?queryVO.deptId="+deptId, function(data){
					$.each(data, function(i){
						$(employee).append("<option value=" + this.UserId + ">" + this.UserName + "</option>");
					});
				});
			},
			$(dept).bind("change",function(){
				obj.employee($(this).val());
			});
			/**
			 * 动态赋值
			 * @param deptId 部门Id，必填
			 * @param employeeId 员工Id，可填
			 * @param positionId 职位id
			 */
			this.value = function(deptId,employeeId,positionId){
				//默认数据设置
				if(deptId==undefined && positionId!=undefined){
					//根据职位获得部门
					if(positionId != undefined){
						$.ajax({url:PATH+'/query/getPositionByPositionId?queryVO.positionId='+positionId,async:false,success:function(data){if(null!=data && data.DeptId!=undefined) deptId=data.DeptId;}});
					}
				}
				//默认数据设置end
				
				if(deptId!=undefined) $(dept).val(deptId); this.employee(deptId);
				if(employeeId!=undefined) $(employee).val(employeeId);
			};
		},
		employeeStatus:function(e,defaultValue){
			$(e).append("<option value=\"\">--选择状态--</option><option value=1>在职</option><option value=-1>离职</option>");
			if(defaultValue!=undefined){
				$(e).val(defaultValue);
			}
		},
		recordType: function(e){
			$(e).append("<option value=\"\">--选择交易类型--</option><option value=\"1\">档案费</option><option value=\"2\">消费</option><option value=\"3\">缴费</option><option value=\"4\">退费</option><option value=\"5\">转账</option><option value=\"6\">单据作废/撤销交易</option><option value=\"7\">手续费</option>");
		},
		payType: function(e){
			$(e).append("<option value=\"\">--选择缴费类型--</option><option value=\"1\">整交</option><option value=\"2\">定金</option><option value=\"3\">尾款</option><option value=\"4\">档案费</option><option value=\"5\">续费</option>");
		},
		//教师短信处理状态
		teaMsgStatus: function(e){
			$(e).append("<option value=\"\">--选择处理状态--</option><option value=\"1\">未处理</option><option value=\"2\">已处理</option>");
		},
		//考勤状态
		attendanceType: function(e){
			$(e).append("<option value=\"\">--选择考勤状态--</option><option value=-1>未考勤</option><option value=1>正常</option><option value=2>缺课</option><option value=3>补课</option>");
		},
		user: function(e){
			var searchObj = new SearchInput({info:'请输入学生姓名',url:PATH+'/query/getStudent',pageName : 'studentVO.page',maxResultsName : 'studentVO.maxResults', load:false,
				data : function(e,e1){return {"studentVO.stuName":e1.searchKey,"studentVO.Status":1};},//传递参数为方法体 返回json类型数据 e:分页器本身对象e1:搜索器本身
				returns : function(e,e1){//回调函数  e:data e1:分页器本身对象 
					 e1.pageCount = e.count;
					 var html = "";
					 $.each(e.studentList, function(i){
						html += "<li id=\""+this.StuFileNumber+"\" _stuId=\""+this.StuId+"\">"+this.StuName+" ("+this.StuFileNumber+")</li>";
					 });
					 $('#'+e1.pageListHtmlId).html(html);
				}
			});
			return searchObj.load(e);
		},
		teacher: function(e){
			var searchObj = new SearchInput({info:'请输入老师姓名',url:PATH+'/employee/queryTeacher',pageName : 'employeeVO.page',maxResultsName : 'employeeVO.maxResults', load:false,
				data : function(e,e1){return {"employeeVO.teacherName":e1.searchKey};},//传递参数为方法体 返回json类型数据 e:分页器本身对象e1:搜索器本身
				returns : function(e,e1){//回调函数  e:data e1:分页器本身对象 
					 e1.pageCount = e.count;
					 var html = "";
					 $.each(e.teacherList, function(i){
						html += "<li id=\""+this.UserId+"\" "+(this.Subjects!=null && this.Subjects!="null"?" subjects=\""+this.Subjects+"\"":"")+">"+this.UserName+(this.EnglishName==""?"":" ("+this.EnglishName+")")+"</li>";
					 });
					 $('#'+e1.pageListHtmlId).html(html);
				}
			});
			return searchObj.load(e);
		},
		client: function(e){
			var searchObj = new SearchInput({info:'请输入客户姓名',url:PATH+'/query/getCustomer',pageName : 'clientVO.page',maxResultsName : 'clientVO.maxResults', load:false,
				data : function(e,e1){return {"clientVO.customerName":e1.searchKey,"clientVO.isPage":true};},//传递参数为方法体 返回json类型数据 e:分页器本身对象e1:搜索器本身
				returns : function(e,e1){//回调函数  e:data e1:分页器本身对象 
					 e1.pageCount = e.count;
					 var html = "";
					 $.each(e.list, function(){
						html += "<li id=\""+this.CustomerId+"\">"+this.CustomerName+"</li>";
					 });
					 $('#'+e1.pageListHtmlId).html(html);
				}
			});
			return searchObj.load(e);
		},
		source: function(e,def){
			$(e).append("<option value=\"\">--选择来源--</option>");
			$.each(MisUtilObj.sourceArray, function(i){
				$(e).append("<option value=" + this.id +(def==this.id? " selected=\"selected\"":"")+">" + this.name + "</option>");
			});
		}
		//select end
	};
	this.getSource = function(e){
		var ret = "未知";
		$(MisUtilObj.sourceArray).each(function(){
			if(e==this.id){
				ret = this.name;
				return false;
			}
		});
		return ret;
	};
}
var misUtil = new MisUtil();
//使用方式

//misUtil.select.subject('#subjectSelectHtmlId');//填充科目下拉option选项