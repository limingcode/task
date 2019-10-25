/*
* @Author: hlin
* @Date:   2017-05-05 10:46:46
*/

// 添加选择题选项
var letterList = ["A","B","C","D","E","F","G","H"];
$(".add_tr").click(function(event) {
    var cLength = $(".editor_opt").length;
    var idIndex = cLength + 3;
    $(".close_option").remove();
    $(this).parent().parent().before("<tr>"+
            "<td class=\"rt_l\">"+
              "<span class=\"close_option\"></span>"+
              "<label><input class=\"option\" type=\"radio\" name=\"option\" /> "+letterList[cLength]+"：</label>"+
            "</td>"+
            "<td class=\"rt_r\">"+
              "<div class=\"editor_box editor_opt\">"+
               "<div id=\""+idIndex+"\" class=\"editor\" style=\"height:105px;\"></div>"+
              "</div>"+
              "<input type=\"text\" class=\"hide_editor\" name=\"option"+(cLength+1)+"\"/>"+
            "</td>"+
          "</tr>");
    eval("var editor" + idIndex + "= new wangEditor('"+idIndex+"');editor"+idIndex+".config.uploadImgUrl = '/upload';editor"+idIndex+".config.menus = ['img'];editor"+idIndex+".create();");
    if (cLength == letterList.length-1) {
        $(this).hide();
    }

    $('body').scrollTop($(document).height());
});

// 删除选项
$(".r_table").on('click', '.close_option', function(event) {
  $(this).parent().parent().prev().find('.rt_l').append('<span class=\"close_option\"></span>')
  $(this).parent().parent().remove();
  $(".add_tr").show();
});

// 给隐藏的编辑器添加内容
$(".r_table").on('input', ".editor", function(event) {
  $(this).parent().parent().parent().find(".hide_editor").val($(this).html());
});