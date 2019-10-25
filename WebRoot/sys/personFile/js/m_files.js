/*
* @Author: Hlin
* @Date:   2017-12-29 09:52:21
* @Last Modified by:   Hlin
* @Last Modified time: 2018-01-08 17:33:02
*/
// 上传文件
$(".upload_btn").on('change', 'input[type="file"]', function(event) {
    var file = this.files[0]; //文件对象
    if (!file) { //没有选择文件则返回
        return;
    }
    if(file.size > 500*1024*1024 - $("#dirSize").val()){ //文件大小检测
        layer.msg('空间不足！',{time:1000});
        return;
    }
    var fileName = file.name; //文件名
    var fileType = fileName.substring(fileName.lastIndexOf(".")+1); //文件类型
    if (!(/\.(zip|png|mp4|jpg|gif|mp3|ogg|ppt)$/).test(fileName)) { //格式检测
        layer.msg('不支持该文件格式！',{time:1000});
        return;
    }
    var formData = new FormData();
    formData.append('uploadFile', file);
    formData.append('oaId', $("#oaId").val());
    var layerIndex  = layer.load(2,{
        content: '<span class="uploadProcess"></span>'
    });
    $.ajax({
        url : getRootPath() + '/personFile/uploadFile.jhtml',
        type : "POST",
        data : formData,
        dataType:'json',
        processData : false,
        contentType : false,
        //拿到XMLHttpRequest对象，增加 progress 事件绑定，然后再返回交给ajax使用
        xhr : function(){
            var xhr = $.ajaxSettings.xhr();
            if (onprogress && xhr.upload) {
                xhr.upload.addEventListener("progress" , onprogress, false);
            }
            return xhr;
        },
        success : function(data){
            layer.close(layerIndex); //关闭加载弹窗
            layer.msg(data.message,{time:1000});
            if (data.success) {
                location.reload();
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.close(layerIndex); //关闭加载弹窗
            layer.msg('上传失败，网络异常！',{time:1000});
        }
    });

    $('input[type="file"]').val('');
    
    //进度条
    function onprogress(evt) {
        if (evt.lengthComputable) {
            // 上传进度
            setTimeout(function(){
            	var uploadProcess = ((evt.loaded / evt.total) * 100).toFixed(2) + "%";
                $('.uploadProcess').html(uploadProcess)
            }, 20);

        }
    }
});

// 修改文件名
$(".files_table").on('click', '.revise', function(event) {
    var obj = $(this).parents('.file_name');
    var fileName = obj.find('.file_name_').html();// 获取文件名
    var fileId = $(this).parents('tr').attr('fileid') //获取文件id
    layer.prompt({
        title: '请输入文件名',
        formType: 3,
        value: fileName.substring(0,fileName.lastIndexOf(".")), // 初始文件名（无后缀）
    },function(text, index){
    	if (isEmpty(text.trim())) {
			layer.msg('文件名不能为空格！');
			return false;
		}
    	
        // 后台事件提交
        $.ajax({
            url: getRootPath() + '/personFile/reNameFile.jhtml',
            type: 'POST',
            dataType: 'json',
            data: {
                "id": fileId,
                "fileName": text
            },
            success: function(res){
                if (res.success) {
                    obj.find('.file_name_').html(text + fileName.substring(fileName.lastIndexOf(".")));
                    obj.parent().find('.download').attr('href', res.url);
                }
                layer.msg(res.message,{time:1000});
                layer.close(index); // 关闭弹窗
            },
            error: function(error){
                layer.msg('删除失败，网络异常!',{time:1000});
                layer.close(index); // 关闭弹窗
            }
        })
    });
});

// 删除文件
//update 黄德彬
function deleteFile(id){
    layer.confirm('是否删除文件？',function(pass, index){
        // 后台事件提交
        $.ajax({
            url: getRootPath() + '/personFile/deleteFile.jhtml',
            type: 'POST',
            dataType: 'json',
            data: {
                id: id
            },
            success: function(res){
                location.reload();
                layer.close(index); // 关闭弹窗
            },
            error: function(error){
                layer.msg('删除失败，网络异常!',{time:1000});
                layer.close(index); // 关闭弹窗
            }
        })
    });
}

//--------------------------------------------------------------------------------------------------------------------
// author 黄德彬
$(function(){
    getPage(1);
});

$('.search_cnt').on('keyup', function(){
    getPage(1);
});

$('.search_btn').on('click', function(){
    getPage(1);
});

function getPage(currPage){
    var oaId = $("#oaId").val();
    var search = $('.search_cnt').val();
    $.ajax({
        url: getRootPath() + '/personFile/getPersonFileList.jhtml',
        type: 'POST',
        dataType: 'json',
        data: {
            oaId: oaId,
            currPage: currPage,
            search: search,
            pageSize: 10
        },
        success: function(data){
            if (data.dataList.length == 0) {
                $('.addTr').remove();
                $('.pagination').hide();
            } else {
                $('.pagination').show();
                write(data.dataList);
                toolBarCtrl(currPage, data.totalPage, data.hasPrePage, data.hasNextPage);
            }
//            layer.close(index); // 关闭弹窗
        },
        error: function(error){
//            layer.close(index); // 关闭弹窗
        }
    })
}

function write(data){
    var html = '';
    $('.addTr').remove();
    $.each(data, function(i, elem) {
        html += '<tr class="addTr" fileid="'+ elem.id +'">'+
                 '    <td class="file_name" type="'+ getFileType(elem.fileType, elem.fileName) +'">'+
                 '      <span class="file_name_">'+ elem.fileName +'</span>'+
                 '      <span class="mark">';
        if (elem.fileType != 1) {
            html += '<i class="revise" title="重命名" href=""></i>';
        }
        html +=  '          <i class="delete" title="删除" onclick="deleteFile('+ elem.id +')"></i>'+
                 '      </span>'+
                 '    </td>'+
                 '    <td>'+ ((elem.fileSize/1024/1024).toFixed(2) == 0.00 ? 0.01 : (elem.fileSize/1024/1024).toFixed(2)) +'M</td>'+
                 '    <td>'+ elem.uploadTime +'</td>'+
                 '    <td><a class="download" download="" href="'+ elem.filePath +'"></a></td>'+
                 '</tr>'
    });
    $('.files_table').append(html);
}


var curr;
var tota;
function toolBarCtrl(currPage, totalPage, hasPrePage, hasNextPage){
    curr = currPage;
    tota = totalPage;
    var perHtml = "";
    if(currPage != 1){
        perHtml += '<li class="first_page">&nbsp;</li><li class="prev_page">&nbsp;</li>';
    }

    var divHtml = "";
    var count = 0;
    for (var i = 2; i > 0; i--) {
        if((currPage - i) > 0){
            divHtml += '<li class="pageNum">'+ (currPage - i) +'</li>';
            count++;
        }
    }
    if(currPage <= totalPage){
        divHtml += '<li class="current_page">'+ currPage++ +'</li>';
        count++;
    }

    while (currPage <= totalPage && count < 5) {
        divHtml += '<li class="pageNum">'+ currPage++ +'</li>';
        count++;
    }

    if(count < 5 && count < totalPage){
        for(i = 3; count < 5 && (curr-i) > 0; i++){
            divHtml = '<li class="pageNum">'+ (curr-i) +'</li>' + divHtml;
            count++;
        }
    }
    if(curr != totalPage){
        divHtml += '<li class="next_page">&nbsp;</li><li class="last_page">&nbsp;</li>';
    }

    divHtml += '<li class="all_page">共'+ totalPage +'页</li><li class="turn_page">到<input type="number" id="number" value="1"/>页</li><li class="go_btn">GO</li>'

    $('.pagination').children('ul').html(perHtml + divHtml);

    $('.pageNum').on('click', function(){
        var page = $(this).html();
        getPage(page);
    });

    $('.first_page').on('click', function(){
        getPage(1);
    })
    $('.prev_page').on('click', function(){
        getPage(--curr);
    })
    $('.next_page').on('click', function(){
        getPage(++curr);
    })
    $('.last_page').on('click', function(){
        getPage(tota);
    })
    $('.go_btn').click(function(){
        goPage()
    })
}

$(document).keypress(function(e) {
    // 回车键事件
    if(e.which == 13) {
    	if ($('input:focus').attr('id') == 'number') {
			goPage();
		} else if ($('input:focus').hasClass('layui-layer-input')) {
			$('input:focus').parent().siblings('.layui-layer-btn').children('.layui-layer-btn0').click();
		}
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

function getFileType(fileType, fileName){
    if (fileType == 1) {
        return "courseware";
    } else if ((/\.zip$/).test(fileName)) {
        return "zip";
    } else if ((/\.mp3|ogg$/).test(fileName)) {
        return "audio";
    } else if ((/\.mp4$/).test(fileName)){
        return "video";
    } else if ((/\.ppt$/).test(fileName)) {
        return "ppt";
    } else {
        return "img";
    }
}



