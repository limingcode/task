function getRootPath() {
	var pathName = window.location.pathname.substring(1);
	var webName = pathName == '' ? '' : pathName.substring(0, pathName
			.indexOf('/'));
	return window.location.protocol + '//' + window.location.host + '/'
			+ webName;
}

function isEmpty(obj){
	return obj==null || obj==undefined || obj=="";
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
