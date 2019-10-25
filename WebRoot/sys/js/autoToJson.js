/*
* @Author: Hlin
* @Date:   2017-07-24 17:14:05
*/
window.autoToJson = function(){
    var indexNum = 0;
    $(".q_editor").addClass('auto_active');
    $(".auto_btn").attr("disabled","disabled");
    var eventCreate = setInterval(function(){
        $(".editor_btn")[indexNum].click();
        $(".editor_btn").eq(indexNum).parents('.q_item').addClass('auto_active');
        setTimeout(function(){
            $(".submit_item_btn").click();
        },30)
        indexNum++;
        if (indexNum == $(".editor_btn").length) {
            clearInterval(eventCreate);
            setTimeout(function(){
                $(".q_editor").removeClass('auto_active');
                $(".auto_btn").removeAttr("disabled");
                $(".editor_btn").parents('.q_item').removeClass('auto_active');
                $(".submit_all_btn button").click();
            },400);
        }
    },500);
}

$(".auto_btn").on('click', function(event) {
    if ($(".q_editor").hasClass('auto_active')) {
        return;
    }
    autoToJson();
});
