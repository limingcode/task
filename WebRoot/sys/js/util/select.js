/**
 * 获得下拉列表
 * @version: 1.0
 * @author: Jinmingming 305281722@qq.com
 * @param htmlName:select标签的name或者id
 * 
 * 使用方法：$.Select.(".x").getDept();或者$("#x").Select.getDept();
 */

$.Select = {};

//部门
$.Select.getDept = function(htmlName){
	$.getJSON(PATH+"/query/getDept",function(data){
		$(htmlName).append("<option value=\"\">--选择部门--</option>");
		$.each(data,function(i){
			$(htmlName).append("<option value="+this.DeptId+">"+this.DeptName+"</option>");
		});
	});
};

//年级
$.Select.getGrade = function(htmlName){
	$.getJSON(PATH+"/query/getGrade",function(data){
		$(htmlName).append("<option value=\"\">--选择年级--</option>");
		$.each(data,function(i){
			$(htmlName).append("<option value="+this.GradeId+">"+this.GradeName+"</option>");
		});
	});
};

//科目
$.Select.getSubject = function(htmlName){
	$.getJSON(PATH+"/query/getSubject",function(data){
		$(htmlName).append("<option value=\"\">--选择科目--</option>");
		$.each(data,function(i){
			$(htmlName).append("<option value="+this.SubjectId+">"+this.SubjectName+"</option>");
		});
	});
};
	
