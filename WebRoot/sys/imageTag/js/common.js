function getRootPath() {
	var pathName = window.location.pathname.substring(1);
	var webName = pathName == '' ? '' : pathName.substring(0, pathName
			.indexOf('/'));
	return window.location.protocol + '//' + window.location.host + '/'
			+ webName;
}

function isEmpty(obj){
	return obj==null || obj==undefined || obj=="" || obj=='undefined' || obj=='null';
}

function isNotEmpty(obj){
	return !isEmpty(obj);
}

function trimBlock(ojb){
	if(isEmpty(ojb))
		return "";
	return ojb.trim();
}

function addLoading(str){
	var html = '<div class="loading">'+ 
	 '    <div class="element">'+ 
	 '        <div class="loading3">'+ 
	 '            <div>'+ str +'</div>'+ 
	 '            <div></div>'+ 
	 '            <div></div>'+ 
	 '        </div>'+ 
	 '    </div>'+ 
	 '</div>';
	 $('body').append(html);
}

function removeLoading(){
	$('.loading').remove();
}

function addCheck(check, str){
	if (check) 
		return str;
	return "";
}

function ajaxCheck(url, dataVal){
	var result;
	$.ajax({
		type : "post",
		url : url,
		async : false, //true为异步加载，false为同步加载，参数为空时，默认异步加载
		cache : false,
		data : dataVal,
		dataType : "json",
		timeout : 5000,
		success : function(data) {
			result = data.success;
			if (!result) {
				layer.msg(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("发送请求失败，请稍后再试。");
			result = false;
		}
	});
	return result;
}