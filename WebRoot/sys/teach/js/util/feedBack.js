var tota;
var curr;

$('.firstPage').on('click', function(){
	if(curr == 1 || tota == 0)
		return false;
	getPage(1);
});

$('.prevPage').on('click', function(){
	if(curr == 1 || tota == 0)
		return false;
	var currPage = $('.pageNum.active').html();
	getPage(--currPage);
});

$('.nextPage').on('click', function(){
	if(curr == tota || tota == 0)
		return false;
	var currPage = $('.pageNum.active').html();
	getPage(++currPage);
});

$('.lastPage').on('click', function(){
	if(curr == tota || tota == 0)
		return false;
	getPage(tota);
});

$('.btnSure').on('click', function(){
	goPage();
});

$(document).keypress(function(e) {  
    // 回车键事件  
	if(e.which == 13) {  
		goPage();  
   	}  
}); 

function goPage(){
	var number = $("#number").val();
	if(number > tota){
		layer.msg('共'+ tota +'页');
		return false;
	}
	if(number == curr){
		layer.msg('当前为第'+ curr +'页');
		return false;			
	}
	if(number < 1){
		layer.msg('请输入正确的页码');
		return false;
	}
	getPage(number);
}

function write(pageData){
	 tota = pageData.totalPage;
	 curr = pageData.currPage;
	 $('.pageDiv').html('');
	 if(tota == 0){
		 $('.notContent').show();
		 $('.page').addClass('hide');
	 }else{
		 $('.notContent').hide();
		 $('.page').removeClass('hide');
		 var list = pageData.dataList;
		 list.forEach(function(data){ 
			 var html = '<li class="msgCont">'+
			            '<i>'+data.name+'</i>'+
			            '<i>'+data.createDate+'</i>'+
			            '<i>'+data.subject+'</i>'+
			            '<i>'+data.graName+'</i>'+
			            '<i>'+data.perName+'</i>'+
			            '<i>'+data.catName+'</i>'+
			            '<i>第'+data.sortNo+'次</i>';
			 if(data.comment == '1'){
				 html += '<i>赞</i>';
			 }else{
				 html += '<i>不赞</i>';
			 }
			     html += '<i title="'+ data.content +'">'+(isEmpty(data.content) ? "&nbsp;" : data.content)+'</i>'+
		             '</li>';
			 $('.pageDiv').append(html);
		 });
	 }
}

function removeData() {
	$('.pageDiv').html('');
}

function toolBarCtrl(currPage, totalPage, hasPrePage, hasNextPage){
	$('.totalPage').html(totalPage);
	if(currPage == 1){
		$('.firstPage').addClass('disabled');
	}else{
		$('.firstPage').removeClass('disabled');
	}
	
	if(currPage == totalPage){
		$('.lastPage').addClass('disabled');
	}else{
		$('.lastPage').removeClass('disabled');
	}
	
	if(hasPrePage){
		$('.prevPage').removeClass('disabled');
	}else{
		$('.prevPage').addClass('disabled');
	}
	
	if(hasNextPage){
		$('.nextPage').removeClass('disabled');
	}else{
		$('.nextPage').addClass('disabled');
	}
	
	var divHtml = "";
	var count = 0;
	for (var i = 2; i > 0; i--) {
		if((currPage - i) > 0){
			divHtml += '<li class="pageNum" style="">'+ (currPage - i) +'</li>';
			count++;
		}
	}
	if(currPage <= totalPage){
		divHtml += '<li class="pageNum active" style="">'+ currPage++ +'</li>';
		count++;
	}
	
	while (currPage <= totalPage && count < 5) {
		divHtml += '<li class="pageNum" style="">'+ currPage++ +'</li>';
		count++;
	}
	
	if(count < 5 && count < totalPage){
		for(i = 3; count < 5; i++){
			divHtml = '<li class="pageNum" style="">'+ (curr-i) +'</li>' + divHtml;
			count++;
		}
	}
	
	$('.pageObj').html(divHtml);
	
	$('.pageNum').on('click', function(){
 		var page = $(this).html();
 		getPage(page);
	});
}

$('.lessons,.semester,.grade,.arrangement').on('change', function(){
	var subject = $('#lessons').val();  //科目
	var period = $('#semester').val();  //学期
	var grade = $('#grade').val();  //年级
	var cate = $('#arrangement').val(); //层次
	if(isNotEmpty(lessons) && isNotEmpty(semester) && isNotEmpty(grade) && isNotEmpty(arrangement)){
		$.ajax({
	        url: getRootPath() + "/prepareLesson/getLesson.jhtml",
	        type: 'POST',
	        data: {'subject':subject,'grade':grade,'cate':cate,'period':period},
	        dataType: "json",
	        success: function (data) {
	        	var htmlString = '<option style="color: #ccc;" value="">--请选择课次--</option>';
	        	data.forEach(function(lesson){  
	        		htmlString += "<option value="+ lesson.iD +">第"+ lesson.sortNo +"次</option>";
	    		});
	        	$(".classTime").html(htmlString);
	        }
	    });
	}
});

$('.lessons,.semester,.grade,.arrangement,.classTime,.state,.name').on('change', function(){
	getPage(1);
});

