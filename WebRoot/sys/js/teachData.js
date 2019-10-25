/*
* @Author: Hlin
* @Date:   2018-01-08 11:17:12
* @Last Modified by:   Hlin
* @Last Modified time: 2018-09-18 09:53:15
*/
// 鼠标覆盖显示一个课次的所有状态
$(".teach_data").on('mouseover', 'tr[catgory="rowspan"] td:first-child', function(event) {
    var trIndex = $(this).parent().attr("index");
    $(".teach_data tbody tr[index='"+trIndex+"']").addClass('active');
});
$(".teach_data").on('mouseleave', 'tr[catgory="rowspan"] td:first-child', function(event) {
    var trIndex = $(this).parent().attr("index");
    $(".teach_data tbody tr[index='"+trIndex+"']").removeClass('active');
});

// 表头浮动
var headerDom = $(".teach_table thead")[0].outerHTML;
$(".teach_data").append('<table class="wa_table teach_table_header hide">'+headerDom+'</table>')
$(window).scroll(function(event){
    if($(window).scrollTop() > $(".teach_table")[0].offsetTop){
        $(".teach_table_header").removeClass('hide');
    }else{
        $(".teach_table_header").addClass('hide');
    }
});

var period = $(".filter_list input[name='period']:checked").val();
var subject = $(".filter_list input[name='subject']:checked").val();
dataRender(subject,period);// 初始化表格
$(".filter_list").on('change', ':radio', function(event) {
    period = $(".filter_list input[name='period']:checked").val();
    subject = $(".filter_list input[name='subject']:checked").val();
    dataRender(subject,period);
});

// 表格数据渲染
function dataRender(subject,period){
    $(".teach_table tbody").html('');
    $.ajax({
         url: getRootPath() + '/statistics/getLessonUploadStatusV2.jhtml',
//        url: '/task/statistics/getLessonUploadStatusV2.jhtml',
        // type: 'GET',
        type: 'POST',
        dataType: 'JSON',
        data:{
            'subject': subject,
            'period': period
        },
        success: function(data){
            var result = sortListData(data);
            var tdData = '',trData = '',tableData = '';
            $.each(result, function(index,trList) {
                $.each(trList, function(index,tr) {
                    $.each(tr.status, function(index,td) {
                        tdData += '<td state="'+(td ? td.caseStatus : 2)+'" type="case"></td>' +
                                  '<td state="'+(td ? td.course : 2)+'" type="ppt"></td>' +
                                  '<td state="'+(td ? td.lesson : 2)+'"></td>' +
                                  '<td state="'+(td ? td.memoir : 2)+'"></td>';
                    });
                    if (index == 0) {
                        trData += '<tr catgory="rowspan" index="'+tr.sortNo+'">'+
                            '<td rowspan="'+trList.length+'">'+tr.sortNo+'</td>' +
                            '<td>'+tr.ecName+'</td>'+
                            tdData +
                        '</tr>';
                    }else if(index == trList.length - 1){
                        trData += '<tr class="bottom_tr" index="'+trList[index].sortNo+'">'+
                            '<td>'+tr.ecName+'</td>'+
                            tdData +
                        '</tr>';
                    }
                    else{
                        trData += '<tr index="'+trList[index].sortNo+'">'+
                            '<td>'+tr.ecName+'</td>'+
                            tdData +
                        '</tr>';
                    }
                    tdData = '';
                });
            });
            $(".teach_table tbody").html(trData);
            trData = '';
        },
        error: function(error) {
            layer.alert("服务器异常！",{
                title: "提示", //标题
                skin: "layer-sky-skin", //皮肤
                btn: ['确定'], // 按钮
                closeBtn: 0, //右上角的关闭按钮，0为不显示，1为显示
                btn1: function(index, dom){ //index是该弹窗的索引
                    layer.close(index); //关闭弹窗
                }
            })
        }
    })

    function sortListData(data){
        var result = {};
        var newStatus = new Array(12).join(',').split(',');
        $.each(data, function(index,el1) {
            var sortNo = el1.sortNo;
            $.each(el1.status, function(index, el2) {
                newStatus[Number(el2.egCode.trim().split('A')[1]) - 1] = el2;
            });
            el1.status = newStatus;
            if (result.hasOwnProperty(sortNo)) {
                result[sortNo].push(el1)
            }else{
                result[sortNo] = [];
                result[sortNo].push(el1)
            }
            newStatus = new Array(12).join(',').split(',');
        });
        return result;
    }
}