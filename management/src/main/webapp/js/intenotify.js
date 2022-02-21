$(function () {
    var t = null;
    t = setTimeout(time, 3000);
    function time() {
        clearTimeout(t);//清除定时器
        $.post("management_war_exploded/arrange/checkApply", {}, function (data) {
            if (data.flag) {
                alert("您有新的加班请假申请待审批，请前往审批");
            }
            t = setTimeout(time, 3000); //设定定时器，循环运行
        });
    }
});