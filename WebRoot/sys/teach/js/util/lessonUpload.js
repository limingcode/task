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

//function write(data){
//	
//	$('.titleCont').remove();
//	$('.addSpan').remove();
//	$('.keciCont').remove();
//	
//	tota = data.totalPage;
//	curr = data.currPage;
//	
//	htmlOne = '',htmlTow = '',htmlThree = '',htmlFore = '',htmlSix = '',titleCont = '';
//	for(var i = 1 ; i <= data.maxLesson ; i++){
//		titleCont += '<div class="titleCont" style="float:left;width: 120px">'+
//			'<span style="height: 21px;line-height: 21px;width: 119px;">'+i+'</span>'+
//			'<span style="height: 21px;line-height: 21px;width: 119px;">'+
//			'<i style="border-right: 1px solid #000">课件</i>'+
//			'<i style="border-right: 1px solid #000">说课</i>'+
//			'<i>实录</i>'+
//			'</span>'+
//			'</div>';
//     }
//     $('.keciC').append(titleCont);
//     $('html').width(120*data.maxLesson+410+'px');
//     $.each(data.dataList, function (i, elem) {
//         htmlFive = '';
//         htmlOne += '<span class="addSpan">'+elem.perName+'</span>';
//         htmlTow += '<span class="addSpan">'+elem.esName+'</span>';
//         htmlThree += '<span class="addSpan">'+elem.egName+'</span>';
//         htmlFore += '<span class="addSpan">'+elem.ecName+'</span>';
//         $.each(elem.status, function (j, ele) {
//             htmlFive +='<div style="height: 21px;line-height: 21px;float:left;width: 119px;border-right: 1px solid #000">'+
//                        '<i style="border-right: 1px solid #000"><img style="width: 20px;height: 20px;" src="../sys/teach/images/statistics/statusIcon_'+ ele.course +'.png" alt=""></i>'+
//                        '<i style="border-right: 1px solid #000"><img style="width: 20px;height: 20px;" src="../sys/teach/images/statistics/statusIcon_'+ ele.lesson +'.png" alt=""></i>'+
//                        '<i><img style="width: 20px;height: 20px;" src="../sys/teach/images/statistics/statusIcon_'+ ele.memoir +'.png" alt=""></i>'+
//                        '</div>';
//         });
//         
//         for (var i = 0; i < data.maxLesson-elem.status.length; i++) {
//        	 htmlFive +='<div style="height: 21px;line-height: 21px;float:left;width: 119px;border-right: 1px solid #000">'+
//			             '<i style="border-right: 1px solid #000; width:39px;height:20px;"></i>'+
//			             '<i style="border-right: 1px solid #000; width:39px;height:20px;"></i>'+
//			             '<i></i>'+
//			             '</div>';
//		 }
//         htmlSix += '<div class="keciCont" style="border-bottom: 1px solid #000;overflow:hidden;">'+htmlFive+'</div>';
//     });
//     $('.xueqi').append(htmlOne);
//     $('.kemu').append(htmlTow);
//     $('.nianji').append(htmlThree);
//     $('.cengci').append(htmlFore);
//     $('.keciTow').append(htmlSix);
//     $.each($('.keciCont'), function (i, elem) {
//         var $keciChil = $('.keciC').children('div').length;
//             $(elem).children('div').eq($keciChil-1).css('border-right','none')
//     });
//     var $keciChil = $('.keciC').children('div').length;
//     $('.keciC').children('div').eq($keciChil-1).find('span').css('border-right','none');
//}

function write(data){
	 tota = data.totalPage;
	 curr = data.currPage;

	 $('.addTd').remove();
	 $('.addTr').remove();
	 var titleTr = "";
	 var addTr = '<tr class="addTr">';
	 for (var i = 0; i < data.maxLesson; i++) {
		 titleTr += '<td class="addTd" colspan="3">'+ (i+1) +'</td>';
		 addTr += '<td class="titleTd">课件</td><td class="titleTd">说课</td><td class="titleTd">实录</td>';
	 }
	 addTr += '</tr>';
	 $('.titleTr').append(titleTr);
	 $('table').append(addTr);
	 var dataList = data.dataList;
	 var htmlString = "";
	 $.each(dataList, function(i, elem){
		 htmlString += "<tr class='addTr' border='1'>";
		 htmlString += "<td>"+ elem.perName +"</td>";
		 htmlString += "<td>"+ elem.esName +"</td>";
		 htmlString += "<td>"+ elem.egName +"</td>";
		 htmlString += "<td>"+ elem.ecName +"</td>";
		 $.each(elem.status, function(i, e){
			 htmlString += "<td><img style='width: 20px;height: 20px;' src='../sys/teach/images/statistics/statusIcon_"+ e.course +".png' alt=''></td>";
			 htmlString += "<td><img style='width: 20px;height: 20px;' src='../sys/teach/images/statistics/statusIcon_"+ e.lesson +".png' alt=''></td>";
			 htmlString += "<td><img style='width: 20px;height: 20px;' src='../sys/teach/images/statistics/statusIcon_"+ e.memoir +".png' alt=''></td>";
		 });
		 for (var i = 0; i < data.maxLesson-elem.status.length; i++) {
			 htmlString += "<td></td>";
			 htmlString += "<td></td>";
			 htmlString += "<td></td>";
		 }
    	 htmlString += '</tr>';
	 });
	 $('table').append(htmlString);
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
	getPage(1);
});

