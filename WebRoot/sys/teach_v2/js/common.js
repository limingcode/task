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

function toolBarCtrl(data){
	if (data.totalPage == 0) {
		return '';
	}
	var toolTotalNum = 7;   //分页栏显示的按扭个数
	var currPage = data.currPage;
	var totalPage = data.totalPage;
	var html = "";
	var toolNum = 0;
	
	for (var i = 3; i > 0; i--) {
		if ((currPage-i) > 0) {
			html += '<li class="pageNum">'+ (currPage - i) +'</li>';
			toolNum++;
		}
	}
	
	html += '<li class="current_page">'+ currPage++ +'</li>';
	toolNum++;
	
	while (currPage <= totalPage && toolNum < toolTotalNum) {
		html += '<li class="pageNum">'+ currPage++ +'</li>';
		toolNum++;
	}
	
	if (currPage < totalPage) {
		html += '<li class="ellipsis">....</li>';
	}
	
	if (toolNum < toolTotalNum) {
		for(i = 3; toolNum < toolTotalNum && (data.currPage-i) > 0; i++){
			html = '<li class="pageNum">'+ (data.currPage-i) +'</li>' + html;
			toolNum++;
        }
	}
	
	if (data.hasPrePage) {
		if (data.currPage > 4) { 
			html = '<li class="ellipsis">....</li>' + html;
		}
		html = '<li class="first_page">&nbsp;</li><li class="prev_page">&nbsp;</li>' + html;
	}
	
	if (data.hasNextPage) {
		html += '<li class="next_page">&nbsp;</li><li class="last_page">&nbsp;</li>';
	}
	
	html += '<li class="all_page">共<span>'+ totalPage +'</span>页</li> '+
		    '<li class="turn_page">到<input type="number">页</li>'+
	        '<li class="go_btn">GO</li>';
	return html;
}

$('.pagination').on('click', '.pageNum', function(){
	var pageNum = trimBlock($(this).html());
	getPage(pageNum);
});

$('.pagination').on('click', '.first_page', function(){
	getPage(1);
});

$('.pagination').on('click', '.prev_page', function(){
	var curr = $(this).siblings('.current_page').html();
	getPage(--curr);
});

$('.pagination').on('click', '.last_page', function(){
	var allPage = $(this).siblings('.all_page').children('span').html();
	getPage(allPage);
});

$('.pagination').on('click', '.next_page', function(){
	var curr = $(this).siblings('.current_page').html();
	getPage(++curr);
});

function goPage(){
	var number;
	var allPage;
	var currPage;
	number = Number($('.pagination').find('.turn_page').children('input').val());
	allPage = Number($('.pagination').find('.all_page').children('span').html());
	currPage = Number($('.pagination').find('.current_page').html());
	if (number > allPage) {
		layer.msg('共'+ allPage +'页');
		return false;
	}
	if(number == currPage){
        layer.msg('当前为第'+ currPage +'页');
        return false;
    }
    if(number < 1){
        layer.msg('请输入正确的页码');
        return false;
    }
	getPage(number);
}

$('.pagination').on('click', '.go_btn', function(){
	goPage();
});

$(document).keypress(function(e) {
    // 回车键事件
    if(e.which == 13) {
    	if ($('input:focus').attr('type') == 'number') {  //页面跳转
			goPage();
			return false;
		}
//		getPage(1);
//		return false;
    }
});
