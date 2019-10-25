/*
* @Author: Hlin
* @Date:   2017-05-11 09:45:10
*/

// 添加填空
$(".add_tr").click(function(event) {
    var eLength = $(".eanwer").length;
    $(".close_option").remove();
    $(this).parent().parent().before("<tr>"+
            "<td class=\"rt_l\">填空"+(eLength+1)+"：<span class=\"close_option close_eanwer\"></span></td>"+
            "<td class=\"rt_r\">"+
              "<input class=\"eanwer\" type=\"text\" placeholder=\"请填写答案\" name=\"option"+(eLength+1)+"\" />"+
            "</td>"+
          "</tr>");

    $(document).scrollTop($(document).height());
});

// 删除选项
$(".r_table").on('click', '.close_eanwer', function(event) {
  $(this).parent().parent().prev().find('.rt_l').append('<span class=\"close_option close_eanwer\"></span>')
  $(this).parent().parent().remove();
});

